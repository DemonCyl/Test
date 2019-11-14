package mes_sfis.client.ui.pack;

import base.client.ui.BasePanel;
import base.client.util.component.P_Component_Meta;
import base.vo.UI_InitVO;
import mes_sfis.client.model.service.ANOBatchPassService;
import mes_sfis.client.sfis.Check_ISN;
import mes_sfis.client.sfis.SoapUtil;
import mes_sfis.client.task.base.LongTask;
import mes_sfis.client.util.ReadConfig2;
import mes_sfis.client.util.SoundUtilANO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.Timer;
import javax.xml.soap.SOAPException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class HangerBatchPass extends BasePanel {
    private static final Logger logger = LogManager.getLogger(HangerBatchPass.class);
    public static final String VERSION = "$Name: $, $Id: HangerBatchPass.java,v 1.1 2018/05/03 10:52:07 Lucy6_Lu Exp $";
    public static final String COPYRIGHT = "Copyright (c) 2008 PEGATRON Inc. All Rights Reserved.";
    private JTextField ISNText, HangerText, VHC_ISNText, VHC_CodeText;//ISN��J�ءB������J�ءB������������ISN���X��J�]��䤤�@��ISN�^�B������������ܮ�
    private JTextArea scannedNewsText, LogText;//���y�H����ܮةM���~�H����ܮ�
    private JButton mandatoryPass, cleanOut, replaceBtn;//�j��j�w���s�B�M�����s
    private Vector c;//Vector? �O�b java ���i�H??��?�W?��?�H??
    UI_InitVO uiVO;
    private JPanel contentPanel = super.UILayoutPanel;//UI�G�����O�FUI�Τ�ɭ�
    ANOBatchPassService cws;//�Ыؤ@��ANOBatchPassService��H
    String FristColor = null;//�Ĥ@���C��
    String NextColor = null;//�U�@���C��
    String UUID;
    String VHC;
    String device = null;
    List list = null;
    Check_ISN check_isn = null;
    SoapUtil su = null;
    HashMap map = new HashMap();
    SimpleDateFormat df;
    private ProgressMonitor progressMonitor;//ProgressMonitor?�q??�ܤ@??��??��??��?��
    private Timer timer;//�t�X�i�ױ��A�@���s�@���i�ױ�
    private LongTask task;//�@��API�A�F���������ޡC
    public final static int ONE_SECOND = 1000;//1000�@��
    private String newline = "\n";//�����
    SoundUtilANO soundUtilANO = new SoundUtilANO();

    public void main(String[] args) {
        JFrame frame = new JFrame("ISN�j�w����");
        JPanel panel = new JPanel();
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // �K�[���O
        frame.add(panel);
        placeComponents(panel);
        frame.setVisible(true);
    }

    void init() {
        placeComponents(contentPanel);
    }//��l�Ƥ�k

    private void placeComponents(JPanel panel) {//��m�ե󪺤�k�A�o�̴N�O�e���O�F
        //��m�ե�A�񭱪O
        //��m���D��ISN�j�w������
        panel.setLayout(null);
        JLabel title = new JLabel("ISN�j�w����");
        title.setBounds(240, 20, 500, 25);
        title.setFont(new Font("���^", Font.BOLD, 16));
        panel.add(title);

        //��m�j��j�w���s
        mandatoryPass = new JButton("�j��j�w");
        mandatoryPass.setBounds(30, 100, 90, 25);
        panel.add(mandatoryPass);

        //��m�M�����s
        cleanOut = new JButton("�M��");
        cleanOut.setBounds(470, 100, 60, 25);
        panel.add(cleanOut);

        //��m�������s
        replaceBtn = new JButton("����");
        replaceBtn.setBounds(300, 160, 60, 25);
        panel.add(replaceBtn);

        //��m��ISN���y�������D
        JLabel JHanger = new JLabel("ISN���y:");
        JHanger.setBounds(30, 70, 50, 25);
        panel.add(JHanger);

        //��m��ISN���y������J��
        ISNText = new JTextField(8);
        ISNText.setBounds(80, 70, 170, 25);
        ISNText.setText("");
        panel.add(ISNText);

        //��m���������y�������D
        JLabel SMOrderLabel = new JLabel("�������y:");
        SMOrderLabel.setBounds(300, 70, 100, 25);
        panel.add(SMOrderLabel);

        //��m���������y������J��
        HangerText = new JTextField(10);
        HangerText.setBounds(360, 70, 170, 25);
        HangerText.setEditable(false);
        panel.add(HangerText);

        //��m��������������ISN���y�]���@���N�i�H�F�^�����D
        JLabel VHCLabel = new JLabel("������������ISN���y�]���@���N�i�H�F�^:");
        VHCLabel.setBounds(30, 130, 245, 25);
        panel.add(VHCLabel);

        //��m��������������ISN���y�]���@���N�i�H�F�^����J��
        VHC_ISNText = new JTextField(8);
        VHC_ISNText.setBounds(280, 130, 170, 25);
        VHC_ISNText.setEditable(false);
        panel.add(VHC_ISNText);

        //��m�����������������D
        JLabel VHC_CodeLabel = new JLabel("����������:");
        VHC_CodeLabel.setBounds(30, 160, 75, 25);
        panel.add(VHC_CodeLabel);

        //��m����������������ܮ�
        VHC_CodeText = new JTextField(8);
        VHC_CodeText.setBounds(100, 160, 170, 25);
        VHC_CodeText.setEditable(false);
        panel.add(VHC_CodeText);

        //��m���y��ܮ�
        scannedNewsText = new JTextArea();
        scannedNewsText.setBounds(30, 200, 500, 100);
        JScrollPane jsLogText1 = new JScrollPane(scannedNewsText);
        scannedNewsText.setEditable(false);
        jsLogText1.setBounds(30, 200, 500, 150);
        jsLogText1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(jsLogText1);

        //��m��LOG�������D
        JLabel log = new JLabel("LOG");
        log.setFont(new Font("���^", Font.BOLD, 16));
        log.setBounds(30, 360, 40, 25);
        panel.add(log);

        //��m��LOG���~��ܮء�
        LogText = new JTextArea();
        LogText.setBounds(30, 380, 500, 150);
        JScrollPane jsLogText = new JScrollPane(LogText);
        LogText.setEditable(false);
        jsLogText.setBounds(30, 380, 500, 150);
        jsLogText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(jsLogText);
/**
 * ---------------------------------------------------------------------------------------�H�W��m���O�A��UI
 * ---------------------------------------------------------------------------------------�H�UUI�ե󪺺�ť��k
 */
        //ISN���y�^���ƥ�
        ISNText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    if (cws.Check_ISNRepeat(ISNText.getText().trim())) {
                        JOptionPane.showConfirmDialog(null, "��ISN" + ISNText.getText().trim() + "�w�g�j�w�A���i���Ƹj�w�I", "����", JOptionPane.OK_OPTION);
                        ISNText.setText("");
                    } else {
                        check_ISN(ISNText.getText().trim());//�P�_ISN
                    }
                }
            }
        });

        //������������ISN���y�]���@���N�i�H�F�^��J�ئ^���ƥ�
        VHC_ISNText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    if (cws.VHC_Code(VHC_ISNText.getText().trim()) != null) {
                        VHC_CodeText.setText(cws.VHC_Code(VHC_ISNText.getText().trim()));
                    } else {
                        LogText.setText("��ISN�èS���j�w������������A�нT�{�Z�A����");
                    }
                    HangerText.setEditable(true);
                    HangerText.grabFocus();
                }
            }
        });

        //�������s����ť�ƥ�
        replaceBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VHC_ISNText.setEditable(true);//���}��J��
                VHC_ISNText.grabFocus();//����J�I
                ISNText.setEditable(false);
            }
        });

        //�M�����s����ť�ƥ�
        cleanOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int tj = JOptionPane.showConfirmDialog(null, "�O�_�M���ƾ�", "����", JOptionPane.YES_NO_OPTION);
                if (tj == 0) {
                    clear();
                    ISNText.setEnabled(true);
                    ISNText.grabFocus();//��o�J�I
                }
            }
        });

        //�j��j�w��k
        mandatoryPass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long time_hm = 0;
