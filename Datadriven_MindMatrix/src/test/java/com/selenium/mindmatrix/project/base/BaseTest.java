package com.selenium.mindmatrix.project.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.selenium.mindmatrix.project.util.ExtentManager;



public class BaseTest {
    public WebDriver driver;
    public Properties prop;
	public ExtentReports rep = ExtentManager.getInstance();
	public ExtentTest test;
	SoftAssert  softAssertion=new SoftAssert();
	
	public void init() {
      if(prop==null) {
			
			prop = new Properties();
			FileInputStream fs;
			try {
				fs = new FileInputStream(System.getProperty("user.dir")+"//src//test//resources//projectconfig.properties");
				prop.load(fs);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	public void openBrowser(String bType) {

		
		test.log(LogStatus.INFO, "Opening browser "+bType );
		if(bType.equals("Mozilla")) {
			System.setProperty("webdriver.gecko.driver",prop.getProperty("firefox_driver"));
			driver = new FirefoxDriver();
		} else if(bType.equals("Chrome")) {
			ChromeOptions options = new ChromeOptions();
			//options.addArguments("--disable-notifications--");
			System.setProperty("webdriver.chrome.driver",prop.getProperty("chrome_driver"));
			driver = new ChromeDriver(options);
		}else if(bType.equals("IE")) {
			System.setProperty("webdriver.ie.driver",prop.getProperty("ie_driver"));
		
			driver = new InternetExplorerDriver();
		}
		
		driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
		driver.manage().window().maximize();
		test.log(LogStatus.INFO, "Browser opened and maximized successfully" );
		
		
	
	}
	
    public void navigate(String urlkey) {
    	test.log(LogStatus.INFO, "Navigating to "+prop.getProperty(urlkey));
    	driver.get(prop.getProperty(urlkey));
		
	}
    
    public void click(String locatorkey) {
    	test.log(LogStatus.INFO, "Clicking on "+locatorkey);
    	getElement(locatorkey).click();
    
		
	}
    
    public void clickJS(String path) {
    	
    	WebElement element = driver.findElement(By.xpath(path));
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("arguments[0].click();", element);
		
    }
    
    public void mouseMovement(String xpath) {
    	Actions mouse = new Actions(driver);
		mouse.moveToElement(driver.findElement(By.xpath(prop.getProperty(xpath)))).perform();
    }
    
    public void mouseClick(String xpath) {
    	Actions mouse = new Actions(driver);
		mouse.moveToElement(driver.findElement(By.xpath(prop.getProperty(xpath)))).click().perform();
    }
	
    public void type(String locatorkey, String data) {
    	test.log(LogStatus.INFO, "Tying in "+locatorkey+". Data - "+data);
    	getElement(locatorkey).sendKeys(data);
    	test.log(LogStatus.INFO, "Typed successfully in "+locatorkey);
	}
    
    public void clickAndWait(String locator_clicked,String locator_pres){
		test.log(LogStatus.INFO, "Clicking and waiting on - "+locator_clicked);
		int count=5;
		for(int i=0;i<count;i++){
			getElement(locator_clicked).click();
			wait(2);
			if(isElementPresent(locator_pres))
				break;
		}
	}
    
    public void acceptAlert() {
    	WebDriverWait wait = new WebDriverWait(driver,5);   	
    	wait.until(ExpectedConditions.alertIsPresent());
    	test.log(LogStatus.INFO,"aceepting alert");
    	driver.switchTo().alert().accept();
    	
    }
    

     // finding element and returning it
    public WebElement getElement(String locatorkey) {
    	WebElement e = null;
    	try {
    	if(locatorkey.endsWith("_xpath")) 
    	e = driver.findElement(By.xpath(prop.getProperty(locatorkey)));
    	else if(locatorkey.endsWith("_id")) 
        	e = driver.findElement(By.id(prop.getProperty(locatorkey)));
    	else {
    		reportFail("locator not working--"+locatorkey);
    		Assert.fail("locator not working--"+locatorkey); 
    	}
    	}catch (Exception ex) {
    		reportFail(ex.getMessage());
    		ex.printStackTrace();
    		Assert.fail("Failed the test"+ex.getMessage());   		
    	}
    	return e;
    }
    public WebElement getElementsoft(String locatorkey) {
    	WebElement e = null;
    	try {
    	if(locatorkey.endsWith("_xpath")) 
    	e = driver.findElement(By.xpath(prop.getProperty(locatorkey)));
    	else if(locatorkey.endsWith("_id")) 
        	e = driver.findElement(By.id(prop.getProperty(locatorkey)));
    	else {
    		reportFail("locator not working--"+locatorkey);
    		Assert.fail("locator not working--"+locatorkey); 
    	}
    	}catch (Exception ex) {
    		reportFail(ex.getMessage());
    		ex.printStackTrace();
    		softAssertion.fail("Failed the test"+ex.getMessage());   		
    	}
    	return e;
    }
    /******************validate*****************/
    
  
    public boolean isElementPresent(String locatorkey) {
    	List<WebElement> elementlist = null;
    	if(locatorkey.endsWith("_xpath")) 
        	elementlist = driver.findElements(By.xpath(prop.getProperty(locatorkey)));
        	else if(locatorkey.endsWith("_id")) 
        		elementlist = driver.findElements(By.id(prop.getProperty(locatorkey)));
        	else {
        		reportFail("locator not working--"+locatorkey);
        		Assert.fail("locator not working--"+locatorkey); 
        	}
    	if(elementlist.size()==0)
    	return false;
    	else 
    	return true;
		
   	}
    
    public boolean verifyText(String locatorkey, String expectedtextkey) {
    	String actual = getElement(locatorkey).getText();
    	String expected = prop.getProperty(expectedtextkey);   	
    	if(actual.equals(expected))
    	return true;
    	else
    	return false;
		
   	}
    public  boolean isClickable(String xpath)      
	{
		WebElement webe=getElementsoft(xpath);
	try
	{
	   WebDriverWait wait = new WebDriverWait(driver, 5);
	   wait.until(ExpectedConditions.elementToBeClickable(webe));
	   return true;
	}
	catch (Exception e)
	{
	  return false;
	}
	}
    
    
    /******************reporting*****************/
    
	/*
	 * public void reportPass(String msg) { test.log(LogStatus.PASS,msg);
	 * 
	 * }
	 * 
	 * public void reportFail(String errmsg) { test.log(LogStatus.FAIL, errmsg);
	 * takeScreenshot(); Assert.fail(errmsg); }
	 */
    
    
    public void linkverification(String name) {
		// WebDriver driver=new FirefoxDriver();

		// driver.manage().window().maximize();

		// driver.get("http://www.google.co.in/");

		List<WebElement> links = driver.findElements(By.tagName("a"));

		// System.out.println("Total links are "+links.size());

		for (int i = 0; i < links.size(); i++) {

			WebElement ele = links.get(i);
			String val = ele.getText();
			if (val.equals(name)) {
				String url = ele.getAttribute("href");
				verifyLinkActive(url, val);
			}

			// if(url.equals(value))
			//

		}

	}
    
   
    public void Toolfield(String xpath, String tolxpath, String Expected) {

		getElement(xpath).click();
		if(isClickable(xpath))
		{
			
		
		// Actions builder=new Actions(driver);
		WebElement username_tooltip = getElement(tolxpath);
		String name = username_tooltip.getText();
		if (name.equals(Expected)) {
			test.log(LogStatus.PASS, Expected + " "+"is appered sucessfully");
		} else {
			test.log(LogStatus.FAIL, Expected + " "+"is not appered.");
		}
		}
		else
		{
			test.log(LogStatus.FAIL, Expected + "is not Clickable.");
		}
	}

	/*
	 * public void placeholder(String xpath, String Expect) { String Actual =
	 * getElement(xpath).getAttribute("placeholder"); if (Actual.equals(Expect)) {
	 * test.log(LogStatus.PASS, Expect + " " + "Placeholder value is verified "); }
	 * else { test.log(LogStatus.FAIL, Expect + " " +
	 * "Placeholder value is not Correct"); } }
	 */
	public void verifyLinkActive(String linkUrl, String val) {
		try {
			URL url = new URL(linkUrl);

			HttpURLConnection httpURLConnect = (HttpURLConnection) url.openConnection();

			httpURLConnect.setConnectTimeout(3000);

			httpURLConnect.connect();

			if (httpURLConnect.getResponseCode() == 200) {
				test.log(LogStatus.PASS, val + "is verified");
			}
			if (httpURLConnect.getResponseCode() == HttpURLConnection.HTTP_NOT_FOUND) {
				test.log(LogStatus.FAIL, val + " is Not verified" + "");
			}
		} catch (Exception e) {

		}
	}

	/***************** reporting ****************/

	public void reportPass(String msg) {
		test.log(LogStatus.PASS, msg);

	}

	public void reportFail(String errmsg) {
		test.log(LogStatus.FAIL, errmsg);
		takeScreenshot();
		softAssertion.fail(errmsg);
	}

	public void reportFailsoft(String errmsg) {
		test.log(LogStatus.FAIL, errmsg);
		takeScreenshot();
		softAssertion.fail(errmsg);
	}
	
	/*
	 * public void placeholder(String xpath, String Expect) { String Actual =
	 * getElement(xpath).getAttribute("placeholder"); if (Actual.equals(Expect)) {
	 * test.log(LogStatus.PASS, Expect + " " + "Placeholder value is verified "); }
	 * else { test.log(LogStatus.FAIL, Expect + " " +
	 * "Placeholder value is not Correct"); } }
	 */
    
	public void submit(String submit) {

		boolean display = getElement(submit).isDisplayed();
		boolean isenable = getElement(submit).isEnabled();
		if (isElementPresent(submit) && display == true && isenable == true) {

			test.log(LogStatus.PASS, " Login button is  enabled");
		} else {
			test.log(LogStatus.FAIL, "Visibility of Login is not enabled");
		}
	}
	
    public void takeScreenshot() {
    	// fileName of the screenshot
    			Date d=new Date();
    			String screenshotFile=d.toString().replace(":", "_").replace(" ", "_")+".jpg";
    			// store screenshot in that file
    			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    			try {
    				FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")+"//Screenshots//"+screenshotFile));
    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    			//put screenshot file in reports
    			test.log(LogStatus.INFO,"Screenshot-> "+ test.addScreenCapture(System.getProperty("user.dir")+"//Screenshots//"+screenshotFile));

   	}
    
    public void waitForPageToLoad() {
    	JavascriptExecutor js = (JavascriptExecutor)driver;
	 	String state = (String)js.executeScript("return document.readystate");
	 	
	 	while(!state.equals("complete")) {
	 		wait(1);
	 		state = (String)js.executeScript("return document.readystate");
	 		
	 		
	 		}
    	
    }
    
    public void wait(int timeToWait) {
    	try {
			Thread.sleep(timeToWait*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
   /****************appFunctions**************/
   
    public boolean doLogin(String username,String password) {
		test.log(LogStatus.INFO, "Trying to login with "+ username+","+password);
		type("email_xpath",username);
		type("password_xpath",password);
		click("loginbutton_xpath");		
		if(isElementPresent("dashboard_xpath")){
			test.log(LogStatus.INFO, "Login Success");
			return true;
		}
		else{
			test.log(LogStatus.INFO, "Login Failed");
			return false;
		}
		
		
	}
    
   
  
}
