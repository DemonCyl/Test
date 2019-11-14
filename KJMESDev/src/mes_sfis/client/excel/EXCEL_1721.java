package mes_sfis.client.excel;


import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.FileOutputStream;
import java.util.Date;

/**
 * Created by Srx_Zhu on 2018/4/23 0023.
 */
public class EXCEL_1721 {

    public String sheetName1 = "線邊倉庫存查詢";
    public String sheetName2 = "入庫批號查詢結果";
    public String sheetName3 = "線邊倉ISN查詢結果";
    public String sheetName4 = "報廢儲位查詢結果";
    public String sheetName5 = "解鎖箱號數據";
    public String sheetName6 = "不良品查詢結果";

    public String titleName1 = "線邊倉庫存查詢";
    public String titleName2 = "入庫批號查詢結果";
    public String titleName3 = "線邊倉ISN查詢結果";
    public String titleName4 = "報廢儲位查詢結果";
    public String titleName5 = "解鎖箱號數據";
    public String titleName6 = "不良品查詢結果";

    public String fileName1 = "線邊倉庫存查詢";
    public String fileName2 = "入庫批號查詢結果";
    public String fileName3 = "線邊倉ISN查詢結果";
    public String fileName4 = "報廢儲位查詢結果";
    public String fileName5 = "解鎖箱號數據";
    public String fileName6 = "不良品查詢結果";

    public String[] tableTitleName1 = {"批號", "入庫·借出·賒賬", "數量", "出庫·返還·賒賬·銷毀", "數量", "作業人員"};
    public String[] tableTitleName2 = {"ISN", "料號", "日期","線邊倉名稱","操作員"};
    public String[] tableTitleName3 = {"ISN", "批號", "倉名稱", "狀態"};
    public String[] tableTitleName4 = {"儲位", "批號", "箱號", "操作員", "日期","ISN","倉名"};
    public String[] tableTitleName5 = {"鎧嘉料號", "ISN","箱號"};
    public String[] tableTitleName6 = {"倉庫", "儲位", "箱號", "ISN", "CSSN", "入庫日期", "出庫日期", "ERROR", "站點", "操作員"};

    public int columnNumber1 = 6;
    public int columnNumber2 = 5;
    public int columnNumber3 = 4;
    public int columnNumber4 = 7;
    public int columnNumber5 = 3;
    public int columnNumber6 = 10;

    public int[] columnWidth1 = {30, 20, 20, 20, 20, 20};
    public int[] columnWidth2 = {50, 30, 30,20,20};
    public int[] columnWidth3 = {50, 30, 20, 20};
    public int[] columnWidth4 = {30, 30, 30, 20,30,40,20};
    public int[] columnWidth5 = {40, 40,30};
    public int[] columnWidth6 = {20,20,30,30,30,30,30,15,20,15};

    public void ExportNoResponse(String sheetName4,
                                 String titleName4,
                                 String fileName4,
                                 int columnNumber4,
                                 int[] columnWidth4,
                                 String[] columnName4,
                                 Object[][] dataList4) throws Exception {
        if (columnNumber4 == columnWidth4.length && columnWidth4.length == columnName4.length) {
            // 第一步，?建一?webbook，??一?Excel文件
            HSSFWorkbook wb = new HSSFWorkbook();
            // 第二步，在webbook中添加一?sheet,??Excel文件中的sheet
            HSSFSheet sheet = wb.createSheet(sheetName4);
            // sheet.setDefaultColumnWidth(15); //?一?置列?
            for (int i = 0; i < columnNumber4; i++) {
                for (int j = 0; j <= i; j++) {
                    if (i == j) {
                        sheet.setColumnWidth(i, columnWidth4[j] * 256); // ???置每列的?
                    }
                }
            }
            // ?建第0行 也就是??
            HSSFRow row1 = sheet.createRow((int) 0);
            row1.setHeightInPoints(50);// ????的高度
            // 第三步?建??的?元格?式style2以及字体?式headerFont1
            HSSFCellStyle style2 = wb.createCellStyle();
            style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            style2.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
            style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            HSSFFont headerFont1 = (HSSFFont) wb.createFont(); // ?建字体?式
            headerFont1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗
            headerFont1.setFontName("黑体"); // ?置字体?型
            headerFont1.setFontHeightInPoints((short) 15); // ?置字体大小
            style2.setFont(headerFont1); // ????式?置字体?式

            HSSFCell cell1 = row1.createCell(0);// ?建??第一列
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columnNumber4 - 1)); // 合并第0到第17列
            cell1.setCellValue(titleName4); // ?置值??
            cell1.setCellStyle(style2); // ?置???式

