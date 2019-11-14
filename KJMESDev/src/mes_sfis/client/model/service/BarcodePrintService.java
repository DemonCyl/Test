package mes_sfis.client.model.service;

import base.enums.DataSourceType;
import base.vo.UI_InitVO;
import mes_sfis.client.sfis.LogInOutSfisSoap;
import mes_sfis.client.util.DataHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * Created by Pino_Gao on 2018/8/29.
 */
public class BarcodePrintService extends BaseService {
    private static final Logger logger = LogManager.getLogger(LogInOutSfisSoap.class);
    UI_InitVO uiVO;
    DataHandler dh;
    public BarcodePrintService(UI_InitVO uiVO) {
        this.uiVO = uiVO;
        dh = new DataHandler(uiVO);
    }


    public boolean saveDataPrint(HashMap<String, String> printMap) {
        boolean insertOk = false;
        String sql = "insert into tp.mes_measuration_print (print_oid,pro_level,floor,line_num,clip_num,mea_type,hole,machine_num,mce_num,create_time,classnum,flow_code,full_code)" +
                "values (sys_guid(),'"+
                printMap.get("PRO_LEVEL")+"','"+
                printMap.get("FLOOR")+"','"+
                printMap.get("LINE_NUM")+"','"+
                printMap.get("CLIP_NUM")+"','"+
                printMap.get("MEA_TYPE")+"','"+
                printMap.get("HOLE")+"','"+
                printMap.get("MACHINE_NUM")+"','"+
                printMap.get("MCE_NUM")+"',to_date('"+
                printMap.get("CREATE_TIME")+"','YYYY/MM/DD HH24:MI:ss'),'"+
                printMap.get("CLASSNUM")+"','"+
                printMap.get("FLOW_NUM")+"','"+
                printMap.get("FULL_CODE")+"')";

        try {
            Vector vector = dh.updateData(sql, DataSourceType._MultiCompanyCurrentDB);
            if(vector!=null){
                insertOk = true;
            }else {
                insertOk = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return insertOk;
    }

    public String findFlowNumA(String day) {
        /*String sql = "select flow_code as FLOW_CODE from tp.mes_measuration_print " +
                "where create_time = (select max(a.create_time) " +
                "from (select * from tp.mes_measuration_print where create_time " +
                "between " +
                "to_date('"+day+" 8:00:00','YYYY-MM-DD HH24:MI:ss') " +
                "and " +
                "to_date('"+day+" 20:00:00','YYYY-MM-DD HH24:MI:ss')) a)";*/
        String sql = "select flow_code as FLOW_CODE from (select * from tp.mes_measuration_print where create_time between to_date('"+day+" 8:00:00','YYYY-MM-DD HH24:MI:ss') and to_date('"+day+" 20:00:00','YYYY-MM-DD HH24:MI:ss') order by flow_code desc ) a where rownum = 1";
        Hashtable ht=null;
        try{
            ht=dh.getDataOne(sql,DataSourceType._MultiCompanyCurrentDB);
            if(ht!=null){
                String flow_num = ht.get("FLOW_CODE").toString();
                return flow_num;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public List<Hashtable> findMODELID() {
        String sql ="select MODELID from MODEL order by MODELID";
        try{
            List<Hashtable> dataList = dh.getDataList(sql, DataSourceType._MultiCompanyCurrentDB);

            return dataList;

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public String findFlowNumB(String yestday, String today) {
        System.out.println("---------------"+yestday);
        System.out.println("---------------"+today);

        /*String sql = "select flow_code as FLOW_CODE from tp.mes_measuration_print " +
                "where create_time = (select max(a.create_time) " +
                "from (select * from tp.mes_measuration_print where create_time " +
                "between " +
                "to_date('"+yestday+" 20:00:00','YYYY-MM-DD HH24:MI:ss') " +
                "and " +
                "to_date('"+today+" 8:00:00','YYYY-MM-DD HH24:MI:ss')) a)";*/
        String sql = "select flow_code as FLOW_CODE from (select * from tp.mes_measuration_print where create_time between to_date('"+yestday+" 20:00:00','YYYY-MM-DD HH24:MI:ss') and to_date('"+today+" 8:00:00','YYYY-MM-DD HH24:MI:ss') order by flow_code desc ) a where rownum = 1";
        Hashtable ht=null;
        try{
            ht=dh.getDataOne(sql,DataSourceType._MultiCompanyCurrentDB);
            if(ht!=null){
                String flow_num = ht.get("FLOW_CODE").toString();
                System.out.println("/////////////////////"+flow_num);
                return flow_num;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;

    }

    public List<Hashtable> findClipNums() {
        String sql ="select clip_num as CLIP,cell as CELL,node as NODE from mes_cnc_clip where SITE_NUM = 'A' ORDER BY CLIP";
        try{
            List<Hashtable> dataList = dh.getDataList(sql, DataSourceType._MultiCompanyCurrentDB);

            return dataList;

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public List<Hashtable> findCell() {
        String sql ="select distinct cell as CELL from mes_cnc_clip order by cell";
        try{
            List<Hashtable> dataList = dh.getDataList(sql, DataSourceType._MultiCompanyCurrentDB);

            return dataList;

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public List<Hashtable> findMeasureType() {
        String sql =" SELECT MEASURE_TYPE FROM MES_SPC_MEASURE_TYPE where type = '1' ORDER BY RIDX";
        try{
            List<Hashtable> dataList = dh.getDataList(sql, DataSourceType._MultiCompanyCurrentDB);

            return dataList;

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public List<Hashtable> findFloor() {
        String sql ="select distinct(floor) as FLOOR from mes_cnc_clip order by floor";
        try{
            List<Hashtable> dataList = dh.getDataList(sql, DataSourceType._MultiCompanyCurrentDB);

            return dataList;

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
