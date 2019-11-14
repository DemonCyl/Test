/**
 * Copyright (c) 2008 Pegatron Inc. All Rights Reserved.
 * $Id: LabelPrintBarCode_PEGA.java,v 1.6 2017/11/21 11:29:01 Lucy6_Lu Exp $
 */

package mes_sfis.client.ui.barcode;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

/**
 * The <code>標籤打印範例</code> class.
 *
 * @version     $Name:  $, $Id: LabelPrintBarCode_PEGA.java,v 1.6 2017/11/21 11:29:01 Lucy6_Lu Exp $
 * @author      <a href="mailto:william_tsai@Pegatroncorp.com">William Tsai</a>
 * @history      [>: General, +: New, #: Modification, -: Remove, ~: Bug]
 *          $Log: LabelPrintBarCode_PEGA.java,v $
 *          Revision 1.6  2017/11/21 11:29:01  Lucy6_Lu
 *          no message
 *
 *          Revision 1.5  2017/11/21 11:14:20  Lucy6_Lu
 *          no message
 *
 *          Revision 1.4  2017/11/21 11:04:06  Lucy6_Lu
 *          no message
 *
 *          Revision 1.3  2017/11/21 10:55:37  Lucy6_Lu
 *          no message
 *
 *          Revision 1.2  2016/11/22 07:31:56  Jieyu_Fu
 *          no message
 *
 *          Revision 1.1  2016/11/09 09:54:18  William_Tsai
 *          no message
 *
 *          Revision 1.3  2016/11/09 02:02:55  William_Tsai
 *          no message
 *
 *          Revision 1.2  2016/11/08 07:30:15  William_Tsai
 *          no message
 *
 *          Revision 1.1  2016/10/30 06:41:34  William_Tsai
 *          no message
 *
 *          Revision 1.2  2016/10/30 06:23:45  William
 *          no message
 *
 *          Revision 1.1  2016/10/29 05:50:54  William
 *          no message
 *
 *
 *
 */
public class LabelPrintBarCode_PEGA extends BasePanel{
	
	
    public static final String VERSION    = "$Name:  $, $Id: LabelPrintBarCode_PEGA.java,v 1.6 2017/11/21 11:29:01 Lucy6_Lu Exp $";
    public static final String COPYRIGHT  = "Copyright (c) 2008 PEGATRON Inc. All Rights Reserved.";
    
	String vCUST_PART_NO;
	String vBATCH_NO;
	String vQTY;
	String vPartNo;
	String vPartName;
    
