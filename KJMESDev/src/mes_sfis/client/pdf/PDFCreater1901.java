package mes_sfis.client.pdf;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import mes_sfis.client.ui.barcode.CodeZXing;
import mes_sfis.client.util.MESGlobe;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;


/**
 * Created by Chris1_Liao on 2018/4/2.
 */
public class PDFCreater1901 {
    protected Document document;
    protected PdfWriter writer;

    protected Font f15;
    protected Font f14;
    protected Font f13;
    protected Font f12;

    protected PdfPTable pdftable;

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

    public PDFCreater1901(String filePath) throws FileNotFoundException, DocumentException {

        if (filePath.indexOf(":") > 0) {
            filePath = filePath.substring(filePath.indexOf(":") + 2);
            filePath = filePath.replace("\\", "");
            filePath = filePath.replace("/", "");
        }

        if (filePath.indexOf(".") > 0) {
            filePath = filePath.split("\\.")[0];
            filePath = filePath + "_" + new Date().getTime() + ".pdf";
        }

        pdfFilePath = MESGlobe.GET_MES_PDF_DIR() + filePath;
        System.out.println(pdfFilePath);
        document = new Document(new Rectangle(240, 120), 5, 5, 0, 0);
        writer = PdfWriter.getInstance(document, new FileOutputStream(pdfFilePath));
        document.open();
        initFont();
    }


