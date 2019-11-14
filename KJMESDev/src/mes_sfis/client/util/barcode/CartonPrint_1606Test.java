/**
 * Copyright (c) 2008 Pegatron Inc. All Rights Reserved.
 * $Id: CartonPrint_1606Test.java,v 1.1 2017/03/07 04:24:17 Jieyu_Fu Exp $
 */
package mes_sfis.client.util.barcode;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JButton;

import base.client.util.MessageUtil;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.Barcode39;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;

/**
 * The <code></code> class.
 *
 * @version     $Name:  $, $Id: CartonPrint_1606Test.java,v 1.1 2017/03/07 04:24:17 Jieyu_Fu Exp $
 * @author      <a href="mailto:william_tsai@Pegatroncorp.com">William Tsai</a>
 * @history      [>: General, +: New, #: Modification, -: Remove, ~: Bug]
 *          $Log: CartonPrint_1606Test.java,v $
 *          Revision 1.1  2017/03/07 04:24:17  Jieyu_Fu
 *          no message
 *
 *          Revision 1.2  2016/11/07 02:20:53  Jieyu_Fu
 *          改大小
 *
 *          Revision 1.1  2016/10/30 06:42:44  William_Tsai
 *          no message
 *
 *          Revision 1.2  2016/10/30 06:23:55  William
 *          no message
 *
 *          Revision 1.1  2016/10/29 05:51:20  William
 *          no message
 *
 *
 *
 */
public class CartonPrint_1606Test {
	
    public static final String VERSION    = "$Name:  $, $Id: CartonPrint_1606Test.java,v 1.1 2017/03/07 04:24:17 Jieyu_Fu Exp $";
    public static final String COPYRIGHT  = "Copyright (c) 2008 PEGATRON Inc. All Rights Reserved.";
    
	private static final String	RESULT		= "d:/barcodes1606test.pdf";
	static PdfPTable			pdftable;
	Font						f14;
	Font						f12;
	Font						f6;
	Font						f7;

	public static final String	_PEGATRON	= "PEGATRON  Unihan";
	public static final String	_VENDOR		= "VENDOR";
	public static final String	_PEGA_PN	= "Pega\n\nP/N";
	public static final String	_VENDOR_PN	= "Vendor P/N";
	public static final String	_DATACODE	= "DateCode";
	public static final String	_BATCHNO	= "BatchNo";
	public static final String	_QTY		= "QTY";
	public static final String	_NOTE		= "Note";

	String						VENDOR;
	String						PEGA_PN;
	String						VENDOR_PN;
	String						DATACODE;
	String						BATCHNO;
	String						QTY;
	String						NOTE;
	int 						count;
	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException, DocumentException {
		// TODO Auto-generated method stub
		new CartonPrint_1606Test("", "", "", "", "", "", "").createPdf(1,true);
	}

	public CartonPrint_1606Test(String VENDOR, String PEGA_PN, String VENDOR_PN, String DATACODE, String BATCHNO, String QTY, String NOTE) {
		this.VENDOR = VENDOR;
		this.PEGA_PN = PEGA_PN;
		this.VENDOR_PN = VENDOR_PN;
		this.DATACODE = DATACODE;
		this.BATCHNO = BATCHNO;
		this.QTY = QTY;
		this.NOTE = NOTE;
		initFont();
		//initPrintData();
	}

