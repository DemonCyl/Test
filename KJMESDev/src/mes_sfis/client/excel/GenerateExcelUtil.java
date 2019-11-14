package mes_sfis.client.excel;

import base.client.ui.BasePanel;
import base.client.util.component.P_Component_Meta;
import base.vo.UI_InitVO;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * Created by Srx_Zhu on 2018/5/16 0016.
 */
public class GenerateExcelUtil extends BasePanel {
    public static final String VERSION = "$Name:  $, $Id: GenerateExcelUtil.java,v 1.10 2018/04/16 09:28:13 Lucy6_Lu Exp $";
    public static final String COPYRIGHT = "Copyright (c) 2008 PEGATRON Inc. All Rights Reserved.";
    private JButton jb = new JButton("打開文件");
    private JButton tran = new JButton("執行");
    private JList<String> jl = new JList<>();
    private DefaultListModel model = new DefaultListModel();
    private File[] files;
    private String MOMM = "MOMM";
    private String GOMM = "GOMM";
    private JPanel panel = new JPanel();
    private List<HashMap> cmmData = new ArrayList<>();
    private List<HashMap> gommData = new ArrayList<>();
    private List<HashMap> mommData = new ArrayList<>();
    private List<HashMap> allData = new ArrayList<>();

    public GenerateExcelUtil(UI_InitVO uiVO) {
        super(uiVO);
        init();
    }

