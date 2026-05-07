package lab3.pages;

import lab3.core.BasePage;
import org.openqa.selenium.WebDriver;

public class ContactsPage extends BasePage {
    public ContactsPage(WebDriver driver) {
        super(driver);
    }

    public boolean hasContactInfo() {
        return isVisible("//*[contains(normalize-space(.),'pom@aha.ru')]");
    }
}
