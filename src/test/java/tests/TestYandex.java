package tests;

import browser_control.ChromeControl;
import config.ConfigurationProperties;
import log_workers.SystemLog;
import org.testng.annotations.AfterTest;
import pages.CheckMailStatusPage;
import pages.LoginToYandexMailPage;
import pages.OpenYandexPage;
import pages.SendMailPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.IOException;
import java.time.Duration;
import java.util.logging.LogManager;


public class TestYandex {
    public static OpenYandexPage openYandexPage;
    public static LoginToYandexMailPage loginToYandexMailPage;
    public static CheckMailStatusPage checkMailStatusPage;
    public static WebDriver driver;
    public static SendMailPage sendMailPage;
    public static ChromeControl chromeControl;
    public static SystemLog systemLog;


    @BeforeMethod
    public static void setup(){

        try {
            LogManager.getLogManager().readConfiguration(
                    TestYandex.class.getResourceAsStream("/logging.properties"));
        } catch (IOException e) {
            System.err.println("Could not setup logger configuration: " + e);
        }
        System.setProperty(
                ConfigurationProperties.getProperty("driver"),
                ConfigurationProperties.getProperty("path_to_chromedriver_win"));
        systemLog = new SystemLog();
        driver = new ChromeDriver();
        openYandexPage = new OpenYandexPage(driver);
        loginToYandexMailPage = new LoginToYandexMailPage(driver);
        checkMailStatusPage = new CheckMailStatusPage(driver);
        sendMailPage = new SendMailPage(driver);
        chromeControl = new ChromeControl(driver);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get(config.ConfigurationProperties.getProperty("url"));
        systemLog.loggerWebOutput("Setup is completed");
    }

    @Test
    public void testYandexSendMailCase(){
        int lettersBeforeTest, lettersAfterTest;
        openYandexPage.clickLoginBtn();
        loginToYandexMailPage.inputLogin(ConfigurationProperties.getProperty("usr"));
        loginToYandexMailPage.clickLoginUsrBtn();
        loginToYandexMailPage.inputPasswd(ConfigurationProperties.getProperty("psswd"));
        loginToYandexMailPage.clickLoginUsrBtn();
        checkMailStatusPage.clickMailBtn();
        chromeControl.CloseTab(0, 1);
        systemLog.loggerWebOutput("Было писем с темой \"" +
                ConfigurationProperties.getProperty("theme") + "\" = " +
                (lettersBeforeTest = checkMailStatusPage.countOfMail()));
        sendMailPage.clickNewMailBtn();
        sendMailPage.inputSubj(ConfigurationProperties.getProperty("theme"));
        sendMailPage.inputMail(ConfigurationProperties.getProperty("usr")+"@yandex.ru");
        sendMailPage.inputLetterText("Найдено " + lettersBeforeTest + " писем\\ьма");
        sendMailPage.clickSendBtn();
        sendMailPage.clickReturnToInbox();
        driver.get(ConfigurationProperties.getProperty("mail_url"));
        chromeControl.DriverWaitForDocumentReady(5);
        systemLog.loggerWebOutput("Стало писем с темой \"" +
                ConfigurationProperties.getProperty("theme") + "\" = " +
                (lettersAfterTest = checkMailStatusPage.countOfMail()));
        if (lettersBeforeTest < lettersAfterTest)
            systemLog.loggerWebOutput(
                    "C каждым запуском теста, кол-во писем с темой " +
                            ConfigurationProperties.getProperty("theme")+ " увеличивается");
            else
            systemLog.loggerWebOutput(
                    "ERROR! C каждым запуском теста, кол-во писем с темой " +
                            ConfigurationProperties.getProperty("theme")+ " НЕ увеличивается");
    }



    @AfterTest
    public void after_test(){
            driver.quit();

    }
}
