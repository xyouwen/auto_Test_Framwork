package com.selenium.xyouwen.pojo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SQLCheckerResult {
	
	/**
	 * sql编号
	 */
	private String no;
	/**
	 * 执行sql后的实际结果
	 */
	private List<LinkedHashMap<String, String>> actualResult;
	/**
	 * 状态：是否通过（pass  or failure)
	 */
	private String status;
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public List<LinkedHashMap<String, String>> getActualResult() {
		return actualResult;
	}
	public void setActualResult(List<LinkedHashMap<String, String>> actualResult) {
		this.actualResult = actualResult;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "SQLCheckerResult [no=" + no + ", actualResult=" + actualResult + ", status=" + status + "]";
	}
	public SQLCheckerResult(String no, List<LinkedHashMap<String, String>> actualResult, String status) {
		super();
		this.no = no;
		this.actualResult = actualResult;
		this.status = status;
	}
}
