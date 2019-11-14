package mes_sfis.client.ui.pack;

import base.client.ui.BasePanel;
import base.client.util.component.P_Component_Meta;
import base.vo.UI_InitVO;
import mes_sfis.client.model.service.BadnessPackCartonService;
import mes_sfis.client.util.*;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by Pino_Gao on 2018/9/4.
 */
public class BadnessPackCarton extends BasePanel {
    public static final String VERSION = "$Name: $, $Id: BadnessPackCarton.java,v 1.01 2018/08/29 08:52:07 Pino_Gao Exp $";
    public static final String COPYRIGHT = "Copyright (c) 2008 PEGATRON Inc. All Rights Reserved.";
    DataHandler dh;
    BadnessPackCartonService badnessPackCartonService;
    JPanel contentPanel = super.UILayoutPanel;
    JLabel jl1 = new JLabel("SSN�G");
    JLabel jl2 = new JLabel("�п�JERROR");
    JLabel jl3 = new JLabel("�п�ܯ��I");
    JLabel jl4 = new JLabel("");
    JLabel jl5 = new JLabel("�п��ROUTE");
    JTextField jt1 = new JTextField();
    JTextField jt2 = new JTextField();
    JTextField jt3 = new JTextField();
    JComboBox jc1 = new JComboBox();
    JComboBox jc2 = new JComboBox();
    JList jList1;
    ListModel model;
    String sum = null;
    String opnum = null;
    String times = null;
    DefaultListModel listModel1 = new DefaultListModel();
    DefaultListModel listModel2 = new DefaultListModel();
    DefaultListModel listModel3 = new DefaultListModel();
    JButton printjb = new JButton("���L");
    JButton del = new JButton("�R��");
    List<String> listIsn = new ArrayList<>();
    JScrollPane s;
    String path = "D:\\mes_data\\configs";
    String error_code = "";

    public BadnessPackCarton(UI_InitVO uiVO) {
        super(uiVO);
        //uiVO.getLogin_id();
        badnessPackCartonService = new BadnessPackCartonService(uiVO);
        dh = new DataHandler(uiVO);
        createDir(path);
        String file1 = "ts24.lib";
        String file2 = "PegaBase.ini";
        File file3 = new File(path + "\\ts24.lib");
        File file4 = new File(path + "\\PegaBase.ini");
        if (!file3.exists()) {
            uploadFiles(file1);
        }
        if (!file4.exists()) {
            uploadFiles(file2);
        }
        ReadConfig3 a = new ReadConfig3();
        a.hashINI();
        try {
            sum = a.iniHash.get("epega.exe.sum").toString();
            opnum = a.iniHash.get("epega.exe.opnum").toString();
            times = a.iniHash.get("epega.exe.times").toString();

        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, "�t�m��󲧱`�A���pô OA     �`�G �t�m���榡�� UNICODE", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
        }
        init();
    }

    private boolean createDir(String path) {
        File dir = new File(path);
        if (dir.exists()) {
            System.out.println("�Ыإؿ�" + path + "���ѡA�ؼХؿ��w�s�b");
            return false;
        }
        if (!path.endsWith(File.separator)) {
            path = path + File.separator;
        }
        if (dir.mkdirs()) {
            System.out.println("�Ыإؿ�" + path + "���\�I");
            return true;
        } else {
            System.out.println("�Ыإؿ�" + path + "���ѡI");
            return false;
        }
    }

    private void uploadFiles(String file1) {
        URL website = null;
        try {
            website = new URL(MESGlobe.PEGA_URL + "/configs/" + file1);
            System.out.println(website);
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = new FileOutputStream("D:\\mes_data\\configs\\" + file1);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void init() {
        //listModel1 = new DefaultListModel();
        jt3.setEditable(false);
        jList1 = new JList(listModel3);
        s = new JScrollPane(jList1);
        s.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jl1.setBounds(150, 50, 50, 30);
        jl2.setBounds(20, 50, 100, 30);
        jl3.setBounds(20, 300, 100, 30);
        jl4.setBounds(20, 115, 100, 30);
        jl5.setBounds(20, 200, 100, 30);
        jt1.setBounds(200, 50, 220, 30);
        jList1.setBounds(150, 100, 350, 350);
        s.setBounds(200, 90, 270, 350);
        jt2.setBounds(20, 85, 120, 30);
        del.setBounds(200, 480, 70, 30);
        printjb.setBounds(300, 480, 70, 30);
        jt3.setBounds(435, 50, 50, 30);
        jc1.setBounds(20, 340, 180, 30);
        jc2.setBounds(20, 235, 180, 30);


        List<Hashtable> list2 = badnessPackCartonService.getAllRoute();

        for (Hashtable ht : list2) {
            jc2.addItem(ht.get("ROUTE"));
        }
        final List<Hashtable> list = badnessPackCartonService.getAllDevice(jc2.getSelectedItem().toString());
        if (list == null) {
            jc1.removeAllItems();
            JOptionPane.showConfirmDialog(null, jc1.getItemCount(), "ĵ�i", JOptionPane.PLAIN_MESSAGE);
        } else {
            for (Hashtable ht : list) {
                jc1.addItem(ht.get("STEPNM"));
            }
        }
        this.add(jl1);
        this.add(jl2);
        this.add(jl3);
        this.add(jl4);
        this.add(jl5);
        this.add(jt1);
        this.add(del);
        this.add(printjb);
        this.add(s);
        this.add(jt2);
        this.add(jt3);
        this.add(jc1);
        this.add(jc2);


        jt2.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == '\n') {
                    String errorName = "";
                    errorName = badnessPackCartonService.getErrorName(jt2.getText());
                    if (errorName.equals("")) {
                        JOptionPane.showConfirmDialog(null, "ERROR��J���~�A���ˬd�I", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
                        jt2.setText("");
                        SoundUtil.PlayNo();
                        return;
                    } else {
                        error_code = jt2.getText();
                        jl4.setText(errorName);
                    }
                }
            }
        });


