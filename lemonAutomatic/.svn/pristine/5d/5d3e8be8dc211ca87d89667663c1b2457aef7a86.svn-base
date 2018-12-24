package com.selenium.xyouwen.util;

import java.io.InputStream;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;

public class ExcellUtil {

	/**
	 * 功能：把excell表格读取为二维数组
	 * 
	 * @param excllPath：excell的路径
	 * @param sheetIndex：sheet序号
	 * @param startRow：需要读取数据的起始行，从第1行起
	 * @param endRow：需要读取数据的结尾行
	 * @param startVol：需要读取数据的起始列，从第1列起
	 * @param endVoll：需要读取数据的结尾列
	 */
	public static Object[][] readExcell(String excllPath, int sheetIndex, int startRow, int endRow, int startVol,
			int endVoll) {
		Object[][] objects = new Object[endRow - startRow + 1][endVoll - startVol + 1];
		try {
			// 1、获取一个workbook对象
			// 流对象与file对象传入路径的区别：
			// 流对象的路径： “/testcase/测试用例.xlsx"
			// file对象的路径："/src/main/resources/testcase/测试用例.xlsx"
			InputStream inp = ExcellUtil.class.getResourceAsStream(excllPath);
			Workbook workbook = WorkbookFactory.create(inp);

			// 2、获取一个sheet表格
			org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(sheetIndex - 1);

			// 3、遍历需要读取的行数
			for (int i = startRow; i <= endRow; i++) {
				// 遍历需要读取的行数
				Row row = sheet.getRow(i - 1);
				for (int j = startVol; j <= endVoll; j++) {
					// 遍历需要读取的列数
					Cell cell = row.getCell(j - 1, MissingCellPolicy.CREATE_NULL_AS_BLANK); // 单元格为空的政策：把null变为空
					cell.setCellType(CellType.STRING); // 设置单元格类型
					String cellvalue = cell.getStringCellValue(); // 获取单元格的数据
					/*
					 * 第2行： 第一列：[0][0] 第二列：[0][1] 第三列：[0][2] 第3行(2+1)： 第一列：[1][0] 第二列：[1][1]
					 * 第三列：[1][2]
					 */
					objects[i - startRow][j - startVol] = cellvalue;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objects;
	}

	/**
	 * 功能：把Excell表格数据读取为二维数组 说明：1、跳过表头，从第2行开始读取，只读取测试数据
	 * 2、Excell数据设计时，只保留表头和有效数据部分，其它行列不要插入空格，否则读取表的行列数会出错
	 * 
	 * @param excllPath
	 * @param sheetIndex
	 * @return
	 */
	public static Object[][] readExcell(String excllPath, int sheetIndex) {
		Object[][] objects = null;
		try {
			InputStream inp = ExcellUtil.class.getResourceAsStream(excllPath);
			Workbook workbook = WorkbookFactory.create(inp);

			org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(sheetIndex - 1);
			// 找出表格有多少行
			int rowNum = sheet.getLastRowNum();
			// 建立一个二维数组的容器
			objects = new Object[rowNum][];

			for (int i = 1; i <= rowNum; i++) {
				// 获取每一行的数据
				Row row = sheet.getRow(i);

				// 建立一个一维数据容器，用来存放每一行的数据
				int colNUm = row.getLastCellNum();
				Object[] singRowDatas = new Object[colNUm];

				for (int j = 0; j < colNUm; j++) {
					// 获取每一行的数据
					Cell cell = row.getCell(j, MissingCellPolicy.CREATE_NULL_AS_BLANK); // 单元格为空的政策：把null变为空
					cell.setCellType(CellType.STRING); // 设置单元格类型
					String cellvalue = cell.getStringCellValue(); // 获取单元格的数据
					singRowDatas[j] = cellvalue;
				}
				objects[i - 1] = singRowDatas;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objects;
	}

	public static void main(String[] args) {
		Object[][] objects = readExcell("/testcase/register.xlsx", 1);
		for (Object[] objects2 : objects) {
			for (Object objects3 : objects2) {
				System.out.print(objects3 + " ,");
			}
			System.out.println();
		}
	}

}
