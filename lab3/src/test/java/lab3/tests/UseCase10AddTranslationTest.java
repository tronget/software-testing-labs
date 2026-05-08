package lab3.tests;

import lab3.core.BaseTest;
import lab3.core.TestData;
import lab3.pages.AddTranslationPage;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class UseCase10AddTranslationTest extends BaseTest {
    @Test
    public void addTranslation() {
        login();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        AddTranslationPage page = new AddTranslationPage(driver);
        page.openPersRussianDictionary();

        assertTrue(page.hasAddTranslationButton(), "Add Translation button should be visible in the English dictionary");


        page.clickAddTranslationButton();
        page.fillTranslation(TestData.WORD, TestData.TRANSLATION);
        page.chooseTheme(TestData.THEME);
        page.save();
        page.confirm();

        assertTrue(page.isSavedOrConfirmation(), "Translation should be saved or require confirmation");
    }
}