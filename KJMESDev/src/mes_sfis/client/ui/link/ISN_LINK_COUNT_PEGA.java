/**
 * Copyright (c) 2008 Pegatron Inc. All Rights Reserved.
 * $Id: ISN_LINK_COUNT_PEGA.java,v 1.2 2016/12/23 09:02:43 Jieyu_Fu Exp $
 */
package mes_sfis.client.ui.link;
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
 * @version     $Name: 1.3 $, $Id: ISN_LINK_COUNT_PEGA.java,v 1.2 2016/12/23 09:02:43 Jieyu_Fu Exp $
 * @author      <a href="mailto:william_tsai@asus.com.tw">William Tsai</a>
 * @history      [>: General, +: New, #: Modification, -: Remove, ~: Bug]
 *          $Log: ISN_LINK_COUNT_PEGA.java,v $
 *          Revision 1.2  2016/12/23 09:02:43  Jieyu_Fu
 *          no message
 *
 *          Revision 1.1  2016/12/23 08:37:33  Jieyu_Fu
 *          no message
 *
 */
public class ISN_LINK_COUNT_PEGA extends BasePanel{
    public static final String VERSION    = "$Name:  $, $Id: ISN_LINK_COUNT_PEGA.java,v 1.2 2016/12/23 09:02:43 Jieyu_Fu Exp $";
    public static final String COPYRIGHT  = "Copyright (c) 2008 PEGATRON Inc. All Rights Reserved.";

	private PExportTable				table;
	private JCEditableVectorDataSource	ds					= new JCEditableVectorDataSource();
	private Vector						data				= new Vector();
	private Vector						column				= new Vector();
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
	private JLabel JLCountLink;
	private PMIDComboBox jcb_dis_class;
	private PDateTimeTextField PDStartTime;
	private PDateTimeTextField PDEndTime;
	Hashtable<String,Hashtable> htUpdateData=new Hashtable<String,Hashtable>();
	
	private ISN_LINK_COUNT_PEGA				self;
	private int							lastStatus			= -1;

	public ISN_LINK_COUNT_PEGA(UI_InitVO uiVO) {
		super(uiVO);
		init();
	}
	
	
	private void init() {
		self = this;
		self.setUILayout(null);
		table				= new PExportTable(uiVO,"table", null, "ISN_LINK_COUNT_PEGA_table");
		column.add("ISN");//"刪除選擇"
		column.add("一維條碼");
		column.add("LINK時間");
		TableUtil.init_table(table, ds, data, column);
		table.addEditCellListener(editListener);
		table.addMouseListener(mouselistener);
		table.setBounds(120,125,800,600);
		add(table);
		JLabel JLStartTime = new JLabel("起始時間");
		JLStartTime.setBounds(50,80,85,25);
        add(JLStartTime);
		PDStartTime=new PDateTimeTextField(uiVO,"PDStartTime",150,true,true);
		PDStartTime.setBounds(new Rectangle(138,80,150,25));
		add(PDStartTime);
		JLabel JLEndTime = new JLabel("結束時間");
		JLEndTime.setBounds(325,80,85,25);
        add(JLEndTime);
		PDEndTime=new PDateTimeTextField(uiVO,"PDEndTime",150,true,true);
		PDEndTime.setBounds(new Rectangle(413,80,150,25));
		add(PDEndTime);
		callSql = new CallSQL(uiVO, this.getClass().toString());
		JLCountLink = new JLabel("");
		JLCountLink.setBounds(250,50,130,25);
        add(JLCountLink);
		setStatus(new int[] { ToolBarItem.SingleQuery, ToolBarItem.Exit });  //拿掉單筆查詢按鈕
		
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
		toWriteTable();

	}
	private void toWriteTable(){
		data.clear();
		try{
			Vector arg = new Vector();
			arg.add(PDStartTime.getText());
			arg.add(PDEndTime.getText());
			BaseServletAgent bsa = new BaseServletAgent(uiVO);
			BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
			bvo.setPackageName("SFIS_LINK_COUNT_PEGA_PG");
			bvo.setFunctionName("Query_Link_Count");	
			ResultVO rvo = bsa.doFunc(bvo);
			Vector result = rvo.getData();
			Hashtable ht = null;
			if(result.size()<2){
				TableUtil.setTableData(table, ds, data);
			}
			else if(result.size()>1){
				for(int x = 1;x<=(result.size()-1);x++){
				    Vector row = new Vector();
					ht = (Hashtable)result.elementAt(x);
					row.add(CloneArray_ChangeStr.NulltoSpace(ht.get("ISN")));
					row.add(CloneArray_ChangeStr.NulltoSpace(ht.get("CUST_BARCODE")));
					row.add(CloneArray_ChangeStr.NulltoSpace(ht.get("ISN_LINK_DATE")));
					data.add(row);
				}			
				TableUtil.setTableData(table, ds, data);
			}
			JLCountLink.setText("共LINK了 " + (result.size()-1) + " 筆");;
		}catch(Exception e){
			e.printStackTrace();
			exUtl.ExceptionNotify(e,"ISN_LINK_COUNT_PEGA toWriteTable ",this.getClass().toString(),VERSION);
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