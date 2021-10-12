package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginToYandexMailPage {
    public WebDriver driver;
    public LoginToYandexMailPage (WebDriver driver){
        PageFactory.initElements(driver,this);
        this.driver = driver;

    }

    /**
     * определение локатора поля ввода username
     */
    @FindBy(xpath = "//input[@id='passp-field-login']")
    private WebElement inputLoginField;

    /**
     * метод для ввода login
     */
    public void inputLogin(String input) {
        inputLoginField.sendKeys(input); }

    // кнопка "Войти" после ввода имени пользователя
    public void clickLoginUsrBtn (){
        driver.findElement(By.xpath("//button[@id='passp:sign-in']")).click(); }


    /**
     * определение локатора поля ввода passwd
     */
    @FindBy(xpath = "//input[@id='passp-field-passwd']")
    private WebElement inputPasswdField;

    /**
     * метод для ввода passwd
     */
    public void inputPasswd(String input) {
        inputPasswdField.sendKeys(input); }



}
