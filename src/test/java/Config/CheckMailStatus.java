package Config;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class CheckMailStatus {
    public WebDriver driver;
    public CheckMailStatus(WebDriver driver){
        PageFactory.initElements(driver,this);
        this.driver = driver;
    }
//нажатие на кнопку для перехода в почту после регистрации
    public void clickMailBtn (){
        driver.findElement(By.xpath("//div[@class='desk-notif-card__mail-title']")).click(); }

//нажатие на кнопку для перехода в Inbox
    public void clickInboxBtn (){
        driver.findElement(By.xpath("//span[contains(text(),'Входящие')]")).click(); }



}
