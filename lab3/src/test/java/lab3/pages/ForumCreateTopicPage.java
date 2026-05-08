package lab3.pages;

import lab3.core.BasePage;
import org.openqa.selenium.WebDriver;

public class ForumCreateTopicPage extends BasePage {
    public ForumCreateTopicPage(WebDriver driver) {
        super(driver);
    }

    private org.openqa.selenium.WebElement findTitleNearBody(org.openqa.selenium.WebElement bodyInput) {
        try {
            return bodyInput.findElement(org.openqa.selenium.By.xpath("ancestor::form[1]//input[not(@type) or @type='text'][1]"));
        } catch (org.openqa.selenium.NoSuchElementException ex) {
            return null;
        }
    }

    public void fillTopic(String title, String body) {
        String[] bodyXpaths = new String[] {
                "//textarea[@name='Msg' or @name='Message' or @name='Body']",
                "//textarea",
                "//div[@id='textBox' or @contenteditable='true']",
                "//div[contains(@class,'editor') and (@contenteditable='true' or @role='textbox')]"
        };
        org.openqa.selenium.WebElement bodyInput = findFirst(bodyXpaths);
        if (bodyInput == null) {
            throw new org.openqa.selenium.TimeoutException("Topic body input not found");
        }

        org.openqa.selenium.WebElement titleInput = findTitleNearBody(bodyInput);
        if (titleInput == null) {
            String[] titleXpaths = new String[] {
                    "//input[@name='Subj' or @name='Subject' or @name='Topic' or @name='Title']",
                    "//input[@type='text' and (contains(@name,'Subj') or contains(@name,'Subject') or contains(@name,'Topic') or contains(@name,'Title') or contains(@id,'title') or contains(@id,'subject'))]",
                    "//td[contains(normalize-space(.),'Topic') or contains(normalize-space(.),'Тема')]/following-sibling::td//input"
            };
            titleInput = findFirst(titleXpaths);
        }
        if (titleInput == null) {
            throw new org.openqa.selenium.TimeoutException("Topic title input not found");
        }

        setTextByJs(titleInput, title);
        setTextByJs(bodyInput, body);
    }

    public void toggleNotifyByEmail() {
        click("//input[@type='checkbox' and (contains(@name,'email') or contains(@value,'email'))]");
    }

    public void toggleSpellcheck() {
        click("//input[@type='checkbox' and (contains(@name,'spell') or contains(@value,'spell'))]");
    }

    public void preview() {
        clickByJs("//input[@type='submit' and (contains(@value,'Preview') or contains(@value,'Предварительный'))]");
    }

    public void save() {
        clickByJs("//input[@type='submit' and (contains(@value,'Save') or contains(@value,'Сохранить соо'))]");
    }

    public boolean isPreviewVisible(String snippet) {
        return isVisible("//*[contains(normalize-space(.),'" + snippet + "')]");
    }

    private org.openqa.selenium.WebElement findInFrames(String xpath) {
        driver.switchTo().defaultContent();
        java.util.List<org.openqa.selenium.WebElement> elements = driver.findElements(org.openqa.selenium.By.xpath(xpath));
        if (!elements.isEmpty()) {
            return elements.get(0);
        }
        java.util.List<org.openqa.selenium.WebElement> frames = driver.findElements(org.openqa.selenium.By.tagName("iframe"));
        for (org.openqa.selenium.WebElement frame : frames) {
            driver.switchTo().frame(frame);
            elements = driver.findElements(org.openqa.selenium.By.xpath(xpath));
            if (!elements.isEmpty()) {
                return elements.get(0);
            }
            driver.switchTo().defaultContent();
        }
        return null;
    }

    private org.openqa.selenium.WebElement findFirst(String[] xpaths) {
        for (String xpath : xpaths) {
            org.openqa.selenium.WebElement element = findInFrames(xpath);
            if (element != null) {
                return element;
            }
        }
        for (String xpath : xpaths) {
            try {
                return waitVisible(xpath);
            } catch (Exception ignored) {
                // try next
            }
        }
        return null;
    }

    private void setTextByJs(org.openqa.selenium.WebElement input, String text) {
        String tagName = input.getTagName();
        boolean useValue = "input".equalsIgnoreCase(tagName) || "textarea".equalsIgnoreCase(tagName);
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});" +
                        (useValue
                                ? "arguments[0].value = arguments[1];"
                                : "arguments[0].innerText = arguments[1];") +
                        "arguments[0].dispatchEvent(new Event('input', {bubbles: true}));",
                input,
                text
        );
    }
}
