package com.selenium.xyouwen.util;

import java.util.HashMap;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.selenium.xyouwen.base.Locator;

public class LocatorUtil {
	
	// 创建第HashMap容器（通过页面文件名称，找到该页面下所有的定位信息）
	private static HashMap<String, HashMap<String, Locator>> pageMap = new HashMap<String, HashMap<String, Locator>>();
	
	// 用静态代码块，只调用一次加载解析的过程
	static {
		loadUiInfo("/UI_Locator/UI_Locator.xml");
	}
	
	/**
	 * 通过页面名称，找到该页面下所有的定位信息
	 * @param pageName
	 * @return
	 */
	public static  HashMap<String, Locator> getPageMapByPageName(String pageName) {
		return pageMap.get(pageName);
	}
	
	/**
	 * 通过页面名称、元素描述，找到该元素所有的定位信息
	 * @param pageName
	 * @param description
	 * @return
	 */
	public static Locator getLocator(String pageName, String description) {
		return pageMap.get(pageName).get(description);
	}
	
	/**
	 * 加载xml文件信息，解析成Locator对象，并用HashMap容器保存
	 * @param xmlPath
	 */
	public static void loadUiInfo(String xmlPath) {
		
		try {
			// 创建解析器reader
			SAXReader reader = new SAXReader();
			// 读取文档，创建document对象
			Document document = reader.read(LocatorUtil.class.getResourceAsStream(xmlPath));
			// 获取根元素
			Element rootElement = document.getRootElement();
			// 获取所有子元素
			List<Element> pages = rootElement.elements("page");
			
			// 第1个map: 创建一个HashMap容器用来存放locator对象（通过描述信息，找到该元素所有的定位信息）
			HashMap<String, Locator> locatorMap = new HashMap<String, Locator>();
			
			// 遍历每个子元素
			for (Element page : pages) {
				// 获取每个页面的名称
				String pageName = page.attributeValue("name");
				// 遍历每个locator元素，得到每个locator的定位信息
				List<Element> locators = page.elements("locator");
				for (Element element : locators) {
					String by = element.attributeValue("by");
					String value = element.attributeValue("value");
					String description = element.attributeValue("description");
					// 把解析出来的数据放到Locator类这个数据载体中
					Locator locator = new Locator(by, value, description);
					// 把locator对象存放在locatorMap容器中去
					locatorMap.put(description, locator);
				}
				// 把locatorMap对象存放到pageMap容器中去
				pageMap.put(pageName, locatorMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
