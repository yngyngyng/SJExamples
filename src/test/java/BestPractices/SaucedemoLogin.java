package BestPractices;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SaucedemoLogin {
    WebDriver driver;

    public SaucedemoLogin(WebDriver driver) {
        this.driver = driver;
    }
    private WebElement getUserNameField() {
        return driver.findElement(By.id("user-name"));
    }

    public void login(String userName, String password) {
        getUserNameField().sendKeys(userName);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.className("btn_action")).submit();
    }
}
