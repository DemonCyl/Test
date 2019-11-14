package mes_sfis.client.ui.pack;

import base.client.ui.BasePanel;
import base.client.util.component.PDateTimeTextField;
import base.client.util.component.P_Component_Meta;
import base.vo.UI_InitVO;
import mes_sfis.client.model.service.BadnessWarehousePutInOutService;
import mes_sfis.client.util.DataHandler;
import mes_sfis.client.util.SoundUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.util.*;
import java.util.List;

/**
 * Created by Srx_Zhu on 2018/09/04.
 */
public class BadnessWarehousePutInOut extends BasePanel {
    public static final String VERSION = "$Name:  $, $Id: BadnessWarehousePutInOut.java,v 1.0 2018/04/16 09:28:13 Srx_zhu Exp $";
    public static final String COPYRIGHT = "Copyright (c) 2018 PEGATRON Inc. All Rights Reserved.";
    JPanel jp = null;
    JButton jb1 = new JButton("入庫");
    JButton jb2 = new JButton("出庫");
    JLabel jl1 = new JLabel("請刷箱號：");
    // JLabel jl2 = new JLabel("箱內數量 ：");
    JLabel jl3 = new JLabel("請輸入儲位:");
    JLabel jl4 = new JLabel("工號：");
    //JLabel jl5 = new JLabel("180");
    JLabel jl6 = new JLabel("請選擇倉庫:");
    int ssSum = 0;
    int ssSumOut = 0;
    JComboBox jc1 = new JComboBox();
    JTextField jt1 = new JTextField();
    JTextField jt2 = new JTextField();
    JTextField jt3 = new JTextField();
    JTextField jt4 = new JTextField();
    //JTextField jt5 = new JTextField();
    JTextArea jlog = new JTextArea("");
    String InOut = "";
    DataHandler dh;
    int sflag = 0;
    BadnessWarehousePutInOutService badnessWarehousePutInOutService;

    public BadnessWarehousePutInOut(UI_InitVO uiVO) {
        super(uiVO);
        badnessWarehousePutInOutService = new BadnessWarehousePutInOutService(uiVO);
        dh = new DataHandler(uiVO);
        init();
    }

    public void init() {
        jlog.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
        jlog.setLineWrap(true);//自動換行
        jlog.setEditable(false);
        jt4.setEditable(false);
        JScrollPane jsCheckSSN = new JScrollPane(jlog);//設置滾動軸的
        jsCheckSSN.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);//設置縱向滾動捲軸總是顯示

        jc1.addItem("不良品倉");

        jp = new JPanel();
        this.add(jl1);
        //this.add(jl2);
        this.add(jl3);
        this.add(jl4);
        //this.add(jl5);
        this.add(jl6);
        this.add(jb1);
        this.add(jb2);
        this.add(jt1);
        this.add(jt2);
        this.add(jt3);
        this.add(jt4);
        //this.add(jt5);
        this.add(jc1);
        this.add(jsCheckSSN);
        this.add(jp);


        jl1.setBounds(100, 100, 70, 30);
        // jl2.setBounds(450, 100, 70, 30);
        jl3.setBounds(100, 160, 70, 30);
        jl4.setBounds(450, 160, 100, 30);
        //jl5.setBounds(520, 100, 70, 30);
        jl6.setBounds(100, 220, 70, 30);
        jc1.setBounds(200, 220, 200, 30);
        jb1.setBounds(100, 40, 300, 50);
        jb2.setBounds(400, 40, 300, 50);
        jt1.setBounds(200, 100, 200, 30);
        jt2.setBounds(200, 160, 200, 30);
        jt3.setBounds(540, 160, 130, 30);
        jt4.setBounds(450,220,200,30);
        //jt5.setBounds(450,220,100,30);
        jsCheckSSN.setBounds(100, 280, 600, 200);

        jt3.setText(uiVO.getLogin_id());
        jt3.setEditable(false);
        jt4.setText("0");
        //jt5.setEditable(false);
        //badnessWarehousePutInOutService.getSum();



