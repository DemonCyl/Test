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
    private JTextField ISNText, HangerText, VHC_ISNText, VHC_CodeText;//ISN輸入框、母掛輸入框、虛擬母掛中的ISN掃碼輸入（刷其中一片ISN）、虛擬母掛號顯示框
    private JTextArea scannedNewsText, LogText;//掃描信息顯示框和錯誤信息顯示框
    private JButton mandatoryPass, cleanOut, replaceBtn;//強制綁定按鈕、清除按鈕
    private Vector c;//Vector? 是在 java 中可以??自?增?的?象??
    UI_InitVO uiVO;
    private JPanel contentPanel = super.UILayoutPanel;//UI佈局面板；UI用戶界面
    ANOBatchPassService cws;//創建一個ANOBatchPassService對象
    String FristColor = null;//第一個顏色
    String NextColor = null;//下一個顏色
    String UUID;
    String VHC;
    String device = null;
    List list = null;
    Check_ISN check_isn = null;
    SoapUtil su = null;
    HashMap map = new HashMap();
    SimpleDateFormat df;
    private ProgressMonitor progressMonitor;//ProgressMonitor?通??示一??度??框??示?度
    private Timer timer;//配合進度條，一秒刷新一次進度條
    private LongTask task;//一種API，幹嘛的先不管。
    public final static int ONE_SECOND = 1000;//1000毫秒
    private String newline = "\n";//換行符
    SoundUtilANO soundUtilANO = new SoundUtilANO();

    public void main(String[] args) {
        JFrame frame = new JFrame("ISN綁定母掛");
        JPanel panel = new JPanel();
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 添加面板
        frame.add(panel);
        placeComponents(panel);
        frame.setVisible(true);
    }

    void init() {
        placeComponents(contentPanel);
    }//初始化方法

    private void placeComponents(JPanel panel) {//放置組件的方法，這裡就是畫面板了
        //放置組件，放面板
        //放置標題“ISN綁定母掛”
        panel.setLayout(null);
        JLabel title = new JLabel("ISN綁定母掛");
        title.setBounds(240, 20, 500, 25);
        title.setFont(new Font("宋体", Font.BOLD, 16));
        panel.add(title);

        //放置強制綁定按鈕
        mandatoryPass = new JButton("強制綁定");
        mandatoryPass.setBounds(30, 100, 90, 25);
        panel.add(mandatoryPass);

        //放置清除按鈕
        cleanOut = new JButton("清除");
        cleanOut.setBounds(470, 100, 60, 25);
        panel.add(cleanOut);

        //放置替換按鈕
        replaceBtn = new JButton("替換");
        replaceBtn.setBounds(300, 160, 60, 25);
        panel.add(replaceBtn);

        //放置“ISN掃描”欄位標題
        JLabel JHanger = new JLabel("ISN掃描:");
        JHanger.setBounds(30, 70, 50, 25);
        panel.add(JHanger);

        //放置“ISN掃描”的輸入框
        ISNText = new JTextField(8);
        ISNText.setBounds(80, 70, 170, 25);
        ISNText.setText("");
        panel.add(ISNText);

        //放置“母掛掃描”欄位標題
        JLabel SMOrderLabel = new JLabel("母掛掃描:");
        SMOrderLabel.setBounds(300, 70, 100, 25);
        panel.add(SMOrderLabel);

        //放置“母掛掃描”的輸入框
        HangerText = new JTextField(10);
        HangerText.setBounds(360, 70, 170, 25);
        HangerText.setEditable(false);
        panel.add(HangerText);

        //放置“虛擬母掛中的ISN掃描（掃一片就可以了）”標題
        JLabel VHCLabel = new JLabel("虛擬母掛中的ISN掃描（掃一片就可以了）:");
        VHCLabel.setBounds(30, 130, 245, 25);
        panel.add(VHCLabel);

        //放置“虛擬母掛中的ISN掃描（掃一片就可以了）”輸入框
        VHC_ISNText = new JTextField(8);
        VHC_ISNText.setBounds(280, 130, 170, 25);
        VHC_ISNText.setEditable(false);
        panel.add(VHC_ISNText);

        //放置“虛擬母掛號”標題
        JLabel VHC_CodeLabel = new JLabel("虛擬母掛號:");
        VHC_CodeLabel.setBounds(30, 160, 75, 25);
        panel.add(VHC_CodeLabel);

        //放置“虛擬母掛號”顯示框
        VHC_CodeText = new JTextField(8);
        VHC_CodeText.setBounds(100, 160, 170, 25);
        VHC_CodeText.setEditable(false);
        panel.add(VHC_CodeText);

        //放置掃描顯示框
        scannedNewsText = new JTextArea();
        scannedNewsText.setBounds(30, 200, 500, 100);
        JScrollPane jsLogText1 = new JScrollPane(scannedNewsText);
        scannedNewsText.setEditable(false);
        jsLogText1.setBounds(30, 200, 500, 150);
        jsLogText1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(jsLogText1);

        //放置“LOG”欄位標題
        JLabel log = new JLabel("LOG");
        log.setFont(new Font("宋体", Font.BOLD, 16));
        log.setBounds(30, 360, 40, 25);
        panel.add(log);

        //放置“LOG錯誤顯示框”
        LogText = new JTextArea();
        LogText.setBounds(30, 380, 500, 150);
        JScrollPane jsLogText = new JScrollPane(LogText);
        LogText.setEditable(false);
        jsLogText.setBounds(30, 380, 500, 150);
        jsLogText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(jsLogText);
/**
 * ---------------------------------------------------------------------------------------以上放置面板，做UI
 * ---------------------------------------------------------------------------------------以下UI組件的監聽方法
 */
        //ISN掃描回車事件
        ISNText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    if (cws.Check_ISNRepeat(ISNText.getText().trim())) {
                        JOptionPane.showConfirmDialog(null, "該ISN" + ISNText.getText().trim() + "已經綁定，不可重複綁定！", "提示", JOptionPane.OK_OPTION);
                        ISNText.setText("");
                    } else {
                        check_ISN(ISNText.getText().trim());//判斷ISN
                    }
                }
            }
        });

        //虛擬母掛中的ISN掃描（掃一片就可以了）輸入框回車事件
        VHC_ISNText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    if (cws.VHC_Code(VHC_ISNText.getText().trim()) != null) {
                        VHC_CodeText.setText(cws.VHC_Code(VHC_ISNText.getText().trim()));
                    } else {
                        LogText.setText("該ISN並沒有綁定任何虛擬母掛，請確認后再替換");
                    }
                    HangerText.setEditable(true);
                    HangerText.grabFocus();
                }
            }
        });

        //替換按鈕的監聽事件
        replaceBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VHC_ISNText.setEditable(true);//打開輸入框
                VHC_ISNText.grabFocus();//獲取焦點
                ISNText.setEditable(false);
            }
        });

        //清除按鈕的監聽事件
        cleanOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int tj = JOptionPane.showConfirmDialog(null, "是否清除數據", "提示", JOptionPane.YES_NO_OPTION);
                if (tj == 0) {
                    clear();
                    ISNText.setEnabled(true);
                    ISNText.grabFocus();//獲得焦點
                }
            }
        });

        //強制綁定方法
        mandatoryPass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long time_hm = 0;
