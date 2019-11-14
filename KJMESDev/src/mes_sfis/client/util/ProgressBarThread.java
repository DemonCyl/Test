package mes_sfis.client.util;

import mes_sfis.client.excel.GenerateCPKExcel;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

/**
 * Created by Srx_Zhu on 2018/6/01 0001.
 */
public class ProgressBarThread extends Thread {
    public void run() {
        String fileToBeRead = "D:\\mes_data\\demo\\1721-443-CPK_template.xlsx"; // excel位置

        try {
            XSSFWorkbook templateWorkbook = new XSSFWorkbook(new BufferedInputStream(new FileInputStream(fileToBeRead), 8 * 1024));
            XSSFSheet templateSheet = templateWorkbook.getSheet("工作表1");
            for(int i=10;i<templateSheet.getLastRowNum()+1;i++){
                templateSheet.removeRow(templateSheet.getRow(i));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
