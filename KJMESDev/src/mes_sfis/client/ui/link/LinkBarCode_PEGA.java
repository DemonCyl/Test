/**
 * Copyright (c) 2008 Pegatron Inc. All Rights Reserved.
 * $Id: LinkBarCode_PEGA.java,v 1.9 2016/12/28 02:38:31 Jieyu_Fu Exp $
 */

package mes_sfis.client.ui.link;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.*;
import java.awt.Font;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.Hashtable;
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

import base.client.util.component.PMIDComboBox;
import base.client.util.component.P_Component_Meta;
import base.client.util.MessageUtil;
import base.client.util.StringUtil;
import base.client.util.CloneArray_ChangeStr;
import base.client.enums.ToolBarItem;

import com.klg.jclass.table.data.JCEditableVectorDataSource;
import base.client.ui.BasePanel;
import base.enums.DataSourceType;
import base.vo.ResultVO;
import base.vo.BaseVO;
import base.client.util.BaseServletAgent;
import base.enums.CommandName;
import base.vo.UI_InitVO;

/**
 * The <code>ISN Link �X�f���X</code> class.
 *
 * @version     $Name:  $, $Id: LinkBarCode_PEGA.java,v 1.9 2016/12/28 02:38:31 Jieyu_Fu Exp $
 * @author      <a href="mailto:william_tsai@Pegatroncorp.com">William Tsai</a>
 * @history      [>: General, +: New, #: Modification, -: Remove, ~: Bug]
 *          $Log: LinkBarCode_PEGA.java,v $
 *          Revision 1.9  2016/12/28 02:38:31  Jieyu_Fu
 *          no message
 *
 *          Revision 1.8  2016/12/16 03:53:06  Jieyu_Fu
 *          no message
 *
 *          Revision 1.7  2016/12/15 10:48:52  Jieyu_Fu
 *          no message
 *
 *          Revision 1.6  2016/12/12 11:25:08  William_Tsai
 *          no message
 *
 *          Revision 1.5  2016/11/30 05:38:51  William_Tsai
 *          no message
 *
 *          Revision 1.4  2016/11/29 07:40:12  William_Tsai
 *          no message
 *
 *          Revision 1.3  2016/11/23 01:02:42  Jieyu_Fu
 *          no message
 *
 *          Revision 1.2  2016/11/17 10:30:06  William_Tsai
 *          no message
 *
 *          Revision 1.1  2016/11/16 12:58:13  William_Tsai
 *          no message
 *
 *          Revision 1.1  2016/11/09 09:54:18  William_Tsai
 *          no message
 *
 *
 */
public class LinkBarCode_PEGA extends BasePanel{
	
	
    public static final String VERSION    = "$Name:  $, $Id: LinkBarCode_PEGA.java,v 1.9 2016/12/28 02:38:31 Jieyu_Fu Exp $";
    public static final String COPYRIGHT  = "Copyright (c) 2008 PEGATRON Inc. All Rights Reserved.";
    
    public final static int _ISN_Query = 1;
    public final static int _BarCode_Query = 2;
    
    int _SVT_Query = 0;
    String vISN;
    String vSTEP;
    String vITEM;
    String vGRP;
	String vIsNeedColor = "";
	String InputText;
	LinkBarCode_PEGA self;
	public LinkBarCode_PEGA(UI_InitVO uiVO) {
		super(uiVO);
		init(); 
	}
	JLabel jLabel_ISN;
	JLabel jLabel_STATUS;
	JLabel jLabel_SUCCESS;
	JTextField jtfISN;
	
	JTextArea jta;
    KeyListener _keyListener_SVT_Query;
	
