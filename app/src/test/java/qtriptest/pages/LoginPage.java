package qtriptest.pages;

import qtriptest.SeleniumWrapper;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class LoginPage {

    RemoteWebDriver driver;
    String url = "https://qtripdynamic-qa-frontend.vercel.app/pages/login";

    @FindBy(name = "email")
    WebElement email_box;
        
    @FindBy(name = "password")
    WebElement password_box;

    @FindBy(className = "btn-login")
    WebElement login_button;

    @FindBy(className = "register")
    WebElement logout;

    public LoginPage(RemoteWebDriver driver) {

        this.driver = driver;
        AjaxElementLocatorFactory ajax = new AjaxElementLocatorFactory(driver, 60);
        PageFactory.initElements(ajax, this);

    }

    public void navigateToLoginPage() throws InterruptedException {

        if (this.driver.getCurrentUrl()!=this.url) {
            // this.driver.get(this.url);
            // Thread.sleep(4000);
            SeleniumWrapper.navigate(driver, this.url);
            Thread.sleep(2000);
        }

    }

    public Boolean performLogin(String username, String password) throws InterruptedException {

        // email_box.sendKeys(username);
        // password_box.sendKeys(password);
        // login_button.click();
        // Thread.sleep(4000);

        SeleniumWrapper.sendKeys(email_box, username);
        SeleniumWrapper.sendKeys(password_box, password);
        SeleniumWrapper.click(login_button, driver);
        Thread.sleep(4000);

        return this.verifyUserLoggedIn();

    }

    public Boolean verifyUserLoggedIn() {
        
        try {
            return logout.getText().equals("Logout");
        }
        catch (Exception e) {
            return false;
        }

    }

    public Boolean performLogout() throws InterruptedException {

        // logout.click();
        // Thread.sleep(4000);
        SeleniumWrapper.click(logout, driver);

        return this.verifyUserLogout();

    }

    public Boolean verifyUserLogout() {
        
        try {
            // System.out.println(logout.isDisplayed());
            return (this.driver.getCurrentUrl().contains("https://qtripdynamic-qa-frontend.vercel.app"));
        }
        catch (Exception e) {
            return false;
        }

    }
    
}

