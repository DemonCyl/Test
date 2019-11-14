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
    static ArrayList cf = new ArrayList();//���ҫH���O�_�꭫��
    static int Total;
    static int actualNum=0;
    static int quantity = 0;
    static Hashtable aa=null;
    private DataHandler dh;
    public static void main(String args[]) {
        JFrame frame = new JFrame("1721�J�w�d��");
        JPanel panel = new JPanel();
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // �K�[���O
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

        JLabel title = new JLabel("1721�X�w/�f���@�~");
        title.setBounds(240, 20, 220, 25);
        title.setFont(new Font("���^", Font.BOLD, 16));
        panel.add(title);

        //�ĤG�ơC�C�C�C
        startPick = new JButton("�}�l�d��");
        startPick.setBounds(20, 60, 100, 30);
        panel.add(startPick);

        jButtonMandatoryPacking = new JButton("�j��X�w");
        jButtonMandatoryPacking.setBounds(200, 60, 100, 30);
        panel.add(jButtonMandatoryPacking);

        cleanOut = new JButton("�M��");
        cleanOut.setBounds(380, 60, 100, 30);
        panel.add(cleanOut);

        //�ĤT�ơC�C�C�C
        //ERP�J�w�渹Label
        JLabel JHnumber = new JLabel("ERP�X�f�q����:");
        JHnumber.setBounds(30, 100, 100, 25);
        panel.add(JHnumber);

        //�]�m�`�̪O�c��Label
        ZBNumberText = new JTextField();
        ZBNumberText.setBounds(130, 100, 160, 25);

        panel.add(ZBNumberText);

        //�]�m�̪O�`�c�����
        ZBXNumberText = new JTextField();
        ZBXNumberText.setBounds(190, 100, 80, 25);
        ZBXNumberText.setText("�`�̪O�c��");
        ZBXNumberText.setEditable(false);

        //�]�m�s���cLabel
        JLabel JHSnumber = new JLabel("�Ȥ�W��:");
        JHSnumber.setBounds(310, 100, 60, 25);
        panel.add(JHSnumber);

        //�]�m�s���c����ܮ�
        ZBSXNumberText = new JTextField();
        ZBSXNumberText.setBounds(370, 100, 140, 25);
        ZBSXNumberText.setEditable(false);
        panel.add(ZBSXNumberText);

        //�ĤT�ơC�C�C�C
        //�]�m���y�J�wLabel
        JLabel SMOrderLabel = new JLabel("���y�X�w:");
        SMOrderLabel.setBounds(50, 130, 60, 25);
        panel.add(SMOrderLabel);

        //�]�m���y���̪O��\�c����ܮ�
        SMOrderText = new JTextField(10);
        SMOrderText.setBounds(130, 130, 160, 25);
        SMOrderText.setEditable(false);
        panel.add(SMOrderText);


        //�ĥ|��
        table = new JTable(tableModel);
        table.setEnabled(false);
        JScrollPane tableJ = new JScrollPane(table);
        tableJ.setBounds(30, 160, 500, 150);
        columnNames.add("�Ȥ�Ƹ�");
        columnNames.add("�ƶq");
        columnNames.add("�w��ƶq");

        tableJ.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(tableJ);
        scannedNewsText = new JTextArea();
        scannedNewsText.setBounds(30, 160, 500, 100);
        scannedNewsText.setEditable(false);
        JScrollPane scannedNewsScroll = new JScrollPane(scannedNewsText);
        scannedNewsScroll.setBounds(30, 160, 500, 150);
        scannedNewsScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        //�Ĥ���
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

        startPick.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.setRowCount(0);
                Check();
            }
        });

        //�M�����s����ť�ƥ�
        cleanOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int tj = JOptionPane.showConfirmDialog(null, "�O�_�M���ƾ�", "����", JOptionPane.YES_NO_OPTION);
               if (tj==0){
                   clear();
               }
            }
        });
        //�j��X�w
        jButtonMandatoryPacking.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                int tj = JOptionPane.showConfirmDialog(null, "�O�_�i��j��X�w", "����", JOptionPane.YES_NO_OPTION);
                if(tj==0) {
                    start();
                    clear();
                }
            }
        });
        //��J�X�f�q����^���ƥ�
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
        //���y�X�w�^���ƥ�
        SMOrderText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    back();//�����P�_
                    boolean status = checkLockBox();//�T�{�X�f�c�O�_��w
                    if (status) {
                        Judge();//sql �ƾ����ҧP�_
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

    //�^����f�H��  �P���p
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
            LogText.setText("�п�J�X�f�q����");
            JOptionPane.showConfirmDialog(null,"�п�J�X�f�q����","ĵ�i",JOptionPane.PLAIN_MESSAGE);
            SoundUtil.PlayNo();//��ܼ����n��
            return;
        }
        if(cws.ship(ZBNumberText.getText())>0){
            LogText.setText("���X�w��w�X�w");
            JOptionPane.showConfirmDialog(null,"���X�w��w�X�w","ĵ�i",JOptionPane.PLAIN_MESSAGE);
            SoundUtil.PlayNo();//��ܼ����n��
            return;
        };
        //����ݲM��ƾ�
        v = cws.itemInformation(ZBNumberText.getText());
        if (v != null) {
            //�ƾڦ^��table��
            showTable(v); //�P���p
            ZBNumberText.setEditable(false);
            SMOrderText.setEditable(true);
            startPick.setEnabled(false);
        } else {
            SoundUtil.PlayNo();//��ܼ����n��
            LogText.setText("�X�f�q���椣�s�b");
            JOptionPane.showConfirmDialog(null,"�X�f�q���椣�s�b","ĵ�i",JOptionPane.PLAIN_MESSAGE);

        }
    }

    public static void clear() {
        //�̪O 1
        //�M�Ŧs�x�ƾ�
        v=null;
        ccc=0;
        qty=0;
        Total=0;
        actualNum=0;
        quantity=0;
        cf.clear();
        c.clear();
        //�M�����
        tableModel.setRowCount(0);
        ZBSXNumberText.setText("");
        //���y�J�w
        SMOrderText.setText("");
        //ERP�J�w�渹
        ZBNumberText.setText("");
        ZBNumberText.setEditable(true);
        SMOrderText.setEditable(false);
        startPick.setEnabled(true);
        SoundUtil.PlayNice();
        LogText.setText("�ާ@���\");

    }

    //�c�� �δ̪O�H���d��
    public static void Judge() {
        //�c���̪O�H������
        Hashtable xxx=cws.veriFication(SMOrderText.getText());
		if(xxx == null){
			JOptionPane.showConfirmDialog(null,"�L���c��/�̪O��","ĵ�i",JOptionPane.PLAIN_MESSAGE);
			SoundUtil.PlayNo();
			return;
		}
        if ( xxx.get("SHIP_OID")!=null){
            SoundUtil.PlayNo();//��ܼ����n��
            LogText.setText("���c��/�̪O���w�X�w,�Юֹ�");
            JOptionPane.showConfirmDialog(null,"���c��/�̪O���w�X�w,�Юֹ�","ĵ�i",JOptionPane.PLAIN_MESSAGE);
            return;
        }else if (Integer.parseInt(xxx.get("IS_CLOSE").toString())!=1){
            SoundUtil.PlayNo();//��ܼ����n��
            LogText.setText("���c����ʽc/�̪O�����ʪO,�Юֹ�");
            JOptionPane.showConfirmDialog(null,"���c����ʽc/�̪O�����ʪO,�Юֹ�","ĵ�i",JOptionPane.PLAIN_MESSAGE);

            return;
        }
        for (int i = 1; i < v.size(); i++) {
            ccc = Integer.parseInt(table.getValueAt(i - 1, 2).toString());
            Total = Integer.parseInt(table.getValueAt(i - 1, 1).toString());
            qty = cws.CARTON(SMOrderText.getText(), table.getValueAt(i - 1, 0).toString());
            ccc +=qty ;
            //���ҨC���ڼƶq�O�_�W�L�ݨD�ƶq
			if(!(i < v.size() - 1 && table.getValueAt(i - 1, 0).toString().equals(table.getValueAt(i, 0).toString()))){
				if (Total < ccc) {
					SoundUtil.PlayNo();//��ܼ����n��
					LogText.setText(table.getValueAt(i - 1, 0) + "�Ƹ��ƶq���X,�Ф��");
					JOptionPane.showConfirmDialog(null,table.getValueAt(i - 1, 0) + "�Ƹ��ƶq���X,�Ф��","ĵ�i",JOptionPane.PLAIN_MESSAGE);

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
					//�^��w���y�ƶq
					table.setValueAt(ccc, i - 1, 2);
					break;
				}
			}
        }
        //����ݨ��f�ƶq�`��
          quantity = 0;
        for (int j = 1; j < v.size(); j++) {
            quantity += Integer.parseInt(table.getValueAt(j - 1, 1).toString());
        }
        //�����ڨ��f�ƶq�`��
        int actual = 0;
        for (int n = 1; n < v.size(); n++) {
            actual += Integer.parseInt(table.getValueAt(n - 1, 2).toString());
        }
        //�N�c���s�x��list��
        cf.add(SMOrderText.getText());
        if (quantity <= actual) {
            SoundUtil.PlayNo();//��ܼ����n��
            LogText.setText("�ƶq�w��,�ХX�f");
            int tj = JOptionPane.showConfirmDialog(null, "�ƶq�w��,�ХX�f", "����", JOptionPane.YES_NO_OPTION);
            if (tj == 0) {
                //�O�s��ƾڮw�}�M�������w�s
                start();
                clear();
            } else {
                //�M���ƾ�
                int  tj2=JOptionPane.showConfirmDialog(null, "�O�_�����X�w", "����", JOptionPane.YES_NO_OPTION);
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
            LogText.setText("�ƶq�w��,�ХX�f");
            int tj = JOptionPane.showConfirmDialog(null, "�ƶq�w��,�ХX�f", "����", JOptionPane.YES_NO_OPTION);
            if (tj == 0) {
                //�O�s��ƾڮw�}�M�������w�s
                start();
                clear();
            } else {
                //�M���ƾ�
                int  tj2=JOptionPane.showConfirmDialog(null, "�O�_�����X�w", "����", JOptionPane.YES_NO_OPTION);
                if (tj2==0){
                    clear();
                }else{
                    Judges();
                }
            }
    }

    public static void start() {
        //���J���Y�ƾ�
        System.out.println(ZBNumberText.getText()+" "+ v.get(1).get("OGB04").toString()+" "+ (String) v.get(1).get("OGA032"));
        java.util.List<Hashtable> ht= cws.MES_Pack_Ship(ZBNumberText.getText(), v.get(1).get("OGB04").toString(), (String) v.get(1).get("OGA032"));
        //���J�樭���Ӽƾ�
        ArrayList list =new ArrayList();
        String data=null;
        System.out.println(v.size());
       for (int i = 1; i <v.size() ; i++) {
            data=v.get(i).get("OGB04")+"','"+v.get(i).get("OGB11")+"',"+v.get(i).get("OGB12")+","+table.getValueAt(i - 1,2);
            list.add(data);
        }
        cws.MES_Pack_Ship_D(ht.get(1).get("SHIP_OID").toString(),list);
        //��s�ʽc ���O�ƾڬO�_�X�f
         cws.ToMES_PACK(cf,ht.get(1).get("SHIP_OID").toString());
		 
		/*String device = "";
		try{
			ReadConfig2 a = new ReadConfig2();
			a.hashINI();
			device = a.iniHash.get("epega.exe.device").toString();
		}catch (Exception e) {
			JOptionPane.showConfirmDialog(null, "�t�m��󲧱`�A���pô OA     �`�G �t�m���榡�� UNICODE", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
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
            JOptionPane.showConfirmDialog(null, "�L���I�s���`", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
        } finally {
			passForProcedure.close();
		}
	}
	
    public static void back() {
        //���ҬO�_�꭫�ƽc��
        if (cf.contains(SMOrderText.getText())) {
            SoundUtil.PlayNo();//��ܼ����n��
            LogText.setText("�c��/�̪O�H���꭫��");
            JOptionPane.showConfirmDialog(null,"�c��/�̪O�H���꭫��","ĵ�i",JOptionPane.PLAIN_MESSAGE);
            SMOrderText.setText("");
            return;
        }

    }

    public static boolean checkLockBox() {
        //���o��w�c��
        java.util.List<Hashtable> lockBoxList = cws.checkLockBox(SMOrderText.getText());
        if (lockBoxList != null && lockBoxList.size() > 0) {
            StringBuffer boxList = new StringBuffer();
            for (int i = 1; i < lockBoxList.size(); i++) {
                boxList.append(lockBoxList.get(i).get("CARTON"));
                if (i != lockBoxList.size() - 1) {
                    boxList.append(",");
                }
            }
            SoundUtil.PlayNo();//��ܼ����n��
            LogText.setText("�w��w�c��" + boxList + ",�Юֹ�");
            JOptionPane.showConfirmDialog(null, "�w��w�c��" + boxList + ",�Юֹ�", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
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
