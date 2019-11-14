package mes_sfis.client.pdf;


import base.client.util.CloneArray_ChangeStr;
import com.lowagie.text.*;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.*;
import mes_sfis.client.ui.barcode.CodeZXing;
import mes_sfis.client.util.MESGlobe;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;


/**
 * Created by Chris1_Liao on 2018/4/2.
 */
public class PDFCreater1606 {
    protected Document document;
    protected PdfWriter writer;

    protected Font						f14;
    protected Font						f13;
    protected Font						f12;
    protected Font                      f11;

    protected PdfPTable			pdftable;
    protected PdfPTable              pdf;

    float lineHeight1 = (float)25.0;
    float lineHeight2 = (float)25.0;

    public HashMap getDataMap() {
        return dataMap;
    }

    public void setDataMap(HashMap dataMap) {
        this.dataMap = dataMap;
    }

    HashMap dataMap;

    public String getPdfFilePath() {
        return pdfFilePath;
    }

    public void setPdfFilePath(String pdfFilePath) {
        this.pdfFilePath = pdfFilePath;
    }

    private String pdfFilePath;

    public PDFCreater1606(String filePath) throws FileNotFoundException, DocumentException {

        if(filePath.indexOf(":")>0){
            filePath = filePath.substring(filePath.indexOf(":")+2);
            filePath=filePath.replace("\\","");
            filePath=filePath.replace("/","");
        }

        if(filePath.indexOf(".")>0){
            filePath = filePath.split("\\.")[0];
            filePath = filePath+"_"+new Date().getTime()+".pdf";
        }

        pdfFilePath = MESGlobe.GET_MES_PDF_DIR()+filePath;
        System.out.println(pdfFilePath);
        document = new Document(new Rectangle(220, 280), 10, 10, 0, 0);
        writer = PdfWriter.getInstance(document,new FileOutputStream(pdfFilePath));
        document.open();
        initFont();
    }



