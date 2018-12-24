package com.selenium.xyouwen.testcase;

import java.util.Map;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONObject;
import com.selenium.xyouwen.base.Basetester;
import com.selenium.xyouwen.global.Container;
import com.selenium.xyouwen.pojo.CellData;
import com.selenium.xyouwen.util.ApiUtil;
import com.selenium.xyouwen.util.HttpUtil;
import com.selenium.xyouwen.util.SQLCheckUtil;

public class ApiTestCaseProcessor extends Basetester{
	

//	preValidateSql(接口执行前的脚本验证)
//	preValidateResult(接口执行前数据库验证结果)
// afterValidateSql(接口执行后的脚本验证)	
// afterValidateResult(接口执行后数据库验证结果)

	
	@Test(dataProvider = "datas")
	public void excuteTestCase(String caseId, String apiId, String isEmpty, String requestData, String expectData, 
			String preValidateSql,  String afterValidateSql ) {
		
		// 数据验证：前置验证sql
		SQLCheckUtil.preValidata(caseId, preValidateSql);
		
		// 1、请求地址( 通过apiId获取url地址)
		String url = ApiUtil.getUrlByApiId(apiId);

		// 2、请求数据（通过解析json数据获取对象）
		Map<String, String> parametersMap = (Map<String, String>) JSONObject.parse(requestData);                         
		
		// 3、获取请求类型（通过apiId获取请求类型）
		String type = ApiUtil.getRequestTypeByApiId(apiId);
		
		// 4、执行请求，获取返回结果
		CloseableHttpResponse response = HttpUtil.request(type, url, parametersMap);
		String responseBody = HttpUtil.getResponseBody(response);
		
		// 5、将返回结果写回到Excell表格
		Container.dataToWriteList.add(new CellData(caseId, 6, responseBody));
		
		// 6、数据校验：后置校验
		SQLCheckUtil.afterValidata(caseId, afterValidateSql);
		
		// 7、断言
		System.out.println(response);
	}

	
	@DataProvider
	public Object[][] datas() {
		Object[][] objects = ApiUtil.getApiCaseDetails();
		return objects;
	}

}
