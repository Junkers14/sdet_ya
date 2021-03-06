package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SendMailPage {

    public WebDriver driver;
    public SendMailPage(WebDriver driver){
        PageFactory.initElements(driver,this);
        this.driver = driver;
    }

    /**
     * определение локатора в поле "Кому"
     */
    @FindBy(className = "composeYabbles")
    private WebElement inputMailTo;

    /**
     * определение локатора в поле "Тема"
     */
    @FindBy(xpath = "//input[@name='subject']")
    private WebElement inputSubject;

    /**
     * определение локатора в поле "текст письма"
     */
    @FindBy(className = "cke_wysiwyg_div")
    private WebElement inputLetterText;

    /**
     * метод для ввода в поле "Кому"
     */
    public void inputMail(String input) {
        inputMailTo.sendKeys(input); }

    /**
     * метод для ввода в поле "Тема"
     */
    public void inputSubj(String input) {
        inputSubject.sendKeys(input); }

    /**
     * метод для ввода в поле "текст письма"
     */
    public void inputLetterText(String input) {
        inputLetterText.sendKeys(input); }

    /**
     * нажатие на кнопку "Написать"
     */
    public void clickNewMailBtn (){
        driver.findElement(By.xpath("//span[@class='mail-ComposeButton-Text']")).click(); }

    /**
     * нажатие на кнопку "Отправить"
     */
    public void clickSendBtn (){
        driver.findElement(By.className("ComposeSendButton_desktop")).click(); }


    public void clickReturnToInbox () {
            driver.findElement(By.className("ComposeDoneScreen-Link")).click();
    }


}
