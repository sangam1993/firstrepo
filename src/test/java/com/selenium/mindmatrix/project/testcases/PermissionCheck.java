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


public class PermissionCheck extends BaseTest {
	SoftAssert sassert; 
	String testCaseName;
	Xls_Reader xls;
	
	@Test(priority=1, dataProvider="getData")
	public void permissionCheck(Hashtable<String,String> data) {
		test = rep.startTest("permissionCheck");
		test.log(LogStatus.INFO, data.toString());
		if(!DataUtil.isRunnable("PermissionCheck", xls) ||  data.get("Runmode").equals("N")){
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

		mouseMovement("setup_xpath");                                   
		mouseClick("expandum_xpath");
		mouseClick("roles_xpath");
		List<WebElement> options = driver.findElements(By.id("msg1"));
		System.out.println(options.size());
		if(options.size()==0) {
			test.log(LogStatus.INFO, "No element to check");
		}else {
			mouseClick("first_xpath");
			wait(2);
			boolean status = getElement("checkbox_xpath").isEnabled();
			System.out.println(status);
			if(status) {
				
				test.log(LogStatus.INFO, "User is having a permission to check the checkboxes");
				
			} else {
			 
			    test.log(LogStatus.INFO, "User is not having a permission to check the checkboxes");
			
			}
				
			
						
		}	
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
		if(driver!=null)
			driver.quit();
		
	}
	
	@DataProvider
	public Object[][] getData() {
		super.init();
		xls = new Xls_Reader(prop.getProperty("xlspath"));
		return DataUtil.getTestData(xls,"LoginTest");
	}
	


}
