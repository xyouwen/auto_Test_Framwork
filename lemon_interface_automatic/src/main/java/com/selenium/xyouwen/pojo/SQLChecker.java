package com.selenium.xyouwen.pojo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SQLChecker {
	/**
	 * sql编号
	 */
	private String no;
	/**
	 * sql语句
	 */
	private String sql;
	/**
	 * 预期结果
	 * 说明：把期望结果用有序的map列表来保存，方便和实际结果进行比对
	 */
	private List<LinkedHashMap<String, String>> expectedResult;
	
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public List<LinkedHashMap<String, String>> getExpectedResult() {
		return expectedResult;
	}
	public void setExpectedResult(List<LinkedHashMap<String, String>> expectedResult) {
		this.expectedResult = expectedResult;
	}
	@Override
	public String toString() {
		return "SQLChecker [no=" + no + ", sql=" + sql + ", expectedResult=" + expectedResult + "]";
	}
}
