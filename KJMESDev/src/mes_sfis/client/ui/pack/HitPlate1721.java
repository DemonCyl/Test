package mes_sfis.client.ui.pack;

import base.client.ui.BasePanel;
import base.client.util.component.P_Component_Meta;
import base.vo.UI_InitVO;
import base.vo.URL_VO;
import mes_sfis.client.model.service.PackageService;
import mes_sfis.client.model.service.PalletService;
import mes_sfis.client.util.SoundUtil;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by Haifeng_Zhou on 2018/4/2.
 */
public class HitPlate1721 extends BasePanel {

    public static final String VERSION = "$Name: 1.12 $, $Id: HitPlate1721.java,v 1.22 2018/04/16 10:31:07 Lucy6_Lu Exp $";
    public static final String COPYRIGHT  = "Copyright (c) 2008 PEGATRON Inc. All Rights Reserved.";
    private JButton jButtonPacking, jButtonClear, jButtonMandatoryPacking;
    private JLabel titleLabel, InFactoryMaterialNumLabel, FinshScanningSSNLabel, LogLabel, ColorLabel, CustomerMaterialNumLabel, Number_BoxLabel, Number_BoxLabelValue, ColorLabelValue;
    private JTextField InFactoryMaterialNumText,caseNo,InFactoryMaterialNumText1,CustomerMaterialNumBox;
    private JTextArea CheckSSNText,LogText;
    private JScrollPane jsLogText;
    private PalletService palletService;
    int a=1;//�O��
    String productC="";//�c���u�ʤ@����ܼƾ�
   // PalletService pack=new PalletService();
    List<Hashtable> value = null;
    ArrayList cf=new ArrayList();//���ҽc���O�_�꭫��
    String[] arr;//�ݭn�[�J���ƾڦs�J�Ʋ�
    String VALUEBM;
    String PALLET_OID=null;


