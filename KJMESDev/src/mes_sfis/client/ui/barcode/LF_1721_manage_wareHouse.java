package mes_sfis.client.ui.barcode;

import base.client.ui.BasePanel;
import base.client.util.BaseServletAgent;
import base.client.util.CloneArray_ChangeStr;
import base.client.util.component.P_Component_Meta;
import base.enums.CommandName;
import base.enums.DataSourceType;
import base.vo.BaseVO;
import base.vo.ResultVO;
import base.vo.UI_InitVO;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import mes_sfis.client.util.DataHandler;
import mes_sfis.client.util.SoundUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * Created by Feng1_Lu on 2017/12/29.
 */
public class LF_1721_manage_wareHouse extends BasePanel {
    public static final String VERSION = "$Name:  $, $Id: LF_1721_manage_wareHouse.java,v 1.49 2018/04/11 11:15:30 Lucy6_Lu Exp $";
    public static final String COPYRIGHT = "Copyright (c) 2008 PEGATRON Inc. All Rights Reserved.";
    private static final String driver = "oracle.jdbc.driver.OracleDriver";
    private Color color = new Color(106, 106, 255);
    private Font font = new Font("宋體", Font.BOLD, 15);
    private Font font2 = new Font("宋體", Font.BOLD, 20);
    private JLabel lhouse, Desc, ISN, KJNO, BatchNO, BISN, OKISN, OKISNTitle, CheckIsn, personNo, Storage, CartonNo, Storage_2, CartonNo_2, BaoFeiSSN, BaoFeiCSSN, BaoFeiSSN2, BaoFeiCSSN2;
    private JRadioButton radioIn, radioOut, radioBatch, radioISN, radioDrop, radioCenter;//radioDrop 除賬，radioCenter銷毀
    private JTextField JISN, JKJNO, JBatchNO, JBISN, OKISN_NUM, JDesc, JCheckIsn, JpersonNo, JStorage, JCartonNo, JStorage_2, JCartonNo_2, JBaoFeiSSN, JBaoFeiCSSN, JBaoFeiSSN2, JBaoFeiCSSN2;
    private JComboBox comboBox;
    private JCheckBox CheckBox;
    private JButton restart, end;
    private JTable table;
    private JScrollPane JSP;
    private String[] tableHead = {"ISN", "料號"};
    private Object[][] data = new Object[][]{{" ", ""}};
    private HashMap mp = new HashMap();//入庫mp，i，ISN，
    private HashMap mmp = new HashMap();//入庫mmp，ISN，KJ
    private HashMap ppXX = new HashMap();
    private int i = 0;
    private int DBNum = 0;
    private MyTableModel TM;
    private com.lowagie.text.Font Pfont;
    private String TIM = null;
    private String BatchNumber;
    private String type;
    private String Section, Grp;
    //private UI_InitVO xx;
    private String LoginNo;
    private String LoginName;
    private String zhang;//控制狀態，ISN以出庫。
    private String power = null;
    private DataHandler dh = null;
    private boolean isDestruction = false;
    private String trueBatchNo = "";
    private String trueBATCHISN = "";
    private int ISNnumber = 0;
    private String checkISNBatchNo = "";
    private String nextISNBatchNo = "";
    private List JISNList = new ArrayList();
    private List JBISNList = new ArrayList();

