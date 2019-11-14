package mes_sfis.client.ui.pack;

import base.client.ui.BasePanel;
import base.client.util.component.P_Component_Meta;
import base.vo.UI_InitVO;
import mes_sfis.client.model.service.BadnessResovlePackService;
import mes_sfis.client.util.DataHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

/**
 * Created by Pino_Gao on 2018/11/12.
 */
public class BadnessResovlePack extends BasePanel {
    public static final String VERSION = "$Name: $, $Id: BadnessResovlePack.java,v 1.01 2018/08/29 08:52:07 Pino_Gao Exp $";
    public static final String COPYRIGHT = "Copyright (c) 2008 PEGATRON Inc. All Rights Reserved.";
    BadnessResovlePackService badnessResovlePackService;
    DataHandler dh;
    JLabel cartonId = new JLabel("箱號:");
    JTextField cartonText = new JTextField();
    JButton printjb = new JButton("解鎖");
    public BadnessResovlePack(UI_InitVO uiVO) {
        super(uiVO);
        //uiVO.getLogin_id();
        badnessResovlePackService = new BadnessResovlePackService(uiVO);
        dh = new DataHandler(uiVO);

        init();
    }

    private void init() {

        cartonId.setBounds(150, 50, 50, 30);
        cartonText.setBounds(200, 50, 220, 30);
        printjb.setBounds(280,100,100,30);


        this.add(cartonId);
        this.add(cartonText);
        this.add(printjb);
        printjb.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                String carton = cartonText.getText();
                //判?箱?是否存在
                boolean flag = badnessResovlePackService.selectAlive(carton);
                if(!flag){
                    JOptionPane.showConfirmDialog(null, "請輸入正確的箱號", "警告", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                //判?是否?1
                int status = badnessResovlePackService.selectStatus(carton);
                if(status==1){
                    JOptionPane.showConfirmDialog(null, "該箱已入庫,不可解箱", "警告", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                if(status==3){
                    JOptionPane.showConfirmDialog(null, "該箱已解箱,不可重複解箱", "警告", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                boolean ustatus = badnessResovlePackService.updateStatus(carton);
                if(ustatus){
                    //插入?据
                    String op = uiVO.getLogin_id();
                    boolean rstatus = badnessResovlePackService.insertRecord(carton,op);
                    if(!rstatus){
                        JOptionPane.showConfirmDialog(null, "數據插入失敗！", "警告", JOptionPane.PLAIN_MESSAGE);
                        return;
                    }else {
                        JOptionPane.showConfirmDialog(null, "解箱成功！", "警告", JOptionPane.PLAIN_MESSAGE);
                        return;
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
