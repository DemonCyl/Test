package mes_sfis.client.excel;


import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ExcelUtil {
    //纐??じ?甧???Α
    private static DecimalFormat df = new DecimalFormat("0");
    // 纐??じΑてら戳才﹃
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    // Αて?
    private static DecimalFormat nf = new DecimalFormat("0.00");
    //private static int row_num=1;
    public static ArrayList<ArrayList<Object>> readExcel(File file,int rownum) {
        if (file == null) {
            return null;
        }
        if (file.getName().endsWith("xlsx")) {
            //?瞶ecxel2007
            return readExcel2003(file,rownum);
        } else {
            //?瞶ecxel2003
            return readExcelCSV(file,rownum);
            //return readExcel2007(file,rownum);
            //
        }
    }

    /*
     * @return ??狦?ArrayList???疼蒓???
     * lists.get(0).get(0)ボ?Excelい0︽0?じ
     */
    public static ArrayList<ArrayList<Object>> readExcel2003(File file,int rownum) {
        try {
            ArrayList<ArrayList<Object>> rowList = new ArrayList<ArrayList<Object>>();
            ArrayList<Object> colList;
            HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(file));
            HSSFRow row;
            HSSFCell cell;
            Object value;
            for(int z=0;z<wb.getNumberOfSheets();z++){
                HSSFSheet sheet = wb.getSheetAt(z);
System.out.println(sheet.getFirstRowNum()+"********************************************************aa"+sheet.getLastRowNum());
                for (int i = rownum, rowCount = 0; rowCount < sheet.getPhysicalNumberOfRows(); i++) {
                    row = sheet.getRow(i);
                    colList = new ArrayList<Object>();
                    colList.add(i+1);
                    if(row == null){
                        //??︽??
                        if(i != sheet.getPhysicalNumberOfRows()){//?琌琌程︽
                            System.out.println(sheet.getPhysicalNumberOfRows());
                            //rowList.add(colList);
                            rowCount++;
                        }

                        continue;
                    }else{
                        System.out.println(row.getCell(0));
                        if(row.getCell(0).getCellType() == HSSFCell.CELL_TYPE_BLANK){
                            continue;
                        }
                        rowCount++;
                    }
                    for( int j = row.getFirstCellNum() ; j <= row.getLastCellNum() ;j++){
                        cell = row.getCell(j);
                        if(cell == null || cell.getCellType() == HSSFCell.CELL_TYPE_BLANK){
                            //???じ?
                            if(j != row.getLastCellNum()){//?琌琌?︽い程??じ
                                colList.add("");
                            }
                            continue;
                        }
                        if(!cell.equals("")){
                            if(cell!=null){
                                switch (cell.getCellType()) {
                                    case XSSFCell.CELL_TYPE_STRING:
                                        System.out.println(i + "︽" + j + "  is String type");

                                        value = cell.getStringCellValue();
                                        break;
                                    case XSSFCell.CELL_TYPE_NUMERIC:
                                        if ("@".equals(cell.getCellStyle().getDataFormatString())) {
                                            value = df.format(cell.getNumericCellValue());
                                        } else if ("General".equals(cell.getCellStyle()
                                                .getDataFormatString())) {
                                            value = nf.format(cell.getNumericCellValue());
                                        } else {
                                            value = sdf.format(HSSFDateUtil.getJavaDate(cell
                                                    .getNumericCellValue()));
                                        }
                                        System.out.println(i + "︽" + j
                                                + "  is Number type ; DateFormt:"
                                                + value.toString());
                                        break;
                                    case XSSFCell.CELL_TYPE_BOOLEAN:
                                        System.out.println(i + "︽" + j + "  is Boolean type");
                                        value = Boolean.valueOf(cell.getBooleanCellValue());
                                        break;
                                    case XSSFCell.CELL_TYPE_BLANK:
                                        System.out.println(i + "︽" + j + "  is Blank type");
                                        value = "";
                                        break;
                                    default:
                                        System.out.println(i + "︽" + j + "  is default type");
                                        value = cell.toString();
                                }// end switch

                                colList.add(value);
                            }
                        }


                    }//end for j
                    rowList.add(colList);

                }//end for i

            }

            return rowList;
        } catch (Exception e) {
            return null;
        }
    }

    public static ArrayList<ArrayList<Object>> readExcelCSV(File file,int rownum){
        ArrayList<ArrayList<Object>> rowList = new ArrayList<ArrayList<Object>>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String d = simpleDateFormat.format(date);
        String path = "D:/SpecGAGE3D_DDS-4X/SpecGAGE3D_DDS_MP-GUI-4X/results/Day_"+d+"-000000.csv";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            //reader.readLine();

            String line = null;
            int row_num = 1;
            //row_num = rownum;
            int tostart = 0;

              /*  for(int a = 1;a<=rownum;a++){
                    reader.readLine();
                }*/
            int flag = 0;
            while((line=reader.readLine())!=null){
                //reader.skip(rownum);
                ArrayList<Object> colList = new ArrayList<>();
                String item[] = line.split(",");

                if(line.contains("Barcode")){
                    tostart = 1;
                    continue;
                }
                    //System.out.println(item[0]);
                //System.out.println("======"+item.length);
                if(tostart==1){
                    if(item.length<=0){
                        row_num++;
                        if(flag>=10){
                            break;
                        }
                        flag++;
                        continue;
                    }
                    row_num++;
                    if(row_num>rownum){
                        colList.add(row_num);
                        for (int i=0;i<5;i++){
                            if(item[0]!=""){

                                colList.add(item[i]);
                            }
                        }
                        //System.out.println(colList.size());
                        rowList.add(colList);
                    }
                    continue;
                }
                row_num++;

                //}
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return rowList;
    }

    public static ArrayList<ArrayList<Object>> readExcel2007(File file,int rownum) {
        try {
            ArrayList<ArrayList<Object>> rowList = new ArrayList<ArrayList<Object>>();
            ArrayList<Object> colList;
            Workbook wb = new XSSFWorkbook(new FileInputStream(file));
            Row row;
            XSSFCell cell;
            Object value;
            for(int z=0;z<wb.getNumberOfSheets();z++){
                Sheet sheet = wb.getSheetAt(z);
                for (int i = rownum, rowCount = 0; rowCount < sheet.getPhysicalNumberOfRows(); i++) {
                    row = sheet.getRow(i);
                    colList = new ArrayList<Object>();
                    colList.add(i+1);
                    if(row == null){
                        //??︽??
                        if(i != sheet.getPhysicalNumberOfRows()){//?琌琌程︽
                            System.out.println(sheet.getPhysicalNumberOfRows());
                            //rowList.add(colList);
                            rowCount++;
                        }

                        continue;
                    }else{
                        System.out.println(row.getCell(0));
                        if(row.getCell(0).getCellType() == Cell.CELL_TYPE_BLANK){
                            continue;
                        }
                        rowCount++;
                    }
                    for( int j = row.getFirstCellNum() ; j <= row.getLastCellNum() ;j++){
                        cell = (XSSFCell) row.getCell(j);
                        if(cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK){
                            //???じ?
                            if(j != row.getLastCellNum()){//?琌琌?︽い程??じ
                                colList.add("");
                            }
                            continue;
                        }
                        if(!cell.equals("")){
                            if(cell!=null){
                                switch (cell.getCellType()) {
                                    case Cell.CELL_TYPE_STRING:
                                        System.out.println(i + "︽" + j + "  is String type");

                                        value = cell.getStringCellValue();
                                        break;
                                    case Cell.CELL_TYPE_NUMERIC:
                                        if ("@".equals(cell.getCellStyle().getDataFormatString())) {
                                            value = df.format(cell.getNumericCellValue());
                                        } else if ("General".equals(cell.getCellStyle()
                                                .getDataFormatString())) {
                                            value = nf.format(cell.getNumericCellValue());
                                        } else {
                                            value = sdf.format(HSSFDateUtil.getJavaDate(cell
                                                    .getNumericCellValue()));
                                        }
                                        System.out.println(i + "︽" + j
                                                + "  is Number type ; DateFormt:"
                                                + value.toString());
                                        break;
                                    case Cell.CELL_TYPE_BOOLEAN:
                                        System.out.println(i + "︽" + j + "  is Boolean type");
                                        value = Boolean.valueOf(cell.getBooleanCellValue());
                                        break;
                                    case Cell.CELL_TYPE_BLANK:
                                        System.out.println(i + "︽" + j + "  is Blank type");
                                        value = "";
                                        break;
                                    default:
                                        System.out.println(i + "︽" + j + "  is default type");
                                        value = cell.toString();
                                }// end switch

                                colList.add(value);
                            }
                        }


                    }//end for j
                    rowList.add(colList);

                }//end for i

            }

            return rowList;
        } catch (Exception e) {
            return null;
        }
    }



    public static DecimalFormat getDf() {
        return df;
    }

    public static void setDf(DecimalFormat df) {
        ExcelUtil.df = df;
    }

    public static SimpleDateFormat getSdf() {
        return sdf;
    }

    public static void setSdf(SimpleDateFormat sdf) {
        ExcelUtil.sdf = sdf;
    }

    public static DecimalFormat getNf() {
        return nf;
    }

    public static void setNf(DecimalFormat nf) {
        ExcelUtil.nf = nf;
    }


}

