package Tests;

import BrowserControl.ChromeControl;
import Config.ConfigurationProperties;
import Pages.CheckMailStatusPage;
import Pages.LoginToYandexMailPage;
import Pages.OpenYandexPage;
import Pages.SendMailPage;
import SimpleLogic.CompareClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.time.Duration;


public class TestYandex {
    public static OpenYandexPage openYandexPage;
    public static LoginToYandexMailPage loginToYandexMailPage;
    public static CheckMailStatusPage checkMailStatusPage;
    public static WebDriver driver;
    public static SendMailPage sendMailPage;
    public static ChromeControl chromeControl;
    public static CompareClass compareClass;


    @BeforeMethod
    public static void setup(){
        System.setProperty("webdriver.chrome.driver",
                ConfigurationProperties.getProperty("chromedriver_win"));
        driver = new ChromeDriver();
        openYandexPage = new OpenYandexPage(driver);
        loginToYandexMailPage = new LoginToYandexMailPage(driver);
        checkMailStatusPage = new CheckMailStatusPage(driver);
        sendMailPage = new SendMailPage(driver);
        chromeControl = new ChromeControl(driver);
        compareClass = new CompareClass();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get(Config.ConfigurationProperties.getProperty("url"));
        System.out.println("Setup is completed");
    }

    @Test
    public void case1Test(){
        openYandexPage.clickLoginBtn();
        loginToYandexMailPage.inputLogin(ConfigurationProperties.getProperty("usr"));
        loginToYandexMailPage.clickLoginUsrBtn();
        loginToYandexMailPage.inputPasswd(ConfigurationProperties.getProperty("psswd"));
        loginToYandexMailPage.clickLoginUsrBtn();
        checkMailStatusPage.clickMailBtn();
        chromeControl.CloseTab(0, 1, driver);
        int lettersBeforeTest = checkMailStatusPage.countOfMail();
        sendMailPage.clickNewMailBtn();
        sendMailPage.inputSubj(ConfigurationProperties.getProperty("theme"));
        sendMailPage.inputMail(ConfigurationProperties.getProperty("usr")+"@yandex.ru");
        sendMailPage.inputLetterText("Найдено " + lettersBeforeTest + " писем\\ьма");
        sendMailPage.clickSendBtn();
        chromeControl.DriverWait(1000);
        sendMailPage.clickReturnToInbox();
        checkMailStatusPage.clickRefreshBtn();
        chromeControl.DriverWait(1500);
        int lettersAfterTest = checkMailStatusPage.countOfMail();

        if (compareClass.CompareLetters(lettersBeforeTest, lettersAfterTest))
            System.out.println("C каждым запуском теста, кол-во писем с темой Simbirsoft theme увеличивается");
        else
            System.out.println("C каждым запуском теста, кол-во писем с темой Simbirsoft theme не увеличивается :(");

    }

    @AfterMethod
    public void after_test(){
    driver.quit();
    }
}
