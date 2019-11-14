package mes_sfis.client.model.service;

import base.enums.DataSourceType;
import base.vo.UI_InitVO;
import base.vo.URL_VO;
import mes_sfis.client.util.DataHandler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

/**
 * Created by Haifeng_Zhou on 2018/4/11.
 */
public class ShipMentService extends BaseService{
    static UI_InitVO uiVO;
    DataHandler dh;


    public ShipMentService(UI_InitVO uiVO) {
        this.uiVO = uiVO;
        dh = new DataHandler(uiVO);
    }
    //鏈接數據庫
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
    //查詢此通知單 是否以出庫
    public static  int  ship(String order) {
        DataHandler dh = new DataHandler(uiVO);
        int ht=0;
        try {
            String sql="select * from MES_PACK_SHIP where ERP_SHIP_OID='"+order+"'";
            ht = dh.getDataOne(sql, DataSourceType._MultiCompanyCurrentDB).size();
        } catch (Exception e) {
            ht=0;
        }
        return ht;
    }

    //回去需要出貨的物品信息
    public  static List<Hashtable> itemInformation(String order){
        DataHandler dh = new DataHandler(uiVO);
        List<Hashtable>  ht = null;
        //orderID 出貨清單； ogb04 鎧嘉料號；ogb11 客戶料號；ogb12 需取貨數量；oga032 廠家名稱
        String sql = "select '"+order+"' as orderID,b.ogb04,b.ogb11,b.ogb12,a.oga032,a.oga01 from ogb_file_rs b join oga_file_rs a on  a.oga01=b.ogb01  where ogb01='"+order+"'";
        try {
            ht = dh.getDataVector(sql,DataSourceType._TIPTOP_KAIJIA_TOPPROD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  ht;
    }
    //驗證棧板 打箱信息并回填數量
    public static int CARTON(String CARTON_NO,String OEMPN) {
        DataHandler dh = new DataHandler(uiVO);
        String sql = "select sum(QTY) as QTY from MES_PACK_CARTON where CARTON_NO='" + CARTON_NO + "' and OEMPN='" + OEMPN + "' and IS_CLOSE=1";
        String sql2 ="select sum(cn.QTY)as QTY from MES_PACK_pallet pt join  MES_PACK_CARTON cn on pt.PALLET_OID=cn.PALLET_OID where pt.PALLET_NO='"+CARTON_NO+"'and cn.OEMPN='"+OEMPN+"' and pt.IS_CLOSE=1";
        System.out.println(sql2);
        Hashtable ht = null;
        int qty = 0;
        try {
            if (CARTON_NO.substring(0, 1).equals("I")) {
                ht = dh.getDataOne(sql, DataSourceType._MultiCompanyCurrentDB);
            }else {
                ht = dh.getDataOne(sql2, DataSourceType._MultiCompanyCurrentDB);
            }
            qty =Integer.parseInt( ht.get("QTY").toString());
            System.out.println("xxxx"+qty);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return qty;
    }
    //驗證是否已出庫
    public static Hashtable veriFication  (String  number){
        DataHandler dh = new DataHandler(uiVO);
        Hashtable ht=null;
        String sql=null;
        if (number.substring(0, 1).equals("I")) {
            sql="select is_close,ship_oid  from MES_PACK_CARTON where CARTON_NO='"+number+"'";
        }else{
            sql="select is_close,ship_oid  from MES_PACK_pallet where PALLET_NO='"+number+"'";
        }
        try {
            ht = dh.getDataOne(sql, DataSourceType._MultiCompanyCurrentDB);
            System.out.println(ht.get("IS_CLOSE"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ht;

    }
	
	public static Vector getIsn(String carton){
		Vector vtISN = new Vector();
		DataHandler dh = new DataHandler(uiVO);
		try{
			String sql = "select t.m_sn from mes_pack_carton_isn t,mes_pack_carton c where t.carton_oid = c.carton_oid and c.carton_no = '" + carton + "'";
			vtISN = dh.getDataVector(sql,DataSourceType._MultiCompanyCurrentDB);
		}catch (Exception e) {
            e.printStackTrace();
        }
		return vtISN;
	}

    //插入單頭數據
    public static  List<Hashtable> MES_Pack_Ship(String erp_ship_oid,String project_name,String m_name ){
        System.out.println("zzzzzzz");
        DataHandler dh = new DataHandler(uiVO);
        List<Hashtable> ht=null;
        try {
           /* String sqlCode="select PROJECT_CODE from MES_PACK_CONFIG where KJ_PN='"+project_name+"'";
            Hashtable Code= dh.getDataOne(sqlCode, DataSourceType._MultiCompanyCurrentDB);*/
            String sql="INSERT INTO MES_PACK_SHIP (SHIP_OID,ERP_SHIP_OID,PROJECT_NAME,MANUFACTURERS,LOG_SYSTEMDATE,LOG_EMPLOYEE_OID,STATUS)VALUES(sys_guid(),'"+erp_ship_oid+"','COP1721','"+m_name+"',"+"sysdate,'"+uiVO.getUSERNAME()+"'," + "0"+ ")";
            System.out.println(sql);
            String sql2="select SHIP_OID from MES_PACK_SHIP order by LOG_SYSTEMDATE desc";
            dh.updateData(sql,DataSourceType._MultiCompanyCurrentDB);
            //查詢剛剛插入的SHIP_OID
            ht= dh.getDataVector(sql2, DataSourceType._MultiCompanyCurrentDB);
            System.out.println("xxxxx"+ht);
        } catch (Exception e) {
        }
        return ht;
    }


    //插入單身明細數據
    public static void MES_Pack_Ship_D(String SHIP_OID,List list){
        DataHandler dh = new DataHandler(uiVO);
        Vector SQL=new Vector();
        String sql=null;
        for (int i=0;i<list.size();i++) {
            sql = "INSERT INTO MES_PACK_SHIP_D (SHIP_OID,KJ_PN,CS_PN,PLAN_QTY,ACTUAL_QTY)VALUES('" +SHIP_OID+"','"+list.get(i).toString()+ ")";
            SQL.add(sql);
        }
        try {
            dh.updateData(SQL,DataSourceType._MultiCompanyCurrentDB);
        } catch (Exception e) {

        }

    }
    //對應箱和棧板，錄入SHIP_OID,標記為已出貨（備貨狀態）
    public static void ToMES_PACK(List list,String SHIP_OID){
        DataHandler dh = new DataHandler(uiVO);
        String sql=null;
        Vector SQL=new Vector();
        for (int i=0;i<list.size();i++) {
            System.out.println("ccccxzzz  "+list.get(i).toString().substring(0,1));
            if (list.get(i).toString().substring(0,1).equals("P")){
                sql = "UPDATE MES_PACK_PALLET SET SHIP_OID='"+SHIP_OID+"' where PALLET_OID=(select PALLET_OID from MES_PACK_PALLET where PALLET_NO='"+list.get(i).toString()+"')";
                System.out.println("xxxxx   "+sql);
            }else {
                sql = "UPDATE MES_PACK_CARTON SET  SHIP_OID='" + SHIP_OID+ "'where CARTON_NO='"+ list.get(i).toString()+"'";
            }
            SQL.add(sql);
        }
        try {
            dh.updateData(SQL,DataSourceType._MultiCompanyCurrentDB);
        } catch (Exception e) {

        }

    }

    //取得鎖定的箱號
    public static List<Hashtable> checkLockBox(String number) {
        DataHandler dh = new DataHandler(uiVO);
        List<Hashtable> ht = null;
        String sql = null;
        if (number.substring(0, 1).equals("I")) {
            sql = "SELECT CARTON_NO AS CARTON FROM MES_PACK_CARTON WHERE STATUS = 9 AND CARTON_NO ='" + number + "'";
        } else {
            sql = "SELECT CARTON_NO AS CARTON FROM MES_PACK_CARTON WHERE STATUS = 9 AND PALLET_OID = (SELECT PALLET_OID FROM MES_PACK_PALLET WHERE PALLET_NO =  '" + number + "')";
        }
        try {
            ht = dh.getDataVector(sql, DataSourceType._MultiCompanyCurrentDB);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ht;
    }

    public static void main(String[] args) throws Exception{
//        URL CODEBASE = new URL("http://127.0.0.1:8088/PEGAMES/");
//        URL PROXY_SERVER = new URL("http://10.162.244.237:8088/PEGAMES/");
//        URL HTTP_SERVER = new URL("http://127.0.0.1:8088/PEGAMES/");
//        URL FILE_SERVER = new URL("http://10.162.244.237:8088/PEGAMES/**/");
//        URL_VO codeBase = new URL_VO(CODEBASE, PROXY_SERVER, HTTP_SERVER, FILE_SERVER);
//
//        UI_InitVO uiVO2 = new UI_InitVO("zh_TW", "3FC0955A8EF33408E05363F4A20AED66", "3FC0955A8EF23408E05363F4A2-31276", "?Z??q???t??(??{)???????q", "ENAME", codeBase, "USER NAME TEST", "SYSTEM", "?t?κ?z?? ?Z??q???t??(??{)???????q", "3B5B598592A53EB2E05363F4A20A-BU2");
//        DataHandler dh2 = new DataHandler(uiVO2);

        MES_Pack_Ship("925-180300004", "YFM00Y060-103-N", "Apple");
       /* //插入單身明細數據
        ArrayList list =new ArrayList();
        String data=null;
        for (int i = 1; i <2 ; i++) {
            data=1+"','"+2+"',"+3+","+4;
            list.add(data);
        }
        MES_Pack_Ship_D("", list);*/


       /* Hashtable a=veriFication("I19170001815400066");
        System.out.println(a.size());*/
       /* ArrayList cf=new ArrayList();
        cf.add("PI19170001815300095");
        cf.add("I19170001815300036");
        cf.add("I19170001815300016");
        ToMES_PACK(cf,"921-170900009");*/



        /*List<Hashtable> v= itemInformation("921-170900009");

        System.out.println(v.get(1).size());
        for (int i = 1; i <v.size() ; i++) {
            Hashtable aa= v.get(i);
            System.out.println(aa.get("OGB03"));
           //System.out.println(aa.get("OGB03"));
            String[]  result = new String[3];
            String resultName=result.toString();
            for (int j = 0; j <aa.size() ; j++) {
            }*/


    }
}
