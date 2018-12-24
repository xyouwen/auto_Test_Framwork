package com.selenium.xyouwen.util;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import com.selenium.xyouwen.base.DriverBase;

public class TakeScreenShotUtil {
	
	/**
	 * 截图
	 * @param screenShotDirectory 截图存放的目录
	 * @return
	 */
	public static File takeScreenShot(String screenShotDirectory) {
		// 拿到driver
		WebDriver driver = DriverBase.driver;
		// Webdriver继承自RemoteWebdriver,而RemoteWebdriver又实现了TakesScreenshot这个接口，所以可以把driver强转为TakesScreenshot类型
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver; 
		// 输出截图为一个文件，拿到截图文件对象，这个对象是存在在内存中的
		File takesScreenshotFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
		
		//将内存中的这张截图复制到我们本地硬盘中去
		long time = (new Date()).getTime();  // 获取时间的毫秒值，用时间戳画保存图片的名称（因为时间是不会重复的）
		String fileName = time + ".jpg";
		File destFile = new File(screenShotDirectory+File.separator+fileName); // File.separator 文件的分隔符
		
		try {
			FileUtils.copyFile(takesScreenshotFile, destFile);  // apach.commons.io.FileUtils，调用copyFile的方法将源文件复制到目标文件中去
			return destFile;
		} catch (IOException e) {
			e.printStackTrace();
		}  return null;
	}

}
