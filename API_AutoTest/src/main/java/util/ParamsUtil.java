package util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

public class ParamsUtil {

    /*
    参数化的思路：
    1、首先添加数据到全局变量 mobilephone：13888888888
    2、设置参数化 mobilephone：${mobilephone}
    3、通过key获取value，替换参数的值
     */

    /**
     * 全局变量池
     */
    public  static Map<String, String> globalDataMap = new HashMap<String, String>();

    /**
     * 往全局变量池添加数据
     * @param key 键
     * @param value 值
     */
    public  static  void addToGlobalDatas(String key, String value){
        globalDataMap.put(key, value);
    }

    /**
     * 通过key从全局变量池中取出value
     * @param key key
     * @return value
     */
    public  static String getValueFromGlobalDatas(String key){
        return globalDataMap.get(key);
    }

    /**
     * 将参数化的值替换为普通的字符串
     * <ul>举例说明：
     * <li>参数化的字符串：{"mobilephone":"${mobilephone}","pwd":"${pwd}","regname":"${regname}"}</li>
     * <li>被替换后字符串：{"mobilephone":"13888888888","pwd":"123456","regname":"jack"}</li>
     * </ul>
     * @param str 参数化的字符串
     * @return  被替换后的普通字符串
     */
    public static  String getCommonStr(String str){
        try {
            String regex = "\\$\\{(.*?)\\}";
            Matcher matcher = RegexUtil.getMatcher(str,regex);
            while (matcher.find()){
                // 获取完整字符串，比如${mobilephone}
                String totalStr = matcher.group(0);
                // 获取完整字符串中的第一对（）里面的内容，比如mobilephone
                String key = matcher.group(1);
                // 通过key值到全局变量池中取出value，比如获取到value=13888888888
                String value = getValueFromGlobalDatas(key);
                // 用value替换完整字符串，比如用13888888888替换${mobilephone}
                str = str.replace(totalStr,value);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("全局变量池中缺少替换的数据");
        }
        return  str;
    }

    public static void main(String[] args){
        addToGlobalDatas("mobilephone","13888888888");
        addToGlobalDatas("pwd","123456");
        addToGlobalDatas("regname","jack");
        String str = "{\"mobilephone\":\"${mobilephone}\",\"pwd\":\"${pwd}\",\"regname\":\"${regname}\"}";
        System.out.println(getCommonStr(str));

    }

}
