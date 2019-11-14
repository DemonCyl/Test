package mes_sfis.client.ui.pack;

import base.client.ui.BasePanel;
import base.client.util.component.P_Component_Meta;
import base.vo.UI_InitVO;

import com.lowagie.text.DocumentException;
import mes_sfis.client.model.bean.PackList;
import mes_sfis.client.model.bean.PackPick;
import mes_sfis.client.model.service.PackingQueryService;
import mes_sfis.client.pdf.PDFCreater;
import mes_sfis.client.pdf.PDFPick;
import mes_sfis.client.util.DataHandler;
import mes_sfis.client.util.SoundUtil;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;

/**
 * Created by Mark_Yang on 2018/4/2.
 */
public class PickView extends BasePanel {
    public static final String VERSION    = "$Name:  $, $Id: PickView.java,v 1.20 2018/04/16 09:11:08 Lucy6_Lu Exp $";
    public static final String COPYRIGHT  = "Copyright (c) 2017 PEGATRON Inc. All Rights Reserved.";
    private JTextField ZBSXNumberText,SMOrderText,ZBNumberText,ZBXNumberText,TotalNumberText;

    private JTextArea scannedNewsText,LogText;


    static UI_InitVO uiVO;
    private String isNull=null;
    private int sanxNumber=0;//聲明零散箱數
    private int zhanbNumber=0;//聲明棧板數
    private int zhanbxNumber=0;//聲明棧板箱數
    private int totalProductNumber=0;//聲明總產品數
    private PackList packList;
    String pickNo = null;
   // private PackPick packPick=new PackPick();
    private List list = new ArrayList<Map>();
    private Map<Integer,String> mapc=new HashMap();
    private Map<Integer,String> mapp=new HashMap();
    private Map<Object,String> map =new HashMap<>();
    public PickView(UI_InitVO uiVO) {
        super(uiVO);
        this.uiVO=uiVO;
        init();

    }
    public void init(){

      /*  JFrame frame = new JFrame("1721撿貨入庫");
        frame.setSize(600, 650);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);*/

        JPanel panel = jPanel();
        panel.setSize(600, 650);
        add(panel);
       // panel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 添加面板
       // frame.add(panel);

        /*
         * ?用用?定?的方法并添加?件到面板
         */
      //  placeComponents(panel);

        // ?置界面可?
      //  frame.setVisible(true);





    }


