package mes_sfis.client.ui.pack;

import base.client.ui.BasePanel;
import base.client.util.component.P_Component_Meta;
import base.vo.UI_InitVO;
import mes_sfis.client.model.service.CInWhseService;
import mes_sfis.client.sfis.PassForProcedure;
import mes_sfis.client.util.SoundUtil;
import mes_sfis.client.util.ReadConfig2;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;
import java.util.List;
import java.util.Map;

public class CheckInWhse extends BasePanel{
    public static final String VERSION    = "$Name:  $, $Id: CheckInWhse.java,v 1.7 2018/04/16 10:08:45 Lucy6_Lu Exp $";
    public static final String COPYRIGHT  = "Copyright (c) 2017 PEGATRON Inc. All Rights Reserved.";
    private static JTextField ZBSXNumberText,SMOrderText,ZBNumberText,ZBXNumberText;
    private static JTextField palletnumGeneral,boxNumPallet,boxNumGeneral,palletnum,boxNum;
    private static JTextArea scannedNewsText,LogText;
    private static JButton startPick,cleanOut;

    private static Map<String,Integer> mapbox=new HashMap<String, Integer>();
    private static Map<String,Integer> mappallet=new HashMap<String, Integer>();
    private static Map<String,String> palletcompare=new HashMap<String, String>();
    private static Map<String,String> palletcarton =new HashMap<String, String>();;
    private static List boxList=null,palletList=null;


    static UI_InitVO uiVO;
    int checkValue;
    private JPanel contentPanel = super.UILayoutPanel;
    private static int sanxNumber=0;//�n���s���c��
    private static int zhanbNumber=0;//�n���̪O��

    static CInWhseService cws;

    public CheckInWhse(UI_InitVO uiVO) {

        super(uiVO);
        cws = new CInWhseService(uiVO);
        this.init();
    }

