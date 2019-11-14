/**
 * Copyright (c) 2008 Pegatron Inc. All Rights Reserved.
 * $Id: ISN_BarCode_Query_PEGA.java,v 1.6 2016/12/07 03:19:03 Jieyu_Fu Exp $
 */
package mes_sfis.client.ui.link;
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
import java.awt.Rectangle;
/**
 * The <code>xxx</code> class.
 *
 * @version     $Name: 1.3 $, $Id: ISN_BarCode_Query_PEGA.java,v 1.6 2016/12/07 03:19:03 Jieyu_Fu Exp $
 * @author      <a href="mailto:william_tsai@asus.com.tw">William Tsai</a>
 * @history      [>: General, +: New, #: Modification, -: Remove, ~: Bug]
 *          $Log: ISN_BarCode_Query_PEGA.java,v $
 *          Revision 1.6  2016/12/07 03:19:03  Jieyu_Fu
 *          no message
 *
 *          Revision 1.5  2016/12/07 02:00:51  Jieyu_Fu
 *          no message
 *
 *          Revision 1.4  2016/12/07 00:23:18  Jieyu_Fu
 *          no message
 *
 *          Revision 1.3  2016/12/06 10:37:54  Jieyu_Fu
 *          no message
 *
 *          Revision 1.2  2016/12/06 02:36:21  Jieyu_Fu
 *          no message
 *
 *          Revision 1.1  2016/12/06 02:13:11  Jieyu_Fu
 *          no message
 *
 *          Revision 1.1  2016/12/03 01:29:05  Jieyu_Fu
 *          no message
 *
 *          Revision 1.2  2016/11/28 05:58:12  Jieyu_Fu
 *          no message
 *
 *			Revision 1.1  2016/11/18 02:16:00  Jieyu_Fu
 *          no message
 *
 *          Revision 1.3  2013/06/14 08:44:51  william_tsai
 *          no message
 *
 */
public class ISN_BarCode_Query_PEGA extends BasePanel {
    public static final String VERSION    = "$Name:  $, $Id: ISN_BarCode_Query_PEGA.java,v 1.6 2016/12/07 03:19:03 Jieyu_Fu Exp $";
    public static final String COPYRIGHT  = "Copyright (c) 2008 PEGATRON Inc. All Rights Reserved.";

	private static final long serialVersionUID = -6120355920482734660L;

	private ISN_BarCode_Query_PEGA self;
	
	private JTextField JTCust_Barcode;
	private JTextArea JTAMessage;
	KeyListener _keyListener_SVT_Query;
	private JLabel JLInput_Text;
	public ISN_BarCode_Query_PEGA(UI_InitVO uiVO) {
		super(uiVO);
		init();
	}
	
	private void init(){
		self = this;
		self.setUILayout(null);
		JLabel JLISN_Barcode = new JLabel("請輸入ISN或者一維條碼:");
		JLISN_Barcode.setBounds(10,50,150,25);
		JTCust_Barcode = new JTextField(40);
        JTCust_Barcode.setBounds(180,50,200,25);
		add(JLISN_Barcode);
        add(JTCust_Barcode);
		JTAMessage = new JTextArea();
		JTAMessage.setBounds(new Rectangle(45,80,400,100));
		JTAMessage.setEditable(false);
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
		JTCust_Barcode.addKeyListener(_keyListener_SVT_Query);
		add(JTAMessage,null);
		JLInput_Text = new JLabel("");
		JLInput_Text.setFont(new Font("宋體", Font.BOLD, 16));
		JLInput_Text.setBounds(new Rectangle(120,190,400,200));
		add(JLInput_Text,null);
		setStatus(new int[]{ToolBarItem.Exit});
	}
	public void SVT_Query(){
		try{
			Vector arg = new Vector();
			arg.add(JTCust_Barcode.getText());
			BaseServletAgent bsa = new BaseServletAgent(uiVO);
			BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
			bvo.setPackageName("SFIS_LINK_ISNQuery_PEGA_PG");
			bvo.setFunctionName("Query_Cust_Barcode");	
			ResultVO rvo = bsa.doFunc(bvo);
			Vector result = rvo.getData();
			if(result.size()>1){
				Hashtable row = (Hashtable)result.get(1);
				if(CloneArray_ChangeStr.NulltoSpace(row.get("ISN")).equals("") && !CloneArray_ChangeStr.NulltoSpace(row.get("CUST_BARCODE")).equals("")){
					JTAMessage.setText("該一維條碼未綁定ISN !");
					JLInput_Text.setText("輸入的為: " + CloneArray_ChangeStr.NulltoSpace(JTCust_Barcode.getText()));
					JTCust_Barcode.setText("");
					return;
				}
				if(!CloneArray_ChangeStr.NulltoSpace(row.get("ERRMISS")).equals("")){
					try{
						String sql = "select t.isn from tp.isn t where t.isn = '" + JTCust_Barcode.getText() + "'";
						Hashtable ht = getDBDATA(sql,DataSourceType._SFIS_KAIJIA_STD,uiVO);
						if(ht == null){
							JTAMessage.setText(CloneArray_ChangeStr.NulltoSpace(row.get("ERRMISS")));
						}else{
							JTAMessage.setText("該ISN尚未被綁定 !");
						}
						JLInput_Text.setText("輸入的為: " + CloneArray_ChangeStr.NulltoSpace(JTCust_Barcode.getText()));
						JTCust_Barcode.setText("");
					}catch(Exception e){
						e.printStackTrace();
						exUtl.ExceptionNotify(e,"Barcode Part_Setting_PEGA JCRoute_Source ",this.getClass().toString(),VERSION);
					}
					return;
				}
				JTAMessage.setText("一維條碼:   " + CloneArray_ChangeStr.NulltoSpace(row.get("CUST_BARCODE"))
				+ "\n" + "ISN:   " + CloneArray_ChangeStr.NulltoSpace(row.get("ISN")) + "\n"  + "綁定日期:   " + CloneArray_ChangeStr.NulltoSpace(row.get("ISN_LINK_DATE"))
				+ "\n" + "料號:   " + CloneArray_ChangeStr.NulltoSpace(row.get("PART_NO")));

				JLInput_Text.setText("輸入的為: " + CloneArray_ChangeStr.NulltoSpace(JTCust_Barcode.getText()));
				JTCust_Barcode.setText("");

			}else{
				JTAMessage.setText("該號碼不存在 !");
				JLInput_Text.setText("輸入的為: " + CloneArray_ChangeStr.NulltoSpace(JTCust_Barcode.getText()));
				JTCust_Barcode.setText("");
			}
		}catch(Exception e){
			e.printStackTrace();
			exUtl.ExceptionNotify(e,"ISN_BarCode_Query_PEGA SVT_Query",this.getClass().toString(),VERSION);
		}
	}
	private Hashtable getDBDATA(String sql,String ds,UI_InitVO uiVO)throws Exception{
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
	public void cancel() {}
	
	@Override
	public void singleQuery(){}
	
    @Override
	public  Hashtable needValidateComponents(){
		return null;
	}
	@Override
	public void create() {}

	@Override
	public void modify() {}
	
	@Override
	public void save() {}
	
	@Override
	public void delete() {}

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