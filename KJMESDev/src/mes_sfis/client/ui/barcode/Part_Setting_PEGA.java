/**
 * Copyright (c) 2008 Pegatron Inc. All Rights Reserved.
 * $Id: Part_Setting_PEGA.java,v 1.22 2016/12/06 08:41:00 Jieyu_Fu Exp $
 */
package mes_sfis.client.ui.barcode;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import java.util.Vector;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;
import base.enums.DataSourceType;
import base.client.enums.ToolBarItem;
import base.client.ui.BasePanel;
import base.client.util.BaseServletAgent;
import base.client.util.CloneArray_ChangeStr;
import base.vo.BaseVO;
import base.vo.ResultVO;
import base.enums.CommandName;
import base.vo.UI_InitVO;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.*;          
import java.awt.*;
import java.awt.event.*;
 /**
 * The <code>xxx</code> class.
 *
 * @version     $Name: 1.3 $, $Id: Part_Setting_PEGA.java,v 1.22 2016/12/06 08:41:00 Jieyu_Fu Exp $
 * @author      <a href="mailto:william_tsai@asus.com.tw">William Tsai</a>
 * @history      [>: General, +: New, #: Modification, -: Remove, ~: Bug]
 *          $Log: Part_Setting_PEGA.java,v $
 *          Revision 1.22  2016/12/06 08:41:00  Jieyu_Fu
 *          no message
 *
 *          Revision 1.21  2016/12/06 07:26:22  Jieyu_Fu
 *          no message
 *
 *          Revision 1.20  2016/12/06 07:08:54  Jieyu_Fu
 *          no message
 *
 *          Revision 1.19  2016/12/06 05:50:13  Jieyu_Fu
 *          no message
 *
 *          Revision 1.18  2016/12/06 05:39:04  Jieyu_Fu
 *          no message
 *
 *          Revision 1.17  2016/12/06 03:59:01  Jieyu_Fu
 *          no message
 *
 *          Revision 1.16  2016/12/06 03:08:43  Jieyu_Fu
 *          no message
 *
 *          Revision 1.15  2016/12/06 02:03:11  Jieyu_Fu
 *          no message
 *
 *          Revision 1.14  2016/12/05 07:42:09  Jieyu_Fu
 *          no message
 *
 *          Revision 1.13  2016/12/05 06:32:06  Jieyu_Fu
 *          no message
 *
 *          Revision 1.12  2016/12/05 05:41:36  Jieyu_Fu
 *          no message
 *
 *          Revision 1.11  2016/12/02 04:00:52  Jieyu_Fu
 *          no message
 *
 *          Revision 1.10  2016/12/02 00:33:13  Jieyu_Fu
 *          no message
 *
 *          Revision 1.9  2016/12/01 02:21:37  Jieyu_Fu
 *          no message
 *
 *          Revision 1.8  2016/12/01 01:11:04  Jieyu_Fu
 *          no message
 *
 *          Revision 1.7  2016/11/30 06:43:55  Jieyu_Fu
 *          no message
 *
 *          Revision 1.6  2016/11/28 12:20:38  Jieyu_Fu
 *          no message
 *
 *          Revision 1.5  2016/11/28 11:58:11  Jieyu_Fu
 *          no message
 *
 *          Revision 1.4  2016/11/28 09:28:06  Jieyu_Fu
 *          no message
 *
 *          Revision 1.3  2016/11/28 09:07:42  Jieyu_Fu
 *          no message
 *
 *			Revision 1.1  2016/11/18 02:16:00  Jieyu_Fu
 *          no message
 *
 *          Revision 1.3  2013/06/14 08:44:51  william_tsai
 *          no message
 *
 */
public class Part_Setting_PEGA extends BasePanel{
    public static final String VERSION    = "$Name:  $, $Id: Part_Setting_PEGA.java,v 1.22 2016/12/06 08:41:00 Jieyu_Fu Exp $";
    public static final String COPYRIGHT  = "Copyright (c) 2008 PEGATRON Inc. All Rights Reserved.";

	private static final long serialVersionUID = -6120355920482734660L;

	private Part_Setting_PEGA self;
	
	private JTextField JTPart_No;
	private JTextField JTCust_Part_No;
	private JTextField JTQty;
	private JTextField JTBatch_No;
	private JTextField JTMemo;	
	private JTextField JTLink_Grp;

	private JComboBox JCRoute;
	private JComboBox JCRoute_Step;
	
	private int LastStatus;

	private final static String _3space = "   ";

	public Part_Setting_PEGA(UI_InitVO uiVO) {
		super(uiVO);
		init();
	}
	
