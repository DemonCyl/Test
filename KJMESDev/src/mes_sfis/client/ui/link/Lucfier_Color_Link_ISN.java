package mes_sfis.client.ui.link;

import java.awt.Rectangle;
import java.awt.event.*;
import java.awt.Font;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JComboBox;

import base.client.util.component.P_Component_Meta;
import base.client.util.CloneArray_ChangeStr;

import base.client.ui.BasePanel;
import base.enums.DataSourceType;
import base.vo.ResultVO;
import base.vo.BaseVO;
import base.client.util.BaseServletAgent;
import base.enums.CommandName;
import base.vo.UI_InitVO;
//import sfis.route.to.*;



public class Lucfier_Color_Link_ISN extends BasePanel{


	public static final String VERSION    = "$Name:  $, $Id: Lucfier_Color_Link_ISN.java,v 1.4 2018/01/22 07:01:53 Lucy6_Lu Exp $";
	public static final String COPYRIGHT  = "Copyright (c) 2008 PEGATRON Inc. All Rights Reserved.";

	public final static int _ISN_Query = 1;
	public final static int _BarCode_Query = 2;

	int _SVT_Query = 0;
	String InputText;
	Lucfier_Color_Link_ISN self;
	public Lucfier_Color_Link_ISN(UI_InitVO uiVO) {
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
		JLabel_ISN = new JLabel("請輸入出貨條碼: ",JLabel.RIGHT);
		JLabel_ISN.setBounds(new Rectangle(10,45,150,25));
		add(JLabel_ISN,null);
		JTFISN = new JTextField();
		JTFISN.setBounds(new Rectangle(180,45,200,25));
		JTFISN.setEditable(false);
		add(JTFISN,null);

		JLabel JLISN_Barcode = new JLabel("請選擇  顏色: ",JLabel.RIGHT);
		JLISN_Barcode.setBounds(10,75,150,25);
		JCBColorPick = new JComboBox();
		JCBColorPick.setBounds(180,75,200,25);
		add(JLISN_Barcode);
		add(JCBColorPick);
		ColorCheck();
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

		JTAMessage = new JTextArea();
		JTAMessage.setBounds(new Rectangle(65,230,400,100));
		add(JTAMessage,null);
		JTAMessage.setEditable(false);

		JLInput_TextBar = new JLabel("");
		JLInput_TextBar.setFont(new Font("宋體", Font.BOLD, 15));
		JLInput_TextBar.setBounds(80,350,500,20);
		add(JLInput_TextBar,null);

		JLInput_TextIsn = new JLabel("");
		JLInput_TextIsn.setFont(new Font("宋體", Font.BOLD, 15));
		JLInput_TextIsn.setBounds(80,375,500,20);
		add(JLInput_TextIsn,null);

		JLLINKSUSS = new JLabel("");
		JLLINKSUSS.setFont(new Font("宋體", Font.BOLD, 35));
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
			JOptionPane.showMessageDialog(null,"請選擇顏色");
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
					setMessage("該出貨條碼不存在");
					JTAMessage.setText("該出貨條碼不存在");
					JOptionPane.showMessageDialog(null,"該出貨條碼不存在");
					alertmess(Query_Type);
					return;
				}
				if(StrBarcodeResult.equals("LK")){
					setMessage("該出貨條碼已LINK");
					JTAMessage.setText("該出貨條碼已LINK");
					JOptionPane.showMessageDialog(null,"該出貨條碼已LINK");
					alertmess(Query_Type);
					System.out.println("XXX+++++++++++++++++++------");
					return;
				}
				if(StrBarcodeResult.equals("DF")){
					setMessage("顏色不一致,出貨條碼顏色: " + CloneArray_ChangeStr.NulltoSpace(row.get("COLOR")));
					JTAMessage.setText("顏色不一致,出貨條碼顏色: " + CloneArray_ChangeStr.NulltoSpace(row.get("COLOR")));
					JOptionPane.showMessageDialog(null,"顏色不一致");
					alertmess(Query_Type);
					return;
				}
				if(StrBarcodeResult.equals("NS")){
					setMessage("該出貨條碼尚未定義 Link 站點 ");
					JTAMessage.setText("該出貨條碼尚未定義 Link 站點 ");
					JOptionPane.showMessageDialog(null,"該出貨條碼尚未定義 Link 站點");
					alertmess(Query_Type);
					return;
				}
				System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXX------");
				JLabel_ISN.setText("請輸入  ISN  : ");
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
					setMessage("SFIS 查無此 ISN");
					JTAMessage.setText("SFIS 查無此 ISN");
					JOptionPane.showMessageDialog(null,"SFIS 查無此 ISN");
					alertmess(Query_Type);
					return;
				}
				String vSTEP = ht.get("STEP").toString();
				String vGRP = (String)ht.get("GRP");
				/*if(!vSTEP.equals(StrBarcodeResult)){
					setMessage("並非 Link 站點 , 當前 GRP : "+vGRP+" , 數據庫定義的 STEP : "+StrBarcodeResult);
					JTAMessage.setText("並非 Link 站點 , 當前 GRP : "+vGRP+" , 數據庫定義的 STEP : "+StrBarcodeResult);
					alertmess(Query_Type);
					return;
				}*/
				int n = 3;
				String isQM7 = vSTEP.substring(vSTEP.length()-n,vSTEP.length());
				if(!isQM7.equals("QM7")&&!isQM7.equals("QMB")){
					setMessage("非QM7站點");
					JTAMessage.setText("非QM7站點");
					JOptionPane.showMessageDialog(null,"該ISN上站未在QM7站,目前在"+isQM7);
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
					setMessage("該ISN已經LINK出貨條碼");
					JTAMessage.setText("該ISN已經LINK出貨條碼");
					JOptionPane.showMessageDialog(null,"該ISN已經LINK出貨條碼");
					alertmess(Query_Type);
					return;
				}
				if(isnlink.equals("ILKC")){
					setMessage("該ISN已經LINK顏色,請先解除顏色LINK");
					JTAMessage.setText("該ISN已經LINK顏色,請先解除顏色LINK");
					JOptionPane.showMessageDialog(null,"該ISN已經LINK顏色,請先解除顏色LINK");
					alertmess(Query_Type);
					return;
				}
				if(isQM7.equals("QMB")){
					JOptionPane.showConfirmDialog(null,"注意該ISN過了組立站，請相關負責人查詢原因并改善");
					return;
				}
				toUpdateStep();
				JLabel_ISN.setText("請輸入出貨條碼: ");
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
			/*System.out.println("##123##");
			SFISTSPWebServiceStub ss = new SFISTSPWebServiceStub();
			SFISTSPWebServiceStub.WTSP_LOGINOUT swg = new SFISTSPWebServiceStub.WTSP_LOGINOUT();
			swg.setOp("TSP_NLSPAD");
			swg.setPassword("1");
			swg.setProgramId("TSP_NLSPAD");
			swg.setProgramPassword(pass);
			swg.setStatus(2);
			swg.setStatus(1);
			swg.setTSP("LINK");
			swg.setDevice("101011");
			ss.wTSP_LOGINOUT(swg);
			
			SFISTSPWebServiceStub.WTSP_CHKROUTE swc = new SFISTSPWebServiceStub.WTSP_CHKROUTE();
			swc.setCheckData("1");
			swc.setCheckFlag("1");
			swc.setDevice("101011");
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
			swt.setDevice("101011");
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

			System.out.println(System.getProperty("user.dir"));
			GotoQMBEc.com.pegatroncorp.www.SFISWebService.SFISTSPWebServiceSoapProxy ab = new GotoQMBEc.com.pegatroncorp.www.SFISWebService.SFISTSPWebServiceSoapProxy();
			ab.WTSP_LOGINOUT("TSP_NLSPAD", pass, "TSP_NLSPAD", pass, "101011", "LINK", 1);
			ab.WTSP_CHKROUTE("TSP_NLSPAD", pass, JTFISN.getText().trim(), "101011", "1", "1", 1);
			ab.WTSP_RESULT_MASSDATA("TSP_NLSPAD",pass,JTFISN.getText().trim(),null,"101011","LINK","1", 1,"1","0","1","1","1","1","1","1","1");
			ab.WTSP_LOGINOUT("TSP_NLSPAD", pass, "TSP_NLSPAD", pass, "101011", "LINK", 2);
			System.out.println(ab.getEndpoint());

		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	private void alertmess(int Query_Type){
		if(Query_Type == _BarCode_Query){
			JLInput_TextBar.setText("出貨條碼: " + CloneArray_ChangeStr.NulltoSpace(JTFISN.getText()));
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
