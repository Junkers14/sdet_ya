package pages;

import config.ConfigurationProperties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class checkMailStatusPage {
    public WebDriver driver;
    public checkMailStatusPage(WebDriver driver){
        PageFactory.initElements(driver,this);
        this.driver = driver;
    }

    /**
     *  опредедение локатора для кнопки "Почта" после успешной регистрации
     */
    @FindBy (xpath = "//div[@class='desk-notif-card__mail-title']")
    private WebElement clickMailBtn;

    /**
     *  опредедение локатора для списка писем с темой
     */
    @FindBy (xpath = "//span[@class='mail-MessageSnippet-Item mail-MessageSnippet-Item_subject']")
    private List<WebElement> mailInBox;

    /**
     *  нажатие на кнопку для перехода в почту после регистрации
     */
    public void clickMailBtn (){
        clickMailBtn.click(); }

    /**
     *  подсчет количества писем с заданной темой
     */
    public int mailCount(){
        int countTheme = 0;
        for (WebElement elements: mailInBox) {
            if (elements.getText().equals(ConfigurationProperties.getProperty("theme")))
            {
                countTheme++;
            }
        }
        return countTheme;
    }

}