package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.ReportSingleton;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import java.io.IOException;
import java.net.MalformedURLException;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

public class testCase_01 {

    static RemoteWebDriver driver;
	private static ExtentReports report;
    private static ExtentTest test;

	// Method to help us log our Unit Tests
	public static void logStatus(String type, String message, String status) {
		System.out.println(String.format("%s |  %s  |  %s | %s",
				String.valueOf(java.time.LocalDateTime.now()), type, message, status));
	}

	// Iinitialize webdriver for our Unit Tests
	@BeforeClass(alwaysRun = true, enabled = true)
	public static void createDriver() throws MalformedURLException {
		logStatus("driver", "Initializing driver", "Started");
		driver = DriverSingleton.createDriver();
		logStatus("driver", "Initializing driver", "Success");
		report = ReportSingleton.getInstance().getExtent();
        test = report.startTest("TestCase 01");
	}


	@Test(description = "verifying home, register and login", priority = 1, groups = "Login Flow", dataProvider = "data-provider", dataProviderClass = DP.class, enabled = true)
	public static void TestCase01(String email, String password) throws IOException {
		Assertion assertion = new Assertion();
		ReportSingleton.getInstance();
		
		logStatus("TestCase 01", "verifying home, register and login", "started");
		try {

			Boolean status;

			HomePage home = new HomePage(driver);
			RegisterPage register = new RegisterPage(driver);
			LoginPage login = new LoginPage(driver);

			home.navigateToHomePage();
			home.clickOnRegister();
			assertion.assertTrue(home.verifyRegisterPage(), "verifying register page failed");
			test.log(LogStatus.PASS, "successful verifying register page");
			
			status = register.registerUser(email, password, true);
			assertion.assertTrue(status, "Registraion failed");
			test.log(LogStatus.PASS, "successful registration");

			String username = register.lastGeneratedUsername;
			status = login.performLogin(username, password);
			assertion.assertTrue(status, "Login failed");
			test.log(LogStatus.PASS, "successful login");

			status = login.performLogout();
			assertion.assertTrue(status, "Logout failed");
			test.log(LogStatus.PASS, "successful logout");

			logStatus("TestCase 01", "verifying home, register and login", "success");
		} catch (Exception e) {
			logStatus("TestCase 01", "verifying home, register and login", "failed");
			e.printStackTrace();
			test.log(LogStatus.FAIL, test.addScreenCapture(ReportSingleton.capture(driver))+"TestCase01 failed, reason: "+e.getMessage());
		}
	}


	// Quit webdriver after Unit Tests
	@AfterClass(enabled = true)
	public static void quitDriver() throws MalformedURLException {
		driver.close();
		driver.quit();
		logStatus("driver", "Quitting driver", "Success");

		// End the test
        report.endTest(test);

        // Write the test to filesystem
        report.flush();
	}

}
