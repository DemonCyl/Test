package mes_sfis.client.ui.barcode;

import base.client.ui.BasePanel;
import base.client.util.BaseServletAgent;
import base.client.util.CloneArray_ChangeStr;
import base.client.util.component.PDateTimeTextField;
import base.client.util.component.P_Component_Meta;
import base.enums.CommandName;
import base.enums.DataSourceType;
import base.vo.BaseVO;
import base.vo.ResultVO;
import base.vo.UI_InitVO;
import base.vo.URL_VO;
import com.lowagie.text.*;
import jxl.*;
import jxl.read.biff.BiffException;
import mes_sfis.client.excel.EXCEL_1721;
import mes_sfis.client.util.DataHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.List;

/**
 * Created by Feng1_Lu on 2018/1/22.
 */
public class LF_1721_indexView extends BasePanel {
    public static final String VERSION = "$Name:  $, $Id: LF_1721_indexView.java,v 1.14 2018/04/05 08:16:33 Lucy6_Lu Exp $";
    public static final String COPYRIGHT = "Copyright (c) 2017 PEGATRON Inc. All Rights Reserved.";
    private Font font = new Font("宋體", Font.BOLD, 14);
    private Color color = new Color(106, 106, 255);
    private String FilePath;
    private MyTableModel TM;
    private DataHandler dh;
    private Object[][] ExcelData1 = null;
    private Object[][] ExcelData2 = null;
    private Object[][] ExcelData3 = null;
    private Object[][] ExcelData4 = null;
    private int num4=0;


    public LF_1721_indexView(UI_InitVO uiVO) {
        super(uiVO);
        dh = new DataHandler(uiVO);
        init();
    }


    public void init() {
        // setUILayout(null);
        JButton btn1 = MyButton("線邊倉庫存查詢", 20, 10, 150, 30);
        JButton btn2 = MyButton("入庫批號查詢", 180, 10, 150, 30);
        JButton btn3 = MyButton("線邊倉ISN查詢", 340, 10, 150, 30);
        JButton btn4 = MyButton("報廢儲位查詢", 500, 10, 150, 30);
        JPanel panel1 = Jpanel1();
        JPanel panel2 = Jpanel2();
        panel2.setVisible(false);
        JPanel panel3 = Jpanel3();
        panel3.setVisible(false);
        JPanel panel4 = Jpanel4();
        panel4.setVisible(false);
        add(btn1);
        add(btn2);
        add(btn3);
        add(btn4);
        add(panel1);
        add(panel2);
        add(panel3);
        add(panel4);
        munuBtnAction(btn1, panel1, panel2, panel3, panel4);
        munuBtnAction(btn2, panel2, panel1, panel3, panel4);
        munuBtnAction(btn3, panel3, panel1, panel2, panel4);
        munuBtnAction(btn4, panel4, panel1, panel2, panel3);
    }

