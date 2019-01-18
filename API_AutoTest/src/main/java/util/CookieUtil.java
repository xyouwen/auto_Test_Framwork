package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.log4j.Logger;

import pojo.CookieData;

public class CookieUtil {
	
	private static Logger logger = Logger.getLogger(CookieUtil.class);
	
	// 存放cookie的全局变量池；
	private static Map<String, CookieData> cookieDataMap = new HashMap<String, CookieData>();

	/**
	 * 通过域名从cookie的全局变量池中取出cookieDatas，并添加到header中
	 * @param URL
	 * @param requestType
	 */
	public static void autoAddCookiesToHeaderByDomainName(String URL, HttpRequestBase requestType) {
		// 1、从URL中提取域名
		String domainName = getDomainName(URL);
		
		// 2、通过域名，从cookie的全局变量池取出cookieDatas
		List<CookieData> cookieDatas = getCookiesFromGlobalByDomainName(domainName);
		
		// 3、将cookieDatas添加到header中
		for (CookieData cookieData : cookieDatas) {
			requestType.addHeader("cookie", cookieData.getCookieValue());
		}
	}

	private static String getDomainName(String URL) {
		String regex = "//(.*?)/";
		Matcher matcher = RegexUtil.getMatcher(URL, regex);
		String domainName = null;
		while(matcher.find()) {
			domainName = matcher.group(1);  // 如果http://www.baidu.com/afassfsf ，直接返回域名
			if(domainName.contains(":")) {   // 如果http://127.0.0.1:8080/afafadsf，再进行一次正则
				String regex2 = "(.*):";
				Matcher matcher2 = RegexUtil.getMatcher(domainName, regex2);
				while(matcher2.find()) {
					domainName = matcher2.group(1);
				}
			}
		}
		return domainName;
	}

	
	/**
	 * 通过域名全局变量池中获取cookie对象
	 * @param domainName
	 * @return
	 */
	private static List<CookieData> getCookiesFromGlobalByDomainName(String domainName) {
		// 1、新建一个List容器
		List<CookieData> cookieDatas = new ArrayList<CookieData>();
		
		Set<String> keys = cookieDataMap.keySet();
		for (String key : keys) {
			CookieData cookieData = cookieDataMap.get(key);
			// 2、获取cookieData的属性之一：domainName
			String dName = cookieData.getDomaiName();
			// 3、如果匹配，就往List列表里添加数据
			if (domainName.equals(dName)) {
				cookieDatas.add(cookieData);
			}
		}
		return cookieDatas;
	}

	public static void autoSaveCookiesFromResponseHeader(CloseableHttpResponse response, String URL) {
		// 1、从响应头中获取cookies
		List<CookieData> cookieDatas = getCookiesFromReponseHeader(response, URL);
		
		// 2、将cookies添加到全局变量池
		for (CookieData cookieData : cookieDatas) {
			String key = cookieData.getCookieKey();
			cookieDataMap.put(key, cookieData);
		}
	}

	/*
	 Response Headers:
		Set-Cookie:
			0:"JSESSIONID=10E00F63CC4792119FB0822AF332E552; Path=/futureloan; HttpOnly"
			1:"rememberMe=deleteMe; Path=/futureloan; Max-Age=0; Expires=Thu, 17-Jan-2019 07:07:16 GMT"
			Content-Type:"application/json;charset=UTF-8"
			Transfer-Encoding:"chunked"
			Date:"Fri, 18 Jan 2019 07:07:16 GMT"
	 */
	private static List<CookieData> getCookiesFromReponseHeader(CloseableHttpResponse response, String URL) {
		List<CookieData> cookieDatas = new ArrayList<CookieData>();
		Header[] headers = response.getHeaders("Set-Cookie");
		for (Header header : headers) {
			// 如果header非空
			if (header != null) {
				String cookieValue = header.getValue();
				// 如果cookievalue不为空
				if (!StringUtil.isEmpty(cookieValue)) {
					// 获取第一个=号的位置
					int index = cookieValue.indexOf("=");  
					 //截取 = 号等前面的字符串为cookie的key
					String cookieKey = cookieValue.substring(0, index);
					String domainName = getDomainName(URL); 
					// 新建一个空的cookieData对象
					CookieData cookieData = new CookieData();
					// 为对象赋值
					cookieData.setCookieKey(cookieKey);
					cookieData.setCookieValue(cookieValue);
					cookieData.setDomainName(domainName);
					// 为列表添加cookie对象
					cookieDatas.add(cookieData);
				}
			}
		}
		return cookieDatas;
	}

//	public static void main(String[] args) {
//		String URL = "    http://www.baidu.com/afassfsf     "; 
////		String URL = "http://127.0.0.1:8080/afafadsf "; 
//		String domainName = getDomainName(URL); 
//		System.out.println(domainName);
//	}
	
//	public static void main(String[] args) {
//		cookieDataMap.put("1", new CookieData("baidu1", "key1", "value1"));
//		cookieDataMap.put("4", new CookieData("baidu1", "key4", "value5"));
//		cookieDataMap.put("2", new CookieData("baidu2", "key2", "value2"));
//		cookieDataMap.put("3", new CookieData("baidu3", "key3", "value3"));
//		List<CookieData> cookieDatas = getCookiesFromGlobalByDomainName("baidu1");
//		for (CookieData cookieData : cookieDatas) {
//			System.out.println(cookieData);
//		}
//	}
	

}
