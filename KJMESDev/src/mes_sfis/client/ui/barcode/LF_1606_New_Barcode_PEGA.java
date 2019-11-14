package mes_sfis.client.ui.barcode;

import base.client.enums.ToolBarItem;
import base.client.ui.BasePanel;
import base.client.util.BaseServletAgent;
import base.client.util.CloneArray_ChangeStr;
import base.client.util.component.PDateTimeTextField;
import base.client.util.component.P_Component_Meta;
import base.enums.CommandName;
import base.vo.BaseVO;
import base.vo.ResultVO;
import base.vo.UI_InitVO;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.*;
import mes_sfis.client.pdf.PDFCreater1606;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;
import java.util.regex.Pattern;

/**
 * Created by Feng1_Lu on 2018/1/15.
 */
public class LF_1606_New_Barcode_PEGA extends BasePanel {
    public static final String VERSION    = "$Name:  $, $Id: LF_1606_New_Barcode_PEGA.java,v 1.2 2018/03/02 03:22:37 Lucy6_Lu Exp $";
    public static final String COPYRIGHT  = "Copyright (c) 2008 PEGATRON Inc. All Rights Reserved.";
    private Color color = new Color(106, 106, 255);
    private Font font = new Font("宋體", Font.BOLD, 15);
    private JLabel LPGPN, LKJPN, LDate, LSUM, LNote, LPrintNum;
    private JComboBox comboBox;
    private JTextField JKJPN, JSUM, JNote, JPrintNum;
    private PDateTimeTextField dateTextField;
    private JButton button;
    private HashMap mp;
    private com.lowagie.text.Font Pfont1, Pfont2, Pfont3;

    public LF_1606_New_Barcode_PEGA(UI_InitVO uiVO) {
        super(uiVO);
        init();
        comBoxEvent();
        print();
    }

    void init() {
        LPGPN = MyLable("客戶料號(PegaPN):", 20, 20);
        LKJPN = MyLable("VenderPn:", 20, 60);
        LDate = MyLable("日期:", 20, 100);
        LSUM = MyLable("QTY:", 20, 140);
        LNote = MyLable("Note:", 20, 180);
        LPrintNum = MyLable("打印張數:", 20, 220);

        comboBox = MyComboBox();
        GetItem();
        JKJPN = MyTextField(180, 60);
        dateTextField = new PDateTimeTextField(uiVO, "PDTimeST", 150, true, true);
        dateTextField.setBounds(180, 100, 200, 30);
        JSUM = MyTextField(180, 140);
        JNote = MyTextField(180, 180);
        JPrintNum = MyTextField(180, 220);

        button = new JButton("打印");
        button.setLayout(null);
        button.setBounds(100,260,150,30);

        add(LPGPN);add(LKJPN);add(LDate);add(LSUM);add(LNote);add(comboBox);add(JKJPN);
        add(dateTextField);add(JSUM);add(JNote);add(LPrintNum);add(JPrintNum);add(button);
    }


    public JLabel MyLable(String name, int x, int y) {
        JLabel label = new JLabel(name, JLabel.RIGHT);
        label.setFont(font);
        label.setForeground(color);
        label.setBounds(x, y, 150, 30);
        label.setVisible(true);
        return label;
    }

    public JTextField MyTextField(int x, int y) {
        JTextField textField = new JTextField(25);
        textField.setFont(font);
        textField.setBounds(x, y, 200, 30);
        textField.setVisible(true);
        return textField;
    }

    public JComboBox MyComboBox() {
        JComboBox comboBox = new JComboBox();
        comboBox.setFont(font);
        comboBox.setForeground(color);
        comboBox.setBounds(180, 20, 200, 30);
        return comboBox;
    }

