package lab3.pages;

import lab3.core.BasePage;
import org.openqa.selenium.WebDriver;

public class ForumPage extends BasePage {
    public ForumPage(WebDriver driver) {
        super(driver);
    }

    public void openEnglishForum() {
        openPath("/m.exe?a=2&l1=1");
    }

    public ForumCreateTopicPage goToCreateTopic() {
        clickByJs("//a[contains(normalize-space(.),'New thread') or contains(normalize-space(.),'New topic') or contains(normalize-space(.),'Создать тему')]");
        return new ForumCreateTopicPage(driver);
    }

    public ForumTopicPage openLatestTopic() {
        String topicLink = "(//tr[.//a[contains(@href,'UserName') or contains(@href,'a=116') or contains(@href,'user')]]" +
                "//a[not(contains(@href,'UserName')) and not(contains(@href,'a=116')) and not(contains(@href,'user'))])[1]";
        clickByJs(topicLink);
        return new ForumTopicPage(driver);
    }
}
