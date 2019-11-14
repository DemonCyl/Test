/**
 * Copyright (c) 2008 Pegatron Inc. All Rights Reserved.
 * $Id: Warehouse_management_lable.java,v 1.6 2017/04/17 11:01:42 Jieyu_Fu Exp $
 */

package mes_sfis.client.ui.barcode;

import base.client.enums.ToolBarItem;
import base.client.ui.BasePanel;
import base.client.util.BaseServletAgent;
import base.client.util.CloneArray_ChangeStr;
import base.client.util.component.P_Component_Meta;
import base.enums.CommandName;
import base.enums.DataSourceType;
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * The <code>Warehouse_management_lable</code> class.
 *
 * @version     $Name:  $, $Id: Warehouse_management_lable.java,v 1.6 2017/04/17 11:01:42 Jieyu_Fu Exp $
 * @author      <a href="mailto:william_tsai@Pegatroncorp.com">William Tsai</a>
 * @history      [>: General, +: New, #: Modification, -: Remove, ~: Bug]
 *          $Log: Warehouse_management_lable.java,v $
 *          Revision 1.6  2017/04/17 11:01:42  Jieyu_Fu
 *          no message
 *
 *          Revision 1.5  2017/04/17 10:32:09  Jieyu_Fu
 *          no message
 *
 *          Revision 1.4  2017/04/17 06:19:06  Jieyu_Fu
 *          no message
 *
 *          Revision 1.3  2017/04/06 06:16:12  Jieyu_Fu
 *          no message
 *
 *          Revision 1.2  2017/04/06 06:13:01  Jieyu_Fu
 *          no message
 *
 *          Revision 1.1  2017/04/06 03:37:16  Jieyu_Fu
 *          no message
 *
 */
public class Warehouse_management_lable extends BasePanel{
	
	
    public static final String VERSION    = "$Name:  $, $Id: Warehouse_management_lable.java,v 1.6 2017/04/17 11:01:42 Jieyu_Fu Exp $";
    public static final String COPYRIGHT  = "Copyright (c) 2008 PEGATRON Inc. All Rights Reserved.";
	Warehouse_management_lable self;
	Font						f14;
	Font						f13;
	static PdfPTable			pdftable;
	//private JTextField JTQuery_Mo;
	private JTextField JTQuery_Pt;
	//private JComboBox JCPart_No;
	private JTextField JTProName;
	private JTextField JTSize;
	private JTextField JTCount;
	private JTextField JTmanufacturer;
	private JTextField JTSdate;
	private JTextField JTBatch;
	private JTextField JTmachine;
	private JTextField JTBit;
	private JTextField jtfLabelQTY;
	private JCheckBox jcboxPrint;
	public Warehouse_management_lable(UI_InitVO uiVO) {
		super(uiVO);
		init(); 
	}
	void init(){
		self=this;
		self.setUILayout(null);
		
		/*JLabel JLQuery_Mo = new JLabel("請輸入工單號");
		JLQuery_Mo.setBounds(10,10,95,25);
		JTQuery_Mo = new JTextField(20);
        JTQuery_Mo.setBounds(110,10,150,25);
        add(JLQuery_Mo);
        add(JTQuery_Mo);
		
		JLabel JLPart_No = new JLabel("請選擇料號");
		JLPart_No.setBounds(10,50,95,25);
		JCPart_No = new JComboBox();
		JCPart_No.setSelectedItem("");
		JCPart_No.setBounds(110,50,150,25);
		JCPart_NoListener jcptlisten = new JCPart_NoListener();
		JCPart_No.addActionListener(jcptlisten);
		add(JLPart_No);
		add(JCPart_No);
		JCPart_No.setEnabled(false);*/
		
		JLabel JLQuery_Pt = new JLabel("請輸入料號");
		JLQuery_Pt.setBounds(10,10,95,25);
		JTQuery_Pt = new JTextField(20);
        JTQuery_Pt.setBounds(110,10,150,25);
        add(JLQuery_Pt);
        add(JTQuery_Pt);
		
		JLabel JLProName = new JLabel("品名");
		JLProName.setBounds(10,90,95,25);
		JTProName = new JTextField(20);
        JTProName.setBounds(110,90,150,25);
        add(JLProName);
        add(JTProName);
		JTProName.setEditable(false);
		
		JLabel JLSize = new JLabel("規格");
		JLSize.setBounds(10,130,95,25);
		JTSize = new JTextField(20);
        JTSize.setBounds(110,130,150,25);
		JTSize.setEditable(false);
		add(JLSize);
		add(JTSize);
		JTSize.setEditable(false);
		
		JLabel JLCount = new JLabel("數量");
		JLCount.setBounds(10,170,95,25);
		JTCount = new JTextField(20);
        JTCount.setBounds(110,170,150,25);
        add(JLCount);
        add(JTCount);
		JTCount.setEditable(false);
		
		JLabel JLmanufacturer = new JLabel("廠商");
		JLmanufacturer.setBounds(265,10,95,25);
		JTmanufacturer = new JTextField(20);
        JTmanufacturer.setBounds(380,10,150,25);
        add(JLmanufacturer);
        add(JTmanufacturer);
		JTmanufacturer.setEditable(false);
		
		JLabel JLSdate = new JLabel("收貨日期");
		JLSdate.setBounds(265,50,95,25);
		JTSdate = new JTextField(20);
        JTSdate.setBounds(380,50,150,25);
        add(JLSdate);
        add(JTSdate);
		JTSdate.setEditable(false);
		
		JLabel JLBatch = new JLabel("生產批號");
		JLBatch.setBounds(265,90,95,25);
		JTBatch = new JTextField(20);
        JTBatch.setBounds(380,90,150,25);
        add(JLBatch);
        add(JTBatch);
		JTBatch.setEditable(false);
		
		JLabel JLmachine = new JLabel("機種");
		JLmachine.setBounds(265,130,95,25);
		JTmachine = new JTextField(20);
        JTmachine.setBounds(380,130,150,25);
        add(JLmachine);
        add(JTmachine);
		JTmachine.setEditable(false);
		
		JLabel JLBit = new JLabel("單位");
		JLBit.setBounds(265,170,95,25);
		JTBit = new JTextField(20);
        JTBit.setBounds(380,170,150,25);
        add(JLBit);
        add(JTBit);
		JTBit.setEditable(false);
		
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
		
		setStatus(new int[]{ToolBarItem.SingleQuery,ToolBarItem.Cancel,ToolBarItem.Exit});
	}
	/*public class JCPart_NoListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			fun_to_update_pn();
		}
	}*/
	public void singleQuery() {
		setMessage("");
		/*String MO = JTQuery_Mo.getText().trim();
		if(MO.equals("")){
			setMessage("請輸入工單號");
			return;
		}*/
		String Pt = JTQuery_Pt.getText().trim();
		if(Pt.equals("")){
			setMessage("請輸入料號");
			return;
		}
		/*int Status = ToolBarItem.SingleQuery;
		try {
			String sql = "select t.sfa03 from sfa_file t where t.sfa01 = '" + MO + "'";
			Hashtable ht = getDBDATA(sql,DataSourceType._TIPTOP_KAIJIA_TOPPROD,uiVO,Status);
			if(ht == null){
				setMessage("該工單內無料號");
				return;
			}
			JCPart_No.setEnabled(true);
			JTQuery_Mo.setEditable(false);*/
		fun_to_update_pn();
			
		/*} catch (Exception e) {
			e.printStackTrace();
		}*/
		
	}
	
	public void cancel() {
		/*JTQuery_Mo.setText("");
		JCPart_No.setSelectedItem("");
		JTQuery_Mo.setEditable(true);
		JCPart_No.setEnabled(false);*/
		JTProName.setText("");
		JTSize.setText("");
		JTCount.setText("");
		JTmanufacturer.setText("");
		JTSdate.setText("");
		JTBatch.setText("");
		JTmachine.setText("");
		JTBit.setText("");
		JTQuery_Pt.setText("");
		JTQuery_Pt.setEditable(true);
		JTProName.setEditable(false);
		JTSize.setEditable(false);
		JTCount.setEditable(false);
		JTmanufacturer.setEditable(false);
		JTSdate.setEditable(false);
		JTBatch.setEditable(false);
		JTmachine.setEditable(false);
		JTBit.setEditable(false);
		setStatus(new int[]{ToolBarItem.SingleQuery,ToolBarItem.Cancel,ToolBarItem.Exit});
	}
	private void fun_to_update_pn(){
		try{
			/*if(JCPart_No.getSelectedItem() == null){
				return;
			}
			if(JCPart_No.getSelectedItem().equals("")){
				JTProName.setText("");
				JTSize.setText("");
				JTCount.setText("");
				JTmanufacturer.setText("");
				JTSdate.setText("");
				JTBatch.setText("");
				JTmachine.setText("");
				JTBit.setText("");
				return;
			}*/
			//String sql = "select t.ima02,t.ima021,t.ima25,to_char(t.ima73,'YYYY$MM^DD*') TD,t.ima09,t.ima54,g.img04,g.img10 from ima_file t,img_file g where t.ima01 = g.img01 and t.ima01 = '" + JCPart_No.getSelectedItem() + "'";
			String sql = "select t.ima02,t.ima021,t.ima25,to_char(t.ima73,'YYYY$MM^DD*') TD,t.ima09,t.ima54,'' as img04,'' as img10 from ima_file t where  t.ima01 = '" + JTQuery_Pt.getText().trim() + "'";
			//品名、規格、庫存單位、最近入庫日期、機種、主要供應廠商、批號、庫存數量
			int Status = ToolBarItem.Save;
			Hashtable ht = getDBDATA(sql,DataSourceType._TIPTOP_KAIJIA_TOPPROD,uiVO,Status);
			if(ht == null){
				JOptionPane.showMessageDialog(null,"料號無相關信息");
				return;
			}
			JTQuery_Pt.setEditable(false);
			JTProName.setEditable(true);
			JTSize.setEditable(true);
			JTCount.setEditable(true);
			JTmanufacturer.setEditable(true);
			JTSdate.setEditable(true);
			JTBatch.setEditable(true);
			JTmachine.setEditable(true);
			JTBit.setEditable(true);
			jtfLabelQTY.setEditable(true);
			setStatus(new int[]{ToolBarItem.Print,ToolBarItem.Cancel,ToolBarItem.Exit});
		}catch(Exception e){
			e.printStackTrace();
			exUtl.ExceptionNotify(e,"Barcode Warehouse_management_lable fun_to_update_pn ",this.getClass().toString(),VERSION);
		}
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
		
			doPrint(jcboxPrint.isSelected(),printNum);
		
	}
	public PdfPCell createBarCode(PdfWriter writer, String codeStr, int maxWidth) {
		PdfContentByte cd = writer.getDirectContent();
		Barcode128 code39ext = new Barcode128();
		code39ext.setStartStopText(false);
		code39ext.setExtended(true);
		code39ext.setTextAlignment(Element.ALIGN_LEFT);
		code39ext.setBarHeight(15f);
		code39ext.setX(0.5f);

		code39ext.setCode(codeStr);
		Image image39 = code39ext.createImageWithBarcode(cd, null, null);

		PdfPCell barcodeCell = new PdfPCell(image39);
		barcodeCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		barcodeCell.setPadding(2);
		barcodeCell.setBorder(0);
		return barcodeCell;
	}
	
	
	private void doPrint(boolean isPrint,String printNum){
		int num=Integer.parseInt(printNum);
		String filePath = "d:/Warehouse.pdf";
		try{
			Document document = new Document(new com.lowagie.text.Rectangle(210, 160), 10, 10, 0, 0);
			PdfWriter writer = PdfWriter.getInstance(document,new FileOutputStream(filePath));
			document.open();
			for(int i=1;i<=num;i++){
				createPdfTable(writer,null);
				document.add(pdftable);
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
		System.out.println("export ok");
		setStatus(new int[]{ToolBarItem.Cancel,ToolBarItem.Exit});
		if(isPrint){
			if(!printPdf(filePath)){
				setMessage("");
			}
		}else{
			setMessage("資料已匯出到   d:/Warehouse.pdf ");
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
	public Hashtable getDBDATA(String sql,String ds,UI_InitVO uiVO,int Status)throws Exception{
		Vector arg = new Vector();
		arg.add(sql);
	    BaseServletAgent bsa1 = new BaseServletAgent(uiVO);
		BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallSQLCmd2,arg);
		bvo.setDataSourceType(ds);
		ResultVO rvo = bsa1.doFunc(bvo);
		Vector result = rvo.getData();
		Hashtable ht = null;
		/*if(Status == ToolBarItem.SingleQuery){
			JCPart_No.removeAllItems();
			JCPart_No.addItem("");
			if(result.size()>1){
				for(int x = 1;x<=(result.size()-1);x++){
					ht = (Hashtable)result.elementAt(x);
					JCPart_No.addItem(CloneArray_ChangeStr.NulltoSpace(ht.get("SFA03")));
				}
			}
		}*/
		//else if(Status == ToolBarItem.Save){
		if(result.size()>1){
			ht = (Hashtable)result.elementAt(1);
			JTProName.setText(CloneArray_ChangeStr.NulltoSpace(ht.get("IMA02")));
			JTSize.setText(CloneArray_ChangeStr.NulltoSpace(ht.get("IMA021")));
			JTCount.setText(CloneArray_ChangeStr.NulltoSpace(ht.get("IMG10")));
			JTmanufacturer.setText(CloneArray_ChangeStr.NulltoSpace(ht.get("IMA54")));
			JTSdate.setText(CloneArray_ChangeStr.NulltoSpace(ht.get("TD")).replace("$", "年").replace("^", "月").replace("*", "日"));
			JTBatch.setText(CloneArray_ChangeStr.NulltoSpace(ht.get("IMG04")));
			JTmachine.setText(CloneArray_ChangeStr.NulltoSpace(ht.get("IMA09")));
			JTBit.setText(CloneArray_ChangeStr.NulltoSpace(ht.get("IMA25")));
		}
		//}
		return ht;
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
		pdftable = new PdfPTable(4);
		pdftable.setTotalWidth(190);
		int[] a = { 30, 130 };
		try {
			int[] TableWidths = { 10, 40, 20, 20 };
			pdftable.setWidths(TableWidths);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pdftable.setLockedWidth(true);
		pdftable.getDefaultCell().setBorder(15);//PdfPCell.NO_BORDER
		pdftable.setHorizontalAlignment(Element.ALIGN_LEFT);
		PdfPCell mega = new PdfPCell(new Paragraph("", f13));
		mega.setBorder(0);
		mega.setColspan(4);
		mega.setPadding(10);
		pdftable.addCell(mega);
		PdfPCell pega = new PdfPCell(new Paragraph("鎧嘉電腦配件有限公司", f13));
		pega.setBorder(0);
		pega.setColspan(4);
		pega.setVerticalAlignment(Element.ALIGN_CENTER);
		pega.setHorizontalAlignment(1);
		//pdftable.addCell(createCell("",f13,0,0));
		pdftable.addCell(pega);
		pdftable.addCell(createCell("料號",f14,23,7));
		//pdftable.addCell(createCell(JCPart_No.getSelectedItem().toString(),f14,0,7));
		pdftable.addCell(createCell(JTQuery_Pt.getText().trim(),f14,0,7));
		pdftable.addCell(createCell("廠商",f14,0,7));
		pdftable.addCell(createCell(JTmanufacturer.getText().trim(),f14,0,15));
		pdftable.addCell(createCell("品名",f14,15,5));
		pdftable.addCell(createCell(JTProName.getText().trim(),f14,0,5));
		pdftable.addCell(createCell("收貨日期",f14,0,7));
		pdftable.addCell(createCell(JTSdate.getText().trim(),f14,0,15));
		pdftable.addCell(createCell("",f14,15,4));
		pdftable.addCell(createCell("",f14,0,4));
		pdftable.addCell(createCell("生產批號",f14,0,7));
		pdftable.addCell(createCell(JTBatch.getText().trim(),f14,0,15));
		pdftable.addCell(createCell("規格",f14,23,15));
		pdftable.addCell(createCell(JTSize.getText().trim(),f14,0,15));
		pdftable.addCell(createCell("機種",f14,0,15));
		pdftable.addCell(createCell(JTmachine.getText().trim(),f14,0,15));
		pdftable.addCell(createCell("數量",f14,23,7));
		pdftable.addCell(createCell(JTCount.getText().trim(),f14,0,7));
		pdftable.addCell(createCell("單位",f14,0,7));
		pdftable.addCell(createCell(JTBit.getText().trim(),f14,0,15));
	}
	
	public void initFont() {
		BaseFont bfChinese;
		try {
			//bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", false);
			bfChinese = BaseFont.createFont("MHei-Medium", "UniCNS-UCS2-H", BaseFont.NOT_EMBEDDED);
			f14 = new Font(bfChinese, 8, Font.NORMAL, Color.BLACK);
			f13 = new Font(bfChinese, 10, Font.NORMAL, Color.BLACK);
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
