package mes_sfis.client.ui.pack;

import base.client.ui.BasePanel;
import base.client.util.component.PDateTimeTextField;
import base.client.util.component.P_Component_Meta;
import base.vo.UI_InitVO;
import mes_sfis.client.excel.EXCEL_1721;
import mes_sfis.client.model.service.BadnessWarehouseQueryService;
import mes_sfis.client.ui.barcode.MyTableModel;
import mes_sfis.client.util.DataHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by Srx_Zhu on 2018/09/04.
 */
public class BadnessWarehouseQuery extends BasePanel {
    public static final String VERSION = "$Name: $, $Id: BadnessWarehouseQuery.java,v 1.0 2018/08/29 08:52:07 Srx_zhu Exp $";
    public static final String COPYRIGHT = "Copyright (c) 2008 PEGATRON Inc. All Rights Reserved.";
    JPanel jp = null;
    JLabel jl1 = new JLabel("請選擇倉庫");
    JLabel jl2 = new JLabel("請輸入儲位");
    JLabel jl3 = new JLabel("請輸入箱號");
    JLabel jl4 = new JLabel("總箱數");
    JLabel jl5 = new JLabel("起始日期");
    JLabel jl6 = new JLabel("截止日期");
    JLabel jl7 = new JLabel("單個 ISN/CSSN 查詢");
    JLabel jl8 = new JLabel("總產品數");
    JLabel jl9 = new JLabel("");
    JLabel jl10 = new JLabel("");
    JLabel jl11 = new JLabel("ERROR");
    JLabel jl12 = new JLabel("ROUTE");
    JLabel jl13 = new JLabel("站點");
    JLabel jl14 = new JLabel("");
    BadnessWarehouseQueryService badnessWarehouseQueryService;
    DataHandler dh;
    PDateTimeTextField beginTime;
    PDateTimeTextField endTime;
    JComboBox jc1 = new JComboBox();
    JComboBox jc2 = new JComboBox();
    JComboBox jc3 = new JComboBox();
    JComboBox jc4 = new JComboBox();
    JComboBox jc5 = new JComboBox();
    JTextField jt1 = new JTextField();
    JTextField jt2 = new JTextField();
    JTextField jt3 = new JTextField();

    JButton jb1 = new JButton("點擊查詢");
    JButton jb2 = new JButton("導出資料");

    private MyTableModel TM;
    private Font font = new Font("宋體", Font.BOLD, 14);
    private Color color = new Color(106, 106, 255);
    Object[][] ExcelData;

    public BadnessWarehouseQuery(UI_InitVO uiVO) {
        super(uiVO);
        badnessWarehouseQueryService = new BadnessWarehouseQueryService(uiVO);
        dh = new DataHandler(uiVO);
        init();
    }

