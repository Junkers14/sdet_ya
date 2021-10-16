package pages;

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
     * определение локатора кнопки "Написать письмо"
     */
    @FindBy (xpath = "//span[@class='mail-ComposeButton-Text']")
    private WebElement clickNewMailBtn;

    /**
     * определение локатора кнопки "Отправить"
     */
    @FindBy (className = "ComposeSendButton_desktop")
    private WebElement clickSendBtn;

    /**
     * определение локатора кнопки "Вернуться в почту" на всплывающем фрейме после успешной отправки
     */
    @FindBy (className = "ComposeDoneScreen-Link")
    private WebElement clickReturnToInbox;

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
     *  нажатие на кнопку "Написать"
     */
    public void clickNewMailBtn (){
        clickNewMailBtn.click(); }

    /**
     * нажатие на кнопку "Отправить"
     */
    public void clickSendBtn (){
        clickSendBtn.click(); }

    /**
     * нажатие на кнопку "Вернуться в почту" на всплывающем фрейме после успешной отправки
     */
    public void clickReturnToInbox () {
        clickReturnToInbox.click();
    }


}
