package base;

import io.appium.java_client.android.AndroidDriver;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import pojo.Locator;
import pojo.Page;
import util.XmlUtil;

import java.lang.reflect.Method;
import java.util.List;

public class Base {

    public AndroidDriver<WebElement> androidDriver;

    private static Logger logger = Logger.getLogger(Base.class);

    /**
     * 智能等待查找元素
     * @param pageDesc 页面的描述信息
     * @param locatorDesc 元素的描述信息
     * @return
     */
    public WebElement getElement(String pageDesc, String locatorDesc){

        // 1、根据页面的关键字、元素的关键字，找到定位的by 和 value
        String bystr = "";
        String valueStr = "";
        List<Page> pageList = XmlUtil.getPageList();
        for (Page page : pageList) {
            String pageNameStr = page.getPageName();
            String pageDescStr = page.getPageDesc();
            if (pageDesc.equals(pageDescStr)){
                List<Locator> locatorList = page.getLocatorList();
                for (Locator locator : locatorList) {
                    String descStr = locator.getDesc();
                    if (locatorDesc.equals(descStr)){
                        bystr = locator.getBy();
                        valueStr = locator.getValue();
                    }
                }
            }else {
                logger.info("请检查输入的页面描述【"+pageDesc+"】和定位元素描述【"+locatorDesc+"】是否正确");
            }
        }

        // 2、由于普通变量不能直接在内部类中直接使用，我们使用中间final变量进行接收，然后再传值给内部类
        final String byStrFinal = bystr;
        final String valueStrFinal = valueStr;

        // 3、设置显式等待的时间
        WebDriverWait webDriverWait = new WebDriverWait(androidDriver,30);

        // 4、智能等待，查找元素
        WebElement webElement = webDriverWait.until(new ExpectedCondition<WebElement>() {
            @Override
            public WebElement apply(WebDriver input) {
//                webDriver.findElement(By.id("name"));
                WebElement webElement = null;
                try {
                    // 通过反射得到对应的by的对象
                    Class<By> clazz = By.class;
                    Method method = clazz.getMethod(byStrFinal,String.class);
                    By by = (By) method.invoke(null, valueStrFinal);

                    // 通过by对象，查找元素
                    webElement = input.findElement(by);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return webElement;
            }
        });

        return webElement;
    }
}