        jt1.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == '\n') {
                    String ssn = jt1.getText();
                    //�P�_isn�O�_�s�b
                    if (jc2.getItemCount() == 0) {
                        JOptionPane.showConfirmDialog(null, "���I���i����", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
                        SoundUtil.PlayNo();
                        return;
                    }
                    if (error_code.equals("")) {
                        JOptionPane.showConfirmDialog(null, "ERROR���i����", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
                        SoundUtil.PlayNo();
                        return;
                    }
                    String isn = badnessPackCartonService.getIsnByOther(ssn);
                    if("".equals(isn)){
                        JOptionPane.showConfirmDialog(null, "�Ӥ����~�X���s�b", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
                        SoundUtil.PlayNo();
                        return;
                    }

                    boolean isIsn = listModel1.contains(isn);
                    if (isIsn) {
                        JOptionPane.showConfirmDialog(null, "�Фŭ��ƲK�[�I�I�I", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
                        SoundUtil.PlayNo();
                        return;
                    }
                    if (listModel1.size() < 180) {
                        boolean flag = badnessPackCartonService.findAliveIsn(isn);
                        //�P�_isn�O�_�O�X�w���A
                        int status = badnessPackCartonService.findPacketStatus(isn);
                        if (!flag) {
                            JOptionPane.showConfirmDialog(null, "���s�b�Ӥ�ISN�I�I�I", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
                            SoundUtil.PlayNo();
                            return;
                        }
                        if (status > 0) {
                            JOptionPane.showConfirmDialog(null, "�Ӥ�ISN�w�X�w�Τw�˽c�I�I�I", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
                            SoundUtil.PlayNo();
                            return;
                        }
                        Hashtable ht = badnessPackCartonService.checkISNError(isn);
                        if (ht == null) {
                            JOptionPane.showConfirmDialog(null, "�Ӥ�ISN�LERROR�I�I�I", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
                            SoundUtil.PlayNo();
                            return;
                        }
                        String error = ht.get("ERROR").toString();
                        if (!error.equals(error_code)) {
                            JOptionPane.showConfirmDialog(null, "��ISN��ERROR��  " + error + "   �P�ӽc�]�w  " + error_code + "  ���šI�I�I", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
                            SoundUtil.PlayNo();
                            return;
                        }
                        String step = badnessPackCartonService.CheckISNStep(jc2.getSelectedItem().toString(), isn);
                        if (!step.equals(jc1.getSelectedItem())) {
                            JOptionPane.showConfirmDialog(null, "��ISN�����I��  " + step + "  �P�ӽc�]�w���I  " + jc1.getSelectedItem() + "  ���šI�I�I", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
                            SoundUtil.PlayNo();
                            return;
                        }
                        String route = badnessPackCartonService.CheckISNRoute(isn);
                        if (!route.equals(jc2.getSelectedItem())) {
                            JOptionPane.showConfirmDialog(null, "��ISN��ROUTE��  " + route + "  �P�ӽc�]�wROUTE:  " + jc2.getSelectedItem() + "  ���šI�I�I", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
                            SoundUtil.PlayNo();
                            return;
                        }

                        String cssn = badnessPackCartonService.getCSSNByISN(isn);
                        if (cssn.equals("")) {
                            JOptionPane.showConfirmDialog(null, "��ISN²���X���`�A�нT�{�I�I�I", "ĵ�i", JOptionPane.PLAIN_MESSAGE);

                            return;
                        }
                        jc1.setEnabled(false);
                        jc2.setEnabled(false);
                        jt2.setEditable(false);
                        listModel1.addElement(isn);
                        listModel2.addElement(cssn);
                        listModel3.addElement(jt1.getText());

                        SoundUtil.PlayOk();
                        if (listModel1 != null) {
                            jt3.setText(String.valueOf(listModel1.getSize()));
                        }
                    } else {

                        //jt3.setText(String.valueOf(listModel1.getSize()));

                        if (listModel1.getSize() != 0) {
                            insertData();
                            JOptionPane.showConfirmDialog(null, "�j��˽c�I�I�I", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
                            jt3.setText(" ");
                        } else {
                            JOptionPane.showConfirmDialog(null, "�ƶq���šA���i�˽c", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
                            return;
                        }

                    }
                    jt1.setText("");
                }

            }
        });
        del.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (jList1.getSelectedIndex() != -1) {
                    int a = jList1.getSelectedIndex();
                    listModel1.remove(a);
                    listModel2.remove(a);
                    listModel3.remove(a);
                    if (listModel1 != null) {
                        jt3.setText(String.valueOf(listModel1.getSize()));
                    }
                    if (listModel1.getSize() == 0) {
                        jc1.setEnabled(true);
                        jc2.setEnabled(true);
                        jt2.setEditable(true);
                    }
                }
            }
        });
        printjb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listModel1.getSize() != 0) {
                    insertData();
                    jt3.setText(String.valueOf(listModel1.getSize()));
                } else {
                    JOptionPane.showConfirmDialog(null, "�ƶq���šA���i�˽c", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
                }

            }
        });
        jc2.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    switch (e.getStateChange()) {
                        case ItemEvent.SELECTED:
                            getAllDevice(e.getItem().toString());
                            break;
                        case ItemEvent.DESELECTED:
                            getAllDevice(e.getItem().toString());
                            break;
                    }
                }
            }
        });

    }

    public void getAllDevice(String route) {
        List<Hashtable> list = new ArrayList<>();
        list = badnessPackCartonService.getAllDevice(route);
        if (list == null) {
            jc1.removeAllItems();
        } else {
            jc1.removeAllItems();
            for (Hashtable ht : list) {
                jc1.addItem(ht.get("STEPNM"));
            }
        }
    }


    public void insertData() {
        if (jc1.getItemCount() == 0) {
            JOptionPane.showConfirmDialog(null, "���I���i����", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
            return;
        }
        String op = uiVO.getLogin_id();
        model = jList1.getModel();
        /*Long timestamp = System.currentTimeMillis();
        String packetId = String.valueOf(timestamp);*/
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        Date date = new Date();
        long currenttime = date.getTime();
        String datestr = simpleDateFormat.format(date);
        boolean result = badnessPackCartonService.insertCartonPT(op, listModel1, listModel2, String.valueOf(currenttime), datestr, jt2.getText(), jc1.getSelectedItem().toString(), jc2.getSelectedItem().toString());
        if (result) {
            JOptionPane.showConfirmDialog(null, "�ƾګO�s���\�I�I�I", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
            //���L
            printCade(model, op, datestr, currenttime);
            listModel1.removeAllElements();
            listModel2.removeAllElements();
            listModel3.removeAllElements();
            //error_code = "";
            jc1.setEnabled(true);
            jc2.setEnabled(true);
            jt2.setEditable(true);
        }

    }

    public boolean printCade(ListModel model, String op, String datestr, long currenttime) {
        ZplPrinter p = new ZplPrinter();
        //String printerurl = p.getPrinterURI();
        //String bar2 = "123459999900188ABCDE";//20��
        /*if(printerurl.contains("600")){
            System.out.println(printerurl+"/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/");
        }*/
        String bar2Paper = "^FO200,370^BY3,3.0,100^BCN,,Y,N,N^FD${data}^FS^FO60,50^GB50,0,4^FS\n" +//???���ҪO
                "^FO400,50^GB500,0,4^FS" +

                "^FO60,345^GB840,0,4^FS\n" +
                "^FO60,530^GB840,0,4^FS\n" +
                "^FO60,50^GB0,480,4^FS\n" +
                "^FO900,50^GB0,480,4^FS\n" +
                "^PQ1,0,1,Y";

        p.setBarcode(String.valueOf(currenttime), bar2Paper);
        p.setText("KAIJIA", 100, 30, 50, 50, 40, 2, 2, 24);
        p.setText(sum + " : " + model.getSize(), 160, 110, 42, 42, 30, 2, 2, 24);
        p.setText(opnum + " : " + op, 160, 175, 42, 42, 30, 2, 2, 24);
        //String[] a = datestr.split(" ");
        p.setText(times + ":  " + datestr, 160, 240, 42, 42, 20, 2, 2, 24);
        //p.setText(a[1], 200, 345, 42, 42, 30, 2, 2, 24);

        String zpl = p.getZpl();
        System.out.println(zpl);
        boolean result = p.print(zpl);
        return result;
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
