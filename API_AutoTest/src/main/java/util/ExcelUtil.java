package util;

import org.apache.poi.ss.usermodel.*;
import pojo.ExcelRowObjects;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

public class ExcelUtil {

    public static List<? extends ExcelRowObjects> readExcelRowsAsObjects(String excellPath, int sheetIdex, Class<? extends  ExcelRowObjects> clazz){
        try {
            // 1、获取workbook
            InputStream inputStream = ExcelUtil.class.getResourceAsStream(excellPath);
            Workbook workbook = WorkbookFactory.create(inputStream);

            // 2、获取sheet
            Sheet sheet = workbook.getSheetAt(sheetIdex-1);

            // 3、获取所有表字段：获取所有表头，以列表形式存储起来
            String[] fieldNameArray = getFieldNameArray(sheet);


            // 4、将每一行数据按表字段封装成一个对象
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }

    /**
     * 获取表字段（即表格第一行的内容），以列表形式返回
     * @param sheet 表格
     * @return String[]  fieldNameArray
     */
    private static String[] getFieldNameArray(Sheet sheet) {
        // 3-1 获取第一行
        Row firstRow = sheet.getRow(0); //
        // 3-2 通过行，获取总列数
        int lastCellNum = firstRow.getLastCellNum();
        // 3-3 新建列表，用来保存表头的数据
        String[] fieldNameArray = new String[lastCellNum];
        // 3-4 往列表里面添加数据
        for (int i = 0; i < lastCellNum; i++) {
            // 3-5 获取第一列（表头）每个单元格的值
            Cell cell = firstRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            // 设置单元格的类型
            cell.setCellType(CellType.STRING);
            // 获取单元格的值
            String cellValue = cell.getStringCellValue();
            // 提取表头的名称
            String fieldName = cellValue.substring(0, cellValue.indexOf("("));
            // 往列表容器里添加数据
            fieldNameArray[i] = fieldName;
        }
        return  fieldNameArray;
    }

    public static void main(String[] args){
        testGetFieldNameArray();
    }
    private static void testGetFieldNameArray(){
        try {
            String excellPath = "/testCase/rest_info_2.xlsx";
            // 1、获取workbook
            InputStream inputStream = ExcelUtil.class.getResourceAsStream(excellPath);
            Workbook workbook = WorkbookFactory.create(inputStream);

            // 2、获取sheet
            Sheet sheet = workbook.getSheetAt(1);

            String[] fieldNameArray = getFieldNameArray(sheet);
            for (int i = 0; i < fieldNameArray.length; i++) {
                System.out.println(fieldNameArray[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
