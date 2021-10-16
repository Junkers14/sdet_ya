package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class openYandexPage {
    public WebDriver driver;
    public openYandexPage(WebDriver driver){
        PageFactory.initElements(driver,this);
        this.driver = driver;
    }

    /**
     * определение локатора кнопки логина на главном экране и нажатие
     */
    @FindBy (xpath = "//a[@data-statlog='notifications.mail.logout.enter']")
    private WebElement clickLoginBtn;

    public void clickLoginBtn (){
        clickLoginBtn.click(); }

}