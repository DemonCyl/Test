/**
 * Copyright (c) 2008 Pegatron Inc. All Rights Reserved.
 * $Id: Barcode_1616_Gasket.java,v 1.2 2017/05/20 07:32:03 Jieyu_Fu Exp $
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
import java.io.BufferedReader; 
import java.io.File; 
import java.io.FileInputStream; 
import java.io.InputStreamReader; 
import java.io.Reader;
import javax.swing.*;
/**
 * The <code>Barcode_1616_Gasket</code> class.
 *
 * @version     $Name: 1.1 $, $Id: Barcode_1616_Gasket.java,v 1.2 2017/05/20 07:32:03 Jieyu_Fu Exp $
 * @author      <a href="mailto:william_tsai@Pegatroncorp.com">William Tsai</a>
 * @history      [>: General, +: New, #: Modification, -: Remove, ~: Bug]
 *          $Log: Barcode_1616_Gasket.java,v $
 *          Revision 1.2  2017/05/20 07:32:03  Jieyu_Fu
 *          no message
 *
 *          Revision 1.1  2017/05/20 06:33:27  Jieyu_Fu
 *          no message
 *
 */
public class Barcode_1616_Gasket extends BasePanel{
	
	
    public static final String VERSION    = "$Name: 1.1 $, $Id: Barcode_1616_Gasket.java,v 1.2 2017/05/20 07:32:03 Jieyu_Fu Exp $";
    public static final String COPYRIGHT  = "Copyright (c) 2008 PEGATRON Inc. All Rights Reserved.";
	Barcode_1616_Gasket self;
	//private JComboBox JCGaskType;
	Font						f14;
	static PdfPTable			pdftable;
	private JTextField JTFormSou;
	/*private JCheckBox JCBfx;
	private JCheckBox JCBpr;*/
	JTextField jtfLabelQTY;
	JCheckBox jcboxPrint;
	JTextField jtfMO;
	JTextArea jta;
	JLabel JLKjPn;
	KeyListener _keyListener_SVT_Gas;
	public Barcode_1616_Gasket(UI_InitVO uiVO) {
		super(uiVO);
		init();
	}
	
	void init(){
		self=this;
		self.setUILayout(null);
		
		JLabel jLabel_MO = new JLabel("請輸入工單號 : ",JLabel.RIGHT);
		jLabel_MO.setBounds(new Rectangle(10,45,140,25));
		add(jLabel_MO,null);
		
		jtfMO = new JTextField();
		jtfMO.setBounds(new Rectangle(160,45,250,25));
		add(jtfMO,null);
		
		/*JCGaskType = new JComboBox();
		JCGaskType.setSelectedItem("");
		JCGaskType.setBounds(10,80,150,25);
		add(JCGaskType);*/
		
		JLabel JLFormSou = new JLabel("請刷Lot No或者分條碼 : ",JLabel.RIGHT);
		JLFormSou.setBounds(new Rectangle(10,80,140,25));
		add(JLFormSou,null);
		
		JTFormSou = new JTextField();
		JTFormSou.setBounds(new Rectangle(160,80,250,25));
		add(JTFormSou,null);
		JTFormSou.setEditable(false);
		
		/*JCBfx = new JCheckBox("分條");
		JCBfx.setBounds(new Rectangle(35,125,80,25));
		add(JCBfx,null);
		JCBfx.setSelected(false);
		JCBfxListener JBFX = new JCBfxListener();
		JCBfx.addActionListener(JBFX);
		
		JCBpr = new JCheckBox("包裝");
		JCBpr.setBounds(new Rectangle(135,125,80,25));
		add(JCBpr,null);
		JCBpr.setSelected(false);
		JCBprListener JBPR = new JCBprListener();
		JCBpr.addActionListener(JBPR);*/
		
		_keyListener_SVT_Gas = new KeyListener() {
			public void keyPressed(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					GasGj();
				}
			}
			public void keyTyped(KeyEvent e) {}
		};
		JTFormSou.addKeyListener(_keyListener_SVT_Gas);
		
		JLabel jLabel_LabelQTY = new JLabel("打印張數 : ",JLabel.RIGHT);
		jLabel_LabelQTY.setBounds(new Rectangle(10,230,100,25));
		add(jLabel_LabelQTY,null);
		
		jtfLabelQTY = new JTextField();
		jtfLabelQTY.setText("1");
		jtfLabelQTY.setBounds(new Rectangle(120,230,250,25));
		add(jtfLabelQTY,null);
		jtfLabelQTY.setEditable(false);
		
		jcboxPrint = new JCheckBox("連線打印機");
		jcboxPrint.setBounds(new Rectangle(400,230,100,25));
		add(jcboxPrint,null);
		jcboxPrint.setSelected(true);
		
