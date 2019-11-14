package mes_sfis.client.ui.pack;

import base.client.ui.BasePanel;
import base.client.util.component.P_Component_Meta;
import base.vo.UI_InitVO;
import mes_sfis.client.model.bean.NutsDataModel;
import mes_sfis.client.model.service.NutsDataGrabService;
import mes_sfis.client.model.service.ThirteenNutsService;
import mes_sfis.client.util.ExcelUtils;
import mes_sfis.client.util.ReadConfig2;
import mes_sfis.client.util.SoundUtil;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.*;
import java.util.List;

/**
 * Created by Mark_Yang on 2018/12/11.
 */
public class NutsDataGrab extends BasePanel {
    public static final String VERSION = "$Name:  $, $Id: NutsDataGrab.java,v 1 2018/12/11 11:00:17 Lucy6_Lu Exp $";
    public static final String COPYRIGHT = "Copyright (c) 2008 PEGATRON Inc. All Rights Reserved.";
    static UI_InitVO uiVO;
    private JTextField MceAndIsnText;
    private NutsDataGrabService nutsDataGrabService;
    private Map<String,String> map=new HashMap<>();
    JLabel JLMCE,JLISN;
    int btn = 0;
    private String meachine;
    private String time;
    private List<NutsDataModel> list=null;

    public NutsDataGrab(UI_InitVO uiVO) {
        super(uiVO);
        nutsDataGrabService=new NutsDataGrabService(uiVO);
        this.uiVO=uiVO;
        ReadConfig2 a=new ReadConfig2();
        a.hashINI();
        try {
            meachine=a.iniHash.get("epega.exe.cnum").toString();
            //grp=a.iniHash.get("epega.exe.grp").toString();

        }catch (Exception e){
            JOptionPane.showConfirmDialog(null, "配置文件異常，請聯繫 OA     注： 配置文件格式為 UNICODE", "警告", JOptionPane.PLAIN_MESSAGE);
        }
        init();
    }

    private void init() {
        JPanel panel = jPanel();
        panel.setSize(600, 650);
        add(panel);

    }

    private JPanel jPanel() {

        JPanel panel=new JPanel();

        //    panel.setLayout(null);



        JLabel title = new JLabel("13nuts?据抓取");
        title.setBounds(240, 20, 120, 25);
        title.setFont(new Font("宋体", Font.BOLD, 16));
        panel.add(title);

        //?ant

        JButton startPick = new JButton("start");
        startPick.setBounds(240, 60, 120, 25);
        panel.add(startPick);

       /* JButton clearlog = new JButton("ClearLog");
        clearlog.setBounds(240, 100, 120, 25);
        panel.add(clearlog);*/

        JLabel log = new JLabel("LOG");
        log.setFont(new Font("宋体", Font.BOLD, 16));
        log.setBounds(30, 140, 40, 25);
        panel.add(log);

        final JTextArea LogText = new JTextArea(25, 50);
        LogText.setBounds(30, 160, 500, 150);
        LogText.setLineWrap(true);
        final JScrollPane jsLogText = new JScrollPane(LogText);
        //  Point p = new Point();
        //  p.setLocation(0, 150);
        //  jsLogText.getViewport().setViewPosition(p);
        LogText.setEditable(false);
        jsLogText.setBounds(30, 160, 500, 150);
        jsLogText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(jsLogText);
        final ExcelUtils excelUtils = new ExcelUtils();
        //?}?l??f???s????????
        startPick.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //optExcleFile(LogText);
                final JButton jb = (JButton) e.getSource();
                //查??据?今天的?量
                // rownum = cognexUploadService.findNewTime();
                //  System.out.println("**************************---" + time);

                final java.util.Timer timer = new java.util.Timer();


                if (jb.getText().equals("start")) {
                    jb.setText("stop");
                    timer.purge();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {

                            if (btn == 0) {
                                String result= null;

                                try {
                                    time= nutsDataGrabService.findNewTime(meachine);
                                   // System.out.println("0000"+time);
                                    if (time==null){
                                        time="";
                                    }
                                    list = excelUtils.excelNutsList(time,meachine);
                                   // System.out.println("gs"+list.size());

                                    result = nutsDataGrabService.insertNutsData(list);
                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                    result=e1.getMessage();
                                }

                                LogText.append(result+"\r\n");

                                btn = 0;
                            } else if (btn == 1) {
                                timer.cancel();
                                btn = 1;
                            }


                            //showFileOpenDialog(panel,LogText);
                        }
                    }, 0, 3600000);
                    btn = 0;
                } else if (jb.getText().equals("stop")) {
                    jb.setText("start");
                    btn = 1;
                    //查看尾行??

                    //timerTask=null;
                }


            }


        });
        return panel;
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
    //  ?取文件?
    public static void getAllFileName(String path,ArrayList<String> fileNameList) {
        //ArrayList<String> files = new ArrayList<String>();
        boolean flag = false;
        File file = new File(path);
        File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
//              System.out.println("文     件：" + tempList[i]);
                //fileNameList.add(tempList[i].toString());
                fileNameList.add(tempList[i].getName());
            }
            if (tempList[i].isDirectory()) {
//              System.out.println("文件?：" + tempList[i]);
                getAllFileName(tempList[i].getAbsolutePath(),fileNameList);
            }
        }
        return;
    }

    private static String getMACAddress() {
        StringBuffer sb = new StringBuffer();

        try{
            InetAddress ia = InetAddress.getLocalHost();
            byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();


            for (int i = 0; i < mac.length; i++) {
                if (i != 0) {
                    sb.append("-");
                }
                String s = Integer.toHexString(mac[i] & 0xFF);
                sb.append(s.length() == 1 ? 0 + s : s);
            }

        }catch (Exception e){

        }
        return sb.toString().toUpperCase().replaceAll("-", ":");
    }

}
