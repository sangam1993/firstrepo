package com.selenium.mindmatrix.project.testcases;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Hashtable;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
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

public class PreLoginVerification extends BaseTest {
	SoftAssert sassert;
	Xls_Reader sss = new Xls_Reader("D:\\New folder\\ReadingExcel.xlsx");
	DataUtil dd = new DataUtil();

	@BeforeMethod
	public void intil() {
		init();
		sassert = new SoftAssert();
	}

	@Test()
	public void verificationLogin() {

		test = rep.startTest("PreLogin Functionality verification");

		openBrowser("Chrome");
		driver.get("https://dvl-master.amp.vg/login");

		Username("email_xpath");
		password("password_xpath");
		submit("loginbutton_xpath");
		// forget("Forget_xpath");
		Register("register_xpath");
		AllLinkverification();
		// Remember("checkboxver_xpath");
	}

	public void AllLinkverification() {
		test = rep.startTest("All href link verification");
		List<WebElement> links = driver.findElements(By.tagName("a"));

		System.out.println("Total links are " + links.size());

		for (int i = 0; i < links.size(); i++) {

			WebElement ele = links.get(i);

			String url = ele.getAttribute("href");

			verifyLinkActive(url);

		}
	}

	public void verifyLinkActive(String linkUrl) {

		try {
			URL url = new URL(linkUrl);

			HttpURLConnection httpURLConnect = (HttpURLConnection) url.openConnection();

			httpURLConnect.setConnectTimeout(3000);

			httpURLConnect.connect();

			if (httpURLConnect.getResponseCode() == 200) {
				test.log(LogStatus.INFO, linkUrl + " - " + httpURLConnect.getResponseMessage());

			}
			if (httpURLConnect.getResponseCode() == HttpURLConnection.HTTP_NOT_FOUND) {
				test.log(LogStatus.INFO, linkUrl + " - " + httpURLConnect.getResponseMessage() + " - "
						+ HttpURLConnection.HTTP_NOT_FOUND);

			}
		} catch (Exception e) {

		}
	}

	public void Username(String username) {
		test = rep.startTest("Usernamefield verification");
		if (isElementPresent(username)) {
			
			test.log(LogStatus.INFO, " your Username field is verified ");
		} else {
			test.log(LogStatus.FAIL, " Email  Box is  not verified");
		}
	}

	public void password(String password) {
		test = rep.startTest("Password verification");
		if (isElementPresent(password)) {
			
			test.log(LogStatus.INFO, " your Password field is verified ");
		} else {
			test.log(LogStatus.FAIL, " password  field is  not verified");
		}
	}

	public void submit(String submit) {
		test = rep.startTest("Submit button verification");
		boolean display = getElement(submit).isDisplayed();
		boolean isenable = getElement(submit).isEnabled();
		if (isElementPresent(submit) && display == true && isenable == true) {
		
			test.log(LogStatus.INFO, " your Submit field is verified ");
		} else {
			test.log(LogStatus.FAIL, " Submit Button  is  not verified");
		}
	}

	/*
	 * public void forget(String forget) { if (isElementPresent(forget)) { test =
	 * rep.startTest("Forget paswordLink verification"); test.log(LogStatus.INFO,
	 * " your Forget Password field is verified "); } else {
	 * test.log(LogStatus.FAIL, " Forget password  field is  not verified"); } }
	 */

	public void Register(String register) {
		test = rep.startTest("Register Link verification");
		if (isElementPresent(register)) {
			
			test.log(LogStatus.INFO, " your Register Link is verified ");
		} else {
			test.log(LogStatus.FAIL, " Register Link  is  not verified");
		}
	}

	/*
	 * public void Remember(String Remember) { if
	 * (getElement(Remember).isSelected()) { test =
	 * rep.startTest("Remember checkbox verification"); test.log(LogStatus.INFO,
	 * " your Remember checkbox is  verified"); System.out.println("verified"); }
	 * else { test.log(LogStatus.FAIL, " Remember checkbox field is  not verified");
	 * } }
	 */

	@AfterMethod
	public void quit() {
		try {
			sassert.assertAll();
		} catch (Error e) {
			test.log(LogStatus.FAIL, e.getMessage());
		}
		rep.endTest(test);
		rep.flush();
		if (driver != null)
			driver.quit();

	}

}
