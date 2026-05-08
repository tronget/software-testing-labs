package lab3.core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverFactory {
    public WebDriver create() {
        String browser = System.getProperty("browser", "chrome").toLowerCase();
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));

        switch (browser) {
            case "firefox":
                String firefoxBinary = System.getProperty("firefoxBinary", System.getenv("FIREFOX_BINARY"));
                if (firefoxBinary == null || firefoxBinary.isBlank() || !new java.io.File(firefoxBinary).exists()) {
                    browser = "chrome";
                } else {
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    firefoxOptions.setBinary(firefoxBinary);
                    if (headless) {
                        firefoxOptions.addArguments("-headless");
                    }
                    return new FirefoxDriver(firefoxOptions);
                }
            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                if (headless) {
                    chromeOptions.addArguments("--headless=new");
                }
                chromeOptions.addArguments("--window-size=1280,900");
                return new ChromeDriver(chromeOptions);
        }
    }
}
