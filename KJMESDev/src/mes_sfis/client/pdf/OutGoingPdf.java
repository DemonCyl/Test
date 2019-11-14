package mes_sfis.client.pdf;


import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.*;
import mes_sfis.client.util.MESGlobe;
import mes_sfis.client.model.bean.OutGoingConfig;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;


public class OutGoingPdf {
    Document document;
    PdfWriter writer;
    Font f14;
    Font f13;
    Font f12;
    Font f11;

    PdfPTable pdftable;


    private static class Header extends PdfPageEventHelper {

        @Override
        public void onEndPage(PdfWriter pdfWriter, Document document) {
//            super.onStartPage(pdfWriter, document);    //To change body of overridden methods use File | Settings | File Templates.
            ColumnText.showTextAligned(pdfWriter.getDirectContent(), Element.ALIGN_CENTER
                    , new Phrase(String.format("page %d", pdfWriter.getPageNumber())), 300, 20, 0);
        }
    }


    public OutGoingPdf(String filPath) throws FileNotFoundException, DocumentException {
        /*String filePath = "Pack70.pdf";*/
        String pdfFilePath = MESGlobe.GET_MES_PDF_DIR() + filPath;
        document = new Document(PageSize.A4, 10, 20, 10, 40);
        writer = PdfWriter.getInstance(document, new FileOutputStream(pdfFilePath));
        Header header = new Header();
        writer.setPageEvent(header);

        document.open();

        initFont();
    }

    public void close() {
        document.close();
        writer.close();
    }

