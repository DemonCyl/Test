package mes_sfis.client.pdf;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.*;
import mes_sfis.client.model.bean.PackList;

import java.io.FileNotFoundException;

/**
 * Created by Mark_Yang on 2018/4/13.
 */
public class PDFPick extends PDFCreater{


    public PDFPick(String filePath) throws FileNotFoundException, DocumentException {
        super(filePath);


    }


    public void addPage(PackList packList) {

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

            PdfPCell pega = new PdfPCell(new Paragraph("PEGATRON", f13));
            pega.setBorder(15);
            pega.setColspan(3);
            pega.setPadding(4);
            pega.setVerticalAlignment(Element.ALIGN_CENTER);

            pdftable.addCell(pega);
            pdftable.addCell(createCell("清單號:",f12,29,15));
            PdfPCell pick = new PdfPCell(createCell(packList.getPickNo(),f12,29,15));
            pick.setBorder(11);
            pick.setColspan(2);
            pick.setVerticalAlignment(Element.ALIGN_MIDDLE);
            pdftable.addCell(pick);
            //pdftable.addCell(createCell(CloneArray_ChangeStr.NulltoSpace(row.get("VENDOR")),f14,0,8));
            pdftable.addCell(createCell("棧板數:",f12,29,15));
            PdfPCell zhanbNumber = new PdfPCell(createCell(packList.getPalletQty()+"",f12, 29,15));
            zhanbNumber.setBorder(11);
            zhanbNumber.setColspan(2);
            pdftable.addCell(zhanbNumber);

            pdftable.addCell(createCell("棧板箱數:",f12,29,15));
            PdfPCell zhanbxNumber = new PdfPCell(createCell(packList.getPalletCartonNumber()+"",f12, 29,15));
            zhanbxNumber.setBorder(11);
            zhanbxNumber.setColspan(2);
            pdftable.addCell(zhanbxNumber);

            pdftable.addCell(createCell("零散箱數:",f12,29,15));
            PdfPCell lingsxNumber = new PdfPCell(createCell(packList.getCartonQty()+"",f12, 29,15));
            lingsxNumber.setBorder(11);
            lingsxNumber.setColspan(2);
            pdftable.addCell(lingsxNumber);

            document.add(pdftable);



        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public PdfPCell createCell(String cellStr, Font font, int rowHeight, int borderNum) {
            PdfPCell newCell = new PdfPCell(new Paragraph(cellStr, font));
            newCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            newCell.setBorder(borderNum);
            if (rowHeight > 0) {
                newCell.setMinimumHeight(rowHeight);
            }

            return newCell;

    }

    public void close(){
        document.close();
        writer.close();
    }

/*    public static void main(String[] args) throws FileNotFoundException, DocumentException {
        PackList list = new PackList();
        list.setCartonQty(12312);
        list.setPalletQty(999);
        list.setPalletCartonNumber(555);
        list.setPickNo("COP1721-816400008");
        PDFPick pdfPicker =new PDFPick("PackList_TEST.pdf");
        pdfPicker.addPage(list);
        pdfPicker.close();
        pdfPicker.printPdf();
    }*/



}
