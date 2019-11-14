package mes_sfis.client.model.service;

import base.enums.DataSourceType;
import base.vo.UI_InitVO;
import mes_sfis.client.model.bean.DataLoadBean;
import mes_sfis.client.task.PassDeviceTask;
import mes_sfis.client.util.DataHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.Vector;

/**
 * Created by Pino_Gao on 2018/7/11.
 */
public class ISRADataLoadService extends BaseService {
    private static final Logger logger = LogManager.getLogger(PassDeviceTask.class);
    UI_InitVO uiVO;
    DataHandler dh;
    //DBHelper dh;
    Set<String> set=null;

    public ISRADataLoadService(UI_InitVO uiVO) {
        this.uiVO = uiVO;
        dh = new DataHandler(uiVO);
        //dh = new DBHelper();
    }

    public int insertISRADataLoadList(List<DataLoadBean> lists){
        int a = 0;
        //System.out.println(LoadBean.toString());
        Vector vector=new Vector();
        /*if(LoadBean==null){
            System.out.println("*************************************shibai ");
            return a;
        }*/

        /*String sql = "";
        DataLoadBean bm = null;
        sql+="MERGE into MES_SPC_DATA a\n" +
                "using(";
        for (int i = 0; i < lists.size(); i++){
            bm =  lists.get(i);
            sql+="(select sys_guid() as EID, '"+bm.getBarcode()+"' as ISN , to_date('"+bm.getTimeStamp()+"', 'yyyy-mm-dd hh24:mi:ss') as TIME_STAMP, '"
                    +bm.getSlot()+"' as SLOT, '"+bm.getTemperature()+"' as TEMPERATURE, '"+
                    bm.getStatus()+"' as STATUS,sysdate  as CREATE_TIME, '"+bm.getRownum()+"' as ROW_NUM,"+bm.getMac()+" as MAC)";
            if(i<(lists.size()-1)){
                sql+=" UNION ";
            }
        }
        sql+=") b\n" +
                "on (a.ISN=b.ISN and a.TIME_STAMP=b.TIME_STAMP ) \n" +
                "when matched then \n" +
                "UPDATE SET a.STATUS = b.STATUS\n" +
                "when not matched then  \n" +
                "insert (EID, ISN, TIME_STAMP, " +
                "SLOT, TEMPERATURE, STATUS, CREATE_TIME, " +
                "ROW_NUM, MAC) " +
                "VALUES" +
                "(b.EID, b.ISN, " +
                "b.TIME_STAMP, " +
                "b.SLOT, " +
                "b.TEMPERATURE, " +
                "b.STATUS, " +
                "b.CREATE_TIME, " +
                "b.ROW_NUM, " +
                "b.MAC )\n";*/


        for(int i = 0;i<lists.size();i++){
            DataLoadBean loadBean = lists.get(i);
            String sql = "insert into MES_ISRA_DATALOAD (EID,ISN,TIME_STAMP,SLOT,TEMPERATURE,STATUS,CREATE_TIME,ROW_NUM,MAC) values (sys_guid(),'"+
                    loadBean.getBarcode()+"',(select to_date('"+loadBean.getTimeStamp()+"','yyyy-mm-dd hh24:mi:ss') from dual ),'"+loadBean.getSlot()+"',"+loadBean.getTemperature()+","+loadBean.getStatus()+",sysdate,"+loadBean.getRownum()+",'"+loadBean.getMac()+"')";
            //vector.add(sql);
            try {
                //dh.insert(sql);
                dh.updateData(sql, DataSourceType._SFIS_KAIJIA_STD);
                System.out.println();

                a=1;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("保存失敗");
                a=0;
            }
        }
        /*String sql = "insert into MES_ISRA_DATALOAD (EID,ISN,TIME_STAMP,SLOT,TEMPERATURE,STATUS,CREATE_TIME,ROW_NUM,MAC) values (sys_guid(),'"+
                LoadBean.getBarcode()+"',(select to_date('"+LoadBean.getTimeStamp()+"','yyyy-mm-dd,hh24:mi:ss') from dual ),'"+LoadBean.getSlot()+"',"+LoadBean.getTemperature()+","+LoadBean.getStatus()+",sysdate,"+LoadBean.getRownum()+",'"+LoadBean.getMac()+"')";*/
        //vector.add(sql);

        return a;

    }

    public String getISNByBarcode(String barcode) {
        if(barcode!=""){
            String sql = "SELECT ISN FROM isninfo where '"+barcode+"' in(isn,ssn,sn1,cssn)";
            //dh.getDataOne()
            try {
                //_MultiCompanyCurrentDB
                Hashtable ht = dh.getDataOne(sql, DataSourceType._SFIS_KAIJIA_STD);
                //Hashtable ht = dh.getDataOne(sql, DataSourceType._MultiCompanyCurrentDB);
                if(ht==null){
                    return "";
                }
                String isn = (String) ht.get("ISN");
                System.out.println(isn);
                return isn;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        return "";

    }
    public int findCountNum(String d,String mac) throws Exception {
        //String sql = "select count(CREATE_TIME) AS \"COUNT\" from MES_ISRA_DATALOAD WHERE trunc(CREATE_TIME)=trunc(sysdate)";
        String sql = "select max(ROW_NUM) ROW_NUM from MES_ISRA_DATALOAD WHERE MAC = '"+mac+"' and trunc(CREATE_TIME)=trunc(to_date('"+d+"','yyyyMMdd')) ";
        try {
            Hashtable ht = dh.getDataOne(sql, DataSourceType._SFIS_KAIJIA_STD);
            //Hashtable ht = dh.getDataOne(sql, DataSourceType._MultiCompanyCurrentDB);
            if(ht==null){
                return 1;
            }
            BigDecimal counttmp = (BigDecimal) ht.get("ROW_NUM");
            System.out.println("================="+counttmp);
            if(counttmp==null||counttmp.equals("")){
                return  1;
            }
            int count = counttmp.intValue();
            return count;

        } catch (Exception e) {
            System.out.println("?据异常");
            e.printStackTrace();
            throw new Exception(e.getMessage());

    }

    }
}