    public void initFont() {
        BaseFont bfChinese;
        //bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        try {
            bfChinese = BaseFont.createFont("MHei-Medium", "UniCNS-UCS2-H", BaseFont.NOT_EMBEDDED);
            Pfont1 = new com.lowagie.text.Font(bfChinese, 20, Font.BOLD, Color.BLACK);
            Pfont2 = new com.lowagie.text.Font(bfChinese, 18, Font.BOLD, Color.BLACK);
            Pfont3 = new com.lowagie.text.Font(bfChinese, 10, Font.BOLD, Color.BLACK);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void GetItem() {
        try {
            mp = new HashMap();
            comboBox.removeAllItems();
            comboBox.addItem("");
            Vector vct = new Vector();
            BaseServletAgent bsa = new BaseServletAgent(uiVO);
            BaseVO bv = new BaseVO(uiVO, this.getClass().toString(), CommandName.CallPLSQLCmd2, vct);
            bv.setPackageName("AAA_1606_MES_CHECK");
            bv.setFunctionName("GetName");
            ResultVO rv = bsa.doFunc(bv);
            Vector result = rv.getData();
            Hashtable ht = null;
            if (result.size() > 1) {
                for (int i = 1; i < result.size(); i++) {
                    ht = (Hashtable) result.elementAt(i);
                    String PagePN = CloneArray_ChangeStr.NulltoSpace(ht.get("PEGAPN"));
                    String Note = CloneArray_ChangeStr.NulltoSpace(ht.get("NOTE"));
                    System.out.println("=========="+PagePN+"================="+Note);
                    mp.put(PagePN, Note);
                    comboBox.addItem(PagePN);
                }
            } else {
                JOptionPane.showConfirmDialog(null, "查資料庫無任何信息");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("-----------------------"+mp.toString());
    }

    public void comBoxEvent() {
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String key = comboBox.getSelectedItem().toString().trim();
                String value = String.valueOf(mp.get(key));
                JNote.setText(value);
            }
        });
    }

    public PdfPCell createBarCode(PdfWriter writer, String codeStr, int maxWidth) {
        PdfContentByte cd = writer.getDirectContent();
        Barcode128 code39ext = new Barcode128();
        code39ext.setStartStopText(false);
        code39ext.setExtended(true);
        //code39ext.setFont(null);// ?示?片下字符串?容
        code39ext.setTextAlignment(Element.ALIGN_LEFT);
        code39ext.setBarHeight(13f);
        code39ext.setX(0.5f);
        code39ext.setCode(codeStr);
        com.lowagie.text.Image image39 = code39ext.createImageWithBarcode(cd, null, null);
        PdfPCell barcodeCell = new PdfPCell(image39);
        barcodeCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        barcodeCell.setPadding(4);
        barcodeCell.setBorder(11);
        return barcodeCell;
    }

    public PdfPCell createCell(String cellStr, com.lowagie.text.Font font, int rowHeight, int borderNum) {
        PdfPCell newCell = new PdfPCell(new Paragraph(cellStr, font));
        newCell.setBorder(borderNum);
        if (rowHeight > 0) {
            newCell.setMinimumHeight(rowHeight);
        }

        return newCell;
    }

