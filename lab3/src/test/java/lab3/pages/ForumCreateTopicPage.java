package lab3.pages;

import lab3.core.BasePage;
import org.openqa.selenium.WebDriver;

public class ForumCreateTopicPage extends BasePage {
    public ForumCreateTopicPage(WebDriver driver) {
        super(driver);
    }

    public void fillTopic(String title, String body) {
        type("//td[contains(normalize-space(.),'Topic') or contains(normalize-space(.),'Тема')]/following-sibling::td//input", title);
        type("//div[@id='textBox']", body);
    }

    public void toggleNotifyByEmail() {
        click("//input[@type='checkbox' and (contains(@name,'email') or contains(@value,'email'))]");
    }

    public void toggleSpellcheck() {
        click("//input[@type='checkbox' and (contains(@name,'spell') or contains(@value,'spell'))]");
    }

    public void preview() {
        click("//input[@type='submit' and (contains(@value,'Preview') or contains(@value,'Предварительный'))]");
    }

    public void save() {
        click("//input[@type='submit' and (contains(@value,'Save') or contains(@value,'Сохранить соо'))]");
    }

    public boolean isPreviewVisible(String snippet) {
        return isVisible("//*[contains(normalize-space(.),'" + snippet + "')]");
    }
}
