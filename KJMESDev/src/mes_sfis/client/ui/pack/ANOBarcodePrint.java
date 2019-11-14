package mes_sfis.client.ui.pack;

import base.client.ui.BasePanel;
import base.client.util.component.P_Component_Meta;
import base.vo.UI_InitVO;
import com.spreada.utils.chinese.ZHConverter;
import mes_sfis.client.model.service.ANOBarcodePrintService;
import mes_sfis.client.util.*;
import mes_sfis.client.util.Item;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Pino_Gao on 2018/8/29.
 */
public class ANOBarcodePrint extends BasePanel {
    public static final String VERSION = "$Name: $, $Id: ANOBarcodePrint.java,v 1.1 2018/12/26 08:52:07 Andy_Lau Exp $";
    public static final String COPYRIGHT = "Copyright (c) 2008 PEGATRON Inc. All Rights Reserved.";
    DataHandler dh;
    ANOBarcodePrintService barcodePrintService;
    JPanel contentPanel = super.UILayoutPanel;
    String proLevel = null;
    String floor = null;
    Integer num = 0;
    String datastatus = null;
    JLabel jl1 = new JLabel("ANO量測單打印程序");
    JLabel jl2 = new JLabel("專案代碼:");
    JLabel jl3 = new JLabel("樓層:");
    JCheckBox checkBox00 = new JCheckBox("全部");
    JCheckBox checkBox01 = new JCheckBox("光澤");
    JCheckBox checkBox02 = new JCheckBox("膜厚");
    JCheckBox checkBox03 = new JCheckBox("色差");
    JCheckBox checkBox04 = new JCheckBox("粗糙");
    JLabel jl4 = new JLabel("工號:");//查的
    JLabel jl5 = new JLabel("顏色:");
    JLabel jl6 = new JLabel("送測類型:");
    JLabel jl7 = new JLabel("送測單位:");
    JLabel jl8 = new JLabel("飛把號:");
    JLabel jl9 = new JLabel("產品編碼:");
    JLabel jl10 = new JLabel("量測類別:");
    JLabel jl11 = new JLabel("顯示SPC:");
    JRadioButton rb1=new JRadioButton("是");
    JRadioButton rb2=new JRadioButton("否");
    JLabel jl12 = new JLabel("量測項目:");
    ButtonGroup group=new ButtonGroup();
    JComboBox jc2 = new JComboBox();
    JComboBox jc3 = new JComboBox();
    JTextField jt4 = new JTextField();
    JComboBox jc5 = new JComboBox();
    JComboBox jc6 = new JComboBox();
    JTextField jt7 = new JTextField();
    JTextField jt8 = new JTextField(30);
    JTextField jt9 = new JTextField(30);
    JComboBox jc10 = new JComboBox();
    JButton printjb = new JButton("打印");
    String path  ="D:\\mes_data\\configs";

    HashMap<String, String> printMap = new HashMap<>();
    List<Hashtable> lists;
    List<Hashtable> lists1;
    List<Hashtable> lists2;
    List<Hashtable> lists3;
    List<Hashtable> lists4;

    public ANOBarcodePrint(UI_InitVO uiVO){
        super(uiVO);
        barcodePrintService = new ANOBarcodePrintService(uiVO);
        dh = new DataHandler(uiVO);
        createDir(path);
        String file1 = "ts24.lib";
        String file2 = "PegaBase.ini";
        File file3 = new File(path+"\\ts24.lib");
        File file4 = new File(path+"\\PegaBase.ini");
        if(!file3.exists()){
            uploadFiles(file1);
        }
        if(!file4.exists()){
            uploadFiles(file2);
        }
        init();
    }

    private boolean createDir(String path) {
        File dir = new File(path);
        if (dir.exists()) {
            System.out.println("創建目錄" + path + "失敗，目標目錄已存在");
            return false;
        }
        if (!path.endsWith(File.separator)) {
            path = path + File.separator;
        }
        //?建目?
        if (dir.mkdirs()) {
            System.out.println("創建目錄" + path + "成功！");
            return true;
        } else {
            System.out.println("創建目錄" + path + "失敗！");
            return false;
        }
    }

