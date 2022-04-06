package utils.selenium;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Utils {

	public static WebDriver driver;
	public static String dateandtime;
	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports extent;
	public static ExtentTest test;

	
	public String addScreenShot(WebDriver d, String imgpath) throws Exception
	{
		String image = "";
		FileInputStream imageFile;
		try
		{
			String dest = System.getProperty("user.dir") +"\\ErrorScreenshots\\"+imgpath+".png";
			File imgfile = new File(capture(d, dest));
			imageFile = new FileInputStream(dest);
            byte imageData[] = new byte[(int) imgfile.length()];
            imageFile.read(imageData);
            byte[] base64EncodedByteArray = Base64.encodeBase64(imageData);
            image = new String(base64EncodedByteArray);
	    }
		catch(Exception e)
		{
			e.printStackTrace();
		}
			return "data:image/png;base64,"+image;
	}
	
	
	// Browser Launch
	public static void browserLaunch(String name) {

		switch (name) {
		case "Chrome":
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			break;
		case "FireFox":
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			break;
		default:
			System.out.println("No valid options given choosing the default browser");
			driver = new ChromeDriver();
		}
		driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		driver.manage().window().fullscreen();
	}
	
    public static String capture(WebDriver driver,String dest) throws IOException
    {
        TakesScreenshot ts = (TakesScreenshot)driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
       
        File destination = new File(dest);
        FileUtils.copyFile(source, destination);        
                     
        return dest;
    }
	public static boolean jstypetext(WebElement element, String Text, String id) {
		boolean flag = false;
		try {
			try {
				if (waitforme(element, id)) {
					((JavascriptExecutor)driver).executeScript("arguments[0].value='"+Text+"'",element); 
					test.log(Status.PASS, Text + "is  typed in " + id);
					flag = true;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (Exception e) {

			System.out.println("Error in Typetext : " + e.getMessage());
			test.log(Status.FAIL, "Error in GetUrl : " + e.getMessage());

		}
		return flag;
	}

	public static String getpropvalue(String key) {
		String value = null;
		try {
			Properties prop = new Properties();
			FileInputStream ip = new FileInputStream(System.getProperty("user.dir") + "//config.properties");
			prop.load(ip);
			value = prop.getProperty(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
	
	//Launch the url
	
	public static void launchUrl(String url) {
		driver.get(url);
	}
	// Check for element availability
	public static boolean waitforme(WebElement element, String id) {
		boolean flag = false;

		try {

			WebDriverWait wait = new WebDriverWait(driver, 15);
			wait.until(ExpectedConditions.visibilityOf(element));
			test.log(Status.PASS, id + "is available");
			flag = true;
		} catch (Exception e) {
			System.out
					.println("Error occured while waiting for the element :" + id + "with exception " + e.getMessage());
			test.log(Status.FAIL, id + "is not available");

		}
		return flag;
	}

	// Click a Web Element

	public void clickElement(WebElement element, String id) {
		try {

			if (waitforme(element, id)) {
				element.click();
				test.log(Status.PASS, id + "is clicked ");
			}
		} catch (Exception e) {
			System.out.println("Error in clicking the webelement :" + id + e.getMessage());
			test.log(Status.FAIL, "Failed to click the element :" + id);
		}

	}
	// Type in a Text Box

	public void typeText(WebElement element, String text , String id) {
		
		try {
			if(waitforme(element,id)) {
				element.sendKeys(text);
				test.log(Status.PASS, "typed "+text + "in the text box "+ id);
			}
		}
		catch (Exception e) {
			System.out.println("Error in clicking the webelement :" + id + e.getMessage());
			test.log(Status.FAIL, "Failed to type the text  the element :" + id);
		}

	}

	public static void extentReportFinisher() {

		try {
			extent.flush();
		} catch (Exception e) {
			System.out.println("Error in ExtentReportFinisher : " + e.getMessage());
		}
	}

	public static boolean getUrl(String url) {
		boolean flag = false;
		try {
			driver.get(url);
			test.log(Status.PASS, "URL Launched Successfully : " + url);
			flag = true;
		} catch (Exception e) {

			System.out.println("Error in GetUrl : " + e.getMessage());
			test.log(Status.FAIL, "Error in GetUrl : " + e.getMessage());

		}
		return flag;
	}

	// Report Starter

	public void reportStarter() {
		try {
			dateandtime = GetDateandTime();
			htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/UITestReport.html");
			// Create an object of Extent Reports
			extent = new ExtentReports();
			extent.attachReporter(htmlReporter);
			extent.setSystemInfo("Company", "Casa Retail Assignment");
			extent.setSystemInfo("Environment", "Assignment");
			htmlReporter.config().setDocumentTitle("Casa Retail Assignment");
			htmlReporter.config().setTheme(Theme.STANDARD);
		} catch (Exception e) {
			System.out.println("Error in ExtentReportStarter : " + e.getMessage());
		}
	}

	public static String GetDateandTime() {
		DateFormat dateFormat = null;
		Date date = null;
		try {
			dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			date = new Date();
		} catch (Exception e) {
			System.out.println("Error in Getdateandtime : " + e.getMessage());
		}

		return dateFormat.format(date);
	}

	// Compare two entities

	// Check if the Element is available

	// Check if the Element is not available
	
	public static boolean elementnotpresent(WebElement element, String id) {
		boolean flag = true;
		try {
			WebDriverWait wait = new WebDriverWait(driver, 5);
			wait.until(ExpectedConditions.visibilityOf(element));
			test.log(Status.FAIL, id + " is not displayed");
			flag = false;
		} catch (Exception e) {
			test.log(Status.PASS, id + " is displayed");

		}
		return flag;
	}
	//Switching Tab
	
	public static boolean switchTab() {
		boolean flag = false;
		try {
		String currentWindowHandle = driver.getWindowHandle();
		Set<String> availableWindowHandles = driver.getWindowHandles();
		for(String wh : availableWindowHandles) {
			if(!wh.equalsIgnoreCase(currentWindowHandle)) {
				driver.switchTo().window(wh);
				break;
			}
		}
		test.log(Status.PASS, "Switched the tab successfully");
		flag = true;
		}
		catch(Exception e) {
			System.out.println("Error in switching the tab " + e .getMessage());
			test.log(Status.FAIL, "Error in switching the tab");
			
		}
		return flag;
		
	}

	// Close the browser
	
	public void closeBrowser() {
		driver.close();
	}
	
	//Quit the browser
	
	public void quitBrowser() {
		driver.quit();
		
	}

}
