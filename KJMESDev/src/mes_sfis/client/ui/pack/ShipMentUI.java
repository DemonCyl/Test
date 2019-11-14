package mes_sfis.client.ui.pack;

import base.client.ui.BasePanel;
import base.client.util.component.P_Component_Meta;
import base.vo.UI_InitVO;
import mes_sfis.client.model.service.ShipMentService;
import mes_sfis.client.util.DataHandler;
import mes_sfis.client.util.SoundUtil;
import mes_sfis.client.util.ReadConfig2;
import mes_sfis.client.sfis.PassForProcedure;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.HashMap;
import java.util.Vector;

/**
 * Created by Efil_Ding on 2018/4/11.
 */
public class ShipMentUI extends BasePanel {
    public static final String VERSION    = "$Name: $, $Id: ShipMentUI.java,v 1.16 2018/04/16 10:31:07 Lucy6_Lu Exp $";
    public static final String COPYRIGHT  = "Copyright (c) 2008 PEGATRON Inc. All Rights Reserved.";
    private static JTextField ZBSXNumberText, SMOrderText, ZBNumberText, ZBXNumberText;
    private static JTable table = null;
    private static JTextArea scannedNewsText, LogText;
    private static JButton startPick, cleanOut,jButtonMandatoryPacking;
    private static DefaultTableModel tableModel = new DefaultTableModel();
    private static Vector columnNames = new Vector();
    private static Vector tableValues = new Vector();
    private static Vector c;
    static UI_InitVO uiVO;
    private JPanel contentPanel = super.UILayoutPanel;
    static ShipMentService cws;
    static java.util.List<Hashtable> v = null;
    static int sumQty = 0;
    static int ccc = 0;
    static int qty = 0;
    static ArrayList cf = new ArrayList();//驗證信息是否刷重複
    static int Total;
    static int actualNum=0;
    static int quantity = 0;
    static Hashtable aa=null;
    private DataHandler dh;
    public static void main(String args[]) {
        JFrame frame = new JFrame("1721入庫查驗");
        JPanel panel = new JPanel();
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 添加面板
        frame.add(panel);
        placeComponents(panel);
        frame.setVisible(true);
    }

