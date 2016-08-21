package testFrameWork;

import java.io.IOException;
import java.util.Properties;

import org.testng.Assert;
import org.testng.ITestResult;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import utility.*;

public class loginGmail {
public String url = "http://www.gmail.com";
public ExtentReports report; 
public ExtentTest logger;
public WebDriver driver; 
public loadExcel load; 
public String[][] data;
WebDriverWait wait;
Properties prop;
loadProperty proper;

	loginGmail(){
	  proper = new utility.loadProperty();
	  try {
		prop = proper.readPropFile();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
	  //load from excel
	  
	  
	  System.setProperty(prop.getProperty("drivername"), prop.getProperty("chromedriver"));
	  report = new ExtentReports("C:/Report/Gmail.html");

  }
  @DataProvider(name = "checkPage")
  public String[][] read(){
		load = new loadExcel();
		return  data = load.readExcel(); 
  }
  
  @BeforeMethod
  public void starterDriver(){
	  driver = new ChromeDriver();
	  wait = new WebDriverWait(driver, 10); 
	   
	 
  }
  @Test(dataProvider="checkPage")
  public void checkPage(String tname, String desc, String step, String expect){
	  
	  logger = report.startTest(tname);
	  logger.log(LogStatus.INFO, "Start test "+tname);
	  driver.get(url);
	  logger.log(LogStatus.INFO, "Fetch url : "+url);
	  String title = driver.getTitle();
	  logger.log(LogStatus.INFO, "Grab title "+driver.getTitle() );
	  Assert.assertTrue(title.contains(expect));
	  logger.log(LogStatus.INFO, "Compare actual tilepage: "+driver.getTitle()+" with expected title page: "+expect);
	  report.endTest(logger);
  }
  @Test 
  public void validLogin(){
	  logger = report.startTest("validLogin");
	  logger.log(LogStatus.INFO, " Starting Test ");
	  driver.get(url);
	  logger.log(LogStatus.INFO, " Fetch ur; : "+url);
	  	wait.until(ExpectedConditions.elementToBeClickable(By.id("next")));
	  	logger.log(LogStatus.INFO, "Wait for id NEXT to Appear");
	  driver.findElement(By.id("Email")).sendKeys(prop.getProperty("user"));
	  driver.findElement(By.id("next")).click(); 
	  	wait.until(ExpectedConditions.elementToBeClickable(By.id("Passwd")));
	  	logger.log(LogStatus.INFO, "Grab title page");
	  driver.findElement(By.id("Passwd")).sendKeys(prop.getProperty("pass"));
	  driver.findElement(By.id("signIn")).click();
	  	wait.until(ExpectedConditions.elementToBeClickable(By.id("gbqfb")));
	  Assert.assertTrue(driver.getTitle().contains("choithach@gmail.com"));	  
  }
  @Test
  public void invalidLogin(){
	 logger = report.startTest("invalidLogin"); 
	 driver.get(url);
	  driver.findElement(By.id("Email")).sendKeys(prop.getProperty("user"));
	  driver.findElement(By.id("next")).click(); 
	  	wait.until(ExpectedConditions.elementToBeClickable(By.id("Passwd")));
	  	logger.log(LogStatus.INFO, "Grab title page");
		  driver.findElement(By.id("Passwd")).sendKeys("testing");
		  driver.findElement(By.id("signIn")).click();
		  Assert.assertTrue(driver.getTitle().contains("Gmail"));
		  
  }
  
  @AfterMethod
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
  @AfterTest
  public void tearDown(){
	report.flush();  

  }

}
