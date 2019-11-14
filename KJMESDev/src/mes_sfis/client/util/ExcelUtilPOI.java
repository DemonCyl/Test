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

        //��o���W
        String fileName = file.getName();
        //�s��Workbook�u�@����H�A��ܾ��excel
        Workbook workbook=null;
        try {
            //���excel���io�y
            InputStream is = new FileInputStream(file);
            //���u���Z�Y�W���P(xls�Mxlsx)��o���P��Workbook�����H
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
        //�P�_�ƾڪ�����
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC: //�Ʀr
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            case Cell.CELL_TYPE_STRING: //�r�Ŧ�
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case Cell.CELL_TYPE_BOOLEAN: //Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA: //����
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case Cell.CELL_TYPE_BLANK: //�ŭ�
                cellValue = "";
                break;
            case Cell.CELL_TYPE_ERROR: //�G��
                cellValue = "�D�k�r��";
                break;
            default:
                cellValue = "����?��";
                break;
        }
        return cellValue;
    }


    public void testLoadExcel() throws Exception {
        ExcelUtilPOI excel = new ExcelUtilPOI("d:\\tempdata\\gommo.xlsx");

        Workbook wk = excel.getWk();

        Sheet sheet = wk.getSheetAt(0);

        int firstRowNum = sheet.getFirstRowNum();
        //��o������
        int lastRowNum = sheet.getLastRowNum();
        //�`�����F�Ĥ@�檺�Ҧ���

        for (int rowNum = firstRowNum; rowNum <= lastRowNum; rowNum++) {
            Row row = sheet.getRow(rowNum);

            int firstCellNum = row.getFirstCellNum();
            //��o�̫�@�C
            int lastCellNum = row.getPhysicalNumberOfCells();
            String[] cells = new String[row.getPhysicalNumberOfCells()];
            //�`??�e��
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
