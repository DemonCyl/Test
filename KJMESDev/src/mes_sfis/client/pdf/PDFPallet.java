package mes_sfis.client.pdf;

import base.client.util.CloneArray_ChangeStr;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.*;
import mes_sfis.client.ui.barcode.CodeZXing;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Chris1_Liao on 2018/4/2.
 */
public class PDFPallet extends PDFCreater{
    public PDFPallet(String filePath) throws FileNotFoundException, DocumentException {
        super(filePath);
    }
    @Override
    public void addPage(HashMap dataMap) {

        pdftable = new PdfPTable(3);
        pdftable.setTotalWidth(199);
        int[] a = { 28, 29, 43 };
        try {
            pdftable.setWidths(a);
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try{
            pdftable.setLockedWidth(true);
            pdftable.getDefaultCell().setBorder(15);//PdfPCell.NO_BORDER
            pdftable.setHorizontalAlignment(Element.ALIGN_LEFT);
			/*PdfPCell mega = new PdfPCell(new Paragraph("", f14));
			mega.setBorder(0);
			mega.setColspan(3);
			mega.setPadding(4);
			pdftable.addCell(mega);*/
            PdfPCell pega = new PdfPCell(new Paragraph("PEGATRON", f13));
            pega.setBorder(15);
            pega.setColspan(3);
            pega.setPadding(4);
            pdftable.addCell(pega);
            pdftable.addCell(createCell("Vendor:",f14,0,7));
            PdfPCell lega = new PdfPCell(createCell(CloneArray_ChangeStr.NulltoSpace(dataMap.get("vendor")),f14,0,11));
            lega.setBorder(11);
            lega.setColspan(2);
            pdftable.addCell(lega);
            //pdftable.addCell(createCell(CloneArray_ChangeStr.NulltoSpace(row.get("VENDOR")),f14,0,8));
            pdftable.addCell(createCell("PEGA PN:",f14,29,7));
            PdfPCell klli = new PdfPCell(createBarCode(writer, CloneArray_ChangeStr.NulltoSpace(dataMap.get("oemPN")), a[1],17f));
            klli.setBorder(11);
            klli.setColspan(2);
            pdftable.addCell(klli);
            //pdftable.addCell(createBarCode(writer, CloneArray_ChangeStr.NulltoSpace(row.get("OEMPN")), a[1]));
			/*PdfPCell soga = new PdfPCell(new Paragraph("APN:  " + CloneArray_ChangeStr.NulltoSpace(row.get("APPLEPN")) + "     Rev:  " +
			CloneArray_ChangeStr.NulltoSpace(row.get("REV") + "     Config:  " + CloneArray_ChangeStr.NulltoSpace(row.get("CONFIG"))), f12));
			soga.setBorder(15);
			soga.setColspan(2);
			pega.setPadding(10);
			pdftable.addCell(soga);*/
/*            String Cdate =(String)dataMap.get("dateStr");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Calendar ca = Calendar.getInstance();
            ca.setTime(sdf.parse(Cdate));*/
            //String a1 = Cdate.substring(3,4);
            //String a2 = Integer.toString(ca.get(Calendar.WEEK_OF_YEAR));
            //if(a2.length()<2){
            //    a2 = "0" + a2;
            //}
            String a3 = (String)dataMap.get("dateStr");
            String alof = (String)dataMap.get("venderCode")+ (String)dataMap.get("venderSite") + a3;

            pdftable.addCell(createCell("APN:  " + CloneArray_ChangeStr.NulltoSpace(dataMap.get("applePN")),f14,8,7));
            PdfPCell nubi = new PdfPCell(createCell("Rev:  " + dataMap.get("rev") + "       Config:  " + CloneArray_ChangeStr.NulltoSpace(dataMap.get("config")),f14,0,11));
            nubi.setBorder(11);
            nubi.setColspan(2);
            pdftable.addCell(nubi);
            //pdftable.addCell(createCell("Rev:  " + CloneArray_ChangeStr.NulltoSpace(row.get("REV")) + "       Config:  " + CloneArray_ChangeStr.NulltoSpace(row.get("CONFIG")),f14,0,8));
            pdftable.addCell(createCell("Desc:",f14,8,7));
            PdfPCell byby = new PdfPCell(createCell(CloneArray_ChangeStr.NulltoSpace(dataMap.get("desc")),f14,0,11));
            byby.setBorder(11);
            byby.setColspan(2);
            pdftable.addCell(byby);
            //pdftable.addCell(createCell(CloneArray_ChangeStr.NulltoSpace(row.get("DESCRIPTION")),f14,0,11));
            pdftable.addCell(createCell("Date Code:",f14,22,7));
            //pdftable.addCell(createBarCode(writer, CloneArray_ChangeStr.NulltoSpace(row.get("SYSD")), a[1]));
            PdfPCell nued = new PdfPCell(createBarCode(writer, a3, a[1],11f));
            nued.setBorder(11);
            nued.setColspan(2);
            pdftable.addCell(nued);
            //pdftable.addCell(createBarCode(writer, a3, a[1]));
            pdftable.addCell(createCell("L/C:",f14,22,7));
            if("".equals((String)dataMap.get("lc"))){
                //pdftable.addCell(createCell("",f14,34,8));
                PdfPCell mavc = new PdfPCell(createCell("",f14,22,11));
                mavc.setBorder(11);
                mavc.setColspan(2);
                pdftable.addCell(mavc);
            }else{
                //pdftable.addCell(createBarCode(writer, JTlc.getText().trim(), a[1]));
                PdfPCell mavc = new PdfPCell(createBarCode(writer,(String)dataMap.get("lc"), a[1],11f));
                mavc.setBorder(11);
                mavc.setColspan(2);
                pdftable.addCell(mavc);
            }
            pdftable.addCell(createCell("QTY:",f14,22,7));
            PdfPCell mavc = new PdfPCell(createBarCode(writer,(String)dataMap.get("qty"),a[1],11f));
            mavc.setBorder(11);
            mavc.setColspan(2);
            pdftable.addCell(mavc);
            //pdftable.addCell(createBarCode(writer,JTQty.getText().trim(),a[1]));
            pdftable.addCell(createCell("Batch No:",f14,22,7));
            //pdftable.addCell(createBarCode(writer,doubt, a[1]));
            PdfPCell nkar = new PdfPCell(createBarCode(writer,(String)dataMap.get("batch"), a[1],11f));
            nkar.setBorder(11);
            nkar.setColspan(2);
            pdftable.addCell(nkar);
            //pdftable.addCell(createBarCode(writer,JTBt.getText().trim(), a[1]));
            pdftable.addCell(createCell("PALLET No:",f14,22,7));
            PdfPCell cbie = new PdfPCell(createBarCode(writer,CloneArray_ChangeStr.NulltoSpace(dataMap.get("cartonNo")), a[1],11f));
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
            PdfPCell kong = new PdfPCell(createCell(CloneArray_ChangeStr.NulltoSpace(dataMap.get("oemComments")),f13,22,7));
            kong.setBorder(15);
            kong.setColspan(6);
            pdftable.addCell(kong);
            //System.out.println(ZXingCode.getLogoQRCode("https://www.baidu.com/", null, null));
            document.add(pdftable);
        }catch (Exception e) {
            e.printStackTrace();
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
        PDFPallet pdfCreater =new PDFPallet("d://a.pdf");
        pdfCreater.addPage(pDFMap);
        pdfCreater.close();
        //printPdf("c://a.pdf");
    }
}
