package lab3.pages;

import lab3.core.BasePage;
import org.openqa.selenium.WebDriver;

public class ForumTopicPage extends BasePage {
    public ForumTopicPage(WebDriver driver) {
        super(driver);
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

    public void reply(String text) {
        String[] replyXpaths = new String[] {
                "//label[contains(normalize-space(.),'Написать ответ') or contains(normalize-space(.),'Write reply')]/following::textarea[1]",
                "//td[contains(normalize-space(.),'Написать ответ') or contains(normalize-space(.),'Write reply')]/following-sibling::td//textarea",
                "//textarea[@name='Msg' or @name='msg' or @name='Message' or @name='Body']",
                "//textarea[@id='Msg' or @id='msg' or contains(@id,'message')]",
                "//textarea",
                "//div[@contenteditable='true' or @role='textbox']"
        };
        org.openqa.selenium.WebElement input = findFirst(replyXpaths);
        if (input == null) {
            throw new org.openqa.selenium.TimeoutException("Reply input not found");
        }
        setTextByJs(input, text);
    }

    public void toggleSpellcheck() {
        String spellcheckXpath = "//label[contains(normalize-space(.),'Проверка орфографии') or contains(normalize-space(.),'Spellcheck')]//input[@type='checkbox']" +
                " | //input[@type='checkbox' and (contains(@name,'spell') or contains(@value,'spell'))]";
        if (isVisible(spellcheckXpath)) {
            clickByJs(spellcheckXpath);
        }
    }

    public void preview() {
        String previewXpath = "//input[@type='submit' and (contains(@value,'Preview') or contains(@value,'Предварительный') " +
                "or contains(@value,'Просмотр') or contains(@value,'Предпросмотр'))]" +
                " | //button[contains(normalize-space(.),'Preview') or contains(normalize-space(.),'Предварительный') " +
                "or contains(normalize-space(.),'Просмотр') or contains(normalize-space(.),'Предпросмотр')]" +
                " | //a[contains(normalize-space(.),'Preview') or contains(normalize-space(.),'Предварительный') " +
                "or contains(normalize-space(.),'Просмотр') or contains(normalize-space(.),'Предпросмотр')]";
        clickByJs(previewXpath);
    }

    public void save() {
        String saveXpath = "//input[@type='submit' and (contains(@value,'Save') or contains(@value,'Сохранить') " +
                "or contains(@value,'Сохранить сообщение'))]" +
                " | //button[contains(normalize-space(.),'Save') or contains(normalize-space(.),'Сохранить') " +
                "or contains(normalize-space(.),'Сохранить сообщение')]" +
                " | //a[contains(normalize-space(.),'Save') or contains(normalize-space(.),'Сохранить') " +
                "or contains(normalize-space(.),'Сохранить сообщение')]";
        clickByJs(saveXpath);
    }

    public boolean isReplyVisible(String snippet) {
        return isVisible("//*[contains(normalize-space(.),'" + snippet + "')]");
    }
}