    public void init() {
        jl.setPreferredSize(new Dimension(600, 500));
        this.add(jb);
        this.add(tran);
        this.add(jl);
        this.add(panel);


        jb.setBounds(50, 60, 100, 40);
        tran.setBounds(170, 60, 100, 40);
        jl.setBounds(50,100,500,500);


        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                allData.clear();
                cmmData.clear();
                gommData.clear();
                mommData.clear();
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
                if (null == files || files.length == 0) {
                    JOptionPane.showConfirmDialog(null, "尚未選擇文件！！！", "警告", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                try {
                    test(files);
                    JOptionPane.showConfirmDialog(null, "成功！已生成CSV文件至D盤", "警告", JOptionPane.PLAIN_MESSAGE);
                } catch (Exception e1) {
                    JOptionPane.showConfirmDialog(null, "生成失敗，請聯繫MES課確認錯誤原因！", "警告", JOptionPane.PLAIN_MESSAGE);
                    e1.printStackTrace();
                }
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
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String dateNowStr = sdf.format(d);
        for (File file : files) {
            if (file.isFile()) {
                fName = file.getName();
                if (fName.toUpperCase().contains("CMM")) {  // CMM-Data
                    if (fName.toUpperCase().contains(".TXT")) {
                        cmmData = openTxt(file);
                        allData.addAll(cmmData);
                    } else {
                        JOptionPane.showConfirmDialog(null, "CMM應為TXT文件，如有其它格式文件，請聯繫MES課朱人為！！！", "警告", JOptionPane.PLAIN_MESSAGE);
                        return;
                    }
                }
                if (fName.toUpperCase().contains("MOMM")) {  // OMM-Data-CNC3
                    openExcel(file, MOMM);
                    allData.addAll(mommData);
                }
                if (fName.toUpperCase().contains("GOMM")) {  // OMM-Data-CNC2
                    openExcel(file, GOMM);
                    allData.addAll(gommData);
                }
            }
        }
        createCSVFile(allData, "D:\\", dateNowStr.replaceAll(":", ""));
    }

    /**
     * @Author:zhurenwei
     * @Description:讀取Excel內容
     * @Date: 2018/5/17 0017
     */
    public void openExcel(File file, String type) throws Exception {

        Workbook workbook = getWorkBook(file);
        if (workbook != null) {
            for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
                //?得?前sheet工作表
                Sheet sheet = workbook.getSheetAt(sheetNum);
                if (sheet == null) {
                    continue;
                }
                //獲得啟示行
                int firstRowNum = sheet.getFirstRowNum();
                //獲得結束行
                int lastRowNum = sheet.getLastRowNum();
                //循環除了第一行的所有行

                if (type.equals("MOMM")) {
                    firstRowNum = firstRowNum + 2;
                }
                if (type.equals("GOMM")) {
                    firstRowNum = firstRowNum + 7;
                }
                for (int rowNum = firstRowNum; rowNum <= lastRowNum; rowNum++) {
                    //?得?前行
                    //System.out.println("第:" + rowNum + "行");
                    Row row = sheet.getRow(rowNum);
                    if (row == null) {
                        continue;
                    }
                    //獲得起始列
                    int firstCellNum = row.getFirstCellNum();
                    //獲得最後一列
                    int lastCellNum = row.getPhysicalNumberOfCells();
                    String[] cells = new String[row.getPhysicalNumberOfCells()];
                    //循??前行
                    for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++) {
                        Cell cell = row.getCell(cellNum);

                        if (type.equals("MOMM") && cell != null && !getCellValue(cell).toString().contains("END") && !getCellValue(cell).toString().contains("BEGIN") && !getCellValue(cell).toString().contains("提示") && !getCellValue(cell).toString().contains(":")) {
                            cells[cellNum] = getCellValue(cell);
                            //System.out.println(cells[cellNum]);
                        }
                        if (type.equals("GOMM") && cell != null && !getCellValue(cell).toString().contains("名稱") && !getCellValue(cell).toString().contains("工件") && !getCellValue(cell).toString().contains("操作") && !getCellValue(cell).toString().contains("測量") && !getCellValue(cell).toString().contains("公司") && !getCellValue(cell).toString().contains("序號")) {
                            cells[cellNum] = getCellValue(cell);
                        }
                    }

                    if (cells[0] != null) {
                        if (type.equals("MOMM") && !cells[0].equals("") && cells.length > 4) {
                            HashMap mommMap = new HashMap();
                            mommMap.put("BATCH", cells[0].replaceAll(" ", ""));
                            mommMap.put("NULL1", "");
                            mommMap.put("NULL2", "");
                            mommMap.put("NULL3", "");
                            mommMap.put("FUNCTION", "MOMM");
                            mommMap.put("NULL4", "");
                            mommMap.put("NOMINAL", cells[2].replaceAll(" ", ""));
                            mommMap.put("+TOL", cells[3].replaceAll(" ", ""));
                            mommMap.put("TOL","-"+cells[4].replaceAll(" ", ""));
                            mommMap.put("NOMINAL2", getNOMINAL(cells[2].replaceAll(" ", ""), cells[3].replaceAll(" ", ""), "-" + cells[4].replaceAll(" ", "")));
                            mommMap.put("+TOL2", getTOL(cells[3].replaceAll(" ", ""),"-"+cells[4].replaceAll(" ", "")));
                            mommMap.put("TOL2",getTOL("-"+cells[4].replaceAll(" ", ""), cells[3].replaceAll(" ", "")));
                            String measurement = cells[1].replaceAll(" ", "");
                            mommMap.put("MEASUREMENT1", measurement);
                            mommMap.put("MEASUREMENT2", measurement);
                            mommMap.put("MEASUREMENT3", measurement);
                            mommData.add(mommMap);
                        } else if (type.equals("GOMM") && !cells[0].equals("") && cells.length > 4) {
                            HashMap gommMap = new HashMap();
                            gommMap.put("BATCH", cells[1].replaceAll(" ", ""));
                            gommMap.put("NULL1", "");
                            gommMap.put("NULL2", "");
                            gommMap.put("NULL3", "");
                            gommMap.put("FUNCTION", "GOMM");
                            gommMap.put("NULL4", "");
                            gommMap.put("NOMINAL", cells[3].replaceAll(" ", ""));
                            gommMap.put("+TOL", cells[4].replaceAll(" ", ""));
                            gommMap.put("TOL", "-" + cells[5].replaceAll(" ", ""));
                            gommMap.put("NOMINAL2", getNOMINAL(cells[3].replaceAll(" ", ""), cells[4].replaceAll(" ", ""), "-" + cells[5].replaceAll(" ", "")));
                            gommMap.put("+TOL2", getTOL(cells[4].replaceAll(" ", ""), "-" + cells[5].replaceAll(" ", "")));
                            gommMap.put("TOL2",getTOL( "-" + cells[5].replaceAll(" ", ""), cells[4].replaceAll(" ", "")));
                            String measurement = cells[6].replaceAll(" ", "");
                            gommMap.put("MEASUREMENT1", measurement);
                            gommMap.put("MEASUREMENT2", measurement);
                            gommMap.put("MEASUREMENT3", measurement);
                            gommData.add(gommMap);
                        }
                    }
                }
            }


        }

    }

