package util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

public class HttpUtil {

    private static Logger logger = Logger.getLogger(HttpUtil.class);

    /*
     * 需求： 1、请求类型post、get 2、参数化（普通参数化、函数参数化） 3、自动从响应头中保存cookie 4、手动添加header
     * 5、sql数据验证（前置验证、后置验证） 6、断言
     */

    public static CloseableHttpResponse request(String type, String URL, Map<String, String> paramsMap, Map<String, String> headersMap) {
        CloseableHttpResponse response = null;
        if (type.trim().equalsIgnoreCase("post")) {
            response = post(URL, paramsMap, headersMap);
        } else if (type.trim().equalsIgnoreCase("get")) {
            response = get(URL, paramsMap, headersMap);
        } else {
            logger.info("本框架暂只支持 post 和 get请求");
        }
        return response;
    }

    private static CloseableHttpResponse get(String URL, Map<String, String> paramsMap, Map<String, String> headersMap) {
        // TODO get
        return null;
    }

    private static CloseableHttpResponse post(String URL, Map<String, String> paramsMap, Map<String, String> headersMap) {
        CloseableHttpResponse response = null;
        try {
            // 1、新建一个发包工具
            CloseableHttpClient httpClient = HttpClients.createDefault();

            // 2、准备请求地址URL

            // 3、准备请求参数
            List<NameValuePair> parameters = changeParamsMapToNameValuePair(paramsMap);

            // 4、设置请求类型
            HttpPost post = new HttpPost(URL);
            post.setEntity(new UrlEncodedFormEntity(parameters));    //将参数设置到请求体

            // 5、手动添加header
            addHeaders(headersMap, post);

            // 6、自动添加cookie：从全局变量池中按“域名”，自动添加cookie到header中
            CookieUtil.autoAddCookiesToHeaderByDomainName(URL, post);

            // 7、发送请求，获取返回结果
            response = httpClient.execute(post);

            // 8、自动保存cookie: 从响应头自动获取cookie添加到cookie的全局数据池
            CookieUtil.autoSaveCookiesFromResponseHeader(response, URL);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // 9、返回response
        return response;
    }

    /**
     * 往请求中添加headers
     * @param headersMap 以Map格式的输入的header
     * @param request http请求（post、get……）
     */
    private static void addHeaders(Map<String, String> headersMap, HttpRequestBase request) {
        Set<String> keys = headersMap.keySet();
        for (String key : keys) {
            request.addHeader(key, headersMap.get(key));
        }
    }

    /**
     * 说明：将Map<String, String> paramsMap集合转变为List<NameValuePair>
     * @param paramsMap 以Map格式输出的参数
     * @return List<NameValuePair>
     */
    private static List<NameValuePair> changeParamsMapToNameValuePair(Map<String, String> paramsMap) {
        List<NameValuePair> pairsList = new ArrayList<NameValuePair>();
        Set<String> keys = paramsMap.keySet();
        for (String key : keys) {
            pairsList.add(new BasicNameValuePair(key, paramsMap.get(key)));
        }
        return pairsList;
    }

//	public static void main(String[] args) {
//		Map<String, String> paramsMap = new HashMap<String, String>();
//		paramsMap.put("name1", "hello1"); 
//		paramsMap.put("name2", "hello2"); 
//		paramsMap.put("name3", "hello3"); 
//		paramsMap.put("name4", "hello4"); 
//		List<NameValuePair> paramsList = changeParamsMapToNameValuePair(paramsMap);
//		for (NameValuePair nameValuePair : paramsList) {
//			System.out.println(nameValuePair);
//		}
//	}
}
