package com.selenium.xyouwen.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.selenium.xyouwen.pojo.APICaseDetails;
import com.selenium.xyouwen.pojo.ApiInfo;

public class ApiUtil {
	
	// 新建一个apiInfoMap，键值对为apiId（接口编号）：apiinfo（接口信息）
	public static Map<String, ApiInfo> apiInfoMap = new HashMap<String, ApiInfo>();  // 由于map要全局访问，所以把apiInfoMap设为全局变量
	
	// 新建一个apiCaseDetailMap,键值对为：caseId（接口用例编号）：apiCaseDetail（接口用例详细信息）
	public static Map<String, APICaseDetails> apiCaseDetailMap = new HashMap<String, APICaseDetails>();
	
	// 存放所有apiInfo（接口信息）的容器
	private static List<ApiInfo> apiInfoList;
	
	// 存放所有apiCaseDetail（接口测试用例详细信息）的容器
	private static List<APICaseDetails> apiCaseDetailList;
	
	// 放到static块中，块中的代码只运行一次，提升性能
	static {
		
		// 1、将Excell数据解析对象列表
		
		// 1-1 apiInfo列表（接口编号、接口名称、请求方式、请求地址）
		apiInfoList = (List<ApiInfo>) ExcellUtil.readExcell("/testcase/rest_info_1.xlsx", 1, ApiInfo.class);
		
		// 1-2 apiCaseDetails（用例编号、是否执行、请求参数、期望结果、实际结果、执行前数据校验、执行后数据校验）
		apiCaseDetailList = (List<APICaseDetails>) ExcellUtil.readExcell("/testcase/rest_info_1.xlsx", 2, APICaseDetails.class);
		
		// 给apiInfoMap添加成员
		for (ApiInfo apiInfo: apiInfoList) {
			apiInfoMap.put(apiInfo.getApiId(), apiInfo);
		}
		
		// 给apiCaseDetailMap 添加成员
		for (APICaseDetails apiCaseDetails : apiCaseDetailList) {
			apiCaseDetailMap.put(apiCaseDetails.getCaseId(), apiCaseDetails);
		}
	}

	/**
	 * 功能：通过apiId获取Url地址
	 * @param apiId 接口的id编号
	 * @return
	 */
	public static String getUrlByApiId(String apiId) {
		// 通过apiId从apiInfoMap中获取apiInfo对象
		ApiInfo apiInfo = apiInfoMap.get(apiId);
		return apiInfo.getUrl();
	}

	/**
	 * 功能：通过apiId获取请求类型
	 * @param apiId 接口的id编号
	 * @return
	 */
	public static String getRequestTypeByApiId(String apiId) {
		// 通过apiId从apiInfoMap中获取apiInfo对象
		ApiInfo apiInfo = apiInfoMap.get(apiId);
		return apiInfo.getType();
	}
	
	/**
	 * 功能：将对象转为二维数组（与dataprovider结果使用，为测试方法传入参数）
	 * @return 返回二维数组 Object[][]
	 */
	public static Object[][] getApiCaseDetails(){
		// 1、获取apiCaseDetail中的测试用例的数量（即Excell中除表头的行数）
		int size = apiCaseDetailList.size();
		// 2、新建一个二维数组用来接收对象(二维数组的行数，即列表的size；二维数组的列数，即我们需要传入方法中的个数）
		Object[][] objects = new Object[size][7];
		//3 、遍历列表，为二维数组赋值
		for (int i = 0; i < size; i++) {
			// 获取某个对象
			APICaseDetails apiCaseDetail = apiCaseDetailList.get(i);
			// 为二维数组赋值
			objects[i][0] =apiCaseDetail.getCaseId(); // 用例编号
			objects[i][1] =apiCaseDetail.getApiId(); // 接口编号
			objects[i][2] =apiCaseDetail.getIsExcute(); // 是否执行
			objects[i][3] =apiCaseDetail.getRequestData(); // 请求参数
			objects[i][4] =apiCaseDetail.getExpectedReponseData(); // 期望响应结果
			objects[i][5] =apiCaseDetail.getPreValidateSql(); // 前置sql验证
			objects[i][6] =apiCaseDetail.getAfterValidateSql(); // 后置sql验证
		}
		return objects;
	}
	
	/**
	 * 功能：通过caseId获取测试用列的所有详细信息
	 * @param CaseId 用例编号
	 * @return APICaseDetails 接口测试用例的所有详细信息
	 */
	public static APICaseDetails getApiCaseDetailsByCaseId(String caseId) {
		return apiCaseDetailMap.get(caseId);
	}
	
	public static void main(String[] args) {
		Object[][] objects = getApiCaseDetails();
		for (Object[] objects2 : objects) {
			for (Object object : objects2) {
				System.out.print(object+",");
			}System.out.println();
			System.out.println("================");
		}
	}
}