    private JPanel Jpanel1() {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(null);
        jPanel.setBounds(20, 60, 800, 600);
        JLabel HouseName = Mylabel("線邊倉名稱:", 0, 0, 150, 30);
        JLabel TimeName = Mylabel("時間:", 270, 0, 60, 30);
        JLabel To = Mylabel("到", 450, 0, 40, 30);
        JLabel Sum = Mylabel("庫存餘額:", 180, 40, 150, 30);
        JButton Index = MyButton("查詢", 60, 40, 100, 30);
        JComboBox comboBox = MyComboBox(160, 0, 100, 30);
        AddItem(comboBox);
        PDateTimeTextField beginTime = MyDateField(340, 0, 100, 30);
        PDateTimeTextField endTime = MyDateField(500, 0, 100, 30);
        JTextField JSum = MyTextField(340, 40, 100, 30);
        JButton JExportExcel1 = MyButton("導出", 620, 0, 100, 30);
        String[] ss = new String[]{"批號", "入庫/借出/除賬", "數量", "出庫/防還/除賬/銷毀", "數量", "作業人員"};
        Object[][] ooo = new Object[][]{{"", "", "", "", "", ""}};
        JTable table1 = MyTable(ooo, ss);
        JScrollPane jsp = new JScrollPane(table1);
        jsp.getViewport().setBackground(Color.white);
        jsp.setBounds(0, 80, 600, 500);
        jPanel.add(JExportExcel1);
        jPanel.add(HouseName);
        jPanel.add(TimeName);
        jPanel.add(To);
        jPanel.add(Sum);
        jPanel.add(Index);
        jPanel.add(comboBox);
        jPanel.add(beginTime);
        jPanel.add(endTime);
        jPanel.add(JSum);
        jPanel.add(jsp);
        BtnActionP1(Index, table1, comboBox, beginTime, endTime, JSum);
        JExportExcel1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int a = 1;
                ExportExcel(a);
            }
        });

        return jPanel;
    }

    private JPanel Jpanel2() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(20, 60, 800, 600);
        JLabel Batch = Mylabel("批號:", 50, 0, 100, 30);
        JLabel HouseName = Mylabel("線邊倉名稱:", 0, 40, 150, 30);
        JLabel Status = Mylabel("出入模式:", 260, 40, 100, 30);
        JLabel Sum = Mylabel("數量:", 440, 40, 60, 30);
        JTextField JBatch = MyTextField(160, 0, 100, 30);
        JTextField JHname = MyTextField(160, 40, 100, 30);
        JTextField JStatus = MyTextField(370, 40, 80, 30);
        JTextField JSum = MyTextField(510, 40, 80, 30);
        JButton Index = MyButton("查詢", 510, 0, 80, 30);
        JButton JExportExcel2 = MyButton("導出", 620, 0, 100, 30);
        String[] ss = new String[]{"ISN", "料號", "日期", "操作人員", "線邊倉名稱"};
        Object[][] ooo = new Object[][]{{"", "", "", "", ""}};
        JTable table = MyTable(ooo, ss);
        JScrollPane jsp = new JScrollPane(table);
        jsp.getViewport().setBackground(Color.white);
        jsp.setBounds(0, 80, 600, 500);

        panel.add(JExportExcel2);
        panel.add(Batch);
        panel.add(HouseName);
        panel.add(Status);
        panel.add(Sum);
        panel.add(JBatch);
        panel.add(JHname);
        panel.add(JStatus);
        panel.add(JSum);
        panel.add(Index);
        panel.add(jsp);
        BtnActionP2(Index, JBatch, JHname, JStatus, JSum, table);
        JExportExcel2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int a = 2;
                ExportExcel(a);
            }
        });
        return panel;
    }

    private JPanel Jpanel3() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(20, 60, 800, 600);
        JLabel isn = Mylabel("單個ISN:", 0, 0, 100, 30);
        JTextField Jisn = MyTextField(110, 0, 150, 30);
        JButton btn = MyButton("多個ISN", 280, 0, 100, 30);
        JButton Index = MyButton("查詢", 390, 0, 100, 30);
        JButton JExportExcel3 = MyButton("導出", 620, 0, 100, 30);
        String[] ss = new String[]{"ISN", "批號", "倉名稱", "狀態"};
        Object[][] ooo = new Object[][]{{"", "", "", ""}};
        JTable table = MyTable(ooo, ss);
        JScrollPane jsp = new JScrollPane(table);
        jsp.getViewport().setBackground(Color.white);
        jsp.setBounds(0, 80, 600, 500);
        panel.add(JExportExcel3);
        panel.add(isn);
        panel.add(Jisn);
        panel.add(btn);
        panel.add(jsp);
        panel.add(Index);
        OpenFile(btn, table);
        BtnActionP3(Index, table, Jisn);
        JExportExcel3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int a = 3;
                ExportExcel(a);
            }
        });
        return panel;
    }

    private JPanel Jpanel4() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(20, 60, 1200, 600);
        JLabel isn = Mylabel("儲位:", 0, 0, 50, 30);
        JButton Index_4 = MyButton("查詢", 0, 40, 100, 30);
        JLabel status4 = Mylabel("狀態:", 100, 40, 60, 30);
        JComboBox comboBox_4 = MyComboBox(170, 40, 90, 30);
        comboBox_4.addItem("");
        comboBox_4.addItem("在庫");
        comboBox_4.addItem("銷毀");
        JTextField Jisn = MyTextField(50, 0, 100, 30);
        JLabel beginTime = Mylabel("時間:", 250, 0, 60, 30);
        PDateTimeTextField beginTime_2 = MyDateField(320, 0, 100, 30);
        JLabel endTime = Mylabel("到", 250, 40, 40, 30);
        PDateTimeTextField endTime_2 = MyDateField(320, 40, 100, 30);
        JLabel carton = Mylabel("箱號", 140, 0, 50, 30);
        JTextField Jcarton = MyTextField(200, 0, 75, 30);
        JLabel carton2 = Mylabel("成品碼", 440, 0, 50, 25);
        JTextField Jcarton2 = MyTextField(500, 0, 300, 25);
        JLabel carton3 = Mylabel("ISN", 440, 25, 50, 25);
        JTextField Jcarton3 = MyTextField(500, 25, 300, 25);
        JLabel carton4 = Mylabel("簡明碼", 440, 50, 50, 25);
        JTextField Jcarton4 = MyTextField(500, 50, 300, 25);
        JLabel carton5 = Mylabel("數量", 810, 50,30, 25);
        JTextField Jcarton5 = MyTextField(860, 50, 100, 25);


        JButton JExportExcel4 = MyButton("導出", 820, 0, 100, 30);
        String[] ss = new String[]{"儲位", "批號", "箱號", "操作員", "日期", "ISN", "倉名"};
        Object[][] ooo = new Object[][]{{"", "", "", "", "", "", ""}};
        JTable table = MyTable(ooo, ss);
        JScrollPane jsp = new JScrollPane(table);
        jsp.getViewport().setBackground(Color.white);
        jsp.setBounds(0, 80, 1000, 500);


        JExportExcel4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int a = 4;
                ExportExcel(a);
            }
        });

        panel.add(isn);
        panel.add(Jisn);
        panel.add(jsp);
        panel.add(Index_4);
        panel.add(beginTime);
        panel.add(endTime);
        panel.add(beginTime_2);
        panel.add(endTime_2);
        panel.add(carton);
        panel.add(Jcarton);
        panel.add(JExportExcel4);
        panel.add(carton2);
        panel.add(Jcarton2);
        panel.add(carton3);
        panel.add(Jcarton3);
        panel.add(carton4);
        panel.add(Jcarton4);
        panel.add(carton5);
        panel.add(Jcarton5);
        panel.add(status4);
        panel.add(comboBox_4);
        BtnActionP4(Index_4, table, Jisn, beginTime_2, endTime_2, Jcarton, Jcarton2, Jcarton3, Jcarton4,Jcarton5, comboBox_4);
        return panel;
    }

    private void ExportExcel(int a) {
        EXCEL_1721 excel = new EXCEL_1721();
        try {
            if (a == 1) {
                if (ExcelData1 == null) {
                    JOptionPane.showConfirmDialog(null, "當前無查詢結果！不可導出空EXCEL", "警告", JOptionPane.PLAIN_MESSAGE);
                } else {
                    excel.ExportNoResponse(excel.sheetName1, excel.titleName1, excel.fileName1, excel.columnNumber1, excel.columnWidth1, excel.tableTitleName1, ExcelData1);
                }
            }
            if (a == 2) {
                if (ExcelData2 == null) {
                    JOptionPane.showConfirmDialog(null, "當前無查詢結果！不可導出空EXCEL", "警告", JOptionPane.PLAIN_MESSAGE);
                } else {
                    excel.ExportNoResponse(excel.sheetName2, excel.titleName2, excel.fileName2, excel.columnNumber2, excel.columnWidth2, excel.tableTitleName2, ExcelData2);
                }
            }
            if (a == 3) {
                if (ExcelData3 == null) {
                    JOptionPane.showConfirmDialog(null, "當前無查詢結果！不可導出空EXCEL", "警告", JOptionPane.PLAIN_MESSAGE);
                } else {
                    excel.ExportNoResponse(excel.sheetName3, excel.titleName3, excel.fileName3, excel.columnNumber3, excel.columnWidth3, excel.tableTitleName3, ExcelData3);
                }
            }
            if (a == 4) {
                if (ExcelData4 == null) {
                    JOptionPane.showConfirmDialog(null, "當前無查詢結果！不可導出空EXCEL", "警告", JOptionPane.PLAIN_MESSAGE);
                } else {
                    excel.ExportNoResponse(excel.sheetName4, excel.titleName4, excel.fileName4, excel.columnNumber4, excel.columnWidth4, excel.tableTitleName4, ExcelData4);
                }
            }
            JOptionPane.showConfirmDialog(null, "導出成功！", "警告", JOptionPane.PLAIN_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private JLabel Mylabel(String name, int x, int y, int w, int h) {
        JLabel label = new JLabel();
        label.setText(name);
        label.setForeground(color);
        label.setHorizontalAlignment(JLabel.RIGHT);
        label.setFont(font);
        label.setBounds(x, y, w, h);
        return label;
    }

    private PDateTimeTextField MyDateField(int x, int y, int w, int h) {
        PDateTimeTextField pdtf = new PDateTimeTextField(uiVO, "PDTimeST", 100, true, true);
        pdtf.setBounds(x, y, w, h);
        return pdtf;
    }

    private JComboBox MyComboBox(int x, int y, int w, int h) {
        JComboBox comboBox = new JComboBox();
        comboBox.setFont(font);
        comboBox.setForeground(color);
        comboBox.setBounds(x, y, w, h);
        return comboBox;
    }

    private JTextField MyTextField(int x, int y, int w, int h) {
        JTextField textField = new JTextField(20);
        textField.setFont(font);
        textField.setBackground(Color.white);
        textField.setForeground(color);
        textField.setBounds(x, y, w, h);
        return textField;
    }

    private JButton MyButton(String name, int x, int y, int w, int h) {
        JButton btn = new JButton(name);
        btn.setLayout(null);
        btn.setFont(font);
        btn.setForeground(color);
        btn.setBounds(x, y, w, h);
        return btn;
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
        //table.setBounds(370,100,400,200);
        table.setEnabled(false);
        return table;
    }

    private Object[][] GetName(String housename, String timeO, String timeT) {
        Object[][] oo = null;
        Hashtable ht = null;
        try {
            if ("".equals(timeO) || "".equals(timeT)) {
                String sql = "select * from sfis_1721_in_out_warehouse where housename ='" + housename + "'";
                List<Hashtable> result = dh.getDataList(sql, DataSourceType._MultiCompanyCurrentDB);
                if (result != null) {
                    oo = new Object[result.size()][4];
                    for (int i = 0; i < result.size(); i++) {
                        oo[i][0] = result.get(i).get("BATCH_NO");
                        oo[i][1] = result.get(i).get("IN_OUT");
                        oo[i][2] = result.get(i).get("ISN");
                        oo[i][3] = result.get(i).get("USERNAME");

                    }
                } else {
                }

            } else {
                Vector arg = new Vector();
                arg.add(housename);
                arg.add(timeO);
                arg.add(timeT);
                BaseServletAgent bsa1 = new BaseServletAgent(uiVO);
                BaseVO bvo = new BaseVO(uiVO, this.getClass().toString(), CommandName.CallPLSQLCmd2, arg);
                bvo.setPackageName("AAA_1721_INDEX_NOTE");
                bvo.setFunctionName("GetName");
                ResultVO rvo = bsa1.doFunc(bvo);
                Vector re = rvo.getData();
                if (re.size() >= 2) {
                    oo = new Object[re.size() - 1][4];
                    for (int i = 0; i < re.size() - 1; i++) {
                        ht = (Hashtable) re.elementAt(i + 1);
                        oo[i][0] = CloneArray_ChangeStr.NulltoSpace(ht.get("BATCH_NO"));
                        oo[i][1] = CloneArray_ChangeStr.NulltoSpace(ht.get("IN_OUT"));
                        oo[i][2] = CloneArray_ChangeStr.NulltoSpace(ht.get("ISN"));
                        oo[i][3] = CloneArray_ChangeStr.NulltoSpace(ht.get("USERNAME"));

                    }
                } else {
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return oo;
    }

    private Object[][] DealObjectER(String housename, String timeO, String timeT) {
        Object[][] datas = GetName(housename, timeO, timeT);
        Object[][] ModelObject = null;
        if (datas != null) {
            ModelObject = new Object[datas.length][6];
            for (int i = 0; i < datas.length; i++) {
                if (datas[i][1].equals("0")) {
                    ModelObject[i][0] = datas[i][0];
                    ModelObject[i][1] = "入庫";
                    ModelObject[i][2] = datas[i][2];
                    ModelObject[i][3] = "";
                    ModelObject[i][4] = "";
                    ModelObject[i][5] = datas[i][3];
                }
                if (datas[i][1].equals("2")) {
                    ModelObject[i][0] = datas[i][0];
                    ModelObject[i][1] = "借給ORT";
                    ModelObject[i][2] = datas[i][2];
                    ModelObject[i][3] = "";
                    ModelObject[i][4] = "";
                    ModelObject[i][5] = datas[i][3];
                }
                if (datas[i][1].equals("1")) {
                    ModelObject[i][0] = datas[i][0];
                    ModelObject[i][1] = "";
                    ModelObject[i][2] = "";
                    ModelObject[i][3] = "出庫";
                    ModelObject[i][4] = datas[i][2];
                    ModelObject[i][5] = datas[i][3];
                }
                if (datas[i][1].equals("4")) {
                    ModelObject[i][0] = datas[i][0];
                    ModelObject[i][1] = "";
                    ModelObject[i][2] = "";
                    ModelObject[i][3] = "銷毀";
                    ModelObject[i][4] = datas[i][2];
                    ModelObject[i][5] = datas[i][3];
                }
                if (datas[i][1].equals("3")) {
                    if (housename.contains("資材")) {
                        ModelObject[i][0] = datas[i][0];
                        ModelObject[i][1] = "報廢除賬";
                        ModelObject[i][2] = datas[i][2];
                        ModelObject[i][3] = "";
                        ModelObject[i][4] = "";
                        ModelObject[i][5] = datas[i][3];
                    } else {
                        ModelObject[i][0] = datas[i][0];
                        ModelObject[i][1] = "";
                        ModelObject[i][2] = "";
                        ModelObject[i][3] = "除賬";
                        ModelObject[i][4] = datas[i][2];
                        ModelObject[i][5] = datas[i][3];
                    }
                }
            }
        }

        return ModelObject;
    }

    public void munuBtnAction(JButton btn, final JPanel panel1, final JPanel panel2, final JPanel panel3, final JPanel panel4) {
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel1.setVisible(true);
                panel2.setVisible(false);
                panel3.setVisible(false);
                panel4.setVisible(false);
            }
        });
    }

    public void OpenFile(JButton btn, final JTable table) {
        FilePath = new String();
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyTableModel mm = (MyTableModel) table.getModel();
                FileDialog fd = new FileDialog(getParentFrame(), "打開文件", FileDialog.LOAD);
                fd.setVisible(true);
                FilePath = fd.getDirectory() + fd.getFile();
                List<String> list = getIsnByExcel(FilePath);
                if (list.size() > 0) {
                    mm.Clearcontents();
                    for (String ll : list) {
                        Object[][] ob = getSingleIsn(ll);
                        for (Object[] b : ob) {
                            try {
                                mm.addRow(b);
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                } else {
                    JOptionPane.showConfirmDialog(null, "選擇文件不正確", "警告", JOptionPane.PLAIN_MESSAGE);
                }
            }
        });
    }

    private Object[][] getSingleIsn(String isn) {
        Object[][] oobb = null;
        try {
            Vector vector = new Vector();
            vector.add(isn);
            BaseServletAgent baseServletAgent = new BaseServletAgent(uiVO);
            BaseVO baseVO = new BaseVO(uiVO, this.getClass().toString(), CommandName.CallPLSQLCmd2, vector);
            baseVO.setPackageName("AAA_1721_INDEX_NOTE");
            baseVO.setFunctionName("getReportFormByIsn");
            ResultVO resultVO = baseServletAgent.doFunc(baseVO);
            Vector result = resultVO.getData();
            if (null == result) {
                JOptionPane.showConfirmDialog(null, "查無ISN記錄", "警告", JOptionPane.PLAIN_MESSAGE);
                return null;
            }
            Hashtable ht = null;
            if (result.size() > 1) {
                oobb = new Object[result.size() - 1][4];
                for (int i = 0; i < result.size() - 1; i++) {
                    ht = (Hashtable) result.elementAt(i + 1);
                    oobb[i][0] = CloneArray_ChangeStr.NulltoSpace(ht.get("ISN"));
                    oobb[i][1] = CloneArray_ChangeStr.NulltoSpace(ht.get("BATCH"));
                    oobb[i][2] = CloneArray_ChangeStr.NulltoSpace(ht.get("HOUSE"));
                    if ("0".equals((String) ht.get("STATUS"))) {
                        oobb[i][3] = "已入庫";
                    } else if ("1".equals((String) ht.get("STATUS"))) {
                        oobb[i][3] = "已出庫";
                    } else if ("3".equals((String) ht.get("STATUS"))) {
                        oobb[i][3] = "已除賬";
                    } else if ("4".equals((String) ht.get("STATUS"))) {
                        oobb[i][3] = "已銷毀";
                    } else {
                        oobb[i][3] = CloneArray_ChangeStr.NulltoSpace(ht.get("STATUS"));
                        ;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return oobb;
    }

    private List<String> getIsnByExcel(String filePath) {
        InputStream stream = null;
        try {
            stream = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Workbook workbook = null;
        try {
            workbook = Workbook.getWorkbook(stream);
        } catch (IOException e) {
            JOptionPane.showConfirmDialog(null, "文件類型異常！", "警告", JOptionPane.PLAIN_MESSAGE);
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
        Sheet sheet = workbook.getSheet(0);
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < sheet.getRows(); i++) {
            jxl.Cell cell = null;
            for (int j = 0; j < sheet.getColumns(); j++) {
                cell = sheet.getCell(j, i);
                list.add(cell.getContents());
            }
        }
        workbook.close();
        try {
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void AddItem(JComboBox comboBox) {
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
            for (int n = 1; n <= (result.size() - 1); n++) {
                row = (Hashtable) result.get(n);
                String name = CloneArray_ChangeStr.NulltoSpace(row.get("HNAME"));
                if (!name.contains("資材")) {
                    comboBox.addItem(name);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void BtnActionP1(JButton btn, final JTable table, final JComboBox comboBox, final PDateTimeTextField beginTime, final PDateTimeTextField endTime, final JTextField JSum) {
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String beginday = "";
                String endday = "";
                if ("".equals(endTime.getText()) || "".equals(beginTime.getText())) {
                } else {
                    beginday = beginTime.getText().substring(0, 10);
                    endday = endTime.getText().substring(0, 10);
                }
                String HouseName = comboBox.getSelectedItem().toString();
                System.out.println("---------" + beginday + "------" + endday);
                if (!beginday.equals("") && !endday.equals("")) {
                    String sum = GetBalance(HouseName);
                    JSum.setText(sum);
                    MyTableModel MTM = (MyTableModel) table.getModel();
                    MTM.Clearcontents();
                    Object[][] ob = DealObjectER(HouseName, beginday, endday);
                    if (ob == null) {
                        JOptionPane.showConfirmDialog(null, "查詢無數據！", "警告", JOptionPane.PLAIN_MESSAGE);
                        MTM.Clearcontents();
                    } else {
                        ExcelData1 = ob;
                        try {
                            MTM.refreshContents(ob);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                        MTM.fireTableDataChanged();
                    }

                } else {
                    String sum = GetBalance(HouseName);
                    JSum.setText(sum);
                    MyTableModel MTM = (MyTableModel) table.getModel();
                    MTM.Clearcontents();
                    Object[][] ob = DealObjectER(HouseName, beginday, endday);
                    if (ob != null) {
                        ExcelData1 = ob;
                        try {
                            MTM.refreshContents(ob);
                            MTM.fireTableDataChanged();
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    } else {
                        MTM.Clearcontents();
                        JOptionPane.showConfirmDialog(null, "查詢無數據！", "警告", JOptionPane.PLAIN_MESSAGE);
                    }

                }

            }
        });
    }

    public void BtnActionP2(JButton btn, final JTextField Jbatch, final JTextField Jhousename, final JTextField Jstatus, final JTextField Jnum, final JTable jTable) {
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String batchNo = "";
                String housename = "";

                if (!Jbatch.getText().toString().trim().equals("")) {
                    batchNo = Jbatch.getText().toString().trim();
                }
                if (!Jhousename.getText().toString().equals("")) {
                    housename = Jhousename.getText().toString().trim();
                }


                if (batchNo.equals("") && !housename.equals("")) {
                    if ("".equals(housename)) {
                        JOptionPane.showConfirmDialog(null, "線邊倉不可為空！", "警告", JOptionPane.PLAIN_MESSAGE);
                    } else if (!"".equals(housename)) {
                        Object[][] on = null;
                        MyTableModel MTM = (MyTableModel) jTable.getModel();
                        MTM.Clearcontents();
                        try {
                            String sql = "";
                            if (housename.length() <= 4) {
                                sql = "SELECT n.house,n.isn,n.kj,n.time,w.username FROM SFIS_1721_IN_OUT_WAREHOUSE w  JOIN SFIS_1721_NOTE n ON n.house = w.housename where n.house like '" + "%" + housename + "%" + "' order by time desc";
                            } else {
                                sql = "SELECT n.house,n.isn,n.kj,n.time,w.username FROM SFIS_1721_IN_OUT_WAREHOUSE w  JOIN SFIS_1721_NOTE n ON n.house = w.housename where n.house= '" + housename + "' order by time desc";
                            }

                            List<Hashtable> result = dh.getDataList(sql, DataSourceType._MultiCompanyCurrentDB);
                            if (result == null) {
                                JOptionPane.showConfirmDialog(null, "查無數據！", "警告", JOptionPane.PLAIN_MESSAGE);
                                return;
                            }
                            on = new Object[result.size()][5];
                            for (int i = 0; i < result.size(); i++) {
                                on[i][0] = result.get(i).get("ISN");
                                on[i][1] = result.get(i).get("KJ");
                                on[i][2] = result.get(i).get("TIME");
                                on[i][3] = result.get(i).get("USERNAME");
                                on[i][4] = result.get(i).get("HOUSE");
                            }
                            ExcelData2 = on;
                            MTM.refreshContents(on);
                            MTM.fireTableDataChanged();
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                } else {
                    try {
                        Vector vc = new Vector();
                        vc.add(batchNo);
                        BaseServletAgent bsa = new BaseServletAgent(uiVO);
                        BaseVO bv = new BaseVO(uiVO, this.getClass().toString(), CommandName.CallPLSQLCmd2, vc);
                        bv.setPackageName("AAA_1721_INDEX_NOTE");
                        bv.setFunctionName("GetBatchNum");
                        ResultVO rv = bsa.doFunc(bv);
                        Vector vct = rv.getData();
                        if (vct.size() == 2) {
                            Hashtable ht = (Hashtable) vct.elementAt(1);
                            String houseName = CloneArray_ChangeStr.NulltoSpace(ht.get("HOUSENAME"));
                            String status = CloneArray_ChangeStr.NulltoSpace(ht.get("IN_OUT"));
                            String sum = CloneArray_ChangeStr.NulltoSpace(ht.get("ISN"));
                            if (status.equals("3")) {
                                status = "除賬";
                            } else if (status.equals("0")) {
                                status = "入庫";
                            } else if (status.equals("1")) {
                                status = "出庫";
                            } else {
                                status = "銷毀";
                            }
                            Jhousename.setText(houseName);
                            Jstatus.setText(status);
                            Jnum.setText(sum);
                        } else {
                            JOptionPane.showConfirmDialog(null, "查詢批號有誤！", "警告", JOptionPane.PLAIN_MESSAGE);
                            return;
                        }
                    } catch (Exception f) {
                        f.printStackTrace();
                        return;
                    }
                    Object[][] on = null;
                    Hashtable ht1 = null;
                    MyTableModel MTM = (MyTableModel) jTable.getModel();
                    MTM.Clearcontents();
                    try {
                        Vector vc1 = new Vector();
                        vc1.add(batchNo);
                        BaseServletAgent bsa1 = new BaseServletAgent(uiVO);
                        BaseVO bv1 = new BaseVO(uiVO, this.getClass().toString(), CommandName.CallPLSQLCmd2, vc1);
                        bv1.setPackageName("AAA_1721_INDEX_NOTE");
                        bv1.setFunctionName("GetBatchISN");
                        ResultVO rv1 = bsa1.doFunc(bv1);
                        Vector vct1 = rv1.getData();
                        if (vct1.size() > 1) {
                            on = new Object[vct1.size() - 1][5];
                            for (int i = 0; i < vct1.size() - 1; i++) {
                                ht1 = (Hashtable) vct1.elementAt(i + 1);
                                on[i][0] = CloneArray_ChangeStr.NulltoSpace(ht1.get("ISN"));
                                on[i][1] = CloneArray_ChangeStr.NulltoSpace(ht1.get("KJ"));
                                on[i][2] = CloneArray_ChangeStr.NulltoSpace(ht1.get("TIME"));
                                on[i][3] = CloneArray_ChangeStr.NulltoSpace(ht1.get("USERNAME"));
                                on[i][4] = CloneArray_ChangeStr.NulltoSpace(ht1.get("HOUSE"));

                            }
                            MTM.refreshContents(on);
                            ExcelData2 = on;
                            MTM.fireTableDataChanged();
                        } else {
                            JOptionPane.showConfirmDialog(null, "查無數據！", "警告", JOptionPane.PLAIN_MESSAGE);
                            return;
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    public void BtnActionP3(JButton btn, final JTable table, final JTextField textField) {
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyTableModel mm = (MyTableModel) table.getModel();
                String ISN = textField.getText().toString().trim();
                if (ISN.equals("")) {
                    JOptionPane.showConfirmDialog(null, "輸入ISN不能為空！", "警告", JOptionPane.PLAIN_MESSAGE);
                } else {
                    Object[][] objects = getSingleIsn(ISN);
                    if (objects == null) {
                        JOptionPane.showConfirmDialog(null, "該ISN不存在記錄", "警告", JOptionPane.PLAIN_MESSAGE);
                    } else {
                        mm.Clearcontents();
                        try {
                            ExcelData3 = objects;
                            mm.refreshContents(objects);
                            mm.fireTableDataChanged();
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    public void BtnActionP4(JButton btn, final JTable table, final JTextField Jisn, final PDateTimeTextField beginTime_2, final PDateTimeTextField endTime_2, final JTextField Jcarton, final JTextField Jcarton2, final JTextField Jcarton3, final JTextField Jcarton4,final JTextField Jcarton5, final JComboBox ComboBox_4) {
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyTableModel mm = (MyTableModel) table.getModel();
                System.out.println(beginTime_2.getText() + "------------------------------------");
                System.out.println(endTime_2.getText() + "---------------------------------");
                String beginday = "";
                String endday = "";
                String code1 = "";
                String code2 = "";
                String code3 = "";
                String storageName = "";
                String code4 = "";
                String status4 = "";
                if (!"".equals(beginTime_2.getText()) && beginTime_2.getText() != null) {
                    beginday = beginTime_2.getText().substring(0, 10);
                }
                if (!"".equals(endTime_2.getText()) && endTime_2.getText() != null) {
                    endday = endTime_2.getText().substring(0, 10);
                }
                if (!"".equals(Jcarton2) || null != Jcarton2.getText()) {
                    code1 = Jcarton2.getText().toString();
                }
                if (!"".equals(Jcarton3.getText().toString()) || null != Jcarton3.getText()) {
                    code2 = Jcarton3.getText().toString();
                }
                if (!"".equals(Jcarton4.getText().toString()) || null != Jcarton4.getText()) {
                    code3 = Jcarton4.getText().toString();
                }
                if (!"".equals(Jisn.getText().toString()) || null != Jisn.getText().toString()) {
                    storageName = Jisn.getText().toString();
                }
                if (!"".equals(Jcarton.getText().toString()) || null != Jcarton.getText().toString()) {
                    code4 = Jcarton.getText().toString();
                }
                if (!ComboBox_4.getSelectedItem().toString().equals("")) {
                    status4 = ComboBox_4.getSelectedItem().toString();
                }

                System.out.println("---------" + beginday + "------" + endday);

                Object[][] objects = null;
                objects=GetStroage(storageName, beginday, endday, code4, code1, code2, code3, status4);
                ExcelData4 = objects;
                num4 = objects.length;
                Jcarton5.setText(String.valueOf(num4));
                try {
                    mm.refreshContents(objects);
                    mm.fireTableDataChanged();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }


            }
        });
    }

    public String GetBalance(String HouseName) {
        String sum = null;
        Vector arg = new Vector();
        arg.add(HouseName);
        BaseServletAgent bsa1 = new BaseServletAgent(uiVO);
        BaseVO bvo = new BaseVO(uiVO, this.getClass().toString(), CommandName.CallPLSQLCmd2, arg);
        bvo.setPackageName("AAA_1721_INDEX_NOTE");
        bvo.setFunctionName("GetBalance");
        ResultVO rvo = null;
        try {
            rvo = bsa1.doFunc(bvo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Vector re = rvo.getData();
        if (re.size() == 2) {
            Hashtable ht = (Hashtable) re.elementAt(1);
            sum = CloneArray_ChangeStr.NulltoSpace(ht.get("NSUM"));
        } else {
            JOptionPane.showConfirmDialog(null, "查無數據！", "警告", JOptionPane.PLAIN_MESSAGE);
            sum = "";
        }
        return sum;
    }

    public Object[][] GetStroage(String storageName, String beginTime, String endTime, String Jcarton, String code2, String code3, String code4, String status) {
        Object[][] ob = null;
        String sql = "";
        String batchNo = "";
        String ISN = "";
        try {
            //通過成品碼找到ISN,ISN找到批號
            if (!code2.equals("") && code3.equals("") && code4.equals("")) {
                String sql2 = "select ISN from ISNINFO　where SSN='" + code2 + "'";
                Hashtable a = null;
                a = dh.getDataOne(sql2, DataSourceType._SFIS_KAIJIA_STD);
                if (a == null) {
                    JOptionPane.showConfirmDialog(null, "成品碼有誤,請核對！", "警告", JOptionPane.PLAIN_MESSAGE);
                } else {
                    ISN = (String) a.get("ISN");
                    String sql3 = "select BATCH from SFIS_1721_NOTE where ISN='" + ISN + "'order by TIME desc";
                    List<Hashtable> a2 = null;
                    a2 = dh.getDataList(sql3, DataSourceType._MultiCompanyCurrentDB);
                    if (a == null) {
                        JOptionPane.showConfirmDialog(null, "成品碼有誤,請核對！", "警告", JOptionPane.PLAIN_MESSAGE);
                    } else {
                        batchNo = (String) a2.get(0).get("BATCH");
                    }
                }
            }
            //通過ISN找到批號
            if (code2.equals("") && !code3.equals("") && code4.equals("")) {
                ISN = code3;
                String sql2 = "select BATCH from SFIS_1721_NOTE where ISN='" + code3 + "'   order by TIME desc";
                List<Hashtable> a = null;
                a = dh.getDataList(sql2, DataSourceType._MultiCompanyCurrentDB);
                if (a == null) {
                    JOptionPane.showConfirmDialog(null, "成品碼有誤,請核對！", "警告", JOptionPane.PLAIN_MESSAGE);
                } else {
                    batchNo = (String) a.get(0).get("BATCH");
                }
            }
            //通過簡明碼找到ISN，ISN找到批號
            if (code2.equals("") && code3.equals("") && !code4.equals("")) {
                String sql2 = "select ISN from tp.ISNINFO　where CSSN='" + code4 + "'";
                Hashtable a = null;
                a = dh.getDataOne(sql2, DataSourceType._SFIS_KAIJIA_STD);
                if (a == null) {
                    JOptionPane.showConfirmDialog(null, "成品碼有誤,請核對！", "警告", JOptionPane.PLAIN_MESSAGE);
                } else {
                    ISN = (String) a.get("ISN");
                    String sql3 = "select BATCH from SFIS_1721_NOTE where ISN='" + ISN + "'order by TIME desc";
                    List<Hashtable> a2 = null;
                    a2 = dh.getDataList(sql3, DataSourceType._MultiCompanyCurrentDB);
                    if (a == null) {
                        JOptionPane.showConfirmDialog(null, "成品碼有誤,請核對！", "警告", JOptionPane.PLAIN_MESSAGE);
                    } else {
                        batchNo = (String) a2.get(0).get("BATCH");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String status_2 = "";
        System.out.println(status);
        if (status.equals("在庫")) {
            status_2 = "3";
        } else {
            status_2 = "4";
        }
        if (!status.equals("") &&beginTime.equals("")&&endTime.equals("")) {
            System.out.println(status);
            String sql3="";
            if(status_2.equals("3")){
                 sql3="select a.isn,a.time,a.batch,w.username,w.housename" +
                        ",sw.storage_name,sw.carton_no " +
                        "from SFIS_1721_note a " +
                        "join SFIS_1721_IN_OUT_WAREHOUSE w on w.batch_no=a.batch " +
                        "left join SFIS_1721_STORAGE_WAREHOUSE sw on sw.batch_no=w.batch_no " +
                        "where  a.isn in(select isn from (select distinct n.isn  from SFIS_1721_STORAGE_WAREHOUSE sw " +
                        "join SFIS_1721_NOTE n on sw.batch_no=n.batch  group by n.isn )a " +
                        "union select isn from (select isn from SFIS_1721_DETAIL_INFO where status='3')b) and not exists " +
                        "(select 1 from SFIS_1721_note where isn=a.isn and time>a.time ) " +
                        "group by a.isn,a.time,a.batch,w.username,w.housename,sw.storage_name,sw.carton_no ";
            }else{
                 sql3 = "select n.batch,w.username,w.tim,n.isn,w.housename from SFIS_1721_IN_OUT_WAREHOUSE w join SFIS_1721_NOTE n on w.batch_no=n.batch  where w.in_out='" + status_2 + "'";
            }
            DataHandler dh = new DataHandler(uiVO);
            try {
                List<Hashtable> ht = null;
                ht = dh.getDataList(sql3, DataSourceType._MultiCompanyCurrentDB);
                ob = new Object[ht.size()][7];
                if (ht == null) {
                    JOptionPane.showConfirmDialog(null, "查無數據！", "警告", JOptionPane.PLAIN_MESSAGE);
                } else {
                    for (int i = 0; i < ht.size(); i++) {
                        System.out.println("------------------------------------------------------------------------------------");
                        if(ht.get(i).get("STORAGE_NAME")==null||ht.get(i).get("STORAGE_NAME").toString().equals("")){
                            ob[i][0] = " ";
                        }else{
                            ob[i][0] = ht.get(i).get("STORAGE_NAME");
                        }
                        ob[i][1] = (String) ht.get(i).get("BATCH");
                        if(ht.get(i).get("CARTON_NO")==null||ht.get(i).get("CARTON_NO").toString().equals("")){
                            ob[i][2] = " ";
                        }else{
                            ob[i][2] = ht.get(i).get("CARTON_NO");
                        }
                        if(ht.get(i).get("USERNAME")==null||ht.get(i).get("USERNAME").toString().equals("")){
                            ob[i][3] ="";
                        }else{
                            ob[i][3] = (String) ht.get(i).get("USERNAME");
                        }
                        ob[i][4] = ht.get(i).get("TIME");
                        ob[i][5] = ht.get(i).get("ISN");
                        ob[i][6] = ht.get(i).get("HOUSENAME");
                        System.out.println("------------------------------------------------------------------------------------");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ob;
        } else if (!status.equals("") &&!beginTime.equals("")&&!endTime.equals("")) {
            String sql3 = "select n.batch,w.username,w.tim,n.isn,w.housename,sw.carton_no,sw.storage_name from SFIS_1721_IN_OUT_WAREHOUSE w join SFIS_1721_NOTE n on w.batch_no=n.batch join SFIS_1721_STORAGE_WAREHOUSE sw on sw.batch_no=w.batch_no  where w.in_out='" + status_2 + "'and   TO_DATE(w.tim,'YYYY-MM-DD HH24:mi:ss') between  TO_DATE('"+beginTime+"','YYYY-MM-DD') and TO_DATE('"+endTime+"','YYYY-MM-DD HH24:mi:ss')";
            DataHandler dh = new DataHandler(uiVO);
            try {
                List<Hashtable> ht = null;
                ht = dh.getDataList(sql3, DataSourceType._MultiCompanyCurrentDB);
                ob = new Object[ht.size()][7];
                if (ht == null) {
                    JOptionPane.showConfirmDialog(null, "查無數據！", "警告", JOptionPane.PLAIN_MESSAGE);
                } else {
                    for (int i = 0; i < ht.size(); i++) {
                        ob[i][0] = (String) ht.get(i).get("STORAGE_NAME");;
                        ob[i][1] = (String) ht.get(i).get("BATCH");
                        ob[i][2] = (String) ht.get(i).get("CARTON_NO");
                        ob[i][3] = (String) ht.get(i).get("USERNAME");
                        ob[i][4] = ht.get(i).get("TIM");
                        ob[i][5] = ht.get(i).get("ISN");
                        ob[i][6] = ht.get(i).get("HOUSENAME");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ob;
        }

        if (!batchNo.equals("")) {
            String sql3 = "select n.isn,n.house,w.* from sfis_1721_storage_warehouse w join Sfis_1721_note n on w.batch_no=n.batch  where n.isn='" + ISN + "' and w.batch_no='" + batchNo + "'";
            DataHandler dh = new DataHandler(uiVO);
            try {
                List<Hashtable> ht = null;
                ht = dh.getDataList(sql3, DataSourceType._MultiCompanyCurrentDB);
                ob = new Object[ht.size()][7];
                if (ht == null) {
                    JOptionPane.showConfirmDialog(null, "查無數據！", "警告", JOptionPane.PLAIN_MESSAGE);
                } else {
                    for (int i = 0; i < ht.size(); i++) {
                        ob[i][0] = (String) ht.get(i).get("STORAGE_NAME");
                        ob[i][1] = (String) ht.get(i).get("BATCH_NO");
                        if (ht.get(i).get("CARTON_NO") == null || ht.get(i).get("CARTON_NO") == "") {
                            ob[i][2] = "";
                        } else {
                            ob[i][2] = (String) ht.get(i).get("CARTON_NO");
                        }
                        ob[i][3] = (String) ht.get(i).get("EMPLOYEE");
                        ob[i][4] = ht.get(i).get("SYSTEM_TIME");
                        ob[i][5] = ht.get(i).get("ISN");
                        ob[i][6] = ht.get(i).get("HOUSE");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ob;
        } else if (status.equals("")) {
            if ("".equals(storageName) || null == storageName) {
                if (!"".equals(beginTime) && !"".equals(endTime)) {
                    sql = "select n.isn,n.house,w.* from sfis_1721_storage_warehouse w join Sfis_1721_note n on w.batch_no=n.batch  where w.carton_no='" + Jcarton + "'and  w.system_time >= TO_DATE ('" + beginTime + "', 'yyyy-mm-dd') and w.system_time <=TO_DATE ('" + endTime + "', 'yyyy-mm-dd')";
                } else {
                    sql = "select n.isn,n.house,w.* from sfis_1721_storage_warehouse w join Sfis_1721_note n on w.batch_no=n.batch  where w.carton_no='" + Jcarton + "'";
                }
            } else if ("".equals(Jcarton) || null == Jcarton) {
                if (!"".equals(beginTime) && !"".equals(endTime)) {
                    sql = "select n.isn,n.house,w.*  from sfis_1721_storage_warehouse w join Sfis_1721_note n on w.batch_no=n.batch  where  w.storage_name='" + storageName + "' and w.system_time >= TO_DATE ('" + beginTime + "', 'yyyy-mm-dd') and w.system_time <=TO_DATE ('" + endTime + "', 'yyyy-mm-dd')";
                } else {
                    sql = "select n.isn,n.house,w.* from sfis_1721_storage_warehouse w join Sfis_1721_note n on w.batch_no=n.batch  where  w.storage_name='" + storageName + "'";

                }
            } else {
                if (!"".equals(beginTime) && !"".equals(endTime)) {
                    sql = "select n.isn,n.house,w.*  from sfis_1721_storage_warehouse w join Sfis_1721_note n on w.batch_no=n.batch  where w.carton_no='" + Jcarton + "'and w.storage_name='" + storageName + "' and w.system_time >= TO_DATE ('" + beginTime + "', 'yyyy-mm-dd') and w.system_time <=TO_DATE ('" + endTime + "', 'yyyy-mm-dd')";
                } else {
                    sql = "select n.isn,n.house,w.*  from sfis_1721_storage_warehouse w join Sfis_1721_note n on w.batch_no=n.batch  where w.carton_no='" + Jcarton + "'and w.storage_name='" + storageName + "'";
                }
            }

            DataHandler dh = new DataHandler(uiVO);
            try {
                List<Hashtable> ht = dh.getDataList(sql, DataSourceType._MultiCompanyCurrentDB);
                ob = new Object[ht.size()][7];
                if (ht == null) {
                    JOptionPane.showConfirmDialog(null, "查無數據！", "警告", JOptionPane.PLAIN_MESSAGE);
                } else {
                    for (int i = 0; i < ht.size(); i++) {
                        ob[i][0] = (String) ht.get(i).get("STORAGE_NAME");
                        ob[i][1] = (String) ht.get(i).get("BATCH_NO");
                        if (ht.get(i).get("CARTON_NO") == null || ht.get(i).get("CARTON_NO") == "") {
                            ob[i][2] = "";
                        } else {
                            ob[i][2] = (String) ht.get(i).get("CARTON_NO");
                        }
                        ob[i][3] = (String) ht.get(i).get("EMPLOYEE");
                        ob[i][4] = ht.get(i).get("SYSTEM_TIME");
                        ob[i][5] = ht.get(i).get("ISN");
                        ob[i][6] = ht.get(i).get("HOUSE");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return ob;
        }
        JOptionPane.showConfirmDialog(null, "請輸入查詢條件！", "警告", JOptionPane.PLAIN_MESSAGE);
        return null;
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