    void init() {
        // ?�� JFrame ?��
        //JFrame frame = new JFrame("1721�J�w�d��");
        // Setting the width and height of frame
        //frame.setSize(600, 600);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // �K�[���O
        //frame.add(panel);
        palletcompare.put("before",null);

        placeComponents(contentPanel);
        // ?�m�ɭ��i?
        //frame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
         /* ����������???���h����?
         * ???�m����? null
         */

        JLabel title = new JLabel("1721�J�w�d��");
        title.setBounds(240,20,120,25);
        title.setFont(new Font("���^", Font.BOLD, 16));
        panel.add(title);

        //�ĤG�ơC�C�C�C
        startPick= new JButton("�}�l�d��");
        startPick.setBounds(100,60,100,30);
        panel.add(startPick);

        cleanOut= new JButton("�M��");
        cleanOut.setBounds(380,60,100,30);
        panel.add(cleanOut);

        //�ĤT�ơC�C�C�C
        //ERP�J�w�渹Label
        JLabel JHnumber=new JLabel("ERP�J�w�渹:");
        JHnumber.setBounds(50,100,100,25);
        panel.add(JHnumber);

        //�]�m�`�̪O�c��Label
        ZBNumberText = new JTextField();
        ZBNumberText.setBounds(130,100,120,25);

//        ZBNumberText.setEditable(false);
        panel.add(ZBNumberText);

        //�]�m�̪O�`�c�����
        ZBXNumberText = new JTextField();
        ZBXNumberText.setBounds(190,100,80,25);
        ZBXNumberText.setText("�`�̪O�c��");
        ZBXNumberText.setEditable(false);
//              panel.add(ZBXNumberText);


        JLabel JHSnumber=new JLabel("�߳f�M�渹:");
        JHSnumber.setBounds(320,100,100,25);
        panel.add(JHSnumber);


        ZBSXNumberText = new JTextField();
        ZBSXNumberText.setBounds(390,100,130,25);
        ZBSXNumberText.setEditable(false);
        panel.add(ZBSXNumberText);

        //�ĥ|�ơC�C�C�C
        JLabel CheckGeneral=new JLabel("�˳f�M�淧�n:");
        CheckGeneral.setBounds(50,140,120,25);
        panel.add(CheckGeneral);

        palletnumGeneral = new JTextField();
        palletnumGeneral.setBounds(130,140,60,25);
        palletnumGeneral.setHorizontalAlignment(JTextField.RIGHT);
        palletnumGeneral.setText("0");
        palletnumGeneral.setEditable(false);
        panel.add(palletnumGeneral);

        final JLabel palletGeneral=new JLabel("�̪O,�`�p");
        palletGeneral.setBounds(191,140,70,25);
        panel.add(palletGeneral);

        boxNumPallet = new JTextField();
        boxNumPallet.setBounds(260,140,60,25);
        boxNumPallet.setHorizontalAlignment(JTextField.RIGHT);
        boxNumPallet.setText("0");
        boxNumPallet.setEditable(false);
        panel.add(boxNumPallet);

        JLabel boxLabel=new JLabel("�c");
        boxLabel.setBounds(322,140,20,25);
        panel.add(boxLabel);

        //�]�m�ƶqLabel
        JLabel boxGeneral=new JLabel("�s���c:");
        boxGeneral.setBounds(370,140,50,25);
        panel.add(boxGeneral);

        //�]�m�ƶq��ܮ�
        boxNumGeneral = new JTextField();
        boxNumGeneral.setBounds(420,140,60,25);
        boxNumGeneral.setHorizontalAlignment(JTextField.RIGHT);
        boxNumGeneral.setText("0");
        boxNumGeneral.setEditable(false);
        panel.add(boxNumGeneral);

        JLabel boxPallet=new JLabel("�c");
        boxPallet.setBounds(481,140,20,25);
        panel.add(boxPallet);

        //�Ĥ��ơC�C�C�C
        //�]�m���y�J�wLabel
        JLabel SMOrderLabel=new JLabel("���y�J�w:");
        SMOrderLabel.setBounds(50,180,60,25);
        panel.add(SMOrderLabel);

        //�]�m���y���̪O��\�c����ܮ�
        SMOrderText = new JTextField(10);
        SMOrderText.setBounds(130,180,120,25);
        SMOrderText.setEditable(false);
        //  SMOrderText.setEditable(false);
        panel.add(SMOrderText);

        palletnum = new JTextField();
        palletnum.setBounds(255,180,60,25);
        palletnum.setHorizontalAlignment(JTextField.RIGHT);
        palletnum.setText("x/X");
        palletnum.setEditable(false);
        panel.add(palletnum);

        JLabel pallet=new JLabel("�̪O");
        pallet.setBounds(316,180,30,25);
        panel.add(pallet);

        //�]�m�ƶqLabel
        JLabel TotalNumberLabel=new JLabel("�s���c:");
        TotalNumberLabel.setBounds(370,180,50,25);
        panel.add(TotalNumberLabel);

        //�]�m�ƶq��ܮ�
        boxNum = new JTextField();
        boxNum.setBounds(420,180,60,25);
        boxNum.setHorizontalAlignment(JTextField.RIGHT);
        boxNum.setText("x/X");
        boxNum.setEditable(false);
        panel.add(boxNum);
        //�c �r
        final JLabel box=new JLabel("�c");
        box.setBounds(481,180,20,25);
        panel.add(box);


        //   String [] str={"sahfdagfh jk","suhdfaiufhiaushfu","asjkfhsaaskjfhjsakhsdabfisfhnisahfsaih","jsdfhashfjksahsukhfjakshfjksahjkhgfajksfgsajkjjskahgjkas"};
        scannedNewsText = new JTextArea();
        scannedNewsText.setBounds(30,220,500,100);
        // CheckSSNText.setLineWrap(true);    //?�m��??��,��??��?��?�X??�V��???
        // CheckSSNText.setEditable(false);
        scannedNewsText.setEditable(false);
        JScrollPane scannedNewsScroll = new JScrollPane(scannedNewsText);
        scannedNewsScroll.setBounds(30,220,500,150);
        scannedNewsScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(scannedNewsScroll);


        JLabel log=new JLabel("LOG");
        log.setFont(new Font("���^", Font.BOLD, 16));
        log.setBounds(30,380,40,25);
        panel.add(log);

        LogText = new JTextArea();
        LogText.setBounds(30,400,500,150);
        JScrollPane jsLogText = new JScrollPane(LogText);
        //  Point p = new Point();
        //  p.setLocation(0, 150);
        //  jsLogText.getViewport().setViewPosition(p);
        LogText.setEditable(false);
        jsLogText.setBounds(30,400,500,150);
        jsLogText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(jsLogText);

        //�}�l�˳f���s����ť�ƥ�
        startPick.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Check();
                checkValue=cws.projectCheck();

            }
        });

        //�M�����s����ť�ƥ�
        cleanOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
            }


