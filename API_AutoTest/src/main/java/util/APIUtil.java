package util;

import pojo.ApiCaseDetails;
import pojo.ApiInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class APIUtil {

    // 2、建Map格式的数据，方便某一行对象

    // 2-1 apiInfoMap: key(接口编号), value(接口基本信息对象)
    private static Map<String, ApiInfo> apiInfoMap = new HashMap<String, ApiInfo>();

    // 2-2 apiCaseDetailsMap: key(用例编号)， value（用例详细信息对象）
    private static Map<String, ApiCaseDetails> apiCaseDetailsMap = new HashMap<String, ApiCaseDetails>();

    /**
     * 通过apiId获取接口基本信息的对象
     * @param apiId 接口编号
     * @return apiInfo对象
     */
    public static ApiInfo getApiInfo(String apiId){
        return apiInfoMap.get(apiId);
    }

    /**
     * 通过caseId获取用例详细的对象
     * @param caseId 用例编号
     * @return  apiCase对象
     */
    public static ApiCaseDetails getApiCase(String caseId){
        return apiCaseDetailsMap.get(caseId);
    }

    private static List<ApiInfo> apiInfoList;
    private static List<ApiCaseDetails> apiCaseDetailsList;

    static {

        // 1、读取Excel表格，将每一行封装成一个对象，以对象列表形式保存

        // 1-1 接口基本信息
        apiInfoList = (List<ApiInfo>) ExcelUtil.readExcelRowsAsObjects("/testCase/rest_info_1.xlsx", 1, ApiInfo.class);

        // 2、用例详细信息
        apiCaseDetailsList = (List<ApiCaseDetails>) ExcelUtil.readExcelRowsAsObjects("/testCase/rest_info_1.xlsx", 2,ApiCaseDetails.class);

        // 3、往Map里添加数据

        // 3-1 往apiInfoMap添加数据
        for (ApiInfo apiInfo : apiInfoList) {
            String apiId = apiInfo.getApiId();
            apiInfoMap.put(apiId,apiInfo);
        }

        // 3-2 往apiCaseDetailsMap添加数据
        for (ApiCaseDetails apiCaseDetails: apiCaseDetailsList) {
            String caseId = apiCaseDetails.getCaseId();
            apiCaseDetailsMap.put(caseId, apiCaseDetails);
        }
    }

    /**
     * 获取请求地址
     * @param apiId 接口编号
     * @return 请求地址URL
     */
    public static String getURl(String apiId){
        return getApiInfo(apiId).getURL();
    }

    /**
     * 获取请求类型
     * @param apiId 接口编号
     * @return 请求类型Type
     */
    public static String getType(String apiId){
        return getApiInfo(apiId).getRequestType();
    }

    /**
     * 通过caseId获取用例详细信息的对象
     * @param caseId 用例编号
     * @return Object[][]
     */
    public static Object[][] getApiCaseDetails(String caseId){
        int size = apiCaseDetailsList.size();
        Object[][] objects = new Object[size][8];
        for (int i = 0; i < size; i++) {
            ApiCaseDetails apiCaseDetails = apiCaseDetailsList.get(i);
            objects[i][0] = apiCaseDetails.getCaseId(); // 用例编号
            objects[i][1] = apiCaseDetails.getApiId(); // 接口编号
            objects[i][2] = apiCaseDetails.getIsExcute(); // 是否执行
            objects[i][3] = apiCaseDetails.getHeader(); // 请求头
            objects[i][4] = apiCaseDetails.getRequestBody(); // 请求体
            objects[i][5] = apiCaseDetails.getExpectedReponseData(); // 期望响应结果
            objects[i][6] = apiCaseDetails.getPreValidateSql(); // 前置sql验证
            objects[i][7] = apiCaseDetails.getAfterValidateSql(); // 后置sql验证
        }
        return objects;
    }
}
