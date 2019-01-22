package com.selenium.xyouwen.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;

import com.selenium.xyouwen.global.Container;
import com.selenium.xyouwen.pojo.APICaseDetails;
import com.selenium.xyouwen.pojo.CellData;
import com.selenium.xyouwen.pojo.ExcelRowObject;

public class ExcellUtil {
	
	// 定义一个类变量，用来管理日志
	private static Logger logger = Logger.getLogger(HttpUtil.class);
	
	private static InputStream inputStream = null;
	private static OutputStream outputStream = null;
	private static Workbook workbook = null;

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
			Sheet sheet = workbook.getSheetAt(sheetIndex - 1);

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
	 * 功能：把Excell表格数据读取为二维数组 
	 * 说明：1、跳过表头，从第2行开始读取，只读取测试数据
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

			Sheet sheet = workbook.getSheetAt(sheetIndex - 1);
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
	
	@Deprecated    //假如这个方法升级或者不再用了，可以用deprecated对它进行标注
	/**
	 * 功能：通过匹配caseId，将数据写入到指定的单元格
	 * @param excellPath 文件路径（传入编译后的target目录下的路径，如“target/test-classes/testcase/[文件名]”
	 * @param sheetNum 工作表编号（1-based）
	 * @param caseId case编号
	 * @param columnNum 行号
	 * @param contentToWrite 需要写入的内容
	 */
	public static void wirteIntoExcell(String excellPath, int sheetNum, String caseId, int columnNum, String response) {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		Workbook workbook = null;
		try {
				// 1、将Excell文件载入内存
//				inputStream = ExcellUtil.class.getResourceAsStream(excellPath);  因为要传入指定路径下的文件，所以不能用这种形式载入文件
				inputStream = new FileInputStream(new File(excellPath));

				//2、获取一个workbook工作薄
				workbook = WorkbookFactory.create(inputStream);
				// 3、获取工作薄中的一个工作表sheet
				Sheet sheet = workbook.getSheetAt(sheetNum-1);
				// 4、通过caseId找到需要写入数据的行，通过columnNum找到需要写入数据的单元格
				int lastRowNum = sheet.getLastRowNum();
				Cell cellToBeWritten = null;
				for (int i = 1; i <= lastRowNum; i++) {               //跳过表头，从表格的第2行开始遍历
					// 得到一行
					Row row = sheet.getRow(i);
					// 获取当前行的第一个单元格
					Cell firstCell = row.getCell(0,MissingCellPolicy.CREATE_NULL_AS_BLANK);
					// 定义第一个单元格的格式
					firstCell.setCellType(CellType.STRING);
					// 获取第一个单元格的值
					String firstCellValue  = firstCell.getStringCellValue();
					// 把第一个单元格的值和caseId进行比对,找到需要写入数据的行
					if (firstCellValue.equals(caseId)) {
						//通过columnNum找到需要写入数据的单元格
						cellToBeWritten = row.getCell(columnNum-1, MissingCellPolicy.CREATE_NULL_AS_BLANK);
						// 5、写入数据
						cellToBeWritten.setCellType(CellType.STRING);
						logger.info("正在向用例编号为：【"+caseId+"】的测试用例写入返回数据，返回数据为：【"+response+"】");
						cellToBeWritten.setCellValue(response);
						break; // 如果匹配上了，就结束循环，节省性能
					}
				}
				// 6、输出流，输出写入后的文件
				outputStream = new FileOutputStream(new File(excellPath));
				workbook.write(outputStream);
			} catch (Exception e) {
				e.printStackTrace();
			}finally {               
				// 释放关闭资源：因为inputstream、outputstream、workbook这种资源在内存里面相当稀缺，所以要即时的释放资源
				closeResources(inputStream, outputStream, workbook);
			}
	}

