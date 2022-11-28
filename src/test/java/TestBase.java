import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
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

    // methods for search:
    public void searchFieldFill(String text) throws InterruptedException {
        click(By.xpath("//*[@id=\"list2\"]/div[1]/div/div/div/input"));
        type(By.xpath("//*[@id=\"list2\"]/div[1]/div/div/div/input"), text);
        Thread.sleep(2000);
    }


    // login and logout methods for all roles:
    public void login(String email) {
        enterEmail(email);
        enterPassword();
        submitSignInBtn();
    }

    public void logoutForClientAcc() {
        click(By.xpath("//*[@id='navbarDropdown']"));
        click(By.xpath("//*[@id=\"home-header3\"]/div/div[1]/ul/li[3]/div/a/span/span"));
    }

    public void logoutForConsultantAcc() {
        click(By.xpath("//*[@id='navbarDropdown']"));
        click(By.xpath("//*[@id=\"home-header1\"]/div/div[1]/ul/li[5]/div/a/span/span"));
    }

    public void logoutForManagerAcc() {
        click(By.xpath("//*[@id='navbarDropdown']"));
        click(By.xpath("//*[@id='home-header1']/div/div[1]/ul/li[5]/div/a/span/span"));
    }


    // pre-login methods:
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


    // common methods:
    public void click(By locator) {
        dr.findElement(locator).click();
    }

    public void type(By locator, String text) {
        dr.findElement(locator).sendKeys(text);
    }

    public void pause(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    // assert methods for auth tests:
    public void assertTrueByPageSource(String text) {
        String source = dr.getPageSource();
        Assert.assertTrue(source.contains(text));
    }

    public void assertFalseByPageSource(String text) {
        String source = dr.getPageSource();
        Assert.assertFalse(source.contains(text));
    }

}
