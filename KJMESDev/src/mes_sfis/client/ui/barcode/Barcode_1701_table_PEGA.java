/**
 * Copyright (c) 2008 Pegatron Inc. All Rights Reserved.
 * $Id: Barcode_1701_table_PEGA.java,v 1.19 2017/12/12 03:00:17 Lucy6_Lu Exp $
 */

package mes_sfis.client.ui.barcode;

import base.client.enums.ToolBarItem;
import base.client.ui.BasePanel;
import base.client.util.BaseServletAgent;
import base.client.util.CloneArray_ChangeStr;
import base.client.util.component.PDateTimeTextField;
import base.client.util.component.P_Component_Meta;
import base.enums.CommandName;
import base.vo.BaseVO;
import base.vo.ResultVO;
import base.vo.UI_InitVO;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.*;

import javax.swing.*;
import java.awt.*;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * The <code>Barcode_1701_table_PEGA</code> class.
 *
 * @version     $Name:  $, $Id: Barcode_1701_table_PEGA.java,v 1.19 2017/12/12 03:00:17 Lucy6_Lu Exp $
 * @author      <a href="mailto:william_tsai@Pegatroncorp.com">William Tsai</a>
 * @history      [>: General, +: New, #: Modification, -: Remove, ~: Bug]
 *          $Log: Barcode_1701_table_PEGA.java,v $
 *          Revision 1.19  2017/12/12 03:00:17  Lucy6_Lu
 *          2017-12-12  1701-??????
 *
 *          Revision 1.18  2017/11/15 01:22:36  Lucy6_Lu
 *          ?????X??l  2017-11-15
 *
 *          Revision 1.17  2017/11/15 01:15:27  Lucy6_Lu
 *          ?????X??l  2017-11-15
 *
 *          Revision 1.16  2017/10/26 09:23:32  Lucy6_Lu
 *          ??s
 *
 *          Revision 1.9  2017/07/13 00:18:35  Jieyu_Fu
 *          no message
 *
 *          Revision 1.8  2017/06/12 02:19:50  Jieyu_Fu
 *          no message
 *
 *          Revision 1.7  2017/06/12 02:11:59  Jieyu_Fu
 *          no message
 *
 *          Revision 1.6  2017/05/27 02:56:31  Jieyu_Fu
 *          no message
 *
 *          Revision 1.5  2017/05/24 06:47:36  Jieyu_Fu
 *          no message
 *
 *          Revision 1.4  2017/05/06 01:03:20  Jieyu_Fu
 *          no message
 *
 *          Revision 1.3  2017/04/25 01:09:50  Jieyu_Fu
 *          no message
 *
 *          Revision 1.2  2017/04/12 01:39:42  Jieyu_Fu
 *          no message
 *
 *          Revision 1.1  2017/04/07 08:42:00  Jieyu_Fu
 *          no message
 *
 *
 */
public class Barcode_1701_table_PEGA extends BasePanel{


	public static final String VERSION    = "$Name:  $, $Id: Barcode_1701_table_PEGA.java,v 1.19 2017/12/12 03:00:17 Lucy6_Lu Exp $";
	public static final String COPYRIGHT  = "Copyright (c) 2008 PEGATRON Inc. All Rights Reserved.";
	Barcode_1701_table_PEGA self;
	Font						f14;
	Font						f13;
	Font						f12;
	static PdfPTable			pdftable;
	//private JTextField JTQuery_Pt;
	private JComboBox JCQuery_Pt;
	private JTextField JTQty;
	private JTextField JTlc;
	private JTextField JTBt;
	private JTextField JTVenC;
	private JTextField JTSite;
	private JTextField JTStage;
	Hashtable row = null;
	private JTextField jtfLabelQTY;
	private JCheckBox jcboxPrint;
	private PDateTimeTextField PDEndTime;
	JTextArea jta;
	//?��?�z�s�W?�q
	private  String REV_UP="";
	int how = 0;
	public Barcode_1701_table_PEGA(UI_InitVO uiVO) {
		super(uiVO);
		init();
	}
	void init(){
		self=this;
		self.setUILayout(null);

		JLabel JLQuery_Pt = new JLabel("�п�J�Ƹ�");
		JLQuery_Pt.setBounds(100,70,95,25);
		/*JTQuery_Pt = new JTextField(20);
        JTQuery_Pt.setBounds(230,70,150,25);*/
		add(JLQuery_Pt);
		//add(JTQuery_Pt);
		JCQuery_Pt = new JComboBox();
		JCQuery_Pt.setSelectedItem("");
		JCQuery_Pt.setBounds(230,70,150,25);
		JCQuery_PtListener jcqlisten = new JCQuery_PtListener();
		JCQuery_Pt.addActionListener(jcqlisten);
		add(JCQuery_Pt);

		JLabel JLVenC = new JLabel("Vender code");
		JLVenC.setBounds(385,70,95,25);
		JTVenC = new JTextField(20);
		JTVenC.setBounds(485,70,150,25);
		add(JLVenC);
		add(JTVenC);

		JLabel JLSite = new JLabel("�t�өҦb�������r��");
		JLSite.setBounds(315,155,155,25);
		JTSite = new JTextField(20);
		JTSite.setBounds(485,155,150,25);
		add(JLSite);
		add(JTSite);

		JLabel JLStage = new JLabel("Stage");
		JLStage.setBounds(385,105,155,25);
		JTStage = new JTextField(20);
		JTStage.setBounds(485,105,150,25);
		add(JLStage);
		add(JTStage);

		JLabel JLQty = new JLabel("Qty");
		JLQty.setBounds(100,155,50,25);
		JTQty = new JTextField(20);
		JTQty.setBounds(160,155,150,25);
		add(JLQty);
		add(JTQty);
		JTQty.setEditable(false);

		JLabel jLabel_LabelQTY = new JLabel("���L�i�� : ",JLabel.RIGHT);
		jLabel_LabelQTY.setBounds(new Rectangle(10,295,100,25));
		add(jLabel_LabelQTY,null);

		jtfLabelQTY = new JTextField();
		jtfLabelQTY.setText("1");
		jtfLabelQTY.setBounds(new Rectangle(120,295,250,25));
		add(jtfLabelQTY,null);
		jtfLabelQTY.setEditable(false);

		jcboxPrint = new JCheckBox("�s�u���L��");
		jcboxPrint.setBounds(new Rectangle(400,295,100,25));
		add(jcboxPrint,null);
		jcboxPrint.setSelected(true);

		jta = new JTextArea();
		jta.setBounds(new Rectangle(100,325,400,200));
		add(jta,null);
		jta.setEditable(false);

		JLabel JLdate = new JLabel("���X��� ");
		JLdate.setBounds(new Rectangle(100,195,95,25));
		add(JLdate,null);

		PDEndTime=new PDateTimeTextField(uiVO,"PDEndTime",150,true,true);
		PDEndTime.setBounds(new Rectangle(230,195,150,25));
		add(PDEndTime);

		JLabel JLlc = new JLabel("L/C : ");
		JLlc.setBounds(new Rectangle(100,105,95,25));
		add(JLlc,null);
		JTlc = new JTextField(20);
		JTlc.setBounds(230,105,150,25);
		add(JTlc);
		JTlc.setText("N/A");

		JLabel JLBt = new JLabel("Batch : ");
		JLBt.setBounds(new Rectangle(100,245,95,25));
		add(JLBt,null);
		JTBt = new JTextField(20);
		JTBt.setBounds(230,245,150,25);
		add(JTBt);

		checkN();

		setStatus(new int[]{ToolBarItem.SingleQuery,ToolBarItem.Cancel,ToolBarItem.Exit});
	}
	public class JCQuery_PtListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(how != 0){
				singleQuery();
			}
		}
	}

	private void checkN(){
		try{
			Vector arg = new Vector();
			BaseServletAgent bsa = new BaseServletAgent(uiVO);
			BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
			bvo.setPackageName("SFIS_LABEL_1701AP_PEGA_PG");
			bvo.setFunctionName("Query_Oempn");
			ResultVO rvo = bsa.doFunc(bvo);
			Vector result = rvo.getData();
			Hashtable row = null;
			JCQuery_Pt.removeAllItems();
			JCQuery_Pt.addItem("");
			for(int n = 1;n <= (result.size() - 1);n++){
				row = (Hashtable)result.get(n);
				JCQuery_Pt.addItem(CloneArray_ChangeStr.NulltoSpace(row.get("OEMPN")));
			}
			how ++;
		}catch(Exception e){
			e.printStackTrace();
			exUtl.ExceptionNotify(e,"SLOT_SPC_CHARGE pickC ",this.getClass().toString(),VERSION);
		}
	}

	public void singleQuery() {
		setMessage("");
		try{
			Vector arg = new Vector();
			//arg.add(JTQuery_Pt.getText().trim());
			arg.add(JCQuery_Pt.getSelectedItem());
			BaseServletAgent bsa = new BaseServletAgent(uiVO);
			BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
			bvo.setPackageName("SFIS_LABEL_1701AP_PEGA_PG");
			bvo.setFunctionName("Query_pt_lable");
			ResultVO rvo = bsa.doFunc(bvo);
			Vector result = rvo.getData();
			if(result.size() < 2){
//				JOptionPane.showMessageDialog(null,"????L?????H??");
				JOptionPane.showMessageDialog(null,"wait....");
				return;
			}
			row = (Hashtable)result.get(1);
			JTQty.setEditable(true);
			JTQty.setText(CloneArray_ChangeStr.NulltoSpace(row.get("REQ")));
			jta.setText("���L���\");
			jtfLabelQTY.setEditable(true);
			//JTQuery_Pt.setEditable(false);
			JTSite.setText("A");
			JTStage.setText(CloneArray_ChangeStr.NulltoSpace(row.get("STAGE")));
			REV_UP=CloneArray_ChangeStr.NulltoSpace(row.get("REV"));
			JTBt.setText("00");
			//JTBt.setText(CloneArray_ChangeStr.NulltoSpace(row.get("REV")));
			JTVenC.setText(CloneArray_ChangeStr.NulltoSpace(row.get("TRACKING")));
			setStatus(new int[]{ToolBarItem.Print,ToolBarItem.Cancel,ToolBarItem.Exit});
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void cancel() {
		//JTQuery_Pt.setText("");
		JTQty.setText("");
		jtfLabelQTY.setText("1");
		//JTQuery_Pt.setEditable(true);
		JTQty.setEditable(false);
		jtfLabelQTY.setEditable(false);
		jta.setText("");
		setStatus(new int[]{ToolBarItem.SingleQuery,ToolBarItem.Cancel,ToolBarItem.Exit});
	}

	public void print() {


		String printNum=jtfLabelQTY.getText().trim();
		if(printNum.equals("")){
			setMessage("�п�J���L�ƶq");
			return;
		}

		if(!isMatcherPatternStr(printNum,"[0-9]")){
			setMessage("�п�J�Ʀr�]0-9 )!");
			return;
		}

		if(!isMatcherPatternStr(JTQty.getText().trim(),"[0-9]")){
			setMessage("QTY���Ʀr");
			return;
		}

		doPrint(jcboxPrint.isSelected(),printNum);

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


	private void doPrint(boolean isPrint,String printNum){
		if(PDEndTime.getText().equals("")){
			JOptionPane.showMessageDialog(null, "�п�J���X���.....");
			return;
		}
		if(JTBt.getText().trim().equals("")){
			JOptionPane.showMessageDialog(null, "�п�JBATCH��...");
			return;
		}
		if(JTVenC.getText().trim().length() != 8){
			JOptionPane.showMessageDialog(null, "�п�JVENDER CODE....");
			return;
		}
		if(JTSite.getText().trim().length() != 1){
			JOptionPane.showMessageDialog(null, "�п�J�t�ӽs��...");
			return;
		}
		if(JTStage.getText().trim().equals("")){
			JOptionPane.showMessageDialog(null, "STAGE���ର��...");
			return;
		}
		int num=Integer.parseInt(printNum);
		String filePath = "d:/Lableof1701.pdf";
		try{
			Document document = new Document(new com.lowagie.text.Rectangle(220, 280), 0, 10, 0, 0);
			PdfWriter writer = PdfWriter.getInstance(document,new FileOutputStream(filePath));
			document.open();
			for(int i=1;i<=num;i++){
				Vector arg = new Vector();
				//arg.add(JTQuery_Pt.getText().trim());
				arg.add(JCQuery_Pt.getSelectedItem());
				BaseServletAgent bsa = new BaseServletAgent(uiVO);
				BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
				bvo.setPackageName("SFIS_LABEL_1701AP_PEGA_PG");
				bvo.setFunctionName("Update_pt_batch");
				ResultVO rvo = bsa.doFunc(bvo);
				createPdfTable(writer,null);
				document.add(pdftable);
				//Image img = Image.getInstance("D:/test.png");
				//img.scalePercent(110f);
				//img.setAbsolutePosition(90f, 31f);
				//img.scaleAbsolute(50, 50);
				//document.add(img);
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
		jta.setText("���L���\");
		System.out.println("export ok");
		setStatus(new int[]{ToolBarItem.Cancel,ToolBarItem.Exit});
		if(isPrint){
			if(!printPdf(filePath)){
				setMessage("");
			}
		}else{
			setMessage("�a�}   d:/Lableof1701.pdf ");
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
	/*public Hashtable getDBDATA(String sql,String ds,UI_InitVO uiVO,int Status)throws Exception{
		Vector arg = new Vector();
		arg.add(sql);
	    BaseServletAgent bsa1 = new BaseServletAgent(uiVO);
		BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallSQLCmd2,arg);
		bvo.setDataSourceType(ds);
		ResultVO rvo = bsa1.doFunc(bvo);
		Vector result = rvo.getData();
		Hashtable ht = null;
		
		if(result.size()>1){
			
		}
		return ht;
	}*/

	public boolean isMatcherPatternStr(String valueStr,String patternStr){
		int charNum=0;
		char[] chs = valueStr.toCharArray();
		for(char ch:chs){
			Pattern pattern = Pattern.compile(patternStr);
			Matcher matcher = pattern.matcher(String.valueOf(ch));
			if(matcher.matches() == false){
				JOptionPane.showMessageDialog(null, "WAIT");
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
		pdftable.setTotalWidth(199);
		int[] a = { 28, 29, 43 };
		try {
			pdftable.setWidths(a);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try{
			Vector uparg = new Vector();
			//uparg.add(JTQuery_Pt.getText().trim());
			uparg.add(JCQuery_Pt.getSelectedItem());
			BaseServletAgent upbsa = new BaseServletAgent(uiVO);
			BaseVO upbvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,uparg);
			upbvo.setPackageName("SFIS_LABEL_1701AP_PEGA_PG");
			upbvo.setFunctionName("Query_pt_lable");
			ResultVO uprvo = upbsa.doFunc(upbvo);
			Vector upresult = uprvo.getData();
			Hashtable uprow = (Hashtable)upresult.get(1);
			String doubt = CloneArray_ChangeStr.NulltoSpace(uprow.get("BATCHNO"));
			if(doubt.length() < 2){
				doubt = "0" + doubt;
			}
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
			PdfPCell lega = new PdfPCell(createCell(CloneArray_ChangeStr.NulltoSpace(row.get("VENDOR")),f14,0,11));
			lega.setBorder(11);
			lega.setColspan(2);
			pdftable.addCell(lega);
			//pdftable.addCell(createCell(CloneArray_ChangeStr.NulltoSpace(row.get("VENDOR")),f14,0,8));
			pdftable.addCell(createCell("PEGA PN:",f14,29,7));
			PdfPCell klli = new PdfPCell(createBarCode(writer, CloneArray_ChangeStr.NulltoSpace(row.get("OEMPN")), a[1],17f));
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
			String Cdate = PDEndTime.getText();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Calendar ca = Calendar.getInstance();
			ca.setTime(sdf.parse(Cdate));
			String a1 = Cdate.substring(3,4);
			String a2 = Integer.toString(ca.get(Calendar.WEEK_OF_YEAR));
			if(a2.length()<2){
				a2 = "0" + a2;
			}
			int dayOfWeek = ca.get(Calendar.DAY_OF_WEEK) - 1;
			if (dayOfWeek == 0)
				dayOfWeek = 7;

			String weekStr =""+dayOfWeek;


			String a3 = a1 + a2 + weekStr;
			String alof = JTVenC.getText().trim() + JTSite.getText().trim() + a3;
			Vector arg = new Vector();
			//arg.add(JTQuery_Pt.getText().trim());
			arg.add(JCQuery_Pt.getSelectedItem());
			arg.add(a3);
			arg.add(alof);
			BaseServletAgent bsa = new BaseServletAgent(uiVO);
			BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
			bvo.setPackageName("SFIS_LABEL_1701AP_PEGA_PG");
			bvo.setFunctionName("Insert_carton");
			ResultVO rvo = bsa.doFunc(bvo);
			Vector result = rvo.getData();
			Hashtable rxc = (Hashtable)result.get(1);
			pdftable.addCell(createCell("APN:  " + CloneArray_ChangeStr.NulltoSpace(row.get("APPLEPN")),f14,8,7));
			PdfPCell nubi = new PdfPCell(createCell("Rev:  " + REV_UP + "       Config:  " + CloneArray_ChangeStr.NulltoSpace(row.get("CONFIG")),f14,0,11));
			nubi.setBorder(11);
			nubi.setColspan(2);
			pdftable.addCell(nubi);
			//pdftable.addCell(createCell("Rev:  " + CloneArray_ChangeStr.NulltoSpace(row.get("REV")) + "       Config:  " + CloneArray_ChangeStr.NulltoSpace(row.get("CONFIG")),f14,0,8));
			pdftable.addCell(createCell("Desc:",f14,8,7));
			PdfPCell byby = new PdfPCell(createCell(CloneArray_ChangeStr.NulltoSpace(row.get("DESCRIPTION")),f14,0,11));
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
			if(JTlc.getText().trim().equals("")){
				//pdftable.addCell(createCell("",f14,34,8));
				PdfPCell mavc = new PdfPCell(createCell("",f14,22,11));
				mavc.setBorder(11);
				mavc.setColspan(2);
				pdftable.addCell(mavc);
			}else{
				//pdftable.addCell(createBarCode(writer, JTlc.getText().trim(), a[1]));
				PdfPCell mavc = new PdfPCell(createBarCode(writer, JTlc.getText().trim(), a[1],11f));
				mavc.setBorder(11);
				mavc.setColspan(2);
				pdftable.addCell(mavc);
			}
			pdftable.addCell(createCell("QTY:",f14,22,7));
			PdfPCell mavc = new PdfPCell(createBarCode(writer,JTQty.getText().trim(),a[1],11f));
			mavc.setBorder(11);
			mavc.setColspan(2);
			pdftable.addCell(mavc);
			//pdftable.addCell(createBarCode(writer,JTQty.getText().trim(),a[1]));
			pdftable.addCell(createCell("Batch No:",f14,22,7));
			//pdftable.addCell(createBarCode(writer,doubt, a[1]));
			PdfPCell nkar = new PdfPCell(createBarCode(writer,JTBt.getText().trim(), a[1],11f));
			nkar.setBorder(11);
			nkar.setColspan(2);
			pdftable.addCell(nkar);
			//pdftable.addCell(createBarCode(writer,JTBt.getText().trim(), a[1]));
			pdftable.addCell(createCell("Carton No:",f14,22,7));
			PdfPCell cbie = new PdfPCell(createBarCode(writer,CloneArray_ChangeStr.NulltoSpace(rxc.get("MISS")), a[1],11f));
			cbie.setBorder(11);
			cbie.setColspan(2);
			pdftable.addCell(cbie);
			if(JTlc.getText().trim().equals("")){
				CodeZXing.CreateImage(CloneArray_ChangeStr.NulltoSpace(rxc.get("MISS")) + "," + CloneArray_ChangeStr.NulltoSpace(row.get("OEMPN"))
						+ "," + CloneArray_ChangeStr.NulltoSpace(row.get("APPLEPN")) + "," + JTBt.getText().trim() + "," + JTQty.getText().trim() + "," + a3);

				//ZXingCode.getLogoQRCode(CloneArray_ChangeStr.NulltoSpace(rxc.get("MISS")) + "," + CloneArray_ChangeStr.NulltoSpace(row.get("OEMPN"))
				//+ "," + CloneArray_ChangeStr.NulltoSpace(row.get("APPLEPN")) + "," + JTBt.getText().trim() + "," + JTQty.getText().trim() + "," + a3
				//, null, null);
			}else{
				CodeZXing.CreateImage(CloneArray_ChangeStr.NulltoSpace(rxc.get("MISS")) + "," + CloneArray_ChangeStr.NulltoSpace(row.get("OEMPN"))
						+ "," + CloneArray_ChangeStr.NulltoSpace(row.get("APPLEPN")) + "," + JTBt.getText().trim() + "," + JTQty.getText().trim() + "," + a3 +
						"," + JTlc.getText().trim());

				//ZXingCode.getLogoQRCode(CloneArray_ChangeStr.NulltoSpace(rxc.get("MISS")) + "," + CloneArray_ChangeStr.NulltoSpace(row.get("OEMPN"))
				//+ "," + CloneArray_ChangeStr.NulltoSpace(row.get("APPLEPN")) + "," + JTBt.getText().trim() + "," + JTQty.getText().trim() + "," + a3 +
				//"," + JTlc.getText().trim(), null, null);
			}
			Image imageX = Image.getInstance("D:\\2008.png");
			imageX.scaleAbsolute(50,50);
			PdfPCell kuga = new PdfPCell(imageX);
			kuga.setVerticalAlignment(Element.ALIGN_MIDDLE);
			kuga.setBorder(2);
			pdftable.addCell(createCell("Carton id:",f14,56,7));
			pdftable.addCell(kuga);
			pdftable.addCell(createCell("                         Stage:" + JTStage.getText().trim(),f14,28,11));
			//PdfPCell kong = new PdfPCell(createCell(CloneArray_ChangeStr.NulltoSpace(row.get("OEMCOMMENTS")),f14,22,7));
			PdfPCell kong = new PdfPCell(createCell(CloneArray_ChangeStr.NulltoSpace(row.get("OEMPN")), f14, 22, 7));
			kong.setBorder(15);
			kong.setColspan(3);
			pdftable.addCell(kong);
			//System.out.println(ZXingCode.getLogoQRCode("https://www.baidu.com/", null, null));
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
