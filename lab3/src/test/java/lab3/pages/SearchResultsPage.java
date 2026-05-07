package lab3.pages;

import lab3.core.BasePage;
import lab3.core.TestData;
import org.openqa.selenium.WebDriver;

public class SearchResultsPage extends BasePage {
    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }

    public boolean hasTranslate() {
        String translatedWord = TestData.SEARCH_WORD_TRANSLATED;
        String xpath = String.format("//table//tr//td[.//a[contains(normalize-space(.), '%s')] or contains(normalize-space(.), '%s')]", translatedWord, translatedWord);
        return isVisible(xpath);
    }

    public boolean hasSuggestionsOrEmptyMessage() {
        return isVisible("//*[contains(normalize-space(.),'Nothing found') or contains(normalize-space(.),'not found') or contains(normalize-space(.),'Ничего не найдено') or contains(normalize-space(.),'Предлагается')]");
    }
}
