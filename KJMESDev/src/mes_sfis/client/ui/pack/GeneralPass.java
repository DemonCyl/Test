package mes_sfis.client.ui.pack;

import base.client.ui.BasePanel;
import base.client.util.component.P_Component_Meta;
import base.vo.UI_InitVO;
import mes_sfis.client.model.service.PassService;
import mes_sfis.client.sfis.PassForProcedure;
import mes_sfis.client.util.JTextFieldHintListener;
import mes_sfis.client.util.SoundUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.util.*;
import java.util.List;

/**
 * Created by andy on 2018/08/08.
 */
public class GeneralPass extends BasePanel {
    public static final String VERSION = "$Name:  $, $Id: GeneralPass.java,v 1.10 2018/12/02 09:28:13 Andy_Lau Exp $";
    public static final String COPYRIGHT = "Copyright (c) 2008 PEGATRON Inc. All Rights Reserved.";
    JPanel jp = null;
    JLabel jl6 = new JLabel();
    JButton jb3 = new JButton("鎖定ERROR CODE");
    JButton jb4 = new JButton("良品過站");
    JButton jb5 = new JButton("鎖定");
    JRadioButton rb1=new JRadioButton("過站");
    JRadioButton rb2=new JRadioButton("記錄不良");
    ButtonGroup group=new ButtonGroup();
    JTextField jt1 = new JTextField("");
    JTextArea jt2 = new JTextArea("");
    JTextField jt3 = new JTextField("");
    JComboBox jc1 = new JComboBox();
    boolean isRepair = false;
    PassForProcedure passForProcedure = new PassForProcedure();
    JScrollPane jsLog = new JScrollPane(jt2);
    String isn = "";
    String errorflag = null;
    List<String> mac = new ArrayList<>();
    String link_sne = null;
    String link_snf = null;
    String link_mce = null;
    String auto_repair = null;
    String scanner = null;
    String rdevice = null;
    String error = null;
    String mce = null;
    String item = null;
    public final static int ONE_SECOND = 1000;
    String device = null;
    PassService passService;

    JLabel jl3 = new JLabel("數據輸入");
    JLabel jl4 = new JLabel("Error Code");
    JLabel jl5 = new JLabel("");
    JLabel jl7 = new JLabel();
    JLabel jl8 = new JLabel();
    JTextField jt4 = new JTextField();

    public GeneralPass(UI_InitVO uiVO) {
        super(uiVO);
        this.uiVO = uiVO;
        passService = new PassService(uiVO);
        init();
    }


