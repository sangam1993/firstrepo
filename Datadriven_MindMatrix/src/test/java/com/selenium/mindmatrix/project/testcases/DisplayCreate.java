package com.selenium.mindmatrix.project.testcases;

import java.util.Hashtable;

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

public class DisplayCreate extends BaseTest {
	SoftAssert sassert;
	String testCaseName ;
	Xls_Reader xls;

	@Test(dataProvider = "getData")
	public void displayCreate(Hashtable<String, String> data) throws InterruptedException {
		test = rep.startTest("LoginTest");
		test.log(LogStatus.INFO, data.toString());

		if (!DataUtil.isRunnable("DisplayCreate", xls) || data.get("Runmode").equals("N")) {
			test.log(LogStatus.SKIP, "Skipping the test as runmode is N");
			throw new SkipException("Skipping the test as runmode is N");

		}

		openBrowser(data.get("Browser"));
		navigate("appurl");

		boolean actualResult = doLogin(data.get("Username"), data.get("Password"));

		boolean expectedResult = false;
		if (data.get("ExpectedResult").equals("Y"))
			expectedResult = true;
		else
			expectedResult = false;

		if (expectedResult != actualResult)
			reportFail("Login Test Failed.");

		reportPass("LoginTest Passed");

		mouseMovement("setup_xpath");
		
		mouseClick("asert_xpath");
		wait(5);
		mouseClick("template_xpath");
		wait(5);
		click("PrintTemplate_xpath");

		{
			wait(5);
			if (isElementPresent("Create_xpath") && getElement("Create_xpath").isEnabled()) {
				System.out.println("passed");
				test.log(LogStatus.INFO, "Create' button is present and Enabled");

			} else {

				test.log(LogStatus.FAIL, "Please verify, Create button is not present");
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
		} catch (Error e) {
			test.log(LogStatus.FAIL, e.getMessage());

		}

		rep.endTest(test);
		rep.flush();

	}

	@DataProvider
	public Object[][] getData() {
		super.init();
		xls = new Xls_Reader(prop.getProperty("xlspath"));
		return DataUtil.getTestData(xls, "LoginTest");
	}

}
