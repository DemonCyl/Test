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
		
		JLabel JLState = new JLabel("請選擇槽號");
		JLState.setBounds(new Rectangle(10,30,70,25));
		add(JLState,null);
		
		JCState = new JComboBox();
		JCState.setSelectedItem("");
		JCState.setBounds(85,30,90,25);
		JCStateListener jcstalisten = new JCStateListener();
		JCState.addActionListener(jcstalisten);
		add(JCState);
		
		JLabel JLuplimter = new JLabel("上限",JLabel.CENTER);
		JLuplimter.setBounds(new Rectangle(180,30,50,25));
		add(JLuplimter,null);
		
		JLabel JLdwlimter = new JLabel("下限",JLabel.CENTER);
		JLdwlimter.setBounds(new Rectangle(230,30,50,25));
		add(JLdwlimter,null);
		
		JLabel JLsdlimter = new JLabel("中限",JLabel.CENTER);
		JLsdlimter.setBounds(new Rectangle(290,30,50,25));
		add(JLsdlimter,null);
		
		JLabel JLbit = new JLabel("單位",JLabel.CENTER);
		JLbit.setBounds(new Rectangle(350,30,50,25));
		add(JLbit,null);
		
		JCcheck = new JComboBox();
		JCcheck.setSelectedItem("");
		JCcheck.setBounds(5,75,85,25);
		JCcheckListener jcchelisten = new JCcheckListener();
		JCcheck.addActionListener(jcchelisten);
		add(JCcheck);
		
		JBcommite = new JButton("提交");
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
		
		JBexcept = new JButton("報表匯出");
		JBexcept.setBounds(350,350,130,60);
		BtnexceptListener BtEp = new BtnexceptListener();
		JBexcept.addActionListener(BtEp);
		add(JBexcept);
		
		JLabel JLexcept = new JLabel("請選擇匯出日期");
		JLexcept.setBounds(new Rectangle(120,380,150,25));
		add(JLexcept,null);
		
		JLabel JLSlname = new JLabel("槽名",JLabel.RIGHT);
		JLSlname.setBounds(new Rectangle(10,440,75,25));
		add(JLSlname,null);
		
		JTSlname = new JTextField();
		JTSlname.setBounds(new Rectangle(100,440,100,25));
		add(JTSlname,null);
		JTSlname.setEditable(false);
		
		JLabel JLStartT = new JLabel("建浴時間");
		JLStartT.setBounds(new Rectangle(210,440,75,25));
		add(JLStartT,null);
		
		JTStartT = new JTextField();
		JTStartT.setBounds(new Rectangle(295,440,100,25));
		add(JTStartT,null);
		JTStartT.setEditable(false);
		
		JLabel JLEndT = new JLabel("預計換槽時間");
		JLEndT.setBounds(new Rectangle(405,440,100,25));
		add(JLEndT,null);
		
		JTEndT = new JTextField();
		JTEndT.setBounds(new Rectangle(515,440,100,25));
		add(JTEndT,null);
		JTEndT.setEditable(false);
		
		JLabel JLPing = new JLabel("換槽頻率",JLabel.RIGHT);
		JLPing.setBounds(new Rectangle(10,475,75,25));
		add(JLPing,null);
		
		JTPing = new JTextField();
		JTPing.setBounds(new Rectangle(100,475,60,25));
		add(JTPing,null);
		//JTPing.setEditable(false);
		
		JLabel JLPingDay = new JLabel("天");
		JLPingDay.setBounds(new Rectangle(165,475,30,25));
		add(JLPingDay,null);
		
		JBChange_No = new JButton("換槽");
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
		
		JCagain = new JCheckBox("複測");
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
		long time = date.getTime(); // 得到指定日期的毫秒?
		day = day*24*60*60*1000; // 要加上的天???成毫秒?
		time+=day; // 相加得到新的毫秒?
		return new Date(time); // ?毫秒???成日期
	}
	
	private void chslstate(){
		try{
			if(JTPing.getText().trim().equals("")||PDTimeST.getText().equals("")){
				JOptionPane.showMessageDialog(null, "請將換槽頻率、建浴時間填寫!");
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
							String mail_Context = CloneArray_ChangeStr.NulltoSpace(row.get("SLOTNO")) + "號槽 " + CloneArray_ChangeStr.NulltoSpace(row.get("SLNAME")) 
							+ " 建浴時間 " + CloneArray_ChangeStr.NulltoSpace(row.get("SST")) + " 預計換槽時間 " + CloneArray_ChangeStr.NulltoSpace(row.get("EET")) + 
							" 換槽頻率 " + CloneArray_ChangeStr.NulltoSpace(row.get("PING")) + "天，特此提醒";
							(new WebServiceUtil()).send_mail(uiVO,"SPC","SPC_陽一換槽提醒",mail_Context);
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
								String mail_Context = CloneArray_ChangeStr.NulltoSpace(row.get("SLOTNO")) + "號槽 " + CloneArray_ChangeStr.NulltoSpace(row.get("SLNAME")) 
								+ " 建浴時間 " + CloneArray_ChangeStr.NulltoSpace(row.get("SST")) + " 預計換槽時間 " + CloneArray_ChangeStr.NulltoSpace(row.get("EET")) + 
								" 換槽頻率 " + CloneArray_ChangeStr.NulltoSpace(row.get("PING")) + "天，特此提醒";
								(new WebServiceUtil()).send_mail(uiVO,"SPC","SPC_陽一換槽提醒",mail_Context);
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
				JOptionPane.showMessageDialog(null, "請選擇量測類型!");
				return;
			}
			if(JTtemp.getText().trim().equals("")){
				JOptionPane.showMessageDialog(null, "請填寫實際值!");
				return;
			}
			if((JTuptemp.getText().trim().equals(""))||(JTdwtemp.getText().trim().equals(""))){
				JOptionPane.showMessageDialog(null, "上下限不能為空!");
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
				JOptionPane.showMessageDialog(null, "請選擇量測類型!");
				return;
			}
			if(PDEndTime.getText().equals("")){
				JOptionPane.showMessageDialog(null, "請選擇匯出日期!");
				return;
			}
			XYDataset dataset = createXYDataset();
			//JFreeChart freeChart = createChart(dataset);
			String airmax = String.valueOf(JCState.getSelectedItem()) + "號槽"+ String.valueOf(JCcheck.getSelectedItem()) + PDEndTime.getText().substring(0,10);
			if(!PD_EndTime.getText().equals("")){
				airmax += "至" + PD_EndTime.getText().substring(0,10);
			}
			JFreeChart freeChart = ChartFactory.createTimeSeriesChart(
				airmax, "時間", String.valueOf(JCcheck.getSelectedItem()), dataset, true,
				true, false);
			Font font1 = new Font("SimSun", 10, 20); // ?定字体、?型、字?
			freeChart.getTitle().setFont(font1);
			XYPlot categoryplot = (XYPlot) freeChart.getPlot();
			Font font2 = new Font("SimSun", 10, 16); // ?定字体、?型、字?
			categoryplot.getDomainAxis().setLabelFont(font2);// 相?于??或理解?X?
			categoryplot.getRangeAxis().setLabelFont(font2);// 相?于??理解?Y?
			Font font3 = new Font("SimSun", 10, 12); // ?定字体、?型、字?
			freeChart.getLegend().setItemFont(font3);// 最下方
			
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

 

            // 保存?PNG      

 

            ChartUtilities.writeChartAsPNG(out, chart, weight, height);      

 

            // 保存?JPEG      

 

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

 

    // 根据XYDataset?建JFreeChart?象      

    public static JFreeChart createChart(XYDataset dataset,String Xname) {      

        // ?建JFreeChart?象：ChartFactory.createXYLineChart      

        /*JFreeChart jfreechart = ChartFactory.createXYLineChart("XYLine Chart Demo", // ??      

                "時間", // categoryAxisLabel （category?，??，X???）      

                String.valueOf(JCcheck.getSelectedItem()), // valueAxisLabel（value?，??，Y?的??）      

                dataset, // dataset      

                PlotOrientation.VERTICAL,   

                true, // legend      

                false, // tooltips      

                false); // URLs      

 

        // 使用CategoryPlot?置各种??。以下?置可以省略。      

        XYPlot plot = (XYPlot) jfreechart.getPlot();      

        // 背景色 透明度      

        plot.setBackgroundAlpha(0.5f);      

        // 前景色 透明度      

        plot.setForegroundAlpha(0.5f);*/      

        // 其它?置可以?考XYPlot?      

		JFreeChart jfreechart = ChartFactory.createTimeSeriesChart(
		Xname, "時間", String.valueOf(JCcheck.getSelectedItem()), dataset, true,true, false);

        return jfreechart;      

    }     

 

    /**     

     * ?建XYDataset?象     

     *      

     */     

 

    private XYDataset createXYDataset() {

        XYSeries xyseries1 = new XYSeries("實際值");
		XYSeries xyseries2 = new XYSeries("中限");
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		TimeSeries timeSeries1 = new TimeSeries("實際值", Minute.class);
		TimeSeries timeSeries2 = new TimeSeries("中限", Minute.class);
		TimeSeries timeSeries3 = new TimeSeries("上限", Minute.class);
		TimeSeries timeSeries4 = new TimeSeries("下限", Minute.class);
		
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
				String mail_Context = String.valueOf(JCState.getSelectedItem()) + "號槽\n" + JTSlname.getText().trim() + " " + String.valueOf(JCcheck.getSelectedItem()) + " : \n實際值 : " + JTtemp.getText().trim().toString() + 
				"\n上限 : " + JTuptemp.getText().trim().toString() + "\n下限 : " + JTdwtemp.getText().trim().toString() + "\n不符合標準";
				String groupS = "SPCDW";
				if(JCagain.isSelected()){
					groupS = "SPC";
				}
				(new WebServiceUtil()).send_mail(uiVO,groupS,"SPC_陽一量測",mail_Context);
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
			JOptionPane.showMessageDialog(null,"Vendor P/N過長 !");
			a++;
			return a;
		}
		else if(JTCust_Part_No.getText().getBytes().length > 20){
			JOptionPane.showMessageDialog(null,"Pega P/N過長 !");
			a++;
			return a;
		}
		else if(JTBatch_No.getText().getBytes().length > 6){
			JOptionPane.showMessageDialog(null,"Batch過長 !");
			a++;
			return a;
		}
		else if(!JTQty.getText().matches("[0-9]*")){
			JOptionPane.showMessageDialog(null,"QTY只能輸入數字 !");
			a++;
			return a;
		}
		else if(JTMemo.getText().getBytes().length > 100){
			JOptionPane.showMessageDialog(null,"Memo過長 !");
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
    * ?是一?通用的方法，利用了JAVA的反射机制，可以?放置在JAVA集合中并且符?一定?件的?据以EXCEL 的形式?出到指定IO??上
    * 
    * @param title
    *            表格??名
    * @param headers
    *            表格?性列名??
    * @param dataset
    *            需要?示的?据集合,集合中一定要放置符合javabean?格的?的?象。此方法支持的
    *            javabean?性的?据?型有基本?据?型及String,Date,byte[](?片?据)
    * @param out
    *            与?出????的流?象，可以?EXCEL文??出到本地文件或者网?中
    * @param pattern
    *            如果有???据，?定?出格式。默??"yyy-MM-dd"
    */
   @SuppressWarnings("unchecked")
   public void exportExcel(String title, String[] headers,
         Collection<T> dataset, OutputStream out, String pattern) {
      // ?明一?工作薄
      HSSFWorkbook workbook = new HSSFWorkbook();
      // 生成一?表格
      HSSFSheet sheet = workbook.createSheet(title);
      // ?置表格默?列?度?15?字?
      sheet.setDefaultColumnWidth((short) 15);
      // 生成一??式
      HSSFCellStyle style = workbook.createCellStyle();
      // ?置?些?式
      style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
      style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
      style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
      style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
      style.setBorderRight(HSSFCellStyle.BORDER_THIN);
      style.setBorderTop(HSSFCellStyle.BORDER_THIN);
      style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
      // 生成一?字体
      HSSFFont font = workbook.createFont();
      font.setColor(HSSFColor.VIOLET.index);
      font.setFontHeightInPoints((short) 12);
      font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
      // 把字体?用到?前的?式
      style.setFont(font);
      // 生成并?置另一??式
      HSSFCellStyle style2 = workbook.createCellStyle();
      style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
      style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
      style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
      style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
      style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
      style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
      style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
      style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
      // 生成另一?字体
      HSSFFont font2 = workbook.createFont();
      font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
      // 把字体?用到?前的?式
      style2.setFont(font2);
      
      // ?明一???的??管理器
      HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
      // 定?注?的大小和位置,??文?
      HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
      // ?置注??容
      comment.setString(new HSSFRichTextString("可以在POI中添加注?！"));
      // ?置注?作者，?鼠?移?到?元格上是可以在???中看到??容.
      comment.setAuthor("leno");
 

      //?生表格??行
      HSSFRow row = sheet.createRow(0);
      for (short i = 0; i < headers.length; i++) {
         HSSFCell cell = row.createCell(i);
         cell.setCellStyle(style);
         HSSFRichTextString text = new HSSFRichTextString(headers[i]);
         cell.setCellValue(text);
      }
 

      //遍?集合?据，?生?据行
      Iterator<T> it = dataset.iterator();
      int index = 0;
      while (it.hasNext()) {
         index++;
         row = sheet.createRow(index);
         T t = (T) it.next();
         //利用反射，根据javabean?性的先后?序，???用getXxx()方法得到?性值
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
                //判?值的?型后?行?制?型??
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
                   textValue = "男";
                   if (!bValue) {
                      textValue ="女";
                   }
                } else if (value instanceof Date) {
                   Date date = (Date) value;
                   SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                    textValue = sdf.format(date);
                }  else if (value instanceof byte[]) {
                   // 有?片?，?置行高?60px;
                   row.setHeightInPoints(60);
                   // ?置?片所在列?度?80px,注意?里?位的一??算
                   sheet.setColumnWidth(i, (short) (35.7 * 80));
                   // sheet.autoSizeColumn(i);
                   byte[] bsValue = (byte[]) value;
                   HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,
                         1023, 255, (short) 10, index, (short) 10, index);
                   //anchor.setAnchorType(2);
                   patriarch.createPicture(anchor, workbook.addPicture(
                         bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
                } else{
                   //其它?据?型都?作字符串???理
                   textValue = value.toString();
                }
                //如果不是?片?据，就利用正?表?式判?textValue是否全部由?字?成
                if(textValue!=null){
                   Pattern p = Pattern.compile("^//d+(//.//d+)?$");   
                   Matcher matcher = p.matcher(textValue);
                   if(matcher.matches()){
                      //是?字?作double?理
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
                //清理?源
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
      // ???生
      ExportExcel<Student> ex = new ExportExcel<Student>();
      String[] headers = { "槽號","槽名", "檢驗項目", "實際值", "上限", "下限",
            "中限", "量測時間","量測人工號","是否符合標準","圖標趨勢" };
      java.util.List<Student> dataset = new ArrayList<Student>();
	  try{
		    TimeSeriesCollection inataset = new TimeSeriesCollection();
			TimeSeries timeSeries1 = new TimeSeries("實際值", Minute.class);
			TimeSeries timeSeries2 = new TimeSeries("中限", Minute.class);
			TimeSeries timeSeries3 = new TimeSeries("上限", Minute.class);
			TimeSeries timeSeries4 = new TimeSeries("下限", Minute.class);
		
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
						
						String airmax = Allrow.get("SLOTNO").toString() + "號槽" + Allrow.get("SLTYME").toString() + SLOT_SPC_CHARGE.PDEndTime.getText().substring(0,10);
						if(!SLOT_SPC_CHARGE.PD_EndTime.getText().equals("")){
							airmax += "至" + SLOT_SPC_CHARGE.PD_EndTime.getText().substring(0,10);
						}
						
						
							JFreeChart freeChart = SLOT_SPC_CHARGE.createChart(inataset,airmax);
							Font font1 = new Font("SimSun", 10, 20); // ?定字体、?型、字?
							freeChart.getTitle().setFont(font1);
							XYPlot categoryplot = (XYPlot) freeChart.getPlot();
							Font font2 = new Font("SimSun", 10, 16); // ?定字体、?型、字?
							categoryplot.getDomainAxis().setLabelFont(font2);// 相?于??或理解?X?
							categoryplot.getRangeAxis().setLabelFont(font2);// 相?于??理解?Y?
							Font font3 = new Font("SimSun", 10, 12); // ?定字体、?型、字?
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
      String[] headers2 = { "槽號","槽名", "檢驗項目", "實際值", "上限", "下限",
            "中限", "量測時間","量測人工號","是否符合標準","圖表趨勢" };
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
		
		 String outname = "D://總數據" + SLOT_SPC_CHARGE.PDEndTime.getText().substring(0,10) + ".xls";
		 String out2name = "D://" + String.valueOf(SLOT_SPC_CHARGE.JCState.getSelectedItem()) + "號槽"+ String.valueOf(SLOT_SPC_CHARGE.JCcheck.getSelectedItem()) + SLOT_SPC_CHARGE.PDEndTime.getText().substring(0,10) + ".xls";
		 if(!SLOT_SPC_CHARGE.PD_EndTime.getText().equals("")){
			 outname = "D://總數據" + SLOT_SPC_CHARGE.PDEndTime.getText().substring(0,10) + "至" + SLOT_SPC_CHARGE.PD_EndTime.getText().substring(0,10) + ".xls";
			 out2name = "D://" + String.valueOf(SLOT_SPC_CHARGE.JCState.getSelectedItem()) + "號槽"+ String.valueOf(SLOT_SPC_CHARGE.JCcheck.getSelectedItem()) + SLOT_SPC_CHARGE.PDEndTime.getText().substring(0,10) + "至" + SLOT_SPC_CHARGE.PD_EndTime.getText().substring(0,10) + ".xls";
		 }

         OutputStream out = new FileOutputStream(outname);
         OutputStream out2 = new FileOutputStream(out2name);
         ex.exportExcel(headers, dataset, out);
         ex2.exportExcel(headers2, dataset2, out2);
         out.close();
         out2.close();
         JOptionPane.showMessageDialog(null, "導出成功!");
         System.out.println("excel導出成功！");
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