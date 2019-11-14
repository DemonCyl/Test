package mes_sfis.client.ui.barcode;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Hashtable;
import java.util.Calendar;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import base.client.util.CloneArray_ChangeStr;
import base.client.util.component.P_Component_Meta;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.Barcode128;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;

import base.client.ui.BasePanel;
import base.vo.ResultVO;
import base.vo.BaseVO;
import base.client.util.BaseServletAgent;
import base.enums.CommandName;
import base.vo.UI_InitVO;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.Paragraph;
import java.io.IOException;
import javax.swing.*;
import java.util.Date;

public class LF_1709_linshi_maxprint extends BasePanel {
    public static final String VERSION    = "$Name:  $, $Id: LF_1709_linshi_maxprint.java,v 1.21 2018/03/26 10:09:10 Lucy6_Lu Exp $";
    public static final String COPYRIGHT  = "Copyright (c) 2008 PEGATRON Inc. All Rights Reserved.";
    private String year;
    private String month;
    private String week;
    private String day;
    LF_1709_linshi_maxprint self;
    com.lowagie.text.Font f14;
    com.lowagie.text.Font f13;
    com.lowagie.text.Font f12;
    static PdfPTable pdftable;
    String OldLotNo;
    int sum =0;
    private JTextField JTQty;
    private JTextField JTlc;
    private JTextField JTBt;
    private JTextField JTBtend;
    private JTextField JTBtal;
    private JComboBox JCVenC;
    private JTextField JTVenC;
    private JTextField JTSite;
    private JComboBox JCStage;
    private JTextField JTStage;
    private JTextField JTDesc;
    Hashtable row = null;;
    private JTextField JTdate;
    private JTextField printMessage,jtfprintnum,surplusnum;
    private String[] numList = {"0","1","2","3","4","5","6"};
    private JLabel usernameLabel,passwordLabel,selectLetLabel;
    private JTextField usernameText,passwordText,selectLetText;
    private JButton booleanbutton,selectbutton,reprintbutton,printbutton,backbutton;
    private String[] yy ={"..."};
    private DefaultComboBoxModel model = new DefaultComboBoxModel(yy);
    //JDialog  toastDialog;
    public LF_1709_linshi_maxprint(UI_InitVO uiVO) {
        super(uiVO);
        init();
    }
    public void init(){
        self=this;
        self.setUILayout(null);

        usernameLabel = new JLabel("請輸入用戶名:",JLabel.RIGHT);
        usernameLabel.setForeground(new Color(106,106,255));
        usernameLabel.setBounds(10,5,100,25);
        usernameLabel.setVisible(false);
        add(usernameLabel);

        passwordLabel = new JLabel("請輸入密碼:",JLabel.RIGHT);
        passwordLabel.setForeground(new Color(106,106,255));
        passwordLabel.setBounds(230,5,80,25);
        passwordLabel.setVisible(false);
        add(passwordLabel);

        usernameText = new JTextField(20);
        usernameText.setBounds(120,5,100,25);
        usernameText.setVisible(false);
        add(usernameText);

        passwordText = new JTextField(20);
        passwordText.setBounds(330,5,100,25);
        passwordText.setVisible(false);
        add(passwordText);

        selectLetLabel = new JLabel("查詢Lot No:",JLabel.RIGHT);
        selectLetLabel.setForeground(new Color(106,106,255));
        selectLetLabel.setBounds(10,35,100,25);
        selectLetLabel.setVisible(false);
        add(selectLetLabel);

        selectLetText = new JTextField(20);
        selectLetText.setEditable(false);
        selectLetText.setBounds(120,35,100,25);
        selectLetText.setVisible(false);
        add(selectLetText);

        selectbutton = new JButton("查詢");
        selectbutton.setEnabled(false);
        selectbutton.setBorder(null);
        selectbutton.setForeground(new Color(106, 106, 255));
        selectbutton.setBackground(new Color(255,255,255));
        selectbutton.setBounds(230,35,80,25);
        selectbutton.setVisible(false);
        add(selectbutton);

        booleanbutton = new JButton("確定");
        booleanbutton.setBorder(null);
        booleanbutton.setForeground(new Color(106,106,255));
        booleanbutton.setBackground(new Color(255, 255, 255));
        booleanbutton.setBounds(450,5,80,25);
        booleanbutton.setVisible(false);
        add(booleanbutton);

        backbutton = new JButton("返回");
        backbutton.setBorder(null);
        backbutton.setForeground(new Color(106,106,255));
        backbutton.setBackground(new Color(255,255,255));
        backbutton.setBounds(100,285,140,25);
        backbutton.setVisible(false);
        add(backbutton);


        reprintbutton = new JButton("重工");
        reprintbutton.setBorder(null);
        reprintbutton.setForeground(new Color(106,106,255));
        reprintbutton.setBackground(new Color(255,255,255));
        reprintbutton.setBounds(570,20,60,25);
        add(reprintbutton);

        final JLabel JLVenC = new JLabel("(P) Customer P/N:");
        JLVenC.setBounds(80,70,95,25);
        JTVenC = new JTextField(20);
        JTVenC.setBounds(210,70,150,25);
        JTVenC.setEditable(false);
        JTVenC.setVisible(false);
        add(JLVenC);
        add(JTVenC);


        JCVenC = new JComboBox(model);
        JCVenC.setForeground(Color.red);
        JCVenC.setBounds(210,70,150,25);
        add(JCVenC);

        JTStage = new JTextField(20);
        JTStage.setBounds(210,105,150,25);
        JTStage.setVisible(false);
        add(JTStage);

        JLabel JLStage = new JLabel("(1P) Manufacturer P/N:");
        JLStage.setBounds(80,105,95,25);

        JCStage = new JComboBox(GetMan_PN());
        JCStage.setForeground(Color.red);
        JCStage.setBounds(210,105,150,25);
        add(JLStage);
        add(JCStage);

        JTStage = new JTextField(20);
        JTStage.setBounds(210,105,150,25);
        JTStage.setVisible(false);
        add(JTStage);

        JLabel JLQty = new JLabel("(Q) QTY:");
        JLQty.setBounds(80,135,95,25);
        JTQty = new JTextField(20);
        JTQty.setBackground(Color.white);
        JTQty.setForeground(Color.black);
        JTQty.setBounds(210,135,150,25);
        add(JLQty);
        add(JTQty);

        JLabel JLlc = new JLabel("(V) Vendor Code:");
        JLlc.setBounds(new Rectangle(80,165,95,25));
        add(JLlc,null);
        JTlc = new JTextField(20);
        JTlc.setBounds(210,165,150,25);
        JTlc.setEditable(false);
        add(JTlc);

        JLabel JLBt = new JLabel("(1T) Lot No:");
        JLBt.setBounds(new Rectangle(80,195,95,25));
        add(JLBt,null);
        JTBt = new JTextField(20);
        JTBt.setBounds(210,195,150,25);
        JTBt.setEditable(false);
        add(JTBt);

        JLabel JLdate = new JLabel("(9D) Date Code:");
        JLdate.setBounds(new Rectangle(80,225,95,25));
        add(JLdate,null);
        JTdate=new JTextField(20);
        JTdate.setBounds(new Rectangle(210,225,150,25));
        JTdate.setEditable(false);
        add(JTdate);

        JLabel JLSite = new JLabel("(M) Manufacturer:");
        JLSite.setBounds(385,70,95,25);
        JTSite = new JTextField(20);
        JTSite.setBounds(485,70,150,25);
        JTSite.setEditable(false);
        add(JLSite);
        add(JTSite);

        JLabel JLBtend = new JLabel("(4L) Country of Origin:");
        JLBtend.setBounds(new Rectangle(385,105,95,25));
        add(JLBtend,null);
        JTBtend = new JTextField(20);
        JTBtend.setBounds(485,105,150,25);
        JTBtend.setEditable(false);
        add(JTBtend);

        JLabel JLBtal = new JLabel("REV : ");
        JLBtal.setBounds(new Rectangle(385,135,95,25));
        add(JLBtal,null);
        JTBtal = new JTextField(20);
        JTBtal.setBounds(485,135,150,25);
        JTBtal.setEditable(false);
        add(JTBtal);

        JLabel JLDesc = new JLabel("DESC : ");
        JLDesc.setBounds(new Rectangle(385,165,95,25));
        add(JLDesc,null);
        JTDesc = new JTextField(20);
        JTDesc.setBounds(485,165,150,25);
        JTDesc.setEditable(false);
        add(JTDesc);

        final JLabel jlnum = new JLabel("張數：",JLabel.RIGHT);
        jlnum.setBounds(220,285,40,25);
        add(jlnum);
        final JComboBox JcbprintNum = new JComboBox(numList);
        JcbprintNum.setForeground(Color.red);
        JcbprintNum.setBounds(260,285,50,25);
        add(JcbprintNum);

        final JButton buttonMidPrint = new JButton("中標籤打印");
        buttonMidPrint.setBackground(Color.white);
        buttonMidPrint.setForeground(Color.black);
        buttonMidPrint.setBounds(100,285,100,25);
        add(buttonMidPrint);

        final JButton buttonForPrint = new JButton("外標籤打印");
        buttonForPrint.setBackground(Color.white);
        buttonForPrint.setForeground(Color.black);
        buttonForPrint.setBounds(330,285,100,25);
        buttonForPrint.setEnabled(false);
        add(buttonForPrint);

        final JButton buttonMaxPrint = new JButton("中外同步打印");
        buttonMaxPrint.setBackground(Color.white);
        buttonMaxPrint.setForeground(Color.black);
        buttonMaxPrint.setBounds(450,285,140,25);
        add(buttonMaxPrint);

        printbutton = new JButton("打印");
        printbutton.setBorder(null);
        printbutton.setBackground(Color.white);
        printbutton.setForeground(new Color(106,106,255));
        printbutton.setBounds(450,285,140,25);
        printbutton.setVisible(false);
        add(printbutton);

        printMessage = new JTextField(20);
        printMessage.setBounds(100,315,150,25);
        printMessage.setEditable(false);
        printMessage.setVisible(false);
        add(printMessage);

        jtfprintnum = new JTextField(20);
        jtfprintnum.setBounds(270,315,100,25);
        jtfprintnum.setVisible(false);
        add(jtfprintnum);

        surplusnum = new JTextField(20);
        surplusnum.setBounds(490,315,100,25);
        surplusnum.setEditable(false);
        surplusnum.setVisible(false);
        add(surplusnum);

        final JButton buttonsure = new JButton("確定");
        buttonsure.setBounds(370,315,60,25);
        buttonsure.setVisible(false);
        add(buttonsure);

        JCStage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //獲取A選項
                String cus_pn = (String)JCVenC.getSelectedItem();
                //通過A獲取B選項后添加進控件
                if(JCVenC.getItemCount() > 1){
                    String man_pn = (String)JCStage.getSelectedItem();
                    String[] k = GetCus_PN(man_pn);
                    for(int j = JCVenC.getItemCount()-1;j>=1;j--){
                        model.removeElementAt(j);
                    }
                    for(int i = 0;i<k.length;i++){
                        String str = k[i];
                        model.addElement(str);
                    }
                }else if(JCVenC.getItemCount() <= 1){
                    String man_pn = (String)JCStage.getSelectedItem();
                    String[] k = GetCus_PN(man_pn);
                    for(int i = 0;i<k.length;i++){
                        String str = k[i];
                        model.addElement(str);
                    }
                }
            }
        });

        JCVenC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cus_PN = (String) JCVenC.getSelectedItem();
                String[] mes = Item(cus_PN);

                //String customer_pn = mes[0];
                String vendor = mes[1];
                String manufactuer = mes[2];
                String country = mes[3];
                String rev = mes[4];
                String dec = mes[5];
                //JTVenC.setText(customer_pn);
                JTlc.setText(vendor);
                JTSite.setText(manufactuer);
                JTBtend.setText(country);
                JTBtal.setText(rev);
                JTDesc.setText(dec);
            }
        });
        buttonMidPrint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTQty.setText("");
                JTQty.setEnabled(false);
                if( JCStage.getSelectedItem().equals("")){
                    JOptionPane.showConfirmDialog(null,"輸入不能為空","警告",JOptionPane.PLAIN_MESSAGE);
                }
                else if(JcbprintNum.getSelectedItem().equals("0")){
                    JOptionPane.showConfirmDialog(null,"打印張數不能為空","警告",JOptionPane.PLAIN_MESSAGE);
                }
                else{
                    String[] ss = Deal_with_Time();
                    String datecode = ss[0];
                    OldLotNo = ss[1];
                    JTdate.setText(datecode);

                    String man_pn = (String) JCVenC.getSelectedItem();
//                    String imgPath = GetImgpath(man_pn);
                    printMessage.setText("打印第1筆QTY：");
                    printMessage.setVisible(true);
                    jtfprintnum.setVisible(true);
                    buttonsure.setVisible(true);
                    int k = Integer.parseInt((String) JcbprintNum.getSelectedItem());
                    surplusnum.setText("打印還剩下"+k+"筆");
                    surplusnum.setVisible(true);
					/*
					for(int i = k;i>0;i--){
						Printdo(true,imgPath);
						//if(CreateDialog(i).equals("")){
						//}
						//JTQty.setText("");
					}
					JOptionPane.showConfirmDialog(null,"打印成功！","提示",JOptionPane.PLAIN_MESSAGE);
					*/
                    //JTQty.setEnabled(false);
                    JCStage.setEnabled(false);
                    JcbprintNum.setEnabled(false);
                    buttonMidPrint.setEnabled(false);
                    buttonMaxPrint.setEnabled(false);
                }
            }
        });
        buttonForPrint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTQty.setText(sum+"");
                if(JCStage.getSelectedItem().equals("")){
                    JOptionPane.showConfirmDialog(null,"輸入不能為空","警告",JOptionPane.PLAIN_MESSAGE);
                }else if(JcbprintNum.getSelectedItem().equals("0")){
                    JOptionPane.showConfirmDialog(null,"打印張數不能為空","警告",JOptionPane.PLAIN_MESSAGE);
                }
                else{
                    String let_no = OldLotNo+"0";
                    JTBt.setText(let_no);
                    String man_pn = (String) JCVenC.getSelectedItem();
                    String imgPath = GetImgpath(man_pn);
                    doPrint(true,imgPath,false);
                    JOptionPane.showConfirmDialog(null,"打印成功！","提示",JOptionPane.PLAIN_MESSAGE);
                    try {
                        Insert_DB_Mes(false);
                    } catch (Exception e1){
                        e1.printStackTrace();
                    }
                    JTVenC.setText("");
                    JTlc.setText("");
                    JTSite.setText("");
                    JTBtend.setText("");
                    JTBtal.setText("");
                    JTDesc.setText("");
                    JTdate.setText("");
                    JTQty.setText("");
                    JTBt.setText("");
                    sum =0;
                    JcbprintNum.setSelectedIndex(0);
                    JTQty.setEnabled(true);
                    JCStage.setEnabled(true);
                    JcbprintNum.setEnabled(true);
                    buttonForPrint.setEnabled(false);
                    buttonMidPrint.setEnabled(true);
                    buttonMaxPrint.setEnabled(true);
                }
            }
        });
        buttonMaxPrint.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(JTQty.getText().trim().equals("")|| JCStage.getSelectedItem().equals("")){
                    JOptionPane.showConfirmDialog(null,"輸入不能為空","警告",JOptionPane.PLAIN_MESSAGE);
                }else{
                    String man_pn = (String) JCVenC.getSelectedItem();
                    String imgPath = GetImgpath(man_pn);
                    String[] ss = Deal_with_Time();
                    String datecode = ss[0];
                    String let_no = ss[1]+"0";
                    JTdate.setText(datecode);
                    JTBt.setText(let_no);
                    doPrint(true,imgPath,false);
					try {
                        Insert_DB_Mes(false);
                    } catch (Exception e1){
                        e1.printStackTrace();
                    }

                    String let_no2 = ss[1]+"1";
                    JTBt.setText(let_no2);
                    Printdo(true,imgPath,false);
                    try {
                        Insert_DB_Mes(false);
                    } catch (Exception e1){
                        e1.printStackTrace();
                    }
					JOptionPane.showConfirmDialog(null,"打印成功！","提示",JOptionPane.PLAIN_MESSAGE);
                    JTVenC.setText("");
                    JTlc.setText("");
                    JTSite.setText("");
                    JTBtend.setText("");
                    JTBtal.setText("");
                    JTDesc.setText("");
                    JTdate.setText("");
                    JTQty.setText("");
                    JTBt.setText("");
                    JcbprintNum.setSelectedIndex(0);
                }
            }
        });
        buttonsure.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String man_pn = (String) JCVenC.getSelectedItem();
                String imgPath = GetImgpath(man_pn);
                String Qty = jtfprintnum.getText().toString().trim();
                if(Qty.equals("")){
                    JOptionPane.showConfirmDialog(null,"輸入不能為空","警告",JOptionPane.PLAIN_MESSAGE);
                }else{
                    String str1 = printMessage.getText().toString().trim();
                    String str2 = surplusnum.getText().toString().trim();
                    JTQty.setText(Qty);
                    sum = sum +Integer.parseInt(Qty);                    
                    String a= str1.substring(3,4);
                    int i = Integer.parseInt(a);
                    String b = str2.substring(5,6);
                    int j = Integer.parseInt(b);
                    jtfprintnum.setText("");
                    String let_no = OldLotNo+a;
                    JTBt.setText(let_no);
					Printdo(true,imgPath,false);
                    try {
                        Insert_DB_Mes(false);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    JTQty.setText("");
                    i++;
                    j--;
                    printMessage.setText("打印第"+i+"筆QTY：");
                    surplusnum.setText("打印還剩下"+j+"筆");
                    if(j==0){
                        printMessage.setText("");
                        jtfprintnum.setText("");
                        surplusnum.setText("");
                        printMessage.setVisible(false);
                        jtfprintnum.setVisible(false);
                        surplusnum.setVisible(false);
                        buttonsure.setVisible(false);
                        JTQty.setEnabled(true);
                        buttonForPrint.setEnabled(true);
                    }
                }
            }
        });
        reprintbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usernameLabel.setVisible(true);
                passwordLabel.setVisible(true);
                usernameText.setVisible(true);
                passwordText.setVisible(true);
                booleanbutton.setVisible(true);
                selectLetLabel.setVisible(true);
                selectLetText.setVisible(true);
                selectbutton.setVisible(true);
                JCVenC.setVisible(false);
                JTVenC.setVisible(true);
                JTVenC.setEnabled(false);
                JCStage.setVisible(false);
                JCStage.setEnabled(false);
                JTStage.setVisible(true);
                JTStage.setEditable(false);
                buttonMidPrint.setVisible(false);
                buttonForPrint.setVisible(false);
                buttonMaxPrint.setVisible(false);
                jlnum.setVisible(false);
                JcbprintNum.setVisible(false);
                printbutton.setVisible(true);
                printbutton.setEnabled(false);
                backbutton.setVisible(true);
            }
        });
        booleanbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = usernameText.getText().toString().trim();
                String password = passwordText.getText().toString().trim();
                if(name.equals("")||password.equals("")){
                    JOptionPane.showConfirmDialog(null,"輸入用戶或密碼不能為空","提示",JOptionPane.ERROR_MESSAGE);
                }
                else if(login() == true){
                    usernameText.setEditable(false);
                    passwordText.setEditable(false);
                    selectLetText.setEditable(true);
                    selectbutton.setEnabled(true);
                    booleanbutton.setEnabled(true);
                }else{
                    JOptionPane.showConfirmDialog(null,"輸入用戶或密碼錯誤","提示",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        selectbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(selectLetText.getText().trim().equals("")){
                    JOptionPane.showConfirmDialog(null,"輸入不能為空","提示",JOptionPane.ERROR_MESSAGE);
                }else{
                    //selectLetText.setEditable(false);
                    printbutton.setEnabled(true);
                    String[] mes = indexLotNo();
                    String manPn = mes[0];
                    String cusPn = mes[1];
                    String QTY = mes[2];
                    String VEN = mes[3];
                    String REV = mes[4];
                    String DESC = mes[5];
                    String[] timemes = Deal_with_Time();
                    String date = timemes[0];
                    String lot = timemes[1];
                    JTVenC.setEditable(true);
                    JTVenC.setText(cusPn);
                    JTStage.setText(manPn);
                    JTQty.setText(QTY);
                    JTlc.setText(VEN);
                    JTBt.setText(lot);
                    JTdate.setText(date);
                    JTSite.setText("KAIJIA");
                    JTBtend.setText("CHINA");
                    JTBtal.setText(REV);
                    JTDesc.setText(DESC);
                }
            }
        });
        printbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(JTQty.getText().trim().equals("")|| JTBt.getText().trim().equals("")){
                    JOptionPane.showConfirmDialog(null,"輸入不能為空","警告",JOptionPane.PLAIN_MESSAGE);
                }else{
                    String man_pn = JTVenC.getText().trim();
                    final String imgPath = GetImgpath(man_pn);
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            doPrint(true,imgPath,true);
                        }
                    };
                    Thread thread = new Thread(runnable);
                    thread.start();
                    Runnable runnable2 = new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }
                            Printdo(true, imgPath, true);
                        }
                    };

                    Thread thread2 = new Thread(runnable2);
                    thread2.start();

                    JOptionPane.showConfirmDialog(null,"打印成功！","提示",JOptionPane.PLAIN_MESSAGE);
                    try {
                        Insert_DB_Mes(true);
                    } catch (Exception e1){
                        e1.printStackTrace();
                    }
                    try {
                        InsertLogo();
                    } catch (Exception e2){
                        e2.printStackTrace();
                    }
                    JTVenC.setText("");
                    JTlc.setText("");
                    JTStage.setText("");
                    JTSite.setText("");
                    JTBtend.setText("");
                    JTBtal.setText("");
                    JTDesc.setText("");
                    JTdate.setText("");
                    JTQty.setText("");
                    JTBt.setText("");
                    selectLetText.setEnabled(true);
                    selectLetText.setText("");
                    JcbprintNum.setSelectedIndex(0);
                }
            }
        });
        backbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usernameLabel.setVisible(false);
                passwordLabel.setVisible(false);
                usernameText.setVisible(false);
                passwordText.setVisible(false);
                booleanbutton.setVisible(false);
                selectLetLabel.setVisible(false);
                selectLetText.setVisible(false);
                selectbutton.setVisible(false);
                JCStage.setVisible(true);
                JCStage.setEnabled(true);
                JTVenC.setVisible(false);
                JCVenC.setVisible(true);
                JTStage.setVisible(false);
                JTStage.setEnabled(true);
                buttonMidPrint.setVisible(true);
                buttonForPrint.setVisible(true);
                buttonMaxPrint.setVisible(true);
                jlnum.setVisible(true);
                JcbprintNum.setVisible(true);
                printbutton.setVisible(false);
                printbutton.setEnabled(true);
                backbutton.setVisible(false);
                usernameText.setText("");
                passwordText.setText("");
                selectLetText.setText("");
                JTVenC.setText("");
                JTStage.setText("");
                JTQty.setText("");
                JTlc.setText("");
                JTBt.setText("");
                JTdate.setText("");
                JTSite.setText("");
                JTBtend.setText("");
                JTBtal.setText("");
                JTDesc.setText("");

            }
        });
    }
    public String[] GetMan_PN()  {
        String[] result = null;
        Vector vct = new Vector();
        BaseServletAgent bsa = new BaseServletAgent(uiVO);
        BaseVO baseVO = new BaseVO(uiVO,this.getClass().toString(), CommandName.CallPLSQLCmd2,vct);
        baseVO.setPackageName("AAA_1709_SUPPLEMENT_NEW_PRINT");
        baseVO.setFunctionName("MANUFACTURER_PN");
        try {
            ResultVO rvo = bsa.doFunc(baseVO);
            Vector vct2 = rvo.getData();
            int n = vct2.size();
            result = new String[n];
            if(vct2.size()>1){
                for(int i= 0;i <= n-1;i++){
                    if(i==0){
                        result[i] ="...";
                    }
                    else{
                        Hashtable ht1 = (Hashtable) vct2.elementAt(i);
                        String MANUFACTUER_PN =  CloneArray_ChangeStr.NulltoSpace(ht1.get("MANUFACTUER_PN"));
                        result[i] = MANUFACTUER_PN;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(result[3]);
        return result;
    }
    public String[] GetCus_PN(String manpn){
        if(!manpn.equals("...")){

        }
        String[] result = null;
        Vector vct = new Vector();
        vct.add(manpn);
        BaseServletAgent bsa = new BaseServletAgent(uiVO);
        BaseVO baseVO = new BaseVO(uiVO,this.getClass().toString(), CommandName.CallPLSQLCmd2,vct);
        baseVO.setPackageName("AAA_1709_SUPPLEMENT_NEW_PRINT");
        baseVO.setFunctionName("CUSTOMER_PN");
        try {
            ResultVO rvo = bsa.doFunc(baseVO);
            Vector vct2 = rvo.getData();
            int n = vct2.size();
            result = new String[n-1];
            if(vct2.size()>1){
                for(int i= 1;i < n;i++){
                    Hashtable ht1 = (Hashtable) vct2.elementAt(i);
                    String CUSTOMER_PN =  CloneArray_ChangeStr.NulltoSpace(ht1.get("CUSTOMER_PN"));
                    result[i-1] = CUSTOMER_PN;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public String[] Item(String Man_PN){
        String[] result = null;
        if(!Man_PN.equals("...")){
            Vector vct = new Vector();
            vct.add(Man_PN);
            BaseServletAgent bsa = new BaseServletAgent(uiVO);
            BaseVO baseVO = new BaseVO(uiVO,this.getClass().toString(), CommandName.CallPLSQLCmd2,vct);
            baseVO.setPackageName("AAA_1709_SUPPLEMENT_NEW_PRINT");
            baseVO.setFunctionName("CODE_MESSAGE");
            ResultVO rvo = null;
            try {
                rvo = bsa.doFunc(baseVO);
                Vector vct2 = rvo.getData();
                if(vct2.size()>1){
                    Hashtable ht1 = (Hashtable) vct2.elementAt(1);
                    String MANUFACTUER_PN = CloneArray_ChangeStr.NulltoSpace(ht1.get("MANUFACTUER_PN"));
                    System.out.println("================="+MANUFACTUER_PN);
                    String vendor = CloneArray_ChangeStr.NulltoSpace(ht1.get("VENDOR"));
                    String manufactuer = CloneArray_ChangeStr.NulltoSpace(ht1.get("MANUFACTUER"));
                    String country = CloneArray_ChangeStr.NulltoSpace(ht1.get("COUNTRY"));
                    String rev = CloneArray_ChangeStr.NulltoSpace(ht1.get("REV"));
                    String dec = CloneArray_ChangeStr.NulltoSpace(ht1.get("DEC"));
                    String[] res = {MANUFACTUER_PN,vendor,manufactuer,country,rev,dec};
                    result = res;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            String[] res = {"","","","","",""};
            result = res;
        }
        return result;
    }
    public String[] Deal_with_Time(){
        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        cal.setTime(date);
        year = cal.get(Calendar.YEAR)+"";
        if(cal.get(Calendar.WEEK_OF_YEAR)<10){
            int xx = cal.get(Calendar.WEEK_OF_YEAR);
            week = "0"+xx;
        }else{week = cal.get(Calendar.WEEK_OF_YEAR)+"";}

        if(cal.get(Calendar.MONTH)+1<10){
            int xx = cal.get(Calendar.MONTH)+1;
            month = "0"+xx;
        }else{month = cal.get(Calendar.MONTH)+1+"";}

        if(cal.get(Calendar.DAY_OF_MONTH)<10){
            day = "0"+cal.get(Calendar.DAY_OF_MONTH)+"";
        }else{day = cal.get(Calendar.DAY_OF_MONTH)+"";}

        String DateCode = year.substring(2,4)+week;
        String time = year+month+day;
        int k = 0;
        try {
            k = Deal_NumTime(time);
            System.out.println(k);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String DealNum = null;
        if(k<10){
            DealNum ="00"+k;;

        }else if(k>=10 && k<100){
            DealNum = "0"+k;
        }else{
            DealNum = k+"";
        }
        System.out.println(DealNum);
        Date date1 = new Date(System.currentTimeMillis());
        String yy  = date1.toString().trim();
        int hour = Integer.parseInt(yy.substring(11,13));
        String lot_no = null;
        if(hour>=8&&hour<20){
            lot_no = "C"+time+"A"+DealNum;
        }else{lot_no = "C"+time+"B"+DealNum;}
        String[] result ={DateCode,lot_no};
        return result;
    }
    public int Deal_NumTime(String time) throws Exception{
        int k = 1;
        Vector arg = new Vector();
        arg.add(time);
        BaseServletAgent bsa = new BaseServletAgent(uiVO);
        BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg);
        bvo.setPackageName("AAA_1709_PORTNO_CHECK");
        bvo.setFunctionName("GET_NUM");
        ResultVO rvo = bsa.doFunc(bvo);
        Vector vct = rvo.getData();
        if(vct.size()<2){
            Vector arg1 = new Vector();
            arg1.add(1);
            arg1.add(time);
            BaseServletAgent bsa1 = new BaseServletAgent(uiVO);
            BaseVO bvo1 = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg1);
            bvo1.setPackageName("AAA_1709_PORTNO_CHECK");
            bvo1.setFunctionName("UPDATE_NUM");
            bsa1.doFunc(bvo1);
        }
        if(vct.size()==2){
            Hashtable ht = (Hashtable) vct.elementAt(1);
            String num = CloneArray_ChangeStr.NulltoSpace(ht.get("NUM"));
            k = Integer.parseInt(num)+1;
            Vector arg1 = new Vector();
            arg1.add(k);
            arg1.add(time);
            BaseServletAgent bsa1 = new BaseServletAgent(uiVO);
            BaseVO bvo1 = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,arg1);
            bvo1.setPackageName("AAA_1709_PORTNO_CHECK");
            bvo1.setFunctionName("UPDATE_NUM");
            bsa1.doFunc(bvo1);
        }
        return k;
    }
    private void Printdo(boolean isPrint,String imgPath,boolean reprint){
        String filePath = "d:/Honor.pdf";
        try{
            Document document = new Document(new com.lowagie.text.Rectangle(170, 280), 05, 05, 05, 05);
            PdfWriter writer = PdfWriter.getInstance(document,new FileOutputStream(filePath));
            document.open();
            PdfTablecreate(writer,reprint);
            document.add(pdftable);

            String desc = JTDesc.getText().trim();
            int i = desc.length()*8;
            //Image img3 = Image.getInstance(imgPath);
            //img3.setAbsolutePosition(34f, 241f);
            //img3.scaleAbsolute(i, 8);
            //document.add(img3);
            document.close();
            writer.close();
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (DocumentException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        if(isPrint){
            printPdf(filePath);
        }
    }
    private void doPrint(boolean isPrint,String imgPath,Boolean reprint){
        String filePath = "d:/HuaWei.pdf";
        String manPN = null;
        String cusPN = null;
        if(reprint==true){
            manPN = JTStage.getText().toString().trim();
            cusPN = JTVenC.getText().toString().trim();

        }else{
            manPN = (String) JCStage.getSelectedItem();
            cusPN = (String) JCVenC.getSelectedItem();
        }
        try{
            Document document = new Document(new com.lowagie.text.Rectangle(170, 280), 05, 05, 00, 00);
            PdfWriter writer = PdfWriter.getInstance(document,new FileOutputStream(filePath));
            document.open();

            createPdfTable(writer,reprint);
            document.add(pdftable);
            ZXingCode.getLogoQRCode("P" + cusPN + " 1P" + manPN + " Q" + JTQty.getText().trim() + " V" + JTlc.getText().trim()
                    + " 1T" + JTBt.getText().trim() + " 9D" + JTdate.getText().trim() + " M" + JTSite.getText().trim() + " 4L" + JTBtend.getText().trim(), null, null);
            Image img = Image.getInstance("D:/test.png");
            img.scalePercent(120f);
            img.setAbsolutePosition(115,140);
            document.add(img);

            ZXingCode.getLogoQRCode("1D" + JTdate.getText().trim() + " 1Q" + JTQty.getText().trim(), null, null);
            Image img2 = Image.getInstance("D:/test.png");
            img2.scalePercent(90f);
            img2.setAbsolutePosition(135,47);
            document.add(img2);

            String desc = JTDesc.getText().trim();
            int i = desc.length()*8;
            //Image img3 = Image.getInstance(imgPath);
            //img3.setAbsolutePosition(34f, 245f);

            //img3.scaleAbsolute(i,8);
            //document.add(img3);
            document.close();
            writer.close();
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (DocumentException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        if(isPrint){
            printPdf(filePath);
        }
    }
    public static boolean printPdf(final String pdfPath){
        Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                        try {
                            Runtime.getRuntime().exec("cmd.exe /C  start acrord32 /P /h " + pdfPath);
                        } catch (IOException e) {
                            JOptionPane.showMessageDialog(null,"請注意：打印異常");
                        }
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            };
            Thread thread = new Thread(runnable);
            thread.start();
            return true;
    }
    public PdfPCell createBarCode(PdfWriter writer, String codeStr, int maxWidth) {
        PdfContentByte cd = writer.getDirectContent();
        Barcode128 code39ext = new Barcode128();
        code39ext.setStartStopText(false);
        code39ext.setExtended(true);
        code39ext.setFont(null);
        code39ext.setTextAlignment(Element.ALIGN_LEFT);
        code39ext.setBarHeight(13f);
        code39ext.setX(0.5f);

        code39ext.setCode(codeStr);
        Image image39 = code39ext.createImageWithBarcode(cd, null, null);

        PdfPCell barcodeCell = new PdfPCell(image39);
        barcodeCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        barcodeCell.setPadding(0);
        barcodeCell.setBorder(0);
        return barcodeCell;
    }
    public PdfPCell createCell(String cellStr, Font font, int rowHeight, int borderNum) {
        PdfPCell newCell = new PdfPCell(new Paragraph(cellStr, font));
        newCell.setBorder(borderNum);
        if (rowHeight > 0) {
            newCell.setMinimumHeight(rowHeight);
        }

        return newCell;
    }
    public void createPdfTable(PdfWriter writer,Boolean reprint) {
        String manPN = null;
        String cusPN = null;
        if(reprint==true){
            manPN = JTStage.getText().toString().trim();
            cusPN = JTVenC.getText().toString().trim();

        }else{
            manPN = (String) JCStage.getSelectedItem();
            cusPN = (String) JCVenC.getSelectedItem();
        }

        initFont();
        pdftable = new PdfPTable(3);
        pdftable.setTotalWidth(150);
        int[] a = { 60, 20 ,20};
        try {
            pdftable.setWidths(a);
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try{

            pdftable.setLockedWidth(true);
            pdftable.getDefaultCell().setBorder(0);//PdfPCell.NO_BORDER
            pdftable.setHorizontalAlignment(Element.ALIGN_LEFT);

            PdfPCell lega = new PdfPCell(createCell("(P) Customer P/N: " +cusPN,f14,0,0));//xx[1]客戶料號
            lega.setBorder(0);
            lega.setColspan(3);
            pdftable.addCell(lega);

            PdfPCell nued = new PdfPCell(createBarCode(writer, "P" + cusPN, a[1]));
            nued.setBorder(0);
            nued.setColspan(3);
            pdftable.addCell(nued);

            //PdfPCell desc = new PdfPCell(createCell("DESC: ", f14,0,0));
            PdfPCell desc = new PdfPCell(createCell("DESC: " + JTDesc.getText().trim(), f14, 0, 0));
            desc.setBorder(0);
            desc.setColspan(3);
            pdftable.addCell(desc);

            PdfPCell nued1 = new PdfPCell(createBarCode(writer, cusPN, a[1]));
            nued1.setBorder(0);
            nued1.setColspan(3);
            pdftable.addCell(nued1);

            PdfPCell byby = new PdfPCell(createCell("(1P) Manufacturer P/N: " +manPN,f14,0,0));
            byby.setBorder(0);
            byby.setColspan(3);
            pdftable.addCell(byby);

            PdfPCell nkar = new PdfPCell(createBarCode(writer,manPN, a[1]));
            nkar.setBorder(0);
            nkar.setColspan(3);
            pdftable.addCell(nkar);

            PdfPCell mavc = new PdfPCell(createCell("(V) Vendor Code: " + JTlc.getText().trim(),f14,0,0));
            mavc.setBorder(0);
            mavc.setColspan(3);
            pdftable.addCell(mavc);

            PdfPCell mavcx = new PdfPCell(createBarCode(writer, "V" + JTlc.getText().trim(), a[1]));
            mavcx.setBorder(0);
            mavcx.setColspan(3);
            pdftable.addCell(mavcx);

            PdfPCell afct = new PdfPCell(createCell("(1T) Lot No: " + JTBt.getText().trim(),f14,0,0));
            afct.setBorder(0);
            afct.setColspan(3);
            pdftable.addCell(afct);

            PdfPCell vost = new PdfPCell(createBarCode(writer,"1T" + JTBt.getText().trim(),a[1]));
            vost.setBorder(0);
            vost.setColspan(3);
            pdftable.addCell(vost);

            PdfPCell fge1 = new PdfPCell(createCell("(9D) Date Code: " + JTdate.getText().trim(),f14,0,0));
            fge1.setBorder(0);
            fge1.setColspan(3);
            pdftable.addCell(fge1);

            PdfPCell edsf = new PdfPCell(createBarCode(writer,"9D" + JTdate.getText().trim(), a[1]));
            edsf.setBorder(0);
            edsf.setColspan(3);
            pdftable.addCell(edsf);

            PdfPCell fge2 = new PdfPCell(createCell("(M) Manufacturer: " + JTSite.getText().trim(),f14,0,0));
            fge2.setBorder(0);
            fge2.setColspan(1);
            pdftable.addCell(fge2);

            PdfPCell kong = new PdfPCell(createCell("(Q) QTY: " + JTQty.getText().trim(),f14,0,0));
            kong.setBorder(0);
            kong.setColspan(2);
            pdftable.addCell(kong);

            PdfPCell edsf2 = new PdfPCell(createBarCode(writer,"M" + JTSite.getText().trim(), a[1]));
            edsf2.setBorder(0);
            edsf2.setColspan(1);
            pdftable.addCell(edsf2);

            PdfPCell cbie = new PdfPCell(createBarCode(writer,"Q" + JTQty.getText().trim(), a[1]));
            cbie.setBorder(0);
            cbie.setColspan(2);
            pdftable.addCell(cbie);

            PdfPCell fge3 = new PdfPCell(createCell("(4L) Country of Origin: " + JTBtend.getText().trim(),f14,0,0));
            fge3.setBorder(0);
            fge3.setColspan(3);
            pdftable.addCell(fge3);

            PdfPCell edsf3 = new PdfPCell(createBarCode(writer,"4L" + JTBtend.getText().trim(), a[1]));
            edsf3.setBorder(0);
            edsf3.setColspan(3);
            pdftable.addCell(edsf3);

            PdfPCell fge4 = new PdfPCell(createCell("REV: " + JTBtal.getText().trim(),f14,0,0));
            fge4.setBorder(0);
            fge4.setColspan(3);
            pdftable.addCell(fge4);

            PdfPCell fge5 = new PdfPCell(createCell("           Date Code",f12,0,0));
            fge5.setBorder(1);
            fge5.setColspan(3);
            pdftable.addCell(fge5);

            PdfPCell pega2 = new PdfPCell(new Paragraph(" ", f14));
            pega2.setBorder(0);
            pega2.setColspan(3);
            pega2.setPadding(5);
            pdftable.addCell(pega2);

            PdfPCell edsf4 = new PdfPCell(createBarCode(writer,JTdate.getText().trim(), a[1]));
            edsf4.setBorder(0);
            pdftable.addCell(edsf4);

            PdfPCell cbie4 = new PdfPCell(createBarCode(writer,JTQty.getText().trim(), a[1]));
            cbie4.setBorder(0);
            cbie4.setColspan(2);
            pdftable.addCell(cbie4);

            pdftable.addCell(createCell("Date Code1:" + JTdate.getText().trim(),f14,0,0));

            PdfPCell pdfQty = new PdfPCell(createCell("QTY:"+JTQty.getText().trim(),f14,0,0));
            pdfQty.setBorder(0);
            pdfQty.setColspan(2);
            pdftable.addCell(pdfQty);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void PdfTablecreate(PdfWriter writer,boolean reprint) {
        String manPN = null;
        String cusPN = null;
        if(reprint==true){
            manPN = JTStage.getText().toString().trim();
            cusPN = JTVenC.getText().toString().trim();

        }else{
            manPN = (String) JCStage.getSelectedItem();
            cusPN = (String) JCVenC.getSelectedItem();
        }
        initFont();
        pdftable = new PdfPTable(3);
        pdftable.setTotalWidth(150);
        int[] a = { 90, 5,5};
        try {
            pdftable.setWidths(a);
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try{
            pdftable.setLockedWidth(true);
            pdftable.getDefaultCell().setBorder(15);//PdfPCell.NO_BORDER
            pdftable.setHorizontalAlignment(Element.ALIGN_LEFT);

            PdfPCell lega = new PdfPCell(createCell("(P) Customer P/N: " + cusPN,f14,0,0));
            lega.setBorder(0);
            lega.setColspan(3);
            pdftable.addCell(lega);
            PdfPCell nued = new PdfPCell(createBarCode(writer, "P" + cusPN, a[1]));
            nued.setBorder(0);
            nued.setColspan(3);
            pdftable.addCell(nued);

            //PdfPCell desc = new PdfPCell(createCell("DESC: ", f14,0,0));
            PdfPCell desc = new PdfPCell(createCell("DESC: " + JTDesc.getText().trim(), f14, 0, 0));
            desc.setBorder(0);
            desc.setColspan(3);
            pdftable.addCell(desc);

            PdfPCell nued1 = new PdfPCell(createBarCode(writer,cusPN, a[1]));
            nued1.setBorder(0);
            nued1.setColspan(3);
            pdftable.addCell(nued1);

            PdfPCell byby = new PdfPCell(createCell("(1P) Manufacturer P/N: " + manPN,f14,0,0));
            byby.setBorder(0);
            byby.setColspan(3);
            pdftable.addCell(byby);

            PdfPCell nkar = new PdfPCell(createBarCode(writer,manPN, a[1]));
            nkar.setBorder(0);
            nkar.setColspan(3);
            pdftable.addCell(nkar);

            PdfPCell kong = new PdfPCell(createCell("(Q) QTY: " + JTQty.getText().trim(),f14,0,0));
            kong.setBorder(0);
            kong.setColspan(3);
            pdftable.addCell(kong);

            PdfPCell cbie = new PdfPCell(createBarCode(writer,"Q" + JTQty.getText().trim(), a[1]));
            cbie.setBorder(0);
            cbie.setColspan(3);
            pdftable.addCell(cbie);

            PdfPCell mavc = new PdfPCell(createCell("(V) Vendor Code: " + JTlc.getText().trim(),f14,0,0));
            mavc.setBorder(0);
            mavc.setColspan(3);
            pdftable.addCell(mavc);

            PdfPCell mavcx = new PdfPCell(createBarCode(writer, "V" + JTlc.getText().trim(), a[1]));
            mavcx.setBorder(0);
            mavcx.setColspan(3);
            pdftable.addCell(mavcx);

            PdfPCell afct = new PdfPCell(createCell("(1T) Lot No: " + JTBt.getText().trim(),f14,0,0));
            afct.setBorder(0);
            afct.setColspan(3);
            pdftable.addCell(afct);

            PdfPCell vost = new PdfPCell(createBarCode(writer,"1T" + JTBt.getText().trim(),a[1]));
            vost.setBorder(0);
            vost.setColspan(3);
            pdftable.addCell(vost);

            PdfPCell fge1 = new PdfPCell(createCell("(9D) Date Code: " + JTdate.getText().trim(),f14,0,0));
            fge1.setBorder(0);
            fge1.setColspan(3);
            pdftable.addCell(fge1);

            PdfPCell edsf = new PdfPCell(createBarCode(writer,"9D" + JTdate.getText().trim(), a[1]));
            edsf.setBorder(0);
            edsf.setColspan(3);
            pdftable.addCell(edsf);

            PdfPCell fge2 = new PdfPCell(createCell("(M) Manufacturer: " + JTSite.getText().trim(),f14,0,0));
            fge2.setBorder(0);
            fge2.setColspan(3);
            pdftable.addCell(fge2);

            PdfPCell edsf2 = new PdfPCell(createBarCode(writer,"M" + JTSite.getText().trim(), a[1]));
            edsf2.setBorder(0);
            edsf2.setColspan(3);
            pdftable.addCell(edsf2);

            PdfPCell fge3 = new PdfPCell(createCell("(4L) Country of Origin: " + JTBtend.getText().trim(),f14,0,0));
            fge3.setBorder(0);
            fge3.setColspan(3);
            pdftable.addCell(fge3);

            PdfPCell edsf3 = new PdfPCell(createBarCode(writer,"4L" + JTBtend.getText().trim(), a[1]));
            edsf3.setBorder(0);
            edsf3.setColspan(3);
            pdftable.addCell(edsf3);

            PdfPCell fge4 = new PdfPCell(createCell("REV: " + JTBtal.getText().trim(),f14,0,0));
            fge4.setBorder(0);
            fge4.setColspan(3);
            pdftable.addCell(fge4);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void initFont() {
        BaseFont bfChinese;
        try {
            bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            //bfChinese = BaseFont.createFont("MHei-Medium", "UniCNS-UCS2-H", BaseFont.NOT_EMBEDDED);
            f14 = new Font(bfChinese, 7, Font.BOLD, Color.BLACK);
            f13 = new Font(bfChinese, 18, Font.BOLD, Color.BLACK);
            f12 = new Font(bfChinese, 10, Font.BOLD, Color.BLACK);
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public String GetImgpath(String Man_PN){
        String result = null;
        Vector vct = new Vector();
        vct.add(Man_PN);
        BaseServletAgent bsa = new BaseServletAgent(uiVO);
        BaseVO baseVO = new BaseVO(uiVO,this.getClass().toString(), CommandName.CallPLSQLCmd2,vct);
        baseVO.setPackageName("AAA_1709_SUPPLEMENT_NEW_PRINT");
        baseVO.setFunctionName("GET_IMGPATH");
        ResultVO rvo = null;
        try {
            rvo = bsa.doFunc(baseVO);
            Vector vct2 = rvo.getData();
            if(vct2.size()>1){
                Hashtable ht1 = (Hashtable) vct2.elementAt(1);
                String imgPath = CloneArray_ChangeStr.NulltoSpace(ht1.get("IMGPATH"));
                result = imgPath;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public void Insert_DB_Mes(boolean reprint) throws Exception {
        String manPN = null;
        String CusPN = null;
        if(reprint==true){
            manPN = JTStage.getText().toString().trim();
            CusPN = JTVenC.getText().toString().trim();
        }else{
            manPN = (String) JCStage.getSelectedItem();
            CusPN = (String) JCVenC.getSelectedItem();
        }
        Vector vct = new Vector();
        vct.add(CusPN);
        vct.add(manPN);
        vct.add(JTQty.getText().toString().trim());
        vct.add(JTlc.getText().toString().trim());
        vct.add(JTBt.getText().toString().trim());
        vct.add(JTdate.getText().toString().trim());
        vct.add(JTBtal.getText().toString().trim());
        vct.add(JTDesc.getText().toString().trim());
        BaseServletAgent bsa = new BaseServletAgent(uiVO);
        BaseVO baseVO = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,vct);
        baseVO.setPackageName("AAA_1709_PORTNO_CHECK");
        baseVO.setFunctionName("INSERT_MES");
        bsa.doFunc(baseVO);
    }
    public void InsertLogo() throws Exception{
        String username =  usernameText.getText().toString().trim();
        String OldLot =  selectLetText.getText().toString().trim();
        String newLot =  JTBt.getText().toString().trim();
        Vector vct = new Vector();
        vct.add(username);
        vct.add(OldLot);
        vct.add(newLot);
        BaseServletAgent bsa = new BaseServletAgent(uiVO);
        BaseVO baseVO = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,vct);
        baseVO.setPackageName("AAA_1709_SUPPLEMENT_NEW_PRINT");
        baseVO.setFunctionName("insertlogo");
        bsa.doFunc(baseVO);
    }
    public boolean login() {
        Vector vct = new Vector();
        vct.add(usernameText.getText().toString().trim());
        vct.add(passwordText.getText().toString().trim());
        BaseServletAgent bsa = new BaseServletAgent(uiVO);
        BaseVO baseVO = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,vct);
        baseVO.setPackageName("AAA_1709_SUPPLEMENT_NEW_PRINT");
        baseVO.setFunctionName("indexuser");
        Vector vct2 = new Vector();
        try {
            ResultVO rvo = bsa.doFunc(baseVO);
            vct2 = rvo.getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(vct2.size()>1){
            return true;
        }else{
            return false;
        }
    }
    public String[] indexLotNo(){
        String[] result = null;
        Vector vct = new Vector();
        vct.add(selectLetText.getText().toString().trim());
        BaseServletAgent bsa = new BaseServletAgent(uiVO);
        BaseVO baseVO = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallPLSQLCmd2,vct);
        baseVO.setPackageName("AAA_1709_SUPPLEMENT_NEW_PRINT");
        baseVO.setFunctionName("indexLotno");
        Vector vct2 = new Vector();
        try {
            ResultVO rvo = bsa.doFunc(baseVO);
            vct2 = rvo.getData();
            if(vct2.size()==2){
                Hashtable ht1 = (Hashtable) vct2.elementAt(1);
                String ManPn = CloneArray_ChangeStr.NulltoSpace(ht1.get("MANUFACTUER_PN"));
                String CUSTOMER_PN = CloneArray_ChangeStr.NulltoSpace(ht1.get("CUSTOMER_PN"));
                String QTY = CloneArray_ChangeStr.NulltoSpace(ht1.get("QTY"));
                String VEN = CloneArray_ChangeStr.NulltoSpace(ht1.get("VEN"));
                String REV = CloneArray_ChangeStr.NulltoSpace(ht1.get("REV"));
                String DESC_PN = CloneArray_ChangeStr.NulltoSpace(ht1.get("DESC_PN"));
                String[] res = {ManPn,CUSTOMER_PN,QTY,VEN,REV,DESC_PN};
                result = res;
            }else if(vct2.size()<2){
                JOptionPane.showConfirmDialog(null,"Lot No不存在","提示",JOptionPane.ERROR_MESSAGE);

            }else{
                JOptionPane.showConfirmDialog(null,"Lot No異常","提示",JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void create() {

    }

    @Override
    public void save() {


    }

    @Override
    public void cancel() {

    }

    @Override
    public void refresh() {

    }

    @Override
    public void modify() {

    }

    @Override
    public void delete() {

    }

    @Override
    public void singleQuery() {

    }

    @Override
    public void multiQuery() {

    }

    @Override
    public void print() {

    }

    @Override
    public void exit() {

    }

    @Override
    public void help() {

    }

    @Override
    public void email() {

    }

    @Override
    public void export() {

    }

    @Override
    public void importData() {

    }

    @Override
    public Hashtable<String, P_Component_Meta> needValidateComponents() {
        return null;
    }

    @Override
    public void setReportOid(String s) {

    }
}
