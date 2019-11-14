/**
 * Copyright (c) 2008 Pegatron Inc. All Rights Reserved.
 * $Id: Barcode_1616_table_PEGA.java,v 1.13 2017/06/23 07:21:50 Jieyu_Fu Exp $
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
/**
 * The <code>Barcode_1616_table_PEGA</code> class.
 *
 * @version     $Name:  $, $Id: Barcode_1616_table_PEGA.java,v 1.13 2017/06/23 07:21:50 Jieyu_Fu Exp $
 * @author      <a href="mailto:william_tsai@Pegatroncorp.com">William Tsai</a>
 * @history      [>: General, +: New, #: Modification, -: Remove, ~: Bug]
 *          $Log: Barcode_1616_table_PEGA.java,v $
 *          Revision 1.13  2017/06/23 07:21:50  Jieyu_Fu
 *          no message
 *
 *          Revision 1.12  2017/05/24 02:54:49  Jieyu_Fu
 *          no message
 *
 *          Revision 1.11  2017/04/07 11:14:38  Jieyu_Fu
 *          no message
 *
 *          Revision 1.10  2017/03/25 02:59:45  Jieyu_Fu
 *          no message
 *
 *          Revision 1.9  2017/03/24 07:21:48  Jieyu_Fu
 *          no message
 *
 *          Revision 1.8  2017/03/17 02:52:07  Jieyu_Fu
 *          no message
 *
 *          Revision 1.7  2017/03/14 03:08:53  Jieyu_Fu
 *          no message
 *
 *          Revision 1.6  2017/02/11 02:22:31  Jieyu_Fu
 *          no message
 *
 *          Revision 1.5  2017/02/11 02:09:22  Jieyu_Fu
 *          no message
 *
 *          Revision 1.4  2017/02/09 07:03:40  Jieyu_Fu
 *          no message
 *
 *          Revision 1.3  2017/02/07 02:38:11  Jieyu_Fu
 *          no message
 *
 *          Revision 1.2  2017/02/07 01:38:10  Jieyu_Fu
 *          no message
 *
 *          Revision 1.1  2017/02/06 02:36:14  Jieyu_Fu
 *          no message
 *
 *          Revision 1.19  2016/11/22 08:14:59  Jieyu_Fu
 *          no message
 *
 */
public class Barcode_1616_table_PEGA extends BasePanel{
	
	
    public static final String VERSION    = "$Name:  $, $Id: Barcode_1616_table_PEGA.java,v 1.13 2017/06/23 07:21:50 Jieyu_Fu Exp $";
    public static final String COPYRIGHT  = "Copyright (c) 2008 PEGATRON Inc. All Rights Reserved.";
	Barcode_1616_table_PEGA self;
	public Barcode_1616_table_PEGA(UI_InitVO uiVO) {
		super(uiVO);
		init(); 
	}
	Font						f14;
	String yield;
	String count;
	String strmt;
	String stroid;
	String strflow;
	String strbglab;
	String strbjpa;
	String strbjkj;
	String ProNumber;
	int bc = 0;
	int jc = 0;
	int sum = 0;
	int printsource = 1616;
	int chStatus = 0;
	int butocou = 0;
	int jatocou = 0;
	int btSta = 0;
	String Bbt;
	Vector vtabcd = new Vector();
	String frmo;
	int fitbc;
	int fitjc;
	Hashtable Materialquantity = new Hashtable();
	JTextField jtfMO;
	JTextField jtfBuShua;
	JLabel BuCount;
	JLabel BuTotal;
	JTextField jtfJaShua;
	JLabel JaCount;
	JLabel JaTotal;
	JLabel allout;
	JTextField jtfMakeUp;
	JTextField jtfexport;
	JTextField jtfLabelQTY;
	JCheckBox jcboxPrint;
	JTextArea jta;
	JButton JBprint;
	JButton JBmuprint;
	JButton JBfit;
	JButton JBfitbt;
	JButton JBstep;
	JLabel JLBuShua;
	JLabel JLJaShua;
	JLabel JLexport;
	JTextField JTChangeMo;
	JLabel JLChangeMo;
	int changemo = 1;
	static String CMO;
	static PdfPTable			pdftable;
	KeyListener _keyListener_SVT_QueryBu;
	KeyListener _keyListener_SVT_QueryJa;
	KeyListener _keyListener_SVT_QueryMu;
	KeyListener _keyListener_SVT_QueryEx;
	KeyListener _keyListener_SVT_ChangeMo;
	private PDateTimeTextField PDEndTime;
	void init(){
		self=this;
		self.setUILayout(null);
		
		JLabel jLabel_MO = new JLabel("請輸入工單號 : ",JLabel.RIGHT);
		jLabel_MO.setBounds(new Rectangle(10,45,100,25));
		add(jLabel_MO,null);
		
		jtfMO = new JTextField();
		jtfMO.setBounds(new Rectangle(120,45,250,25));
		add(jtfMO,null);
		jtfMO.setEditable(false);
		
		JLBuShua = new JLabel("請刷布料號 : ",JLabel.RIGHT);
		JLBuShua.setBounds(new Rectangle(10,95,100,25));
		add(JLBuShua,null);
		
		jtfBuShua = new JTextField();
		jtfBuShua.setBounds(new Rectangle(120,95,250,25));
		add(jtfBuShua,null);
		jtfBuShua.setEditable(false);
		
		BuCount = new JLabel("已刷: " + Integer.toString(bc),JLabel.RIGHT);
		BuCount.setBounds(new Rectangle(330,95,100,25));
		add(BuCount,null);
		
		BuTotal = new JLabel("工單已用量: " + Integer.toString(butocou),JLabel.RIGHT);
		BuTotal.setBounds(new Rectangle(440,95,100,25));
		add(BuTotal,null);
		
		_keyListener_SVT_QueryBu = new KeyListener() {
			public void keyPressed(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					BUSVT_Query();
				}
			}
			public void keyTyped(KeyEvent e) {}
		};
		jtfBuShua.addKeyListener(_keyListener_SVT_QueryBu);
		
		JLJaShua = new JLabel("請刷膠料號 : ",JLabel.RIGHT);
		JLJaShua.setBounds(new Rectangle(10,145,100,25));
		add(JLJaShua,null);
		
		jtfJaShua = new JTextField();
		jtfJaShua.setBounds(new Rectangle(120,145,250,25));
		add(jtfJaShua,null);
		jtfJaShua.setEditable(false);
		
		JaCount = new JLabel("已刷: " + Integer.toString(jc),JLabel.RIGHT);
		JaCount.setBounds(new Rectangle(330,145,100,25));
		add(JaCount,null);
		
		JaTotal = new JLabel("工單已用量: " + Integer.toString(jatocou),JLabel.RIGHT);
		JaTotal.setBounds(new Rectangle(440,145,100,25));
		add(JaTotal,null);
		
