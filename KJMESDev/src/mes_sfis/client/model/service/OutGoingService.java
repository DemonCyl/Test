package mes_sfis.client.model.service;

import base.enums.DataSourceType;
import base.vo.UI_InitVO;
import mes_sfis.client.model.bean.OutGoingConfig;
import mes_sfis.client.util.DataHandler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

/**
 * Created by Srx_Zhu on 2018/4/11 0011.
 */
public class OutGoingService extends BaseService {
    DataHandler dh;
    UI_InitVO uiVO;

    public OutGoingService(UI_InitVO uiVO) {
        this.uiVO = uiVO;
        dh = new DataHandler(uiVO);
    }

    public OutGoingService() {

    }

    /**
     * @Author:zhurenwei
     * @Description:通過PalletOid獲取棧板所有箱信息列表
     * @Date: 2018/4/14 0014
     */
    public List<Hashtable> getCartonListByPalletOid(String palletOid) throws Exception {
        List<Hashtable> cartonList = new ArrayList();
        String sql = "select * from mes_pack_carton where pallet_oid ='" + palletOid + "'";
        cartonList = dh.getDataList(sql, DataSourceType._MultiCompanyCurrentDB);
        return cartonList;
    }

    /**
     * @Author:zhurenwei
     * @Description:通過ShipOid獲取mes_pack_ship信息
     * @Date: 2018/4/14 0014
     */
    public Vector<OutGoingConfig> getShipConfig(String shipOid) throws Exception {
        String sql = "select mpsd.ship_oid," +
                "  mpsd.kj_pn," +
                "  mpsd.plan_qty," +
                "  mpsd.cs_pn," +
                "  mps.ship_oid," +
                "  mps.erp_ship_oid," +
                "  mps.project_name," +
                "  mps.manufacturers," +
                "  mps.log_systemdate," +
                "  mps.log_employee_oid" +
                "  from Mes_Pack_Ship mps" +
                "  join Mes_Pack_Ship_d mpsd on mps.ship_oid = mpsd.ship_oid" +
                "  where mps.ship_oid =" +
                " '"+shipOid+"'";
        //Hashtable result = dh.getDataOne(sql, DataSourceType._MultiCompanyCurrentDB);
		List listresult = dh.getDataList(sql, DataSourceType._MultiCompanyCurrentDB);
		Vector<OutGoingConfig> vtOut = new Vector<OutGoingConfig>();
		if(listresult != null){
			for(int i = 0;i < listresult.size();i++){
				Hashtable result = (Hashtable)listresult.get(i);
				OutGoingConfig outGoingConfig = new OutGoingConfig();
				if (result.get("SHIP_OID") == null || result.get("SHIP_OID") == "") {
					outGoingConfig.setShipOid("");
				} else {
					outGoingConfig.setKjPn((String) result.get("SHIP_OID"));
				}
				if (result.get("KJ_PN") == null || result.get("KJ_PN") == "") {
					outGoingConfig.setKjPn("");
				} else {
					outGoingConfig.setKjPn((String) result.get("KJ_PN"));
				}
				if (result.get("CS_PN") == null || result.get("CS_PN") == "") {
					outGoingConfig.setCsPn("");
				} else {
					outGoingConfig.setCsPn((String) result.get("CS_PN"));
				}
				if (result.get("PLAN_QTY") == null || result.get("PLAN_QTY") == "") {
					outGoingConfig.setPlanQty(0);
				} else {

					BigDecimal a =(BigDecimal)result.get("PLAN_QTY");
					int b=a.intValue();

					outGoingConfig.setPlanQty(b);
				}
				if (result.get("ACTUAL_QTY") == null || result.get("ACTUAL_QTY") == "") {
					outGoingConfig.setActualQty(0);
				} else {
					outGoingConfig.setActualQty((int) result.get("ACTUAL_QTY"));
				}
				if (result.get("ERP_SHIP_OID") == null || result.get("ERP_SHIP_OID") == "") {
					outGoingConfig.setErpShipOid("");
				} else {
					outGoingConfig.setErpShipOid((String) result.get("ERP_SHIP_OID"));
				}
				if (result.get("ERP_OUT_OID") == null || result.get("ERP_OUT_OID") == "") {
					outGoingConfig.setErpOutOid("");
				} else {
					outGoingConfig.setErpOutOid((String) result.get("ERP_OUT_OID"));
				}
				if (result.get("PROJECT_NAME") == null || result.get("PROJECT_NAME") == "") {
					outGoingConfig.setProjectName("");
				} else {
					outGoingConfig.setProjectName((String) result.get("PROJECT_NAME"));
				}
				if (result.get("MANUFACTURERS") == null || result.get("MANUFACTURERS") == "") {
					outGoingConfig.setManufacturers("");
				} else {
					outGoingConfig.setManufacturers((String) result.get("MANUFACTURERS"));
				}
				if (result.get("LOG_SYSTEMDATE") == null || result.get("LOG_SYSTEMDATE") == "") {
					outGoingConfig.setLogSystemDate(null);
				} else {
					outGoingConfig.setLogSystemDate((Date) result.get("LOG_SYSTEMDATE"));
				}
				if (result.get("LOG_EMPLOYEE_OID") == null || result.get("LOG_EMPLOYEE_OID") == "") {
					outGoingConfig.setLogEmployeeOid("");
				} else {
					outGoingConfig.setLogEmployeeOid((String) result.get("LOG_EMPLOYEE_OID"));
				}
				if (result.get("MEMO") == null || result.get("MEMO") == "") {
					outGoingConfig.setMemo("");
				} else {
					outGoingConfig.setMemo((String) result.get("MEMO"));
				}
				vtOut.add(outGoingConfig);
			}
		}
        return vtOut;
    }

