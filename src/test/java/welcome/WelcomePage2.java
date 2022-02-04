package welcome;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class WelcomePage2 {

    private WebDriver webDriver;

    public WelcomePage2 go() {
        WebDriverManager manager = WebDriverManager.chromedriver();
        manager.setup();

        webDriver = new ChromeDriver();
        webDriver.get("https://training360.github.io/swd-java-2021-11-15/welcome/");
        return this;
    }

    public WelcomePage2 type(String name) {
        WebElement inputField = webDriver.findElement(By.id("name-input"));
        inputField.sendKeys(name);
        return this;
    }

    public WelcomePage2 press() {
        WebElement submitButton = webDriver.findElement((By.id("welcome-button")));
        submitButton.click();
        return this;
    }

    public String readMessage() {
        WebElement welcomeDiv = webDriver.findElement(By.id("welcome-div"));
        String message = welcomeDiv.getText();
        return message;
    }
}
