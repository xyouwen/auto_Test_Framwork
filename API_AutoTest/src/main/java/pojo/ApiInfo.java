package pojo;

/**
 * 接口的基本信息
 * <li>apiId    接口编号</li>
 * <li>apiName  接口名称</li>
 * <li>requestType  请求方式</li>
 * <li>URL  请求地址</li>
 */
public class ApiInfo extends  ExcelRowObjects {

    /**
    * 接口编写
    */
    private String apiId;

    /**
    * 接口名称
    */
    private String apiName;

    /**
    * 请求方式
    */
    private String requestType;

    /**
    * 接口地址
    */
    private String URL;

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    @Override
    public String toString() {
        return "ApiInfo{" +
                "apiId='" + apiId + '\'' +
                ", apiName='" + apiName + '\'' +
                ", requestType='" + requestType + '\'' +
                ", URL='" + URL + '\'' +
                '}';
    }
}
