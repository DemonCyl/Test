package mes_sfis.client.ui.pack;

import base.client.ui.BasePanel;
import base.client.util.component.P_Component_Meta;
import base.vo.UI_InitVO;
import mes_sfis.client.model.service.BarcodePrintService;
import mes_sfis.client.model.service.StationToService;
import mes_sfis.client.util.DataHandler;
import mes_sfis.client.util.ReadConfig2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Hashtable;

/**
 * Created by Pino_Gao on 2018/9/11.
 */
public class StationTo extends BasePanel {
    public static final String VERSION = "$Name: $, $Id: StationTo.java,v 1.0 2018/09/11 08:52:07 Pino_Gao Exp $";
    public static final String COPYRIGHT = "Copyright (c) 2008 PEGATRON Inc. All Rights Reserved.";
    DataHandler dh;
    StationToService stationToService;
    JPanel contentPanel = super.UILayoutPanel;
    JComboBox jc1 = new JComboBox();
    JLabel jl1 = new JLabel("站點：");
    JLabel jl2 = new JLabel("SP：");
    JLabel jl3 = new JLabel("BG NO：");
    JLabel jl4 = new JLabel("ISN：");
    JTextField jt1 = new JTextField();
    JTextField jt2 = new JTextField();
    JTextField jt3 = new JTextField();
    JButton jb = new JButton("更新");

    public StationTo(UI_InitVO uiVO){
        super(uiVO);
        stationToService = new StationToService(uiVO);
        dh = new DataHandler(uiVO);
        
        init();
    }

    private void init() {
        jl1.setBounds(180,150,50,30);
        jc1.setBounds(230,150,200,30);
        jl2.setBounds(180,200,50,30);
        jl3.setBounds(180,200,50,30);
        jl4.setBounds(180,250,50,30);
        jt1.setBounds(230,200,200,30);
        jt2.setBounds(230,200,200,30);
        jt3.setBounds(230,250,200,30);
        jb.setBounds(250,300,100,30);

        jc1.addItem("請選擇站點");
        jc1.addItem("SP TO BAND");
        jc1.addItem("BG TO HSG");
        jl2.setVisible(false);
        jt1.setVisible(false);
        jl3.setVisible(false);
        jt2.setVisible(false);
        jl4.setVisible(false);
        jt3.setVisible(false);

        this.add(jl1);
        this.add(jc1);
        this.add(jl2);
        this.add(jl3);
        this.add(jl4);
        this.add(jt1);
        this.add(jt2);
        this.add(jt3);
        this.add(jb);

        jc1.addItemListener(new ItemListener(){

            @Override
            public void itemStateChanged(ItemEvent e) {
                String item = e.getItem().toString();
                if("SP TO BAND".equals(item)){
                    jl3.setVisible(false);
                    jt2.setVisible(false);
                    jl2.setVisible(true);
                    jt1.setVisible(true);
                    jl4.setVisible(true);
                    jt3.setVisible(true);


                }else if ("BG TO HSG".equals(item)){
                    jl2.setVisible(false);
                    jt1.setVisible(false);
                    jl3.setVisible(true);
                    jt2.setVisible(true);
                    jl4.setVisible(true);
                    jt3.setVisible(true);
                }else {
                    jl2.setVisible(false);
                    jt1.setVisible(false);
                    jl3.setVisible(false);
                    jt2.setVisible(false);
                    jl4.setVisible(false);
                    jt3.setVisible(false);
                }

            }
        });

        jb.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                String selectItem = jc1.getSelectedItem().toString();
                if("SP TO BAND".equals(selectItem)){
                    String sp = jt1.getText();
                    String isn = jt3.getText();
                    if("".equals(sp)){
                        JOptionPane.showConfirmDialog(null, "請輸入SP值！", "警告", JOptionPane.PLAIN_MESSAGE);
                        return;
                    }
                    if("".equals(isn)){
                        JOptionPane.showConfirmDialog(null, "請輸入ISN值！", "警告", JOptionPane.PLAIN_MESSAGE);
                        return;
                    }
                    if(sp.length()!=25){
                        JOptionPane.showConfirmDialog(null, "請輸入25位SP值！", "警告", JOptionPane.PLAIN_MESSAGE);
                        return;
                    }
                    boolean status = stationToService.updateSn1BySn1(sp);
                    if(status){
                        boolean flag = stationToService.updateSn1BySpIsn(sp,isn);
                        if(flag){
                            //清空數據
                            JOptionPane.showConfirmDialog(null, "數據更新成功", "警告", JOptionPane.PLAIN_MESSAGE);
                            jt1.setText("");
                            jt3.setText("");
                        }

                    }
                }else if ("BG TO HSG".equals(selectItem)){
                    String bgno = jt2.getText();
                    String isn = jt3.getText();
                    if("".equals(bgno)){
                        JOptionPane.showConfirmDialog(null, "請輸入BG NO值！", "警告", JOptionPane.PLAIN_MESSAGE);
                        return;
                    }
                    if("".equals(isn)){
                        JOptionPane.showConfirmDialog(null, "請輸入ISN值！", "警告", JOptionPane.PLAIN_MESSAGE);
                        return;
                    }
                    if(bgno.length()!=44){
                        JOptionPane.showConfirmDialog(null, "請輸入44位的玻璃碼！", "警告", JOptionPane.PLAIN_MESSAGE);
                        return;
                    }
                    boolean flag = stationToService.updateSndByBgIsn(bgno,isn);
                    if(flag){
                        //清空?据
                        JOptionPane.showConfirmDialog(null, "數據更新成功", "警告", JOptionPane.PLAIN_MESSAGE);
                        jt2.setText("");
                        jt3.setText("");
                    }
                }
            }
        });
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