	LabelPrintBarCode_PEGA self;
	public LabelPrintBarCode_PEGA(UI_InitVO uiVO) {
		super(uiVO);
		init(); 
	}
	JTextField jtfMO,jMPN,jCPN;
	JTextField jtfQTY;
	JTextField jtfLabelQTY;
	JCheckBox jcboxPrint;
	JTextArea jta;
	
	
	void init(){
		self=this;
		self.setUILayout(null);
		
		JLabel jLabel_MO = new JLabel("請輸入工單號 : ",JLabel.RIGHT);
		jLabel_MO.setBounds(new Rectangle(10,45,100,25));
		add(jLabel_MO,null);
		
		jtfMO = new JTextField();
		jtfMO.setBounds(new Rectangle(120,45,250,25));
		add(jtfMO,null);
		
		
		JLabel JLMPN = new JLabel("場內料號: ",JLabel.RIGHT);
		JLMPN.setBounds(new Rectangle(10,80,100,25));
		add(JLMPN,null);
		jMPN = new JTextField();
		jMPN.setBounds(new Rectangle(120,80,150,25));
		jMPN.setVisible(false);
		add(jMPN,null);
		
		JLabel JLCPN = new JLabel("客戶料號: ",JLabel.RIGHT);
		JLCPN.setBounds(new Rectangle(280,80,100,25));
		add(JLCPN,null);
		jCPN = new JTextField();
		jCPN.setBounds(new Rectangle(390,80,150,25));
		add(jCPN,null);
		
		
		JLabel jLabel_QTY = new JLabel("QTY : ",JLabel.RIGHT);
		jLabel_QTY.setBounds(new Rectangle(10,115,100,25));
		add(jLabel_QTY,null);
		
		jtfQTY = new JTextField();
		jtfQTY.setBounds(new Rectangle(120,115,250,25));
		add(jtfQTY,null);
		
		
		JLabel jLabel_LabelQTY = new JLabel("打印張數 : ",JLabel.RIGHT);
		jLabel_LabelQTY.setBounds(new Rectangle(10,150,100,25));
		add(jLabel_LabelQTY,null);
		
		jtfLabelQTY = new JTextField();
		jtfLabelQTY.setText("1");
		jtfLabelQTY.setBounds(new Rectangle(120,150,250,25));
		add(jtfLabelQTY,null);
		
		jcboxPrint = new JCheckBox("連線打印機");
		jcboxPrint.setBounds(new Rectangle(400,150,100,25));
		add(jcboxPrint,null);
		jcboxPrint.setSelected(true);
		
		jta = new JTextArea();
		jta.setBounds(new Rectangle(120,185,400,200));
		add(jta,null);
		jta.setEditable(false);
		
		setStatus(new int[]{ToolBarItem.SingleQuery,ToolBarItem.Cancel,ToolBarItem.Exit});
	}
	
	
	public void singleQuery() {
		String MO = jtfMO.getText().trim();
		jta.setText("");
		if(MO.equals("")){
			setMessage("請輸入工單號!");
			return;
		}
		
		try {
			String sql = "select i.ima01,i.ima02 from sfb_file t , ima_file i where t.sfb05 = i.ima01 and t.sfb01 = '"+MO+"'";
			Hashtable ht = getDBDATA(sql,DataSourceType._TIPTOP_KAIJIA_TOPPROD,uiVO);
			if(ht == null){
				setMessage("Tiptop 查無此工單");
				return;
			}
			vPartNo = ht.get("IMA01").toString();
			vPartName = ht.get("IMA02").toString();
			jMPN.setText(vPartNo);
			jta.append("PartNo : "+vPartNo+"   PartName : "+vPartName+"\n");
			System.out.println("PartNo : "+vPartNo+"   PartName : "+vPartName+"\n");//812-171100536
			
			Vector arg = new Vector();
			arg.add(vPartNo);
			BaseServletAgent bsa = new BaseServletAgent(uiVO);
			BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
			bvo.setPackageName("SFIS_LABEL_CARTON_PEGA_PG");
			bvo.setFunctionName("Query_CARTON_INFO");
			ResultVO rvo = bsa.doFunc(bvo);
		  	Vector result = rvo.getData();
		  	if(result.size()<2){
		  		setMessage("MES 無定義廠內料號與客戶料號對應");
				return;
		  	}
			Hashtable row = (Hashtable)result.get(1);
			
			vCUST_PART_NO = row.get("CUST_PART_NO").toString();
			vBATCH_NO = row.get("BATCH_NO").toString();
			vQTY = row.get("QTY").toString();
			jCPN.setText(vCUST_PART_NO);
			jtfQTY.setText(vQTY);
			jta.append("CustPartNo : "+vCUST_PART_NO+"   BATCH_NO : "+vBATCH_NO+"  QTY :"+vQTY+"\n");
			jtfMO.setEditable(false);
			setStatus(new int[]{ToolBarItem.Print,ToolBarItem.Cancel,ToolBarItem.Exit});
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	public void cancel() {
		setStatus(new int[]{ToolBarItem.SingleQuery,ToolBarItem.Cancel,ToolBarItem.Exit});
		jtfMO.setEditable(true);
		jta.setText("");
		jtfQTY.setText("");
		vPartNo = null;
		vPartName = null;
		vCUST_PART_NO = null;
		vBATCH_NO = null;
		vQTY = null;
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
		String qty=jtfQTY.getText().trim();
		String CPN=jCPN.getText().trim();
		if(qty.equals("")){
			setMessage("請輸入QTY!");
			return;
		}
		
		if(!isMatcherPatternStr(qty,"[0-9]")){
			setMessage("請輸入QTY(數字 0-9 )!");
			return;
		}
		try {
			CartonPrint_1 pt = new CartonPrint_1("KAIJIA", CPN,vPartNo,getNowDate(), vBATCH_NO, qty,vPartName);
			pt.createPdf(Integer.parseInt(printNum),jcboxPrint.isSelected());
		} catch (Exception e) {
			e.printStackTrace();
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
	public String getNowDate(){
		Calendar rightNow = Calendar.getInstance();
		String _YEAR = Integer.toString(rightNow.get(Calendar.YEAR));
		String _MONTH = "";
		String _DAY_OF_MONTH = "";
		int _m = rightNow.get(Calendar.MONTH)+1;
		if(_m < 10 )
			_MONTH = "0"+_m;
		else
			_MONTH = Integer.toString(_m);
		
		int _d = rightNow.get(Calendar.DAY_OF_MONTH);
		
		if(_d < 10 )
			_DAY_OF_MONTH = "0"+_d;
		else
			_DAY_OF_MONTH = Integer.toString(_d);
		return _YEAR+_MONTH+_DAY_OF_MONTH;
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