    private  JPanel jPanel() {

        JPanel panel=new JPanel();

    //    panel.setLayout(null);




        panel.setLayout(null);
        JLabel title = new JLabel("1721檢貨入庫");
        title.setBounds(240,10,120,30);
        title.setFont(new Font("宋体", Font.BOLD, 16));
        panel.add(title);
        // panel.setSize(600, 50);
        JButton startPick= new JButton("開始檢貨");
        startPick.setBounds(100,50,100,30);
        startPick.setVisible(false);
        panel.add(startPick);
        JButton finishPick= new JButton("結束撿貨");
        finishPick.setBounds(240,50,100,30);
        panel.add(finishPick);
        JButton cleanOut= new JButton("清除");
        cleanOut.setBounds(380,50,100,30);
        panel.add(cleanOut);

     /*   //設置廠內料號Label
        JLabel userLabel = new JLabel("廠內料號:");
        userLabel.setBounds(10,100,60,25);
        panel.add(userLabel);

        //設置廠內料號顯示框
        JTextField userText = new JTextField(20);
        userText.setBounds(70,100,130,25);
        userText.setEditable(false);
        panel.add(userText);

        //設置顏色Label
        JLabel colorLabel = new JLabel("顏色:");
        colorLabel.setBounds(210,100,30,25);
        panel.add(colorLabel);

        //設置顏色顯示框
        JTextField colorText = new JTextField(10);
        colorText.setBounds(240,100,50,25);
        colorText.setEditable(false);
        panel.add(colorText);

        // 設置客戶料號Label
        JLabel userNumberLabel = new JLabel("客戶料號:");
        userNumberLabel.setBounds(310,100,60,25);
        panel.add(userNumberLabel);

      //設置客戶料號下拉選擇對照表
        JTextField userNumberText = new JTextField(10);
        userNumberText.setBounds(370,100,130,25);
        userNumberText.setEditable(false);
        panel.add(userNumberText);*/

        //設置撿貨概要Label
        JLabel zhanbLable=new JLabel("棧板數:");
        zhanbLable.setBounds(10,100,50,25);
        panel.add(zhanbLable);

        //設置總棧板箱數Label
        ZBNumberText = new JTextField(10);
        ZBNumberText.setBounds(70,100,60,25);
        ZBNumberText.setEditable(false);
        panel.add(ZBNumberText);

        JLabel zhanbxLable=new JLabel("棧板總箱數:");
        zhanbxLable.setBounds(140,100,70,25);
        panel.add(zhanbxLable);
        //設置棧板總箱數顯示
        ZBXNumberText = new JTextField(10);
        ZBXNumberText.setBounds(220,100,80,25);
        ZBXNumberText.setEditable(false);
        panel.add(ZBXNumberText);

        //設置零散箱Label
        JLabel JHSnumber=new JLabel("零散箱:");
        JHSnumber.setBounds(320,100,60,25);
        panel.add(JHSnumber);

        //設置零散箱數顯示框
        ZBSXNumberText = new JTextField(10);
        ZBSXNumberText.setBounds(370,100,80,25);
        ZBSXNumberText.setEditable(false);
        panel.add(ZBSXNumberText);

        //設置掃描入庫Label
        JLabel SMOrderLabel=new JLabel("掃描入庫:");
        SMOrderLabel.setBounds(10,150,60,25);
        panel.add(SMOrderLabel);

        //設置掃描的棧板號\箱號顯示框
        SMOrderText = new JTextField(10);
        SMOrderText.setBounds(70,150,140,25);
      //  SMOrderText.setEditable(false);
        panel.add(SMOrderText);

        //設置數量Label
        JLabel TotalNumberLabel=new JLabel("數量/PCs:");
        TotalNumberLabel.setBounds(240,150,60,25);
        panel.add(TotalNumberLabel);

        //設置數量顯示框
        final JTextField TotalNumberText = new JTextField(10);
        TotalNumberText.setBounds(310,150,140,25);
        TotalNumberText.setEditable(false);
        panel.add(TotalNumberText);

        scannedNewsText = new JTextArea();
        scannedNewsText.setBounds(30,200,500,100);
        // CheckSSNText.setLineWrap(true);
        scannedNewsText.setEditable(false);
        JScrollPane scannedNewsScroll = new JScrollPane(scannedNewsText);
        scannedNewsScroll.setBounds(30,200,500,150);
        scannedNewsScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(scannedNewsScroll);

        LogText = new JTextArea();
        LogText.setBounds(30,370,500,150);
        JScrollPane jsLogText = new JScrollPane(LogText);
        jsLogText.setBounds(30,370,500,150);
        LogText.setEditable(false);
        jsLogText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(jsLogText);


       // SMOrderText.requestFocus();
        //開始撿貨按鈕監聽事件
        startPick.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        finishPick.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isNull!=null){
                    int isSure = JOptionPane.showConfirmDialog(null, "確認加入清單么?", "加入清單",JOptionPane.YES_NO_OPTION);//返回的是按?的index  i=0或者1
                    if (isSure==0){
                        PackPick packPick=new PackPick();
                        String projectCode = "COP1721";

                        packPick.setDh(new DataHandler(uiVO));

                        packList=new PackList();//建立存儲實體類對象
                        packList.setCartonQty(sanxNumber);//加箱數
                        try {
                            packList.setDateCode(packPick.getPickSn(projectCode).substring(0,4));//加日期編碼
                            pickNo="COP1721-"+packPick.getPickSn(projectCode);
                            packList.setPickNo(pickNo);//加清單碼
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                        packList.setLogEmployee(uiVO.getEmployee_oid());//加操作人員
                        // packList.setLOG_SYSTEMDATE(date);//加系統日期
                        packList.setMemo("");//加備註
                        packList.setPalletQty(zhanbNumber);//加棧板數
                        packList.setStatus(0);//加狀態碼 0：未入庫   1：已入庫
                        packList.setPalletCartonNumber(zhanbxNumber);
                        PackingQueryService ps = new PackingQueryService(uiVO);
                        list.add(mapc);
                        list.add(mapp);
                        boolean isSuccess =ps.addPick_OID(packList,"MES_PACK_CARTON","MES_PACK_PALLET",list);
                        if (isSuccess==false){
                            SoundUtil.PlayNo();
                            LogText.append("系統異常-添加清單異常"+"\r\n");
                            SMOrderText.setText("");
                           // showDialog("添加清單異常","系統異常");

                            JOptionPane.showMessageDialog(null, "添加清單異常","系統異常",JOptionPane.ERROR_MESSAGE);
                        }else {
                            SoundUtil.PlayNice();
                            LogText.append("Success-添加清單成功"+"\r\n");
                            ZBSXNumberText.setText("");
                            SMOrderText.setText("");
                            ZBNumberText.setText("");
                            ZBXNumberText.setText("");
                            TotalNumberText.setText("");
                            scannedNewsText.setText("");
                            LogText.setText("");
                            sanxNumber=0;//初始化零散箱數
                            zhanbNumber=0;//初始化棧板數
                            zhanbxNumber=0;//初始化棧板箱數
                            totalProductNumber=0;//初始化總產品數
                            map.clear();
                            isNull=null;
                           // int a=JOptionPane.showConfirmDialog(null,"清單號:"+pickNo,"提示",JOptionPane.PLAIN_MESSAGE);
                           // JOptionPane.showConfirmDialog(null,a,"警告",JOptionPane.PLAIN_MESSAGE);
                            JOptionPane.showMessageDialog(null, "清單號:"+pickNo);
                            try {
                               // pickNo="COP1721-"+packPick.getPickSn(projectCode);
                                PDFPick pdfPick = new PDFPick(packList.getPickNo()+".pdf");
                                pdfPick.addPage(packList);
                                pdfPick.close();
                                pdfPick.printPdf();
                            } catch (FileNotFoundException e1) {
                                e1.printStackTrace();
                            } catch (DocumentException e1) {
                                e1.printStackTrace();
                            }


                        }
                    }else {

                        LogText.setText("取消加入清單"+"\r\n");
                    }

                }else {
                    JOptionPane.showMessageDialog(null, "無數據信息","提示",JOptionPane.NO_OPTION);
                }
                    SMOrderText.requestFocus();
            }
        });


        cleanOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ZBSXNumberText.setText("");
                SMOrderText.setText("");
                ZBNumberText.setText("");
                ZBXNumberText.setText("");
                TotalNumberText.setText("");
                scannedNewsText.setText("");
                LogText.setText("");
                map.clear();
                isNull=null;
                sanxNumber=0;//初始化零散箱數
                zhanbNumber=0;//初始化棧板數
                zhanbxNumber=0;//初始化棧板箱數
                totalProductNumber=0;//初始化總產品數
                SMOrderText.requestFocus();
            }
        });

        SMOrderText.addKeyListener(new KeyAdapter() {
               @Override
               public void keyPressed(KeyEvent e) {
                   super.keyPressed(e);
                   if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                       if (SMOrderText.getText().toString()!=""&&SMOrderText.getText().toString()!=null){
                           PackingQueryService ps = new PackingQueryService(uiVO);
                           if (map.get(SMOrderText.getText().toString())!=null){
                               showDialog("該碼已掃描過","流程異常");
                              // JOptionPane.showMessageDialog(null, "該碼已掃描過","流程異常",JOptionPane.NO_OPTION);

                           }else {

                               // boolean isBox=true;

                               String isBox = ps.isBox(SMOrderText.getText().toString());//判斷刷的是箱號還是棧板號   是箱號為true
                               if (isBox=="true"){
                                   //判斷是否已入清單或已入庫start
                                   int ispick= ps.isCreatPick(SMOrderText.getText().toString(),"MES_PACK_CARTON","CARTON_NO");
                               /* if(ispick ==0){
                                    LogText.append("此箱碼已入清單"+"\r\n");
                                }else*/
                                   if (ispick==1){

                                       showDialog("此箱碼已入庫","流程異常");
                                       // JOptionPane.showMessageDialog(null, "此箱碼已入庫","流程異常",JOptionPane.ERROR_MESSAGE);
                                   }else if (ispick==-1){

                                       showDialog("查詢此箱碼是否已入庫","系統異常");
                                      // JOptionPane.showMessageDialog(null, "查詢此箱碼是否已入庫","系統異常",JOptionPane.ERROR_MESSAGE);
                                   }else {
                                       if (ps.xProductNumber(SMOrderText.getText().toString())==-1){//判斷查詢箱內產品數時是否出現異常

                                           showDialog("查詢箱內產品數時","系統異常");
                                           //JOptionPane.showMessageDialog(null, "查詢箱內產品數時","系統異常",JOptionPane.ERROR_MESSAGE);
                                       }else {
                                           scannedNewsText.append("箱碼:"+SMOrderText.getText().toString()+"\r\n");//在捲軸里添加新刷入的carton_on碼號
                                           map.put(SMOrderText.getText().toString(),"ishave");
                                           sanxNumber+=1;
                                           mapc.put(sanxNumber,SMOrderText.getText().toString());
                                           totalProductNumber+=ps.xProductNumber(SMOrderText.getText().toString());//總產品數等於當前產品數加此箱號內產品數
                                           ZBSXNumberText.setText(sanxNumber+"");//設置零散箱數改變
                                           TotalNumberText.setText(totalProductNumber+"");//設置總產品數改變
                                           isNull="判斷生成清單數據是否為空";
                                           SoundUtil.PlayOk();
                                           SMOrderText.setText("");

                                       }
                                   }  //判斷是否已入清單或已入庫end


                               }else if(isBox=="false"){
                                   String ispallet=ps.isPallet(SMOrderText.getText().toString());////判斷刷的是不是棧板號   是棧板號號為true
                                   if (ispallet=="true"){
                                       //判斷是否已入清單或已入庫start
                             /*      if (ps.isCreatPick(SMOrderText.getText().toString(),"MES_PACK_PALLET","PALLET_NO")==0){
                                       LogText.append("此棧板碼已入清單"+"\r\n");
                                       SoundUtil.PlayNo();
                                   }else*/
                                       int ispick= ps.isCreatPick(SMOrderText.getText().toString(),"MES_PACK_PALLET","PALLET_NO");
                                       if (ispick==1){

                                           showDialog("此棧板碼已入庫","流程異常");
                                          // JOptionPane.showMessageDialog(null, "此棧板碼已入庫","流程異常",JOptionPane.ERROR_MESSAGE);
                                       }else if (ispick==-1){

                                           showDialog("查詢此棧板是否入庫","系統異常");
                                          // JOptionPane.showMessageDialog(null, "查詢此棧板是否入庫","系統異常",JOptionPane.ERROR_MESSAGE);
                                       }else {
                                           int zhanbxnumber=ps.zhanbxNumber(SMOrderText.getText().toString());
                                           int zhanbproduct=ps.zhanbProductNumber(SMOrderText.getText().toString());
                                           if (zhanbxnumber==-1||zhanbproduct==-1){

                                               showDialog("查詢棧板內箱數","系統異常");
                                              // JOptionPane.showMessageDialog(null, "查詢棧板內箱數","系統異常",JOptionPane.ERROR_MESSAGE);
                                           }else if (zhanbproduct==-2){

                                               showDialog("棧板為空棧板","流程異常");
                                             //  JOptionPane.showMessageDialog(null, "棧板為空棧板","流程異常",JOptionPane.ERROR_MESSAGE);
                                           }else {
                                               scannedNewsText.append("棧板碼:"+SMOrderText.getText().toString()+"\r\n");//在捲軸里添加新刷入的carton_on碼號
                                               map.put(SMOrderText.getText().toString(),"ishave");
                                               zhanbNumber+=1;
                                               mapp.put(zhanbNumber,SMOrderText.getText().toString());
                                               zhanbxNumber+=zhanbxnumber;//總棧板箱數等於當前所有棧板箱數加此棧板號內箱數
                                               totalProductNumber+=zhanbproduct;//總產品數等於當前產品數加此棧板號內產品數
                                               ZBNumberText.setText(zhanbNumber+"");
                                               ZBXNumberText.setText(zhanbxNumber+"");
                                               TotalNumberText.setText(totalProductNumber+"");//設置總產品數改變
                                               isNull="判斷生成清單數據是否為空";
                                               SoundUtil.PlayOk();
                                               SMOrderText.setText("");
                                           }

                                       }

                                   }else if(ispallet=="false"){

                                       showDialog("此碼不存在","提示");
                                     //  JOptionPane.showMessageDialog(null, "此碼不存在","提示",JOptionPane.ERROR_MESSAGE);
                                   }else {

                                       showDialog(ispallet.substring(6),ispallet.substring(1,5));
                                      // JOptionPane.showMessageDialog(null, ispallet,ispallet.substring(1,5),JOptionPane.ERROR_MESSAGE);
                                   }
                               }else {

                                   showDialog(isBox.substring(6),isBox.substring(1,5));
                               //    JOptionPane.showMessageDialog(null, isBox,isBox.substring(1,5),JOptionPane.ERROR_MESSAGE);
                               }
                           }

                       }else {
                           showDialog("無數據信息","提示");
                         //  JOptionPane.showMessageDialog(null, "無數據信息","提示",JOptionPane.ERROR_MESSAGE);
                       }



                   }
               }
        });

        return panel;
    }


    public  void  showDialog(String stra,String strb){
        LogText.append(SMOrderText.getText().toString()+"-"+stra+"-"+strb+"\r\n");
        SoundUtil.PlayNo();
        SMOrderText.setText("");
        JOptionPane.showMessageDialog(null, stra,strb,JOptionPane.ERROR_MESSAGE);
        //SMOrderText.setFocusable(true);
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
