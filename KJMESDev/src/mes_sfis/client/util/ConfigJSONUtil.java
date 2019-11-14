package mes_sfis.client.util;



import javax.swing.*;
import java.io.*;
import java.util.HashMap;

/**
 * Created by Srx_Zhu on 2018/5/07 0007.
 */
public class ConfigJSONUtil {
    public static String path="D:\\mes_data\\config_setting";

    public static boolean createDir() {
        File dir = new File(path);
        if (dir.exists()) {
            System.out.println("創建目錄" + path + "失敗，目標目錄已存在");
            return false;
        }
        if (!path.endsWith(File.separator)) {
            path = path + File.separator;
        }
        //?建目?
        if (dir.mkdirs()) {
            System.out.println("創建目錄" + path + "成功！");
            return true;
        } else {
            System.out.println("創建目錄" + path + "失敗！");
            return false;
        }
    }

    public static String getJSON() {
        createDir();
        File file = new File("D:\\mes_data\\config_setting\\PROJECT_CODE_SETTING.JSON");
        String PEOJECT_CODE = "";
        if (file.exists()) {
            System.out.println("檔案存在");
            BufferedReader brname;
            try {
                brname = new BufferedReader(new FileReader("D:\\mes_data\\config_setting\\PROJECT_CODE_SETTING.JSON"));// ?取NAMEID??值
                String sname = null;
                String[] shuzu = null;
                HashMap dataMap = new HashMap();
                while ((sname = brname.readLine()) != null) {
                    shuzu = sname.split("\"");
                    if (sname.length() > 1) {
                        dataMap.put(shuzu[1], shuzu[3]);
                    }
                }
                PEOJECT_CODE = (String) dataMap.get("PROJECT_CODE");

                brname.close();
                return PEOJECT_CODE;
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        } else {
            System.out.println("檔案不存在");
            String project = JOptionPane.showInputDialog(null, "系統初始化，請輸入專案代碼" + "\n", "警告", JOptionPane.PLAIN_MESSAGE);
            generateJSON(project);
            return PEOJECT_CODE;
        }
        return PEOJECT_CODE;
    }


    public static void generateJSON(String ss) {

        System.out.println("報錯了");
        String temp="{\""+"PROJECT_CODE"+"\""+":"+"\""+ss+"\""+"}";
        File file = new File("D:\\mes_data\\config_setting\\PROJECT_CODE_SETTING.JSON");
        PrintStream out = null; // 打印流
        try {
            out = new PrintStream(new FileOutputStream(file)); // ?例化打印流?象
            out.print(temp); //輸出流
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (out != null) { // 如果打印流不?空，???打印流
                out.close();
            }
        }
    }

}
