package mes_sfis.client.model.service;

import base.enums.DataSourceType;
import base.vo.UI_InitVO;
import mes_sfis.client.model.bean.PackList;
import mes_sfis.client.util.DataHandler;
import java.math.BigDecimal;
import java.util.*;


/**
 * Created by Mark_Yang on 2018/4/4.
 */
public class PackingQueryService extends BaseService {
    UI_InitVO uiVO;
    static  DataHandler dh;

    private String carton_no;
    public PackingQueryService(UI_InitVO uiVO){
        this.uiVO = uiVO;
        dh = new DataHandler(uiVO);
    }



    //查詢箱碼表里有沒有要查詢的箱碼
    public String isBox(String carton_no) {
        String isbox; //設置初始值為true(代表默認有此箱碼)
       // String isvalid;
        int isClose,isBreak;
        String sql = "select * from MES_PACK_CARTON t where carton_no = '"+carton_no+"'";//執行查詢SQL語句

        try {
            Vector ht =  dh.getDataVector(sql, DataSourceType._MultiCompanyCurrentDB);
            if(ht!=null){
                BigDecimal bigDecimalc= (BigDecimal)((Hashtable) ht.get(1)).get("IS_CLOSE");
                BigDecimal bigDecimalp= (BigDecimal)((Hashtable)ht.get(1)).get("IS_BREAK");
                isClose=bigDecimalc.intValue();
                isBreak=bigDecimalp.intValue();
                if (isBreak==0&&isClose==1){
                    isbox="true";
                }else if (isClose==0){
                    isbox="-流程異常-未封箱,請重新裝箱";
                }else {
                    isbox="-流程異常-封箱被破壞";
                }
                //isbox = isValidCarton(carton_no);//有此箱碼
            }else{
                isbox = "false";//無此箱碼

            }
        } catch (Exception e) {

            isbox = "-系統異常-查詢箱碼";

            e.printStackTrace();

            //   throw new Exception("是否有箱碼查詢錯誤:"+e.getMessage());

        }
        return isbox;

    }



    //查詢棧板表里有沒有要查詢的棧板碼
    public String isPallet(String pallet_no) {
        String ispallet; //設置初始值為true(代表默認有此棧板碼)
        int isClose,isBreak;
        String sql = "select * from MES_PACK_PALLET p where pallet_no = '"+pallet_no+"'";
        // System.out.print("這裡是判斷是否是棧板碼的方法");
        try {
            Vector ht =  dh.getDataVector(sql, DataSourceType._MultiCompanyCurrentDB);
            if(ht!=null){
                BigDecimal bigDecimalc= (BigDecimal)((Hashtable) ht.get(1)).get("IS_CLOSE");
                BigDecimal bigDecimalp= (BigDecimal)((Hashtable)ht.get(1)).get("IS_BREAK");
                isClose=bigDecimalc.intValue();
                isBreak=bigDecimalp.intValue();
                if (isBreak==0&&isClose==1){
                    ispallet="true";
                }else if (isClose==0){
                    ispallet="-流程異常-未封成棧板,請重新打板";
                }else {
                    ispallet="-流程異常-該棧板已被破壞";
                }
             //   ispallet = isValidPallet(pallet_no);//有此棧板碼
            }else{
                ispallet = "false";//無此棧板碼
            }
        } catch (Exception e) {
            e.printStackTrace();
            ispallet = "-系統異常-查詢棧板碼";
            //  throw new Exception("是否報廢查詢錯誤:"+e.getMessage());
        }
        return ispallet;
    }

    //查詢棧板表里有多少箱數
    public int zhanbxNumber(String pallet_no) {
        int zhanbxNumber=0; //設置初始值為true(代表默認有此棧板碼)
        String sql = "select CARTON_QTY from MES_PACK_PALLET p where pallet_no = '"+pallet_no+"'";
        try {
            Hashtable ht = (Hashtable) dh.getDataVector(sql, DataSourceType._MultiCompanyCurrentDB).get(1);
            BigDecimal bigDecimal= (BigDecimal) ht.get("CARTON_QTY");
            zhanbxNumber=bigDecimal.intValue();

        } catch (Exception e) {
            e.printStackTrace();
            zhanbxNumber=-1;
            //  throw new Exception("是否報廢查詢錯誤:"+e.getMessage());
        }
        return zhanbxNumber;
    }

    //查詢棧板表里有多少產品數
    public int zhanbProductNumber(String pallet_no) {
        int zhanbProductNumber=0; //設置初始值為true(代表默認有此棧板碼)
        String sql = "select CARTON_NO from MES_PACK_CARTON t where pallet_oid = (select PALLET_OID from MES_PACK_PALLET p where pallet_no='"+pallet_no+"')";
        try {
            Vector vector=dh.getDataVector(sql,DataSourceType._MultiCompanyCurrentDB);
            if (vector!=null){
                for (int a=1;a<vector.size();a++){
                    Hashtable ht = (Hashtable) vector.get(a);
                    String xCarton_no= (String) ht.get("CARTON_NO");

                    zhanbProductNumber+=xProductNumber(xCarton_no);
                }
            }else {
                zhanbProductNumber=-2;
            }



        } catch (Exception e) {
            e.printStackTrace();
            zhanbProductNumber=-1;
            //  throw new Exception("是否報廢查詢錯誤:"+e.getMessage());
        }
        return zhanbProductNumber;
    }