    public void init() {
        mac = getMACAddress();
        Hashtable data = null;
        data = passService.getDeviceConfig(mac);
        if (data == null) {
            JOptionPane.showConfirmDialog(null, "該電腦未設置站點,請聯繫 MIS-OA 12499添加設置", "站點未設置", JOptionPane.PLAIN_MESSAGE);
            return;
        }
        device = data.get("DEVICE").toString();
        errorflag = data.get("ERRFLAG").toString();
        link_sne = data.get("LINK_SNE").toString();
        link_snf = data.get("LINK_SNF").toString();
        link_mce = data.get("LINK_MCE").toString();
        auto_repair = data.get("AUTO_REPAIR").toString();
        scanner = data.get("SCANNER").toString();
        rdevice = data.get("RDEVICE").toString();
        String deviceNm = passService.findNameByDevice(device);
        if ("".equals(deviceNm) || deviceNm == null) {
            JOptionPane.showConfirmDialog(null, "未找到對應的站點名稱", "警告", JOptionPane.PLAIN_MESSAGE);
            return;
        }
        jl6.setText(deviceNm);
        if ("Y".equals(link_snf)) {
            jl7.setText("鋁錠供應商");
            jc1.addItem("K");
            jc1.addItem("H");
            this.add(jc1);
            this.add(jb5);
            jc1.setBounds(260, 130, 150, 30);
            jb5.setBounds(420, 130, 100, 30);
            jl7.setBounds(50, 130, 100, 30);
        }
        if ("Y".equals(link_sne)) {
            jl7.setText("顏色");
            jc1.addItem("SP");
            jc1.addItem("NDA");
            this.add(jc1);
            this.add(jb5);
            jc1.setBounds(260, 130, 150, 30);
            jb5.setBounds(420, 130, 100, 30);
            jl7.setBounds(50, 130, 100, 30);
        }
        if ("Y".equals(link_mce)) {
            jl8.setText("模具號");
            this.add(jl8);
            this.add(jt4);
            jl8.setBounds(540, 130, 60, 30);
            jt4.setBounds(600, 130, 150, 30);
            jt4.setText("1721-");
        }
        if (!"Y".equals(scanner)) {
            jt1.setEnabled(false);
            Reder reder = new Reder();
            reder.start();
        }
        if ("Y".equals(errorflag)) {
            this.add(rb1);
            this.add(rb2);
            group.add(rb1);
            group.add(rb2);
            rb1.setBounds(50, 70, 100, 30);
            rb2.setBounds(260, 70, 100, 30);
        }

        jt2.setEditable(false);
        jp = new JPanel();
        this.add(jl3);
        this.add(jl4);
        this.add(jl5);
        this.add(jl6);
        this.add(jb3);
        this.add(jb4);
        this.add(jt1);
        this.add(jsLog);
        this.add(jt3);
        this.add(jl6);
        this.add(jl7);
        jsLog.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jt1.addFocusListener(new JTextFieldHintListener(jt1, "請輸入產品序號，如刷ＮＧ請先輸入ERROR CODE回車後再輸入產品序號"));
        jt3.addFocusListener(new JTextFieldHintListener(jt3, "請輸入Error Code"));
        jl6.setSize(500, 100);
        jl6.setFont(new java.awt.Font("Dialog", 1, 35));
        jl5.setBackground(Color.white);
        jt2.setLineWrap(true);
        this.add(jp);
        jl3.setBounds(50, 220, 100, 30);
        jl4.setBounds(50, 160, 100, 30);
        jl5.setBounds(530, 190, 200, 30);
        jp.setBounds(20, 100, 700, 600);
        jl6.setBounds(50, 10, 500, 50);
        jb3.setBounds(260, 190, 150, 30);
        jb4.setBounds(420, 190, 100, 30);
        jt1.setBounds(50, 250, 700, 30);
        jt3.setBounds(50, 190, 200, 30);
        jsLog.setBounds(50, 300, 700, 400);
        jb3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              String errorName =  getErrorName(jt3.getText());
                if(errorName==null){
                    return;
                }
                isRepair = true;
                jb3.setBackground(Color.GREEN);
                jb4.setBackground(Color.WHITE);
                jt3.setEditable(false);
            }
        });
        jb4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isRepair = false;
                jt3.setText("");
                jb3.setBackground(Color.WHITE);
                jb4.setBackground(Color.GREEN);
                jt3.setEditable(true);
            }
        });
        jb5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jc1.setEnabled(false);

            }
        });
        jt1.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent arg0) {
                int key = arg0.getKeyCode();
                if (key == '\n') {
                    pass();
                }
            }

            @Override
            public void keyReleased(KeyEvent e1) {
                int key = e1.getKeyCode();
                if (key == '\n') {

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
        jt3.addKeyListener(new KeyListener() {
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
                    getErrorName(jt3.getText());
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

    class Reder extends Thread {
        @Override
        public void run() {
            Socket socket = null;
            InputStream is = null;
            BufferedReader br = null;
            try {
                socket = new Socket("192.168.100.100", 9004);
                is = socket.getInputStream();
                br = new BufferedReader(new InputStreamReader(is));
                String info = null;
                while ((info = br.readLine()) != null) {
                    jt1.setText(info);
                    jt2.setText("");
                    pass();
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    br.close();
                    is.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public void pass() {
        String flag = null;
        String chkstatus = null;
        String chk = null;
        int autorepair = 0;
        jt2.setText("");
        isn = jt1.getText();
        error = jt3.getText();
        HashMap map = new HashMap();
        map.put("OP", uiVO.getLogin_id());
        try {
            passForProcedure.open();
            if (rb2.isSelected()) {
                map.put("ISN", isn);
                map.put("DEVICE", device);
                map.put("ERROR", error);
                flag = passForProcedure.inserterrflag(map);
            } else {
                if ("Y".equals(auto_repair)) {
                    autorepair = passService.autoRepair(isn, device);
                    System.out.println(autorepair + "####");
                    if (autorepair > 0) {
                        map.put("ISN", isn);
                        map.put("DEVICE", rdevice);
                        map.put("STATUS", "2");
                        passForProcedure.procedureLogin(map);
                        map.put("STATUS", "1");
                        passForProcedure.procedureLogin(map);
                        passForProcedure.repair(map);
                    }

                }
                map.put("DEVICE", device);
                if (isRepair) {
                    map.put("ISN", error);
                    passForProcedure.procedurePass(map);
                }
                if ("Y".equals(link_snf) || "Y".equals(link_sne)) {
                    item = jc1.getSelectedItem().toString();
                    if ("SP".equals(item)) {
                        item = "B";
                    } else if ("NDA".equals(item)) {
                        item = "W";
                    }
                    map.put("ISN", item);
                    passForProcedure.procedurePass(map);
                }

                if ("Y".equals(link_mce)) {
                    mce = jt4.getText();
                    map.put("ISN", mce);
                    passForProcedure.procedurePass(map);
                }
                map.put("ISN", isn);
                flag = passForProcedure.procedurePass(map);
                if (flag != null) {
                    chkstatus = flag.substring(5, 16);
                }
                while ("Login First".equals(chkstatus)) {
                    if ("Y".equals(auto_repair)) {
                        autorepair = passService.autoRepair(isn, device);
                        if (autorepair > 0) {
                            map.put("ISN", isn);
                            map.put("DEVICE", rdevice);
                            map.put("STATUS", "2");
                            passForProcedure.procedureLogin(map);
                            map.put("STATUS", "1");
                            passForProcedure.procedureLogin(map);
                            passForProcedure.repair(map);
                        }
                    }
                    map.put("DEVICE", device);
                    map.put("STATUS", "1");
                    passForProcedure.procedureLogin(map);
                    if (isRepair) {
                        map.put("ISN", error);
                        passForProcedure.procedurePass(map);
                    }
                    if ("Y".equals(link_snf) || "Y".equals(link_sne)) {
                        item = jc1.getSelectedItem().toString();
                        if ("SP".equals(item)) {
                            item = "B";
                        } else if ("NDA".equals(item)) {
                            item = "W";
                        }
                        map.put("ISN", item);
                        passForProcedure.procedurePass(map);
                    }

                    if ("Y".equals(link_mce)) {
                        mce = jt4.getText();
                        map.put("ISN", mce);
                        passForProcedure.procedurePass(map);
                    }
                    map.put("ISN", isn);
                    flag = passForProcedure.procedurePass(map);
                    if (flag != null) {
                        chkstatus = flag.substring(5, 16);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            passForProcedure.close();
        }
        if (flag != null) {
            chk = flag.substring(2, 3);
            flag = flag.substring(5, flag.length());
        }
        if ("1".equals(chk)) {
            SoundUtil.PlayOk();
        } else {
            SoundUtil.PlayNo();
        }
        jt2.append(flag);
        jt1.setText("");
    }

    public String getErrorName(String errorId) {
        String errorName = null;
        errorName = passService.getErrorName(errorId);
        if (errorName == null) {
            JOptionPane.showConfirmDialog(null, "ErrorID不存在！請確認！", "警告", JOptionPane.PLAIN_MESSAGE);
            SoundUtil.PlayNo();
        } else {
            jl5.setText(errorName);
        }
        return errorName;
    }

    private static List<String> getMACAddress() {
        List<String> list = new ArrayList<>();
        try {
            Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
            while (enumeration.hasMoreElements()) {
                StringBuffer stringBuffer = new StringBuffer();
                NetworkInterface networkInterface = enumeration.nextElement();
                if (networkInterface != null) {
                    byte[] bytes = networkInterface.getHardwareAddress();
                    if (bytes != null) {
                        for (int i = 0; i < bytes.length; i++) {
                            if (i != 0) {
                                stringBuffer.append("-");
                            }
                            int tmp = bytes[i] & 0xff; // 字????整?
                            String str = Integer.toHexString(tmp);
                            if (str.length() == 1) {
                                stringBuffer.append("0" + str);
                            } else {
                                stringBuffer.append(str);
                            }
                        }
                        String mac = stringBuffer.toString().toUpperCase().replaceAll("-", ":");
                        list.add(mac);
                        System.out.println(mac);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
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
