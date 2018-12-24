package com.selenium.xyouwen.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap.KeySetView;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;

import com.alibaba.fastjson.JSONObject;
import com.selenium.xyouwen.global.Container;
import com.selenium.xyouwen.pojo.CellData;
import com.selenium.xyouwen.pojo.SQLChecker;
import com.selenium.xyouwen.pojo.SQLCheckerResult;

public class SQLCheckUtil {
	
	/**
	 * 列号：sql前置验证结果写回的列
	 */
	private static final int COLUM_OF_PRE_VALIDATA_SQL_REUSULT = 8;
	/**
	 * 列号：sql后置验证结果要写回的列
	 */
	private static final int COLUM_OF_AFTER_VALIDATA_SQL_REUSULT = 10;

	// 定义一个类变量，用来管理日志
	private static Logger logger = Logger.getLogger(SQLCheckUtil.class);
	
	/**
	 * 功能：对指定测试用例的json格式的sql语句进行验证
	 * @param sqlStrOfJsonType json格式的sql字符串 for instance:
	 *  [{"key":"value", "key":"sqlString", "key":[{"key":"value"}]},
	 *  {"key":"value", "key":"value", "key":[{"key":"value","key":"value"}]},
	 *  {"key":"value", "key":"value", "key":[{"key":"value","key":"value"},{"key":"value","key":"value"},{"key":"value","key":"value"}]}]
	 * @return sqlCheckerResultsStr 所有sql执行完之后的结果，以json格式字符串返回
	 */
	public static String getSQLCheckerResult(String sqlStrOfJsonType) {
		// 新建一个列表容器，用来存放执行结果
		List<SQLCheckerResult> sqlCheckerResults = new ArrayList<SQLCheckerResult>();
		// fastjson把json数组解析成列表（SQLChecker.class是我们自定义SQLchecker类）
		List<SQLChecker> sqlCheckers = JSONObject.parseArray(sqlStrOfJsonType, SQLChecker.class);
		for (SQLChecker sqlChecker : sqlCheckers) {
			// 编号
			String no = sqlChecker.getNo();
			// sql语句
			String sql = sqlChecker.getSql();
			// 预期结果
			List<LinkedHashMap<String, String>> expectedResultList = sqlChecker.getExpectedResult();
			String expectedResult = JSONObject.toJSONString(expectedResultList);
			logger.info("sql编号:【"+no+"】, 数据验证的预期结果为：" + expectedResult);
			// 实际结果
			List<LinkedHashMap<String, String>> actualResultList = JDBCUtil.query(sql);
			String actualResult = JSONObject.toJSONString(actualResultList);
			logger.info("sql编号:【"+no+"】, 数据验证的实际结果为：" + actualResult);
			
			// 实际结果和期望结果进行比较
			if (expectedResult.equalsIgnoreCase(actualResult)) {
				sqlCheckerResults.add(new SQLCheckerResult(no, actualResultList, "pass"));
			} else {
				sqlCheckerResults.add(new SQLCheckerResult(no, actualResultList, "failure"));
			}
		}
		// 要写回的结果
		String sqlCheckerResultsStr = JSONObject.toJSONString(sqlCheckerResults);
		logger.info("数据验证的写回结果为：" + sqlCheckerResultsStr);
		logger.info("=========================================");
		return sqlCheckerResultsStr;
	}
	
	/**
	 * 功能：sql数据验证，并将验证结果写回到excell表格
	 * @param caseId 行号（即用例编号）
	 * @param colum 列号（即需要写入数据的列号）
	 * @param validateSql 输入的sql语句
	 */
	public static void validataSql(String caseId, int colum, String validateSql) {
		if (! StringUtil.isEmpty(validateSql)) {
			String validataResult = SQLCheckUtil.getSQLCheckerResult(validateSql);
			Container.dataToWriteList.add(new CellData(caseId, colum, validataResult));
		}
	}
	
	/**
	 * 功能: sql前置验证
	 * @param caseId 行号（即用例编号）
	 * @param preValidateSql 前置验证sql语句
	 */
	public static void preValidata(String caseId, String preValidateSql) {
		validataSql(caseId, COLUM_OF_PRE_VALIDATA_SQL_REUSULT, preValidateSql);
	}
	
	/**
	 * 功能：后置验证
	 * @param caseId 行号（即用例编号）
	 * @param afterValidateSql 后置验证的sql语句
	 */
	public static void afterValidata(String caseId, String afterValidateSql) {
	validataSql(caseId, COLUM_OF_AFTER_VALIDATA_SQL_REUSULT, afterValidateSql);
	}

}
