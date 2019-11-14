package mes_sfis.client.ui.pack;

import base.client.ui.BasePanel;
import base.client.util.component.P_Component_Meta;
import base.vo.UI_InitVO;
import mes_sfis.client.excel.ExcelUtil;
import mes_sfis.client.model.bean.DataLoadBean;
import mes_sfis.client.model.service.ISRADataLoadService;
import mes_sfis.client.util.DataHandler;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.Timer;

public class ISRADataLoad extends BasePanel {
    public static final String VERSION = "$Name: $, $Id: ISRADataLoad.java,v 1.0 2018/05/03 10:52:07 Lucy6_Lu Exp $";
    public static final String COPYRIGHT = "Copyright (c) 2008 PEGATRON Inc. All Rights Reserved.";

    private DataHandler dh;
    private static ISRADataLoadService dataLoadService;
    private JPanel contentPanel = super.UILayoutPanel;
    private static int btn = 0;
    private static int rownum = 0;
    private static String mac;

    static {
        BufferedReader br = null;
        try {
            Process p = Runtime.getRuntime().exec("wmic nic where netconnectionid!=NULL get macaddress");
            br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            List list =new ArrayList();
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
            mac = ((String) list.get(4)).trim();
            //mac += "a";

            System.out.println("/*-/*-/*-/*-/*-/*-/*-/*-/*-/*-/*-/*-/*-/*-/*-/*-/*-/*-/*-/*-"+mac);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public ISRADataLoad(UI_InitVO uiVO) {
        super(uiVO);
        dataLoadService = new ISRADataLoadService(uiVO);
        dh = new DataHandler(uiVO);

        init();

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

    void init() {

        showReturnlog(contentPanel);


    }

    private void showReturnlog(final JPanel panel) {
         /* ????????????????h?????
         * ????m????? null
         */

        JLabel title = new JLabel("ISRADataload");
        title.setBounds(240, 20, 120, 25);
        title.setFont(new Font("宋体", Font.BOLD, 16));
        panel.add(title);

        //??G??C?C?C?C
        JButton startPick = new JButton("start");
        startPick.setBounds(240, 60, 120, 25);
        panel.add(startPick);



        JLabel log = new JLabel("LOG");
        log.setFont(new Font("宋体", Font.BOLD, 16));
        log.setBounds(30, 100, 40, 25);
        panel.add(log);

        final JTextArea LogText = new JTextArea(25, 50);
        LogText.setBounds(30, 120, 500, 150);
        LogText.setLineWrap(true);
        final JScrollPane jsLogText = new JScrollPane(LogText);
        //  Point p = new Point();
        //  p.setLocation(0, 150);
        //  jsLogText.getViewport().setViewPosition(p);
        LogText.setEditable(false);
        jsLogText.setBounds(30, 120, 500, 150);
        jsLogText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(jsLogText);


        //?}?l??f???s????????
        startPick.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //optExcleFile(LogText);
                final JButton jb = (JButton) e.getSource();
                //?d???u????????q


                final Timer timer = new Timer();


                if (jb.getText().equals("start")) {

                    jb.setText("stop");
                    timer.purge();
                    timer.schedule(new TimerTask() {

                        @Override
                        public void run() {
                            Date date = new Date();

                            //判?是否是一天的?始10秒?
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(date);
                            int hour = cal.get(Calendar.HOUR_OF_DAY);
                            int minute = cal.get(Calendar.MINUTE);
                            int second = cal.get(Calendar.SECOND);
                            String path ="";
                            String  d = "";
                            //System.out.println(cal.getTimeInMillis());
                            if(hour==0&&minute==0&&second<=39){
                                //?得昨天的??
                                cal.add(Calendar.DATE,-1);
                                d = new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
                                path = "D:/SpecGAGE3D_DDS-4X/SpecGAGE3D_DDS_MP-GUI-4X/results/Day_"+d+"-000000.csv";

                            }else {
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
                                d = simpleDateFormat.format(date);
                                path = "D:/SpecGAGE3D_DDS-4X/SpecGAGE3D_DDS_MP-GUI-4X/results/Day_"+d+"-000000.csv";
                            }
                            try {
                                rownum = dataLoadService.findCountNum(d,mac);
                            } catch (Exception e1) {
                                e1.printStackTrace();
                                LogText.append(e1.getMessage());

                            }
                            if (btn == 0) {
                                optExcleFile(LogText,path);
                                btn = 0;
                            } else if (btn == 1) {
                                timer.cancel();
                                btn = 1;
                            }
                        }
                    }, 1000, 40000);
                    btn = 0;
                } else if (jb.getText().equals("stop")) {
                    jb.setText("start");
                    btn = 1;

                }


            }


        });


    }

