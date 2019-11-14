/**
 * Copyright (c) 2008 Pegatron Inc. All Rights Reserved.
 * $Id: ISN_Link_Color_PEGA.java,v 1.8 2017/12/26 08:28:38 Lucy6_Lu Exp $
 */
package mes_sfis.client.ui.link;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import java.util.Vector;
import java.util.StringTokenizer;
import java.util.Hashtable;
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
 * @version     $Name: 1.3 $, $Id: ISN_Link_Color_PEGA.java,v 1.8 2017/12/26 08:28:38 Lucy6_Lu Exp $
 * @author      <a href="mailto:william_tsai@asus.com.tw">William Tsai</a>
 * @history      [>: General, +: New, #: Modification, -: Remove, ~: Bug]
 *          $Log: ISN_Link_Color_PEGA.java,v $
 *          Revision 1.8  2017/12/26 08:28:38  Lucy6_Lu
 *          2017-12-26 信息提示修改
 *
 *          Revision 1.7  2017/12/23 06:42:21  Lucy6_Lu
 *          2017-12-23 信息提示
 *
 *          Revision 1.6  2016/12/28 11:41:56  Jieyu_Fu
 *          no message
 *
 *          Revision 1.5  2016/12/28 03:32:51  Jieyu_Fu
 *          no message
 *
 *          Revision 1.4  2016/12/28 02:38:47  Jieyu_Fu
 *          no message
 *
 *          Revision 1.3  2016/12/27 10:29:31  Jieyu_Fu
 *          no message
 *
 *          Revision 1.2  2016/12/16 03:53:24  Jieyu_Fu
 *          no message
 *
 *          Revision 1.1  2016/12/15 10:49:28  Jieyu_Fu
 *          no message
 *
 
 *
 */
public class ISN_Link_Color_PEGA extends BasePanel{
    public static final String VERSION    = "$Name:  $, $Id: ISN_Link_Color_PEGA.java,v 1.8 2017/12/26 08:28:38 Lucy6_Lu Exp $";
    public static final String COPYRIGHT  = "Copyright (c) 2008 PEGATRON Inc. All Rights Reserved.";

	private static final long serialVersionUID = -6120355920482734660L;

	private ISN_Link_Color_PEGA self;
	
	private JTextField JTLink_ISN;
	private JComboBox JCBColorPick;
	private JTextArea JTAMessage;
	private JButton JBSure;
	private JButton JBUnSure;
	private JLabel JLInput_Text;
	private JLabel JLColorPick;
	KeyListener _keyListener_SVT_Query;
	private String StrColorId = "";
	Hashtable Colht = new Hashtable();
	public ISN_Link_Color_PEGA(UI_InitVO uiVO) {
		super(uiVO);
		init();
	}
	
	private void init(){
		self = this;
		self.setUILayout(null);
		JLabel JLLink_ISN = new JLabel("請輸入ISN:");
		JLLink_ISN.setBounds(10,50,150,25);
		JTLink_ISN = new JTextField(40);
        JTLink_ISN.setBounds(180,50,200,25);
		JTLink_ISN.setEditable(false);
		add(JLLink_ISN);
        add(JTLink_ISN);
		JLabel JLISN_Barcode = new JLabel("請選擇顏色:");
		JLISN_Barcode.setBounds(10,80,150,25);
		JCBColorPick = new JComboBox();
        JCBColorPick.setBounds(180,80,200,25);
		add(JLISN_Barcode);
        add(JCBColorPick);
		ColorCheck();
		JTAMessage = new JTextArea();
		JTAMessage.setFont(new Font("宋體",Font.BOLD,20));
		JTAMessage.setForeground(Color.red);
		JTAMessage.setBounds(new Rectangle(45,230,400,100));
		JTAMessage.setEditable(false);
		add(JTAMessage,null);
		JLColorPick = new JLabel("");
		JLColorPick.setFont(new Font("宋體", Font.BOLD, 35));
		JLColorPick.setBounds(400,120,170,80);
		add(JLColorPick);
		JBSure = new JButton("鎖定顏色");
		JBSure.setBounds(80,120,130,80);
		JBUnSure = new JButton("解鎖顏色");
		JBUnSure.setBounds(240,120,130,80);
		BtnSureListener BtSu = new BtnSureListener();
		JBSure.addActionListener(BtSu);
		BtnUnSureListener BtUnSu = new BtnUnSureListener();
		JBUnSure.addActionListener(BtUnSu);
		JBUnSure.setEnabled(false);
		add(JBSure);
		add(JBUnSure);
		JLInput_Text = new JLabel("");
		JLInput_Text.setFont(new Font("宋體", Font.BOLD, 25));
		JLInput_Text.setBounds(80,270,500,200);
		add(JLInput_Text,null);
		_keyListener_SVT_Query = new KeyListener() {
			public void keyPressed(KeyEvent e) {
			}
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					SVT_Query();
				}
			}
			public void keyTyped(KeyEvent e) {}
		};
		JTLink_ISN.addKeyListener(_keyListener_SVT_Query);
		setStatus(new int[]{ToolBarItem.Exit});
	}
	public class BtnSureListener implements ActionListener {
		private JButton theBtn; 
		public BtnSureListener(){
			super();
		}
		public BtnSureListener(JButton theBtn){
			this();
			this.theBtn = theBtn;

		}
		public void actionPerformed(ActionEvent e) {  
			SureTheColor();
		}
	}
	public class BtnUnSureListener implements ActionListener {
		private JButton theBtn; 
		public BtnUnSureListener(){
			super();
		}
		public BtnUnSureListener(JButton theBtn){
			this();
			this.theBtn = theBtn;

		}
		public void actionPerformed(ActionEvent e) {  
			JCBColorPick.setEnabled(true);
			JCBColorPick.setSelectedItem("");
			JBSure.setEnabled(true);
			JLColorPick.setText("");
			StrColorId = "";
			JTLink_ISN.setEditable(false);
			JBUnSure.setEnabled(false);
		}
	}
	private void ColorCheck(){
		try {
			Vector arg = new Vector();
			BaseServletAgent bsa = new BaseServletAgent(uiVO);
			BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
			bvo.setPackageName("SFIS_ISN_LINK_COLOR_PEGA_PG");
			bvo.setFunctionName("Query_Link_Color");
			ResultVO rvo = bsa.doFunc(bvo);
		  	Vector result = rvo.getData();
			Hashtable ht = null;
		  	if(result.size()>1){
				JCBColorPick.removeAllItems();
				JCBColorPick.addItem("");
				for(int x = 1;x<=(result.size()-1);x++){
					ht = (Hashtable)result.elementAt(x);
					Colht.put(CloneArray_ChangeStr.NulltoSpace(ht.get("COLOR_NAME")),CloneArray_ChangeStr.NulltoSpace(ht.get("COLOR_ID")));
					JCBColorPick.addItem(CloneArray_ChangeStr.NulltoSpace(ht.get("COLOR_NAME")));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	private void SureTheColor(){
		if(CloneArray_ChangeStr.NulltoSpace(JCBColorPick.getSelectedItem()).equals("")){
			JOptionPane.showMessageDialog(null,"請選擇顏色");
			return;
		}
		StrColorId = CloneArray_ChangeStr.NulltoSpace(Colht.get(CloneArray_ChangeStr.NulltoSpace(JCBColorPick.getSelectedItem())));
		JLColorPick.setText(CloneArray_ChangeStr.NulltoSpace(JCBColorPick.getSelectedItem()));
		JCBColorPick.setEnabled(false);
		JBSure.setEnabled(false);
		JTLink_ISN.setEditable(true);
		JBUnSure.setEnabled(true);
	}
	public void SVT_Query(){
		setMessage("");
		try{
			String sql = "select t.isn,t.item from tp.isn t where t.isn = '" + JTLink_ISN.getText() + "'";
			Hashtable ht = getDBDATA(sql,DataSourceType._SFIS_KAIJIA_STD,uiVO);
			if(ht == null){
				JLInput_Text.setText("該ISN不存在 !");
				setMessage("該ISN不存在");
				JTAMessage.setText("輸入的為: " + CloneArray_ChangeStr.NulltoSpace(JTLink_ISN.getText()));
				JOptionPane.showMessageDialog(null,"該ISN不存在");
				JTLink_ISN.setText("");
				return;
			}
			Vector arg = new Vector();
			arg.add(StrColorId);
			arg.add(CloneArray_ChangeStr.NulltoSpace(JTLink_ISN.getText()));
			arg.add(CloneArray_ChangeStr.NulltoSpace(ht.get("ITEM")));
			BaseServletAgent bsa = new BaseServletAgent(uiVO);
			BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
			bvo.setPackageName("SFIS_ISN_LINK_COLOR_PEGA_PG");
			bvo.setFunctionName("Insert_ISN_Color");	
			ResultVO rvo = bsa.doFunc(bvo);
			Vector result = rvo.getData();
			if(result.size()>1){
				Hashtable row = (Hashtable)result.get(1);
				JLInput_Text.setText(CloneArray_ChangeStr.NulltoSpace(row.get("ERRMISS")) + CloneArray_ChangeStr.NulltoSpace(JTLink_ISN.getText()));
				JTAMessage.setText("輸入的為: " + CloneArray_ChangeStr.NulltoSpace(JTLink_ISN.getText()));
				JTLink_ISN.setText("");
			}
		}catch(Exception e){
				e.printStackTrace();
				exUtl.ExceptionNotify(e,"ISN_Link_Color_PEGA SVT_Query ",this.getClass().toString(),VERSION);
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
		if(result.size() == 2){			
			ht = (Hashtable)result.elementAt(1);
		}
		return ht;
	}
    @Override
	public void modify(){}
	public void create(){}
	public void save(){}
	@Override
	public void delete(){}
	@Override
	public void cancel(){}
	@Override
	public void singleQuery(){}
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