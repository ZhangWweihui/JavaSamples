package com.zwh.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ZhangWeihui
 * @date 2018/11/7 11:08
 */
@Slf4j
public class ExcelUtils {

    private static final String EXCEL_XLS = "xls";
    private static final String EXCEL_XLSX = "xlsx";

    /**
     * 判断Excel的版本,获取Workbook
     * @param filePath
     * @return
     * @throws IOException
     */
    public static Workbook getWorkbok(String filePath) throws IOException {
        Workbook wb = null;
        if(filePath.endsWith(EXCEL_XLS)){
            wb = new HSSFWorkbook(new FileInputStream(filePath));
        }else if(filePath.endsWith(EXCEL_XLSX)){
            wb = new XSSFWorkbook(new FileInputStream(filePath));
        }
        return wb;
    }

    /**
     * 根据文件后缀名类型获取对应的工作簿对象
     * @param inputStream 读取文件的输入流
     * @param fileType 文件后缀名类型（xls或xlsx）
     * @return 包含文件数据的工作簿对象
     * @throws IOException
     */
    public static Workbook getWorkbook(InputStream inputStream, String fileType) throws IOException {
        Workbook workbook = null;
        if (fileType.equalsIgnoreCase(EXCEL_XLS)) {
            workbook = new HSSFWorkbook(inputStream);
        } else if (fileType.equalsIgnoreCase(EXCEL_XLSX)) {
            workbook = new XSSFWorkbook(inputStream);
        }
        return workbook;
    }

    /**
     * 判断文件是否是excel
     * @throws Exception
     */
    public static void checkExcelVaild(File file) throws Exception{
        if(!file.exists()){
            throw new Exception("文件不存在");
        }
        if(!(file.isFile() && (file.getName().endsWith(EXCEL_XLS) || file.getName().endsWith(EXCEL_XLSX)))){
            throw new Exception("文件不是Excel");
        }
    }

