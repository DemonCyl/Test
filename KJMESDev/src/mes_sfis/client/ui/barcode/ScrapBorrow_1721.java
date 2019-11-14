package mes_sfis.client.ui.barcode;

import base.client.ui.BasePanel;
import base.client.util.BaseServletAgent;
import base.client.util.CloneArray_ChangeStr;
import base.client.util.component.P_Component_Meta;
import base.enums.CommandName;
import base.vo.BaseVO;
import base.vo.ResultVO;
import base.vo.UI_InitVO;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import mes_sfis.client.model.service.Borrow_LenderService;
import mes_sfis.client.util.DataHandler;
import mes_sfis.client.util.SoundUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * Created by SRX_zhu on 2018/5/21.
 */
public class ScrapBorrow_1721 extends BasePanel {
    public static final String VERSION = "$Name:  $, $Id: ScrapBorrow_1721.java,v 1.10 2018/04/16 09:28:13 Lucy6_Lu Exp $";
    public static final String COPYRIGHT = "Copyright (c) 2008 PEGATRON Inc. All Rights Reserved.";
    private Color color = new Color(106, 106, 255);
    private Font font = new Font("宋體", Font.BOLD, 15);
    private MyTableModel TM;
    private com.lowagie.text.Font Pfont;
    String[] ss = new String[]{"鎧嘉料號", "ISN", "批號"};
    Object[][][] ooo = {new Object[][]{{"", "", ""}}};
    JTable table = MyTable(ooo[0], ss);
    Object[][] tableData = null;
    Object[][] tableData1 = null;
    Borrow_LenderService borrow;
    DataHandler dh;
    int DBNum = 0;
    JPanel panel = new JPanel();
    JScrollPane jsp = new JScrollPane(table);
    JFrame frame = new JFrame("線邊倉借出");
    JLabel BatchLabel = new JLabel("BATCH:");
    JLabel ISNLabel = new JLabel("ISN:");
    JLabel JobNumLabel = new JLabel("工號:");
    JLabel BorrowLabel = new JLabel("借用說明:");
    JLabel QueryLabel = new JLabel("借出廢料:");
    JLabel QueryNumLabel = new JLabel("0");
    JLabel QueryLabel2 = new JLabel("歸還廢料:");
    JButton ConfirmBtn = new JButton("確認借出");
    JButton ConfirmBtn2 = new JButton("確認歸還");
    JLabel returnLabel = new JLabel("請刷批號歸還");
    JTextField returnText = new JTextField();
    JButton ClearBtn = new JButton("重置");
    JTextField BatchText = new JTextField();
    JTextField ISNText = new JTextField();
    JTextField JobNumText = new JTextField();
    JLabel JobNumDescLabel = new JLabel("(必填！只能由資材用戶借出！)");
    JTextField BorrowText = new JTextField();
    int QueryNum = 0;
    List<HashMap> simpleList = new ArrayList<HashMap>();
    String BatchNo = "";
    String houseName = "";
    String inOutBatch = "";
    List ISNList = new ArrayList();

    public ScrapBorrow_1721(UI_InitVO uiVO) {
        super(uiVO);
        borrow = new Borrow_LenderService(uiVO);
        dh = new DataHandler(uiVO);

        init();
    }

