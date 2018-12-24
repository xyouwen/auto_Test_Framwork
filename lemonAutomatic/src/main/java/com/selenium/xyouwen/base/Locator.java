package com.selenium.xyouwen.base;

public class Locator {
	
	/**
	 * by的方式
	 */
	private String by;
	
	/**
	 * by的值
	 */
	private String value;
	
	/**
	 * 定位元素的描述
	 */
	private String description;
	
	public String getBy() {
		return by;
	}
	public void setBy(String by) {
		this.by = by;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "Locator [by=" + by + ", value=" + value + ", description=" + description + "]";
	}
	
	public Locator(String by, String value, String description) {
		super();
		this.by = by;
		this.value = value;
		this.description = description;
	}
	
	
}
