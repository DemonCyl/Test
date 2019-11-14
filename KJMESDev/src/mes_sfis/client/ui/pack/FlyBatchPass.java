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
    private static JTextField FlyText, HangerText;//飛靶輸入框和母掛輸入框
    private static JTextArea scannedNewsText, LogText;//掃描顯示框和錯誤顯示框
    private static JButton mandatoryPass, cleanOut;//強制過站按鈕和清除按鈕
    private Vector c;//Vector? 是在 java 中可以??自?增?的?象??
    UI_InitVO uiVO;
    private JPanel contentPanel = super.UILayoutPanel;//UI佈局面板；UI用戶界面
    ANOBatchPassService cws;//創建一個ANOBatchPassService對象
    String thisFlyID;//飛靶編號
    HashMap map_hangerid = new HashMap();
    String device = null;//站點
    private ProgressMonitor progressMonitor;
    private Timer timer;
    private LongTask task;
    private String newline = "\n";
    public final static int ONE_SECOND = 1000;
    String flyUID;
    SoundUtilANO soundUtilANO = new SoundUtilANO();

    public void main(String[] args) {
        JFrame frame = new JFrame("母掛綁定飛靶");
        JPanel panel = new JPanel();
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 添加面板
        frame.add(panel);
        placeComponents(panel);
        frame.setVisible(true);
    }

    /**
     * 放置組件的方法，這裡就是畫面板了
     *
     * @param panel
     */
    private void placeComponents(JPanel panel) {
        //放置組件，放面板
        panel.setLayout(null);
        JLabel title = new JLabel("母掛綁定飛靶");
        title.setBounds(240, 20, 500, 25);
        title.setFont(new Font("宋体", Font.BOLD, 16));
        panel.add(title);

        mandatoryPass = new JButton("強制過站");
        mandatoryPass.setBounds(50, 60, 120, 30);
        panel.add(mandatoryPass);
        cleanOut = new JButton("清除");
        cleanOut.setBounds(410, 60, 100, 30);
        panel.add(cleanOut);

        //飛靶編號
        JLabel JHanger = new JLabel("飛靶編號:");
        JHanger.setBounds(50, 100, 60, 25);
        panel.add(JHanger);

        //設置飛靶編號輸入框
        FlyText = new JTextField(8);
        FlyText.setBounds(120, 100, 200, 25);
        FlyText.setText("");
        panel.add(FlyText);

        //母掛掃描
        JLabel SMOrderLabel = new JLabel("母掛掃描:");
        SMOrderLabel.setBounds(50, 130, 60, 25);
        panel.add(SMOrderLabel);

        //母掛掃描輸入框
        HangerText = new JTextField(10);
        HangerText.setBounds(120, 130, 200, 25);
        HangerText.setEditable(false);
        panel.add(HangerText);

        //掃描顯示框
        scannedNewsText = new JTextArea();
        scannedNewsText.setBounds(30, 160, 500, 100);
        JScrollPane jsLogText1 = new JScrollPane(scannedNewsText);
        scannedNewsText.setEditable(false);
        jsLogText1.setBounds(30, 160, 500, 150);
        jsLogText1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(jsLogText1);
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

        //清除按鈕的監聽事件
        cleanOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int tj = JOptionPane.showConfirmDialog(null, "是否清除數據", "提示", JOptionPane.YES_NO_OPTION);
                if (tj == 0) {
                    clear();
                    HangerText.grabFocus();//獲得焦點
                }
            }
        });

        //強制綁定方法
        mandatoryPass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HashMap mapISN = new HashMap();
                int tj = JOptionPane.showConfirmDialog(null, "這是飛靶編號為 " + FlyText.getText().trim() + " 的" + map_hangerid.size() + "個母掛,", "提示", JOptionPane.YES_NO_OPTION);
                if (tj == 0) {
                    int a = 0;
                    while (a == 0) {
                        flyUID = cws.getUID();
                        thisFlyID = FlyText.getText().trim();
                        if (scannedNewsText.getText().isEmpty()) {
                            JOptionPane.showConfirmDialog(null, "沒有ISN信息，無法強制綁定", "提示", JOptionPane.OK_OPTION);
                            break;
                        } else {
                            a = cws.Hanger_FlyBinding(flyUID, thisFlyID, map_hangerid);
                            if (a == 1) {
                                //強制批量綁定
                                mapISN = cws.ISNnumber(mapISN, map_hangerid, thisFlyID);
                                StatePass(device, mapISN, uiVO.getLogin_id());
                                HangerText.grabFocus();
                                map_hangerid.clear();
                                mapISN.clear();
                                clear();
                            } else {
                                LogText.append("稍等片刻！");
                            }
                        }
                    }
                }
            }
        });

        //輸入飛靶編號回車事件
        FlyText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                long time_hm_fly = 0;
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    if (!FlyText.getText().trim().isEmpty()) {//判定飛靶輸入框不等于空
                        FlyText.setEditable(false);//不為空，則飛靶輸入框設置為不可編輯狀態
                        try {
                            time_hm_fly = cws.CheckFly_time(FlyText.getText().trim());
                        } catch (ParseException e1) {
                            logger.debug("檢查飛靶時間異常：" + e1.getMessage());
                            e1.printStackTrace();
                        }
                        if (time_hm_fly != 0 && time_hm_fly <= 7200000) {
                            JOptionPane.showConfirmDialog(null, "飛靶編號已刷，再次刷入必須兩小時以後", "提示", JOptionPane.OK_OPTION);
                            HangerText.setEditable(false);
                            clear();
                        } else {
                            flyUID = cws.getUID();
                            HangerText.grabFocus();//獲得焦點
                            HangerText.setEditable(true);
                        }
                        if (FlyText.getText().trim().matches("[0-9]+")) {
                            flyUID = cws.getUID();
                            HangerText.grabFocus();//獲得焦點
                            HangerText.setEditable(true);
                        } else {
                            JOptionPane.showConfirmDialog(null, "請輸入數字編碼", "提示", JOptionPane.OK_OPTION);
                            clear();
                        }
                    }//則母掛輸入框設置為可編輯狀態
                    else {
                        JOptionPane.showConfirmDialog(null, "飛靶編號為空", "提示", JOptionPane.OK_OPTION);
                    }
                }
            }
        });

        //掃描母掛監聽事件
        HangerText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    check_Hanger(HangerText.getText().toString());//判斷母掛
                }
            }
        });
    }

    public void clear() {
        //清除飛靶編號
        FlyText.setText("");
        //清除母掛編號
        HangerText.setText("");
        //清除 兩個 LOG
        scannedNewsText.setText("");
        LogText.setText("");
        //打開飛靶輸入框，關閉母掛輸入框
        FlyText.setEditable(true);
        HangerText.setEditable(false);
        map_hangerid.clear();
    }

    //判斷母掛是否合格，當母掛數量達到6個時自動過站與飛靶綁定
    public void check_Hanger(String thisHangerID) {
        HashMap mapISN = new HashMap();
        int isBindingISN = cws.CheckHang_ISNisBanding(thisHangerID);
        int isPassDevice = cws.IsPassDevice(thisHangerID);
        //檢查該母掛是否綁定ISN，有沒有ISN信息
        if (isBindingISN == 1) {
            LogText.append("這個母掛:" + thisHangerID + "沒有綁定ISN信息，無法綁定飛靶，請確認！" + "\r\n");
            HangerText.setText("");
            soundUtilANO.PlayNo();
        } else {
            //當母掛號滿足上一個條件，則判斷該母掛綁定的ISN是否已經過站
            if (isPassDevice == 1) {
                LogText.append("這個母掛:" + thisHangerID + "綁定的ISN已經綁定過飛靶,請確認之後再輸入!" + "\r\n");
                HangerText.setText("");
                soundUtilANO.PlayNo();
            } else {
                //滿足以上兩個條件，所有的檢查均已經完成，接下來將母掛號存入map_hangerid
                int first = map_hangerid.size();
                map_hangerid.put(thisHangerID, "1");//把母掛id存在map裡面
                int last = map_hangerid.size();
                if (first == last) {
                    LogText.append("這個母掛重複了" + thisHangerID + "\r\n");
                    HangerText.setText("");
                    soundUtilANO.PlayNo();
                } else {
                    scannedNewsText.append("第" + last + "個：" + thisHangerID + "\r\n");
                    HangerText.setText("");
                    soundUtilANO.PlayOk();
                    if (last == 6) {
                        int tj = JOptionPane.showConfirmDialog(null, "這是飛靶編號為 " + FlyText.getText().trim() + " 的" + map_hangerid.size() + "個,", "提示", JOptionPane.YES_NO_OPTION);
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
                                LogText.append("過站異常！");
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
     * 初始化方法
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
            System.out.println("配置文件異常，請聯繫 OA     注： 配置文件格式為 UNICODE");
            logger.debug("配置文件異常，請聯繫 OA     注： 配置文件格式為 UNICODE");
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
        //保存ISN數據到文件
        cws.txtfile(device, map, op);
    }

    class TimerListener implements ActionListener {//計時器監聽實現動作監聽

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

    public void showBar() {
        progressMonitor = new ProgressMonitor(new JFrame("EFIL"), "批量過站進行中,請稍後", "", 0, task.getLengthOfTask());
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
