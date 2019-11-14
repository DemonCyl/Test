/**
 * Copyright (c) 2008 Pegatron Inc. All Rights Reserved.
 * $Id: Label_1606PrintBarCode_PEGA.java,v 1.9 2018/01/13 07:42:06 Lucy6_Lu Exp $
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

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.HashMap;

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
import base.client.util.component.PDateTimeTextField;
import base.client.util.CloneArray_ChangeStr;

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
import mes_sfis.client.util.barcode.CartonPrint_1606Test;

/**
 * The <code>標籤打印範例</code> class.
 *
 * @version     $Name:  $, $Id: Label_1606PrintBarCode_PEGA.java,v 1.9 2018/01/13 07:42:06 Lucy6_Lu Exp $
 * @author      <a href="mailto:william_tsai@Pegatroncorp.com">William Tsai</a>
 * @history      [>: General, +: New, #: Modification, -: Remove, ~: Bug]
 *          $Log: Label_1606PrintBarCode_PEGA.java,v $
 *          Revision 1.9  2018/01/13 07:42:06  Lucy6_Lu
 *          2018-01-13 1606
 *
 *          Revision 1.8  2018/01/13 02:07:59  Lucy6_Lu
 *          2018-01-13 1606
 *
 *          Revision 1.7  2017/12/18 00:53:39  Lucy6_Lu
 *          2017-12-18 1606添加料號信息
 *
 *          Revision 1.6  2017/12/09 03:41:46  Lucy6_Lu
 *          2017-12-09 Note欄位添加
 *
 *          Revision 1.4  2017/12/09 02:37:23  Lucy6_Lu
 *          2017-12-09 Note欄位添加
 *
 *          Revision 1.2  2017/06/24 06:00:40  Jieyu_Fu
 *          no message
 *
 *          Revision 1.1  2017/03/07 04:23:40  Jieyu_Fu
 *          no message
 *
 *          Revision 1.2  2016/11/22 07:31:56  Jieyu_Fu
 *          no message
 *
 *
 */
public class Label_1606PrintBarCode_PEGA extends BasePanel{
	
	
    public static final String VERSION    = "$Name:  $, $Id: Label_1606PrintBarCode_PEGA.java,v 1.9 2018/01/13 07:42:06 Lucy6_Lu Exp $";
    public static final String COPYRIGHT  = "Copyright (c) 2008 PEGATRON Inc. All Rights Reserved.";
    
	String vCUST_PART_NO;
	String vBATCH_NO;
	String vQTY;
	String vPartNo;
	String vPartName;
	String vDate;
	HashMap mo = new HashMap();
    
