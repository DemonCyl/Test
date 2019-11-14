/**
 * Copyright (c) 2008 Pegatron Inc. All Rights Reserved.
 * $Id: Barcode_1616_MakeUp_PEGA.java,v 1.4 2017/02/11 03:24:05 Jieyu_Fu Exp $
 */

package mes_sfis.client.ui.barcode;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.Hashtable;
import java.util.Iterator;
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

import base.client.util.CloneArray_ChangeStr;
import base.client.util.component.PMIDComboBox;
import base.client.util.component.P_Component_Meta;
import base.client.util.MessageUtil;
import base.client.util.StringUtil;
import base.client.enums.ToolBarItem;

import com.klg.jclass.table.data.JCEditableVectorDataSource;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;


import base.client.ui.BasePanel;
import base.enums.DataSourceType;
import base.vo.ResultVO;
import base.vo.BaseVO;
import base.client.util.BaseServletAgent;
import base.enums.CommandName;
import base.vo.UI_InitVO;
import mes_sfis.client.util.barcode.CartonPrint_1;
import java.io.IOException;
import base.client.util.component.PDateTimeTextField;
/**
 * The <code>Barcode_1616_MakeUp_PEGA</code> class.
 *
 * @version     $Name:  $, $Id: Barcode_1616_MakeUp_PEGA.java,v 1.4 2017/02/11 03:24:05 Jieyu_Fu Exp $
 * @author      <a href="mailto:william_tsai@Pegatroncorp.com">William Tsai</a>
 * @history      [>: General, +: New, #: Modification, -: Remove, ~: Bug]
 *          $Log: Barcode_1616_MakeUp_PEGA.java,v $
 *          Revision 1.4  2017/02/11 03:24:05  Jieyu_Fu
 *          no message
 *
 *          Revision 1.3  2017/02/10 00:32:09  Jieyu_Fu
 *          no message
 *
 *          Revision 1.2  2017/02/10 00:28:49  Jieyu_Fu
 *          no message
 *
 *          Revision 1.1  2017/02/10 00:16:16  Jieyu_Fu
 *          no message
 *
 *          Revision 1.4  2017/02/09 07:03:40  Jieyu_Fu
 *          no message
 *
 */
public class Barcode_1616_MakeUp_PEGA extends BasePanel{
	
	
    public static final String VERSION    = "$Name:  $, $Id: Barcode_1616_MakeUp_PEGA.java,v 1.4 2017/02/11 03:24:05 Jieyu_Fu Exp $";
    public static final String COPYRIGHT  = "Copyright (c) 2008 PEGATRON Inc. All Rights Reserved.";
	Barcode_1616_MakeUp_PEGA self;
	public Barcode_1616_MakeUp_PEGA(UI_InitVO uiVO) {
		super(uiVO);
		init(); 
	}

	Hashtable Materialquantity = new Hashtable();
	JTextField jtfBuShua;
	JTextField jtfJaShua;
	JTextField jtfexport;
	int bc = 0;
	int jc = 0;
	int butocou = 0;
	int jatocou = 0;
	int chStatus = 0;
	String strbjpa;
	String strbjkj;
	JLabel BuCount;
	JLabel BuTotal;
	JLabel JaCount;
	JLabel JaTotal;
	JLabel JLBuShua;
	JLabel JLJaShua;
	KeyListener _keyListener_SVT_QueryBu;
	KeyListener _keyListener_SVT_QueryJa;
	KeyListener _keyListener_SVT_QueryEx;
	void init(){
		self=this;
		self.setUILayout(null);
		
		JLBuShua = new JLabel("請刷布料號 : ",JLabel.RIGHT);
		JLBuShua.setBounds(new Rectangle(10,95,100,25));
		add(JLBuShua,null);
		
		jtfBuShua = new JTextField();
		jtfBuShua.setBounds(new Rectangle(120,95,250,25));
		add(jtfBuShua,null);
		jtfBuShua.setEditable(false);
		
		BuCount = new JLabel("已刷: " + Integer.toString(bc),JLabel.RIGHT);
		BuCount.setBounds(new Rectangle(330,95,100,25));
		add(BuCount,null);
		
		BuTotal = new JLabel("已用: " + Integer.toString(butocou),JLabel.RIGHT);
		BuTotal.setBounds(new Rectangle(440,95,100,25));
		add(BuTotal,null);
		
		_keyListener_SVT_QueryBu = new KeyListener() {
			public void keyPressed(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					BUSVT_Query();
				}
			}
			public void keyTyped(KeyEvent e) {}
		};
		jtfBuShua.addKeyListener(_keyListener_SVT_QueryBu);
		