	public void initFont() {
		BaseFont bfChinese;
		try {
			//bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", false);
			bfChinese = BaseFont.createFont("MHei-Medium", "UniCNS-UCS2-H", BaseFont.NOT_EMBEDDED);
			f14 = new Font(bfChinese, 20, Font.BOLD, Color.BLACK);
			f12 = new Font(bfChinese, 13, Font.NORMAL, Color.BLACK);
			f6 = new Font(bfChinese, 7, Font.NORMAL, Color.BLACK);
			f7 = new Font(bfChinese, 8, Font.BOLD, Color.BLACK);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
/*
	public void initPrintData() {
		VENDOR = "KAIJIA";
		PEGA_PN = "13N0-R7P0202";
		VENDOR_PN = "90PC02080100GZ";
		DATACODE = "20141009";
		BATCHNO = "0A0000";
		QTY = "640";
		NOTE = "LCD HINGE CAP 2#";
	}
*/
	public void createPdf(int count,boolean isPrint) throws IOException, DocumentException {
		if (!validateStr()) {
			return;
		}
		try {
			Document document = new Document(new com.lowagie.text.Rectangle(218, 275), 10, 10, 10, 5);
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(RESULT));
			document.open();

			createPdfTable(writer);
			document.add(pdftable);

			document.close();
			writer.close();
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("export ok");
		System.out.println("isPrint : "+isPrint);
		if(isPrint){
			if(!printPdf(RESULT,count)){
				MessageUtil.showInfomationMessage("打印出錯！");
			}
		}
		//Message.showInfomationMessage("資料已匯出到   d:/barcodes.pdf ");

	}

	public boolean validateStr() {
		if (VENDOR == null || VENDOR.equals("")) {
			MessageUtil.showInfomationMessage("請輸入VENDOR！");
			return false;
		}
		if (PEGA_PN == null || PEGA_PN.equals("")) {
			MessageUtil.showInfomationMessage("請輸入PEGA_PN！");
			return false;
		}
		if (VENDOR_PN == null || VENDOR_PN.equals("")) {
			MessageUtil.showInfomationMessage("請輸入VENDOR_PN！");
			return false;
		}
		if (DATACODE == null || DATACODE.equals("")) {
			MessageUtil.showInfomationMessage("請輸入DATECODE！");
			return false;
		}
		if (BATCHNO == null || BATCHNO.equals("")) {
			MessageUtil.showInfomationMessage("請輸入BATCHNO！");
			return false;
		}
		if (QTY == null || QTY.equals("")) {
			MessageUtil.showInfomationMessage("請輸入QTY！");
			return false;
		}
		return true;
	}

	public void createPdfTable(PdfWriter writer) {
		pdftable = new PdfPTable(2);
		pdftable.setTotalWidth(198);
		int[] a = { 58, 140 };
		try {
			pdftable.setWidths(a);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pdftable.setLockedWidth(true);
		pdftable.getDefaultCell().setBorder(15);//PdfPCell.NO_BORDER
		pdftable.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell pega = new PdfPCell(new Paragraph(_PEGATRON, f14));
		pega.setColspan(2); //
		pega.setHorizontalAlignment(Element.ALIGN_LEFT);
		pega.setPadding(4);
		pdftable.addCell(pega);

		pdftable.addCell(createCell(_VENDOR, f7, 14, 7));
		pdftable.addCell(createCell(VENDOR, f6, 14, 11));

		pdftable.addCell(createCell(_PEGA_PN, f7, 34, 7));
		pdftable.addCell(createBarCode(writer, PEGA_PN, a[1]));

		pdftable.addCell(createCell(_VENDOR_PN, f7, 14, 7));
		pdftable.addCell(createCell(VENDOR_PN, f6, 14, 11));

		pdftable.addCell(createCell(_DATACODE, f7, 34, 7));
		pdftable.addCell(createBarCode(writer, DATACODE, a[1]));

		pdftable.addCell(createCell(_BATCHNO, f7, 34, 7));
		pdftable.addCell(createBarCode(writer, BATCHNO, a[1]));

		pdftable.addCell(createCell(_QTY, f7, 34, 7));
		pdftable.addCell(createBarCode(writer, QTY, a[1]));

		pdftable.addCell(createCell(_NOTE, f7, 57, 7));
		pdftable.addCell(createCell(NOTE, f6, 57, 11));

	}

	public PdfPCell createCell(String cellStr, Font font, int rowHeight, int borderNum) {
		PdfPCell newCell = new PdfPCell(new Paragraph(cellStr, font));
		newCell.setBorder(borderNum);
		if (rowHeight > 0) {
			newCell.setMinimumHeight(rowHeight);
		}

		return newCell;
	}

	public PdfPCell createBarCode(PdfWriter writer, String codeStr, int maxWidth) {
		PdfContentByte cd = writer.getDirectContent();
		Barcode39 code39ext = new Barcode39();
		code39ext.setStartStopText(false);
		code39ext.setExtended(true);
		code39ext.setTextAlignment(Element.ALIGN_LEFT);
		code39ext.setBarHeight(16f);
		code39ext.setX(0.5f);

		code39ext.setCode(codeStr);
		Image image39 = code39ext.createImageWithBarcode(cd, null, null);

		PdfPCell barcodeCell = new PdfPCell(image39);
		barcodeCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		barcodeCell.setPadding(4);
		barcodeCell.setBorder(11);
		return barcodeCell;
	}

	public static boolean printPdf(String pdfPath,int c) {
		try {
			for(int i=0;i<c;i++){
				Runtime.getRuntime().exec("cmd.exe /C start acrord32 /P /h " + pdfPath);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	//  Image img = null;
	//	try {
	//		img = Image.getInstance("D:/EAN8.jpg");
	//	} catch (BadElementException e1) {
	//		// TODO Auto-generated catch block
	//		e1.printStackTrace();
	//	} catch (MalformedURLException e1) {
	//		// TODO Auto-generated catch block
	//		e1.printStackTrace();
	//	} catch (IOException e1) {
	//		// TODO Auto-generated catch block
	//		e1.printStackTrace();
	//	}
	//  自動調整圖片適應單元格
	//  topTable.addCell(new PdfPCell(img,true));

}