    /**
     * @Author:zhurenwei
     * @Description:讀取TXT內容
     * @Date: 2018/5/17 0017
     */
    public List<HashMap> openTxt(File file) {
        String result = new String();

        List<HashMap> cmmList = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一?BufferedReader???取文件
            String s = null;
            while ((s = br.readLine()) != null) {//使用readLine方法，一次?一行
                result = s;
                String[] resultArray = result.split(",");
                if (resultArray.length > 0) {
                    HashMap cmmMap = new HashMap();
                    cmmMap.put("BATCH", resultArray[0].substring(0, resultArray[0].length() - 4).replaceAll(" ", ""));
                    cmmMap.put("NULL1", "");
                    cmmMap.put("NULL2", "");
                    cmmMap.put("NULL3", "");
                    cmmMap.put("FUNCTION", "CMM");
                    cmmMap.put("NULL4", "");
                    cmmMap.put("NOMINAL", resultArray[1].replaceAll(" ", "").substring(0, 5));
                    cmmMap.put("+TOL", resultArray[2].replaceAll(" ", "").substring(0, 5));
                    cmmMap.put("TOL", "-"+resultArray[3].replaceAll(" ", "").substring(0, 5));
                    cmmMap.put("NOMINAL2", getNOMINAL(resultArray[1].replaceAll(" ", ""), resultArray[2].replaceAll(" ", ""),"-"+resultArray[3].replaceAll(" ", "")));
                    cmmMap.put("+TOL2", getTOL(resultArray[2].replaceAll(" ", ""),"-"+resultArray[3].replaceAll(" ", "")));
                    cmmMap.put("TOL2",getTOL("-"+resultArray[3].replaceAll(" ", ""), resultArray[2].replaceAll(" ", "")));
                    String measurement = resultArray[4].substring(0, resultArray[4].indexOf(".") + 5);
                    cmmMap.put("MEASUREMENT1", measurement.replaceAll(" ", ""));
                    cmmMap.put("MEASUREMENT2", measurement.replaceAll(" ", ""));
                    cmmMap.put("MEASUREMENT3", measurement.replaceAll(" ", ""));
                    cmmList.add(cmmMap);
                }

            }

            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cmmList;
    }

