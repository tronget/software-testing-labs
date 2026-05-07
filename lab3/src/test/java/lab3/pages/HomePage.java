package lab3.pages;

import lab3.core.BasePage;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {
    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        openPath("/");
    }

    public LoginPage goToLogin() {
        click("//a[contains(normalize-space(.),'Sign in') or contains(normalize-space(.),'Вход')]");
        return new LoginPage(driver);
    }

    public void selectPopularEnglishRussian() {
        clickByJs("//a[@href='/m.exe?l1=1&l2=2']");
    }

    public void searchFor(String query) {
        type("//input[(self::input or self::textarea) and not(@type='hidden') and (@name='s' or @id='s' or contains(@placeholder,'type word') or contains(@aria-label,'type word') or @type='search' or @type='text')][1]", query);
        clickByJs("//input[@type='submit' and (contains(@value,'Search') or contains(@value,'Поиск') or contains(@value,'Иск'))] | //button[contains(normalize-space(.),'Search') or contains(normalize-space(.),'Поиск')]");
    }

    public ContactsPage goToContacts() {
        click("//a[contains(normalize-space(.),'Contacts') or contains(normalize-space(.),'Контак')]");
        return new ContactsPage(driver);
    }

    public LanguagePage goToLanguageMenu() {
        click("//a[contains(normalize-space(.),'English') or contains(normalize-space(.),'Рус') or contains(normalize-space(.),'Language')]");
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
