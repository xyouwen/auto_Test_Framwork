package com.selenium.xyouwen.base;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.selenium.xyouwen.util.LocatorUtil;
import com.selenium.xyouwen.util.PropertiesUtil;
import com.selenium.xyouwen.util.SeleniumUtil;

public class DriverBase {

	public static WebDriver driver; // 这里要定义成static类的属性
	private static Logger logger = Logger.getLogger(DriverBase.class); // 定义一个类变量，用来管理日志

	/**
	 * 创建driver
	 * @param browserName
	 * @param installPath
	 * @param driverPath
	 * @param seleniumVerison
	 */
	@BeforeSuite
	@Parameters(value = { "browserName", "installPath", "driverPath", "seleniumVerison" })
	public void beforeSuite(String browserName, String installPath, String driverPath, String seleniumVerison) {
		logger.info("正在启动【"+browserName+"】浏览器");
		driver = SeleniumUtil.getWebdriver(browserName, installPath, driverPath, seleniumVerison);
	}

	/**
	 * 关闭释放资源
	 * @throws InterruptedException
	 */
	@AfterSuite
	public void afterSuite() throws InterruptedException {
		Thread.sleep(3000);
		logger.info("正在释放资源");
		driver.quit();
	}

	/**
	 * 智能等待获取单个元素
	 * 
	 * @param timeOutInSeconds 等待时间（单位：秒）
	 * @param by               定位方式
	 * @return
	 */
	public WebElement getWebElement(final By by, long timeOutInSeconds) {
		
		logger.info("正在以【"+by+")】的方式查找元素");
		
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		WebElement element = wait.until(new ExpectedCondition<WebElement>() {
			public WebElement apply(WebDriver input) {
				WebElement element = input.findElement(by);
				return element;
			}
		});
		return element;
	}

	/**
	 * 获取单个元素，默认等待5秒
	 * 
	 * @param by 定位方式
	 * @return
	 */
	public WebElement getWebElement(By by) {
		return getWebElement(by, 5);
	}

	/**
	 * 智能等待获取多个元素
	 * 
	 * @param by               定位方式
	 * @param timeOutInSeconds 超时时间
	 * @return
	 */
	public List<WebElement> getWebElements(final By by, long timeOutInSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		List<WebElement> elements = wait.until(new ExpectedCondition<List<WebElement>>() {
			public List<WebElement> apply(WebDriver input) {
				List<WebElement> elements = input.findElements(by);
				return elements;
			}
		});
		return elements;
	}

	/**
	 * 智能等待获取多个元素，默认等待时间为5秒
	 * @param by 定位方式
	 * @return
	 */
	public List<WebElement> getWebElements(By by) {
		return getWebElements(by, 5);
	}

	/**
	 * 通过locator来查找元素
	 * 
	 * @param locator          通过LocatorUtil来获得定位器
	 * @param timeOutInSeconds 超时时间（单位：秒）
	 * @return
	 */
	public WebElement getWebElement(Locator locator, long timeOutInSeconds) {

		// 获取by的方式
		final String byStr = locator.getBy();
		// 获取by的值
		final String value = locator.getValue();
		
		// 输出日志
		logger.info("正在以【"+byStr+".(\""+value+"\")】的方式查找元素");

		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		WebElement element = wait.until(new ExpectedCondition<WebElement>() {
			public WebElement apply(WebDriver input) {
				By by = null;
				try {
					// 拿到字节码对象
					Class<By> clazz = By.class;
					// 获取到方法名：id,className,name...等
					Method byMethod = clazz.getMethod(byStr, String.class); // 第一个参数：方法名； 第二个参数：该方法的形参类型
					// 反向调用这个by方法
					by = (By) byMethod.invoke(null, value); // 第一个参数： 要调用的方法的名字所隶属的对象实体 第二个参数：方法的参数值
				} catch (Exception e) {
					e.printStackTrace();
				}
				WebElement element = input.findElement(by);
				return element;
			}
		});
		return element;
	}

	/**
	 * 通过locator来查找元素，默认等待时间为5秒
	 * 
	 * @param locator 通过LocatorUtil来获得定位器
	 * @return
	 */
	public WebElement getWebElement(Locator locator) {
		return getWebElement(locator, 5);
	}
	
	/**
	 * 获取元素
	 * @param description 元素的描述信息
	 * @param timeOutInSeconds 等待时间
	 * @param 元素所在页面对应的测试类的字节码对象clazz（比如：RegisterTest.class）
	 *	注意：xml文件中的pageNme信息必须为对应测试类的Qualified Name
	 * @return
	 */
	public WebElement getWebElement(String description, long timeOutInSeconds, Class<?> clazz) {

		// 用反射机制，自动获取页面名称
		HashMap<String, Locator> locatorMap = LocatorUtil.getPageMapByPageName(clazz.getName());
		Locator locator = locatorMap.get(description);

		// 获取by的方式
		final String byStr = locator.getBy();
		// 获取by的值
		final String value = locator.getValue();
		
		// 输出日志
		logger.info("正在以【"+byStr+".(\""+value+"\")】的方式，查找【"+description+"】的元素");
		
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		WebElement element = wait.until(new ExpectedCondition<WebElement>() {
			public WebElement apply(WebDriver input) {
				By by = null;
				try {
					// 拿到字节码对象
					Class<By> clazz = By.class;
					// 获取到方法名：id,className,name...等
					Method byMethod = clazz.getMethod(byStr, String.class); // 第一个参数：方法名； 第二个参数：该方法的形参类型
					// 反向调用这个by方法
					by = (By) byMethod.invoke(null, value); // 第一个参数： 要调用的方法的名字所隶属的对象实体 第二个参数：方法的参数值
				} catch (Exception e) {
					e.printStackTrace();
				}
				WebElement element = input.findElement(by);
				return element;
			}
		});
		return element;
	}
	
	/**
	 * 获取元素（从当前页面）
	 * @param description 元素的描述信息
	 * @return
	 */
	public WebElement getWebElement(String description) {
		return getWebElement(description, 5, this.getClass());
	}
	
	/**
	 * 获取元素（从其它页面）
	 * @param description 元素的描述信息
	 * @param clazz  元素所在页面对应的测试类的字节码对象clazz（比如：RegisterTest.class）
	   	注意：xml文件中的pageNme信息必须为对应测试类的Qualified Name
	 * @return
	 */
	public WebElement getWebElement(String description, Class<?> clazz) {
		return getWebElement(description, 5, clazz);
	}
	
	/**
	 * 打开网页
	 * @param propertyKey url配置文件的key（例如：registerURL=/lmcanon_web_auto/mng/register.html )
	 */
	public void to(String propertyKey) {
		String url = PropertiesUtil.getURL("baseURL") + PropertiesUtil.getURL(propertyKey);
		logger.info("正在打开【url="+url+"】的网页");
		driver.get(url);
	}
}
