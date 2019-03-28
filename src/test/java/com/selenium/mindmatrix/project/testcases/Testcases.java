package com.selenium.mindmatrix.project.testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
import com.selenium.mindmatrix.project.base.BaseTest;

public class Testcases extends BaseTest {
	SoftAssert sassert ;
	
	
	@BeforeMethod
	public void initilization() {
		init();
		sassert = new SoftAssert() ;
		

	}

	@Test
	public void template_case05() {
		test = rep.startTest("getTestData");
		openBrowser("Chrome");
		driver.get("https://dvl-master.amp.vg");
		doLogin("rohit26580@rediffmail.com", "rohit123");

		click("setup_xpath");
		mouseClick("asert_xpath");
		mouseClick("template_xpath");
		wait(5);
		click("PrintTemplate_xpath");
		click("Create_xpath");
		click("Go_xpath");
		System.out.println(getElement("info_xpath").getText());
		if (isElementPresent("info_xpath") && isElementPresent("data_xpath") && isElementPresent("prineditor_xpath")) {

			test.log(LogStatus.INFO, "All wizaed are present under Create respective template ");
		} else {
			System.out.println("test case are failed");
			reportFail("All wizaed are not present please verify from screenshot");
		}
		
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

}
