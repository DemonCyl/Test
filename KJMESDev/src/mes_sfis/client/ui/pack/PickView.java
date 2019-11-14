package mes_sfis.client.ui.pack;

import base.client.ui.BasePanel;
import base.client.util.component.P_Component_Meta;
import base.vo.UI_InitVO;

import com.lowagie.text.DocumentException;
import mes_sfis.client.model.bean.PackList;
import mes_sfis.client.model.bean.PackPick;
import mes_sfis.client.model.service.PackingQueryService;
import mes_sfis.client.pdf.PDFCreater;
import mes_sfis.client.pdf.PDFPick;
import mes_sfis.client.util.DataHandler;
import mes_sfis.client.util.SoundUtil;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;

/**
 * Created by Mark_Yang on 2018/4/2.
 */
public class PickView extends BasePanel {
    public static final String VERSION    = "$Name:  $, $Id: PickView.java,v 1.20 2018/04/16 09:11:08 Lucy6_Lu Exp $";
    public static final String COPYRIGHT  = "Copyright (c) 2017 PEGATRON Inc. All Rights Reserved.";
    private JTextField ZBSXNumberText,SMOrderText,ZBNumberText,ZBXNumberText,TotalNumberText;

    private JTextArea scannedNewsText,LogText;


    static UI_InitVO uiVO;
    private String isNull=null;
    private int sanxNumber=0;//�n���s���c��
    private int zhanbNumber=0;//�n���̪O��
    private int zhanbxNumber=0;//�n���̪O�c��
    private int totalProductNumber=0;//�n���`���~��
    private PackList packList;
    String pickNo = null;
   // private PackPick packPick=new PackPick();
    private List list = new ArrayList<Map>();
    private Map<Integer,String> mapc=new HashMap();
    private Map<Integer,String> mapp=new HashMap();
    private Map<Object,String> map =new HashMap<>();
    public PickView(UI_InitVO uiVO) {
        super(uiVO);
        this.uiVO=uiVO;
        init();

    }
    public void init(){

      /*  JFrame frame = new JFrame("1721�߳f�J�w");
        frame.setSize(600, 650);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);*/

        JPanel panel = jPanel();
        panel.setSize(600, 650);
        add(panel);
       // panel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // �K�[���O
       // frame.add(panel);

        /*
         * ?�Υ�?�w?����k�}�K�[?��쭱�O
         */
      //  placeComponents(panel);

        // ?�m�ɭ��i?
      //  frame.setVisible(true);





    }


