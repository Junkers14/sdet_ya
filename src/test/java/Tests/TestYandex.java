package Tests;

import BrowserControl.ChromeControl;
import Config.ConfigurationProperties;
import LogWorkers.SystemLog;
import Pages.CheckMailStatusPage;
import Pages.LoginToYandexMailPage;
import Pages.OpenYandexPage;
import Pages.SendMailPage;
import SimpleLogic.CompareClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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
    public static SystemLog systemLog;


    @BeforeMethod
    public static void setup(){
        System.setProperty("webdriver.chrome.driver",
                ConfigurationProperties.getProperty("chromedriver_win"));
        systemLog = new SystemLog();
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
        int lettersBeforeTest, lettersAfterTest;
        openYandexPage.clickLoginBtn();
        loginToYandexMailPage.inputLogin(ConfigurationProperties.getProperty("usr"));
        loginToYandexMailPage.clickLoginUsrBtn();
        loginToYandexMailPage.inputPasswd(ConfigurationProperties.getProperty("psswd"));
        loginToYandexMailPage.clickLoginUsrBtn();
        checkMailStatusPage.clickMailBtn();
        chromeControl.CloseTab(0, 1, driver);
        systemLog.loggerTestOutput("Было писем с темой \"" +
                ConfigurationProperties.getProperty("theme") + "\" = " +
                String.valueOf(lettersBeforeTest = checkMailStatusPage.countOfMail()));
        sendMailPage.clickNewMailBtn();
        sendMailPage.inputSubj(ConfigurationProperties.getProperty("theme"));
        sendMailPage.inputMail(ConfigurationProperties.getProperty("usr")+"@yandex.ru");
        sendMailPage.inputLetterText("Найдено " + lettersBeforeTest + " писем\\ьма");
        sendMailPage.clickSendBtn();
        //chromeControl.DriverWait(1000);
        sendMailPage.clickReturnToInbox();
        checkMailStatusPage.clickRefreshBtn(driver);
        chromeControl.DriverWait(1500);
        systemLog.loggerTestOutput("Стало писем с темой \"" +
                ConfigurationProperties.getProperty("theme") + "\" = " +
                String.valueOf(lettersAfterTest = checkMailStatusPage.countOfMail()));
        if (compareClass.CompareLetters(lettersBeforeTest, lettersAfterTest))
            systemLog.loggerTestOutput(
                    "C каждым запуском теста, кол-во писем с темой " +
                            "Simbirsoft theme увеличивается");
            else
            systemLog.loggerTestOutput(
                    "ERROR! C каждым запуском теста, кол-во писем с темой " +
                            "Simbirsoft theme НЕ увеличивается");
    }

    @AfterMethod
    public void after_test(){
    //driver.quit();
    }
}
