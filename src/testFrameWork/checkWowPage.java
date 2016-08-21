package testFrameWork;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.*;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import utility.loadExcel;

public class checkWowPage {
	public ExtentReports report; 
	public ExtentTest logger;
	public WebDriver driver; 
	public loadExcel load; 
	private WebDriverWait wait;
	private Properties prop;
	private String url; 

	public checkWowPage(){
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
		url = prop.getProperty("urlWow");
	}
	
	@DataProvider(name = "wowpage")
	public String[][]loadExecel(){
		load = new loadExcel(prop.getProperty("pathToTestCases"));
		return load.readExcel();
	}
	
	@BeforeMethod
	public void startDriver(){
		  driver = new ChromeDriver();
		  wait = new WebDriverWait(driver, 10); 
	}

	@Test(dataProvider="wowpage")
	public void checkPage(String execute, String tname, String desc, String step, String expect, String id){
		if(execute.equalsIgnoreCase("yes")){
		logger = report.startTest(tname);
		logger.log(LogStatus.INFO, "Start test "+tname);
		driver.get(url);
		logger.log(LogStatus.INFO, "Fetch url : "+url);
//		String currentURL = driver.getCurrentUrl();
		logger.log(LogStatus.INFO, "Browser has reach: "+driver.getCurrentUrl());
		if(id.isEmpty()){
			logger.log(LogStatus.INFO, "Compare actual URL: "+driver.getCurrentUrl()+" with expected title page: "+expect);
			Assert.assertTrue(driver.getCurrentUrl().contains(expect));
		}else if(id.contains("dropbox")){
			
			driver.findElement(By.xpath("/html/body/center/div[1]/div/ul/li[3]/div/div/span[1]/a/")).getText();
			logger.log(LogStatus.INFO, "verify dropbox");
		
			//Select dropdown = new Select(driver.findElement(By.id("header")));
			//System.out.println(dropdown.getOptions());
			//dropdown.selectByIndex(1);
			//	WebElement option = driver.findElement(By.id("media"));
		//	Select select = new Select(option);
		//	select.selectByVisibleText("Wallpapers");
			//			System.out.println(select.toString());
//			List<WebElement>options = select.getOptions();
//			for(WebElement a: options){
//				System.out.println("Options: " +a.getText());
//			}
			logger.log(LogStatus.INFO, "The url of the drop box" +driver.getCurrentUrl());
		}
		else{//all of the others beside the first test cases and dropmenu
			driver.findElement(By.id(id)).click();
			logger.log(LogStatus.INFO, "Compare actual URL: "+driver.getCurrentUrl()+" with expected title page: "+expect);
			Assert.assertTrue(driver.getCurrentUrl().contains(expect));
		}
	 }else{
		 logger.log(LogStatus.SKIP, "Test was skipped");
		 throw new SkipException("Test was skipped");
		 
	 }
	}
	public void verifyPage(String tname, String desc, String step, String expect, String iD ){
		logger = report.startTest(tname);
		logger.log(LogStatus.INFO, "Start test "+tname);
		driver.get(url);
		logger.log(LogStatus.INFO, "Fetch url : "+url);
		String currentURL = driver.getCurrentUrl();
		logger.log(LogStatus.INFO, "Grab title "+driver.getTitle() );
		logger.log(LogStatus.INFO, "Compare actual title page: "+driver.getCurrentUrl()+" with expected title page: "+expect);
		//report.endTest(logger);
		Assert.assertTrue(currentURL.contains(expect));
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
