package mes_sfis.client.ui.barcode;

import base.client.ui.BasePanel;
import base.client.util.component.PDateTimeTextField;
import base.client.util.component.P_Component_Meta;
import base.enums.DataSourceType;
import base.vo.UI_InitVO;
import com.lowagie.text.DocumentException;
import mes_sfis.client.model.bean.L_C_1718Bean;
import mes_sfis.client.pdf.PDFCreater1718LC;
import mes_sfis.client.util.DataHandler;
import mes_sfis.client.util.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.logging.log4j.util.Strings.isBlank;

public class Barcode_1901_LC_PEGA extends BasePanel {
    public static final String VERSION = "$Name:  $, $Id: Barcode_1901_LC_PEGA.java,v 1 2017/12/12 03:00:17 SRX_zhu Exp $";
    public static final String COPYRIGHT = "Copyright (c) 2008 PEGATRON Inc. All Rights Reserved.";
    JPanel jp = null;
    JLabel jl1 = new JLabel("請輸入OEM PN");
    JLabel jl2 = new JLabel("請輸入打印張數");
    JLabel jl3 = new JLabel("(每張標籤包含7個L/C標籤)");
    JLabel jl4 = new JLabel("請選擇標籤打印時間");
    JLabel lineLabel = new JLabel("線別");
    JLabel shiftLabel = new JLabel("班別");
    JComboBox shiftCheckbok = new JComboBox();
    JComboBox lineCombox = new JComboBox();


    PDateTimeTextField selectTime = MyDateField(230, 200,200, 30);
    JComboBox jc1 = new JComboBox();
    JTextField jt2 = new JTextField();
    JButton jb1 = new JButton("確認生成");
    L_C_1718Bean pc = new L_C_1718Bean();

    public Barcode_1901_LC_PEGA(UI_InitVO uiVO) {
        super(uiVO);
        pc.setDh(new DataHandler(uiVO));
        init();
    }


