package tests;

import browser_control.ChromeControl;
import config.ConfigurationProperties;
import log_workers.SystemLog;
import org.testng.Assert;
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
    String usr, psswd, theme, mail_url;


    @BeforeMethod
    public void setup(){
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
        driver.get(ConfigurationProperties.getProperty("url"));
        usr = ConfigurationProperties.getProperty("usr");
        psswd = ConfigurationProperties.getProperty("psswd");
        theme = ConfigurationProperties.getProperty("theme");
        mail_url = ConfigurationProperties.getProperty("mail_url");
        systemLog.loggerWebOutput("Setup is completed");
    }

    @Test
    public void testYandexSendMailCase(){
        int lettersBeforeTest, lettersAfterTest;
        openYandexPage.clickLoginBtn();
        loginToYandexMailPage.inputLogin(usr);
        loginToYandexMailPage.clickLoginUsrBtn();
        loginToYandexMailPage.inputPasswd(psswd);
        loginToYandexMailPage.clickLoginUsrBtn();
        checkMailStatusPage.clickMailBtn();
        chromeControl.closeTab(0, 1);
        systemLog.loggerWebOutput("Было писем с темой \"" + theme + "\" = " +
                (lettersBeforeTest = checkMailStatusPage.mailCount()));
        sendMailPage.clickNewMailBtn();
        sendMailPage.inputSubj(theme);
        sendMailPage.inputMail(usr+"@yandex.ru");
        sendMailPage.inputLetterText("Найдено " + lettersBeforeTest + " писем\\ьма");
        sendMailPage.clickSendBtn();
        sendMailPage.clickReturnToInbox();
        driver.get(mail_url);
        chromeControl.driverWaitForDocumentReady(5);
        systemLog.loggerWebOutput("Стало писем с темой \"" + theme + "\" = " +
                (lettersAfterTest = checkMailStatusPage.mailCount()));
        Assert.assertEquals(lettersAfterTest,lettersBeforeTest+1);
    }



    @AfterTest
    public void after_test(){
            driver.quit();

    }
}
