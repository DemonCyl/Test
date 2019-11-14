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

        //初始化打印机
        AppContext.getAppContext().put(PrintServiceLookup.class.getDeclaredClasses()[0], null);//刷新打印机列表
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
            System.out.println("有找到打印機：["+printerURI+"]");
            //循?出所有的打印机
            if (services != null && services.length > 0) {
                //System.out.println("可用打印機列表：");
                for (PrintService service : services) {
                    System.out.println("["+service.getName()+"]");
                }
            }
        }else{
            System.out.println("找到打印機：["+printerURI+"]");
            System.out.println("打印機名稱：["+ this.printService.getAttribute(PrinterName.class).getValue()+"]");
        }
    }

    /**
     * ?置?形?
     * @param barcode ??字符
     * @param zpl ???式模板
     */
    public void setBarcode(String barcode,String zpl) {
        content += zpl.replace("${data}", barcode);
    }

    /**
     * 中文字符、英文字符(包含?字)混合
     * @param str 中文、英文
     * @param x x坐?
     * @param y y坐?
     * @param eh 英文字体高度height
     * @param ew 英文字体?度width
     * @param es 英文字体?距spacing
     * @param mx 中文x?字体?形放大倍率。范?1-10，默?1
     * @param my 中文y?字体?形放大倍率。范?1-10，默?1
     * @param ms 中文字体?距。24是?比?合适的值。
     */
    public void setText(String str, int x, int y, int eh, int ew, int es, int mx, int my, int ms) {
        byte[] ch = str2bytes(str);
        for (int off = 0; off < ch.length;) {
            if (((int) ch[off] & 0x00ff) >= 0xA0) {//中文字符
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
     * 英文字符串(包含?字)
     * @param str 英文字符串
     * @param x x坐?
     * @param y y坐?
     * @param h 高度
     * @param w ?度
     */
    public void setChar(String str, int x, int y, int h, int w) {
        content += "^FO" + x + "," + y + "^A0," + h + "," + w + "^FD" + str + "^FS";
    }
    /**
     * 英文字符(包含?字)???旋?90度
     * @param str 英文字符串
     * @param x x坐?
     * @param y y坐?
     * @param h 高度
     * @param w ?度
     */
    public void setCharR(String str, int x, int y, int h, int w) {
        content += "^FO" + x + "," + y + "^A0R," + h + "," + w + "^FD" + str + "^FS";
    }
    /**
     * ?取完整的ZPL
     * @return
     */
    public String getZpl() {
        return begin + content + end;
    }
    /**
     * 重置ZPL指令，?需要打印多??的?候需要?用。
     */
    public void resetZpl() {
        begin = "^XA" + darkness + width;
        end = "^XZ";
        content = "";
    }
    /**
     * 打印
     * @param zpl 完整的ZPL
     */
    public boolean print(String zpl){
        if(printService==null){
            message = "打印路勁：沒有找到打印機["+printerURI+"]";
            return false;
        }
        DocPrintJob job = printService.createPrintJob();
        byte[] by = zpl.getBytes();
        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        Doc doc = new SimpleDoc(by, flavor, null);
        try {
            job.print(doc, null);
            message = "已打印";
            return true;
        } catch (PrintException e) {
            message = "打印信息:"+e.getMessage();
            e.printStackTrace();
            return false;
        }
    }
    public String getMessage(){
        return message;
    }
    /**
     * 字符串?byte[]
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