	void init(){
		self=this;
		_SVT_Query = _ISN_Query;
		self.setUILayout(null);
		
		jLabel_ISN = new JLabel("�п�J  ISN  : ",JLabel.RIGHT);
		jLabel_ISN.setBounds(new Rectangle(10,45,180,25));
		add(jLabel_ISN,null);
		
		jtfISN = new JTextField();
		jtfISN.setBounds(new Rectangle(200,45,250,25));
		add(jtfISN,null);
		
		jta = new JTextArea();
		jta.setBounds(new Rectangle(120,80,400,100));
		add(jta,null);
		jta.setEditable(false);
		
		jLabel_STATUS = new JLabel("");
		jLabel_STATUS.setFont(new Font("����", Font.BOLD, 100));
		jLabel_STATUS.setBounds(new Rectangle(80,190,500,150));
		add(jLabel_STATUS,null);
		
		jLabel_SUCCESS = new JLabel("");
		jLabel_SUCCESS.setFont(new Font("����", Font.BOLD, 50));
		jLabel_SUCCESS.setBounds(new Rectangle(80,350,500,150));
		add(jLabel_SUCCESS,null);
		
		
		_keyListener_SVT_Query = new KeyListener() {
			public void keyPressed(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					SVT_Query(_SVT_Query);
				}
			}
			public void keyTyped(KeyEvent e) {}
		};
		jtfISN.addKeyListener(_keyListener_SVT_Query);
	}
	
	public void SVT_Query(int Query_Type){
		setMessage("");
		jta.setText("");
		jLabel_STATUS.setText("");
		jLabel_SUCCESS.setText("");
		if(Query_Type == _ISN_Query){
			vISN = null;
			vSTEP = null;
			vITEM = null;
			vGRP = null;
			try {
				String sql = "select t.isn,t.step,t.item,t.grp from tp.mo_d t where t.isn = '"+jtfISN.getText()+"'";
				appendText("ISN : "+jtfISN.getText()+"\n");
				jtfISN.setText("");
				Hashtable ht = getDBDATA(sql,DataSourceType._SFIS_KAIJIA_STD,uiVO);
				if(ht == null){
					setMessage("SFIS �d�L�� ISN");
					appendText("MSG : SFIS �d�L�� ISN \n");
					return;
				}
				vISN = ht.get("ISN").toString();
				vSTEP = ht.get("STEP").toString();
				vITEM = ht.get("ITEM").toString();
				vGRP = (String)ht.get("GRP");
			} catch (Exception e) {
				setMessage("SFIS ���A���s�u���`");
				appendText("MSG : SFIS ���A���s�u���` \n");
				e.printStackTrace();
			}
			try{
				Vector arg = new Vector();
				arg.add(vISN);
				arg.add(vITEM);
				BaseServletAgent bsa = new BaseServletAgent(uiVO);
				BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
				bvo.setPackageName("SFIS_LINK_ISN_PEGA_PG");
				bvo.setFunctionName("Query_ISN");
				ResultVO rvo = bsa.doFunc(bvo);
				Vector result = rvo.getData();
				if(result.size()>1){
					Hashtable htisn = (Hashtable)result.elementAt(1);
					if(htisn.get("MSG").toString().equals("CL")){
						appendText("��ISN�|��LINK�C��,�Х�LINK�C�� \n");
						setMessage("��ISN�|��LINK�C��,�Х�LINK�C��");
						return;
					}
					if(htisn.get("MSG").toString().equals("LN")){
						setMessage("ISN �w�g Link");
						appendText("MSG : ISN �w�g Link \n");
						return;
					}
					vIsNeedColor = htisn.get("MSG").toString();	
				}
				arg = new Vector();
				arg.add(vITEM);
				bsa = new BaseServletAgent(uiVO);
				bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
				bvo.setPackageName("SFIS_LINK_ISN_PEGA_PG");
				bvo.setFunctionName("Query_STEP");
				rvo = bsa.doFunc(bvo);
				result = rvo.getData();
				System.out.println("result : "+result);
				Hashtable h = (Hashtable)result.elementAt(1);
				String aSTEP = (String)h.get("STEP");
				if(aSTEP.equals("NON")){
					setMessage("�Ƹ� : "+vITEM+" �|���w�q Link ���I ");
					appendText("MSG : �Ƹ� : "+vITEM+" �|���w�q Link ���I\n");
					return;
				}
				if(!aSTEP.equals(vSTEP)){
					setMessage("�ëD Link ���I , ��e GRP : "+vGRP+" , �ƾڮw�w�q�� STEP : "+aSTEP);
					appendText("MSG : �ëD Link ���I , ��e GRP : "+vGRP+" , �ƾڮw�w�q�� STEP : "+aSTEP+"\n");
					return;
				}
				
				jLabel_ISN.setText("�п�J �X �f �� �X :");
				_SVT_Query = _BarCode_Query;
			} catch (Exception e) {
				setMessage("MES1 ���A���s�u���`");
				appendText("MSG : MES1 ���A���s�u���` \n");
				e.printStackTrace();
			}
		}
		if(Query_Type == _BarCode_Query){
			try {
				Vector arg = new Vector();
				arg.add(vISN);
				arg.add(jtfISN.getText());
				arg.add(vITEM);
				arg.add(vIsNeedColor);
				appendText("�X�f���X : "+jtfISN.getText()+"\n");
				InputText = jtfISN.getText();
				jtfISN.setText("");
				BaseServletAgent bsa = new BaseServletAgent(uiVO);
				BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
				bvo.setPackageName("SFIS_LINK_ISN_PEGA_PG");
				bvo.setFunctionName("LINK_ISN_INFO");
				ResultVO rvo = bsa.doFunc(bvo);
			  	Vector result = rvo.getData();
			  	Hashtable ht = (Hashtable)result.elementAt(1);
			  	String re = ht.get("RES").toString();
				if(re.equals("DF")){
					appendText("�C�⤣�@�P,LINK���� \n");
					setMessage("�C�⤣�@�P,LINK����");
					return;
				}
			  	String part_no = (String)ht.get("PART_NO");
			  	if(part_no != null){
			  		setMessage("�X�f���X�Ƹ�("+part_no+")�PISN�Ƹ����@�P("+vITEM+") ");
			  		appendText("MSG : �X�f���X�Ƹ�("+part_no+")\n�PISN�Ƹ����@�P("+vITEM+") \n");
					return;
			  	}
			  	if(re.equals("NG")){
			  		String ISN = (String)ht.get("ISN");
			  		if( ISN != null){
			  			setMessage("�@�����X�w�Q Link ");
			  			appendText("MSG : �@�����X�w�Q Link  \n");
			  		}else{
			  			setMessage("�X�f���X���s�b");
			  			appendText("MSG : �X�f���X���s�b \n");
			  		}
					return;
			  	}
				jLabel_ISN.setText("�п�J  ISN  : ");
				jLabel_STATUS.setText("Link" + CloneArray_ChangeStr.NulltoSpace(ht.get("COLOR")));
				jLabel_SUCCESS.setText(InputText);
				_SVT_Query = _ISN_Query;
			} catch (Exception e) {
				setMessage("MES2 ���A���s�u���`");
				appendText("MSG : MES2 ���A���s�u���` \n");
				e.printStackTrace();
			}
		}
	}
	
	
	private void appendText(String s){
		String a = jta.getText();
		a=s+a;
		jta.setText(a);
		
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
	
	public void singleQuery() {
	}
	
	public void cancel() {
	}
	
	public void print() {
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
