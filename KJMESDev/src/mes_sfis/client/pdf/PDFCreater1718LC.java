package mes_sfis.client.pdf;


import base.client.util.CloneArray_ChangeStr;
import com.lowagie.text.*;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.*;
import mes_sfis.client.util.MESGlobe;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;


/**
 * Created by Srx_zhu on 2018/4/2.
 */
public class PDFCreater1718LC {
    protected Document document;
    protected PdfWriter writer;

    protected Font						f14;
    protected Font						f13;
    protected Font						f12;
    protected Font                      f11;

    protected PdfPTable			pdftable1;
    protected PdfPTable              pdf;

    public  void createDir() {
        File dir = new File(MESGlobe.GET_MES_PDF_DIR());
        if (!dir.exists()) {
            System.out.println("正在生成文件夾");
            dir.mkdirs();
        }
    }

    private String pdfFilePath;

    public PDFCreater1718LC(String filePath) throws FileNotFoundException, DocumentException {
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
        createDir();
        pdftable1 = new PdfPTable(3);
        pdftable1.setTotalWidth(199);
        int[] tableWidths = {15, 29, 43};
        try {
            pdftable1.setWidths(tableWidths);

        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            pdftable1.setLockedWidth(true);
            pdftable1.getDefaultCell().setBorder(10);//PdfPCell.NO_BORDER
            pdftable1.setHorizontalAlignment(Element.ALIGN_LEFT);

            pdftable1.addCell(createCell("PEGA PN:", f14, 22, 7));
            PdfPCell klli = new PdfPCell(createBarCode(writer, CloneArray_ChangeStr.NulltoSpace(dataMap.get("oemPN")), tableWidths[1], 14f));
            klli.setBorder(11);
            klli.setColspan(2);
            pdftable1.addCell(klli);

            pdftable1.addCell(createNullCell());
            PdfPCell byby = new PdfPCell(createCell(" ", f14, 0, 11));
            byby.setBorder(0);
            byby.setColspan(2);
            byby.setBorderWidthRight(0);
            byby.setBorderWidthLeft(0);
            pdftable1.addCell(byby);

            pdftable1.addCell(createCell("L/C:", f14, 18, 7));
            PdfPCell mavc = new PdfPCell(createBarCode(writer,CloneArray_ChangeStr.NulltoSpace(dataMap.get("LC1")), tableWidths[1], 14f));
            mavc.setBorder(11);
            mavc.setColspan(2);
            pdftable1.addCell(mavc);

            pdftable1.addCell(createNullCell());
            PdfPCell byby2 = new PdfPCell(createCell(" ", f14, 0, 11));
            byby2.setBorder(0);
            byby2.setColspan(2);
            byby2.setBorderWidthLeft(0);
            byby2.setBorderWidthLeft(0);
            pdftable1.addCell(byby2);

            pdftable1.addCell(createCell("L/C:", f14, 18, 7));
            PdfPCell mavc2 = new PdfPCell(createBarCode(writer, CloneArray_ChangeStr.NulltoSpace(dataMap.get("LC2")), tableWidths[1], 14f));
           // PdfPCell mavc2 = new PdfPCell(createBarCode(writer, "KJA5812VLBT608257000000002", tableWidths[1], 14f));
            mavc2.setBorder(11);
            mavc2.setColspan(2);
            pdftable1.addCell(mavc2);

            pdftable1.addCell(createNullCell());
            PdfPCell byby3 = new PdfPCell(createCell(" ", f14, 0, 11));
            byby3.setBorder(0);
            byby3.setColspan(2);
            byby3.setBorderWidthRight(0);
            byby3.setBorderWidthLeft(0);
            pdftable1.addCell(byby3);

            pdftable1.addCell(createCell("L/C:", f14, 18, 7));
            PdfPCell mavc3 = new PdfPCell(createBarCode(writer,CloneArray_ChangeStr.NulltoSpace(dataMap.get("LC3")), tableWidths[1], 14f));
            mavc3.setBorder(11);
            mavc3.setColspan(2);
            pdftable1.addCell(mavc3);

            pdftable1.addCell(createNullCell());
            PdfPCell byby4 = new PdfPCell(createCell(" ", f14, 0, 11));
            byby4.setBorder(0);
            byby4.setColspan(2);
            byby4.setBorderWidthRight(0);
            byby4.setBorderWidthLeft(0);
            pdftable1.addCell(byby4);

            pdftable1.addCell(createCell("L/C:", f14, 18, 7));
            PdfPCell mavc4 = new PdfPCell(createBarCode(writer,CloneArray_ChangeStr.NulltoSpace(dataMap.get("LC4")), tableWidths[1], 14f));
            mavc4.setBorder(11);
            mavc4.setColspan(2);
            pdftable1.addCell(mavc4);

            pdftable1.addCell(createNullCell());
            PdfPCell byby5 = new PdfPCell(createCell(" ", f14, 0, 11));
            byby5.setBorder(0);
            byby5.setColspan(2);
            byby5.setBorderWidthRight(0);
            byby5.setBorderWidthLeft(0);
            pdftable1.addCell(byby5);

            pdftable1.addCell(createCell("L/C:", f14, 18, 7));
            PdfPCell mavc5 = new PdfPCell(createBarCode(writer,CloneArray_ChangeStr.NulltoSpace(dataMap.get("LC5")), tableWidths[1], 14f));
            mavc5.setBorder(11);
            mavc5.setColspan(2);
            pdftable1.addCell(mavc5);

            pdftable1.addCell(createNullCell());
            PdfPCell byby6 = new PdfPCell(createCell(" ", f14, 0, 11));
            byby6.setBorder(0);
            byby6.setColspan(2);
            byby6.setBorderWidthRight(0);
            byby6.setBorderWidthLeft(0);
            pdftable1.addCell(byby6);

            pdftable1.addCell(createCell("L/C:", f14, 18, 7));
            PdfPCell mavc6 = new PdfPCell(createBarCode(writer,CloneArray_ChangeStr.NulltoSpace(dataMap.get("LC6")), tableWidths[1], 14f));
            mavc6.setBorder(11);
            mavc6.setColspan(2);
            pdftable1.addCell(mavc6);

            pdftable1.addCell(createNullCell());
            PdfPCell byby7 = new PdfPCell(createCell(" ", f14, 0, 11));
            byby7.setBorder(0);
            byby7.setColspan(2);
            byby7.setBorderWidthRight(0);
            byby7.setBorderWidthLeft(0);
            pdftable1.addCell(byby7);

            pdftable1.addCell(createCell("L/C:", f14, 18, 7));
            PdfPCell mavc7 = new PdfPCell(createBarCode(writer,CloneArray_ChangeStr.NulltoSpace(dataMap.get("LC7")), tableWidths[1], 14f));
            mavc7.setBorder(11);
            mavc7.setColspan(2);
            pdftable1.addCell(mavc7);

            pdftable1.addCell(createNullCell());
            PdfPCell byby8 = new PdfPCell(createCell(" ", f14, 0, 11));
            byby8.setBorder(0);
            byby8.setColspan(2);
            byby8.setBorderWidthRight(0);
            byby8.setBorderWidthLeft(0);
            pdftable1.addCell(byby8);


            document.add(pdftable1);


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

    public PdfPCell createNullCell(){
        PdfPCell newCell = new PdfPCell(new Paragraph("",f11));
        newCell.setBorder(0);
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

    public static void main(String[] args) throws Exception {
        HashMap<String,String> pDFMap = new HashMap<>();

        pDFMap.put("LC1","KJA5812VLBT608257000000001");
        pDFMap.put("LC2","KJA5812VLBT608257000000002");
        pDFMap.put("LC3","KJA5812VLBT608257000000002");
        pDFMap.put("LC4","KJA5812VLBT608257000000002");
        pDFMap.put("LC5","KJA5812VLBT608257000000002");
        pDFMap.put("LC6","KJA5812VLBT608257000000002");
        pDFMap.put("LC7","");
        pDFMap.put("oemPN","13E5-3DN3H81");
        PDFCreater1718LC pdfCreater =new PDFCreater1718LC("d://CARTON.pdf");
        pdfCreater.addPage(pDFMap);
        pdfCreater.close();
        // pdfCreater.printPdf();
    }

}





