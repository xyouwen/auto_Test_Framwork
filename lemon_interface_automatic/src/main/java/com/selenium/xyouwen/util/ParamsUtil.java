package com.selenium.xyouwen.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParamsUtil {
	
	// 1、全局数据池
	public static Map<String, String> globalDatas = new HashMap<String, String>();
	
	// 2、为全局数据池添加数据
	public static void addToGlobalData(String key,String value) {
		globalDatas.put(key, value);
	}
	
	//3、从全局数据词取数据
	public static String getValueFromGlobalData(String key) {
		return globalDatas.get(key);
	}
	
	/**
	 * 正则表达式：普通参数化（例："mobilephone":"${mobilephone}"）
	 */
	public static final String COMMON_STR = "\\$\\{(.*?)\\}"; 
	
	/**
	 * 正则表达式：函数参数化 （例："mobilephone":"__MD5(123456)"）
	 */
	public static final String Function_STR = "__(\\w*?)\\(((\\w*,?)*)\\)"; 
	
	
	
	
	/**
	 * 功能：普通参数化
	 * @param str 需要被替换的字符串，比如 "{\"mobilephone\":\"${mobilephone}\",\"pwd\":\"${pwd}\",\"regname\":\"${regname}\"}"
	 * @return  被替换后的字符串，比如 {"mobilephone":"13888888888","pwd":"123456","regname":"tom"}
	 */
	public static String getCommanStr(String str) {
			// 1、新建一个匹配器
			Matcher matcher = RegexUtil.getMatcher(str, COMMON_STR);
			// 2、按正则表达式匹配
			while(matcher.find()) {
				// group(0): 表示匹配到的完整的数据，比如${mobilephone}
				String totalStr = matcher.group(0);
				// group(1): 表示匹配到的完整数据中的第一对（）里同的内容，比如mobilephone
				String param = matcher.group(1);
				// 从全局数据池中得到该变量的值
				String value = getValueFromGlobalData(param);
				// 用value替换原先str里面的字符串
				str = str.replace(totalStr, value);
			}
			// 3、把替换后的str继续用函数参数化替换
			return  getFunctionOptStr(str);
	}
	
	/**
	 * 功能：函数参数化 
	 * @param str 需要被替换的字符串，比如： "{"mobilephone":"__setMobilephone()","pwd":"__MD5(123456) ","regname":"__setNickName(aa,bb,cc)"}"
	 * @return 被替换后的字符串
	 */
	public static String getFunctionOptStr(String str) {
		
		// 5、获取方法类的字节码对象
		Class clazz = FunctionUtil.class;
		
		// 1、获得匹配器
		Matcher matcher = RegexUtil.getMatcher(str, Function_STR);
		while(matcher.find()) {
			// group(0): 表示匹配到的完整的数据，比如${random(1,100)}
			String totalStr = matcher.group(0);
			// 2、方法名称：group(1): 表示匹配到的完整数据中的第一对（）里同的内容，比如(random)
			String functionName = matcher.group(1);
			// 3、参数列表：group(2): 表示匹配到的完整数据中的第二对（）里同的内容，比如(1,100)
			String params = matcher.group(2);
			// 4、反射，调用方法，获得执行结果
			String result = null; 
			try {
				// 4-1 如果参数为空
				if (StringUtil.isEmpty(params)) {
					// 5-1 获得方法名称
					Method method = clazz.getMethod(functionName);  // functionName为方法名, 因为没有参数，所以参数传入空，同clazz.getMethod(functionName, null)
					result = (String) method.invoke(null);
				}
				// 4-2 如果参数不为空
				else {
					// 获得所有的参数
					String[] paramsArray = params.split(",");  // 将参数用逗号进行分隔，得到字符串数组
					// 获得参数个数
					int length = paramsArray.length;
					// 创建一个类型数组, 为每个参数类型赋值
					Class[] typesArray = new Class[length];
					for (int i = 0; i < typesArray.length; i++) {
						// 数组里面的每一个元素的类型都是String.class
						typesArray[i] = String.class; 
					}
					// 5-1 获得方法名称
					Method method = clazz.getMethod(functionName, typesArray); // functionName为方法名，typesArray为方法的参数
					// 5-2 反射调用该方法
					result = (String) method.invoke(null, paramsArray); // 由于调用的是静态方法，所以object为null；paramsArray为所有的参数
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 6、用执行函数后的结果，替换原字符串的内容
			str = str.replace(totalStr, result);
		}
		// 7、将替换后的字符串返回
		return str;
			
	}
	public static void main(String[] args) {
		
		// 1、往全局变量池里面添加数据
		ParamsUtil.addToGlobalData("phone", "13888888887");
		ParamsUtil.addToGlobalData("phone", "123456");
		ParamsUtil.addToGlobalData("regname", "tom");
		
		// 2、需要替换的字符串
		String string = "{\"mobilephone\":\"${phone}\",\"pwd\":\"__setNickName(aa,bb,cc)\",\"regname\":\"__setMobilephone()\"}";
		String newStr = ParamsUtil.getCommanStr(string);
//		String newStr = ParamsUtil.getFunctionOptStr(string);
		System.out.println("=============");
		System.out.println(newStr);
	}
}