    public  String getCellValue(Cell cell) {
        String cellValue = "";
        if (cell == null) {
            return cellValue;
        }
        //判斷數據的類型
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC: //數字
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
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

    public  Workbook getWorkBook(File file)throws Exception{



        //獲得文件名
        String fileName = file.getName();
        //新建Workbook工作薄對象，表示整個excel
        Workbook workbook=null;
        try {
            //獲取excel文件的io流
            InputStream is = new FileInputStream(file);
            //根据文件后墜名不同(xls和xlsx)獲得不同的Workbook實體對象
            if (fileName.contains("xlsx")) {
                System.out.println("xlsx_________________________________"+file.getName());
                workbook =  new XSSFWorkbook(is);
                return workbook;
            } else if (fileName.contains("xls")) {
                System.out.println("xls_________________________________"+file.getName());
                workbook = new HSSFWorkbook(is);
                return workbook;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return workbook;
    }



    /**
     * @Author:zhurenwei
     * @Description:獲得對稱公差值NOMINAL
     * @Date: 2018/5/17 0017
     */
    public  BigDecimal getNOMINAL(String G6, String H6, String I6) {
        System.out.println(I6);
        BigDecimal g6 = new BigDecimal(G6);
        //g6 = g6.setScale(2, BigDecimal.ROUND_DOWN); //小?位 直接舍去
        System.out.println("g6"+g6);
        BigDecimal h6 = new BigDecimal(H6);
       // h6 = h6.setScale(2, BigDecimal.ROUND_DOWN); //小?位 直接舍去
        System.out.println("h6"+h6);
        BigDecimal i6 = new BigDecimal(I6);
       // i6 = i6.setScale(2, BigDecimal.ROUND_DOWN); //小?位 直接舍去
        System.out.println("i6"+i6);
        BigDecimal v1 = new BigDecimal(String.valueOf(h6.add(i6)));
        BigDecimal v2 = new BigDecimal(2.00);
        BigDecimal data = v1.divide(v2, 3, BigDecimal.ROUND_DOWN);
        BigDecimal data2 = g6.add(data);
        System.out.println("標準值計算完成");
        return data2;
    }

    /**
     * @Author:zhurenwei
     * @Description:獲得對稱公差值+TOL/TOL
     * @Date: 2018/5/17 0017
     */
    public  BigDecimal getTOL(String H6, String I6) {
        System.out.println("開始上下公差計算");
        BigDecimal h6 = new BigDecimal(H6);
        System.out.println("h6"+h6);
      //  h6 = h6.setScale(2, BigDecimal.ROUND_DOWN); //小?位 直接舍去
        BigDecimal i6 = new BigDecimal(I6);
        System.out.println("i6"+i6);
      //  i6 = i6.setScale(2, BigDecimal.ROUND_DOWN); //小?位 直接舍去
        BigDecimal v1 = new BigDecimal(h6.subtract(i6).toString());
        BigDecimal v2 = new BigDecimal(2.00);
        BigDecimal data = v1.divide(v2, 3, BigDecimal.ROUND_DOWN);
        System.out.println("結束上下公差計算");
        return data;
    }

    public  File createCSVFile(List<HashMap> dataList, String outPutPath, String filename) {
        File csvFile = null;
        BufferedWriter csvWtriter = null;
        try {
            csvFile = new File(outPutPath + File.separator + filename + ".csv");
            File parent = csvFile.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            csvFile.createNewFile();
            // GB2312使正确?取分隔符","
            csvWtriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "Big5"), 1024);
            // ?入文件?部
            //writeRow(head, csvWtriter);
            // ?入文件?容
            for (HashMap row : dataList) {
                writeRow(row, csvWtriter);
            }
            csvWtriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                csvWtriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return csvFile;
    }

    private  void writeRow(HashMap row, BufferedWriter csvWriter) throws IOException {
        // ?入文件?部
        Iterator iter = row.entrySet().iterator();//?取key和value的set
        List list = new ArrayList();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();//把hashmap?成Iterator再迭代到entry
            String key = entry.getKey().toString();//?entry?取key
            //System.out.println(key);
            list.add(key);
        }

        StringBuffer sb = new StringBuffer();
        String str = sb.append("\"").append(row.get(list.get(12))).append("\",") //batch
                .append(row.get(list.get(10))).append(",")//null1
                .append(row.get(list.get(11))).append(",")//null2
                .append(row.get(list.get(13))).append(",")//null3
                .append("\"").append(row.get(list.get(4))).append("\",")//function
                .append(row.get(list.get(14))).append(",")//null4
                .append(row.get(list.get(2))).append(",")//NOMINAL
                .append(row.get(list.get(8))).append(",")//+tol
                .append(row.get(list.get(5))).append(",")//tol
                .append(row.get(list.get(0))).append(",")//NOMINAL2
                .append(row.get(list.get(3))).append(",")//+tol2
                .append(row.get(list.get(1))).append(",")//tol2
                .append(row.get(list.get(9))).append(",")//measurement1
                .append(row.get(list.get(7))).append(",")//measurement2
                .append(row.get(list.get(6))).append(",").toString();//measurement3
        String str2 = str.substring(0, str.length() - 1);
        csvWriter.write(str2);
        csvWriter.newLine();
    }


}
