/**
 * Copyright (c) 2008 Pegatron Inc. All Rights Reserved.
 * $Id: Code_1709.java,v 1.3 2017/11/11 01:10:28 Lucy6_Lu Exp $
 */

package mes_sfis.client.ui.barcode;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Calendar;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import base.client.util.CloneArray_ChangeStr;
import base.client.util.component.PMIDComboBox;
import base.client.util.component.P_Component_Meta;
import base.client.util.MessageUtil;
import base.client.util.StringUtil;
import base.client.enums.ToolBarItem;

import com.klg.jclass.table.data.JCEditableVectorDataSource;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.Barcode128;
import com.lowagie.text.pdf.BarcodeEAN;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;

import base.client.ui.BasePanel;
import base.enums.DataSourceType;
import base.vo.ResultVO;
import base.vo.BaseVO;
import base.client.util.BaseServletAgent;
import base.enums.CommandName;
import base.vo.UI_InitVO;
import mes_sfis.client.util.barcode.CartonPrint_1;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import java.io.IOException;
import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 * The <code>Code_1709</code> class.
 *
 * @version     $Name:  $, $Id: Code_1709.java,v 1.3 2017/11/11 01:10:28 Lucy6_Lu Exp $
 * @author      <a href="mailto:william_tsai@Pegatroncorp.com">William Tsai</a>
 * @history      [>: General, +: New, #: Modification, -: Remove, ~: Bug]
 *
 */
public class Code_1709 extends BasePanel{
	
	
    public static final String VERSION    = "$Name:  $, $Id: Code_1709.java,v 1.3 2017/11/11 01:10:28 Lucy6_Lu Exp $";
    public static final String COPYRIGHT  = "Copyright (c) 2008 PEGATRON Inc. All Rights Reserved.";
	Code_1709 self;
	Font						f14;
	Font						f13;
	Font						f12;
	static PdfPTable			pdftable;
	private JTextField JTQty;
	private JTextField JTlc;
	private JTextField JTBt;
	private JTextField JTBtend;
	private JTextField JTBtal;
	private JTextField JTVenC;
	private JTextField JTSite;
	private JTextField JTStage;
	private JTextField JTDesc;
	Hashtable row = null;
	private JTextField jtfLabelQTY;
	private JCheckBox jcboxPrint;
	private JTextField JTdate;
	private JTextField JTqq;
	JTextArea jta;
	public Code_1709(UI_InitVO uiVO) {
		super(uiVO);
		init(); 
	}
	void init(){
		self=this;
		self.setUILayout(null);
		
		JLabel JLVenC = new JLabel("(P) Customer P/N:");
		JLVenC.setBounds(80,70,95,25);
		JTVenC = new JTextField(20);
        JTVenC.setBounds(210,70,150,25);
		JTVenC.setEditable(false);
        add(JLVenC);
        add(JTVenC);
		
		JLabel JLStage = new JLabel("(1P) Manufacturer P/N:");
		JLStage.setBounds(80,105,95,25);
		JTStage = new JTextField(20);
        JTStage.setBounds(210,105,150,25);
		JTStage.setEditable(false);
        add(JLStage);
        add(JTStage);
		
		JLabel JLQty = new JLabel("(Q) QTY:");
		JLQty.setBounds(80,135,95,25);
		JTQty = new JTextField(20);
        JTQty.setBounds(210,135,150,25);
		JTQty.setEditable(false);
        add(JLQty);
        add(JTQty);
		
		JLabel JLlc = new JLabel("(V) Vendor Code:");
		JLlc.setBounds(new Rectangle(80,165,95,25));
		add(JLlc,null);
		JTlc = new JTextField(20);
        JTlc.setBounds(210,165,150,25);
		JTlc.setEditable(false);
        add(JTlc);
		
		JLabel JLBt = new JLabel("(1T) Lot No:");
		JLBt.setBounds(new Rectangle(80,195,95,25));
		add(JLBt,null);
		JTBt = new JTextField(20);
        JTBt.setBounds(210,195,150,25);
		JTBt.setEditable(false);
        add(JTBt);
		
		JLabel JLdate = new JLabel("(9D) Date Code:");
		JLdate.setBounds(new Rectangle(80,225,95,25));
		add(JLdate,null);
		JTdate=new JTextField(20);
		JTdate.setBounds(new Rectangle(210,225,150,25));
		JTdate.setEditable(false);
		add(JTdate);
		
		JLabel JLSite = new JLabel("(M) Manufacturer:");
		JLSite.setBounds(385,70,95,25);
		JTSite = new JTextField(20);
        JTSite.setBounds(485,70,150,25);
		JTSite.setEditable(false);
        add(JLSite);
        add(JTSite);
		
		JLabel JLBtend = new JLabel("(4L) Country of Origin:");
		JLBtend.setBounds(new Rectangle(385,105,95,25));
		add(JLBtend,null);
		JTBtend = new JTextField(20);
        JTBtend.setBounds(485,105,150,25);
		JTBtend.setEditable(false);
        add(JTBtend);
		
		JLabel JLBtal = new JLabel("REV : ");
		JLBtal.setBounds(new Rectangle(385,135,95,25));
		add(JLBtal,null);
		JTBtal = new JTextField(20);
        JTBtal.setBounds(485,135,150,25);
		JTBtal.setEditable(false);
        add(JTBtal);
		
		//17-08-03
		JLabel JLDesc = new JLabel("DESC : ");
		JLDesc.setBounds(new Rectangle(385,165,95,25));
		add(JLDesc,null);
		JTDesc = new JTextField(20);
        JTDesc.setBounds(485,165,150,25);
		JTDesc.setEditable(false);
        add(JTDesc);
		
		JLabel jLabel_LabelQTY = new JLabel("打印張數 : ",JLabel.RIGHT);
		jLabel_LabelQTY.setBounds(new Rectangle(10,295,100,25));
		add(jLabel_LabelQTY,null);
		
		jtfLabelQTY = new JTextField();
		jtfLabelQTY.setText("1");
		jtfLabelQTY.setBounds(new Rectangle(120,295,250,25));
		add(jtfLabelQTY,null);
		jtfLabelQTY.setEditable(false);
		
		jcboxPrint = new JCheckBox("連線打印機");
		jcboxPrint.setBounds(new Rectangle(400,295,100,25));
		add(jcboxPrint,null);
		jcboxPrint.setSelected(true);
		
		jta = new JTextArea();
		jta.setBounds(new Rectangle(80,325,400,200));
		add(jta,null);
		jta.setEditable(false);
		
	    runTask();
		setStatus(new int[]{ToolBarItem.Print,ToolBarItem.Cancel,ToolBarItem.Exit});
		
	}
	
	public void singleQuery() {
		setMessage("");
		try{
			setStatus(new int[]{ToolBarItem.Print,ToolBarItem.Cancel,ToolBarItem.Exit});
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void cancel() {
		JTQty.setText("");
		jtfLabelQTY.setText("1");
		JTQty.setEditable(false);
		jtfLabelQTY.setEditable(false);
		jta.setText("");
		setStatus(new int[]{ToolBarItem.SingleQuery,ToolBarItem.Cancel,ToolBarItem.Exit});
	}
	
	public void print() {
		
		String printNum=jtfLabelQTY.getText().trim();
		String[] route = null;
		try {
			route = getfile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] desc = SelectDeviceDesc(route[2]);	
		String DEVICEDESC = desc[0];
		String DEVICEIMGTYPE = desc[1];
		if(printNum.equals("")){
			setMessage("請輸入打印份數!");
			return;
		}
		
		if(!isMatcherPatternStr(printNum,"[0-9]")){
			setMessage("請輸入打印份數(數字 0-9 )!");
			return;
		}
		
		if(!isMatcherPatternStr(JTQty.getText().trim(),"[0-9]")){
			setMessage("QTY請輸入數字");
			return;
		}
			doPrint(jcboxPrint.isSelected(),printNum,DEVICEDESC,DEVICEIMGTYPE);
			Printdo(jcboxPrint.isSelected(),printNum,DEVICEDESC,DEVICEIMGTYPE);
		    try {
				Insert_DB_Mes();
			} catch (Exception e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	public PdfPCell createBarCode(PdfWriter writer, String codeStr, int maxWidth) {
		PdfContentByte cd = writer.getDirectContent();
		Barcode128 code39ext = new Barcode128();
		code39ext.setStartStopText(false);
		code39ext.setExtended(true);
		code39ext.setFont(null);// ?示?片下字符串?容
		code39ext.setTextAlignment(Element.ALIGN_LEFT);
		code39ext.setBarHeight(13f);
		code39ext.setX(0.5f);

		code39ext.setCode(codeStr);
		Image image39 = code39ext.createImageWithBarcode(cd, null, null);

		PdfPCell barcodeCell = new PdfPCell(image39);
		barcodeCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		barcodeCell.setPadding(0);
		barcodeCell.setBorder(0);
		return barcodeCell;
	}
	private void Printdo(boolean isPrint,String printNum,String DEVICEDESC,String DEVICEIMGTYPE){
		int num=Integer.parseInt(printNum);
		String filePath = "d:/Honor.pdf";
		try{
			Document document = new Document(new com.lowagie.text.Rectangle(170, 280), 05, 05,0,0);
			PdfWriter writer = PdfWriter.getInstance(document,new FileOutputStream(filePath));
			document.open();
			for(int i=1;i<=num;i++){
				PdfTablecreate(writer,null);
				document.add(pdftable);
				if(JTVenC.getText().trim().equals("")){
					JTVenC.setText("*");
				}
				if(JTStage.getText().trim().equals("")){
					JTStage.setText("*");
				}
				if(JTQty.getText().trim().equals("")){
					JTQty.setText("*");
				}
				if(JTlc.getText().trim().equals("")){
					JTlc.setText("*");
				}
				if(JTBt.getText().trim().equals("")){
					JTBt.setText("*");
				}
				if(JTdate.getText().equals("")){
					JTdate.setText("*");
				}
				if(JTSite.getText().trim().equals("")){
					JTSite.setText("*");
				}
				if(JTBtend.getText().trim().equals("")){
					JTBtend.setText("*");
				}
				
				//17-08-03
				if(JTDesc.getText().trim().equals("")){
					JTDesc.setText("*");
				}
				String desc = JTDesc.getText().trim();
			    int k = desc.length()*8;
				Image img3 = Image.getInstance(DEVICEIMGTYPE);
				img3.setAbsolutePosition(34f, 245.5f);
				img3.scaleAbsolute(k,8);
				document.add(img3);
				Image img4 = Image.getInstance(DEVICEDESC);
				img4.setAbsolutePosition(125f, 54f);
				img4.scaleAbsolute(20, 6);
				document.add(img4);
			}
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
		jta.setText("打印成功");
		System.out.println("export ok");
		//setStatus(new int[]{ToolBarItem.Cancel,ToolBarItem.Exit});
		if(isPrint){
			if(!printPdf(filePath)){
				setMessage("");
			}
		}else{
			setMessage("資料已匯出到   d:/HuaWei.pdf和d:/Honor.pdf ");
		}
	}
	
	private void doPrint(boolean isPrint,String printNum,String DEVICEDESC,String DEVICEIMGTYPE){
		int num=Integer.parseInt(printNum);
		String filePath = "d:/HuaWei.pdf";
		try{
			Document document = new Document(new com.lowagie.text.Rectangle(170, 280), 05, 05, 00, 00);
			PdfWriter writer = PdfWriter.getInstance(document,new FileOutputStream(filePath));
			document.open();
			for(int i=1;i<=num;i++){
				createPdfTable(writer,null);
				document.add(pdftable);
				if(JTVenC.getText().trim().equals("")){
					JTVenC.setText("*");
				}
				if(JTStage.getText().trim().equals("")){
					JTStage.setText("*");
				}
				if(JTQty.getText().trim().equals("")){
					JTQty.setText("*");
				}
				if(JTlc.getText().trim().equals("")){
					JTlc.setText("*");
				}
				if(JTBt.getText().trim().equals("")){
					JTBt.setText("*");
				}
				if(JTdate.getText().equals("")){
					JTdate.setText("*");
				}
				if(JTSite.getText().trim().equals("")){
					JTSite.setText("*");
				}
				if(JTBtend.getText().trim().equals("")){
					JTBtend.setText("*");
				}		
				//17-08-03
				if(JTDesc.getText().trim().equals("")){
					JTDesc.setText("*");
				}
				
				ZXingCode.getLogoQRCode("P" + JTVenC.getText().trim() + " 1P" + JTStage.getText().trim() + " Q" + JTQty.getText().trim() + " V" + JTlc.getText().trim()
				 + " 1T" + JTBt.getText().trim() + " 9D" + JTdate.getText().trim() + " M" + JTSite.getText().trim() + " 4L" + JTBtend.getText().trim(), null, null);
				Image img = Image.getInstance("D:/test.png");
				img.scalePercent(120f);
				img.setAbsolutePosition(115f,145f);
				document.add(img);
				
				ZXingCode.getLogoQRCode("1D" + JTdate.getText().trim() + " 1Q" + JTQty.getText().trim(), null, null);
				Image img2 = Image.getInstance("D:/test.png");
				img2.scalePercent(90f);
				img2.setAbsolutePosition(135f,53f);
				document.add(img2);
				
				String desc = JTDesc.getText().trim();
			    int k = desc.length()*8;
			    Image img3 = Image.getInstance(DEVICEIMGTYPE);
				img3.setAbsolutePosition(34f, 245.5f);
				img3.scaleAbsolute(k, 8);
				//img3.scaleAbsolute(img3.getIconWidth(), 8);
				document.add(img3);
				Image img4 = Image.getInstance(DEVICEDESC);
				
				img4.setAbsolutePosition(100f, 79f);
				img4.scaleAbsolute(20, 6);
				//img4.scaleAbsolute(img4.getIconWidth(), 6);
				document.add(img4);
				
				/*Paragraph p1 = new Paragraph("LEFT");
				p1.add(new Chunk(new LineSeparator()));
				document.add(p1);*/
				
			}
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
		jta.setText("打印成功");
		System.out.println("export ok");
		//setStatus(new int[]{ToolBarItem.Cancel,ToolBarItem.Exit});
		if(isPrint){
			if(!printPdf(filePath)){
				setMessage("");
			}
			/*filePath = "d:/Honor.pdf";
			printPdf(filePath);*/
		}else{
			setMessage("資料已匯出到   d:/HuaWei.pdf和d:/Honor.pdf ");
		}
	}
	public void PdfTablecreate(PdfWriter writer,String mmo) {
		initFont();
		pdftable = new PdfPTable(3);
		pdftable.setTotalWidth(150);
		int[] a = { 90, 5,5};
		try {
			pdftable.setWidths(a);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try{
			
			pdftable.setLockedWidth(true);
			pdftable.getDefaultCell().setBorder(0);//PdfPCell.NO_BORDER
			pdftable.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			PdfPCell lega = new PdfPCell(createCell("(P) Customer P/N: " + JTVenC.getText().trim(),f14,0,0));
			lega.setBorder(0);
			lega.setColspan(3);
			pdftable.addCell(lega);
			
			PdfPCell nued = new PdfPCell(createBarCode(writer, "P" + JTVenC.getText().trim(), a[1]));
			nued.setBorder(0);
			nued.setColspan(3);
			pdftable.addCell(nued);
			
			//PdfPCell desc = new PdfPCell(createCell("DESC: " + JTDesc.getText().trim(), f14,0,0));
			PdfPCell desc = new PdfPCell(createCell("DESC: ", f14,0,0));
			desc.setBorder(0);
			desc.setColspan(3);
			pdftable.addCell(desc);
			
			PdfPCell nued1 = new PdfPCell(createBarCode(writer,JTVenC.getText().trim(), a[1]));
			nued1.setBorder(0);
			nued1.setColspan(3);
			pdftable.addCell(nued1);

			PdfPCell byby = new PdfPCell(createCell("(1P) Manufacturer P/N: " + JTStage.getText().trim(),f14,0,0));
			byby.setBorder(0);
			byby.setColspan(3);
			pdftable.addCell(byby);

			PdfPCell nkar = new PdfPCell(createBarCode(writer,JTStage.getText().trim(), a[1]));
			nkar.setBorder(0);
			nkar.setColspan(3);
			pdftable.addCell(nkar);
			
			PdfPCell kong = new PdfPCell(createCell("(Q) QTY: " + JTQty.getText().trim(),f14,0,0));
			kong.setBorder(0);
			kong.setColspan(3);
			pdftable.addCell(kong);

			PdfPCell cbie = new PdfPCell(createBarCode(writer,"Q" + JTQty.getText().trim(), a[1]));
			cbie.setBorder(0);
			cbie.setColspan(3);
			pdftable.addCell(cbie);

			PdfPCell mavc = new PdfPCell(createCell("(V) Vendor Code: " + JTlc.getText().trim(),f14,0,0));
			mavc.setBorder(0);
			mavc.setColspan(3);
			pdftable.addCell(mavc);

			PdfPCell mavcx = new PdfPCell(createBarCode(writer, "V" + JTlc.getText().trim(), a[1]));
			mavcx.setBorder(0);
			mavcx.setColspan(3);
			pdftable.addCell(mavcx);

			PdfPCell afct = new PdfPCell(createCell("(1T) Lot No: " + JTBt.getText().trim(),f14,0,0));
			afct.setBorder(0);
			afct.setColspan(3);
			pdftable.addCell(afct);
			
			PdfPCell vost = new PdfPCell(createBarCode(writer,"1T" + JTBt.getText().trim(),a[1]));
			vost.setBorder(0);
			vost.setColspan(3);
			pdftable.addCell(vost);
			
			PdfPCell fge1 = new PdfPCell(createCell("(9D) Date Code: " + JTdate.getText().trim(),f14,0,0));
			fge1.setBorder(0);
			fge1.setColspan(3);
			pdftable.addCell(fge1);
			
			PdfPCell edsf = new PdfPCell(createBarCode(writer,"9D" + JTdate.getText().trim(), a[1]));
			edsf.setBorder(0);
			edsf.setColspan(3);
			pdftable.addCell(edsf);
			
			PdfPCell fge2 = new PdfPCell(createCell("(M) Manufacturer: " + JTSite.getText().trim(),f14,0,0));
			fge2.setBorder(0);
			fge2.setColspan(3);
			pdftable.addCell(fge2);
			
			PdfPCell edsf2 = new PdfPCell(createBarCode(writer,"M" + JTSite.getText().trim(), a[1]));
			edsf2.setBorder(0);
			edsf2.setColspan(3);
			pdftable.addCell(edsf2);
			
			PdfPCell fge3 = new PdfPCell(createCell("(4L) Country of Origin: " + JTBtend.getText().trim(),f14,0,0));
			fge3.setBorder(0);
			fge3.setColspan(3);
			pdftable.addCell(fge3);
			
			PdfPCell edsf3 = new PdfPCell(createBarCode(writer,"4L" + JTBtend.getText().trim(), a[1]));
			edsf3.setBorder(0);
			edsf3.setColspan(3);
			pdftable.addCell(edsf3);
			
			PdfPCell fge4 = new PdfPCell(createCell("REV: " + JTBtal.getText().trim(),f14,0,0));
			fge4.setBorder(0);
			fge4.setColspan(3);
			pdftable.addCell(fge4);
			
			//PdfPCell QQ = new PdfPCell(createCell(JTqq.getText().trim(),f14,0,0));
			//System.out.println(JTqq.getText().trim());
			//QQ.setBorder(0);
			//QQ.setColspan(2);
			//QQ.setUseAscender(true);
			//QQ.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			//QQ.setHorizontalAlignment(Element.ALIGN_RIGHT);
			//pdftable.addCell(QQ);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static boolean printPdf(String pdfPath){   
        try{   
            Runtime.getRuntime().exec("cmd.exe /C start acrord32 /P /h " + pdfPath);   
            return true;   
        }catch(Exception e){   
            e.printStackTrace();   
            return false;   
        }   
    }

	public boolean isMatcherPatternStr(String valueStr,String patternStr){
		    int charNum=0;
		    char[] chs = valueStr.toCharArray();
			for(char ch:chs){
				Pattern pattern = Pattern.compile(patternStr);
				Matcher matcher = pattern.matcher(String.valueOf(ch));
				if(matcher.matches() == false){
					JOptionPane.showMessageDialog(null, "請輸入正確的打印條碼內容！");
					return false;
				}
			}
			return true;
	}
	public PdfPCell createCell(String cellStr, Font font, int rowHeight, int borderNum) {
		PdfPCell newCell = new PdfPCell(new Paragraph(cellStr, font));
		newCell.setBorder(borderNum);
		if (rowHeight > 0) {
			newCell.setMinimumHeight(rowHeight);
		}

		return newCell;
	}
	public void createPdfTable(PdfWriter writer,String mmo) {
		initFont();
		pdftable = new PdfPTable(3);
		pdftable.setTotalWidth(150);
		int[] a = { 60, 20 ,20};
		try {
			pdftable.setWidths(a);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try{
			
			pdftable.setLockedWidth(true);
			pdftable.getDefaultCell().setBorder(0);//PdfPCell.NO_BORDER
			pdftable.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			PdfPCell lega = new PdfPCell(createCell("(P) Customer P/N: " +JTVenC.getText().trim(),f14,0,0));//xx[1]客戶料號
			lega.setBorder(0);
			lega.setColspan(3);
			pdftable.addCell(lega);
			
			PdfPCell nued = new PdfPCell(createBarCode(writer, "P" + JTVenC.getText().trim(), a[1]));
			nued.setBorder(0);
			nued.setColspan(3);
			pdftable.addCell(nued);
			
			//PdfPCell desc = new PdfPCell(createCell("DESC: " + JTDesc.getText().trim(),f14,0,0));
			PdfPCell desc = new PdfPCell(createCell("DESC: ", f14,0,0));
			desc.setBorder(0);
			desc.setColspan(3);
			pdftable.addCell(desc);
			
			PdfPCell nued1 = new PdfPCell(createBarCode(writer, JTVenC.getText().trim(), a[1]));
			nued1.setBorder(0);
			nued1.setColspan(3);
			pdftable.addCell(nued1);

			PdfPCell byby = new PdfPCell(createCell("(1P) Manufacturer P/N: " + JTStage.getText().trim(),f14,0,0));
			byby.setBorder(0);
			byby.setColspan(3);
			pdftable.addCell(byby);

			PdfPCell nkar = new PdfPCell(createBarCode(writer,JTStage.getText().trim(), a[1]));
			nkar.setBorder(0);
			nkar.setColspan(3);
			pdftable.addCell(nkar);

			PdfPCell mavc = new PdfPCell(createCell("(V) Vendor Code: " + JTlc.getText().trim(),f14,0,0));
			mavc.setBorder(0);
			mavc.setColspan(3);
			pdftable.addCell(mavc);

			PdfPCell mavcx = new PdfPCell(createBarCode(writer, "V" + JTlc.getText().trim(), a[1]));
			mavcx.setBorder(0);
			mavcx.setColspan(3);
			pdftable.addCell(mavcx);

			PdfPCell afct = new PdfPCell(createCell("(1T) Lot No: " + JTBt.getText().trim(),f14,0,0));
			afct.setBorder(0);
			afct.setColspan(3);
			pdftable.addCell(afct);
			
			PdfPCell vost = new PdfPCell(createBarCode(writer,"1T" + JTBt.getText().trim(),a[1]));
			vost.setBorder(0);
			vost.setColspan(3);
			pdftable.addCell(vost);
			
			
			PdfPCell fge1 = new PdfPCell(createCell("(9D) Date Code: " + JTdate.getText().trim(),f14,0,0));
			fge1.setBorder(0);
			fge1.setColspan(3);
			pdftable.addCell(fge1);
			
			PdfPCell edsf = new PdfPCell(createBarCode(writer,"9D" + JTdate.getText().trim(), a[1]));
			edsf.setBorder(0);
			edsf.setColspan(3);
			pdftable.addCell(edsf);
			
			PdfPCell fge2 = new PdfPCell(createCell("(M) Manufacturer: " + JTSite.getText().trim(),f14,0,0));
			fge2.setBorder(0);
			fge2.setColspan(1);
			pdftable.addCell(fge2);
			
			PdfPCell kong = new PdfPCell(createCell("(Q) QTY: " + JTQty.getText().trim(),f14,0,0));
			kong.setBorder(0);
			kong.setColspan(2);
			pdftable.addCell(kong);
			
			PdfPCell edsf2 = new PdfPCell(createBarCode(writer,"M" + JTSite.getText().trim(), a[1]));
			edsf2.setBorder(0);
			edsf2.setColspan(1);
			pdftable.addCell(edsf2);
			
			PdfPCell cbie = new PdfPCell(createBarCode(writer,"Q" + JTQty.getText().trim(), a[1]));
			cbie.setBorder(0);
			cbie.setColspan(2);
			pdftable.addCell(cbie);
			
			PdfPCell fge3 = new PdfPCell(createCell("(4L) Country of Origin: " + JTBtend.getText().trim(),f14,0,0));
			fge3.setBorder(0);
			fge3.setColspan(3);
			pdftable.addCell(fge3);
			
			PdfPCell edsf3 = new PdfPCell(createBarCode(writer,"4L" + JTBtend.getText().trim(), a[1]));
			edsf3.setBorder(0);
			edsf3.setColspan(3);
			pdftable.addCell(edsf3);
			
			PdfPCell fge4 = new PdfPCell(createCell("REV: " + JTBtal.getText().trim(),f14,0,0));
			fge4.setBorder(0);
			fge4.setColspan(3);
			pdftable.addCell(fge4);
			
			//PdfPCell QQ = new PdfPCell(createCell(JTqq.getText().trim(),f14,0,0));
			//QQ.setBorder(0);
			//pdftable.addCell(QQ);
			
			PdfPCell fge5 = new PdfPCell(createCell("           Date Code",f12,0,0));
			fge5.setBorder(1);
			fge5.setColspan(3);
			pdftable.addCell(fge5);
			
			PdfPCell pega2 = new PdfPCell(new Paragraph(" ", f14));
			pega2.setBorder(0);
			pega2.setColspan(3);
			pega2.setPadding(2);
			pdftable.addCell(pega2);
			
			PdfPCell edsf4 = new PdfPCell(createBarCode(writer,JTdate.getText().trim(), a[1]));
			edsf4.setBorder(0);
			pdftable.addCell(edsf4);
			
			PdfPCell cbie4 = new PdfPCell(createBarCode(writer,JTQty.getText().trim(), a[1]));
			cbie4.setBorder(0);
			cbie4.setColspan(2);
			pdftable.addCell(cbie4);
			
			pdftable.addCell(createCell("Date Code1:" + JTdate.getText().trim(),f14,0,0));
			
			PdfPCell pdfQty = new PdfPCell(createCell("QTY:"+JTQty.getText().trim(),f14,0,0));
			pdfQty.setBorder(0);
			pdfQty.setColspan(2);
			pdftable.addCell(pdfQty);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void initFont() {
		BaseFont bfChinese;
		try {
			//bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
			bfChinese = BaseFont.createFont("MHei-Medium", "UniCNS-UCS2-H", BaseFont.NOT_EMBEDDED);
			f14 = new Font(bfChinese, 7, Font.BOLD, Color.BLACK);
			f13 = new Font(bfChinese, 18, Font.BOLD, Color.BLACK);
			f12 = new Font(bfChinese, 10, Font.BOLD, Color.BLACK);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getPrintName() throws IOException{
        String filepath = "d:/printName_1709.txt";
        String K = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(filepath)));
        for (String line = br.readLine(); line != null; line = br.readLine()) {
            K= line;
        }
        br.close();
        String ResNmae = K;
        return ResNmae;
    }
    public void runTask() {
    final long timeInterval = 3000;// 3秒?行一次
    Runnable runnable = new Runnable() {
        public void run() {
            while (true) {
                // ------- code for task to
			String name = null;
				try {
					name = getPrintName();
				} catch (IOException e) {
					e.printStackTrace();
				}
			    String[] num_code_time = GET_NUM_CODE_TIME(name);
			    String num1 = num_code_time[0];
		        String code1 = num_code_time[1];
			    String time1= num_code_time[2];
				String DEVICE_NO1= num_code_time[3];
			    String num2 = num_code_time[4];
			    String code2 = num_code_time[5];
			    String time2= num_code_time[6];
				String DEVICE_NO2 = num_code_time[7];
			    try {
				String[] filetime = getfile();
				if(!filetime[0].equals(time1)){
					Insertfile(time1,time2,DEVICE_NO1);
					String[] device = SelectDeviceDesc(DEVICE_NO1);
					String DEVICEDESC = device[0];
					String DEVICEIMGTYPE = device[1];
					try {
						DealWebserver(num1,code1,time1,DEVICEDESC,DEVICEIMGTYPE);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				  }
				if(!filetime[1].equals(time2)){
					Insertfile(time1,time2,DEVICE_NO2);
					String[] device = SelectDeviceDesc(DEVICE_NO2);
					String DEVICEDESC = device[0];
					String DEVICEIMGTYPE = device[1];
					try {
						DealWebserver(num2,code2,time2,DEVICEDESC,DEVICEIMGTYPE);
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				  }
			    } catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			  } 
                // ------- ends here
                try {
                    Thread.sleep(timeInterval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };
    Thread thread = new Thread(runnable);
    thread.start();
    }
	public String[] GET_NUM_CODE_TIME(String devno){
		String[] result = null;
		
		try {
			Vector arg = new Vector();
			arg.add(devno);
			BaseServletAgent bsa = new BaseServletAgent(uiVO);
			BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
			bvo.setPackageName("AAA_1709_PORTNO_CHECK");
			bvo.setFunctionName("GET_NUM_CODE_TIME");
			ResultVO rvo = bsa.doFunc(bvo);
			Vector vct = rvo.getData();
			if(vct.size()==1){
			}
			if(vct.size()==3){
				Hashtable ht1 = (Hashtable) vct.elementAt(1);
				String NUM1 =  CloneArray_ChangeStr.NulltoSpace(ht1.get("QTY"));
				String CODE1 = CloneArray_ChangeStr.NulltoSpace(ht1.get("MAT_NO"));
				String TIME1 = CloneArray_ChangeStr.NulltoSpace(ht1.get("TIME"));
				String DEVICE_NO1 = CloneArray_ChangeStr.NulltoSpace(ht1.get("DEVICE_NO"));
				Hashtable ht2 = (Hashtable) vct.elementAt(2);
				String NUM2 =  CloneArray_ChangeStr.NulltoSpace(ht2.get("QTY"));
				String CODE2 = CloneArray_ChangeStr.NulltoSpace(ht2.get("MAT_NO"));
				String TIME2 = CloneArray_ChangeStr.NulltoSpace(ht2.get("TIME"));
				String DEVICE_NO2 = CloneArray_ChangeStr.NulltoSpace(ht2.get("DEVICE_NO"));
				String[] result1 ={NUM1,CODE1,TIME1,DEVICE_NO1,NUM2,CODE2,TIME2,DEVICE_NO2};
				result = result1;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
    public String[] GET_code_mes(String manufactuer_PN){//獲取對應料號的標籤數據庫的數據
		String[] result = null;
		try {
			Vector arg = new Vector();
			arg.add(manufactuer_PN);
			BaseServletAgent bsa = new BaseServletAgent(uiVO);
			BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
			bvo.setPackageName("AAA_1709_PORTNO_CHECK");
			bvo.setFunctionName("GET_LOT_NO");
			ResultVO rvo = bsa.doFunc(bvo);
			Vector vct = rvo.getData();
			if(vct.size()==2){
				Hashtable ht = (Hashtable) vct.elementAt(1);
				String MANUFACTUER_PN =  CloneArray_ChangeStr.NulltoSpace(ht.get("MANUFACTUER_PN"));
				String CUSTOMER_PN = CloneArray_ChangeStr.NulltoSpace(ht.get("CUSTOMER_PN"));
				String QTY = CloneArray_ChangeStr.NulltoSpace(ht.get("QTY"));
				String VENDOR = CloneArray_ChangeStr.NulltoSpace(ht.get("VENDOR"));
				String MANUFACTUER = CloneArray_ChangeStr.NulltoSpace(ht.get("MANUFACTUER"));
				String COUNTRY = CloneArray_ChangeStr.NulltoSpace(ht.get("COUNTRY"));
				String REV = CloneArray_ChangeStr.NulltoSpace(ht.get("REV"));
				String DEC = CloneArray_ChangeStr.NulltoSpace(ht.get("DEC"));
				String[] result1 ={MANUFACTUER_PN,CUSTOMER_PN,QTY,VENDOR,MANUFACTUER,COUNTRY,REV,DEC};
				result = result1;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	public void DealWebserver(String num,String code,String time,String DEVICEDESC,String DEVICEIMGTYPE) throws Exception{//處理Webserver返回值
		String time_day = time.substring(0,8);
		int k = Deal_SerialNum(time_day);
		String SerialNum = null;
		if(k<10){
			SerialNum ="00"+k;;
			
		}else if(k>=10 && k<100){
			SerialNum = "0"+k;
		}else{
			SerialNum = k+"";
		}
		String hour = time.substring(8,10);
		String lot_no = "C"+time.substring(0,8)+Deal_White_Night(hour)+SerialNum;
		String year = time.substring(0,4);
	    String month = time.substring(4,6);
	    String day = time.substring(6,8);
		String[] yy = GET_code_mes(code);//ccd傳入料號
		String qty = yy[2];
		String dateCode = Deal_Data_Code(year,month,day);
		int j = Integer.parseInt(qty);
		int i = Integer.parseInt(num);
		if(i == j){ 
			//自動打印
			JTVenC.setText(yy[1]);
			System.out.println("這是個打印測試"+yy[1]);
			JTStage.setText(yy[0]);
	        JTQty.setText(qty);
	        JTlc.setText(yy[3]);
			JTBt.setText(lot_no);
	        JTdate.setText(dateCode);
	        JTSite.setText(yy[4]);
	        JTBtend.setText(yy[5]);
			JTBtal.setText(yy[6]);
			JTDesc.setText(yy[7]);
			//JTDesc.setText("");
			//JTqq.setText(Device);
			//JTqq.setText(qdefile());
			Printdo(true,1+"",DEVICEDESC,DEVICEIMGTYPE);
			doPrint(true,1+"",DEVICEDESC,DEVICEIMGTYPE);
			JOptionPane.showMessageDialog(null,"系統正在打印請稍後","提示",JOptionPane.ERROR_MESSAGE);
			try {
				Insert_DB_Mes();
			} catch (Exception e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			JOptionPane.showMessageDialog(null,"這是未滿相","提示",JOptionPane.ERROR_MESSAGE);
			//手動答應
			JTVenC.setText(yy[1]);
			JTStage.setText(yy[0]);
	        JTQty.setText(num);
	        JTlc.setText(yy[3]);
	        JTBt.setText(lot_no);
	        JTdate.setText(dateCode);
	        JTSite.setText(yy[4]);
	        JTBtend.setText(yy[5]);
			JTBtal.setText(yy[6]);
			JTDesc.setText(yy[7]);
			//JTDesc.setText("");
			//JTqq.setText(Device);
		}			
	}
	//處理LETNO上的料號
	public int Deal_SerialNum(String time) throws Exception{//傳人webserver返回的時間
		int k = 1;
		Vector arg = new Vector();
			arg.add(time);
			BaseServletAgent bsa = new BaseServletAgent(uiVO);
			BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
			bvo.setPackageName("AAA_1709_PORTNO_CHECK");
			bvo.setFunctionName("GET_NUM");
			ResultVO rvo = bsa.doFunc(bvo);
			Vector vct = rvo.getData();
		if(vct.size()<2){
			Vector arg1 = new Vector();
			arg1.add(1);
			arg1.add(time);
			BaseServletAgent bsa1 = new BaseServletAgent(uiVO);
			BaseVO bvo1 = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg1);
			bvo1.setPackageName("AAA_1709_PORTNO_CHECK");
			bvo1.setFunctionName("UPDATE_NUM");
			bsa1.doFunc(bvo1);
		}
		if(vct.size()==2){
			Hashtable ht = (Hashtable) vct.elementAt(1);
			String num = CloneArray_ChangeStr.NulltoSpace(ht.get("NUM"));
			k = Integer.parseInt(num)+1;
			Vector arg1 = new Vector();
			arg1.add(k);
			arg1.add(time);
			BaseServletAgent bsa1 = new BaseServletAgent(uiVO);
			BaseVO bvo1 = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg1);
			bvo1.setPackageName("AAA_1709_PORTNO_CHECK");
			bvo1.setFunctionName("UPDATE_NUM");
			bsa1.doFunc(bvo1);
		}
		return k;
    }
	//處理白夜班的代號
	public String Deal_White_Night(String time){
		String White_or_Nihgt = null;
		int hour = Integer.parseInt(time);
		if(hour>=8&&hour<20){
			White_or_Nihgt = "A";
		}else{White_or_Nihgt = "B";}
		
		return White_or_Nihgt;
	}
	//處理DataCode
	public String Deal_Data_Code(String year,String month,String day){
		String Date_Code = null;
		String week = null;
		String year0 = year.substring(2,4);
		int a = Integer.parseInt(year);
		int b = Integer.parseInt(month)-1;
		int c = Integer.parseInt(day);
		Calendar cal = Calendar.getInstance();
		Date date = new Date(a,b,c);
		if(cal.get(Calendar.WEEK_OF_YEAR)<10){
			int xx = cal.get(Calendar.WEEK_OF_YEAR);
			week = "0"+xx;
		}else{week = cal.get(Calendar.WEEK_OF_YEAR)+"";}
		Date_Code = year0+week;
		return Date_Code;	
	}
	//插入數據庫標籤信息
	public void Insert_DB_Mes() throws Exception {
		Vector vct = new Vector();
		vct.add(JTVenC.getText().toString().trim());
		vct.add(JTStage.getText().toString().trim());
		vct.add(JTQty.getText().toString().trim());
		vct.add(JTlc.getText().toString().trim());
		vct.add(JTBt.getText().toString().trim());
		vct.add(JTdate.getText().toString().trim());
		vct.add(JTBtal.getText().toString().trim());
		vct.add(JTDesc.getText().toString().trim());
		BaseServletAgent bsa = new BaseServletAgent(uiVO);
		BaseVO baseVO = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,vct);
		baseVO.setPackageName("AAA_1709_PORTNO_CHECK");
		baseVO.setFunctionName("INSERT_MES");
		bsa.doFunc(baseVO);
	}
	public void Insertfile(String time1,String time2,String route){
		String filepath = "d:/time_1709.txt";
		File Util1 = new File(filepath);
		try {
			Util1.createNewFile();
			BufferedWriter br = new BufferedWriter(new FileWriter(Util1));
			br.write(time1 + "\r\n");
			br.write(time2 + "\r\n");
			br.write(route + "\r\n");
			br.flush();
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String[] getfile() throws IOException{
		String filepath = "d:/time_1709.txt";
		String[] K =new String[3];
		int i = 0;
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(filepath)));
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			K[i]= line;
			i++;
		}
		br.close();
		String[] Restime = K;
		return Restime;	
	}
	public String[] SelectDeviceDesc(String desc){
		String[] result = new String[2];
		try {
			Vector arg = new Vector();
			arg.add(desc);
			System.out.println("222222222222222222"+desc);
			BaseServletAgent bsa = new BaseServletAgent(uiVO);
			BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
			bvo.setPackageName("AAA_1709_PORTNO_CHECK");
			bvo.setFunctionName("SELECT_DEVICE");
			ResultVO rvo = bsa.doFunc(bvo);
			Vector vct = rvo.getData();
			if(vct.size()==2){
				Hashtable ht1 = (Hashtable) vct.elementAt(1);
				String DEVICEDESC =  CloneArray_ChangeStr.NulltoSpace(ht1.get("DEVICEDESC"));
				System.out.println("11111111111111111111111"+DEVICEDESC);
				String DEVICEIMGTYPE =  CloneArray_ChangeStr.NulltoSpace(ht1.get("DEVICEIMGTYPE"));
				String[] result1 = {DEVICEDESC,DEVICEIMGTYPE};
				result = result1;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	//public String qde(){
		//ZHConverter converter = ZHConverter.getInstance(ZHConverter.SIMPLIFIED);
        //簡體轉繁体
        //String traditionalSrc = "";
        //String simplified = converter.convert(traditionalSrc);
		//System.out.println(traditionalSrc);
        //System.out.println(simplified);
		//return traditionalSrc;
	//}
	@Override
	public void create() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void email() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void export() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void help() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void importData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modify() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void multiQuery() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Hashtable<String, P_Component_Meta> needValidateComponents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setReportOid(String reportOid) {
		// TODO Auto-generated method stub
		
	}

	

}
