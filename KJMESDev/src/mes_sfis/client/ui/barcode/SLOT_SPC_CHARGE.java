/**
 * Copyright (c) 2008 Pegatron Inc. All Rights Reserved.
 * $Id: SLOT_SPC_CHARGE.java,v 1.14 2017/06/01 11:31:47 Jieyu_Fu Exp $
 */
package mes_sfis.client.ui.barcode;
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
import base.client.util.component.PDateTimeTextField;
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
import java.io.File;   

import java.io.FileNotFoundException;   

import java.io.FileOutputStream;   

import java.io.IOException;   

import org.jfree.chart.ChartFactory;   

import org.jfree.chart.ChartUtilities;   

import org.jfree.chart.JFreeChart;   

import org.jfree.chart.plot.PlotOrientation;   

import org.jfree.chart.plot.XYPlot;   

import org.jfree.data.xy.XYDataset;   

import org.jfree.data.xy.XYSeries;   

import org.jfree.data.xy.XYSeriesCollection; 

import org.jfree.data.time.Day;
import org.jfree.data.time.Hour;
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.chart.plot.*;
import org.jfree.util.PublicCloneable;

import java.io.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import java.util.Date;
import base.util.WebServiceUtil;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
 /**
 * The <code>xxx</code> class.
 *
 * @version     $Name: 1.3 $, $Id: SLOT_SPC_CHARGE.java,v 1.14 2017/06/01 11:31:47 Jieyu_Fu Exp $
 * @author      <a href="mailto:william_tsai@asus.com.tw">William Tsai</a>
 * @history      [>: General, +: New, #: Modification, -: Remove, ~: Bug]
 *          $Log: SLOT_SPC_CHARGE.java,v $
 *          Revision 1.14  2017/06/01 11:31:47  Jieyu_Fu
 *          no message
 *
 *          Revision 1.13  2017/05/31 06:36:15  Jieyu_Fu
 *          no message
 *
 *          Revision 1.12  2017/05/31 06:31:41  Jieyu_Fu
 *          no message
 *
 *          Revision 1.11  2017/05/31 06:27:05  Jieyu_Fu
 *          no message
 *
 *          Revision 1.10  2017/05/27 09:24:59  Jieyu_Fu
 *          no message
 *
 *          Revision 1.9  2017/05/27 09:23:12  Jieyu_Fu
 *          no message
 *
 *          Revision 1.8  2017/05/13 02:26:50  Jieyu_Fu
 *          no message
 *
 *          Revision 1.7  2017/05/13 01:37:42  Jieyu_Fu
 *          no message
 *
 *          Revision 1.6  2017/05/12 08:35:01  Jieyu_Fu
 *          no message
 *
 *          Revision 1.5  2017/05/05 09:09:09  Jieyu_Fu
 *          no message
 *
 *          Revision 1.4  2017/05/05 08:35:46  Jieyu_Fu
 *          no message
 *
 *          Revision 1.3  2017/05/05 07:41:01  Jieyu_Fu
 *          no message
 *
 *          Revision 1.2  2017/05/05 07:33:38  Jieyu_Fu
 *          no message
 *
 *          Revision 1.1  2017/05/05 06:45:15  Jieyu_Fu
 *          no message
 *
 *
 */
public class SLOT_SPC_CHARGE extends BasePanel{
    public static final String VERSION    = "$Name:  $, $Id: SLOT_SPC_CHARGE.java,v 1.14 2017/06/01 11:31:47 Jieyu_Fu Exp $";
    public static final String COPYRIGHT  = "Copyright (c) 2008 PEGATRON Inc. All Rights Reserved.";

	private static final long serialVersionUID = -6120355920482734660L;

	private SLOT_SPC_CHARGE self;
	JTextField JTState;
	static JComboBox JCState;
	static JComboBox JCcheck;
	static JTextField JTtemp;
	static JTextField JTuptemp;
	static JTextField JTdwtemp;
	static JTextField JTsdtemp;
	JLabel JLbtemp;
	JTextArea jta;
	JButton JBcommite;
	JButton JBexcept;
	JTextField JTSlname;
	JTextField JTStartT;
	JTextField JTEndT;
	JTextField JTPing;
	JButton JBChange_No;
	PDateTimeTextField PDTimeST;
	PDateTimeTextField PDTimeED;
	static PDateTimeTextField PDEndTime;
	static PDateTimeTextField PD_EndTime;
	JCheckBox JCagain;
	static Vector vmark = null;
	static Vector vAllm = null;
	
	public SLOT_SPC_CHARGE(UI_InitVO uiVO) {
		super(uiVO);
		init();
	}
	
	private void init(){
		self = this;
		self.setUILayout(null);
		
		JLabel JLState = new JLabel("�п�ܼѸ�");
		JLState.setBounds(new Rectangle(10,30,70,25));
		add(JLState,null);
		
		JCState = new JComboBox();
		JCState.setSelectedItem("");
		JCState.setBounds(85,30,90,25);
		JCStateListener jcstalisten = new JCStateListener();
		JCState.addActionListener(jcstalisten);
		add(JCState);
		
		JLabel JLuplimter = new JLabel("�W��",JLabel.CENTER);
		JLuplimter.setBounds(new Rectangle(180,30,50,25));
		add(JLuplimter,null);
		
		JLabel JLdwlimter = new JLabel("�U��",JLabel.CENTER);
		JLdwlimter.setBounds(new Rectangle(230,30,50,25));
		add(JLdwlimter,null);
		
		JLabel JLsdlimter = new JLabel("����",JLabel.CENTER);
		JLsdlimter.setBounds(new Rectangle(290,30,50,25));
		add(JLsdlimter,null);
		
		JLabel JLbit = new JLabel("���",JLabel.CENTER);
		JLbit.setBounds(new Rectangle(350,30,50,25));
		add(JLbit,null);
		
		JCcheck = new JComboBox();
		JCcheck.setSelectedItem("");
		JCcheck.setBounds(5,75,85,25);
		JCcheckListener jcchelisten = new JCcheckListener();
		JCcheck.addActionListener(jcchelisten);
		add(JCcheck);
		
		JBcommite = new JButton("����");
		JBcommite.setBounds(410,50,130,60);
		BtncommiteListener BtCt = new BtncommiteListener();
		JBcommite.addActionListener(BtCt);
		add(JBcommite);

		
		JTtemp = new JTextField();
		JTtemp.setBounds(new Rectangle(110,75,50,25));
		add(JTtemp,null);
		
		JTuptemp = new JTextField();
		JTuptemp.setBounds(new Rectangle(170,75,50,25));
		add(JTuptemp,null);
		
		JTdwtemp = new JTextField();
		JTdwtemp.setBounds(new Rectangle(230,75,50,25));
		add(JTdwtemp,null);
		
		JTsdtemp = new JTextField();
		JTsdtemp.setBounds(new Rectangle(290,75,50,25));
		add(JTsdtemp,null);
		
		JLbtemp = new JLabel("");
		JLbtemp.setBounds(new Rectangle(350,75,50,25));
		add(JLbtemp,null);		
		
		jta = new JTextArea();
		jta.setBounds(new Rectangle(120,120,400,200));
		add(jta,null);
		jta.setEditable(false);
		
		PDEndTime=new PDateTimeTextField(uiVO,"PDEndTime",150,true,true);
		PDEndTime.setBounds(new Rectangle(120,350,150,25));
		add(PDEndTime);
		
		PD_EndTime=new PDateTimeTextField(uiVO,"PD_EndTime",150,true,true);
		PD_EndTime.setBounds(new Rectangle(120,410,150,25));
		add(PD_EndTime);
		
		JBexcept = new JButton("����ץX");
		JBexcept.setBounds(350,350,130,60);
		BtnexceptListener BtEp = new BtnexceptListener();
		JBexcept.addActionListener(BtEp);
		add(JBexcept);
		
		JLabel JLexcept = new JLabel("�п�ܶץX���");
		JLexcept.setBounds(new Rectangle(120,380,150,25));
		add(JLexcept,null);
		
		JLabel JLSlname = new JLabel("�ѦW",JLabel.RIGHT);
		JLSlname.setBounds(new Rectangle(10,440,75,25));
		add(JLSlname,null);
		
		JTSlname = new JTextField();
		JTSlname.setBounds(new Rectangle(100,440,100,25));
		add(JTSlname,null);
		JTSlname.setEditable(false);
		
		JLabel JLStartT = new JLabel("�دD�ɶ�");
		JLStartT.setBounds(new Rectangle(210,440,75,25));
		add(JLStartT,null);
		
		JTStartT = new JTextField();
		JTStartT.setBounds(new Rectangle(295,440,100,25));
		add(JTStartT,null);
		JTStartT.setEditable(false);
		
		JLabel JLEndT = new JLabel("�w�p���Ѯɶ�");
		JLEndT.setBounds(new Rectangle(405,440,100,25));
		add(JLEndT,null);
		
		JTEndT = new JTextField();
		JTEndT.setBounds(new Rectangle(515,440,100,25));
		add(JTEndT,null);
		JTEndT.setEditable(false);
		
		JLabel JLPing = new JLabel("�����W�v",JLabel.RIGHT);
		JLPing.setBounds(new Rectangle(10,475,75,25));
		add(JLPing,null);
		
		JTPing = new JTextField();
		JTPing.setBounds(new Rectangle(100,475,60,25));
		add(JTPing,null);
		//JTPing.setEditable(false);
		
		JLabel JLPingDay = new JLabel("��");
		JLPingDay.setBounds(new Rectangle(165,475,30,25));
		add(JLPingDay,null);
		
		JBChange_No = new JButton("����");
		JBChange_No.setBounds(210,475,75,25);
		BtnCNListener BtCN = new BtnCNListener();
		JBChange_No.addActionListener(BtCN);
		add(JBChange_No);
		
		PDTimeST=new PDateTimeTextField(uiVO,"PDTimeST",150,true,true);
		PDTimeST.setBounds(new Rectangle(305,475,150,25));
		add(PDTimeST);
		//PDTimeST.setVisible(false);
		
		PDTimeED=new PDateTimeTextField(uiVO,"PDTimeED",150,true,true);
		PDTimeED.setBounds(new Rectangle(465,475,150,25));
		add(PDTimeED);
		//PDTimeED.setVisible(false);
		
		JCagain = new JCheckBox("�ƴ�");
		JCagain.setBounds(new Rectangle(5,100,100,25));
		add(JCagain,null);
		JCagain.setSelected(false);
		
		pickC();
		CheckChange();
		
		setStatus(new int[]{ToolBarItem.SingleQuery,ToolBarItem.Create,ToolBarItem.Cancel,ToolBarItem.Exit});
	}
	
