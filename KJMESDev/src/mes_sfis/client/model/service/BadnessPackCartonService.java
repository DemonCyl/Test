package mes_sfis.client.model.service;

import base.enums.DataSourceType;
import base.vo.UI_InitVO;
import mes_sfis.client.sfis.LogInOutSfisSoap;
import mes_sfis.client.util.DataHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Pino_Gao on 2018/9/4.
 */
public class BadnessPackCartonService extends BaseService {

    private static final Logger logger = LogManager.getLogger(LogInOutSfisSoap.class);
    UI_InitVO uiVO;
    DataHandler dh;

    public BadnessPackCartonService(UI_InitVO uiVO) {
        this.uiVO = uiVO;
        dh = new DataHandler(uiVO);
    }


    public boolean findAliveIsn(String isn) {
        String sql = "select 1 from isninfo where isn='" + isn + "'";
        Hashtable ht = null;
        try {
            ht = dh.getDataOne(sql, DataSourceType._SFIS_KAIJIA_STD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (ht == null) {
            return false;
        } else {
            return true;
        }
    }


    public boolean insertCartonPT(String op, ListModel listIsn, DefaultListModel listModel2, String packetId, String date,String error,String device,String route) {
        boolean insertOk = true;
        String sql = "insert into MES_CARTON_PT (CARTON_ID,SUM,STATUS,EMP_OID,CREATETIME,ERROR,DEVICE,ROUTE)" +
                "values (" + packetId + "," + listIsn.getSize() + ",2,'" + op + "',to_date('" + date + "','YYYY/MM/DD HH24:MI:ss'),'"+error+"','"+device+"','"+route+"')";

        try {
            Vector ht = dh.updateData(sql, DataSourceType._SFIS_KAIJIA_STD);
            if (ht != null) {
                insertOk = true;//ok
                String sqlwh = "select NVL(MAX(SEQ),0) AS  SEQ  from MES_CARTON_ERR_WH where carton_id = '"+packetId+"'";
                Hashtable htSeq = dh.getDataOne(sqlwh, DataSourceType._SFIS_KAIJIA_STD);
                int max = 0;
                if(htSeq==null){
                    System.out.println("zheshi   null");
                    max = 1;
                }else {
                    System.out.println("zheshi *****  null");
                    BigDecimal maxTmp = (BigDecimal) htSeq.get("SEQ");
                    max = maxTmp.intValue()+1;
                }
                String sqlwhi = "insert into MES_CARTON_ERR_WH a (a.carton_id,a.intime,a.op,a.status,a.seq) values ('"+packetId+"',sysdate,'"+op+"',2,"+max+")";
                Vector htwhi = dh.updateData(sqlwhi, DataSourceType._SFIS_KAIJIA_STD);
                if(htwhi==null){
                    return false;
                }
                //插入所有isn?据
                for (int i = 0; i < listIsn.getSize(); i++) {
                    String isn = listIsn.getElementAt(i).toString();
                    String sqlIsn = "insert into MES_ISN_PT(ISN,CSSN,CARTON_ID) values('" + isn + "','" + listModel2.getElementAt(i) + "','" + packetId + "')";
                    Vector htisn = dh.updateData(sqlIsn, DataSourceType._SFIS_KAIJIA_STD);
                    if (htisn != null) {
                        insertOk = true;//ok
                    } else {
                        insertOk = false;//升級失敗
                    }
                }


            } else {
                insertOk = false;//升級失敗
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return insertOk;
    }

    public int findPacketStatus(String isn) {
        String sql = "select count(*) as COUNTNUM    from MES_ISN_PT isn join MES_CARTON_PT carton on isn.carton_id = carton.carton_id where   (carton.STATUS=1 or carton.STATUS=2)  and  isn.isn = '" + isn + "'";
        try {
            Hashtable ht = dh.getDataOne(sql, DataSourceType._SFIS_KAIJIA_STD);
            if (ht == null) {
                return 0;
            }
            BigDecimal a = (BigDecimal) ht.get("COUNTNUM");
            int result = a.intValue();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String getCSSNByISN(String isn) {
        String sql = "select CSSN from ISNINFO where ISN='" + isn + "'";
        Hashtable ht = new Hashtable();
        try {
            ht = dh.getDataOne(sql, DataSourceType._SFIS_KAIJIA_STD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (ht == null) {
            return "";
        } else if (ht.get("CSSN") == null) {
            return "";
        } else {
            return ht.get("CSSN").toString();
        }
    }

    public List<Hashtable> getAllDevice(String route) {
        List<Hashtable> list = new ArrayList<>();
        String sql = "select STEPNM from ROUTE_STEP where route like '" + route + "' and RIDX<1000 ";
        try {
            list = dh.getDataList(sql, DataSourceType._SFIS_KAIJIA_STD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public String getErrorName(String text) {
        String sql = "select ERRNM from ERROR where ERRID ='" + text + "'";
        Hashtable ht = new Hashtable();
        try {
            ht = dh.getDataOne(sql, DataSourceType._SFIS_KAIJIA_STD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (ht == null) {
            return "";
        } else {
            return ht.get("ERRNM").toString();
        }
    }

    public List<Hashtable> getAllRoute() {
        List<Hashtable> list = new ArrayList<>();
        String sql = "SELECT ROUTE FROM ROUTE WHERE ROUTESTAT = 'N'";
        try {
            list = dh.getDataList(sql, DataSourceType._SFIS_KAIJIA_STD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Hashtable checkISNError(String text) {
        String sql = "select ERROR,STEP from SNERR where status = '0' AND SN = '" + text + "'";
        Hashtable ht = new Hashtable();
        try {
            ht = dh.getDataOne(sql, DataSourceType._SFIS_KAIJIA_STD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ht;
    }

    public String CheckISNStep(String route, String ISN) {
        String sql = "select STEP from mo_d where ISN='" + ISN + "'";
        Hashtable ht = new Hashtable();
        try {
            ht = dh.getDataOne(sql, DataSourceType._SFIS_KAIJIA_STD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String sql2 = "select stepnm from ROUTE_STEP where route = '" + route + "' and RIDX<1000 and step='" + ht.get("STEP") + "'";
        Hashtable ht2 = new Hashtable();
        try {
            ht2 = dh.getDataOne(sql2, DataSourceType._SFIS_KAIJIA_STD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (ht2 == null) {
            return "";
        } else {
            return ht2.get("STEPNM").toString();
        }

    }

    public String CheckISNRoute(String text) {
        String sql="select route from mo_d where  ISN= '"+text+"'";
        Hashtable ht = new Hashtable();
        try {
            ht = dh.getDataOne(sql,DataSourceType._SFIS_KAIJIA_STD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(ht==null){
            return "";
        }else{
            return ht.get("ROUTE").toString();
        }
    }

    public String getIsnByOther(String ssn) {
        String sql = "select isn as ISN from isninfo where '"+ssn+"' in (isn,ssn,sn1,cssn)";
        Hashtable ht = new Hashtable();
        try {
            ht = dh.getDataOne(sql,DataSourceType._SFIS_KAIJIA_STD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(ht==null){
            return "";
        }else{
            return ht.get("ISN").toString();
        }
    }
}
