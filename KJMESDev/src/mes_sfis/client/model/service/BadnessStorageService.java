package mes_sfis.client.model.service;

import base.enums.DataSourceType;
import base.vo.UI_InitVO;
import base.vo.URL_VO;
import mes_sfis.client.util.DataHandler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Xiaojian1_Yu on 2018/7/17.
 */
public class BadnessStorageService extends BaseService {
    DataHandler dh;
    Set<String> set = null;
    String oidHt = null;
    private static UI_InitVO uiVO;

    public BadnessStorageService(UI_InitVO uiVO) {
        this.uiVO = uiVO;
        dh = new DataHandler(uiVO);
    }

    //����x��H������k
    public Hashtable getSTORAGENews(String storagename) {
        Hashtable oid =  new Hashtable();
        String oidsql = "select * from MES_STORAGE_PT where STORAGE_NAME='"+storagename+"'";
        try {
            oid = dh.getDataOne(oidsql, DataSourceType._SFIS_KAIJIA_STD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return oid;
    }

    //�󴫭ק��x��B�Ӽh�B�ɧO
    public boolean Change_STORAGENews(String storagename,String floor,String building,String oldname) {
        boolean result = true;
        String sql = "UPDATE MES_STORAGE_PT SET STORAGE_NAME='"+storagename+"',FLOOR='"+floor+"',BUILDING='"+building+"' where STORAGE_NAME='"+oldname+"'";
        System.out.println(sql);
        try {
            Vector ht = dh.updateData(sql, DataSourceType._SFIS_KAIJIA_STD);

            return   true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //�ק��x��]�ק�e�^�J�w�O���H�����K�[�X�w�H��
    public boolean AddSTORAGEIn(String username,String oldname) {
        boolean result = true;
        String sql = "UPDATE  MES_INOUT_RECORD SET PUT_OUT_TIME=sysdate,PUT_OUT_USER='"+username+"' where STORAGE_ID='"+oldname+"' and PUT_OUT_TIME is null";
        System.out.println(sql);
        try {
            Vector ht = dh.updateData(sql, DataSourceType._SFIS_KAIJIA_STD);

            return   true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //�d��ק��x�쪺�]�J�w���A�^���c�X�J�w�O���H��
    public Vector FindSTORAGENews(String oldname) {
        boolean result = true;
        String sql = "select * from  MES_INOUT_RECORD  where STORAGE_ID='"+oldname+"' and PUT_OUT_TIME is null ";
        Vector ht=null;
        try {
            ht = dh.getDataVector(sql, DataSourceType._SFIS_KAIJIA_STD);
            return   ht;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //�s�W�ק���x��J�w�O��
    public boolean AddSTORAGEMark(Vector vector,String storagename,String floor,String building,String inname) {
        boolean result = true;
        Vector arg = new Vector();
        for (int i=1;i<vector.size();i++) {
            Hashtable hashtable= (Hashtable) vector.get(i);
            String sql="insert into MES_INOUT_RECORD VALUES ('" + hashtable.get("WAREHOUSE_ID") + "','" + storagename+ "','" + hashtable.get("CARTON_ID") + "',sysdate,null,'"
                    + inname + "',null,'" + inname +"')";

            arg.add(sql);
        }
        try {
            dh.updateData(arg, DataSourceType._SFIS_KAIJIA_STD);

            return   true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


/*  public static void main(String[] args)throws Exception {
        URL CODEBASE = new URL("http://127.0.0.1:8088/PEGAMES/");
        URL PROXY_SERVER = new URL("http://10.162.244.237:8088/PEGAMES/");
        URL HTTP_SERVER = new URL("http://127.0.0.1:8088/PEGAMES/");
        URL FILE_SERVER = new URL("http://10.162.244.237:8088/PEGAMES*//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//*");
        URL_VO codeBase = new URL_VO(CODEBASE, PROXY_SERVER, HTTP_SERVER, FILE_SERVER);

        UI_InitVO uiVO2 = new UI_InitVO("zh_TW", "3FC0955A8EF33408E05363F4A20AED66", "3FC0955A8EF23408E05363F4A2-31276", "�Z�Źq���t��(Ĭ�{)�������q", "ENAME", codeBase, "USER NAME TEST", "SYSTEM", "�t�κ޲z�� �Z�Źq���t��(Ĭ�{)�������q", "3B5B598592A53EB2E05363F4A20A-BU2");
        DataHandler dh2 = new DataHandler(uiVO2);

       // findDeviceLine();
        String sql = "select * from MES_STORAGE_PT where STORAGE_NAME='"+777+"'";
        Hashtable a = null;
        try {
            a = dh2.getDataOne(sql, DataSourceType._MultiCompanyCurrentDB);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(a.get("STORAGE_NAME"));
    }*/


}
