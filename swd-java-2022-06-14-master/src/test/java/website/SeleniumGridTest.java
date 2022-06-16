package website;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

class SeleniumGridTest {

    @Test
    void testOnGrid() throws Exception {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        var driver = new RemoteWebDriver(new URL("http://localhost:4444"), options);

        driver.get("http://host.docker.internal:8080");
        driver.quit();
    }
}
