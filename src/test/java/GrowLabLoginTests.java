import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GrowLabLoginTests extends TestBase {

    @Test
    public void loginNegativeTest() {
        dr.findElement(By.id("sw-form-capture-email-input")).click();
        dr.findElement(By.id("sw-form-capture-email-input")).clear();
        dr.findElement(By.id("sw-form-capture-email-input")).sendKeys("22");
        dr.findElement(By.id("sw-sign-in-submit-btn")).click();

        dr.findElement(By.xpath("//*[@id='signin']/div[2]/div[1]"));
    }

    @Test
    public void loginNegativeTestWithWrongEmail() {

        click(By.id("sw-form-capture-email-input"));
        dr.findElement(By.id("sw-form-capture-email-input")).clear();
        type(By.id("sw-form-capture-email-input"), "billye@example.com");
        click(By.id("sw-form-password-input"));
        dr.findElement(By.id("sw-form-password-input")).clear();
        type(By.cssSelector("#sw-form-password-input"), "12345");
        click(By.cssSelector("#sw-sign-in-submit-btn"));
        dr.findElement(By.xpath("//*[text()='Invalid email or password']"));
    }

    @Test
    public void loginAsManagerPositiveTest() throws InterruptedException {
        enterEmail("billye@example.com");
        enterPassword();
        submitSignInBtn();
        Thread.sleep(2000);
        dr.get("https://derrick686.softr.app/clients");
        dr.findElement(By.xpath("//*[text()='      Our Clients     ']"));
        Thread.sleep(2000);
        logoutForManagerAcc();
    }

    @Test
    public void loginAsClientPositiveTest() throws InterruptedException {
        enterEmail("lucie@example.com");
        enterPassword();
        submitSignInBtn();
        Thread.sleep(2000);
        String source = dr.getPageSource();
        Assert.assertTrue(source.contains("Welcome to your Client Portal"));
        logoutForClientAcc();
    }

    @Test
    public void loginAsConsultantPositiveTest() throws InterruptedException {
        enterEmail("edra@example.com");
        enterPassword();
        submitSignInBtn();
        Thread.sleep(2000);
        click(By.xpath("//*[@id=\"home-header1\"]/div/div[1]/ul/li[2]/a"));
        String source = dr.getPageSource();
        Assert.assertTrue(source.contains("Our Clients"));
    }


}
