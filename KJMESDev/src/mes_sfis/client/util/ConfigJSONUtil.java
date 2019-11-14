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
            System.out.println("�Ыإؿ�" + path + "���ѡA�ؼХؿ��w�s�b");
            return false;
        }
        if (!path.endsWith(File.separator)) {
            path = path + File.separator;
        }
        //?�إ�?
        if (dir.mkdirs()) {
            System.out.println("�Ыإؿ�" + path + "���\�I");
            return true;
        } else {
            System.out.println("�Ыإؿ�" + path + "���ѡI");
            return false;
        }
    }

    public static String getJSON() {
        createDir();
        File file = new File("D:\\mes_data\\config_setting\\PROJECT_CODE_SETTING.JSON");
        String PEOJECT_CODE = "";
        if (file.exists()) {
            System.out.println("�ɮצs�b");
            BufferedReader brname;
            try {
                brname = new BufferedReader(new FileReader("D:\\mes_data\\config_setting\\PROJECT_CODE_SETTING.JSON"));// ?��NAMEID??��
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
            System.out.println("�ɮפ��s�b");
            String project = JOptionPane.showInputDialog(null, "�t�Ϊ�l�ơA�п�J�M�ץN�X" + "\n", "ĵ�i", JOptionPane.PLAIN_MESSAGE);
            generateJSON(project);
            return PEOJECT_CODE;
        }
        return PEOJECT_CODE;
    }


    public static void generateJSON(String ss) {

        System.out.println("�����F");
        String temp="{\""+"PROJECT_CODE"+"\""+":"+"\""+ss+"\""+"}";
        File file = new File("D:\\mes_data\\config_setting\\PROJECT_CODE_SETTING.JSON");
        PrintStream out = null; // ���L�y
        try {
            out = new PrintStream(new FileOutputStream(file)); // ?�Ҥƥ��L�y?�H
            out.print(temp); //��X�y
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (out != null) { // �p�G���L�y��?�šA???���L�y
                out.close();
            }
        }
    }

}