		JLJaShua = new JLabel("請刷膠料號 : ",JLabel.RIGHT);
		JLJaShua.setBounds(new Rectangle(10,145,100,25));
		add(JLJaShua,null);
		
		jtfJaShua = new JTextField();
		jtfJaShua.setBounds(new Rectangle(120,145,250,25));
		add(jtfJaShua,null);
		jtfJaShua.setEditable(false);		
		
		_keyListener_SVT_QueryJa = new KeyListener() {
			public void keyPressed(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					JASVT_Query();
				}
			}
			public void keyTyped(KeyEvent e) {}
		};
		jtfJaShua.addKeyListener(_keyListener_SVT_QueryJa);
		
		JaCount = new JLabel("已刷: " + Integer.toString(jc),JLabel.RIGHT);
		JaCount.setBounds(new Rectangle(330,145,100,25));
		add(JaCount,null);
		
		JaTotal = new JLabel("已用: " + Integer.toString(jatocou),JLabel.RIGHT);
		JaTotal.setBounds(new Rectangle(440,145,100,25));
		add(JaTotal,null);
		
		JLabel JLexport = new JLabel("刷貼合標籤 : ",JLabel.RIGHT);
		JLexport.setBounds(new Rectangle(10,245,100,25));
		add(JLexport,null);
		
		jtfexport = new JTextField();
		jtfexport.setBounds(new Rectangle(120,245,250,25));
		add(jtfexport,null);
		
		_keyListener_SVT_QueryEx = new KeyListener() {
			public void keyPressed(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					ExSVT_Query();
				}
			}
			public void keyTyped(KeyEvent e) {}
		};
		jtfexport.addKeyListener(_keyListener_SVT_QueryEx);
		
		setStatus(new int[]{ToolBarItem.Cancel,ToolBarItem.Exit});
	}
	
	public void singleQuery() {
		
	}
	public void ExSVT_Query(){
		try{
			Vector arg = new Vector();
			arg.add(jtfexport.getText().trim());
			BaseServletAgent bsa = new BaseServletAgent(uiVO);
			BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
			bvo.setPackageName("SFIS_FINGERPRINT_MaUp_PEGA_PG");
			bvo.setFunctionName("Query_Total_Bu");
			ResultVO rvo = bsa.doFunc(bvo);
			Vector result = rvo.getData();
			butocou = result.size()-1;
			BuTotal.setText("已用: " + Integer.toString(butocou));
			Vector uparg = new Vector();
			uparg.add(jtfexport.getText().trim());
			BaseServletAgent upbsa = new BaseServletAgent(uiVO);
			BaseVO upbvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,uparg);
			upbvo.setPackageName("SFIS_FINGERPRINT_MaUp_PEGA_PG");
			upbvo.setFunctionName("Query_Total_Ja");
			ResultVO uprvo = upbsa.doFunc(upbvo);
			Vector upresult = uprvo.getData();
			jatocou = upresult.size()-1;
			JaTotal.setText("已用: " + Integer.toString(jatocou));
			jtfexport.setEditable(false);
			jtfBuShua.setEditable(true);
			jtfJaShua.setEditable(true);
		}catch(Exception e){
			e.printStackTrace();
			exUtl.ExceptionNotify(e,"Barcode_1616_MakeUp_PEGA ExSVT_Query",this.getClass().toString(),VERSION);
		}
	}
	public void BUSVT_Query(){
		setMessage("");
		if(chStatus == 0){
			try{
				strbjpa = jtfBuShua.getText().trim();
				Vector alarg = new Vector();
				alarg.add(jtfBuShua.getText().trim());
				BaseServletAgent albsa = new BaseServletAgent(uiVO);
				BaseVO albvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,alarg);
				albvo.setPackageName("SFIS_FINGERPRINT_MaUp_PEGA_PG");
				albvo.setFunctionName("Goto_contrast_BJPega");
				ResultVO alrvo = albsa.doFunc(albvo);
				Vector alresult = alrvo.getData();
				if(alresult.size()<2){
					setMessage("對照表內無此客戶料號");
					return;
				}
				Hashtable bjpa = (Hashtable)alresult.get(1);
				strbjkj = CloneArray_ChangeStr.NulltoSpace(bjpa.get("KJNUM"));
				String sql = "select b.sfb08,a.sfa03,a.sfa05,i.ima02 from sfa_file a,sfb_file b,ima_file i where b.sfb01 = '"+jtfexport.getText().trim().substring(0,13)+"' and a.sfa01 = b.sfb01"
				+ " and i.ima01 = a.sfa03 and a.sfa05 != '0' and a.sfa03 = '" + strbjkj + "'";
				Hashtable checkbj = getDBCOPYDATA(sql,DataSourceType._TIPTOP_KAIJIA_TOPPROD,uiVO);
				if(checkbj == null){
					setMessage("料號不符");
					return;
				}
				JLBuShua.setText("請刷布批號 : ");
				jtfBuShua.setText("");
				chStatus = 1;
				jtfJaShua.setEditable(false);
			}catch(Exception e){
				e.printStackTrace();
				exUtl.ExceptionNotify(e,"Barcode_1616_table_PEGA BUSVT_Query",this.getClass().toString(),VERSION);
			}
		}
		else if(chStatus == 1){
			try{
				Vector arg = new Vector();
				arg.add(jtfexport.getText().trim());
				arg.add(jtfBuShua.getText().trim());
				arg.add(strbjpa);
				arg.add(strbjkj);
				BaseServletAgent bsa = new BaseServletAgent(uiVO);
				BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
				bvo.setPackageName("SFIS_FINGERPRINT_MaUp_PEGA_PG");
				bvo.setFunctionName("Insert_BULABEL_INFO");
				ResultVO rvo = bsa.doFunc(bvo);
				Vector result = rvo.getData();
				Hashtable row = (Hashtable)result.get(1);
				if(CloneArray_ChangeStr.NulltoSpace(row.get("MISSING")).equals("OK")){			
					setMessage("刷入成功");
					bc +=1;
					BuCount.setText("已刷: " + Integer.toString(bc));
					butocou +=1;
					BuTotal.setText("已用: " + Integer.toString(butocou));
					jtfBuShua.setText("");
					JLBuShua.setText("請刷布料號 : ");
					chStatus = 0;
					jtfJaShua.setEditable(true);
				}
			}catch(Exception e){
				e.printStackTrace();
				exUtl.ExceptionNotify(e,"Barcode_1616_MakeUp_PEGA BUSVT_Query",this.getClass().toString(),VERSION);
			}
		}
	}
	public void JASVT_Query(){
		setMessage("");
		if(chStatus == 0){
			try{
				strbjpa = jtfJaShua.getText().trim();
				Vector alarg = new Vector();
				alarg.add(jtfJaShua.getText().trim());
				BaseServletAgent albsa = new BaseServletAgent(uiVO);
				BaseVO albvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,alarg);
				albvo.setPackageName("SFIS_LABEL_FINGERPRINT_PEGA_PG");
				albvo.setFunctionName("Goto_contrast_BJPega");
				ResultVO alrvo = albsa.doFunc(albvo);
				Vector alresult = alrvo.getData();
				if(alresult.size()<2){
					setMessage("對照表內無此客戶料號");
					return;
				}
				Hashtable bjpa = (Hashtable)alresult.get(1);
				strbjkj = CloneArray_ChangeStr.NulltoSpace(bjpa.get("KJNUM"));
				String sql = "select b.sfb08,a.sfa03,a.sfa05,i.ima02 from sfa_file a,sfb_file b,ima_file i where b.sfb01 = '"+jtfexport.getText().trim().substring(0,13)+"' and a.sfa01 = b.sfb01"
				+ " and i.ima01 = a.sfa03 and a.sfa05 != '0' and a.sfa03 = '" + strbjkj + "'";
				Hashtable checkbj = getDBCOPYDATA(sql,DataSourceType._TIPTOP_KAIJIA_TOPPROD,uiVO);
				if(checkbj == null){
					setMessage("料號不符");
					return;
				}
				JLJaShua.setText("請刷膠批號 : ");
				jtfJaShua.setText("");
				chStatus = 1;
				jtfBuShua.setEditable(false);
			}catch(Exception e){
				e.printStackTrace();
				exUtl.ExceptionNotify(e,"Barcode_1616_table_PEGA JASVT_Query",this.getClass().toString(),VERSION);
			}
		}
		else if(chStatus == 1){
			try{
				Vector arg = new Vector();
				arg.add(jtfexport.getText().trim());
				arg.add(jtfJaShua.getText().trim());
				arg.add(strbjpa);
				arg.add(strbjkj);
				BaseServletAgent bsa = new BaseServletAgent(uiVO);
				BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
				bvo.setPackageName("SFIS_FINGERPRINT_MaUp_PEGA_PG");
				bvo.setFunctionName("Insert_JALABEL_INFO");
				ResultVO rvo = bsa.doFunc(bvo);
				Vector result = rvo.getData();
				Hashtable row = (Hashtable)result.get(1);
				if(CloneArray_ChangeStr.NulltoSpace(row.get("MISSING")).equals("OK")){			
					setMessage("刷入成功");
					jc +=1;
					JaCount.setText("已刷: " + Integer.toString(jc));
					jatocou +=1;
					JaTotal.setText("已用: " + Integer.toString(jatocou));
					jtfJaShua.setText("");
					JLJaShua.setText("請刷膠料號 : ");
					chStatus = 0;
					jtfBuShua.setEditable(true);
				}
			}catch(Exception e){
				e.printStackTrace();
				exUtl.ExceptionNotify(e,"Barcode_1616_MakeUp_PEGA JASVT_Query",this.getClass().toString(),VERSION);
			}
		}
	}
	public Hashtable getDBCOPYDATA(String sql,String ds,UI_InitVO uiVO)throws Exception{
		String reString = "";
		Vector arg = new Vector();
		arg.add(sql);
	    BaseServletAgent bsa1 = new BaseServletAgent(uiVO);
		BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallSQLCmd2,arg);
		bvo.setDataSourceType(ds);
		ResultVO rvo = bsa1.doFunc(bvo);
		Vector result = rvo.getData();
		Hashtable ht = null;
		if(result.size()>1){
			ht = (Hashtable)result.elementAt(1);
		}
		return ht;
	}
	public void cancel() {
		jtfexport.setEditable(true);
		jtfexport.setText("");
		bc = 0;
		BuCount.setText("已刷: " + Integer.toString(bc));
		butocou = 0;
		BuTotal.setText("已用: " + Integer.toString(butocou));
		jc = 0;
		JaCount.setText("已刷: " + Integer.toString(jc));
		jatocou = 0;
		JaTotal.setText("已用: " + Integer.toString(jatocou));
		jtfBuShua.setText("");
		jtfBuShua.setEditable(false);
		jtfJaShua.setText("");
		jtfJaShua.setEditable(false);
		chStatus = 0;
		JLBuShua.setText("請刷布料號 : ");
		JLJaShua.setText("請刷膠料號 : ");
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
	public void print(){
		
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
