package mes_sfis.client.model.service;

import base.enums.DataSourceType;
import base.vo.UI_InitVO;
import mes_sfis.client.util.DataHandler;
import mes_sfis.client.util.MESGlobe;

import java.util.Hashtable;

/**
 * Created by Haifeng_Zhou on 2018/4/11.
 */
public class COPService extends BaseService {
    UI_InitVO uiVO;
    DataHandler dh;
    String path = "/resources/DistinguishBinSql.txt";

    public COPService(UI_InitVO uiVO) {
        this.uiVO = uiVO;
        dh = new DataHandler(uiVO);
    }

    //鏈接數據庫
/*
    public static UI_InitVO jdbc() {
        URL CODEBASE, PROXY_SERVER, HTTP_SERVER, FILE_SERVER;
        URL_VO codeBase;
        UI_InitVO ov = null;
        try {
            CODEBASE = new URL("http://127.0.0.1:8088/PEGAMES/");
            PROXY_SERVER = new URL("http://10.162.244.107:8088/PEGAMES/");
            HTTP_SERVER = new URL("http://127.0.0.1:8088/PEGAMES/");
            FILE_SERVER = new URL("http://10.162.244.107:8088/PEGAMES");
            codeBase = new URL_VO(CODEBASE, PROXY_SERVER, HTTP_SERVER, FILE_SERVER);
            ov = new UI_InitVO("zh_TW", "3FC0955A8EF33408E05363F4A20AED66", "3FC0955A8EF23408E05363F4A2-31276", "鎧嘉電腦配件(蘇州)有限公司", "ENAME", codeBase, "USER NAME TEST", "SYSTEM", "系統管理者 鎧嘉電腦配件(蘇州)有限公司", "3B5B598592A53EB2E05363F4A20A-BU2");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return ov;
    }
*/

    //查詢此通知單 是否以出庫
    public Hashtable getThreeCode(String inputdata) throws Exception {
        Hashtable ht = null;
            String sql = "SELECT II.ISN,                                       "+
                    "       II.CSSN,                                      "+
                    "       II.SSN,                                       "+
                    "       SN1,                                          "+
                    "       SNA,                                          "+
                    "       SNB,                                          "+
                    "       SNC,                                          "+
                    "       SND,                                          "+
                    "       SNE,                                          "+
                    "       SNF,                                          "+
                    "       SNG                                           "+
                    "  FROM ISNINFO II WHERE '"+inputdata+"' IN (II.ISN,II.SSN,II.CSSN)";
            ht = dh.getDataOne(sql, DataSourceType._SFIS_KAIJIA_STD);
        return ht;
    }
    public String getOQC2Badgoods(String inputdata){
        String result="";
        Hashtable ht=null;
        String sql="select count(*) as OK from mo_route where isn='"+inputdata+"' and device in('1721010059','1721010060')";
        try {
            ht = dh.getDataOne(sql, DataSourceType._SFIS_KAIJIA_STD);
            result=  ht.get("OK").toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public Hashtable getAllData(String SSN, String ISN, String CSSN) {
        Hashtable ht = null;
        try {
            String sql = MESGlobe.getQuerySql("ISN_STATUS_ALL");
            ht = dh.getDataOne(sql, DataSourceType._SFIS_KAIJIA_STD);
        } catch (Exception e) {
            ht = null;
        }
        return ht;
    }


}
