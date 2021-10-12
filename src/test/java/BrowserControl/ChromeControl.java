package BrowserControl;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.ArrayList;

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

    public void DriverWaitForDocumentReady (int timeInSeconds){
       new WebDriverWait(driver, Duration.ofSeconds(timeInSeconds)).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript(
                        "return document.readyState").equals("complete"));
    }
}
