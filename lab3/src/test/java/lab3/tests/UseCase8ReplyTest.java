package lab3.tests;

import lab3.core.BaseTest;
import lab3.core.TestData;
import lab3.pages.ForumPage;
import lab3.pages.ForumTopicPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UseCase8ReplyTest extends BaseTest {
    @Test
    public void replyToTopic() {
        login();
        ForumPage forum = new ForumPage(driver);
        forum.openEnglishForum();
        ForumTopicPage topic = forum.openLatestTopic();

        topic.reply(TestData.REPLY_BODY);
        topic.toggleSpellcheck();
        topic.preview();

        String updatedReply = TestData.REPLY_BODY + " updated";
        topic.reply(updatedReply);
        topic.preview();
        topic.save();

        boolean replyVisible = topic.isReplyVisible(updatedReply);
        boolean duplicateNotice = topic.isReplyVisible("данное сообщение уже на форуме")
                || topic.isReplyVisible("This message is already on the forum");
        Assertions.assertTrue(replyVisible || duplicateNotice,
                "Reply should be visible or forum should report duplicate message");
    }
}
