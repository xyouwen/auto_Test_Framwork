package util;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import pojo.Locator;
import pojo.Page;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XmlUtil {

    /**
     * 所有页面的信息（包括：页面名称、页面描述、元素的by、元素的value、元素的描述）
     */
    private static List<Page> pageList;

    static {
        pageList = readUILibrary();
    }

    /**
     * 获取所有页面的信息（包括：页面名称、页面描述、元素的by、元素的value、元素的描述）
     * @return
     */
    public static List<Page> getPageList() {
        return pageList;
    }

    /**
     * 解析UILibrary.xml，得到page对象列表
     * @return List<Page> pageList
     */
    private static List<Page> readUILibrary(){
        SAXReader reader = new SAXReader();
        List<Page> pageList = new ArrayList<Page>();

        try {
            // 1、获取xml文档
            InputStream inputStream = XmlUtil.class.getResourceAsStream("/UILibrary.xml");
            Document document = reader.read(inputStream);

            // 2、获取根元素 （获取到xml文件中的pages）
            Element rootElement = document.getRootElement();

            // 3、获取所有子元素（获取所有的页面及元素，获取到xml文件中所有的page）
            List<Element> pageElementList = rootElement.elements("page");

            // 4、遍历每个页面及元素的信息
            for (Element pageInfo : pageElementList) {
                // 4-1 获取页面的信息
                String pageName = pageInfo.attributeValue("activityName"); // 页面名称
                String pageDesc = pageInfo.attributeValue("pageDesc"); // 页面描述
                List<Element> locatorInfoList = pageInfo.elements("locator"); // 页面元素信息

                List<Locator> locatorList = new ArrayList<Locator>();
                for (Element locatorInfo : locatorInfoList) {
                    // 4-2 获取元素的信息
                    String by = locatorInfo.attributeValue("by"); // 元素的定位方式
                    String value = locatorInfo.attributeValue("value"); // 元素定位的值
                    String desc = locatorInfo.attributeValue("desc"); // 元素的描述信

                    // 4-3 利用locator信息生成locator对象
                    Locator locator = new Locator(by,value,desc);
                    locatorList.add(locator);
                }
                Page page = new Page(pageName,pageDesc,locatorList);
                pageList.add(page);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageList;
    }

    public static void main(String[] args){
         testReadUILibrary();
    }

    public static void testReadUILibrary(){
        List<Page> pageList = getPageList();
        for (Page page : pageList) {
            System.out.println("页面名称："+page.getPageName());
            System.out.println("页面描述：" + page.getPageDesc());
            List<Locator> locatorList = page.getLocatorList();
            for (Locator locator : locatorList) {
                System.out.println("by:" + locator.getBy());
                System.out.println("value:" + locator.getValue());
                System.out.println("元素描述：" + locator.getDesc());
            }
        }
    }
}
