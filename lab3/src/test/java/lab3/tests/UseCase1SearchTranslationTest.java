package lab3.tests;

import lab3.core.BaseTest;
import lab3.core.TestData;
import lab3.pages.HomePage;
import lab3.pages.SearchResultsPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UseCase1SearchTranslationTest extends BaseTest {
    @Test
    public void searchTranslation() {
        HomePage home = new HomePage(driver);
        home.open();
        home.selectPopularEnglishRussian();
        home.searchFor(TestData.SEARCH_WORD);

        SearchResultsPage results = new SearchResultsPage(driver);
        Assertions.assertTrue(results.hasTranslate() || results.hasSuggestionsOrEmptyMessage(), "Search results or suggestions should be visible");
    }
}
