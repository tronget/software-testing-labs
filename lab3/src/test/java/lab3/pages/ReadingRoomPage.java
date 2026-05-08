package lab3.pages;

import lab3.core.BasePage;
import org.openqa.selenium.WebDriver;

public class ReadingRoomPage extends BasePage {
    public ReadingRoomPage(WebDriver driver) {
        super(driver);
    }

    public void openDirect() {
        openPath("/m.exe?a=41");
    }

    public boolean hasAuthor() {
        return isVisible("//a[contains(text(),'Евгений Замятин')]");
    }

    public void openAuthor() {
        clickByJs("//a[contains(text(),'Евгений Замятин')]");
    }

    public boolean hasMaterial() {
        return isVisible("//a[contains(text(),'Мы')]");
    }

    public void openMaterial() {
        clickByJs("//a[contains(text(),'Мы')]");
    }

    public boolean hasChangeLanguageButton() {
        return isVisible("//a[contains(text(),'⇄')]");
    }

    public void changeLanguage() {
        clickByJs("//a[contains(text(),'⇄')]");
    }

    public boolean hasSpecificRussianLine() {
        return isVisible("//a[contains(text(),'Конспект: Объявление. Мудрейшая из линий. Поэма')]");
    }

    public boolean hasSpecificEnglishLine() {
        return isVisible("//a[contains(text(),'An Announcement The Wisest of Lines A Poem')]");
    }
}
