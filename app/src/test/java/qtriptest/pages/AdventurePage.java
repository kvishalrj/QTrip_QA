package qtriptest.pages;

import qtriptest.SeleniumWrapper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class AdventurePage {

    RemoteWebDriver driver;
    
    public AdventurePage(RemoteWebDriver driver) {

        this.driver = driver;
        AjaxElementLocatorFactory ajax = new AjaxElementLocatorFactory(driver, 60);
        PageFactory.initElements(ajax, this);

    }

    public void searchForAdventure(String adventure) throws Exception {

        // WebElement adv = driver.findElement(By.xpath("//h5[contains(text(), '"+adventure+"')]"));
        // Thread.sleep(4000);
        // adv.click();
        // Thread.sleep(4000);

        WebElement adv = SeleniumWrapper.findElementWithRetry(driver, By.xpath("//h5[contains(text(), '"+adventure+"')]"), 3);
        Thread.sleep(4000);
        SeleniumWrapper.click(adv, driver);
    }


}