	private void wlhf(){
		try{
			String z0 = "";
			if(!SLOT_SPC_CHARGE.JCState.getSelectedItem().equals("")){
				StringTokenizer st = new StringTokenizer(String.valueOf(SLOT_SPC_CHARGE.JCState.getSelectedItem()),"(");
				z0 = st.nextToken();
				String z1 = st.nextToken();
			}
			
			Vector arg = new Vector();
			arg.add(z0);
			BaseServletAgent bsa = new BaseServletAgent(uiVO);
			BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
			bvo.setPackageName("SFIS_SPC_CHARGE_PEGA");
			bvo.setFunctionName("Query_Ping");
			ResultVO rvo = bsa.doFunc(bvo);
			Vector result = rvo.getData();
			Hashtable row = null;
			if(result.size()>1){
				row = (Hashtable)result.get(1);
				JTSlname.setText(CloneArray_ChangeStr.NulltoSpace(row.get("SLNAME")));
				JTStartT.setText(CloneArray_ChangeStr.NulltoSpace(row.get("SST")));
				JTEndT.setText(CloneArray_ChangeStr.NulltoSpace(row.get("EET")));
				JTPing.setText(CloneArray_ChangeStr.NulltoSpace(row.get("PING")));
				JBChange_No.setVisible(true);
				PDTimeST.setVisible(true);
				PDTimeED.setVisible(false);
			}else{
				JTSlname.setText("");
				JTStartT.setText("");
				JTEndT.setText("");
				JTPing.setText("");
				JBChange_No.setVisible(false);
				PDTimeST.setVisible(false);
				PDTimeED.setVisible(false);
			}
		}catch(Exception e1){
			e1.printStackTrace();
			exUtl.ExceptionNotify(e1,"SLOT_SPC_CHARGE Listener ",this.getClass().toString(),VERSION);
		}
		
	}
	
	private static Date addDate(Date date,long day) throws ParseException {
		long time = date.getTime(); // �o����w������@��?
		day = day*24*60*60*1000; // �n�[�W����???���@��?
		time+=day; // �ۥ[�o��s���@��?
		return new Date(time); // ?�@��???�����
	}
	
