package locating.elements;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.time.Duration;

public class LocatingElements {
    WebDriver driver;

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void Checkout() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        //Go to Page
        driver.get("https://www.saucedemo.com");

        //Login as user
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        //Add item to the cart
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        driver.findElement(By.className("shopping_cart_link")).click();
        driver.findElement(By.id("checkout")).click();

        //Input Name
        driver.findElement(By.id("first-name")).sendKeys("FirstName");
        driver.findElement(By.id("last-name")).sendKeys("LastName");
        driver.findElement(By.id("postal-code")).sendKeys("ZIP101");
        driver.findElement(By.id("continue")).click();

        //Final Checkout
        driver.findElement(By.id("finish")).click();

        Assert.assertTrue(driver.findElement(By.cssSelector("#checkout_complete_container")).isDisplayed());
   }
}
