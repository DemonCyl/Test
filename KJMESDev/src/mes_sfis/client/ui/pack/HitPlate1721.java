package mes_sfis.client.ui.pack;

import base.client.ui.BasePanel;
import base.client.util.component.P_Component_Meta;
import base.vo.UI_InitVO;
import base.vo.URL_VO;
import mes_sfis.client.model.service.PackageService;
import mes_sfis.client.model.service.PalletService;
import mes_sfis.client.util.SoundUtil;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by Haifeng_Zhou on 2018/4/2.
 */
public class HitPlate1721 extends BasePanel {

    public static final String VERSION = "$Name: 1.12 $, $Id: HitPlate1721.java,v 1.22 2018/04/16 10:31:07 Lucy6_Lu Exp $";
    public static final String COPYRIGHT  = "Copyright (c) 2008 PEGATRON Inc. All Rights Reserved.";
    private JButton jButtonPacking, jButtonClear, jButtonMandatoryPacking;
    private JLabel titleLabel, InFactoryMaterialNumLabel, FinshScanningSSNLabel, LogLabel, ColorLabel, CustomerMaterialNumLabel, Number_BoxLabel, Number_BoxLabelValue, ColorLabelValue;
    private JTextField InFactoryMaterialNumText,caseNo,InFactoryMaterialNumText1,CustomerMaterialNumBox;
    private JTextArea CheckSSNText,LogText;
    private JScrollPane jsLogText;
    private PalletService palletService;
    int a=1;//板數
    String productC="";//箱號滾動一欄顯示數據
   // PalletService pack=new PalletService();
    List<Hashtable> value = null;
    ArrayList cf=new ArrayList();//驗證箱號是否刷重複
    String[] arr;//需要加入的數據存入數組
    String VALUEBM;
    String PALLET_OID=null;


