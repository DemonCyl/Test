package mes_sfis.client.ui.pack;

import base.client.ui.BasePanel;
import base.client.util.component.P_Component_Meta;
import base.vo.UI_InitVO;
import mes_sfis.client.model.service.ANOBatchPassService;
import mes_sfis.client.task.PassDeviceTaskV2;
import mes_sfis.client.task.base.LongTask;
import mes_sfis.client.util.ReadConfig2;
import mes_sfis.client.util.SoundUtilANO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.util.*;
import java.util.List;

/**
 * Created by Xiaojian1_Yu on 2018/7/17.
 */
public class FlyBatchPass extends BasePanel {
    public static final String VERSION = "$Name: $, $Id: FlyBatchPass.java,v 1.0 2018/05/03 10:52:07 Lucy6_Lu Exp $";
    public static final String COPYRIGHT = "Copyright (c) 2008 PEGATRON Inc. All Rights Reserved.";
    private static final Logger logger = LogManager.getLogger(FlyBatchPass.class);
    private static JTextField FlyText, HangerText;//���v��J�ةM������J��
    private static JTextArea scannedNewsText, LogText;//���y��ܮةM���~��ܮ�
    private static JButton mandatoryPass, cleanOut;//�j��L�����s�M�M�����s
    private Vector c;//Vector? �O�b java ���i�H??��?�W?��?�H??
    UI_InitVO uiVO;
    private JPanel contentPanel = super.UILayoutPanel;//UI�G�����O�FUI�Τ�ɭ�
    ANOBatchPassService cws;//�Ыؤ@��ANOBatchPassService��H
    String thisFlyID;//���v�s��
    HashMap map_hangerid = new HashMap();
    String device = null;//���I
    private ProgressMonitor progressMonitor;
    private Timer timer;
    private LongTask task;
    private String newline = "\n";
    public final static int ONE_SECOND = 1000;
    String flyUID;
    SoundUtilANO soundUtilANO = new SoundUtilANO();

    public void main(String[] args) {
        JFrame frame = new JFrame("�����j�w���v");
        JPanel panel = new JPanel();
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // �K�[���O
        frame.add(panel);
        placeComponents(panel);
        frame.setVisible(true);
    }

