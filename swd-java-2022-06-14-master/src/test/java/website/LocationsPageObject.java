package website;

import lombok.AllArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocationsPageObject {

    private WebDriver driver;

    private WebDriverWait wait;

    @FindBy(linkText = "Create location")
    private WebElement createLocationLink;

    @FindBy(id = "location-name")
    private WebElement nameInput;

    @FindBy(id = "location-coords")
    private WebElement coordsInput;

    @FindBy(css = "input.btn-primary[value='Create location']")
    private WebElement createButton;

    @FindBy(id = "message-div")
    private WebElement messageDiv;

    public LocationsPageObject(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public LocationsPageObject go() {
        driver.get("http://localhost:8080");
        return this;
    }

    public LocationsPageObject clickOnCreateLocationLink() {
        createLocationLink.click();
        return this;
    }

    public LocationsPageObject fillForm(String name, String coords) {
        nameInput.sendKeys(name);
        coordsInput.sendKeys(coords);
        return this;
    }

    public LocationsPageObject fillForm() {
        fillForm("Anonymous", "1,1");
        return this;
    }

    public LocationsPageObject fillForm(String name) {
        fillForm(name, "1,1");
        return this;
    }

    public LocationsPageObject clickOnCreateButton() {
        createButton.click();
        return this;
    }

    public String waitForMessageAndGetText() {
        var message = wait.until(
                ExpectedConditions.visibilityOf(driver.findElement(By.id("message-div"))));
        return message.getText();
    }

    public Location waitForLocationAppears(String name) {
        wait.until(d ->
                d.findElements(By.cssSelector("tr > td:nth-child(2)"))
                        .stream().map(WebElement::getText)
                        .filter(t -> t.equals(name)).count() == 1
        );

        var td = driver.findElements(By.cssSelector("tr > td:nth-child(2)"))
                .stream()
                .filter(e -> e.getText().equals(name)).findAny().orElseThrow();
        var parent = (WebElement) ((JavascriptExecutor) driver).executeScript(
                "return arguments[0].parentNode;", td);

        String coords = parent.findElement(By.cssSelector("tr > td:nth-child(3)")).getText();

        return new Location(name, coords);
    }
}
