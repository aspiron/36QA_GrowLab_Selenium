import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class GrowLabLoginTests {
    WebDriver dr;

    @BeforeMethod
    public void setUp() {
        dr = new ChromeDriver();
        dr.manage().window().maximize();
        dr.get("https://derrick686.softr.app/login");
    }

    @Test
    public void loginNegativeTest() {
        dr.findElement(By.id("sw-form-capture-email-input")).click(); // клик по полю для ввода логина (имейл)
        dr.findElement(By.id("sw-form-capture-email-input")).clear(); // очистка поля (на случай если там что-то введено)
        dr.findElement(By.id("sw-form-capture-email-input")).sendKeys("22"); // ввод данных в поле логин (имейл)
        dr.findElement(By.id("sw-sign-in-submit-btn")).click(); // клик по кнопке submit для попытки входа в аккаунт
                                                                // при этом, поле пароль пустое, оно вообще не заполнялось
        dr.findElement(By.xpath("//*[@id='signin']/div[2]/div[1]")); // поиск элемента, отвечающего за появившееся предупреждение об ошибке
    }

    @Test
    public void loginNegativeTestWithWrongEmail() {
        // создан отдельный метод click, который принимает данные о локаторе (id, xpath, cssSelector, неважно)
        // и по указанному элементу кликает:
        click(By.id("sw-form-capture-email-input"));
        dr.findElement(By.id("sw-form-capture-email-input")).clear();
        // создан отдельный метод type, который принимает данные о локаторе (тип By) и данные для ввода (типа String):
        type(By.id("sw-form-capture-email-input"), "billye@example.com"); // введен правильный имейл
        click(By.id("sw-form-password-input"));
        dr.findElement(By.id("sw-form-password-input")).clear();
        type(By.cssSelector("#sw-form-password-input"), "12345"); // введен заведомо неправильный пароль
        click(By.cssSelector("#sw-sign-in-submit-btn"));
        // находим элемент, который выдает предупреждение об ошибке, и проверяем, что это предупреждение по содержащемуся в нем тексту:
        dr.findElement(By.xpath("//*[text()='Invalid email or password']"));
    }

    @Test
    public void loginAsManagerPositiveTest() throws InterruptedException {
        // для удобства создан отдельный метод enterEmail, который объединяет сразу несколько действий
        // - кликнуть в поле имейл, очистить поле имейл и ввести данные в поле имейл;
        // метод принимает данные об имейле
        enterEmail("billye@example.com");

        // для удобства создан отдельный метод enterPassword, который по структуре аналогичен методу enterEmail
        // но он ничего не принимает. т.к. правильный пароль везде одинаковый, он уже зашит в самом методе
        enterPassword();

        submitSignInBtn();  // метод, в котором содержится обычный клик, но для конкретной кнопки - submit
        Thread.sleep(2000);

        // проверка, что мы успешно залогинились:
        // 1) переходим на страницу о клиентах
        // ищем элемент, который отображается только у авторизованного пользователя с ролью менеджер
        // если залогиниться не удалось, отобразится страница с ошибкой 404 и соответственно искомого элемента с текстом "Our Clients" на ней не будет
        dr.get("https://derrick686.softr.app/clients");
        dr.findElement(By.xpath("//*[text()='      Our Clients     ']")); // из-за такой кучи пробелов как в этом примере, лучше не переписывать текст из кода, а тупо скопировать
        Thread.sleep(2000);
        logoutForManagerAcc(); // метод для выхода из аккаунта менеджера
    }

    @Test
    public void loginAsClientPositiveTest() throws InterruptedException {
        // набор методов аналогичный позитивному тесту для менеджера
        enterEmail("lucie@example.com");
        enterPassword();
        submitSignInBtn();
        Thread.sleep(2000);
        String source = dr.getPageSource();
        Assert.assertTrue(source.contains("Welcome to your Client Portal")); // ассерт на наличие на странице текста
        logoutForClientAcc(); // метод для выхода из аккаунта менеджера
    }

    @Test
    public void loginAsConsultantPositiveTest() throws InterruptedException {
        // набор методов аналогичный позитивному тесту для менеджера
        enterEmail("edra@example.com");
        enterPassword();
        submitSignInBtn();
        Thread.sleep(2000);
        // проверка успешного входа: клик на одну из ссылок, которая есть в аккаунте консультанта, и ее не будет если залогиниться не удалось
        click(By.xpath("//*[@id=\"home-header1\"]/div/div[1]/ul/li[2]/a"));
        String source = dr.getPageSource();
        Assert.assertTrue(source.contains("Our Clients")); // после перехода по ссылке из предыдущего клика - ассерт по поиску текста на странице
    }

    @AfterMethod(enabled = false) // браузер автоматически не закрывается при выполнении теста (не обязательно, помогает успеть увидеть конечную точку теста)
    public void tearDown() {
        dr.quit();
    }



    public void click(By locator) {
        dr.findElement(locator).click();
    } // метод, который кликает по выбранному элементу

    public void type(By locator, String text) {
        dr.findElement(locator).sendKeys(text);
    } // метод, который вводит текст в выбранный элемент (если туда можно ввести текст),
    // в сигнатуру метода нужно ввести локатор элемента, и какой текст надо ввести
    public void enterEmail(String email) {
        // метод объединяет несколько повторяющихся команд и методов, позволяет ввести имейл в соответствущее поле
        // в сигнатуру метода нужно ввести нужный имейл
        click(By.id("sw-form-capture-email-input"));
        dr.findElement(By.id("sw-form-capture-email-input")).clear();
        type(By.id("sw-form-capture-email-input"), email);
    }

    public void enterPassword() {
        // метод объединяет несколько повторяющихся команд и методов, позволяет ввести пароль (всегда одинаковый) в соответствущее поле
        // в сигнатуру метода ничего вводить не нужно
        click(By.id("sw-form-password-input"));
        dr.findElement(By.id("sw-form-password-input")).clear();
        type(By.cssSelector("#sw-form-password-input"), "123456");
    }

    public void submitSignInBtn() {
        // метод кликает по кнопке для входа в аккаунт (чтобы не плодить локаторы в тестах)
        click(By.cssSelector("#sw-sign-in-submit-btn"));
    }

    public void logoutForManagerAcc(){
        // выходим из аккаунта менеджера, если находимся в данный момент
        click(By.xpath("//*[@id='navbarDropdown']"));
        click(By.xpath("//*[@id='home-header1']/div/div[1]/ul/li[5]/div/a/span/span"));
    }

    public void logoutForClientAcc(){
        // выходим из аккаунта клиента, если находимся в данный момент
        click(By.xpath("//*[@id='navbarDropdown']"));
        click(By.xpath("//*[@id=\"home-header3\"]/div/div[1]/ul/li[3]/div/a/span/span"));
    }





}
