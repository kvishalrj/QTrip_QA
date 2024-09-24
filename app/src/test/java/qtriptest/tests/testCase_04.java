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

public class testCase_04 {

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
        test = report.startTest("TestCase 04");
	}


	@Test(description = "Verifying all bookings", priority = 4, groups = "Reliability Flow", dataProvider = "data-provider", dataProviderClass = DP.class, enabled = true)
	public static void TestCase04(String username, String password, String dataset1, String dataset2, String dataset3) throws IOException {
		Assertion assertion = new Assertion();
		ReportSingleton.getInstance();

		logStatus("TestCase 04", "Verifying all bookings", "started");
		try {

            String[] datasets = {dataset1, dataset2, dataset3};

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
			
			for (String dataset : datasets) {
                String[] data = dataset.split(";");
                home.searchForCity(data[0]);
                home.clickOnTheCity(data[0]);
				Thread.sleep(2000);
                adventure.searchForAdventure(data[1]);
                adventureDetails.reserveAdventure(data[2], data[3], data[4]);
                assertion.assertTrue(adventureDetails.checkAdventureSuccessfull(), "Adventure unsuccessfull");
                test.log(LogStatus.PASS, "Adventure successfull");
				home.navigateToHomePage();
            }

            history.navigateToHistory();
            int totalBook = history.getTotalBookings();
            assertion.assertTrue(totalBook==3, "Bookings display failed");
			test.log(LogStatus.PASS, "Bookings display successfull");
			login.performLogout();
			
			logStatus("TestCase 04", "Verifying all bookings", "success");

		} catch (Exception e) {
			logStatus("TestCase 04", "Verifying all bookings", "failed");
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
