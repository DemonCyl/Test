package mes_sfis.client.model.service;

import base.enums.DataSourceType;
import base.vo.UI_InitVO;
import base.vo.URL_VO;
import mes_sfis.client.util.DataHandler;
import oracle.sql.NUMBER;
import org.apache.axis2.dataretrieval.Data;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

/**
 * Created by Srx_Zhu on 2018/09/04.
 */
public class BadnessWarehousePutInOutService {
    UI_InitVO uiVO;
    DataHandler dh;

    public BadnessWarehousePutInOutService(UI_InitVO uiVO) {
        dh = new DataHandler(uiVO);
        this.uiVO = uiVO;
    }

    public Hashtable CheckCartonExist(String carton_no){
        Hashtable ht = new Hashtable();
        String sql ="select CARTON_ID,SUM from MES_CARTON_PT where   CARTON_ID='"+carton_no+"'";
        try {
            ht = dh.getDataOne(sql, DataSourceType._SFIS_KAIJIA_STD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ht;
    }

    public boolean CheckCartonCanPutIn(String carton_no){
        Hashtable ht = new Hashtable();
        boolean isOk;
        String sql ="select 1 from MES_CARTON_PT where (STATUS=1 or STATUS = 3) and CARTON_ID='"+carton_no+"'";
        try {
            ht=dh.getDataOne(sql,DataSourceType._SFIS_KAIJIA_STD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(ht!=null){
            isOk=true;
        }else{
            isOk=false;
        }
        return isOk;
    }

    public boolean CheckCartonCanPutOut(String carton_no){
        Hashtable ht = new Hashtable();
        boolean isOk;
        String sql = "select 1 from MES_CARTON_PT where (STATUS=0 or STATUS=2)  and CARTON_ID='"+carton_no+"'";
        try {
            ht=dh.getDataOne(sql,DataSourceType._SFIS_KAIJIA_STD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(ht!=null){
            isOk=true;
        }else{
            isOk=false;
        }
        return isOk;
    }


    public void PutInCarton(String warehouse_id, String storage_id, String login_id,String carton_oid) {
        String sql2 = "select STORAGE_ID  from MES_STORAGE_PT where STORAGE_NAME='"+storage_id+"'";
        Hashtable ht=new Hashtable();
        try {
            ht=dh.getDataOne(sql2,DataSourceType._SFIS_KAIJIA_STD);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String sql ="update MES_CARTON_PT set WAREHOUSE_NAME='"+warehouse_id+"',STORAGE_ID='"+ht.get("STORAGE_ID")+"',STATUS=1,EMP_OID='"+login_id+"'where CARTON_ID='"+carton_oid+"'";
        try {
            dh.updateData(sql,DataSourceType._SFIS_KAIJIA_STD);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void PutInTotalWarehouse(String warehouse_id, String num, String login_id) {
        String sql ="SELECT SUM as SUMS from MES_TOTAL_WAREHOUSE where WAREHOUSE_NAME='"+warehouse_id+"'";
        Hashtable ht = new Hashtable();
        try {
            ht = dh.getDataOne(sql,DataSourceType._SFIS_KAIJIA_STD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int sum=0;
        sum= Integer.parseInt(ht.get("SUMS").toString())+Integer.parseInt(num);
        String sql2 = "update MES_TOTAL_WAREHOUSE set SUM='"+sum+"',NUM="+num+",UPDATA_TIME=sysdate,UPDATA_USER='"+login_id+"'";
        try {
            dh.updateData(sql2,DataSourceType._SFIS_KAIJIA_STD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void PutInRecord(String warehouse_id, String storage_id, String carton_id, String put_in_user, String login_id) {
        String sql="insert into MES_INOUT_RECORD(WAREHOUSE_ID,STORAGE_ID,CARTON_ID,PUT_IN_TIME,PUT_IN_USER,EMP_OID)values('"+warehouse_id+"','"+storage_id+"','"+carton_id+"',sysdate,'"+put_in_user+"','"+login_id+"')";
        System.out.println(sql);
        try {
            dh.updateData(sql, DataSourceType._SFIS_KAIJIA_STD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Hashtable> CheckUserPermissions(String login_id) {
        String sql="select PERMISSIONS from MES_EMPLOYEES_PERMISSIONS where EMP_OID='"+login_id+"'";
        List<Hashtable> list = new ArrayList<>();
        try {
            list=dh.getDataList(sql,DataSourceType._SFIS_KAIJIA_STD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void CheckStorageExist(String storage_name, String num) {
        String sql ="select SUM from MES_STORAGE_PT where STORAGE_NAME='"+storage_name+"'";
        Hashtable ht = new Hashtable();
        try {
            ht = dh.getDataOne(sql,DataSourceType._SFIS_KAIJIA_STD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String sql2="";
        if(ht==null){
            sql2="insert into MES_STORAGE_PT(STORAGE_ID,STORAGE_NAME,SUM)values(TP.MES_STORAGE_PT_SEQ.nextval,'"+storage_name+"',"+num+")";
        }else{
            int allNum=0;
            allNum=Integer.parseInt(num)+Integer.parseInt(ht.get("SUM").toString());
            sql2="update MES_STORAGE_PT set SUM="+allNum+" where STORAGE_NAME='"+storage_name+"'";
        }
        try {
            dh.updateData(sql2,DataSourceType._SFIS_KAIJIA_STD);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void PutOutCarton(String carton_no) {
        String sql ="update MES_CARTON_PT set STATUS=0 where CARTON_ID='"+carton_no+"'";
        try {
            dh.updateData(sql,DataSourceType._SFIS_KAIJIA_STD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void PutOutTotalWarehouse(String carton_no) {
        String sql="select w.warehouse_name,w.sum,p.sum as num from MES_TOTAL_WAREHOUSE w join MES_CARTON_PT p on w.warehouse_name=p.warehouse_name where p.carton_id='"+carton_no+"'";
        Hashtable ht = new Hashtable();
        try {
            ht=dh.getDataOne(sql,DataSourceType._SFIS_KAIJIA_STD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int allNum=0;
        allNum=Integer.parseInt(ht.get("SUM").toString())-Integer.parseInt(ht.get("NUM").toString());
        String sql2="update MES_TOTAL_WAREHOUSE set sum='"+allNum+"',num='-"+ht.get("NUM")+"',updata_time=sysdate,updata_user='"+uiVO.getLogin_id()+"' where WAREHOUSE_NAME='"+ht.get("WAREHOUSE_NAME")+"'";
        try {
            dh.updateData(sql2,DataSourceType._SFIS_KAIJIA_STD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void PutOutStorage(String carton_no) {
        String sql ="select c.SUM as num,c.STORAGE_ID,s.SUM from MES_CARTON_PT c join MES_STORAGE_PT s on c.storage_id = s.storage_id where c.carton_id='"+carton_no+"'";
        Hashtable ht = new Hashtable();
        try {
            ht = dh.getDataOne(sql,DataSourceType._SFIS_KAIJIA_STD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int allNum=0;
        allNum=Integer.parseInt(ht.get("SUM").toString())-Integer.parseInt(ht.get("NUM").toString());
        String sql2="update MES_STORAGE_PT set SUM="+allNum+"";
        try {
            dh.updateData(sql2,DataSourceType._SFIS_KAIJIA_STD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void PutOutRecord(String carton_no,String userId) {

        String sql ="update MES_INOUT_RECORD set put_out_time=sysdate,put_out_user='"+userId+"'where  carton_id='"+carton_no+"' and put_in_time in(select MAX(put_in_time) from MES_INOUT_RECORD where carton_id='"+carton_no+"')";
        try {
            dh.updateData(sql,DataSourceType._SFIS_KAIJIA_STD);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public int getStorageSum(String storage_name) {
        String sql ="select count(*) as SUMS from (select distinct c.carton_id from (select distinct a.carton_id from MES_CARTON_PT a join MES_ISN_PT b on a.carton_id = b.carton_id where a.status = 1) c join MES_INOUT_RECORD d on c.carton_id =  d.carton_id where d.storage_id='"+storage_name+"') e join MES_ISN_PT f on e.carton_id = f.carton_id";
        Hashtable ht = new Hashtable();
        try {
            ht = dh.getDataOne(sql,DataSourceType._SFIS_KAIJIA_STD);
            if(ht!=null){
                BigDecimal db = (BigDecimal) ht.get("SUMS");
                int result = db.intValue();
                System.out.println("**********************"+result);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int checkSameCartonId(String text, String text1) {
        String sql = "select c.status as STAT from (select * from MES_CARTON_PT a join MES_STORAGE_PT b on a.storage_id = b.storage_id  WHERE a.carton_id = '"+text+"' and b.storage_name = '"+text1+"' order by intime desc) c where  rownum = 1";
        Hashtable ht = null;
        int result = -1;
        try {
            ht = dh.getDataOne(sql, DataSourceType._SFIS_KAIJIA_STD);
            if (ht!=null&&ht.containsKey("STAT")) {
                BigDecimal resultb = (BigDecimal) ht.get("STAT");
                result = resultb.intValue();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean insertRecordInOut(String warhouse, String storage, String carton, String inout,String op) {
        String sql = "SELECT warehouse_id as WH from MES_TOTAL_WAREHOUSE where WAREHOUSE_NAME='"+warhouse+"'";
        String sqlstorage = "select storage_id as SI from MES_STORAGE_PT where storage_name = '"+storage+"'";
        Hashtable ht = null;
        Hashtable htstorage = null;
        Hashtable htinsert = null;
        String whid = "";
        String siid = "";
        String sqli;
        int max = 0;
        boolean result = false;
        try {
            ht = dh.getDataOne(sql, DataSourceType._SFIS_KAIJIA_STD);
            htstorage = dh.getDataOne(sqlstorage, DataSourceType._SFIS_KAIJIA_STD);
            String sqlwh = "select NVL(MAX(SEQ),0) AS  SEQ  from MES_CARTON_ERR_WH where carton_id = '" + carton + "'";
            System.out.println("111111111111111111111");
            Hashtable htSeq = dh.getDataOne(sqlwh, DataSourceType._SFIS_KAIJIA_STD);

            if (htSeq == null) {
                max = 1;
            } else {
                BigDecimal maxTmp = (BigDecimal) htSeq.get("SEQ");
                max = maxTmp.intValue() + 1;
            }
            if (ht.containsKey("WH") ) {
                BigDecimal whidb = (BigDecimal) ht.get("WH");
                whid = String.valueOf(whidb.intValue());
                result =  true;
            }else {
                result = false;
            }
            if (htstorage.containsKey("SI") ) {
                BigDecimal siidb = (BigDecimal) htstorage.get("SI");
                siid = String.valueOf(siidb.intValue());
                System.out.println("/*/*/*/*/*/*/*/*/*/*/*/*/*/*"+siid);
                result =  true;
            }else {
                result = false;
            }
            if(inout.equals("IN")){
                sqli = "insert into MES_CARTON_ERR_WH a (a.warehouse_id,a.storage_id,a.carton_id,a.intime,a.op,a.status,a.seq) values ('" + whid + "','" + siid + "','" + carton + "',sysdate,'" + op + "',1," + max + ")";
            }else {
                sqli = "insert into MES_CARTON_ERR_WH a (a.warehouse_id,a.storage_id,a.carton_id,a.intime,a.op,a.status,a.seq) values ('" + whid + "','" + siid + "','" + carton + "',sysdate,'" + op + "',0," + max + ")";
            }
            Vector htwhi = dh.updateData(sqli, DataSourceType._SFIS_KAIJIA_STD);
            if (htwhi != null) {
                result =  true;
            }else {
                result = false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;

    }
}
