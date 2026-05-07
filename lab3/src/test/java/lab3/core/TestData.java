package lab3.core;

public final class TestData {
    private TestData() {}

    public static final String LOGIN = System.getProperty("userLogin", "tronget");
    public static final String PASSWORD = System.getProperty("userPassword", "cwjhbcywufe");

    public static final String TOPIC_TITLE = "TPOLR3 Test Topic";
    public static final String TOPIC_BODY = "TPOLR3 test message. Please ignore.";
    public static final String REPLY_BODY = "TPOLR3 test reply.";

    public static final String WORD = "tpolr3testword";
    public static final String TRANSLATION = "tpolr3testtranslation";
    public static final String THEME = "General";

    public static final String SEARCH_WORD = "test";
    public static final String SEARCH_WORD_TRANSLATED = "тест";

    public static final String PATTERN = "t?st";
}
