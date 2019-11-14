package mes_sfis.client.ui.pack;

import base.client.ui.BasePanel;
import base.client.util.component.P_Component_Meta;
import base.vo.UI_InitVO;
import mes_sfis.client.model.service.OQC1PassService;
import mes_sfis.client.util.DataHandler;
import mes_sfis.client.util.JTextFieldHintListener;
import mes_sfis.client.util.ReadConfig2;
import mes_sfis.client.util.SoundUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.List;

/**
 * Created by Pino_Gao on 2018/8/21.
 */
public class SSYInPass extends BasePanel {
    public static final String VERSION = "$Name:  $, $Id: SSYInPass.java,v 1.00 2018/08/13 09:28:13 Pino_Gao Exp $";
    public static final String COPYRIGHT = "Copyright (c) 2008 PEGATRON Inc. All Rights Reserved.";
    OQC1PassService oqc1PassService;
    DataHandler dh;
    private JPanel contentPanel = super.UILayoutPanel;

    JPanel jp = null;
    JLabel jl6 = new JLabel("組立來料流程管制作業");
    JButton jba = new JButton("A");
    JButton jbb = new JButton("B");
    JButton jbc = new JButton("C");
    JButton jbd = new JButton("D");
    JButton jba1 = new JButton("A1");
    JTextField jt1 = new JTextField("");
    JTextArea jt2 = new JTextArea("");
    JTextField jt3 = new JTextField("");
    boolean isRepair = false;//判斷為刷ERROR還是良品過站
    boolean needRepair = false;//是否需要修
    boolean isOk= true;//過站是否成功
    JScrollPane jsLog = new JScrollPane(jt2);
    public final static int ONE_SECOND = 1000;
    String newline = "\n";
    String device = null;
    String repair = null;
    String btnname = "";

    //JLabel jl1 = new JLabel("不良名稱");
    JLabel jl2 = new JLabel("Error Code");
    JLabel jl3 = new JLabel("等級選擇");
    JLabel jl4 = new JLabel("inputData");
    JLabel jl5 = new JLabel("");

