package qtriptest;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SeleniumWrapper {

    // Scroll to a specific WebElement using JavaScript
    private static void scrollToElement(WebDriver driver, WebElement element) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /* Check if the element is displayed before attempting to click
    * Scroll in to view before clicking on an element
    * return false if the element is not displayed / any other exception
    */
    public static boolean click (WebElement elementToClick, WebDriver driver) throws InterruptedException {
        try {
            if (elementToClick.isDisplayed()) {
                scrollToElement(driver, elementToClick);
                elementToClick.click();
                Thread.sleep(2000);
                return true;
            }
            return false;
        }
        catch (Exception e) {
            return false;
        }
    }
    
    /* clear the existing text on the inputBox
    * Type in the new keys
    */
    public static boolean sendKeys (WebElement inputBox, String keysToSend) {
        try {
            inputBox.clear();
            inputBox.sendKeys(keysToSend);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    /* Navigate to the given url if the current url is not equal to the given url
    * after navigation, ensure that the current url is updated as per the given url
    */
    public static boolean navigate (WebDriver driver, String url) {
        try {
            if (!driver.getCurrentUrl().contains(url)) {
                driver.get(url);
                Thread.sleep(3000);
                if (driver.getCurrentUrl().contains(url)) {
                    return true;
                }
                else {
                    return false;
                }
            }
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    /*
    Try to find the webelement with the given By
    * if the element is not found, retry for the given number of times
    * if you do not want retry, set the retry value to 0
    */
    public static WebElement findElementWithRetry (WebDriver driver, By by, int retryCount) throws Exception {
        try {
            WebElement element = driver.findElement(by);
            return element;
        }
        catch (Exception e) {
            for (int i=1; i<=retryCount; i++) {
                try {
                    WebElement element = driver.findElement(by);
                    return element; 
                }
                catch (Exception ex) {
                    System.out.println("exception");
                }
            }
            return null;
        }
    }

}
