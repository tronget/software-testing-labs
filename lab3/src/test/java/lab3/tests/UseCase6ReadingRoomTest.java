package lab3.tests;

import lab3.core.BaseTest;
import lab3.pages.ReadingRoomPage;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class UseCase6ReadingRoomTest extends BaseTest {
    @Test
    public void readTextFromReadingRoom() {
        ReadingRoomPage readingRoom = new ReadingRoomPage(driver);
        readingRoom.openDirect();

        assertTrue(readingRoom.hasAuthor(), "Author should be visible");
        readingRoom.openAuthor();

        assertTrue(readingRoom.hasMaterial(), "Reading room material should be visible");
        readingRoom.openMaterial();

        assertTrue(readingRoom.hasSpecificRussianLine());

        assertTrue(readingRoom.hasChangeLanguageButton(), "Change language button should be visible");
        readingRoom.changeLanguage();

        assertTrue(readingRoom.hasSpecificEnglishLine());
    }
}