    public SSYInPass(UI_InitVO uiVO) {
        super(uiVO);
        oqc1PassService = new OQC1PassService(uiVO);
        dh = new DataHandler(uiVO);
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

    void init(){
        jt2.setEditable(false);
        jp = new JPanel();
        //this.add(jl1);
        this.add(jl2);
        this.add(jl3);
        this.add(jl4);
        this.add(jl6);
        this.add(jba);
        this.add(jbb);
        this.add(jbc);
        this.add(jbd);
        this.add(jba1);
        this.add(jt1);
        this.add(jt3);
        this.add(jl5);
        this.add(jsLog);
        jsLog.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jt1.addFocusListener(new JTextFieldHintListener(jt1, "請輸入ISN編號"));
        //jt3.addFocusListener(new JTextFieldHintListener(jt3, "請輸入Error Code"));

        jl6.setSize(500, 100);
        jl6.setFont(new java.awt.Font("Dialog", 1, 35));
        jl5.setBackground(Color.white);
        jt2.setLineWrap(true);
        this.add(jp);
        //jl1.setBounds(500, 220, 100, 30);
        jl2.setBounds(50, 160, 100, 30);
        jl3.setBounds(50, 220, 100, 30);
        jl4.setBounds(50, 280, 100, 30);
        jp.setBounds(20, 100, 700, 600);
        jl6.setBounds(100, 50, 500, 80);
        jba.setBounds(50, 250, 70, 30);
        jbb.setBounds(150, 250, 70, 30);
        jbc.setBounds(250, 250, 70, 30);
        jbd.setBounds(350, 250, 70, 30);
        jba1.setBounds(450, 250, 70, 30);
        jl5.setBounds(530, 190, 200, 30);
        jt1.setBounds(50, 310, 200, 30);
        jt3.setBounds(50, 190, 200, 30);
        //comboBox.setBounds(50,250,200,30);
        jsLog.setBounds(50, 370, 700, 400);
        jba.setBackground(Color.lightGray);
        jbb.setBackground(Color.lightGray);
        jbc.setBackground(Color.lightGray);
        jbd.setBackground(Color.lightGray);
        jba1.setBackground(Color.lightGray);

        jba.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                /*if(jt3.getText().equals("")){
                    //error?空
                    String isn = jt1.getText();


                    return;

                }*/

                changeButtenColor(jba);

            }
        });
        jbb.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                changeButtenColor(jbb);
            }
        });
        jbc.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                changeButtenColor(jbc);
            }
        });
        jbd.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                changeButtenColor(jbd);
            }
        });
        jba1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                changeButtenColor(jba1);
            }
        });

        jt3.addKeyListener(new KeyListener(){
            @Override
            public void keyPressed(KeyEvent arg0) {
                int key = arg0.getKeyCode();
                if (key == '\n') {

                }
            }

            @Override
            public void keyReleased(KeyEvent e1) {
                System.out.println("jt3aaa");
                System.out.println(jt3.getText());
                int key = e1.getKeyCode();
                if (key == '\n') {
                    System.out.println("[kaishi ]");
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

        jt1.addKeyListener(new KeyListener(){

            @Override
            public void keyTyped(KeyEvent e) {
                //System.out.println("typeed");

            }

            @Override
            public void keyPressed(KeyEvent e) {
                //System.out.println("pressed");

            }

            @Override
            public void keyReleased(KeyEvent e) {
                Color colora = jba.getBackground();
                Color colorb = jbb.getBackground();
                Color colorc = jbc.getBackground();
                Color colord = jbd.getBackground();
                Color colora1 = jba1.getBackground();

                if(colora.equals(Color.GREEN)){
                    btnname = jba.getText();
                }
                if(colorb.equals(Color.GREEN)){
                    btnname = jbb.getText();
                }
                if(colorc.equals(Color.GREEN)){
                    btnname = jbc.getText();
                }
                if(colord.equals(Color.GREEN)){
                    btnname = jbd.getText();
                }
                if(colora1.equals(Color.GREEN)){
                    btnname = jba1.getText();
                }
                //System.out.println(colora);
                int key = e.getKeyCode();
                if(key=='\n'){
                    LogInOut("2");
                    LogInOut("1");
                    needRepair = oqc1PassService.checkISNOk(jt1.getText());
                    System.out.println(needRepair);
                    if(jt3.getText().equals("")){
                        System.out.println(btnname);
                        System.out.println(jt1.getText());
                        PassDeviceSfisSoap(btnname);
                        PassDeviceSfisSoap(jt1.getText());
                    }else {
                        PassDeviceSfisSoap(jt3.getText());
                        PassDeviceSfisSoap(btnname);
                        PassDeviceSfisSoap(jt1.getText());
                    }
                    if(isOk){
                        SoundUtil.PlayOk();
                        System.out.println("ok");
                    }else{
                        SoundUtil.PlayNo();
                        System.out.println("no");
                    }
                    jt1.setText("");

                }

            }
        });
    }

    private void changeButtenColor(JButton jb) {
        //修改一下?式
        jba.setBackground(Color.lightGray);
        jbb.setBackground(Color.lightGray);
        jbc.setBackground(Color.lightGray);
        jbd.setBackground(Color.lightGray);
        jba1.setBackground(Color.lightGray);


        isRepair = true;
        jb.setBackground(Color.GREEN);
        //jb4.setBackground(Color.GREEN);
        jt3.setEditable(false);

    }


    public void LogInOut(String status) {
        List<String> list = new ArrayList<>();
        list = oqc1PassService.LogInOut(uiVO.getLogin_id(), device, status);
        if (list.get(0).equals("0")) {
            ///  jt2.append("-------------------------------------------------------------------------------------------------------------------------------------------" + "\n");
            /// jt2.setForeground(Color.red);
            // jt2.append((status.equals("1")) ? "登錄失敗" + "\n" : "登出失敗" + "\n");
        } else {
            /// jt2.append("-------------------------------------------------------------------------------------------------------------------------------------------" + "\n");
            // jt2.setForeground(Color.green);
            //jt2.append((status.equals("1")) ? "登錄成功" + "\n" : "登出成功" + "\n");
        }
    }

    public void PassDeviceSfisSoap(String isn) {
        java.util.List<String> list = new ArrayList<>();
        list = oqc1PassService.PassDeviceSfisSoap(isn, device);
        if (list.get(0).equals("0")) {
            jt2.append("-------------------------------------------------------------------------------------------------------------------------------------------" + "\n");
            if (isRepair) {
                jt2.append("刷Error失敗："+isn+"\n");
            } else {
                jt2.append("過站失敗：" + isn +"\n");
            }
            isOk=false;
        } else {
            jt2.append("-------------------------------------------------------------------------------------------------------------------------------------------" + "\n");
            if (isRepair) {
                jt2.append("刷Error成功：" + isn + "\n");
            } else {
                jt2.append("過站成功：" + isn + "\n");
            }
            isOk=true;
        }
        for (int i = 1; i < list.size(); i++) {
            jt2.append(list.get(i) + "\n");
        }
    }

    public void getErrorName(String errorId) {
        String errorName = null;
        System.out.println(errorId);
        errorName = oqc1PassService.getErrorName(errorId);

        System.out.println("////"+errorName);
        if (errorName == null) {
            System.out.println("555555");
            JOptionPane.showConfirmDialog(null, "ErrorID不存在！請確認！", "警告", JOptionPane.PLAIN_MESSAGE);
            SoundUtil.PlayNo();
        } else {
            System.out.println("text");
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
