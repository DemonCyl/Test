/**
 * Copyright (c) 2008 Pegatron Inc. All Rights Reserved.
 * $Id: Barcode_1616_Query_PEGA.java,v 1.2 2017/03/25 05:34:16 Jieyu_Fu Exp $
 */
package mes_sfis.client.ui.barcode;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Constructor;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;
import java.awt.event.*;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
//import javax.swing.JButton;

import com.klg.jclass.cell.editors.JCComboBoxCellEditor;
import com.klg.jclass.table.CellStyleModel;
import com.klg.jclass.table.JCCellPosition;
import com.klg.jclass.table.JCCellStyle;
import com.klg.jclass.table.JCEditCellEvent;
import com.klg.jclass.table.JCEditCellListener;
import com.klg.jclass.table.JCTableEnum;
import com.klg.jclass.table.data.JCEditableVectorDataSource;
import javax.swing.JLabel;
import base.client.enums.ToolBarItem;
import base.client.ui.BasePanel;
import base.client.util.BaseServletAgent;
import base.client.util.CallSQL;
import base.client.util.CloneArray_ChangeStr;
import base.client.util.StringUtil;
import base.client.util.component.Dept_Dialog;
import base.client.util.component.PEmployee_Multiple_Choice;
import base.client.util.component.PJButton;
import base.client.util.component.PExportTable;
import base.client.util.TableUtil;
import base.vo.BaseVO;
import base.vo.ResultVO;
import base.vo.UI_InitVO;
import javax.swing.JTextField;
import base.client.enums.FieldType;
import base.client.enums.PegaBaseModule;
import base.enums.CommandName;
import base.client.util.component.PMIDComboBox;
import javax.swing.JFormattedTextField;
import base.client.util.component.PDateTimeTextField;
 /**
 * The <code>xxx</code> class.
 *
 * @version     $Name: 1.3 $, $Id: Barcode_1616_Query_PEGA.java,v 1.2 2017/03/25 05:34:16 Jieyu_Fu Exp $
 * @author      <a href="mailto:william_tsai@asus.com.tw">William Tsai</a>
 * @history      [>: General, +: New, #: Modification, -: Remove, ~: Bug]
 *          $Log: Barcode_1616_Query_PEGA.java,v $
 *          Revision 1.2  2017/03/25 05:34:16  Jieyu_Fu
 *          no message
 *
 *          Revision 1.1  2017/02/07 06:55:01  Jieyu_Fu
 *          no message
 *
 *          Revision 1.2  2016/12/23 09:02:43  Jieyu_Fu
 *          no message
 *
 *          Revision 1.1  2016/12/23 08:37:33  Jieyu_Fu
 *          no message
 *
 */
public class Barcode_1616_Query_PEGA extends BasePanel{
    public static final String VERSION    = "$Name:  $, $Id: Barcode_1616_Query_PEGA.java,v 1.2 2017/03/25 05:34:16 Jieyu_Fu Exp $";
    public static final String COPYRIGHT  = "Copyright (c) 2008 PEGATRON Inc. All Rights Reserved.";

	private PExportTable				table;
	private PExportTable				elbat;
	private JCEditableVectorDataSource	ds					= new JCEditableVectorDataSource();
	private JCEditableVectorDataSource	sd					= new JCEditableVectorDataSource();
	private Vector						data				= new Vector();
	private Vector						column				= new Vector();
	private Vector						atad				= new Vector();
	private Vector						nmuloc				= new Vector();
	private CellStyleModel				editStyle			= null;
	private CellStyleModel				editStyle_r		    = null;//紅色底
	private CellStyleModel				unEditStyle			= null;
	private CellStyleModel				comboxStyle_day		= null;
	private CellStyleModel				comboxStyle_week	= null;
	private CellStyleModel				comboxStyle_hour	= null;
	private CellStyleModel				comboxStyle_minute	= null;
	private CellStyleModel				comboxStyle_status	= null;
	private CellStyleModel				comboxStyle_type	= null;
	private CellStyleModel				updateStyle	        = null;

	
	private Dimension di = this.getMainPanel().getToolkit().getScreenSize();

