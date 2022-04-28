package BestPractices;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SaucelabsCart {
    WebDriver driver;

    public SaucelabsCart(WebDriver driver) {
        this.driver = driver;
    }
    private WebElement getCheckoutButton() {
        return driver.findElement(By.id("finish"));
    }

    public void startCheckout() {
        getCheckoutButton().click();
    }
}
