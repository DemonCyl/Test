package mes_sfis.client.ui.barcode;

import base.client.ui.BasePanel;
import base.client.util.component.P_Component_Meta;
import base.vo.UI_InitVO;
import mes_sfis.client.model.service.Borrow_LenderService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by SRX_zhu on 2018/5/21.
 */
public class ScrapBack_1721 extends BasePanel {
    public static final String VERSION = "$Name:  $, $Id: ScrapBack_1721.java,v 1.10 2018/04/16 09:28:13 SRX_zhu Exp $";
    public static final String COPYRIGHT = "Copyright (c) 2008 PEGATRON Inc. All Rights Reserved.";
    private Color color = new Color(106, 106, 255);
    private Font font = new Font("����", Font.BOLD, 15);
    JPanel panel = new JPanel();
    MyTableModel TM;
    JLabel ISNLabel = new JLabel("ISN:");
    JLabel BatchLabel = new JLabel("BATCH:");
    JLabel JobNumLabel = new JLabel("�u��:");
    JButton CreditBtn = new JButton("�T�{�ɤJ");
    JLabel QueryLabel = new JLabel("�w��J:");
    JLabel returnLabel = new JLabel("�k�ټo��");
    JLabel returnLabel2 = new JLabel("�п�J�帹");
    JButton returnBtn = new JButton("�T�{�k��");
    JLabel JobNumDescLabel = new JLabel("(����I�u��Ѹ���Τ�ɥX�I)");
    JTextField returnText = new JTextField();
    JTextField JobNumText = new JTextField(20);
    JTextField ISNText = new JTextField();
    JTextField BatchText = new JTextField(20);
    Borrow_LenderService borrow;

    String[] ss = new String[]{"�Z�ŮƸ�", "ISN", "�帹"};
    Object[][][] ooo = {new Object[][]{{"", "", ""}}};
    JTable table = MyTable(ooo[0], ss);
    Object[][] tableData = null;
    Object[][] tableData1 = null;
    JScrollPane jsp = new JScrollPane(table);
    java.util.List<HashMap> simpleList = new ArrayList<HashMap>();
    String BatchNo = "";
    String houseName = "";
    List ISNList = new ArrayList();

    public ScrapBack_1721(UI_InitVO uiVO) {
        super(uiVO);
        borrow = new Borrow_LenderService(uiVO);
        init();
    }