		_keyListener_SVT_QueryJa = new KeyListener() {
			public void keyPressed(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					JASVT_Query();
				}
			}
			public void keyTyped(KeyEvent e) {}
		};
		jtfJaShua.addKeyListener(_keyListener_SVT_QueryJa);
		
		JLabel JLMakeUp = new JLabel("補印條碼 : ",JLabel.RIGHT);
		JLMakeUp.setBounds(new Rectangle(10,195,100,25));
		add(JLMakeUp,null);
		
		jtfMakeUp = new JTextField();
		jtfMakeUp.setBounds(new Rectangle(120,195,250,25));
		add(jtfMakeUp,null);
		jtfMakeUp.setEditable(false);
		
		_keyListener_SVT_QueryMu = new KeyListener() {
			public void keyPressed(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					MuSVT_Query();
				}
			}
			public void keyTyped(KeyEvent e) {}
		};
		jtfMakeUp.addKeyListener(_keyListener_SVT_QueryMu);
		
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
		jta.setBounds(new Rectangle(120,325,400,200));
		add(jta,null);
		jta.setEditable(false);
		
		JBprint = new JButton("分條");
		JBprint.setBounds(380,25,130,60);
		JBmuprint = new JButton("補印");
		JBmuprint.setBounds(380,175,130,60);
		BtnprintListener BtPr = new BtnprintListener();
		JBprint.addActionListener(BtPr);
		BtnmuprintListener BtMu = new BtnmuprintListener();
		JBmuprint.addActionListener(BtMu);
		add(JBprint);
		add(JBmuprint);
		
		JBfit = new JButton("Top貼合");
		JBfit.setBounds(512,25,130,60);
		add(JBfit);
		BtnfitListener BtFt = new BtnfitListener();
		JBfit.addActionListener(BtFt);
		
		JBfitbt = new JButton("Bottom貼合");
		JBfitbt.setBounds(543,102,102,60);
		add(JBfitbt);
		BtnfitbtListener BtFtbt = new BtnfitbtListener();
		JBfitbt.addActionListener(BtFtbt);
		
		
		JBstep = new JButton("過站");
		JBstep.setBounds(512,175,130,60);
		add(JBstep);
		BtnStepListener BtSt = new BtnStepListener();
		JBstep.addActionListener(BtSt);
		
		JLexport = new JLabel("刷貼合標籤 : ",JLabel.RIGHT);
		JLexport.setBounds(new Rectangle(10,245,100,25));
		add(JLexport,null);
		
		jtfexport = new JTextField();
		jtfexport.setBounds(new Rectangle(120,245,250,25));
		add(jtfexport,null);
		jtfexport.setEditable(false);
		
		PDEndTime=new PDateTimeTextField(uiVO,"PDEndTime",150,true,true);
		PDEndTime.setBounds(new Rectangle(400,245,150,25));
		add(PDEndTime);
		
		_keyListener_SVT_QueryEx = new KeyListener() {
			public void keyPressed(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					ExSVT_Query();
				}
			}
			public void keyTyped(KeyEvent e) {}
		};
		jtfexport.addKeyListener(_keyListener_SVT_QueryEx);
		
		allout = new JLabel("",JLabel.RIGHT);
		allout.setBounds(new Rectangle(60,20,300,25));
		add(allout,null);
		
		JLChangeMo = new JLabel("請刷現工單分條碼 : ",JLabel.RIGHT);
		JLChangeMo.setBounds(new Rectangle(10,10,100,25));
		add(JLChangeMo,null);
		
		JTChangeMo = new JTextField();
		JTChangeMo.setBounds(new Rectangle(120,10,250,25));
		add(JTChangeMo,null);
		
		_keyListener_SVT_ChangeMo = new KeyListener() {
			public void keyPressed(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					to_ChangeMo();
				}
			}
			public void keyTyped(KeyEvent e) {}
		};
		JTChangeMo.addKeyListener(_keyListener_SVT_ChangeMo);
		
		setStatus(new int[]{ToolBarItem.Cancel,ToolBarItem.Exit});
	}
	
	private void to_ChangeMo(){
		if(changemo == 1){
			try{
				Vector arg = new Vector();
				arg.add(JTChangeMo.getText().trim());
				BaseServletAgent bsa = new BaseServletAgent(uiVO);
				BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
				bvo.setPackageName("SFIS_LABEL_FINGERPRINT_PEGA_PG");
				bvo.setFunctionName("Check_newtimeflow");	
				ResultVO rvo = bsa.doFunc(bvo);
				Vector result = rvo.getData();
				//Hashtable row = null;
				if(result.size() < 2){
					JOptionPane.showMessageDialog(null,"此分條碼不存在");
					return;
				}
				CMO = JTChangeMo.getText().trim().substring(0,13);
				JLChangeMo.setText("請刷餘料分條碼");
				changemo = 2;
				JTChangeMo.setText("");
			}catch(Exception e){
				e.printStackTrace();
				exUtl.ExceptionNotify(e,"Barcode_1616_table_PEGA to_ChangeMo",this.getClass().toString(),VERSION);
			}
			
		}else{
			try{
				Vector arg = new Vector();
				arg.add(JTChangeMo.getText().trim());
				arg.add(CMO);
				BaseServletAgent bsa = new BaseServletAgent(uiVO);
				BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
				bvo.setPackageName("SFIS_LABEL_FINGERPRINT_PEGA_PG");
				bvo.setFunctionName("let_me_changemo");	
				ResultVO rvo = bsa.doFunc(bvo);
				Vector result = rvo.getData();
				Hashtable row = null;
				if(result.size() > 1){
					row = (Hashtable)result.get(1);
					if(CloneArray_ChangeStr.NulltoSpace(row.get("MISS")).equals("NON")){
						JOptionPane.showMessageDialog(null,"此分條碼不存在");
						return;
					}
					if(CloneArray_ChangeStr.NulltoSpace(row.get("MISS")).equals("NG")){
						JOptionPane.showMessageDialog(null,"此分條碼已經貼合");
						return;
					}
					Afterprint("1",CloneArray_ChangeStr.NulltoSpace(row.get("MISS")));
					JTChangeMo.setText("");
					changemo = 1;
					JLChangeMo.setText("請刷現工單分條碼");
				}
			}catch(Exception e){
				e.printStackTrace();
				exUtl.ExceptionNotify(e,"Barcode_1616_table_PEGA to_ChangeMo",this.getClass().toString(),VERSION);
			}
		}
		
	}
	
	private void Afterprint(String num,String cd){
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
		
		AfterdoPrint(jcboxPrint.isSelected(),printNum,cd);
	}
	
	private void AfterdoPrint(boolean isPrint,String printNum,String cd){
		int num=Integer.parseInt(printNum);
		String filePath = "d:/1616ChangeMo.pdf";
		
		try {
			Document document = new Document(new com.lowagie.text.Rectangle(210, 45), -25, 10, 0, 0);
			PdfWriter writer = PdfWriter.getInstance(document,new FileOutputStream(filePath));
			document.open();
			AftercreatePdfTable(writer,cd);
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
		if(isPrint){
			if(!printPdf(filePath)){
				setMessage("打印出錯！");
			}
		}else{
			setMessage("資料已匯出到 " + filePath);
		}
			
	}
	
	public void AftercreatePdfTable(PdfWriter writer,String mmo) {
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
		
	}
	
	public class BtnprintListener implements ActionListener {
		private JButton theBtn; 
		public BtnprintListener(){
			super();
		}
		public BtnprintListener(JButton theBtn){
			this();
			this.theBtn = theBtn;

		}
		public void actionPerformed(ActionEvent e) {
			cancel();
			jtfMO.setEditable(true);
			jtfexport.setEditable(false);
			JBmuprint.setEnabled(false);
			JBprint.setEnabled(false);
			JBfit.setEnabled(false);
			JBstep.setEnabled(false);
			JBfitbt.setEnabled(false);
			setStatus(new int[]{ToolBarItem.SingleQuery,ToolBarItem.Cancel,ToolBarItem.Exit});
		}
	}
	public class BtnmuprintListener implements ActionListener {
		private JButton theBtn; 
		public BtnmuprintListener(){
			super();
		}
		public BtnmuprintListener(JButton theBtn){
			this();
			this.theBtn = theBtn;

		}
		public void actionPerformed(ActionEvent e) {
			cancel();
			jtfMakeUp.setEditable(true);
			jtfexport.setEditable(false);
			JBprint.setEnabled(false);
			JBmuprint.setEnabled(false);
			JBfit.setEnabled(false);
			JBstep.setEnabled(false);
			JBfitbt.setEnabled(false);
		}
	}
	public class BtnfitListener implements ActionListener {
		private JButton theBtn; 
		public BtnfitListener(){
			super();
		}
		public BtnfitListener(JButton theBtn){
			this();
			this.theBtn = theBtn;

		}
		public void actionPerformed(ActionEvent e) {
			cancel();
			vtabcd.clear();
			JBmuprint.setEnabled(false);
			JBprint.setEnabled(false);
			JBfit.setEnabled(false);
			JBstep.setEnabled(false);
			JBfitbt.setEnabled(false);
			JLexport.setText("刷入一膠兩布 : ");
			jtfexport.setEditable(true);
			printsource = 8484;
			btSta = 0;
		}
	}
	public class BtnfitbtListener implements ActionListener {
		private JButton theBtn; 
		public BtnfitbtListener(){
			super();
		}
		public BtnfitbtListener(JButton theBtn){
			this();
			this.theBtn = theBtn;

		}
		public void actionPerformed(ActionEvent e) {
			cancel();
			vtabcd.clear();
			JBmuprint.setEnabled(false);
			JBprint.setEnabled(false);
			JBfit.setEnabled(false);
			JBstep.setEnabled(false);
			JBfitbt.setEnabled(false);
			JLexport.setText("刷入一膠兩布 : ");
			jtfexport.setEditable(true);
			printsource = 8484;
			btSta = 12807;
		}
	}
	public class BtnStepListener implements ActionListener {
		private JButton theBtn; 
		public BtnStepListener(){
			super();
		}
		public BtnStepListener(JButton theBtn){
			this();
			this.theBtn = theBtn;

		}
		public void actionPerformed(ActionEvent e) {
			cancel();
			JBmuprint.setEnabled(false);
			JBprint.setEnabled(false);
			JBfit.setEnabled(false);
			JBstep.setEnabled(false);
			JBfitbt.setEnabled(false);
			printsource = 8998;
			jtfexport.setEditable(true);
		}
	}
	public void singleQuery() {
		setMessage("");
		String MO = jtfMO.getText().trim();
		if(MO.equals("")){
			setMessage("請輸入工單號!");
			return;
		}
		
		try {
			String sql = "select b.sfb08,a.sfa03,a.sfa05,i.ima02 from sfa_file a,sfb_file b,ima_file i where b.sfb01 = '"+MO+"' and a.sfa01 = b.sfb01"
			+ " and i.ima01 = a.sfa03 and a.sfa05 != '0'";
			Hashtable ht = getDBDATA(sql,DataSourceType._TIPTOP_KAIJIA_TOPPROD,uiVO);
			if(ht == null){
				setMessage("Tiptop 查無此工單");
				return;
			}
			Iterator itr = Materialquantity.keySet().iterator();
			while (itr.hasNext()){
				String str = CloneArray_ChangeStr.NulltoSpace(itr.next());
				  jta.append(str + "————————" + Materialquantity.get(str) + "\n");
			}
			jta.append("需刷總次數: " + Integer.toString(sum));
			Vector arg = new Vector();
			arg.add(MO);
			BaseServletAgent bsa = new BaseServletAgent(uiVO);
			BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
			bvo.setPackageName("SFIS_LABEL_FINGERPRINT_PEGA_PG");
			bvo.setFunctionName("Query_Total_Bu");	
			ResultVO rvo = bsa.doFunc(bvo);
		  	Vector result = rvo.getData();
			butocou = result.size()-1;
			BuTotal.setText("工單已用量: " + Integer.toString(butocou));
			Vector uparg = new Vector();
			uparg.add(MO);
			BaseServletAgent upbsa = new BaseServletAgent(uiVO);
			BaseVO upbvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,uparg);
			upbvo.setPackageName("SFIS_LABEL_FINGERPRINT_PEGA_PG");
			upbvo.setFunctionName("Query_Total_Ja");
			ResultVO uprvo = upbsa.doFunc(upbvo);
			Vector upresult = uprvo.getData();
			jatocou = upresult.size()-1;
			JaTotal.setText("工單已用量: " + Integer.toString(jatocou));
			Vector alarg = new Vector();
			alarg.add(MO);
			BaseServletAgent albsa = new BaseServletAgent(uiVO);
			BaseVO albvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,alarg);
			albvo.setPackageName("SFIS_LABEL_FINGERPRINT_PEGA_PG");
			albvo.setFunctionName("Query_Have_Print");
			ResultVO alrvo = albsa.doFunc(albvo);
			Vector alresult = alrvo.getData();
			int alcount = alresult.size()-1;
			allout.setText("該工單產品總數量: " + yield + "   已印成品標籤數量: " + Integer.toString(alcount));
			//getMoTime();
			//getMoFlow();
			//getDelOid();
			//strbglab = strmt + strflow;
			jtfMO.setEditable(false);
			jtfBuShua.setEditable(true);
			jtfJaShua.setEditable(true);
			jtfLabelQTY.setText("5");
			jtfLabelQTY.setEditable(false);
			printsource = ToolBarItem.SingleQuery;
			setStatus(new int[]{ToolBarItem.Cancel}); //ToolBarItem.Print
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public void MuSVT_Query(){
		setMessage("");
		printsource = 3232;
		jtfLabelQTY.setEditable(true);
		setStatus(new int[]{ToolBarItem.Print,ToolBarItem.Cancel});
	}
	public void ExSVT_Query(){
		setMessage("");
		/*if(PDEndTime.getText().equals("")){
			setMessage("請選擇結束日期！");
			return;
		}*/
		if(printsource == 8484){
			printsource = 8484;
			dofitfun();
		}
		if(printsource == 8998){
			funStep(); 
		}
		/*if(printsource != 8484 && printsource != 8998){
			printsource = 4848;
			funStep();
			jtfLabelQTY.setEditable(true);
			setStatus(new int[]{ToolBarItem.Print,ToolBarItem.Cancel});
		}*/
	}
	public void BUSVT_Query(){
		setMessage("");
		if(chStatus == 0){
			try{
				strbjpa = jtfBuShua.getText().trim();
				Vector alarg = new Vector();
				alarg.add(jtfBuShua.getText().trim());
				BaseServletAgent albsa = new BaseServletAgent(uiVO);
				BaseVO albvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,alarg);
				albvo.setPackageName("SFIS_LABEL_FINGERPRINT_PEGA_PG");
				albvo.setFunctionName("Goto_contrast_BJPega");
				ResultVO alrvo = albsa.doFunc(albvo);
				Vector alresult = alrvo.getData();
				if(alresult.size()<2){
					setMessage("對照表內無此客戶料號");
					return;
				}
				Hashtable bjpa = (Hashtable)alresult.get(1);
				strbjkj = CloneArray_ChangeStr.NulltoSpace(bjpa.get("KJNUM"));
				String sql = "select b.sfb08,a.sfa03,a.sfa05,i.ima02 from sfa_file a,sfb_file b,ima_file i where b.sfb01 = '"+jtfMO.getText().trim()+"' and a.sfa01 = b.sfb01"
				+ " and i.ima01 = a.sfa03 and a.sfa05 != '0' and a.sfa03 = '" + strbjkj + "'";
				Hashtable checkbj = getDBCOPYDATA(sql,DataSourceType._TIPTOP_KAIJIA_TOPPROD,uiVO);
				if(checkbj == null){
					setMessage("料號不符");
					return;
				}
				JLBuShua.setText("請刷布批號 : ");
				jtfBuShua.setText("");
				chStatus = 1;
				jtfJaShua.setEditable(false);
			}catch(Exception e){
				e.printStackTrace();
				exUtl.ExceptionNotify(e,"Barcode_1616_table_PEGA BUSVT_Query",this.getClass().toString(),VERSION);
			}
		}
		else if(chStatus == 1){
			try{
				/*Vector arg = new Vector();
				arg.add(strbglab);
				arg.add(stroid);
				arg.add(jtfBuShua.getText().trim());
				arg.add(strbjpa);
				arg.add(strbjkj);
				BaseServletAgent bsa = new BaseServletAgent(uiVO);
				BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
				bvo.setPackageName("SFIS_LABEL_FINGERPRINT_PEGA_PG");
				bvo.setFunctionName("Insert_BULABEL_INFO");*/
				Vector arg = new Vector();
				arg.add(jtfBuShua.getText().trim());
				BaseServletAgent bsa = new BaseServletAgent(uiVO);
				BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
				bvo.setPackageName("SFIS_LABEL_FINGERPRINT_PEGA_PG");
				bvo.setFunctionName("Check_Bu_exist");
				ResultVO rvo = bsa.doFunc(bvo);
				Vector result = rvo.getData();
				Hashtable row = (Hashtable)result.get(1);
				if(CloneArray_ChangeStr.NulltoSpace(row.get("MISSING")).equals("NG")){
					setMessage("該布已經刷過");
					JLBuShua.setText("請刷布料號 : ");
					chStatus = 0;
					jtfJaShua.setEditable(true);
					return;
				}
				if(CloneArray_ChangeStr.NulltoSpace(row.get("MISSING")).equals("OK")){
					setMessage("刷入成功");
					bc +=1;
					BuCount.setText("已刷: " + Integer.toString(bc));
					butocou +=1;
					BuTotal.setText("工單已用量: " + Integer.toString(butocou));
					//jtfBuShua.setText("");
					JLBuShua.setText("請刷布料號 : ");
					chStatus = 0;
					jtfJaShua.setEditable(false);
					jtfBuShua.setEditable(false);
					setStatus(new int[]{ToolBarItem.Print,ToolBarItem.Cancel});
				}
			}catch(Exception e){
				e.printStackTrace();
				exUtl.ExceptionNotify(e,"Barcode_1616_table_PEGA BUSVT_Query",this.getClass().toString(),VERSION);
			}
		}
	}
	public void JASVT_Query(){
		setMessage("");
		if(chStatus == 0){
			try{
				strbjpa = jtfJaShua.getText().trim();
				Vector alarg = new Vector();
				alarg.add(jtfJaShua.getText().trim());
				BaseServletAgent albsa = new BaseServletAgent(uiVO);
				BaseVO albvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,alarg);
				albvo.setPackageName("SFIS_LABEL_FINGERPRINT_PEGA_PG");
				albvo.setFunctionName("Goto_contrast_BJPega");
				ResultVO alrvo = albsa.doFunc(albvo);
				Vector alresult = alrvo.getData();
				if(alresult.size()<2){
					setMessage("對照表內無此客戶料號");
					return;
				}
				Hashtable bjpa = (Hashtable)alresult.get(1);
				strbjkj = CloneArray_ChangeStr.NulltoSpace(bjpa.get("KJNUM"));
				String sql = "select b.sfb08,a.sfa03,a.sfa05,i.ima02 from sfa_file a,sfb_file b,ima_file i where b.sfb01 = '"+jtfMO.getText().trim()+"' and a.sfa01 = b.sfb01"
				+ " and i.ima01 = a.sfa03 and a.sfa05 != '0' and a.sfa03 = '" + strbjkj + "'";
				Hashtable checkbj = getDBCOPYDATA(sql,DataSourceType._TIPTOP_KAIJIA_TOPPROD,uiVO);
				if(checkbj == null){
					setMessage("料號不符");
					return;
				}
				JLJaShua.setText("請刷膠批號 : ");
				jtfJaShua.setText("");
				chStatus = 1;
				jtfBuShua.setEditable(false);
			}catch(Exception e){
				e.printStackTrace();
				exUtl.ExceptionNotify(e,"Barcode_1616_table_PEGA JASVT_Query",this.getClass().toString(),VERSION);
			}
		}
		else if(chStatus == 1){
			try{
				/*Vector arg = new Vector();
				arg.add(strbglab);
				arg.add(stroid);
				arg.add(jtfJaShua.getText().trim());
				arg.add(strbjpa);
				arg.add(strbjkj);
				BaseServletAgent bsa = new BaseServletAgent(uiVO);
				BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
				bvo.setPackageName("SFIS_LABEL_FINGERPRINT_PEGA_PG");
				bvo.setFunctionName("Insert_JALABEL_INFO");*/
				Vector arg = new Vector();
				arg.add(jtfJaShua.getText().trim());
				BaseServletAgent bsa = new BaseServletAgent(uiVO);
				BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
				bvo.setPackageName("SFIS_LABEL_FINGERPRINT_PEGA_PG");
				bvo.setFunctionName("Check_Ja_exist");
				ResultVO rvo = bsa.doFunc(bvo);
				Vector result = rvo.getData();
				Hashtable row = (Hashtable)result.get(1);
				if(CloneArray_ChangeStr.NulltoSpace(row.get("MISSING")).equals("NG")){
					setMessage("該膠已經刷過");
					JLJaShua.setText("請刷膠料號 : ");
					chStatus = 0;
					jtfBuShua.setEditable(true);
					return;
				}
				if(CloneArray_ChangeStr.NulltoSpace(row.get("MISSING")).equals("OK")){
					setMessage("刷入成功");
					jc +=1;
					JaCount.setText("已刷: " + Integer.toString(jc));
					jatocou +=1;
					JaTotal.setText("工單已用量: " + Integer.toString(jatocou));
					//jtfJaShua.setText("");
					JLJaShua.setText("請刷膠料號 : ");
					chStatus = 0;
					jtfBuShua.setEditable(false);
					jtfJaShua.setEditable(false);
					setStatus(new int[]{ToolBarItem.Print,ToolBarItem.Cancel});
				}
			}catch(Exception e){
				e.printStackTrace();
				exUtl.ExceptionNotify(e,"Barcode_1616_table_PEGA JASVT_Query",this.getClass().toString(),VERSION);
			}
		}
	}
	public void cancel() {
		setStatus(new int[]{ToolBarItem.Cancel,ToolBarItem.Exit});
		jtfMO.setText("");
		jtfMO.setEditable(false);
		jtfBuShua.setText("");
		jtfBuShua.setEditable(false);
		jtfJaShua.setText("");
		jtfJaShua.setEditable(false);
		jtfMakeUp.setText("");
		jtfMakeUp.setEditable(false);
		jtfexport.setText("");
		jtfexport.setEditable(false);
		jtfLabelQTY.setText("1");
		jtfLabelQTY.setEditable(false);
		JBprint.setEnabled(true);
		JBmuprint.setEnabled(true);
		JLexport.setText("刷貼合標籤 : ");
		allout.setText("");
		bc = 0;
		BuCount.setText("已刷: " + Integer.toString(bc));
		butocou = 0;
		BuTotal.setText("工單已用量: " + Integer.toString(butocou));
		jc = 0;
		JaCount.setText("已刷: " + Integer.toString(jc));
		jatocou = 0;
		JaTotal.setText("工單已用量: " + Integer.toString(jatocou));
		printsource = 1616;
		jta.setText("");
		sum = 0;
		chStatus = 0;
		JLBuShua.setText("請刷布料號 : ");
		JLJaShua.setText("請刷膠料號 : ");
		vtabcd.clear();
		JBfit.setEnabled(true);
		JBstep.setEnabled(true);
		frmo = null;
		JBfitbt.setEnabled(true);
		btSta = 0;
		JLChangeMo.setText("請刷現工單分條碼");
		changemo = 1;
	}
	private void readTxtFile(String filePath){
        try {
                String encoding="GBK";
                File file=new File(filePath);
                if(file.isFile() && file.exists()){
                    InputStreamReader read = new InputStreamReader(
                    new FileInputStream(file),encoding);
                    BufferedReader bufferedReader = new BufferedReader(read);
                    String lineTxt = null;
                    while((lineTxt = bufferedReader.readLine()) != null){
                        //System.out.println(lineTxt);
                        int a = Integer.parseInt(lineTxt);
                        /*if(a == 1){
                        	System.out.println("123");
                        }*/
						Vector arg = new Vector();
						arg.add(jtfexport.getText().trim());
						BaseServletAgent bsa = new BaseServletAgent(uiVO);
						BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
						bvo.setPackageName("SFIS_LABEL_FINGERPRINT_PEGA_PG");
						bvo.setFunctionName("Check_fitlable_status");	
						ResultVO rvo = bsa.doFunc(bvo);
						Vector result = rvo.getData();
						Hashtable row = (Hashtable)result.get(1);
						if(CloneArray_ChangeStr.NulltoSpace(row.get("MISSING")).equals("NG")){
							setMessage("無此貼合標籤");
							return;
						}
						String b = CloneArray_ChangeStr.NulltoSpace(row.get("MISSING"));
						Vector argup = new Vector();
						argup.add(b);
						BaseServletAgent bsaup = new BaseServletAgent(uiVO);
						BaseVO bvoup = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,argup);
						bvoup.setPackageName("SFIS_LABEL_FINGERPRINT_PEGA_PG");
						bvoup.setFunctionName("Query_fitlable_status");
						ResultVO rvoup = bsaup.doFunc(bvoup);
						Vector resultup = rvoup.getData();
						Hashtable rowup = (Hashtable)resultup.get(1);
						if(!(a == (Integer.parseInt(b) + 1))){
							jta.setText("");
							jta.append("該貼合標籤目前所在站點 : " + CloneArray_ChangeStr.NulltoSpace(rowup.get("MISSING")) + "\n" + "過站失敗");
							return;
						}
						if(a == (Integer.parseInt(b) + 1)){
							if(b.equals("0")){
								Vector uparg = new Vector();
								uparg.add(jtfexport.getText().trim());
								BaseServletAgent upbsa = new BaseServletAgent(uiVO);
								BaseVO upbvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,uparg);
								upbvo.setPackageName("SFIS_LABEL_FINGERPRINT_PEGA_PG");
								upbvo.setFunctionName("Query_time_reduce");
								ResultVO uprvo = upbsa.doFunc(upbvo);
								Vector upresult = uprvo.getData();
								Hashtable uprow = (Hashtable)upresult.get(1);
								int endtime = Integer.parseInt(CloneArray_ChangeStr.NulltoSpace(uprow.get("BF")));
								int nowtime = Integer.parseInt(CloneArray_ChangeStr.NulltoSpace(uprow.get("AF")));
								int h = Integer.parseInt(Integer.toString(endtime).substring(8,10));
								int d = Integer.parseInt(Integer.toString(endtime).substring(0,8));
								/*if(nowtime < (endtime + 100)){
									jta.setText("");
									jta.append("該貼合標籤目前所在站點 : " + CloneArray_ChangeStr.NulltoSpace(rowup.get("MISSING")) + "\n" +
									"未到24小時,過站失敗");
									return;
								}*/
								if(h + 12 > 24){
									h = h - 12;
									d = d + 1;
									String sh = Integer.toString(h);
									String sd = Integer.toString(d);
									if(sh.length() < 2){
										sh = "0" + sh;
									}
									endtime = Integer.parseInt(sd + sh);
									if(nowtime < endtime){
										jta.setText("");
										jta.append("該貼合標籤目前所在站點 : " + CloneArray_ChangeStr.NulltoSpace(rowup.get("MISSING")) + "\n" +
										"未到12小時,過站失敗");
										return;
									}
								}else if(h + 12 <= 24){
									endtime = endtime + 12;
									if(nowtime < endtime){
										jta.setText("");
										jta.append("該貼合標籤目前所在站點 : " + CloneArray_ChangeStr.NulltoSpace(rowup.get("MISSING")) + "\n" +
										"未到12小時,過站失敗");
										return;
									}
								}
								Vector csarg = new Vector();
								csarg.add(jtfexport.getText().trim());
								csarg.add(Integer.toString(a));
								BaseServletAgent csbsa = new BaseServletAgent(uiVO);
								BaseVO csbvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,csarg);
								csbvo.setPackageName("SFIS_LABEL_FINGERPRINT_PEGA_PG");
								csbvo.setFunctionName("Change_fitlable_status");
								ResultVO csrvo = csbsa.doFunc(csbvo);
								jta.setText("");
								jta.append("OK");
								jtfexport.setText("");
							}
							if(b.equals("1")){
								Vector uparg = new Vector();
								uparg.add(jtfexport.getText().trim());
								BaseServletAgent upbsa = new BaseServletAgent(uiVO);
								BaseVO upbvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,uparg);
								upbvo.setPackageName("SFIS_LABEL_FINGERPRINT_PEGA_PG");
								upbvo.setFunctionName("Query_time_step2");
								ResultVO uprvo = upbsa.doFunc(upbvo);
								Vector upresult = uprvo.getData();
								Hashtable uprow = (Hashtable)upresult.get(1);
								int endtime = Integer.parseInt(CloneArray_ChangeStr.NulltoSpace(uprow.get("BF")));
								int nowtime = Integer.parseInt(CloneArray_ChangeStr.NulltoSpace(uprow.get("AF")));
								Vector csarg = new Vector();
								csarg.add(jtfexport.getText().trim());
								csarg.add(Integer.toString(a));
								BaseServletAgent csbsa = new BaseServletAgent(uiVO);
								BaseVO csbvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,csarg);
								csbvo.setPackageName("SFIS_LABEL_FINGERPRINT_PEGA_PG");
								csbvo.setFunctionName("Change_fitlable_status");
								ResultVO csrvo = csbsa.doFunc(csbvo);
								jta.setText("");
								jta.append("OK");
								jtfexport.setText("");
							}
							if(b.equals("2")){
								Vector uparg = new Vector();
								uparg.add(jtfexport.getText().trim());
								BaseServletAgent upbsa = new BaseServletAgent(uiVO);
								BaseVO upbvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,uparg);
								upbvo.setPackageName("SFIS_LABEL_FINGERPRINT_PEGA_PG");
								upbvo.setFunctionName("Query_time_step3");
								ResultVO uprvo = upbsa.doFunc(upbvo);
								Vector upresult = uprvo.getData();
								Hashtable uprow = (Hashtable)upresult.get(1);
								int endtime = Integer.parseInt(CloneArray_ChangeStr.NulltoSpace(uprow.get("BF")));
								int nowtime = Integer.parseInt(CloneArray_ChangeStr.NulltoSpace(uprow.get("AF")));
								int h = Integer.parseInt(Integer.toString(endtime).substring(8,10));
								int d = Integer.parseInt(Integer.toString(endtime).substring(0,8));
								if(nowtime < (endtime + 100)){
									jta.setText("");
									jta.append("該貼合標籤目前所在站點 : " + CloneArray_ChangeStr.NulltoSpace(rowup.get("MISSING")) + "\n" +
									"未到24小時,過站失敗");
									return;
								}
								/*if(h + 12 > 24){
									h = h - 12;
									d = d + 1;
									String sh = Integer.toString(h);
									String sd = Integer.toString(d);
									if(sh.length() < 2){
										sh = "0" + sh;
									}
									endtime = Integer.parseInt(sd + sh);
									if(nowtime < endtime){
										jta.setText("");
										jta.append("該貼合標籤目前所在站點 : " + CloneArray_ChangeStr.NulltoSpace(rowup.get("MISSING")) + "\n" +
										"未到12小時,過站失敗");
										return;
									}
								}else if(h + 12 <= 24){
									endtime = endtime + 12;
									if(nowtime < endtime){
										jta.setText("");
										jta.append("該貼合標籤目前所在站點 : " + CloneArray_ChangeStr.NulltoSpace(rowup.get("MISSING")) + "\n" +
										"未到12小時,過站失敗");
										return;
									}
								}*/
								Vector csarg = new Vector();
								csarg.add(jtfexport.getText().trim());
								csarg.add(Integer.toString(a));
								BaseServletAgent csbsa = new BaseServletAgent(uiVO);
								BaseVO csbvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,csarg);
								csbvo.setPackageName("SFIS_LABEL_FINGERPRINT_PEGA_PG");
								csbvo.setFunctionName("Change_fitlable_status");
								ResultVO csrvo = csbsa.doFunc(csbvo);
								jta.setText("");
								jta.append("OK");
								jtfexport.setText("");
							}
							if(b.equals("3")){
								Vector uparg = new Vector();
								uparg.add(jtfexport.getText().trim());
								BaseServletAgent upbsa = new BaseServletAgent(uiVO);
								BaseVO upbvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,uparg);
								upbvo.setPackageName("SFIS_LABEL_FINGERPRINT_PEGA_PG");
								upbvo.setFunctionName("Query_time_step4");
								ResultVO uprvo = upbsa.doFunc(upbvo);
								Vector upresult = uprvo.getData();
								Hashtable uprow = (Hashtable)upresult.get(1);
								int endtime = Integer.parseInt(CloneArray_ChangeStr.NulltoSpace(uprow.get("BF")));
								int nowtime = Integer.parseInt(CloneArray_ChangeStr.NulltoSpace(uprow.get("AF")));
								Vector csarg = new Vector();
								csarg.add(jtfexport.getText().trim());
								csarg.add(Integer.toString(a));
								BaseServletAgent csbsa = new BaseServletAgent(uiVO);
								BaseVO csbvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,csarg);
								csbvo.setPackageName("SFIS_LABEL_FINGERPRINT_PEGA_PG");
								csbvo.setFunctionName("Change_fitlable_status");
								ResultVO csrvo = csbsa.doFunc(csbvo);
								jta.setText("");
								jta.append("OK");
								printsource = 4848;
								setStatus(new int[]{ToolBarItem.Print,ToolBarItem.Cancel});
								jtfLabelQTY.setEditable(true);
								jtfexport.setEditable(false);
							}
							/*Vector uparg = new Vector();
							uparg.add(jtfexport.getText().trim());
							BaseServletAgent upbsa = new BaseServletAgent(uiVO);
							BaseVO upbvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,uparg);
							upbvo.setPackageName("SFIS_LABEL_FINGERPRINT_PEGA_PG");
							upbvo.setFunctionName("Check_fitlable_exist");
							ResultVO uprvo = upbsa.doFunc(upbvo);
							Vector upresult = uprvo.getData();
							Hashtable uprow = (Hashtable)upresult.get(1);*/
						}
                    }
                    read.close();
        }else{
            System.out.println("找不到指定的文件"); 
        }
        } catch (Exception e) {
            System.out.println("讀取文件內容出錯"); 
            e.printStackTrace();
        }
      
    }
	private void funStep(){
		String filePath = "C:\\device.txt";
        readTxtFile(filePath);
	}
	private void dofitfun(){
		setMessage("");
		if(btSta == 12580){
			if(jtfexport.getText().trim().substring(15,16).equals("B")){
				setMessage("請刷入布,不是膠");
				return;
			}
		}
		try{
			Vector arg = new Vector();
			arg.add(jtfexport.getText().trim());
			BaseServletAgent bsa = new BaseServletAgent(uiVO);
			BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
			bvo.setPackageName("SFIS_LABEL_FINGERPRINT_PEGA_PG");
			bvo.setFunctionName("Check_timeflow_exist");	
			ResultVO rvo = bsa.doFunc(bvo);
		  	Vector result = rvo.getData();
			Hashtable row = (Hashtable)result.get(1);
			if(CloneArray_ChangeStr.NulltoSpace(row.get("MISSING")).equals("NO")){
				setMessage("無此分條碼");
				return;
			}
			Vector uparg = new Vector();
			uparg.add(jtfexport.getText().trim());
			BaseServletAgent upbsa = new BaseServletAgent(uiVO);
			BaseVO upbvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,uparg);
			upbvo.setPackageName("SFIS_LABEL_FINGERPRINT_PEGA_PG");
			upbvo.setFunctionName("Check_fitlable_exist");
			ResultVO uprvo = upbsa.doFunc(upbvo);
			Vector upresult = uprvo.getData();
			Hashtable uprow = (Hashtable)upresult.get(1);
			if(CloneArray_ChangeStr.NulltoSpace(uprow.get("MISSING")).equals("NG")){
				setMessage("該分條碼已刷");
				return;
			}
			if(vtabcd.size() == 0){
				vtabcd.add(jtfexport.getText().trim());
				frmo = jtfexport.getText().trim().substring(0,13);
				if(jtfexport.getText().trim().substring(15,16).equals("F")){
					jta.append("布 : " + jtfexport.getText().trim() + "\n");
					fitbc = 1;
				}
				if(jtfexport.getText().trim().substring(15,16).equals("B")){
					jta.append("膠 : " + jtfexport.getText().trim() + "\n");
					fitjc = 1;
				}
			}else{
				if(!jtfexport.getText().trim().substring(0,13).equals(frmo)){
					setMessage("該分條碼和前面不屬於同一工單");
					return;
				}
				//Hashtable ht = new Hashtable();
				for(int n = 0;n < vtabcd.size();n++){
					//ht.put(vtabcd.elementAt(n),"value");
					/*for(Iterator<String> itr = ht.keySet().iterator();itr.hasNext();){
						String key = (String)itr.next();
						if(jtfexport.getText().trim().equals(key)){
							setMessage("該分條碼已刷");
							return;
						}
					}*/
					String hava = vtabcd.elementAt(n).toString();
					//System.out.println(hava);
					if(jtfexport.getText().trim().equals(hava)){
						setMessage("該分條碼已刷");
						return;
					}
				}
				if(jtfexport.getText().trim().substring(15,16).equals("F")){
					jta.append("布 : " + jtfexport.getText().trim() + "\n");
					fitbc++;
				}
				if(jtfexport.getText().trim().substring(15,16).equals("B")){
					jta.append("膠 : " + jtfexport.getText().trim() + "\n");
					fitjc++;
				}
				vtabcd.add(jtfexport.getText().trim());
			}
			/*if((fitbc == 2 && fitjc == 1)||(fitbc == 4 && fitjc == 2)){
				jtfLabelQTY.setEditable(false);
				setStatus(new int[]{ToolBarItem.Print,ToolBarItem.Cancel});
			}
			if(!(fitbc == 2 && fitjc == 1)&&!(fitbc == 4 && fitjc == 2)){
				jtfLabelQTY.setEditable(false);
				setStatus(new int[]{ToolBarItem.Cancel});
			}*/
			if(btSta == 0 || btSta == 12807){
				if(fitbc == 2 && fitjc == 1){
					jtfLabelQTY.setEditable(false);
					print();
					if(btSta == 0){
						jta.setText("");
					}
					if(btSta == 12807){
						btSta = 12580;
						JLexport.setText("請刷入兩布");
						jta.setText("");
						jta.append("膠 : " + Bbt + "\n");
					}
					vtabcd.clear();
					fitbc = 0;
					fitjc = 0;
				}
			}else if(btSta == 12580){
				if(fitbc == 2){
					jtfLabelQTY.setEditable(false);
					print();
					btSta = 12807;
					vtabcd.clear();
					fitbc = 0;
					fitjc = 0;
					JLexport.setText("請刷入一膠兩布");
					jta.setText("");
				}
			}
			jtfexport.setText("");
		}catch(Exception e){
			e.printStackTrace();
			exUtl.ExceptionNotify(e,"Barcode_1616_table_PEGA dofitfun",this.getClass().toString(),VERSION);
		}
	}
	public void getMoTime(){
		try{
			Vector arg = new Vector();
			arg.add(jtfMO.getText().trim());
			BaseServletAgent bsa = new BaseServletAgent(uiVO);
			BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
			bvo.setPackageName("SFIS_LABEL_FINGERPRINT_PEGA_PG");
			bvo.setFunctionName("Query_MOTIME_INFO");
			ResultVO rvo = bsa.doFunc(bvo);
		  	Vector result = rvo.getData();
			Hashtable row = (Hashtable)result.get(1);
			strmt = CloneArray_ChangeStr.NulltoSpace(row.get("GMT"));
		}catch(Exception e){
			e.printStackTrace();
			exUtl.ExceptionNotify(e,"Barcode_1616_table_PEGA getMoTime",this.getClass().toString(),VERSION);
		}
	}
	public void getMoFlow(){
		try{
			Vector arg = new Vector();
			arg.add(jtfMO.getText().trim());
			arg.add(strmt.length());
			BaseServletAgent bsa = new BaseServletAgent(uiVO);
			BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
			bvo.setPackageName("SFIS_LABEL_FINGERPRINT_PEGA_PG");
			bvo.setFunctionName("Query_MOFLOW_INFO");
			ResultVO rvo = bsa.doFunc(bvo);
		  	Vector result = rvo.getData();
			Hashtable row = (Hashtable)result.get(1);
			//int a = row.get("FLM");
			//strflow = Integer.toString(a);
			strflow = CloneArray_ChangeStr.NulltoSpace(row.get("FLM"));
			if(strflow == null){
				strflow = "01";
			}
			if(strflow.equals("")){
				strflow = "01";
			}
			if(strflow.length()<2){
				strflow = "0" + strflow;
			}
		}catch(Exception e){
			e.printStackTrace();
			exUtl.ExceptionNotify(e,"Barcode_1616_table_PEGA getMoFlow",this.getClass().toString(),VERSION);
		}
	}
	public void getDelOid(){
		try{
			Vector arg = new Vector();
			BaseServletAgent bsa = new BaseServletAgent(uiVO);
			BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
			bvo.setPackageName("SFIS_LABEL_FINGERPRINT_PEGA_PG");
			bvo.setFunctionName("Query_OID_INFO");
			ResultVO rvo = bsa.doFunc(bvo);
		  	Vector result = rvo.getData();
			Hashtable row = (Hashtable)result.get(1);
			stroid = CloneArray_ChangeStr.NulltoSpace(row.get("OID"));
		}catch(Exception e){
			e.printStackTrace();
			exUtl.ExceptionNotify(e,"Barcode_1616_table_PEGA getDelOid",this.getClass().toString(),VERSION);
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
		
		if(printsource == ToolBarItem.SingleQuery){
			doPrint(jcboxPrint.isSelected(),printNum);
		}else if(printsource == 3232){
			MakeUpPr(jcboxPrint.isSelected(),printNum);
		}else if(printsource == 4848){
			ExportLabel(jcboxPrint.isSelected(),printNum);
		}else if(printsource == 8484){
			FitLabel(jcboxPrint.isSelected(),printNum);
			//System.out.println("@@@");
		}
		
		
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
	
	private void FitLabel(boolean isPrint,String printNum){
		int num=Integer.parseInt(printNum);
		String filePath = "d:/1616FitLable.pdf";
		if(btSta == 0 || btSta == 12807){
			try{
				String F1 = null;
				String F2 = null;
				String F3 = null;
				String B1 = null;
				int J16 = 0;
				Document document = new Document(new com.lowagie.text.Rectangle(210, 45), -25, 10, 0, 0);
				PdfWriter writer = PdfWriter.getInstance(document,new FileOutputStream(filePath));
				document.open();
				if(vtabcd.size() == 3){
					F1 = vtabcd.get(0).toString();
					F2 = vtabcd.get(1).toString();
					F3 = vtabcd.get(2).toString();
				}
				for(int n = 0;n < 3;n++){
					if(vtabcd.get(n).toString().substring(15,16).equals("B")){
						J16 = n;
					}
				}
				String fitlable = null;
				if(J16 == 0){
					B1 = F1;
					fitlable = frmo + B1.substring(13,18) + F2.substring(13,18) + F3.substring(13,18);
					Vector arg = new Vector();
					arg.add(B1);
					arg.add(F2);
					arg.add(F3);
					arg.add(fitlable);
					BaseServletAgent bsa = new BaseServletAgent(uiVO);
					BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
					bvo.setPackageName("SFIS_LABEL_FINGERPRINT_PEGA_PG");
					bvo.setFunctionName("Update_fitlable_exist");	
					ResultVO rvo = bsa.doFunc(bvo);
				}
				if(J16 == 1){
					B1 = F2;
					fitlable = frmo + B1.substring(13,18) + F1.substring(13,18) + F3.substring(13,18);
					Vector arg = new Vector();
					arg.add(B1);
					arg.add(F1);
					arg.add(F3);
					arg.add(fitlable);
					BaseServletAgent bsa = new BaseServletAgent(uiVO);
					BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
					bvo.setPackageName("SFIS_LABEL_FINGERPRINT_PEGA_PG");
					bvo.setFunctionName("Update_fitlable_exist");	
					ResultVO rvo = bsa.doFunc(bvo);
				}
				if(J16 == 2){
					B1 = F3;
					fitlable = frmo + B1.substring(13,18) + F1.substring(13,18) + F2.substring(13,18);
					Vector arg = new Vector();
					arg.add(B1);
					arg.add(F1);
					arg.add(F2);
					arg.add(fitlable);
					BaseServletAgent bsa = new BaseServletAgent(uiVO);
					BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
					bvo.setPackageName("SFIS_LABEL_FINGERPRINT_PEGA_PG");
					bvo.setFunctionName("Update_fitlable_exist");	
					ResultVO rvo = bsa.doFunc(bvo);
				}
				if(btSta == 12807){
					Bbt = B1;
				}
				for(int i=1;i<=num;i++){
					createPdfTable(writer,fitlable);
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
		}else if(btSta == 12580){
			try{
				Document document = new Document(new com.lowagie.text.Rectangle(210, 45), -25, 10, 0, 0);
				PdfWriter writer = PdfWriter.getInstance(document,new FileOutputStream(filePath));
				document.open();
				String fitlable = null;
				fitlable = frmo + Bbt.substring(13,18) + vtabcd.get(0).toString().substring(13,18) + vtabcd.get(1).toString().substring(13,18);
				Vector arg = new Vector();
				arg.add(Bbt);
				arg.add(vtabcd.get(0).toString());
				arg.add(vtabcd.get(1).toString());
				arg.add(fitlable);
				BaseServletAgent bsa = new BaseServletAgent(uiVO);
				BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
				bvo.setPackageName("SFIS_LABEL_FINGERPRINT_PEGA_PG");
				bvo.setFunctionName("Update_btfitlable_exist");	
				ResultVO rvo = bsa.doFunc(bvo);
				for(int i=1;i<=num;i++){
					createPdfTable(writer,fitlable);
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
		}
		System.out.println("export ok");
		frmo = null;
		setStatus(new int[]{ToolBarItem.Cancel,ToolBarItem.Exit});
		if(isPrint){
			if(!printPdf(filePath)){
				setMessage("打印出錯！");
			}
		}else{
			setMessage("資料已匯出到   d:/1616FitLable.pdf ");
		}
	}
	
	private void doPrint(boolean isPrint,String printNum){
		int num=Integer.parseInt(printNum);
		//num = 5;
		String filePath = "d:/1616BeginLable.pdf";
		try {
			Document document = new Document(new com.lowagie.text.Rectangle(145, 90), -25, 10, 0, 0);
			PdfWriter writer = PdfWriter.getInstance(document,new FileOutputStream(filePath));
			document.open();
			if(!jtfBuShua.getText().equals("")){
				Vector arg = new Vector();
				arg.add(jtfMO.getText().trim());
				//arg.add(jtfBuShua.getText().trim());
				BaseServletAgent bsa = new BaseServletAgent(uiVO);
				BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
				bvo.setPackageName("SFIS_LABEL_FINGERPRINT_PEGA_PG");
				bvo.setFunctionName("toGetBu_Flow");
				ResultVO rvo = bsa.doFunc(bvo);
				Vector result = rvo.getData();
				Hashtable row = (Hashtable)result.get(1);
				String bflow = CloneArray_ChangeStr.NulltoSpace(row.get("FLM"));
				if(bflow == null){
					bflow = "01";
				}
				if(bflow.equals("")){
					bflow = "01";
				}
				if(bflow.length()<2){
					bflow = "0" + bflow;
				}
				for(int i=1;i<=num;i++){
					Vector uparg = new Vector();
					uparg.add(jtfMO.getText().trim());
					uparg.add(bflow);
					uparg.add(jtfBuShua.getText().trim());
					uparg.add(strbjpa);
					uparg.add(strbjkj);
					BaseServletAgent upbsa = new BaseServletAgent(uiVO);
					BaseVO upbvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,uparg);
					upbvo.setPackageName("SFIS_LABEL_FINGERPRINT_PEGA_PG");
					upbvo.setFunctionName("toGetBu_Lable");
					ResultVO uprvo = upbsa.doFunc(upbvo);
					Vector upresult = uprvo.getData();
					Hashtable uprow = (Hashtable)upresult.get(1);
					createdPdfTablep(writer,CloneArray_ChangeStr.NulltoSpace(uprow.get("FLM")),jtfBuShua.getText().trim());
					document.add(pdftable);
				}
			}
			if(!jtfJaShua.getText().equals("")){
				Vector arg = new Vector();
				arg.add(jtfMO.getText().trim());
				//arg.add(jtfJaShua.getText().trim());
				BaseServletAgent bsa = new BaseServletAgent(uiVO);
				BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
				bvo.setPackageName("SFIS_LABEL_FINGERPRINT_PEGA_PG");
				bvo.setFunctionName("toGetJa_Flow");
				ResultVO rvo = bsa.doFunc(bvo);
				Vector result = rvo.getData();
				Hashtable row = (Hashtable)result.get(1);
				String jflow = CloneArray_ChangeStr.NulltoSpace(row.get("FLM"));
				if(jflow == null){
					jflow = "01";
				}
				if(jflow.equals("")){
					jflow = "01";
				}
				if(jflow.length()<2){
					jflow = "0" + jflow;
				}
				for(int i=1;i<=num;i++){
					Vector uparg = new Vector();
					uparg.add(jtfMO.getText().trim());
					uparg.add(jflow);
					uparg.add(jtfJaShua.getText().trim());
					uparg.add(strbjpa);
					uparg.add(strbjkj);
					BaseServletAgent upbsa = new BaseServletAgent(uiVO);
					BaseVO upbvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,uparg);
					upbvo.setPackageName("SFIS_LABEL_FINGERPRINT_PEGA_PG");
					upbvo.setFunctionName("toGetJa_Lable");
					ResultVO uprvo = upbsa.doFunc(upbvo);
					Vector upresult = uprvo.getData();
					Hashtable uprow = (Hashtable)upresult.get(1);
					createdPdfTablep(writer,CloneArray_ChangeStr.NulltoSpace(uprow.get("FLM")),jtfJaShua.getText().trim());
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
		jtfBuShua.setEditable(false);
		jtfJaShua.setEditable(false);
		jtfLabelQTY.setEditable(false);
		setStatus(new int[]{ToolBarItem.Cancel,ToolBarItem.Exit});
		if(isPrint){
			if(!printPdf(filePath)){
				setMessage("打印出錯！");
			}
		}else{
			setMessage("資料已匯出到   d:/1616BeginLable.pdf ");
		}
	}
	private void MakeUpPr(boolean isPrint,String printNum){
		int num=Integer.parseInt(printNum);
		String filePath = "d:/1616MakeUpLable.pdf";
		try {
			Document document = new Document(new com.lowagie.text.Rectangle(210, 35), -25, 10, 0, 0);
			PdfWriter writer = PdfWriter.getInstance(document,new FileOutputStream(filePath));
			document.open();
			for(int i=1;i<=num;i++){
				Vector arg = new Vector();
				arg.add(jtfMakeUp.getText().trim());
				BaseServletAgent bsa = new BaseServletAgent(uiVO);
				BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
				bvo.setPackageName("SFIS_LABEL_FINGERPRINT_PEGA_PG");
				bvo.setFunctionName("Update_table_Mk");
				ResultVO rvo = bsa.doFunc(bvo);
				createPdfTable(writer,jtfMakeUp.getText().trim());
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
		jtfMakeUp.setText("");
		jtfLabelQTY.setText("1");
		jtfLabelQTY.setEditable(false);
		setStatus(new int[]{ToolBarItem.Cancel,ToolBarItem.Exit});
		if(isPrint){
			if(!printPdf(filePath)){
				setMessage("打印出錯！");
			}
		}else{
			setMessage("資料已匯出到   d:/1616MakeUpLable.pdf ");
		}
	}
	private void ExportLabel(boolean isPrint,String printNum){
		String printStr = "";
		int num=Integer.parseInt(printNum);
		String filePath = "d:/1616ExportLable.pdf";
		try {
			Document document = new Document(new com.lowagie.text.Rectangle(210, 70), -25, 10, 0, 0);
			PdfWriter writer = PdfWriter.getInstance(document,new FileOutputStream(filePath));
			document.open();
			for(int i=1;i<=num;i++){
				/*Vector arg = new Vector();
				arg.add(jtfexport.getText().trim());
				arg.add(PDEndTime.getText());
				BaseServletAgent bsa = new BaseServletAgent(uiVO);
				BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
				bvo.setPackageName("SFIS_LABEL_FINGERPRINT_PEGA_PG");
				bvo.setFunctionName("INSERT_Finished_product");
				ResultVO rvo = bsa.doFunc(bvo);
			  	Vector result = rvo.getData();
				Hashtable row = (Hashtable)result.get(1);
				printStr = CloneArray_ChangeStr.NulltoSpace(row.get("VT"));*/
				createPdfTable(writer,jtfexport.getText().trim());
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
		jtfexport.setText("");
		jtfLabelQTY.setText("1");
		jtfLabelQTY.setEditable(false);
		printsource = 8998;
		setStatus(new int[]{ToolBarItem.Cancel,ToolBarItem.Exit});
		if(isPrint){
			if(!printPdf(filePath)){
				setMessage("打印出錯！");
			}
		}else{
			setMessage("資料已匯出到   d:/1616ExportLable.pdf ");
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
	public Hashtable getDBDATA(String sql,String ds,UI_InitVO uiVO)throws Exception{
		String reString = "";
		Vector arg = new Vector();
		arg.add(sql);
	    BaseServletAgent bsa1 = new BaseServletAgent(uiVO);
		BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallSQLCmd2,arg);
		bvo.setDataSourceType(ds);
		ResultVO rvo = bsa1.doFunc(bvo);
		Vector result = rvo.getData();
		Hashtable ht = null;
		if(result.size()>1){
			for(int x = 1;x<=(result.size()-1);x++){
				ht = (Hashtable)result.elementAt(x);
				yield = ht.get("SFB08").toString();
				count = ht.get("SFA05").toString();
				//sum = sum + Integer.parseInt(count);
				Materialquantity.put(ht.get("IMA02"),ht.get("SFA05"));
			}
		}
		return ht;
	}
	public Hashtable getDBCOPYDATA(String sql,String ds,UI_InitVO uiVO)throws Exception{
		String reString = "";
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
		String ProName = null;
		if(printsource == 4848){
			ProNumber = "";
			try{
				/*Vector arggt = new Vector();
				arggt.add(jtfexport.getText().trim());
				BaseServletAgent bsagt = new BaseServletAgent(uiVO);
				BaseVO bvogt = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arggt);
				bvogt.setPackageName("SFIS_LABEL_FINGERPRINT_PEGA_PG");
				bvogt.setFunctionName("Update_Product_JDMlable");
				ResultVO rvogt = bsagt.doFunc(bvogt);
				Vector resultgt = rvogt.getData();
				Hashtable rowgt = (Hashtable)resultgt.get(1);
				ProNumber = CloneArray_ChangeStr.NulltoSpace(rowgt.get("MO"));*/
				String sql = "select i.ima01 from sfb_file b,ima_file i where b.sfb05 = i.ima01 and b.sfb01 = '" + jtfexport.getText().trim().substring(0,13) + "'";
				Hashtable ht = getDBCOPYDATA(sql,DataSourceType._TIPTOP_KAIJIA_TOPPROD,uiVO);
				ProNumber = ht.get("IMA01").toString();
				Vector arg = new Vector();
				arg.add(ProNumber);
				BaseServletAgent bsa = new BaseServletAgent(uiVO);
				BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
				bvo.setPackageName("SFIS_LABEL_FINGERPRINT_PEGA_PG");
				bvo.setFunctionName("Change_Kpn_JDM");	
				ResultVO rvo = bsa.doFunc(bvo);
				Vector result = rvo.getData();
				if(result.size()<2){
					setMessage("對照表內無此客戶料號");
					return;
				}
				Hashtable row = (Hashtable)result.get(1);
				ProNumber = CloneArray_ChangeStr.NulltoSpace(row.get("PEGANO"));
				Vector uparg = new Vector();
				uparg.add(jtfexport.getText().trim());
				uparg.add(ProNumber);
				System.out.println(ProNumber);
				BaseServletAgent upbsa = new BaseServletAgent(uiVO);
				BaseVO upbvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,uparg);
				upbvo.setPackageName("SFIS_LABEL_FINGERPRINT_PEGA_PG");
				upbvo.setFunctionName("Group_jdmagain_Lable");
				ResultVO uprvo = upbsa.doFunc(upbvo);
				Vector upresult = uprvo.getData();
				Hashtable uprow = (Hashtable)upresult.get(1);
				ProNumber = CloneArray_ChangeStr.NulltoSpace(uprow.get("JPML"));
				Vector argup = new Vector();
				argup.add(jtfexport.getText().trim());
				argup.add(ProNumber);
				System.out.println(ProNumber);
				BaseServletAgent bsaup = new BaseServletAgent(uiVO);
				BaseVO bvoup = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,argup);
				bvoup.setPackageName("SFIS_LABEL_FINGERPRINT_PEGA_PG");
				bvoup.setFunctionName("INSERT_Product_JDMlable");
				ResultVO rvoup = bsaup.doFunc(bvoup);
			}catch(Exception e){
				e.printStackTrace();
				exUtl.ExceptionNotify(e,"Barcode 1616 GetProNumber",this.getClass().toString(),VERSION);
			}
		}else if(printsource == 8484){
			try{
				String sql = "select i.ima02 from sfb_file b,ima_file i where b.sfb05 = i.ima01 and b.sfb01 = '" + frmo + "'";
				Hashtable ht = getDBCOPYDATA(sql,DataSourceType._TIPTOP_KAIJIA_TOPPROD,uiVO);
				ProName = ht.get("IMA02").toString();
			}catch(Exception e){
				e.printStackTrace();
				exUtl.ExceptionNotify(e,"Barcode 1616 GetProName",this.getClass().toString(),VERSION);
			}
		}
		initFont();
		pdftable = new PdfPTable(2);
		pdftable.setTotalWidth(195);
		int[] a = { 30, 160 };
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
		if(printsource == 4848){
			pdftable.addCell(createCell("12345",f14,0,0));
			pdftable.addCell(createBarCode(writer, ProNumber, a[1]));
			pdftable.addCell(createCell("12345",f14,0,0));
			pdftable.addCell(createCell(" ",f14,0,0));
		}
		pdftable.addCell(pega);
		pdftable.addCell(createBarCode(writer, mmo, a[1]));
		if(printsource == 8484){
			pdftable.addCell(createCell("12345",f14,0,0));
			pdftable.addCell(createCell(ProName,f14,0,0));
		}
	}
	public void createdPdfTablep(PdfWriter writer,String mmo,String lot) {
		String ProName = "";
		try{
			String sql = "select i.ima02 from sfb_file b,ima_file i where b.sfb05 = i.ima01 and b.sfb01 = '" + jtfMO.getText().trim() + "'";
			Hashtable ht = getDBCOPYDATA(sql,DataSourceType._TIPTOP_KAIJIA_TOPPROD,uiVO);
			ProName = ht.get("IMA02").toString();
		}catch(Exception e){
			e.printStackTrace();
			exUtl.ExceptionNotify(e,"Barcode 1616 GetProName",this.getClass().toString(),VERSION);
		}
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
		pdftable.addCell(createCell("12345",f14,0,0));
		pdftable.addCell(createCell(" ",f14,0,0));
		pdftable.addCell(createCell("12345",f14,0,0));
		pdftable.addCell(createBarCode(writer, lot, a[1]));
		pdftable.addCell(createCell("12345",f14,0,0));
		pdftable.addCell(createCell(ProName,f14,0,0));
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
