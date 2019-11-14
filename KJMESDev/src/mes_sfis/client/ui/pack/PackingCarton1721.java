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
    private String Material="C-Mesh �L���";//�s�®ưϤ�
    private String isNew="";
    private PackCarton packCarton = new PackCarton();
    private HashMap<String, String> cartonMap = new HashMap<>();
    private boolean isFirst = true;//�O���O�Ĥ@��
    private boolean isOk = true;//���~�X�O�_���T
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
    private JButton jButtonMandatoryPacking = new JButton("�j��˽c");
    private JButton jButtonClear = new JButton("�M��");
    private JLabel titleLabel = new JLabel("1721�˽c");
    private JLabel inFactoryMaterialNumLabel = new JLabel("�t���Ƹ�");
    private JLabel scanningSSNLabel = new JLabel("�w���y���~�X");
    private JLabel finshScanningSSNLabel = new JLabel("���~�X");
    private JLabel logLabel = new JLabel("Log");
    private JLabel colorLabel = new JLabel("�C��G");
    private JLabel customerMaterialNumLabel = new JLabel("�Ȥ�Ƹ�");
    private JLabel number_BoxLabel = new JLabel();
    private JLabel number_BoxLabelValue = new JLabel("�ƶq/�c");
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
            titleLabel.setText("1606�˽c");
        }
        init();
    }

    void init() {
        checkSSNText.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
        checkSSNText.setLineWrap(true);//�۰ʴ���
        checkSSNText.setEditable(false);
        JScrollPane jsCheckSSN = new JScrollPane(checkSSNText);//�]�m�u�ʶb��
        jsCheckSSN.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);//�]�m�a�V�u�ʱ��b�`�O���


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

        //�M�ū��s��ť�ƥ�
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
                        JOptionPane.showConfirmDialog(null, "�c���L�f�A�L�k�˽c" + "\n", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
                        logText.append("�c���L�f�A�L�k�˽c" + "\n");
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
                        JOptionPane.showConfirmDialog(null, "�п�J���~�X", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
                        logText.append("�п�J���~�X\n");
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
                                    JOptionPane.showConfirmDialog(null, "���~�X���פ�����62��Ϊ�57��I�I�I", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
                                    logText.append("���~�X���פ�����62��Ϊ�57��I�I\n");
                                    return;
                                }
                                //?����16,19,20�쪺?�u
                                String str16 = pc.substring(15, 16);
                                String str19 = pc.substring(18,19);
                                String str20 = pc.substring(19,20);
                                System.out.println(standStr+"/////////////////////");
                                //TODO  �ק菉�l�N�X�A 65���i�X�f
                                if((standStr==null||"".equals(standStr)) || aa1 == 1){
                                    System.out.println("�i�J�ŭȽ��");
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
                                    System.out.println("�i�J���Ƚ��");
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
                                        System.out.println("��^���\");
                                        //standStr = standStr.substring(0,3);
                                        JOptionPane.showConfirmDialog(null, "�Ӥ��P�c�������������ۦP�I�I�I", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
                                       return;
                                    }
									standStroof = standStrTmp;
                                    //standStr = standStr.substring(0,3);
                                }
                                System.out.println(standStr+"--------/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*-------111");

                                for (int i = 0; isnList.size() > i; i++) {
                                    if (isnList.get(i).equals(pc)) {
                                        JOptionPane.showConfirmDialog(null, "�����~�X�w�J�c,���ˬd�Z���s��J�G" + pc + "\n", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
                                        logText.append("�����~�X�w�J�c,���ˬd�Z���s��J�G" + pc + "\n");
                                        SoundUtil.PlayNo();
                                        return;
                                    }
                                }
                            //}

                            List<String> result = packageService.isISNExist(pc);

                            if (result == null) {
                                JOptionPane.showConfirmDialog(null, "�Ӧ��~�X���s�b�I�G" + pc + "\n", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
                                logText.append("�Ӧ��~�X���s�b�I�G" + pc + "\n");
                                return;
                            }
                            //��o�Ӥ��ƪ����A�C�O�_���s�ơC isNewMaterial      ---1���s�ơA0���®�,""??�������]SP TO BAND�^?�ơC
                             isNew =packageService.isNewMaterial(result.get(0).toString());

                            //TODO �@SNE��b�@List<String> result = packageService.isISNExist(pc)�@���X��
                            SNE = packageService.getSNEName(result.get(0).toString());
                            eeee = pc.substring(11, 15);
                            ISNCode = result.get(0).toString();
                            CSSN = result.get(1).toString();
                            SNA = result.get(2).toString();
							int dfg = 0;
							dfg = packageService.dateCheck(ISNCode);

                            //TODO  SRART �d�M���@snd�@SND�@isE Europe �쩳�b�F��
							String Europe = "M";
							boolean isE = true;
							if(Europe.equals(SND)){
								isE = false;
							}
							String snd = "";
                            //TODO  result.get(3) �@���o������X
							if(result.get(3) != null || "".equals(result.get(3).toString()))
								snd = result.get(3).toString().substring(11,15);

                            //�����X���S�w���result.get(3).toString().substring(11,15)�@�i�H�ΨӧP�O��O
							if("KRWF".equals(snd) || "KRWG".equals(snd)){
								Europe = "Y";
							}else{
								Europe = "N";
							}
							if(isE){
								if(!Europe.equals(SND)){
									JOptionPane.showConfirmDialog(null, "�����X����O�X�M�c�����P�I�G" + pc + "\n", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
									logText.append("�����X����O�X�M�c�����P�I�G" + pc + "\n");
									return;
								}
							}
							SND = Europe;
                            //TODO END

                            //�q�L���Ӧ��~�T�w�o�@�c��BIN��&&��&&�C��
                            if (isFirst == true) {
                                //�s�®� �Ϥ�
                                Material=isNew;
                                SSNBIN =pc.substring(34,35);
                                System.out.println(SSNBIN);

                                if (checkHasPack()) {
                                    return;
                                }
                                if (ISNCode == "") {
                                    JOptionPane.showConfirmDialog(null, "�Ӧ��~�X���s�b�I�G" + pc + "\n", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
                                    logText .append("�Ӧ��~�X���s�b�I�G" + pc + "\n");
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
                                //���MES_PACK_CONFIG�H��
                                mp = packageService.getConfigByEeeeProjectCode(PROJECT_CODE, check_value, check_bin_value,standStroof,isNew,Europe,ISNCode,dfg);
                                if (mp == null) {
									//System.out.println(PROJECT_CODE + "***" + check_value + "***" + check_bin_value + "***" + standStr + "***" + isNew);
                                    JOptionPane.showConfirmDialog(null, "�����Ӧ��~�X�H���A���pô��� " + "\n", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
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
                                    JOptionPane.showConfirmDialog(null, "���~�X�����]��35��BIN�Ȳ��`�^���` " + "\n", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
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
									JOptionPane.showConfirmDialog(null, "���w�Ƹ��P�D���w����V�c " + "\n", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
                                    return;
								}
								if(dfg != dateflag){
									JOptionPane.showConfirmDialog(null, "�ɶ��q���P����V�c " + "\n", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
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
                        //TODO  ���X�˽c�����D�A���i�HCATCH�A�n���A�����ܻP�B�z�I�I�I
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

    //��� SFIS_CHECK_VALUE �M CHECK_FIELD_VALUE
    public boolean getDoubleValue() {
        check_field = packageService.getCheckField(PROJECT_CODE);
        if (check_field == null) {
            JOptionPane.showConfirmDialog(null, "�M�ץN�X���~�A���pôOA�B�z�I�I�I", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
            return false;
        }
        String[] arr = check_field.split("\\.");
        check_value = packageService.getCheckEEEEValue(ISNCode,arr[1],arr[0]);
        System.out.println("-----------------------" + check_value);
        if (check_value == null) {
            JOptionPane.showConfirmDialog(null, "�C�ⲧ�`�A�нT�{", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
            logText.append("�C�ⲧ�`�A�нT�{�I�G" + pc + "\n");
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
            JOptionPane.showConfirmDialog(null, "�M�ץN�X���~�A���pôOA�B�z�I�I�Icheck_bin_field="+check_bin_field+";PROJECT_CODE="+PROJECT_CODE, "ĵ�i", JOptionPane.PLAIN_MESSAGE);
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

    //�˴��O�_�Q�˽c
    public boolean checkHasPack() throws Exception {
        if (packageService.isRepeatISN(ISNCode) == true) {
            JOptionPane.showConfirmDialog(null, "�����~�X�w�Q�˽c�I�G" + pc + "\n", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
            logText.append("�����~�X�w�Q�˽c�G" + pc + "\n");
            return true;
        }
        return false;
    }

    //�ˬd�O�_���PSFIS_CHECK_VALUE �M CHECK_FIELD_VALUE
    public boolean checkDoubleValueDifferent() throws Exception {
        String[] arr = check_field.split("\\.");
        isRepeatEEEE = packageService.getCheckEEEEValue(ISNCode, arr[1],arr[0]);
        if (!isRepeatEEEE.equals(check_value)) {
            JOptionPane.showConfirmDialog(null, "���Ƹ���" + isRepeatEEEE + "�P�c������", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
            logText.append("���Ƹ���" + isRepeatEEEE + "�P�c������" + pc + "\n");
            return true;
        }
        String[] arr2 = check_bin_field.split("\\.");
        isRepeatMaterial = packageService.getCheckBinValue(ISNCode, arr2[2],arr2[1]);
        if (!isRepeatMaterial.equals(check_bin_value)) {
            if (isRepeatMaterial == null || isRepeatMaterial.equals("")) {
                JOptionPane.showConfirmDialog(null, "���Ƹ���NG�ơA�P�c������", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
                logText.append("���Ƹ���NG�ơA�P�c������" + pc + "\n");
            } else {
                JOptionPane.showConfirmDialog(null, "���Ƹ�BIN��" + isRepeatMaterial + "�P�c������", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
                logText.append("���Ƹ���" + isRepeatMaterial + "�P�c������" + pc + "\n");
            }
            return true;
        }
        // �P�_�P�c�O�_���O�s�®�
        if(!Material.equals(isNew)){
            if(isNew.equals("1")){
                JOptionPane.showConfirmDialog(null, "���Ƭ��s�ơA�P�c������", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
                logText.append("���Ƭ��s�ơA�P�c������" + pc + "\n");
            }else if (isNew.equals("0")) {
                JOptionPane.showConfirmDialog(null, "���Ƭ��®ơA�P�c������", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
                logText.append("���Ƭ��®ơA�P�c������" + pc + "\n");
            }else {
                JOptionPane.showConfirmDialog(null, "SPtoBand�L�����`", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
                logText.append("SPtoBand�L�����`" + pc + "\n");
            }
            return true;
        }
        return false;
    }

     //�ˬdBin�ȬO�_���`
    public boolean checkBinOk() throws Exception {
        trueMaterial = packageService.checkUmpMaterial(ISNCode);
        trueMaterial2 = packageService.checkUmpMaterial(pc);

        if (trueMaterial == null ||"".equals(trueMaterial)||trueMaterial2==null||"".equals(trueMaterial2)) {
            JOptionPane.showConfirmDialog(null, "UMP   BIN�Ȳ��`�I�G" + pc + "\n", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
            logText.append("UMP   BIN�Ȳ��`�I�G" + pc + "\n");
            return false;
        }
        if (!trueMaterial.equals("1") && !trueMaterial.equals("2") && !trueMaterial.equals("3")&&!trueMaterial2.equals("1") && !trueMaterial2.equals("2") && !trueMaterial2.equals("3")) {
            JOptionPane.showConfirmDialog(null, "UMP   �~�褣�X��I�G" + pc + "\n", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
            logText.append("UMP   �~�褣�X��I�G" + pc + "\n");
            return false;
        }
        return true;
    }

    //�ˬdmes_ump_log ��Bin�ȬO�_���`
    public boolean checkNextBinOk() throws Exception {
        isRepeatMaterial = packageService.checkUmpMaterial(ISNCode);
        trueMaterial2 = packageService.checkUmpMaterial(pc);
        if (trueMaterial == null ||"".equals(trueMaterial)||trueMaterial2==null||"".equals(trueMaterial2)) {
            JOptionPane.showConfirmDialog(null, "UMP   BIN�Ȳ��`�I�G" + pc + "\n", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
            logText.append("UMP   BIN�Ȳ��`�I�G" + pc + "\n");
            return true;
        } else if (!trueMaterial.equals("1") && !trueMaterial.equals("2") && !trueMaterial.equals("3")&&!trueMaterial2.equals("1") && !trueMaterial2.equals("2") && !trueMaterial2.equals("3")) {
            JOptionPane.showConfirmDialog(null, "UMP   �~�褣�X��I�G" + pc + "\n", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
            logText.append("UMP   �~�褣�X��I�G" + pc + "\n");
            return true;
        }
        return false;
    }

    //�̾�EEEE���omes_pack_config
    public void checkMesPackConfig() throws Exception {
        try {

            if (null == mp.getProjectCode()) {
                JOptionPane.showConfirmDialog(null, "�Ӧ��~�X���~�A���ˬd�Z�A��J" + pc + "\n", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
                logText.append("�Ӧ��~�X���~�A���ˬd�Z�A��J" + pc + "\n");
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

            // TODO: ?�Φs??�{
            System.out.println(ISNCode+"**********");
            int count1 = packageService.getCountOne(ISNCode);
            System.out.println("********"+count1+"---------");
            int count2 = packageService.getCountTwo(ISNCode);
            System.out.println("********"+count2+"---------");
            if (count1>0 && count2==0){
                JOptionPane.showConfirmDialog(null, "CNC64 ������ �аh�^���Ƴ��o" , "ĵ�i", JOptionPane.PLAIN_MESSAGE);
                logText.append("CNC64 ������ �аh�^���Ƴ��o" + pc + "\n");
                return;
            }
            int count3 = packageService.checkStatus(ISNCode);
            if(count3==0){
                JOptionPane.showConfirmDialog(null, "�Ӳ��~��e���A���OOQC2�}�~,���i�]?" , "ĵ�i", JOptionPane.PLAIN_MESSAGE);
                logText.append("�Ӳ��~��e���A���OOQC2�}�~,���i�]?" + pc + "\n");
                return;
            }

            showResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //�ˬd�̫᯸�I�O�_���`
    public boolean isTerminalWorking() throws Exception {
        try {
            String grp = mp.getSfisCheckGrp();
            if (packageService.isTerminalWorking(grp, ISNCode) == false) {
                JOptionPane.showConfirmDialog(null, "�̫�@���X�{���`:" + pc + "\n", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
                logText.append("�̫�@���X�{���`:" + pc + "\n");
                SoundUtil.PlayNo();
                //JOptionPane.showConfirmDialog(null, "�̫�@���X�{���`", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    //�ˬdSFIS�ƾڮwISNINFO
    public boolean checkISN() throws Exception {
        try {
            if (packageService.checkISN(ISNCode, eeee) == false) {
                JOptionPane.showConfirmDialog(null, "�Ӧ��~�X�C�⤣�ũ�ISN���s�b�B" + pc + "\n", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
                logText.append("�Ӧ��~�X�C�⤣�ũ�ISN���s�b" + pc + "\n");
                SoundUtil.PlayNo();
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    //�ˬdSFIS�ƾڮw,�Ӳ��~�O�_���o
    public boolean isScrapped() throws Exception {
        try {
            if (packageService.isScrapped(ISNCode) == true) {
                JOptionPane.showConfirmDialog(null, "�Ӳ��~�w���o:" + pc + "\n", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
                logText.append("�Ӳ��~�w���o:" + pc + "\n");
                SoundUtil.PlayNo();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    //�i��config�H��
    public void showResult() throws Exception {

        try {
            productMax = mp.getPcsCarton().intValue();
            if (productNum >= productMax) {
                JOptionPane.showConfirmDialog(null, "�ҪO", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
                logText.append("�w�W�L�̤j�ƶq�A�и˽c\n");
                SoundUtil.PlayNice();
                return;
            }
            productNum++;
            isFirst = false;

            // �s�®� ���񤣦P�n��
            if(isNew.equals("1")){
              //  SoundUtil.PlayNEWM(1000);
                isNew="�s�ơA";
            }else if(isNew.equals("0")) {
             //   SoundUtil.PlayOLDM(1000);
                isNew="�®ơA";
            }else{
                isNew="C-Mesh �L��ơC";
            }

            //SoundUtil.PlayOk(1000);
            isnList.add(pc);
            logText.append(isNew+"�J�c���\�I��e�c���ƶq:" + productNum + "\n");
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

    //�T�{�˽c
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
        cartonMap.put("qty", mp.getPcsCarton().toString());//�ƶq
        cartonMap.put("batch", mp.getBatChno());
        cartonMap.put("cartonNo", cartonNo);//PICK_NO
        cartonMap.put("stage", mp.getStage());
        if ("W".equals(SNE)){
            if("�s�ơA".equals(isNew)){
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
        int tj = JOptionPane.showConfirmDialog(null, "�T�{�˽c&�U�@�c", "����", JOptionPane.YES_NO_OPTION);
//0�G�T�w �_�M���ƾ�
        if (tj == 0) {
            logText.append("���b�˽c");
        } else {
            int ti = JOptionPane.showConfirmDialog(null, "�����˽c�H", "����", JOptionPane.YES_NO_OPTION);
//0�G�T�w �_�M���ƾ�
            if (ti == 0) {
                deny();
                return;
            } else {
                logText.append("���b�˽c");
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

        logText.append("�˽c���\" + "\n");
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
            JOptionPane.showConfirmDialog(null, "�t�m��󲧱`�A���pô OA     �`�G �t�m���榡�� UNICODE", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
        } finally {
			passForProcedure.close();
			isnVec = new Vector();
		}
	}

    //�����˽c�B�M�żƾ�
    public void deny() {
        clearALL();
    }

    //�M���Ҧ�UI�ƾڤΦs�x��LIST
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