package welcome;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

class WelcomeTest {

    @Test
    void testInput() {
        WebDriverManager manager = WebDriverManager.chromedriver();
        manager.setup();

        WebDriver webDriver = new ChromeDriver();
        webDriver.get("https://training360.github.io/swd-java-2021-11-15/welcome/");

        WebElement inputField = webDriver.findElement(By.id("name-input"));
        inputField.sendKeys("John Doe");

        WebElement submitButton = webDriver.findElement((By.id("welcome-button")));
        submitButton.click();

        WebElement welcomeDiv = webDriver.findElement(By.id("welcome-div"));
        String message = welcomeDiv.getText();

        assertEquals("Hello John Doe!", message);
    }

    @Test
    void testInput2() {
        WelcomePage welcomePage = new WelcomePage();
        welcomePage.go();
        welcomePage.type("John Doe");
        welcomePage.press();
        String message = welcomePage.readMessage();

        assertEquals("Hello John Doe!", message);
    }

    @Test
    void testInput3() {
        String message = new WelcomePage2()
                .go()
                .type("Jack Doe")
                .press()
                .readMessage();

        assertEquals("Hello Jack Doe!", message);
    }
}