package qtriptest.pages;

import qtriptest.SeleniumWrapper;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class AdventureDetailsPage {

    RemoteWebDriver driver;

    @FindBy(name = "name")
    WebElement name;

    @FindBy(name = "date")
    WebElement date;

    @FindBy(name = "person")
    WebElement person;

    @FindBy(className = "reserve-button")
    WebElement reserveButton;

    @FindBy(id = "reserved-banner")
    WebElement reserveBanner;
    

    public AdventureDetailsPage(RemoteWebDriver driver) {

        this.driver = driver;
        AjaxElementLocatorFactory ajax = new AjaxElementLocatorFactory(driver, 60);
        PageFactory.initElements(ajax, this);

    }

    public void reserveAdventure(String advName, String advDate, String advPerson) throws InterruptedException {

        // name.clear();
        // name.sendKeys(advName);
        // date.clear();
        // date.sendKeys(advDate);
        // person.clear();
        // person.sendKeys(advPerson);
        // reserveButton.click();
        // Thread.sleep(4000);

        SeleniumWrapper.sendKeys(name, advName);
        SeleniumWrapper.sendKeys(date, advDate);
        SeleniumWrapper.sendKeys(person, advPerson);
        SeleniumWrapper.click(reserveButton, driver);
        Thread.sleep(4000);
    }

    public Boolean checkAdventureSuccessfull() {

        return reserveBanner.getText().contains("successful");

    }


}
