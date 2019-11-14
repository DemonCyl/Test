package mes_sfis.client.model.service;

import base.enums.DataSourceType;
import base.vo.UI_InitVO;
import mes_sfis.client.util.DataHandler;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by Srx_Zhu on 2018/09/08.
 */
public class BadnessWarehouseQueryService extends BaseService {
    UI_InitVO uiVO;
    DataHandler dh;

    public BadnessWarehouseQueryService(UI_InitVO uiVO) {
        this.uiVO = uiVO;
        dh = new DataHandler(uiVO);
    }

    public List<Hashtable> QueryByWarehouse() {
        List<Hashtable> list = new ArrayList<>();

        return list;
    }

    public List<Hashtable> QueryByTime(String s, int status, String beginTime, String endTime, String error, String route, String device) {
        List<Hashtable> list = new ArrayList<>();
        String sql = "";
        if (!beginTime.equals("") && !endTime.equals("")) {
            sql = "select cp.warehouse_name," +
                    "       ir.storage_id," +
                    "       cp.carton_id," +
                    "       cp.error," +
                    "       cp.route," +
                    "       cp.device," +
                    "       ir.put_in_time," +
                    "       ir.put_in_user," +
                    "       ir.put_out_time," +
                    "       ir.put_out_user," +
                    "       ir.emp_oid" +
                    "  from Mes_Total_Warehouse tw" +
                    "  join MES_CARTON_PT cp on tw.warehouse_name = cp.warehouse_name" +
                    "  join MES_INOUT_RECORD ir on cp.carton_id = ir.carton_id" +
                    "  where tw.warehouse_name='" + s + "' " +
                    "and cp.status=" + status + "" +
                    "and put_in_time between to_date('" + beginTime + "','yyyy/MM/dd hh24:mi:ss') and to_date('" + endTime + "','yyyy/MM/dd hh24:mi:ss')";
        } else {
            sql = "select cp.warehouse_name," +
                    "       ir.storage_id," +
                    "       cp.carton_id," +
                    "       cp.error," +
                    "       cp.route," +
                    "       cp.device," +
                    "       ir.put_in_time," +
                    "       ir.put_in_user," +
                    "       ir.put_out_time," +
                    "       ir.put_out_user," +
                    "       ir.emp_oid" +
                    "  from Mes_Total_Warehouse tw" +
                    "  join MES_CARTON_PT cp on tw.warehouse_name = cp.warehouse_name" +
                    "  join MES_INOUT_RECORD ir on cp.carton_id = ir.carton_id" +
                    "  where tw.warehouse_name='" + s + "' " +
                    "  and cp.status=" + status + "";
        }
        if (!error.equals("--") && route.equals("--") && device.equals("--")) {
            sql += " and cp.error='" + error + "'";
        }
        if (!error.equals("--") && !route.equals("--") && device.equals("--")) {
            sql += " and cp.error = '" + error + "' and cp.route = '" + route + "'";
        }
        if (!error.equals("--") && !route.equals("--") && !device.equals("--")) {
            sql += " and cp.error = '" + error + "' and cp.route = '" + route + "' and cp.device = '" + device + "'";
        }
        if (error.equals("--") && !route.equals("--") && device.equals("--")) {
            sql += " and cp.route = '" + route + "'";
        }
        if (error.equals("--") && !route.equals("--") && !device.equals("--")) {
            sql += " and cp.route = '" + route + "' and cp.device = '" + device + "'";
        }
        sql += " order by carton_id";
        try {
            list = dh.getDataList(sql, DataSourceType._SFIS_KAIJIA_STD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Hashtable> getSumConfigOfTime(String s, int status, String beginTime, String endTime, String error, String route, String device) {
        List<Hashtable> list = new ArrayList<>();
        String sql = "";
        if (!beginTime.equals("") && !endTime.equals("")) {
            sql = "select distinct cp.carton_id,tw.sum" +
                    "  from Mes_Total_Warehouse tw" +
                    "  join MES_CARTON_PT cp on tw.warehouse_name = cp.warehouse_name" +
                    "  join mes_inout_record ir on ir.carton_id=cp.carton_id" +
                    " where tw.warehouse_name='" + s + "' and  cp.status = " + status + " and put_in_time between to_date('" + beginTime + "','yyyy/MM/dd hh24:mi:ss') and to_date('" + endTime + "','yyyy/MM/dd hh24:mi:ss')";
        } else {
            sql = "select distinct cp.carton_id,cp.sum" +
                    "  from Mes_Total_Warehouse tw" +
                    "  join MES_CARTON_PT cp on tw.warehouse_name = cp.warehouse_name" +
                    "  join mes_inout_record ir on ir.carton_id=cp.carton_id" +
                    " where tw.warehouse_name='" + s + "' and  cp.status = " + status + "";
        }
        if (!error.equals("--") && route.equals("--") && device.equals("--")) {
            sql += " and cp.error='" + error + "'";
        }
        if (!error.equals("--") && !route.equals("--") && device.equals("--")) {
            sql += " and cp.error = '" + error + "' and cp.route = '" + route + "'";
        }
        if (!error.equals("--") && !route.equals("--") && !device.equals("--")) {
            sql += " and cp.error = '" + error + "' and cp.route = '" + route + "' and cp.device = '" + device + "'";
        }
        if (error.equals("--") && !route.equals("--") && device.equals("--")) {
            sql += " and cp.route = '" + route + "'";
        }
        if (error.equals("--") && !route.equals("--") && !device.equals("--")) {
            sql += " and cp.route = '" + route + "' and cp.device = '" + device + "'";
        }
        try {
            list = dh.getDataList(sql, DataSourceType._SFIS_KAIJIA_STD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Hashtable> QueryByStorage(String jc1, String jc2, String jt1, String beginTime, String endTime, String error, String route, String device) {
        List<Hashtable> list = new ArrayList<>();
        String sql = "";
        if (!beginTime.equals("") && !endTime.equals("")) {
            sql = "select cp.warehouse_name," +
                    "       ir.storage_id," +
                    "       cp.carton_id," +
                    "       cp.error," +
                    "       cp.route," +
                    "       cp.device," +
                    "       cp.sum," +
                    "       ir.put_in_time," +
                    "       ir.put_in_user," +
                    "       ir.put_out_time," +
                    "       ir.put_out_user," +
                    "       ir.emp_oid" +
                    "  from Mes_Total_Warehouse tw" +
                    "  join MES_CARTON_PT cp on tw.warehouse_name = cp.warehouse_name" +
                    "  join MES_INOUT_RECORD ir on cp.carton_id = ir.carton_id" +
                    "  join Mes_Storage_Pt sp on sp.storage_id = cp.storage_id" +
                    " where tw.warehouse_name = '" + jc1 + "'" +
                    "   and sp.storage_name='" + jt1 + "'" +
                    "   and ir.storage_id='" + jt1 + "'" +
                    "   and cp.status = " + jc2 + "" +
                    "   and ir.put_in_time between to_date('" + beginTime + "','yyyy/MM/dd hh24:mi:ss') and to_date('" + endTime + "','yyyy/MM/dd hh24:mi:ss')";
        } else {
            sql = "select cp.warehouse_name," +
                    "       ir.storage_id," +
                    "       cp.carton_id," +
                    "       cp.error," +
                    "       cp.route," +
                    "       cp.device," +
                    "       cp.sum," +
                    "       ir.put_in_time," +
                    "       ir.put_in_user," +
                    "       ir.put_out_time," +
                    "       ir.put_out_user," +
                    "       ir.emp_oid" +
                    "  from Mes_Total_Warehouse tw" +
                    "  join MES_CARTON_PT cp on tw.warehouse_name = cp.warehouse_name" +
                    "  join MES_INOUT_RECORD ir on cp.carton_id = ir.carton_id" +
                    "  join Mes_Storage_Pt sp on sp.storage_id = cp.storage_id" +
                    " where tw.warehouse_name = '" + jc1 + "'" +
                    "   and sp.storage_name='" + jt1 + "'" +
                    "   and ir.storage_id='" + jt1 + "'" +
                    "   and cp.status = " + jc2 + "";
        }
        if (!error.equals("--") && route.equals("--") && device.equals("--")) {
            sql += " and cp.error='" + error + "'";
        }
        if (!error.equals("--") && !route.equals("--") && device.equals("--")) {
            sql += " and cp.error = '" + error + "' and cp.route = '" + route + "'";
        }
        if (!error.equals("--") && !route.equals("--") && !device.equals("--")) {
            sql += " and cp.error = '" + error + "' and cp.route = '" + route + "' and cp.device = '" + device + "'";
        }
        if (error.equals("--") && !route.equals("--") && device.equals("--")) {
            sql += " and cp.route = '" + route + "'";
        }
        if (error.equals("--") && !route.equals("--") && !device.equals("--")) {
            sql += " and cp.route = '" + route + "' and cp.device = '" + device + "'";
        }
        sql += " order by carton_id";
        try {
            list = dh.getDataList(sql, DataSourceType._SFIS_KAIJIA_STD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Hashtable> QueryByCarton(String text,String jc2) {
        List<Hashtable> list = new ArrayList<>();
        String sql = "select cp.carton_id,cp.warehouse_name,cp.sum,ip.isn,ip.cssn,cp.error,cp.route,cp.device,ir.storage_id,ir.put_in_time,ir.put_out_time,ir.put_in_user,ir.put_out_user,ir.emp_oid" +
                "  from MES_CARTON_PT CP" +
                "  join MES_ISN_PT ip on cp.carton_id = ip.carton_id" +
                "  join MES_INOUT_RECORD ir on cp.carton_id = ir.carton_id" +
                "  join MES_STORAGE_PT sp on sp.storage_id =cp.storage_id" +
                " where cp.carton_id = '" + text + "'" +
                "   and cp.status = " + jc2 + "" +
                "  order by ir.put_in_time desc";
        try {
            list = dh.getDataList(sql, DataSourceType._SFIS_KAIJIA_STD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Hashtable> QueryByISN(String text) {
        List<Hashtable> list = new ArrayList<>();
        String sql = "select cp.carton_id," +
                "       cp.warehouse_name," +
                "       ir.storage_id," +
                "       ip.isn," +
                "       ip.cssn," +
                "       cp.error," +
                "       cp.device," +
                "       cp.route," +
                "       ir.put_in_time," +
                "       ir.put_out_time," +
                "       ir.put_in_user," +
                "       ir.put_out_user," +
                "       ir.emp_oid" +
                "  from MES_ISN_PT ip" +
                "  join MES_CARTON_PT cp on ip.carton_id = cp.carton_id" +
                "  join MES_INOUT_RECORD ir on ir.carton_id = cp.carton_id" +
                " where ip.isn = '" + text + "' or ip.cssn='" + text + "'" +
                " order by ir.put_in_time";
        try {
            list = dh.getDataList(sql, DataSourceType._SFIS_KAIJIA_STD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Hashtable> getAllError() {
        List<Hashtable> list = new ArrayList<>();
        String sql = "select  distinct ERROR from MES_CARTON_PT";
        try {
            list = dh.getDataList(sql, DataSourceType._SFIS_KAIJIA_STD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
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

    public List<Hashtable> getAllDevice(String s) {
        List<Hashtable> list = new ArrayList<>();
        String sql = "select stepnm from ROUTE_STEP where route = '" + s + "' and rtype3 ='A'";
        try {
            list = dh.getDataList(sql, DataSourceType._SFIS_KAIJIA_STD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
