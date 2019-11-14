package mes_sfis.client.ui.pack;

import base.client.ui.BasePanel;
import base.client.util.component.P_Component_Meta;
import base.vo.UI_InitVO;
import com.spreada.utils.chinese.ZHConverter;
import mes_sfis.client.model.service.BarcodePrintService;
import mes_sfis.client.util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public class BarcodePrint extends BasePanel {
    public static final String VERSION = "$Name: $, $Id: BarcodePrint.java,v 1.0 2018/08/29 08:52:07 Pino_Gao Exp $";
    public static final String COPYRIGHT = "Copyright (c) 2008 PEGATRON Inc. All Rights Reserved.";
    DataHandler dh;
    BarcodePrintService barcodePrintService;
    JPanel contentPanel = super.UILayoutPanel;
    String proLevel = null;
    String proLevel2 = null;
    String floor = null;
    Integer num = 0;
    JLabel jl1 = new JLabel("量測單打印程序");
    JLabel jl2 = new JLabel("產品階段:");
    JLabel jl3 = new JLabel("樓層:");

    JLabel jl4 = new JLabel("線別:");//查的
    JLabel jl5 = new JLabel("夾位:");
    JLabel jl6 = new JLabel("送測類型:");
    JLabel jl7 = new JLabel("穴號:");
    JLabel jl8 = new JLabel("機臺/治具號:");
    JLabel jl9 = new JLabel("備註:");
    JComboBox jc2 = new JComboBox();
    JComboBox jc3 = new JComboBox();
    JComboBox jc4 = new JComboBox();
    JComboBox jc5 = new JComboBox();
    JComboBox jc6 = new JComboBox();
    JTextField jt7 = new JTextField();
    JTextField jt8 = new JTextField(30);
    JTextField jt9 = new JTextField(30);
    JButton printjb = new JButton("打印");
    String path  ="D:\\mes_data\\configs";

    HashMap<String, String> printMap = new HashMap<>();
    List<Hashtable> lists;
    List<Hashtable> lists1;
    List<Hashtable> lists2;
    List<Hashtable> lists3;

    public BarcodePrint(UI_InitVO uiVO){
        super(uiVO);
        barcodePrintService = new BarcodePrintService(uiVO);
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
        ReadConfig3 a = new ReadConfig3();
        a.hashINI();
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
        //JLabel jl3_ = new JLabel(floor);
        jl1.setSize(500, 100);
        jl1.setFont(new java.awt.Font("Dialog", 1, 35));
        jl1.setBounds(100, 50, 500, 80);
        jl2.setBounds(50,130,100,30);
        jl3.setBounds(350,130,100,30);
        jc2.setBounds(120,130,180,30);
        jc3.setBounds(420,130,180,30);
        jl4.setBounds(50,170,100,30);
        jc4.setBounds(120,170,180,30);
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
        printjb.setBounds(280,290,100,30);

        jc2.addItem("");
        lists1 = barcodePrintService.findMODELID();
        for (Hashtable ht:lists1) {
            jc2.addItem(ht.get("MODELID").toString());
        }

        jc3.addItem("");
        jc4.addItem("");


        jc5.addItem("");
        lists = barcodePrintService.findClipNums();
        for (Hashtable ht:lists) {
            //jc4.addItem(ht.get("CELL").toString());
            jc5.addItem(ht.get("NODE").toString()+"&"+ht.get("CLIP").toString());
        }
        lists1 = barcodePrintService.findCell();
        for (Hashtable ht:lists1) {
            jc4.addItem(ht.get("CELL").toString());
        }
        lists2 = barcodePrintService.findFloor();
        for (Hashtable ht:lists2) {
            jc3.addItem(ht.get("FLOOR").toString());
        }

        jc6.addItem("");
        lists3 = barcodePrintService.findMeasureType();
        for (Hashtable ht:lists3) {
            jc6.addItem(ht.get("MEASURE_TYPE").toString());
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
        this.add(jc4);
        this.add(jc5);
        this.add(jc6);
        this.add(jt7);
        this.add(jt8);
        this.add(jt9);
        this.add(printjb);

        printjb.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                //保存?据到?据?
                String vjc3 = jc3.getSelectedItem().toString();
                String vjc4 = jc4.getSelectedItem().toString();
                String vjc5 = jc5.getSelectedItem().toString();
                String vjc6 = jc6.getSelectedItem().toString();
                String vjc7 = jt7.getText();
                String vjt8 = jt8.getText();
                String vjt9 = jt9.getText();
                if("".equals(vjc3)||vjc3==null){
                    JOptionPane.showConfirmDialog(null, "請選擇樓層", "警告", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                if("".equals(vjc4)||vjc4==null){
                    JOptionPane.showConfirmDialog(null, "請選擇線別", "警告", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                if("".equals(vjc5)||vjc5==null){
                    JOptionPane.showConfirmDialog(null, "請選擇夾位", "警告", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                if("".equals(vjc6)||vjc6==null){
                    JOptionPane.showConfirmDialog(null, "請選擇送測類型", "警告", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                if("".equals(vjt8)||vjt8==null){
                    JOptionPane.showConfirmDialog(null, "機臺號或ASSY治具號", "警告", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                floor = vjc3;
                proLevel = jc2.getSelectedItem().toString();
                printMap.put("PRO_LEVEL",proLevel);
                printMap.put("FLOOR",floor);
                printMap.put("LINE_NUM",vjc4);
                String[] vjc5tmp = vjc5.split("&");
                printMap.put("CLIP_NUM",vjc5tmp[1]);
                printMap.put("CLIP_NUMSTR",vjc5tmp[0]);
                String[] vjc6tmp = vjc6.split("&");
                printMap.put("MEA_TYPE",vjc6tmp[0]);
                printMap.put("MEA_TYPESTR",vjc6tmp[1]);
                printMap.put("HOLE",vjc7);
                printMap.put("MACHINE_NUM",floor+"-"+vjt8);
                printMap.put("MCE_NUM",vjt9);

                //流水?
                //根据??得到班?
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
                //String create_time = printMap.get("CREATE_TIME");
                String reg = "[^0-9]";
                //String[] strings = create_time.split(" ");
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
                    System.out.println("****************************//"+yestday);
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
                //String a = ZHConverter.convert(proLevel, ZHConverter.TRADITIONAL);
                //String a  = getChinese(proLevel );
                sb.append(proLevel2);

                sb.append("&");
                sb.append(vjc5tmp[1]);
                sb.append("&");
                sb.append(vjt8);
                sb.append("&");
                sb.append(vjc6tmp[1]);
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
        //上方的??
        String create_time = printMap.get("CREATE_TIME");

        //String bar1 ="凱嘉電腦配件_123_456凱嘉電腦配件_123_456凱嘉電腦配件_123_456";

        //String bar1Zpl = "^FO85,70^BY4,3.0,140^BCN,,Y,N,N^FD${data}^FS";//???式模板
        String bar1Zpl = "^JMA^LL600^PW960^MD15^PR3^PON^LRN^LH30,10^FO60,100^BQ,2,6^CI26^FH^FDD03040C,LA,${data}" +

                "^FS^FO30,420^GB840,0,4^FS" +
                "^FS^FO30,480^GB840,0,4^FS" +
                "^FS^FO30,540^GB840,0,4^FS" +
                "^FS^FO30,600^GB840,0,4^FS" +
                "^FS^FO30,660^GB840,0,4^FS" +
                "^FS^FO30,420^GB0,240,4^FS" +
                "^FS^FO250,420^GB0,240,4^FS" +
                "^FS^FO560,420^GB0,240,4^FS" +
                "^FS^FO870,420^GB0,240,4^FS" +
                "^PQ1,0,1,Y";
        try{
            System.out.println(printMap.get("CODE")+"/*/*/*/*/*/*/*/*/*/*/*/*/");
            p.setBarcode(printMap.get("CODE"),bar1Zpl);
            //源?位

            //p.setText(proLevel, 80, 90, 30, 30, 13, 1, 1, 24);
            //目??位
            //p.setText(printMap.get("LINE_NUM"),290, 90, 30, 30, 13, 1, 1, 24);
            //?品??
            String CLIP_NUM = printMap.get("CLIP_NUMSTR");
            //String CLIP_NUM= new String(b.getBytes("GB2312"), "GB2312");
            //p.setText(CLIP_NUM, 500, 90, 30, 30, 13, 1, 1, 24);
            //件??
            String MEA_TYPE = printMap.get("MEA_TYPESTR");
            //p.setText(MEA_TYPE, 710, 90, 30, 30, 13, 1, 1, 24);
            //p.setText(floor, 80, 210, 30, 30, 13, 1, 1, 24);
            String showStr = printMap.get("LINE_NUM")+"_"+CLIP_NUM+"_"+MEA_TYPE;
            if(!"".equals(printMap.get("MACHINE_NUM"))){
                //p.setText(printMap.get("MACHINE_NUM"), 500, 210, 30, 30, 13, 1, 1, 24);
                showStr+="_"+printMap.get("MACHINE_NUM");
            }
            if(!"".equals(printMap.get("HOLE"))){
                //p.setText(printMap.get("HOLE"),290, 210, 30, 30, 13, 1, 1, 24);
                showStr+="_"+printMap.get("HOLE");
            }
            p.setText(showStr, 60, 30, 30, 30, 20, 1, 1, 24);
            p.setText("Type", 100, 430, 30, 30, 20, 1, 1, 24);
            p.setText("OP", 100, 490, 30, 30, 20, 1, 1, 24);
            p.setText("Start", 100, 550, 30, 30, 20, 1, 1, 24);
            p.setText("Finish", 100, 610, 30, 30, 20, 1, 1, 24);
            p.setText("OMM", 350, 430, 30, 30, 20, 1, 1, 24);
            p.setText("CMM", 660, 430, 30, 30, 20, 1, 1, 24);
            p.setText(create_time, 350, 370, 30, 30, 18, 1, 1, 24);
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
