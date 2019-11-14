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
    private static JTextArea LogText;//���y��ܮةM���~��ܮ�
    private static JButton mandatoryPass, testSound;//�j��L�����s�M�M�����s
    static UI_InitVO uiVO;
    static Set<String> set = null;
    private JPanel contentPanel = super.UILayoutPanel;//UI�G�����O�FUI�Τ�ɭ�
    static String device = null;//���I
    private static ProgressMonitor progressMonitor;
    private static Timer timer;
    private static LongTask task;
    private static String newline = "\n";
    public final static int ONE_SECOND = 1000;

   /* public static void main(String[] args) {
        JFrame frame = new JFrame("�����j�w���v���յe��");
        JPanel panel = new JPanel();
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // �K�[���O
        frame.add(panel);
        placeComponents(panel);
        frame.setVisible(true);
    }*/

  /*  private static void placeComponents(JPanel panel) {
        //��m�ե�A�񭱪O
        panel.setLayout(null);
        JLabel title = new JLabel("���սu�{�M�n��");
        title.setBounds(240, 20, 500, 25);
        title.setFont(new Font("���^", Font.BOLD, 16));
        panel.add(title);
        mandatoryPass = new JButton("�u�{����");
        mandatoryPass.setBounds(50, 60, 120, 30);
        panel.add(mandatoryPass);
        testSound = new JButton("�n������");
        testSound.setBounds(50, 120, 120, 30);
        panel.add(testSound);
        //���~��ܮ�
        JLabel log = new JLabel("LOG");
        log.setFont(new Font("���^", Font.BOLD, 16));
        log.setBounds(30, 320, 40, 25);
        panel.add(log);
        LogText = new JTextArea();
        LogText.setBounds(30, 340, 500, 150);
        JScrollPane jsLogText = new JScrollPane(LogText);
        LogText.setEditable(false);
        jsLogText.setBounds(30, 340, 500, 150);
        jsLogText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(jsLogText);

        //�j��j�w��k
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

    static class TimerListener implements ActionListener {//�p�ɾ���ť��{�ʧ@��ť

        public void actionPerformed(ActionEvent evt) {//����ާ@��k
            progressMonitor.setProgress(task.getCurrent());//�]�m�i��
            String s = task.getMessage();//���������ȵ�s
            if (s != null) {
                progressMonitor.setNote(s);//���i�׺ʱ��]�m�`�O
                LogText.append(s + newline);
                LogText.setCaretPosition(//�]�m���J�Ÿ�����m
                        LogText.getDocument().getLength());//�����󪺪���
            }
            if (progressMonitor.isCanceled() || task.isDone()) {//�ˬd�i�׺ʱ��O�_�����F�Ϊ̥��ȬO�_����
                progressMonitor.close();//�N�i�׺ʱ�����
                task.stop();//���Ȱ���
                Toolkit.getDefaultToolkit().beep();//�o��@�ӹ��n��
                timer.stop();//�p�ɾ�����
                if (task.isDone()) {
                    LogText.append("Task completed." + newline);
                } else {
                    LogText.append("Task canceled." + newline);
                }
            }
        }
    }

    public static void showBar() {
        progressMonitor = new ProgressMonitor(new JFrame("EFIL"), "��q�L���i�椤,�еy��", "", 0, task.getLengthOfTask());
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
