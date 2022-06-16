package website;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.WebDriver;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SeleniumTest
class LocationsTest {


    // SOHA NE ÍRJUNK ILYEN TESZTET!!! EHELYETT MINDIG PAGE OBJECTET HASZNÁLJUNK
//    @Test
//    @DisplayName("Create location, assert message, then find in table")
//    void testCreate(WebDriver driver) {
//        driver.get("http://localhost:8080");
//
//        driver.findElement(By.linkText("Create location")).click();
//
//        String name = "Test location " + UUID.randomUUID().toString();
//        driver.findElement(By.id("location-name")).sendKeys(name);
//        driver.findElement(By.id("location-coords")).sendKeys("1,1");
//        driver.findElement(By.cssSelector("input.btn-primary[value='Create location']")).click()  ;
//
//    var wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//    var message = wait.until(
//            ExpectedConditions.visibilityOf(driver.findElement(By.id("message-div")))
//    );
//
//    assertEquals("Location has been created",message.getText());
//
//        wait.until(d ->
//            d.findElements(By.cssSelector("tr > td:nth-child(2)"))
//            .stream()
//            .map(WebElement::getText)
//            .anyMatch(t ->t.equals(name))
//            );
//    }


    // Ez ugyanaz, page objecttel
//    @Test
//    void testCreateWithPageObject(WebDriver driver) {
//        var page = new LocationsPageObject(driver);
//        page.go();
//        page.clickOnCreateLocation();
//        var name = "Test location " + UUID.randomUUID();
//        page.fillForm(name, "1,1");
//    }


    // Ez ugyanaz, rövidebben
    LocationsPageObject page;

    @BeforeEach
    void initPage(WebDriver driver) {
        page = new LocationsPageObject(driver);
    }

    @Test
    void testCreateWithPageObject() {
        // Ne hivatkozzunk WebDriverre, mert a helye a page objectben van
        var name = "Test location " + UUID.randomUUID();
        page
                .go()
                .clickOnCreateLocationLink()
                .fillForm(name)
                .clickOnCreateButton();

        assertEquals("Location has been created", page.waitForMessageAndGetText());
        Location created = page.waitForLocationAppear(name);

        assertEquals("1, 1", created.getCoords());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/MOCK_DATA.csv", numLinesToSkip = 1)
    void testCreateLocationDDT(String name, String latitude, String longitude) {
        page
                .go()
                .clickOnCreateLocationLink()
                .fillForm(name, latitude + "," + longitude)
                .clickOnCreateButton();
    }

    @Test
    void testEdit() {
        var fixture = new LocationDatabaseFixture();
        fixture.deleteLocations();
        fixture.createLocation("Test location name", 10, 10);
    }
}