    public void initFont() {
        BaseFont bfChinese;
        try {


            //bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", false);
            bfChinese = BaseFont.createFont("MHei-Medium", "UniCNS-UCS2-H", BaseFont.NOT_EMBEDDED);
            f14 = new Font(bfChinese, 8, Font.BOLD, Color.BLACK);
            f13 = new Font(bfChinese, 13, Font.BOLD, Color.BLACK);
            f12 = new Font(bfChinese, 17, Font.BOLD, Color.BLACK);
            f11 = new Font(bfChinese, 23, Font.BOLD, Color.BLACK);
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public boolean printPdf(String fileName) {
        try {
            Runtime.getRuntime().exec("cmd.exe /C start acrord32 /P /h " + MESGlobe.GET_MES_PDF_DIR() + fileName);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
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

    public PdfPCell createBarCode(PdfWriter writer, String codeStr, int maxWidth, Float BB) {
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

    public void PalletPdfGenerate(List<Hashtable> palletList, Vector<OutGoingConfig> vtOut, int catronNum,String erp_out_oid) {
        int palletNum = 0;
        String palletName = "";
        for (int i = 0; i < palletList.size(); i++) {
            if (!palletName.equals((String) palletList.get(i).get("PALLET_OID")) && (String) palletList.get(i).get("PALLET_OID") != null) {
                palletNum++;
                palletName = (String) palletList.get(i).get("PALLET_OID");
            }
        }
        pdftable = new PdfPTable(3);
        pdftable.setTotalWidth(575);
        float[] a = {150f, 150f, 275f};
        try {
            pdftable.setWidthPercentage(a, PageSize.A4);
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		OutGoingConfig outGoingConfig = new OutGoingConfig();
		if(vtOut != null && vtOut.size() > 0){
			outGoingConfig = vtOut.get(0);
		}
        try {
            pdftable.setLockedWidth(true);
            pdftable.getDefaultCell().setBorder(15);//PdfPCell.NO_BORDER
            pdftable.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell pega = new PdfPCell(new Paragraph("出貨作業單", f11));
            pega.setBorder(15);
            pega.setColspan(3);
            pega.setPadding(4);
            pdftable.addCell(pega);
            PdfPCell pega2 = new PdfPCell(new Paragraph("出貨通知單號：" + outGoingConfig.getErpShipOid() + "      出貨單號：" + erp_out_oid, f14));
            pega2.setBorder(15);
            pega2.setColspan(3);
            pega2.setPadding(5);
            pdftable.addCell(pega2);
			int allCount = 0;
			if(vtOut != null && vtOut.size() > 0){
				for(int m = 0;m < vtOut.size();m++){
					outGoingConfig = vtOut.get(m);
					allCount += outGoingConfig.getPlanQty();
				}
			}
			pega2 = new PdfPCell(new Paragraph("產品總數：" + allCount + "      棧板總數：" + palletNum + "      總零散箱書：" + catronNum + "       提貨機種：" + outGoingConfig.getProjectName(), f14));
			pega2.setBorder(15);
            pega2.setColspan(3);
            pega2.setPadding(5);
			pdftable.addCell(pega2);
			if(vtOut != null && vtOut.size() > 0){
				for(int m = 0;m < vtOut.size();m++){
					outGoingConfig = vtOut.get(m);
					String KJPN = "";
					String palletOid = "";
					for (int i = 0; i < palletList.size(); i++) {
						if(!((String) palletList.get(i).get("KJ_PN")).equals(outGoingConfig.getKjPn()) || !outGoingConfig.getCsPn().equals((String) palletList.get(i).get("OEMPN")))
							continue;
						if (!KJPN.equals((String) palletList.get(i).get("KJ_PN"))) {
							KJPN = (String) palletList.get(i).get("KJ_PN");
							PdfPCell pega5 = new PdfPCell(new Paragraph("鎧嘉料號：" + palletList.get(i).get("KJ_PN"), f13));
							pega5.setBorder(15);
							pega5.setColspan(3);
							pega5.setPadding(5);
							pdftable.addCell(pega5);
							PdfPCell pega3 = new PdfPCell(new Paragraph("產品總數：" + outGoingConfig.getPlanQty() + "        客戶料號：" + outGoingConfig.getCsPn() + "        提貨日期" + outGoingConfig.getLogSystemDate(), f14));
							pega3.setBorder(15);
							pega3.setColspan(3);
							pega3.setPadding(5);
							pdftable.addCell(pega3);
							PdfPCell pega6 = new PdfPCell(new Paragraph("棧板信息：", f14));
							pega6.setBorder(15);
							pega6.setColspan(3);
							pega6.setPadding(5);
							pdftable.addCell(pega6);
							if (!palletOid.equals((String) palletList.get(i).get("PALLET_OID")) && (String) palletList.get(i).get("PALLET_OID") != null) {
								palletOid = (String) palletList.get(i).get("PALLET_OID");
								PdfPCell pega7 = new PdfPCell(new Paragraph("棧板號：" + palletList.get(i).get("PALLET_NO") + "                     棧板總箱數：" + palletList.get(i).get("PALLET_NO"), f14));
								pega7.setBorder(15);
								pega7.setColspan(3);
								pega7.setPadding(5);
								pdftable.addCell(pega7);
							}
							PdfPCell pega8 = new PdfPCell(new Paragraph("               箱號：" + palletList.get(i).get("CARTON_NO") + "                     箱內產品數：" + palletList.get(i).get("QTY"), f14));
							pega8.setBorder(15);
							pega8.setColspan(3);
							pega8.setPadding(5);
							pdftable.addCell(pega8);
						} else {
							if (!palletOid.equals((String) palletList.get(i).get("PALLET_OID")) && (String) palletList.get(i).get("PALLET_OID") != null) {
								palletOid = (String) palletList.get(i).get("PALLET_OID");
								PdfPCell pega7 = new PdfPCell(new Paragraph("棧板號：" + palletList.get(i).get("PALLET_NO") + "                     棧板總箱數：" + palletList.get(i).get("PALLET_NO"), f14));
								pega7.setBorder(15);
								pega7.setColspan(3);
								pega7.setPadding(5);
								pdftable.addCell(pega7);

							}
							PdfPCell pega8 = new PdfPCell(new Paragraph("               箱號：" + palletList.get(i).get("CARTON_NO") + "                     箱內產品數：" + palletList.get(i).get("QTY"), f14));
							pega8.setBorder(15);
							pega8.setColspan(3);
							pega8.setPadding(5);
							pdftable.addCell(pega8);
						}
					}
					
				}
			}
            document.add(pdftable);
        } catch (
                Exception e
                )

        {
            e.printStackTrace();
        } finally{
			close();
		}

    }

    public void CartonPdfGenerate(List<Hashtable> palletList, List<Hashtable> cartonList, Vector<OutGoingConfig> vtOut) {
        int palletNum = 0;
        String palletName = "";
        if(palletList==null){
            System.out.println("進來啦！111111111111111111111");
        }else{
            System.out.println("進來啦！2222222222222222222222");
            for (int i = 0; i < palletList.size(); i++) {
                if (!palletName.equals((String) palletList.get(i).get("PALLET_OID"))) {
                    palletNum++;
                    palletName = (String) palletList.get(i).get("PALLET_OID");
                }
            }
        }

        System.out.println("進來啦！3333333333333333333333");

        pdftable = new PdfPTable(3);
        pdftable.setTotalWidth(575);
        float[] a = {150f, 150f, 275f};
        try {
            pdftable.setWidthPercentage(a, PageSize.A4);
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		OutGoingConfig outGoingConfig = new OutGoingConfig();
        try {
            pdftable.setLockedWidth(true);
            pdftable.getDefaultCell().setBorder(15);//PdfPCell.NO_BORDER
            pdftable.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell pega = new PdfPCell(new Paragraph("出貨作業單", f11));
            pega.setBorder(15);
            pega.setColspan(3);
            pega.setPadding(4);
            pdftable.addCell(pega);
            PdfPCell pega2 = new PdfPCell(new Paragraph("出貨單號：" + outGoingConfig.getErpShipOid(), f13));
            pega2.setBorder(15);
            pega2.setColspan(3);
            pega2.setPadding(5);
            pdftable.addCell(pega2);
            System.out.println("進來啦！444444444444444444444444");
			if(vtOut != null && vtOut.size() > 0){
				for(int m = 0;m < vtOut.size();m++){
					outGoingConfig = vtOut.get(m);
					if (palletList == null) {
						System.out.println("進來啦！5555555555555555555555555");
						PdfPCell pega3 = new PdfPCell(new Paragraph("棧板總數：" + "0" + "      總零散箱書：" + cartonList.size() + "        產品總數：" + outGoingConfig.getPlanQty() + "       提貨機種：" + outGoingConfig.getProjectName() + "        客戶料號：" + outGoingConfig.getCsPn(), f14));
						pega3.setBorder(15);
						pega3.setColspan(3);
						pega3.setPadding(5);
						pdftable.addCell(pega3);
					} else {
						PdfPCell pega3 = new PdfPCell(new Paragraph("棧板總數：" + palletNum + "      總零散箱書：" + cartonList.size() + "        產品總數：" + outGoingConfig.getPlanQty() + "       提貨機種：" + outGoingConfig.getProjectName() + "        客戶料號：" + outGoingConfig.getCsPn(), f14));
						pega3.setBorder(15);
						pega3.setColspan(3);
						pega3.setPadding(5);
						pdftable.addCell(pega3);
					}

					PdfPCell pega4 = new PdfPCell(new Paragraph("提貨日期" + outGoingConfig.getLogSystemDate(), f14));
					pega4.setBorder(15);
					pega4.setColspan(3);
					pega4.setPadding(5);
					pdftable.addCell(pega4);
					String KJPN = "";
					for (int i = 0; i < cartonList.size(); i++) {
						if (!KJPN.equals((String) cartonList.get(i).get("KJ_PN"))) {
							KJPN = (String) cartonList.get(i).get("KJ_PN");
							PdfPCell pega5 = new PdfPCell(new Paragraph("鎧嘉料號：" + cartonList.get(i).get("KJ_PN"), f13));
							pega5.setBorder(15);
							pega5.setColspan(3);
							pega5.setPadding(5);
							pdftable.addCell(pega5);
							PdfPCell pega6 = new PdfPCell(new Paragraph("零散箱信息：", f14));
							pega6.setBorder(15);
							pega6.setColspan(3);
							pega6.setPadding(5);
							pdftable.addCell(pega6);
							PdfPCell pega8 = new PdfPCell(new Paragraph("零散箱號：" + cartonList.get(i).get("CARTON_NO") + "                     箱內產品數：" + cartonList.get(i).get("QTY"), f14));
							pega8.setBorder(15);
							pega8.setColspan(3);
							pega8.setPadding(5);
							pdftable.addCell(pega8);

						} else {
							PdfPCell pega8 = new PdfPCell(new Paragraph("零散箱號：" + cartonList.get(i).get("CARTON_NO") + "                     箱內產品數：" + cartonList.get(i).get("QTY"), f14));
							pega8.setBorder(15);
							pega8.setColspan(3);
							pega8.setPadding(5);
							pdftable.addCell(pega8);
						}
					}
					pdftable.addCell(createCell("PEGA PN:", f14, 29, 7));
					
				}
			}
            document.add(pdftable);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}