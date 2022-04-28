import BestPractices.CheckoutForm;
import BestPractices.SaucedemoLogin;
import BestPractices.SaucelabsCart;
import BestPractices.SaucelabsProducts;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Set;


public class ExampleTests {
    WebDriver driver;
    WebElement element;

    @BeforeClass
    public void setupClass() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void dropdown()  {
        driver.get("https://the-internet.herokuapp.com/dropdown");
        element = driver.findElement(By.id("dropdown"));
        element.click();
        WebElement option1 = element.findElement(By.cssSelector("option[value='1']"));
        WebElement option2 = element.findElement(By.cssSelector("option[value='2']"));
        option1.click();
        Assert.assertTrue(option1.isSelected());
        Assert.assertFalse(option2.isSelected());
    }

    @Test
    public void hover() {
        driver.get("https://the-internet.herokuapp.com/hovers");
        element = driver.findElement(By.className("figure"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();

        element = driver.findElement(By.xpath("//*[contains(text(), 'name: user1')]"));
        Assert.assertTrue(element.isDisplayed(), "user1 expected because of the hover");
    }

    @Test
    public void rightClick() {
        driver.navigate().to("https://the-internet.herokuapp.com/context_menu");
        element = driver.findElement(By.id("hot-spot"));
        Actions actions = new Actions(driver);
        actions.contextClick(element).perform();
        driver.switchTo().alert().accept();
    }

    @Test
    public void keyPresses() {
        driver.get("https://the-internet.herokuapp.com/key_presses");
        element = driver.findElement(By.id("target"));
        element.click();

        Actions actions = new Actions(driver);
        actions.
                sendKeys(Keys.ARROW_RIGHT).
                pause(1000).
                perform();
        element = driver.findElement(By.id("result"));
        Assert.assertEquals(element.getText(), "You entered: RIGHT", "Clicked Right Arrow Key");
    }

    @Test
    public void getCSSValue() {
        driver.get("https://ultimateqa.com/simple-html-elements-for-automation");
        element = driver.findElement(By.linkText("Clickable Icon"));
        String link = element.getAttribute("href");
        Assert.assertEquals(link, "https://ultimateqa.com/link-success/", "Link correct.");
        Assert.assertEquals(element.getCssValue("background-origin"), "padding-box", "CSS Value Correct.");
    }

    @Test
    public void windowsFrames() {
        driver.get("https://the-internet.herokuapp.com/windows");

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.open('https://the-internet.herokuapp.com/windows/new');");

        String originalWindow = driver.getWindowHandle();
        Set<String> handles = driver.getWindowHandles();
        handles.remove(originalWindow);

        String nextWindow = String.valueOf(handles.iterator().next());

        driver.switchTo().window(nextWindow);
        Assert.assertEquals(driver.getTitle(), "New Window");

        driver.close();
        driver.switchTo().window(originalWindow);
        Assert.assertEquals(driver.getTitle(), "The Internet");
    }

    @Test
    public void frames() {
        driver.get("https://the-internet.herokuapp.com/nested_frames");

        driver.findElement(By.name("frame-top"));
        driver.switchTo().frame(1);
        Assert.assertEquals(driver.findElement(By.tagName("body")).getText(), "BOTTOM");

        driver.switchTo().parentFrame();
        driver.switchTo().frame("frame-top");
        driver.switchTo().frame("frame-left");
        Assert.assertEquals(driver.findElement(By.tagName("body")).getText(), "LEFT");

        driver.switchTo().defaultContent();
        Assert.assertTrue(driver.findElement(By.name("frame-top")).getSize().height > 0);
    }

    @Test
    public void alerts() {
        driver.get("https://the-internet.herokuapp.com/javascript_alerts");

        driver.findElement(By.xpath("//*[@id=\"content\"]/div/ul/li[1]/button")).click();
        driver.switchTo().alert().dismiss();

        driver.findElement(By.xpath("//*[@id=\"content\"]/div/ul/li[2]/button")).click();
        driver.switchTo().alert().accept();

        driver.findElement(By.xpath("//*[@id=\"content\"]/div/ul/li[3]/button")).click();
        Alert inputAlert = driver.switchTo().alert();
        inputAlert.sendKeys("Automated Alert!");
        inputAlert.accept();
    }

    @Test
    public void cypressPassword() {
        driver.get("https://example.cypress.io/commands/actions");

        element = driver.findElement(By.cssSelector(".action-focus"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().perform();
        Assert.assertTrue(
                driver.findElement(By.xpath("//*[@for='password1']")).getAttribute("style").contains("color: orange;"));
    }

    @Test
    public void cookies() {
        driver.get("https://example.cypress.io/commands/cookies");
        element = driver.findElement(By.cssSelector(".set-a-cookie"));
        element.click();

        var cookie = driver.manage().getCookieNamed("token");
        Assert.assertEquals(cookie.getValue(), "123ABC");
    }

    @Test
    public void Login() {
        driver.get("https://www.saucedemo.com/");
        SaucedemoLogin login = new SaucedemoLogin(driver);
        PageFactory.initElements(driver, login);
        var userName = "standard_user";
        var password = "secret_sauce";
        login.login(userName, password);
    }

    @Test
    public void addItemToCart() {
        Login();
        driver.get("https://www.saucedemo.com/inventory.html");
        SaucelabsProducts item = new SaucelabsProducts(driver);
        PageFactory.initElements(driver, item);
        item.shoppingCartClick();
    }

    @Test
    public void shoppingCart() {
        addItemToCart();
        driver.get("https://www.saucedemo.com/checkout-step-one.html");
        CheckoutForm checkout = new CheckoutForm(driver);
        PageFactory.initElements(driver, checkout);
        checkout.fillOutCheckoutForm();
        checkout.clickContinueButton();
    }

    @Test
    public void completePurchase() {
        shoppingCart();
        driver.get("https://www.saucedemo.com/checkout-step-two.html");
        SaucelabsCart finish = new SaucelabsCart(driver);
        PageFactory.initElements(driver, finish);
        finish.startCheckout();
    }
}