    public void addPage(HashMap dataMap) {

        pdftable = new PdfPTable(3);
        pdftable.setTotalWidth(400);
        int[] tableWidths = {100, 100, 200};
        try {
            pdftable.setWidths(tableWidths);
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            pdftable.setLockedWidth(false);
            pdftable.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell pega = new PdfPCell(new Paragraph("HH P/N:" + dataMap.get("HH PN"), f13));
            pega.setBorder(Rectangle.NO_BORDER);
            pega.setColspan(2);
            pdftable.addCell(pega);


            pega = new PdfPCell(new Paragraph("Lot No:" + dataMap.get("Lot No"), f13));
            pega.setBorder(Rectangle.NO_BORDER);
            pega.setColspan(1);
            pega.setHorizontalAlignment(Element.ALIGN_RIGHT);
            pdftable.addCell(pega);

            pega = new PdfPCell(createBarCode(writer, dataMap.get("HH PN").toString(), tableWidths[1], 14f));
            pega.setBorder(Rectangle.NO_BORDER);
            pega.setColspan(2);
            pdftable.addCell(pega);


            pega = new PdfPCell(createBarCode(writer, dataMap.get("Lot No").toString(), tableWidths[1], 14f));
            pega.setBorder(Rectangle.NO_BORDER);
            pega.setHorizontalAlignment(Element.ALIGN_RIGHT);
            pega.setColspan(1);
            pdftable.addCell(pega);

            pega = new PdfPCell(new Paragraph("QTY:" + dataMap.get("QTY"), f13));
            pega.setBorder(Rectangle.NO_BORDER);
            pega.setPaddingTop(4);
            pega.setColspan(2);
            pdftable.addCell(pega);


            pega = new PdfPCell(new Paragraph("PKG ID:" + dataMap.get("PKG ID"), f13));
            pega.setBorder(Rectangle.NO_BORDER);
            pega.setPaddingTop(4);
            pega.setHorizontalAlignment(Element.ALIGN_RIGHT);
            pega.setColspan(1);
            pdftable.addCell(pega);

            pega = new PdfPCell(createBarCode(writer, dataMap.get("QTY").toString(), tableWidths[1], 14f));
            pega.setBorder(Rectangle.NO_BORDER);
            pega.setColspan(2);
            pdftable.addCell(pega);


            pega = new PdfPCell(createBarCode(writer, dataMap.get("PKG ID").toString(), tableWidths[1], 14f));
            pega.setBorder(Rectangle.NO_BORDER);
            pega.setHorizontalAlignment(Element.ALIGN_RIGHT);
            pega.setColspan(1);
            pdftable.addCell(pega);

            pega = new PdfPCell(new Paragraph("Mfr P/N:" + dataMap.get("Mfr P/N"), f13));
            pega.setBorder(Rectangle.NO_BORDER);
            pega.setPaddingTop(4);
            pega.setColspan(2);
            pdftable.addCell(pega);

            pega = new PdfPCell(new Paragraph("", f13));
            pega.setBorder(Rectangle.NO_BORDER);
            pega.setHorizontalAlignment(Element.ALIGN_RIGHT);
            pega.setColspan(1);
            pdftable.addCell(pega);

            pega = new PdfPCell(createBarCode(writer, dataMap.get("Mfr P/N").toString(), tableWidths[1], 14f));
            pega.setBorder(Rectangle.NO_BORDER);
            pega.setColspan(2);
            pdftable.addCell(pega);
            String qrCodeStr = dataMap.get("HH PN").toString() +
                    dataMap.get("QTY").toString() +
                    dataMap.get("Mfr P/N").toString() +
                    dataMap.get("Date Code").toString() +
                    dataMap.get("Lot No").toString() +
                    dataMap.get("PKG ID").toString();
            CodeZXing.CreateImage(qrCodeStr);
            Image imageX = Image.getInstance("D:\\2008.png");
            imageX.scaleAbsolute(50, 50);
            pega = new PdfPCell(imageX);
            pega.setBorder(Rectangle.NO_BORDER);
            pega.setHorizontalAlignment(Element.ALIGN_CENTER);
            pega.setColspan(1);
            pega.setRowspan(3);
            pdftable.addCell(pega);

            pega = new PdfPCell(new Paragraph("Date Code:" + dataMap.get("Date Code"), f13));
            pega.setBorder(Rectangle.NO_BORDER);
            pega.setPaddingTop(4);
            pega.setColspan(2);
            pdftable.addCell(pega);

            pega = new PdfPCell(createBarCode(writer, dataMap.get("Date Code").toString(), tableWidths[1], 14f));
            pega.setBorder(Rectangle.NO_BORDER);
            pega.setColspan(2);
            pdftable.addCell(pega);


/*
            pdftable.addCell(createCell("Vendor:",f15,0,7));
            PdfPCell lega = new PdfPCell(createCell(CloneArray_ChangeStr.NulltoSpace(dataMap.get("vendor")),f15,0,11));
            lega.setBorder(Rectangle.NO_BORDER);
            lega.setColspan(1);
            pdftable.addCell(lega);

            pdftable.addCell(createCell("PEGA PN:",f14,29,7));
            PdfPCell klli = new PdfPCell(createBarCode(writer, CloneArray_ChangeStr.NulltoSpace(dataMap.get("oemPN")), tableWidths[1],11f));
            klli.setBorder(Rectangle.NO_BORDER);
            klli.setColspan(2);
            pdftable.addCell(klli);

            String a3 = (String)dataMap.get("dateStr");


            pdftable.addCell(createCell("APN:  " + CloneArray_ChangeStr.NulltoSpace(dataMap.get("applePN")),f14,8,7));
            PdfPCell nubi = new PdfPCell(createCell("Rev:  " + dataMap.get("rev") + "       Config:  " + CloneArray_ChangeStr.NulltoSpace(dataMap.get("config")),f14,0,11));
            nubi.setBorder(Rectangle.NO_BORDER);
            nubi.setColspan(2);
            pdftable.addCell(nubi);


            pdftable.addCell(createCell("Desc:",f14,8,7));
            PdfPCell byby = new PdfPCell(createCell(CloneArray_ChangeStr.NulltoSpace(dataMap.get("desc")),f14,0,11));
            byby.setBorder(Rectangle.NO_BORDER);
            byby.setColspan(2);
            pdftable.addCell(byby);

            pdftable.addCell(createCell("Date Code:",f14,22,7));

            PdfPCell nued = new PdfPCell(createBarCode(writer, a3, tableWidths[1],11f));
            nued.setBorder(Rectangle.NO_BORDER);
            nued.setColspan(2);
            pdftable.addCell(nued);

            pdftable.addCell(createCell("L/C:",f14,22,7));
            if("".equals((String)dataMap.get("lc"))){
                //pdftable.addCell(createCell("",f14,34,8));
                PdfPCell mavc = new PdfPCell(createCell("",f14,22,11));
                mavc.setBorder(Rectangle.NO_BORDER);
                mavc.setColspan(2);
                pdftable.addCell(mavc);
            }else{

                PdfPCell mavc = new PdfPCell(createBarCode(writer,(String)dataMap.get("lc"),tableWidths[1],11f));
                mavc.setBorder(Rectangle.NO_BORDER);
                mavc.setColspan(2);
                pdftable.addCell(mavc);
            }
            pdftable.addCell(createCell("QTY:",f14,22,7));
            PdfPCell mavc = new PdfPCell(createBarCode(writer,(String)dataMap.get("qty"),tableWidths[1],11f));
            mavc.setBorder(Rectangle.NO_BORDER);
            mavc.setColspan(2);
            pdftable.addCell(mavc);

            pdftable.addCell(createCell("Batch No:",f14,22,7));

            PdfPCell nkar = new PdfPCell(createBarCode(writer,(String)dataMap.get("batch"), tableWidths[1],11f));
            nkar.setBorder(Rectangle.NO_BORDER);
            nkar.setColspan(2);
            pdftable.addCell(nkar);

            pdftable.addCell(createCell("Carton No:",f14,22,7));
            PdfPCell cbie = new PdfPCell(createBarCode(writer,CloneArray_ChangeStr.NulltoSpace(dataMap.get("cartonNo")), tableWidths[1],11f));
            cbie.setBorder(Rectangle.NO_BORDER);
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
            kuga.setBorder(Rectangle.NO_BORDER);
            pdftable.addCell(createCell("Carton id:",f14,56,7));
            pdftable.addCell(kuga);
            pdftable.addCell(createCell("                         Stage:" + dataMap.get("stage"),f14,28,11));
            PdfPCell kong =
                    new PdfPCell(
                            createCell(
                                    CloneArray_ChangeStr.NulltoSpace(
                                            dataMap.get("oemComments")),f15,22,7));
            kong.setBorder(Rectangle.NO_BORDER);
            kong.setColspan(3);
            pdftable.addCell(kong);*/

            document.add(pdftable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void initFont() {
        BaseFont bfChinese;
        try {
            bfChinese = BaseFont.createFont("C:/WINDOWS/Fonts/SIMSUN.TTC,1", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            f15 = new Font(bfChinese, 14f, Font.BOLD, BaseColor.BLACK);
            f14 = new Font(bfChinese, 5f, Font.BOLD, BaseColor.BLACK);
            f13 = new Font(bfChinese, 5f, Font.BOLD, BaseColor.BLACK);
            f12 = new Font(bfChinese, 10f, Font.BOLD, BaseColor.BLACK);
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
        newCell.setBorder(Rectangle.NO_BORDER);
        if (rowHeight > 0) {
            newCell.setMinimumHeight(rowHeight);
        }

        return newCell;
    }


    public PdfPCell createBarCode(PdfWriter writer, String codeStr, int maxWidth, Float BB) {
        PdfContentByte cd = writer.getDirectContent();
        Barcode128 code39ext = new Barcode128();
        code39ext.setSize(1);
        code39ext.setStartStopText(false);
        code39ext.setExtended(true);
        code39ext.setFont(null);
        code39ext.setTextAlignment(Element.ALIGN_LEFT);
        code39ext.setBarHeight(BB);
        code39ext.setX(0.4f);

        code39ext.setCode(codeStr);
        Image image39 = code39ext.createImageWithBarcode(cd, null, null);

        PdfPCell barcodeCell = new PdfPCell(image39);
        barcodeCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        barcodeCell.setPadding(1);
        barcodeCell.setBorder(Rectangle.NO_BORDER);
        return barcodeCell;
    }

    public void close() {
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

        HashMap<String, String> pDFMap = new HashMap<String, String>();
        pDFMap.put("HH PN", "806-20182-B-CT");
        pDFMap.put("Lot No", "SA1-OK-20190513G1");
        pDFMap.put("QTY", "30");
        pDFMap.put("PKG ID", "HKAIJIA2019051300001");
        pDFMap.put("Mfr P/N", "806-20182-B-CT-01");
        pDFMap.put("Date Code", "2019-05-13");
        for(int i = 0;i<100;i++){
            PDFCreater1901 pdfCreater = new PDFCreater1901("d://CARTON.pdf");
            pdfCreater.addPage(pDFMap);
            pdfCreater.close();
        }
    }
}