	private Timer                       timer;	
	private HashSet						hsDelOid			= new HashSet();
	private CallSQL						callSql;
	private Color						editColor			= Color.PINK;
	private JLabel JLMaxFlow;
	private PMIDComboBox jcb_dis_class;
	private PDateTimeTextField PDStartTime;
	private PDateTimeTextField PDEndTime;
	JTextField jtfMO;
	JTextField jtfFitLab;
	JTextField jtfProLab;
	JTextField jtfJdmLab;
	int suut = 0;
	KeyListener _keyListener_SVT_QueryFit;
	KeyListener _keyListener_SVT_QueryPro;
	KeyListener _keyListener_SVT_QueryJdm;
	Hashtable<String,Hashtable> htUpdateData=new Hashtable<String,Hashtable>();
	
	private Barcode_1616_Query_PEGA				self;
	private int							lastStatus			= -1;

	public Barcode_1616_Query_PEGA(UI_InitVO uiVO) {
		super(uiVO);
		init();
	}
	
	
	private void init() {
		self = this;
		self.setUILayout(null);
		table				= new PExportTable(uiVO,"table", null, "Barcode_1616_Query_PEGA_table");
		column.add("分條標籤");//"刪除選擇"
		column.add("布標籤");
		column.add("膠標籤");
		column.add("分條標籤打印時間");
		column.add("貼合標籤1");
		column.add("貼合標籤2");
		column.add("貼合標籤打印時間");
		column.add("客戶料號");
		column.add("鎧嘉料號");
		TableUtil.init_table(table, ds, data, column);
		table.addEditCellListener(editListener);
		table.addMouseListener(mouselistener);
		table.setBounds(50,125,800,300);
		add(table);
		elbat				= new PExportTable(uiVO,"elbat", null, "Barcode_1616_Query_PEGA_elbat");
		nmuloc.add("貼合標籤");//"刪除選擇"
		nmuloc.add("JDM標籤");
		nmuloc.add("目前站點");
		nmuloc.add("Rough Cutting過站時間");
		nmuloc.add("Rough Cutting過站人");
		nmuloc.add("開始靜置過站時間");
		nmuloc.add("開始靜置過站人");
		nmuloc.add("Final Cutting過站時間");
		nmuloc.add("Final Cutting過站人");
		nmuloc.add("包裝過站時間");
		nmuloc.add("包裝過站人");
		TableUtil.init_table(elbat, sd, atad, nmuloc);
		elbat.addEditCellListener(editListener);
		elbat.addMouseListener(mouselistener);
		elbat.setBounds(50,450,800,600);
		add(elbat);
		JLabel jLabel_MO = new JLabel("請輸入工單號 : ",JLabel.RIGHT);
		jLabel_MO.setBounds(new Rectangle(10,45,100,25));
		add(jLabel_MO,null);
		
		jtfMO = new JTextField();
		jtfMO.setBounds(new Rectangle(120,45,250,25));
		add(jtfMO,null);

		JLabel JLFitLab = new JLabel("請刷貼合標籤 : ",JLabel.RIGHT);
		JLFitLab.setBounds(new Rectangle(380,45,100,25));
		add(JLFitLab,null);
		
		jtfFitLab = new JTextField();
		jtfFitLab.setBounds(new Rectangle(490,45,250,25));
		add(jtfFitLab,null);
		
		_keyListener_SVT_QueryFit = new KeyListener() {
			public void keyPressed(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					FitSVT_Query();
				}
			}
			public void keyTyped(KeyEvent e) {}
		};
		jtfFitLab.addKeyListener(_keyListener_SVT_QueryFit);
		
		JLabel JLProLab = new JLabel("請刷分條標籤 : ",JLabel.RIGHT);
		JLProLab.setBounds(new Rectangle(10,95,100,25));
		add(JLProLab,null);
		
