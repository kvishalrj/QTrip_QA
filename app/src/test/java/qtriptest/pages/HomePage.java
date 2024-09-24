package qtriptest.pages;

import qtriptest.SeleniumWrapper;
import java.util.List;
import org.apache.logging.log4j.core.util.WrappedFileWatcher;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class HomePage {

    RemoteWebDriver driver;
    String url = "https://qtripdynamic-qa-frontend.vercel.app/";

    @FindBy(className = "register")
    WebElement register_button;

    @FindBy(id = "autocomplete")
    WebElement searchBar;

    @FindBy(id = "results")
    WebElement verifySearch;

    @FindBy(xpath = "//*[@id='duration-select']/option")
    List<WebElement> filterHours;

    @FindBy(xpath = "/html/body/div/div/div[1]/div[1]/div[2]/div")
    WebElement clearHours;

    @FindBy(xpath = "//*[@id='category-select']/option")
    List<WebElement> filterCategories;

    @FindBy(xpath = "/html/body/div/div/div[1]/div[1]/div[3]/div")
    WebElement clearCategories;

    @FindBy(xpath = "//*[@id='data']/div")
    List<WebElement> allAdventures;

    public HomePage(RemoteWebDriver driver) {

        this.driver = driver;
        AjaxElementLocatorFactory ajax = new AjaxElementLocatorFactory(driver, 60);
        PageFactory.initElements(ajax, this);

    }

    public void navigateToHomePage() throws InterruptedException {

        // if (this.driver.getCurrentUrl()!=this.url) {
        //     this.driver.get(this.url);
        //     Thread.sleep(4000);
        // }

        SeleniumWrapper.navigate(this.driver, this.url);

    }

    public void clickOnRegister() throws InterruptedException {
        
        // register_button.click();
        // Thread.sleep(4000);
        SeleniumWrapper.click(register_button, this.driver);
        Thread.sleep(4000);

    }

    public Boolean verifyRegisterPage() {

        if (this.driver.getCurrentUrl().endsWith("register/")) {
            System.out.println("Register Page Appears");
            return true;
        }
        return false;

    }

    public void searchForCity(String city) throws InterruptedException {

        // searchBar.clear();
        // searchBar.sendKeys(city);
        // Thread.sleep(4000);
        SeleniumWrapper.sendKeys(searchBar, city);
        Thread.sleep(4000);

    }

    public Boolean verifyNoCity() throws Exception {

        // String searchResults = verifySearch.findElement(By.xpath("//h5")).getText();
        // return searchResults.contains("No City found");
        WebElement search = SeleniumWrapper.findElementWithRetry(this.driver, By.xpath("//h5"), 3);
        String searchResults = search.getText();
        return searchResults.contains("No City found");

    }

    public Boolean verifyCityDisplayed(String city) throws Exception {

        String city1 = city.toLowerCase();
        // String searchResults = verifySearch.findElement(By.id(city1)).getText().toLowerCase();
        // return searchResults.contains(city1);
        WebElement search = SeleniumWrapper.findElementWithRetry(this.driver, By.id(city1), 3);
        String searchResults = search.getText().toLowerCase();
        return searchResults.contains(city1);

    }

    public void clickOnTheCity(String city) throws InterruptedException {

        String city1 = city.toLowerCase();
        // verifySearch.findElement(By.id(city1)).click();
        // Thread.sleep(4000);
        WebElement verify = verifySearch.findElement(By.id(city1));
        SeleniumWrapper.click(verify, this.driver);
        Thread.sleep(4000);


    }

    public void filterHoursSelect(String hours) throws InterruptedException {

        for (WebElement h : filterHours) {
            if (h.getText().contains(hours)) {
                h.click();
                Thread.sleep(4000);
            }
        }

    }

    public void clearFiltersHours() throws InterruptedException {
        // clearHours.click();
        // Thread.sleep(4000);
        SeleniumWrapper.click(clearHours, this.driver);
        Thread.sleep(4000);
    }

    public void filterCategorySelect(String category) throws InterruptedException {

        for (WebElement c : filterCategories) {
            if (c.getText().contains(category)) {
                // c.click();
                SeleniumWrapper.click(c, this.driver);
                Thread.sleep(4000);
            }
        }

    }

    public void clearFiltersCategories() throws InterruptedException {
        // clearCategories.click();
        SeleniumWrapper.click(clearCategories, this.driver);
        Thread.sleep(4000);
    }

    public int verifyRecords() {
        return allAdventures.size();
    }

}
