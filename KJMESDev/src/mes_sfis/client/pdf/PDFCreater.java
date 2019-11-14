package mes_sfis.client.pdf;


import base.client.util.CloneArray_ChangeStr;

import com.lowagie.text.*;
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

import java.util.Date;
import java.util.HashMap;


/**
 * Created by Chris1_Liao on 2018/4/2.
 */
public class PDFCreater {
    protected Document document;
    protected PdfWriter writer;

    protected Font                      f15;
    protected Font						f14;
    protected Font						f13;
    protected Font						f12;

    protected PdfPTable			pdftable;

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

    public PDFCreater(String filePath) throws FileNotFoundException, DocumentException {

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
        int[] tableWidths = { 28, 29, 43 };
        try {
            pdftable.setWidths(tableWidths);
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try{
            pdftable.setLockedWidth(true);
            pdftable.getDefaultCell().setBorder(15);//PdfPCell.NO_BORDER
            pdftable.setHorizontalAlignment(Element.ALIGN_LEFT);


            PdfPCell pega = new PdfPCell(new Paragraph("PEGATRON", f13));
            pega.setBorder(15);
            pega.setColspan(3);
            pega.setPadding(4);
            pdftable.addCell(pega);
            pdftable.addCell(createCell("Vendor:",f15,0,7));
            PdfPCell lega = new PdfPCell(createCell(CloneArray_ChangeStr.NulltoSpace(dataMap.get("vendor")),f15,0,11));
            lega.setBorder(11);
            lega.setColspan(2);
            pdftable.addCell(lega);

            pdftable.addCell(createCell("PEGA PN:",f14,29,7));
            PdfPCell klli = new PdfPCell(createBarCode(writer, CloneArray_ChangeStr.NulltoSpace(dataMap.get("oemPN")), tableWidths[1],17f));
            klli.setBorder(11);
            klli.setColspan(2);
            pdftable.addCell(klli);

            String a3 = (String)dataMap.get("dateStr");


            pdftable.addCell(createCell("APN:  " + CloneArray_ChangeStr.NulltoSpace(dataMap.get("applePN")),f14,8,7));
            PdfPCell nubi = new PdfPCell(createCell("Rev:  " + dataMap.get("rev") + "       Config:  " + CloneArray_ChangeStr.NulltoSpace(dataMap.get("config")),f14,0,11));
            nubi.setBorder(11);
            nubi.setColspan(2);
            pdftable.addCell(nubi);


            pdftable.addCell(createCell("Desc:",f14,8,7));
            PdfPCell byby = new PdfPCell(createCell(CloneArray_ChangeStr.NulltoSpace(dataMap.get("desc")),f14,0,11));
            byby.setBorder(11);
            byby.setColspan(2);
            pdftable.addCell(byby);

            pdftable.addCell(createCell("Date Code:",f14,22,7));

            PdfPCell nued = new PdfPCell(createBarCode(writer, a3, tableWidths[1],11f));
            nued.setBorder(11);
            nued.setColspan(2);
            pdftable.addCell(nued);

            pdftable.addCell(createCell("L/C:",f14,22,7));
            if("".equals((String)dataMap.get("lc"))){
                //pdftable.addCell(createCell("",f14,34,8));
                PdfPCell mavc = new PdfPCell(createCell("",f14,22,11));
                mavc.setBorder(11);
                mavc.setColspan(2);
                pdftable.addCell(mavc);
            }else{

                PdfPCell mavc = new PdfPCell(createBarCode(writer,(String)dataMap.get("lc"),tableWidths[1],11f));
                mavc.setBorder(11);
                mavc.setColspan(2);
                pdftable.addCell(mavc);
            }
            pdftable.addCell(createCell("QTY:",f14,22,7));
            PdfPCell mavc = new PdfPCell(createBarCode(writer,(String)dataMap.get("qty"),tableWidths[1],11f));
            mavc.setBorder(11);
            mavc.setColspan(2);
            pdftable.addCell(mavc);

            pdftable.addCell(createCell("Batch No:",f14,22,7));

            PdfPCell nkar = new PdfPCell(createBarCode(writer,(String)dataMap.get("batch"), tableWidths[1],11f));
            nkar.setBorder(11);
            nkar.setColspan(2);
            pdftable.addCell(nkar);

            pdftable.addCell(createCell("Carton No:",f14,22,7));
            PdfPCell cbie = new PdfPCell(createBarCode(writer,CloneArray_ChangeStr.NulltoSpace(dataMap.get("cartonNo")), tableWidths[1],11f));
            cbie.setBorder(11);
            cbie.setColspan(2);
            pdftable.addCell(cbie);
            String qrCodeStr = CloneArray_ChangeStr.NulltoSpace(dataMap.get("cartonNo")) + "," + CloneArray_ChangeStr.NulltoSpace(dataMap.get("oemPN"))
                    + "," + CloneArray_ChangeStr.NulltoSpace(dataMap.get("applePN")) + "," + dataMap.get("batch") + "," + dataMap.get("qty") + "," + a3;
            if (!"".equals((String)dataMap.get("lc"))) {
                qrCodeStr+=dataMap.get("lc");

            }
            CodeZXing.CreateImage(qrCodeStr);
            Image imageX = Image.getInstance("D:\\2008.png");
            imageX.scaleAbsolute(50,50);
            PdfPCell kuga = new PdfPCell(imageX);
            kuga.setVerticalAlignment(Element.ALIGN_MIDDLE);
            kuga.setBorder(2);
            pdftable.addCell(createCell("Carton id:",f14,56,7));
            pdftable.addCell(kuga);
            pdftable.addCell(createCell("                         Stage:" + dataMap.get("stage"),f14,28,11));
            PdfPCell kong =
                    new PdfPCell(
                            createCell(
                                    CloneArray_ChangeStr.NulltoSpace(
                                            dataMap.get("oemComments")),f15,22,7));
            kong.setBorder(15);
            kong.setColspan(3);
            pdftable.addCell(kong);

            document.add(pdftable);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void initFont() {
        BaseFont bfChinese;
        try {
            bfChinese = BaseFont.createFont("MHei-Medium", "UniCNS-UCS2-H", BaseFont.NOT_EMBEDDED);
            f15 = new Font(bfChinese, 14, Font.BOLD, Color.BLACK);
            f14 = new Font(bfChinese, 5, Font.BOLD, Color.BLACK);
            f13 = new Font(bfChinese, 5, Font.BOLD, Color.BLACK);
            f12 = new Font(bfChinese, 10, Font.BOLD, Color.BLACK);
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
        code39ext.setX(0.5f);

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

    public static void main(String[] args) throws FileNotFoundException, DocumentException {
        HashMap<String,String> pDFMap = new HashMap<String,String>();
        pDFMap.put("vendor","Casetek");
        pDFMap.put("oemPN","BLACK_XXXX");
        pDFMap.put("dateStr","8542");//DATE_CODE
        pDFMap.put("venderCode","I1917000"); //VENDER
        pDFMap.put("venderSite","");
        pDFMap.put("applePN","604-19782");
        pDFMap.put("rev","01");
        pDFMap.put("config","K-28");
        pDFMap.put("desc","ASSY,HOUSING,A,WW,Star,Sparrow 28,JPT3");//DESCRIPTION
        pDFMap.put("lc","");
        pDFMap.put("qty","3");//¼Æ¶q
        pDFMap.put("batch","1");
        pDFMap.put("cartonNo","");//PICK_NO
        pDFMap.put("stage","Star-P1");
        pDFMap.put("oemComments","YFM00Y060-103-N"); //kj_pn
        PDFCreater pdfCreater =new PDFCreater("d://CARTON.pdf");
        pdfCreater.addPage(pDFMap);
        pdfCreater.close();
        //pdfCreater.printPdf();
    }
}
