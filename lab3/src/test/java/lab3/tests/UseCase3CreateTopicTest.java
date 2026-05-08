package lab3.tests;

import lab3.core.BaseTest;
import lab3.core.TestData;
import lab3.pages.ForumCreateTopicPage;
import lab3.pages.ForumPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UseCase3CreateTopicTest extends BaseTest {
    @Test
    public void createForumTopic() throws InterruptedException {
        login();
        ForumPage forum = new ForumPage(driver);
        forum.openEnglishForum();
        ForumCreateTopicPage createTopic = forum.goToCreateTopic();
        createTopic.fillTopic(TestData.TOPIC_TITLE, TestData.TOPIC_BODY);
        createTopic.preview();
        createTopic.save();

        Assertions.assertTrue(createTopic.isPreviewVisible(TestData.TOPIC_TITLE)
                || createTopic.isPreviewVisible(TestData.TOPIC_BODY),
                "Created topic should appear on the preview or topic page");
    }
}
