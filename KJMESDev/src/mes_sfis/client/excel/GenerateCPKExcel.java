package mes_sfis.client.excel;

/**
 * Created by Srx_Zhu on 2018/5/24 0024.
 */


import base.client.ui.BasePanel;
import base.client.util.component.P_Component_Meta;
import base.vo.UI_InitVO;
import mes_sfis.client.util.MESGlobe;
import mes_sfis.client.util.ProgressBarThread;
import mes_sfis.client.util.ThreadDiag;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * Created by sunming on 2017/12/13.
 */
public class GenerateCPKExcel extends BasePanel {

    public static final String VERSION = "$Name:  $, $Id: GenerateCPKExcel.java,v 1.10 2018/04/16 09:28:13 SRX_zhu Exp $";
    public static final String COPYRIGHT = "Copyright (c) 2008 PEGATRON Inc. All Rights Reserved.";
    private String fileToBeRead = "D:\\mes_data\\demo\\1721-443-CPK_template.xlsx"; // excel位置
    public static final String EXCEL_DEMO = "D:\\mes_data\\demo\\";
    public static final String FILE_NAME = "1721-443-CPK_template.xlsx";
    private JButton jb = new JButton("打開文件");
    private JButton tran = new JButton("執行");
    private JList<String> jl = new JList<>();
    private DefaultListModel model = new DefaultListModel();
    private File[] files;
    private JPanel panel = new JPanel();
    private int k = 0;
    private int dataWidthSize = 0;
    private List<HashMap> list = new ArrayList();

    public GenerateCPKExcel(UI_InitVO uiVO) {
        super(uiVO);
        init();
    }