//                map.clear();


        });
        //���y����ť
        SMOrderText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {

                    Judge();
                    back();
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
        ZBNumberText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    Check();
                    checkValue=cws.projectCheck();

                    System.out.println(checkValue+"=======");
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

    //�M����?
    public  void clear(){
        //LOG
        LogText.setText("");
        //�̪O 1
        palletnumGeneral.setText("0");
        //�c�A�̪O1
        boxNumPallet.setText("0");
        //�s���c1
        boxNumGeneral.setText("0");
        //�̪O2
        palletnum.setText("0/X");
        //�s���c2
        boxNum.setText("0/X");

        ZBSXNumberText.setText("");
        //���y�J�w
        SMOrderText.setText("");
        //ERP�J�w�渹
        ZBNumberText.setText("");
        //
        // ZBXNumberText.setText("d");
        //ISN�H��


        mappallet.clear();
        mapbox.clear();
        ZBNumberText.setEditable(true);
        SMOrderText.setEditable(false);
        startPick.setEnabled(true);
    }
    //?�l�d?��?
    public void Check(){

        String pallet=ZBNumberText.getText();
        Hashtable ht=cws.CheckInfo(pallet);
        String piclno=(String)ht.get("SFUUD03");

        if(piclno!=null) {
            //?��  PICK_OID
            ht= cws.Getinfo(piclno);
            String str=ht.get("PICK_OID").toString();
            //�s���c��?�q
            ht=cws.GetBoxNo(str);
            int box_num=Integer.parseInt(ht.get("CARTON_NUM").toString());
            //?��?�O?�c��??�q
            ht=cws.GetBoxNo_T(str);
            int box_num_t=Integer.parseInt(ht.get("CARTON_NUM").toString());

            //?���O��??�q
            ht=cws.GetPalletNo(str);
            String pallet_num=ht.get("PALLET_NUM").toString();


            //�˳f�M�渹
            ZBSXNumberText.setText(piclno);

            //�̪O 1
            //palletnumGeneral.setText("0");

            palletnumGeneral.setText(pallet_num);
            //�c�A�̪O1
            boxNumPallet.setText(box_num_t+"");
            //�s���c1
            boxNumGeneral.setText(box_num+"");
            //  boxNumGeneral.setText("0");
            //�̪O2
            palletnum.setText("0/"+pallet_num);
            //�s���c2
            boxNum.setText("0/"+box_num);
            //  boxNum.setText("0/"+"X");
            LogText.setText("");
            scannedNewsText.setText("");
            startPick.setEnabled(false);
            ZBNumberText.setEditable(false);
            SMOrderText.setEditable(true);
        }

    }
    //???�y��?�u,�O�_�s�b�_���M?�A�����`???
    public  void Judge(){

        String pallet=ZBSXNumberText.getText();
        String noid=SMOrderText.getText();
        int type;
        if (checkValue==1){
            type=cws.CheckOk(pallet,noid,palletcompare.get("before"));
            type(type,noid);
        }else if (checkValue==2){
            type=cws.CheckOkChou(pallet,noid,palletcompare.get("before"));
            type(type,noid);

        }else {
            type=cws.CheckOkAll(pallet,noid,palletcompare.get("before"));
            type(type,noid);

        }




    }


    public void type(int type,String noid){
        if (type==1){
            //�̪O2
            int first=mappallet.size();
            mappallet.put(noid,1);
            int laster=mappallet.size();

            if (first<laster) {

                palletcarton.clear();
                // palletcarton.put(SMOrderText.getText().toString(),null);
                if (checkValue==2){
                    CInWhseService.count=1;
                }

                palletnum.setText(mappallet.size() + "/" + palletnumGeneral.getText());
                scannedNewsText.append("�̪O���G" + SMOrderText.getText().toString() + "\r\n");
                palletcompare.put("before",SMOrderText.getText().toString());
                if (checkValue==3){
                    cws.checkAllCount(palletcompare.get("before"));

                }

                SoundUtil.PlayOk();
            }else {
                LogText.append( SMOrderText.getText().toString() +"���ƤF" + "\r\n");
                SoundUtil.PlayNo();
            }
        }else if(type==2){
            //�s���c2

            mapbox.put(noid,1);
            boxNum.setText(mapbox.size()+"/"+boxNumGeneral.getText());
            scannedNewsText.append("�c��"+SMOrderText.getText().toString()+"\r\n");
            palletcompare.put("before",null);
            SoundUtil.PlayOk();
        }else if (type==3){
            if (palletcarton.get(SMOrderText.getText().toString())!=null){
                if (checkValue==3){
                    CInWhseService.countallend+=1;
                    System.out.println(CInWhseService.countallend+"haha");
                }
                scannedNewsText.append("    ���ƨ�-�̪O���c"+SMOrderText.getText().toString()+"���T�c"+"\r\n");
            }else {
                palletcarton.put(SMOrderText.getText().toString(),"have");
                scannedNewsText.append("    �̪O���c"+SMOrderText.getText().toString()+"\r\n");
            }
            SoundUtil.PlayOk();

        }else if (type==4){
            if (palletcarton.get(SMOrderText.getText().toString())!=null){
                if (checkValue==3){
                    CInWhseService.countallend+=1;
                }
                scannedNewsText.append("    ���ƨ�-�̪O���c"+SMOrderText.getText().toString()+"���~�̪O�c"+"\r\n");
            }else {
                palletcarton.put(SMOrderText.getText().toString(),"have");
                mappallet.remove(palletcompare.get("before"));
                palletnum.setText(mappallet.size() + "/" + palletnumGeneral.getText());
                scannedNewsText.append("    �̪O���c"+SMOrderText.getText().toString()+"�ӽc�P�Ӵ̪O�ǰt����"+"\r\n");
                LogText.append("�̪O���c"+SMOrderText.getText().toString()+"�ӽc�P�Ӵ̪O�ǰt����"+"\r\n");
                SoundUtil.PlayNo();
            }

        }else{
            palletcompare.put("before",null);
            SoundUtil.PlayNo();
            LogText.append(SMOrderText.getText().toString()+"���s�b�I�I�I�I�I�I�I�I"+"\r\n");
        }

    }
    //??�O?�M�c?���ŦX???�u�H��?�A�^?status
    public  void back(){

        String pallet=ZBNumberText.getText();

        String X=null,Y=null;
        X=mappallet.size()+"";
        Y=mapbox.size()+"";

        if(X==null){
            X="0";
        }
        if (Y==null){
            Y="0";
        }


        if(X.equals(palletnumGeneral.getText().toString())&&Y.equals(boxNumGeneral.getText().toString())){
            //�^��Status
            if (cws.LastCheck(ZBSXNumberText.getText(),pallet))
            {
				/*String carton = SMOrderText.getText().trim();
				Vector vtISN = cws.getIsn(carton);
				if(vtISN != null && vtISN.size() > 1){
					String device = "";
					try{
						ReadConfig2 a = new ReadConfig2();
						a.hashINI();
						device = a.iniHash.get("epega.exe.device").toString();
					}catch (Exception e) {
						JOptionPane.showConfirmDialog(null, "�t�m��󲧱`�A���pô OA     �`�G �t�m���榡�� UNICODE", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
					}
					for(int i = 1;i < vtISN.size();i++){
						Hashtable htISN = new Hashtable();
						htISN = (Hashtable)vtISN.get(i);
						procedureFun(device,carton,(String)htISN.get("M_SN"));
					}
				}*/
                clear();
                SoundUtil.PlayOk(1000);
                LogText.setText("�禬OK�I�I�I�I�I�I�I�I"+"\r\n");
            }
            else {
                SoundUtil.PlayNo();
                LogText.setText("�禬GG�I�I�I�I�I�I�I�I"+"\r\n");
            }
        }
    }
	
	private void procedureFun(String device,String cartonNo,String isn){
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