    public void init() {

        jsp.getViewport().setBackground(Color.white);
        this.add(panel);
        this.add(jsp);
        this.add(ISNLabel);
        this.add(ISNText);
        this.add(BatchLabel);
        this.add(BatchText);
        this.add(JobNumLabel);
        this.add(JobNumText);
        this.add(BorrowText);
        this.add(BorrowLabel);
        this.add(QueryLabel);
        this.add(QueryNumLabel);
        this.add(QueryLabel2);
        this.add(returnLabel);
        this.add(returnText);
        this.add(ConfirmBtn);
        this.add(ConfirmBtn2);
        this.add(ClearBtn);
        this.add(JobNumDescLabel);

        //設置寬高
        //設置界面可見


        ClearBtn.setBounds(150, 210, 80, 25);
        BatchLabel.setBounds(50, 80, 80, 25);
        ISNLabel.setBounds(50, 50, 80, 25);
        ISNText.setBounds(100, 50, 165, 25);
        jsp.setBounds(50, 400, 500, 200);
        BatchText.setBounds(100, 80, 165, 25);
        JobNumLabel.setBounds(50, 110, 80, 25);
        JobNumText.setBounds(100, 110, 165, 25);
        BorrowLabel.setBounds(50, 140, 80, 25);
        BorrowText.setBounds(50, 160, 400, 50);
        QueryLabel.setBounds(50, 370, 80, 25);
        QueryNumLabel.setBounds(140, 370, 80, 25);
        QueryLabel2.setBounds(50, 270, 80, 25);
        returnLabel.setBounds(50, 300, 80, 25);
        returnText.setBounds(140, 300, 160, 25);
        ConfirmBtn.setBounds(50, 210, 100, 25);
        ConfirmBtn2.setBounds(330, 300, 120, 25);
        JobNumDescLabel.setBounds(270, 110, 300, 25);
        //添加監聽
        ISNText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ISNText.getText().toString().equals("")) {
                    JOptionPane.showConfirmDialog(null, "請輸入ISN", "", JOptionPane.PLAIN_MESSAGE);
                } else {
                    try {
                        Hashtable ht = new Hashtable();
                        ht = borrow.CheckISN(ISNText.getText().trim().toString(),"1");
                        if (ht == null) {
                            JOptionPane.showConfirmDialog(null, "查無此ISN", "", JOptionPane.PLAIN_MESSAGE);
                            return;
                        }
                        HashMap simpleMap = new HashMap();
                        simpleMap.put("KJ", ht.get("KJ"));
                        simpleMap.put("ISN", ht.get("ISN"));
                        simpleMap.put("BATCH", ht.get("BATCH"));
                        simpleMap.put("HOUSE", ht.get("HOUSE"));
                        houseName = ht.get("HOUSE").toString();
                        if (ht.get("STATUS").toString().equals("3")) {
                            for(int i=0;i<ISNList.size();i++){
                                if(ISNList.get(i).toString().equals(ht.get("ISN").toString())){
                                    JOptionPane.showConfirmDialog(null, "請勿重複刷入廢料！！", "", JOptionPane.PLAIN_MESSAGE);
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
                            MTM.refreshContents(tableData1);
                            MTM.fireTableDataChanged();
                            ISNText.setText("");
                            QueryNum++;
                            SoundUtil.PlayOk();
                            QueryNumLabel.setText(String.valueOf(QueryNum));
                        } else {
                            JOptionPane.showConfirmDialog(null, "該產品不是已除賬的廢料，請核實", "", JOptionPane.PLAIN_MESSAGE);
                        }

                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }


                }
            }
        });

        //添加批號
        BatchText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (BatchText.getText().toString().equals("")) {
                    JOptionPane.showConfirmDialog(null, "請輸入BATCH", "", JOptionPane.PLAIN_MESSAGE);
                } else {
                    List<Hashtable> isnList = null;
                    try {
                        isnList = borrow.CheckBATCH(BatchText.getText().toString());
                        if (isnList == null) {
                            JOptionPane.showConfirmDialog(null, "該批號不存在，請核實！", "", JOptionPane.PLAIN_MESSAGE);
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
                        if (!isnList.get(i).get("STATUS").equals("3")) {
                            JOptionPane.showConfirmDialog(null, "該批號中有產品尚未報廢，請核實", "", JOptionPane.PLAIN_MESSAGE);
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

        //添加借用說明
        BorrowText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Borrow = "";
                if (BorrowText.getText().toString().equals("")) {
                    JOptionPane.showConfirmDialog(null, "請輸入借用說明", "", JOptionPane.PLAIN_MESSAGE);
                }
            }
        });

        //確認歸還
        ConfirmBtn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String emp_oid = JobNumText.getText().toString().trim();
                    String empOk = "";
                    if (emp_oid.equals("") || emp_oid == null) {
                        JOptionPane.showConfirmDialog(null, "請輸入工號", "", JOptionPane.PLAIN_MESSAGE);
                        return;
                    } else {
                        empOk = borrow.CheckEmpOid(JobNumText.getText().toString().trim());
                        if (empOk == null) {
                            JobNumText.setText("");
                            JOptionPane.showConfirmDialog(null, "查無此用戶，請確認！", "", JOptionPane.PLAIN_MESSAGE);
                            return;
                        }
                        if (!empOk.equals("warehouse")) {
                            JobNumText.setText("");
                            JOptionPane.showConfirmDialog(null, "非資材用戶無權外借！！！", "", JOptionPane.PLAIN_MESSAGE);
                            return;
                        }
                    }
                    List<Hashtable> list = new ArrayList<Hashtable>();
                    list = borrow.CheckBATCH(returnText.getText().toString());
                    if(list==null){
                        JOptionPane.showConfirmDialog(null, "查無此批號！！！", "", JOptionPane.PLAIN_MESSAGE);
                        return;
                    }
                    for (int i = 0; i < list.size(); i++) {
                        HashMap dataMap = new HashMap();
                        dataMap.put("KJ", list.get(i).get("KJ").toString());
                        dataMap.put("ISN", list.get(i).get("ISN").toString());
                        dataMap.put("BATCH", list.get(i).get("BATCH").toString());
                        dataMap.put("HOUSE", list.get(i).get("HOUSE").toString());
                        simpleList.add(dataMap);
                    }
                    String house = borrow.CheckReturnBatch(returnText.getText().toString());
                    if (house == null) {
                        JOptionPane.showConfirmDialog(null, "查無此批號，請核對！", "", JOptionPane.PLAIN_MESSAGE);
                        return;
                    }
                    borrow.ChangeISNStatus(simpleList, "3");
                    borrow.ChangeWAREHOUSE(house, simpleList.size(), "0");
                    borrow.ChangeInOutWAREHOUSE(returnText.getText().toString(), 0, "", house, uiVO.getLogin_id(), "0");
                    borrow.InsertNote(simpleList, "0",returnText.getText().toString());
                    JOptionPane.showConfirmDialog(null, "回收成功！", "", JOptionPane.PLAIN_MESSAGE);
                } catch (Exception e1) {
                    JOptionPane.showConfirmDialog(null, "回收失敗！請聯繫MES課核對！", "", JOptionPane.PLAIN_MESSAGE);
                    e1.printStackTrace();
                }
            }
        });

        //確認借用
        ConfirmBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String emp_oid = JobNumText.getText().toString().trim();
                String desc = BorrowText.getText().toString().trim();
                String empOk = "";
                if (emp_oid.equals("") || emp_oid == null) {
                    JOptionPane.showConfirmDialog(null, "請輸入工號", "", JOptionPane.PLAIN_MESSAGE);
                    return;
                } else {
                    empOk = borrow.CheckEmpOid(JobNumText.getText().toString().trim());
                    if (empOk == null) {
                        JobNumText.setText("");
                        JOptionPane.showConfirmDialog(null, "查無此用戶，請確認！", "", JOptionPane.PLAIN_MESSAGE);
                        return;
                    }
                    if (!empOk.equals("warehouse")) {
                        JobNumText.setText("");
                        JOptionPane.showConfirmDialog(null, "非資材用戶無權外借！！！", "", JOptionPane.PLAIN_MESSAGE);
                        return;
                    }
                }
                if (tableData == null && tableData1 == null) {
                    JOptionPane.showConfirmDialog(null, "請選擇要借出的報廢產品！", "", JOptionPane.PLAIN_MESSAGE);
                }
                try {
                    Date d = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    String dateNowStr = sdf.format(d).replaceAll(":","_");
                    inOutBatch = BatchNumber();
                    String path = "d://1721BatchNo//" + houseName + BatchNo +dateNowStr+".pdf";
                    String status = "報廢倉借出";
                    Map<Object, Integer> KJMP = new HashMap<Object, Integer>();
                    KJMP.put(simpleList.get(0).get("KJ"), simpleList.size());
                    PDFFactory(path, houseName, dateNowStr, status, uiVO.getLogin_id(), JobNumText.getText().toString(), inOutBatch, Integer.toString(simpleList.size()), KJMP);
                    borrow.ChangeWAREHOUSE(houseName, simpleList.size(), "1");
                    borrow.InsertNote(simpleList, "1",inOutBatch);
                    borrow.ChangeISNStatus(simpleList, "1");
                    borrow.ChangeInOutWAREHOUSE(inOutBatch, simpleList.size(), BorrowText.getText().toString(), houseName, uiVO.getLogin_id(), "1");
                    JOptionPane.showConfirmDialog(null, "成功借出，請通知借用部門刷入！", "", JOptionPane.PLAIN_MESSAGE);
                } catch (Exception e1) {
                    JOptionPane.showConfirmDialog(null, "借出失敗！請聯繫MIS課確認！", "", JOptionPane.PLAIN_MESSAGE);
                    e1.printStackTrace();
                }


            }
        });

        //添加重製按鈕
        ClearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ISNText.setText("");
                BatchText.setText("");
                JobNumText.setText("");
                BorrowText.setText("");
                inOutBatch = "";
                BatchNo = "";
                houseName = "";
                tableData = null;
                tableData1 = null;
                MyTableModel MTM = (MyTableModel) table.getModel();
                MTM.Clearcontents();
                simpleList.clear();
                ISNList.clear();
                QueryNum=0;
                QueryNumLabel.setText(String.valueOf(QueryNum));
                try {
                    MTM.refreshContents(tableData);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                MTM.fireTableDataChanged();
                JOptionPane.showConfirmDialog(null, "清除成功", "", JOptionPane.PLAIN_MESSAGE);
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

    //產生批號
    public String BatchNumber() {
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

    //更新當天批號的筆數
    public void UpdateBNNumber() {
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

    //生成PDF
    public void PDFFactory(String Path, String HouseName, String Day, String Status, String Name, String Desc, String inOutBatch, String n, Map<Object, Integer> KJMP) throws IOException, DocumentException {
        System.out.println("開始生產PDF");
        System.out.println(Path);
        System.out.println(HouseName);
        System.out.println(Day);
        System.out.println(Status);
        System.out.println(Name);
        System.out.println(Desc);
        System.out.println(inOutBatch);
        System.out.println(n);
        System.out.println(KJMP);

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
        PdfPCell cell6_6 = new PdfPCell(createBarCode(writer, inOutBatch, 25));
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
        System.out.println("生產PDF完成");
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