    /**
     * @Author:zhurenwei
     * @Description:通過ShipOid獲取mes_pack_carton信息
     * @Date: 2018/4/14 0014
     */
    public List<Hashtable> getCartonListByShipOid(String shipOid, String KJPN) throws Exception {
        List<Hashtable> a = new ArrayList();
        String sql = "select * from mes_pack_carton where KJ_PN='" + KJPN + "' and pallet_oid is null and ship_oid = '" + shipOid + "'";
        a = dh.getDataList(sql, DataSourceType._MultiCompanyCurrentDB);
        return a;
    }

    public List<Hashtable> getCartonListByShipOid2(String shipOid) throws Exception {
        List<Hashtable> a = new ArrayList();
        String sql = "select * from mes_pack_carton where pallet_oid is null and ship_oid = '" + shipOid + "' order by KJ_PN";
        a = dh.getDataList(sql, DataSourceType._MultiCompanyCurrentDB);
        return a;
    }
	
	public List<Hashtable> getIsnSsn(String carton){
		List<Hashtable> list = new ArrayList();
		String sql = "select t.m_sn,t.s_sn from mes_pack_carton_isn t,mes_pack_carton m where t.carton_oid = m.carton_oid and m.carton_no = '" + carton + "'";
		try{
			list = dh.getDataList(sql, DataSourceType._MultiCompanyCurrentDB);
		}catch (Exception e) {
            e.printStackTrace();
        }
		return list;
	}

    /**
     * @Author:zhurenwei
     * @Description:通過出貨通知單或出貨單獲取ship_oid
     * @Date: 2018/4/14 0014
     */
    public Hashtable ERPIsTrue(String out_going_no) {
        Hashtable ht = null;
        String sql = "SELECT " +
                "OGA01 AS OUT_GOING_NO,OGA011 AS ERP_SHIP_OID,oga02 AS OUT_GOING_DATE,oga03 AS CUST_NO,oga032 AS CUST_NAME,oga16 AS ORDER_NO,oga15 AS DEPT_NO,ogaconf AS CONFIRM_YN,oga55 AS STATUS,oga914,oga908,ogaconf " +
                " FROM oga_file " +
                "WHERE (OGA01 = '" + out_going_no + "'" + "OR OGA011='" + out_going_no + "')" +
                "AND oga09 = 2 and ogaconf='Y'";
        System.out.println(sql);
        try {
            ht = dh.getDataOne(sql, DataSourceType._TIPTOP_KAIJIA_TOPPROD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ht;
    }

    /**
     * @Author:zhurenwei
     * @Description:得到KJ對應棧板的所有信息；
     * @Date: 2018/4/16 0016
     */
    public String getShipOidByERP(String ERPShipOid) {
        String a = "";
        String sql = "select ship_oid from mes_pack_ship where erp_ship_oid='" + ERPShipOid + "'";
        try {
            a = dh.getDataOne(sql, DataSourceType._MultiCompanyCurrentDB).get("SHIP_OID").toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return a;
    }

    /**
     * @Author:zhurenwei
     * @Description:得到KJ對應棧板的所有信息；
     * @Date: 2018/4/16 0016
     */
    public List<Hashtable> getALLConfigByShipOid(String ShipOid) {

        List<Hashtable> ALLConfig = new ArrayList<Hashtable>();
        String sql = " SELECT " +
                "            MPC.KJ_PN, MPC.OEMPN," +
                "            MPC.CARTON_NO,MPC.DATE_CODE," +
                "            MPC.QTY," +
                "            MPP.PALLET_NO," +
                "            MPP.IS_CLOSE," +
                "            MPP.IS_BREAK," +
                "            MPP.STATUS," +
                "            MPP.CARTON_QTY," +
                "            MPP.SHIP_OID," +
                "            MPP.PALLET_OID" +
                "            FROM MES_PACK_PALLET MPP Right" +
                "            " +
                "            JOIN MES_PACK_CARTON MPC ON MPC.PALLET_OID = MPP.PALLET_OID" +
                "           where MPC.ship_oid='" + ShipOid + "'" +
                "            order BY " +
                "            MPC.KJ_PN," +
                "            MPC.CARTON_NO," +
                "            MPC.QTY," +
                "            MPP.PALLET_NO," +
                "            MPP.IS_CLOSE," +
                "            MPP.IS_BREAK," +
                "            MPP.STATUS," +
                "            MPP.CARTON_QTY," +
                "            MPP.SHIP_OID," +
                "            MPP.PALLET_OID" +
                "            --ORDER BY MPC.KJ_PN";
        try {
            ALLConfig = dh.getDataList(sql, DataSourceType._MultiCompanyCurrentDB);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ALLConfig;
    }

    //返回??至MES_PACK_SHIP
    public void Setinfo(String erp_out_oid, String erp_ship_oid) {
        Hashtable ht = null;
        String sql = "UPDATE  MES_PACK_SHIP SET  LAST_SYSTEMDATE=sysdate,STATUS='1' ,ERP_OUT_OID='" + erp_out_oid + "'WHERE ERP_SHIP_OID='" + erp_ship_oid + "'";
        try {
            dh.updateData(sql, DataSourceType._MultiCompanyCurrentDB);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public Vector getIsn(String carton){
		Vector vtISN = new Vector();
		try{
			String sql = "select t.m_sn from mes_pack_carton_isn t,mes_pack_carton c where t.carton_oid = c.carton_oid and c.carton_no = '" + carton + "'";
			vtISN = dh.getDataVector(sql,DataSourceType._MultiCompanyCurrentDB);
		}catch (Exception e) {
            e.printStackTrace();
        }
		return vtISN;
	}
	
}
