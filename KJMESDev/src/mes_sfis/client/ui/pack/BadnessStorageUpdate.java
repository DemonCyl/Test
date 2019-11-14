package mes_sfis.client.ui.pack;

import base.client.ui.BasePanel;
import base.client.util.component.PDateTimeTextField;
import base.client.util.component.P_Component_Meta;
import base.vo.UI_InitVO;
import mes_sfis.client.model.service.BadnessStorageService;
import mes_sfis.client.model.service.ErrorViewService;
import mes_sfis.client.util.DataHandler;
import mes_sfis.client.util.ReadConfig2;
import mes_sfis.client.util.SoundUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

/**
 * Created by Srx_Zhu on 2018/09/04.
 */
public class BadnessStorageUpdate extends BasePanel {
    public static final String VERSION    = "$Name:  $, $Id: BadnessStorageUpdate.java,v 1.0 2018/09/06 10:08:45 Lucy6_Lu Exp $";
    public static final String COPYRIGHT  = "Copyright (c) 2017 PEGATRON Inc. All Rights Reserved.";
    BadnessStorageService badnessStorageService ;
    Hashtable hashtable=null;
    Vector vector=null;

    public BadnessStorageUpdate(UI_InitVO uiVO) {
        super(uiVO);
        this.uiVO=uiVO;
        System.out.println("3333333");
        badnessStorageService = new BadnessStorageService(uiVO);
        init();
    }
    JPanel jp = null;
    JLabel jl1 = new JLabel("請輸入儲位：");
    JLabel jl2 = new JLabel("樓層：");
    JLabel jl3 = new JLabel("棟別：");
    JLabel jl4 = new JLabel("更改為");
    JLabel jl5 = new JLabel("更改為");
    JLabel jl6 = new JLabel("更改為");
    JTextField jt1 = new JTextField();
    JTextField jt2 = new JTextField();
    JTextField jt3 = new JTextField();
    JTextField jt4 = new JTextField();
    JTextField jt5 = new JTextField();
    JTextField jt6 = new JTextField();

    JButton jb1 = new JButton("確認更新");
    JButton jb2 = new JButton("清空");

    public void init() {

        jp = new JPanel();
        this.add(jl1);
        this.add(jl2);
        this.add(jl3);
        this.add(jl4);
        this.add(jl5);
        this.add(jl6);
        this.add(jt1);
        this.add(jt2);
        this.add(jt3);
        this.add(jt4);
        this.add(jt5);
        this.add(jt6);
        this.add(jb1);
        this.add(jb2);
        this.add(jp);


        jl1.setBounds(50, 50, 100, 30);
        jl2.setBounds(50, 100, 100, 30);
        jl3.setBounds(50, 150, 100, 30);
        jl4.setBounds(300, 50, 100, 30);
        jl5.setBounds(300, 100, 100, 30);
        jl6.setBounds(300, 150, 100, 30);
        jt1.setBounds(140, 50, 140, 30);
        jt2.setBounds(140, 100, 140, 30);
        jt3.setBounds(140, 150, 140, 30);
        jt4.setBounds(420, 50, 140, 30);
        jt5.setBounds(420, 100, 140, 30);
        jt6.setBounds(420, 150, 140, 30);
        jb1.setBounds(200, 200, 140, 50);
        jb2.setBounds(350, 200, 140, 50);

        jt1.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == '\n') {
                    if (!jt1.getText().toString().isEmpty()) {
                        hashtable = badnessStorageService.getSTORAGENews(jt1.getText());
                        if (hashtable != null) {
                            if(hashtable.get("FLOOR")!=null&&hashtable.get("FLOOR")!=""){
                                jt2.setText(hashtable.get("FLOOR").toString());
                            }else{
                                jt2.setText("");
                            }
                            if(hashtable.get("BUILDING")!=null&&hashtable.get("BUILDING")!=""){
                                jt3.setText(hashtable.get("BUILDING").toString());
                            }else{
                                jt3.setText("");
                            }
                            jt1.setEditable(false);
                            jt2.setEditable(false);
                            jt3.setEditable(false);
                        } else {
                            JOptionPane.showMessageDialog(null, "差無此儲位", "提示", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "請輸入儲位信息", "提示", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        jb1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!jt4.getText().isEmpty()&&!jt5.getText().isEmpty()&&!jt6.getText().isEmpty()){
                  boolean flag=  badnessStorageService.Change_STORAGENews(jt4.getText(),jt5.getText(),jt6.getText(),jt1.getText());
                    if (flag){
                        vector=badnessStorageService.FindSTORAGENews(jt1.getText());
                        if (vector==null){
                            return;
                        }
                        boolean outflag= badnessStorageService.AddSTORAGEIn(uiVO.getLogin_id(),jt1.getText());
                        if (!outflag){
                            JOptionPane.showMessageDialog(null, "更新失敗","出庫提示",JOptionPane.ERROR_MESSAGE);
                           return;
                        }
                        boolean isflag= badnessStorageService.AddSTORAGEMark(vector,jt4.getText(),jt5.getText(),jt6.getText(),uiVO.getLogin_id());
                        if (isflag){
                            clearkong();
                            JOptionPane.showMessageDialog(null, "更新成功","提示",JOptionPane.QUESTION_MESSAGE);
                        }else {
                            JOptionPane.showMessageDialog(null, "更新失敗","入庫提示",JOptionPane.ERROR_MESSAGE);
                        }

                    }else {
                        JOptionPane.showMessageDialog(null, "更新失敗","提示",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        jb2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearkong();

            }
        });
    }



    private void clearkong(){
        jt1.setText("");
        jt2.setText("");
        jt3.setText("");
        jt4.setText("");
        jt5.setText("");
        jt6.setText("");
        jt1.setEditable(true);
        jt2.setEditable(true);
        jt3.setEditable(true);
    }
    private PDateTimeTextField MyDateField(int x, int y, int w, int h) {
        PDateTimeTextField pdtf = new PDateTimeTextField(null, "PDTimeST", 100, true, true);
        pdtf.setBounds(x, y, w, h);
        return pdtf;
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