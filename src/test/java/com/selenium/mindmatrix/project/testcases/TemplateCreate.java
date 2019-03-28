package com.selenium.mindmatrix.project.testcases;

import java.awt.List;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
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

public class TemplateCreate extends BaseTest {
	SoftAssert sassert;
	String homePage = "http://www.zlti.com";
	String url = "";
	HttpURLConnection huc = null;
	int respCode = 200;
	String testCaseName = "TemplateCreate";
	Xls_Reader sss;
	java.util.List<WebElement> lst = new ArrayList<WebElement>();

	@BeforeMethod
	public void initilization() {
		init();
		sassert = new SoftAssert();

	}
@Test
public void brokenlink()
{ 
	test = rep.startTest("TemplateCreate");
	openBrowser("Chrome");
driver.get("https://www.google.com/");
java.util.List<WebElement> links = driver.findElements(By.tagName("a")); 
//To print the total number of links
System.out.println("Total links are "+links.size()); 
//used for loop to 
for(int i=0; i<links.size(); i++) {
String element = links.get(i).getText();
if(!element.equals(null))
{


verifyLinkActive(element);
}}
	
		
	}
	


public static void verifyLinkActive(String linkUrl)
{
    try 
    {
       URL url = new URL(linkUrl);
       
       HttpURLConnection httpURLConnect=(HttpURLConnection)url.openConnection();
       
       httpURLConnect.setConnectTimeout(3000);
       
       httpURLConnect.connect();
       
       if(httpURLConnect.getResponseCode()==200)
       {
           System.out.println(linkUrl+" - "+httpURLConnect.getResponseMessage());
        }
      if(httpURLConnect.getResponseCode()==HttpURLConnection.HTTP_NOT_FOUND)  
       {
           System.out.println(linkUrl+" - "+httpURLConnect.getResponseMessage() + " - "+ HttpURLConnection.HTTP_NOT_FOUND);
        }
    } catch (Exception e) {
       
    }
} 

	

	
	//@Test(dataProvider = "getData")
	public void template_case05(Hashtable<String, String> data) {
		test = rep.startTest("TemplateCreate");
		if (data.get("Runmode").equals("N") || !DataUtil.isRunnable(testCaseName, sss)) {
			test.log(LogStatus.INFO, "Test case skip");
			throw new SkipException("Test case is skipped because Runable value is N");
		}
		openBrowser(data.get("Browser"));
		driver.get(data.get("Url"));
		doLogin(data.get("Username"), data.get("Password"));
		wait(5);
		mouseMovement("setup_xpath");
		mouseClick("asert_xpath");
		mouseClick("template_xpath");
		wait(5);
		click("PrintTemplate_xpath");
		click("Create_xpath");
		click("Go_xpath");

		if (isElementPresent("info_xpath") && isElementPresent("data_xpath") && isElementPresent("prineditor_xpath")) {

			test.log(LogStatus.INFO, "All wizard are verified under Create respective template ");
			if (isElementPresent("newtemp_xpath") && getElement("newtemp_xpath").isDisplayed()) {
				test.log(LogStatus.INFO, "Info wizard is present and opened sucessfully");
			} else {
				test.log(LogStatus.FAIL, "Info wizard is present and opened sucessfully");
			}
			driver.findElement(By.xpath("//*[@id='wizardNext']/div[1]/i")).click();
			if (isElementPresent("img_xpath")) {
				test.log(LogStatus.INFO, "Data Wizard is opened sucessfully");
			} else {
				test.log(LogStatus.FAIL, "Data wizard is not opened sucessfuly");
			}
			driver.findElement(By.xpath("//*[@id='wizardNext']/div[1]/i")).click();
			wait(5);
			if (driver.getPageSource().contains("Print Editor")) {
				test.log(LogStatus.INFO, "Print Editor wizard is Enable and showing properly");
			} else {
				test.log(LogStatus.FAIL, "Failed,Have issue on print Editor  Wizard ");
			}
			
		
			/*
			 * lst=driver.findElements(By.tagName("li")); for (WebElement option : lst) { if
			 * (option.getText().equals("Info")) { System.out.println("jhwdjkhkjqwdh");
			 * break; } }
			 */

		} else {
			System.out.println("test case are failed");
			reportFail("All wizards are not present please verify from screenshot");
		}

	}

	@AfterMethod
	public void quit() {
		try {
			sassert.assertAll();
	
			
		} catch (Error e) {
			test.log(LogStatus.FAIL, e.getMessage());
		}


		rep.flush();
		rep.endTest(test);

	}

	@DataProvider
	public Object[][] getData() {
		super.init();
		sss = new Xls_Reader(prop.getProperty("xlspath"));
		return DataUtil.getTestData(sss, testCaseName);
	}
}