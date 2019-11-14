package mes_sfis.test;

import base.enums.DataSourceType;
import base.vo.UI_InitVO;
import base.vo.URL_VO;
import mes_sfis.client.model.bean.Product;
import mes_sfis.client.util.DataHandler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * Created by Chris1_Liao on 2018/4/6.
 */
public class DataHandleTest {

    UI_InitVO uiVO;
    public static void main(String[] args) throws Exception {

        DataHandleTest test = new DataHandleTest();
        //test.testInsertData();
        //test.testExecPackage();
        //test.testInsertMass();
        //test.testGetDataOne();
        //test.testGetDataVector();
        test.testInsertMass();
        //test.testGetDataList();


        //test.testBug();
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


    public void testGetDataOne() throws Exception {
        DataHandler dh = new DataHandler(uiVO);

        String sql = "select isn from tp.mo_d t where t.isn = 'GKP8100002UJKKX4X50' ";

        Hashtable ht =  dh.getDataOne(sql, DataSourceType._SFIS_KAIJIA_STD);

        System.out.println(ht.get("ISN"));

    }

    public void testGetDataVector() throws Exception {

        System.out.println("======GetDataVector===================");
        DataHandler dh = new DataHandler(uiVO);

        String sql = "SELECT ISN,DEVICE,INTIME FROM tp.mo_d t WHERE GRP = 'QMF' ";
        sql +=" AND ISN = 'KJ180230119EEEE0XE0'";
        Vector dataVector =  dh.getDataVector(sql, DataSourceType._SFIS_KAIJIA_STD);
        System.out.println("dataVector.size():"+dataVector.size());
        Hashtable ht;
        System.out.println("======取得所有的字段===================");
        Vector test =  (Vector) dataVector.get(0);
        for(int i = 0; i<test.size();i++){
            String temp = (String) test.get(i);
            System.out.println(i+":"+temp);
        }

        System.out.println("======取得所有的數據===================");
        ht = (Hashtable)dataVector.get(1);
        for(int i = 1; i<dataVector.size();i++){
            ht = (Hashtable) dataVector.get(i);
            System.out.println("ISN:"+ht.get("ISN"));
        }


    }

    public void testGetDataList() throws Exception {
        DataHandler dh = new DataHandler(uiVO);
        System.out.println("======GetDataList===================");
        String sql = "SELECT ISN,DEVICE,INTIME FROM tp.mo_d t WHERE GRP = 'QMF' ";
        sql +=" AND ISN = 'KJ180230119EEEE0XE0'";
        List<Hashtable> list =  dh.getDataList(sql, DataSourceType._SFIS_KAIJIA_STD);
        System.out.println("list.size():"+list.size());
        Hashtable ht;
        for(Iterator i =list.iterator();i.hasNext();){

            ht = (Hashtable)i.next();
            System.out.println("ISN:"+ht.get("ISN"));
            System.out.println("DEVICE:"+ht.get("DEVICE"));
            System.out.println("INTIME:"+ht.get("INTIME"));


            System.out.println("======以Enumeration取得所有Hashtable中的KEY,並取得數據===================");
            Enumeration e = ht.keys();

            while(e. hasMoreElements()){
                String s= e.nextElement().toString();
                System.out.println(s+":"+ht.get(s));
            }
        }


    }

    public void testBug(){
        DataHandler dh = new DataHandler(uiVO);
        String sql = "select 1 from MO_ROUTE where GRP = 'QMF' and ISN ='GKP8112000QJKKX4H50'";
        try {
            boolean isTerminalWorking;
            Hashtable ht = dh.getDataOne(sql, DataSourceType._SFIS_KAIJIA_STD);
            if (ht != null) {
                isTerminalWorking = true;//正常
            } else {
                isTerminalWorking = false;//不正常
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void testInsertMassSN() throws Exception {
        String sql = "";
        sql+=" INSERT INTO MES_PACK_CARTON_ISN( ";
        sql+=" CARTON_OID,                  ";
        sql+=" PROJECT_CODE,                ";
        sql+=" KJ_PN,                       ";
        sql+=" CARTON_NO,                   ";
        sql+=" DATE_CODE,                   ";
        sql+=" QTY,                         ";
        sql+=" LOG_SYSTEMDATE,              ";
        sql+=" LOG_EMPLOYEE_OID             ";
        sql+=" )values(                     ";
        sql+=" '$CARTON_OID$',                  ";
        sql+=" 'COP1766',                   ";
        sql+=" 'KJ_PN',                     ";
        sql+=" 'CARTON_NO',                 ";
        sql+=" '1451',                      ";
        sql+=" 123,                         ";
        sql+=" sysdate,                     ";
        sql+=" 'LOG_EMPLOYEE_OID'           ";
        sql+=" )                           ";

        Vector arg = new Vector();

        String cartonOID = "sawsegqwgasdfasdf";
        List<Product> productList = new ArrayList<Product>();
        for(Iterator i =productList.iterator();i.hasNext();){
            Product product = (Product)i.next();
            //sql.replace("$CARTON_OID$",product.getCartonOID);

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
