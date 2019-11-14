/**
 * Copyright (c) 2008 Pegatron Inc. All Rights Reserved.
 * $Id: Barcode_1616_False_function_PEGA.java,v 1.1 2017/02/11 06:58:39 Jieyu_Fu Exp $
 */

package mes_sfis.client.ui.barcode;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.*;
import java.awt.*;
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
import javax.swing.*;

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
 * The <code>Barcode_1616_False_function_PEGA</code> class.
 *
 * @version     $Name:  $, $Id: Barcode_1616_False_function_PEGA.java,v 1.1 2017/02/11 06:58:39 Jieyu_Fu Exp $
 * @author      <a href="mailto:william_tsai@Pegatroncorp.com">William Tsai</a>
 * @history      [>: General, +: New, #: Modification, -: Remove, ~: Bug]
 *          $Log: Barcode_1616_False_function_PEGA.java,v $
 *          Revision 1.1  2017/02/11 06:58:39  Jieyu_Fu
 *          no message
 *
 *          Revision 1.4  2017/02/11 03:24:05  Jieyu_Fu
 *
 */
public class Barcode_1616_False_function_PEGA extends BasePanel{
	
	
    public static final String VERSION    = "$Name:  $, $Id: Barcode_1616_False_function_PEGA.java,v 1.1 2017/02/11 06:58:39 Jieyu_Fu Exp $";
    public static final String COPYRIGHT  = "Copyright (c) 2008 PEGATRON Inc. All Rights Reserved.";
	Barcode_1616_False_function_PEGA self;
	public Barcode_1616_False_function_PEGA(UI_InitVO uiVO) {
		super(uiVO);
		init(); 
	}
	JTextField jtfBuShua;
	JTextField jtfJaShua;
	JLabel JLOkOrNg;
	KeyListener _keyListener_SVT_QueryBu;
	KeyListener _keyListener_SVT_QueryJa;
	void init(){
		self=this;
		self.setUILayout(null);
		
		JLabel JLBuShua = new JLabel("請刷布貼合標籤 : ",JLabel.RIGHT);
		JLBuShua.setBounds(new Rectangle(10,95,100,25));
		add(JLBuShua,null);
		
		jtfBuShua = new JTextField();
		jtfBuShua.setBounds(new Rectangle(120,95,250,25));
		add(jtfBuShua,null);
		
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
		
		JLabel JLJaShua = new JLabel("請刷膠貼合標籤 : ",JLabel.RIGHT);
		JLJaShua.setBounds(new Rectangle(10,145,100,25));
		add(JLJaShua,null);
		
		jtfJaShua = new JTextField();
		jtfJaShua.setBounds(new Rectangle(120,145,250,25));
		add(jtfJaShua,null);
		
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
		
		JLOkOrNg = new JLabel("",JLabel.RIGHT);
		JLOkOrNg.setFont(new Font("宋體", Font.BOLD, 120));
		JLOkOrNg.setBounds(new Rectangle(120,250,200,200));
		add(JLOkOrNg,null);
		
		setStatus(new int[]{ToolBarItem.Cancel,ToolBarItem.Exit});
	}
	
	public void singleQuery() {
		
	}

	public void BUSVT_Query(){
		if(!jtfJaShua.getText().trim().equals("")){
			if(jtfBuShua.getText().trim().equals(jtfJaShua.getText().trim())){
				JLOkOrNg.setText("OK");
				jtfBuShua.setText("");
				jtfJaShua.setText("");
			}else{
				JLOkOrNg.setText("NG");
				jtfBuShua.setText("");
				jtfJaShua.setText("");
			}
		}
	}
	public void JASVT_Query(){
		if(!jtfBuShua.getText().trim().equals("")){
			if(jtfBuShua.getText().trim().equals(jtfJaShua.getText().trim())){
				JLOkOrNg.setText("OK");
				jtfBuShua.setText("");
				jtfJaShua.setText("");
			}else{
				JLOkOrNg.setText("NG");
				jtfBuShua.setText("");
				jtfJaShua.setText("");
			}
		}
	}

	public void cancel() {

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
