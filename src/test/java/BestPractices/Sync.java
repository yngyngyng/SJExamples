package BestPractices;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.time.Duration;

public class Sync {
    WebDriver driver;
    String elementExistsInDOM = "https://the-internet.herokuapp.com/dynamic_loading/1";
    String elementRenderedAfter = "https://the-internet.herokuapp.com/dynamic_loading/2";
    By locator = By.id("finish");

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void implicitWaitFindsHiddenElement() {
        driver.get(elementExistsInDOM);
        driver.findElement(locator);
    }

    @Test
    public void implicitWaitThrowsNoSuchElementException() {
        driver.get(elementRenderedAfter);
        driver.findElement(locator);
    }

    @Test
    public void configuredImplicitWait() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get(elementRenderedAfter);
        driver.findElement(locator);
    }

    @Test(expected = org.openqa.selenium.TimeoutException.class)
    public void explicitWaitFixesImplicitWaitIssues() {
        driver.get(elementExistsInDOM);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    @Test
    public void explicitWaitWhenElementPresent() {
        driver.get(elementExistsInDOM);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    @Test
    public void correctSynchronization() {
        driver.get(elementRenderedAfter);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement startButton =
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("start"))).findElement(By.xpath("//button"));
        startButton.click();
        WebElement helloWorldElement =
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("finish")));
    }
}
