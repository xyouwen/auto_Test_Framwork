package com.selenium.xyouwen.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.Set;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.selenium.xyouwen.pojo.CookieData;

public class HttpUtil {
	
	// 定义一个类变量，用来管理日志
	private static Logger logger = Logger.getLogger(HttpUtil.class); 
	
	/**
	 * 功能：发送http请求
	 * @param type 请求类型
	 * @param url 请求地址
	 * @param parametersMap 请求参数
	 * @return 
	 */
	public static CloseableHttpResponse request( String type, String url, Map<String, String> parametersMap) {
		CloseableHttpResponse response = null; 
		if (type.equalsIgnoreCase("post")) {
			response = post(url, parametersMap);
		} else {
			response = get(url, parametersMap);
		}
		return response;
	}
	
	/**
	 * 功能：发送post请求
	 * @param url 请求地址
	 * @param parametersMap 请求参数
	 * @return
	 */
	private static CloseableHttpResponse post(String url, Map<String, String> parametersMap){
		CloseableHttpResponse response = null;
		try {
		// 1、创建一个客户端（类似于我们打开postman）
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		// 2、输入url地址 --->从参数中传入
		
		// 3、准备请求参数
		List<NameValuePair> parameters = prepareParams(parametersMap);
		
		// 4、选择请求类型
		HttpPost post = new HttpPost(url); 
		post.setEntity(new UrlEncodedFormEntity(parameters)); 	//将参数设置到请求体
		
		// 5、用正则表达式，从url中提取domainName
		String domainName = getDomainName(url);  
					
		// 6、自动添加cookie：从cookie全局数据池取数据，按domainName自动添加cookie到请求头中
		CookiesUtil.addCookiesToRequestHeaderByDomainNameAutomatically(post, domainName);
		
		// 7、发送请求，获取返回结果
		logger.info("正在发送post请求，请求url为：【"+url+"】；请求参数为：【"+parameters.toString()+"】");
		response = httpClient.execute(post);
		logger.info("获得的响应结果response为：【"+response+"】");
		
		// 8、自动保存cookie：从响应头中获取cookie信息，并保存到全局数据池
		CookiesUtil.getCookiesFromResponseAndAddToCookieDataMap(response, domainName);
		
		} catch (Exception e) {
			e.printStackTrace();
		} return response;
	}
	
	/**
	 * 功能：发送get请求
	 * @param url 请求地址
	 * @param parametersMap 请求参数
	 * @return
	 */
	private static CloseableHttpResponse get(String url, Map<String, String> parametersMap) {
		CloseableHttpResponse response = null;
		try {
			// 1、创建一个客户端
			CloseableHttpClient httpClient = HttpClients.createDefault();
			
			// 2、请求url从参数入
			
			// 3、准备请求参数
			List<NameValuePair> parameters = prepareParams(parametersMap);
			
			// 4、url 和参数拼接
			String parametersEncode = URLEncodedUtils.format(parameters, "Utf-8");  //将参数进行编码
			url += "?"+parametersEncode;

			// 5、选择请求类型get
			logger.info("正在发送get请求，请求数据为：【"+url+"】");
			HttpRequestBase get = new HttpGet(url);
			
			// 6、用正则表达式，从url中提取domainName
			String domainName = getDomainName(url);  
			
			// 7、自动添加cookie：从cookie全局数据池取数据，按domainName自动添加cookie到请求头中
			CookiesUtil.addCookiesToRequestHeaderByDomainNameAutomatically(get, domainName);

			// 8、发包，获取返回结果
			response = httpClient.execute(get);
			logger.info("响应结果response：【"+response+"】");
			
			// 9、自动保存cookie：从响应头中获取cookie信息，并保存到全局数据池
			CookiesUtil.getCookiesFromResponseAndAddToCookieDataMap(response, domainName);

		} catch (Exception e) {
			e.printStackTrace();
		}return response;
	}
	
	/**
	 * 功能：准备请求参数
	 * @param parametersMap Map类型请求参数
	 * @return  List<NameValuePair> 参数列表
	 */
	public static List<NameValuePair> prepareParams(Map<String, String> parametersMap) {
		// 1、新建一个NameValuePair列表
		List<NameValuePair> parametersList = new ArrayList<NameValuePair>(); 
		
		// 2、将Map转List
		// 2-1 获得parametersMap中所有的key
		Set<String> keySet = parametersMap.keySet(); 
		for (String key : keySet) {  
			// 2-2 遍历keySet，给parameters列表添加NameValuePair数据
			parametersList.add(new BasicNameValuePair(key, parametersMap.get(key)));  
		}
		return parametersList;
	}
	
