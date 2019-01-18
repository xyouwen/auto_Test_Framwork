package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
	
	/**
	 * 获取匹配器
	 * @param string
	 * @param regex
	 * @return
	 */
	public static Matcher getMatcher(String string, String regex) {
		// 1、编译正则表达式
		Pattern pattern = Pattern.compile(regex);
		// 2、对字符串进行模糊匹配
		Matcher matcher = pattern.matcher(string);
		return matcher;
	}
}
