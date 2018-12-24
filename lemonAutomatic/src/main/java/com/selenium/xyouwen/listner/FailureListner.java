package com.selenium.xyouwen.listner;

import java.io.File;

import org.apache.log4j.Logger;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;

import com.selenium.xyouwen.util.TakeScreenShotUtil;

/**
 * 监听失败截图
 * @author THINK
 *
 */
public class FailureListner extends TestListenerAdapter {
	private static Logger logger = Logger.getLogger(FailureListner.class);
	
	// 复写onTestFailure的方法
	@Override
	public void onTestFailure(ITestResult tr) {
		super.onTestFailure(tr);
		
		// 把截图存放至指定的screenshot目录，并按测试名称进行分类
		// 1、获取输出的目录  
		String outputDirectory = tr.getTestContext().getOutputDirectory();  // tr.getTestContext() 获取测试结果的上下文，再获取输出的目录
		// 2、对目录进行字符串截串（截取到最后一 \ 的索引位置去）
		String surefireDirectory = outputDirectory.substring(0, outputDirectory.lastIndexOf("\\"));
		// 3、获取当前test的名称
		String test = tr.getTestContext().getCurrentXmlTest().getName();
		// 4、拼串，获取截图输出的目录
		String screenShotDirectory = surefireDirectory+File.separator+"screenshot"+File.separator+test;  // File.separator 指文件的分隔符
		logger.info("正在将失败用例截图，并保存至【"+screenShotDirectory+"】目录");
		
		// 调用截图方法，得到截图的目标文件
		File screenShotFile = TakeScreenShotUtil.takeScreenShot(screenShotDirectory);
		// 得到截图的完整路径
		String absolutePath = screenShotFile.getAbsolutePath();
		logger.info("错误截图的完整路径为：【"+absolutePath+"】");
		/*
		示例说明：
		1、System.out.println(absolutePath);  会得到绝对路径：C:\Users\THINK\eclipse-workspace\web_automatic_frame\target\surefire-reports\screenshot\SeleniumUtilTest\1536043627190.jpg
		2、需要的操作：
			String imgURl = "http://127.0.0.1:7777/screenshot/SeleniumUtilTest/1535709063583.jpg";
			Reporter.log("<img src='"+imgURl+"'  hight='100' width='100' <a href='"+imgURl+"'  target='_blank'>点查查看大图</a></img>");
			实际变化的部分只是：SeleniumUtilTest/1535709063583.jpg
		3、从绝对路径中截取  SeleniumUtilTest\1535709063583.jpg，替换imgURl中变化的部分
		*/
		
		// 截取字符串    得到 C:\Users\THINK\eclipse-workspace\web_automatic_frame\target\surefire-reports
		String subStr = absolutePath.substring(0, absolutePath.indexOf("screenshot"));
		// 替换的baseurl
		String baseUrl = "http://127.0.0.1:7777/";
		// 用baseUrl替换oldStr    得到  http://127.0.0.1:7777/screenshot\SeleniumUtilTest\1535709063583.jpg
		String tempUrl = absolutePath.replace(subStr, baseUrl);
		// 把 \ 换成 /
		String imgUrl = tempUrl.replace("\\", "/");
		logger.info("错误截图的地址为：【"+imgUrl+"】"); // 结果：http://127.0.0.1:7777/screenshot/SeleniumUtilTest/1536044031985.jpg
		
		//利用reportng输出日志
		Reporter.log("<img src='"+imgUrl+"'  hight='100' width='100' ><a href='"+imgUrl+"'  target='_blank'><br>点查查看大图</a></img>");  	
	}

}
