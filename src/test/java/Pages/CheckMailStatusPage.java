package Pages;

import Config.ConfigurationProperties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CheckMailStatusPage {
    public WebDriver driver;
    public CheckMailStatusPage(WebDriver driver){
        PageFactory.initElements(driver,this);
        this.driver = driver;
    }
    /**
     *  нажатие на кнопку для перехода в почту после регистрации
     */
    public void clickMailBtn (){
        driver.findElement(By.xpath("//div[@class='desk-notif-card__mail-title']")).click(); }

    /**
     *  нажатие на кнопку для перехода в Inbox
     */
    public void clickInboxBtn (){
        driver.findElement(By.xpath("//span[contains(text(),'Входящие')]")).click(); }

    /**
     *  подсчет количества писем с заданной темой
     */
    public int countOfMail (){
        List<WebElement> mailInBox = driver.findElements(By.xpath("//span[@class='mail-MessageSnippet-Item mail-MessageSnippet-Item_subject']"));
        int countTheme =0;
        for (WebElement elements: mailInBox
        ) {
            if (elements.getText().equals(ConfigurationProperties.getProperty("theme")))
            {
                countTheme++;
                System.out.println("Найдена тема = " + elements.getText());
            }

          //  count++;
        }
        //  System.out.println("Всего писем = " + count);
        System.out.println("Писем с темой \"" + ConfigurationProperties.getProperty("theme") + "\" = " + countTheme);
    return countTheme;
    }

}
