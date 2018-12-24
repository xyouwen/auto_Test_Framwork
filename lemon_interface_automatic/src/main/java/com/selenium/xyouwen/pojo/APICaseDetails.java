package com.selenium.xyouwen.pojo;

public class APICaseDetails extends ExcelRowObject{
	
	private String IsExcute; // (是否执行)	
	private String CaseId; //(用例编号)	
	private String ApiId; //(接口编号)	
	private String RequestData; //(接口请求参数))	
	private String ExpectedReponseData; //(期望响应数据)	
	private String ActualResponseData; //(实际响应数据)	
	private String PreValidateSql; //(接口执行前的脚本验证)	
	private String PreValidateResult; //(接口执行前数据库验证结果)	
	private String AfterValidateSql; //(接口执行后的脚本验证)	
	private String AfterValidateResult; //(接口执行后数据库验证结果)private String 
	
	public String getIsExcute() {
		return IsExcute;
	}
	public void setIsExcute(String isExcute) {
		IsExcute = isExcute;
	}
	public String getCaseId() {
		return CaseId;
	}
	public void setCaseId(String caseId) {
		CaseId = caseId;
	}
	public String getApiId() {
		return ApiId;
	}
	public void setApiId(String apiId) {
		ApiId = apiId;
	}
	public String getRequestData() {
		return RequestData;
	}
	public void setRequestData(String requestData) {
		RequestData = requestData;
	}
	public String getExpectedReponseData() {
		return ExpectedReponseData;
	}
	public void setExpectedReponseData(String expectedReponseData) {
		ExpectedReponseData = expectedReponseData;
	}
	public String getActualResponseData() {
		return ActualResponseData;
	}
	public void setActualResponseData(String actualResponseData) {
		ActualResponseData = actualResponseData;
	}
	public String getPreValidateSql() {
		return PreValidateSql;
	}
	public void setPreValidateSql(String preValidateSql) {
		PreValidateSql = preValidateSql;
	}
	public String getPreValidateResult() {
		return PreValidateResult;
	}
	public void setPreValidateResult(String preValidateResult) {
		PreValidateResult = preValidateResult;
	}
	public String getAfterValidateSql() {
		return AfterValidateSql;
	}
	public void setAfterValidateSql(String afterValidateSql) {
		AfterValidateSql = afterValidateSql;
	}
	public String getAfterValidateResult() {
		return AfterValidateResult;
	}
	public void setAfterValidateResult(String afterValidateResult) {
		AfterValidateResult = afterValidateResult;
	}
	@Override
	public String toString() {
		return "APIDetails [RowNum="+super.getRowNum()+", IsExcute=" + IsExcute + ", CaseId=" + CaseId + ", ApiId=" + ApiId + ", RequestData="
				+ RequestData + ", ExpectedReponseData=" + ExpectedReponseData + ", ActualResponseData="
				+ ActualResponseData + ", PreValidateSql=" + PreValidateSql + ", PreValidateResult=" + PreValidateResult
				+ ", AfterValidateSql=" + AfterValidateSql + ", AfterValidateResult=" + AfterValidateResult + "]";
	}

}
