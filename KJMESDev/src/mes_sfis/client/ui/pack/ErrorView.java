package mes_sfis.client.ui.pack;

import base.client.ui.BasePanel;
import base.client.util.component.P_Component_Meta;
import base.vo.UI_InitVO;
import mes_sfis.client.model.service.ErrorViewService;
import mes_sfis.client.task.PassDeviceTask;
import mes_sfis.client.task.base.LongTask;
import mes_sfis.client.util.DataHandler;
import mes_sfis.client.util.ReadConfig2;
import mes_sfis.client.util.SoundUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;


/**
 * Created by mark_yang on 2018/06/14.
 */
public class ErrorView extends BasePanel {
    public static final String VERSION    = "$Name:  $, $Id: ErrorView.java,v 1.19 2017/12/12 03:00:17 Lucy6_Lu Exp $";
    public static final String COPYRIGHT  = "Copyright (c) 2008 PEGATRON Inc. All Rights Reserved.";
    private static final Logger logger = LogManager.getLogger(ErrorView.class);
    JTextField jterror = new JTextField();
    JTextField jtisn = new JTextField();
    JTextArea jt2 = new JTextArea();
    JScrollPane jsCheckSSN = new JScrollPane(jt2);//設置滾動軸的
    JLabel jselect = null;
    JLabel jerror = null;
    JLabel jisn = null;
    JLabel jdevicenm = null;
    JButton jbsure = null;
    JPanel jp = null;
    String isnCode = "";
    String ierrorCode = "";
    String grp=null;
    String cnum=null;
    private  LongTask task;
    ErrorViewService errorViewService;
    DataHandler dh;
    UI_InitVO uiVO;
    Vector<Hashtable<String,String>> list=new Vector();
    HashMap map;
/*
   public static void main(String[] args) {

       //DataHandler dh2 = new DataHandler(uiVO2);

       *//* Hashtable<String,String>  test=new Hashtable();
        test.put("LINE","a001");
        list.add(new Hashtable());
        list.add(test);
        System.out.println(list);*//*
       try {
           errorViewService = new ErrorViewService(uiVO);
           list= errorViewService.findDeviceLine("1001");
       } catch (Exception e) {
           e.printStackTrace();
       }

       init(list);
    }*/

