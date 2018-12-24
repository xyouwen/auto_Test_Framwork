package com.selenium.xyouwen.pojo;

public class CookieData {
	/**
	 * 域名
	 */
	private String domainName;
	
	/**
	 * cookie的键
	 */
	private String cookieKey;
	
	/**
	 * cookie的值
	 */
	private String cookieValue;

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getCookieKey() {
		return cookieKey;
	}

	public void setCookieKey(String cookieKey) {
		this.cookieKey = cookieKey;
	}

	public String getCookieValue() {
		return cookieValue;
	}

	public void setCookieValue(String cookieValue) {
		this.cookieValue = cookieValue;
	}

	@Override
	public String toString() {
		return "CookieData [domainName=" + domainName + ", cookieKey=" + cookieKey + ", cookieValue=" + cookieValue
				+ "]";
	}

	public CookieData(String domainName, String cookieKey, String cookieValue) {
		super();
		this.domainName = domainName;
		this.cookieKey = cookieKey;
		this.cookieValue = cookieValue;
	}

	public CookieData() {
	}
	
}