    private void uploadFiles(String file1) {
        URL website = null;
        try {
            website = new URL(MESGlobe.PEGA_URL+"/configs/"+file1);
            System.out.println(website);
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = new FileOutputStream("D:\\mes_data\\configs\\"+file1);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        group.add(rb1);
        group.add(rb2);
        jl1.setSize(500, 100);
        jl1.setFont(new java.awt.Font("Dialog", 1, 35));
        jl1.setBounds(100, 50, 500, 80);
        jl2.setBounds(50,130,100,30);
        jc2.setBounds(120,130,180,30);
        jl3.setBounds(350,130,100,30);
        //jl3_.setBounds(450,130,100,30);
        jc3.setBounds(420,130,180,30);
        jl4.setBounds(50,170,100,30);
        jt4.setBounds(120,170,180,30);
        jl5.setBounds(350,170,100,30);
        jc5.setBounds(420,170,180,30);
        jl6.setBounds(50,210,100,30);
        jc6.setBounds(120,210,180,30);
        jl7.setBounds(350,210,100,30);
        jt7.setBounds(420,210,180,30);
        jl8.setBounds(50,250,100,30);
        jt8.setBounds(120,250,180,30);
        jt8.setBackground(Color.white);
        jl9.setBounds(350,250,100,30);
        jt9.setBounds(420,250,180,30);
        jt9.setBackground(Color.white);
        jl10.setBounds(50,290,100,30);
        jc10.setBounds(120,290,180,30);
        jl11.setBounds(350,290,100,30);
        rb1.setBounds(420,290,100,30);
        rb2.setBounds(530,290,100,30);
        jl12.setBounds(50,330,100,30);
        checkBox00.setBounds(120,330,100,30);
        checkBox01.setBounds(220,330,100,30);
        checkBox02.setBounds(320,330,100,30);
        checkBox03.setBounds(420,330,100,30);
        checkBox04.setBounds(520,330,100,30);
        printjb.setBounds(50,370,550,30);

        jc3.addItem("");
        jc2.addItem("");
        lists1 = barcodePrintService.findMODELID();
        for (Hashtable ht:lists1) {
            jc2.addItem(ht.get("MODELID").toString());
        }

        jc5.addItem("");
        lists = barcodePrintService.findClipNums();
        for (Hashtable ht:lists) {
            Item item = new Item(ht.get("NODE").toString(),ht.get("CLIP").toString());
            jc5.addItem(item);
        }
        lists2 = barcodePrintService.findFloor();
        for (Hashtable ht:lists2) {
            jc3.addItem(ht.get("FLOOR").toString());
        }

        jc6.addItem("");
        lists3 = barcodePrintService.findMeasureType();
        for (Hashtable ht:lists3) {
            Item item = new Item(ht.get("RIDX").toString(),ht.get("MEASURE_TYPE").toString());
            jc6.addItem(item);
        }

        jc10.addItem("");
        lists4 = barcodePrintService.findTestItem();
        for (Hashtable ht:lists4) {
            jc10.addItem(ht.get("TESTITEM").toString());
        }
        this.add(jl1);
        this.add(jl2);
        this.add(jc2);
        this.add(jl3);
        this.add(jc3);
        this.add(jl4);
        this.add(jl5);
        this.add(jl6);
        this.add(jl7);
        this.add(jl8);
        this.add(jl9);
        this.add(jt4);
        this.add(jc5);
        this.add(jc6);
        this.add(jt7);
        this.add(jt8);
        this.add(jt9);
        this.add(jl10);
        this.add(jl11);
        this.add(jc10);
        this.add(rb1);
        this.add(rb2);
        this.add(checkBox00);
        this.add(checkBox01);
        this.add(checkBox02);
        this.add(checkBox03);
        this.add(checkBox04);
        this.add(jl12);
        this.add(printjb);

        checkBox00.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                // ?取事件源（即复?框本身）
               if(checkBox00.isSelected()){
                   checkBox01.setSelected(true);
                   checkBox02.setSelected(true);
                   checkBox03.setSelected(true);
                   checkBox04.setSelected(true);
               }else {
                   checkBox01.setSelected(false);
                   checkBox02.setSelected(false);
                   checkBox03.setSelected(false);
                   checkBox04.setSelected(false);
               }
            }
        });


        printjb.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                //保存?据到?据?
                String vjc3 = jc3.getSelectedItem().toString();
                String vjc4 = jt4.getText();
                Item vjc5 = (Item) jc5.getSelectedItem();
                Item vjc6 = (Item) jc6.getSelectedItem();
                String vjc7 = jt7.getText();
                String vjt8 = jt8.getText();
                String vjt9 = jt9.getText();
                String testitem = jc10.getSelectedItem().toString();
                if("".equals(vjc3)||vjc3==null){
                    JOptionPane.showConfirmDialog(null, "請選擇樓層", "警告", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                if("".equals(vjc4)||vjc4==null){
                    JOptionPane.showConfirmDialog(null, "請輸入送測人員工號", "警告", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                if("".equals(vjc5)||vjc5==null){
                    JOptionPane.showConfirmDialog(null, "請選擇顏色", "警告", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                if("".equals(vjc6)||vjc6==null){
                    JOptionPane.showConfirmDialog(null, "請選擇送測類型", "警告", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                if("".equals(testitem)||testitem==null){
                    JOptionPane.showConfirmDialog(null, "請選擇測量類型", "警告", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                if(!rb1.isSelected()&&!rb2.isSelected()){
                    JOptionPane.showConfirmDialog(null, "請確認是否要上偉看板", "警告", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                if(rb1.isSelected()){
                    datastatus = "1";
                }else {
                    datastatus = "0";
                }
                floor = vjc3;
                proLevel = jc2.getSelectedItem().toString();
                printMap.put("PRO_LEVEL",proLevel);
                printMap.put("FLOOR",floor);
                int chkop =  barcodePrintService.checkOP(vjc4.toUpperCase());
                if (chkop==0){
                    JOptionPane.showConfirmDialog(null, "送測人員工號輸入錯誤或該員工已離職", "警告", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                printMap.put("LINE_NUM",vjc4);
                String vjc5Key = vjc5.getKey();
                String vjc5Value = vjc5.getValue();
                printMap.put("CLIP_NUM",vjc5Value);
                printMap.put("CLIP_NUMSTR",vjc5Key);
                String vjc6Key = vjc6.getKey();
                String vjc6Value = vjc6.getValue();
                printMap.put("MEA_TYPE",vjc6Key);
                printMap.put("MEA_TYPESTR",vjc6Value);
                printMap.put("HOLE",vjc7);
                printMap.put("MACHINE_NUM",floor+"-"+vjt8);
                String cssn = "";
                if(vjt9!=null||vjt9!=""){
                 cssn =   barcodePrintService.getCssn(vjt9.toUpperCase());
                }
                printMap.put("MCE_NUM",cssn);
                printMap.put("TESTITEM",testitem);
                printMap.put("DATASTATUS",datastatus);
                System.out.println(testitem+"量測類別");
                Date date = new Date();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String datestr = simpleDateFormat.format(date);
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                printMap.put("CREATE_TIME",datestr);
                cal.add(cal.DATE,1);
                date = cal.getTime();
                String tomstr = simpleDateFormat.format(date);
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                String reg = "[^0-9]";
                Pattern pattern = Pattern.compile(reg);
                String bar1 = "";

                String today = datestr.substring(0,11);
                String tommrow = tomstr.substring(0,11);
                String flow_Num = "";
                if(hour>=8&&hour<20){
                    printMap.put("CLASSNUM","A");
                    //查?最大流水
                    flow_Num = barcodePrintService.findFlowNumA(today);
                    Matcher matcher = pattern.matcher(today);
                    bar1 = floor+matcher.replaceAll("").trim()+printMap.get("CLASSNUM");

                }else if(hour>=0&&hour<8) {
                    printMap.put("CLASSNUM","B");
                    cal.add(cal.DATE,-2);
                    date = cal.getTime();
                    String yeststr = simpleDateFormat.format(date);
                    String yestday = yeststr.substring(0,11);
                    flow_Num = barcodePrintService.findFlowNumB(yestday,today);
                    Matcher matcher = pattern.matcher(yestday);
                    bar1 = floor+matcher.replaceAll("").trim()+printMap.get("CLASSNUM");
                }else {
                    printMap.put("CLASSNUM","B");
                    flow_Num = barcodePrintService.findFlowNumB(today,tommrow);
                    Matcher matcher = pattern.matcher(today);
                    bar1 = floor+matcher.replaceAll("").trim()+printMap.get("CLASSNUM");
                }
                if("".equals(flow_Num)||flow_Num==null){
                    num=0;

                }else {
                    num = Integer.parseInt(flow_Num);
                }
                String fn = getFlowNum(num);
                printMap.put("FLOW_NUM",fn);
                bar1=bar1+printMap.get("FLOW_NUM");

                StringBuffer sb = new StringBuffer();

                sb.append(proLevel);

                sb.append("&");
                sb.append(vjc5Value);
                sb.append("&");
                sb.append(vjt8);
                sb.append("&");
                sb.append(vjc6Value);
                sb.append("&");
                //String b = getChinese(printMap.get("MEA_TYPE"));
                //String b = ZHConverter.convert(printMap.get("MEA_TYPE"), ZHConverter.TRADITIONAL);
                sb.append(vjc4);
                sb.append("&");
                sb.append(bar1);
                if(!"".equals(vjc7)){
                    sb.append("&");
                    sb.append(vjc7);
                }
                if(!"".equals(vjt9)){
                    sb.append("&");
                    sb.append(vjt9);
                }
                sb.append("&");
                printMap.put("FULL_CODE",bar1);
                printMap.put("CODE",sb.toString());
                boolean result = barcodePrintService.saveDataPrint(printMap);
                if(result){
                    JOptionPane.showConfirmDialog(null, "數據保存成功！！！", "警告", JOptionPane.PLAIN_MESSAGE);
                    //數據打印
                    ZplPrinter p = new ZplPrinter();
                    printRestoking(p,printMap);

                }else {
                    JOptionPane.showConfirmDialog(null, "數據保存失敗！！！", "警告", JOptionPane.PLAIN_MESSAGE);
                }


            }
        });

        jt9.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent arg0) {
                int key = arg0.getKeyCode();
                if (key == '\n') {

                }
            }

            @Override
            public void keyReleased(KeyEvent e1) {
                int key = e1.getKeyCode();
                if (key == '\n') {
                  String  flyid = barcodePrintService.getFlyID(jt9.getText());
                    if(flyid!=null){
                       jt8.setText(flyid.toUpperCase());
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

    }
    public static void main(String[] args){
        String str1="F2-220181008A000008";
        String str2=str1.substring(str1.length()-15,str1.length()-7);
        System.out.println(str2);
    }
    public static String getChinese(String paramValue) {
        System.out.println(paramValue);
        String regex = "([\u4e00-\u9fa5]+)";
        String replacedStr = paramValue;
        Matcher matcher = Pattern.compile(regex).matcher(paramValue);
        while (matcher.find()) {
            System.out.println("----------"+matcher.group(0));
            ZHConverter converter2 = ZHConverter.getInstance(ZHConverter.TRADITIONAL);
            String traditionalStr = converter2.convert(matcher.group(0));

            replacedStr = replacedStr.replace(matcher.group(0),traditionalStr);

            System.out.println("zyf" +  traditionalStr  +  replacedStr);
        }
        return replacedStr;
    }


    public static String getEncoding(String str) {
        String encode = "GB2312";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s = encode;
                return s;
            }
        } catch (Exception exception) {
        }
        encode = "ISO-8859-1";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s1 = encode;
                return s1;
            }
        } catch (Exception exception1) {
        }
        encode = "UTF-8";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s2 = encode;
                return s2;
            }
        } catch (Exception exception2) {
        }
        encode = "GBK";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s3 = encode;
                return s3;
            }
        } catch (Exception exception3) {
        }
        return "";
    }


    private boolean printRestoking(ZplPrinter p, HashMap<String, String> printMap)  {
        String create_time = printMap.get("CREATE_TIME");

        String bar1Zpl = "^JMA^LL600^PW960^MD15^PR3^PON^LRN^LH30,10^FO60,100^BQ,2,6^CI26^FH^FDD03040C,LA,${data}^FS" +

              /*  "^FS^FO30,420^GB840,0,4^FS" +
                "^FS^FO30,480^GB840,0,4^FS" +
                "^FS^FO30,540^GB840,0,4^FS" +
                "^FS^FO30,600^GB840,0,4^FS" +
                "^FS^FO30,660^GB840,0,4^FS" +
                "^FS^FO30,420^GB0,240,4^FS" +
                "^FS^FO250,420^GB0,240,4^FS" +
                "^FS^FO560,420^GB0,240,4^FS" +
                "^FS^FO870,420^GB0,240,4^FS" +*/
                "^PQ1,0,1,Y";
        try{
            System.out.println(printMap.get("CODE")+"/*/*/*/*/*/*/*/*/*/*/*/*/");
            p.setBarcode(printMap.get("CODE"),bar1Zpl);
            String CLIP_NUM = printMap.get("CLIP_NUMSTR");
            String MEA_TYPE = printMap.get("MEA_TYPESTR");

            String showStr = proLevel+"_"+CLIP_NUM+"_"+MEA_TYPE;
            if(!"".equals(printMap.get("MACHINE_NUM"))){
                showStr+="_"+printMap.get("MACHINE_NUM");
            }
            if(!"".equals(printMap.get("HOLE"))){
                showStr+="_"+printMap.get("HOLE");
            }
            String item = "";
            if(checkBox01.isSelected()){
                item+= checkBox01.getText()+";";
            }
            if(checkBox02.isSelected()){
                item+= checkBox02.getText()+";";
            }
            if(checkBox03.isSelected()){
                item+= checkBox03.getText()+";";
            }
            if(checkBox04.isSelected()){
                item+= checkBox04.getText();
            }

            String testitem = printMap.get("TESTITEM");
            p.setText(showStr, 60, 30, 30, 30, 20, 1, 1, 24);
            p.setText(item, 350, 180, 30, 30, 18, 1, 1, 24);
            p.setText(testitem, 350, 230, 30, 30, 18, 1, 1, 24);
            p.setText(create_time, 350, 280, 30, 30, 18, 1, 1, 24);
        }catch (Exception e){
            e.printStackTrace();
        }

        String zpl = p.getZpl();
        System.out.println(zpl);
        boolean result = p.print(zpl);
        return result;
    }

    private String getFlowNum(int num) {

        DecimalFormat df=new DecimalFormat("000000");//位?不足?0
        if(num<=999999){
            num++;
            return df.format(num);
        }else {
            return "數據異常";
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
