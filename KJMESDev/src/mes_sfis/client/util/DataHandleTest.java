package mes_sfis.client.util;

import base.enums.DataSourceType;
import base.vo.UI_InitVO;
import base.vo.URL_VO;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;
import java.util.Vector;

/**
 * Created by Chris1_Liao on 2018/4/6.
 */
public class DataHandleTest {

    UI_InitVO uiVO;
    public static void main(String[] args) throws Exception {

        DataHandleTest test = new DataHandleTest();
        //test.testInsertData();
        //test.testExecPackage();
        test.testInsertMass();
    }

    public DataHandleTest() throws Exception {

        /**
         codeBase : http://127.0.0.1:8080/PEGAMES/
         PROXY_SERVER : http://10.162.244.107:8088/PEGAMES/
         HTTP_SERVER : http://127.0.0.1:8080/PEGAMES/
         FILE_SERVER : http://10.162.244.107:8088/PEGAMES/**/

        URL CODEBASE = new URL("http://127.0.0.1:8080/PEGAMES/");
        URL PROXY_SERVER = new URL("http://10.162.244.107:8088/PEGAMES/");
        URL HTTP_SERVER = new URL("http://127.0.0.1:8080/PEGAMES/");
        URL FILE_SERVER = new URL("http://10.162.244.107:8088/PEGAMES/**/");
        URL_VO codeBase = new URL_VO(CODEBASE,PROXY_SERVER,HTTP_SERVER,FILE_SERVER);

        uiVO = new UI_InitVO ("zh_TW", "3FC0955A8EF33408E05363F4A20AED66", "3FC0955A8EF23408E05363F4A2-31276","鎧嘉電腦配件(蘇州)有限公司", "KJ",  codeBase, "USER NAME TEST", "SYSTEM","系統管理者 鎧嘉電腦配件(蘇州)有限公司", "3B5B598592A53EB2E05363F4A20A-BU2");


    }
    public void testExecPackage() throws Exception {
        Vector arg = new Vector();
        arg.add("COP1721");
        // arg.add("YFM00Y060-103-N");
        arg.add("YFM00Y060-103-N");
        DataHandler dh = new DataHandler(uiVO);
        Vector result = dh.selectVector("MES_PACK_SYSTEM","getCartonSn",arg);
        System.out.println(dh.getMessage());
        Hashtable row = null;
        for(int n = 1;n <= (result.size() - 1);n++){
            row = (Hashtable)result.get(n);
            System.out.println(row.get("THIS_SN"));
        }
    }

    public void testInsertData() throws Exception {
        String sql = "";
        sql+=" INSERT INTO MES_PACK_CARTON( ";
        sql+=" CARTON_OID,                  ";
        sql+=" PROJECT_CODE,                ";
        sql+=" KJ_PN,                       ";
        sql+=" CARTON_NO,                   ";
        sql+=" DATE_CODE,                   ";
        sql+=" QTY,                         ";
        sql+=" LOG_SYSTEMDATE,              ";
        sql+=" LOG_EMPLOYEE_OID             ";
        sql+=" )values(                     ";
        sql+=" sys_guid(),                  ";
        sql+=" 'COP1744',                   ";
        sql+=" 'KJ_PN',                     ";
        sql+=" 'CARTON_NO',                 ";
        sql+=" '1451',                      ";
        sql+=" 123,                         ";
        sql+=" sysdate,                     ";
        sql+=" 'LOG_EMPLOYEE_OID'           ";
        sql+=" )                           ";


        DataHandler dh = new DataHandler(uiVO);
        try {
            Vector result =  dh.updateData(sql, DataSourceType._MultiCompanyCurrentDB);
            System.out.println(dh.getMessage());
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();

            throw new Exception("錯誤信息:"+e.getMessage());
        }

    }

    public void testInsertMass() throws Exception {
        String sql = "";
        sql+=" INSERT INTO MES_PACK_CARTON( ";
        sql+=" CARTON_OID,                  ";
        sql+=" PROJECT_CODE,                ";
        sql+=" KJ_PN,                       ";
        sql+=" CARTON_NO,                   ";
        sql+=" DATE_CODE,                   ";
        sql+=" QTY,                         ";
        sql+=" LOG_SYSTEMDATE,              ";
        sql+=" LOG_EMPLOYEE_OID             ";
        sql+=" )values(                     ";
        sql+=" sys_guid(),                  ";
        sql+=" 'COP1766',                   ";
        sql+=" 'KJ_PN',                     ";
        sql+=" 'CARTON_NO',                 ";
        sql+=" '1451',                      ";
        sql+=" 123,                         ";
        sql+=" sysdate,                     ";
        sql+=" 'LOG_EMPLOYEE_OID'           ";
        sql+=" )                           ";

        Vector arg = new Vector();

        for(int i=0;i<180;i++){
            arg.add(sql);
        }

        DataHandler dh = new DataHandler(uiVO);
        try {
            Vector result =  dh.updateData(arg, DataSourceType._MultiCompanyCurrentDB);
            System.out.println(dh.getMessage());
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();

            throw new Exception("錯誤信息:"+e.getMessage());
        }

    }
}