    public HitPlate1721(UI_InitVO uiVO) {
        super(uiVO);
        palletService = new PalletService(uiVO);
        init();
    }
    //�M�Žw�s�ƾ�
    public void Eliminate(){
        InFactoryMaterialNumText.setText("");
        ColorLabelValue.setText("");
        Number_BoxLabelValue.setText("0/12");
        CheckSSNText.setText("");
        InFactoryMaterialNumText1.setText("");
        CustomerMaterialNumBox.setText("");
        a=1;
        productC="";
        //�M�Ŧs�x�ƾ�
        cf.clear();
        arr=null;
        LogText.setText("���\");
       /* caseNo.setText("");*/
    }
    public void start(String  BM){
        SoundUtil.PlayNice();

        //�M�Žw�s�ƾ�
        Eliminate();
    }
    void init() {
        setUILayout(null);
        jButtonPacking = new JButton("�}�l���O ");
        jButtonMandatoryPacking = new JButton("�j��O");
        jButtonClear = new JButton("�M��");
        titleLabel = new JLabel("1721���O");
        InFactoryMaterialNumLabel = new JLabel("�t���Ƹ�");
        FinshScanningSSNLabel = new JLabel("�w���y�c��");
        LogLabel = new JLabel("Log");
        ColorLabel = new JLabel("�C��G");
        CustomerMaterialNumLabel = new JLabel("�Ȥ�Ƹ�");
        Number_BoxLabel = new JLabel("�c��/�̪O");
        Number_BoxLabelValue = new JLabel("0/12");
        ColorLabelValue = new JLabel();
        InFactoryMaterialNumText = new JTextField();
        InFactoryMaterialNumText1 = new JTextField();//�s�x�Ĥ@�c���y�Ƹ� ��K���L12�c�i����
        caseNo = new JTextField("");
        CustomerMaterialNumBox = new JTextField();
        CustomerMaterialNumBox.setEditable(false);
        CheckSSNText = new JTextArea();
        CheckSSNText.setLineWrap(true);
        CheckSSNText.setEditable(false);
        JScrollPane jsCheckSSN = new JScrollPane(CheckSSNText);
       /* jsCheckSSN.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); ��ܥ��k�u�ʱ�*/
        jsCheckSSN.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        LogText = new JTextArea();
        LogText.setLineWrap(true);
        LogText.setEditable(false);
        jsLogText = new JScrollPane(LogText);
        jsLogText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


        add(jButtonPacking);
        add(jButtonMandatoryPacking);
        add(jButtonClear);
        add(titleLabel);
        add(InFactoryMaterialNumLabel);
        add(FinshScanningSSNLabel);
        add(ColorLabel);
        add(CustomerMaterialNumLabel);
        add(Number_BoxLabel);
        add(InFactoryMaterialNumText);
        add(InFactoryMaterialNumText1);
        add(CustomerMaterialNumBox);
        add(Number_BoxLabelValue);
        add(ColorLabelValue);
        add(LogLabel);
        add(jsLogText);
        add(jsCheckSSN);
        add(caseNo);

        jButtonPacking.setBounds(20, 100, 100, 40);
        jButtonMandatoryPacking.setBounds(/*270*/170, 100, 100, 40);
        jButtonClear.setBounds(/*520*/420, 100, 100, 40);
        jButtonPacking.setVisible(false); //���Ҽv��

        titleLabel.setBounds(270, 50, 100, 20);

        InFactoryMaterialNumLabel.setBounds(20, 160, 100, 40);
        FinshScanningSSNLabel.setBounds(20, 200, 80, 40);
        caseNo.setBounds(120, 210, 130, 32);
        InFactoryMaterialNumText.setBounds(120, 165, 100, 30);
        InFactoryMaterialNumText.setEditable(false);
        InFactoryMaterialNumText1.setBounds(0, 165, 100, 30);
        InFactoryMaterialNumText1.setVisible(false); //���Ҽv��

        ColorLabel.setBounds(270, 160, 100, 40);
        ColorLabelValue.setBounds(320, 160, 100, 40);

        CustomerMaterialNumLabel.setBounds(400, 160, 100, 40);
        Number_BoxLabel.setBounds(400, 210, 100, 40);
        Number_BoxLabelValue.setBounds(520, 210, 100, 40);
        CustomerMaterialNumBox.setBounds(520, 165, 100, 30);
        //�c���u��
        CheckSSNText.setBounds(20, 280, 600, 80);
        jsCheckSSN.setBounds(20, 280, 600, 80);

        LogLabel.setBounds(20, 320, 100, 100);
        LogText.setBounds(20, 380, 600, 50);
        jsLogText.setBounds(20, 380, 600, 50);
        //����w���y�c�� �X�o�ƥ�

        caseNo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    value = palletService.key(caseNo.getText());
                    //�P�_���c�����b�˽c�����J�H��
                    if (value.size()<1) {
                        SoundUtil.PlayNo(1000);//��ܼ����n��
                        LogText.setText("���c�|���ʽc,�Юֹ�");
                        caseNo.setText("");
                        return;
                    }
                    if (a == 1) {
                        //  (String) value.get("KJ_PN"))
                        // �s�x�Ĥ@�c���y�Ƹ� ��K���L11�c�i����
                        InFactoryMaterialNumText1.setText((String) value.get(1).get("KJ_PN"));
                    }

                    //�^��Ƹ��s�X�C��
                    InFactoryMaterialNumText.setText((String) value.get(1).get("KJ_PN"));
                    ColorLabelValue.setText((String) value.get(1).get("VALUEZW"));
                    //�Ȥ�Ƹ�
                    CustomerMaterialNumBox.setText((String) value.get(1).get("OEMPN"));
                    //���ҪO���Ƹ��O�_�Τ@
                    if (!InFactoryMaterialNumText1.getText().equals((String) value.get(1).get("KJ_PN"))) {
                        SoundUtil.PlayNo(1000);//��ܼ����n��
                        LogText.setText("���c�Ƹ����Τ@,�Юֹ�");
                        caseNo.setText("");
                        return;
                    }

                    //���ҬO�_�꭫�ƽc��
                    if (cf.contains(caseNo.getText())) {
                        SoundUtil.PlayNo(1000);//��ܼ����n��
                        JOptionPane.showConfirmDialog(null,"���c���꭫�ơI","ĵ�i",JOptionPane.PLAIN_MESSAGE);
                        LogText.setText("���c���꭫��");
                        caseNo.setText("");
                        return;
                    }
                    //���Ҧ��c�O�_�w����
                    if(palletService.PalletVerify(caseNo.getText())==null){
                        SoundUtil.PlayOk(1000);
                    }else {
                        JOptionPane.showConfirmDialog(null,"���c�w�g����,�Юֹ�I","ĵ�i",JOptionPane.PLAIN_MESSAGE);
                        LogText.setText("���c�w�g����,�Юֹ�");
                        SoundUtil.PlayNo(1000);//��ܼ����n��
                        caseNo.setText("");
                        return;
                    }
                    //�N�c���s�x��list��
                    cf.add(caseNo.getText());
                    //�N�w�g���y�F�c���Τ@��ܤ@�_
                    productC = productC + caseNo.getText() + "\n";
                    CheckSSNText.setText(productC);
                    //�c�Ƥw���y���c
                    Number_BoxLabelValue.setText((a++) + "/12");
                    //���Ҧ��c�O�_�w����
                    caseNo.setText("");
                    //PDF���L
                    if (a > 12) { //���O12���ܻݭn���O
                        //����ݭn���Lpdf�ƾ�
                        if (((String) value.get(1).get("KJ_PN")).equals("YFM00Y060-103-N")){
                            VALUEBM="JPT3";
                        }else{
                            VALUEBM="JPT4";
                        }
                        palletService.PDF(VALUEBM, (a - 1) + "", (String) value.get(1).get("CHECK_FIELD_VALUE"));
                        //������O�c��/�Ƹ��s�X
                        String[] arr=new String[]{(a-1)+"",VALUEBM};
                       String PALLET_OID= palletService.increase(arr, (String) value.get(1).get("CHECK_FIELD_VALUE"));
                        // SoundUtil.PlayNo();//��ܼ����n��
                        int tj = JOptionPane.showConfirmDialog(null, "�O�_�i�楴�O", "����", JOptionPane.YES_NO_OPTION);
                        LogText.setText("�w��12�c,�Х����O");
                        //0�G�T�w �ϥ��ȲM���ƾ�
                        if (tj == 0) {
                            //��s���O�ʪO���A
                            palletService.upCARTON((cf),PALLET_OID);
                            start(VALUEBM);
                        } else {
                            int tj1 = JOptionPane.showConfirmDialog(null, "�O�_�������O", "����", JOptionPane.YES_NO_OPTION);
                            if (tj1 == 0) {
                                Eliminate();
                            }else{
                                Repeat();
                            }

                        }
                    }
                    LogText.setText("");
                }
            }

    });



        //�}�l���O
        jButtonPacking.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        //�j��O
        jButtonMandatoryPacking.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cf.size()<1){
                    SoundUtil.PlayNo(1000);//��ܼ����n��
                    JOptionPane.showConfirmDialog(null,"�п��J�H���Z�j��O�I","ĵ�i",JOptionPane.PLAIN_MESSAGE);
                    LogText.setText("�п��J�H���Z�j��O");
                    return;
                }
                //����ݭn���Lpdf�ƾ�
                if (InFactoryMaterialNumText1.getText().equals("YFM00Y060-103-N")) {
                    VALUEBM="JPT3";
                }else{
                    VALUEBM="JPT4";
                }
                palletService.PDF(VALUEBM,(a-1)+"", (String) value.get(1).get("CHECK_FIELD_VALUE"));
                //������O�c��/�Ƹ��s�X
                String[] arr=new String[]{(a-1)+"",VALUEBM};
                String PALLET_OID= palletService.increase(arr, (String) value.get(1).get("CHECK_FIELD_VALUE"));
                int tj = JOptionPane.showConfirmDialog(null, "�O�_�i��j��O", "����", JOptionPane.YES_NO_OPTION);
                if(tj==0){
                    palletService.upCARTON((cf),PALLET_OID);
                    start(VALUEBM);
                }

            }
        });
        //����
        jButtonClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoundUtil.PlayNice(1000);
                Eliminate();
            }
        });
    }
    public   void Repeat(){
        int tj = JOptionPane.showConfirmDialog(null, "�O�_�i�楴�O", "����", JOptionPane.YES_NO_OPTION);
        LogText.setText("�w��12�c,�Х����O");
        //0�G�T�w �ϥ��ȲM���ƾ�
        if (tj == 0) {
            //��s���O�ʪO���A
            palletService.upCARTON((cf),PALLET_OID);
            start(VALUEBM);
        } else {
            int tj1 = JOptionPane.showConfirmDialog(null, "�O�_�������O", "����", JOptionPane.YES_NO_OPTION);
            if (tj1 == 0) {
                Eliminate();
            }else{
                Repeat();
            }

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
