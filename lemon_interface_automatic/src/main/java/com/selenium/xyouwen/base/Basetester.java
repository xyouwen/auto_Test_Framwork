package com.selenium.xyouwen.base;

import org.testng.annotations.AfterSuite;

import com.selenium.xyouwen.util.ExcellUtil;

public class Basetester {
	
	@AfterSuite
	
	public void afterSuite() {
		ExcellUtil.batchWriteIntoExcell("src/main/resources/testcase/rest_info_2.xlsx", "target/classes/testcase/rest_info_1.xlsx", 2);
	}

}
