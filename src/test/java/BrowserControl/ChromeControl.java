package BrowserControl;

import org.openqa.selenium.WebDriver;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class ChromeControl {

    public WebDriver driver;
    public ChromeControl(WebDriver driver){
        this.driver = driver;
    }

    public void CloseTab (int tabIdForClose, int tabIdForFocus, WebDriver currentWebDriver){
        ArrayList<String> browserTabs = new ArrayList<>(currentWebDriver.getWindowHandles());
        currentWebDriver.switchTo().window(browserTabs.get(tabIdForClose));
        currentWebDriver.close();
        currentWebDriver.switchTo().window(browserTabs.get(tabIdForFocus));
    }

    public void DriverWait (int time){
        try {
            TimeUnit.MILLISECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}