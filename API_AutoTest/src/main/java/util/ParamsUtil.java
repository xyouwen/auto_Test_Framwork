package util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

public class ParamsUtil {


    /**
     * 全局变量池
     */
    public  static Map<String, String> globalDatas = new HashMap<String, String>();

    /**
     * 往全局变量池添加数据
     * @param key 键
     * @param value 值
     */
    public  static  void addToGlobalDatas(String key, String value){
        globalDatas.put(key, value);
    }

    /**
     * 通过key从全局变量池中取出value
     * @param key key
     * @return value
     */
    public  static String getValueFromGlobalDatas(String key){
        return globalDatas.get(key);
    }

    /**
     * 将参数化的字符串替换为普通的字符串
     * <ul>举例说明：
     * <li>参数化的字符串：{"mobilephone":"${mobilephone}","pwd":"${pwd}","regname":"${regname}"}</li>
     * <li>被替换后字符串：{"mobilephone":"13888888888","pwd":"123456","regname":"jack"}</li>
     * </ul>
     * @param str 参数化的字符串
     * @return  被替换后的普通字符串
     */
    public static  String getCommonStr(String str){
        try {
            // 1、正则表达式
            String regex = "\\$\\{(.*?)\\}";

            // 2、获得匹配器
            Matcher matcher = RegexUtil.getMatcher(str,regex);
            while (matcher.find()){

                // 3、需要替换的字符串：获取完整字符串，比如${mobilephone}
                String totalStr = matcher.group(0);

                // 4、获取key，再通过key从全局变量池中取值
                // 获取完整字符串中的第一对（）里面的内容，比如mobilephone
                String key = matcher.group(1);
                // 通过key值到全局变量池中取出value，比如获取到value=13888888888
                String value = getValueFromGlobalDatas(key);

                //5、替换：用value替换原字符串中参数化的部分，比如用13888888888替换${mobilephone}
                str = str.replace(totalStr,value);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("全局变量池中缺少替换的数据");
        }
        return  getFunctionOptStr(str);
    }

    /**
     * 用函数执行的结果来替换原字符串中的函数部分
     * ul>举例说明：
     *<li>参数化的字符串：{"mobilephone":"__setMobilephone()","pwd":"__MD5(123456) ","regname":"__setNickName(aa,bb,cc)"}
     *<li>被替换后字符串：{"mobilephone":"13888888888","pwd":"dfsdfsdfdfdf ","regname":"aabbcc"}
     * </ul>
     * @param str 需要被替换的字符串
     * @return  String 被替换后的字符串
     */
    private   static  String getFunctionOptStr(String str){

        //5、获取需要反射调用的字节码文件
        Class clazz = FunctionUtil.class;

        // 1、正则表达式
        String regex = "__(\\w*?)\\(((\\w*,?)*)\\)";

        // 2、获取匹配器
        Matcher matcher = RegexUtil.getMatcher(str, regex);
        while (matcher.find()){

            // 3、获取需求替换的字符串

            // 3-1 获取完整字符串，比如 __setNickName(aa,bb,cc)
            String totalStr = matcher.group(0);
            // 3-2 获取函数名，比如 setNickName
            String funName = matcher.group(1);
            // 3-3 获取参数，比如 aa,bb,cc
            String params = matcher.group(2);

            // 4、反射执行方法，替换原字符串里的函数部分
            // 反射执行的条件：方法名称、参数列表、各参数类型

            String result = null;
            // 4-1 如果参数为空
            try {
                if (StringUtil.isEmpty(params)){
                    // 通过方法名获得指定的方法
                    Method method = clazz.getMethod(funName);
                    // 通过方法反射调用对象来执行方法
                    result = (String) method.invoke(null);
                }
                // 4-2 如果参数不为空
                else{
                    // 参数列表
                    String[] paramsList = params.split(",");
                    // 参数类型：为所有参数指定参数类型
                    int length = paramsList.length;
                    Class[] paramsTypes = new Class[length];
                    for (int i = 0; i < length; i++) {
                        paramsTypes[i] = String.class;  // 所有参数的类型都设置为String
                    }
                    // 方法：通过指定名称，参数类型获得方法
                    Method method = clazz.getMethod(funName,paramsTypes);
                    // 反射执行
                    result = (String) method.invoke(null, paramsList);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 5、用结果进行替换
            str = str.replace(totalStr,result);
        }
        return  str;
    }

    public static void main(String[] args){
//        testGetCommonStr();
        testGetFuntionStr();
    }

    private static void testGetCommonStr() {
        addToGlobalDatas("mobilephone","13888888888");
        addToGlobalDatas("pwd","123456");
        addToGlobalDatas("regname","jack");
        String str = "{\"mobilephone\":\"${mobilephone}\",\"pwd\":\"${pwd}\",\"regname\":\"${regname}\"}";
        System.out.println(getCommonStr(str));
    }

    private  static  void testGetFuntionStr(){
        String str = "{\"mobilephone\":\"__setMobilephone()\",\"pwd\":\"__MD5(123456) \",\"regname\":\"__setNickName(aa,bb,cc)\"}";
        System.out.println(getFunctionOptStr(str));
    }

}
