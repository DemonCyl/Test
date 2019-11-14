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

    public String sheetName1 = "�u��ܮw�s�d��";
    public String sheetName2 = "�J�w�帹�d�ߵ��G";
    public String sheetName3 = "�u���ISN�d�ߵ��G";
    public String sheetName4 = "���o�x��d�ߵ��G";
    public String sheetName5 = "����c���ƾ�";
    public String sheetName6 = "���}�~�d�ߵ��G";

    public String titleName1 = "�u��ܮw�s�d��";
    public String titleName2 = "�J�w�帹�d�ߵ��G";
    public String titleName3 = "�u���ISN�d�ߵ��G";
    public String titleName4 = "���o�x��d�ߵ��G";
    public String titleName5 = "����c���ƾ�";
    public String titleName6 = "���}�~�d�ߵ��G";

    public String fileName1 = "�u��ܮw�s�d��";
    public String fileName2 = "�J�w�帹�d�ߵ��G";
    public String fileName3 = "�u���ISN�d�ߵ��G";
    public String fileName4 = "���o�x��d�ߵ��G";
    public String fileName5 = "����c���ƾ�";
    public String fileName6 = "���}�~�d�ߵ��G";

    public String[] tableTitleName1 = {"�帹", "�J�w�P�ɥX�P����", "�ƶq", "�X�w�P���١P����P�P��", "�ƶq", "�@�~�H��"};
    public String[] tableTitleName2 = {"ISN", "�Ƹ�", "���","�u��ܦW��","�ާ@��"};
    public String[] tableTitleName3 = {"ISN", "�帹", "�ܦW��", "���A"};
    public String[] tableTitleName4 = {"�x��", "�帹", "�c��", "�ާ@��", "���","ISN","�ܦW"};
    public String[] tableTitleName5 = {"�Z�ŮƸ�", "ISN","�c��"};
    public String[] tableTitleName6 = {"�ܮw", "�x��", "�c��", "ISN", "CSSN", "�J�w���", "�X�w���", "ERROR", "���I", "�ާ@��"};

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
            // �Ĥ@�B�A?�ؤ@?webbook�A??�@?Excel���
            HSSFWorkbook wb = new HSSFWorkbook();
            // �ĤG�B�A�bwebbook���K�[�@?sheet,??Excel��󤤪�sheet
            HSSFSheet sheet = wb.createSheet(sheetName4);
            // sheet.setDefaultColumnWidth(15); //?�@?�m�C?
            for (int i = 0; i < columnNumber4; i++) {
                for (int j = 0; j <= i; j++) {
                    if (i == j) {
                        sheet.setColumnWidth(i, columnWidth4[j] * 256); // ???�m�C�C��?
                    }
                }
            }
            // ?�ز�0�� �]�N�O??
            HSSFRow row1 = sheet.createRow((int) 0);
            row1.setHeightInPoints(50);// ????������
            // �ĤT�B?��??��?����?��style2�H�Φr�^?��headerFont1
            HSSFCellStyle style2 = wb.createCellStyle();
            style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            style2.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
            style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            HSSFFont headerFont1 = (HSSFFont) wb.createFont(); // ?�ئr�^?��
            headerFont1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // �r�^�[��
            headerFont1.setFontName("���^"); // ?�m�r�^?��
            headerFont1.setFontHeightInPoints((short) 15); // ?�m�r�^�j�p
            style2.setFont(headerFont1); // ????��?�m�r�^?��

            HSSFCell cell1 = row1.createCell(0);// ?��??�Ĥ@�C
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columnNumber4 - 1)); // �X�}��0���17�C
            cell1.setCellValue(titleName4); // ?�m��??
            cell1.setCellStyle(style2); // ?�m???��

            // ?�ز�1�� �]�N�O��?
            HSSFRow row = sheet.createRow((int) 1);
            row.setHeightInPoints(37);// ?�m��?����

            // �ĥ|�B�A?�ت�??����?�� �H�Ϊ�?���r�^?��
            HSSFCellStyle style = wb.createCellStyle();
            style.setWrapText(true);// ?�m��??��
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // ?�ؤ@?�~���榡

            style.setBottomBorderColor(HSSFColor.BLACK.index);
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);

            HSSFFont headerFont = (HSSFFont) wb.createFont(); // ?�ئr�^?��
            headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // �r�^�[��
            headerFont.setFontName("���^"); // ?�m�r�^?��
            headerFont.setFontHeightInPoints((short) 10); // ?�m�r�^�j�p
            style.setFont(headerFont); // ????��?�m�r�^?��

            // �ĥ|.�@�B�A?�ت�?���C
            for (int i = 0; i < columnNumber4; i++) {
                HSSFCell cell = row.createCell(i);
                cell.setCellValue(columnName4[i]);
                cell.setCellStyle(style);
            }

            // �Ĥ��B�A?��?����A�}?�m��
            for (int i = 0; i < dataList4.length; i++) {
                row = sheet.createRow((int) i + 2);
                // ??�u?�e?�m�S?�s?����?��1 ��??�� �W�U�~��
                HSSFCellStyle zidonghuanhang = wb.createCellStyle();
                zidonghuanhang.setWrapText(true);// ?�m��??��
                zidonghuanhang.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // ?�ؤ@?�~���榡
                // ?�m?��
                zidonghuanhang.setBottomBorderColor(HSSFColor.BLACK.index);
                zidonghuanhang.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                zidonghuanhang.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                zidonghuanhang.setBorderRight(HSSFCellStyle.BORDER_THIN);
                zidonghuanhang.setBorderTop(HSSFCellStyle.BORDER_THIN);
                // ??�u?�e?�m�S?�s?����?��2 ��??�� �W�U�~�����k�]�~��
                HSSFCellStyle zidonghuanhang2 = wb.createCellStyle();
                zidonghuanhang2.setWrapText(true);// ?�m��??��
                zidonghuanhang2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // ?�ؤ@?�W�U�~���榡
                zidonghuanhang2.setAlignment(HSSFCellStyle.ALIGN_CENTER);// ���k�~��
                // ?�m?��
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

            // �Ĥ��B�A?���s����w��m
            try {
                String fileName = "D:\\" + new Date(System.currentTimeMillis()).toString().replaceAll(" ", "_").replace(":", "_");
                String fileName_2 = fileName.substring(0, 31) + sheetName4 + ".xls";
                System.out.println(fileName_2);
                FileOutputStream fout = new FileOutputStream(fileName_2);
                wb.write(fout);
                String str = "?�X" + fileName4 + "���\�I";
                System.out.println(str);
                fout.close();
            } catch (Exception e) {
                e.printStackTrace();
                String str1 = "?�X" + fileName4 + "��?�I";
                System.out.println(str1);
            }
        } else {
            System.out.println("�C?��?�צW?�T????�׭n�@�P");
        }

    }

}