	Label_1606PrintBarCode_PEGA self;
	public Label_1606PrintBarCode_PEGA(UI_InitVO uiVO) {
		super(uiVO);
		init(); 
	}
	JTextField jtfMO;
	JTextField JTKjPn;
	JTextField jtfQTY;
	JTextField jtfLabelQTY;
	JCheckBox jcboxPrint;
	JTextArea jta;
	PDateTimeTextField PDTimeST;
	int isExist;
	
	
	void init(){
		mo.put("13E5-3DB5101","DRAGON(QS)MFRAE C3 BLK ASSY B1 KAI JIA/M1067613-001/T2-1/EV3");
        mo.put("13E5-3DB4V01","DRAGON(QS)MFRAE C3 BLK ASSY B2 KAI JIA/M1067613-002/T2-1/EV3");
        mo.put("13E5-3DN3Y01","DRAGON(QS)TRAY CARD-RE BLK B1 KAI JIA/M1066835-001/T2-1/EV3");
        mo.put("13E5-3DN3H21","DRAGON(QS)BTN VOL BLK/KAI JIA/M1067624-001/T2-1/EV3");
		mo.put("13E5-3DB5201","DRAGON(QS)MFRAE C3 BLK ASSY B3 KAI JIA/M1067613-003/T2-1/EV3"); 
        mo.put("13E5-3DB5001","DRAGON(QS)MFRAE R2 BLK ASSY B1 KAI JIA/M1067612-001/T2-1/EV3"); 
        mo.put("13E5-3DB4W01","DRAGON(QS)MFRAE R2 BLK ASSY B2 KAI JIA/M1067612-002/T2-1/EV3"); 
        mo.put("13E5-3DB5401","DRAGON(QS)MFRAE R2 BLK ASSY B3 KAI JIA/M1067612-003/T2-1/EV3");
		
        mo.put("13E5-3DB3N01","DRAGON(QS)MFRAE R2 BLK ASSY B4 KAI JIA/M1067612-004/T2-1/EV3");
        mo.put("13E5-3DB4U01","DRAGON(QS)MFRAE R2 BGD ASSY B2 KAI JIA/M1074276-002/T2-1/EV3");		
        mo.put("13E5-3DB4T01","DRAGON(QS)MFRAE R2 BGD ASSY B3 KAI JIA/M1074276-003/T2-1/EV3");	
		
        mo.put("13E5-3DB3P01","DRAGON(QS)MFRAE C3 BGD ASSY B1 KAI JIA/M1074281-001/T2-1/EV3");		
        mo.put("13E5-3DB5301","DRAGON(QS)MFRAE C3 BGD ASSY B2 KAI JIA/M1074281-002/T2-1/EV3");		
        mo.put("13E5-3DB4Y01","DRAGON(QS)MFRAE C3 BGD ASSY B3 KAI JIA/M1074281-003/T2-1/EV3");

		mo.put("13E5-3DN3K01","DRAGON(QS)TRAY CARD-RE BGD B1 KAI JIA/M1074285-001/T2-1/EV3");
		mo.put("13E5-3DN3W01","DRAGON(QS)TRAY CARD-RE BGD B2 KAI JIA/M1074285-002/T2-1/EV3");
		mo.put("13E5-3DN3Z01","DRAGON(QS)TRAY CARD-RE BGD B3 KAI JIA/M1074285-003/T2-1/EV3");
		
		mo.put("13E5-3DN3L01","DRAGON(QS)BTN VOL BGD KAI JIA/M1067624-002/T2-1/EV3");
		mo.put("13E5-3DB5601","DRAGON(QS)MFRAE R2 BLK ASSY C KAI JIA/M1067612-XXX/CPK/EV3");
		mo.put("13E5-3DN4001","DRAGON(QS)TRAY CARD-RE BLK C KAI JIA/M1066835-XXX/CPK/EV3");
		mo.put("13E5-3DN4101","DRAGON(QS)BTN VOL BLK C KAI JIA/M1067624-001/CPK/EV3");
		mo.put("13E5-3DB5701","DRAGON(QS)MFRAE C3 BLK ASSY C KAI JIA/M1067613-XXX/CPK/EV3");
		
		mo.put("13E5-3DB5011","DRAGON(QS)MFRAE R2 BLK ASSY B1 KAI JIA/M1067612-001/T3/EV3");
		mo.put("13E5-3DB4W11","DRAGON(QS)MFRAE R2 BLK ASSY B2 KAI JIA/M1067612-002/T3/EV3");
		mo.put("13E5-3DB5411","DRAGON(QS)MFRAE R2 BLK ASSY B3 KAI JIA/M1067612-003/T3/EV3");
		mo.put("13E5-3DB5511","DRAGON(QS)MFRAE R2 BLK ASSY B4 KAI JIA/M1067612-004/T3/EV3");
		mo.put("13E5-3DB3N11","DRAGON(QS)MFRAE R2 BGD ASSY B1 KAI JIA/M1074276-001/T3/EV3");
		mo.put("13E5-3DB5111","DRAGON(QS)MFRAE C3 BLK ASSY B1 KAI JIA/M1067613-001/T3/EV3");
		mo.put("13E5-3DB4V11","DRAGON(QS)MFRAE C3 BLK ASSY B2 KAI JIA/M1067613-002/T3/EV3");
		mo.put("13E5-3DB5211","DRAGON(QS)MFRAE C3 BLK ASSY B3 KAI JIA/M1067613-003/T3/EV3");
		mo.put("13E5-3DB4Z11","DRAGON(QS)MFRAE C3 BLK ASSY B4 KAI JIA/M1067613-004/T3/EV3");
		mo.put("13E5-3DB3P11","DRAGON(QS)MFRAE C3 BGD ASSY B1 KAI JIA/M1074281-001/T3/EV3");
		
		mo.put("13E5-3DN3Y11","DRAGON(QS)TRAY CARD-RE BLK B1 KAI JIA/M1066835-001/T3/EV3");
		mo.put("13E5-3DN3K11","DRAGON(QS)TRAY CARD-RE BGD B1 KAI JIA/M1074285-001/T3/EV3");
		mo.put("13E5-3DN3H31","DRAGON(QS)BTN VOL BLK/KAI JIA/M1067624-001/T3/EV3");
		mo.put("13E5-3DN3L11","DRAGON(QS)BTN VOL BGD KAI JIA/M1067624-002/T3/EV3");
		
		self=this;
		self.setUILayout(null);
		
		JLabel jLabel_MO = new JLabel("請輸入Pega P/N : ",JLabel.RIGHT);
		jLabel_MO.setBounds(new Rectangle(10,45,100,25));
		add(jLabel_MO,null);
		
		jta = new JTextArea();
		jta.setBounds(new Rectangle(120,195,400,200));
		add(jta,null);
		jta.setEditable(false);
		
		jtfMO = new JTextField();
		jtfMO.setBounds(new Rectangle(120,45,250,25));
		add(jtfMO,null);
		
		JLabel JLKjPn = new JLabel("請輸入Vendor P/N : ",JLabel.RIGHT);
		JLKjPn.setBounds(new Rectangle(10,75,100,25));
		add(JLKjPn,null);
		
		JTKjPn = new JTextField();
		JTKjPn.setBounds(new Rectangle(120,75,250,25));
		add(JTKjPn,null);
		
		
		JLabel jLabel_QTY = new JLabel("QTY : ",JLabel.RIGHT);
		jLabel_QTY.setBounds(new Rectangle(10,135,100,25));
		add(jLabel_QTY,null);
		
		jtfQTY = new JTextField();
		jtfQTY.setBounds(new Rectangle(120,135,250,25));
		add(jtfQTY,null);
		
		
		JLabel jLabel_LabelQTY = new JLabel("打印張數 : ",JLabel.RIGHT);
		jLabel_LabelQTY.setBounds(new Rectangle(10,165,100,25));
		add(jLabel_LabelQTY,null);
		
		jtfLabelQTY = new JTextField();
		jtfLabelQTY.setText("1");
		jtfLabelQTY.setBounds(new Rectangle(120,165,250,25));
		add(jtfLabelQTY,null);
		
		jcboxPrint = new JCheckBox("連線打印機");
		jcboxPrint.setBounds(new Rectangle(400,165,100,25));
		add(jcboxPrint,null);
		jcboxPrint.setSelected(true);
		
		JLabel JLabelTime = new JLabel("選擇時間 : ",JLabel.RIGHT);
		JLabelTime.setBounds(new Rectangle(10,105,100,25));
		add(JLabelTime,null);
		
		PDTimeST=new PDateTimeTextField(uiVO,"PDTimeST",150,true,true);
		PDTimeST.setBounds(new Rectangle(120,105,150,25));
		add(PDTimeST);
		TextFieldAction();
		setStatus(new int[]{ToolBarItem.SingleQuery,ToolBarItem.Cancel,ToolBarItem.Exit});
	}
	
