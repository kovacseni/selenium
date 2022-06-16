package website;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SeleniumTest
@Slf4j
class LocationsTest {

    @Test
    @DisplayName("Create location, assert message, then find in table")
    // SOHA NE ÍRJUNK ILYEN TESZTET!
    void testCreate(WebDriver driver) {
        driver = new EventFiringDecorator(new ElementFoundEventListener()).decorate(driver);

        driver.get("http://localhost:8080");

        driver.findElement(By.linkText("Create location")).click();

        var name = "Test Location " + UUID.randomUUID();
        driver.findElement(By.id("location-name"))
                .sendKeys(name);
        driver.findElement(By.id("location-coords"))
                .sendKeys("1,1");
        driver.findElement(By.cssSelector("input.btn-primary[value='Create location']"))
                .click();

        var wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        var message = wait.until(
                ExpectedConditions.visibilityOf(driver.findElement(By.id("message-div"))));
        assertEquals("Location has been created", message.getText());


    }

    LocationsPageObject page;

    @BeforeEach
    void initPage(WebDriver driver) {
        page = new LocationsPageObject(driver);
    }

    @Test
    void testCreateWithPageObject() {
        // Ne hivatkozzunk WebDriverre, mert a helye a page objectben van
        var name = "Test Location " + UUID.randomUUID();
        page
            .go()
            .clickOnCreateLocationLink()
            .fillForm(name)
            .clickOnCreateButton();


        assertEquals("Location has been created", page.waitForMessageAndGetText());
        Location created = page.waitForLocationAppears(name);

        assertEquals("1, 1", created.getCoords());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/MOCK_DATA.csv", numLinesToSkip = 1)
    void testCreateLocationDDT(String name, String lat, String lon) {
        log.debug("Location: {} ({}, {})", name, lat, lon);

        page
                .go()
                .clickOnCreateLocationLink()
                .fillForm(name, lat + "," + lon)
                .clickOnCreateButton();
    }

    @Test
    void testEdit() {
        var fixture = new LocationDatabaseFixture();
        fixture.deleteLocations();
        fixture.createLocation("Test Location Name", 10, 10);
    }
}