    public void addPage(HashMap dataMap) {

        pdftable = new PdfPTable(3);
        pdftable.setTotalWidth(199);
        int[] tableWidths = {28, 29, 43};
        int[] tablewidths = {50,50};
        try {

            pdftable.setWidths(tableWidths);
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            pdftable.setLockedWidth(true);
            pdftable.getDefaultCell().setBorder(10);//PdfPCell.NO_BORDER
            pdftable.setHorizontalAlignment(Element.ALIGN_LEFT);

            PdfPCell pega = new PdfPCell(new Paragraph("PEGATRON", f13));
            pega.setBorder(15);
            pega.setColspan(3);
            pega.setPadding(4);
            pdftable.addCell(pega);

            pdftable.addCell(createCell("Vendor:", f14, 0, 7));
            PdfPCell lega = new PdfPCell(createCell(CloneArray_ChangeStr.NulltoSpace(dataMap.get("vendor")), f14, 0, 11));
            lega.setBorder(11);
            lega.setColspan(2);
            pdftable.addCell(lega);

            pdftable.addCell(createCell("PEGA PN:", f14, 30, 7));
            PdfPCell klli = new PdfPCell(createBarCode(writer, CloneArray_ChangeStr.NulltoSpace(dataMap.get("oemPN")), tableWidths[1], 14f));
            klli.setBorder(11);
            klli.setColspan(2);
            pdftable.addCell(klli);
            String a3 = (String) dataMap.get("dateStr");

            //計算出當前日期，加上保質期的日期

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");// 可以方便地修改日期格式

            Date date =  dateFormat.parse(dataMap.get("Cdate").toString());

            String hehe = dateFormat.format(date);

            String fmt = "yyyy-MM-dd";
            Calendar cal = new GregorianCalendar();
            int year = cal.get(Calendar.YEAR) + 2;//yy  直接?算年?+2
            int month = cal.get(Calendar.MONTH) + 1;//MM
            int day = cal.get(Calendar.DATE);//dd
            int hour = cal.get(Calendar.HOUR_OF_DAY);//HH
            int minute = cal.get(Calendar.MINUTE);//mm

            if (fmt.indexOf("yy") != -1) {
                fmt = fmt.replaceAll("yy", String.valueOf(year).substring(2));
            }
            if (fmt.indexOf("MM") != -1) {
                fmt = fmt.replaceAll("MM", month < 10 ? "0" + String.valueOf(month) : String.valueOf(month));
            }
            if (fmt.indexOf("HH") != -1) {
                fmt = fmt.replaceAll("HH", hour < 10 ? "0" + String.valueOf(hour) : String.valueOf(hour));
            }
            if (fmt.indexOf("dd") != -1) {
                fmt = fmt.replaceAll("dd", day < 10 ? "0" + String.valueOf(day) : String.valueOf(day));
            }
            if (fmt.indexOf("mm") != -1) {
                fmt = fmt.replaceAll("mm", minute < 10 ? "0" + String.valueOf(minute) : String.valueOf(minute));
            }
                String Tdate=  dateAddYear(hehe);
            String ago = hehe.replaceAll("-", "");
            String later = Tdate.replaceAll("-", "");

            pdftable.addCell(createCell("Desc:", f14, 8, 7));
            PdfPCell byby = new PdfPCell(createCell(CloneArray_ChangeStr.NulltoSpace(dataMap.get("desc")), f14, 0, 11));
            byby.setBorder(11);
            byby.setColspan(2);
            pdftable.addCell(byby);

            pdftable.addCell(createCell("QTY:", f14, 30, 7));
            PdfPCell mavc = new PdfPCell(createBarCode(writer, (String) dataMap.get("qty"), tableWidths[1], 14f));
            mavc.setBorder(11);
            mavc.setColspan(2);
            pdftable.addCell(mavc);

            pdftable.addCell(createCell("Batch No:", f14, 30, 7));
            PdfPCell nkar = new PdfPCell(createBarCode(writer, (String) dataMap.get("batch"), tableWidths[1],14f));
            nkar.setBorder(11);
            nkar.setColspan(2);
            pdftable.addCell(nkar);

            pdftable.addCell(createCell("Carton No:", f14, 30, 7));
            PdfPCell cbie = new PdfPCell(createBarCode(writer, CloneArray_ChangeStr.NulltoSpace(dataMap.get("cartonNo")), tableWidths[1],14f));
            cbie.setBorder(11);
            cbie.setColspan(2);
            pdftable.addCell(cbie);

            /*
            二維碼生成
             */
            String qrCodeStr = CloneArray_ChangeStr.NulltoSpace(dataMap.get("oemPN"))
                    + "/" + CloneArray_ChangeStr.NulltoSpace(ago.substring(2,ago.length())) + "/" +
                    CloneArray_ChangeStr.NulltoSpace(later.substring(2,later.length()));

            CodeZXing.CreateImage(qrCodeStr);
            Image imageX = Image.getInstance("D:\\2008.png");
            imageX.scaleAbsolute(40,40);

            PdfPCell kuga = new PdfPCell(imageX);
            kuga.setBorder(2);
            kuga.setUseAscender(true);
            kuga.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            kuga.setHorizontalAlignment(Element.ALIGN_CENTER);
            kuga.setVerticalAlignment(Element.ALIGN_MIDDLE);
            kuga.setPadding(5);
            kuga.setFixedHeight(lineHeight1+lineHeight2);
            pdftable.addCell(createCell( "\n"+"生產日期:"+ago + "\n\n\n" + "過期日期:"+later + "\n\n\n" + "保存時期:二年", f14,41, 7));
            pdftable.addCell(kuga);
            pdftable.addCell(createCell("", f14, 30, 10));
            PdfPCell tiaoma = new PdfPCell(createBarCode(writer, qrCodeStr, tableWidths[1], 12f));
            tiaoma.setUseAscender(true);
            tiaoma.setHorizontalAlignment(Element.ALIGN_CENTER);
            tiaoma.setVerticalAlignment(Element.ALIGN_MIDDLE);
            tiaoma.setPadding(5);
            tiaoma.setBorder(15);
            tiaoma.setColspan(3);
            pdftable.addCell(tiaoma);
            PdfPCell kong = new PdfPCell(createCell(CloneArray_ChangeStr.NulltoSpace(dataMap.get("oemComments")), f11,12,7));
            kong.setBorder(15);
            kong.setColspan(3);
            kong.setPadding(4);
            pdftable.addCell(kong);
            document.add(pdftable);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initFont() {
        BaseFont bfChinese;
        try {
            bfChinese = BaseFont.createFont("MHei-Medium", "UniCNS-UCS2-H", BaseFont.NOT_EMBEDDED);
            f14 = new Font(bfChinese, 5, Font.BOLD, Color.BLACK);
            f13 = new Font(bfChinese, 18, Font.BOLD, Color.BLACK);
            f12 = new Font(bfChinese, 10, Font.BOLD, Color.BLACK);
            f11 = new Font(bfChinese, 12, Font.BOLD, Color.BLACK);
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public PdfPCell createCell(String cellStr, Font font, int rowHeight, int borderNum) {
        PdfPCell newCell = new PdfPCell(new Paragraph(cellStr, font));
        newCell.setBorder(borderNum);
        if (rowHeight > 0) {
            newCell.setMinimumHeight(rowHeight);
        }

        return newCell;
    }


    public PdfPCell createBarCode(PdfWriter writer, String codeStr, int maxWidth,Float BB) {
        PdfContentByte cd = writer.getDirectContent();
        Barcode128 code39ext = new Barcode128();
        code39ext.setStartStopText(false);
        code39ext.setExtended(true);
        code39ext.setTextAlignment(Element.ALIGN_LEFT);
        code39ext.setBarHeight(BB);
        code39ext.setX(0.6f);

        code39ext.setCode(codeStr);
        Image image39 = code39ext.createImageWithBarcode(cd, null, null);

        PdfPCell barcodeCell = new PdfPCell(image39);
        barcodeCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        barcodeCell.setPadding(1);
        barcodeCell.setBorder(1);
        return barcodeCell;
    }

    public void close(){
        document.close();
        writer.close();

    }

    public boolean printPdf() {
        try {
            Runtime.getRuntime().exec("cmd.exe /C start acrord32 /P /h " + pdfFilePath);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public static String dateAddYear(String str) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dt = sdf.parse(str);
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dt);
        rightNow.add(Calendar.YEAR, 2);// 日期加1年
        Date dt1 = rightNow.getTime();
        String reStr = sdf.format(dt1);
        return reStr;
        }
    public static void main(String[] args) throws FileNotFoundException, DocumentException {
        HashMap<String,String> pDFMap = new HashMap<>();
        /*
        pDFMap.put("vendor","");
        pDFMap.put("oemPN","");
        pDFMap.put("dateStr","");//DATE_CODE
        pDFMap.put("venderCode",""); //VENDER
        pDFMap.put("venderSite","");
        pDFMap.put("applePN","");
        pDFMap.put("rev","");
        pDFMap.put("config","");
        pDFMap.put("desc","");//DESCRIPTION
        pDFMap.put("lc","");
        pDFMap.put("qty","");//數量
        pDFMap.put("batch","");
        pDFMap.put("cartonNo","");//PICK_NO
        pDFMap.put("stage","");
        pDFMap.put("oemComments",""); //kj_pn
*/

        pDFMap.put("vendor","Casetek");
        pDFMap.put("oemPN","13E5-3DN3H81");
        pDFMap.put("dateStr","8542");//DATE_CODE
        pDFMap.put("venderCode","I1917000"); //VENDER
        pDFMap.put("venderSite","");
        pDFMap.put("applePN","604-19782");
        pDFMap.put("rev","01");
        pDFMap.put("config","K-28");
        pDFMap.put("desc","BTN,VOLUME, BLK");//DESCRIPTION
        pDFMap.put("lc","");
        pDFMap.put("qty","182");//數量
        pDFMap.put("batch","1");
        pDFMap.put("cartonNo","");//PICK_NO
        pDFMap.put("stage","T2");
        pDFMap.put("oemComments","YFM0070P0-112-N"); //kj_pn

        PDFCreater1606 pdfCreater =new PDFCreater1606("d://CARTON.pdf");
        pdfCreater.addPage(pDFMap);
        pdfCreater.close();
        pdfCreater.printPdf();
    }

}





