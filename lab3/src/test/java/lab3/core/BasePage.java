package lab3.core;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasePage {
    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(12));
    }

    protected String baseUrl() {
        return System.getProperty("baseUrl", "https://www.multitran.com");
    }

    protected void openPath(String path) {
        driver.get(baseUrl() + path);
    }

    protected WebElement waitVisible(String xpath) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }

    protected void click(String xpath) {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))).click();
    }

    protected void clickByJs(String xpath) {
        WebElement element = waitVisible(xpath);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    protected void type(String xpath, String text) {
        WebElement element = waitVisible(xpath);
        element.clear();
        element.sendKeys(text);
    }

    protected String textOf(String xpath) {
        return waitVisible(xpath).getText();
    }

    protected boolean isVisible(String xpath) {
        try {
            return waitVisible(xpath).isDisplayed();
        } catch (Exception ex) {
            return false;
        }
    }

    protected void selectByVisibleText(String selectXpath, String optionText) {
        WebElement selectElement = waitVisible(selectXpath);
        new Select(selectElement).selectByVisibleText(optionText);
    }
}
