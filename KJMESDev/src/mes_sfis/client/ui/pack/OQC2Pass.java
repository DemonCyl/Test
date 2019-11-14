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
    JButton jb3 = new JButton("��wERROR CODE");
    JButton jb4 = new JButton("�}�~�L��");
    JTextField jt1 = new JTextField("");
    JTextArea jt2 = new JTextArea("");
    JTextField jt3 = new JTextField("");
    JTextField jt4 = new JTextField("");
    JTextField jt5 = new JTextField("");
    boolean isRepair = false;//�P�_����ERROR�٬O�}�~�L��
    boolean isOk= true;//�L���O�_���\
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

    JLabel jl3 = new JLabel("�ƾڿ�J");
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
            JOptionPane.showConfirmDialog(null, "�t�m��󲧱`�A���pô OA     �`�G �t�m���榡�� UNICODE", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
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
            JOptionPane.showConfirmDialog(null, "�Х��T�t�mdevice", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
            return;
        }
        String deviceNm = passService.findNameByDevice(device);
        if("".equals(deviceNm)||deviceNm==null){
            JOptionPane.showConfirmDialog(null, "�������������I�W��", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
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
        jt1.addFocusListener(new JTextFieldHintListener(jt1, "�п�J���~�Ǹ��A�p��ܢսХ���JERROR CODE�^����A��J���~�Ǹ�"));
        jt3.addFocusListener(new JTextFieldHintListener(jt3, "�п�JError Code"));
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
                //jl2.setText("<html>W       (���� : ISN�b�ƾڮw��SNE�r�q��W�Awhite�A�զ�)<br>JPT4      (�����G���~���զ�A���~�X�]�tJPT4)</html>");
                jb1.setForeground(new Color(43, 63, 0xcc));// ?�m�r�^?��
                jb1.setBorderPainted(true);
                jb2.setForeground(new Color(93, 87, 86));// ?�m�r�^?��
                jb2.setBorderPainted(true);
                chooseSnc = "NDA";
                chooseSne = "W";
                chooseJpt="JPT4|JVMH";
            }
        });
        jb2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //jl2.setText("<html>B      (���� : ISN�b�ƾڮw��SNE�r�q��B�Ablack�A�¦�) <br>JPT3      (�����G���~���¦�A���~�X�]�tJPT3)</html>");
                jb2.setForeground(new Color(59, 60, 204));// ?�m�r�^?��
                jb2.setBorderPainted(true);
                jb1.setForeground(new Color(93, 87, 86));// ?�m�r�^?��
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
                    JOptionPane.showConfirmDialog(null, "�Х���J���T��Error Code", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
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
                        JOptionPane.showConfirmDialog(null, "���i��J���", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
                        SoundUtil.PlayNo();
                        return;
                    }
                    Hashtable data = null;
                    data = passService.getAllConfig(enterCode);
                    if (data == null) {
                        JOptionPane.showConfirmDialog(null, "�d�L���X�A�Юֹ�I�I�I", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
                        return;
                    }
                    String SSN = "";
                    String SNE = "";
                    String isn = "";
                    if(data.get("SSN")==null){
                        JOptionPane.showConfirmDialog(null, "�ﲣ�~���~�X���šA�нT�{�I", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
                        return;
                    }else{
                        SSN = data.get("SSN").toString();
                    }
/*                    if(data.get("SNC")==null){
                        JOptionPane.showConfirmDialog(null, "�ﲣ�~�C��|���]�w�A�нT�{�I", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
                        return;
                    }else{
                        SNC = data.get("SNC").toString();
                    }*/
                    if(data.get("SNE")==null){
                        JOptionPane.showConfirmDialog(null, "�ﲣ�~�C��|���]�w�A�нT�{�I", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
                        return;
                    }else{
                        SNE = data.get("SNE").toString();
                    }
                    if(data.get("ISN")==null){
                        JOptionPane.showConfirmDialog(null, "�ﲣ�~ISN���šA�нT�{�I", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
                        return;
                    }else{
                        isn = data.get("ISN").toString();

                    }
                    if(chooseJpt.equals("")){
                        JOptionPane.showConfirmDialog(null, "�Х�����C��I", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
                        return;
                    }
                    /*if (SSN.length() != 62) {
                        JOptionPane.showConfirmDialog(null, "���~���~�X�]SSN�^���ŦX62����סI�Юֹ�", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
                        return;
                    }*/
                    eeee = SSN.substring(11,15);
                    if(!eeee.matches(chooseJpt)){
                        JOptionPane.showConfirmDialog(null, "���~�X�s�X���t  " + chooseJpt + "  �P�襤�C�⤣��", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
                        return;
                    }
/*                    if (!SNC.contains(chooseSnc)) {
                        JOptionPane.showConfirmDialog(null, "���~�C�⬰  " + SNC + "  �P�襤�C�⤣��", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
                        return;
                    }*/
                    if (!SNE.contains(chooseSne)) {
                        JOptionPane.showConfirmDialog(null, "���~�C�⬰  " + SNE + "  �P�襤�C�⤣��", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
                        return;
                    }
                    jt4.setText("");
                    jt5.setText("");
                    int umpResult = passService.umpNGCount(isn);
                    if(umpResult>0){
                        jt4.setText("�����eNG "+umpResult+"�����u��");
                    }
                    int altResult = passService.altNGCount(isn);
                    if(altResult>0){
                        jt5.setText("��KNG "+altResult+"�����u��");
                    }
                    int result = passService.checkUmpT3Pass(isn);
                    try {
                        passForProcedure.open();
                        if (result > 0) {
                            map.put("DEVICE", "999049");
                            map.put("STATUS", "2");
                            passForProcedure.procedureLogin(map);//�n�X
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
                                passForProcedure.procedureLogin(map);//�n�X
                                map.put("STATUS", "1");//�n?
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
                        //�����ƾ��챵
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
            JOptionPane.showConfirmDialog(null, "ErrorID���s�b�I�нT�{�I", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
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