    public PdfPTable createPdfTable(PdfWriter writer, String BatchNo, String note) {
        initFont();
        PdfPTable pdftable = new PdfPTable(2);
        pdftable.setTotalWidth(198);
        int[] a = {40, 60};
        try {
            pdftable.setWidths(a);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        pdftable.setLockedWidth(true);
        pdftable.getDefaultCell().setBorder(0);//PdfPCell.NO_BORDER
        pdftable.setHorizontalAlignment(Element.ALIGN_LEFT);

        PdfPCell title = new PdfPCell(new Paragraph("PEGATRON  Unihan", Pfont1));//xx[1]客戶料號
        title.setColspan(2);
        title.setHorizontalAlignment(Element.ALIGN_LEFT);
        title.setPadding(4);
        pdftable.addCell(title);

        pdftable.addCell(createCell("Page P/N", Pfont3, 14, 7));
        pdftable.addCell(createBarCode(writer, comboBox.getSelectedItem().toString().trim(), a[1]));

        pdftable.addCell(createCell("Vendor P/N", Pfont3, 14, 7));
        pdftable.addCell(createBarCode(writer, JKJPN.getText().trim(), a[1]));

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateTime = dateTextField.getText().toString().trim().substring(0,10);
        System.out.println("---------------------------------------" + dateTime);
        Date date = null;
        try {
            date = dateFormat.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat SDF = new SimpleDateFormat("MMddyyyy");
        dateTime = SDF.format(date);
        System.out.println("**************************************" + dateTime);
        pdftable.addCell(createCell("DateCode", Pfont3, 14, 7));
        pdftable.addCell(createBarCode(writer, dateTime, a[1]));

        pdftable.addCell(createCell("BatchNo", Pfont3, 14, 7));
        pdftable.addCell(createBarCode(writer, BatchNo, a[1]));

        pdftable.addCell(createCell("QTY", Pfont3, 14, 7));
        pdftable.addCell(createBarCode(writer, JSUM.getText().trim(), a[1]));

        pdftable.addCell(createCell("Note", Pfont3, 57, 7));
        pdftable.addCell(createCell(note, Pfont3, 57, 11));
        return pdftable;
    }

    public static boolean printPdf(String pdfPath) {
        try {
            Runtime.getRuntime().exec("cmd.exe /C start acrord32 /P /h " + pdfPath);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void doPrint() {
        String filePath = "d:/barcodes1606test.pdf";
        Document document = new Document(new com.lowagie.text.Rectangle(218, 275), 10, 10, 10, 5);
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
            int printNum = Integer.parseInt(JPrintNum.getText().toString().trim());
            System.out.println("7777777777777777");
            for (int i = 0; i < printNum; i++) {
                String batch = "";
                PdfPTable pdfPTable = createPdfTable(writer, batch, String.valueOf(mp.get(comboBox.getSelectedItem().toString().trim())));
                document.add(pdfPTable);
            }
            document.close();
            writer.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("666666666666666666666666666");
        printPdf(filePath);
        System.out.println("88888888888888888888888888888888");
    }

    public void writeNote(String pagePn,String vendor,String date,String qty){
        try {
            Vector vt = new Vector();
            vt.add(pagePn);
            vt.add(vendor);
            vt.add(date);
            vt.add("");
            vt.add(qty);
            BaseServletAgent bsa = new BaseServletAgent(uiVO);
            BaseVO bv = new BaseVO(uiVO, this.getClass().toString(), CommandName.CallPLSQLCmd2, vt);
            bv.setPackageName("AAA_1606_MES_CHECK");
            bv.setFunctionName("InsertNote");
            ResultVO rv = bsa.doFunc(bv);
        } catch (Exception e) {
            e.printStackTrace();
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

    public void print() {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String reg = "^[0-9]*[1-9][0-9]*$";
                if (comboBox.getSelectedItem().toString().equals("")) {
                    JOptionPane.showConfirmDialog(null, "料號不能為空");
                    return;
                } else if (JKJPN.getText().trim().equals("")) {
                    JOptionPane.showConfirmDialog(null, "Vendor不能為空");
                    return;
                } else if (dateTextField.getText().equals("")) {
                    JOptionPane.showConfirmDialog(null, "日期不能為空");
                    return;
                } else if (JSUM.getText().equals("")) {
                    JOptionPane.showConfirmDialog(null, "GTY不能為空");
                    return;
                } else if (JPrintNum.getText().equals("")) {
                    JOptionPane.showConfirmDialog(null, "打印數量不能為空");
                    return;
                }
                if (!JSUM.getText().equals("")&&!Pattern.matches(reg, JSUM.getText().trim())) {
                    JOptionPane.showConfirmDialog(null, "QTY必須是正整數");
                    return;
                }
                else if (!JPrintNum.getText().equals("")&&!Pattern.matches(reg, JPrintNum.getText().trim())) {
                    JOptionPane.showConfirmDialog(null, "打印數量必須是正整數");
                    return;
                }
                else{
                    writeNote(comboBox.getSelectedItem().toString().trim(),JKJPN.getText().trim(),dateTextField.getText(),JNote.getText().trim());
                    HashMap pDFMap = new HashMap();

                    pDFMap.put("vendor","Casetek");
                    pDFMap.put("oemPN",comboBox.getSelectedItem().toString().trim());
                    pDFMap.put("dateStr","");//DATE_CODE
                    pDFMap.put("venderCode","I1917000"); //VENDER
                    pDFMap.put("venderSite","");
                    pDFMap.put("applePN","");
                    pDFMap.put("rev","");
                    pDFMap.put("config","");
                    pDFMap.put("desc",JNote.getText().trim());//DESCRIPTION
                    pDFMap.put("lc","");
                    pDFMap.put("qty",JSUM.getText().trim());//數量
                    pDFMap.put("batch","1");
                    pDFMap.put("cartonNo","");//PICK_NO
                    pDFMap.put("stage","T2");
                    pDFMap.put("oemComments",JKJPN.getText().trim()); //kj_pn
                    pDFMap.put("Cdate",dateTextField.getText());
                    try {
                        PDFCreater1606 pdfCreater1606 = new PDFCreater1606("d://CARTON.pdf");
                        pdfCreater1606.addPage(pDFMap);
                        pdfCreater1606.close();
                        pdfCreater1606.printPdf();
                    } catch (DocumentException e1) {
                        e1.printStackTrace();
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    }
                    //doPrint();
                    System.out.print("****************");
                    comboBox.setSelectedIndex(0);
                    JPrintNum.setText("1");
                    JNote.setText("");
                    JSUM.setText("");
                    dateTextField.setDateText("");
                    JKJPN.setText("");
                }
            }
        });
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