    public void init() {
        System.out.println("===========123");
        checkN();
        jp = new JPanel();
        this.add(jl1);
        this.add(jl2);
        this.add(jl3);
        this.add(jl4);
        this.add(lineLabel);
        this.add(shiftLabel);
        this.add(jt2);
        this.add(jb1);
        this.add(jc1);
        this.add(selectTime);
        this.add(lineCombox);
        this.add(shiftCheckbok);
        this.add(jp);
        jl1.setBounds(100, 100, 120, 30);
        jl2.setBounds(100, 150, 120, 30);
        jl3.setBounds(80, 170, 150, 30);
        jl4.setBounds(100, 200, 150, 30);
        lineLabel.setBounds(100, 250, 150, 30);
        shiftLabel.setBounds(100, 300, 150, 30);
        lineCombox.setBounds(220, 250, 100, 25);
        shiftCheckbok.setBounds(220, 300, 100, 25);
        Item item = new Item("1","白班");
        shiftCheckbok.addItem(item);
        item = new Item("2","夜班");
        shiftCheckbok.addItem(item);
        Item lineItem = new Item("01","一線");
        lineCombox.addItem(lineItem);
        lineItem = new Item("02","二線");
        lineCombox.addItem(lineItem);
        lineItem = new Item("03","三線");
        lineCombox.addItem(lineItem);
        lineItem = new Item("04","四線");
        lineCombox.addItem(lineItem);
        lineItem = new Item("05","五線");
        lineCombox.addItem(lineItem);
        lineItem = new Item("06","六線");
        lineCombox.addItem(lineItem);
        lineItem = new Item("07","七線");
        lineCombox.addItem(lineItem);
        lineItem = new Item("08","八線");
        lineCombox.addItem(lineItem);
        lineItem = new Item("09","九線");
        lineCombox.addItem(lineItem);
        lineItem = new Item("10","十線");
        lineCombox.addItem(lineItem);
        jl3.setForeground(Color.red);
        jp.setBounds(20, 100, 700, 400);
        jb1.setBounds(450, 100, 100, 30);
        jt2.setBounds(220, 150, 200, 30);
        jc1.setBounds(220, 100, 200, 30);
        jb1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                print();
            }
        });

    }


    public void checkN() {
        String sql="select OEMPN from SFIS_LABLE_1901AP_PEGA";
        List<Hashtable> list = null;
        try {
             list=new DataHandler(uiVO).getDataList(sql, DataSourceType._MultiCompanyCurrentDB);
            jc1.removeAllItems();
            jc1.addItem("");
            for (int n = 0; n <list.size(); n++) {
                jc1.addItem(list.get(n).get("OEMPN"));
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    public void print() {
        String today =selectTime.getText().substring(0,10);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        Item lineComboxSelectedItem = (Item) lineCombox.getSelectedItem();
        Item shiftComboxSelectedItem = (Item) shiftCheckbok.getSelectedItem();
        String line = lineComboxSelectedItem.getKey();
        String shift = shiftComboxSelectedItem.getKey();
        try {
            date = format.parse(today);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
		String yearStr = String.valueOf(calendar.get(Calendar.WEEK_OF_YEAR));
		if(yearStr.length() < 2){
			yearStr = "0" + yearStr;
		}
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek == 0)
            dayOfWeek = 7;

        String weekStr =""+dayOfWeek;

        String selectDate=String.valueOf(calendar.get(Calendar.YEAR)).substring(3,4) + yearStr + weekStr;
        System.out.println("selectDate:"+selectDate);
        if (jc1.getSelectedItem().equals("")) {
            JOptionPane.showConfirmDialog(null, "OEM PN不可為空" + "\n", "警告", JOptionPane.PLAIN_MESSAGE);
            return;
        }
        if (jt2.getText().equals("")) {
            JOptionPane.showConfirmDialog(null, "打印數量不可為空" + "\n", "警告", JOptionPane.PLAIN_MESSAGE);
            return;
        }
        if (!isPureDigital(jt2.getText())) {
            JOptionPane.showConfirmDialog(null, "打印數量必須為正整數" + "\n", "警告", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        PDFCreater1718LC pdfCreater = null;
        for (int i = 0; i < Integer.parseInt(jt2.getText()); i++) {
            HashMap<String, String> pDFMap = new HashMap<>();
            try {
                if (pc.getL_CStart1901(jc1.getSelectedItem().toString(), uiVO) == null) {
                    pDFMap.put("LC1", "N/A");
                    pDFMap.put("LC2", "N/A");
                    pDFMap.put("LC3", "N/A");
                    pDFMap.put("LC4", "N/A");
                    pDFMap.put("LC5", "N/A");
                    pDFMap.put("LC6", "N/A");
                    pDFMap.put("LC7", "N/A");
                } else {
                    Hashtable ht = pc.getL_CStart1901(jc1.getSelectedItem().toString(), uiVO);
                    String L_Cstr = ht.get("VENDOR_CODE").toString() + ht.get("SITE_CODE") + ht.get("PART_NO") + ht.get("PART_NAME") + ht.get("REV") + ht.get("CONFIG");
                    System.out.println(pc.getL_CEnd("1718"));
                    pDFMap.put("LC1", L_Cstr + selectDate+shift+line+pc.getL_CEnd("1718").substring(4,10));
                    pDFMap.put("LC2", L_Cstr + selectDate+shift+line+pc.getL_CEnd("1718").substring(4,10));
                    pDFMap.put("LC3", L_Cstr + selectDate+shift+line+pc.getL_CEnd("1718").substring(4,10));
                    pDFMap.put("LC4", L_Cstr + selectDate+shift+line+pc.getL_CEnd("1718").substring(4,10));
                    pDFMap.put("LC5", L_Cstr + selectDate+shift+line+pc.getL_CEnd("1718").substring(4,10));
                    pDFMap.put("LC6", L_Cstr + selectDate+shift+line+pc.getL_CEnd("1718").substring(4,10));
                    pDFMap.put("LC7", L_Cstr + selectDate+shift+line+pc.getL_CEnd("1718").substring(4,10));
                    pc.insertRecord(pDFMap.get("LC1"), uiVO.getLogin_id(), uiVO);
                    pc.insertRecord(pDFMap.get("LC2"), uiVO.getLogin_id(), uiVO);
                    pc.insertRecord(pDFMap.get("LC3"), uiVO.getLogin_id(), uiVO);
                    pc.insertRecord(pDFMap.get("LC4"), uiVO.getLogin_id(), uiVO);
                    pc.insertRecord(pDFMap.get("LC5"), uiVO.getLogin_id(), uiVO);
                    pc.insertRecord(pDFMap.get("LC6"), uiVO.getLogin_id(), uiVO);
                    pc.insertRecord(pDFMap.get("LC7"), uiVO.getLogin_id(), uiVO);
                }
                System.out.println("---------------" + pDFMap);
                pDFMap.put("oemPN", jc1.getSelectedItem().toString());
                pdfCreater = new PDFCreater1718LC("d://CARTON.pdf");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            pdfCreater.addPage(pDFMap);
            pdfCreater.close();
            pdfCreater.printPdf();
        }
        JOptionPane.showConfirmDialog(null, "打印完成！" + "\n", "警告", JOptionPane.PLAIN_MESSAGE);
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

    public static boolean isPureDigital(String string) {
        if (isBlank(string))
            return false;
        String regEx1 = "\\d+";
        Pattern p;
        Matcher m;
        p = Pattern.compile(regEx1);
        m = p.matcher(string);
        if (m.matches())
            return true;
        else
            return false;
    }
    private PDateTimeTextField MyDateField(int x, int y, int w, int h) {
        PDateTimeTextField pdtf = new PDateTimeTextField(uiVO, "PDTimeST", 200, true, true);
        pdtf.setBounds(x, y, w, h);
        return pdtf;
    }
}