    /**
     * ��m�ե󪺤�k�A�o�̴N�O�e���O�F
     *
     * @param panel
     */
    private void placeComponents(JPanel panel) {
        //��m�ե�A�񭱪O
        panel.setLayout(null);
        JLabel title = new JLabel("�����j�w���v");
        title.setBounds(240, 20, 500, 25);
        title.setFont(new Font("���^", Font.BOLD, 16));
        panel.add(title);

        mandatoryPass = new JButton("�j��L��");
        mandatoryPass.setBounds(50, 60, 120, 30);
        panel.add(mandatoryPass);
        cleanOut = new JButton("�M��");
        cleanOut.setBounds(410, 60, 100, 30);
        panel.add(cleanOut);

        //���v�s��
        JLabel JHanger = new JLabel("���v�s��:");
        JHanger.setBounds(50, 100, 60, 25);
        panel.add(JHanger);

        //�]�m���v�s����J��
        FlyText = new JTextField(8);
        FlyText.setBounds(120, 100, 200, 25);
        FlyText.setText("");
        panel.add(FlyText);

        //�������y
        JLabel SMOrderLabel = new JLabel("�������y:");
        SMOrderLabel.setBounds(50, 130, 60, 25);
        panel.add(SMOrderLabel);

        //�������y��J��
        HangerText = new JTextField(10);
        HangerText.setBounds(120, 130, 200, 25);
        HangerText.setEditable(false);
        panel.add(HangerText);

        //���y��ܮ�
        scannedNewsText = new JTextArea();
        scannedNewsText.setBounds(30, 160, 500, 100);
        JScrollPane jsLogText1 = new JScrollPane(scannedNewsText);
        scannedNewsText.setEditable(false);
        jsLogText1.setBounds(30, 160, 500, 150);
        jsLogText1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(jsLogText1);
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

        //�M�����s����ť�ƥ�
        cleanOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int tj = JOptionPane.showConfirmDialog(null, "�O�_�M���ƾ�", "����", JOptionPane.YES_NO_OPTION);
                if (tj == 0) {
                    clear();
                    HangerText.grabFocus();//��o�J�I
                }
            }
        });

        //�j��j�w��k
        mandatoryPass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HashMap mapISN = new HashMap();
                int tj = JOptionPane.showConfirmDialog(null, "�o�O���v�s���� " + FlyText.getText().trim() + " ��" + map_hangerid.size() + "�ӥ���,", "����", JOptionPane.YES_NO_OPTION);
                if (tj == 0) {
                    int a = 0;
                    while (a == 0) {
                        flyUID = cws.getUID();
                        thisFlyID = FlyText.getText().trim();
                        if (scannedNewsText.getText().isEmpty()) {
                            JOptionPane.showConfirmDialog(null, "�S��ISN�H���A�L�k�j��j�w", "����", JOptionPane.OK_OPTION);
                            break;
                        } else {
                            a = cws.Hanger_FlyBinding(flyUID, thisFlyID, map_hangerid);
                            if (a == 1) {
                                //�j���q�j�w
                                mapISN = cws.ISNnumber(mapISN, map_hangerid, thisFlyID);
                                StatePass(device, mapISN, uiVO.getLogin_id());
                                HangerText.grabFocus();
                                map_hangerid.clear();
                                mapISN.clear();
                                clear();
                            } else {
                                LogText.append("�y������I");
                            }
                        }
                    }
                }
            }
        });

        //��J���v�s���^���ƥ�
        FlyText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                long time_hm_fly = 0;
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    if (!FlyText.getText().trim().isEmpty()) {//�P�w���v��J�ؤ����_��
                        FlyText.setEditable(false);//�����šA�h���v��J�س]�m�����i�s�説�A
                        try {
                            time_hm_fly = cws.CheckFly_time(FlyText.getText().trim());
                        } catch (ParseException e1) {
                            logger.debug("�ˬd���v�ɶ����`�G" + e1.getMessage());
                            e1.printStackTrace();
                        }
                        if (time_hm_fly != 0 && time_hm_fly <= 7200000) {
                            JOptionPane.showConfirmDialog(null, "���v�s���w��A�A����J������p�ɥH��", "����", JOptionPane.OK_OPTION);
                            HangerText.setEditable(false);
                            clear();
                        } else {
                            flyUID = cws.getUID();
                            HangerText.grabFocus();//��o�J�I
                            HangerText.setEditable(true);
                        }
                        if (FlyText.getText().trim().matches("[0-9]+")) {
                            flyUID = cws.getUID();
                            HangerText.grabFocus();//��o�J�I
                            HangerText.setEditable(true);
                        } else {
                            JOptionPane.showConfirmDialog(null, "�п�J�Ʀr�s�X", "����", JOptionPane.OK_OPTION);
                            clear();
                        }
                    }//�h������J�س]�m���i�s�説�A
                    else {
                        JOptionPane.showConfirmDialog(null, "���v�s������", "����", JOptionPane.OK_OPTION);
                    }
                }
            }
        });

        //���y������ť�ƥ�
        HangerText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    check_Hanger(HangerText.getText().toString());//�P�_����
                }
            }
        });
    }

    public void clear() {
        //�M�����v�s��
        FlyText.setText("");
        //�M�������s��
        HangerText.setText("");
        //�M�� ��� LOG
        scannedNewsText.setText("");
        LogText.setText("");
        //���}���v��J�ءA����������J��
        FlyText.setEditable(true);
        HangerText.setEditable(false);
        map_hangerid.clear();
    }

    //�P�_�����O�_�X��A������ƶq�F��6�Ӯɦ۰ʹL���P���v�j�w
    public void check_Hanger(String thisHangerID) {
        HashMap mapISN = new HashMap();
        int isBindingISN = cws.CheckHang_ISNisBanding(thisHangerID);
        int isPassDevice = cws.IsPassDevice(thisHangerID);
        //�ˬd�ӥ����O�_�j�wISN�A���S��ISN�H��
        if (isBindingISN == 1) {
            LogText.append("�o�ӥ���:" + thisHangerID + "�S���j�wISN�H���A�L�k�j�w���v�A�нT�{�I" + "\r\n");
            HangerText.setText("");
            soundUtilANO.PlayNo();
        } else {
            //������������W�@�ӱ���A�h�P�_�ӥ����j�w��ISN�O�_�w�g�L��
            if (isPassDevice == 1) {
                LogText.append("�o�ӥ���:" + thisHangerID + "�j�w��ISN�w�g�j�w�L���v,�нT�{����A��J!" + "\r\n");
                HangerText.setText("");
                soundUtilANO.PlayNo();
            } else {
                //�����H�W��ӱ���A�Ҧ����ˬd���w�g�����A���U�ӱN�������s�Jmap_hangerid
                int first = map_hangerid.size();
                map_hangerid.put(thisHangerID, "1");//�����id�s�bmap�̭�
                int last = map_hangerid.size();
                if (first == last) {
                    LogText.append("�o�ӥ������ƤF" + thisHangerID + "\r\n");
                    HangerText.setText("");
                    soundUtilANO.PlayNo();
                } else {
                    scannedNewsText.append("��" + last + "�ӡG" + thisHangerID + "\r\n");
                    HangerText.setText("");
                    soundUtilANO.PlayOk();
                    if (last == 6) {
                        int tj = JOptionPane.showConfirmDialog(null, "�o�O���v�s���� " + FlyText.getText().trim() + " ��" + map_hangerid.size() + "��,", "����", JOptionPane.YES_NO_OPTION);
                        if (tj == 0) {
                            int a = 0;
                            flyUID = cws.getUID();
                            thisFlyID = FlyText.getText().trim();
                            a = cws.Hanger_FlyBinding(flyUID, thisFlyID, map_hangerid);
                            if (a == 1) {
                                mapISN = cws.ISNnumber(mapISN, map_hangerid, thisFlyID);
                                StatePass(device, mapISN, uiVO.getLogin_id());
                                FlyText.grabFocus();
                                map_hangerid.clear();
                                mapISN.clear();
                                clear();
                            } else {
                                LogText.append("�L�����`�I");
                            }
                        }
                    }
                }
            }
        }
    }

    public FlyBatchPass(UI_InitVO uiVO, boolean onlyQuery) {
        super(uiVO, onlyQuery);
    }

    /**
     * ��l�Ƥ�k
     */
    void init() {
        placeComponents(contentPanel);
    }

    public FlyBatchPass(UI_InitVO uiVO) {
        super(uiVO);
        this.uiVO = uiVO;
        cws = new ANOBatchPassService(uiVO);
        ReadConfig2 a = new ReadConfig2();
        a.hashINI();
        try {
            device = a.iniHash.get("epega.exe.device").toString();
            System.out.println("device=" + device);
            logger.debug("device=" + device);
        } catch (Exception e) {
            System.out.println("�t�m��󲧱`�A���pô OA     �`�G �t�m���榡�� UNICODE");
            logger.debug("�t�m��󲧱`�A���pô OA     �`�G �t�m���榡�� UNICODE");
        }
        timer = new Timer(ONE_SECOND, new TimerListener());
        this.init();
    }

    public void StatePass(String device, HashMap map, String op) {
        List list = new ArrayList<>();
        for (int i = 0; i <= map.size() - 1; i++) {
            list.add(map.get(i));
        }
        task = new PassDeviceTaskV2(list.size());
        task.setIsnList(list);
        task.setDevice(device);
        task.setOp(op);
        task.getMessage();
        showBar();
        //�O�sISN�ƾڨ���
        cws.txtfile(device, map, op);
    }

    class TimerListener implements ActionListener {//�p�ɾ���ť��{�ʧ@��ť

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

    public void showBar() {
        progressMonitor = new ProgressMonitor(new JFrame("EFIL"), "��q�L���i�椤,�еy��", "", 0, task.getLengthOfTask());
        progressMonitor.setProgress(0);
        progressMonitor.setMillisToDecideToPopup(2 * ONE_SECOND);
        task.go();
        timer.start();
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
