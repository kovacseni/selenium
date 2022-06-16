package website;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.extension.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.util.List;

public class SeleniumExtension implements BeforeEachCallback, BeforeAllCallback,
        AfterEachCallback, ParameterResolver
{

    private WebDriver driver;

    @Override
    public void afterEach(ExtensionContext extensionContext) throws Exception {
        driver.quit();
    }

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        WebDriverManager.chromedriver().setup();
    }

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
        // Optionally remove existing handlers attached to j.u.l root logger
        SLF4JBridgeHandler.removeHandlersForRootLogger();  // (since SLF4J 1.6.5)
        // add SLF4JBridgeHandler to j.u.l's root logger, should be done once during
        // the initialization phase of your application
        SLF4JBridgeHandler.install();
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("excludeSwitches", List.of("enable-automation", "enable-logging"));
//        options.addArguments("--silent", "headless", "loglevel=3", "--disable-logging");

        driver = new ChromeDriver(options);
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == WebDriver.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return driver;
    }
}
