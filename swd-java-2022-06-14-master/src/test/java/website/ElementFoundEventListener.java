package website;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.openqa.selenium.support.events.WebDriverListener;

@Slf4j
public class ElementFoundEventListener implements WebDriverListener {

    @Override
    public void afterFindElement(WebDriver driver, By locator, WebElement result) {
        log.debug("Element has found: " + result.getText());
    }
}
