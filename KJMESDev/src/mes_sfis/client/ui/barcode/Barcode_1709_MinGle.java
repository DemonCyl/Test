/**
 * Copyright (c) 2008 Pegatron Inc. All Rights Reserved.
 * $Id: Barcode_1709_MinGle.java,v 1.2 2017/07/21 00:35:08 Jieyu_Fu Exp $
 */

package mes_sfis.client.ui.barcode;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import base.client.util.component.PDateTimeTextField;
import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 * The <code>Barcode_1709_MinGle</code> class.
 *
 * @version     $Name:  $, $Id: Barcode_1709_MinGle.java,v 1.2 2017/07/21 00:35:08 Jieyu_Fu Exp $
 * @author      <a href="mailto:william_tsai@Pegatroncorp.com">William Tsai</a>
 * @history      [>: General, +: New, #: Modification, -: Remove, ~: Bug]
 *
 */
public class Barcode_1709_MinGle extends BasePanel{
	
	
    public static final String VERSION    = "$Name:  $, $Id: Barcode_1709_MinGle.java,v 1.2 2017/07/21 00:35:08 Jieyu_Fu Exp $";
    public static final String COPYRIGHT  = "Copyright (c) 2008 PEGATRON Inc. All Rights Reserved.";
	Barcode_1709_MinGle self;
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
	Hashtable row = null;
	private JTextField jtfLabelQTY;
	private JCheckBox jcboxPrint;
	private PDateTimeTextField PDEndTime;
	JTextArea jta;
	String a3;
	public Barcode_1709_MinGle(UI_InitVO uiVO) {
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
        add(JLVenC);
        add(JTVenC);
		
		JLabel JLStage = new JLabel("(1P) Manufacturer P/N:");
		JLStage.setBounds(80,105,95,25);
		JTStage = new JTextField(20);
        JTStage.setBounds(210,105,150,25);
        add(JLStage);
        add(JTStage);
		
		JLabel JLQty = new JLabel("(Q) QTY:");
		JLQty.setBounds(80,135,95,25);
		JTQty = new JTextField(20);
        JTQty.setBounds(210,135,150,25);
        add(JLQty);
        add(JTQty);
		
		JLabel JLlc = new JLabel("(V) Vendor Code:");
		JLlc.setBounds(new Rectangle(80,165,95,25));
		add(JLlc,null);
		JTlc = new JTextField(20);
        JTlc.setBounds(210,165,150,25);
        add(JTlc);
		
		JLabel JLBt = new JLabel("(1T) Lot No:");
		JLBt.setBounds(new Rectangle(80,195,95,25));
		add(JLBt,null);
		JTBt = new JTextField(20);
        JTBt.setBounds(210,195,150,25);
        add(JTBt);
		
		JLabel JLdate = new JLabel("(9D) Date Code:");
		JLdate.setBounds(new Rectangle(80,225,95,25));
		add(JLdate,null);
		
		PDEndTime=new PDateTimeTextField(uiVO,"PDEndTime",150,true,true);
		PDEndTime.setBounds(new Rectangle(210,225,150,25));
		add(PDEndTime);
		
		JLabel JLSite = new JLabel("(M) Manufacturer:");
		JLSite.setBounds(385,70,95,25);
		JTSite = new JTextField(20);
        JTSite.setBounds(485,70,150,25);
        add(JLSite);
        add(JTSite);
		
		JLabel JLBtend = new JLabel("(4L) Country of Origin:");
		JLBtend.setBounds(new Rectangle(385,105,95,25));
		add(JLBtend,null);
		JTBtend = new JTextField(20);
        JTBtend.setBounds(485,105,150,25);
        add(JTBtend);
		
		JLabel JLBtal = new JLabel("REV : ");
		JLBtal.setBounds(new Rectangle(385,135,95,25));
		add(JLBtal,null);
		JTBtal = new JTextField(20);
        JTBtal.setBounds(485,135,150,25);
        add(JTBtal);
		
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
		
			doPrint(jcboxPrint.isSelected(),printNum);
		
	}
	public PdfPCell createBarCode(PdfWriter writer, String codeStr, int maxWidth) {
		PdfContentByte cd = writer.getDirectContent();
		Barcode128 code39ext = new Barcode128();
		code39ext.setStartStopText(false);
		code39ext.setExtended(true);
		code39ext.setFont(null);// ?示?片下字符串?容
		code39ext.setTextAlignment(Element.ALIGN_LEFT);
		code39ext.setBarHeight(7f);
		code39ext.setX(0.5f);

		code39ext.setCode(codeStr);
		Image image39 = code39ext.createImageWithBarcode(cd, null, null);

		PdfPCell barcodeCell = new PdfPCell(image39);
		barcodeCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		barcodeCell.setPadding(4);
		barcodeCell.setBorder(11);
		return barcodeCell;
	}
	
	
	private void doPrint(boolean isPrint,String printNum){
		if(PDEndTime.getText().equals("")){
			JOptionPane.showMessageDialog(null, "請選擇產出日期！");
			return;
		}
		/*if(JTQty.getText().trim().equals("")){
			JOptionPane.showMessageDialog(null, "請選擇QTY！");
			return;
		}*/
		int num=Integer.parseInt(printNum);
		String filePath = "d:/Honor.pdf";
		try{
			Document document = new Document(new com.lowagie.text.Rectangle(170, 270), 10, 10, 10, 0);
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
				if(PDEndTime.getText().equals("")){
					a3 = "*";
				}
				if(JTSite.getText().trim().equals("")){
					JTSite.setText("*");
				}
				if(JTBtend.getText().trim().equals("")){
					JTBtend.setText("*");
				}
				
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
			setMessage("資料已匯出到   d:/Honor.pdf ");
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
		pdftable = new PdfPTable(2);
		pdftable.setTotalWidth(150);
		int[] a = { 60, 40 };
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
			
			PdfPCell lega = new PdfPCell(createCell("(P) Customer P/N: " + JTVenC.getText().trim(),f14,0,0));
			lega.setBorder(0);
			lega.setColspan(2);
			pdftable.addCell(lega);
			
			PdfPCell nued = new PdfPCell(createBarCode(writer, "P" + JTVenC.getText().trim(), a[1]));
			nued.setBorder(0);
			nued.setColspan(2);
			pdftable.addCell(nued);

			PdfPCell byby = new PdfPCell(createCell("(1P) Manufacturer P/N: " + JTStage.getText().trim(),f14,0,0));
			byby.setBorder(0);
			byby.setColspan(2);
			pdftable.addCell(byby);

			PdfPCell nkar = new PdfPCell(createBarCode(writer,"1P" + JTStage.getText().trim(), a[1]));
			nkar.setBorder(0);
			nkar.setColspan(2);
			pdftable.addCell(nkar);
			
			PdfPCell kong = new PdfPCell(createCell("(Q) QTY: " + JTQty.getText().trim(),f14,0,0));
			kong.setBorder(0);
			kong.setColspan(2);
			pdftable.addCell(kong);

			PdfPCell cbie = new PdfPCell(createBarCode(writer,"Q" + JTQty.getText().trim(), a[1]));
			cbie.setBorder(0);
			cbie.setColspan(2);
			pdftable.addCell(cbie);

			PdfPCell mavc = new PdfPCell(createCell("(V) Vendor Code: " + JTlc.getText().trim(),f14,0,0));
			mavc.setBorder(0);
			mavc.setColspan(2);
			pdftable.addCell(mavc);

			PdfPCell mavcx = new PdfPCell(createBarCode(writer, "V" + JTlc.getText().trim(), a[1]));
			mavcx.setBorder(0);
			mavcx.setColspan(2);
			pdftable.addCell(mavcx);

			PdfPCell afct = new PdfPCell(createCell("(1T) Lot No: " + JTBt.getText().trim(),f14,0,0));
			afct.setBorder(0);
			afct.setColspan(2);
			pdftable.addCell(afct);
			
			PdfPCell vost = new PdfPCell(createBarCode(writer,"1T" + JTBt.getText().trim(),a[1]));
			vost.setBorder(0);
			vost.setColspan(2);
			pdftable.addCell(vost);
			
			String Cdate = PDEndTime.getText();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Calendar ca = Calendar.getInstance();
			ca.setTime(sdf.parse(Cdate));
			String a1 = Cdate.substring(2,4);
			String a2 = Integer.toString(ca.get(Calendar.WEEK_OF_YEAR));
			if(a2.length()<2){
				a2 = "0" + a2;
			}
			a3 = a1 + a2;
			
			PdfPCell fge1 = new PdfPCell(createCell("(9D) Date Code: " + a3,f14,0,0));
			fge1.setBorder(0);
			fge1.setColspan(2);
			pdftable.addCell(fge1);
			
			PdfPCell edsf = new PdfPCell(createBarCode(writer,"9D" + a3, a[1]));
			edsf.setBorder(0);
			edsf.setColspan(2);
			pdftable.addCell(edsf);
			
			PdfPCell fge2 = new PdfPCell(createCell("(M) Manufacturer: " + JTSite.getText().trim(),f14,0,0));
			fge2.setBorder(0);
			fge2.setColspan(2);
			pdftable.addCell(fge2);
			
			PdfPCell edsf2 = new PdfPCell(createBarCode(writer,"M" + JTSite.getText().trim(), a[1]));
			edsf2.setBorder(0);
			edsf2.setColspan(2);
			pdftable.addCell(edsf2);
			
			PdfPCell fge3 = new PdfPCell(createCell("(4L) Country of Origin: " + JTBtend.getText().trim(),f14,0,0));
			fge3.setBorder(0);
			fge3.setColspan(2);
			pdftable.addCell(fge3);
			
			PdfPCell edsf3 = new PdfPCell(createBarCode(writer,"4L" + JTBtend.getText().trim(), a[1]));
			edsf3.setBorder(0);
			edsf3.setColspan(2);
			pdftable.addCell(edsf3);
			
			PdfPCell fge4 = new PdfPCell(createCell("REV: " + JTBtal.getText().trim(),f14,0,0));
			fge4.setBorder(0);
			fge4.setColspan(2);
			pdftable.addCell(fge4);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void initFont() {
		BaseFont bfChinese;
		try {
			//bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", false);
			bfChinese = BaseFont.createFont("MHei-Medium", "UniCNS-UCS2-H", BaseFont.NOT_EMBEDDED);
			f14 = new Font(bfChinese, 5, Font.BOLD, Color.BLACK);
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
