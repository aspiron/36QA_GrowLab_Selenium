import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class FirstSeleniumTest {

    WebDriver dr;

    @BeforeMethod
    public void setUp() {
        dr = new ChromeDriver();
        dr.get("https://google.com");
    }

    @Test
    public void firstTest() throws InterruptedException {
        Thread.sleep(1000);

    }


    @AfterMethod (enabled = false)
    public void tearDown() {
        dr.close();
    }
}
