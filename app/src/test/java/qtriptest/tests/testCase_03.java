package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.ReportSingleton;
import qtriptest.pages.AdventureDetailsPage;
import qtriptest.pages.AdventurePage;
import qtriptest.pages.HistoryPage;
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

public class testCase_03 {

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
        test = report.startTest("TestCase 03");
	}


	@Test(description = "Booking and Cancellation flow", priority = 3, groups = "Booking and Cancellation Flow", dataProvider = "data-provider", dataProviderClass = DP.class, enabled = true)
	public static void TestCase03(String username, String password, String city, String advName, String guestName, String date, String count) throws IOException {
		Assertion assertion = new Assertion();
		ReportSingleton.getInstance();

		logStatus("TestCase 03", "verify that adventures booking and cancellation work fine", "started");
		try {

			HomePage home = new HomePage(driver);
			RegisterPage register = new RegisterPage(driver);
			LoginPage login = new LoginPage(driver);
			AdventurePage adventure = new AdventurePage(driver);
			AdventureDetailsPage adventureDetails = new AdventureDetailsPage(driver);
			HistoryPage history = new HistoryPage(driver);

			home.navigateToHomePage();
			home.clickOnRegister();
			register.registerUser(username, password, true);
			String lastGeneratedUsername = register.lastGeneratedUsername;
			login.performLogin(lastGeneratedUsername, password);
			home.searchForCity(city);
			home.clickOnTheCity(city);
			adventure.searchForAdventure(advName);
			adventureDetails.reserveAdventure(guestName, date, count);
			assertion.assertTrue(adventureDetails.checkAdventureSuccessfull(), "Adventure unsuccessfull");
			test.log(LogStatus.PASS, "successful Adventure");
			history.navigateToHistory();
			String id = history.getTransactionId();
			history.cancelReservation();
			Boolean status = history.noTransactionId(id);
			assertion.assertTrue(status, "Id has not been removed");
			test.log(LogStatus.PASS, "Id has been removed");
			logStatus("TestCase 03", "verify that adventures booking and cancellation work fine", "success");

		} catch (Exception e) {
			logStatus("TestCase 03", "verify that adventures booking and cancellation work fine", "failed");
			e.printStackTrace();
			test.log(LogStatus.FAIL, test.addScreenCapture(ReportSingleton.capture(driver))+"TestCase03 failed, reason: "+e.getMessage());
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
