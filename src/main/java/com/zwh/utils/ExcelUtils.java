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
        if(filePath.endsWith(EXCEL_XLS)){  //Excel 2003
            wb = new HSSFWorkbook(new FileInputStream(filePath));
        }else if(filePath.endsWith(EXCEL_XLSX)){  // Excel 2007/2010
            wb = new XSSFWorkbook(new FileInputStream(filePath));
        }
        return wb;
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

}
