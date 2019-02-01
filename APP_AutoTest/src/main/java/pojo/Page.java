package pojo;

import java.util.List;

public class Page {
    /**
     * 页面名称
     */
    private String pageName;

    /**
     * 页面描述
     */
    private String pageDesc;

    /**
     * 页面元素
     */
    private List<Locator> locatorList;

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getPageDesc() {
        return pageDesc;
    }

    public void setPageDesc(String pageDesc) {
        this.pageDesc = pageDesc;
    }

    public List<Locator> getLocatorList() {
        return locatorList;
    }

    public void setLocatorList(List<Locator> locatorList) {
        this.locatorList = locatorList;
    }

    @Override
    public String toString() {
        return "Page{" +
                "pageName='" + pageName + '\'' +
                ", pageDesc='" + pageDesc + '\'' +
                ", locatorList=" + locatorList +
                '}';
    }

    public Page(String pageName, String pageDesc, List<Locator> locatorList) {
        this.pageName = pageName;
        this.pageDesc = pageDesc;
        this.locatorList = locatorList;
    }
}
