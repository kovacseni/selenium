package website;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@SeleniumTest
class WaitTest {

    //@Test
    @RepeatedTest(15)
    void testAlertWithTimeout(WebDriver driver) {
        driver.get("http://127.0.0.1:5500/messages/index.html");
        var wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.findElement(By.id("liveAlertTimeoutBtn")).click();

        var alert = wait
                .until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector(".alert")));
        assertThat(alert.getText())
                .startsWith("Nice,");
    }
}