	/**
	 * 功能：准备请求参数
	 * @param jsonString json格式的字符串，比如{"key":"value","key":"value","key":"value"}
	 * @return  List<NameValuePair> 参数列表
	 */
	public static List<NameValuePair> prepareParams(String jsonString) {
		// 1、新建一个Map容器
		Map<String, String> parametersMap = new HashMap<String, String>();
		
		// 2、通过解析json字符串，往Map里面添加数据
		parametersMap = (Map<String, String>) JSONObject.parse(jsonString);
		
		// 3、将Map转List
		// 3-1 新建一个NameValuePair列表
		List<NameValuePair> parametersList = new ArrayList<NameValuePair>(); 
		
		// 3-2 获得parametersMap中所有的key
		Set<String> keySet = parametersMap.keySet(); // 
		for (String key : keySet) {  
		
			//3-3 遍历keySet，给parameters列表添加NameValuePair数据
			parametersList.add(new BasicNameValuePair(key, parametersMap.get(key)));  
		}
		return parametersList;
	}
	
	/**
	 * 功能：获得所有响应头
	 * @param response
	 * @return allHeaders
	 */
	public static Header[] getAllResponseHeaders(CloseableHttpResponse response) {
		return  response.getAllHeaders();
	}
	
	/**
	 * 功能：获得响应body
	 * @param response 响应结果
	 * @return responseBody 响应体
	 */
	public static String  getResponseBody(CloseableHttpResponse response) {
		String responseBody = null;
		try {
			// 2、获得响应结果中的body
			responseBody = EntityUtils.toString(response.getEntity());
			logger.info("获得的responseBody为：【"+responseBody+"】");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseBody;
	}

	
	/**
	 * 功能：获取域名
	 * @param url
	 * @return domainName
	 */
	public static String getDomainName(String url) {

		String firstRegex = "//(.*?)/";
		Matcher matcher = RegexUtil.getMatcher(url, firstRegex);

		String domainName = null;
		while (matcher.find()) {
			domainName = matcher.group(1);
			if (domainName.contains(":")) {
				String secondRegex = "(.*):";
				Matcher matcher2 = RegexUtil.getMatcher(domainName, secondRegex);
				while (matcher2.find()) {
					domainName = matcher2.group(1);
				}
			}
		}
		return domainName;
	}
	

	public static void main(String[] args) {
		String url = "http://47.107.232.188:8080/futureloan/mvc/api/member/login?mobilephone=18201012793&pwd=123456";
		String url2 = "http://www.baidu.com/fult=134";
//		getDomainName(url2);
		testbaidu();
		login();
		getFinancialLog();

		
		System.out.println("===================================");
		Set<String> keys = CookiesUtil.cookieDataMap.keySet();
		for (String string : keys) {
			System.out.println(string+"---->"+CookiesUtil.getCookieObjectByKey(string));
		}

	}
	
	public static void testbaidu() {
		String url = "https://sp0.baidu.com/5bU_dTmfKgQFm2e88IuM_a/w.gif";
		Map<String, String> parametersMap = new HashMap<String, String>();
		parametersMap.put("q", "123456"); 
		CloseableHttpResponse response = request("get", url, parametersMap);
		List<CookieData> cookies = CookiesUtil.getCookiesFromResponse(url, response);
		
	}
	public static void getFinancialLog() {
		String url = "http://47.107.232.188:8080/futureloan/mvc/api/financelog/getFinanceLogList";
		Map<String, String> parametersMap = new HashMap<String, String>();
		parametersMap.put("memberId", "90"); 
		CloseableHttpResponse response = request("post", url, parametersMap);
		String body = getResponseBody(response);
	}
	
	public static void login() {
		String url = "http://47.107.232.188:8080/futureloan/mvc/api/member/login";
		Map<String, String> parametersMap = new HashMap<String, String>();
		parametersMap.put("mobilephone", "18201012793"); 
		parametersMap.put("pwd", "123456");
		CloseableHttpResponse response = request("post", url, parametersMap);
}
	
}
