package mes_sfis.client.ui.pack;

import base.client.ui.BasePanel;
import base.client.util.component.P_Component_Meta;
import base.vo.UI_InitVO;
import mes_sfis.client.model.service.BatchPassService;
import mes_sfis.client.model.service.PassService;
import mes_sfis.client.sfis.PassForProcedure;
import mes_sfis.client.task.PassDeviceTask;
import mes_sfis.client.task.base.LongTask;
import mes_sfis.client.util.GetHostInfo;
import mes_sfis.client.util.JTextFieldHintListener;
import mes_sfis.client.util.ReadConfig2;
import mes_sfis.client.util.SoundUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

/**
 * Created by Srx_Zhu on 2018/08/08.
 */
public class OQC2Pass extends BasePanel {
    public static final String VERSION = "$Name:  $, $Id: OQC2Pass.java,v 1.10 2018/08/13 09:28:13 SRX_zhu Exp $";
    public static final String COPYRIGHT = "Copyright (c) 2008 PEGATRON Inc. All Rights Reserved.";
    private static final Logger logger = LogManager.getLogger(PassDeviceTask.class);
    JPanel jp = null;
    JButton jb1 = null;
    JButton jb2 = null;
    JLabel jl6 = new JLabel();
    JButton jb3 = new JButton("鎖定ERROR CODE");
    JButton jb4 = new JButton("良品過站");
    JTextField jt1 = new JTextField("");
    JTextArea jt2 = new JTextArea("");
    JTextField jt3 = new JTextField("");
    JTextField jt4 = new JTextField("");
    JTextField jt5 = new JTextField("");
    boolean isRepair = false;//判斷為刷ERROR還是良品過站
    boolean isOk= true;//過站是否成功
    PassForProcedure passForProcedure = new PassForProcedure();
    JScrollPane jsLog = new JScrollPane(jt2);
    String chooseSne = "";
    String chooseSnc = "";
    String chooseJpt = "";
    String enterCode= "";
    String flag = null;
    String  eeee = null;
    String chkstatus = null;
    ProgressMonitor progressMonitor;
    Timer timer;
    LongTask task;
    BatchPassService cws;
    public final static int ONE_SECOND = 1000;
    String newline = "\n";
    String device = null;
    String repair = null;
    PassService passService;

    JLabel jl3 = new JLabel("數據輸入");
    JLabel jl4 = new JLabel("Error Code");
    JLabel jl5 = new JLabel("");

