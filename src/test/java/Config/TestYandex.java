package Config;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class TestYandex {
    public static OpenYandexPage openYandexPage;
    public static LoginToYandexMailPage loginToYandexMailPage;
    public static CheckMailStatusPage checkMailStatusPage;
    public static WebDriver driver;
    public static SendMailPage sendMailPage;

    @BeforeMethod
    public static void setup(){
        System.setProperty("webdriver.chrome.driver", ConfigurationProperties.getProperty("chromedriver_win"));
        driver = new ChromeDriver();

        openYandexPage = new OpenYandexPage(driver);
        loginToYandexMailPage = new LoginToYandexMailPage(driver);
        checkMailStatusPage = new CheckMailStatusPage(driver);
        sendMailPage = new SendMailPage(driver);

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

        ArrayList<String> browserTabs = new ArrayList<>(driver.getWindowHandles());

        //change focus
        driver.switchTo().window(browserTabs.get(0));
        driver.close();
        driver.switchTo().window(browserTabs.get(1));

       // checkMailStatus.clickInboxBtn();  //если нужно явно нажать кнопку "Входящие"


        List<WebElement> mailInBox = driver.findElements(By.xpath("//span[@class='mail-MessageSnippet-Item mail-MessageSnippet-Item_subject']"));

        int count = 0;
        int countTheme =0;
        for (WebElement elements: mailInBox
             ) {

            if (elements.getText().equals(ConfigurationProperties.getProperty("theme")))
            {
                countTheme++;
                System.out.println("Найдена тема = " + elements.getText());
            }

                count++;
        }
        System.out.println("Всего писем = " + count);
        System.out.println("Писем с темой \"" + ConfigurationProperties.getProperty("theme") + "\" = " + countTheme);

        sendMailPage.clickNewMailBtn();
        sendMailPage.inputSubj(ConfigurationProperties.getProperty("theme"));
        sendMailPage.inputMail(ConfigurationProperties.getProperty("usr")+"@yandex.ru");

        sendMailPage.inputLetterText("Найдено " + countTheme + " писем\\ьма");
        sendMailPage.clickSendBtn();

    }

    @AfterMethod
    public void after_test(){
    driver.quit();
    }
}