    private  JPanel jPanel() {

        JPanel panel=new JPanel();

    //    panel.setLayout(null);




        panel.setLayout(null);
        JLabel title = new JLabel("1721�˳f�J�w");
        title.setBounds(240,10,120,30);
        title.setFont(new Font("���^", Font.BOLD, 16));
        panel.add(title);
        // panel.setSize(600, 50);
        JButton startPick= new JButton("�}�l�˳f");
        startPick.setBounds(100,50,100,30);
        startPick.setVisible(false);
        panel.add(startPick);
        JButton finishPick= new JButton("�����߳f");
        finishPick.setBounds(240,50,100,30);
        panel.add(finishPick);
        JButton cleanOut= new JButton("�M��");
        cleanOut.setBounds(380,50,100,30);
        panel.add(cleanOut);

     /*   //�]�m�t���Ƹ�Label
        JLabel userLabel = new JLabel("�t���Ƹ�:");
        userLabel.setBounds(10,100,60,25);
        panel.add(userLabel);

        //�]�m�t���Ƹ���ܮ�
        JTextField userText = new JTextField(20);
        userText.setBounds(70,100,130,25);
        userText.setEditable(false);
        panel.add(userText);

        //�]�m�C��Label
        JLabel colorLabel = new JLabel("�C��:");
        colorLabel.setBounds(210,100,30,25);
        panel.add(colorLabel);

        //�]�m�C����ܮ�
        JTextField colorText = new JTextField(10);
        colorText.setBounds(240,100,50,25);
        colorText.setEditable(false);
        panel.add(colorText);

        // �]�m�Ȥ�Ƹ�Label
        JLabel userNumberLabel = new JLabel("�Ȥ�Ƹ�:");
        userNumberLabel.setBounds(310,100,60,25);
        panel.add(userNumberLabel);

      //�]�m�Ȥ�Ƹ��U�Կ�ܹ�Ӫ�
        JTextField userNumberText = new JTextField(10);
        userNumberText.setBounds(370,100,130,25);
        userNumberText.setEditable(false);
        panel.add(userNumberText);*/

        //�]�m�߳f���nLabel
        JLabel zhanbLable=new JLabel("�̪O��:");
        zhanbLable.setBounds(10,100,50,25);
        panel.add(zhanbLable);

        //�]�m�`�̪O�c��Label
        ZBNumberText = new JTextField(10);
        ZBNumberText.setBounds(70,100,60,25);
        ZBNumberText.setEditable(false);
        panel.add(ZBNumberText);

        JLabel zhanbxLable=new JLabel("�̪O�`�c��:");
        zhanbxLable.setBounds(140,100,70,25);
        panel.add(zhanbxLable);
        //�]�m�̪O�`�c�����
        ZBXNumberText = new JTextField(10);
        ZBXNumberText.setBounds(220,100,80,25);
        ZBXNumberText.setEditable(false);
        panel.add(ZBXNumberText);

        //�]�m�s���cLabel
        JLabel JHSnumber=new JLabel("�s���c:");
        JHSnumber.setBounds(320,100,60,25);
        panel.add(JHSnumber);

        //�]�m�s���c����ܮ�
        ZBSXNumberText = new JTextField(10);
        ZBSXNumberText.setBounds(370,100,80,25);
        ZBSXNumberText.setEditable(false);
        panel.add(ZBSXNumberText);

        //�]�m���y�J�wLabel
        JLabel SMOrderLabel=new JLabel("���y�J�w:");
        SMOrderLabel.setBounds(10,150,60,25);
        panel.add(SMOrderLabel);

        //�]�m���y���̪O��\�c����ܮ�
        SMOrderText = new JTextField(10);
        SMOrderText.setBounds(70,150,140,25);
      //  SMOrderText.setEditable(false);
        panel.add(SMOrderText);

        //�]�m�ƶqLabel
        JLabel TotalNumberLabel=new JLabel("�ƶq/PCs:");
        TotalNumberLabel.setBounds(240,150,60,25);
        panel.add(TotalNumberLabel);

        //�]�m�ƶq��ܮ�
        final JTextField TotalNumberText = new JTextField(10);
        TotalNumberText.setBounds(310,150,140,25);
        TotalNumberText.setEditable(false);
        panel.add(TotalNumberText);

        scannedNewsText = new JTextArea();
        scannedNewsText.setBounds(30,200,500,100);
        // CheckSSNText.setLineWrap(true);
        scannedNewsText.setEditable(false);
        JScrollPane scannedNewsScroll = new JScrollPane(scannedNewsText);
        scannedNewsScroll.setBounds(30,200,500,150);
        scannedNewsScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(scannedNewsScroll);

        LogText = new JTextArea();
        LogText.setBounds(30,370,500,150);
        JScrollPane jsLogText = new JScrollPane(LogText);
        jsLogText.setBounds(30,370,500,150);
        LogText.setEditable(false);
        jsLogText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(jsLogText);


       // SMOrderText.requestFocus();
        //�}�l�߳f���s��ť�ƥ�
        startPick.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        finishPick.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isNull!=null){
                    int isSure = JOptionPane.showConfirmDialog(null, "�T�{�[�J�M��\?", "�[�J�M��",JOptionPane.YES_NO_OPTION);//��^���O��?��index  i=0�Ϊ�1
                    if (isSure==0){
                        PackPick packPick=new PackPick();
                        String projectCode = "COP1721";

                        packPick.setDh(new DataHandler(uiVO));

                        packList=new PackList();//�إߦs�x��������H
                        packList.setCartonQty(sanxNumber);//�[�c��
                        try {
                            packList.setDateCode(packPick.getPickSn(projectCode).substring(0,4));//�[����s�X
                            pickNo="COP1721-"+packPick.getPickSn(projectCode);
                            packList.setPickNo(pickNo);//�[�M��X
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                        packList.setLogEmployee(uiVO.getEmployee_oid());//�[�ާ@�H��
                        // packList.setLOG_SYSTEMDATE(date);//�[�t�Τ��
                        packList.setMemo("");//�[�Ƶ�
                        packList.setPalletQty(zhanbNumber);//�[�̪O��
                        packList.setStatus(0);//�[���A�X 0�G���J�w   1�G�w�J�w
                        packList.setPalletCartonNumber(zhanbxNumber);
                        PackingQueryService ps = new PackingQueryService(uiVO);
                        list.add(mapc);
                        list.add(mapp);
                        boolean isSuccess =ps.addPick_OID(packList,"MES_PACK_CARTON","MES_PACK_PALLET",list);
                        if (isSuccess==false){
                            SoundUtil.PlayNo();
                            LogText.append("�t�β��`-�K�[�M�沧�`"+"\r\n");
                            SMOrderText.setText("");
                           // showDialog("�K�[�M�沧�`","�t�β��`");

                            JOptionPane.showMessageDialog(null, "�K�[�M�沧�`","�t�β��`",JOptionPane.ERROR_MESSAGE);
                        }else {
                            SoundUtil.PlayNice();
                            LogText.append("Success-�K�[�M�榨�\"+"\r\n");
                            ZBSXNumberText.setText("");
                            SMOrderText.setText("");
                            ZBNumberText.setText("");
                            ZBXNumberText.setText("");
                            TotalNumberText.setText("");
                            scannedNewsText.setText("");
                            LogText.setText("");
                            sanxNumber=0;//��l�ƹs���c��
                            zhanbNumber=0;//��l�ƴ̪O��
                            zhanbxNumber=0;//��l�ƴ̪O�c��
                            totalProductNumber=0;//��l���`���~��
                            map.clear();
                            isNull=null;
                           // int a=JOptionPane.showConfirmDialog(null,"�M�渹:"+pickNo,"����",JOptionPane.PLAIN_MESSAGE);
                           // JOptionPane.showConfirmDialog(null,a,"ĵ�i",JOptionPane.PLAIN_MESSAGE);
                            JOptionPane.showMessageDialog(null, "�M�渹:"+pickNo);
                            try {
                               // pickNo="COP1721-"+packPick.getPickSn(projectCode);
                                PDFPick pdfPick = new PDFPick(packList.getPickNo()+".pdf");
                                pdfPick.addPage(packList);
                                pdfPick.close();
                                pdfPick.printPdf();
                            } catch (FileNotFoundException e1) {
                                e1.printStackTrace();
                            } catch (DocumentException e1) {
                                e1.printStackTrace();
                            }


                        }
                    }else {

                        LogText.setText("�����[�J�M��"+"\r\n");
                    }

                }else {
                    JOptionPane.showMessageDialog(null, "�L�ƾګH��","����",JOptionPane.NO_OPTION);
                }
                    SMOrderText.requestFocus();
            }
        });


        cleanOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ZBSXNumberText.setText("");
                SMOrderText.setText("");
                ZBNumberText.setText("");
                ZBXNumberText.setText("");
                TotalNumberText.setText("");
                scannedNewsText.setText("");
                LogText.setText("");
                map.clear();
                isNull=null;
                sanxNumber=0;//��l�ƹs���c��
                zhanbNumber=0;//��l�ƴ̪O��
                zhanbxNumber=0;//��l�ƴ̪O�c��
                totalProductNumber=0;//��l���`���~��
                SMOrderText.requestFocus();
            }
        });

        SMOrderText.addKeyListener(new KeyAdapter() {
               @Override
               public void keyPressed(KeyEvent e) {
                   super.keyPressed(e);
                   if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                       if (SMOrderText.getText().toString()!=""&&SMOrderText.getText().toString()!=null){
                           PackingQueryService ps = new PackingQueryService(uiVO);
                           if (map.get(SMOrderText.getText().toString())!=null){
                               showDialog("�ӽX�w���y�L","�y�{���`");
                              // JOptionPane.showMessageDialog(null, "�ӽX�w���y�L","�y�{���`",JOptionPane.NO_OPTION);

                           }else {

                               // boolean isBox=true;

                               String isBox = ps.isBox(SMOrderText.getText().toString());//�P�_�ꪺ�O�c���٬O�̪O��   �O�c����true
                               if (isBox=="true"){
                                   //�P�_�O�_�w�J�M��Τw�J�wstart
                                   int ispick= ps.isCreatPick(SMOrderText.getText().toString(),"MES_PACK_CARTON","CARTON_NO");
                               /* if(ispick ==0){
                                    LogText.append("���c�X�w�J�M��"+"\r\n");
                                }else*/
                                   if (ispick==1){

                                       showDialog("���c�X�w�J�w","�y�{���`");
                                       // JOptionPane.showMessageDialog(null, "���c�X�w�J�w","�y�{���`",JOptionPane.ERROR_MESSAGE);
                                   }else if (ispick==-1){

                                       showDialog("�d�ߦ��c�X�O�_�w�J�w","�t�β��`");
                                      // JOptionPane.showMessageDialog(null, "�d�ߦ��c�X�O�_�w�J�w","�t�β��`",JOptionPane.ERROR_MESSAGE);
                                   }else {
                                       if (ps.xProductNumber(SMOrderText.getText().toString())==-1){//�P�_�d�߽c�����~�ƮɬO�_�X�{���`

                                           showDialog("�d�߽c�����~�Ʈ�","�t�β��`");
                                           //JOptionPane.showMessageDialog(null, "�d�߽c�����~�Ʈ�","�t�β��`",JOptionPane.ERROR_MESSAGE);
                                       }else {
                                           scannedNewsText.append("�c�X:"+SMOrderText.getText().toString()+"\r\n");//�b���b���K�[�s��J��carton_on�X��
                                           map.put(SMOrderText.getText().toString(),"ishave");
                                           sanxNumber+=1;
                                           mapc.put(sanxNumber,SMOrderText.getText().toString());
                                           totalProductNumber+=ps.xProductNumber(SMOrderText.getText().toString());//�`���~�Ƶ����e���~�ƥ[���c�������~��
                                           ZBSXNumberText.setText(sanxNumber+"");//�]�m�s���c�Ƨ���
                                           TotalNumberText.setText(totalProductNumber+"");//�]�m�`���~�Ƨ���
                                           isNull="�P�_�ͦ��M��ƾڬO�_����";
                                           SoundUtil.PlayOk();
                                           SMOrderText.setText("");

                                       }
                                   }  //�P�_�O�_�w�J�M��Τw�J�wend


                               }else if(isBox=="false"){
                                   String ispallet=ps.isPallet(SMOrderText.getText().toString());////�P�_�ꪺ�O���O�̪O��   �O�̪O������true
                                   if (ispallet=="true"){
                                       //�P�_�O�_�w�J�M��Τw�J�wstart
                             /*      if (ps.isCreatPick(SMOrderText.getText().toString(),"MES_PACK_PALLET","PALLET_NO")==0){
                                       LogText.append("���̪O�X�w�J�M��"+"\r\n");
                                       SoundUtil.PlayNo();
                                   }else*/
                                       int ispick= ps.isCreatPick(SMOrderText.getText().toString(),"MES_PACK_PALLET","PALLET_NO");
                                       if (ispick==1){

                                           showDialog("���̪O�X�w�J�w","�y�{���`");
                                          // JOptionPane.showMessageDialog(null, "���̪O�X�w�J�w","�y�{���`",JOptionPane.ERROR_MESSAGE);
                                       }else if (ispick==-1){

                                           showDialog("�d�ߦ��̪O�O�_�J�w","�t�β��`");
                                          // JOptionPane.showMessageDialog(null, "�d�ߦ��̪O�O�_�J�w","�t�β��`",JOptionPane.ERROR_MESSAGE);
                                       }else {
                                           int zhanbxnumber=ps.zhanbxNumber(SMOrderText.getText().toString());
                                           int zhanbproduct=ps.zhanbProductNumber(SMOrderText.getText().toString());
                                           if (zhanbxnumber==-1||zhanbproduct==-1){

                                               showDialog("�d�ߴ̪O���c��","�t�β��`");
                                              // JOptionPane.showMessageDialog(null, "�d�ߴ̪O���c��","�t�β��`",JOptionPane.ERROR_MESSAGE);
                                           }else if (zhanbproduct==-2){

                                               showDialog("�̪O���Ŵ̪O","�y�{���`");
                                             //  JOptionPane.showMessageDialog(null, "�̪O���Ŵ̪O","�y�{���`",JOptionPane.ERROR_MESSAGE);
                                           }else {
                                               scannedNewsText.append("�̪O�X:"+SMOrderText.getText().toString()+"\r\n");//�b���b���K�[�s��J��carton_on�X��
                                               map.put(SMOrderText.getText().toString(),"ishave");
                                               zhanbNumber+=1;
                                               mapp.put(zhanbNumber,SMOrderText.getText().toString());
                                               zhanbxNumber+=zhanbxnumber;//�`�̪O�c�Ƶ����e�Ҧ��̪O�c�ƥ[���̪O�����c��
                                               totalProductNumber+=zhanbproduct;//�`���~�Ƶ����e���~�ƥ[���̪O�������~��
                                               ZBNumberText.setText(zhanbNumber+"");
                                               ZBXNumberText.setText(zhanbxNumber+"");
                                               TotalNumberText.setText(totalProductNumber+"");//�]�m�`���~�Ƨ���
                                               isNull="�P�_�ͦ��M��ƾڬO�_����";
                                               SoundUtil.PlayOk();
                                               SMOrderText.setText("");
                                           }

                                       }

                                   }else if(ispallet=="false"){

                                       showDialog("���X���s�b","����");
                                     //  JOptionPane.showMessageDialog(null, "���X���s�b","����",JOptionPane.ERROR_MESSAGE);
                                   }else {

                                       showDialog(ispallet.substring(6),ispallet.substring(1,5));
                                      // JOptionPane.showMessageDialog(null, ispallet,ispallet.substring(1,5),JOptionPane.ERROR_MESSAGE);
                                   }
                               }else {

                                   showDialog(isBox.substring(6),isBox.substring(1,5));
                               //    JOptionPane.showMessageDialog(null, isBox,isBox.substring(1,5),JOptionPane.ERROR_MESSAGE);
                               }
                           }

                       }else {
                           showDialog("�L�ƾګH��","����");
                         //  JOptionPane.showMessageDialog(null, "�L�ƾګH��","����",JOptionPane.ERROR_MESSAGE);
                       }



                   }
               }
        });

        return panel;
    }


    public  void  showDialog(String stra,String strb){
        LogText.append(SMOrderText.getText().toString()+"-"+stra+"-"+strb+"\r\n");
        SoundUtil.PlayNo();
        SMOrderText.setText("");
        JOptionPane.showMessageDialog(null, stra,strb,JOptionPane.ERROR_MESSAGE);
        //SMOrderText.setFocusable(true);
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
