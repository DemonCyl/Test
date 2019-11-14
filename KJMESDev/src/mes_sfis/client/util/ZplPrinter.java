package mes_sfis.client.util;

import java.io.File;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.standard.PrinterName;

import sun.awt.AppContext;

/**
 * Created by Pino_Gao on 2018/8/28.
 */
public class ZplPrinter {
    private String printerURI = null;
    private PrintService printService = null;
    private byte[] dotFont;
    private String darkness = "~SD22";
    private String width = "^PW1200";
    private String length = "^LL800";
    private String begin = "^XA" + darkness + width;
    private String end = "^XZ";
    private String content = "";
    private String message = "";


    public ZplPrinter(){

        File file = new File("D://mes_data//configs//ts24.lib");
        if(file.exists()){
            FileInputStream fis;
            try {
                fis = new FileInputStream(file);
                dotFont = new byte[fis.available()];
                fis.read(dotFont);
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //��l�ƥ��L��
        AppContext.getAppContext().put(PrintServiceLookup.class.getDeclaredClasses()[0], null);//��s���L��C��
        PrintService k = PrintServiceLookup.lookupDefaultPrintService();
        String a = k.getName();
        this.printerURI = a;
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null,null);
        if (services != null && services.length > 0) {
            for (PrintService service : services) {
                if (a.equals(service.getName())) {
                    printService = service;
                    break;
                }
            }
        }

        if (this.printService == null) {
            System.out.println("����쥴�L���G["+printerURI+"]");
            //�`?�X�Ҧ������L��
            if (services != null && services.length > 0) {
                //System.out.println("�i�Υ��L���C��G");
                for (PrintService service : services) {
                    System.out.println("["+service.getName()+"]");
                }
            }
        }else{
            System.out.println("��쥴�L���G["+printerURI+"]");
            System.out.println("���L���W�١G["+ this.printService.getAttribute(PrinterName.class).getValue()+"]");
        }
    }

    /**
     * ?�m?��?
     * @param barcode ??�r��
     * @param zpl ???���ҪO
     */
    public void setBarcode(String barcode,String zpl) {
        content += zpl.replace("${data}", barcode);
    }

    /**
     * ����r�šB�^��r��(�]�t?�r)�V�X
     * @param str ����B�^��
     * @param x x��?
     * @param y y��?
     * @param eh �^��r�^����height
     * @param ew �^��r�^?��width
     * @param es �^��r�^?�Zspacing
     * @param mx ����x?�r�^?�Ω�j���v�C�S?1-10�A�q?1
     * @param my ����y?�r�^?�Ω�j���v�C�S?1-10�A�q?1
     * @param ms ����r�^?�Z�C24�O?��?�X�쪺�ȡC
     */
    public void setText(String str, int x, int y, int eh, int ew, int es, int mx, int my, int ms) {
        byte[] ch = str2bytes(str);
        for (int off = 0; off < ch.length;) {
            if (((int) ch[off] & 0x00ff) >= 0xA0) {//����r��
                try {
                    int qcode = ch[off] & 0xff;
                    int wcode = ch[off + 1] & 0xff;
                    content += String.format("^FO%d,%d^XG0000%01X%01X,%d,%d^FS\n", x, y, qcode, wcode, mx, my);
                    begin += String.format("~DG0000%02X%02X,00072,003,\n", qcode, wcode);
                    qcode = (qcode + 128 - 32) & 0x00ff;
                    wcode = (wcode + 128 - 32) & 0x00ff;
                    int offset = ((int) qcode - 16) * 94 * 72 + ((int) wcode - 1) * 72;
                    for (int j = 0; j < 72; j += 3) {
                        qcode = (int) dotFont[j + offset] & 0x00ff;
                        wcode = (int) dotFont[j + offset + 1] & 0x00ff;
                        int qcode1 = (int) dotFont[j + offset + 2] & 0x00ff;
                        begin += String.format("%02X%02X%02X\n", qcode, wcode, qcode1);
                    }
                    x = x + ms * mx;
                    off = off + 2;
                } catch (Exception e) {
                    e.printStackTrace();
                    setChar("X", x, y, eh, ew);
                    x = x + es;
                    off = off + 2;
                }
            } else if (((int) ch[off] & 0x00FF) < 0xA0) {
                setChar(String.format("%c", ch[off]), x, y, eh, ew);
                x = x + es;
                off++;
            }
        }
    }
    /**
     * �^��r�Ŧ�(�]�t?�r)
     * @param str �^��r�Ŧ�
     * @param x x��?
     * @param y y��?
     * @param h ����
     * @param w ?��
     */
    public void setChar(String str, int x, int y, int h, int w) {
        content += "^FO" + x + "," + y + "^A0," + h + "," + w + "^FD" + str + "^FS";
    }
    /**
     * �^��r��(�]�t?�r)???��?90��
     * @param str �^��r�Ŧ�
     * @param x x��?
     * @param y y��?
     * @param h ����
     * @param w ?��
     */
    public void setCharR(String str, int x, int y, int h, int w) {
        content += "^FO" + x + "," + y + "^A0R," + h + "," + w + "^FD" + str + "^FS";
    }
    /**
     * ?�����㪺ZPL
     * @return
     */
    public String getZpl() {
        return begin + content + end;
    }
    /**
     * ���mZPL���O�A?�ݭn���L�h??��?�Իݭn?�ΡC
     */
    public void resetZpl() {
        begin = "^XA" + darkness + width;
        end = "^XZ";
        content = "";
    }
    /**
     * ���L
     * @param zpl ���㪺ZPL
     */
    public boolean print(String zpl){
        if(printService==null){
            message = "���L���l�G�S����쥴�L��["+printerURI+"]";
            return false;
        }
        DocPrintJob job = printService.createPrintJob();
        byte[] by = zpl.getBytes();
        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        Doc doc = new SimpleDoc(by, flavor, null);
        try {
            job.print(doc, null);
            message = "�w���L";
            return true;
        } catch (PrintException e) {
            message = "���L�H��:"+e.getMessage();
            e.printStackTrace();
            return false;
        }
    }
    public String getMessage(){
        return message;
    }
    /**
     * �r�Ŧ�?byte[]
     * @param s
     * @return
     */
    private byte[] str2bytes(String s) {
        if (null == s || "".equals(s)) {
            return null;
        }
        byte[] abytes = null;
        try {
            abytes = s.getBytes("gb2312");
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        return abytes;
    }
}