	public void TextFieldAction(){
        jtfMO.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                String ss = jtfMO.getText().toString().trim();
                       if(!mo.get(ss).toString().equals(null)&&!mo.get(ss).toString().equals("")){
                           vPartName = mo.get(ss).toString();
                       }else{
						   vPartName = "";
					   }
            }
        });
    }
	
	public void singleQuery() {
		String MO = jtfMO.getText().trim();
		jta.setText("");
		if(MO.equals("")){
			JOptionPane.showMessageDialog(null, "請輸入客戶料號!");
			return;
		}
		
		try {
			Vector arg = new Vector();
			arg.add(MO);
			BaseServletAgent bsa = new BaseServletAgent(uiVO);
			BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
			bvo.setPackageName("SFIS_1606_LABLE_TEST_PEGA_PG");
			bvo.setFunctionName("Query_PN_INFO");
			ResultVO rvo = bsa.doFunc(bvo);
		  	Vector result = rvo.getData();
		  	if(result.size()<2){
				JOptionPane.showMessageDialog(null, "MES 無定義此客戶料號對應,請自行填寫后打印!");
				isExist = 1;
//				vPartName = " ";
				vBATCH_NO = " ";
				setStatus(new int[]{ToolBarItem.Print,ToolBarItem.Cancel,ToolBarItem.Exit});
		  	}else{
				Hashtable row = (Hashtable)result.get(1);
				vCUST_PART_NO = row.get("PEGA_PN").toString();
				vDate = row.get("VDATE").toString();
				if(!PDTimeST.getText().equals("")){
					vDate = PDTimeST.getText().substring(5,7) + PDTimeST.getText().substring(8,10) + PDTimeST.getText().substring(0,4);
				}
				vQTY = row.get("QTY").toString();
//				vPartName = " ";
				vBATCH_NO = " ";
				vPartNo = CloneArray_ChangeStr.NulltoSpace(row.get("VENDOR_PN"));
				JTKjPn.setText(vPartNo);
				jtfQTY.setText(vQTY);
				jta.append("CustPartNo : " + vCUST_PART_NO + "\nDateCode : " + vDate + "\nBATCH_NO : " + vBATCH_NO + "\nQTY :"+vQTY+"\n");
				jtfMO.setEditable(false);
				setStatus(new int[]{ToolBarItem.Print,ToolBarItem.Cancel,ToolBarItem.Exit});
			}
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	public void cancel() {
		setStatus(new int[]{ToolBarItem.SingleQuery,ToolBarItem.Cancel,ToolBarItem.Exit});
		jtfMO.setEditable(true);
		jtfMO.setText("");
		jta.setText("");
		jtfQTY.setText("");
		vPartNo = null;
		vPartName = null;
		vCUST_PART_NO = null;
		vBATCH_NO = null;
		vQTY = null;
		vDate = null;
		isExist = 0;
		JTKjPn.setText("");
	}
	
	public void print() {
		
		
		String printNum=jtfLabelQTY.getText().trim();
		if(printNum.equals("")){
			JOptionPane.showMessageDialog(null,"請輸入打印份數!");
			return;
		}
		
		if(!isMatcherPatternStr(printNum,"[0-9]")){
			JOptionPane.showMessageDialog(null,"請輸入打印份數(數字 0-9 )!");
			return;
		}
		String qty=jtfQTY.getText().trim();
		if(qty.equals("")){
			JOptionPane.showMessageDialog(null,"請輸入QTY!");
			return;
		}
		
		if(!isMatcherPatternStr(qty,"[0-9]")){
			JOptionPane.showMessageDialog(null,"請輸入QTY(數字 0-9 )!");
			return;
		}
		try {
			if(isExist == 0){
				if(!PDTimeST.getText().equals("")){
					vDate = PDTimeST.getText().substring(5,7) + PDTimeST.getText().substring(8,10) + PDTimeST.getText().substring(0,4);
				}
				CartonPrint_1606Test pt = new CartonPrint_1606Test("KAI JIA COMPUTER ACCESSORY CO., LTD.", vCUST_PART_NO,JTKjPn.getText().trim(),vDate,vBATCH_NO, qty,vPartName);
				pt.createPdf(Integer.parseInt(printNum),jcboxPrint.isSelected());
				cancel();
			}else{
				if(!PDTimeST.getText().equals("")){
					vDate = PDTimeST.getText().substring(5,7) + PDTimeST.getText().substring(8,10) + PDTimeST.getText().substring(0,4);
				}
				CartonPrint_1606Test pt = new CartonPrint_1606Test("KAI JIA COMPUTER ACCESSORY CO., LTD.", jtfMO.getText().trim(),JTKjPn.getText().trim(),vDate,vBATCH_NO, qty,vPartName);
				pt.createPdf(Integer.parseInt(printNum),jcboxPrint.isSelected());
				Vector arg = new Vector();
				arg.add(jtfMO.getText().trim());
				arg.add(vDate);
				arg.add(qty);
				arg.add(JTKjPn.getText().trim());
				BaseServletAgent bsa = new BaseServletAgent(uiVO);
				BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
				bvo.setPackageName("SFIS_1606_LABLE_TEST_PEGA_PG");
				bvo.setFunctionName("Insert_No_Exist");
				ResultVO rvo = bsa.doFunc(bvo);
				cancel();
			}
			
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
	/*public String getNowDate(){
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
	}*/
	
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
