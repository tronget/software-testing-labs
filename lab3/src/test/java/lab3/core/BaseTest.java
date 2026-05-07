package lab3.core;

import lab3.pages.HomePage;
import lab3.pages.LoginPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

public abstract class BaseTest {
    protected WebDriver driver;

    @BeforeEach
    public void setUp() {
        driver = new lab3.core.DriverFactory().create();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    protected void login() {
        HomePage home = new HomePage(driver);
        home.open();
        LoginPage loginPage = home.goToLogin();
        loginPage.login(lab3.core.TestData.LOGIN, lab3.core.TestData.PASSWORD);
    }
}
