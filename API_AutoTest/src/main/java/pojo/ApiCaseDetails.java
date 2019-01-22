package pojo;

/**
 * 接口用例的详细信息
 * <li>用例编号</li>
 * <li>接口编写</li>
 * <li>是否执行</li>
 * <li>header</li>
 * <li>body</li>
 * <li>依赖的case</li>
 * <li>依赖的数据</li>
 * <li>依赖的字段</li>
 * <li>期望响应数据</li>
 * <li>实际响应数据</li>
 * <li>前置数据验证sql</li>
 * <li>前置数据验证结果</li>
 * <li>后置数据验证sql</li>
 * <li>后置数据验证结果</li>
 */
public class ApiCaseDetails extends  ExcelRowObjects {

    /**
     * 用例编号
     */
    private String caseId; //用例编号

    /**
     * 接口编号
     */
    private String apiId; //接口编号

    /**
     * 是否执行
     */
    private String isExcute; //是否执行

    /**
     * 请求头
     */
    private String header ;

    /**
     * 请求body
     */
    private String requestBody; //接口请求参数

    /**
     * 依赖的case
     */
    private String dependCase ;

    /**
     * 依赖的数据
     */
    private String dependResponseData  ;

    /**
     * 依赖的字段
     */
    private String dependField ;

    /**
     * 期望响应数据
     */
    private String expectedReponseData;

    /**
     * 实际响应数据
     */
    private String actualResponseData;
    /**
     * 前置数据验证sql（接口执行前的数据验证）
     */
    private String preValidateSql;

    /**
     * 前置数据验证结果
     */
    private String preValidateResult;

    /**
     * 后果数据验证sql
     */
    private String afterValidateSql;

    /**
     * 后置数据验证的结果
     */
    private String afterValidateResult;

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public String getIsExcute() {
        return isExcute;
    }

    public void setIsExcute(String isExcute) {
        this.isExcute = isExcute;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public String getDependCase() {
        return dependCase;
    }

    public void setDependCase(String dependCase) {
        this.dependCase = dependCase;
    }

    public String getDependResponseData() {
        return dependResponseData;
    }

    public void setDependResponseData(String dependResponseData) {
        this.dependResponseData = dependResponseData;
    }

    public String getDependField() {
        return dependField;
    }

    public void setDependField(String dependField) {
        this.dependField = dependField;
    }

    public String getExpectedReponseData() {
        return expectedReponseData;
    }

    public void setExpectedReponseData(String expectedReponseData) {
        this.expectedReponseData = expectedReponseData;
    }

    public String getActualResponseData() {
        return actualResponseData;
    }

    public void setActualResponseData(String actualResponseData) {
        this.actualResponseData = actualResponseData;
    }

    public String getPreValidateSql() {
        return preValidateSql;
    }

    public void setPreValidateSql(String preValidateSql) {
        this.preValidateSql = preValidateSql;
    }

    public String getPreValidateResult() {
        return preValidateResult;
    }

    public void setPreValidateResult(String preValidateResult) {
        this.preValidateResult = preValidateResult;
    }

    public String getAfterValidateSql() {
        return afterValidateSql;
    }

    public void setAfterValidateSql(String afterValidateSql) {
        this.afterValidateSql = afterValidateSql;
    }

    public String getAfterValidateResult() {
        return afterValidateResult;
    }

    public void setAfterValidateResult(String afterValidateResult) {
        this.afterValidateResult = afterValidateResult;
    }

    @Override
    public String toString() {
        return "ApiCaseDetails{" +
                "caseId='" + caseId + '\'' +
                ", apiId='" + apiId + '\'' +
                ", isExcute='" + isExcute + '\'' +
                ", header='" + header + '\'' +
                ", requestBody='" + requestBody + '\'' +
                ", dependCase='" + dependCase + '\'' +
                ", dependResponseData='" + dependResponseData + '\'' +
                ", dependField='" + dependField + '\'' +
                ", expectedReponseData='" + expectedReponseData + '\'' +
                ", actualResponseData='" + actualResponseData + '\'' +
                ", preValidateSql='" + preValidateSql + '\'' +
                ", preValidateResult='" + preValidateResult + '\'' +
                ", afterValidateSql='" + afterValidateSql + '\'' +
                ", afterValidateResult='" + afterValidateResult + '\'' +
                '}';
    }
}
