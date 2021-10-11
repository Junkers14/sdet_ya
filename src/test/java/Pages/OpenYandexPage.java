package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class OpenYandexPage {
    public WebDriver driver;
    public OpenYandexPage(WebDriver driver){
        this.driver = driver;
    }

    /**
     * определение локатора кнопки логина на главном экране и нажатие
     */
    public void clickLoginBtn (){
        driver.findElement(By.xpath("//a[@class='home-link desk-notif-card__login-new-item " +
                "desk-notif-card__login-new-item_enter home-link_black_yes home-link_hover_inherit']")).click(); }

}