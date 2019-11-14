package mes_sfis.client.ui.pack;
import   org.apache.logging.log4j.LogManager;
import base.client.ui.BasePanel;
import base.client.util.component.P_Component_Meta;
import base.vo.UI_InitVO;
import mes_sfis.client.model.bean.CognexModel;
import mes_sfis.client.model.service.CognexUploadService;
import mes_sfis.client.util.DataHandler;
import mes_sfis.client.util.ExcelUtils;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.util.Timer;

public class CognexView extends BasePanel {
    public static final String VERSION = "$Name: $, $Id: CognexView.java,v 1.0 2018/05/03 10:52:07 Lucy6_Lu Exp $";
    public static final String COPYRIGHT = "Copyright (c) 2008 PEGATRON Inc. All Rights Reserved.";

    DataHandler dh;
    CognexUploadService cognexUploadService;
    private JPanel panel = super.UILayoutPanel;
    int btn = 0;
    String time;
    List<CognexModel> list = null;


    public CognexView(UI_InitVO uiVO) {
        super(uiVO);
        cognexUploadService = new CognexUploadService(uiVO);
        dh = new DataHandler(uiVO);

        init(panel);

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

    void init(JPanel panel) {
        JLabel title = new JLabel("CognexBarcode");
        title.setBounds(240, 20, 120, 25);
        title.setFont(new Font("宋体", Font.BOLD, 16));
        panel.add(title);

        //?ant

        JButton startPick = new JButton("start");
        startPick.setBounds(240, 60, 120, 25);
        panel.add(startPick);

       /* JButton clearlog = new JButton("ClearLog");
        clearlog.setBounds(240, 100, 120, 25);
        panel.add(clearlog);*/

        JLabel log = new JLabel("LOG");
        log.setFont(new Font("宋体", Font.BOLD, 16));
        log.setBounds(30, 140, 40, 25);
        panel.add(log);

        final JTextArea LogText = new JTextArea(25, 50);
        LogText.setBounds(30, 160, 500, 150);
        LogText.setLineWrap(true);
        final JScrollPane jsLogText = new JScrollPane(LogText);
        //  Point p = new Point();
        //  p.setLocation(0, 150);
        //  jsLogText.getViewport().setViewPosition(p);
        LogText.setEditable(false);
        jsLogText.setBounds(30, 160, 500, 150);
        jsLogText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(jsLogText);
        final ExcelUtils excelUtils = new ExcelUtils();
        //?}?l??f???s????????
        startPick.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //optExcleFile(LogText);
                final JButton jb = (JButton) e.getSource();
                //查??据?今天的?量
                // rownum = cognexUploadService.findNewTime();
              //  System.out.println("**************************---" + time);

                final Timer timer = new Timer();


                if (jb.getText().equals("start")) {
                    jb.setText("stop");
                    timer.purge();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (btn == 0) {
                                String result= null;

                                try {
                                    time = cognexUploadService.findNewTime();
                                    System.out.println(time);
                                    if (time==null){
                                        time="";
                                    }
                                    list = excelUtils.excelForList(time);
                                     System.out.println("gs"+list.size());

                                    result = cognexUploadService.insertCognexRawData(list);
                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                    result=e1.getMessage();
                                }

                                LogText.append(result+"\r\n");

                                btn = 0;
                            } else if (btn == 1) {
                                timer.cancel();
                                btn = 1;
                            }


                            //showFileOpenDialog(panel,LogText);
                        }
                    }, 0, 1800000);
                    btn = 0;
                } else if (jb.getText().equals("stop")) {
                    jb.setText("start");
                    btn = 1;
                    //查看尾行??

                    //timerTask=null;
                }


            }


        });



    }



   /* public static void main(String[] args) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("chenggong ");
            }
        }, 1000, 5000);
    }*/

}