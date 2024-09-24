package qtriptest.pages;

import qtriptest.SeleniumWrapper;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class HistoryPage {

    RemoteWebDriver driver;

    @FindBy(xpath = "//*[@id='navbarNavDropdown']/ul/li[2]/a")
    WebElement reservationsButton;

    @FindBy(xpath = "//*[@id='reservation-table']/tr/th")
    WebElement transactionId;

    @FindBy(className = "cancel-button")
    WebElement cancelButton;

    @FindBy(id = "no-reservation-banner")
    WebElement noReservationBanner;

    @FindBy(xpath = "//*[@id='reservation-table']/tr/th")
    List<WebElement> totalBookings;
    

    public HistoryPage(RemoteWebDriver driver) {

        this.driver = driver;
        AjaxElementLocatorFactory ajax = new AjaxElementLocatorFactory(driver, 60);
        PageFactory.initElements(ajax, this);

    }

    public void navigateToHistory() throws InterruptedException {

        // reservationsButton.click();
        // Thread.sleep(3000);
        SeleniumWrapper.click(reservationsButton, driver);
        Thread.sleep(3000);

    }

    public String getTransactionId() {

        return transactionId.getText();

    }

    public void cancelReservation() throws InterruptedException {

        // cancelButton.click();
        // Thread.sleep(4000);

        SeleniumWrapper.click(cancelButton, driver);
        Thread.sleep(4000);
        
    }

    public Boolean noTransactionId(String id) {

        try {
            return !transactionId.getText().contains(id);
        }
        catch (Exception e) {
            return true;
        }

    }

    public int getTotalBookings() {
        return totalBookings.size();
    }


}
