package com.selenium.xyouwen.pojo;

// abstract抽象类：偏向于模版设计思想，避免子类设计的随意性
// interface 接口：偏向于能力、功能
// 这个ExcellRowObject类，没有实际的意义，可以用来当模版用，所以将其定义为abstract抽象类

/**
 * 把Excel的行抽象成对象
 * @author xyouwen
 */
public abstract class ExcelRowObject {
	
	/**
	 * 行号
	 */
	private int rowNum;

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}
	
	
}