    public HitPlate1721(UI_InitVO uiVO) {
        super(uiVO);
        palletService = new PalletService(uiVO);
        init();
    }
    //清空緩存數據
    public void Eliminate(){
        InFactoryMaterialNumText.setText("");
        ColorLabelValue.setText("");
        Number_BoxLabelValue.setText("0/12");
        CheckSSNText.setText("");
        InFactoryMaterialNumText1.setText("");
        CustomerMaterialNumBox.setText("");
        a=1;
        productC="";
        //清空存儲數據
        cf.clear();
        arr=null;
        LogText.setText("成功");
       /* caseNo.setText("");*/
    }
    public void start(String  BM){
        SoundUtil.PlayNice();

        //清空緩存數據
        Eliminate();
    }
    void init() {
        setUILayout(null);
        jButtonPacking = new JButton("開始打板 ");
        jButtonMandatoryPacking = new JButton("強制打板");
        jButtonClear = new JButton("清除");
        titleLabel = new JLabel("1721打板");
        InFactoryMaterialNumLabel = new JLabel("廠內料號");
        FinshScanningSSNLabel = new JLabel("已掃描箱號");
        LogLabel = new JLabel("Log");
        ColorLabel = new JLabel("顏色：");
        CustomerMaterialNumLabel = new JLabel("客戶料號");
        Number_BoxLabel = new JLabel("箱數/棧板");
        Number_BoxLabelValue = new JLabel("0/12");
        ColorLabelValue = new JLabel();
        InFactoryMaterialNumText = new JTextField();
        InFactoryMaterialNumText1 = new JTextField();//存儲第一箱掃描料號 方便跟其他12箱進行對比
        caseNo = new JTextField("");
        CustomerMaterialNumBox = new JTextField();
        CustomerMaterialNumBox.setEditable(false);
        CheckSSNText = new JTextArea();
        CheckSSNText.setLineWrap(true);
        CheckSSNText.setEditable(false);
        JScrollPane jsCheckSSN = new JScrollPane(CheckSSNText);
       /* jsCheckSSN.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); 顯示左右滾動條*/
        jsCheckSSN.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        LogText = new JTextArea();
        LogText.setLineWrap(true);
        LogText.setEditable(false);
        jsLogText = new JScrollPane(LogText);
        jsLogText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


        add(jButtonPacking);
        add(jButtonMandatoryPacking);
        add(jButtonClear);
        add(titleLabel);
        add(InFactoryMaterialNumLabel);
        add(FinshScanningSSNLabel);
        add(ColorLabel);
        add(CustomerMaterialNumLabel);
        add(Number_BoxLabel);
        add(InFactoryMaterialNumText);
        add(InFactoryMaterialNumText1);
        add(CustomerMaterialNumBox);
        add(Number_BoxLabelValue);
        add(ColorLabelValue);
        add(LogLabel);
        add(jsLogText);
        add(jsCheckSSN);
        add(caseNo);

        jButtonPacking.setBounds(20, 100, 100, 40);
        jButtonMandatoryPacking.setBounds(/*270*/170, 100, 100, 40);
        jButtonClear.setBounds(/*520*/420, 100, 100, 40);
        jButtonPacking.setVisible(false); //標籤影藏

        titleLabel.setBounds(270, 50, 100, 20);

        InFactoryMaterialNumLabel.setBounds(20, 160, 100, 40);
        FinshScanningSSNLabel.setBounds(20, 200, 80, 40);
        caseNo.setBounds(120, 210, 130, 32);
        InFactoryMaterialNumText.setBounds(120, 165, 100, 30);
        InFactoryMaterialNumText.setEditable(false);
        InFactoryMaterialNumText1.setBounds(0, 165, 100, 30);
        InFactoryMaterialNumText1.setVisible(false); //標籤影藏

        ColorLabel.setBounds(270, 160, 100, 40);
        ColorLabelValue.setBounds(320, 160, 100, 40);

        CustomerMaterialNumLabel.setBounds(400, 160, 100, 40);
        Number_BoxLabel.setBounds(400, 210, 100, 40);
        Number_BoxLabelValue.setBounds(520, 210, 100, 40);
        CustomerMaterialNumBox.setBounds(520, 165, 100, 30);
        //箱號滾動
        CheckSSNText.setBounds(20, 280, 600, 80);
        jsCheckSSN.setBounds(20, 280, 600, 80);

        LogLabel.setBounds(20, 320, 100, 100);
        LogText.setBounds(20, 380, 600, 50);
        jsLogText.setBounds(20, 380, 600, 50);
        //獲取已掃描箱號 出發事件

        caseNo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    value = palletService.key(caseNo.getText());
                    //判斷此箱號為在裝箱中錄入信息
                    if (value.size()<1) {
                        SoundUtil.PlayNo(1000);//選擇播放聲音
                        LogText.setText("此箱尚未封箱,請核實");
                        caseNo.setText("");
                        return;
                    }
                    if (a == 1) {
                        //  (String) value.get("KJ_PN"))
                        // 存儲第一箱掃描料號 方便跟其他11箱進行對比
                        InFactoryMaterialNumText1.setText((String) value.get(1).get("KJ_PN"));
                    }

                    //回填料號編碼顏色
                    InFactoryMaterialNumText.setText((String) value.get(1).get("KJ_PN"));
                    ColorLabelValue.setText((String) value.get(1).get("VALUEZW"));
                    //客戶料號
                    CustomerMaterialNumBox.setText((String) value.get(1).get("OEMPN"));
                    //驗證板中料號是否統一
                    if (!InFactoryMaterialNumText1.getText().equals((String) value.get(1).get("KJ_PN"))) {
                        SoundUtil.PlayNo(1000);//選擇播放聲音
                        LogText.setText("此箱料號不統一,請核實");
                        caseNo.setText("");
                        return;
                    }

                    //驗證是否刷重複箱號
                    if (cf.contains(caseNo.getText())) {
                        SoundUtil.PlayNo(1000);//選擇播放聲音
                        JOptionPane.showConfirmDialog(null,"此箱號刷重複！","警告",JOptionPane.PLAIN_MESSAGE);
                        LogText.setText("此箱號刷重複");
                        caseNo.setText("");
                        return;
                    }
                    //驗證此箱是否已打版
                    if(palletService.PalletVerify(caseNo.getText())==null){
                        SoundUtil.PlayOk(1000);
                    }else {
                        JOptionPane.showConfirmDialog(null,"此箱已經打版,請核實！","警告",JOptionPane.PLAIN_MESSAGE);
                        LogText.setText("此箱已經打版,請核實");
                        SoundUtil.PlayNo(1000);//選擇播放聲音
                        caseNo.setText("");
                        return;
                    }
                    //將箱號存儲到list中
                    cf.add(caseNo.getText());
                    //將已經掃描了箱號統一顯示一起
                    productC = productC + caseNo.getText() + "\n";
                    CheckSSNText.setText(productC);
                    //箱數已掃描機箱
                    Number_BoxLabelValue.setText((a++) + "/12");
                    //驗證此箱是否已打版
                    caseNo.setText("");
                    //PDF打印
                    if (a > 12) { //打板12提示需要打板
                        //獲取需要打印pdf數據
                        if (((String) value.get(1).get("KJ_PN")).equals("YFM00Y060-103-N")){
                            VALUEBM="JPT3";
                        }else{
                            VALUEBM="JPT4";
                        }
                        palletService.PDF(VALUEBM, (a - 1) + "", (String) value.get(1).get("CHECK_FIELD_VALUE"));
                        //獲取打板箱數/料號編碼
                        String[] arr=new String[]{(a-1)+"",VALUEBM};
                       String PALLET_OID= palletService.increase(arr, (String) value.get(1).get("CHECK_FIELD_VALUE"));
                        // SoundUtil.PlayNo();//選擇播放聲音
                        int tj = JOptionPane.showConfirmDialog(null, "是否進行打板", "提示", JOptionPane.YES_NO_OPTION);
                        LogText.setText("已滿12箱,請先打板");
                        //0：確定 反正值清除數據
                        if (tj == 0) {
                            //更新打板封板狀態
                            palletService.upCARTON((cf),PALLET_OID);
                            start(VALUEBM);
                        } else {
                            int tj1 = JOptionPane.showConfirmDialog(null, "是否取消打板", "提示", JOptionPane.YES_NO_OPTION);
                            if (tj1 == 0) {
                                Eliminate();
                            }else{
                                Repeat();
                            }

                        }
                    }
                    LogText.setText("");
                }
            }

    });



        //開始打板
        jButtonPacking.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        //強制打板
        jButtonMandatoryPacking.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cf.size()<1){
                    SoundUtil.PlayNo(1000);//選擇播放聲音
                    JOptionPane.showConfirmDialog(null,"請錄入信息后強制打板！","警告",JOptionPane.PLAIN_MESSAGE);
                    LogText.setText("請錄入信息后強制打板");
                    return;
                }
                //獲取需要打印pdf數據
                if (InFactoryMaterialNumText1.getText().equals("YFM00Y060-103-N")) {
                    VALUEBM="JPT3";
                }else{
                    VALUEBM="JPT4";
                }
                palletService.PDF(VALUEBM,(a-1)+"", (String) value.get(1).get("CHECK_FIELD_VALUE"));
                //獲取打板箱數/料號編碼
                String[] arr=new String[]{(a-1)+"",VALUEBM};
                String PALLET_OID= palletService.increase(arr, (String) value.get(1).get("CHECK_FIELD_VALUE"));
                int tj = JOptionPane.showConfirmDialog(null, "是否進行強制打板", "提示", JOptionPane.YES_NO_OPTION);
                if(tj==0){
                    palletService.upCARTON((cf),PALLET_OID);
                    start(VALUEBM);
                }

            }
        });
        //取消
        jButtonClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoundUtil.PlayNice(1000);
                Eliminate();
            }
        });
    }
    public   void Repeat(){
        int tj = JOptionPane.showConfirmDialog(null, "是否進行打板", "提示", JOptionPane.YES_NO_OPTION);
        LogText.setText("已滿12箱,請先打板");
        //0：確定 反正值清除數據
        if (tj == 0) {
            //更新打板封板狀態
            palletService.upCARTON((cf),PALLET_OID);
            start(VALUEBM);
        } else {
            int tj1 = JOptionPane.showConfirmDialog(null, "是否取消打板", "提示", JOptionPane.YES_NO_OPTION);
            if (tj1 == 0) {
                Eliminate();
            }else{
                Repeat();
            }

        }
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
