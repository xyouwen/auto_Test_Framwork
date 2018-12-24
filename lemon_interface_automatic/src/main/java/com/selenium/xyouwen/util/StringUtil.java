package com.selenium.xyouwen.util;

public class StringUtil {
	
	/**
	 * 功能：判断是否为空
	 * @param string
	 * @return
	 */
	public static boolean isEmpty(String string) {
		// “     ”这种类型的空字符串，因为无法判断到底有多少个空格，所以可以将一个空格替换为空，然后再去和空字符串去比较
		return (string==null) || (string.replace(" ", "").equals(""));  
	}
	
	public static void main(String[] args) {
		String str1 = null;
		String str2 = "";
		String str3 = "    ";
		String str4 = "   1    ";
		System.out.println(isEmpty(str1));
		System.out.println(isEmpty(str2));
		System.out.println(isEmpty(str3));
		System.out.println(isEmpty(str4));
	}
	
}
