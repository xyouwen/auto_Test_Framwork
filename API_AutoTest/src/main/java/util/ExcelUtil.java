package util;

import org.apache.poi.ss.usermodel.*;
import pojo.ExcelRowObjects;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtil {

    /**
     * 读取表格内容，将表格的每一行按类型封装成一个对象
     * @param excellPath 文件路径
     * @param sheetIdex 表格编号（从1开始）
     * @param clazz 对象的类型
     * @return  List<? extends ExcelRowObjects> 对象列表
     */
    public static List<? extends ExcelRowObjects> readExcelRowsAsObjects(String excellPath, int sheetIdex, Class<? extends  ExcelRowObjects> clazz){

        List<? extends ExcelRowObjects> excelRowObjects = null;
        try {
            // 1、获取workbook
            InputStream inputStream = ExcelUtil.class.getResourceAsStream(excellPath);
            Workbook workbook = WorkbookFactory.create(inputStream);

            // 2、获取sheet
            Sheet sheet = workbook.getSheetAt(sheetIdex-1);

            // 3、获取所有表字段：获取所有表头，以数组形式存储起来
            String[] fieldNameArray = getFieldNameArray(sheet);

            // 4、将每一行数据按表字段封装成一个对象
            excelRowObjects = getExcelRowObjects(clazz, sheet, fieldNameArray);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return excelRowObjects;
    }

    /**
     * 将Excel表格的每一行封装成一个对象
     * @param clazz 对象的类型
     * @param sheet 工作表
     * @param fieldNameArray 表字段列表
     * @return List<? extends ExcelRowObjects> 对象列表
     */
    private static List<? extends ExcelRowObjects> getExcelRowObjects(Class<? extends ExcelRowObjects> clazz, Sheet sheet, String[] fieldNameArray)  {
        List<ExcelRowObjects> excelRowObjectsList = new ArrayList<ExcelRowObjects>();
        // 总列数
        int columnNum = fieldNameArray.length;
        // 总行数
        int lastRowNum = sheet.getLastRowNum();
        try {
            // 跳过表头，从第2行开始遍历
            for (int i = 1; i < lastRowNum; i++) {
                Row row = sheet.getRow(i);
                ExcelRowObjects excelRowObjects = clazz.newInstance();
                // 为对象设置行号
                excelRowObjects.setRowNum(i);

                for (int k = 0; k < columnNum; k++) {
                    // 对象的属性：对象属性所对应的公共方法
                    String filedName = fieldNameArray[k];
                    String setMethodName = "set"+filedName;

                    // 对象的值：获取单元格的内容
                    Cell cell = row.getCell(k, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    cell.setCellType(CellType.STRING);
                    String cellvalue= cell.getStringCellValue();
                    // 参数化还原：普通普通字符口串
                    String commonCellValue = ParamsUtil.getCommonStr(cellvalue);

                    // 反射赋值
                    Method method = clazz.getMethod(setMethodName, String.class);
                    method.invoke(excelRowObjects, commonCellValue);
                }
                // 6、往列表里添加对象
                excelRowObjectsList.add(excelRowObjects);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return  excelRowObjectsList;
    }

    /**
     * 获取表字段（即表格第一行的内容），以列表形式返回
     * @param sheet 表格
     * @return String[]  表字段的数组
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
//        testGetFieldNameArray();
//        testReadExcelRowsAsObjects();
    }

//    private static void testGetFieldNameArray(){
//        try {
//            String excellPath = "/testCase/rest_info_2.xlsx";
//            // 1、获取workbook
//            InputStream inputStream = ExcelUtil.class.getResourceAsStream(excellPath);
//            Workbook workbook = WorkbookFactory.create(inputStream);
//
//            // 2、获取sheet
//            Sheet sheet = workbook.getSheetAt(1);
//
//            String[] fieldNameArray = getFieldNameArray(sheet);
//            for (int i = 0; i < fieldNameArray.length; i++) {
//                System.out.println(fieldNameArray[i]);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    private   static void testReadExcelRowsAsObjects(){
//        ParamsUtil.addToGlobalDatas("mobilephone","13888888888");
//        ParamsUtil.addToGlobalDatas("pwd","efssefsef");
//        ParamsUtil.addToGlobalDatas("regname","jack");
//        try {
//            String excellPath = "/testCase/rest_info_2.xlsx";
//            // 1、获取workbook
//            InputStream inputStream = ExcelUtil.class.getResourceAsStream(excellPath);
//            Workbook workbook = WorkbookFactory.create(inputStream);
//            // 2、获取sheet
//            List<? extends ExcelRowObjects> list = readExcelRowsAsObjects(excellPath, 2, ApiCaseDetails.class);
//            for (int i = 0; i < list.size(); i++) {
//                ExcelRowObjects object = list.get(i);
//                System.out.println(object);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
