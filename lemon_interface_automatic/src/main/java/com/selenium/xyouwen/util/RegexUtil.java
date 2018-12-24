package com.selenium.xyouwen.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
	
	/**
	 * 功能：获得匹配器
	 * @param str 需要被模糊匹配的字符口中
	 * @param regex 正则表达式
	 * @return matcher 模糊匹配器对象
	 */
	public static Matcher getMatcher(String str, String regex) {

		// 1、编译正则表达式
		Pattern pattern = Pattern.compile(regex);
		// 2、对字符串进行模糊匹配
		Matcher matcher = pattern.matcher(str);
		return matcher;
	} 
	
	public static void main(String[] args) {
		
	}

}