        jb1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jb2.setBackground(Color.WHITE);
                jb1.setBackground(Color.GREEN);
                InOut = "IN";
                jl4.setText("送至人員工號");
                jt4.setText(String.valueOf(ssSum));
            }
        });
        jb2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jb1.setBackground(Color.WHITE);
                jb2.setBackground(Color.GREEN);
                InOut = "OUT";
                jl4.setText("領用人員工號");
                jt4.setText(String.valueOf(ssSumOut));
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
                if (key == '\n') {
                    if (InOut.equals("")) {
                        SoundUtil.PlayNo();
                        JOptionPane.showConfirmDialog(null, "請選擇出入庫狀態", "警告", JOptionPane.PLAIN_MESSAGE);
                        return;
                    }
                    if (jt1.getText().equals("")) {
                        return;
                    }
                    if (jt2.getText().equals("")&&InOut.equals("IN")) {
                        SoundUtil.PlayNo();
                        JOptionPane.showConfirmDialog(null, "儲位不可為空", "警告", JOptionPane.PLAIN_MESSAGE);

                        return;
                    }
                    if (jt3.getText().equals("")) {
                        SoundUtil.PlayNo();
                        JOptionPane.showConfirmDialog(null, "工號不可為空", "警告", JOptionPane.PLAIN_MESSAGE);
                        return;
                    }

                    Hashtable ht = new Hashtable();
                    ht = badnessWarehousePutInOutService.CheckCartonExist(jt1.getText());
                    if (ht == null) {
                        SoundUtil.PlayNo();
                        JOptionPane.showConfirmDialog(null, "該箱號不存在，請核實！！！", "警告", JOptionPane.PLAIN_MESSAGE);
                        jlog.append("該箱號不存在或已出庫，請核實！" + jt1.getText() + "\n");
                        jt1.setText("");
                        return;
                    }
                    List<Hashtable> list = new ArrayList<>();
                    list= badnessWarehousePutInOutService.CheckUserPermissions(uiVO.getLogin_id());
                    String allPerm="";
                    if(list==null){
                        SoundUtil.PlayNo();
                        JOptionPane.showConfirmDialog(null, "當前用戶無權進行入庫操作，請申請權限！！！", "警告", JOptionPane.PLAIN_MESSAGE);
                        return;
                    }
                    for(Hashtable ht2:list){
                        allPerm+= ht2.get("PERMISSIONS");
                    }
                    //jt4.setText("0");
                    sflag = badnessWarehousePutInOutService.checkSameCartonId(jt1.getText(),jt2.getText());


                    if (InOut.equals("IN")) {
                        if(!allPerm.contains(InOut)){
                            SoundUtil.PlayNo();
                            JOptionPane.showConfirmDialog(null, "當前用戶無權進行入庫操作，請申請權限！！！", "警告", JOptionPane.PLAIN_MESSAGE);
                            return;
                        }
                        if(badnessWarehousePutInOutService.CheckCartonCanPutIn(jt1.getText())){
                            SoundUtil.PlayNo();
                            JOptionPane.showConfirmDialog(null, "該箱已入庫或者解箱狀態,不可入庫", "警告", JOptionPane.PLAIN_MESSAGE);
                            return;
                        }
                        if (sflag==1||sflag==3){
                            SoundUtil.PlayNo();
                            JOptionPane.showConfirmDialog(null, "該箱已在儲位中!請先出庫在入庫", "警告", JOptionPane.PLAIN_MESSAGE);
                            return;
                        }
                        CheckStorageExist(jt2.getText(),ht.get("SUM").toString());
                        String op = uiVO.getLogin_id();
                        badnessWarehousePutInOutService.insertRecordInOut(jc1.getSelectedItem().toString(),jt2.getText(),jt1.getText(),InOut,op);
                        PutInWarehouse(ht.get("SUM").toString());
                        jlog.append(jc1.getSelectedItem() + "入庫成功，箱號：" + jt1.getText() + "    " + jc1.getSelectedItem() + "   儲位：" + jt2.getText() + "   產品數量：" + ht.get("SUM") + "\n");

                        jt1.setText("");
                        SoundUtil.PlayOk();
                        BigDecimal bigDecimal  = (BigDecimal)ht.get("SUM");
                        ssSum += bigDecimal.intValue();
                        jt4.setText(String.valueOf(ssSum));
                    }
                    if (InOut.equals("OUT")) {
                        if(!allPerm.contains(InOut)){
                            SoundUtil.PlayNo();
                            JOptionPane.showConfirmDialog(null, "當前用戶無權進行出庫操作，請申請權限！！！", "警告", JOptionPane.PLAIN_MESSAGE);
                            return;
                        }
                        if(badnessWarehousePutInOutService.CheckCartonCanPutOut(jt1.getText())){
                            SoundUtil.PlayNo();
                            JOptionPane.showConfirmDialog(null, "該箱已出庫或尚未入庫！！！", "警告", JOptionPane.PLAIN_MESSAGE);
                            return;
                        }
                        System.out.println("**************************"+sflag);
                        if (sflag==-1){
                            SoundUtil.PlayNo();
                            JOptionPane.showConfirmDialog(null, "該箱與儲位不匹配中!", "警告", JOptionPane.PLAIN_MESSAGE);
                            return;
                        }else if(sflag==0||sflag==2||sflag==3) {
                            SoundUtil.PlayNo();
                            JOptionPane.showConfirmDialog(null, "狀態不符，無法出庫!", "警告", JOptionPane.PLAIN_MESSAGE);
                            return;
                        }
                        String op = uiVO.getLogin_id();
                        badnessWarehousePutInOutService.insertRecordInOut(jc1.getSelectedItem().toString(),jt2.getText(),jt1.getText(),InOut,op);
                        PutOutWarehouse();
                        jlog.append(jc1.getSelectedItem() + "出庫成功，箱號：" + jt1.getText() +"   產品數量：" + ht.get("SUM")+ "\n");
                        jt1.setText("");
                        SoundUtil.PlayOk();
                        BigDecimal bigDecimal  = (BigDecimal)ht.get("SUM");

                        ssSumOut += bigDecimal.intValue();
                        jt4.setText(String.valueOf(ssSumOut));
                    }
                    //ssSum = badnessWarehousePutInOutService.getStorageSum(jt2.getText());



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

    public void CheckStorageExist(String storage_name,String num){
        badnessWarehousePutInOutService.CheckStorageExist(storage_name,num);
    }

    public void PutInWarehouse(String num) {
        badnessWarehousePutInOutService.PutInCarton(jc1.getSelectedItem().toString(), jt2.getText(), uiVO.getLogin_id(), jt1.getText());
        badnessWarehousePutInOutService.PutInTotalWarehouse(jc1.getSelectedItem().toString(),num,uiVO.getLogin_id());
        badnessWarehousePutInOutService.PutInRecord(jc1.getSelectedItem().toString(),jt2.getText(),jt1.getText(),jt3.getText(),uiVO.getLogin_id());
    }

    public void PutOutWarehouse() {
        badnessWarehousePutInOutService.PutOutCarton(jt1.getText());
        badnessWarehousePutInOutService.PutOutTotalWarehouse(jt1.getText());
        badnessWarehousePutInOutService.PutOutStorage(jt1.getText());
        badnessWarehousePutInOutService.PutOutRecord(jt1.getText(),jt3.getText());
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
