package com.selenium.xyouwen.util;

import java.util.Properties;

public class PropertiesUtil {
	
	private static Properties urlProperties = new Properties();
	
	static {
		loadUrlProperties("/url.properties");
	}

	/**
	 * 加载url配置文件
	 * @param pathOfUrlProperties 配置文件的流路径
	 */
	private static void loadUrlProperties(String pathOfUrlProperties) {
		try {
			urlProperties.load(PropertiesUtil.class.getResourceAsStream(pathOfUrlProperties));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 从配置文件中通过key获取URL
	 * @param key 配置文件的key
	 * @return
	 */
	public static String getURL(String key) {
		return urlProperties.getProperty(key);
	}

}