    public void init() {
        createDir(EXCEL_DEMO);
        jl.setPreferredSize(new Dimension(600, 500));
        this.add(jb);
        this.add(tran);
        this.add(jl);
        this.add(panel);

        jb.setBounds(50, 60, 100, 40);
        tran.setBounds(170, 60, 100, 40);
        jl.setBounds(50, 200, 500, 500);
        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!model.isEmpty()) {
                    model.removeAllElements();
                    jl.setModel(model);
                }
                JFileChooser jf = new JFileChooser();
                jf.setFileSelectionMode(0);
                jf.setMultiSelectionEnabled(true);
                int state = jf.showOpenDialog(null);
                if (state == 1) {
                    return;
                } else {
                    dataWidthSize=0;
                    list.clear();
                    k=0;
                    files = jf.getSelectedFiles();
                    for (File file : files) {
                        if (file.isFile()) {
                            model.addElement(file.getAbsoluteFile());
                        }
                    }
                    jl.setModel(model);
                }
            }
        });
        tran.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (null == files || files.length == 0) {
                            JOptionPane.showConfirmDialog(null, "尚未選擇文件！！！", "警告", JOptionPane.PLAIN_MESSAGE);
                            return;
                        }
                        try {
                            test(files);
                        } catch (Exception e1) {
                            JOptionPane.showConfirmDialog(null, "生成失敗，請聯繫MES課確認錯誤原因！", "警告", JOptionPane.PLAIN_MESSAGE);
                            e1.printStackTrace();
                        }
                    }
                }).start();
            }
        });
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


    public void test(File[] files) throws Exception {
        String fName;
        for (File file : files) {
            if (file.isFile()) {
                fName = file.getAbsolutePath();
                System.out.println("1------------------------------" + fName);
                List<HashMap> loadData = getdata(fName);
                generateCPK(loadData);
            }
        }

    }


    public List<HashMap> getdata(String filename) throws IOException {

        List<HashMap> dataList = new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(new BufferedInputStream(new FileInputStream(filename), 8 * 1024));
            XSSFSheet sheet = workbook.getSheet("sheet1");
            for (int i = 10; i < sheet.getLastRowNum() + 1; i++) {
                XSSFRow row = sheet.getRow((short) i);
                if (null == row) {
                    continue;
                } else {
                    HashMap map = new HashMap();
                    for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
                        dataWidthSize = row.getPhysicalNumberOfCells() - 4;
                        XSSFCell cell = row.getCell((short) j);
                        map.put(j, getCellValue(cell));
                    }
                    dataList.add(map);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    public static Object getCellValue(Cell cell) {
        String cellValue = "";
        if (cell == null) {
            return cellValue;
        }
        //判斷數據的類型
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC: //數字
                return cell.getNumericCellValue();
            case Cell.CELL_TYPE_STRING: //字符串
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case Cell.CELL_TYPE_BOOLEAN: //Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA: //公式
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case Cell.CELL_TYPE_BLANK: //空值
                cellValue = "";
                break;
            case Cell.CELL_TYPE_ERROR: //故障
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知?型";
                break;
        }
        return cellValue;
    }


    public void generateCPK(List<HashMap> list) {


        try {
            XSSFWorkbook templateWorkbook = new XSSFWorkbook(new BufferedInputStream(new FileInputStream(fileToBeRead), 8 * 1024));
            XSSFSheet templateSheet = templateWorkbook.getSheet("工作表1");
            for (int i = 10; i < 10 + list.size(); i++) {
                XSSFRow row2 = templateSheet.getRow(i);
                for (int j = 0; j <row2.getPhysicalNumberOfCells(); j++) {
                    XSSFCell cell = row2.getCell(j);
                    switch (j) {
                        case 2:
                            cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                            cell.setCellValue(list.get(k).get(0).toString());
                            break;
                        case 6:
                            if (list.get(k).get(1).equals("")) {
                                cell.setCellType(XSSFCell.CELL_TYPE_BLANK);
                            } else {
                                cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
                                cell.setCellValue(Double.parseDouble(list.get(k).get(1).toString()));
                            }
                            break;
                        case 7:
                            if (list.get(k).get(2).equals("")) {
                                cell.setCellType(XSSFCell.CELL_TYPE_BLANK);
                            } else {
                                cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
                                cell.setCellValue(Double.parseDouble(list.get(k).get(2).toString()));
                            }
                            break;
                        case 8:
                            if (list.get(k).get(3).equals("")) {
                                cell.setCellType(XSSFCell.CELL_TYPE_BLANK);
                            } else {
                                cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
                                cell.setCellValue(Double.parseDouble(list.get(k).get(3).toString()));
                            }
                            break;
                        default:
                            if(j>=37&&j<38+dataWidthSize){
                                if (list.get(k).get(j - 33) == null || list.get(k).get(j - 33).equals("")) {
                                    cell.setCellType(XSSFCell.CELL_TYPE_BLANK);
                                } else {
                                    cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
                                    cell.setCellValue(Double.parseDouble(list.get(k).get(j - 33).toString()));
                                }
                            }
                            if(j>=38 + dataWidthSize){
                                cell.setCellType(XSSFCell.CELL_TYPE_BLANK);
                            }
                            break;
                    }
                }
                k++;
            }
            ProgressBarThread progressBarThread = new ProgressBarThread();//新生成一個線程
            progressBarThread.start();//啟動事務線程
            (new ThreadDiag(progressBarThread, "正在執行，請等待····")).start();//啟動等待線程框
            for (int i = list.size() + 10; i < templateSheet.getLastRowNum() + 1; i++) {
                templateSheet.removeRow(templateSheet.getRow(i));
            }
            templateSheet.setForceFormulaRecalculation(true);
            BufferedOutputStream out = null;
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String dateNowStr = sdf.format(d);
            try {
                out = new BufferedOutputStream(new FileOutputStream("D:\\" + dateNowStr.replaceAll(":", "").replaceAll(" ", "") + ".xlsx"), 8 * 1024);
                templateWorkbook.write(out);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public  void createDir(String path) {
        File dir = new File(path);
        if(!dir.exists()){
            System.out.println("正在生成文件夾");
            dir.mkdirs();
        }
        dir = new File(EXCEL_DEMO+FILE_NAME);
        if(!dir.exists()){
            System.out.println("DownLoad");
            downloadFile();
        }
    }

    private void downloadFile(){
        URL website = null;
        try {
            website = new URL(MESGlobe.PEGA_URL+"/demo/1721-443-CPK_template.xlsx");
            System.out.println("正在下載------------------------"+website);
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            System.out.println("文件輸出路勁----------------"+EXCEL_DEMO+FILE_NAME);
            FileOutputStream fos = new FileOutputStream(EXCEL_DEMO+FILE_NAME);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}