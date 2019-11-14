package mes_sfis.client.ui.pack;

import base.client.ui.BasePanel;
import base.client.util.component.P_Component_Meta;
import base.vo.UI_InitVO;
import mes_sfis.client.model.service.PassService;
import mes_sfis.client.sfis.PassForProcedure;
import mes_sfis.client.task.PassDeviceTask;
import mes_sfis.client.task.base.LongTask;
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

/**
 * Created by Srx_Zhu on 2018/08/08.
 */
public class CncBatchPass extends BasePanel {
    public static final String VERSION = "$Name:  $, $Id: OQC2Pass.java,v 1.10 2018/08/13 09:28:13 SRX_zhu Exp $";
    public static final String COPYRIGHT = "Copyright (c) 2008 PEGATRON Inc. All Rights Reserved.";
    private static final Logger logger = LogManager.getLogger(PassDeviceTask.class);
    JPanel jp = null;
    JLabel jl6 = new JLabel();
    JButton jb3 = new JButton("鎖定ERROR CODE");
    JButton jb4 = new JButton("良品過站");
    JButton jb5 = new JButton("鎖定");
    JTextField jt1 = new JTextField("");
    JTextArea jt2 = new JTextArea("");
    JTextField jt3 = new JTextField("");
    JComboBox jc1 = new JComboBox();
    boolean isRepair = false;//判斷為刷ERROR還是良品過站
    PassForProcedure passForProcedure = new PassForProcedure();
    JScrollPane jsLog = new JScrollPane(jt2);
    String enterCode= "";
    String flag = null;
    String chkstatus = null;
    Timer timer;
    LongTask task;
    public final static int ONE_SECOND = 1000;
    String device = null;
    String repair = null;
    PassService passService;

    JLabel jl3 = new JLabel("數據輸入");
    JLabel jl4 = new JLabel("Error Code");
    JLabel jl5 = new JLabel("");
    JLabel jl7 = new JLabel("鋁錠供應商");

    public CncBatchPass(UI_InitVO uiVO) {
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
        jc1.addItem("K");
        jc1.addItem("H");
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
        this.add(jc1);
        this.add(jb5);
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

        /*jl1.setBounds(500, 220, 100, 30);
        jl2.setBounds(620, 220, 100, 30);*/
        jl3.setBounds(50, 220, 100, 30);
        jl4.setBounds(50, 160, 100, 30);
        jl5.setBounds(530, 190, 200, 30);
        jp.setBounds(20, 100, 700, 600);
        jl6.setBounds(100, 50, 500, 80);

        jb3.setBounds(260, 190, 150, 30);
        jb4.setBounds(420, 190, 100, 30);
        jt1.setBounds(50, 250, 700, 30);
        jt3.setBounds(50, 190, 200, 30);
        jsLog.setBounds(50, 300, 700, 400);
        jc1.setBounds(260,130,150,30);
        jb5.setBounds(420,130,100,30);
        jl7.setBounds(50,130,100,30);


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
                    try {
                        passForProcedure.open();

                        if (isRepair) {
                            map.put("ISN", jt3.getText());
                            passForProcedure.procedurePass(map);
                            map.put("ISN", jc1.getSelectedItem().toString());
                            passForProcedure.procedurePass(map);
                            map.put("ISN", jt1.getText());
                            flag = passForProcedure.procedurePass(map);
                            chkstatus = flag.substring(5, 16);
                        }else {
                            map.put("ISN", jc1.getSelectedItem().toString());
                            passForProcedure.procedurePass(map);
                            map.put("ISN", jt1.getText());
                            flag = passForProcedure.procedurePass(map);
                            chkstatus = flag.substring(5, 16);
                        }
                        while ("Login First".equals(chkstatus)) {
                            passForProcedure.procedureLogin(map);
                            if (isRepair) {
                                map.put("ISN", jt3.getText());
                                passForProcedure.procedurePass(map);
                                map.put("ISN", jc1.getSelectedItem().toString());
                                passForProcedure.procedurePass(map);
                                map.put("ISN", jt1.getText());
                                flag = passForProcedure.procedurePass(map);
                                chkstatus = flag.substring(5, 16);
                            }else {
                                map.put("ISN", jc1.getSelectedItem().toString());
                                passForProcedure.procedurePass(map);
                                map.put("ISN", jt1.getText());
                                flag = passForProcedure.procedurePass(map);
                                chkstatus = flag.substring(5, 16);
                            }
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
