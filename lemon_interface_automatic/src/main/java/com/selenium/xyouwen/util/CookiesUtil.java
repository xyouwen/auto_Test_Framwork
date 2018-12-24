package com.selenium.xyouwen.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.log4j.Logger;

import com.selenium.xyouwen.pojo.CookieData;

public class CookiesUtil {
	
	private static Logger logger = Logger.getLogger(CookiesUtil.class);
	
	// 新建一个map，作为存放cookies的全局数据池, key为cookieData的key,value为cookieData对象本身
	public static Map<String,CookieData> cookieDataMap = new HashMap<String,CookieData>();

	/**
	 * 功能：往cookie的全局数据池添加数据
	 * @param key
	 * @param cookieData
	 */
	public static void addToCookieDataMap(String key, CookieData cookieData) {
		cookieDataMap.put(key, cookieData);
	}
	
	/**
	 * 功能：从cookie的全局数据池中拿到cookie对象
	 * @param key
	 * @return
	 */
	public static CookieData getCookieObjectByKey(String key) {
		return cookieDataMap.get(key);
	}
	
	/**
	 * 功能：通过domainName拿到相应的cookie对象
	 * @param url 请求地址
	 * @return List<CookieData> cookieDataList
	 */
	public static List<CookieData> getCookieObjectsByDomainName(String domainName) {
		// 1、新建一个列表用来存放数据
		 List<CookieData> cookieDataList = new ArrayList<CookieData>();
		 
		 // 2、将Map转List
		Set<String> keys = cookieDataMap.keySet();
		for (String key : keys) {
			CookieData cookieData = cookieDataMap.get(key);
			
			// 2-1 通过cookieData对象，获取域名
			String cookieDomainName = cookieData.getDomainName();
			
			// 2-2 如果匹配成功，就往List列表传入数据
			if(cookieDomainName.equalsIgnoreCase(domainName)) {
				cookieDataList.add(cookieData);
			}
		}
		return cookieDataList;
	}
	
	/**
	 * 功能：按domainName自动从cookie全局数据池获取cookie，并将cookie添加到请求头中
	 * @param requestTye 请求类型 post/get
	 * @param domainName 域名
	 */
	public static void addCookiesToRequestHeaderByDomainNameAutomatically(HttpRequestBase requestTye, String domainName) {
		// 根据domainName拿到相应的cookie对象
		List<CookieData> cookieObjects = CookiesUtil.getCookieObjectsByDomainName(domainName);
		for (CookieData cookieData : cookieObjects) {
			// 通过cookie对象拿到cookie的值
			String cookieValue = cookieData.getCookieValue();
			logger.info("正在向请求头中添加cookie，cookieValue:【"+cookieValue+"】");
			// 向请求头中添加cookie
			requestTye.addHeader("cookie", cookieValue);
		}
	}
	
	/**
	 * 功能：从response响应头中获取cookies，将添加到cookie的全局变量池
	 * @param response
	 * @param domainName
	 */
	public static void getCookiesFromResponseAndAddToCookieDataMap(CloseableHttpResponse response, String domainName) {
		// 8-1 从响应头中获得cookies
		List<CookieData> cookieList = getCookiesFromResponse(domainName, response);
		// 8-2 将List转Map，向cookie全局数据池添加数据
		for (CookieData cookieData : cookieList) {
			String cookieKey = cookieData.getCookieKey();
			logger.info("正在向addToCookieDataMap添加数据，cookieKey：【"+cookieKey+"】，cookieData:【"+cookieData+"】");
			CookiesUtil.addToCookieDataMap(cookieKey, cookieData);
		}
	}
	
	/**
	 * 功能：从响应结果中自动获得cookies
	 * 
	 * @param url      请求地址
	 * @param response 响应结果
	 * @return List<CookieData> cookieDataList
	 */
	public static List<CookieData> getCookiesFromResponse(String domainName, CloseableHttpResponse response) {
		
		// 5、新建一个列表，用来存放cookie
		List<CookieData> cookieDataList = new ArrayList<CookieData>();
		
		// 1、从返回的请求头中获得需要写入cookie的数据
		Header[] headers = response.getHeaders("Set-Cookie");
		for (Header header : headers) {
			if (header != null) {

				// 2、新建一个cookie对象，用来保存cookie的信息
				CookieData cookieData = new CookieData();

				// 3、获取cookie所需要的信息
				// 3-1 域名(访问哪个网站留下的的cookie)，从参数中传入

				// 3-2 cookie的value
				String cookieValue = header.getValue();

				// 3-3 cookie的key（获取value第一个=号左边的字符串作为key）
				if (!StringUtil.isEmpty(cookieValue)) {
					// 获取第一个 = 号的位置
					int index2 = cookieValue.indexOf("=");
					// 截取 =  号前面的字符串
					String cookieKey = cookieValue.substring(0, index2);
					
					// 4、为cookie对象的属性赋值
					cookieData.setDomainName(domainName);
					cookieData.setCookieKey(cookieKey);
					cookieData.setCookieValue(cookieValue);
				}
				// 5 为cookie列表添加对象
				cookieDataList.add(cookieData);
			}
		}
		return cookieDataList;
	}

	public static void main(String[] args) {
		cookieDataMap.put("bd1", new CookieData("47.107.232.188", "bd1", "123456"));
		cookieDataMap.put("bd2", new CookieData("47.107.232.188", "bd2", "12345678"));
		cookieDataMap.put("bd3", new CookieData("www.baidu.com", "bd3", "123456789"));
		
		String url =  "http://47.107.232.188/futureloan/mvc/api/member/login?mobilephone=18201012793&pwd=123456";
		List<CookieData> cookieDatas = getCookieObjectsByDomainName(url);
		for (CookieData cookieData : cookieDatas) {
			System.out.println(cookieData);
		}
		
		 List<CookieData> cookieDataList = new ArrayList<CookieData>();
		 for (CookieData cookieData : cookieDataList) {
			
			 System.out.println(cookieData);
		}
	}



}