    //查詢箱表里有多少產品數
    public static int xProductNumber(String carton_no) {
        int xProductNumber=0; //設置初始值為true(代表默認有此棧板碼)
        String sql = "select QTY from MES_PACK_CARTON t where CARTON_NO = '"+carton_no+"'";
        try {
            Hashtable ht=dh.getDataOne(sql, DataSourceType._MultiCompanyCurrentDB);

            BigDecimal bigDecimal= (BigDecimal) ht.get("QTY");
            xProductNumber=bigDecimal.intValue();

        } catch (Exception e) {
            e.printStackTrace();
            xProductNumber=-1;
            //  throw new Exception("是否報廢查詢錯誤:"+e.getMessage());
        }
        //   System.out.println(xProductNumber+"多少產品");
        return xProductNumber;
    }

    //查詢掃描碼是否已入清單
    public static int isCreatPick(String carton_no,String strbiao,String strcolumn) {
        int status; //設置初始值為true(代表默認有此棧板碼)
        String sql =  "select PICK_OID from "+strbiao+" where "+strcolumn+"='"+carton_no+"'";
        try {

            Hashtable ht=dh.getDataOne(sql, DataSourceType._MultiCompanyCurrentDB);

            if ((String)ht.get("PICK_OID")!=null&&(String)ht.get("PICK_OID")!=""){
                status=isWhatPick(carton_no,strbiao,strcolumn);

            }else {
                status=-2;
            }

        } catch (Exception e) {
            e.printStackTrace();
            status=-1;

        }

        return status;
    }
    //查詢掃描碼是否已入庫
    public static int isWhatPick(String carton_no,String strbiao,String strcolumn) {
        int status; //設置初始值為true(代表默認有此棧板碼)
        String sql = "select t.STATUS from MES_PACK_PICK t where PICK_OID =  (select PICK_OID from "+strbiao+" where "+strcolumn+"='"+carton_no+"')";
        try {

            Hashtable ht=dh.getDataOne(sql, DataSourceType._MultiCompanyCurrentDB);

            BigDecimal bigDecimal= (BigDecimal)ht.get("STATUS");
            status=bigDecimal.intValue();
            if (status!=0&&status!=1){
                status=-1;
            }



        } catch (Exception e) {
            e.printStackTrace();
            status=-1;
            //  throw new Exception("是否報廢查詢錯誤:"+e.getMessage());
        }

        return status;
    }


    //撿貨清單加入數據庫
    public boolean addPackingNews(PackList packList) {

        boolean isSuccess; //設置初始值為true(代表默認有此棧板碼)
        String sql = "insert into MES_PACK_PICK  values ((select sys_guid() from dual),'"+
                packList.getPickNo()+"','"+packList.getDateCode()+"',"+"NULL"+packList.getCartonQty()+","+packList.getPalletQty()+","+" (select to_date(sysdate(),'yyyy-mm-dd,hh24:mi:ss') from dual ),'"+packList.getLogEmployee()+"',"+packList.getStatus()+",'"+packList.getMemo()+"')";

        try {
            Vector ht=dh.updateData(sql, DataSourceType._MultiCompanyCurrentDB);
            isSuccess=true;

        } catch (Exception e) {
            e.printStackTrace();
            isSuccess=false;//出現異常返回false
            //  throw new Exception("是否報廢查詢錯誤:"+e.getMessage());
        }
        return isSuccess;
    }

    //在箱表和棧板表中添加清單ID
    public boolean addPick_OID(PackList packList, String strc, String strp, List list){
        boolean isSuccess;
        String sql = "INSERT INTO MES_PACK_PICK (PICK_OID,PICK_NO,DATE_CODE,CARTON_QTY,PALLET_QTY,LOG_SYSTEMDATE,LOG_EMPLOYEE_OID,STATUS) values ( sys_guid(),'"+
                packList.getPickNo()+"','"+packList.getDateCode()+"',"+packList.getCartonQty()+","+packList.getPalletQty()+","+"SYSDATE"+",'"+packList.getLogEmployee()+"',"+packList.getStatus()+")";
        Map<Object,String> mapc= (Map<Object, String>) list.get(0);
        Map<Object,String> mapp= (Map<Object, String>) list.get(1);


        Vector vector=new Vector();
        vector.add(sql);
        for (int i=1;i<packList.getCartonQty()+1;i++){
            vector.add("UPDATE "+strc+" set PICK_OID= (select PICK_OID from MES_PACK_PICK where PICK_NO='"+packList.getPickNo()+"')" +"WHERE CARTON_NO='"+mapc.get(i)+"'");
        }

        for (int i=1;i<packList.getPalletQty()+1;i++){
            vector.add("UPDATE "+strp+" set PICK_OID= (select PICK_OID from MES_PACK_PICK where PICK_NO='"+packList.getPickNo()+"')" +"WHERE PALLET_NO='"+mapp.get(i)+"'");

        }

        try {

            Vector vct=dh.updateData(vector,DataSourceType._MultiCompanyCurrentDB);
            isSuccess=true;

        } catch (Exception e) {
            e.printStackTrace();
            isSuccess=false;
        }
        return  isSuccess;
    }



}
