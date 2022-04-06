package basedriver;

import java.io.IOException;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.Status;

import utils.selenium.Utils;

public class BaseClass extends Utils{
	
	
	
@BeforeMethod
@Parameters("tc")
public void suiteInitializer(String testCaseName) {
	test = extent.createTest(testCaseName);
	browserLaunch(getpropvalue("browser"));
	launchUrl(getpropvalue("url"));
	
}
//Test
@BeforeSuite
public void testInitializer() {
	reportStarter();
	//sys
}

@AfterMethod
public void testCloserIT(ITestResult result) throws Exception {
	
	if(result.getStatus() == ITestResult.FAILURE)
    {
     //   String screenShotPath =capture(driver, "screenShotName");
        test.log(Status.FAIL, result.getThrowable());
        test.addScreenCaptureFromBase64String(addScreenShot(driver,"screenShotPath"));
        System.out.println("Completed");
    }
	extentReportFinisher();
	driver.close();
	
}

@AfterSuite
public void suiteCloser() {
	
	extentReportFinisher();
	
}

}
