package website;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.openqa.selenium.support.locators.RelativeLocator.with;

@SeleniumTest
class GridTest {

    @Test
    void testRelative(WebDriver webDriver) {
        webDriver.get("http://127.0.0.1:5500/grid/index.html");
        var cell5 = webDriver.findElement(By.cssSelector("tr:nth-child(2) > td:nth-child(2)"));
        var cell2 = webDriver.findElement(with(By.tagName("td")).below(cell5));

        assertEquals("2", cell2.getText());
        boolean elementExists = webDriver.findElements(By.cssSelector("ggrrr"))
                .isEmpty();
        System.out.println(elementExists);
    }

    @Test
    void testScreenshot(WebDriver webDriver) throws IOException {
        webDriver.get("http://127.0.0.1:5500/grid/index.html");
        var file = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
        System.out.println(file);
        Files.move(file.toPath(), Path.of("./screenshot.png"), StandardCopyOption.REPLACE_EXISTING);

        file = webDriver.findElement(By.cssSelector("tr:nth-child(2)")).getScreenshotAs(OutputType.FILE);
        Files.move(file.toPath(), Path.of("./screenshot-component.png"), StandardCopyOption.REPLACE_EXISTING);

        System.out.println(UUID.randomUUID().toString());
    }
}
