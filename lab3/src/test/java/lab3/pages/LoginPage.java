package lab3.pages;

import lab3.core.BasePage;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void login(String login, String password) {
        typeByJs("//input[@name='UserName']", login);
        typeByJs("//input[@name='UserPassword']", password);
        clickByJs("//input[@type='submit' and (contains(@value,'Sign in') or contains(@value,'Вход'))]");
    }

    public boolean isLoggedIn() {
        return isVisible("//a[contains(normalize-space(.),'Sign out') or contains(normalize-space(.),'Выйти')]");
    }
}
