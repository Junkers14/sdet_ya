package BrowserControl;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;

public class ChromeControl {

    public WebDriver driver;
    public ChromeControl(WebDriver driver){
        PageFactory.initElements(driver,this);
        this.driver = driver;
    }


    public void ChangeFocus (int tabId){
        ArrayList<String> browserTabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(browserTabs.get(tabId));
    }

    public void CloseTab (int tabIdForClose, int tabIdForFocus, WebDriver currentWebDriver){
        ArrayList<String> browserTabs = new ArrayList<>(currentWebDriver.getWindowHandles());
        currentWebDriver.switchTo().window(browserTabs.get(tabIdForClose));
        currentWebDriver.close();
        currentWebDriver.switchTo().window(browserTabs.get(tabIdForFocus));
    }
}