//                Object[] options = {"綁定虛擬母掛", "綁定母掛"};
                if (ToDBC(HangerText.getText()).toUpperCase().trim().isEmpty()) {//如果母掛框為空，就問用戶綁虛擬母掛還是綁母掛
//                    int xn = JOptionPane.showOptionDialog(null, "" + map.size() + "片ISN綁定哪個掛？", "提示", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
//                    if (xn == 0) {
//                        VHC_CodeText.setText(String.valueOf(cws.virtualHangerCode_function()));//以毫秒數為虛擬母掛編號
//                        int a = 0;
//                        while (a == 0) {
//                            if (scannedNewsText.getText().isEmpty()) {
//                                JOptionPane.showConfirmDialog(null, "沒有ISN信息，無法強制綁定虛擬母掛", "提示", JOptionPane.OK_OPTION);
//                                break;
//                            } else {
//                                UUID = cws.getUID();
//                                if (a == 0) {
//                                    a = cws.ISN_VHCBinding(UUID, VHC_CodeText.getText().trim(), map);
//                                }
//                                if (a == 1) {
//                                    LogText.append(map.size() + "片ISN與虛擬母掛" + VHC_CodeText.getText().trim() + "綁定完成!!!");
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
                    int tj = JOptionPane.showConfirmDialog(null, "這是母掛編號為 " + ToDBC(HangerText.getText()).toUpperCase().trim() + " 的" + map.size() + "個ISN,", "提示", JOptionPane.YES_NO_OPTION);
                    if (tj == 0) {
                        String thisHangerID = ToDBC(HangerText.getText()).toUpperCase().trim();
                        int a = 0;
                        while (a == 0) {
                            if (scannedNewsText.getText().trim().isEmpty()) {
                                JOptionPane.showConfirmDialog(null, "沒有ISN信息，無法強制綁定", "提示", JOptionPane.OK_OPTION);
                                break;
                            } else if (!ToDBC(HangerText.getText()).toUpperCase().trim().isEmpty()) {
                                HangerText.setEditable(true);
                                try {
                                    time_hm = cws.CheckHanger_time(ToDBC(HangerText.getText()).toUpperCase().trim());
                                } catch (ParseException e1) {
                                    e1.printStackTrace();
                                }
                                if (time_hm != 0 && time_hm <= 7200000) {
                                    JOptionPane.showConfirmDialog(null, "母掛編號已刷，再次刷入必須兩小時以後", "提示", JOptionPane.OK_OPTION);
                                    break;
                                }
                                UUID = cws.getUID();
                                a = cws.ISN_HangerBinding(UUID, thisHangerID, map);
                                if (a == 1) {
                                    clear();
                                    ISNText.grabFocus();
                                    ISNText.setEditable(true);
                                    LogText.append("綁定完成!!");
                                    map.clear();
                                } else {
                                    LogText.append("綁定異常，請打12828聯繫管理員!!");
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        });

        //輸入母掛編號回車事件
        HangerText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                long time_hm = 0;
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    if (!VHC_ISNText.getText().trim().isEmpty()) {
                        if (!ToDBC(HangerText.getText()).toUpperCase().trim().isEmpty()) {//判定母掛輸入框不等于空
                            HangerText.setEditable(false);//不為空，則母掛輸入框設置為不可編輯狀態
                            try {
                                time_hm = cws.CheckHanger_time(ToDBC(HangerText.getText()).toUpperCase().trim());
                            } catch (ParseException e1) {
                                e1.printStackTrace();
                            }
                            if (time_hm != 0 && time_hm <= 7200000) {
                                JOptionPane.showConfirmDialog(null, "母掛編號已刷，再次刷入必須兩小時以後", "提示", JOptionPane.OK_OPTION);
                                HangerText.grabFocus();
                                HangerText.setText("");
                                HangerText.setEditable(true);
                            } else {
                                cws.replace_function(VHC_ISNText.getText().trim(), ToDBC(HangerText.getText()).toUpperCase().trim());
                                LogText.append("成功將虛擬母掛:" + VHC_CodeText.getText().trim() + "中的ISN替換到母掛為:" + ToDBC(HangerText.getText()).toUpperCase().trim() + "！");
                            }
                        }//則ISN輸入框設置為可編輯狀態
                        else {
                            JOptionPane.showConfirmDialog(null, "母掛編號為空", "提示", JOptionPane.OK_OPTION);
                        }
                    } else {
                        if (!ToDBC(HangerText.getText()).toUpperCase().trim().isEmpty()) {//判定母掛輸入框不等于空
                            HangerText.setEditable(false);//不為空，則母掛輸入框設置為不可編輯狀態
                            try {
                                time_hm = cws.CheckHanger_time(ToDBC(HangerText.getText()).toUpperCase().trim());
                            } catch (ParseException e1) {
                                e1.printStackTrace();
                            }
                            if (time_hm != 0 && time_hm <= 7200000) {
                                JOptionPane.showConfirmDialog(null, "母掛編號已刷，再次刷入必須兩小時以後", "提示", JOptionPane.OK_OPTION);
                            }
                        }
                    }
                }
            }
        });
    }

    //清除方法
    public void clear() {
        ISNText.setEditable(true);//開啟ISN輸入框
        HangerText.setEditable(false);//關閉母掛掃描框
        VHC_ISNText.setEditable(false);//關閉虛擬母掛中的ISN掃碼輸入（刷其中一片ISN）顯示框
        VHC_ISNText.setText("");//清除虛擬母掛中的ISN掃碼輸入（刷其中一片ISN）的ISN
        VHC_CodeText.setText("");//清除虛擬母掛號
        HangerText.setText("");//清除母掛編號
        ISNText.setText("");//清除ISN
        map.clear();
        scannedNewsText.setText("");//清除掃描信息顯示框
        LogText.setText("");//清除提示信息顯示框
        FristColor = null;//清空第一片ISN顏色信息
    }

    // 展示進度彈窗
    public void showBar() {
        progressMonitor = new ProgressMonitor(new JFrame("EFIL"),
                "批量綁定進行中",
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
     * ISN的檢查判斷，並且判斷顏色是否和第一片刷的顏色是否一致，當ISN數量達到70個時讓用戶選擇綁母掛還是綁虛擬母掛
     */
    public void check_ISN(String isn) {
        //檢查ISN是否存在
        if (cws.CheckISN_State(isn) == null) {
            //ISN不存在
            LogText.append("這片" + isn + "不存在，請檢查這片ISN的信息是否正確！" + "\r\n");
            ISNText.setText("");
            soundUtilANO.PlayNo();
        } else {
            //如果ISN存在，判斷ISN是否有顏色信息
            if (cws.CheckColor_State(isn).isEmpty()) {
                //ISN沒有顏色信息
                LogText.append("這片" + isn + "有問題，沒有顏色信息，請確認這片ISN的信息是否正確！" + "\r\n");
                ISNText.setText("");
                soundUtilANO.PlayNo();
            } else {
                //如果ISN有顏色信息，判斷是否與第一片顏色相同
                if (FristColor == null) {
                    //沒有顏色信息，記錄下第一片ISN的顏色信息，將第一片ISN的顏色信息存入全局變量FristColor中
                    FristColor = cws.CheckColor_State(isn).get("SNE").toString();
                    LogText.append("第一片的顏色是： " + FristColor + "\r\n");
                    NextColor = FristColor;
                } else {
                    //已存在顏色信息，將下一片ISN的顏色信息存入全局變量NextColor中
                    NextColor = cws.CheckColor_State(isn).get("SNE").toString();
                }
                if (!FristColor.equals(NextColor)) {
                    //如果下一片ISN的顏色信息與第一片ISN的顏色信息不符合，就顯示報錯
                    LogText.append("這片" + isn + "有問題，顏色信息與前次不符，請確認該ISN信息是否正確！" + "\r\n");
                    ISNText.setText("");
                    soundUtilANO.PlayNo();
                } else {
                    //如果顏色符合，就調用WTSP_GETVERSION檢查該ISN是否應該過當前站點
                    try {
                        su = new SoapUtil();
                        check_isn = new Check_ISN();
                    } catch (SOAPException e) {
                        logger.debug("創建Check_ISN()對象異常" + e.getMessage());
                        e.printStackTrace();
                    }
                    check_isn.setISN(isn);
                    check_isn.setDevice(device);
                    try {
                        list = check_isn.createAndSendSOAPRequest(su);
                    } catch (SOAPException e) {
                        logger.debug("給list賦值的SOAPException異常：" + e.getMessage());
                        e.printStackTrace();
                    } catch (IOException e) {
                        logger.debug("給list賦值的IOException異常：" + e.getMessage());
                        e.printStackTrace();
                    }
                    if ("0".equals(list.get(0))) {
                        LogText.append(isn + "不能過站:" + list.get(1) + "\r\n");
                        ISNText.setText("");
                        soundUtilANO.PlayNo();
                    }
                    if ("1".equals(list.get(0))) {
                        LogText.append("可以過站:" + list.get(1) + "\r\n");
                        int first = map.size();
                        map.put(isn, "1");
                        int last = map.size();
                        //判斷ISN是否重複輸入
                        if (first == last) {
                            //如果重複了，提示報錯
                            LogText.append("這片" + isn + "重複了，請確認該ISN的信息是否正確！" + "\r\n");
                            ISNText.setText("");
                            soundUtilANO.PlayNo();
                        } else {
                            soundUtilANO.PlayOk();
                            //如果沒有重複，所有的檢查都已經完成
                            scannedNewsText.append("第" + last + "個：" + isn + "\r\n");
                            ISNText.setText("");
                            if (last == 70) {
                                JOptionPane.showConfirmDialog(null, "已刷入70片ISN，請綁定母掛！", "提示", JOptionPane.OK_OPTION);
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

    //圓角轉換成半角方法
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
            System.out.println("配置文件異常，請聯繫 OA     注： 配置文件格式為 UNICODE");
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
