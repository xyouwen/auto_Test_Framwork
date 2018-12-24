package com.selenium.xyouwen.util;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriver.SystemProperty;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.selenium.xyouwen.base.DriverBase;

public class SeleniumUtil {
	/*
	 * ----------------------------------------------------------------------------
	 * 封装思想：
	 * 1、封装自定义的常量
	 * 2、封装webdriver的常量
	 * 2、if分支抽取方法
	 * 3、beforesuite 在suite运行之前启动一次
	 * ----------------------------------------------------------------------------
	 */
	
	// 定义一个类变量，用来管理日志
	private static Logger logger = Logger.getLogger(SeleniumUtil.class); 
	
	// ie浏览器
	private static final String  IE_BROWSER_NAME = "ie";
	// chrome浏览器
	private static final String  CHROME_BROWSER_NAME = "chrome";
	// firefox浏览器
	private static final String  FIREFOX_BROWSER_NAME = "firefox";
	
	/**
	 * 功能：启动浏览器
	 * @param browserName：浏览器名称
	 * @param installPath：浏览器安装路径
	 * @param driverPath：浏览器驱动所在路径
	 * @param seleniumVerison：selenium版本
	 * @return
	 */
	public static WebDriver getWebdriver(String browserName, String installPath, String driverPath, String seleniumVerison) {
		WebDriver driver = null;
		if(browserName.equalsIgnoreCase(IE_BROWSER_NAME)) {
			driver = getIEDriver(driverPath);
		}else if(browserName.equalsIgnoreCase(CHROME_BROWSER_NAME)){
			driver = getChromeDriver(driverPath);
		}else if(browserName.equalsIgnoreCase(FIREFOX_BROWSER_NAME)) {
			driver = getFirefoxDriver(installPath, driverPath, seleniumVerison);
		}else {
			driver = printException();
		}
		return driver;
	}
	
	/**
	 * 浏览器启动异常，控制台输出
	 * @return
	 */
	private static WebDriver printException() {
		WebDriver driver;
		logger.info("浏览器启动异常: \n1、请检查浏览器的名称输入是否正确、浏览器驱动路径是否正确 \n2、目前只支持ie、chrome、firefox三个浏览器的启动 ");
		driver = null;
		return driver;
	}
	
	/**
	 * 获取firefoxDriver
	 * @param installPath
	 * @param driverPath
	 * @param seleniumVerison
	 * @return
	 */
	private static WebDriver getFirefoxDriver(String installPath, String driverPath, String seleniumVerison) {
		WebDriver driver;
		// 把浏览器的安装路径设为全局变量
		System.setProperty(SystemProperty.BROWSER_BINARY,installPath);
		driver = new FirefoxDriver();
		if(seleniumVerison.equalsIgnoreCase("3.x")) {
			System.setProperty(SystemProperty.BROWSER_BINARY, installPath);
		    System.setProperty(GeckoDriverService.GECKO_DRIVER_EXE_PROPERTY, driverPath);
		    driver = new FirefoxDriver();
		}
		return driver;
	}
	
	/**
	 * 获取chromeDriver
	 * @param driverPath
	 * @return
	 */
	private static WebDriver getChromeDriver(String driverPath) {
		WebDriver driver;
		ChromeOptions options = new ChromeOptions();
		// 忽略掉提示警告语“chrome浏览器正在受自动化程序控制”
		options.addArguments("disable-infobars");
		// 把chrome浏览器的驱动路径设置为全局变量
		System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY,driverPath); 
		driver = new ChromeDriver(options);
		return driver;
	}
	
	/**
	 * 获取ieDriver
	 * @param driverPath
	 * @return
	 */
	private static WebDriver getIEDriver(String driverPath) {
		WebDriver driver;
		// 把浏览器的驱动路径设为全局变量
		System.setProperty(InternetExplorerDriverService.IE_DRIVER_EXE_PROPERTY, driverPath); 
		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		// 赋予驱动能力1：忽略安全区域设置
		desiredCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		// 赋予驱动能力2：忽略浏览器缩放
		desiredCapabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
		driver = new InternetExplorerDriver(desiredCapabilities);
		return driver;
	}
}
