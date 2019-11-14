/**
 * Copyright (c) 2008 Pegatron Inc. All Rights Reserved.
 * $Id: ISN_Color_DeLink_PEGA.java,v 1.1 2016/12/15 10:50:04 Jieyu_Fu Exp $
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
 * @version     $Name: 1.3 $, $Id: ISN_Color_DeLink_PEGA.java,v 1.1 2016/12/15 10:50:04 Jieyu_Fu Exp $
 * @author      <a href="mailto:william_tsai@asus.com.tw">William Tsai</a>
 * @history      [>: General, +: New, #: Modification, -: Remove, ~: Bug]
 *          $Log: ISN_Color_DeLink_PEGA.java,v $
 *          Revision 1.1  2016/12/15 10:50:04  Jieyu_Fu
 *          no message
 *
 *
 *
 */
public class ISN_Color_DeLink_PEGA extends BasePanel {
    public static final String VERSION    = "$Name:  $, $Id: ISN_Color_DeLink_PEGA.java,v 1.1 2016/12/15 10:50:04 Jieyu_Fu Exp $";
    public static final String COPYRIGHT  = "Copyright (c) 2008 PEGATRON Inc. All Rights Reserved.";

	private static final long serialVersionUID = -6120355920482734660L;

	private ISN_Color_DeLink_PEGA self;
	
	private JTextField JTISN_Color;
	private JTextArea JTAMessage;
	KeyListener _keyListener_SVT_Query;
	private JLabel JLInput_Text;
	public ISN_Color_DeLink_PEGA(UI_InitVO uiVO) {
		super(uiVO);
		init();
	}
	
	private void init(){
		self = this;
		self.setUILayout(null);
		JLabel JLISN_Color = new JLabel("請輸入ISN:");
		JLISN_Color.setBounds(10,50,150,25);
		JTISN_Color = new JTextField(40);
        JTISN_Color.setBounds(180,50,200,25);
		add(JLISN_Color);
        add(JTISN_Color);
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
		JTISN_Color.addKeyListener(_keyListener_SVT_Query);
		add(JTAMessage,null);
		JLInput_Text = new JLabel("");
		JLInput_Text.setFont(new Font("", Font.BOLD, 16));
		JLInput_Text.setBounds(new Rectangle(120,190,400,200));
		add(JLInput_Text,null);
		setStatus(new int[]{ToolBarItem.Exit});
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
	public void SVT_Query(){
		try{
			String sql = "select t.isn from tp.isn t where t.isn = '" + JTISN_Color.getText() + "'";
			Hashtable ht = getDBDATA(sql,DataSourceType._SFIS_KAIJIA_STD,uiVO);
			if(ht == null){
				JTAMessage.setText("該ISN不存在 !");
				JLInput_Text.setText("輸入的為: " + CloneArray_ChangeStr.NulltoSpace(JTISN_Color.getText()));
				JTISN_Color.setText("");
				return;
			}
			Vector arg = new Vector();
			arg.add(JTISN_Color.getText());
			BaseServletAgent bsa = new BaseServletAgent(uiVO);
			BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
			bvo.setPackageName("SFIS_ISN_COLOR_DLK_PEGA_PG");
			bvo.setFunctionName("ISN_DELINK_COLOR");	
			ResultVO rvo = bsa.doFunc(bvo);
			Vector result = rvo.getData();
			if(result.size()>1){
				Hashtable row = (Hashtable)result.get(1);
				JTAMessage.setText(CloneArray_ChangeStr.NulltoSpace(row.get("ERRMISS")));
				JLInput_Text.setText("輸入的為: " + CloneArray_ChangeStr.NulltoSpace(JTISN_Color.getText()));
				JTISN_Color.setText("");
			}else{
				JTAMessage.setText("此ISN尚未綁定");
			}
		}catch(Exception e){
			e.printStackTrace();
			exUtl.ExceptionNotify(e,"ISN_Color_DeLink_PEGA SVT_Query",this.getClass().toString(),VERSION);
		}
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