            // ?建第1行 也就是表?
            HSSFRow row = sheet.createRow((int) 1);
            row.setHeightInPoints(37);// ?置表?高度

            // 第四步，?建表??元格?式 以及表?的字体?式
            HSSFCellStyle style = wb.createCellStyle();
            style.setWrapText(true);// ?置自??行
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // ?建一?居中格式

            style.setBottomBorderColor(HSSFColor.BLACK.index);
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);

            HSSFFont headerFont = (HSSFFont) wb.createFont(); // ?建字体?式
            headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗
            headerFont.setFontName("黑体"); // ?置字体?型
            headerFont.setFontHeightInPoints((short) 10); // ?置字体大小
            style.setFont(headerFont); // ????式?置字体?式

            // 第四.一步，?建表?的列
            for (int i = 0; i < columnNumber4; i++) {
                HSSFCell cell = row.createCell(i);
                cell.setCellValue(columnName4[i]);
                cell.setCellStyle(style);
            }

            // 第五步，?建?元格，并?置值
            for (int i = 0; i < dataList4.length; i++) {
                row = sheet.createRow((int) i + 2);
                // ??据?容?置特?新?元格?式1 自??行 上下居中
                HSSFCellStyle zidonghuanhang = wb.createCellStyle();
                zidonghuanhang.setWrapText(true);// ?置自??行
                zidonghuanhang.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // ?建一?居中格式
                // ?置?框
                zidonghuanhang.setBottomBorderColor(HSSFColor.BLACK.index);
                zidonghuanhang.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                zidonghuanhang.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                zidonghuanhang.setBorderRight(HSSFCellStyle.BORDER_THIN);
                zidonghuanhang.setBorderTop(HSSFCellStyle.BORDER_THIN);
                // ??据?容?置特?新?元格?式2 自??行 上下居中左右也居中
                HSSFCellStyle zidonghuanhang2 = wb.createCellStyle();
                zidonghuanhang2.setWrapText(true);// ?置自??行
                zidonghuanhang2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // ?建一?上下居中格式
                zidonghuanhang2.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
                // ?置?框
                zidonghuanhang2.setBottomBorderColor(HSSFColor.BLACK.index);
                zidonghuanhang2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                zidonghuanhang2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                zidonghuanhang2.setBorderRight(HSSFCellStyle.BORDER_THIN);
                zidonghuanhang2.setBorderTop(HSSFCellStyle.BORDER_THIN);
                HSSFCell datacell = null;
                for (int j = 0; j < columnNumber4; j++) {
                    datacell = row.createCell(j);
                    datacell.setCellValue(String.valueOf(dataList4[i][j]));
                    datacell.setCellStyle(zidonghuanhang2);
                }
            }

            // 第六步，?文件存到指定位置
            try {
                String fileName = "D:\\" + new Date(System.currentTimeMillis()).toString().replaceAll(" ", "_").replace(":", "_");
                String fileName_2 = fileName.substring(0, 31) + sheetName4 + ".xls";
                System.out.println(fileName_2);
                FileOutputStream fout = new FileOutputStream(fileName_2);
                wb.write(fout);
                String str = "?出" + fileName4 + "成功！";
                System.out.println(str);
                fout.close();
            } catch (Exception e) {
                e.printStackTrace();
                String str1 = "?出" + fileName4 + "失?！";
                System.out.println(str1);
            }
        } else {
            System.out.println("列?目?度名?三????度要一致");
        }

    }

}
