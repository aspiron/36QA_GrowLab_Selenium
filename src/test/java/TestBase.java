import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class TestBase {
    WebDriver dr;

    @BeforeMethod
    public void setUp() {
        dr = new ChromeDriver();
        dr.manage().window().maximize();
        dr.get("https://derrick686.softr.app/login");
    }

    @AfterMethod(enabled = false)
    public void tearDown() {
        dr.quit();
    }



    public void click(By locator) {
        dr.findElement(locator).click();
    }

    public void type(By locator, String text) {
        dr.findElement(locator).sendKeys(text);
    }
    public void enterEmail(String email) {
        click(By.id("sw-form-capture-email-input"));
        dr.findElement(By.id("sw-form-capture-email-input")).clear();
        type(By.id("sw-form-capture-email-input"), email);
    }

    public void enterPassword() {
        click(By.id("sw-form-password-input"));
        dr.findElement(By.id("sw-form-password-input")).clear();
        type(By.cssSelector("#sw-form-password-input"), "123456");
    }

    public void submitSignInBtn() {
        click(By.cssSelector("#sw-sign-in-submit-btn"));
    }

    public void logoutForManagerAcc(){
        click(By.xpath("//*[@id='navbarDropdown']"));
        click(By.xpath("//*[@id='home-header1']/div/div[1]/ul/li[5]/div/a/span/span"));
    }

    public void logoutForClientAcc(){
        click(By.xpath("//*[@id='navbarDropdown']"));
        click(By.xpath("//*[@id=\"home-header3\"]/div/div[1]/ul/li[3]/div/a/span/span"));
    }


    // methods for search tests:

    public void loginAsManager(){
        enterEmail("billye@example.com");
        enterPassword();
        submitSignInBtn();
    }

    public void searchFieldFill(String text) throws InterruptedException {
        click(By.xpath("//*[@id=\"list2\"]/div[1]/div/div/div/input"));
        type(By.xpath("//*[@id=\"list2\"]/div[1]/div/div/div/input"), text);
        Thread.sleep(2000);
    }

}