		jta = new JTextArea();
		jta.setBounds(new Rectangle(120,180,400,30));
		add(jta,null);
		jta.setEditable(false);
		
		JLKjPn = new JLabel("");
		JLKjPn.setBounds(new Rectangle(80,160,150,25));
		add(JLKjPn,null);
		
		//let_fit();
		
		setStatus(new int[]{ToolBarItem.SingleQuery,ToolBarItem.Cancel,ToolBarItem.Exit});
	}
	
	/*public class JCBfxListener implements ActionListener {
		private JCheckBox theBtn; 
		public JCBfxListener(){
			super();
		}
		public JCBfxListener(JCheckBox theBtn){
			this();
			this.theBtn = theBtn;

		}
		public void actionPerformed(ActionEvent e) {
			JCBpr.setSelected(false);
		}
	}
	
	public class JCBprListener implements ActionListener {
		private JCheckBox theBtn; 
		public JCBprListener(){
			super();
		}
		public JCBprListener(JCheckBox theBtn){
			this();
			this.theBtn = theBtn;

		}
		public void actionPerformed(ActionEvent e) {
			JCBfx.setSelected(false);
		}
	}*/
	
	private void GasGj(){
		try{
			Vector arg = new Vector();
			arg.add(JTFormSou.getText().trim());
			BaseServletAgent bsa = new BaseServletAgent(uiVO);
			BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
			bvo.setPackageName("SFIS_LABEL_F1616_PEGA_GASKET");
			bvo.setFunctionName("Check_lab_type");	
			ResultVO rvo = bsa.doFunc(bvo);
		  	Vector result = rvo.getData();
			Hashtable row = null;
			row = (Hashtable)result.get(1);
			if(CloneArray_ChangeStr.NulltoSpace(row.get("MISS")).equals("")){
				Afterprint(1,"1",CloneArray_ChangeStr.NulltoSpace(row.get("GASKET_PR")));
			}else{
				Vector uparg = new Vector();
				uparg.add(JLKjPn.getText().trim());
				BaseServletAgent upbsa = new BaseServletAgent(uiVO);
				BaseVO upbvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,uparg);
				upbvo.setPackageName("SFIS_LABEL_F1616_PEGA_GASKET");
				upbvo.setFunctionName("Qty_number");
				ResultVO uprvo = upbsa.doFunc(upbvo);
				Vector upresult = uprvo.getData();
				Hashtable uprow = null;
				if(upresult.size() > 1){
					uprow = (Hashtable)upresult.get(1);
					Afterprint(2,CloneArray_ChangeStr.NulltoSpace(uprow.get("GASKET_NUM")),null);
				}else{
					JOptionPane.showMessageDialog(null,"對照表內無此鎧嘉料號");
				}
				
			}
			JTFormSou.setText("");
			
		}catch(Exception e){
			e.printStackTrace();
			exUtl.ExceptionNotify(e,"Barcode_1616_Gasket GasGj",this.getClass().toString(),VERSION);
		}
	}
	
	/*private void let_fit(){
		try{
			Vector arg = new Vector();
			BaseServletAgent bsa = new BaseServletAgent(uiVO);
			BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
			bvo.setPackageName("SFIS_LABEL_F1616_PEGA_GASKET");
			bvo.setFunctionName("Query_type");	
			ResultVO rvo = bsa.doFunc(bvo);
		  	Vector result = rvo.getData();
			Hashtable row = null;
			JCGaskType.removeAllItems();
			JCGaskType.addItem("");
			for(int n = 1;n <= (result.size() - 1);n++){
				row = (Hashtable)result.get(n);
				JCGaskType.addItem(CloneArray_ChangeStr.NulltoSpace(row.get("GASKET_CUPN")));
			}
		}catch(Exception e){
			e.printStackTrace();
			exUtl.ExceptionNotify(e,"Barcode_1616_Gasket let_fit ",this.getClass().toString(),VERSION);
		}
	}*/
	
	
	
	public void singleQuery() {
		setMessage("");
		String MO = jtfMO.getText().trim();
		if(MO.equals("")){
			JOptionPane.showMessageDialog(null,"請輸入工單號");
			return;
		}
		try {
			String sql = "select t.sfb05,i.ima02 from sfb_file t,ima_file i where t.sfb05 = i.ima01 and t.sfb01 = '" + MO + "'";
			Hashtable ht = getDBDATA(sql,DataSourceType._TIPTOP_KAIJIA_TOPPROD,uiVO);
			if(ht == null){
				JOptionPane.showMessageDialog(null,"無此工單號");
				return;
			}
			jta.setText(CloneArray_ChangeStr.NulltoSpace(ht.get("IMA02")));
			JLKjPn.setText(CloneArray_ChangeStr.NulltoSpace(ht.get("SFB05")));
			jtfMO.setEditable(false);
			JTFormSou.setEditable(true);
			setStatus(new int[]{ToolBarItem.Cancel,ToolBarItem.Exit});
		}catch(Exception e){
			e.printStackTrace();
			exUtl.ExceptionNotify(e,"Barcode_1616_Gasket singleQuery",this.getClass().toString(),VERSION);
		}
	}
	
	public Hashtable getDBDATA(String sql,String ds,UI_InitVO uiVO)throws Exception{
		Vector arg = new Vector();
		arg.add(sql);
	    BaseServletAgent bsa1 = new BaseServletAgent(uiVO);
		BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallSQLCmd2,arg);
		bvo.setDataSourceType(ds);
		ResultVO rvo = bsa1.doFunc(bvo);
		Vector result = rvo.getData();
		Hashtable ht = null;
		if(result.size()>1){
			ht = (Hashtable)result.elementAt(1);
		}
		return ht;
	}
	
	public void cancel() {
		jtfMO.setText("");
		jtfMO.setEditable(true);
		JTFormSou.setText("");
		JTFormSou.setEditable(false);
		jta.setText("");
		JLKjPn.setText("");
		setMessage("");
		setStatus(new int[]{ToolBarItem.SingleQuery,ToolBarItem.Cancel,ToolBarItem.Exit});
	}	
	
	private void Afterprint(int x,String num,String cd){
		jtfLabelQTY.setText(num);
		String printNum=jtfLabelQTY.getText().trim();
		if(printNum.equals("")){
			setMessage("請輸入打印份數!");
			return;
		}
		
		if(!isMatcherPatternStr(printNum,"[0-9]")){
			setMessage("請輸入打印份數(數字 0-9 )!");
			return;
		}
		
		doPrint(jcboxPrint.isSelected(),printNum,x,cd);
	}
	
	public void print() {	
		
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
	
	private void doPrint(boolean isPrint,String printNum,int x,String cd){
		int num=Integer.parseInt(printNum);
		String filePath = "";
		if(x == 1){
			filePath = "d:/1616GasketProduct.pdf";
		}
		if(x == 2){
			filePath = "d:/1616GasketSingle.pdf";
		}
		
		try {
			Document document = new Document(new com.lowagie.text.Rectangle(145, 55), -25, 10, 0, 0);
			PdfWriter writer = PdfWriter.getInstance(document,new FileOutputStream(filePath));
			document.open();
			if(x == 1){
				createPdfTable(writer,cd,x);
				document.add(pdftable);
			}
			if(x == 2){
				for(int i=1;i<=num;i++){
					Vector uparg = new Vector();
					uparg.add(JTFormSou.getText().trim());
					uparg.add(jtfMO.getText().trim());
					uparg.add(JLKjPn.getText().trim());
					BaseServletAgent upbsa = new BaseServletAgent(uiVO);
					BaseVO upbvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,uparg);
					upbvo.setPackageName("SFIS_LABEL_F1616_PEGA_GASKET");
					upbvo.setFunctionName("Quickly_fng");
					ResultVO uprvo = upbsa.doFunc(upbvo);
					Vector upresult = uprvo.getData();
					Hashtable uprow = (Hashtable)upresult.get(1);
					createPdfTable(writer,CloneArray_ChangeStr.NulltoSpace(uprow.get("GASKET_FN")),x);
					document.add(pdftable);
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
		System.out.println("export ok");
		if(isPrint){
			if(!printPdf(filePath)){
				setMessage("打印出錯！");
			}
		}else{
			setMessage("資料已匯出到 " + filePath);
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
	public void createPdfTable(PdfWriter writer,String mmo,int x) {
		initFont();
		pdftable = new PdfPTable(2);
		pdftable.setTotalWidth(130);
		int[] a = { 30, 115 };
		try {
			pdftable.setWidths(a);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pdftable.setLockedWidth(true);
		pdftable.getDefaultCell().setBorder(0);//PdfPCell.NO_BORDER
		pdftable.setHorizontalAlignment(Element.ALIGN_LEFT);
		PdfPCell pega = new PdfPCell(new Paragraph("", null));
		pega.setBorder(0);
		pdftable.addCell(pega);
		pdftable.addCell(createBarCode(writer, mmo, a[1]));
		if(x == 2){
			pdftable.addCell(createCell("12345",f14,0,0));
			pdftable.addCell(createCell(" ",f14,0,0));
			pdftable.addCell(createCell("12345",f14,0,0));
			pdftable.addCell(createCell(jta.getText().trim(),f14,0,0));
		}
		
	}
	
	public void initFont() {
		BaseFont bfChinese;
		try {
			//bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", false);
			bfChinese = BaseFont.createFont("MHei-Medium", "UniCNS-UCS2-H", BaseFont.NOT_EMBEDDED);
			f14 = new Font(bfChinese, 6, Font.BOLD, Color.BLACK);
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
