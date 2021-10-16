package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginToYandexMailPage {
    public WebDriver driver;
    public LoginToYandexMailPage(WebDriver driver){
        PageFactory.initElements(driver,this);
        this.driver = driver;
    }

    /**
     * определение локатора поля ввода username
     */
    @FindBy(xpath = "//input[@id='passp-field-login']")
    private WebElement inputLoginField;

    /**
     * определение локатора для кнопки авторизации
     */
    @FindBy(xpath = "//button[@id='passp:sign-in']")
    private WebElement clickLoginUsrBtn;

    /**
     * определение локатора поля ввода passwd
     */
    @FindBy(xpath = "//input[@id='passp-field-passwd']")
    private WebElement inputPasswdField;

    /**
     * метод для ввода login
     */
    public void inputLogin(String input) {
        inputLoginField.sendKeys(input); }

    /**
     * метод для нажатия кнопки авторизации пользователя
     */
    public void clickLoginUsrBtn (){
        clickLoginUsrBtn.click();}

    /**
     * метод для ввода passwd
     */
    public void inputPasswd(String input) {
        inputPasswdField.sendKeys(input); }

}