    public OQC2Pass(UI_InitVO uiVO) {
        super(uiVO);
        this.uiVO = uiVO;
        passService = new PassService(uiVO);
        ReadConfig2 a = new ReadConfig2();
        a.hashINI();
        try {
            device = a.iniHash.get("epega.exe.device").toString();
            repair = a.iniHash.get("epega.exe.repair").toString();
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, "配置文件異常，請聯繫 OA     注： 配置文件格式為 UNICODE", "警告", JOptionPane.PLAIN_MESSAGE);
        }
        init();
    }


    public void init() {
        passForProcedure.open();
        final HashMap map = new HashMap();
        map.put("DEVICE", device);
        map.put("OP", uiVO.getLogin_id());
        map.put("STATUS", "2");
        passForProcedure.procedureLogin(map);
        map.put("STATUS", "1");
        passForProcedure.close();
        if("".equals(device)){
            JOptionPane.showConfirmDialog(null, "請正確配置device", "警告", JOptionPane.PLAIN_MESSAGE);
            return;
        }
        String deviceNm = passService.findNameByDevice(device);
        if("".equals(deviceNm)||deviceNm==null){
            JOptionPane.showConfirmDialog(null, "未找到對應的站點名稱", "警告", JOptionPane.PLAIN_MESSAGE);
            return;
        }
        jl6.setText(deviceNm);

        jt2.setEditable(false);
        jt4.setEditable(false);
        jt5.setEditable(false);
        jp = new JPanel();

        jb1 = new JButton("NDA");
        jb2 = new JButton("SP28");
        /*this.add(jl1);
        this.add(jl2);*/
        this.add(jl3);
        this.add(jl4);
        this.add(jl5);
        this.add(jl6);
        this.add(jb1);
        this.add(jb2);
        this.add(jb3);
        this.add(jb4);
        this.add(jt1);
        this.add(jsLog);
        this.add(jt3);
        this.add(jt4);
        this.add(jt5);
        jsLog.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jt1.addFocusListener(new JTextFieldHintListener(jt1, "請輸入產品序號，如刷ＮＧ請先輸入ERROR CODE回車後再輸入產品序號"));
        jt3.addFocusListener(new JTextFieldHintListener(jt3, "請輸入Error Code"));
        jl6.setSize(500, 100);
        jl6.setFont(new java.awt.Font("Dialog", 1, 35));
        jl5.setBackground(Color.white);
        jt2.setLineWrap(true);
        this.add(jp);

        /*jl1.setBounds(500, 220, 100, 30);
        jl2.setBounds(620, 220, 100, 30);*/
        jl3.setBounds(50, 220, 100, 30);
        jl4.setBounds(50, 160, 100, 30);
        jl5.setBounds(530, 190, 200, 30);
        jp.setBounds(20, 100, 700, 600);
        jl6.setBounds(100, 50, 500, 80);
        jb1.setBounds(50, 130, 100, 30);
        jb2.setBounds(200, 130, 100, 30);
        jb3.setBounds(260, 190, 150, 30);
        jb4.setBounds(420, 190, 100, 30);
        jt1.setBounds(50, 250, 700, 30);
        jt3.setBounds(50, 190, 200, 30);
        jt4.setBounds(550, 130, 200, 30);
        jt5.setBounds(550, 190, 200, 30);
        jsLog.setBounds(50, 300, 700, 400);

        /*jl1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                try {
                    Runtime.getRuntime().exec("cmd.exe /c start " + "http://mes.ch.casetekcorp.com/MES/view/ssntracking/tracking.jsp");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });*/
        /*jl2.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                try {
                    Runtime.getRuntime().exec("cmd.exe /c start " + "http://mes.ch.casetekcorp.com/MES/view/altquery/inquiriesALT.jsp");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });*/

        List<String> list = new ArrayList<>();
        list =  GetHostInfo.getMACAddress();
        String message = "[";
        for(int i = 0;i<list.size();i++){
            if(i!=list.size()-1){
                message+= list.get(i)+",";
            }else {
                message+=list.get(i)+"]";
            }
        }
        String hostname = GetHostInfo.getLocalHostName();
        HashMap<String, String> info = new HashMap<>();
        info.put("MESSAGE",message);
        info.put("OP", uiVO.getLogin_id());
        info.put("DEVICE", device);
        info.put("HOSTNAME", hostname);
        passService.insertLogininfo(info);
        jb1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //jl2.setText("<html>W       (說明 : ISN在數據庫的SNE字段為W，white，白色)<br>JPT4      (說明：產品為白色，成品碼包含JPT4)</html>");
                jb1.setForeground(new Color(43, 63, 0xcc));// ?置字体?色
                jb1.setBorderPainted(true);
                jb2.setForeground(new Color(93, 87, 86));// ?置字体?色
                jb2.setBorderPainted(true);
                chooseSnc = "NDA";
                chooseSne = "W";
                chooseJpt="JPT4|JVMH";
            }
        });
        jb2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //jl2.setText("<html>B      (說明 : ISN在數據庫的SNE字段為B，black，黑色) <br>JPT3      (說明：產品為黑色，成品碼包含JPT3)</html>");
                jb2.setForeground(new Color(59, 60, 204));// ?置字体?色
                jb2.setBorderPainted(true);
                jb1.setForeground(new Color(93, 87, 86));// ?置字体?色
                jb1.setBorderPainted(true);
                chooseSnc = "SP28";
                chooseSne = "B";
                chooseJpt="JPT3|JVMG";
            }
        });

        jb3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jl5.getText().equals("")) {
                    JOptionPane.showConfirmDialog(null, "請先輸入正確的Error Code", "警告", JOptionPane.PLAIN_MESSAGE);
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
                jb3.setBackground(Color.WHITE);
                jb4.setBackground(Color.GREEN);
                jt3.setEditable(true);
            }
        });
        jt1.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent arg0) {
                int key = arg0.getKeyCode();
                if (key == '\n') {

                }
            }

            @Override
            public void keyReleased(KeyEvent e1) {
                int key = e1.getKeyCode();
                enterCode = jt1.getText().toString().replace(" ", "");
                if (key == '\n') {
                    jt2.setText("");
                    if ("".equals(enterCode)) {
                        JOptionPane.showConfirmDialog(null, "不可輸入位空", "警告", JOptionPane.PLAIN_MESSAGE);
                        SoundUtil.PlayNo();
                        return;
                    }
                    Hashtable data = null;
                    data = passService.getAllConfig(enterCode);
                    if (data == null) {
                        JOptionPane.showConfirmDialog(null, "查無此碼，請核對！！！", "警告", JOptionPane.PLAIN_MESSAGE);
                        return;
                    }
                    String SSN = "";
                    String SNE = "";
                    String isn = "";
                    if(data.get("SSN")==null){
                        JOptionPane.showConfirmDialog(null, "改產品成品碼為空，請確認！", "警告", JOptionPane.PLAIN_MESSAGE);
                        return;
                    }else{
                        SSN = data.get("SSN").toString();
                    }
/*                    if(data.get("SNC")==null){
                        JOptionPane.showConfirmDialog(null, "改產品顏色尚未設定，請確認！", "警告", JOptionPane.PLAIN_MESSAGE);
                        return;
                    }else{
                        SNC = data.get("SNC").toString();
                    }*/
                    if(data.get("SNE")==null){
                        JOptionPane.showConfirmDialog(null, "改產品顏色尚未設定，請確認！", "警告", JOptionPane.PLAIN_MESSAGE);
                        return;
                    }else{
                        SNE = data.get("SNE").toString();
                    }
                    if(data.get("ISN")==null){
                        JOptionPane.showConfirmDialog(null, "改產品ISN為空，請確認！", "警告", JOptionPane.PLAIN_MESSAGE);
                        return;
                    }else{
                        isn = data.get("ISN").toString();

                    }
                    if(chooseJpt.equals("")){
                        JOptionPane.showConfirmDialog(null, "請先選擇顏色！", "警告", JOptionPane.PLAIN_MESSAGE);
                        return;
                    }
                    /*if (SSN.length() != 62) {
                        JOptionPane.showConfirmDialog(null, "產品成品碼（SSN）不符合62位長度！請核對", "警告", JOptionPane.PLAIN_MESSAGE);
                        return;
                    }*/
                    eeee = SSN.substring(11,15);
                    if(!eeee.matches(chooseJpt)){
                        JOptionPane.showConfirmDialog(null, "成品碼編碼不含  " + chooseJpt + "  與選中顏色不符", "警告", JOptionPane.PLAIN_MESSAGE);
                        return;
                    }
/*                    if (!SNC.contains(chooseSnc)) {
                        JOptionPane.showConfirmDialog(null, "產品顏色為  " + SNC + "  與選中顏色不符", "警告", JOptionPane.PLAIN_MESSAGE);
                        return;
                    }*/
                    if (!SNE.contains(chooseSne)) {
                        JOptionPane.showConfirmDialog(null, "產品顏色為  " + SNE + "  與選中顏色不符", "警告", JOptionPane.PLAIN_MESSAGE);
                        return;
                    }
                    jt4.setText("");
                    jt5.setText("");
                    int umpResult = passService.umpNGCount(isn);
                    if(umpResult>0){
                        jt4.setText("內長寬NG "+umpResult+"次重工料");
                    }
                    int altResult = passService.altNGCount(isn);
                    if(altResult>0){
                        jt5.setText("氣密NG "+altResult+"次重工料");
                    }
                    int result = passService.checkUmpT3Pass(isn);
                    try {
                        passForProcedure.open();
                        if (result > 0) {
                            map.put("DEVICE", "999049");
                            map.put("STATUS", "2");
                            passForProcedure.procedureLogin(map);//登出
                            map.put("STATUS", "1");
                            passForProcedure.procedureLogin(map);
                            map.put("ISN", jt1.getText());
                            passForProcedure.procedurePass(map);
                        }
                        map.put("DEVICE", device);
                        if (isRepair) {
                            map.put("ISN", jt3.getText());
                            passForProcedure.procedurePass(map);
                        }
                        map.put("ISN", isn);
                        flag = passForProcedure.procedurePass(map);
                        chkstatus = flag.substring(5, 16);
                        while ("Login First".equals(chkstatus)) {
                            passForProcedure.procedureLogin(map);
                            if (result > 0) {
                                map.put("DEVICE", "999049");
                                map.put("STATUS", "2");
                                passForProcedure.procedureLogin(map);//登出
                                map.put("STATUS", "1");//登?
                                passForProcedure.procedureLogin(map);
                                map.put("ISN", jt1.getText());
                                passForProcedure.procedurePass(map);
                            }
                            map.put("DEVICE", device);
                            if (isRepair) {
                                map.put("ISN", jt3.getText());
                                passForProcedure.procedurePass(map);
                            }
                            map.put("ISN", isn);
                            flag = passForProcedure.procedurePass(map);
                            chkstatus = flag.substring(5, 16);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        //關閉數據鏈接
                        passForProcedure.close();
                    }
                    String chk = flag.substring(2,3);

                    if("1".equals(chk)){
                        SoundUtil.PlayOk();
                    }else{
                        SoundUtil.PlayNo();
                    }
                    jt2.append(flag);
                    jt1.setText("");

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

    public void getErrorName(String errorId) {
        String errorName = null;
        errorName = passService.getErrorName(errorId);
        if (errorName == null) {
            JOptionPane.showConfirmDialog(null, "ErrorID不存在！請確認！", "警告", JOptionPane.PLAIN_MESSAGE);
            SoundUtil.PlayNo();
        } else {
            jl5.setText(errorName);
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
