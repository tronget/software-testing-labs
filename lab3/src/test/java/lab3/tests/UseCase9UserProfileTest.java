package lab3.tests;

import lab3.core.BaseTest;
import lab3.pages.UserProfilePage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UseCase9UserProfileTest extends BaseTest {
    @Test
    public void viewUserQuestionsHistory() {
        UserProfilePage profile = new UserProfilePage(driver);
        profile.openUserProfile("Moysha");

        Assertions.assertTrue(profile.hasForumParticipation(), "Forum participation section should be visible");
        Assertions.assertTrue(profile.hasThreadAndReplyStats(), "Threads and replies counts should be visible or empty");
        Assertions.assertTrue(profile.hasQuestionsLink(), "Questions count link should be visible");
        profile.openQuestionsHistory();
        Assertions.assertTrue(profile.hasQuestionsHistory(), "Questions history should be visible");
    }
}