    public void init() {
        jsp.getViewport().setBackground(Color.white);
        this.add(panel);
        this.add(jsp);
        this.add(CreditBtn);
        this.add(BatchText);
        this.add(ISNText);
        this.add(ISNLabel);
        this.add(BatchLabel);
        this.add(JobNumLabel);
        this.add(JobNumText);
        this.add(QueryLabel);
        this.add(returnLabel);
        this.add(returnLabel2);
        this.add(returnText);
        this.add(returnBtn);
        this.add(JobNumDescLabel);

        QueryLabel.setBounds(50, 300, 80, 25);
        CreditBtn.setBounds(50, 150, 130, 25);
        BatchText.setBounds(100, 80, 165, 25);
        BatchLabel.setBounds(50, 80, 80, 25);
        ISNLabel.setBounds(50, 50, 80, 25);
        ISNText.setBounds(100, 50, 165, 25);
        JobNumLabel.setBounds(50, 110, 80, 25);
        JobNumText.setBounds(100, 110, 165, 25);
        jsp.setBounds(50, 330, 700, 200);
        returnLabel.setBounds(50, 220, 130, 25);
        returnLabel2.setBounds(50, 250, 80, 25);
        returnText.setBounds(140, 250, 150, 25);
        returnBtn.setBounds(340, 250, 130, 25);
        JobNumDescLabel.setBounds(270, 110, 300, 25);

        ISNText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ISNText.getText().toString().equals("")) {
                    JOptionPane.showConfirmDialog(null, "�п�JISN", "", JOptionPane.PLAIN_MESSAGE);
                } else {
                    Hashtable ht = new Hashtable();
                    ht = borrow.CheckISN(ISNText.getText().toString().trim(),"5");
                    if (ht == null) {
                        JOptionPane.showConfirmDialog(null, "�d�L��ISN", "", JOptionPane.PLAIN_MESSAGE);
                        return;
                    }
                    System.out.println("------------------"+ht.get("ISN"));
                    if(!ht.get("STATUS").equals("1")){
                        JOptionPane.showConfirmDialog(null, "�Ӽo�ƥ��ɥX�I�Юֹ�I", "", JOptionPane.PLAIN_MESSAGE);
                        return;
                    }
                    HashMap simpleMap = new HashMap();
                    simpleMap.put("KJ", ht.get("KJ"));
                    simpleMap.put("ISN", ht.get("ISN"));
                    simpleMap.put("BATCH", ht.get("BATCH"));
                    simpleMap.put("HOUSE", ht.get("HOUSE"));
                    if (ht.get("STATUS").toString().equals("1")) {
                        for (int i = 0; i < ISNList.size(); i++) {
                            if (ISNList.get(i).toString().equals(ht.get("ISN").toString())) {
                                JOptionPane.showConfirmDialog(null, "�Фŭ��ƨ�J�o�ơI�I", "", JOptionPane.PLAIN_MESSAGE);
                                return;
                            }
                        }
                        ISNList.add(ht.get("ISN"));
                        simpleList.add(simpleMap);
                        tableData1 = new Object[simpleList.size()][3];
                        for (int i = 0; i < simpleList.size(); i++) {
                            tableData1[i][0] = simpleList.get(i).get("KJ");
                            tableData1[i][1] = simpleList.get(i).get("ISN");
                            tableData1[i][2] = simpleList.get(i).get("BATCH");
                        }
                        MyTableModel MTM = (MyTableModel) table.getModel();
                        MTM.Clearcontents();
                        try {
                            MTM.refreshContents(tableData1);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                        MTM.fireTableDataChanged();
                        BatchNo = ht.get("BATCH").toString();
                        houseName = ht.get("HOUSE").toString();
                        ISNText.setText("");
                        ht = null;
                    } else {
                        JOptionPane.showConfirmDialog(null, "��ISN�|���ɥX�A���i�J�w�I", "", JOptionPane.PLAIN_MESSAGE);
                    }

                }
            }
        });
        BatchText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (BatchText.getText().toString().equals("")) {
                    JOptionPane.showConfirmDialog(null, "�п�JBATCH", "", JOptionPane.PLAIN_MESSAGE);
                } else {
                    List<Hashtable> isnList = null;
                    try {
                        isnList = borrow.CheckBATCH(BatchText.getText().toString());
                        if (isnList == null) {
                            JOptionPane.showConfirmDialog(null, "�ӧ帹���s�b�A�Юֹ�I", "", JOptionPane.PLAIN_MESSAGE);
                            return;
                        }
                        BatchNo = "";
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    BatchNo = isnList.get(0).get("BATCH").toString();
                    houseName = isnList.get(0).get("HOUSE").toString();
                    tableData = new Object[isnList.size()][3];
                    for (int i = 0; i < isnList.size(); i++) {
                        tableData[i][0] = isnList.get(i).get("KJ");
                        tableData[i][1] = isnList.get(i).get("ISN");
                        tableData[i][2] = isnList.get(i).get("BATCH");
                        HashMap dataMap = new HashMap();
                        dataMap.put("KJ", isnList.get(i).get("KJ").toString());
                        dataMap.put("ISN", isnList.get(i).get("ISN").toString());
                        dataMap.put("BATCH", isnList.get(i).get("BATCH").toString());
                        dataMap.put("HOUSE", isnList.get(i).get("HOUSE").toString());
                        simpleList.add(dataMap);
                        if (!isnList.get(i).get("STATUS").equals("1")) {
                            JOptionPane.showConfirmDialog(null, "�ӧ帹�������~�|�����o�A�Юֹ�", "", JOptionPane.PLAIN_MESSAGE);
                            return;
                        }
                    }
                    MyTableModel MTM = (MyTableModel) table.getModel();
                    MTM.Clearcontents();
                    try {
                        MTM.refreshContents(tableData);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    MTM.fireTableDataChanged();
                    BatchText.setText("");
                }
            }
        });
        //�T�{�ɤJ
        CreditBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String empOid = "";
                empOid = borrow.CheckEmpOid(JobNumText.getText().toString());
                if (empOid == null || !empOid.equals("department")) {
                    JOptionPane.showConfirmDialog(null, "�ӥΤᤣ�s�b�εL�v�ɤJ", "", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                if (simpleList == null) {
                    JOptionPane.showConfirmDialog(null, "�ɤJ���i���šI", "", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                try {
                    borrow.ChangeWAREHOUSE("���o��έ�", simpleList.size(), "5");
                    borrow.InsertNote(simpleList,"5",BatchNo);
                    borrow.ChangeISNStatus(simpleList, "0");
                    borrow.ChangeInOutWAREHOUSE(BatchNo, simpleList.size(), JobNumText.getText().toString(),"���o��έ�", uiVO.getLogin_id(), "5");
                    JOptionPane.showConfirmDialog(null, "�ɤJ���\�I", "", JOptionPane.PLAIN_MESSAGE);
                } catch (Exception e1) {
                    JOptionPane.showConfirmDialog(null, "�ɤJ���ѡI���pôMIS�Үֹ�", "", JOptionPane.PLAIN_MESSAGE);
                    e1.printStackTrace();
                }

            }

        });
        //�T�{�k��
        returnBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String empOid = "";
                empOid = borrow.CheckEmpOid(JobNumText.getText().toString());
                if (empOid == null || !empOid.equals("department")) {
                    JOptionPane.showConfirmDialog(null, "�ӥΤᤣ�s�b�εL�v�k��", "", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                try {
                    String house = borrow.CheckReturnBatch(returnText.getText().toString());
                    if (house == null) {
                        JOptionPane.showConfirmDialog(null, "�d�L���帹�A�Юֹ�I", "", JOptionPane.PLAIN_MESSAGE);
                        return;
                    }

                    List<Hashtable> list = new ArrayList<Hashtable>();
                    list = borrow.CheckBATCH(returnText.getText().toString());
                    for (int i = 0; i < list.size(); i++) {
                        HashMap dataMap = new HashMap();
                        dataMap.put("KJ", list.get(i).get("KJ").toString());
                        dataMap.put("ISN", list.get(i).get("ISN").toString());
                        dataMap.put("BATCH", list.get(i).get("BATCH").toString());
                        dataMap.put("HOUSE", list.get(i).get("HOUSE").toString());
                        simpleList.add(dataMap);
                    }

                    borrow.ChangeISNStatus(simpleList, "1");
                    borrow.ChangeWAREHOUSE("���o��έ�", simpleList.size(), "6");
                    borrow.ChangeInOutWAREHOUSE(returnText.getText().toString(),0, "","���o�����", uiVO.getLogin_id(), "6");
                    borrow.InsertNote(simpleList, "6",returnText.getText().toString());
                    JOptionPane.showConfirmDialog(null, "�k�٦��\�I���pô�ܮw��J�I", "", JOptionPane.PLAIN_MESSAGE);
                } catch (Exception e1) {
                    JOptionPane.showConfirmDialog(null, "�k�٥��ѡI���pôMIS�Үֹ�", "", JOptionPane.PLAIN_MESSAGE);
                    e1.printStackTrace();
                }
            }

        });
    }

    public JTable MyTable(Object[][] data, String[] tableHead) {
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
