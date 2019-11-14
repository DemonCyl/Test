package mes_sfis.client.ui.pack;

import base.client.ui.BasePanel;
import base.client.util.component.P_Component_Meta;
import base.vo.UI_InitVO;
//import mes_sfis.client.task.PassDeviceTaskChris;
import mes_sfis.client.task.base.LongTask;
import mes_sfis.client.util.SoundUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Set;

public class Thread_SoundTest extends BasePanel {
    public static final String VERSION = "$Name: $, $Id: FlyBatchPass.java,v 1.0 2018/05/03 10:52:07 Lucy6_Lu Exp $";
    public static final String COPYRIGHT = "Copyright (c) 2008 PEGATRON Inc. All Rights Reserved.";
    private static JTextArea LogText;//掃描顯示框和錯誤顯示框
    private static JButton mandatoryPass, testSound;//強制過站按鈕和清除按鈕
    static UI_InitVO uiVO;
    static Set<String> set = null;
    private JPanel contentPanel = super.UILayoutPanel;//UI佈局面板；UI用戶界面
    static String device = null;//站點
    private static ProgressMonitor progressMonitor;
    private static Timer timer;
    private static LongTask task;
    private static String newline = "\n";
    public final static int ONE_SECOND = 1000;

   /* public static void main(String[] args) {
        JFrame frame = new JFrame("母掛綁定飛靶測試畫面");
        JPanel panel = new JPanel();
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 添加面板
        frame.add(panel);
        placeComponents(panel);
        frame.setVisible(true);
    }*/

  /*  private static void placeComponents(JPanel panel) {
        //放置組件，放面板
        panel.setLayout(null);
        JLabel title = new JLabel("測試線程和聲音");
        title.setBounds(240, 20, 500, 25);
        title.setFont(new Font("宋体", Font.BOLD, 16));
        panel.add(title);
        mandatoryPass = new JButton("線程測試");
        mandatoryPass.setBounds(50, 60, 120, 30);
        panel.add(mandatoryPass);
        testSound = new JButton("聲音測試");
        testSound.setBounds(50, 120, 120, 30);
        panel.add(testSound);
        //錯誤顯示框
        JLabel log = new JLabel("LOG");
        log.setFont(new Font("宋体", Font.BOLD, 16));
        log.setBounds(30, 320, 40, 25);
        panel.add(log);
        LogText = new JTextArea();
        LogText.setBounds(30, 340, 500, 150);
        JScrollPane jsLogText = new JScrollPane(LogText);
        LogText.setEditable(false);
        jsLogText.setBounds(30, 340, 500, 150);
        jsLogText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(jsLogText);

        //強制綁定方法
        mandatoryPass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HashMap mapISN = new HashMap();
                for (int i = 0; i < 100; i++) {
                    mapISN.put("" + i, "" + i);
                }
                statePass("1721010051", mapISN, "K18000205");
            }
        });

        testSound.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoundUtil.PlayNo();
                SoundUtil.PlayNice(1000);
                System.out.println("123123123");
                try {
                    for (int i = 0; i < 4000; i++) {
                        Thread.sleep(10);
//                        SoundUtil.PlayNice();
                        SoundUtil.PlayOk();
                        SoundUtil.PlayNo();
                    }
                    Thread.sleep(2000);
                    SoundUtil.PlayOk();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }*/

 /*   public static void statePass(String device, HashMap map, String op) {
        java.util.List list = new ArrayList<>();
        for (int i = 0; i < map.size(); i++) {
            list.add(map.get(i + ""));
        }
        task = new PassDeviceTaskChris(list.size());
        task.setIsnList(list);
        task.setDevice("1721010051");
        task.setOp("K18000205");
        task.getMessage();
        showBar();
    }

    void init() {
        placeComponents(contentPanel);
    }*/

    static class TimerListener implements ActionListener {//計時器監聽實現動作監聽

        public void actionPerformed(ActionEvent evt) {//執行操作方法
            progressMonitor.setProgress(task.getCurrent());//設置進度
            String s = task.getMessage();//獲取消息賦值給s
            if (s != null) {
                progressMonitor.setNote(s);//給進度監控設置注記
                LogText.append(s + newline);
                LogText.setCaretPosition(//設置插入符號的位置
                        LogText.getDocument().getLength());//獲取文件的長度
            }
            if (progressMonitor.isCanceled() || task.isDone()) {//檢查進度監控是否取消了或者任務是否完成
                progressMonitor.close();//將進度監控關閉
                task.stop();//任務停止
                Toolkit.getDefaultToolkit().beep();//得到一個嗶聲音
                timer.stop();//計時器停止
                if (task.isDone()) {
                    LogText.append("Task completed." + newline);
                } else {
                    LogText.append("Task canceled." + newline);
                }
            }
        }
    }

    public static void showBar() {
        progressMonitor = new ProgressMonitor(new JFrame("EFIL"), "批量過站進行中,請稍後", "", 0, task.getLengthOfTask());
        progressMonitor.setProgress(0);
        progressMonitor.setMillisToDecideToPopup(2 * ONE_SECOND);
        timer = new Timer(ONE_SECOND, new TimerListener());
        task.go();
        timer.start();
    }

    public Thread_SoundTest(UI_InitVO uiVO, boolean onlyQuery) {
        super(uiVO, onlyQuery);
    }

    public Thread_SoundTest(UI_InitVO uiVO) {
        super(uiVO);
        this.uiVO = uiVO;
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
