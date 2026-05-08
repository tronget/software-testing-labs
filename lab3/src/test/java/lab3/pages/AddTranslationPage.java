package lab3.pages;

import lab3.core.BasePage;
import org.openqa.selenium.WebDriver;

public class AddTranslationPage extends BasePage {
    public AddTranslationPage(WebDriver driver) {
        super(driver);
    }

    public void openPersRussianDictionary() {
        openPath("/m.exe?l1=2&l2=52");
    }

    public boolean hasAddTranslationButton() {
//        isVisible("//a[contains(text(),'Add')])[2]")
        return isVisible("//a[contains(normalize-space(.),'Add') or contains(normalize-space(.),'Добавить')]");
    }

    public void clickAddTranslationButton() {
        clickByJs("//a[contains(normalize-space(.),'Add') or contains(normalize-space(.),'Добавить')]");
    }

    public void fillTranslation(String word, String translation) {
        typeByJs("//input[@id='orig']", word);
        typeByJs("//input[@id='trans']", translation);
    }

    public void chooseTheme(String theme) {
        if (isVisible("//select")) {
            selectByVisibleText("//select", theme);
        }
    }

    public void save() {
        clickByJs("//input[@type='submit' and (contains(@value,'Save') or contains(@value,'Сохранить'))]");
    }

    public void confirm() {
        clickByJs("//input[@type='submit' and (contains(@value,'Save entry') or contains(@value,'Сохранить статью'))]");
    }

    public boolean isSavedOrConfirmation() {
        return isVisible("//*[contains(normalize-space(.),'Запись сохранена') or contains(normalize-space(.),'Сохранено') or contains(normalize-space(.),'confirm') or contains(normalize-space(.),'подтверд')]");
    }
}
