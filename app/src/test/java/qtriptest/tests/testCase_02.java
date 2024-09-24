package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.ReportSingleton;
import qtriptest.pages.HomePage;
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

public class testCase_02 {

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
        test = report.startTest("TestCase 02");
	}


	@Test(description = "verifying search and filters", priority = 2, groups = "Search and Filter flow", dataProvider = "data-provider", dataProviderClass = DP.class, enabled = true)
	public static void TestCase02(String city, String categoryFilter, String durationFilter, String expectedFilterResults, String expectedUnfilterResults) throws IOException {
		Assertion assertion = new Assertion();
		ReportSingleton.getInstance();

		logStatus("TestCase 02", "verifying search and filters", "started");
		try {

			HomePage home = new HomePage(driver);

            home.navigateToHomePage();
			// home.searchForCity("Ranchi");
			// assertion.assertTrue(home.verifyNoCity(), "verify no city failed");
			home.searchForCity(city);
			assertion.assertTrue(home.verifyCityDisplayed(city), "verify city display failed");
			test.log(LogStatus.PASS, "successful verify city display");
			
			home.clickOnTheCity(city);
			home.filterHoursSelect(durationFilter);
			// assertion.assertTrue(home.verifyRecords()==10, "verify records failed");
			home.filterCategorySelect(categoryFilter);
			assertion.assertTrue(home.verifyRecords()==Integer.parseInt(expectedFilterResults), "verify records failed");
			test.log(LogStatus.PASS, "successful verify records");

			home.clearFiltersHours();
			home.clearFiltersCategories();
			assertion.assertTrue(home.verifyRecords()==Integer.parseInt(expectedUnfilterResults), "verify records failed");
			test.log(LogStatus.PASS, "successful verify records");

			logStatus("TestCase 02", "verifying search and filters", "success");

		} catch (Exception e) {
			logStatus("TestCase 02", "verifying search and filters", "failed");
			e.printStackTrace();
			test.log(LogStatus.FAIL, test.addScreenCapture(ReportSingleton.capture(driver))+"TestCase02 failed, reason: "+e.getMessage());
		}
	}


	// Quit webdriver after Unit Tests
	@AfterClass(enabled = true)
	public static void quitDriver() throws MalformedURLException {
		driver.close();
		driver.quit();
		logStatus("driver", "Quitting driver", "Success");

		//End the test
        report.endTest(test);

        // Write the test to filesystem
        report.flush();
	}

}
