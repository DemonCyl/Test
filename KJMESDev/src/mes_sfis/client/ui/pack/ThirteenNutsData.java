package mes_sfis.client.ui.pack;

import base.client.ui.BasePanel;
import base.client.util.component.P_Component_Meta;
import base.vo.UI_InitVO;
import mes_sfis.client.model.service.ThirteenNutsService;
import mes_sfis.client.util.SoundUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by Mark_Yang on 2018/12/3.
 */
public class ThirteenNutsData extends BasePanel {
    public static final String VERSION = "$Name:  $, $Id: ThirteenNutsData.java,v 1 2018/12/03 19:00:17 Lucy6_Lu Exp $";
    public static final String COPYRIGHT = "Copyright (c) 2008 PEGATRON Inc. All Rights Reserved.";
    static UI_InitVO uiVO;
    private JTextField MceAndIsnText;
    private Map<String,String> map=new HashMap<>();
    JLabel JLMCE,JLISN;


    public ThirteenNutsData(UI_InitVO uiVO) {
        super(uiVO);
        this.uiVO=uiVO;
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




        panel.setLayout(null);
        JLabel title = new JLabel("13nuts治具綁定");
        title.setBounds(240,30,160,30);
        title.setFont(new Font("宋体", Font.BOLD, 16));
        panel.add(title);
        // panel.setSize(600, 50);
        JLabel JLISNSHOW=new JLabel("ISN:");
        JLISNSHOW.setBounds(170,80,30,25);
        panel.add(JLISNSHOW);
        JLabel JLMCESHOW=new JLabel("治具:");
        JLMCESHOW.setBounds(170,110,30,25);
        panel.add(JLMCESHOW);
        JLISN=new JLabel();
        JLISN.setBounds(200,80,300,25);
        panel.add(JLISN);
        JLMCE=new JLabel();
        JLMCE.setBounds(200,110,300,25);
        panel.add(JLMCE);

        //設置總棧板箱數Label
        MceAndIsnText = new JTextField(10);
        MceAndIsnText.setBounds(150,150,300,30);
        MceAndIsnText.setEditable(true);
        panel.add(MceAndIsnText);
        MceAndIsnText.requestFocus();

        MceAndIsnText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    if (MceAndIsnText.getText().toString().trim().length()==16){
                        map.put("mce",MceAndIsnText.getText());
                        JLMCE.setText(MceAndIsnText.getText());
                    }else if (MceAndIsnText.getText().toString().trim().length()==25){
                        map.put("isn",MceAndIsnText.getText());
                        JLISN.setText(MceAndIsnText.getText());
                    }else {
                        JOptionPane.showMessageDialog(null, "此碼為異常碼","掃碼長度異常",JOptionPane.ERROR_MESSAGE);
                    }
                    MceAndIsnText.setText("");
                    if(!JLMCE.getText().isEmpty()&&!JLISN.getText().isEmpty()){
                        map.put("user",uiVO.getLogin_id());
                        ThirteenNutsService thirteenNutsService=new ThirteenNutsService(uiVO);
                        boolean flag=thirteenNutsService.addNutsData(map);
                        if (flag){
                            SoundUtil.PlayOk();
                        }else {
                            SoundUtil.PlayNo();
                        }
                        JLISN.setText("");
                        JLMCE.setText("");
                    }
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
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
}
