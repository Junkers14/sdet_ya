package tests;

import browser_control.chromeControl;
import config.ConfigurationProperties;
import log_workers.SystemLog;
import org.testng.annotations.AfterTest;
import pages.checkMailStatusPage;
import pages.loginToYandexMailPage;
import pages.openYandexPage;
import pages.sendMailPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.IOException;
import java.time.Duration;
import java.util.logging.LogManager;


public class TestYandex {
    public static pages.openYandexPage openYandexPage;
    public static pages.loginToYandexMailPage loginToYandexMailPage;
    public static pages.checkMailStatusPage checkMailStatusPage;
    public static WebDriver driver;
    public static pages.sendMailPage sendMailPage;
    public static browser_control.chromeControl chromeControl;
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
        openYandexPage = new openYandexPage(driver);
        loginToYandexMailPage = new loginToYandexMailPage(driver);
        checkMailStatusPage = new checkMailStatusPage(driver);
        sendMailPage = new sendMailPage(driver);
        chromeControl = new chromeControl(driver);
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
        chromeControl.closeTab(0, 1);
        systemLog.loggerWebOutput("Было писем с темой \"" +
                ConfigurationProperties.getProperty("theme") + "\" = " +
                (lettersBeforeTest = checkMailStatusPage.mailCount()));
        sendMailPage.clickNewMailBtn();
        sendMailPage.inputSubj(ConfigurationProperties.getProperty("theme"));
        sendMailPage.inputMail(ConfigurationProperties.getProperty("usr")+"@yandex.ru");
        sendMailPage.inputLetterText("Найдено " + lettersBeforeTest + " писем\\ьма");
        sendMailPage.clickSendBtn();
        sendMailPage.clickReturnToInbox();
        driver.get(ConfigurationProperties.getProperty("mail_url"));
        chromeControl.driverWaitForDocumentReady(5);
        systemLog.loggerWebOutput("Стало писем с темой \"" +
                ConfigurationProperties.getProperty("theme") + "\" = " +
                (lettersAfterTest = checkMailStatusPage.mailCount()));
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
