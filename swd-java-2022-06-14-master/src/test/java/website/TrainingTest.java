package website;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SeleniumTest
@Slf4j
class TrainingTest {

//    @Test
    @RepeatedTest(15)
    void testTraining(WebDriver driver) {
        driver.get("https://training360.com");
        var wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        var modal = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("NewsletterModal"))));

        wait.until(d -> {
            log.debug("Check opacity");
            return modal.getCssValue("opacity").equals("1");
        });

        log.debug("Modal has appeared");

        // Felesleges, hiszen a wait úgyis TimeoutException-t dobna, ha nem jelenne meg
        assertTrue(modal.isDisplayed());

        wait
                .until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("#NewsletterModalCloseButton > span"))))
                        .click();

        wait.until(ExpectedConditions.invisibilityOf(modal));

        // Felesleges, hiszen a wait úgyis TimeoutException-t dobna, ha nem tűnne el
        assertFalse(modal.isDisplayed());


    }
}
