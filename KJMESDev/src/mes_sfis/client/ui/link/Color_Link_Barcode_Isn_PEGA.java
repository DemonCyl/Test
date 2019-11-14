/**
 * Copyright (c) 2008 Pegatron Inc. All Rights Reserved.
 * $Id: Color_Link_Barcode_Isn_PEGA.java,v 1.21 2018/04/10 15:31:49 Lucy6_Lu Exp $
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
import javax.swing.JComboBox;
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
import GotoQMBEc.com.pegatroncorp.www.SFISWebService.*;
import mes_sfis.client.util.SoundUtil;
//import sfis.route.to.*;


/**
 * The <code>ISN Link �X�f���X</code> class.
 *
 * @version     $Name:  $, $Id: Color_Link_Barcode_Isn_PEGA.java,v 1.21 2018/04/10 15:31:49 Lucy6_Lu Exp $
 * @author      <a href="mailto:william_tsai@Pegatroncorp.com">William Tsai</a>
 * @history      [>: General, +: New, #: Modification, -: Remove, ~: Bug]
 *          $Log: Color_Link_Barcode_Isn_PEGA.java,v $
 *          Revision 1.21  2018/04/10 15:31:49  Lucy6_Lu
 *          Rejected commit: Default
 *
 *          Revision 1.20  2018/04/10 15:08:15  Lucy6_Lu
 *          Rejected commit: Default
 *
 *          Revision 1.19  2017/12/29 01:42:01  Lucy6_Lu
 *          2017-12-29 test
 *
 *          Revision 1.18  2017/12/29 00:25:07  Lucy6_Lu
 *          2017-12-28 ���L101010
 *
 *          Revision 1.17  2017/12/28 07:52:36  Lucy6_Lu
 *          2017-12-28 QM7���I��QMB
 *
 *          Revision 1.16  2017/12/27 06:20:33  Lucy6_Lu
 *          no message
 *
 *          Revision 1.13  2017/12/27 05:49:09  Lucy6_Lu
 *          no message
 *
 *          Revision 1.12  2017/12/27 05:39:54  Lucy6_Lu
 *          no message
 *
 *          Revision 1.11  2017/12/26 09:09:32  Lucy6_Lu
 *          2017-12-26 �H�����ܭק�
 *
 *          Revision 1.10  2017/03/31 10:35:13  Jieyu_Fu
 *          no message
 *
 *          Revision 1.9  2017/03/31 10:15:36  Jieyu_Fu
 *          no message
 *
 *          Revision 1.8  2017/03/08 07:36:49  Jieyu_Fu
 *          no message
 *
 *          Revision 1.7  2017/03/08 07:34:05  Jieyu_Fu
 *          no message
 *
 *          Revision 1.6  2017/01/19 07:38:24  Jieyu_Fu
 *          no message
 *
 *          Revision 1.5  2017/01/19 07:08:02  Jieyu_Fu
 *          no message
 *
 *          Revision 1.4  2017/01/19 06:40:03  Jieyu_Fu
 *          no message
 *
 *          Revision 1.3  2017/01/10 05:38:48  Jieyu_Fu
 *          no message
 *
 *          Revision 1.2  2017/01/10 03:58:36  Jieyu_Fu
 *          no message
 *
 *          Revision 1.1  2017/01/10 03:52:24  Jieyu_Fu
 *          no message
 *
 *          Revision 1.1  2016/12/28 02:38:31  Jieyu_Fu
 *          no message
 *
 *
 */
public class Color_Link_Barcode_Isn_PEGA extends BasePanel{
	
	
    public static final String VERSION    = "$Name:  $, $Id: Color_Link_Barcode_Isn_PEGA.java,v 1.21 2018/04/10 15:31:49 Lucy6_Lu Exp $";
    public static final String COPYRIGHT  = "Copyright (c) 2008 PEGATRON Inc. All Rights Reserved.";
    
    public final static int _ISN_Query = 1;
    public final static int _BarCode_Query = 2;
    
