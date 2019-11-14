package mes_sfis.client.model.service;

import base.enums.DataSourceType;
import base.vo.UI_InitVO;
import mes_sfis.client.sfis.LogInOutSfisSoap;
import mes_sfis.client.util.DataHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

/**
 * Created by Pino_Gao on 2018/11/12.
 */
public class BadnessResovlePackService {
    private static final Logger logger = LogManager.getLogger(LogInOutSfisSoap.class);
    UI_InitVO uiVO;
    DataHandler dh;

    public BadnessResovlePackService(UI_InitVO uiVO) {
        this.uiVO = uiVO;
        dh = new DataHandler(uiVO);
    }

    public boolean selectAlive(String carton) {
        Hashtable ht = new Hashtable();
        boolean isOk;
        String sql = "select 1 from MES_CARTON_PT where  CARTON_ID='" + carton + "'";
        try {
            ht = dh.getDataOne(sql, DataSourceType._SFIS_KAIJIA_STD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (ht != null) {
            isOk = true;
        } else {
            isOk = false;
        }
        return isOk;
    }

    public int selectStatus(String carton) {
        String sql = "select status as STATUS from MES_CARTON_PT where carton_id = '" + carton + "'";
        Hashtable ht = new Hashtable();
        try {
            ht = dh.getDataOne(sql, DataSourceType._SFIS_KAIJIA_STD);
            if (ht != null) {
                BigDecimal db = (BigDecimal) ht.get("STATUS");
                int result = db.intValue();
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean updateStatus(String carton) {
        String sql = "update MES_CARTON_PT set status = 3 where carton_id = '" + carton + "'";
        try {
            dh.updateData(sql, DataSourceType._SFIS_KAIJIA_STD);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean insertRecord(String carton, String op) {
        String sql = "select w.warehouse_id as WH,p.storage_id as SI from MES_CARTON_PT p join MES_TOTAL_WAREHOUSE w on p.warehouse_name= w.warehouse_name where carton_id='" + carton + "'";
        Hashtable ht = new Hashtable();
        boolean insertOk = false;
        String sqli = "";
        int max = 0;
        try {
            String sqlwh = "select NVL(MAX(SEQ),0) AS  SEQ  from MES_CARTON_ERR_WH where carton_id = '" + carton + "'";
            System.out.println("111111111111111111111");
            Hashtable htSeq = dh.getDataOne(sqlwh, DataSourceType._SFIS_KAIJIA_STD);

            if (htSeq == null) {
                max = 1;
            } else {
                BigDecimal maxTmp = (BigDecimal) htSeq.get("SEQ");
                max = maxTmp.intValue() + 1;
            }

            System.out.println("000000000000000000");
            ht = dh.getDataOne(sql, DataSourceType._SFIS_KAIJIA_STD);
            String wh = null;
            String si = null;
            if (ht != null && ht.containsKey("WH") && ht.containsKey("SI")) {
                wh = ht.get("WH").toString();
                si = ht.get("SI").toString();

                sqli = "insert into MES_CARTON_ERR_WH a (a.warehouse_id,a.storage_id,a.carton_id,a.intime,a.op,a.status,a.seq) values ('" + wh + "','" + si + "','" + carton + "',sysdate,'" + op + "',3," + max + ")";
                System.out.println("2222222222222222222222222222");
                Vector htisn = dh.updateData(sqli, DataSourceType._SFIS_KAIJIA_STD);

                if (htisn != null) {
                    insertOk = true;//ok
                } else {
                    insertOk = false;//¤É¯Å¥¢±Ñ
                }
            }else {
                System.out.println("33333333333333333333");
                sqli ="insert into MES_CARTON_ERR_WH a (a.carton_id,a.intime,a.op,a.status,a.seq) values ('"+carton+"',sysdate,'"+op+"',3,"+max+")";
                Vector htwhi = dh.updateData(sqli, DataSourceType._SFIS_KAIJIA_STD);
                if (htwhi != null) {
                    insertOk =  true;
                }else {
                    insertOk = false;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return insertOk;
    }
}