//                Object[] options = {"�j�w��������", "�j�w����"};
                if (ToDBC(HangerText.getText()).toUpperCase().trim().isEmpty()) {//�p�G�����ج��šA�N�ݥΤ�j���������٬O�j����
//                    int xn = JOptionPane.showOptionDialog(null, "" + map.size() + "��ISN�j�w���ӱ��H", "����", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
//                    if (xn == 0) {
//                        VHC_CodeText.setText(String.valueOf(cws.virtualHangerCode_function()));//�H�@��Ƭ����������s��
//                        int a = 0;
//                        while (a == 0) {
//                            if (scannedNewsText.getText().isEmpty()) {
//                                JOptionPane.showConfirmDialog(null, "�S��ISN�H���A�L�k�j��j�w��������", "����", JOptionPane.OK_OPTION);
//                                break;
//                            } else {
//                                UUID = cws.getUID();
//                                if (a == 0) {
//                                    a = cws.ISN_VHCBinding(UUID, VHC_CodeText.getText().trim(), map);
//                                }
//                                if (a == 1) {
//                                    LogText.append(map.size() + "��ISN�P��������" + VHC_CodeText.getText().trim() + "�j�w����!!!");
//                                    ISNText.setEditable(false);
//                                    map.clear();
//                                }
//                            }
//                        }
//                    } else {
                    ISNText.setEditable(false);
                    HangerText.setEditable(true);
                    HangerText.grabFocus();
//                    }
                } else {
                    int tj = JOptionPane.showConfirmDialog(null, "�o�O�����s���� " + ToDBC(HangerText.getText()).toUpperCase().trim() + " ��" + map.size() + "��ISN,", "����", JOptionPane.YES_NO_OPTION);
                    if (tj == 0) {
                        String thisHangerID = ToDBC(HangerText.getText()).toUpperCase().trim();
                        int a = 0;
                        while (a == 0) {
                            if (scannedNewsText.getText().trim().isEmpty()) {
                                JOptionPane.showConfirmDialog(null, "�S��ISN�H���A�L�k�j��j�w", "����", JOptionPane.OK_OPTION);
                                break;
                            } else if (!ToDBC(HangerText.getText()).toUpperCase().trim().isEmpty()) {
                                HangerText.setEditable(true);
                                try {
                                    time_hm = cws.CheckHanger_time(ToDBC(HangerText.getText()).toUpperCase().trim());
                                } catch (ParseException e1) {
                                    e1.printStackTrace();
                                }
                                if (time_hm != 0 && time_hm <= 7200000) {
                                    JOptionPane.showConfirmDialog(null, "�����s���w��A�A����J������p�ɥH��", "����", JOptionPane.OK_OPTION);
                                    break;
                                }
                                UUID = cws.getUID();
                                a = cws.ISN_HangerBinding(UUID, thisHangerID, map);
                                if (a == 1) {
                                    clear();
                                    ISNText.grabFocus();
                                    ISNText.setEditable(true);
                                    LogText.append("�j�w����!!");
                                    map.clear();
                                } else {
                                    LogText.append("�j�w���`�A�Х�12828�pô�޲z��!!");
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        });

        //��J�����s���^���ƥ�
        HangerText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                long time_hm = 0;
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    if (!VHC_ISNText.getText().trim().isEmpty()) {
                        if (!ToDBC(HangerText.getText()).toUpperCase().trim().isEmpty()) {//�P�w������J�ؤ����_��
                            HangerText.setEditable(false);//�����šA�h������J�س]�m�����i�s�説�A
                            try {
                                time_hm = cws.CheckHanger_time(ToDBC(HangerText.getText()).toUpperCase().trim());
                            } catch (ParseException e1) {
                                e1.printStackTrace();
                            }
                            if (time_hm != 0 && time_hm <= 7200000) {
                                JOptionPane.showConfirmDialog(null, "�����s���w��A�A����J������p�ɥH��", "����", JOptionPane.OK_OPTION);
                                HangerText.grabFocus();
                                HangerText.setText("");
                                HangerText.setEditable(true);
                            } else {
                                cws.replace_function(VHC_ISNText.getText().trim(), ToDBC(HangerText.getText()).toUpperCase().trim());
                                LogText.append("���\�N��������:" + VHC_CodeText.getText().trim() + "����ISN�����������:" + ToDBC(HangerText.getText()).toUpperCase().trim() + "�I");
                            }
                        }//�hISN��J�س]�m���i�s�説�A
                        else {
                            JOptionPane.showConfirmDialog(null, "�����s������", "����", JOptionPane.OK_OPTION);
                        }
                    } else {
                        if (!ToDBC(HangerText.getText()).toUpperCase().trim().isEmpty()) {//�P�w������J�ؤ����_��
                            HangerText.setEditable(false);//�����šA�h������J�س]�m�����i�s�説�A
                            try {
                                time_hm = cws.CheckHanger_time(ToDBC(HangerText.getText()).toUpperCase().trim());
                            } catch (ParseException e1) {
                                e1.printStackTrace();
                            }
                            if (time_hm != 0 && time_hm <= 7200000) {
                                JOptionPane.showConfirmDialog(null, "�����s���w��A�A����J������p�ɥH��", "����", JOptionPane.OK_OPTION);
                            }
                        }
                    }
                }
            }
        });
    }

    //�M����k
    public void clear() {
        ISNText.setEditable(true);//�}��ISN��J��
        HangerText.setEditable(false);//�����������y��
        VHC_ISNText.setEditable(false);//����������������ISN���X��J�]��䤤�@��ISN�^��ܮ�
        VHC_ISNText.setText("");//�M��������������ISN���X��J�]��䤤�@��ISN�^��ISN
        VHC_CodeText.setText("");//�M������������
        HangerText.setText("");//�M�������s��
        ISNText.setText("");//�M��ISN
        map.clear();
        scannedNewsText.setText("");//�M�����y�H����ܮ�
        LogText.setText("");//�M�����ܫH����ܮ�
        FristColor = null;//�M�ŲĤ@��ISN�C��H��
    }

    // �i�ܶi�׼u��
    public void showBar() {
        progressMonitor = new ProgressMonitor(new JFrame("EFIL"),
                "��q�j�w�i�椤",
                "", 0, task.getLengthOfTask());
        progressMonitor.setProgress(0);
        progressMonitor.setMillisToDecideToPopup(2 * ONE_SECOND);
        task.go();
        timer.start();
    }

    class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            progressMonitor.setProgress(task.getCurrent());
            String s = task.getMessage();
            if (s != null) {
                progressMonitor.setNote(s);
                LogText.append(s + newline);
                LogText.setCaretPosition(
                        LogText.getDocument().getLength());
            }
            if (progressMonitor.isCanceled() || task.isDone()) {
                progressMonitor.close();
                task.stop();
                Toolkit.getDefaultToolkit().beep();
                timer.stop();
                if (task.isDone()) {
                    LogText.append("Task completed." + newline);
                } else {
                    LogText.append("Task canceled." + newline);
                }
            }
        }
    }

    /**
     * ISN���ˬd�P�_�A�åB�P�_�C��O�_�M�Ĥ@���ꪺ�C��O�_�@�P�A��ISN�ƶq�F��70�Ӯ����Τ��ܸj�����٬O�j��������
     */
    public void check_ISN(String isn) {
        //�ˬdISN�O�_�s�b
        if (cws.CheckISN_State(isn) == null) {
            //ISN���s�b
            LogText.append("�o��" + isn + "���s�b�A���ˬd�o��ISN���H���O�_���T�I" + "\r\n");
            ISNText.setText("");
            soundUtilANO.PlayNo();
        } else {
            //�p�GISN�s�b�A�P�_ISN�O�_���C��H��
            if (cws.CheckColor_State(isn).isEmpty()) {
                //ISN�S���C��H��
                LogText.append("�o��" + isn + "�����D�A�S���C��H���A�нT�{�o��ISN���H���O�_���T�I" + "\r\n");
                ISNText.setText("");
                soundUtilANO.PlayNo();
            } else {
                //�p�GISN���C��H���A�P�_�O�_�P�Ĥ@���C��ۦP
                if (FristColor == null) {
                    //�S���C��H���A�O���U�Ĥ@��ISN���C��H���A�N�Ĥ@��ISN���C��H���s�J�����ܶqFristColor��
                    FristColor = cws.CheckColor_State(isn).get("SNE").toString();
                    LogText.append("�Ĥ@�����C��O�G " + FristColor + "\r\n");
                    NextColor = FristColor;
                } else {
                    //�w�s�b�C��H���A�N�U�@��ISN���C��H���s�J�����ܶqNextColor��
                    NextColor = cws.CheckColor_State(isn).get("SNE").toString();
                }
                if (!FristColor.equals(NextColor)) {
                    //�p�G�U�@��ISN���C��H���P�Ĥ@��ISN���C��H�����ŦX�A�N��ܳ���
                    LogText.append("�o��" + isn + "�����D�A�C��H���P�e�����šA�нT�{��ISN�H���O�_���T�I" + "\r\n");
                    ISNText.setText("");
                    soundUtilANO.PlayNo();
                } else {
                    //�p�G�C��ŦX�A�N�ե�WTSP_GETVERSION�ˬd��ISN�O�_���ӹL��e���I
                    try {
                        su = new SoapUtil();
                        check_isn = new Check_ISN();
                    } catch (SOAPException e) {
                        logger.debug("�Ы�Check_ISN()��H���`" + e.getMessage());
                        e.printStackTrace();
                    }
                    check_isn.setISN(isn);
                    check_isn.setDevice(device);
                    try {
                        list = check_isn.createAndSendSOAPRequest(su);
                    } catch (SOAPException e) {
                        logger.debug("��list��Ȫ�SOAPException���`�G" + e.getMessage());
                        e.printStackTrace();
                    } catch (IOException e) {
                        logger.debug("��list��Ȫ�IOException���`�G" + e.getMessage());
                        e.printStackTrace();
                    }
                    if ("0".equals(list.get(0))) {
                        LogText.append(isn + "����L��:" + list.get(1) + "\r\n");
                        ISNText.setText("");
                        soundUtilANO.PlayNo();
                    }
                    if ("1".equals(list.get(0))) {
                        LogText.append("�i�H�L��:" + list.get(1) + "\r\n");
                        int first = map.size();
                        map.put(isn, "1");
                        int last = map.size();
                        //�P�_ISN�O�_���ƿ�J
                        if (first == last) {
                            //�p�G���ƤF�A���ܳ���
                            LogText.append("�o��" + isn + "���ƤF�A�нT�{��ISN���H���O�_���T�I" + "\r\n");
                            ISNText.setText("");
                            soundUtilANO.PlayNo();
                        } else {
                            soundUtilANO.PlayOk();
                            //�p�G�S�����ơA�Ҧ����ˬd���w�g����
                            scannedNewsText.append("��" + last + "�ӡG" + isn + "\r\n");
                            ISNText.setText("");
                            if (last == 70) {
                                JOptionPane.showConfirmDialog(null, "�w��J70��ISN�A�иj�w�����I", "����", JOptionPane.OK_OPTION);
                                ISNText.setEditable(false);
                                HangerText.setEditable(true);
                                HangerText.grabFocus();

                            }
                        }
                    }
                    su.close();
                }
            }
        }
    }

    //�ꨤ�ഫ���b����k
    public String ToDBC(String input) {
        char c[] = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == '\u3000') {
                c[i] = ' ';
            } else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
                c[i] = (char) (c[i] - 65248);

            }
        }
        String returnString = new String(c);
        return returnString;
    }

    public HangerBatchPass(UI_InitVO uiVO) {
        super(uiVO);
        this.uiVO = uiVO;
        cws = new ANOBatchPassService(uiVO);
        ReadConfig2 a = new ReadConfig2();
        a.hashINI();
        try {
            device = a.iniHash.get("epega.exe.device").toString();
            System.out.println("device=" + device);

        } catch (Exception e) {
            System.out.println("�t�m��󲧱`�A���pô OA     �`�G �t�m���榡�� UNICODE");
        }
        timer = new Timer(ONE_SECOND, new TimerListener());
        this.init();
    }

    public HangerBatchPass(UI_InitVO uiVO, boolean onlyQuery) {
        super(uiVO, onlyQuery);
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