		jtfProLab = new JTextField();
		jtfProLab.setBounds(new Rectangle(120,95,250,25));
		add(jtfProLab,null);
		
		_keyListener_SVT_QueryPro = new KeyListener() {
			public void keyPressed(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					ProSVT_Query();
				}
			}
			public void keyTyped(KeyEvent e) {}
		};
		jtfProLab.addKeyListener(_keyListener_SVT_QueryPro);
		
		JLabel JLJdmLab = new JLabel("請刷JDM標籤 : ",JLabel.RIGHT);
		JLJdmLab.setBounds(new Rectangle(380,95,100,25));
		add(JLJdmLab,null);
		
		jtfJdmLab = new JTextField();
		jtfJdmLab.setBounds(new Rectangle(490,95,250,25));
		add(jtfJdmLab,null);
		
		_keyListener_SVT_QueryJdm = new KeyListener() {
			public void keyPressed(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					JdmSVT_Query();
				}
			}
			public void keyTyped(KeyEvent e) {}
		};
		jtfJdmLab.addKeyListener(_keyListener_SVT_QueryJdm);
		
		callSql = new CallSQL(uiVO, this.getClass().toString());
		/*JLMaxFlow = new JLabel("");
		JLMaxFlow.setBounds(80,20,130,25);
        add(JLMaxFlow);*/
		setStatus(new int[] { ToolBarItem.SingleQuery,ToolBarItem.Cancel,ToolBarItem.Exit });  //拿掉單筆查詢按鈕
		
	}
	private void FitSVT_Query(){
		if(!(suut == 100)){
			data.clear();
			atad.clear();
		}
		try{
			Vector arg = new Vector();
			arg.add(jtfFitLab.getText().trim());
			BaseServletAgent bsa = new BaseServletAgent(uiVO);
			BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
			bvo.setPackageName("SFIS_QUERY_FINGERPRINT_PEGA_PG");
			bvo.setFunctionName("Query_Fit_Lable");
			ResultVO rvo = bsa.doFunc(bvo);
			Vector result = rvo.getData();
			Hashtable ht = null;
			if(result.size()<2){
				TableUtil.setTableData(table, ds, data);
			}else if(result.size()>1){
				for(int x = 1;x<=(result.size()-1);x++){
				    Vector row = new Vector();
					ht = (Hashtable)result.elementAt(x);
					row.add(CloneArray_ChangeStr.NulltoSpace(ht.get("TIMEFLOW")));
					row.add(CloneArray_ChangeStr.NulltoSpace(ht.get("BULABLENUM")));
					row.add(CloneArray_ChangeStr.NulltoSpace(ht.get("JALABLENUM")));
					row.add(CloneArray_ChangeStr.NulltoSpace(ht.get("STARTDATE")));
					row.add(CloneArray_ChangeStr.NulltoSpace(ht.get("FITLABLE")));
					row.add(CloneArray_ChangeStr.NulltoSpace(ht.get("BTFIT")));
					row.add(CloneArray_ChangeStr.NulltoSpace(ht.get("ENDDATE")));
					row.add(CloneArray_ChangeStr.NulltoSpace(ht.get("PEGANO")));
					row.add(CloneArray_ChangeStr.NulltoSpace(ht.get("KJNUM")));
					data.add(row);
				}
				TableUtil.setTableData(table, ds, data);
			}
			Vector uparg = new Vector();
			uparg.add(jtfFitLab.getText().trim());
			BaseServletAgent upbsa = new BaseServletAgent(uiVO);
			BaseVO upbvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,uparg);
			upbvo.setPackageName("SFIS_QUERY_FINGERPRINT_PEGA_PG");
			upbvo.setFunctionName("Query_Fit_Lable_Two");
			ResultVO uprvo = upbsa.doFunc(upbvo);
			Vector upresult = uprvo.getData();
			Hashtable hy = null;
			if(upresult.size()<2){
				TableUtil.setTableData(elbat, sd, atad);
			}else if(upresult.size()>1){
				for(int x = 1;x<=(upresult.size()-1);x++){
					Vector row = new Vector();
					hy = (Hashtable)upresult.elementAt(x);
					row.add(CloneArray_ChangeStr.NulltoSpace(hy.get("FITLABLE")));
					row.add(CloneArray_ChangeStr.NulltoSpace(hy.get("JDM")));
					row.add(CloneArray_ChangeStr.NulltoSpace(hy.get("NAME")));
					row.add(CloneArray_ChangeStr.NulltoSpace(hy.get("SDATE1")));
					row.add(CloneArray_ChangeStr.NulltoSpace(hy.get("OPNUM1")));
					row.add(CloneArray_ChangeStr.NulltoSpace(hy.get("SDATE2")));
					row.add(CloneArray_ChangeStr.NulltoSpace(hy.get("OPNUM2")));
					row.add(CloneArray_ChangeStr.NulltoSpace(hy.get("SDATE3")));
					row.add(CloneArray_ChangeStr.NulltoSpace(hy.get("OPNUM3")));
					row.add(CloneArray_ChangeStr.NulltoSpace(hy.get("SDATE4")));
					row.add(CloneArray_ChangeStr.NulltoSpace(hy.get("OPNUM4")));
					atad.add(row);
				}
				TableUtil.setTableData(elbat, sd, atad);
			}
			jtfFitLab.setText("");
		}catch(Exception e){
			e.printStackTrace();
			exUtl.ExceptionNotify(e,"Barcode_1616_Query_PEGA FitSVT_Query ",this.getClass().toString(),VERSION);
		}
	}
	private void JdmSVT_Query(){
		data.clear();
		atad.clear();
		try{
			Vector arg = new Vector();
			arg.add(jtfJdmLab.getText().trim());
			BaseServletAgent bsa = new BaseServletAgent(uiVO);
			BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
			bvo.setPackageName("SFIS_QUERY_FINGERPRINT_PEGA_PG");
			bvo.setFunctionName("Query_JDM_Fit");
			ResultVO rvo = bsa.doFunc(bvo);
			Vector result = rvo.getData();
			Hashtable ht = null;
			String afit = null;
			if(result.size() > 1){
				for(int x = 1;x<=(result.size()-1);x++){
					ht = (Hashtable)result.elementAt(x);
					afit = CloneArray_ChangeStr.NulltoSpace(ht.get("PRODUCTLABEL"));
				}
				jtfFitLab.setText(afit);
				FitSVT_Query();
			}else{
				TableUtil.setTableData(elbat, sd, atad);
				TableUtil.setTableData(table, ds, data);
			}
			jtfJdmLab.setText("");
		}catch(Exception e){
			e.printStackTrace();
			exUtl.ExceptionNotify(e,"Barcode_1616_Query_PEGA JdmSVT_Query ",this.getClass().toString(),VERSION);
		}
	}
	
	private void ProSVT_Query(){
		data.clear();
		atad.clear();
		try{
			//String strtimeflow = "";
			Vector arg = new Vector();
			arg.add(jtfProLab.getText().trim());
			BaseServletAgent bsa = new BaseServletAgent(uiVO);
			BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
			bvo.setPackageName("SFIS_QUERY_FINGERPRINT_PEGA_PG");
			bvo.setFunctionName("Query_Pro_Lable");
			ResultVO rvo = bsa.doFunc(bvo);
			Vector result = rvo.getData();
			Hashtable ht = null;
			String afit = null;
			String bfit = null;
			System.out.println("##########");
			if(result.size() > 1){
				for(int x = 1;x<=(result.size()-1);x++){
					ht = (Hashtable)result.elementAt(x);
					afit = CloneArray_ChangeStr.NulltoSpace(ht.get("FITLABLE"));
					bfit = CloneArray_ChangeStr.NulltoSpace(ht.get("BTFIT"));
				}
				//System.out.println(afit + "\n" + bfit);
				if(afit.equals("")&&bfit.equals("")){
					Vector uparg = new Vector();
					uparg.add(jtfProLab.getText().trim());
					BaseServletAgent upbsa = new BaseServletAgent(uiVO);
					BaseVO upbvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,uparg);
					upbvo.setPackageName("SFIS_QUERY_FINGERPRINT_PEGA_PG");
					upbvo.setFunctionName("Query_Pro_Lable_Two");
					ResultVO uprvo = upbsa.doFunc(upbvo);
					Vector upresult = uprvo.getData();
					Hashtable hy = null;
					if(upresult.size()<2){
						TableUtil.setTableData(table, ds, data);
					}else if(upresult.size()>1){
						for(int x = 1;x<=(upresult.size()-1);x++){
							Vector row = new Vector();
							hy = (Hashtable)upresult.elementAt(x);
							row.add(CloneArray_ChangeStr.NulltoSpace(hy.get("TIMEFLOW")));
							row.add(CloneArray_ChangeStr.NulltoSpace(hy.get("BULABLENUM")));
							row.add(CloneArray_ChangeStr.NulltoSpace(hy.get("JALABLENUM")));
							row.add(CloneArray_ChangeStr.NulltoSpace(hy.get("STARTDATE")));
							row.add(CloneArray_ChangeStr.NulltoSpace(hy.get("FITLABLE")));
							row.add(CloneArray_ChangeStr.NulltoSpace(hy.get("BTFIT")));
							row.add(CloneArray_ChangeStr.NulltoSpace(hy.get("ENDDATE")));
							row.add(CloneArray_ChangeStr.NulltoSpace(hy.get("PEGANO")));
							row.add(CloneArray_ChangeStr.NulltoSpace(hy.get("KJNUM")));
							data.add(row);
						}
						TableUtil.setTableData(table, ds, data);
					}
					Vector row = new Vector();
					row.add(null);
					row.add(null);
					row.add(null);
					row.add(null);
					row.add(null);
					row.add(null);
					row.add(null);
					row.add(null);
					row.add(null);
					row.add(null);
					row.add(null);
					atad.add(row);
					TableUtil.setTableData(elbat, sd, atad);
				}else{
					jtfFitLab.setText(afit);
					FitSVT_Query();
					suut = 100;
					jtfFitLab.setText(bfit);
					FitSVT_Query();
					jtfFitLab.setText("");
					suut = 0;
				}
			}/*else{
				System.out.println("123");
				Vector uparg = new Vector();
				uparg.add(jtfProLab.getText().trim());
				BaseServletAgent upbsa = new BaseServletAgent(uiVO);
				BaseVO upbvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,uparg);
				upbvo.setPackageName("SFIS_QUERY_FINGERPRINT_PEGA_PG");
				upbvo.setFunctionName("Query_Pro_Lable_Two");
				ResultVO uprvo = upbsa.doFunc(upbvo);
				Vector upresult = uprvo.getData();
				Hashtable hy = null;
				if(upresult.size()<2){
					TableUtil.setTableData(table, ds, data);
				}else if(upresult.size()>1){
					for(int x = 1;x<=(upresult.size()-1);x++){
						Vector row = new Vector();
						hy = (Hashtable)upresult.elementAt(x);
						row.add(CloneArray_ChangeStr.NulltoSpace(hy.get("TIMEFLOW")));
						row.add(CloneArray_ChangeStr.NulltoSpace(hy.get("BULABLENUM")));
						row.add(CloneArray_ChangeStr.NulltoSpace(hy.get("JALABLENUM")));
						row.add(CloneArray_ChangeStr.NulltoSpace(hy.get("STARTDATE")));
						row.add(CloneArray_ChangeStr.NulltoSpace(hy.get("FITLABLE")));
						row.add(CloneArray_ChangeStr.NulltoSpace(hy.get("BTFIT")));
						row.add(CloneArray_ChangeStr.NulltoSpace(hy.get("ENDDATE")));
						row.add(CloneArray_ChangeStr.NulltoSpace(hy.get("PEGANO")));
						row.add(CloneArray_ChangeStr.NulltoSpace(hy.get("KJNUM")));
						data.add(row);
					}
					TableUtil.setTableData(table, ds, data);
				}
			}*/
			/*if(result.size()<2){
				TableUtil.setTableData(elbat, sd, atad);
			}else if(result.size()>1){
				for(int x = 1;x<=(result.size()-1);x++){
				    Vector row = new Vector();
					ht = (Hashtable)result.elementAt(x);
					row.add(CloneArray_ChangeStr.NulltoSpace(ht.get("TIMEFLOW")));
					row.add(CloneArray_ChangeStr.NulltoSpace(ht.get("PRODUCTLABEL")));
					row.add(CloneArray_ChangeStr.NulltoSpace(ht.get("PRINTTIME")));
					atad.add(row);
					strtimeflow = CloneArray_ChangeStr.NulltoSpace(ht.get("TIMEFLOW"));
				}
				TableUtil.setTableData(elbat, sd, atad);
			}*/
			/*Vector uparg = new Vector();
			uparg.add(strtimeflow);
			BaseServletAgent upbsa = new BaseServletAgent(uiVO);
			BaseVO upbvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,uparg);
			upbvo.setPackageName("SFIS_QUERY_FINGERPRINT_PEGA_PG");
			upbvo.setFunctionName("Query_Pro_Lable_Two");
			ResultVO uprvo = upbsa.doFunc(upbvo);
			Vector upresult = uprvo.getData();
			Hashtable hy = null;
			if(upresult.size()<2){
				TableUtil.setTableData(table, ds, data);
			}else if(upresult.size()>1){
				for(int x = 1;x<=(upresult.size()-1);x++){
					Vector row = new Vector();
					hy = (Hashtable)upresult.elementAt(x);
					row.add(CloneArray_ChangeStr.NulltoSpace(hy.get("TIMEFLOW")));
					row.add(CloneArray_ChangeStr.NulltoSpace(hy.get("BULABLENUM")));
					row.add(CloneArray_ChangeStr.NulltoSpace(hy.get("STARTDATE")));
					row.add(CloneArray_ChangeStr.NulltoSpace(hy.get("JALABLENUM")));
					row.add(CloneArray_ChangeStr.NulltoSpace(hy.get("ENDDATE")));
					data.add(row);
				}
				TableUtil.setTableData(table, ds, data);
			}*/
			jtfProLab.setText("");
		}catch(Exception e){
			e.printStackTrace();
			exUtl.ExceptionNotify(e,"Barcode_1616_Query_PEGA ProSVT_Query ",this.getClass().toString(),VERSION);
		}
	}
	ActionListener		action			= new ActionListener() {
											public void actionPerformed(ActionEvent e) {
										
											
											}
										};

	MouseListener		mouselistener	= new MouseAdapter() {

											public void mouseClicked(MouseEvent e) {
												// TODO Auto-generated method stub
												
											}
										};

    
	JCEditCellListener	editListener	= new JCEditCellListener() {

											public void afterEditCell(JCEditCellEvent e) {
												// TODO Auto-generated method stub
												
											}

											public void beforeEditCell(JCEditCellEvent arg0) {
												// TODO Auto-generated method stub

											}

											public void editCell(JCEditCellEvent arg0) {
												// TODO Auto-generated method stub

											}

										};

	@Override
	public void cancel() {
		//JLMaxFlow.setText("");
		suut = 0;
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
		hsDelOid.clear();
		setStatus(new int[] { ToolBarItem.Save, ToolBarItem.Cancel, ToolBarItem.Exit });
	}

	@Override
	public void multiQuery() {}

	@Override
	public void print() {}

	@Override
	public void refresh() {}

	@Override
	public void save() {
		
	}


	@Override
	public void singleQuery() {
		data.clear();
		atad.clear();
		try{
			/*Vector arg = new Vector();
			arg.add(jtfMO.getText().trim());
			BaseServletAgent bsa = new BaseServletAgent(uiVO);
			BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
			bvo.setPackageName("SFIS_QUERY_FINGERPRINT_PEGA_PG");
			bvo.setFunctionName("Query_Max_Mo");	
			ResultVO rvo = bsa.doFunc(bvo);
		  	Vector result = rvo.getData();
			if(result.size()>1){
				Hashtable row = (Hashtable)result.get(1);
				JLMaxFlow.setText(CloneArray_ChangeStr.NulltoSpace(row.get("MA")));
			}else if(result.size()<2){
				JLMaxFlow.setText("尚無打印信息");
			}*/
			Vector xarg = new Vector();
			xarg.add(jtfMO.getText().trim());
			BaseServletAgent xbsa = new BaseServletAgent(uiVO);
			BaseVO xbvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,xarg);
			xbvo.setPackageName("SFIS_QUERY_FINGERPRINT_PEGA_PG");
			xbvo.setFunctionName("Query_Mo_Begin");
			ResultVO xrvo = xbsa.doFunc(xbvo);
			Vector xresult = xrvo.getData();
			Hashtable ht = null;
			if(xresult.size()<2){
				TableUtil.setTableData(elbat, sd, atad);
				TableUtil.setTableData(table, ds, data);
			}else if(xresult.size()>1){
				for(int x = 1;x<=(xresult.size()-1);x++){
				    Vector row = new Vector();
					ht = (Hashtable)xresult.elementAt(x);
					row.add(CloneArray_ChangeStr.NulltoSpace(ht.get("PRODUCTLABEL")));
					row.add(CloneArray_ChangeStr.NulltoSpace(ht.get("JDMLABLE")));
					row.add(null);
					row.add(null);
					row.add(null);
					row.add(null);
					row.add(null);
					row.add(null);
					row.add(null);
					row.add(null);
					row.add(null);
					atad.add(row);
				}
				TableUtil.setTableData(elbat, sd, atad);
				TableUtil.setTableData(table, ds, data);
			}
			/*Vector uparg = new Vector();
			uparg.add(jtfMO.getText().trim());
			BaseServletAgent upbsa = new BaseServletAgent(uiVO);
			BaseVO upbvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,uparg);
			upbvo.setPackageName("SFIS_QUERY_FINGERPRINT_PEGA_PG");
			upbvo.setFunctionName("Query_Mo_End");
			ResultVO uprvo = upbsa.doFunc(upbvo);
			Vector upresult = uprvo.getData();
			Hashtable hy = null;
			if(upresult.size()<2){
				TableUtil.setTableData(elbat, sd, atad);
			}else if(upresult.size()>1){
				for(int x = 1;x<=(upresult.size()-1);x++){
					Vector row = new Vector();
					hy = (Hashtable)upresult.elementAt(x);
					row.add(CloneArray_ChangeStr.NulltoSpace(hy.get("TIMEFLOW")));
					row.add(CloneArray_ChangeStr.NulltoSpace(hy.get("PRODUCTLABEL")));
					row.add(CloneArray_ChangeStr.NulltoSpace(hy.get("PRINTTIME")));
					atad.add(row);
				}
				TableUtil.setTableData(elbat, sd, atad);
			}*/
			jtfMO.setText("");
		}catch(Exception e){
			e.printStackTrace();
			exUtl.ExceptionNotify(e,"Barcode_1616_Query_PEGA singleQuery ",this.getClass().toString(),VERSION);
		}
	}
	@Override
	public  Hashtable needValidateComponents(){
		return null;
	}
	@Override
	public void setReportOid(String reportOid) {
		// TODO Auto-generated method stub
		
	}
}                             