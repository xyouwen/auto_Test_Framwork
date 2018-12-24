package com.selenium.xyouwen.testcase;

import org.testng.annotations.Test;

import com.selenium.xyouwen.base.DriverBase;
import com.selenium.xyouwen.util.ExcellUtil;
import com.selenium.xyouwen.util.TakeScreenShotUtil;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

public class RegisterTest extends DriverBase {

	@AfterMethod
	public void afterMethod() {
		driver.navigate().refresh();
	}

	@Test(dataProvider = "failureTest_dp" )
	public void failureTest(String mobilephone, String password, String pwdConfirm, String expectedResult) throws InterruptedException {
		// 打开注册页面
		to("registerURL");
		// 直接传入页面的描述信息，即可定位元素
		getWebElement("用户名").sendKeys(mobilephone);
		getWebElement("密码").sendKeys(password);
		getWebElement("确认密码").sendKeys(pwdConfirm);
		getWebElement("注册按钮").click();
		
//		Thread.sleep(2000);
		String actualResult = getWebElement("实际结果").getText();
		assertEquals(actualResult, expectedResult);
	
	}

	@DataProvider
	public Object[][] failureTest_dp() {
		Object[][] objects = ExcellUtil.readExcell("/testcase/register.xlsx", 1);
		return objects;
	}

	@Test(dataProvider = "sucessTest_dp",enabled = false)
	public void sucessTest(String mobilephone, String password, String pwdConfirm) {
		// 打开注册页面
		to("registerURL");
		// 直接传入页面的描述信息，即可定位元素
		getWebElement("用户名").sendKeys(mobilephone);
		getWebElement("密码").sendKeys(password);
		getWebElement("确认密码").sendKeys(pwdConfirm);
		getWebElement("注册按钮").click();
		
		WebElement loginBtn = getWebElement("登陆按钮", LoginTest.class);
		assertNotNull(loginBtn);
		
	}

	@DataProvider
	public Object[][] sucessTest_dp() {
		Object[][] objects = ExcellUtil.readExcell("/testcase/register.xlsx", 2);
		return objects;
	}
}