    /**
     * 读取 Excel 到 Object List中
     * @throws Exception
     */
    public static List<List<Object>> getData(String filePath, int sheetIndex, int startRow, int endRow, int maxColumn) throws Exception {
        List<List<Object>> objectLists = new ArrayList<>();
        try {
            File excelFile = new File(filePath);
            checkExcelVaild(excelFile);
            Workbook workbook = getWorkbok(filePath);
            //int sheetCount = workbook.getNumberOfSheets(); // Sheet的数量
            /**
             * 设置当前excel中sheet的下标：0开始
             */
            Sheet sheet = workbook.getSheetAt(sheetIndex);

            //获取总行数
            //System.out.println(sheet.getLastRowNum());

            //为跳过第一行目录设置count
            int count = 0;
            for (Row row : sheet) {
                try {
                    if(count < startRow) {
                        count++;
                        continue;
                    }

                    if(count==endRow){
                        break;
                    }

                    //如果当前行没有数据，跳出循环
                    if(row.getCell(0).toString().equals("")){
                        return objectLists;
                    }

                    //获取总列数(空格的不计算)
                    int columnTotalNum = row.getPhysicalNumberOfCells();
                    log.info("行号：{}，总列数：{}，最大列数：{}",count+1,columnTotalNum,row.getLastCellNum());
                    int end = row.getLastCellNum();
                    if(end > maxColumn){
                        end = maxColumn;
                    }
                    List<Object> objects = new ArrayList<>();
                    for (int i = 0; i < end; i++) {
                        Cell cell = row.getCell(i);
                        if(cell == null) {
                            log.info("第{}行第{}列为null", count+1, i+1);
                            continue;
                        }

                        Object obj = getValue(cell);
                        objects.add(obj);
                    }
                    objectLists.add(objects);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return objectLists;
        } catch (Exception e) {
            log.error("ExcelUtils#analysis 发生异常", e);
            return objectLists;
        }
    }

    private static Object getValue(Cell cell) {
        Object obj = null;
        switch (cell.getCellTypeEnum()) {
            case BOOLEAN:
                obj = cell.getBooleanCellValue();
                break;
            case ERROR:
                obj = cell.getErrorCellValue();
                break;
            case NUMERIC:
                obj = cell.getNumericCellValue();
                break;
            case STRING:
                obj = cell.getStringCellValue();
                break;
            default:
                break;
        }
        return obj;
    }

    /**
     * 将单元格内容转换为字符串
     * @param cell
     * @return
     */
    private static String convertCellValueToString(Cell cell) {
        if(cell==null){
            return null;
        }
        String returnValue = null;
        switch (cell.getCellTypeEnum()) {
            case NUMERIC:
                Double doubleValue = cell.getNumericCellValue();
                // 格式化科学计数法，取一位整数
                DecimalFormat df = new DecimalFormat("0");
                returnValue = df.format(doubleValue);
                break;
            case STRING:
                returnValue = cell.getStringCellValue();
                break;
            case BOOLEAN:
                Boolean booleanValue = cell.getBooleanCellValue();
                returnValue = booleanValue.toString();
                break;
            case BLANK:
                break;
            case FORMULA:
                returnValue = cell.getCellFormula();
                break;
            case ERROR:
                returnValue = "Error:" + cell.getErrorCellValue();
                break;
            default:
                returnValue = "";
                break;
        }
        return returnValue;
    }

    /**
     * 提取每一行中需要的数据，构造成为一个List<String>
     *
     * @param row 行数据
     * @return 解析后的行数据对象，行数据错误时返回null
     */
    private static List<String> convertRowToStringList(Row row) {
        List<String> list = new ArrayList<>();
        int cellNum = row.getPhysicalNumberOfCells();
        for(int i=0; i<cellNum; i++) {
            list.add(convertCellValueToString(row.getCell(i)));
        }
        return list;
    }

    /**
     * 读取Excel文件内容
     * @param fileName 要读取的Excel文件所在路径
     * @return 读取结果列表，读取失败时返回null
     */
    public static List<List<String>> readExcel(String fileName, int sheetNum) {
        // 获取Excel后缀名
        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
        // 获取Excel文件
        File excelFile = new File(fileName);
        if (!excelFile.exists()) {
            System.out.println("指定的Excel文件不存在");
            return null;
        }
        try(FileInputStream inputStream = new FileInputStream(excelFile);
            Workbook workbook = getWorkbook(inputStream, fileType)){
            // 读取excel中的数据
            List<List<String>> resultDataList = parseExcel(workbook, sheetNum);
            return resultDataList;
        } catch (Exception e) {
            System.out.println("解析Excel失败，文件名：" + fileName + " 错误信息：" + e.getMessage());
            return null;
        }
    }

    /**
     * 解析Excel数据
     * @param workbook Excel工作簿对象
     * @return 解析结果
     */
    private static List<List<String>> parseExcel(Workbook workbook, int sheetNum) {
        List<List<String>> resultDataList = new ArrayList<>();
        // 解析sheet
        Sheet sheet = workbook.getSheetAt(sheetNum);
        // 校验sheet是否合法
        if (sheet == null) {
            return resultDataList;
        }

        // 获取第一行数据
        int firstRowNum = sheet.getFirstRowNum();
        Row firstRow = sheet.getRow(firstRowNum);
        if (null == firstRow) {
            System.out.println("解析Excel失败，在第一行没有读取到任何数据！");
        }

        // 解析每一行的数据，构造数据对象
        int rowStart = firstRowNum + 1;
        int rowEnd = sheet.getPhysicalNumberOfRows();
        for (int rowNum = rowStart; rowNum < rowEnd; rowNum++) {
            Row row = sheet.getRow(rowNum);

            if (null == row) {
                continue;
            }

            List<String> resultData = convertRowToStringList(row);
            if (null == resultData) {
                System.out.print("第 " + row.getRowNum() + "行数据不合法，已忽略！");
                continue;
            }
            resultDataList.add(resultData);
        }

        return resultDataList;
    }
}
