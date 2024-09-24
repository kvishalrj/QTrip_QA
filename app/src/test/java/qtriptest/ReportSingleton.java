package qtriptest;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;

// Extent report imports
import com.relevantcodes.extentreports.ExtentReports;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ReportSingleton {

    private static ReportSingleton instance;
    // public ExtentTest test;
    public ExtentReports report;

    private ReportSingleton() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String timestampString = String.valueOf(timestamp.getTime());
        
        // TODO - 1. CREATE an instance of ExtentReports
        report = new ExtentReports(System.getProperty("user.dir")+"/OurExtentReport"+timestampString+".html");
        
        report.loadConfig(new File(System.getProperty("user.dir")+"/extent_customization_configs.xml"));

        // TODO - 2. Start a new test
        // test = report.startTest("QTrip");
    }

    public static synchronized ReportSingleton getInstance() {
        if (instance == null) {
            instance = new ReportSingleton();
        }
        return instance;
    }

    public ExtentReports getExtent() {
        return report;
    }

    public static String capture(WebDriver driver) throws IOException{
        //TODO: Add all the components here 
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File Dest = new File(System.getProperty("user.dir")+"/QTRIPImages/" + System.currentTimeMillis()+ ".png");      
        String errflpath = Dest.getAbsolutePath();
        FileUtils.copyFile(scrFile, Dest);
        return errflpath;
    }

}