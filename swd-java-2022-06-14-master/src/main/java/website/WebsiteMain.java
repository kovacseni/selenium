package website;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;

public class WebsiteMain {

    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        //System.setProperty("webdriver.chrome.driver", "C:\\Java\\chromedriver.exe");
        System.out.println(System.getProperty("webdriver.chrome.driver"));
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.python.org");

        driver.findElement(By.id("id-search-field")).click();
        driver.findElement(By.id("id-search-field")).sendKeys("testing");
        driver.findElement(By.id("submit")).click();

        String result = driver.findElement(By.cssSelector("h3:nth-child(2)")).getText();
        System.out.println(result);
    }
}
