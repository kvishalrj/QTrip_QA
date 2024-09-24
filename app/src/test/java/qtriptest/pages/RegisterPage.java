package qtriptest.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import qtriptest.SeleniumWrapper;
import java.lang.Thread;
import java.util.UUID;

public class RegisterPage {

    RemoteWebDriver driver;
    
    String url = "https://qtripdynamic-qa-frontend.vercel.app/pages/register/";
    public String lastGeneratedUsername;

    @FindBy(name = "email")
    WebElement email_box;
        
    @FindBy(name = "password")
    WebElement password_box;

    @FindBy(name = "confirmpassword")
    WebElement confirm_password_box;

    @FindBy(className = "btn-login")
    WebElement register_button;

    public RegisterPage(RemoteWebDriver driver) {
        this.driver = driver;
        AjaxElementLocatorFactory ajax = new AjaxElementLocatorFactory(driver, 60);
        PageFactory.initElements(ajax, this);
    }

    public void navigateToRegisterPage() throws InterruptedException {
        if (this.driver.getCurrentUrl()!=this.url) {
            // this.driver.get(this.url);
            // Thread.sleep(4000);
            SeleniumWrapper.navigate(driver, this.url);
            Thread.sleep(4000);
        }
    }

    public Boolean registerUser(String email, String password, Boolean makeUsernameDynamic) throws InterruptedException {

        // Get time stamp for generating a unique username
        // Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        String test_data_username = email;
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();

        if (makeUsernameDynamic)
            // Concatenate the timestamp to string to form unique timestamp
            test_data_username = email + uuidAsString;
        else
            test_data_username = email;

        // email_box.clear();
        // email_box.sendKeys(test_data_username);
        SeleniumWrapper.sendKeys(email_box, test_data_username);

        String test_data_password = password;

        // password_box.clear();
        // password_box.sendKeys(test_data_password);
        SeleniumWrapper.sendKeys(password_box, test_data_password);

        // confirm_password_box.clear();
        // confirm_password_box.sendKeys(test_data_password);
        SeleniumWrapper.sendKeys(confirm_password_box, test_data_password);

        // register_button.click();
        SeleniumWrapper.click(register_button, driver);

        Thread.sleep(4000);

        this.lastGeneratedUsername = test_data_username;
        
        return this.driver.getCurrentUrl().endsWith("/login");
    }
    
}

