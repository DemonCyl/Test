package mes_sfis.client.util;

import mes_sfis.client.model.bean.CognexModel;
import mes_sfis.client.model.bean.NutsDataModel;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLDocumentPart;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTMarker;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by Mark_Yang on 2018/5/27.
 */
public class ExcelUtils {

    public List<NutsDataModel> excelNutsList(String time,String meachine) throws Exception {
        if (time==""){
            time="2017-10-10 10:00:00";
        }
        List<NutsDataModel> list=new ArrayList<>();
        List<String> filelist=new ArrayList<>();
       // DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat formatdetail = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=null;
        Date markdate = formatdetail.parse(time.substring(0,10)+" 00:00:00");
        Date markdatedetail = formatdetail.parse(time);
      /*  System.out.println(time+"aaaaaaa"+markdatedetail);
        System.out.println("===========AAAbb");*/
        filelist=getAllFileName("D:\\bb");

        //System.out.println("filelist"+filelist.size()+filelist.toString());
        for (int i = 0; i < filelist.size(); i++) {
            date = formatdetail.parse(filelist.get(i).substring(0,filelist.get(i).lastIndexOf("."))+" 00:00:00");
           // System.out.println(date+"hhhhh"+markdate);
            if (markdate.getTime() <= date.getTime()){
               // System.out.println(true);
                //System.out.println("===========AAAbb2");
                File file = new File("D:\\bb\\"+filelist.get(i));
                FileInputStream inputStream = null;
                XSSFWorkbook workbook=null;
                try {
                    inputStream = new FileInputStream(file);
                    workbook = new XSSFWorkbook(inputStream);
                    XSSFSheet hssfSheet =  workbook.getSheetAt(0);
                   // System.out.println(hssfSheet.getLastRowNum()+"cc");
                    for (int a=1; a <= hssfSheet.getLastRowNum(); a++) {
                        XSSFRow row = hssfSheet.getRow(a);

                        boolean  flag = !(getCellValue(row.getCell(0)).isEmpty())&&(formatdetail.parse(getCellValue(row.getCell(0)).trim().replace("_"," ")).getTime()>markdatedetail.getTime())  ;
                     //   System.out.println("dd"+flag);
                        //判斷讀取excel列不為空
                        if (flag) {

                            // 得到行
                            NutsDataModel nutsDataModel=new NutsDataModel();
                            if (!getCellValue( row.getCell(0)).trim().isEmpty()) {
                                nutsDataModel.setINTIME(getCellValue( row.getCell(0)).trim().replace("_"," "));
                            //    System.out.println("shijiana"+getCellValue( row.getCell(0)).trim().replace("_"," "));
                            }

                            if (!getCellValue(row.getCell(1)).trim().isEmpty()) {
                                nutsDataModel.setMCE(getCellValue(row.getCell(1)).trim().replace("\\r\\n",""));
                            }
                            if (!getCellValue((XSSFCell) row.getCell(2)).trim().isEmpty()) {
                                nutsDataModel.setSN1(getCellValue(row.getCell(2)).trim().replace("\\r\\n",""));
                                //System.out.println(getCellValue((XSSFCell) row.getCell(2))+"aaaaaaaaaaa");
                            }
                            nutsDataModel.setMACHINE_ID(meachine);
                            list.add(nutsDataModel);
                            // System.out.println("==" + boardModel);


                        } else {
                            break;
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    throw new Exception("readCSV異常"+e.getMessage());
                } finally {
                    try {
                        inputStream.close();
                        workbook.close();
                    } catch (IOException e) {

                    }
                }
            }

        }


        return list;

    }


    private String getCellValue(Cell cell) {
        String cellValue = "";
        DataFormatter formatter = new DataFormatter();
        if (cell != null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        cellValue = formatter.formatCellValue(cell);
                    } else {
                        Double value = (cell.getNumericCellValue());
                        cellValue = value.toString();
                    }
                    break;
                case Cell.CELL_TYPE_STRING:
                    cellValue = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    cellValue = String.valueOf(cell.getBooleanCellValue());
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    cellValue = String.valueOf(cell.getCellFormula());
                    break;
                case Cell.CELL_TYPE_BLANK:
                    cellValue = "";
                    break;
                case Cell.CELL_TYPE_ERROR:
                    cellValue = "";
                    break;
                default:
                    cellValue = cell.toString().trim();
                    break;
            }
        }
        return cellValue.trim();
    }
    //  ?取文件?
    public static List<String> getAllFileName(String path) {
        List<String> fileNameList =new ArrayList<>();
        System.out.println(path);
        boolean flag = false;
        File file = new File(path);
        File[] tempList = file.listFiles();
        System.out.println("changdu"+tempList.length);

        try {
            for (int i = 0; i < tempList.length; i++) {
                if (tempList[i].isFile()) {
    //              System.out.println("文     件：" + tempList[i]);
                    //fileNameList.add(tempList[i].toString());
                    if(tempList[i].getName().length()==15){
                     //   System.out.println("mingzi"+tempList[i].getName());
                        fileNameList.add(tempList[i].getName());
                    }

                }
                if (tempList[i].isDirectory()) {
    //              System.out.println("文件?：" + tempList[i]);
                    fileNameList.addAll(getAllFileName(tempList[i].getAbsolutePath()));
                    System.out.println(fileNameList.size());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileNameList;
    }
    public List getFileName(){

        return null;
    }



    public List<CognexModel> excelForList(String time) throws Exception {
        List<CognexModel> list = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        System.out.println(hour+"shijian");
        Date nowDate = new Date();

       // int hour=nowDate.getHours();
        // SimpleDateFormat formattera = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String today = formatter.format(nowDate);
        Date newday;
        String newdate = null;
        if (time.isEmpty()){
          //  System.out.println("1null");
            return excelData(today,null);

        }else {
            newday = formatter.parse(time);
            newdate=formatter.format(newday);
            if(hour<1){
                System.out.println("小於1");
                List<CognexModel> olddata= null;
                try {
                    olddata = excelData(newdate,newdate);
                    list=excelData(today,newdate);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new Exception(e.getMessage());
                }
                list.addAll(olddata);
                return list;

            }else {
                System.out.println("大於");
                return excelData(today,newdate);

            }
        }

    }

    public List<CognexModel> excelData(String day,String newdate) throws Exception {
        List<CognexModel> list = new ArrayList<>();
        File filea = new File("C:/Cognex/Image and Result/"+day.substring(0,10)+"/result2D.csv");
        System.out.println(filea.getPath());
        BufferedReader reader = null;
        try {

            reader = new BufferedReader(new FileReader(filea));
            String line=reader.readLine();
           // Sheet hssfSheet = workbook.getSheetAt(0);
            //  Map<String, XSSFPictureData> picMap = getPictures((XSSFSheet) hssfSheet);
            // 得到第一?工作表

            while ((line = reader.readLine()) != null) {
                if (!(line.trim().isEmpty())) {
                    String item[] = line.trim().split(",");//CSV格式文件?逗?分隔符文件，?里根据逗?切分
                    //CognexModel cognexModel = new CognexModel();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date exceldate = formatter.parse(item[0].substring(1));
                    boolean flag;
                    if (newdate!=null){

                        Date olddate=formatter.parse(day);
                        System.out.println("notnull"+newdate+exceldate+olddate);
                        int res = exceldate.compareTo(olddate);
                        System.out.println(res+formatter.format(exceldate));
                        flag= (res==1 && !item[0].isEmpty()&&!item[16].isEmpty());
                    }else {
                        System.out.println("null");
                        flag= (!item[0].isEmpty()&&!item[16].isEmpty());
                    }
                    if (flag) {
                        // 得到行
                        CognexModel cognexModel = new CognexModel();
                        cognexModel.setTIME(item[0].substring(1));
                        cognexModel.setAMODULE(item[1].substring(1));
                        cognexModel.setREADING(item[2]);
                        cognexModel.setDECODE_TIME(item[3]);
                        cognexModel.setOVERALL_GRAGE(item[4]);
                        cognexModel.setSYMBOL_CONTRAST(item[5]);
                        cognexModel.setRAWUP(Double.parseDouble(item[6]));
                        cognexModel.setPRINTGROWTH(item[7]);
                        cognexModel.setRAWDOWN(Double.parseDouble(item[8]));
                        cognexModel.setERROR_CORRECTION(item[9]);
                        cognexModel.setRAWONE(new Double(Double.valueOf(item[10])).intValue());
                        cognexModel.setMODULATION(item[11].trim());
                        cognexModel.setRAWTWO(new Double(Double.valueOf(item[12])).intValue());
                        cognexModel.setFIXEDPATTERN(item[13]);
                        cognexModel.setRAWTHREE(new Double(Double.valueOf(item[14])).intValue());
                        cognexModel.setGRID_NONUNIFORMITY(item[15]);
                        cognexModel.setRAWFOUR(Double.parseDouble(item[16]));
                        //readRawData(cognexModel,row);


                        // System.out.println("==" + boardModel);
                        list.add(cognexModel);
                    }

                }
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("readCSV異常"+e.getMessage());
        }

    }

    public static Date getNextDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        return date;
    }







    private String getCellValue(XSSFCell cell) {
        String cellValue = "";
        DataFormatter formatter = new DataFormatter();
        if (cell != null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        cellValue = formatter.formatCellValue(cell);
                    } else {
                        Double value = (cell.getNumericCellValue());
                        cellValue = value.toString();
                    }
                    break;
                case Cell.CELL_TYPE_STRING:
                    cellValue = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    cellValue = String.valueOf(cell.getBooleanCellValue());
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    cellValue = String.valueOf(cell.getCellFormula());
                    break;
                case Cell.CELL_TYPE_BLANK:
                    cellValue = "";
                    break;
                case Cell.CELL_TYPE_ERROR:
                    cellValue = "";
                    break;
                default:
                    cellValue = cell.toString().trim();
                    break;
            }
        }
        return cellValue.trim();
    }



}
