package mes_sfis.client.ui.pack;

import base.client.ui.BasePanel;
import base.client.util.component.P_Component_Meta;
import base.vo.UI_InitVO;
import mes_sfis.client.model.service.COPService;
import mes_sfis.client.util.SoundUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Hashtable;

/**
 * Created by Efil_Ding on 2018/4/11.
 */
public class COP1721 extends BasePanel {
    private static final Logger logger = LogManager.getLogger(COP1721.class);
    public static final String VERSION    = "$Name: $, $Id: COP1721.java,v 1.10 2018/04/16 10:31:07 SRX_zhu Exp $";
    public static final String COPYRIGHT  = "Copyright (c) 2008 PEGATRON Inc. All Rights Reserved.";
    private static JTextField   ZBNumberText ;
    private static JTextArea   DataText;
    private static  JCheckBox OldRule=new JCheckBox("原分BIN規則");
    private static JCheckBox OQC2Rule =new JCheckBox("OQC2 0059 & 0060 過站物料 ");
    static UI_InitVO uiVO;
    private JPanel contentPanel = super.UILayoutPanel;
    static COPService cop;
    public static void main(String args[]) {
        JFrame frame = new JFrame("1721掃碼分BIN");
        JPanel panel = new JPanel();
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 添加面板
        frame.add(panel);
        placeComponents(panel);
        frame.setVisible(true);
    }

    public COP1721(UI_InitVO uiVO) {
        super(uiVO);
        cop = new COPService(uiVO);
        this.init();
    }
    void init() {
        placeComponents(contentPanel);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);
        //SSN
        JLabel JHnumber = new JLabel("ISN:");
        JHnumber.setBounds(30, 100, 100, 25);
        panel.add(JHnumber);

        ZBNumberText = new JTextField();
        ZBNumberText.setBounds(60, 100, 500, 25);

        OldRule.setBounds(60,130,120,25);
        panel.add(OldRule);

        OQC2Rule.setBounds(150,130,200,25);
        panel.add(OQC2Rule);
        panel.add(ZBNumberText);
        JLabel XXData = new JLabel("詳細信息");

        XXData.setBounds(60, 180, 450, 25);
        panel.add(XXData);
        DataText = new JTextArea();
        DataText.setBounds(60, 200, 500,200);
        panel.add(DataText);
        //輸入SSN回車事件
        ZBNumberText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    logger.debug(ZBNumberText.getText());
                    String inputdata= ZBNumberText.getText().trim();
                    String ouputData="";
                    if(OldRule.isSelected()){
                        ouputData+=OldRule(inputdata);
                    }
                    if (OQC2Rule.isSelected()){
                        ouputData+=OQC2Rule(inputdata);
                    }

                    logger.debug("COLLECT OUTPUT DATA END");
                    DataText.setText(ouputData);
                    logger.debug("OUTPUT DATA");
                    ZBNumberText.setText("");
                }
            }
        });
    }
    //第一版的??
    public static String OldRule(String inputdata){

        Hashtable data= null;
        try {
            data = cop.getThreeCode(inputdata);
            logger.debug("GET Data:"+data);
        } catch (Exception e1) {
            e1.printStackTrace();
            JOptionPane.showConfirmDialog(null, "查無此碼，請核對", "", JOptionPane.PLAIN_MESSAGE);

            return "查無此碼，請核對";
        }
        if(data==null){
            JOptionPane.showConfirmDialog(null, "查無此碼，請核對", "", JOptionPane.PLAIN_MESSAGE);

        }
        String SSN= (String) data.get("SSN");
        String ISN= (String) data.get("ISN");
        String CSSN= (String) data.get("CSSN");

        // Hashtable allData =cop.getAllData(SSN,ISN,CSSN);
        String SNA=(String)data.get("SNA");
        String SN1=(String)data.get("SN1");
        String SNB=(String)data.get("SNB");
        String SNC=(String)data.get("SNC");
        String SND=(String)data.get("SND");
        String SNE=(String)data.get("SNE");
        String SNF=(String)data.get("SNF");
        String SNG=(String)data.get("SNG");
        /**
         String T1_UMP_TIMESTAMP=allData.get("T1_UMP_TIMESTAMP").toString();
         String T1_BIN=allData.get("T1_BIN").toString();
         String T3_UMP_TIMESTAMP=allData.get("T3_UMP_TIMESTAMP").toString();
         String T3_BIN=allData.get("T3_BIN").toString();
         String ALT2_UMP_TIMESTAMP=allData.get("ALT2_UMP_TIMESTAMP").toString();
         String ALT2_BIN=allData.get("ALT2_BIN").toString();
         String ALT3_UMP_TIMESTAMP=allData.get("ALT3_UMP_TIMESTAMP").toString();
         String ALT3_BIN=allData.get("ALT3_BIN").toString();**/
        logger.debug("SOUND PLAY");
        SNE(SNE);
        logger.debug("COLLECT OUTPUT DATA");
        String ouputData = "SSN:"+SSN+"\n"+"ISN:"+ISN+"\n"+"CSSN:"+CSSN+"\n";

        //CGSF(SF) CGOFF(OF)  CGSF+CGOFF(FC)
        ouputData += "WAVIER BIN:" + SNB + "    說明: CGSF(SF) CGOFF(OF)  CGSF+CGOFF(FC)  null為無放行數據" + "\r\n" ;
        ouputData += "CONFIG :" + SNC  + "\r\n" ;
        ouputData += "顏色:" + SNE + "        W:白色 ,B:黑色" + "\r\n" ;
        return ouputData;
    }
    //OQC2 0059 & 0060 過站物料 ?生的需求
    public static String  OQC2Rule(String inputdata){
        String outdata="OQC2 0059 & 0060 過站物料\n";
        String result=cop.getOQC2Badgoods(inputdata);
        if ("0".equals(result)){
            outdata="OK，非危險料\n";
        }
        return outdata;
    }
    public static void SNE(String sne){
        if ("W".equals(sne)){
            SoundUtil.PColor(1000);
        }
        if("B".equals(sne)){
            SoundUtil.Black(1000);
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