	private void chslstate(){
		try{
			if(JTPing.getText().trim().equals("")||PDTimeST.getText().equals("")){
				JOptionPane.showMessageDialog(null, "�бN�����W�v�B�دD�ɶ���g!");
				return;
			}
			
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date d1 = df.parse(PDTimeST.getText());
			Date d2 = addDate(d1,Integer.parseInt(JTPing.getText().trim()));
			String time2 = df.format(d2);
			
			
			String z0 = "";
			if(!SLOT_SPC_CHARGE.JCState.getSelectedItem().equals("")){
				StringTokenizer st = new StringTokenizer(String.valueOf(SLOT_SPC_CHARGE.JCState.getSelectedItem()),"(");
				z0 = st.nextToken();
				String z1 = st.nextToken();
			}
			
			Vector arg = new Vector();
			arg.add(z0);
			arg.add(PDTimeST.getText().substring(0,PDTimeST.getText().length()));
			arg.add(time2);
			arg.add(JTPing.getText().trim());
			BaseServletAgent bsa = new BaseServletAgent(uiVO);
			BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
			bvo.setPackageName("SFIS_SPC_CHARGE_PEGA");
			bvo.setFunctionName("Update_State");
			ResultVO rvo = bsa.doFunc(bvo);
			
			wlhf();
		}catch(Exception e1){
			e1.printStackTrace();
			exUtl.ExceptionNotify(e1,"SLOT_SPC_CHARGE Listener ",this.getClass().toString(),VERSION);
		}
	}
	private void CheckChange(){
		try{
			Vector arg = new Vector();
			BaseServletAgent bsa = new BaseServletAgent(uiVO);
			BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
			bvo.setPackageName("SFIS_SPC_CHARGE_PEGA");
			bvo.setFunctionName("Query_EmailTime");
			ResultVO rvo = bsa.doFunc(bvo);
			Vector result = rvo.getData();
			Hashtable row = null;
			if(result.size() > 1){
				for(int n = 1;n <= (result.size() - 1);n++){
					row = (Hashtable)result.get(n);
					String dt1 = CloneArray_ChangeStr.NulltoSpace(row.get("EET"));
					String dt2 = CloneArray_ChangeStr.NulltoSpace(row.get("MMT"));
					Date date = new Date();
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					String nowday = df.format(date);
					if(!dt1.equals("")){
						Date d1 = df.parse(dt1);
						Date dn = df.parse(nowday);
					
						long diff = d1.getTime() - dn.getTime();
						long days = diff / (1000 * 60 * 60 * 24);
					
						if(days < 2 && dt2.equals("")){
							String mail_Context = CloneArray_ChangeStr.NulltoSpace(row.get("SLOTNO")) + "���� " + CloneArray_ChangeStr.NulltoSpace(row.get("SLNAME")) 
							+ " �دD�ɶ� " + CloneArray_ChangeStr.NulltoSpace(row.get("SST")) + " �w�p���Ѯɶ� " + CloneArray_ChangeStr.NulltoSpace(row.get("EET")) + 
							" �����W�v " + CloneArray_ChangeStr.NulltoSpace(row.get("PING")) + "�ѡA�S������";
							(new WebServiceUtil()).send_mail(uiVO,"SPC","SPC_���@���Ѵ���",mail_Context);
							Vector uparg = new Vector();
							uparg.add(CloneArray_ChangeStr.NulltoSpace(row.get("SLOTNO")));
							BaseServletAgent upbsa = new BaseServletAgent(uiVO);
							BaseVO upbvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,uparg);
							upbvo.setPackageName("SFIS_SPC_CHARGE_PEGA");
							upbvo.setFunctionName("Update_EmailTime");
							ResultVO uprvo = upbsa.doFunc(upbvo);
						}
						if(!dt2.equals("")){
							Date d2 = df.parse(dt2);
							long diff2 = dn.getTime() - d2.getTime();
							long days2 = diff2 / (1000 * 60 * 60 * 24);
							if(days < 2 && days2 > 0){
								String mail_Context = CloneArray_ChangeStr.NulltoSpace(row.get("SLOTNO")) + "���� " + CloneArray_ChangeStr.NulltoSpace(row.get("SLNAME")) 
								+ " �دD�ɶ� " + CloneArray_ChangeStr.NulltoSpace(row.get("SST")) + " �w�p���Ѯɶ� " + CloneArray_ChangeStr.NulltoSpace(row.get("EET")) + 
								" �����W�v " + CloneArray_ChangeStr.NulltoSpace(row.get("PING")) + "�ѡA�S������";
								(new WebServiceUtil()).send_mail(uiVO,"SPC","SPC_���@���Ѵ���",mail_Context);
								Vector uparg = new Vector();
								uparg.add(CloneArray_ChangeStr.NulltoSpace(row.get("SLOTNO")));
								BaseServletAgent upbsa = new BaseServletAgent(uiVO);
								BaseVO upbvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,uparg);
								upbvo.setPackageName("SFIS_SPC_CHARGE_PEGA");
								upbvo.setFunctionName("Update_EmailTime");
								ResultVO uprvo = upbsa.doFunc(upbvo);
							}
						}
					}
				}
			}
		}catch(Exception e1){
			e1.printStackTrace();
			exUtl.ExceptionNotify(e1,"SLOT_SPC_CHARGE Listener ",this.getClass().toString(),VERSION);
		}
		
	}
	
	public class BtnCNListener implements ActionListener {
		private JButton theBtn; 
		public BtnCNListener(){
			super();
		}
		public BtnCNListener(JButton theBtn){
			this();
			this.theBtn = theBtn;

		}
		public void actionPerformed(ActionEvent e) {
			chslstate();
		}
	}
	
	public class BtncommiteListener implements ActionListener {
		private JButton theBtn; 
		public BtncommiteListener(){
			super();
		}
		public BtncommiteListener(JButton theBtn){
			this();
			this.theBtn = theBtn;

		}
		public void actionPerformed(ActionEvent e) {
			if(String.valueOf(JCcheck.getSelectedItem()).equals("")){
				JOptionPane.showMessageDialog(null, "�п�ܶq������!");
				return;
			}
			if(JTtemp.getText().trim().equals("")){
				JOptionPane.showMessageDialog(null, "�ж�g��ڭ�!");
				return;
			}
			if((JTuptemp.getText().trim().equals(""))||(JTdwtemp.getText().trim().equals(""))){
				JOptionPane.showMessageDialog(null, "�W�U�����ର��!");
				return;
			}
			FxhBg();
		}
	}
	public class BtnexceptListener implements ActionListener {
		private JButton theBtn; 
		public BtnexceptListener(){
			super();
		}
		public BtnexceptListener(JButton theBtn){
			this();
			this.theBtn = theBtn;

		}
		public void actionPerformed(ActionEvent e) {
			if(String.valueOf(JCcheck.getSelectedItem()).equals("")){
				JOptionPane.showMessageDialog(null, "�п�ܶq������!");
				return;
			}
			if(PDEndTime.getText().equals("")){
				JOptionPane.showMessageDialog(null, "�п�ܶץX���!");
				return;
			}
			XYDataset dataset = createXYDataset();
			//JFreeChart freeChart = createChart(dataset);
			String airmax = String.valueOf(JCState.getSelectedItem()) + "����"+ String.valueOf(JCcheck.getSelectedItem()) + PDEndTime.getText().substring(0,10);
			if(!PD_EndTime.getText().equals("")){
				airmax += "��" + PD_EndTime.getText().substring(0,10);
			}
			JFreeChart freeChart = ChartFactory.createTimeSeriesChart(
				airmax, "�ɶ�", String.valueOf(JCcheck.getSelectedItem()), dataset, true,
				true, false);
			Font font1 = new Font("SimSun", 10, 20); // ?�w�r�^�B?���B�r?
			freeChart.getTitle().setFont(font1);
			XYPlot categoryplot = (XYPlot) freeChart.getPlot();
			Font font2 = new Font("SimSun", 10, 16); // ?�w�r�^�B?���B�r?
			categoryplot.getDomainAxis().setLabelFont(font2);// ��?�_??�βz��?X?
			categoryplot.getRangeAxis().setLabelFont(font2);// ��?�_??�z��?Y?
			Font font3 = new Font("SimSun", 10, 12); // ?�w�r�^�B?���B�r?
			freeChart.getLegend().setItemFont(font3);// �̤U��
			
			try{
				if(PD_EndTime.getText().equals("")){
					String vdate = SLOT_SPC_CHARGE.PDEndTime.getText().substring(0,10);
					String z0 = "";
					if(!SLOT_SPC_CHARGE.JCState.getSelectedItem().equals("")){
						StringTokenizer st = new StringTokenizer(String.valueOf(SLOT_SPC_CHARGE.JCState.getSelectedItem()),"(");
						z0 = st.nextToken();
						String z1 = st.nextToken();
					}
					
					Vector arg = new Vector();
					arg.add(z0);
					arg.add(SLOT_SPC_CHARGE.JCcheck.getSelectedItem());
					arg.add(vdate);
					BaseServletAgent bsa = new BaseServletAgent(uiVO);
					BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
					bvo.setPackageName("SFIS_SPC_CHARGE_PEGA");
					bvo.setFunctionName("Query_excel");	
					ResultVO rvo = bsa.doFunc(bvo);
					vmark = rvo.getData();
				
					Vector uparg = new Vector();
					uparg.add(vdate);
					BaseServletAgent upbsa = new BaseServletAgent(uiVO);
					BaseVO upbvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,uparg);
					upbvo.setPackageName("SFIS_SPC_CHARGE_PEGA");
					upbvo.setFunctionName("Query_Allexcel");
					ResultVO uprvo = upbsa.doFunc(upbvo);
					vAllm = uprvo.getData();
				}else{
					String vdate = SLOT_SPC_CHARGE.PDEndTime.getText().substring(0,PDEndTime.getText().length());
					String vdatetwo = SLOT_SPC_CHARGE.PD_EndTime.getText().substring(0,PD_EndTime.getText().length());
					String z0 = "";
					if(!SLOT_SPC_CHARGE.JCState.getSelectedItem().equals("")){
						StringTokenizer st = new StringTokenizer(String.valueOf(SLOT_SPC_CHARGE.JCState.getSelectedItem()),"(");
						z0 = st.nextToken();
						String z1 = st.nextToken();
					}
					
					Vector arg = new Vector();
					arg.add(z0);
					arg.add(SLOT_SPC_CHARGE.JCcheck.getSelectedItem());
					arg.add(vdate);
					arg.add(vdatetwo);
					BaseServletAgent bsa = new BaseServletAgent(uiVO);
					BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
					bvo.setPackageName("SFIS_SPC_CHARGE_PEGA");
					bvo.setFunctionName("Query_excel_No");	
					ResultVO rvo = bsa.doFunc(bvo);
					vmark = rvo.getData();
					
					Vector uparg = new Vector();
					uparg.add(vdate);
					uparg.add(vdatetwo);
					BaseServletAgent upbsa = new BaseServletAgent(uiVO);
					BaseVO upbvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,uparg);
					upbvo.setPackageName("SFIS_SPC_CHARGE_PEGA");
					upbvo.setFunctionName("Query_Allexcel_No");
					ResultVO uprvo = upbsa.doFunc(upbvo);
					vAllm = uprvo.getData();
				}
				
			}catch(Exception e1){
				e1.printStackTrace();
				exUtl.ExceptionNotify(e1,"SLOT_SPC_CHARGE Listener ",this.getClass().toString(),VERSION);
			}
			saveAsFile(freeChart, "D:\\lineXY.png", 600, 400);
			//saveAsFile(AllfreeChart, "D:\\AlllineXY.png", 600, 400);
			ExportExcel.ggo();


			
		}
	}
	public class JCStateListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {  
			checkN();
			wlhf();
		}
	}
	public class JCcheckListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {  
			spchf();
		}
	}
	private void ExceptExcel(){
		try{
			String vdate = PDEndTime.getText().substring(0,10);
			String z0 = "";
			if(!SLOT_SPC_CHARGE.JCState.getSelectedItem().equals("")){
				StringTokenizer st = new StringTokenizer(String.valueOf(SLOT_SPC_CHARGE.JCState.getSelectedItem()),"(");
				z0 = st.nextToken();
				String z1 = st.nextToken();
			}
			Vector arg = new Vector();
			arg.add(z0);
			arg.add(JCcheck.getSelectedItem());
			arg.add(vdate);
			BaseServletAgent bsa = new BaseServletAgent(uiVO);
			BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
			bvo.setPackageName("SFIS_SPC_CHARGE_PEGA");
			bvo.setFunctionName("Query_excel");	
			ResultVO rvo = bsa.doFunc(bvo);
		  	Vector result = rvo.getData();
			Hashtable row = null;
			if(result.size() > 1){
				for(int n = 1;n <= (result.size() - 1);n++){
					row = (Hashtable)result.get(n);
					JCState.addItem(CloneArray_ChangeStr.NulltoSpace(row.get("SLOTNO")));
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
			exUtl.ExceptionNotify(e,"SLOT_SPC_CHARGE ExceptExcel ",this.getClass().toString(),VERSION);
		}
	}
	public static void saveAsFile(JFreeChart chart, String outputPath,      

 

            int weight, int height) {      

 

        FileOutputStream out = null;      

 

        try {      

 

            File outFile = new File(outputPath);      

 

            if (!outFile.getParentFile().exists()) {      

 

                outFile.getParentFile().mkdirs();      

 

            }      

 

            out = new FileOutputStream(outputPath);      

 

            // �O�s?PNG      

 

            ChartUtilities.writeChartAsPNG(out, chart, weight, height);      

 

            // �O�s?JPEG      

 

            // ChartUtilities.writeChartAsJPEG(out, chart, weight, height);      

 

            out.flush();      

 

        } catch (FileNotFoundException e) {      

 

            e.printStackTrace();      

 

        } catch (IOException e) {      

 

            e.printStackTrace();      

 

        } finally {      

 

            if (out != null) {      

 

                try {      

 

                    out.close();      

                } catch (IOException e) {      

                    // do nothing      

                }      

            }      

        }      

    }      

 

    // ���uXYDataset?��JFreeChart?�H      

    public static JFreeChart createChart(XYDataset dataset,String Xname) {      

        // ?��JFreeChart?�H�GChartFactory.createXYLineChart      

        /*JFreeChart jfreechart = ChartFactory.createXYLineChart("XYLine Chart Demo", // ??      

                "�ɶ�", // categoryAxisLabel �]category?�A??�AX???�^      

                String.valueOf(JCcheck.getSelectedItem()), // valueAxisLabel�]value?�A??�AY?��??�^      

                dataset, // dataset      

                PlotOrientation.VERTICAL,   

                true, // legend      

                false, // tooltips      

                false); // URLs      

 

        // �ϥ�CategoryPlot?�m�U��??�C�H�U?�m�i�H�ٲ��C      

        XYPlot plot = (XYPlot) jfreechart.getPlot();      

        // �I���� �z����      

        plot.setBackgroundAlpha(0.5f);      

        // �e���� �z����      

        plot.setForegroundAlpha(0.5f);*/      

        // �䥦?�m�i�H?��XYPlot?      

		JFreeChart jfreechart = ChartFactory.createTimeSeriesChart(
		Xname, "�ɶ�", String.valueOf(JCcheck.getSelectedItem()), dataset, true,true, false);

        return jfreechart;      

    }     

 

    /**     

     * ?��XYDataset?�H     

     *      

     */     

 

    private XYDataset createXYDataset() {

        XYSeries xyseries1 = new XYSeries("��ڭ�");
		XYSeries xyseries2 = new XYSeries("����");
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		TimeSeries timeSeries1 = new TimeSeries("��ڭ�", Minute.class);
		TimeSeries timeSeries2 = new TimeSeries("����", Minute.class);
		TimeSeries timeSeries3 = new TimeSeries("�W��", Minute.class);
		TimeSeries timeSeries4 = new TimeSeries("�U��", Minute.class);
		
		try{
			Vector result = null;
			if(PD_EndTime.getText().equals("")){
				String vdate = PDEndTime.getText().substring(0,10);
				String z0 = "";
				if(!SLOT_SPC_CHARGE.JCState.getSelectedItem().equals("")){
					StringTokenizer st = new StringTokenizer(String.valueOf(SLOT_SPC_CHARGE.JCState.getSelectedItem()),"(");
					z0 = st.nextToken();
					String z1 = st.nextToken();
				}
				Vector arg = new Vector();
				arg.add(z0);
				arg.add(JCcheck.getSelectedItem());
				arg.add(vdate);
				BaseServletAgent bsa = new BaseServletAgent(uiVO);
				BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
				bvo.setPackageName("SFIS_SPC_CHARGE_PEGA");
				bvo.setFunctionName("Query_excel");	
				ResultVO rvo = bsa.doFunc(bvo);
				result = rvo.getData();
			}else{
				String vdate = SLOT_SPC_CHARGE.PDEndTime.getText().substring(0,SLOT_SPC_CHARGE.PDEndTime.getText().length());
				String vdatetwo = SLOT_SPC_CHARGE.PD_EndTime.getText().substring(0,SLOT_SPC_CHARGE.PD_EndTime.getText().length());
				String z0 = "";
				if(!SLOT_SPC_CHARGE.JCState.getSelectedItem().equals("")){
					StringTokenizer st = new StringTokenizer(String.valueOf(SLOT_SPC_CHARGE.JCState.getSelectedItem()),"(");
					z0 = st.nextToken();
					String z1 = st.nextToken();
				}
				Vector arg = new Vector();
				arg.add(z0);
				arg.add(SLOT_SPC_CHARGE.JCcheck.getSelectedItem());
				arg.add(vdate);
				arg.add(vdatetwo);
				BaseServletAgent bsa = new BaseServletAgent(uiVO);
				BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
				bvo.setPackageName("SFIS_SPC_CHARGE_PEGA");
				bvo.setFunctionName("Query_excel_No");	
				ResultVO rvo = bsa.doFunc(bvo);
				result = rvo.getData();
			}
			
			Hashtable row = null;
			if(result.size() > 1){
				for(int n = 1;n <= (result.size() - 1);n++){
					row = (Hashtable)result.get(n);
					xyseries1.add(1,Double.parseDouble(CloneArray_ChangeStr.NulltoSpace(row.get("NOWVALUE"))));
					xyseries2.add(2,Double.parseDouble(JTsdtemp.getText().trim()));
					timeSeries1.addOrUpdate(new Minute(Integer.parseInt(row.get("MSI").toString()), new Hour(Integer.parseInt(row.get("HH").toString()), new Day(Integer.parseInt(row.get("DD").toString()), Integer.parseInt(row.get("MM").toString()), Integer.parseInt(row.get("YY").toString())))), Double.parseDouble(CloneArray_ChangeStr.NulltoSpace(row.get("NOWVALUE"))));
					timeSeries2.addOrUpdate(new Minute(Integer.parseInt(row.get("MSI").toString()), new Hour(Integer.parseInt(row.get("HH").toString()), new Day(Integer.parseInt(row.get("DD").toString()), Integer.parseInt(row.get("MM").toString()), Integer.parseInt(row.get("YY").toString())))), Double.parseDouble(CloneArray_ChangeStr.NulltoSpace(row.get("SDLIMTER"))));
					timeSeries3.addOrUpdate(new Minute(Integer.parseInt(row.get("MSI").toString()), new Hour(Integer.parseInt(row.get("HH").toString()), new Day(Integer.parseInt(row.get("DD").toString()), Integer.parseInt(row.get("MM").toString()), Integer.parseInt(row.get("YY").toString())))), Double.parseDouble(CloneArray_ChangeStr.NulltoSpace(row.get("UPLIMTER"))));
					timeSeries4.addOrUpdate(new Minute(Integer.parseInt(row.get("MSI").toString()), new Hour(Integer.parseInt(row.get("HH").toString()), new Day(Integer.parseInt(row.get("DD").toString()), Integer.parseInt(row.get("MM").toString()), Integer.parseInt(row.get("YY").toString())))), Double.parseDouble(CloneArray_ChangeStr.NulltoSpace(row.get("DWLIMTER"))));
					//System.out.println(CloneArray_ChangeStr.NulltoSpace(row.get("SLTIME")));
					//System.out.println(new Minute(Integer.parseInt(row.get("MSI").toString()), new Hour(Integer.parseInt(row.get("HH").toString()), new Day(Integer.parseInt(row.get("DD").toString()), Integer.parseInt(row.get("MM").toString()), Integer.parseInt(row.get("YY").toString())))));
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
			exUtl.ExceptionNotify(e,"SLOT_SPC_CHARGE ExceptExcel ",this.getClass().toString(),VERSION);
		}

        XYSeriesCollection xySeriesCollection = new XYSeriesCollection();

        //xySeriesCollection.addSeries(xyseries1);

        //xySeriesCollection.addSeries(xyseries2);
		
		dataset.addSeries(timeSeries1);
		dataset.addSeries(timeSeries2);
		dataset.addSeries(timeSeries3);
		dataset.addSeries(timeSeries4);

        //return xySeriesCollection;
		return dataset;

    }
	
	private void pickC(){
		try{
			Vector arg = new Vector();
			BaseServletAgent bsa = new BaseServletAgent(uiVO);
			BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
			bvo.setPackageName("SFIS_SPC_CHARGE_PEGA");
			bvo.setFunctionName("Query_state");	
			ResultVO rvo = bsa.doFunc(bvo);
		  	Vector result = rvo.getData();
			Hashtable row = null;
			JCState.removeAllItems();
			JCState.addItem("");
			for(int n = 1;n <= (result.size() - 1);n++){
				row = (Hashtable)result.get(n);
				JCState.addItem(CloneArray_ChangeStr.NulltoSpace(row.get("SLOTNO")) + "(" + CloneArray_ChangeStr.NulltoSpace(row.get("SLNAME")) + ")");
			}
			
		}catch(Exception e){
			e.printStackTrace();
			exUtl.ExceptionNotify(e,"SLOT_SPC_CHARGE pickC ",this.getClass().toString(),VERSION);
		}
	}
	private void checkN(){
		try{
			jta.setText("");
			JTtemp.setText("");
			JTuptemp.setText("");
			JTdwtemp.setText("");
			JTsdtemp.setText("");
			JLbtemp.setText("");
			String z0 = "";
			if(!SLOT_SPC_CHARGE.JCState.getSelectedItem().equals("")){
				StringTokenizer st = new StringTokenizer(String.valueOf(SLOT_SPC_CHARGE.JCState.getSelectedItem()),"(");
				z0 = st.nextToken();
				String z1 = st.nextToken();
			}
			
			Vector arg = new Vector();
			arg.add(z0);
			BaseServletAgent bsa = new BaseServletAgent(uiVO);
			BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
			bvo.setPackageName("SFIS_SPC_CHARGE_PEGA");
			bvo.setFunctionName("Query_check");	
			ResultVO rvo = bsa.doFunc(bvo);
		  	Vector result = rvo.getData();
			Hashtable row = null;
			JCcheck.removeAllItems();
			JCcheck.addItem("");
			if(result.size() > 0){
				for(int n = 1;n <= (result.size() - 1);n++){
					row = (Hashtable)result.get(n);
					JCcheck.addItem(CloneArray_ChangeStr.NulltoSpace(row.get("SLTYME")));
					jta.append(CloneArray_ChangeStr.NulltoSpace(row.get("SLTYME")) + "\n");
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
			exUtl.ExceptionNotify(e,"SLOT_SPC_CHARGE checkN ",this.getClass().toString(),VERSION);
		}
	}
	private void spchf(){
		try{
			String z0 = "";
			if(!SLOT_SPC_CHARGE.JCState.getSelectedItem().equals("")){
				StringTokenizer st = new StringTokenizer(String.valueOf(SLOT_SPC_CHARGE.JCState.getSelectedItem()),"(");
				z0 = st.nextToken();
				String z1 = st.nextToken();
			}
			Vector arg = new Vector();
			arg.add(z0);
			arg.add(JCcheck.getSelectedItem());
			BaseServletAgent bsa = new BaseServletAgent(uiVO);
			BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
			bvo.setPackageName("SFIS_SPC_CHARGE_PEGA");
			bvo.setFunctionName("Query_spchf");	
			ResultVO rvo = bsa.doFunc(bvo);
		  	Vector result = rvo.getData();
			Hashtable row = null;
			if(result.size() > 0){
				for(int n = 1;n <= (result.size() - 1);n++){
					row = (Hashtable)result.get(n);
					JTuptemp.setText(CloneArray_ChangeStr.NulltoSpace(row.get("UPLIMTER")));
					JTdwtemp.setText(CloneArray_ChangeStr.NulltoSpace(row.get("DWLIMTER")));
					JTsdtemp.setText(CloneArray_ChangeStr.NulltoSpace(row.get("SDLIMTER")));
					JLbtemp.setText(CloneArray_ChangeStr.NulltoSpace(row.get("SLBIT")));
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
			exUtl.ExceptionNotify(e,"SLOT_SPC_CHARGE spchf ",this.getClass().toString(),VERSION);
		}
	}
	private void FxhBg(){
		try{
			String ko = null;
			if((Double.parseDouble(JTtemp.getText().trim()) > Double.parseDouble(JTuptemp.getText().trim()))||
			(Double.parseDouble(JTtemp.getText().trim()) < Double.parseDouble(JTdwtemp.getText().trim()))){
				ko = "NG";
				String mail_Context = String.valueOf(JCState.getSelectedItem()) + "����\n" + JTSlname.getText().trim() + " " + String.valueOf(JCcheck.getSelectedItem()) + " : \n��ڭ� : " + JTtemp.getText().trim().toString() + 
				"\n�W�� : " + JTuptemp.getText().trim().toString() + "\n�U�� : " + JTdwtemp.getText().trim().toString() + "\n���ŦX�з�";
				String groupS = "SPCDW";
				if(JCagain.isSelected()){
					groupS = "SPC";
				}
				(new WebServiceUtil()).send_mail(uiVO,groupS,"SPC_���@�q��",mail_Context);
			}else{
				ko = "OK";
			}
			String z0 = "";
			if(!SLOT_SPC_CHARGE.JCState.getSelectedItem().equals("")){
				StringTokenizer st = new StringTokenizer(String.valueOf(SLOT_SPC_CHARGE.JCState.getSelectedItem()),"(");
				z0 = st.nextToken();
				String z1 = st.nextToken();
			}
			
			Vector arg = new Vector();
			arg.add(z0);
			arg.add(JTtemp.getText().trim());
			arg.add(JTuptemp.getText().trim());
			arg.add(JTdwtemp.getText().trim());
			arg.add(JTsdtemp.getText().trim());
			arg.add(JCcheck.getSelectedItem());
			arg.add(ko);
			BaseServletAgent bsa = new BaseServletAgent(uiVO);
			BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
			bvo.setPackageName("SFIS_SPC_CHARGE_PEGA");
			if(JCagain.isSelected()){
				bvo.setFunctionName("Insert_FxhBg_Ag");
			}else{
				bvo.setFunctionName("Insert_FxhBg");
			}
			ResultVO rvo = bsa.doFunc(bvo);
		  	Vector result = rvo.getData();
			Hashtable row = null;
			if(result.size() > 0){
				row = (Hashtable)result.elementAt(1);
				jta.append(JCcheck.getSelectedItem() + "~~~~(" + JTtemp.getText().trim() + ")~~~~" + CloneArray_ChangeStr.NulltoSpace(row.get("MISS")) + "\n");
			}
			
		}catch(Exception e){
			e.printStackTrace();
			exUtl.ExceptionNotify(e,"SLOT_SPC_CHARGE FxhBg ",this.getClass().toString(),VERSION);
		}
	}
	/*private int Input_Check(){
		int a = 0;
		if(JTPart_No.getText().getBytes().length > 20){
			JOptionPane.showMessageDialog(null,"Vendor P/N�L�� !");
			a++;
			return a;
		}
		else if(JTCust_Part_No.getText().getBytes().length > 20){
			JOptionPane.showMessageDialog(null,"Pega P/N�L�� !");
			a++;
			return a;
		}
		else if(JTBatch_No.getText().getBytes().length > 6){
			JOptionPane.showMessageDialog(null,"Batch�L�� !");
			a++;
			return a;
		}
		else if(!JTQty.getText().matches("[0-9]*")){
			JOptionPane.showMessageDialog(null,"QTY�u���J�Ʀr !");
			a++;
			return a;
		}
		else if(JTMemo.getText().getBytes().length > 100){
			JOptionPane.showMessageDialog(null,"Memo�L�� !");
			a++;
			return a;
		}
		return a;
	}*/
	
    @Override
	public void modify(){
		
	}
	public void create(){
		
	}
	public void save(){
		
	}
	@Override
	public void delete(){
		
	}
	@Override
	public void cancel() {
		
	}
	
	@Override
	public void singleQuery() {
		
	}
	
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
class ExportExcel<T> {
 

   public void exportExcel(Collection<T> dataset, OutputStream out) {
      exportExcel("SPC Test", null, dataset, out, "yyyy-MM-dd");
   }
 

   public void exportExcel(String[] headers, Collection<T> dataset,
         OutputStream out) {
      exportExcel("SPC Test", headers, dataset, out, "yyyy-MM-dd");
   }
 

   public void exportExcel(String[] headers, Collection<T> dataset,
         OutputStream out, String pattern) {
      exportExcel("SPC Test", headers, dataset, out, pattern);
   }
 

   /**
    * ?�O�@?�q�Ϊ���k�A�Q�ΤFJAVA���Ϯg���A�i�H?��m�bJAVA���X���}�B��?�@�w?��?�u�HEXCEL ���Φ�?�X����wIO??�W
    * 
    * @param title
    *            ���??�W
    * @param headers
    *            ���?�ʦC�W??
    * @param dataset
    *            �ݭn?�ܪ�?�u���X,���X���@�w�n��m�ŦXjavabean?�檺?��?�H�C����k�����
    *            javabean?�ʪ�?�u?������?�u?����String,Date,byte[](?��?�u)
    * @param out
    *            �O?�X????���y?�H�A�i�H?EXCEL��??�X�쥻�a���Ϊ��I?��
    * @param pattern
    *            �p�G��???�u�A?�w?�X�榡�C�q??"yyy-MM-dd"
    */
   @SuppressWarnings("unchecked")
   public void exportExcel(String title, String[] headers,
         Collection<T> dataset, OutputStream out, String pattern) {
      // ?���@?�u�@��
      HSSFWorkbook workbook = new HSSFWorkbook();
      // �ͦ��@?���
      HSSFSheet sheet = workbook.createSheet(title);
      // ?�m����q?�C?��?15?�r?
      sheet.setDefaultColumnWidth((short) 15);
      // �ͦ��@??��
      HSSFCellStyle style = workbook.createCellStyle();
      // ?�m?��?��
      style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
      style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
      style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
      style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
      style.setBorderRight(HSSFCellStyle.BORDER_THIN);
      style.setBorderTop(HSSFCellStyle.BORDER_THIN);
      style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
      // �ͦ��@?�r�^
      HSSFFont font = workbook.createFont();
      font.setColor(HSSFColor.VIOLET.index);
      font.setFontHeightInPoints((short) 12);
      font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
      // ��r�^?�Ψ�?�e��?��
      style.setFont(font);
      // �ͦ��}?�m�t�@??��
      HSSFCellStyle style2 = workbook.createCellStyle();
      style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
      style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
      style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
      style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
      style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
      style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
      style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
      style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
      // �ͦ��t�@?�r�^
      HSSFFont font2 = workbook.createFont();
      font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
      // ��r�^?�Ψ�?�e��?��
      style2.setFont(font2);
      
      // ?���@???��??�޲z��
      HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
      // �w?�`?���j�p�M��m,??��?
      HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
      // ?�m�`??�e
      comment.setString(new HSSFRichTextString("�i�H�bPOI���K�[�`?�I"));
      // ?�m�`?�@�̡A?��?��?��?����W�O�i�H�b???���ݨ�??�e.
      comment.setAuthor("leno");
 

      //?�ͪ��??��
      HSSFRow row = sheet.createRow(0);
      for (short i = 0; i < headers.length; i++) {
         HSSFCell cell = row.createCell(i);
         cell.setCellStyle(style);
         HSSFRichTextString text = new HSSFRichTextString(headers[i]);
         cell.setCellValue(text);
      }
 

      //�M?���X?�u�A?��?�u��
      Iterator<T> it = dataset.iterator();
      int index = 0;
      while (it.hasNext()) {
         index++;
         row = sheet.createRow(index);
         T t = (T) it.next();
         //�Q�ΤϮg�A���ujavabean?�ʪ����Z?�ǡA???��getXxx()��k�o��?�ʭ�
         Field[] fields = t.getClass().getDeclaredFields();
         for (short i = 0; i < fields.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(style2);
            Field field = fields[i];
            String fieldName = field.getName();
            String getMethodName = "get"
                   + fieldName.substring(0, 1).toUpperCase()
                   + fieldName.substring(1);
            try {
                Class tCls = t.getClass();
                Method getMethod = tCls.getMethod(getMethodName,
                      new Class[] {});
                Object value = getMethod.invoke(t, new Object[] {});
                //�P?�Ȫ�?���Z?��?��?��??
                String textValue = null;
//              if (value instanceof Integer) {
//                 int intValue = (Integer) value;
//                 cell.setCellValue(intValue);
//              } else if (value instanceof Float) {
//                 float fValue = (Float) value;
//                 textValue = new HSSFRichTextString(
//                       String.valueOf(fValue));
//                 cell.setCellValue(textValue);
//              } else if (value instanceof Double) {
//                 double dValue = (Double) value;
//                 textValue = new HSSFRichTextString(
//                       String.valueOf(dValue));
//                 cell.setCellValue(textValue);
//              } else if (value instanceof Long) {
//                 long longValue = (Long) value;
//                 cell.setCellValue(longValue);
//              } 
                if (value instanceof Boolean) {
                   boolean bValue = (Boolean) value;
                   textValue = "�k";
                   if (!bValue) {
                      textValue ="�k";
                   }
                } else if (value instanceof Date) {
                   Date date = (Date) value;
                   SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                    textValue = sdf.format(date);
                }  else if (value instanceof byte[]) {
                   // ��?��?�A?�m�氪?60px;
                   row.setHeightInPoints(60);
                   // ?�m?���Ҧb�C?��?80px,�`�N?��?�쪺�@??��
                   sheet.setColumnWidth(i, (short) (35.7 * 80));
                   // sheet.autoSizeColumn(i);
                   byte[] bsValue = (byte[]) value;
                   HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,
                         1023, 255, (short) 10, index, (short) 10, index);
                   //anchor.setAnchorType(2);
                   patriarch.createPicture(anchor, workbook.addPicture(
                         bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
                } else{
                   //�䥦?�u?����?�@�r�Ŧ�???�z
                   textValue = value.toString();
                }
                //�p�G���O?��?�u�A�N�Q�Υ�?��?���P?textValue�O�_������?�r?��
                if(textValue!=null){
                   Pattern p = Pattern.compile("^//d+(//.//d+)?$");   
                   Matcher matcher = p.matcher(textValue);
                   if(matcher.matches()){
                      //�O?�r?�@double?�z
                      cell.setCellValue(Double.parseDouble(textValue));
                   }else{
                      HSSFRichTextString richString = new HSSFRichTextString(textValue);
                      HSSFFont font3 = workbook.createFont();
                      font3.setColor(HSSFColor.BLUE.index);
                      richString.applyFont(font3);
                      cell.setCellValue(richString);
                   }
                }
            } catch (SecurityException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                //�M�z?��
            }
         }
 

      }
      try {
         workbook.write(out);
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
 

   }
 

   public static void ggo() {
      // ???��
      ExportExcel<Student> ex = new ExportExcel<Student>();
      String[] headers = { "�Ѹ�","�ѦW", "���綵��", "��ڭ�", "�W��", "�U��",
            "����", "�q���ɶ�","�q���H�u��","�O�_�ŦX�з�","�ϼ��Ͷ�" };
      java.util.List<Student> dataset = new ArrayList<Student>();
	  try{
		    TimeSeriesCollection inataset = new TimeSeriesCollection();
			TimeSeries timeSeries1 = new TimeSeries("��ڭ�", Minute.class);
			TimeSeries timeSeries2 = new TimeSeries("����", Minute.class);
			TimeSeries timeSeries3 = new TimeSeries("�W��", Minute.class);
			TimeSeries timeSeries4 = new TimeSeries("�U��", Minute.class);
		
			Hashtable Allrow = null;
			String aga = "";
			if(SLOT_SPC_CHARGE.vAllm.size() > 1){
				for(int n = 1;n <= (SLOT_SPC_CHARGE.vAllm.size() - 1);n++){
					Allrow = (Hashtable)SLOT_SPC_CHARGE.vAllm.get(n);
					if(aga.equals("")){
						aga = Allrow.get("SLOTNO").toString() + Allrow.get("SLTYPE").toString();
					}
					if(aga.equals(Allrow.get("SLOTNO").toString() + Allrow.get("SLTYPE").toString())){
						timeSeries1.addOrUpdate(new Minute(Integer.parseInt(Allrow.get("MSI").toString()), new Hour(Integer.parseInt(Allrow.get("HH").toString()), new Day(Integer.parseInt(Allrow.get("DD").toString()), Integer.parseInt(Allrow.get("MM").toString()), Integer.parseInt(Allrow.get("YY").toString())))), Double.parseDouble(CloneArray_ChangeStr.NulltoSpace(Allrow.get("NOWVALUE"))));
						timeSeries2.addOrUpdate(new Minute(Integer.parseInt(Allrow.get("MSI").toString()), new Hour(Integer.parseInt(Allrow.get("HH").toString()), new Day(Integer.parseInt(Allrow.get("DD").toString()), Integer.parseInt(Allrow.get("MM").toString()), Integer.parseInt(Allrow.get("YY").toString())))), Double.parseDouble(CloneArray_ChangeStr.NulltoSpace(Allrow.get("SDLIMTER"))));
						timeSeries3.addOrUpdate(new Minute(Integer.parseInt(Allrow.get("MSI").toString()), new Hour(Integer.parseInt(Allrow.get("HH").toString()), new Day(Integer.parseInt(Allrow.get("DD").toString()), Integer.parseInt(Allrow.get("MM").toString()), Integer.parseInt(Allrow.get("YY").toString())))), Double.parseDouble(CloneArray_ChangeStr.NulltoSpace(Allrow.get("UPLIMTER"))));
						timeSeries4.addOrUpdate(new Minute(Integer.parseInt(Allrow.get("MSI").toString()), new Hour(Integer.parseInt(Allrow.get("HH").toString()), new Day(Integer.parseInt(Allrow.get("DD").toString()), Integer.parseInt(Allrow.get("MM").toString()), Integer.parseInt(Allrow.get("YY").toString())))), Double.parseDouble(CloneArray_ChangeStr.NulltoSpace(Allrow.get("DWLIMTER"))));
						Hashtable Map = null;
						if(n + 1 <= (SLOT_SPC_CHARGE.vAllm.size() - 1)){
							Map = (Hashtable)SLOT_SPC_CHARGE.vAllm.get(n + 1);
							if(!aga.equals(Map.get("SLOTNO").toString() + Map.get("SLTYPE").toString())){
								inataset.addSeries(timeSeries1);
								inataset.addSeries(timeSeries2);
								inataset.addSeries(timeSeries3);
								inataset.addSeries(timeSeries4);
							}
						}
						
						String airmax = Allrow.get("SLOTNO").toString() + "����" + Allrow.get("SLTYME").toString() + SLOT_SPC_CHARGE.PDEndTime.getText().substring(0,10);
						if(!SLOT_SPC_CHARGE.PD_EndTime.getText().equals("")){
							airmax += "��" + SLOT_SPC_CHARGE.PD_EndTime.getText().substring(0,10);
						}
						
						
							JFreeChart freeChart = SLOT_SPC_CHARGE.createChart(inataset,airmax);
							Font font1 = new Font("SimSun", 10, 20); // ?�w�r�^�B?���B�r?
							freeChart.getTitle().setFont(font1);
							XYPlot categoryplot = (XYPlot) freeChart.getPlot();
							Font font2 = new Font("SimSun", 10, 16); // ?�w�r�^�B?���B�r?
							categoryplot.getDomainAxis().setLabelFont(font2);// ��?�_??�βz��?X?
							categoryplot.getRangeAxis().setLabelFont(font2);// ��?�_??�z��?Y?
							Font font3 = new Font("SimSun", 10, 12); // ?�w�r�^�B?���B�r?
							freeChart.getLegend().setItemFont(font3);
							SLOT_SPC_CHARGE.saveAsFile(freeChart, "D:\\AlllineXY.png", 600, 400);
							BufferedInputStream Allbis = new BufferedInputStream(
							new FileInputStream("D://AlllineXY.png"));
							byte[] Allbuf = new byte[Allbis.available()];
							while ((Allbis.read(Allbuf)) != -1) {
								//
							}
							dataset.add(new Student(Allrow.get("SLOTNO").toString(),Allrow.get("SLNAME").toString(),Allrow.get("SLTYME").toString(),Allrow.get("NOWVALUE").toString(),
							Allrow.get("UPLIMTER").toString(),Allrow.get("DWLIMTER").toString(),Allrow.get("SDLIMTER").toString(),Allrow.get("SLTIME").toString(),Allrow.get("SLOP").toString(),Allrow.get("ISOK").toString(),Allbuf));
						if(!(n == (SLOT_SPC_CHARGE.vAllm.size() - 1))){
							System.out.println("12345");
							if(!aga.equals(Map.get("SLOTNO").toString() + Map.get("SLTYPE").toString())){
								aga = Map.get("SLOTNO").toString() + Map.get("SLTYPE").toString();
								timeSeries1.clear();
								timeSeries2.clear();
								timeSeries3.clear();
								timeSeries4.clear();
								inataset.removeAllSeries();
							}
							System.out.println("54321");
						}
						
					}
				}
			}
			
			System.out.println("HJKL") ;
			  

				  
      ExportExcel<Book> ex2 = new ExportExcel<Book>();
      String[] headers2 = { "�Ѹ�","�ѦW", "���綵��", "��ڭ�", "�W��", "�U��",
            "����", "�q���ɶ�","�q���H�u��","�O�_�ŦX�з�","�Ϫ��Ͷ�" };
      java.util.List<Book> dataset2 = new ArrayList<Book>();
         BufferedInputStream bis = new BufferedInputStream(
                new FileInputStream("D://lineXY.png"));
         byte[] buf = new byte[bis.available()];
         while ((bis.read(buf)) != -1) {
            //
         }
		
		Hashtable row = null;
		if(SLOT_SPC_CHARGE.vmark.size() > 1){
			for(int n = 1;n <= (SLOT_SPC_CHARGE.vmark.size() - 1);n++){
				row = (Hashtable)SLOT_SPC_CHARGE.vmark.get(n);
				dataset2.add(new Book(String.valueOf(SLOT_SPC_CHARGE.JCState.getSelectedItem()),row.get("SLNAME").toString(),String.valueOf(SLOT_SPC_CHARGE.JCcheck.getSelectedItem()),row.get("NOWVALUE").toString(),
				row.get("UPLIMTER").toString(),row.get("DWLIMTER").toString(),row.get("SDLIMTER").toString(),row.get("SLTIME").toString(),row.get("SLOP").toString(),row.get("ISOK").toString(),buf));
			}
		}
		
		 String outname = "D://�`�ƾ�" + SLOT_SPC_CHARGE.PDEndTime.getText().substring(0,10) + ".xls";
		 String out2name = "D://" + String.valueOf(SLOT_SPC_CHARGE.JCState.getSelectedItem()) + "����"+ String.valueOf(SLOT_SPC_CHARGE.JCcheck.getSelectedItem()) + SLOT_SPC_CHARGE.PDEndTime.getText().substring(0,10) + ".xls";
		 if(!SLOT_SPC_CHARGE.PD_EndTime.getText().equals("")){
			 outname = "D://�`�ƾ�" + SLOT_SPC_CHARGE.PDEndTime.getText().substring(0,10) + "��" + SLOT_SPC_CHARGE.PD_EndTime.getText().substring(0,10) + ".xls";
			 out2name = "D://" + String.valueOf(SLOT_SPC_CHARGE.JCState.getSelectedItem()) + "����"+ String.valueOf(SLOT_SPC_CHARGE.JCcheck.getSelectedItem()) + SLOT_SPC_CHARGE.PDEndTime.getText().substring(0,10) + "��" + SLOT_SPC_CHARGE.PD_EndTime.getText().substring(0,10) + ".xls";
		 }

         OutputStream out = new FileOutputStream(outname);
         OutputStream out2 = new FileOutputStream(out2name);
         ex.exportExcel(headers, dataset, out);
         ex2.exportExcel(headers2, dataset2, out2);
         out.close();
         out2.close();
         JOptionPane.showMessageDialog(null, "�ɥX���\!");
         System.out.println("excel�ɥX���\�I");
      } catch (FileNotFoundException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }
}
class Student {
 

       private String slotno;
	   private String slname;
	   private String sltyme;
	   private String nowvalue;
	   private String uplimter;
	   private String dwlimter;
	   private String sdlimter;
	   private String sltime;
	   private String slop;
	   private String isOK;
	   private byte[] preface;
 

   public Student() {
      super();
      // TODO Auto-generated constructor stub
   }
 

   public Student(String slotno,String slname, String sltyme, String nowvalue, String uplimter,
	         String dwlimter, String sdlimter,String sltime,String slop,String isOK,byte[] preface) {
	      super();
	      this.slotno = slotno;
		  this.slname = slname;
	      this.sltyme = sltyme;
	      this.nowvalue = nowvalue;
	      this.uplimter = uplimter;
	      this.dwlimter = dwlimter;
	      this.sdlimter = sdlimter;
		  this.sltime = sltime;
		  this.slop = slop;
		  this.isOK = isOK;
		  this.preface = preface;
	}
 

	   public String getSlotno() {
	      return slotno;
	   }
	 

	   public void setSlotno(String slotno) {
	      this.slotno = slotno;
	   }
	   
	   public String getSlname() {
	      return slname;
	   }
	 

	   public void setSlname(String slname) {
	      this.slname = slname;
	   }
	 

	   public String getSltyme() {
	      return sltyme;
	   }
	 

	   public void setSltyme(String sltyme) {
	      this.sltyme = sltyme;
	   }
	 

	   public String getNowvalue() {
	      return nowvalue;
	   }
	 

	   public void setNowvalue(String nowvalue) {
	      this.nowvalue = nowvalue;
	   }
	 

	   public String getUplimter() {
	      return uplimter;
	   }
	 

	   public void setUplimter(String uplimter) {
	      this.uplimter = uplimter;
	   }
	 

	   public String getDwlimter() {
	      return dwlimter;
	   }
	 

	   public void setDwlimter(String dwlimter) {
	      this.dwlimter = dwlimter;
	   }
	 

	   public String getSdlimter() {
	      return sdlimter;
	   }
	 

	   public void setSdlimter(String sdlimter) {
	      this.sdlimter = sdlimter;
	   }
		
		public String getSltime() {
	      return sltime;
	   }
	 

	   public void setSltime(String sltime) {
	      this.sltime = sltime;
	   }
	   
	   public String getSlop() {
	      return slop;
	   }
	 

	   public void setSlop(String slop) {
	      this.slop = slop;
	   }
	   
	   public String getIsOK() {
	      return isOK;
	   }
	 

	   public void setIsOK(String isOK) {
	      this.isOK = isOK;
	   }

	   public byte[] getPreface() {
	      return preface;
	   }
	 

	   public void setPreface(byte[] preface) {
	      this.preface = preface;
	   }

}
class Book {
	   private String slotno;
	   private String slname;
	   private String sltyme;
	   private String nowvalue;
	   private String uplimter;
	   private String dwlimter;
	   private String sdlimter;
	   private String sltime;
	   private String slop;
	   private String isOK;
	   private byte[] preface;
	 

	   public Book() {
	      super();
	   }
	 

	   public Book(String slotno,String slname, String sltyme, String nowvalue, String uplimter,
	         String dwlimter, String sdlimter,String sltime,String slop,String isOK, byte[] preface) {
	      super();
	      this.slotno = slotno;
		  this.slname = slname;
	      this.sltyme = sltyme;
	      this.nowvalue = nowvalue;
	      this.uplimter = uplimter;
	      this.dwlimter = dwlimter;
	      this.sdlimter = sdlimter;
		  this.sltime = sltime;
		  this.slop = slop;
		  this.isOK = isOK;
	      this.preface = preface;
	   }
	 

	   public String getSlotno() {
	      return slotno;
	   }
	 

	   public void setSlotno(String slotno) {
	      this.slotno = slotno;
	   }
	   
	   public String getSlname() {
	      return slname;
	   }
	 

	   public void setSlname(String slname) {
	      this.slname = slname;
	   }
	 

	   public String getSltyme() {
	      return sltyme;
	   }
	 

	   public void setSltyme(String sltyme) {
	      this.sltyme = sltyme;
	   }
	 

	   public String getNowvalue() {
	      return nowvalue;
	   }
	 

	   public void setNowvalue(String nowvalue) {
	      this.nowvalue = nowvalue;
	   }
	 

	   public String getUplimter() {
	      return uplimter;
	   }
	 

	   public void setUplimter(String uplimter) {
	      this.uplimter = uplimter;
	   }
	 

	   public String getDwlimter() {
	      return dwlimter;
	   }
	 

	   public void setDwlimter(String dwlimter) {
	      this.dwlimter = dwlimter;
	   }
	 

	   public String getSdlimter() {
	      return sdlimter;
	   }
	 

	   public void setSdlimter(String sdlimter) {
	      this.sdlimter = sdlimter;
	   }
		
		public String getSltime() {
	      return sltime;
	   }
	 

	   public void setSltime(String sltime) {
	      this.sltime = sltime;
	   }
	   
	   public String getSlop() {
	      return slop;
	   }
	 

	   public void setSlop(String slop) {
	      this.slop = slop;
	   }
	   
	   public String getIsOK() {
	      return isOK;
	   }
	 

	   public void setIsOK(String isOK) {
	      this.isOK = isOK;
	   }

	   public byte[] getPreface() {
	      return preface;
	   }
	 

	   public void setPreface(byte[] preface) {
	      this.preface = preface;
	   }
	}