    public ErrorView(UI_InitVO uiVO) {
        super(uiVO);
        this.uiVO=uiVO;
        System.out.println("3333333");
        errorViewService = new ErrorViewService(uiVO);
        dh = new DataHandler(uiVO);
        ReadConfig2 a=new ReadConfig2();
        a.hashINI();
        try {
            cnum=a.iniHash.get("epega.exe.cnum").toString();
            //grp=a.iniHash.get("epega.exe.grp").toString();
            list= errorViewService.findDeviceLine(cnum);
        }catch (Exception e){
            JOptionPane.showConfirmDialog(null, "配置文件異常，請聯繫 OA     注： 配置文件格式為 UNICODE", "警告", JOptionPane.PLAIN_MESSAGE);
        }
        init(list);
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


    public void init(final Vector list) {
        // JFrame frame = new JFrame("1721入庫查驗");
        jp = new JPanel();
        jbsure=new JButton("確定");
        jselect = new JLabel("線別選擇:");
        jerror = new JLabel("ERROR:");
        jisn = new JLabel("ISN:");

        jisn.setBounds(100,160,50,30);
        jp.add(jisn);
        jerror.setBounds(100, 100, 50, 30);
        jp.add(jerror);
        jselect.setBounds(100,30,60,30);
        jp.add(jselect);


        final JComboBox comboBox=new JComboBox();
        comboBox.setBounds(180,30,140,30);
        for (int i=1;i<list.size();i++){
            Hashtable ht= (Hashtable) list.get(i);
            //   System.out.println(ht.get("DEVICE"));
            comboBox.addItem(ht.get("LINE")+"("+ht.get("DEVICE")+")");
        }

        jp.add(comboBox);
        Hashtable htshow= (Hashtable) list.get(comboBox.getSelectedIndex()+1);
        jdevicenm=new JLabel(htshow.get("DEVICENM").toString());
        jdevicenm.setBounds(460,30,150,30);
        jp.add(jdevicenm);
        jbsure.setBounds(360,30,80,30);
        jp.add(jbsure);
        jt2.setFont(new Font("宋体",Font.BOLD, 16));
        jt2.setLineWrap(true);//自動換行
        jp.add(jt2);
        jsCheckSSN.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jsCheckSSN.setFont(new Font("宋体",Font.BOLD, 16));
        jp.add(jsCheckSSN);
        jp.setLayout(null);
        jp.setSize(720, 700);
        jtisn.setBounds(160, 160, 350, 30);
        jtisn.setEditable(false);
        jp.add(jtisn);
        jterror.setBounds(160, 100, 350, 30);
        jterror.setEditable(false);
        jp.add(jterror);
        jsCheckSSN.setBounds(100,200,520,150);
        add(jp);
        comboBox.addActionListener(new ActionListener() {


            @Override
            public void actionPerformed(ActionEvent e) {
                Hashtable ht= (Hashtable) list.get(comboBox.getSelectedIndex()+1);

                String devicenm= ht.get("DEVICENM").toString();
                jdevicenm.setText(devicenm);
                //  System.out.println(comboBox.getSelectedIndex()+devicenm+ht.get("DEVICE").toString()+comboBox.getSelectedItem());
            }

        });
        jbsure.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // jbsure.setForeground(new Color(59, 60, 204));// ?置字体?色
                jbsure.setBorderPainted(true);
                jterror.setEditable(true);
                jtisn.setEditable(false);
                jterror.requestFocus();


            }
        });
        jterror.addKeyListener(new KeyListener() {
            String device;
            @Override
            public void keyPressed(KeyEvent arg0) {
                int key = arg0.getKeyCode();
                ierrorCode = jterror.getText().toString().trim();
                if (key == '\n') {
                    map=new HashMap();
                    Hashtable ht= (Hashtable) list.get(comboBox.getSelectedIndex()+1);
                    device= ht.get("DEVICE").toString();


                }
            }

            @Override
            public void keyReleased(KeyEvent e1) {
                int key = e1.getKeyCode();
                List passRouteMsg=null;
                if (key == '\n') {
                    jtisn.setEditable(true);
                    jtisn.requestFocus();
                    jterror.setEditable(false);
                    try {
                        passRouteMsg= errorViewService.passRoute(device,ierrorCode,uiVO.getLogin_id());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if("0".equals(passRouteMsg.get(0))){
                        logger.debug("過站失敗:"+passRouteMsg.get(1));
                        SoundUtil.PlayNo();
                        String ok="";
                        // JOptionPane.showConfirmDialog(null, "過站失敗:"+passRouteMsg.get(1), "警告", JOptionPane.PLAIN_MESSAGE);
                        for(int i =1;i<passRouteMsg.size();i++){
                            ok+=passRouteMsg.get(i).toString();
                        }
                        jt2.setText("過站失敗:"+ok);
                        jt2.setForeground(Color.red);

                    }else{

                        logger.debug("過站成功:"+passRouteMsg.get(1));
                        SoundUtil.PlayOk();
                        String no ="";
                        for(int i =1;i<passRouteMsg.size();i++){
                            no+=passRouteMsg.get(i).toString();
                        }
                        jt2.setText("過站成功:"+no);
                        jt2.setForeground(Color.black);

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

        jtisn.addKeyListener(new KeyListener() {
            String device;
            @Override
            public void keyTyped(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == '\n') {
                    JOptionPane.showMessageDialog(null, "undefined");

                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                isnCode = jtisn.getText().toString().trim();
                List passRouteMsg = null;
                device = null;
                if (key == '\n') {
                    Hashtable ht= (Hashtable) list.get(comboBox.getSelectedIndex()+1);
                    device= ht.get("DEVICE").toString();

                    try {
                        passRouteMsg = errorViewService.passRoute(device, isnCode, uiVO.getLogin_id());
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }

                    if ("0".equals(passRouteMsg.get(0))) {
                        logger.debug("過站失敗:" + passRouteMsg.get(1));
                        SoundUtil.PlayNo();
                        String ok = "";
                        // JOptionPane.showConfirmDialog(null, "過站失敗:"+passRouteMsg.get(1), "警告", JOptionPane.PLAIN_MESSAGE);
                        for (int i = 1; i < passRouteMsg.size(); i++) {
                            ok += passRouteMsg.get(i).toString();
                        }
                        jt2.setText("過站失敗:" + ok);
                        jt2.setForeground(Color.red);
                    } else {
                        logger.debug("過站成功:" + passRouteMsg.get(1));
                        SoundUtil.PlayOk();
                        String no = "";
                        for (int i = 1; i < passRouteMsg.size(); i++) {
                            no += passRouteMsg.get(i).toString();
                        }
                        jt2.setText("過站成功:" + no);
                        jt2.setForeground(Color.black);
                    }


                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == '\n') {
                    jterror.setEditable(true);
                    jterror.requestFocus();
                    jtisn.setEditable(false);
                    jterror.setText("");
                    jtisn.setText("");
                }
            }
        });

    }


 /*   public JTextField textSet(JTextField field) {
        field.setBackground(new Color(255, 237, 193));
        field.setPreferredSize(new Dimension(150, 28));
        MatteBorder border = new MatteBorder(0, 0, 2, 0, new Color(39, 50, 75));
        field.setBorder(border);
        return field;
    }*/
}

