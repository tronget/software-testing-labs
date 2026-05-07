package lab3.pages;

import lab3.core.BasePage;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {
    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        openPath("/");
    }

    public LoginPage goToLogin() {
        clickByJs("//a[contains(normalize-space(.),'Sign in') or contains(normalize-space(.),'Вход')]");
        return new LoginPage(driver);
    }

    public void selectPopularEnglishRussian() {
        clickByJs("//a[contains(text(),'Англо‑русский')] | //a[contains(text(),'English‑Russian')]");
                                                                                                                                                                                                                                                                try {
                                                                                                                                                                                                                                                                    Thread.sleep(500);
                                                                                                                                                                                                                                                                } catch (InterruptedException e) {
                                                                                                                                                                                                                                                                    throw new RuntimeException(e);
                                                                                                                                                                                                                                                                }
    }

    public void searchFor(String query) {
        typeByJs("//input[@id='s']", query);
        clickByJs("//input[@type='submit' and (contains(@value,'Search') or contains(@value,'Поиск') or contains(@value,'Иск'))] | //button[contains(normalize-space(.),'Search') or contains(normalize-space(.),'Поиск')]");
    }

    public ContactsPage goToContacts() {
        clickByJs("//a[contains(text(),'Контакты')]");
        return new ContactsPage(driver);
    }

    public LanguagePage goToLanguageMenu() {
        clickByJs("//a[normalize-space()='Russian']");
        return new LanguagePage(driver);
    }

    public ReadingRoomPage goToReadingRoom() {
        click("//a[contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'reading') or contains(normalize-space(.),'Читаль')]");
        return new ReadingRoomPage(driver);
    }

    public PatternSearchPage goToPatternSearch() {
        click("//a[contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'containing') or contains(normalize-space(.),'содержащ')]");
        return new PatternSearchPage(driver);
    }

    public ForumPage goToForum() {
        click("//a[contains(normalize-space(.),'Forum') or contains(normalize-space(.),'Форум')]");
        return new ForumPage(driver);
    }
}