    public LF_1721_manage_wareHouse(UI_InitVO uiVO) {
        super(uiVO);
        dh = new DataHandler(uiVO);
        LoginNo = uiVO.getLogin_id();
        power = UserPower();
        try {
            SelectLoginName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        init(power);
    }

    void init(String power) {
        lhouse = MyLable("線邊倉名稱：", 10, 20);
        lhouse.setVisible(true);
        comboBox = MyComboBox();
        DB_ComboBox_Data(power);
        radioIn = MyRadioButton("入庫", 60, 60);
        radioOut = MyRadioButton("出庫", 150, 60);
        radioDrop = MyRadioButton("除賬", 240, 60);
        radioDrop.setVisible(false);
        radioCenter = MyRadioButton("銷毀", 240, 60);
        radioCenter.setVisible(false);

        personNo = MyLable("工號：", 10, 100);
        personNo.setVisible(false);
        JpersonNo = MyTextField(200, 100);
        JpersonNo.setVisible(false);


        Desc = MyLable("說明：", 10, 140);
        Desc.setVisible(true);
        JDesc = MyTextField(200, 140);
        JDesc.setVisible(true);

        ISN = MyLable("請刷ISN：", 10, 180);
        JISN = MyTextField(200, 180);
        KJNO = MyLable("鎧嘉料號：", 10, 220);
        JKJNO = MyTextField(200, 220);
        end = MyButton("執行", 60, 420);
        restart = MyButton("重置", 200, 420);

        ButtonGroup bg = new ButtonGroup();
        bg.add(radioIn);
        bg.add(radioOut);
        bg.add(radioDrop);
        bg.add(radioCenter);

        radioBatch = MyRadioButton("刷批號", 60, 180);
        radioBatch.setEnabled(true);
        radioBatch.setVisible(false);

        radioISN = MyRadioButton("刷ISN", 150, 180);
        radioISN.setEnabled(true);
        radioISN.setVisible(false);
        ButtonGroup gb = new ButtonGroup();
        gb.add(radioBatch);
        gb.add(radioISN);

        BatchNO = MyLable("請刷批號：", 10, 220);
        JBatchNO = MyTextField(200, 220);
        BISN = MyLable("請刷ISN：", 10, 220);
        JBISN = MyTextField(200, 220);
        CheckBox = MyCheckBox("是否核對ISN", 60, 380);
        CheckBox.setEnabled(false);
        CheckIsn = MyLable("請核對ISN：", 10, 340);
        JCheckIsn = MyTextField(200, 340);
        Storage = MyLable("儲位：", 10, 260);
        JStorage = MyTextField(200, 260);
        CartonNo = MyLable("箱號：", 10, 300);
        JCartonNo = MyTextField(200, 300);
        Storage_2 = MyLable("儲位：", 10, 260);
        JStorage_2 = MyTextField(200, 260);
        CartonNo_2 = MyLable("箱號：", 10, 300);
        JCartonNo_2 = MyTextField(200, 300);
        BaoFeiSSN = MyLable("請刷成品碼", 10, 340);
        JBaoFeiSSN = MyTextField(200, 340);
        BaoFeiCSSN = MyLable("請刷簡明碼", 10, 380);
        JBaoFeiCSSN = MyTextField(200, 380);

        BaoFeiSSN2 = MyLable("請刷成品碼", 10, 340);
        JBaoFeiSSN2 = MyTextField(200, 340);
        BaoFeiCSSN2 = MyLable("請刷簡明碼", 10, 380);
        JBaoFeiCSSN2 = MyTextField(200, 380);


        OKISN = MyLable("已刷ISN數量：", 370, 20);
        OKISN.setVisible(true);
        OKISN_NUM = MyTextField(540, 20);
        OKISN_NUM.setVisible(true);
        OKISN_NUM.setEditable(false);
        OKISNTitle = MyLable("已刷ISN清單：", 370, 60);
        OKISNTitle.setVisible(true);

        table = MyTable();
        JSP = new JScrollPane(table);
        JSP.getViewport().setBackground(Color.white);
        JSP.setBounds(400, 100, 400, 400);

        // radioIn.isSelected();
        add(lhouse);
        add(radioIn);
        add(radioOut);
        add(Desc);
        add(comboBox);
        add(JDesc);
        add(JISN);
        add(radioDrop);
        add(CheckBox);
        add(KJNO);
        add(JKJNO);
        add(restart);
        add(end);
        add(ISN);
        add(radioBatch);
        add(radioISN);
        add(JSP);
        add(radioCenter);
        add(Storage);
        add(JStorage);
        add(BatchNO);
        add(JBatchNO);
        add(BISN);
        add(JBISN);
        add(OKISN);
        add(OKISN_NUM);
        add(OKISNTitle);
        add(CheckIsn);
        add(JCheckIsn);
        add(personNo);
        add(JpersonNo);
        add(CartonNo);
        add(JCartonNo);
        add(JCartonNo_2);
        add(CartonNo_2);
        add(JStorage_2);
        add(Storage_2);
        add(BaoFeiSSN);
        add(JBaoFeiSSN);
        add(BaoFeiCSSN);
        add(JBaoFeiCSSN);
        add(BaoFeiSSN2);
        add(JBaoFeiSSN2);
        add(BaoFeiCSSN2);
        add(JBaoFeiCSSN2);
        ComboBoxAction();
        RadioAction(radioIn);
        RadioAction(radioOut);
        RadioAction(radioDrop);
        RadioAction(radioCenter);
        RadioAction(radioBatch);
        RadioAction(radioISN);
        CheckBoxAction();
        keyEvent();
        EndAction();
        ReStartAction();
        JBaoFeiSSNAction();
        JBaoFeiCSSNAction();
        JBaoFeiSSNA2ction();
        JBaoFeiCSSNA2ction();
        BatchKeyEvent();
        BISNKeyEvent();
        JCheckIsnKeyEvent();
    }

    public JLabel MyLable(String name, int x, int y) {
        JLabel label = new JLabel(name, JLabel.RIGHT);
        label.setFont(font);
        label.setForeground(color);
        label.setBounds(x, y, 150, 30);
        label.setVisible(false);
        return label;
    }

    public JCheckBox MyCheckBox(String name, int x, int y) {
        JCheckBox checkBox = new JCheckBox(name);
        checkBox.setBounds(x, y, 150, 30);
        checkBox.setVisible(false);
        return checkBox;
    }

    public JButton MyButton(String name, int x, int y) {
        JButton button = new JButton(name);
        button.setLayout(null);
        button.setFont(font);
        button.setForeground(color);
        button.setBackground(Color.white);
        button.setBounds(x, y, 100, 30);
        button.setVisible(false);
        return button;
    }

    public JTextField MyTextField(int x, int y) {
        JTextField textField = new JTextField(25);
        textField.setFont(font);
        textField.setBounds(x, y, 150, 30);
        textField.setVisible(false);
        return textField;
    }

    public JRadioButton MyRadioButton(String name, int x, int y) {
        JRadioButton radioButton = new JRadioButton(name);
        radioButton.setFont(font);
        radioButton.setBounds(x, y, 80, 30);
        radioButton.setEnabled(false);
        return radioButton;
    }

    public JComboBox MyComboBox() {
        JComboBox comboBox = new JComboBox();
        comboBox.setFont(font);
        comboBox.setForeground(color);
        comboBox.setBounds(200, 20, 150, 30);
        return comboBox;
    }

    public void ComboBoxAction() {
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String str = comboBox.getSelectedItem().toString().trim();
                if (str.contains("報廢")) {
                    if (power.equals("warehouse")) {
                        personNo.setVisible(false);
                        JpersonNo.setVisible(false);
                        radioIn.setVisible(false);
                        radioOut.setVisible(true);
                        radioDrop.setVisible(false);
                        radioCenter.setVisible(true);
                        radioIn.setEnabled(true);
                        radioOut.setEnabled(true);
                        radioCenter.setEnabled(true);
                        radioOut.setText("除賬");
                    } else {
                        personNo.setVisible(false);
                        JpersonNo.setVisible(false);
                        radioIn.setVisible(true);
                        radioOut.setVisible(false);
                        radioDrop.setVisible(false);
                        radioCenter.setVisible(false);
                        radioIn.setEnabled(true);
                        radioIn.setText("入庫");
                    }
                } else if (str.contains("領用")) {
                    personNo.setVisible(true);
                    JpersonNo.setVisible(true);
                    radioIn.setVisible(true);
                    radioOut.setVisible(true);
                    if(power.equals("warehouse")){
                        radioDrop.setVisible(true);
                        radioDrop.setEnabled(true);
                    }else{
                        radioDrop.setVisible(false);
                        radioDrop.setEnabled(false);
                    }
                    radioCenter.setVisible(false);
                    radioIn.setEnabled(true);
                    radioOut.setEnabled(true);

                    radioIn.setText("借用");
                    radioOut.setText("返還");
                } else if (str.contains("量測")) {
                    personNo.setVisible(true);
                    JpersonNo.setVisible(true);
                    radioIn.setVisible(true);
                    radioOut.setVisible(true);
                    radioDrop.setVisible(false);
                    radioCenter.setVisible(false);
                    radioIn.setEnabled(true);
                    radioOut.setEnabled(true);
                    radioDrop.setEnabled(true);
                    radioIn.setText("借用");
                    radioOut.setText("返還");
                } else if (str.equals("ORT倉") && power.equals("normal")) {
                    personNo.setVisible(false);
                    JpersonNo.setVisible(false);
                    radioIn.setVisible(true);
                    radioOut.setVisible(false);
                    radioDrop.setVisible(false);
                    radioCenter.setVisible(false);
                    radioIn.setEnabled(true);
                    radioOut.setEnabled(false);
                    radioDrop.setEnabled(true);
                    radioIn.setText("借給ORT");
                } else {
                    personNo.setVisible(false);
                    JpersonNo.setVisible(false);
                    radioIn.setVisible(true);
                    radioOut.setVisible(true);
                    radioDrop.setVisible(false);
                    radioCenter.setVisible(false);
                    radioIn.setEnabled(true);
                    radioOut.setEnabled(true);
                    radioIn.setText("入庫");
                    radioOut.setText("出庫");
                }
            }
        });
    }

    public JTable MyTable() {
        try {
            TM = new MyTableModel(data, tableHead);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JTable table = new JTable(TM);
        table.setFont(font);
        table.setForeground(color);
        table.setRowHeight(20);
        table.setBackground(Color.white);
        TM.removeRow(0);
        TM.fireTableDataChanged();
        table.setEnabled(false);
        return table;
    }

    public void keyEvent() {

        JISN.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    keyEventAction();
                }
            }
        });
    }

    public void keyEventAction() {
        radioOut.setEnabled(false);
        radioCenter.setEnabled(false);
        radioDrop.setEnabled(false);
        comboBox.setEnabled(false);
        String cc = JISN.getText().toString().trim();
        String house = comboBox.getSelectedItem().toString().trim();
        for(int i=0;i<JISNList.size();i++){
            if(cc.equals(JISNList.get(i))){
                JOptionPane.showConfirmDialog(null, "請勿重複刷入ISN！！！", "警告", JOptionPane.PLAIN_MESSAGE);
                return;
            }
        }
        Boolean status = true;
        if (cc.equals("")) {
            JOptionPane.showConfirmDialog(null, "刷入不能為空", "警告", JOptionPane.PLAIN_MESSAGE);
        } else {
            if (!house.contains("玻璃")) {//非玻璃倉
                String sql = "select t.isn,t.item from tp.isn t where t.isn = '" + cc + "'";
                Hashtable ht = null;
                try {
                    ht = getDBDATA(sql, DataSourceType._SFIS_KAIJIA_STD, uiVO);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                if (ht == null) {
                    JOptionPane.showConfirmDialog(null, "刷入ISN不存在", "警告", JOptionPane.PLAIN_MESSAGE);
                } else {
                    String KJ = CloneArray_ChangeStr.NulltoSpace(ht.get("ITEM"));
                    if (BaoFei(cc)) {//未報廢
                        if (house.contains("報廢")) {
                            JOptionPane.showConfirmDialog(null, "該ISN未報廢，不可入報廢倉", "警告", JOptionPane.PLAIN_MESSAGE);
                        } else {
                            if (house.contains("量測") || house.contains("領用") && JpersonNo.equals("")) {
                                JOptionPane.showConfirmDialog(null, "請輸入借用工號", "警告", JOptionPane.PLAIN_MESSAGE);
                            } else {
                                if (ReInHouse(cc)) {
                                    doKeyAction(cc, KJ, status);
                                    JISNList.add(cc);
                                    SoundUtil.PlayOk();
                                }
                            }
                        }
                    } else {
                        if (house.contains("報廢")) {
                            if (ReInHouse(cc)) {
                                doKeyAction(cc, KJ, status);
                                JISNList.add(cc);
                                SoundUtil.PlayOk();
                            }
                        } else {
                            JOptionPane.showConfirmDialog(null, "該ISN已報廢,只可入報廢倉", "警告", JOptionPane.PLAIN_MESSAGE);
                        }
                    }
                }
            } else {//玻璃倉
                String KJ = JKJNO.getText().toString().trim();
                BoLiCheck(cc, KJ, status);
                JISNList.add(cc);
                SoundUtil.PlayOk();
            }
            //遍歷HashMap，如果值存在，提示并return ：否就HashTable加一，table加一
            JISN.setText("");
            OKISN_NUM.setText(i + "");
        }

    }

    public void BatchKeyEvent() {
        JBatchNO.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    radioIn.setEnabled(false);
                    comboBox.setEnabled(false);
                    radioISN.setEnabled(false);
                    radioDrop.setEnabled(false);

                    String batNo = JBatchNO.getText().toString().trim();
                    String houseName = comboBox.getSelectedItem().toString();
                    if (!batNo.equals("")) {
                        try {
                            Vector arg = new Vector();
                            arg.add(batNo);
                            BaseServletAgent bsa = new BaseServletAgent(uiVO);
                            BaseVO bvo = new BaseVO(uiVO, this.getClass().toString(), CommandName.CallPLSQLCmd2, arg);
                            bvo.setPackageName("AAA_1721_GET_HOUSE");
                            bvo.setFunctionName("CHECKBATCH");
                            ResultVO rvo = bsa.doFunc(bvo);
                            Vector result = rvo.getData();
                            Hashtable row = null;
                            if (result.size() > 1) {
                                int j = 0;
                                row = (Hashtable) result.elementAt(1);
                                String Name = CloneArray_ChangeStr.NulltoSpace(row.get("HOUSE"));
                                if (!houseName.equals(Name)) {
                                    JOptionPane.showConfirmDialog(null, "當前批號不屬於此倉別", "警告", JOptionPane.PLAIN_MESSAGE);
                                } else {
                                    Boolean ok = false;
                                    for (j = 1; j <= (result.size() - 1); j++) {
                                        row = (Hashtable) result.elementAt(j);
                                        String ISNNO = CloneArray_ChangeStr.NulltoSpace(row.get("ISN"));
                                        if (BatchISNstatus(ISNNO).equals("0")) {
                                            ok = true;
                                        } else if (BatchISNstatus(ISNNO).equals("3") && houseName.contains("報廢")) {
                                            ok = true;
                                        } else {

                                            ok = false;
                                            GetRemaining(batNo);
                                            break;
                                        }
                                    }
                                    if (ok) {

                                        for (j = 1; j <= (result.size() - 1); j++) {
                                            row = (Hashtable) result.elementAt(j);
                                            String ISN = CloneArray_ChangeStr.NulltoSpace(row.get("ISN"));
                                            String KJ = CloneArray_ChangeStr.NulltoSpace(row.get("KJ"));
                                            TM.addRow(new Object[]{ISN, KJ});
                                            TM.fireTableDataChanged();
                                            mp.put(i + j - 1, ISN);
                                            trueBatchNo = batNo;
                                            ppXX.put(ISN, i + j - 1);
                                            mmp.put(ISN, KJ);
                                            System.out.println("=====。。。。=======i:" + i + "=======。。。。======ISN:" + ISN);
                                        }
                                        CheckBox.setEnabled(true);
                                        i = i + j - 1;
                                        JBatchNO.setText("");
                                        OKISN_NUM.setText(i + "");
                                        JBatchNO.setEnabled(false);
                                    }
                                }

                            } else {
                                JOptionPane.showConfirmDialog(null, "查此批號的無可出庫ISN", "警告", JOptionPane.PLAIN_MESSAGE);
                            }
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    } else {
                        JOptionPane.showConfirmDialog(null, "刷入不能為空", "警告", JOptionPane.PLAIN_MESSAGE);
                    }
                }
            }
        });
    }
    //將批號內剩餘ISN查出并除賬
    public void GetRemaining(String batch){
        Object[] options = {"自動選中剩餘ISN除賬","手動刷ISN除賬"};
        int response= JOptionPane.showOptionDialog(null, "本批號中已有ISN出庫或除賬,不可批出請選擇刷ISN", "警告！！！",JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if(response==1) {
            return;
        }
        String houseName = comboBox.getSelectedItem().toString();
        String sql="select t.isn,t.status,t.kj from sfis_1721_detail_info t where t.isn in(select isn from SFIS_1721_NOTE where batch='"+batch+"'and house='"+houseName+"') and t.status='0'";
        List<Hashtable> list =null;
        try {
            list =dh.getDataList(sql,DataSourceType._MultiCompanyCurrentDB);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(list==null){
            JOptionPane.showConfirmDialog(null, "該批號產品已不在此領用倉,不可被除賬", "警告", JOptionPane.PLAIN_MESSAGE);
            return;
        }
        for(Hashtable ht:list){
            InsertTable(ht.get("ISN").toString(),ht.get("KJ").toString());
        }
    }

    public void InsertTable(String isn,String kj){
        try {
            TM.addRow(new Object[]{isn, kj});
        } catch (Exception e) {
            e.printStackTrace();
        }
        TM.fireTableDataChanged();
        i++;
        mp.put(i, isn);
        mmp.put(isn, kj);
        JBISN.setText("");
        ISNnumber++;
        OKISN_NUM.setText(i + "");
        JBISNList.add(isn);
    }

    public void BISNKeyEvent() {
        JBISN.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {

                    CheckISN();
                }
            }
        });
    }

    public void CheckISN() {
        radioIn.setEnabled(false);
        radioBatch.setEnabled(false);
        radioDrop.setEnabled(false);
        comboBox.setEnabled(false);
        String batISN = JBISN.getText().toString().trim();
        String houseName = comboBox.getSelectedItem().toString();
        for(int i=0;i<JBISNList.size();i++){
            if(batISN.equals(JBISNList.get(i))){
                JOptionPane.showConfirmDialog(null, "請勿重複刷入產品！", "警告", JOptionPane.PLAIN_MESSAGE);
                return;
            }
        }
        String sql = "select * from sfis_1721_note where isn='" + batISN + "' and house = '" + houseName + "' order by time";
        List<Hashtable> ISNNo = null;
        try {
            ISNNo = dh.getDataList(sql, DataSourceType._MultiCompanyCurrentDB);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        if (ISNNo == null) {
            JOptionPane.showConfirmDialog(null, "該ISN尚未綁定批號！", "警告", JOptionPane.PLAIN_MESSAGE);
            return;
        } else {
            checkISNBatchNo = (String) ISNNo.get(0).get("BATCH");
        }
        if(ISNNo.get(0).get("STATUS").toString().equals("3")){
            JOptionPane.showConfirmDialog(null, "該ISN已除賬，請核實！", "警告", JOptionPane.PLAIN_MESSAGE);
            return;
        }
        if (ISNnumber == 0) {
            nextISNBatchNo = checkISNBatchNo;
        }
        if (ISNnumber > 0) {
            if (checkISNBatchNo.equals(nextISNBatchNo)) {

            } else {
                JOptionPane.showConfirmDialog(null, "該ISN不是同一批號，請核實！", "警告", JOptionPane.PLAIN_MESSAGE);
                return;
            }
        }
        if (!batISN.equals("")) {
            try {
                Vector arg = new Vector();
                arg.add(batISN);
                BaseServletAgent bsa = new BaseServletAgent(uiVO);
                BaseVO bvo = new BaseVO(uiVO, this.getClass().toString(), CommandName.CallPLSQLCmd2, arg);
                bvo.setPackageName("AAA_1721_GET_HOUSE");
                bvo.setFunctionName("CHECKISNSTATUS");
                ResultVO rvo = bsa.doFunc(bvo);
                Vector result = rvo.getData();
                Hashtable row = null;
                String sta = null;
                String KJ = null;
                if (result.size() < 2) {
                    JOptionPane.showConfirmDialog(null, "查無可出庫的該ISN", "警告", JOptionPane.PLAIN_MESSAGE);
                }
                if (result.size() == 2) {
                    row = (Hashtable) result.elementAt(1);
                    sta = CloneArray_ChangeStr.NulltoSpace(row.get("STATUS"));
                    KJ = CloneArray_ChangeStr.NulltoSpace(row.get("KJ"));

                }
                if (sta.equals("1")) {
                    JOptionPane.showConfirmDialog(null, "該ISN已出庫", "警告", JOptionPane.PLAIN_MESSAGE);
                }
                if (sta.equals("4")) {
                    JOptionPane.showConfirmDialog(null, "該ISN已銷毀", "警告", JOptionPane.PLAIN_MESSAGE);
                }
                if (sta.equals("3")) {

                    if (houseName.contains("報廢") && type.equals("DDD")) {
                        Vector ag = new Vector();
                        ag.add(batISN);
                        ag.add("3");
                        BaseServletAgent ba = new BaseServletAgent(uiVO);
                        BaseVO bv = new BaseVO(uiVO, this.getClass().toString(), CommandName.CallPLSQLCmd2, ag);
                        bv.setPackageName("AAA_1721_GET_HOUSE");
                        bv.setFunctionName("CHECKHOUSENAME");
                        ResultVO rv = ba.doFunc(bv);
                        Vector rt = rv.getData();

                        if (result.size() == 2) {
                            Hashtable hh = (Hashtable) rt.elementAt(1);
                            String name = CloneArray_ChangeStr.NulltoSpace(hh.get("HOUSE"));

                            if (!name.equals(houseName)) {
                                JOptionPane.showConfirmDialog(null, "該報廢ISN不在本報廢倉,不可銷毀", "警告", JOptionPane.PLAIN_MESSAGE);
                            } else {
                                TM.addRow(new Object[]{batISN, KJ});
                                TM.fireTableDataChanged();
                                i++;
                                mp.put(i, batISN);
                                mmp.put(batISN, KJ);
                                JBISN.setText("");
                                ISNnumber++;
                                OKISN_NUM.setText(i + "");
                                JBISNList.add(batISN);
                                SoundUtil.PlayOk();
                            }
                        }

                    } else {
                        JOptionPane.showConfirmDialog(null, "該ISN已除賬不可出庫或領用", "警告", JOptionPane.PLAIN_MESSAGE);
                    }
                }

                if (sta.equals("0")) {
                    if (type.equals("DDD")) {
                        JOptionPane.showConfirmDialog(null, "該ISN未報廢,不可被銷毀", "警告", JOptionPane.PLAIN_MESSAGE);
                    } else {
                        Vector ag = new Vector();
                        ag.add(batISN);
                        ag.add(houseName);
                        BaseServletAgent ba = new BaseServletAgent(uiVO);
                        BaseVO bv = new BaseVO(uiVO, this.getClass().toString(), CommandName.CallPLSQLCmd2, ag);
                        bv.setPackageName("AAA_1721_GET_HOUSE");
                        bv.setFunctionName("CHECKISNHOUSE");
                        ResultVO rv = ba.doFunc(bv);
                        Vector rt = rv.getData();
                        if (result.size() == 3) {
                            JOptionPane.showConfirmDialog(null, "查該ISN不在此倉", "警告", JOptionPane.PLAIN_MESSAGE);
                        }
                        if (result.size() == 2) {

                            TM.addRow(new Object[]{batISN, KJ});
                            TM.fireTableDataChanged();
                            i++;
                            mp.put(i, batISN);
                            trueBATCHISN = batISN;
                            mmp.put(batISN, KJ);
                            trueBATCHISN = batISN;
                            JBISN.setText("");
                            JBaoFeiSSN.setText("");
                            JBaoFeiCSSN.setText("");
                            ISNnumber++;
                            JBISNList.add(batISN);
                            OKISN_NUM.setText(i + "");
                            SoundUtil.PlayOk();
                        }
                    }
                }

            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

    }

    public void JCheckIsnKeyEvent() {
        JCheckIsn.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    end.setEnabled(false);
                    String isn = JCheckIsn.getText().toString().trim();
                    int RowCount = table.getRowCount();
                    System.out.println("**************************------RowCount:" + RowCount);
                    boolean bln = false;
                    for (int j = 0; j < RowCount; j++) {
                        String id = String.valueOf(table.getValueAt(j, 0));
                        System.out.println("===============================id:" + id);
                        if (isn.equals(id)) {
                            bln = true;
                            TM.removeRow(j);
                            break;
                        }
                    }
                    if (bln == false) {
                        JOptionPane.showConfirmDialog(null, "該實物不在本批號中,請放棄作業并刷ISN出倉", "警告", JOptionPane.PLAIN_MESSAGE);
                        JCheckIsn.setEnabled(false);
                    }
                    if (TM.getRowCount() == 0) {
                        JOptionPane.showConfirmDialog(null, "核對本批號OK，請執行出倉", "警告", JOptionPane.PLAIN_MESSAGE);
                        end.setEnabled(true);
                    }
                    TM.fireTableDataChanged();
                    JCheckIsn.setText("");
                    i--;
                    OKISN_NUM.setText(i + "");
                }
            }
        });
    }

    public void RadioAction(final JRadioButton radioButton) {
        radioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (radioButton == radioIn) {
                    ISN.setVisible(true);
                    JISN.setVisible(true);
                    KJNO.setVisible(true);
                    JKJNO.setVisible(true);
                    end.setVisible(true);
                    restart.setVisible(true);
                    radioBatch.setVisible(false);
                    radioISN.setVisible(false);
                    BatchNO.setVisible(false);
                    JBatchNO.setVisible(false);
                    BISN.setVisible(false);
                    JBISN.setVisible(false);
                    CheckBox.setVisible(false);
                    Storage.setVisible(false);
                    JStorage.setVisible(false);
                    CartonNo.setVisible(false);
                    JCartonNo.setVisible(false);
                    Storage_2.setVisible(false);
                    JStorage_2.setVisible(false);
                    CartonNo_2.setVisible(false);
                    JCartonNo_2.setVisible(false);
                    JBaoFeiSSN.setVisible(false);
                    JBaoFeiCSSN.setVisible(false);
                    BaoFeiSSN.setVisible(false);
                    BaoFeiCSSN.setVisible(false);
                    JBaoFeiSSN2.setVisible(true);
                    JBaoFeiCSSN2.setVisible(true);
                    BaoFeiSSN2.setVisible(true);
                    BaoFeiCSSN2.setVisible(true);
                    Desc.setText(radioIn.getText() + "說明");
                    JDesc.setText("");
                    JISN.setText("");
                    type = "AAA";
                }
                if (radioButton == radioOut) {
                    ISN.setVisible(false);
                    JISN.setVisible(false);
                    KJNO.setVisible(false);
                    JKJNO.setVisible(false);
                    end.setVisible(false);
                    restart.setVisible(false);
                    BatchNO.setVisible(false);
                    JBatchNO.setVisible(false);
                    BISN.setVisible(false);
                    JBISN.setVisible(false);
                    radioBatch.setVisible(true);
                    radioISN.setVisible(true);
                    CheckBox.setVisible(false);
                    Storage.setVisible(false);
                    JStorage.setVisible(false);
                    CartonNo.setVisible(false);
                    JCartonNo.setVisible(false);
                    Storage_2.setVisible(false);
                    JStorage_2.setVisible(false);
                    CartonNo_2.setVisible(false);
                    JCartonNo_2.setVisible(false);
                    BaoFeiSSN.setVisible(false);
                    JBaoFeiSSN.setVisible(false);
                    BaoFeiCSSN.setVisible(false);
                    JBaoFeiCSSN.setVisible(false);
                    BaoFeiSSN2.setVisible(true);
                    JBaoFeiSSN2.setVisible(true);
                    BaoFeiCSSN2.setVisible(true);
                    JBaoFeiCSSN2.setVisible(true);

                    Desc.setText(radioOut.getText() + "說明");
                    JDesc.setText("");
                    JISN.setText("");
                    type = "BBB";
                }


                if (radioButton.getText() == radioDrop.getText()) {
                    isDestruction = false;
                    ISN.setVisible(false);
                    JISN.setVisible(false);
                    KJNO.setVisible(false);
                    JKJNO.setVisible(false);
                    end.setVisible(false);
                    restart.setVisible(false);
                    BatchNO.setVisible(false);
                    JBatchNO.setVisible(false);
                    BISN.setVisible(false);
                    JBISN.setVisible(false);
                    radioBatch.setVisible(true);
                    radioISN.setVisible(true);
                    CheckBox.setVisible(false);
                    Storage.setVisible(false);
                    JStorage.setVisible(false);
                    CartonNo.setVisible(false);
                    JCartonNo.setVisible(false);
                    Storage_2.setVisible(false);
                    JStorage_2.setVisible(false);
                    CartonNo_2.setVisible(false);
                    JCartonNo_2.setVisible(false);
                    BaoFeiSSN.setVisible(false);
                    JBaoFeiSSN.setVisible(false);
                    BaoFeiCSSN.setVisible(false);
                    JBaoFeiCSSN.setVisible(false);
                    BaoFeiSSN2.setVisible(false);
                    JBaoFeiSSN2.setVisible(false);
                    BaoFeiCSSN2.setVisible(false);
                    JBaoFeiCSSN2.setVisible(false);
                    Desc.setText(radioDrop.getText() + "說明");
                    JDesc.setText("");
                    JISN.setText("");
                    type = "CCC";
                }
                if (radioButton.getText() == radioCenter.getText()) {
                    isDestruction = true;
                    ISN.setVisible(false);
                    JISN.setVisible(false);
                    KJNO.setVisible(false);
                    JKJNO.setVisible(false);
                    end.setVisible(false);
                    restart.setVisible(false);
                    BatchNO.setVisible(false);
                    JBatchNO.setVisible(false);
                    BISN.setVisible(false);
                    JBISN.setVisible(false);
                    radioBatch.setVisible(true);
                    radioISN.setVisible(true);
                    CheckBox.setVisible(false);
                    Storage.setVisible(false);
                    JStorage.setVisible(false);
                    CartonNo.setVisible(false);
                    JCartonNo.setVisible(false);
                    Storage_2.setVisible(false);
                    JStorage_2.setVisible(false);
                    CartonNo_2.setVisible(false);
                    JCartonNo_2.setVisible(false);
                    BaoFeiSSN.setVisible(false);
                    JBaoFeiSSN.setVisible(false);
                    BaoFeiCSSN.setVisible(false);
                    JBaoFeiCSSN.setVisible(false);
                    BaoFeiSSN2.setVisible(false);
                    JBaoFeiSSN2.setVisible(false);
                    BaoFeiCSSN2.setVisible(false);
                    JBaoFeiCSSN2.setVisible(false);
                    Desc.setText(radioCenter.getText() + "說明");
                    JDesc.setText("");
                    JISN.setText("");
                    type = "DDD";
                }
                if (radioButton == radioBatch) {
                    BatchNO.setVisible(true);
                    JBatchNO.setVisible(true);
                    BISN.setVisible(false);
                    JBISN.setVisible(false);
                    restart.setVisible(true);
                    Storage_2.setVisible(false);
                    JStorage_2.setVisible(false);
                    CartonNo_2.setVisible(false);
                    JCartonNo_2.setVisible(false);
                    BaoFeiSSN.setVisible(false);
                    JBaoFeiSSN.setVisible(false);
                    BaoFeiCSSN.setVisible(false);
                    JBaoFeiCSSN.setVisible(false);
                    BaoFeiSSN2.setVisible(false);
                    JBaoFeiSSN2.setVisible(false);
                    BaoFeiCSSN2.setVisible(false);
                    JBaoFeiCSSN2.setVisible(false);
                    CheckBox.setVisible(true);
                    CheckBox.setEnabled(false);
                    end.setVisible(true);
                    if (comboBox.getSelectedItem().toString().contains("報廢")||comboBox.getSelectedItem().toString().contains("領用") && isDestruction == false) {
                        Storage.setVisible(true);
                        JStorage.setVisible(true);
                        CartonNo.setVisible(true);
                        JCartonNo.setVisible(true);
                    }
                }
                if (radioButton == radioISN) {
                    BatchNO.setVisible(false);
                    JBatchNO.setVisible(false);
                    Storage.setVisible(false);
                    JStorage.setVisible(false);
                    CartonNo.setVisible(false);
                    JCartonNo.setVisible(false);
                    BISN.setVisible(true);
                    JBISN.setVisible(true);
                    restart.setVisible(true);
                    CheckBox.setVisible(false);
                    end.setVisible(true);
                    CheckIsn.setVisible(false);
                    JCheckIsn.setVisible(false);
                    BaoFeiSSN.setVisible(true);
                    JBaoFeiSSN.setVisible(true);
                    BaoFeiCSSN.setVisible(true);
                    JBaoFeiCSSN.setVisible(true);
                    BaoFeiSSN2.setVisible(false);
                    JBaoFeiSSN2.setVisible(false);
                    BaoFeiCSSN2.setVisible(false);
                    JBaoFeiCSSN2.setVisible(false);
                    if (comboBox.getSelectedItem().toString().contains("報廢") ||comboBox.getSelectedItem().toString().contains("領用")&& isDestruction == false) {
                        Storage_2.setVisible(true);
                        JStorage_2.setVisible(true);
                        CartonNo_2.setVisible(true);
                        JCartonNo_2.setVisible(true);
                        BaoFeiSSN.setVisible(true);
                        JBaoFeiSSN.setVisible(true);
                        BaoFeiCSSN.setVisible(true);
                        JBaoFeiCSSN.setVisible(true);

                    }
                }
            }
        });

    }

    public void CheckBoxAction() {
        CheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (CheckBox.isSelected()) {
                    CheckIsn.setVisible(true);
                    JCheckIsn.setVisible(true);
                }
                if (!CheckBox.isSelected()) {
                    CheckIsn.setVisible(false);
                    JCheckIsn.setVisible(false);
                }
            }
        });
    }

    public void DB_ComboBox_Data(String power) {
        try {
            Vector arg = new Vector();
            BaseServletAgent bsa = new BaseServletAgent(uiVO);
            BaseVO bvo = new BaseVO(uiVO, this.getClass().toString(), CommandName.CallPLSQLCmd2, arg);
            bvo.setPackageName("AAA_1721_GET_HOUSE");
            bvo.setFunctionName("GetName");
            ResultVO rvo = bsa.doFunc(bvo);
            Vector result = rvo.getData();
            Hashtable row = null;
            comboBox.removeAllItems();
            comboBox.addItem("");
            if (power.equals("department")) {
                for (int n = 1; n <= (result.size() - 1); n++) {
                    row = (Hashtable) result.get(n);
                    String name = CloneArray_ChangeStr.NulltoSpace(row.get("HNAME"));
                    if (!name.contains("資材")) {
                        comboBox.addItem(name);
                    }
                }
            }
            if (power.equals("normal")) {
                for (int n = 1; n <= (result.size() - 1); n++) {
                    row = (Hashtable) result.get(n);
                    String name = CloneArray_ChangeStr.NulltoSpace(row.get("HNAME"));
                    if (!name.contains("領用") && !name.contains("資材") && !name.equals("ORT報廢倉")) {
                        comboBox.addItem(name);
                    }
                }
            }
            if (power.equals("warehouse")) {

                for (int n = 1; n <= (result.size() - 1); n++) {
                    row = (Hashtable) result.get(n);
                    String name = CloneArray_ChangeStr.NulltoSpace(row.get("HNAME"));
                    System.out.println("---------"+name+"-------------------------------------");
                    if (name.contains("報廢")||name.contains("領用") && !name.contains("資材")) {
                        comboBox.addItem(name);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //生成批號，數據插入表，生成報表
    public void EndAction() {
        end.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String HouseName = (String) comboBox.getSelectedItem();
                BatchNumber = BatchNumber();
                SimpleDateFormat SD = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date();
                TIM = SD.format(date);
                if (mp.size() == 0) {
                    JOptionPane.showConfirmDialog(null, "暫未有任何操作，請刷出人庫產品", "警告", JOptionPane.PLAIN_MESSAGE);
                    end.setEnabled(true);
                } else {
                    String path = "d://1721BatchNo//" +HouseName+ BatchNumber + ".pdf";
                    SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
                    Date dd = new Date();
                    String day = SDF.format(dd);
                    String Status = Desc.getText().toString().substring(0, 2);
                    String Name = LoginName; //Desc.getText().toString().substring(1,2);
                    String Desc = JDesc.getText().toString();
                    String n = String.valueOf(mp.size());
                    //PDFFactory();
                    Collection list = mmp.values();
                    Map<Object, Integer> map = new HashMap<Object, Integer>();
                    for (Object a : list) {
                        map.put(a, map.get(a) == null ? 1 : map.get(a) + 1);
                    }
                    for (Object key : map.keySet()) {
                        System.out.println("***************" + key + ":" + map.get(key));
                    }
                    try {
                        PDFFactory(path, HouseName, day, Status, Name, Desc, BatchNumber, n, map);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (DocumentException e1) {
                        e1.printStackTrace();
                    }
                    end.setEnabled(false);
                    //插入數據庫
                    if (type.equals("AAA")) {
                        if (radioIn.getText().toString().trim().equals("借給ORT")) {
                            InsertDetail("2");
                            InsertBatch("2", HouseName);
                        } else {
                            InsertDetail("0");
                            InsertBatch("0", HouseName);
                        }
                        try {
                            ChangeStepStatus("1", "TEST", "TZ8", "GSW", TIM, "D", "0");
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                        UpdateHouse(true, HouseName);
                    }
                    if (type.equals("BBB")) {
                        if (comboBox.getSelectedItem().toString().trim().contains("報廢")) {
                            JOptionPane.showConfirmDialog(null, "報廢產品不允許出庫！！！", "警告", JOptionPane.PLAIN_MESSAGE);
                            return;
/*                            UpdateDetail("3", HouseName);
                            InsertBatch("3", HouseName);
                            UpdateHouse(false, HouseName);
                            InsertBatch("3", HouseName.substring(0, HouseName.length() - 3) + "資材倉");
                            UpdateHouse(true, HouseName.substring(0, HouseName.length() - 3) + "資材倉");*/
                        } else {
                            try {
                                ChangeStepStatus("1", "TEST", "TZ8", "GSW", TIM, "D", "9");
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
                            UpdateDetail("1", HouseName);
                            InsertBatch("1", HouseName);
                            UpdateHouse(false, HouseName);
                        }
                    }
                    if (type.equals("CCC")) {
                        UpdateDetail("3",HouseName.substring(0, HouseName.length() - 3) + "資材倉");
                        //InsertBatch("3", HouseName);
                        InsertBatch("3", HouseName.substring(0, HouseName.length() - 3) + "資材倉");
                        UpdateHouse(false, HouseName);
                        UpdateHouse(true, HouseName.substring(0, HouseName.length() - 3) + "資材倉");
                    }
                    if (type.equals("DDD")) {
                        UpdateDetail("4", HouseName.substring(0, HouseName.length() - 3) + "資材倉");
                        InsertBatch("4", HouseName.substring(0, HouseName.length() - 3) + "資材倉");
                        UpdateHouse(false, HouseName.substring(0, HouseName.length() - 3) + "資材倉");
                    }


                        if (!"".equals(JStorage.getText()) || !"".equals(JStorage_2.getText())) {
                            String storageName = "";
                            String storage_2Name = "";
                            String batchNo = "";
                            String carton = "";
                            String carton_2 = "";
                            String employee = uiVO.getLogin_id();

                            storageName = (String) JStorage.getText();
                            storage_2Name = (String) JStorage_2.getText();
                            carton = (String) JCartonNo.getText();
                            carton_2 = (String) JCartonNo_2.getText();
                            try {
                            String sql = "";
                            String realBatch = "";
                            if (!"".equals(storageName)) {
                                sql = "insert INTO SFIS_1721_STORAGE_WAREHOUSE(STORAGE_NAME,BATCH_NO,CARTON_NO,EMPLOYEE,SYSTEM_TIME)values('" + storageName + "','" + BatchNumber + "','" + carton + "','" + employee + "', sysdate)";
                            } else if (!"".equals(storage_2Name) && "".equals(storageName)) {
                                sql = "select * from sfis_1721_note where isn='" + trueBATCHISN + "'order by time";
                                List<Hashtable> ISNNo = null;
                                ISNNo = dh.getDataList(sql, DataSourceType._MultiCompanyCurrentDB);
                                if (ISNNo == null) {
                                    JOptionPane.showConfirmDialog(null, "該ISN尚未綁定批號！", "警告", JOptionPane.PLAIN_MESSAGE);
                                    return;
                                }
                                sql = "insert INTO SFIS_1721_STORAGE_WAREHOUSE(STORAGE_NAME,BATCH_NO,CARTON_NO,EMPLOYEE,SYSTEM_TIME)values('" + storage_2Name + "','" + BatchNumber + "','" + carton_2 + "','" + employee + "', sysdate)";
                            }
                            Vector a = dh.updateData(sql, DataSourceType._MultiCompanyCurrentDB);
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
                        } else if (!"".equals(JCartonNo.getText()) || !"".equals(JStorage_2.getText())) {
                            JOptionPane.showConfirmDialog(null, "輸入箱號時，儲位不可為空", "警告", JOptionPane.PLAIN_MESSAGE);
                        }



                    JOptionPane.showConfirmDialog(null, "操作成功", "警告", JOptionPane.PLAIN_MESSAGE);
                }
                mmp.clear();
                mp.clear();
                i = 0;
                DBNum = 0;
            }
        });
    }

    public void ReStartAction() {
        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comboBox.setEnabled(true);
                radioOut.setEnabled(true);
                radioIn.setEnabled(true);
                radioDrop.setEnabled(true);
                radioCenter.setEnabled(true);
                radioBatch.setEnabled(true);
                radioISN.setEnabled(true);
                end.setEnabled(true);
                BatchNO.setText("請刷批號:");
                JDesc.setText("");
                JISN.setText("");
                JKJNO.setText("");
                JStorage.setText("");
                JStorage_2.setText("");
                JCartonNo.setText("");
                JCartonNo_2.setText("");
                JBaoFeiSSN.setText("");
                JBaoFeiCSSN.setText("");
                end.setEnabled(true);
                OKISN_NUM.setText("");
                JBatchNO.setText("");
                JISN.setText("");
                CheckIsn.setVisible(false);
                JCheckIsn.setVisible(false);
                JCheckIsn.setText("");
                JCheckIsn.setEnabled(true);
                JBatchNO.setEnabled(true);
                mmp.clear();
                mp.clear();
                i = 0;
                DBNum = 0;
                CheckBox.setVisible(false);
                MyTableModel dd = (MyTableModel) table.getModel();
                int i = table.getRowCount();
                int k = table.getColumnCount();
                for (int j = 0; j < i; j++) {
                    dd.removeRows(0, k);
                    dd.fireTableDataChanged();
                }
                JISNList.clear();
                JBISNList.clear();
            }
        });

    }

    public void JBaoFeiCSSNAction() {
        JBaoFeiCSSN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ISNsql = "";
                Hashtable queryISN = null;
                String batISN = null;
                if (!JBaoFeiCSSN.getText().equals("") && JBISN.getText().equals("")) {
                    ISNsql = "select ISN from ISNINFO where CSSN='" + JBaoFeiCSSN.getText() + "'";
                }
                if (!JBaoFeiSSN.getText().equals("") && JBISN.getText().equals("")) {
                    ISNsql = "select ISN from ISNINFO where SSN='" + JBaoFeiSSN.getText() + "'";
                }
                if (!ISNsql.equals("")) {
                    try {
                        queryISN = dh.getDataOne(ISNsql, DataSourceType._SFIS_KAIJIA_STD);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
                if (queryISN == null) {
                    batISN = JBISN.getText().toString().trim();
                } else {
                    batISN = (String) queryISN.get("ISN");
                }
                JBISN.setText(batISN);
                CheckISN();
            }
        });
    }

    public void JBaoFeiSSNAction() {
        JBaoFeiSSN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ISNsql = "";
                Hashtable queryISN = null;
                String batISN = null;
                if (!JBaoFeiCSSN.getText().equals("") && JBISN.getText().equals("")) {
                    ISNsql = "select ISN from ISNINFO where CSSN='" + JBaoFeiCSSN.getText() + "'";
                }
                if (!JBaoFeiSSN.getText().equals("") && JBISN.getText().equals("")) {
                    ISNsql = "select ISN from ISNINFO where SSN='" + JBaoFeiSSN.getText() + "'";
                }
                if (!ISNsql.equals("")) {
                    try {
                        queryISN = dh.getDataOne(ISNsql, DataSourceType._SFIS_KAIJIA_STD);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
                if (queryISN == null) {
                    batISN = JBISN.getText().toString().trim();
                } else {
                    batISN = (String) queryISN.get("ISN");
                }
                JBISN.setText(batISN);
                CheckISN();
            }
        });
    }

    public void JBaoFeiCSSNA2ction() {
        JBaoFeiCSSN2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ISNsql = "";
                Hashtable queryISN = null;
                String batISN = null;
                if (!JBaoFeiCSSN2.getText().equals("")){
                    ISNsql = "select ISN from ISNINFO where CSSN='" + JBaoFeiCSSN2.getText() + "'";
                }
                if (!JBaoFeiSSN2.getText().equals("") && JISN.getText().equals("")) {
                    ISNsql = "select ISN from ISNINFO where SSN='" + JBaoFeiSSN2.getText() + "'";
                }
                if (!ISNsql.equals("")) {
                    try {
                        queryISN = dh.getDataOne(ISNsql, DataSourceType._SFIS_KAIJIA_STD);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
                if (queryISN == null) {
                    JOptionPane.showConfirmDialog(null, "該成品碼或簡明碼不存在！請核實！", "警告", JOptionPane.PLAIN_MESSAGE);
                } else {
                    batISN = (String) queryISN.get("ISN");
                }
                JISN.setText(batISN);
                keyEventAction();
            }
        });
    }

    public void JBaoFeiSSNA2ction() {
        JBaoFeiSSN2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ISNsql = "";
                Hashtable queryISN = null;
                String batISN = null;
                if (!JBaoFeiSSN2.getText().equals("")){
                    ISNsql = "select ISN from ISNINFO where SSN='" + JBaoFeiSSN2.getText() + "'";
                }
                if (!ISNsql.equals("")) {
                    try {
                        queryISN = dh.getDataOne(ISNsql, DataSourceType._SFIS_KAIJIA_STD);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
                if (queryISN == null) {
                    JOptionPane.showConfirmDialog(null, "該成品碼或簡明碼不存在！請核實！", "警告", JOptionPane.PLAIN_MESSAGE);
                } else {
                    batISN = (String) queryISN.get("ISN");
                }
                JISN.setText(batISN);
                keyEventAction();
            }
        });
    }

    public String BatchNumber() {//產生批號
        UpdateBNNumber();
        SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMdd");
        Date dd = new Date();
        String XX = SDF.format(dd);
        String num = "";
        String d = DBNum + "";
        for (int j = 0; j < 3 - d.length(); j++) {
            num = num + "0";
        }
        String BatchName = XX + num + DBNum;
        System.out.println("++++++++++++" + XX + "+++++++++++" + num + "+++++++++++" + DBNum);
        return BatchName;
    }

    public void PDFFactory(String Path, String HouseName, String Day, String Status, String Name, String Desc, String BatchNumber, String n, Map<Object, Integer> KJMP) throws IOException, DocumentException {
        Document document = new Document(PageSize.A4.rotate());
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(new File(Path)));
        BaseFont bfChinese = BaseFont.createFont("MHei-Medium", "UniCNS-UCS2-H", BaseFont.NOT_EMBEDDED);
        Pfont = new com.lowagie.text.Font(bfChinese, 15, com.lowagie.text.Font.BOLD, Color.BLACK);
        document.open();
        PdfPTable table = new PdfPTable(1);
        PdfPCell cell11 = new PdfPCell(new Paragraph("線邊倉入出庫單報表", Pfont));
        cell11.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell11.setBorder(0);
        table.addCell(cell11);
        document.add(table);

        Paragraph blankRow1 = new Paragraph(18f, " ", Pfont);
        document.add(blankRow1);

        //table2
        PdfPTable table2 = new PdfPTable(4);
        //?置每列?度比例
        int width21[] = {25, 25, 25, 25};
        table2.setWidths(width21);
        table2.getDefaultCell().setBorder(0);
        PdfPCell cell1 = new PdfPCell(new Paragraph("線邊倉名稱 :", Pfont));
        cell1.setBorder(0);
        PdfPCell cell1_1 = new PdfPCell(new Paragraph(HouseName, Pfont));
        cell1_1.setBorder(0);

        PdfPCell cell2 = new PdfPCell(new Paragraph("操作日期時間 :", Pfont));
        cell2.setBorder(0);
        PdfPCell cell2_2 = new PdfPCell(new Paragraph(Day, Pfont));
        cell2_2.setBorder(0);

        PdfPCell cell3 = new PdfPCell(new Paragraph("入/出庫模式 :", Pfont));
        cell3.setBorder(0);
        PdfPCell cell3_3 = new PdfPCell(new Paragraph(Status, Pfont));
        cell3_3.setBorder(0);

        PdfPCell cell4 = new PdfPCell(new Paragraph("操作員 :", Pfont));
        cell4.setBorder(0);
        PdfPCell cell4_4 = new PdfPCell(new Paragraph(Name, Pfont));
        cell4_4.setBorder(0);

        PdfPCell cell5 = new PdfPCell(new Paragraph("入/出庫說明 :", Pfont));
        cell5.setBorder(0);
        cell5.setColspan(1);
        PdfPCell cell5_5 = new PdfPCell(new Paragraph(Desc, Pfont));
        cell5_5.setBorder(0);
        cell5_5.setColspan(3);

        PdfPCell cell6 = new PdfPCell(new Paragraph("入/出庫批號 :", Pfont));
        cell6.setBorder(0);
        cell6.setColspan(1);
        PdfPCell cell6_6 = new PdfPCell(createBarCode(writer, BatchNumber, 25));
        cell6_6.setBorder(0);
        cell6_6.setColspan(3);

        table2.addCell(cell1);
        table2.addCell(cell1_1);
        table2.addCell(cell2);
        table2.addCell(cell2_2);
        table2.addCell(cell3);
        table2.addCell(cell3_3);
        table2.addCell(cell4);
        table2.addCell(cell4_4);
        table2.addCell(cell5);
        table2.addCell(cell5_5);
        table2.addCell(cell6);
        table2.addCell(cell6_6);
        document.add(table2);

        Paragraph blankRow2 = new Paragraph(18f, " ", Pfont);
        document.add(blankRow2);

        PdfPTable table3 = new PdfPTable(2);
        int width31[] = {50, 50};
        PdfPCell cl0 = new PdfPCell(new Paragraph("總數:", Pfont));
        PdfPCell cl0_0 = new PdfPCell(new Paragraph(n, Pfont));
        PdfPCell cl1 = new PdfPCell(new Paragraph("鎧嘉料號:", Pfont));
        PdfPCell cl2 = new PdfPCell(new Paragraph("ISN數量:", Pfont));
        table3.addCell(cl0);
        table3.addCell(cl0_0);
        table3.addCell(cl1);
        table3.addCell(cl2);
        Iterator iter = KJMP.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String val = String.valueOf(entry.getValue());
            String key = String.valueOf(entry.getKey());
            PdfPCell KJ = new PdfPCell(new Paragraph(key, Pfont));
            PdfPCell SUM = new PdfPCell(new Paragraph(val, Pfont));
            table3.addCell(KJ);
            table3.addCell(SUM);
        }
        document.add(table3);
        document.close();
        writer.close();
    }

    public PdfPCell createBarCode(PdfWriter writer, String codeStr, int maxWidth) {
        PdfContentByte cd = writer.getDirectContent();
        Barcode128 code39ext = new Barcode128();
        code39ext.setStartStopText(false);
        code39ext.setExtended(true);
        code39ext.setFont(null);// ?示?片下字符串?容
        code39ext.setTextAlignment(Element.ALIGN_LEFT);
        code39ext.setBarHeight(13f);
        code39ext.setX(0.5f);
        code39ext.setCode(codeStr);
        com.lowagie.text.Image image39 = code39ext.createImageWithBarcode(cd, null, null);
        PdfPCell barcodeCell = new PdfPCell(image39);
        barcodeCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        barcodeCell.setPadding(0);
        barcodeCell.setBorder(0);
        return barcodeCell;
    }

    //數據庫連接操作
    public void UpdateHouse(boolean bb, String Name) {//更新線邊倉庫存
        try {
            int HouseSum = 0;
            Vector arg = new Vector();
            arg.add(Name);
            BaseServletAgent bsa = new BaseServletAgent(uiVO);
            BaseVO bvo = new BaseVO(uiVO, this.getClass().toString(), CommandName.CallPLSQLCmd2, arg);
            bvo.setPackageName("AAA_1721_GET_HOUSE");
            bvo.setFunctionName("GETHOUSENUM");
            ResultVO rvo = bsa.doFunc(bvo);
            Vector result = rvo.getData();
            Hashtable row = (Hashtable) result.elementAt(1);
            HouseSum = Integer.parseInt(CloneArray_ChangeStr.NulltoSpace(row.get("NSUM")));
            System.out.println("=============HouseSum:" + HouseSum);
            String Intime = CloneArray_ChangeStr.NulltoSpace(row.get("INTIME"));
            String Outtime = CloneArray_ChangeStr.NulltoSpace(row.get("OUTTIME"));
            Vector vct = new Vector();
            if (bb) {
                HouseSum = HouseSum + i;
                vct.add(Name);
                vct.add(HouseSum);
                vct.add(TIM);
                vct.add(Outtime);
            } else {
                HouseSum = HouseSum - i;
                vct.add(Name);
                vct.add(HouseSum);
                vct.add(Intime);
                vct.add(TIM);
            }
            BaseServletAgent bs = new BaseServletAgent(uiVO);
            System.out.println("-------------------qqqqqqqq---------------------");
            BaseVO bv = new BaseVO(uiVO, this.getClass().toString(), CommandName.CallPLSQLCmd2, vct);
            bv.setPackageName("AAA_1721_GET_HOUSE");
            bv.setFunctionName("UPDATEGETHOUSE");
            ResultVO rv = bs.doFunc(bv);
            System.out.println("-------------------gggggggg---------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void InsertDetail(String uu) {//插入批號，ISN，KJPN，出入庫
        try {
            Iterator it = mmp.entrySet().iterator();
            String houseName = comboBox.getSelectedItem().toString();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                String value = String.valueOf(entry.getValue());
                String key = String.valueOf(entry.getKey());
                ReInHouse(key);
                if (zhang.equals("yichu")) {
                    Vector arg = new Vector();
                    arg.add(key);//ISNISN
                    arg.add(uu);
                    BaseServletAgent bsa = new BaseServletAgent(uiVO);
                    BaseVO bvo = new BaseVO(uiVO, this.getClass().toString(), CommandName.CallPLSQLCmd2, arg);
                    bvo.setPackageName("AAA_1721_GET_HOUSE");
                    bvo.setFunctionName("CHANGESTATUS");
                    ResultVO rvo = bsa.doFunc(bvo);
                    StepMP(key);
                }
                if (zhang.equals("weiru")) {
                    Vector arg = new Vector();
                    arg.add(key);//ISN
                    arg.add(value);
                    arg.add(uu);
                    BaseServletAgent bsa = new BaseServletAgent(uiVO);
                    BaseVO bvo = new BaseVO(uiVO, this.getClass().toString(), CommandName.CallPLSQLCmd2, arg);
                    bvo.setPackageName("AAA_1721_GET_HOUSE");
                    bvo.setFunctionName("INFOINSERT");
                    ResultVO rvo = bsa.doFunc(bvo);
                    StepMP(key);
                }
                Vector ar = new Vector();
                ar.add(comboBox.getSelectedItem().toString().trim());
                ar.add(BatchNumber);
                ar.add(key);//ISN
                ar.add(value);//KJNO
                ar.add(uu);
                ar.add(TIM);
                BaseServletAgent ba = new BaseServletAgent(uiVO);
                BaseVO bo = new BaseVO(uiVO, this.getClass().toString(), CommandName.CallPLSQLCmd2, ar);
                bo.setPackageName("AAA_1721_GET_HOUSE");
                bo.setFunctionName("INSERTNOTE");
                ResultVO rv = ba.doFunc(bo);
                StepMP(key);
                //ChangeStepStatus(DataSourceType._SFIS_KAIJIA_STD,key,1,"1",Section,Grp,"XXX",StepTime(),"D","0",uiVO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void InsertBatch(String uu, String Name) {//插入出入庫，批號，ISN數量，時間，出入庫說明，庫名，操作員
        String ss = JDesc.getText().toString().trim();
        String str = comboBox.getSelectedItem().toString().trim();
        String gonghao = JpersonNo.getText().toString().trim();
        if (str.contains("領用") || str.contains("量測") && !gonghao.equals("")) {
            ss = gonghao + "," + ss;
        }
        try {
            Vector arg = new Vector();
            arg.add(uu);
            arg.add(BatchNumber);
            arg.add(i);
            arg.add(TIM);
            arg.add(ss);
            arg.add(Name);
            arg.add(LoginName);
            BaseServletAgent bsa = new BaseServletAgent(uiVO);
            System.out.println("-------------------JJJJJJJJJ---------------------");
            BaseVO bvo = new BaseVO(uiVO, this.getClass().toString(), CommandName.CallPLSQLCmd2, arg);
            bvo.setPackageName("AAA_1721_GET_HOUSE");
            bvo.setFunctionName("INSERTINFO");
            ResultVO rvo = bsa.doFunc(bvo);
            System.out.println("-------------------ladallada---------------------");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void UpdateBNNumber() {//更新當天批號的筆數
        try {
            SimpleDateFormat SD = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            String TIM = SD.format(date);
            String DBtime = null;
            Vector arg = new Vector();
            BaseServletAgent bsa = new BaseServletAgent(uiVO);
            BaseVO bvo = new BaseVO(uiVO, this.getClass().toString(), CommandName.CallPLSQLCmd2, arg);
            bvo.setPackageName("AAA_1721_GET_HOUSE");
            bvo.setFunctionName("CHECKTIME");
            ResultVO rvo = bsa.doFunc(bvo);
            Vector result = rvo.getData();
            Hashtable row = null;
            for (int n = 1; n <= (result.size() - 1); n++) {
                row = (Hashtable) result.get(n);
                DBtime = CloneArray_ChangeStr.NulltoSpace(row.get("TIME"));
                DBNum = Integer.parseInt(CloneArray_ChangeStr.NulltoSpace(row.get("NUM")));
                System.out.println("=========########========Intime:" + DBtime);
                System.out.println("=========@@@@@========Outtime:" + DBNum);
            }
            System.out.println("=========&&&&&&&&&&&&&&&&&&&&&========TIM:" + TIM);
            System.out.println("=========&&&&&&&&&&&&&&&&&&&&&========DBtime:" + DBtime);
            if (TIM.equals(DBtime)) {
                DBNum++;
                System.out.println("=========&&&&&&&&&&&&&&&&&&&&&========DBNum:" + DBNum);
                Vector vct = new Vector();
                vct.add(TIM);
                vct.add(DBNum);
                BaseServletAgent bs = new BaseServletAgent(uiVO);
                BaseVO bv = new BaseVO(uiVO, this.getClass().toString(), CommandName.CallPLSQLCmd2, vct);
                bv.setPackageName("AAA_1721_GET_HOUSE");
                bv.setFunctionName("UpdateTIMENUM");
                ResultVO rv = bs.doFunc(bv);
            } else {
                DBNum = 1;
                Vector vct = new Vector();
                vct.add(TIM);
                vct.add(DBNum);
                BaseServletAgent bs = new BaseServletAgent(uiVO);
                BaseVO bv = new BaseVO(uiVO, this.getClass().toString(), CommandName.CallPLSQLCmd2, vct);
                bv.setPackageName("AAA_1721_GET_HOUSE");
                bv.setFunctionName("UpdateTIMENUM");
                ResultVO rv = bs.doFunc(bv);
            }
            System.out.println("-------------------test-----------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void UpdateDetail(String nu, String HouseName) {
        try {
            Iterator it = mmp.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                String ISN = String.valueOf(entry.getKey());
                String KJ = String.valueOf(entry.getValue());
                System.out.println("STATUS--------------------"+nu);
                System.out.println("ISN--------------------"+ISN);
                Vector arg = new Vector();
                arg.add(ISN);
                arg.add(nu);

                BaseServletAgent bsa = new BaseServletAgent(uiVO);
                BaseVO bvo = new BaseVO(uiVO, this.getClass().toString(), CommandName.CallPLSQLCmd2, arg);
                bvo.setPackageName("AAA_1721_GET_HOUSE");
                bvo.setFunctionName("CHANGESTATUS");
                ResultVO rvo = bsa.doFunc(bvo);
                StepMP(ISN);

                Vector ar = new Vector();
                ar.add(HouseName);
                ar.add(BatchNumber);
                ar.add(ISN);//ISN
                ar.add(KJ);//KJNO
                ar.add(nu);
                ar.add(TIM);
                BaseServletAgent ba = new BaseServletAgent(uiVO);
                BaseVO bo = new BaseVO(uiVO, this.getClass().toString(), CommandName.CallPLSQLCmd2, ar);
                bo.setPackageName("AAA_1721_GET_HOUSE");
                bo.setFunctionName("INSERTNOTE");
                ResultVO rv = ba.doFunc(bo);
                StepMP(ISN);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Hashtable getDBDATA(String sql, String ds, UI_InitVO uiVO) throws Exception {
        Vector arg = new Vector();
        arg.add(sql);
        BaseServletAgent bsa1 = new BaseServletAgent(uiVO);
        BaseVO bvo = new BaseVO(uiVO, this.getClass().toString(), CommandName.CallSQLCmd2, arg);
        bvo.setDataSourceType(DataSourceType._SFIS_KAIJIA_STD);
        ResultVO rvo = bsa1.doFunc(bvo);
        Vector result = rvo.getData();
        Hashtable ht = null;
        if (result.size() == 2) {
            ht = (Hashtable) result.elementAt(1);
        }
        return ht;
    }

    public void StepMP(String ISN) throws Exception {
        if (comboBox.getSelectedItem().toString().trim().contains("玻璃")) {
            return;
        } else {
            String reString = "select t.section,t.grp from tp.mo_d t where t.isn =  '" + ISN + "'";
            Vector arg = new Vector();
            arg.add(reString);
            BaseServletAgent bsa1 = new BaseServletAgent(uiVO);
            BaseVO bvo = new BaseVO(uiVO, this.getClass().toString(), CommandName.CallSQLCmd2, arg);
            bvo.setDataSourceType(DataSourceType._SFIS_KAIJIA_STD);
            ResultVO rvo = bsa1.doFunc(bvo);
            Vector result = rvo.getData();

            Hashtable ht = (Hashtable) result.elementAt(1);
            Section = CloneArray_ChangeStr.NulltoSpace(ht.get("SECTION"));
            Grp = CloneArray_ChangeStr.NulltoSpace(ht.get("GRP"));
            System.out.println("------size-------------result-size:" + result.size());
            System.out.println("------result-------------result:" + ht.toString());
            System.out.println("------ht-------------ht:" + ht.toString());
            System.out.println("=e===Section=======Section:" + Section);
            System.out.println("=====Grp======Grp:" + Grp);
        }
    }

    public Boolean BaoFei(String vv) {
        Boolean laodu = null;
        String sql = "select * from tp.mo_d t where t.isn = '" + vv + "' and t.status ='4'";
        try {
            Hashtable ht = getDBDATA(sql, DataSourceType._SFIS_KAIJIA_STD, uiVO);
            if (ht != null) {
                laodu = false;//已報廢
            } else {
                laodu = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return laodu;
    }

    public Boolean ReInHouse(String vv) {
        Boolean laodu = null;
        try {
            Vector arg = new Vector();
            arg.add(vv);
            //arg.add(comboBox.getSelectedItem().toString());
            BaseServletAgent bsa = new BaseServletAgent(uiVO);
            BaseVO bvo = new BaseVO(uiVO, this.getClass().toString(), CommandName.CallPLSQLCmd2, arg);
            bvo.setPackageName("AAA_1721_GET_HOUSE");
            bvo.setFunctionName("CHECKISNREIN");
            ResultVO rvo = bsa.doFunc(bvo);
            Vector result = rvo.getData();
            if (result.size() > 1) {
                Hashtable row = (Hashtable) result.get(1);
                String status = CloneArray_ChangeStr.NulltoSpace(row.get("STATUS"));
                if (status.equals("0")) {
                    laodu = false;//已存在某一線邊倉。
                    JOptionPane.showConfirmDialog(null, "該ISN已入,不可再入庫", "警告", JOptionPane.PLAIN_MESSAGE);
                }
                if (status.equals("2")) {
                    laodu = false;
                    JOptionPane.showConfirmDialog(null, "該ISN已被領用,不可再領", "警告", JOptionPane.PLAIN_MESSAGE);
                }
                if (status.equals("1")) {
                    laodu = true;
                    zhang = "yichu";
                }
                if (status.equals("3")) {
                    laodu = false;
                    JOptionPane.showConfirmDialog(null, "該ISN已除賬", "警告", JOptionPane.PLAIN_MESSAGE);
                }
                if (status.equals("4")) {
                    laodu = false;
                    JOptionPane.showConfirmDialog(null, "該ISN已銷毀", "警告", JOptionPane.PLAIN_MESSAGE);
                }
            } else {
                laodu = true;
                zhang = "weiru";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return laodu;
    }

    public void SelectLoginName() throws Exception {
        Vector arg = new Vector();
        arg.add(LoginNo);
        BaseServletAgent bsa = new BaseServletAgent(uiVO);
        BaseVO bvo = new BaseVO(uiVO, this.getClass().toString(), CommandName.CallPLSQLCmd2, arg);
        bvo.setPackageName("AAA_1721_GET_HOUSE");
        bvo.setFunctionName("USERNAME");
        ResultVO rvo = bsa.doFunc(bvo);
        Vector result = rvo.getData();
        Hashtable ht = (Hashtable) result.elementAt(1);
        LoginName = CloneArray_ChangeStr.NulltoSpace(ht.get("CNAME"));
        System.out.println("-------------------ladallada---------------------" + LoginName);
    }

    public String UserPower() {
        String Power = null;
        Vector arg = new Vector();
        arg.add(LoginNo);
        BaseServletAgent bsa = new BaseServletAgent(uiVO);
        BaseVO bvo = new BaseVO(uiVO, this.getClass().toString(), CommandName.CallPLSQLCmd2, arg);
        bvo.setPackageName("AAA_1721_GET_HOUSE");
        bvo.setFunctionName("USERPOWER");
        ResultVO rvo = null;
        try {
            rvo = bsa.doFunc(bvo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("*************************************************-");
        Vector result = rvo.getData();
        if (result.size() == 2) {
            Hashtable ht = (Hashtable) result.elementAt(1);
            Power = CloneArray_ChangeStr.NulltoSpace(ht.get("POWER"));
        } else {
            Power = "normal";
        }
        return Power;
    }

    public String BatchISNstatus(String str) {
        String status;
        Vector result = null;
        try {
            Vector arg = new Vector();
            arg.add(str);
            BaseServletAgent bsa = new BaseServletAgent(uiVO);
            BaseVO bvo = new BaseVO(uiVO, this.getClass().toString(), CommandName.CallPLSQLCmd2, arg);
            bvo.setPackageName("AAA_1721_GET_HOUSE");
            bvo.setFunctionName("CHECKISNSTATUS");
            ResultVO rvo = bsa.doFunc(bvo);
            result = rvo.getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Hashtable ht = (Hashtable) result.elementAt(1);
        status = CloneArray_ChangeStr.NulltoSpace(ht.get("STATUS"));
        return status;
    }

    public void doKeyAction(String cc, String KJ, Boolean status) {
        JKJNO.setText(KJ);
        if (mp.size() > 0) {
            Iterator iter = mp.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                Object val = entry.getValue();
                if (cc.equals(val.toString())) {
                    JOptionPane.showConfirmDialog(null, "刷入重複", "警告", JOptionPane.PLAIN_MESSAGE);
                    status = false;
                    break;
                }
            }
            if (status == true) {
                i++;
                mp.put(i, cc);
                mmp.put(cc, KJ);
                try {
                    TM.addRow(new Object[]{cc, KJ});
                    TM.fireTableDataChanged();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        } else {
            i++;
            mp.put(i, cc);
            mmp.put(cc, KJ);
            try {
                TM.addRow(new Object[]{cc, KJ});
                TM.fireTableDataChanged();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    public void BoLiCheck(String cc, String KJ, Boolean status) {
        if (cc.length() == 44) {
            Vector arg = new Vector();
            arg.add(cc);
            //arg.add(comboBox.getSelectedItem().toString());
            BaseServletAgent bsa = new BaseServletAgent(uiVO);
            BaseVO bvo = new BaseVO(uiVO, this.getClass().toString(), CommandName.CallPLSQLCmd2, arg);
            bvo.setPackageName("AAA_1721_GET_HOUSE");
            bvo.setFunctionName("CHECKISNREIN");
            ResultVO rvo = null;
            try {
                rvo = bsa.doFunc(bvo);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            Vector result = rvo.getData();
            if (result.size() == 2) {
                JOptionPane.showConfirmDialog(null, "該玻璃已入庫了", "警告", JOptionPane.PLAIN_MESSAGE);
            } else {
                doKeyAction(cc, KJ, status);
            }
        } else {
            JOptionPane.showConfirmDialog(null, "該玻璃ISN不符合規則", "警告", JOptionPane.PLAIN_MESSAGE);
        }
    }

    public void ChangeStepStatus(String owner_seq, String owner_sec, String owner_grp, String c_op, String c_date, String flag, String status) throws Exception {

        Iterator it = mmp.entrySet().iterator();
        Vector sqlVector = new Vector();
        String sql;
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            // String value = String.valueOf(entry.getValue());//KJ
            String isn = String.valueOf(entry.getKey());
            String seq = getSEQ(isn);
            int s = Integer.parseInt(seq) + 1;
            sql = "insert into tp.mo_route_owner (isn,seq,owner_seq,owner_sec,owner_grp,c_op,c_date,flag,status) " +
                    "values ('" + isn + "','" + s + "','" + owner_seq + "','" + owner_sec + "','" + owner_grp + "','" + c_op + "',to_date ('" + c_date + "','YYYY-MM-DD HH24:MI:SS'),'" + flag + "','" + status + "')";
            sqlVector.add(sql);
        }
        dh.updateData(sqlVector, DataSourceType._MultiCompanyCurrentDB);
        //String sql = "insert into tp.mo_route_owner (isn,seq,owner_seq,owner_sec,owner_grp,c_op,c_date,flag,status) " +
        //"values ('"+isn+"','"+s+"','"+owner_seq+"','"+owner_sec+"','"+owner_grp+"','"+c_op+"',to_date ('"+c_date+"','YYYY-MM-DD HH24:MI:SS'),'"+flag+"','"+status+"')";

    }

    public String getSEQ(String str) {
        String sql = "select t.seq from tp.mo_route_owner t where t.isn = '" + str + "'";
        Hashtable ht = null;
        String seq = null;
        Vector arg = new Vector();
        arg.add(sql);
        BaseServletAgent bsa1 = new BaseServletAgent(uiVO);
        BaseVO bvo = new BaseVO(uiVO, this.getClass().toString(), CommandName.CallSQLCmd2, arg);
        bvo.setDataSourceType(DataSourceType._SFIS_KAIJIA_STD);
        ResultVO rvo = null;
        try {
            rvo = bsa1.doFunc(bvo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Vector result = rvo.getData();
        if (result.size() == 2) {
            ht = (Hashtable) result.elementAt(1);
            seq = CloneArray_ChangeStr.NulltoSpace(ht.get("SEQ"));
        }
        if (result.size() < 2) {
            seq = "0";
        }
        return seq;
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