    int _SVT_Query = 0;
	String InputText;
	Color_Link_Barcode_Isn_PEGA self;
	public Color_Link_Barcode_Isn_PEGA(UI_InitVO uiVO) {
		super(uiVO);
		init(); 
	}
	JLabel JLabel_ISN;
	JTextField JTFISN;
	private JComboBox JCBColorPick;
	private JButton JBSure;
	private JButton JBUnSure;
	private JLabel JLColorPick;
	private JLabel JLInput_TextBar;
	private JLabel JLInput_TextIsn;
	private JLabel JLLINKSUSS;;
	JTextArea JTAMessage;
    KeyListener _keyListener_SVT_Query;
	private String StrColorId;
	private String StrBarcodeResult;
	private String vBarcode;
	Hashtable Colht = new Hashtable();
	void init(){
		self=this;
		_SVT_Query = _BarCode_Query;
		self.setUILayout(null);
		JLabel_ISN = new JLabel("�п�J�X�f���X: ",JLabel.RIGHT);
		JLabel_ISN.setBounds(new Rectangle(10,45,150,25));
		add(JLabel_ISN,null);
		JTFISN = new JTextField();
		JTFISN.setBounds(new Rectangle(180,45,200,25));
		JTFISN.setEditable(false);
		add(JTFISN,null);
		
		JLabel JLISN_Barcode = new JLabel("�п��  �C��: ",JLabel.RIGHT);
		JLISN_Barcode.setBounds(10,75,150,25);
		JCBColorPick = new JComboBox();
        JCBColorPick.setBounds(180,75,200,25);
		add(JLISN_Barcode);
        add(JCBColorPick);
		ColorCheck();
		JLColorPick = new JLabel("");
		JLColorPick.setFont(new Font("����", Font.BOLD, 35));
		JLColorPick.setBounds(400,120,170,80);
		add(JLColorPick);
		JBSure = new JButton("��w�C��");
		JBSure.setBounds(80,120,130,80);
		JBUnSure = new JButton("�����C��");
		JBUnSure.setBounds(240,120,130,80);
		BtnSureListener BtSu = new BtnSureListener();
		JBSure.addActionListener(BtSu);
		BtnUnSureListener BtUnSu = new BtnUnSureListener();
		JBUnSure.addActionListener(BtUnSu);
		JBUnSure.setEnabled(false);
		add(JBSure);
		add(JBUnSure);
		
		JTAMessage = new JTextArea();
		JTAMessage.setBounds(new Rectangle(65,230,400,100));
		add(JTAMessage,null);
		JTAMessage.setEditable(false);
		
		JLInput_TextBar = new JLabel("");
		JLInput_TextBar.setFont(new Font("����", Font.BOLD, 15));
		JLInput_TextBar.setBounds(80,350,500,20);
		add(JLInput_TextBar,null);
		
		JLInput_TextIsn = new JLabel("");
		JLInput_TextIsn.setFont(new Font("����", Font.BOLD, 15));
		JLInput_TextIsn.setBounds(80,375,500,20);
		add(JLInput_TextIsn,null);
		
		JLLINKSUSS = new JLabel("");
		JLLINKSUSS.setFont(new Font("����", Font.BOLD, 35));
		JLLINKSUSS.setBounds(80,450,500,50);
		add(JLLINKSUSS,null);
		
		_keyListener_SVT_Query = new KeyListener() {
			public void keyPressed(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					SVT_Query(_SVT_Query);
				}
			}
			public void keyTyped(KeyEvent e) {}
		};
		JTFISN.addKeyListener(_keyListener_SVT_Query);
	}
	private void ColorCheck(){
		try {
			Vector arg = new Vector();
			BaseServletAgent bsa = new BaseServletAgent(uiVO);
			BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
			bvo.setPackageName("SFIS_COLOR_LINK_BCI_PEGA_PG");
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
			JOptionPane.showMessageDialog(null,"�п���C��");
			return;
		}
		StrColorId = CloneArray_ChangeStr.NulltoSpace(Colht.get(CloneArray_ChangeStr.NulltoSpace(JCBColorPick.getSelectedItem())));
		JLColorPick.setText(CloneArray_ChangeStr.NulltoSpace(JCBColorPick.getSelectedItem()));
		JCBColorPick.setEnabled(false);
		JTFISN.setEditable(true);
		JBSure.setEnabled(false);
		JBUnSure.setEnabled(true);
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
			JTFISN.setEditable(false);
			JBUnSure.setEnabled(false);
		}
	}
	public void SVT_Query(int Query_Type){
		setMessage("");
		if(Query_Type == _BarCode_Query){
			try{
				Vector arg = new Vector();
				arg.add(StrColorId);
				arg.add(JTFISN.getText());
				BaseServletAgent bsa = new BaseServletAgent(uiVO);
				BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
				bvo.setPackageName("SFIS_COLOR_LINK_BCI_PEGA_PG");
				bvo.setFunctionName("Query_Barcode_Info");	
				ResultVO rvo = bsa.doFunc(bvo);
				Vector result = rvo.getData();
				Hashtable row = (Hashtable)result.get(1);
				StrBarcodeResult = CloneArray_ChangeStr.NulltoSpace(row.get("BARCODERESULT"));
				if(StrBarcodeResult.equals("NB")){
					SoundUtil.PlayNo();
					setMessage("�ӥX�f���X���s�b");
					JTAMessage.setText("�ӥX�f���X���s�b");
					JOptionPane.showMessageDialog(null,"�ӥX�f���X���s�b");
					alertmess(Query_Type);

					return;
				}
				if(StrBarcodeResult.equals("LK")){
					SoundUtil.PlayNo();
					setMessage("���~�I�I�I�ӥX�f���X�wLINK");
					JTAMessage.setText("���~�I�I�I�ӥX�f���X�wLINK");
					JOptionPane.showMessageDialog(null,"���~�I�I�I�ӥX�f���X�wLINK");
					alertmess(Query_Type);
					System.out.println("���~�I�I�I");

					return;
				}
				if(StrBarcodeResult.equals("DF")){
					SoundUtil.PlayNo();
					setMessage("�C�⤣�@�P,�X�f���X�C��: " + CloneArray_ChangeStr.NulltoSpace(row.get("COLOR")));
					JTAMessage.setText("�C�⤣�@�P,�X�f���X�C��: " + CloneArray_ChangeStr.NulltoSpace(row.get("COLOR")));
					JOptionPane.showMessageDialog(null,"�C�⤣�@�P");
					alertmess(Query_Type);
					return;
				}
				if(StrBarcodeResult.equals("NS")){
					SoundUtil.PlayNo();
					setMessage("�ӥX�f���X�|���w�q Link ���I ");
					JTAMessage.setText("�ӥX�f���X�|���w�q Link ���I ");
					JOptionPane.showMessageDialog(null,"�ӥX�f���X�|���w�q Link ���I");
					alertmess(Query_Type);
					return;
				}
				System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXX------");
				JLabel_ISN.setText("�п�J  ISN  : ");
				System.out.println("XXXXXXXXXXX-----XXXXXXXXXXXXXXX------"+JLabel_ISN.getText());
				vBarcode = CloneArray_ChangeStr.NulltoSpace(JTFISN.getText());
				JBUnSure.setEnabled(false);
				alertmess(Query_Type);
				_SVT_Query = _ISN_Query;
			}catch(Exception e){	
				e.printStackTrace();
				exUtl.ExceptionNotify(e,"Link Color_Link_Barcode_Isn_PEGA SVT_Query",this.getClass().toString(),VERSION);
			}
		}
		if(Query_Type == _ISN_Query){
			try{
				String sql = "select t.step,t.item,t.grp from tp.mo_d t where t.isn = '"+JTFISN.getText()+"'";
				Hashtable ht = getDBDATA(sql,DataSourceType._SFIS_KAIJIA_STD,uiVO);
				if(ht == null){
					SoundUtil.PlayNo();
					setMessage("SFIS �d�L�� ISN");
					JTAMessage.setText("SFIS �d�L�� ISN");
					JOptionPane.showMessageDialog(null,"SFIS �d�L�� ISN");
					alertmess(Query_Type);
					return;
				}
				String vSTEP = ht.get("STEP").toString();
				String vGRP = (String)ht.get("GRP");
				/*if(!vSTEP.equals(StrBarcodeResult)){
					setMessage("�ëD Link ���I , ��e GRP : "+vGRP+" , �ƾڮw�w�q�� STEP : "+StrBarcodeResult);
					JTAMessage.setText("�ëD Link ���I , ��e GRP : "+vGRP+" , �ƾڮw�w�q�� STEP : "+StrBarcodeResult);
					alertmess(Query_Type);
					return;
				}*/
				int n = 3;
				String isQM7 = vSTEP.substring(vSTEP.length()-n,vSTEP.length());
				if(!isQM7.equals("QMB")){
					SoundUtil.PlayNo();
					setMessage("�DQMB���I");
					JTAMessage.setText("�DQMB���I");
					JOptionPane.showMessageDialog(null,"S�DQMB���I");
					alertmess(Query_Type);
					return;
				}
				String vITEM = ht.get("ITEM").toString();
				Vector arg = new Vector();
				arg.add(vITEM);
				arg.add(StrColorId);
				arg.add(vBarcode);
				arg.add(JTFISN.getText());
				BaseServletAgent bsa = new BaseServletAgent(uiVO);
				BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
				bvo.setPackageName("SFIS_COLOR_LINK_BCI_PEGA_PG");
				bvo.setFunctionName("Link_Isn_Info");
				ResultVO rvo = bsa.doFunc(bvo);
				Vector result = rvo.getData();
				Hashtable row = (Hashtable)result.get(1);
				String isnlink = CloneArray_ChangeStr.NulltoSpace(row.get("ISLINK"));
				if(isnlink.equals("ILK")){
					SoundUtil.PlayNo();
					setMessage("��ISN�w�gLINK�X�f���X");
					JTAMessage.setText("��ISN�w�gLINK�X�f���X");
					JOptionPane.showMessageDialog(null,"��ISN�w�gLINK�X�f���X");
					alertmess(Query_Type);
					return;
				}
				if(isnlink.equals("ILKC")){
					SoundUtil.PlayNo();
					setMessage("��ISN�w�gLINK�C��,�Х��Ѱ��C��LINK");
					JTAMessage.setText("��ISN�w�gLINK�C��,�Х��Ѱ��C��LINK");
					JOptionPane.showMessageDialog(null,"��ISN�w�gLINK�C��,�Х��Ѱ��C��LINK");
					alertmess(Query_Type);
					return;
				}
				String StrOutResult =  CloneArray_ChangeStr.NulltoSpace(row.get("STEP"));
				/*if(StrOutResult.equals("NON")){
					setMessage("��ISN�|���w�q Link ���I ");
					JTAMessage.setText("��ISN�|���w�q Link ���I ");
					alertmess(Query_Type);
					return;
				}
				String part_no = (String)ht.get("PART_NO");
			  	if(part_no != null){
			  		setMessage("�X�f���X�Ƹ�("+part_no+")�PISN�Ƹ����@�P("+vITEM+") ");
					JTAMessage.setText("�X�f���X�Ƹ�("+part_no+")�PISN�Ƹ����@�P("+vITEM+") ");
					alertmess(Query_Type);
					return;
			  	}*/
				toUpdateStep();
				JLabel_ISN.setText("�п�J�X�f���X: ");
				JTAMessage.setText("");
				JLLINKSUSS.setText(CloneArray_ChangeStr.NulltoSpace(row.get("LINKOK")));
				JBUnSure.setEnabled(true);
				alertmess(Query_Type);
				_SVT_Query = _BarCode_Query;
			}catch(Exception e){	
				e.printStackTrace();
				exUtl.ExceptionNotify(e,"Link Color_Link_Barcode_Isn_PEGA SVT_Query",this.getClass().toString(),VERSION);
			}
		}
	}
	public boolean toUpdateStep(){
    	
    	System.out.println("############  into GoToPaint ....");
    	
    	try{
			String sql = "select t.oppass from tp.op t where t.op = 'TSP_NLSPAD'";
			Hashtable ht = getDBDATA(sql,DataSourceType._SFIS_KAIJIA_STD,uiVO);
			String pass = CloneArray_ChangeStr.NulltoSpace(ht.get("OPPASS"));
			/*TestXYStub ww = new TestXYStub();
			TestXYStub.Testx mm = new TestXYStub.Testx();
			mm.setB("123");
			TestXYStub.Testy nn = new TestXYStub.Testy();
			
			ww.testx(mm);
			ww.testy(nn);*/
			System.out.println("##123##");
			/*SFISTSPWebServiceStub ss = new SFISTSPWebServiceStub();
			SFISTSPWebServiceStub.WTSP_LOGINOUT swg = new SFISTSPWebServiceStub.WTSP_LOGINOUT();
			swg.setOp("TSP_NLSPAD");
			swg.setPassword("1");
			swg.setProgramId("TSP_NLSPAD");
			swg.setProgramPassword(pass);
			swg.setStatus(2);
			swg.setStatus(1);
			swg.setTSP("LINK");
			swg.setDevice("101010");
			ss.wTSP_LOGINOUT(swg);
			
			SFISTSPWebServiceStub.WTSP_CHKROUTE swc = new SFISTSPWebServiceStub.WTSP_CHKROUTE();
			swc.setCheckData("1");
			swc.setCheckFlag("1");
			swc.setDevice("101010");
			swc.setISN(JTFISN.getText().trim());
			swc.setProgramId("TSP_NLSPAD");
			swc.setProgramPassword(pass);
			swc.setType(1);
			ss.wTSP_CHKROUTE(swc);
			
			SFISTSPWebServiceStub.WTSP_RESULT_MASSDATA swt = new SFISTSPWebServiceStub.WTSP_RESULT_MASSDATA();
			System.out.println("##456##");
			swt.setProgramId("TSP_NLSPAD");
			swt.setProgramPassword(pass);
			System.out.println(pass);
			swt.setISN(JTFISN.getText().trim());
			swt.setError(null);
			System.out.println("###111");
			swt.setDevice("101010");
			swt.setTSP("LINK");
			System.out.println("###222");
			swt.setAoiloc("0");
			swt.setCPKFlag("1");
			swt.setData("1");
			swt.setData2("1");
			swt.setData3("1");
			swt.setData4("1");
			swt.setData5("1");
			swt.setData6("1");
			swt.setData7("1");
			swt.setData8("1");
			swt.setStatus(1);
			SFISTSPWebServiceStub.WTSP_RESULT_MASSDATAResponse re = ss.wTSP_RESULT_MASSDATA(swt);
			System.out.println(re.getWTSP_RESULT_MASSDATAResult());
			
			swg.setStatus(2);
			ss.wTSP_LOGINOUT(swg);*/
			/*javax.xml.rpc.ServiceException x = null;
			System.out.println("789654321");
			org.apache.axis.client.Service y = null;
			System.out.println("123456789");*/
			/*
			System.out.println(System.getProperty("user.dir"));
			GotoQMBEc.com.pegatroncorp.www.SFISWebService.SFISTSPWebServiceSoapProxy ab = new GotoQMBEc.com.pegatroncorp.www.SFISWebService.SFISTSPWebServiceSoapProxy();
			ab.WTSP_LOGINOUT("TSP_NLSPAD", pass, "TSP_NLSPAD", pass, "101010", "LINK", 1);
			ab.WTSP_CHKROUTE("TSP_NLSPAD", pass, JTFISN.getText().trim(), "101010", "1", "1", 1);
			ab.WTSP_RESULT_MASSDATA("TSP_NLSPAD",pass,JTFISN.getText().trim(),null,"101010","LINK","1", 1,"1","0","1","1","1","1","1","1","1");
			ab.WTSP_LOGINOUT("TSP_NLSPAD", pass, "TSP_NLSPAD", pass, "101010", "LINK", 2);
			System.out.println(ab.getEndpoint());
			*/
			
		}catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}
    	return true;
    }
	private void alertmess(int Query_Type){
		if(Query_Type == _BarCode_Query){
			JLInput_TextBar.setText("�X�f���X: " + CloneArray_ChangeStr.NulltoSpace(JTFISN.getText()));
			JLInput_TextIsn.setText("");
			JLLINKSUSS.setText("");
			JTFISN.setText("");
		}
		if(Query_Type == _ISN_Query){
			JLInput_TextIsn.setText("Isn: " + CloneArray_ChangeStr.NulltoSpace(JTFISN.getText()));
			JTFISN.setText("");
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
		System.out.println("-------------------size"+result.size());
		
		if(result.size() == 2){			
			ht = (Hashtable)result.elementAt(1);
			System.out.println("++++++++++++++size"+result.size());
		}
		System.out.println("-------------------"+ht.get("STEP").toString());
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
