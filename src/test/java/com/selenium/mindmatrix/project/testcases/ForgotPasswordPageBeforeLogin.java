package com.selenium.mindmatrix.project.testcases;

import java.util.Hashtable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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

public class ForgotPasswordPageBeforeLogin extends BaseTest {
	SoftAssert sassert;
	String testCaseName;
	Xls_Reader xls;

	@Test(dataProvider = "getData")
	public void testForgotPasswordPageBeforeLogin(Hashtable<String, String> data) {
		test = rep.startTest("ForgotPasswordPageBeforeLogin");
		test.log(LogStatus.INFO, data.toString());
		if (!DataUtil.isRunnable("TestForgotPasswordPageBeforeLogin" , xls) || data.get("Runmode").equals("N")) {
			test.log(LogStatus.SKIP, "Skipping the test as runmode is N");
			throw new SkipException("Skipping the test as runmode is N");
		}
		openBrowser(data.get("Browser"));
		navigate("appurl");
		wait(5);
		linkverification("Forgot password?");
		//click("forgotpasswrd_xpath");
		verifyText("forgotpasswrd_xpath", "Forgot Password");
		linkverification("Ready to log in?");
		placeholder("usernameforgotpw_xpath", "Username");
		Toolfield("usernamefpicon_xpath", "usernamefpicontext_xpath", "Username for password reset.");
		submit("sendemail_xpath");

	}

	public void Toolfield(String xpath, String tolxpath, String Expected) {

		getElement(xpath).click();
		if (isClickable(xpath)) {

			// Actions builder=new Actions(driver);
			WebElement username_tooltip = getElement(tolxpath);
			String name = username_tooltip.getText();
			if (name.equals(Expected)) {
				test.log(LogStatus.PASS, Expected + " " + "is appered sucessfully");
			} else {
				test.log(LogStatus.FAIL, Expected + " " + "is not appered.");
			}
		} else {
			test.log(LogStatus.FAIL, Expected + "is not Clickable.");
		}
	}

	public void submit(String submit) {

		boolean display = getElement(submit).isDisplayed();
		boolean isenable = getElement(submit).isEnabled();
		if (isElementPresent(submit) && display == true && isenable == true) {

			test.log(LogStatus.PASS, " Login button is  enabled");
		} else {
			test.log(LogStatus.FAIL, "Visibility of Login is not enabled");
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
