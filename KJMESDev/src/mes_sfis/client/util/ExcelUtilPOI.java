package mes_sfis.client.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.Date;

/**
 * Created by Chris1_Liao on 2018/5/22.
 */
public class ExcelUtilPOI {

    private static final Logger logger = LogManager.getLogger(ExcelUtilPOI.class);

    public Workbook getWk() {
        return wk;
    }

    public void setWk(Workbook wk) {
        this.wk = wk;
    }

    private Workbook wk;

    public ExcelUtilPOI(String file) throws Exception {
        this.wk = this.getWorkBook(file);
    }

    public  Workbook getWorkBook(String file)throws Exception {
        return getWorkBook(new File(file));
    }


    public  Workbook getWorkBook(File file)throws Exception{

        //獲得文件名
        String fileName = file.getName();
        //新建Workbook工作薄對象，表示整個excel
        Workbook workbook=null;
        try {
            //獲取excel文件的io流
            InputStream is = new FileInputStream(file);
            //根据文件后墜名不同(xls和xlsx)獲得不同的Workbook實體對象
            if (fileName.contains("xlsx")) {
                System.out.println("xlsx_________________________________"+file.getName());
                workbook =  new XSSFWorkbook(is);
                return workbook;
            } else if (fileName.contains("xls")) {
                System.out.println("xls_________________________________"+file.getName());
                workbook = new HSSFWorkbook(is);
                return workbook;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return workbook;
    }

    public  String getCellValue(Cell cell) {
        String cellValue = "";
        if (cell == null) {
            return cellValue;
        }
        //判斷數據的類型
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC: //數字
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            case Cell.CELL_TYPE_STRING: //字符串
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case Cell.CELL_TYPE_BOOLEAN: //Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA: //公式
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case Cell.CELL_TYPE_BLANK: //空值
                cellValue = "";
                break;
            case Cell.CELL_TYPE_ERROR: //故障
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知?型";
                break;
        }
        return cellValue;
    }


    public void testLoadExcel() throws Exception {
        ExcelUtilPOI excel = new ExcelUtilPOI("d:\\tempdata\\gommo.xlsx");

        Workbook wk = excel.getWk();

        Sheet sheet = wk.getSheetAt(0);

        int firstRowNum = sheet.getFirstRowNum();
        //獲得結束行
        int lastRowNum = sheet.getLastRowNum();
        //循環除了第一行的所有行

        for (int rowNum = firstRowNum; rowNum <= lastRowNum; rowNum++) {
            Row row = sheet.getRow(rowNum);

            int firstCellNum = row.getFirstCellNum();
            //獲得最後一列
            int lastCellNum = row.getPhysicalNumberOfCells();
            String[] cells = new String[row.getPhysicalNumberOfCells()];
            //循??前行
            for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++) {
                Cell cell = row.getCell(cellNum);

                logger.debug(excel.getCellValue(cell));
            }
        }
    }

    public static void main(String[] args) throws Exception {
        ExcelUtilPOI excel = new ExcelUtilPOI("d:\\tempdata\\successful.xlsx");

        Workbook wk = excel.getWk();

        Date startDate = new Date();

        Sheet sheet = wk.getSheetAt(0);

        //sheet.shiftRows(100,sheet.getLastRowNum(),-sheet.getLastRowNum());


        for(int endIndex=500;endIndex< sheet.getLastRowNum();endIndex++){
            //logger.debug("endIndex:"+endIndex);
            sheet.removeRow( sheet.getRow(endIndex) );
        }

        try (OutputStream fileOut = new FileOutputStream("d:\\tempdata\\workbook.xlsx")) {
            wk.write(fileOut);
        }


        Date endDate = new Date();

        logger.debug("SPEND:"+(endDate.getTime()-startDate.getTime()));
    }
}
