package com.selenium.xyouwen.pojo;

public class ApiInfo extends ExcelRowObject{

	/**
	 * 接口id
	 */
	private String apiId;
	/**
	 * 用例名称
	 */
	private String apiName;
	/**
	 * 请求类型：post 或 get
	 */
	private String type;
	/**
	 * 请求地址
	 */
	private String url;
	
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "ApiInfo [apiId=" + apiId + ", apiName=" + apiName + ", type=" + type + ", url=" + url + "]";
	}
	
}