	/**
	 * 功能：释放资源
	 * @param inputStream 输入流
	 * @param outputStream 输出流
	 * @param workbook 工作薄
	 */
	public static void closeResources(InputStream inputStream, OutputStream outputStream, Workbook workbook) {
		if (inputStream != null) {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (outputStream != null) {
			try {
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(workbook != null) {
			try {
				workbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 功能：将Excell的每一行数据按指定类封装成一个对象，将解析出来的数据用List列表存储起来
	 * @param excellPath 
	 * @param sheetIndex
	 * @param clazz 指定类（比如ApiInfo类、ApiCaseDetails类）
	 * @return List<? extends Object> 返回一个对象列表
	 */
	public static List<? extends ExcelRowObject> readExcell(String excellPath, int sheetIndex, Class<? extends ExcelRowObject> clazz) {
		//  5、新建一个列表，用来存放每一行的数据（每一行封装成一个对象）
		List< ExcelRowObject> excellDataList = new ArrayList<ExcelRowObject>();
		try {
			// 1、新建一个workbook
			InputStream inp = ExcellUtil.class.getResourceAsStream(excellPath);
			Workbook workbook = WorkbookFactory.create(inp);
			
			// 2、新建一个sheet
			Sheet sheet = workbook.getSheetAt(sheetIndex - 1);

			// 3、获取表头（表字段）的内容，将其存放在一个数组容器里
			//3-1 找到第一行
			Row firstRow = sheet.getRow(0);
			// 3-2 找到最后一列
			int lastCellNum = firstRow.getLastCellNum();
			// 3-3 建立一个数组容器，用来存放"表字段"的名称
			String[] filedArray = new String[lastCellNum];
			// 3-4 遍历表头，获取表字段的值
			for (int i = 0; i < lastCellNum; i++) {
				// 获取每一个单元格的数据
				Cell cell = firstRow.getCell(i, MissingCellPolicy.CREATE_NULL_AS_BLANK);
				// 设置单元格的格式
				cell.setCellType(CellType.STRING);
				// 获取表字段的值
				String cellValue = cell.getStringCellValue();
				// 对表字段的值进行截串，for example：IsExcute(是否执行)	，即从第一个字符开始，到第一个（ 结束，进行截取
				String filedName = cellValue.substring(0, cellValue.indexOf("("));
				// 将表字段添加到数组容器里
				filedArray[i] = filedName;
			}
			
			// 4、读取除表头之外的所有行，将每一行的数据保存在一个pojo对象中
			// 4-1 获取所有行
			int lastRowNum = sheet.getLastRowNum();
			// 4-3 遍历每一行(跳过第一行）
			for (int j = 1; j < lastRowNum; j++) {
				// 新建一个对象，用来存放每一行的数据
				ExcelRowObject excelRowObject = clazz.newInstance();
				
				// 将该对象在Excel表格中的行号，设值给对象
				Method setRowNumMethod = clazz.getMethod("setRowNum",int.class);
				setRowNumMethod.invoke(excelRowObject, j+1);
				
				// 循环到某一行
				Row row = sheet.getRow(j);
				// 4-4 遍历某一行的所有单元格，按表字段名对应保存在一个pojo对象中
				for (int k = 0; k < lastCellNum; k++) {
					Cell cell = row.getCell(k,MissingCellPolicy.CREATE_NULL_AS_BLANK);
					cell.setCellType(CellType.STRING);
					// 获取单元格的值
					String cellvalue = cell.getStringCellValue();
					// 获取当前列对应的表字段名
					String filedName = filedArray[k];
					// 获取setter方法名
					String setterMethodName = "set"+ filedName;
					// 获取方法名
					Method setterMethod = clazz.getMethod(setterMethodName, String.class);
					// 对带有参数的单元格数据进行正则匹配
					String commonStr = ParamsUtil.getCommanStr(cellvalue);
					// 反射进行赋值
					setterMethod.invoke(excelRowObject, commonStr);
				}
				// 6、往excellDataList容器添加数据
				excellDataList.add(excelRowObject);
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return excellDataList;
	}

	/**
	 * 功能：批量写入数据
	 * @param sourceFilePath 载入内存的源文件（从项目的根路径去读取，比如 src/test/resources/testcase/rest_info_1.xlsx）
	 * @param destinationFilePath 写入后输出的目标文件
	 * @param sheetNum 表编号（1-based）
	 */
	public static void batchWriteIntoExcell(String sourceFilePath, String destinationFilePath, int sheetNum) {
		
		try {
				// 1、将Excell文件载入内存
//				inputStream = ExcellUtil.class.getResourceAsStream(excellPath);  因为要传入指定路径下的文件，所以不能用这种形式载入文件
				// new file的时候，需要从项目的根路径去读取，比如 src/test/resources/testcase/rest_info_1.xlsx
				inputStream = new FileInputStream(new File(sourceFilePath)); 

				//2、获取一个workbook工作薄
				workbook = WorkbookFactory.create(inputStream);
				
				// 3、获取工作薄中的一个工作表sheet
				Sheet sheet = workbook.getSheetAt(sheetNum-1);
				
				// 4、将dataToWriteList列表收集到的数据拆分出来写入到对应的单元格
				for (CellData cellData : Container.dataToWriteList) {
					
					// 4-1 cellData的基本属性：行、列、数据
					String caseId = cellData.getCaseId();
					int columnNum = cellData.getColumnNum();
					String response = cellData.getDatasToWrite();
					
					// 4-2 通过caseId找到对应的apiCaseDetail对象
					APICaseDetails apiCaseDetails = ApiUtil.getApiCaseDetailsByCaseId(caseId);
					
					// 4-3 通过apiCaseDetail对象得到行号
					int rowNum = apiCaseDetails.getRowNum();
					
					// 4-4 通过rowNum行号得到一个Row对象，
					Row row = sheet.getRow(rowNum-1);
					
					// 4-5 通过row对象，找到需要写入的单元格
					Cell cellToBeWritten = row.getCell(columnNum-1, MissingCellPolicy.CREATE_NULL_AS_BLANK);
					
					// 4-6、为单元格设置数据类型
					cellToBeWritten.setCellType(CellType.STRING);
					
					// 5、向单元格写入数据
					logger.info("正在向编号为【"+caseId+"】的测试用例写入返回数据，返回数据为：【"+response+"】");
					cellToBeWritten.setCellValue(response);
				}
				// 6、输出流，输出写入后的文件
				outputStream = new FileOutputStream(new File(destinationFilePath));
				workbook.write(outputStream);
			} catch (Exception e) {
				e.printStackTrace();
			}finally {               
				// 释放关闭资源：因为inputstream、outputstream、workbook这种资源在内存里面相当稀缺，所以要即时的释放资源
				closeResources(inputStream, outputStream, workbook);
			}
	}

	public static void main(String[] args) {
		ParamsUtil.addToGlobalData("mobilephone", "13888866666");
		
		List<APICaseDetails> maps = (List<APICaseDetails>) readExcell("/testcase/rest_info_2.xlsx", 2, APICaseDetails.class);
		for (APICaseDetails map : maps) {
			System.out.println(map);
		}
	}
	
}

