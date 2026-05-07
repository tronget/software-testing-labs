package lab3.tests;

import lab3.core.BaseTest;
import lab3.pages.SettingsPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UseCase2SettingsTest extends BaseTest {
    @Test
    public void updateSettings() {
        login();
        SettingsPage settings = new SettingsPage(driver);
        settings.openFromHeader();
        settings.toggleFirstCheckbox();
        settings.save();

        Assertions.assertTrue(settings.isSaved(), "Settings should be saved or remain on settings page");
    }
}
