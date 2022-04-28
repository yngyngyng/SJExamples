package BestPractices;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CheckoutForm {
    WebDriver driver;

    public CheckoutForm(WebDriver driver) {
        this.driver = driver;
    }

    private WebElement getFirstNameField() {
          return driver.findElement(By.id("first-name"));
    }
    private WebElement getLastNameField() {
        return driver.findElement(By.id("last-name"));
    }
    private WebElement getZipCodeField() {
        return driver.findElement(By.id("postal-code"));
    }
    private WebElement getContinueButton() {
        return driver.findElement(By.id("continue"));
    }

    public void fillOutCheckoutForm() {
        getFirstNameField().sendKeys("firstName");
        getLastNameField().sendKeys("lastName");
        getZipCodeField().sendKeys("zipCode");
    }

    public void clickContinueButton() {
        getContinueButton().click();
    }
}