	private void init(){
		self = this;
		self.setUILayout(null);
		
		JLabel JLPart_No = new JLabel("Vendor P/N");
		JLPart_No.setBounds(10,10,95,25);
		JTPart_No = new JTextField(20);
        JTPart_No.setBounds(110,10,150,25);
        add(JLPart_No);
        add(JTPart_No);
        JLabel JLCust_Part_No = new JLabel("Pega P/N");
		JLCust_Part_No.setBounds(10,50,95,25);
		JTCust_Part_No = new JTextField(20);
        JTCust_Part_No.setBounds(110,50,150,25);
        add(JLCust_Part_No);
		add(JTCust_Part_No);
		JLabel JLBatch_No = new JLabel("BATCH_NO");
		JLBatch_No.setBounds(10,90,95,25);
		JTBatch_No = new JTextField(20);
        JTBatch_No.setBounds(110,90,150,25);
        add(JLBatch_No);
        add(JTBatch_No);
        JLabel JLQty = new JLabel("QTY");
		JLQty.setBounds(10,130,95,25);
		JTQty = new JTextField(20);
        JTQty.setBounds(110,130,150,25);
		add(JLQty);
        add(JTQty);
        JLabel JLMemo = new JLabel("Memo");
		JLMemo.setBounds(10,170,95,25);
		JTMemo = new JTextField(20);
        JTMemo.setBounds(110,170,150,25);
		add(JLMemo);
		add(JTMemo);
		JLabel JLLink_GRP = new JLabel("Route_Step");
		JLLink_GRP.setBounds(10,290,95,25);
		JTLink_Grp = new JTextField(20);
        JTLink_Grp.setBounds(110,290,150,25);
		JTLink_Grp.setEditable(false);
		add(JLLink_GRP);
		add(JTLink_Grp);
		JLabel JLRoute = new JLabel("Route");
		JLRoute.setBounds(10,210,95,25);
		JCRoute = new JComboBox();
		JCRoute_Source();
		JCRoute.setSelectedItem("");
		JCRoute.setBounds(110,210,150,25);
		JCRouteListener jcroulisten = new JCRouteListener();
		JCRoute.addActionListener(jcroulisten);
		JCRoute.setEnabled(false);
		add(JLRoute);
		add(JCRoute);
		JLabel JLRoute_Step = new JLabel("Items");
		JLRoute_Step.setBounds(10,250,95,25);
		JCRoute_Step = new JComboBox();
		JCRoute_Step.setBounds(110,250,450,25);
		JCRoute_StepListener jcrou_steplisten = new JCRoute_StepListener();
		JCRoute_Step.addActionListener(jcrou_steplisten);
		JCRoute_Step.setEnabled(false);
		add(JLRoute_Step);
		add(JCRoute_Step);
		JTPart_No.setEditable(true);
		JTCust_Part_No.setEditable(false);
		JTBatch_No.setEditable(false);
		JTMemo.setEditable(false);
		JTQty.setEditable(false);
		setStatus(new int[]{ToolBarItem.SingleQuery,ToolBarItem.Create,ToolBarItem.Cancel,ToolBarItem.Exit});
	}
	public class JCRouteListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {  
			JCRoute_Step_Source();
		}
	}
	public class JCRoute_StepListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {  
			Atuo_Write_JTGrp();
		}
	}
	private int Input_Check(){
		int a = 0;
		if(JTPart_No.getText().getBytes().length > 20){
			JOptionPane.showMessageDialog(null,"Vendor P/N過長 !");
			a++;
			return a;
		}
		else if(JTCust_Part_No.getText().getBytes().length > 20){
			JOptionPane.showMessageDialog(null,"Pega P/N過長 !");
			a++;
			return a;
		}
		else if(JTBatch_No.getText().getBytes().length > 6){
			JOptionPane.showMessageDialog(null,"Batch過長 !");
			a++;
			return a;
		}
		else if(!JTQty.getText().matches("[0-9]*")){
			JOptionPane.showMessageDialog(null,"QTY只能輸入數字 !");
			a++;
			return a;
		}
		else if(JTMemo.getText().getBytes().length > 100){
			JOptionPane.showMessageDialog(null,"Memo過長 !");
			a++;
			return a;
		}
		return a;
	}
	private void JCombobox_Able(){
		if(LastStatus == ToolBarItem.Modify || LastStatus == ToolBarItem.Create){
			JCRoute.setEnabled(true);
			JCRoute_Step.setEnabled(true);
		}else{
			JCRoute.setEnabled(false);
			JCRoute_Step.setEnabled(false);
		}
	}
	private void Atuo_Write_JTGrp(){
		String step = CloneArray_ChangeStr.NulltoSpace(JCRoute_Step.getSelectedItem());
		JTLink_Grp.setText("");
		if(!step.equals("")){
			StringTokenizer st = new StringTokenizer(step,_3space);
			String z0 = st.nextToken();
			String z1 = st.nextToken();
			JTLink_Grp.setText(z1);
		}		
	}
	private void JCRoute_Source(){
		try{
			String sql = "select t.route from tp.route t";
			int Status = ToolBarItem.Create;
			Hashtable ht = getDBDATA(sql,DataSourceType._SFIS_KAIJIA_STD,uiVO,Status);
		}catch(Exception e){
			e.printStackTrace();
			exUtl.ExceptionNotify(e,"Barcode Part_Setting_PEGA JCRoute_Source ",this.getClass().toString(),VERSION);
		}	
	}
	private void JCRoute_Step_Source(){
		try{
			if(JCRoute.getSelectedItem().equals("")){
				JCRoute_Step.setSelectedItem("");
				JTLink_Grp.setText("");
				return;
			}
			String sql = "select t.route,t.step,t.grp,t.stepnm from tp.route_step t where t.route = '" + JCRoute.getSelectedItem() + "' AND RTYPE3 = 'A' ORDER BY RIDX";
			int Status = ToolBarItem.Save;
			Hashtable ht = getDBDATA(sql,DataSourceType._SFIS_KAIJIA_STD,uiVO,Status);
			if(ht == null){
				JOptionPane.showMessageDialog(null,"SFIS 裡面不存在此 Route_Step !");
				return;
			}
		}catch(Exception e){
			e.printStackTrace();
			exUtl.ExceptionNotify(e,"Barcode Part_Setting_PEGA JCRoute_Step_Source ",this.getClass().toString(),VERSION);
		}
	}
    @Override
	public void modify(){
		JTPart_No.setEditable(false);
		JTCust_Part_No.setEditable(true);
		JTBatch_No.setEditable(true);
		JTMemo.setEditable(true);
		JTQty.setEditable(true);
		LastStatus = ToolBarItem.Modify;
		JCombobox_Able();
		setStatus(new int[]{ToolBarItem.Save,ToolBarItem.Cancel,ToolBarItem.Exit});
	}
	public void create(){
		all_clear();
		JTPart_No.setEditable(true);
		JTCust_Part_No.setEditable(true);
		JTBatch_No.setEditable(true);
		JTMemo.setEditable(true);
		JTQty.setEditable(true);
		LastStatus = ToolBarItem.Create;
		JCombobox_Able();
		setStatus(new int[]{ToolBarItem.Save,ToolBarItem.Cancel,ToolBarItem.Exit});
	}
	public void save(){
		if(Input_Check()>0){
			return;
		}
		if(LastStatus == ToolBarItem.Modify){
			update();
		}else if(LastStatus == ToolBarItem.Create){
			insert();
		}
	}
	private void update(){
		if(JTPart_No.getText().equals("")||JTCust_Part_No.getText().equals("")||JTBatch_No.getText().equals("")||JTQty.getText().equals("")||JTMemo.getText().equals("")
			||JTLink_Grp.getText().equals("")){
			JOptionPane.showMessageDialog(null,"請填寫所有空白!");
			return;
		}
		try{
			Vector uparg = new Vector();
			uparg.add(JTPart_No.getText());
			uparg.add(JTCust_Part_No.getText());
			uparg.add(JTBatch_No.getText());
			uparg.add(JTQty.getText());
			uparg.add(JTMemo.getText());
			uparg.add(JTLink_Grp.getText());
			BaseServletAgent upbsa = new BaseServletAgent(uiVO);
			BaseVO upbvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,uparg);
			upbvo.setPackageName("SFIS_LABEL_PSETTING_PEGA_PG");
			upbvo.setFunctionName("Update_Custom_Part");
			ResultVO uprvo = upbsa.doFunc(upbvo);
			Vector upresult = uprvo.getData();
			Hashtable row = (Hashtable)upresult.get(1);
			JOptionPane.showMessageDialog(null,CloneArray_ChangeStr.NulltoSpace(row.get("ERRMISS")));
			all_clear();
			JTPart_No.setEditable(true);
			JTCust_Part_No.setEditable(false);
			JTBatch_No.setEditable(false);
			JTMemo.setEditable(false);
			JTQty.setEditable(false);
			setStatus(new int[]{ToolBarItem.SingleQuery,ToolBarItem.Create,ToolBarItem.Cancel,ToolBarItem.Exit});
		}catch(Exception e){
			e.printStackTrace();
			exUtl.ExceptionNotify(e,"Barcode Part_Setting_PEGA update ",this.getClass().toString(),VERSION);
		}			
	}
	private void insert(){
		try{
			String sql = "select i.ima01 from ima_file i where ima01 = '"+JTPart_No.getText()+"'";
			int Status = ToolBarItem.SingleQuery;
			Hashtable ht = getDBDATA(sql,DataSourceType._TIPTOP_KAIJIA_TOPPROD,uiVO,Status);
			if(ht == null){
				JOptionPane.showMessageDialog(null,"Tiptop 中無此 Vendor P/N !");
				return;
			}
			if(JTPart_No.getText().equals("")||JTCust_Part_No.getText().equals("")||JTBatch_No.getText().equals("")||JTQty.getText().equals("")||JTMemo.getText().equals("")
				||JTLink_Grp.getText().equals("")){
				JOptionPane.showMessageDialog(null,"請填寫所有空白!");
				return;
			}
			Vector arg = new Vector();
			arg.add(JTPart_No.getText());
			arg.add(JTCust_Part_No.getText());
			arg.add(JTBatch_No.getText());
			arg.add(JTQty.getText());
			arg.add(JTMemo.getText());
			arg.add(JTLink_Grp.getText());
			BaseServletAgent bsa = new BaseServletAgent(uiVO);
			BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
			bvo.setPackageName("SFIS_LABEL_PSETTING_PEGA_PG");
			bvo.setFunctionName("Insert_Custom_Part");	
			ResultVO rvo = bsa.doFunc(bvo);
		  	Vector result = rvo.getData();
			Hashtable row = (Hashtable)result.get(1);
			if(CloneArray_ChangeStr.NulltoSpace(row.get("ERRMISS")).equals("MSG_Ins")){
				JOptionPane.showMessageDialog(null,"插入成功 !");
				all_clear();
				JTPart_No.setEditable(true);
				JTCust_Part_No.setEditable(false);
				JTBatch_No.setEditable(false);
				JTMemo.setEditable(false);
				JTQty.setEditable(false);
				setStatus(new int[]{ToolBarItem.SingleQuery,ToolBarItem.Create,ToolBarItem.Cancel,ToolBarItem.Exit});
			}else{
				JOptionPane.showMessageDialog(null,CloneArray_ChangeStr.NulltoSpace(row.get("ERRMISS")));
			}
		}catch(Exception e){
			e.printStackTrace();
			exUtl.ExceptionNotify(e,"Barcode Part_Setting_PEGA insert ",this.getClass().toString(),VERSION);
		}
	}
	@Override
	public void delete(){
		try{
			Vector arg = new Vector();
			arg.add(JTPart_No.getText());
			BaseServletAgent bsa = new BaseServletAgent(uiVO);
			BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
			bvo.setPackageName("SFIS_LABEL_PSETTING_PEGA_PG");
			bvo.setFunctionName("Delete_Custom_Part");
			ResultVO rvo = bsa.doFunc(bvo);
			Vector result = rvo.getData();
			Hashtable row = (Hashtable)result.get(1);
			if(CloneArray_ChangeStr.NulltoSpace(row.get("ERRMISS")).equals("MSG_Del")){
				JOptionPane.showMessageDialog(null,"刪除成功 !");
				all_clear();
				JTPart_No.setEditable(true);
				JTCust_Part_No.setEditable(false);
				JTBatch_No.setEditable(false);
				JTMemo.setEditable(false);
				JTQty.setEditable(false);
				setStatus(new int[]{ToolBarItem.SingleQuery,ToolBarItem.Create,ToolBarItem.Cancel,ToolBarItem.Exit});
			}else{
				JOptionPane.showMessageDialog(null,CloneArray_ChangeStr.NulltoSpace(row.get("ERRMISS")));
			}
		}catch(Exception e){
			e.printStackTrace();
			exUtl.ExceptionNotify(e,"Barcode Part_Setting_PEGA delete ",this.getClass().toString(),VERSION);
		}				
	}
	@Override
	public void cancel() {
		all_clear();
		JTPart_No.setEditable(true);
		JTCust_Part_No.setEditable(false);
		JTBatch_No.setEditable(false);
		JTMemo.setEditable(false);
		JTQty.setEditable(false);
		setStatus(new int[]{ToolBarItem.SingleQuery,ToolBarItem.Create,ToolBarItem.Cancel,ToolBarItem.Exit});
	}
	private void all_clear(){
		LastStatus = -1;
		JCombobox_Able();
		JTPart_No.setText("");
		JTCust_Part_No.setText("");
		JTBatch_No.setText("");
		JTQty.setText("");
		JTMemo.setText("");
		JCRoute.setSelectedItem("");
	}
	@Override
	public void singleQuery() {
		if(JTPart_No.getText().equals("")){
			JOptionPane.showMessageDialog(null,"請輸入 Vendor P/N !");
			return;
		}
		Vector arg = new Vector();
		arg.add(JTPart_No.getText());
		BaseServletAgent bsa = new BaseServletAgent(uiVO);
		BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
		bvo.setPackageName("SFIS_LABEL_PSETTING_PEGA_PG");
		bvo.setFunctionName("Query_Custom_Part");			
		try{
			String sql = "select i.ima01 from ima_file i where ima01 = '"+JTPart_No.getText()+"'";
			int Status = ToolBarItem.SingleQuery;
			Hashtable ht = getDBDATA(sql,DataSourceType._TIPTOP_KAIJIA_TOPPROD,uiVO,Status);
			ResultVO frvo = bsa.doFunc(bvo);
			Vector fresult = frvo.getData();
			if(ht == null){
				JOptionPane.showMessageDialog(null,"Tiptop 中無此 Vendor P/N !");
				return;
			}
			if(fresult.size()<2){
				JOptionPane.showMessageDialog(null,"數據庫表中不存在此 Vendor P/N !");
				return;
			}
				
			ResultVO rvo = bsa.doFunc(bvo);
			Vector result = rvo.getData();
			Hashtable row = (Hashtable)result.get(1);
			JTCust_Part_No.setText(CloneArray_ChangeStr.NulltoSpace(row.get("CUST_PART_NO")));
			JTBatch_No.setText(CloneArray_ChangeStr.NulltoSpace(row.get("BATCH_NO")));
			JTQty.setText(CloneArray_ChangeStr.NulltoSpace(row.get("QTY")));
			JTMemo.setText(CloneArray_ChangeStr.NulltoSpace(row.get("MEMO")));
			JTLink_Grp.setText(CloneArray_ChangeStr.NulltoSpace(row.get("LINK_ROUTE_STEP")));
			setStatus(new int[]{ToolBarItem.Modify,ToolBarItem.Delete,ToolBarItem.Cancel});
		    JTPart_No.setEditable(false);
		}catch(Exception e){
				e.printStackTrace();
				exUtl.ExceptionNotify(e,"Barcode Part_Setting_PEGA singleQuery ",this.getClass().toString(),VERSION);
		}
	}
	private Hashtable getDBDATA(String sql,String ds,UI_InitVO uiVO,int Status)throws Exception{
		String reString = "";
		Vector arg = new Vector();
		arg.add(sql);
	    BaseServletAgent bsa1 = new BaseServletAgent(uiVO);
		BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallSQLCmd2,arg);
		bvo.setDataSourceType(ds);
		ResultVO rvo = bsa1.doFunc(bvo);
		Vector result = rvo.getData();
		Hashtable ht = null;
		if(Status == ToolBarItem.Create){
			JCRoute.removeAllItems();
			JCRoute.addItem("");
			if(result.size()>1){
				for(int x = 1;x<=(result.size()-1);x++){
					ht = (Hashtable)result.elementAt(x);
					JCRoute.addItem(CloneArray_ChangeStr.NulltoSpace(ht.get("ROUTE")));
				}
			}
		}else if(Status == ToolBarItem.Save){
			JCRoute_Step.removeAllItems();
			JCRoute_Step.addItem("");
			if(result.size()>1){
				for(int x = 1;x<=(result.size()-1);x++){
					ht = (Hashtable)result.elementAt(x);
					JCRoute_Step.addItem(CloneArray_ChangeStr.NulltoSpace(ht.get("ROUTE")) + _3space + CloneArray_ChangeStr.NulltoSpace(ht.get("STEP")) + _3space + 
					CloneArray_ChangeStr.NulltoSpace(ht.get("GRP")) + _3space + CloneArray_ChangeStr.NulltoSpace(ht.get("STEPNM")));
				}
			}
		}else{
			if(result.size() == 2){			
				ht = (Hashtable)result.elementAt(1);
			}
		}	
		return ht;	
	}
	
    @Override
	public  Hashtable needValidateComponents(){
		return null;
	}

	@Override
	public void email() {}

	@Override
	public void exit() {}

	@Override
	public void export() {}

	@Override
	public void help() {}

	@Override
	public void importData() {}
	
    @Override
	public void multiQuery() {}

	@Override
	public void print() {}

	@Override
	public void refresh() {}
	@Override
	public void setReportOid(String reportOid) {
		
	}
        
	
}                               