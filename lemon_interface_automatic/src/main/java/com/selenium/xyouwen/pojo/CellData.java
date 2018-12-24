package com.selenium.xyouwen.pojo;

/**
 * 说明：待写入Excell单元格的数据类
 * 对象属性：行号、列号、待写入的数据
 */
public class CellData {
	
	/**
	 * 行：需要写入的行号
	 */
	private String caseId;
	/**
	 * 列：需要写入的列号
	 */
	private int ColumnNum;
	/**
	 * 数据：需要写入的数据
	 */
	private String datasToWrite;
	
	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	public int getColumnNum() {
		return ColumnNum;
	}
	public void setColumnNum(int columnNum) {
		ColumnNum = columnNum;
	}
	public String getDatasToWrite() {
		return datasToWrite;
	}
	public void setDatasToWrite(String datasToWrite) {
		this.datasToWrite = datasToWrite;
	}
	@Override
	public String toString() {
		return "CellData [caseId=" + caseId + ", ColumnNum=" + ColumnNum + ", datasToWrite=" + datasToWrite + "]";
	}
	
	public CellData(String caseId, int columnNum, String datasToWrite) {
		super();
		this.caseId = caseId;
		ColumnNum = columnNum;
		this.datasToWrite = datasToWrite;
	}
	
}
