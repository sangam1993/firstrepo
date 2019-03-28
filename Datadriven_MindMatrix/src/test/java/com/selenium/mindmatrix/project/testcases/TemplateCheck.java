package com.selenium.mindmatrix.project.testcases;


import java.util.Hashtable;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.relevantcodes.extentreports.LogStatus;
import com.selenium.mindmatrix.project.base.BaseTest;
import com.selenium.mindmatrix.project.util.DataUtil;
import com.selenium.mindmatrix.project.util.Xls_Reader;


public class TemplateCheck extends BaseTest {
	SoftAssert sassert; 
	String testCaseName;
	Xls_Reader xls;
	
	@Test(priority=1, dataProvider="getData")
	public void templateCheck(Hashtable<String,String> data) {
		test = rep.startTest("LoginTest");
		test.log(LogStatus.INFO, data.toString());
		if(!DataUtil.isRunnable("TemplateCheck", xls)){
			test.log(LogStatus.SKIP, "Skipping the test as runmode is N");
			throw new SkipException("Skipping the test as runmode is N");
			
		}

		openBrowser(data.get("Browser"));
		navigate("appurl");
		
		boolean actualResult=doLogin(data.get("Username"), data.get("Password"));
		
		boolean expectedResult=false;
		if(data.get("ExpectedResult").equals("Y"))
			expectedResult=true;
		else
			expectedResult=false;
		
		if(expectedResult!=actualResult)
			reportFail("Login Test Failed.");
		
		reportPass("LoginTest Passed");
		
		  /******************part 1 *****************/
		
		test = rep.startTest("TemplateCheck");	
		
		test.log(LogStatus.INFO, "Templatecheck part 1 is starting");
		
		mouseMovement("setup_xpath");                                   
		click("assetexpand_xpath");
		click("template_xpath");
		wait(2);
		//WebElement boxx = prop.getProperty("box_xpath");
		//List<WebElement> options = boxx.findElements(By.tagName("li"));
		
		
		
		WebElement box = driver.findElement(By.xpath("//*[@class='inbox-folder-scrool']"));
		List<WebElement> options = box.findElements(By.tagName("li"));
				 
		try {
		
			
		for (int i=0;i<=options.size();i++) {
			
			test.log(LogStatus.INFO,options.get(i).getText());		 		
		}
		
		} catch (Exception e) {
			
			test.log(LogStatus.INFO, "links are not getting printed in the report");
		}
		
	    if(options.size()==15) {
			 
			 test.log(LogStatus.PASS,"Template check part 1 is passed");	
	    }
	    
	    else {
	    	 test.log(LogStatus.FAIL,"Template check part 1 is failed");	
	    }
	    
	    
	  /******************part 2 *****************/
	    
	    
	    test.log(LogStatus.INFO, "Templatecheck part 2 is starting");
	    
	   boolean status = getElement("switch_xpath").isEnabled();
	   
	   if (status) {
		   
		   test.log(LogStatus.INFO, "Switch is enabled and changing options are available");
		   
	   } else {
		   
		   test.log(LogStatus.INFO, "Switch is not enabled");
		   
	   }
	   
	   test.log(LogStatus.INFO, "switch contains "+ getElement("switch_xpath").getAttribute("data-swchon-text")+" button" );
	   test.log(LogStatus.INFO, "switch contains "+ getElement("switch_xpath").getAttribute("data-swchoff-text")+" button" );
	}

	
	@BeforeMethod
	public void init() {
	
		sassert = new SoftAssert();	    
		
	}
		
	@AfterMethod
	public void quit() {
		try {
			sassert.assertAll();
		}catch(Error e) {
			test.log(LogStatus.FAIL, e.getMessage());
			
		}
		
		rep.endTest(test); 
		rep.flush();
		//if(driver!=null)
		  //driver.quit();
		
	}
	
	@DataProvider
	public Object[][] getData() {
		super.init();
		xls = new Xls_Reader(prop.getProperty("xlspath"));
		return DataUtil.getTestData(xls,"LoginTest");
	}
	


}
