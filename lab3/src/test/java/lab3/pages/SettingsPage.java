package lab3.pages;

import lab3.core.BasePage;
import org.openqa.selenium.WebDriver;

public class SettingsPage extends BasePage {
    public SettingsPage(WebDriver driver) {
        super(driver);
    }

    public void openFromHeader() {
        clickByJs("//a[contains(normalize-space(.),'Settings') or contains(normalize-space(.),'Настройки')]");
    }

    public void toggleFirstCheckbox() {
        clickByJs("(//input[@type='checkbox'])[1]");
    }

    public void save() {
        clickByJs("//input[@type='submit' and (contains(@value,'Save') or contains(@value,'Сохранить'))]");
    }

    public boolean isSaved() {
        return isVisible("//*[contains(normalize-space(.),'saved') or contains(normalize-space(.),'Сохранено')]");
    }
}
