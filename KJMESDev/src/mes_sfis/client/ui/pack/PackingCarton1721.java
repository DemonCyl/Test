package mes_sfis.client.ui.pack;


import base.client.ui.BasePanel;
import base.client.util.component.P_Component_Meta;
import base.vo.UI_InitVO;
import mes_sfis.client.model.bean.MesPackConfig;
import mes_sfis.client.model.bean.PackCarton;
import mes_sfis.client.model.bean.Product;
import mes_sfis.client.model.service.PackageService;
import mes_sfis.client.pdf.PDFCreater;
import mes_sfis.client.pdf.PDFCreater1606;
import mes_sfis.client.sfis.PassForProcedure;
import mes_sfis.client.task.PassDeviceTask;
import mes_sfis.client.util.ConfigJSONUtil;
import mes_sfis.client.util.DataHandler;
import mes_sfis.client.util.ReadConfig2;
import mes_sfis.client.util.SoundUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.util.*;
import java.util.List;

/**
 * Created by Srx_Zhu on 2018/4/11 0011.
 */
public class PackingCarton1721 extends BasePanel {
    public static final String VERSION = "$Name:  $, $Id: PackingCarton1721.java,v 1.20 2018/04/16 09:28:13 Lucy6_Lu Exp $";
    public static final String COPYRIGHT = "Copyright (c) 2008 PEGATRON Inc. All Rights Reserved.";
    private static final Logger logger = LogManager.getLogger(PassDeviceTask.class);
    private String cartonOid = "";
    private String cartonNo = "";
    private String cartonSn = "";
    private String dateCode = "";
    private String Material="C-Mesh 無資料";//新舊料區分
    private String isNew="";
    private PackCarton packCarton = new PackCarton();
    private HashMap<String, String> cartonMap = new HashMap<>();
    private boolean isFirst = true;//是不是第一個
    private boolean isOk = true;//成品碼是否正確
    private int productNum = 0;
    private int productMax;
    private ArrayList<String> isnList = new ArrayList<String>();
    private MesPackConfig mp = new MesPackConfig();
    private PackageService packageService;
    private ArrayList<Product> productList = new ArrayList<Product>();
    private String ISNCode;
    private String eeee;
    private String CSSN;
    private String SNE;
    private String SNA;
	private String SND;
	private Vector isnVec = new Vector();
	private int dateflag;
    private String standStr;
	private int aa1;
	private boolean isss;
	private boolean iss2;
    private String PROJECT_CODE = "COP1721";
    private JPanel contentPanel = new JPanel();
    private JButton jButtonMandatoryPacking = new JButton("強制裝箱");
    private JButton jButtonClear = new JButton("清除");
    private JLabel titleLabel = new JLabel("1721裝箱");
    private JLabel inFactoryMaterialNumLabel = new JLabel("廠內料號");
    private JLabel scanningSSNLabel = new JLabel("已掃描成品碼");
    private JLabel finshScanningSSNLabel = new JLabel("成品碼");
    private JLabel logLabel = new JLabel("Log");
    private JLabel colorLabel = new JLabel("顏色：");
    private JLabel customerMaterialNumLabel = new JLabel("客戶料號");
    private JLabel number_BoxLabel = new JLabel();
    private JLabel number_BoxLabelValue = new JLabel("數量/箱");
    private JLabel BoxLabelValueLabel = new JLabel();
    private JLabel colorLabelValue = new JLabel();
    private JTextField inFactoryMaterialNumText = new JTextField();
    private JTextField productCode = new JTextField("");
    private JComboBox customerMaterialNumBox = new JComboBox();
    private JTextArea checkSSNText = new JTextArea("");
    private JTextArea logText = new JTextArea("");
    private String pc = "";
    private String SSNBIN="";
    private DataHandler dh;
    private String trueMaterial = "";
    private String trueMaterial2 = "";
    private String isRepeatMaterial = "";
    private String isRepeatEEEE = "";
    private String check_field = "";
    private String check_value = "";
    private String check_bin_field = "";
    private String check_bin_value = "";//special
    private ConfigJSONUtil cj = new ConfigJSONUtil();

    public PackingCarton1721(UI_InitVO uiVO) {
        super(uiVO);
        packageService = new PackageService(uiVO);
        dh = new DataHandler(uiVO);
        packCarton.setDh(dh);
        if (cj.getJSON() == null || cj.getJSON().equals("")) {
            PROJECT_CODE = cj.getJSON();
        } else {
            PROJECT_CODE = cj.getJSON();
        }
        if (cj.getJSON().equals("1606")) {
            titleLabel.setText("1606裝箱");
        }
        init();
    }