    public void init() {

        String[] ss = new String[]{"倉庫", "儲位", "箱號", "ISN", "CSSN", "入庫日期", "出庫日期", "ERROR", "站點", "操作員"};
        Object[][][] ooo = {new Object[][]{{"", "", "", "", "", "", "", "", "", ""}}};
        final JTable table = MyTable(ooo[0], ss);
        JScrollPane jsp = new JScrollPane(table);
        jsp.getViewport().setBackground(Color.white);
        jsp.setBounds(0, 100, 800, 500);
        beginTime = MyDateField(500, 55, 120, 30);
        endTime = MyDateField(500, 105, 120, 30);
        jc1.addItem("不良品倉");
        jc2.addItem("出庫");
        jc2.addItem("在庫");

        jp = new JPanel();
        jl1.setBounds(50, 50, 120, 30);
        jl2.setBounds(50, 100, 120, 30);
        jl3.setBounds(50, 150, 120, 30);
        jl4.setBounds(50, 200, 120, 30);
        jl5.setBounds(400, 50, 120, 30);
        jl6.setBounds(400, 100, 120, 30);
        jl7.setBounds(390, 150, 130, 30);
        jl8.setBounds(200, 200, 120, 30);
        jl9.setBounds(120, 200, 70, 30);
        jl10.setBounds(300, 200, 70, 30);
        jl11.setBounds(650, 55, 80, 30);
        jl12.setBounds(650, 105, 80, 30);
        jl13.setBounds(870, 105, 80, 30);
        jl14.setBounds(450, 200, 0, 0);
        jc1.setBounds(130, 50, 130, 30);
        jc2.setBounds(280, 50, 70, 30);
        jc3.setBounds(720, 55, 130, 30);
        jc4.setBounds(720, 105, 130, 30);
        jc5.setBounds(940, 105, 130, 30);
        jt1.setBounds(130, 100, 200, 30);
        jt2.setBounds(130, 150, 200, 30);
        jt3.setBounds(500, 150, 200, 30);
        jb1.setBounds(400, 200, 100, 30);
        jb2.setBounds(520, 200, 100, 30);
        jsp.setBounds(50, 240, 1500, 300);


        this.add(jc1);
        this.add(jc2);
        this.add(jc3);
        this.add(jc4);
        this.add(jc5);
        this.add(jl1);
        this.add(jl2);
        this.add(jl3);
        this.add(jl4);
        this.add(jl5);
        this.add(jl6);
        this.add(jl7);
        this.add(jl8);
        this.add(jl9);
        this.add(jl10);
        this.add(jt1);
        this.add(jt2);
        this.add(jt3);
        this.add(jb1);
        this.add(jb2);
        this.add(beginTime);
        this.add(endTime);
        this.add(jp);
        this.add(jsp);
        this.add(jl11);
        this.add(jl12);
        this.add(jl13);
        this.add(jl14);

        List<Hashtable> list = new ArrayList<>();
        list = badnessWarehouseQueryService.getAllError();
        jc3.addItem("--");
        if (list != null) {
            for (Hashtable ht : list) {
                jc3.addItem(ht.get("ERROR"));
            }
        }
        List<Hashtable> list2 = new ArrayList<>();
        list2 = badnessWarehouseQueryService.getAllRoute();
        jc4.addItem("--");
        jc5.addItem("--");
        if (list2 != null) {
            for (Hashtable ht : list2) {
                jc4.addItem(ht.get("ROUTE"));
            }
        }
        jc4.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    switch (e.getStateChange()) {
                        case ItemEvent.SELECTED:
                            getAllDevice(jc4.getSelectedItem().toString());
                            break;
                        case ItemEvent.DESELECTED:
                            getAllDevice(jc4.getSelectedItem().toString());
                            break;
                    }
                }
            }
        });


        jb1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyTableModel MTM = (MyTableModel) table.getModel();
                MTM.Clearcontents();
                Object[][] ob;
                if (!jt1.getText().trim().equals("")) {
                    ob = QueryByStorage();
                } else if (!jt2.getText().trim().equals("")) {
                    ob = QueryByCarton();
                } else if (!jt3.getText().trim().equals("")) {
                    ob = QueryByISN();
                } else {
                    ob = QueryByTime();
                }
                try {
                    MTM.refreshContents(ob);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                MTM.fireTableDataChanged();
                ExcelData = ob;
            }

        });
        jb2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(ExcelData!=null){
                    ExportExcel();
                }else{
                    JOptionPane.showConfirmDialog(null, "查詢數據為空，不可導出", "提示", JOptionPane.PLAIN_MESSAGE);
                }

            }




        });


    }
    public void ExportExcel() {
        EXCEL_1721 excel = new EXCEL_1721();
        try {
            excel.ExportNoResponse(excel.sheetName6, excel.titleName6, excel.fileName6, excel.columnNumber6, excel.columnWidth6, excel.tableTitleName6, ExcelData);
            JOptionPane.showConfirmDialog(null, "導出EXCEL至D盤成功", "提示", JOptionPane.PLAIN_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getAllDevice(String s) {
        List<Hashtable> list = new ArrayList<>();
        list = badnessWarehouseQueryService.getAllDevice(s);
        jc5.removeAllItems();
        jc5.addItem("--");
        if(list!=null){
            for(Hashtable ht:list){
                jc5.addItem(ht.get("STEPNM"));
            }
        }
    }

    private Object[][] QueryByStorage() {
        Object[][] ModelObject = null;
        List<Hashtable> list = badnessWarehouseQueryService.QueryByStorage(jc1.getSelectedItem().toString(), String.valueOf(jc2.getSelectedIndex()), jt1.getText(), beginTime.getText(), endTime.getText(),jc3.getSelectedItem().toString(),jc4.getSelectedItem().toString(),jc5.getSelectedItem().toString());
        if (list != null) {
            jl9.setText(String.valueOf(list.size()));
            int sum = 0;
            for (Hashtable ht : list) {
                sum += Integer.parseInt(ht.get("SUM").toString());
            }
            jl10.setText(String.valueOf(sum));
        } else {
            jl9.setText("0");
            jl10.setText("0");
        }
        ModelObject = getModelBack(list);
        return ModelObject;
    }

    private Object[][] QueryByCarton() {
        Object[][] ModelObject = null;
        List<Hashtable> list = badnessWarehouseQueryService.QueryByCarton(jt2.getText(), String.valueOf(jc2.getSelectedIndex()));
        if (list != null) {
            jl9.setText("1");
            jl10.setText(list.get(0).get("SUM").toString());
        } else {
            jl9.setText("0");
            jl10.setText("0");
        }
        ModelObject = getModelBack(list);
        return ModelObject;
    }

    private Object[][] QueryByISN() {
        Object[][] ModelObject = null;
        List<Hashtable> list = badnessWarehouseQueryService.QueryByISN(jt3.getText());
        jl9.setText("0");
        jl10.setText("0");
        ModelObject = getModelBack(list);
        return ModelObject;
    }


    private Object[][] QueryByTime() {
        Object[][] ModelObject = null;
        List<Hashtable> list = badnessWarehouseQueryService.QueryByTime(jc1.getSelectedItem().toString(), jc2.getSelectedIndex(), beginTime.getText(), endTime.getText(),jc3.getSelectedItem().toString(),jc4.getSelectedItem().toString(),jc5.getSelectedItem().toString());
        List<Hashtable> ht = badnessWarehouseQueryService.getSumConfigOfTime(jc1.getSelectedItem().toString(), jc2.getSelectedIndex(), beginTime.getText(), endTime.getText(),jc3.getSelectedItem().toString(),jc4.getSelectedItem().toString(),jc5.getSelectedItem().toString());
        if (ht != null) {
            jl9.setText(String.valueOf(ht.size()));
            int sum = 0;
            for (Hashtable ht2 : ht) {
                sum += Integer.parseInt(ht2.get("SUM").toString());
            }
            jl10.setText(String.valueOf(sum));
        } else {
            jl9.setText("0");
            jl10.setText("0");
        }
        ModelObject = getModelBack(list);
        return ModelObject;
    }

    private Object[][] getModelBack(List<Hashtable> list) {
        Object[][] ModelObject = null;
        if (list != null) {
            ModelObject = new Object[list.size()][10];
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).get("WAREHOUSE_NAME") != null) {
                    ModelObject[i][0] = list.get(i).get("WAREHOUSE_NAME");
                } else {
                    ModelObject[i][0] = "";
                }
                if (list.get(i).get("STORAGE_ID") != null) {
                    ModelObject[i][1] = list.get(i).get("STORAGE_ID");
                } else {
                    ModelObject[i][1] = "";
                }
                if (list.get(i).get("CARTON_ID") != null) {
                    ModelObject[i][2] = list.get(i).get("CARTON_ID");
                } else {
                    ModelObject[i][2] = "";
                }
                if (list.get(i).get("ISN") != null) {
                    ModelObject[i][3] = list.get(i).get("ISN");
                } else {
                    ModelObject[i][3] = "";
                }
                if (list.get(i).get("CSSN") != null) {
                    ModelObject[i][4] = list.get(i).get("CSSN");
                } else {
                    ModelObject[i][4] = "";
                }
                if (list.get(i).get("PUT_IN_TIME") != null) {
                    ModelObject[i][5] = list.get(i).get("PUT_IN_TIME").toString();
                } else {
                    ModelObject[i][5] = "";
                }
                if (list.get(i).get("PUT_OUT_TIME") != null) {
                    ModelObject[i][6] = list.get(i).get("PUT_OUT_TIME").toString();
                } else {
                    ModelObject[i][6] = "";
                }
                if (list.get(i).get("ERROR") != null) {
                    ModelObject[i][7] = list.get(i).get("ERROR").toString();
                } else {
                    ModelObject[i][7] = "";
                }
                if (list.get(i).get("DEVICE") != null) {
                    ModelObject[i][8] = list.get(i).get("ROUTE").toString() + "---" + list.get(i).get("DEVICE").toString();
                } else {
                    ModelObject[i][8] = "";
                }
                if (list.get(i).get("EMP_OID") != null) {
                    ModelObject[i][9] = list.get(i).get("EMP_OID");
                } else {
                    ModelObject[i][9] = "";
                }
            }
        }
        return ModelObject;
    }

    private PDateTimeTextField MyDateField(int x, int y, int w, int h) {
        PDateTimeTextField pdtf = new PDateTimeTextField(uiVO, "PDTimeST", 100, true, true);
        pdtf.setBounds(x, y, w, h);
        return pdtf;
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