    public ShipMentUI(UI_InitVO uiVO) {
        super(uiVO);
        cws = new ShipMentService(uiVO);

        this.init();
    }
    void init() {
        placeComponents(contentPanel);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel title = new JLabel("1721出庫/貨比對作業");
        title.setBounds(240, 20, 220, 25);
        title.setFont(new Font("宋体", Font.BOLD, 16));
        panel.add(title);

        //第二排。。。。
        startPick = new JButton("開始查驗");
        startPick.setBounds(20, 60, 100, 30);
        panel.add(startPick);

        jButtonMandatoryPacking = new JButton("強制出庫");
        jButtonMandatoryPacking.setBounds(200, 60, 100, 30);
        panel.add(jButtonMandatoryPacking);

        cleanOut = new JButton("清除");
        cleanOut.setBounds(380, 60, 100, 30);
        panel.add(cleanOut);

        //第三排。。。。
        //ERP入庫單號Label
        JLabel JHnumber = new JLabel("ERP出貨通知單:");
        JHnumber.setBounds(30, 100, 100, 25);
        panel.add(JHnumber);

        //設置總棧板箱數Label
        ZBNumberText = new JTextField();
        ZBNumberText.setBounds(130, 100, 160, 25);

        panel.add(ZBNumberText);

        //設置棧板總箱數顯示
        ZBXNumberText = new JTextField();
        ZBXNumberText.setBounds(190, 100, 80, 25);
        ZBXNumberText.setText("總棧板箱數");
        ZBXNumberText.setEditable(false);

        //設置零散箱Label
        JLabel JHSnumber = new JLabel("客戶名稱:");
        JHSnumber.setBounds(310, 100, 60, 25);
        panel.add(JHSnumber);

        //設置零散箱數顯示框
        ZBSXNumberText = new JTextField();
        ZBSXNumberText.setBounds(370, 100, 140, 25);
        ZBSXNumberText.setEditable(false);
        panel.add(ZBSXNumberText);

        //第三排。。。。
        //設置掃描入庫Label
        JLabel SMOrderLabel = new JLabel("掃描出庫:");
        SMOrderLabel.setBounds(50, 130, 60, 25);
        panel.add(SMOrderLabel);

        //設置掃描的棧板號\箱號顯示框
        SMOrderText = new JTextField(10);
        SMOrderText.setBounds(130, 130, 160, 25);
        SMOrderText.setEditable(false);
        panel.add(SMOrderText);


        //第四排
        table = new JTable(tableModel);
        table.setEnabled(false);
        JScrollPane tableJ = new JScrollPane(table);
        tableJ.setBounds(30, 160, 500, 150);
        columnNames.add("客戶料號");
        columnNames.add("數量");
        columnNames.add("已刷數量");

        tableJ.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(tableJ);
        scannedNewsText = new JTextArea();
        scannedNewsText.setBounds(30, 160, 500, 100);
        scannedNewsText.setEditable(false);
        JScrollPane scannedNewsScroll = new JScrollPane(scannedNewsText);
        scannedNewsScroll.setBounds(30, 160, 500, 150);
        scannedNewsScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        //第五排
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

        startPick.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.setRowCount(0);
                Check();
            }
        });

        //清除按鈕的監聽事件
        cleanOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int tj = JOptionPane.showConfirmDialog(null, "是否清除數據", "提示", JOptionPane.YES_NO_OPTION);
               if (tj==0){
                   clear();
               }
            }
        });
        //強制出庫
        jButtonMandatoryPacking.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                int tj = JOptionPane.showConfirmDialog(null, "是否進行強制出庫", "提示", JOptionPane.YES_NO_OPTION);
                if(tj==0) {
                    start();
                    clear();
                }
            }
        });
        //輸入出貨通知單回車事件
        ZBNumberText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {

                    System.out.println("aaaaa");
                    Check();
                }
            }
        });
        //掃描出庫回車事件
        SMOrderText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    back();//頁面判斷
                    boolean status = checkLockBox();//確認出貨箱是否鎖定
                    if (status) {
                        Judge();//sql 數據驗證判斷
                    }
                    SMOrderText.setText("");
                }
            }

            public void removeUpdate(DocumentEvent e) {
                //  System.out.println("removeUpdate");
                // System.out.println("nihao");
            }

            public void changedUpdate(DocumentEvent e) {
                //  System.out.println("changedUpdate");
            }
        });
    }

    //回填取貨信息  周海峰
    public static void showTable(java.util.List<Hashtable> v) {
        for (int i = 1; i < v.size(); i++) {
             aa = v.get(i);
            ZBSXNumberText.setText((String) aa.get("OGA032"));
            c = new Vector();
            c.add(aa.get("OGB11"));
            c.add(aa.get("OGB12"));
            c.add(0);
            tableValues.add(c);
            tableModel.setColumnCount(3);
            tableModel.setDataVector(tableValues, columnNames);
        }
    }

    public static void Check() {

        if (ZBNumberText.getText().equals("")) {
            LogText.setText("請輸入出貨通知單");
            JOptionPane.showConfirmDialog(null,"請輸入出貨通知單","警告",JOptionPane.PLAIN_MESSAGE);
            SoundUtil.PlayNo();//選擇播放聲音
            return;
        }
        if(cws.ship(ZBNumberText.getText())>0){
            LogText.setText("此出庫單已出庫");
            JOptionPane.showConfirmDialog(null,"此出庫單已出庫","警告",JOptionPane.PLAIN_MESSAGE);
            SoundUtil.PlayNo();//選擇播放聲音
            return;
        };
        //獲取需清單數據
        v = cws.itemInformation(ZBNumberText.getText());
        if (v != null) {
            //數據回填table中
            showTable(v); //周海峰
            ZBNumberText.setEditable(false);
            SMOrderText.setEditable(true);
            startPick.setEnabled(false);
        } else {
            SoundUtil.PlayNo();//選擇播放聲音
            LogText.setText("出貨通知單不存在");
            JOptionPane.showConfirmDialog(null,"出貨通知單不存在","警告",JOptionPane.PLAIN_MESSAGE);

        }
    }

    public static void clear() {
        //棧板 1
        //清空存儲數據
        v=null;
        ccc=0;
        qty=0;
        Total=0;
        actualNum=0;
        quantity=0;
        cf.clear();
        c.clear();
        //清除表格
        tableModel.setRowCount(0);
        ZBSXNumberText.setText("");
        //掃描入庫
        SMOrderText.setText("");
        //ERP入庫單號
        ZBNumberText.setText("");
        ZBNumberText.setEditable(true);
        SMOrderText.setEditable(false);
        startPick.setEnabled(true);
        SoundUtil.PlayNice();
        LogText.setText("操作成功");

    }

    //箱號 或棧板信息查詢
    public static void Judge() {
        //箱號棧板信息驗證
        Hashtable xxx=cws.veriFication(SMOrderText.getText());
		if(xxx == null){
			JOptionPane.showConfirmDialog(null,"無此箱號/棧板號","警告",JOptionPane.PLAIN_MESSAGE);
			SoundUtil.PlayNo();
			return;
		}
        if ( xxx.get("SHIP_OID")!=null){
            SoundUtil.PlayNo();//選擇播放聲音
            LogText.setText("此箱號/棧板號已出庫,請核實");
            JOptionPane.showConfirmDialog(null,"此箱號/棧板號已出庫,請核實","警告",JOptionPane.PLAIN_MESSAGE);
            return;
        }else if (Integer.parseInt(xxx.get("IS_CLOSE").toString())!=1){
            SoundUtil.PlayNo();//選擇播放聲音
            LogText.setText("此箱號位封箱/棧板號未封板,請核實");
            JOptionPane.showConfirmDialog(null,"此箱號位封箱/棧板號未封板,請核實","警告",JOptionPane.PLAIN_MESSAGE);

            return;
        }
        for (int i = 1; i < v.size(); i++) {
            ccc = Integer.parseInt(table.getValueAt(i - 1, 2).toString());
            Total = Integer.parseInt(table.getValueAt(i - 1, 1).toString());
            qty = cws.CARTON(SMOrderText.getText(), table.getValueAt(i - 1, 0).toString());
            ccc +=qty ;
            //驗證每行實際數量是否超過需求數量
			if(!(i < v.size() - 1 && table.getValueAt(i - 1, 0).toString().equals(table.getValueAt(i, 0).toString()))){
				if (Total < ccc) {
					SoundUtil.PlayNo();//選擇播放聲音
					LogText.setText(table.getValueAt(i - 1, 0) + "料號數量溢出,請比對");
					JOptionPane.showConfirmDialog(null,table.getValueAt(i - 1, 0) + "料號數量溢出,請比對","警告",JOptionPane.PLAIN_MESSAGE);

					break;
				}
			}
			if(i < v.size() - 1 && table.getValueAt(i - 1, 0).toString().equals(table.getValueAt(i, 0).toString()) && 
			table.getValueAt(i - 1, 1).toString().equals(table.getValueAt(i - 1, 2).toString())){
				
			}else{
				if(i > 1 && table.getValueAt(i - 1, 0).toString().equals(table.getValueAt(i - 2, 0).toString()) && 
				Integer.parseInt(table.getValueAt(i - 2, 1).toString()) == Integer.parseInt(table.getValueAt(i - 2, 2).toString())){
					table.setValueAt(ccc, i - 1, 2);
				}else{
					//回填已掃描數量
					table.setValueAt(ccc, i - 1, 2);
					break;
				}
			}
        }
        //獲取需取貨數量總數
          quantity = 0;
        for (int j = 1; j < v.size(); j++) {
            quantity += Integer.parseInt(table.getValueAt(j - 1, 1).toString());
        }
        //獲取實際取貨數量總數
        int actual = 0;
        for (int n = 1; n < v.size(); n++) {
            actual += Integer.parseInt(table.getValueAt(n - 1, 2).toString());
        }
        //將箱號存儲到list中
        cf.add(SMOrderText.getText());
        if (quantity <= actual) {
            SoundUtil.PlayNo();//選擇播放聲音
            LogText.setText("數量已滿,請出貨");
            int tj = JOptionPane.showConfirmDialog(null, "數量已滿,請出貨", "提示", JOptionPane.YES_NO_OPTION);
            if (tj == 0) {
                //保存到數據庫并清除頁面緩存
                start();
                clear();
            } else {
                //清除數據
                int  tj2=JOptionPane.showConfirmDialog(null, "是否取消出庫", "提示", JOptionPane.YES_NO_OPTION);
                if (tj2==0){
                    clear();
                }else {
                    Judges();
                }
            }
        }else{
			SoundUtil.PlayNice();
		}
    }
    public static void Judges(){
            LogText.setText("數量已滿,請出貨");
            int tj = JOptionPane.showConfirmDialog(null, "數量已滿,請出貨", "提示", JOptionPane.YES_NO_OPTION);
            if (tj == 0) {
                //保存到數據庫并清除頁面緩存
                start();
                clear();
            } else {
                //清除數據
                int  tj2=JOptionPane.showConfirmDialog(null, "是否取消出庫", "提示", JOptionPane.YES_NO_OPTION);
                if (tj2==0){
                    clear();
                }else{
                    Judges();
                }
            }
    }

    public static void start() {
        //插入單頭數據
        System.out.println(ZBNumberText.getText()+" "+ v.get(1).get("OGB04").toString()+" "+ (String) v.get(1).get("OGA032"));
        java.util.List<Hashtable> ht= cws.MES_Pack_Ship(ZBNumberText.getText(), v.get(1).get("OGB04").toString(), (String) v.get(1).get("OGA032"));
        //插入單身明細數據
        ArrayList list =new ArrayList();
        String data=null;
        System.out.println(v.size());
       for (int i = 1; i <v.size() ; i++) {
            data=v.get(i).get("OGB04")+"','"+v.get(i).get("OGB11")+"',"+v.get(i).get("OGB12")+","+table.getValueAt(i - 1,2);
            list.add(data);
        }
        cws.MES_Pack_Ship_D(ht.get(1).get("SHIP_OID").toString(),list);
        //更新封箱 打板數據是否出貨
         cws.ToMES_PACK(cf,ht.get(1).get("SHIP_OID").toString());
		 
		/*String device = "";
		try{
			ReadConfig2 a = new ReadConfig2();
			a.hashINI();
			device = a.iniHash.get("epega.exe.device").toString();
		}catch (Exception e) {
			JOptionPane.showConfirmDialog(null, "配置文件異常，請聯繫 OA     注： 配置文件格式為 UNICODE", "警告", JOptionPane.PLAIN_MESSAGE);
		}
		for(int m = 0;m < cf.size();m++){
			String carton = "";
			if(cf.get(m) != null)
				carton = (String)cf.get(m);
			Vector vtISN = cws.getIsn(carton);
			if(vtISN != null && vtISN.size() > 1){
				for(int i = 1;i < vtISN.size();i++){
					Hashtable htISN = new Hashtable();
					htISN = (Hashtable)vtISN.get(i);
					procedureFun(device,carton,(String)htISN.get("M_SN"));
				}
			}
		}*/
    }
	
	private static void procedureFun(String device,String cartonNo,String isn){
		PassForProcedure passForProcedure = new PassForProcedure();
		try {
			passForProcedure.open();
			HashMap map = new HashMap();
			map.put("DEVICE", device);
			map.put("OP", uiVO.getLogin_id());
			map.put("STATUS", "2");
			passForProcedure.procedureLogin(map);
			map.put("STATUS", "1");
			passForProcedure.procedureLogin(map);
			map.put("ISN", cartonNo);
			passForProcedure.procedurePass(map);
			map.put("ISN", isn);
			passForProcedure.procedurePass(map);
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, "過站呼叫異常", "警告", JOptionPane.PLAIN_MESSAGE);
        } finally {
			passForProcedure.close();
		}
	}
	
    public static void back() {
        //驗證是否刷重複箱號
        if (cf.contains(SMOrderText.getText())) {
            SoundUtil.PlayNo();//選擇播放聲音
            LogText.setText("箱號/棧板信息刷重複");
            JOptionPane.showConfirmDialog(null,"箱號/棧板信息刷重複","警告",JOptionPane.PLAIN_MESSAGE);
            SMOrderText.setText("");
            return;
        }

    }

    public static boolean checkLockBox() {
        //取得鎖定箱號
        java.util.List<Hashtable> lockBoxList = cws.checkLockBox(SMOrderText.getText());
        if (lockBoxList != null && lockBoxList.size() > 0) {
            StringBuffer boxList = new StringBuffer();
            for (int i = 1; i < lockBoxList.size(); i++) {
                boxList.append(lockBoxList.get(i).get("CARTON"));
                if (i != lockBoxList.size() - 1) {
                    boxList.append(",");
                }
            }
            SoundUtil.PlayNo();//選擇播放聲音
            LogText.setText("已鎖定箱號" + boxList + ",請核實");
            JOptionPane.showConfirmDialog(null, "已鎖定箱號" + boxList + ",請核實", "警告", JOptionPane.PLAIN_MESSAGE);
            return false;
        } else {
            return true;
        }
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
