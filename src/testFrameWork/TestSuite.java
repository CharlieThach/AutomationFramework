package testFrameWork;

import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import utility.loadExcel;
import utility.loadProperty;

public class TestSuite {
	public ExtentReports report; 
	public ExtentTest logger;
	public WebDriver driver; 
	public loadExcel load; 
	private String[][] data;
	private WebDriverWait wait;
	private Properties prop;
	private loadProperty proper;

	public TestSuite(){
		try {
			prop = new utility.loadProperty().readPropFile();
		} catch (IOException e) {
			System.out.println("Can't open property file");
			e.printStackTrace();
		}
		
	}
	
	@BeforeClass //run only once before first test method 
	public void setupTest(){
		System.setProperty(prop.getProperty("browser"), prop.getProperty("browserPath"));
		report = new ExtentReports(prop.getProperty("reportPath"));
	}
	
	@DataProvider(name = "wowpage")
	public String[][]loadExecel(){
		load = new loadExcel("wowTestCase");
		return data = load.readExcel();
	}
	
	@BeforeMethod
	public void startDriver(){
		  driver = new ChromeDriver();
		  wait = new WebDriverWait(driver, 10); 
	}
	
	@AfterMethod //run after each test
	public void houseCleaning(ITestResult result){
		 if(result.isSuccess()){
			 logger.log(LogStatus.INFO,"Test case "+logger.getTest().getName()+" has been verified");
			 logger.log(LogStatus.PASS, "Passed");
		 }else{
			 logger.log(LogStatus.INFO,"Test case "+logger.getTest().getName()+" has been verified");
			 logger.log(LogStatus.FAIL, "Test Failed");
		 }
		 report.endTest(logger);
		  driver.quit();
	}
	
	
	@AfterTest //run last after all test have gone
	public void tearDown(){
		report.flush();  
	}
	
	
	
}
