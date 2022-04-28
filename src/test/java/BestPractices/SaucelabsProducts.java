package BestPractices;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SaucelabsProducts {
    WebDriver driver;

    public SaucelabsProducts(WebDriver driver) {
        this.driver = driver;
    }
    private WebElement getShoppingCartElement() {
        return driver.findElement(By.id("add-to-cart-sauce-labs-backpack"));
    }

    public void shoppingCartClick() {
        getShoppingCartElement().click();
    }

}