    void init() {
        checkSSNText.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
        checkSSNText.setLineWrap(true);//自動換行
        checkSSNText.setEditable(false);
        JScrollPane jsCheckSSN = new JScrollPane(checkSSNText);//設置滾動軸的
        jsCheckSSN.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);//設置縱向滾動捲軸總是顯示


        logText.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
        logText.setLineWrap(true);
        logText.setEditable(false);
        JScrollPane jsLog = new JScrollPane(logText);
        jsLog.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


        this.add(contentPanel);
        this.add(jButtonMandatoryPacking);
        this.add(jButtonClear);
        this.add(titleLabel);
        this.add(inFactoryMaterialNumLabel);
        this.add(scanningSSNLabel);
        this.add(finshScanningSSNLabel);
        this.add(colorLabel);
        this.add(customerMaterialNumLabel);
        this.add(number_BoxLabel);
        this.add(BoxLabelValueLabel);
        this.add(inFactoryMaterialNumText);
        this.add(customerMaterialNumBox);
        this.add(number_BoxLabelValue);
        this.add(colorLabelValue);
        this.add(jsCheckSSN);
        this.add(jsLog);
        this.add(logLabel);
        this.add(productCode);

        //清空按鈕監聽事件
        jButtonClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                clearALL();
            }
        });

        jButtonMandatoryPacking.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (productNum < 1) {
                        JOptionPane.showConfirmDialog(null, "箱內無貨，無法裝箱" + "\n", "警告", JOptionPane.PLAIN_MESSAGE);
                        logText.append("箱內無貨，無法裝箱" + "\n");
                        return;
                    } else {
                        confirm();
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        productCode.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent arg0) {
                int key = arg0.getKeyCode();
                pc = productCode.getText().toString().replace(" ", "");
                if (key == '\n') {
                    if ("".equals(pc)) {
                        JOptionPane.showConfirmDialog(null, "請輸入成品碼", "警告", JOptionPane.PLAIN_MESSAGE);
                        logText.append("請輸入成品碼\n");
                        SoundUtil.PlayNo();
                        isOk = false;
                        return;
                    } else {
                        isOk = true;
                        return;
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e1) {
                int key = e1.getKeyCode();
                if (key == '\n') {
                    try {
                        if (!"".equals(pc)) {

							String standStroof = "";
                            //if (mp != null) {
                                if (isOk == false) {
                                    return;
                                }
                                if (pc.length() != 62 && pc.length() != 57) {
                                    JOptionPane.showConfirmDialog(null, "成品碼長度不滿足62位或者57位！！！", "警告", JOptionPane.PLAIN_MESSAGE);
                                    logText.append("成品碼長度不滿足62位或者57位！！\n");
                                    return;
                                }
                                //?取第16,19,20位的?据
                                String str16 = pc.substring(15, 16);
                                String str19 = pc.substring(18,19);
                                String str20 = pc.substring(19,20);
                                System.out.println(standStr+"/////////////////////");
                                //TODO  修改冗餘代碼， 65不可出貨
                                if((standStr==null||"".equals(standStr)) || aa1 == 1){
                                    System.out.println("進入空值賦值");
                                    //logger.info("abc++++++++++");
                                    standStr = str16+str19+str20;
									if("536".equals(standStr) || "540".equals(standStr)){
										standStr = "65";
									}else if("740".equals(standStr)){
										standStr = "65A";
									}else if("A0A".equals(standStr)){
										standStr = "PRQ";
									}
									SND = "M";
									standStroof = standStr;
									aa1 = 1;
									isnVec = new Vector();
                                }else {
                                    System.out.println("進入有值賦值");
                                    String standStrTmp = str16+str19+str20;
									if("536".equals(standStrTmp) || "540".equals(standStrTmp)){
										standStrTmp = "65";
									}else if("740".equals(standStrTmp)){
										standStrTmp = "65A";
									}else if("A0A".equals(standStrTmp)){
										standStrTmp = "PRQ";
									}

                                    System.out.println(standStr+"---------------------");
                                    if(!standStr.equals(standStrTmp)){
                                        System.out.println("返回成功");
                                        //standStr = standStr.substring(0,3);
                                        JOptionPane.showConfirmDialog(null, "該片與箱內的版本號不相同！！！", "警告", JOptionPane.PLAIN_MESSAGE);
                                       return;
                                    }
									standStroof = standStrTmp;
                                    //standStr = standStr.substring(0,3);
                                }
                                System.out.println(standStr+"--------/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*-------111");

                                for (int i = 0; isnList.size() > i; i++) {
                                    if (isnList.get(i).equals(pc)) {
                                        JOptionPane.showConfirmDialog(null, "此成品碼已入箱,請檢查后重新輸入：" + pc + "\n", "警告", JOptionPane.PLAIN_MESSAGE);
                                        logText.append("此成品碼已入箱,請檢查后重新輸入：" + pc + "\n");
                                        SoundUtil.PlayNo();
                                        return;
                                    }
                                }
                            //}

                            List<String> result = packageService.isISNExist(pc);

                            if (result == null) {
                                JOptionPane.showConfirmDialog(null, "該成品碼不存在！：" + pc + "\n", "警告", JOptionPane.PLAIN_MESSAGE);
                                logText.append("該成品碼不存在！：" + pc + "\n");
                                return;
                            }
                            //獲得該片料的狀態。是否為新料。 isNewMaterial      ---1為新料，0為舊料,""??有此站（SP TO BAND）?料。
                             isNew =packageService.isNewMaterial(result.get(0).toString());

                            //TODO 　SNE放在　List<String> result = packageService.isISNExist(pc)　拿出來
                            SNE = packageService.getSNEName(result.get(0).toString());
                            eeee = pc.substring(11, 15);
                            ISNCode = result.get(0).toString();
                            CSSN = result.get(1).toString();
                            SNA = result.get(2).toString();
							int dfg = 0;
							dfg = packageService.dateCheck(ISNCode);

                            //TODO  SRART 搞清楚　snd　SND　isE Europe 到底在幹嘛
							String Europe = "M";
							boolean isE = true;
							if(Europe.equals(SND)){
								isE = false;
							}
							String snd = "";
                            //TODO  result.get(3) 　取得玻璃邊碼
							if(result.get(3) != null || "".equals(result.get(3).toString()))
								snd = result.get(3).toString().substring(11,15);

                            //玻璃碼的特定位數result.get(3).toString().substring(11,15)　可以用來判別國別
							if("KRWF".equals(snd) || "KRWG".equals(snd)){
								Europe = "Y";
							}else{
								Europe = "N";
							}
							if(isE){
								if(!Europe.equals(SND)){
									JOptionPane.showConfirmDialog(null, "玻璃碼的國別碼和箱內不同！：" + pc + "\n", "警告", JOptionPane.PLAIN_MESSAGE);
									logText.append("玻璃碼的國別碼和箱內不同！：" + pc + "\n");
									return;
								}
							}
							SND = Europe;
                            //TODO END

                            //通過首個成品確定這一箱的BIN值&&料&&顏色
                            if (isFirst == true) {
                                //新舊料 區分
                                Material=isNew;
                                SSNBIN =pc.substring(34,35);
                                System.out.println(SSNBIN);

                                if (checkHasPack()) {
                                    return;
                                }
                                if (ISNCode == "") {
                                    JOptionPane.showConfirmDialog(null, "該成品碼不存在！：" + pc + "\n", "警告", JOptionPane.PLAIN_MESSAGE);
                                    logText .append("該成品碼不存在！：" + pc + "\n");
                                    return;
                                }
                                if (!getDoubleValue()) {
                                    return;
                                }
                                if (PROJECT_CODE.equals("COP1721")) {
                                    if (!checkBinOk()) {
                                        return;
                                    }
                                }
								System.out.println(PROJECT_CODE + "+++++++" + check_value + "+++++++" + check_bin_value + "+++++++" + standStroof + "+++++++" + isNew + "+++++++" + Europe);
								isss = packageService.isSpec(ISNCode);
                                //獲取MES_PACK_CONFIG信息
                                mp = packageService.getConfigByEeeeProjectCode(PROJECT_CODE, check_value, check_bin_value,standStroof,isNew,Europe,ISNCode,dfg);
                                if (mp == null) {
									//System.out.println(PROJECT_CODE + "***" + check_value + "***" + check_bin_value + "***" + standStr + "***" + isNew);
                                    JOptionPane.showConfirmDialog(null, "未找到該成品碼信息，請聯繫交管 " + "\n", "警告", JOptionPane.PLAIN_MESSAGE);
                                    return;
                                }
                                try {
                                    cartonSn = packCarton.getCartonSn(PROJECT_CODE);
                                    dateCode = packCarton.getDateCode();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                BoxLabelValueLabel.setText(mp.getPcsCarton().toString());
                                colorLabelValue.setText(check_value);
                                inFactoryMaterialNumText.setText(mp.getKJPN());
                                customerMaterialNumBox.addItem(mp.getOemPN());
								
								dateflag = dfg;
                            } else {
                                if (!SSNBIN.equals(pc.substring(34,35))){
                                    JOptionPane.showConfirmDialog(null, "成品碼分類（第35位BIN值異常）異常 " + "\n", "警告", JOptionPane.PLAIN_MESSAGE);
                                    return;
                                }
                                if (checkHasPack()) {
                                    return;
                                }
                                if (PROJECT_CODE.equals("COP1721")) {
                                    if (checkDoubleValueDifferent()) {
                                        return;
                                    }
                                } else {
                                    if (checkNextBinOk()) {
                                        return;
                                    }
                                }
								iss2 = packageService.isSpec(ISNCode);
								if(isss != iss2){
									JOptionPane.showConfirmDialog(null, "指定料號與非指定不能混箱 " + "\n", "警告", JOptionPane.PLAIN_MESSAGE);
                                    return;
								}
								if(dfg != dateflag){
									JOptionPane.showConfirmDialog(null, "時間段不同不能混箱 " + "\n", "警告", JOptionPane.PLAIN_MESSAGE);
                                    return;
								}
                            }
                            if (mp == null) {
                                return;
                            } else {
								isnVec.add(ISNCode);
                                checkMesPackConfig();
                            }
							aa1 = 2;
							if(("").equals(check_bin_value)){
								SoundUtil.PlayNo();
							}else{
								SoundUtil.PlayBin(check_bin_value);
							}
                        }
                    } catch (Exception e) {
                        //TODO  掃碼裝箱有問題，不可以CATCH，要做適當的提示與處理！！！
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void keyTyped(KeyEvent e2) {
                int key = e2.getKeyCode();
                if (key == '\n') {
                    JOptionPane.showMessageDialog(null, "undefined");
                }
            }
        });
        jButtonMandatoryPacking.setBounds(270, 60, 100, 40);
        jButtonClear.setBounds(520, 60, 100, 40);
        titleLabel.setBounds(270, 10, 100, 20);
        inFactoryMaterialNumLabel.setBounds(20, 160, 100, 40);
        scanningSSNLabel.setBounds(20, 240, 100, 40);
        finshScanningSSNLabel.setBounds(20, 200, 80, 40);
        productCode.setBounds(120, 205, 450, 30);
        inFactoryMaterialNumText.setBounds(120, 165, 140, 30);
        inFactoryMaterialNumText.setEditable(false);
        colorLabel.setBounds(270, 160, 100, 40);
        colorLabelValue.setBounds(320, 160, 100, 40);
        customerMaterialNumLabel.setBounds(400, 160, 100, 40);
        number_BoxLabel.setBounds(400, 220, 100, 40);
        number_BoxLabelValue.setBounds(400, 240, 100, 40);
        BoxLabelValueLabel.setBounds(520, 240, 100, 40);
        customerMaterialNumBox.setBounds(475, 165, 140, 30);
        checkSSNText.setBounds(20, 280, 600, 80);
        jsCheckSSN.setBounds(20, 280, 600, 80);
        logLabel.setBounds(20, 320, 100, 100);
        logText.setBounds(20, 380, 600, 80);
        jsLog.setBounds(20, 380, 600, 80);
    }

    //獲取 SFIS_CHECK_VALUE 和 CHECK_FIELD_VALUE
    public boolean getDoubleValue() {
        check_field = packageService.getCheckField(PROJECT_CODE);
        if (check_field == null) {
            JOptionPane.showConfirmDialog(null, "專案代碼有誤，請聯繫OA處理！！！", "警告", JOptionPane.PLAIN_MESSAGE);
            return false;
        }
        String[] arr = check_field.split("\\.");
        check_value = packageService.getCheckEEEEValue(ISNCode,arr[1],arr[0]);
        System.out.println("-----------------------" + check_value);
        if (check_value == null) {
            JOptionPane.showConfirmDialog(null, "顏色異常，請確認", "警告", JOptionPane.PLAIN_MESSAGE);
            logText.append("顏色異常，請確認！：" + pc + "\n");
            return false;
        }
/*        if(check_value.equals("W")){
            System.out.println("This is W--------------------------------------------------------------");
            check_bin_value = "specialW";
        }
        if(check_value.equals("B")){
            System.out.println("This is B--------------------------------------------------------------");
            check_bin_value = "specialB";
        }*/

        check_bin_field = packageService.getBinCheckField(PROJECT_CODE);
        if (check_bin_field == null) {
            JOptionPane.showConfirmDialog(null, "專案代碼有誤，請聯繫OA處理！！！check_bin_field="+check_bin_field+";PROJECT_CODE="+PROJECT_CODE, "警告", JOptionPane.PLAIN_MESSAGE);
            return false;
        }

        check_bin_value = packageService.checkUmpMaterial(ISNCode);
		/*if(("").equals(check_bin_value)){
			SoundUtil.PlayNo();
		}else{
			SoundUtil.PlayBin(check_bin_value);
		}*/
        return true;
    }

    //檢測是否被裝箱
    public boolean checkHasPack() throws Exception {
        if (packageService.isRepeatISN(ISNCode) == true) {
            JOptionPane.showConfirmDialog(null, "此成品碼已被裝箱！：" + pc + "\n", "警告", JOptionPane.PLAIN_MESSAGE);
            logText.append("此成品碼已被裝箱：" + pc + "\n");
            return true;
        }
        return false;
    }

    //檢查是否不同SFIS_CHECK_VALUE 和 CHECK_FIELD_VALUE
    public boolean checkDoubleValueDifferent() throws Exception {
        String[] arr = check_field.split("\\.");
        isRepeatEEEE = packageService.getCheckEEEEValue(ISNCode, arr[1],arr[0]);
        if (!isRepeatEEEE.equals(check_value)) {
            JOptionPane.showConfirmDialog(null, "此料號為" + isRepeatEEEE + "與箱內不符", "警告", JOptionPane.PLAIN_MESSAGE);
            logText.append("此料號為" + isRepeatEEEE + "與箱內不符" + pc + "\n");
            return true;
        }
        String[] arr2 = check_bin_field.split("\\.");
        isRepeatMaterial = packageService.getCheckBinValue(ISNCode, arr2[2],arr2[1]);
        if (!isRepeatMaterial.equals(check_bin_value)) {
            if (isRepeatMaterial == null || isRepeatMaterial.equals("")) {
                JOptionPane.showConfirmDialog(null, "此料號為NG料，與箱內不符", "警告", JOptionPane.PLAIN_MESSAGE);
                logText.append("此料號為NG料，與箱內不符" + pc + "\n");
            } else {
                JOptionPane.showConfirmDialog(null, "此料號BIN為" + isRepeatMaterial + "與箱內不符", "警告", JOptionPane.PLAIN_MESSAGE);
                logText.append("此料號為" + isRepeatMaterial + "與箱內不符" + pc + "\n");
            }
            return true;
        }
        // 判斷同箱是否都是新舊料
        if(!Material.equals(isNew)){
            if(isNew.equals("1")){
                JOptionPane.showConfirmDialog(null, "此料為新料，與箱內不符", "警告", JOptionPane.PLAIN_MESSAGE);
                logText.append("此料為新料，與箱內不符" + pc + "\n");
            }else if (isNew.equals("0")) {
                JOptionPane.showConfirmDialog(null, "此料為舊料，與箱內不符", "警告", JOptionPane.PLAIN_MESSAGE);
                logText.append("此料為舊料，與箱內不符" + pc + "\n");
            }else {
                JOptionPane.showConfirmDialog(null, "SPtoBand過站異常", "警告", JOptionPane.PLAIN_MESSAGE);
                logText.append("SPtoBand過站異常" + pc + "\n");
            }
            return true;
        }
        return false;
    }

     //檢查Bin值是否異常
    public boolean checkBinOk() throws Exception {
        trueMaterial = packageService.checkUmpMaterial(ISNCode);
        trueMaterial2 = packageService.checkUmpMaterial(pc);

        if (trueMaterial == null ||"".equals(trueMaterial)||trueMaterial2==null||"".equals(trueMaterial2)) {
            JOptionPane.showConfirmDialog(null, "UMP   BIN值異常！：" + pc + "\n", "警告", JOptionPane.PLAIN_MESSAGE);
            logText.append("UMP   BIN值異常！：" + pc + "\n");
            return false;
        }
        if (!trueMaterial.equals("1") && !trueMaterial.equals("2") && !trueMaterial.equals("3")&&!trueMaterial2.equals("1") && !trueMaterial2.equals("2") && !trueMaterial2.equals("3")) {
            JOptionPane.showConfirmDialog(null, "UMP   品質不合格！：" + pc + "\n", "警告", JOptionPane.PLAIN_MESSAGE);
            logText.append("UMP   品質不合格！：" + pc + "\n");
            return false;
        }
        return true;
    }

    //檢查mes_ump_log 表Bin值是否正常
    public boolean checkNextBinOk() throws Exception {
        isRepeatMaterial = packageService.checkUmpMaterial(ISNCode);
        trueMaterial2 = packageService.checkUmpMaterial(pc);
        if (trueMaterial == null ||"".equals(trueMaterial)||trueMaterial2==null||"".equals(trueMaterial2)) {
            JOptionPane.showConfirmDialog(null, "UMP   BIN值異常！：" + pc + "\n", "警告", JOptionPane.PLAIN_MESSAGE);
            logText.append("UMP   BIN值異常！：" + pc + "\n");
            return true;
        } else if (!trueMaterial.equals("1") && !trueMaterial.equals("2") && !trueMaterial.equals("3")&&!trueMaterial2.equals("1") && !trueMaterial2.equals("2") && !trueMaterial2.equals("3")) {
            JOptionPane.showConfirmDialog(null, "UMP   品質不合格！：" + pc + "\n", "警告", JOptionPane.PLAIN_MESSAGE);
            logText.append("UMP   品質不合格！：" + pc + "\n");
            return true;
        }
        return false;
    }

    //依據EEEE取得mes_pack_config
    public void checkMesPackConfig() throws Exception {
        try {

            if (null == mp.getProjectCode()) {
                JOptionPane.showConfirmDialog(null, "該成品碼有誤，請檢查后再輸入" + pc + "\n", "警告", JOptionPane.PLAIN_MESSAGE);
                logText.append("該成品碼有誤，請檢查后再輸入" + pc + "\n");
                SoundUtil.PlayNo();
                return;
            }
            if (isTerminalWorking() == true) {

            } else {
                return;
            }
            if (PROJECT_CODE.equals("1721EVT1_5")) {
            } else {
                if (checkISN() == true) {

                } else {
                    return;
                }
            }

            if (isScrapped() == true) {

            } else {
                return;
            }

            // TODO: ?用存??程
            System.out.println(ISNCode+"**********");
            int count1 = packageService.getCountOne(ISNCode);
            System.out.println("********"+count1+"---------");
            int count2 = packageService.getCountTwo(ISNCode);
            System.out.println("********"+count2+"---------");
            if (count1>0 && count2==0){
                JOptionPane.showConfirmDialog(null, "CNC64 版物料 請退回物料報廢" , "警告", JOptionPane.PLAIN_MESSAGE);
                logText.append("CNC64 版物料 請退回物料報廢" + pc + "\n");
                return;
            }
            int count3 = packageService.checkStatus(ISNCode);
            if(count3==0){
                JOptionPane.showConfirmDialog(null, "該產品當前狀態不是OQC2良品,不可包?" , "警告", JOptionPane.PLAIN_MESSAGE);
                logText.append("該產品當前狀態不是OQC2良品,不可包?" + pc + "\n");
                return;
            }

            showResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //檢查最後站點是否正常
    public boolean isTerminalWorking() throws Exception {
        try {
            String grp = mp.getSfisCheckGrp();
            if (packageService.isTerminalWorking(grp, ISNCode) == false) {
                JOptionPane.showConfirmDialog(null, "最後一站出現異常:" + pc + "\n", "警告", JOptionPane.PLAIN_MESSAGE);
                logText.append("最後一站出現異常:" + pc + "\n");
                SoundUtil.PlayNo();
                //JOptionPane.showConfirmDialog(null, "最後一站出現異常", "警告", JOptionPane.PLAIN_MESSAGE);
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    //檢查SFIS數據庫ISNINFO
    public boolean checkISN() throws Exception {
        try {
            if (packageService.checkISN(ISNCode, eeee) == false) {
                JOptionPane.showConfirmDialog(null, "該成品碼顏色不符或ISN不存在、" + pc + "\n", "警告", JOptionPane.PLAIN_MESSAGE);
                logText.append("該成品碼顏色不符或ISN不存在" + pc + "\n");
                SoundUtil.PlayNo();
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    //檢查SFIS數據庫,該產品是否報廢
    public boolean isScrapped() throws Exception {
        try {
            if (packageService.isScrapped(ISNCode) == true) {
                JOptionPane.showConfirmDialog(null, "該產品已報廢:" + pc + "\n", "警告", JOptionPane.PLAIN_MESSAGE);
                logText.append("該產品已報廢:" + pc + "\n");
                SoundUtil.PlayNo();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    //展示config信息
    public void showResult() throws Exception {

        try {
            productMax = mp.getPcsCarton().intValue();
            if (productNum >= productMax) {
                JOptionPane.showConfirmDialog(null, "模板", "警告", JOptionPane.PLAIN_MESSAGE);
                logText.append("已超過最大數量，請裝箱\n");
                SoundUtil.PlayNice();
                return;
            }
            productNum++;
            isFirst = false;

            // 新舊料 播放不同聲音
            if(isNew.equals("1")){
              //  SoundUtil.PlayNEWM(1000);
                isNew="新料，";
            }else if(isNew.equals("0")) {
             //   SoundUtil.PlayOLDM(1000);
                isNew="舊料，";
            }else{
                isNew="C-Mesh 無資料。";
            }

            //SoundUtil.PlayOk(1000);
            isnList.add(pc);
            logText.append(isNew+"入箱成功！當前箱內數量:" + productNum + "\n");
            checkSSNText.append(pc + "\n");
            productCode.setText("");


            cartonNo = mp.getVender() + mp.getSiteCode() + cartonSn;
            System.out.println("cartonSn: " + cartonSn);
            System.out.println("dateCode: " + dateCode);
            System.out.println("cartonNo: " + cartonNo);
            Product product = new Product();

            product.setProjectCode(PROJECT_CODE);
            product.setCartonOid(cartonOid);
            product.setMSN(ISNCode);
            product.setCSN(CSSN);
            product.setSSN(pc);
            product.setSN1(SNA);
            product.setSN2(trueMaterial);
            product.setSN3("");
            product.setSN4("");
            product.setSN5("");
            product.setSN6("");
            product.setSN7("");
            product.setSN8("");
            product.setSN9("");
            product.setSN10("");
            product.setStatus(null);
            productList.add(product);

            if (productNum >= productMax) {
                confirm();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //確認裝箱
    public void confirm() throws Exception {
        if (productNum != productMax) {
            BigDecimal number = new BigDecimal(0);
            int value = productNum;
            number = BigDecimal.valueOf((int) value);

            mp.setPcsCarton(number);
        }

        cartonMap.put("vendor", mp.getVendor());
        cartonMap.put("oemPN", mp.getOemPN());
        cartonMap.put("dateStr", dateCode);//DATE_CODE
        cartonMap.put("venderCode", mp.getVender()); //VENDER
        cartonMap.put("venderSite", mp.getSiteCode());
        cartonMap.put("applePN", mp.getApplepn());
        cartonMap.put("rev", mp.getRev());
        cartonMap.put("config", mp.getConfig());
        cartonMap.put("desc", mp.getDescription());//DESCRIPTION
        cartonMap.put("lc", mp.getLc());
        cartonMap.put("qty", mp.getPcsCarton().toString());//數量
        cartonMap.put("batch", mp.getBatChno());
        cartonMap.put("cartonNo", cartonNo);//PICK_NO
        cartonMap.put("stage", mp.getStage());
        if ("W".equals(SNE)){
            if("新料，".equals(isNew)){
                /*if ("1".equals(check_bin_value)){
                    cartonMap.put("oemComments", "YFM00Y0K2-106-N"+"\r\n"+standStr+"CW"); //kj_pn
                }else if("2".equals(check_bin_value)){
                    cartonMap.put("oemComments", "YFM00Y0K9-106-N"+"\r\n"+standStr+"CW"); //kj_pn
                }else {
                    cartonMap.put("oemComments", "YFM00Y0K4-106-N"+"\r\n"+standStr+"CW"); //kj_pn
                }*/
				if(dateflag != 0){
					cartonMap.put("oemComments", mp.getKJPN()+"\r\n"+standStr+"CW New De-ano");
				}else{
					cartonMap.put("oemComments", mp.getKJPN()+"\r\n"+standStr+"CW");
				}
            }else {
				if(dateflag != 0){
					cartonMap.put("oemComments", mp.getKJPN()+"\r\n"+standStr+"CB New De-ano"); //kj_pn
				}else{
					cartonMap.put("oemComments", mp.getKJPN()+"\r\n"+standStr+"CB"); //kj_pn
				}
            }
        }else{
			if(dateflag != 0){
				cartonMap.put("oemComments", mp.getKJPN()+"\r\n"+standStr + " New De-ano"); //kj_pn
			}else{
				cartonMap.put("oemComments", mp.getKJPN()+"\r\n"+standStr); //kj_pn
			}
        }

        if (PROJECT_CODE.equals("COP1606")) {
            PDFCreater1606 pdfCreater = new PDFCreater1606("d://mes_data/carton.pdf");
            pdfCreater.addPage(cartonMap);
            pdfCreater.close();
            pdfCreater.printPdf();
        } else {
            PDFCreater pdfCreater = new PDFCreater("d://mes_data/carton.pdf");
            pdfCreater.addPage(cartonMap);
            pdfCreater.close();
            pdfCreater.printPdf();
        }

        HashMap<String, String> insertMap = new HashMap<>();
        if (productNum != productMax) {
            BigDecimal number = new BigDecimal(0);
            int value = productNum;
            number = BigDecimal.valueOf((int) value);

            mp.setPcsCarton(number);
        }
        insertMap.put("CARTON_OID", "sys_guid()");
        insertMap.put("PROJECT_CODE", PROJECT_CODE);
        insertMap.put("KJ_PN", mp.getKJPN().toString());
        insertMap.put("CARTON_NO", cartonNo);
        insertMap.put("DATE_CODE", dateCode);
        insertMap.put("QTY", mp.getPcsCarton().toString());
        insertMap.put("LOG_SYSTEMDATE", "sysdate");
        insertMap.put("LOG_EMPLOYEE_OID", uiVO.getEmployee_oid());
        insertMap.put("SNAPSHOT", "");
        insertMap.put("JSON", "");
        insertMap.put("MEMO", "");
        insertMap.put("IS_CLOSE", "1");
        insertMap.put("IS_BREAK", "0");
        insertMap.put("CLOSE_SYSTEMDATE", "sysdate");
        insertMap.put("BREAK_SYSTEMDATE", "");
        insertMap.put("STATUS", "");
        insertMap.put("LAST_SYSTEMDATE", "sysdate");
        insertMap.put("PALLET_OID", "");
        insertMap.put("PICK_OID", "");
        insertMap.put("MO_ID", "");
        insertMap.put("OEMPN", mp.getOemPN().toString());
        packageService.insertCarton(insertMap);
        int tj = JOptionPane.showConfirmDialog(null, "確認裝箱&下一箱", "提示", JOptionPane.YES_NO_OPTION);
//0：確定 否清除數據
        if (tj == 0) {
            logText.append("正在裝箱");
        } else {
            int ti = JOptionPane.showConfirmDialog(null, "取消裝箱？", "提示", JOptionPane.YES_NO_OPTION);
//0：確定 否清除數據
            if (ti == 0) {
                deny();
                return;
            } else {
                logText.append("正在裝箱");
            }

        }

/*
        LoginOutClient loc = new LoginOutClient();
        loc.setOp("K18001314");
        loc.setDevice("111043");
        loc.setStatus("1");
        loc.init();
        loc.start();
        for (int i = 0; i < isnList.size(); i++) {
            PassDeviceClient pdc = new PassDeviceClient();
            pdc.setData(isnList.get(i));
            pdc.setDevice("111043");
            pdc.init();
            pdc.start();
        }*/
        packageService.sealing(cartonNo);
        cartonOid = packageService.findByCartonNo(cartonNo);
        for (int i = 0; i < productList.size(); i++) {
            productList.get(i).setCartonOid(cartonOid);
        }
        packageService.insertCartonISN(productList, productNum);
		
		procedureFun(cartonNo);

        clearALL();

        logText.append("裝箱成功" + "\n");
    }
	
	private void procedureFun(String cartonNo){
		PassForProcedure passForProcedure = new PassForProcedure();
		try {
			String device = "";
			ReadConfig2 a = new ReadConfig2();
			a.hashINI();
            device = a.iniHash.get("epega.exe.device").toString();
			passForProcedure.open();
			HashMap map = new HashMap();
			map.put("DEVICE", device);
			map.put("OP", uiVO.getLogin_id());
			map.put("STATUS", "2");
			passForProcedure.procedureLogin(map);
			map.put("STATUS", "1");
			passForProcedure.procedureLogin(map);
			for(int i = 0;i < isnVec.size();i++){
				map.put("ISN", cartonNo);
				passForProcedure.procedurePass(map);
				map.put("ISN", (String)isnVec.get(i));
				String flag = passForProcedure.procedurePass(map);
				String chkstatus = flag.substring(5, 16);
				while ("Login First".equals(chkstatus)) {
					passForProcedure.procedureLogin(map);
					map.put("ISN", cartonNo);
					passForProcedure.procedurePass(map);
					map.put("ISN", (String)isnVec.get(i));
					flag = passForProcedure.procedurePass(map);
					chkstatus = flag.substring(5, 16);
				}
			}
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, "配置文件異常，請聯繫 OA     注： 配置文件格式為 UNICODE", "警告", JOptionPane.PLAIN_MESSAGE);
        } finally {
			passForProcedure.close();
			isnVec = new Vector();
		}
	}

    //取消裝箱、清空數據
    public void deny() {
        clearALL();
    }

    //清除所有UI數據及存儲的LIST
    public void clearALL() {
         /*workOrderNumberText.setText("");*/
        inFactoryMaterialNumText.setText("");
        productCode.setText("");
        checkSSNText.setText("");
        logText.setText("");
        BoxLabelValueLabel.setText("");
        colorLabelValue.setText("");
        customerMaterialNumBox.removeAllItems();
        productNum = 0;
        isnList.clear();
        check_field = "";
        check_value = "";
        productList.clear();
        isFirst = true;
        cartonMap.clear();
        cartonNo = "";
        cartonOid = "";
        mp = null;
        eeee = "";
        trueMaterial = "";
        isRepeatEEEE = "";
        isRepeatMaterial = "";
        standStr = "";

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