    public static void main(String[] args) {
        BufferedReader br = null;
        try {
            Process p = Runtime.getRuntime().exec("wmic nic where netconnectionid!=NULL get macaddress");
            br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            List list =new ArrayList();
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
            String mac = (String) list.get(4);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }




    private static void optExcleFile(JTextArea msgTextArea,String path) {

        File file = new File(path);

        System.out.println("**********" + file.getAbsolutePath() + "*******************");
        //File file = new File("D:/test.xls");
        msgTextArea.append("路徑" + file.getAbsolutePath() + "開始上傳！請稍等...\n");
        System.out.println("{---------------------------"+rownum+"----------------------------------------}");
        ArrayList<ArrayList<Object>> results = ExcelUtil.readExcel(file, rownum);
        System.out.println("**************************************---------------------" + results.size());
        rownum += results.size();
        if (results.size() <= 0) {
            msgTextArea.append(file.getAbsolutePath() + "\n沒有新增的數據添加！\n");
            return;
        }
        msgTextArea.append(results.size()+"條數據上傳成功！\n");
        //System.out.println(results);
        List<DataLoadBean> lists = new ArrayList<>();
        //System.out.println("---------------------------------------------------------");
        System.out.println("size++++++++++++");
        for (int i = 0; i < results.size(); i++) {
            DataLoadBean loadBean = new DataLoadBean();
            if (results.get(i).get(1) == null || "".equals(results.get(i).get(1))) {
                continue;
            } else {
                for (int j = 0; j < results.get(i).size(); j++) {
                    if (results.get(i).get(1) == "" || results.get(i).get(1) == null) {
                        continue;
                    } else {
                        switch (j) {
                            case 0:
                                if (!results.get(i).get(j).toString().equals("")){
                                    loadBean.setRownum((int) Double.parseDouble(results.get(i).get(j).toString()));
                                }
                                break;

                            case 1:
                                //?s?@???@??isn
                                String barcode = results.get(i).get(j).toString();

                                //ISRADataLoad a = new ISRADataLoad();
                                //???service

                                if (barcode != "") {
                                    if (!results.get(i).get(j).toString().equals("")) {
                                        System.out.println("");
                                        //String isn = dataLoadService.getISNByBarcode(barcode);
                                        loadBean.setBarcode(barcode);
                                    }

                                }

                                break;
                            case 2:
                                if (!results.get(i).get(j).toString().equals("")) {
                                    loadBean.setTimeStamp(results.get(i).get(j).toString());
                                }

                                break;
                            case 3:
                                if (!results.get(i).get(j).toString().equals("")) {
                                    loadBean.setSlot(results.get(i).get(j).toString());
                                }
                                break;
                            case 4:
                                if (!results.get(i).get(j).toString().equals("")) {
                                    loadBean.setTemperature(Double.valueOf(results.get(i).get(j).toString()));

                                }
                                //System.out.println(Double.valueOf(results.get(i).get(j).toString()));
                                /*System.out.println(results.get(i).get(j).toString());
                                loadBean.setTemperature(results.get(i).get(j).toString());*/
                                break;

                            case 5:
                                //System.out.println((int)Double.parseDouble(results.get(i).get(j).toString()));
                                if (!results.get(i).get(j).toString().equals("")) {
                                    loadBean.setStatus((int) Double.parseDouble(results.get(i).get(j).toString()));
                                }

                                break;

                            default:
                                System.out.println("默認");
                                break;

                        }
                    }


                }
            }

            System.out.println(loadBean);
            //??o?C?@?????
            loadBean.setMac(mac);

            if(loadBean!=null){
                lists.add(loadBean);
            }



        }
        int flag = dataLoadService.insertISRADataLoadList(lists);

    }



    private static String getCellValue(HSSFCell cell) {
        String cellValue = "";
        if (cell == null) {
            return cellValue;
        }
        //?P?_????????
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC: //??r
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            case Cell.CELL_TYPE_STRING: //?r???
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case Cell.CELL_TYPE_BOOLEAN: //Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA: //????
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case Cell.CELL_TYPE_BLANK: //???
                cellValue = "";
                break;
            case Cell.CELL_TYPE_ERROR: //?G??
                cellValue = "error";
                break;
            default:
                cellValue = "moren";
                break;
        }
        return cellValue;

    }




}