package mes_sfis.client.model.service;

import base.enums.DataSourceType;
import base.vo.UI_InitVO;
import mes_sfis.client.util.DataHandler;

import java.math.BigDecimal;
import java.util.Hashtable;
import java.util.Vector;

/**
 /**
 * Created by Efil_Ding on 2018/4/7.
 */
public class CInWhseService extends  BaseService {
    DataHandler dh;
    public static  int count=1;
    public static  int countall=0;
    public static  int countallend=2;
    private static UI_InitVO uiVO;
    public CInWhseService(UI_InitVO uiVO) {
        this.uiVO = uiVO;
        dh = new DataHandler(uiVO);
    }

    //查出清??。
    public Hashtable CheckInfo(String pallet){
        Hashtable ht=null;
        String sql="SELECT  SFUUD03 FROM SFU_FILE_rs WHERE SFU01='"+pallet+"'";
        //String sql = "SELECT * FROM MES_PACK_CARTON WHERE CATRON_NO = '4'";//執行查詢SQL語句
        System.out.println(sql);
        try {
            ht=dh.getDataOne(sql, DataSourceType._TIPTOP_KAIJIA_TOPPROD);


        } catch (Exception e) {
            e.printStackTrace();

        }

        return ht;
    }
    //根据清??查???信息
    public Hashtable Getinfo(String piclno){
        Hashtable ht=null;
        String sql="SELECT  * FROM MES_PACK_PICK WHERE PICK_NO='"+piclno+"'";
        //  String sql = "SELECT * FROM MES_PACK_CARTON WHERE CATRON_NO = '4'";//執行查詢SQL語句

        try {
            ht=dh.getDataOne(sql, DataSourceType._MultiCompanyCurrentDB);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ht;
    }
    //?算零散箱
    public Hashtable GetBoxNo(String piclno){
        Hashtable ht=null;
        String sql="SELECT COUNT(CARTON_NO) AS CARTON_NUM FROM MES_PACK_CARTON WHERE   PICK_OID='"+piclno+"'";
        try {
            ht= dh.getDataOne(sql,DataSourceType._MultiCompanyCurrentDB);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ht;
    }
    //?算版?的箱
    public Hashtable GetBoxNo_T(String piclno){
        Hashtable ht=null;
        //String sql="SELECT COUNT(CARTON_NO) AS CARTON_NUM FROM MES_PACK_CARTON  PALLET_OID IS  NULL  AND WHERE  PICK_OID='"+piclno+"'";
        String sql="SELECT COUNT(C.CARTON_NO) AS CARTON_NUM FROM MES_PACK_CARTON C JOIN MES_PACK_PALLET P ON C.PALLET_OID=P.PALLET_OID WHERE P.PICK_OID='"+piclno+"'";
        try {
            ht= dh.getDataOne(sql,DataSourceType._MultiCompanyCurrentDB);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ht;
    }
    //?算板的?
    public Hashtable GetPalletNo(String piclno){
        Hashtable ht=null;
        String sql="SELECT COUNT(PALLET_NO) AS PALLET_NUM FROM MES_PACK_PALLET WHERE  PICK_OID='"+piclno+"'";
        try {
            ht= dh.getDataOne(sql,DataSourceType._MultiCompanyCurrentDB);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ht;
    }

    //獲取查驗類型  1自由查驗 2抽查 3全部查驗
    public int projectCheck(){
        Hashtable ht=null;
        int checked=2;
        String sql="SELECT STORAGE_CHECK FROM MES_PACK_PROJECT WHERE PROJECT_CODE='"+"COP1721"+"'";
        try {
              ht=dh.getDataOne(sql,DataSourceType._MultiCompanyCurrentDB);
                BigDecimal bigDecimal= (BigDecimal) ht.get("STORAGE_CHECK");
                checked=bigDecimal.intValue();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return checked;
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

    public void checkAllCount(String palletNo){
        Hashtable ht=null;
        String sql="SELECT CARTON_QTY FROM MES_PACK_PALLET WHERE PALLET_NO='"+palletNo+"'";

        try {
            ht=dh.getDataOne(sql,DataSourceType._MultiCompanyCurrentDB);
            BigDecimal bigDecimal = (BigDecimal) ht.get("CARTON_QTY");
            countallend=bigDecimal.intValue();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    //判??描的每?是不是?于??清??
    public int CheckOkAll(String piclno,String noid,String before){
        System.out.println(countallend+"hehe");

        Hashtable ht=null;
        int key=0;
        //清單號查棧板號
        String sql="SELECT PALLET_NO  FROM MES_PACK_PALLET WHERE PICK_OID=(SELECT PICK_OID  FROM MES_PACK_PICK WHERE PICK_NO='"+piclno+"') AND PALLET_NO='" +noid+"' ";

        //清單號查箱號
        String sql2="SELECT CARTON_NO  FROM MES_PACK_CARTON WHERE PICK_OID=(SELECT PICK_OID  FROM MES_PACK_PICK WHERE PICK_NO='"+piclno+"')  AND CARTON_NO='" +noid+"' ";
        String sql3 = "select * from MES_PACK_CARTON t where carton_no = '"+noid+"'";//執行查詢SQL語句
        //  String sql4="SELECT CARTON_NO  FROM MES_PACK_CARTON WHERE PALLET_OID=(SELECT PALLET_OID  FROM MES_PACK_PALLET WHERE PALLET_NO='"+before+"')";
        //   String sql5="STELEC PALLET_OID  FROM MES_PACK_CARTON WHERE CARTON_NO='"+noid+"'";
        String sql4="SELECT PALLET_NO  FROM MES_PACK_PALLET WHERE PALLET_OID=(SELECT PALLET_OID  FROM MES_PACK_CARTON WHERE CARTON_NO='"+noid+"')";

        try {

            if (before!=null){

                if (countall++<countallend){
                    ht=dh.getDataOne(sql4, DataSourceType._MultiCompanyCurrentDB);
                    if (ht!=null){
                        // System.out.println(ht.get("PALLET_NO")+"2222"+before);
                        if (before.equals(ht.get("PALLET_NO"))){
                            key=3;
                            System.out.println("3");
                        }else {
                            key=4;
                            System.out.println("4");
                        }
                    }else {
                        key=4;
                    }

                }else {
                    // CheckOk(piclno, noid, before);
                    //默認執行行棧板查詢，如果為空則執行箱號查詢
                    ht= dh.getDataOne(sql, DataSourceType._MultiCompanyCurrentDB);
                    if (ht!=null)
                    {
                        key = 1;
                    }else {
                        ht=dh.getDataOne(sql2, DataSourceType._MultiCompanyCurrentDB);
                        if (ht!=null){
                            key = 2;
                        }
                    }
                }


            }else {
                //默認執行行棧板查詢，如果為空則執行箱號查詢
                ht= dh.getDataOne(sql, DataSourceType._MultiCompanyCurrentDB);
                if (ht!=null)
                {
                    key = 1;
                }else {
                    ht=dh.getDataOne(sql2, DataSourceType._MultiCompanyCurrentDB);
                    if (ht!=null){
                        key = 2;
                    }
                }
            }


        } catch (Exception e) {

            e.printStackTrace();
        }

        return key;
    }
    //

    public int CheckOkChou(String piclno,String noid,String before){
        Hashtable ht=null;
        int key=0;
        //清單號查棧板號
        String sql="SELECT PALLET_NO  FROM MES_PACK_PALLET WHERE PICK_OID=(SELECT PICK_OID  FROM MES_PACK_PICK WHERE PICK_NO='"+piclno+"') AND PALLET_NO='" +noid+"' ";

        //清單號查箱號
        String sql2="SELECT CARTON_NO  FROM MES_PACK_CARTON WHERE PICK_OID=(SELECT PICK_OID  FROM MES_PACK_PICK WHERE PICK_NO='"+piclno+"')  AND CARTON_NO='" +noid+"' ";
        String sql3 = "select * from MES_PACK_CARTON t where carton_no = '"+noid+"'";//執行查詢SQL語句
        //  String sql4="SELECT CARTON_NO  FROM MES_PACK_CARTON WHERE PALLET_OID=(SELECT PALLET_OID  FROM MES_PACK_PALLET WHERE PALLET_NO='"+before+"')";
        //   String sql5="STELEC PALLET_OID  FROM MES_PACK_CARTON WHERE CARTON_NO='"+noid+"'";
        String sql4="SELECT PALLET_NO  FROM MES_PACK_PALLET WHERE PALLET_OID=(SELECT PALLET_OID  FROM MES_PACK_CARTON WHERE CARTON_NO='"+noid+"')";

        try {

            if (before!=null){
                if (count++==1){
                    ht=dh.getDataOne(sql4, DataSourceType._MultiCompanyCurrentDB);
                    if (ht!=null){
                        // System.out.println(ht.get("PALLET_NO")+"2222"+before);
                        if (before.equals(ht.get("PALLET_NO"))){
                            key=3;
                            System.out.println("3");
                        }else {
                            key=4;
                            System.out.println("4");
                        }
                    }else {
                        key=4;
                    }

                }else {
                    CheckOk(piclno, noid, before);
                }


            }else {
                //默認執行行棧板查詢，如果為空則執行箱號查詢
                ht= dh.getDataOne(sql, DataSourceType._MultiCompanyCurrentDB);
                if (ht!=null)
                {
                    key = 1;
                }else {
                    ht=dh.getDataOne(sql2, DataSourceType._MultiCompanyCurrentDB);
                    if (ht!=null){
                        key = 2;
                    }
                }
            }


        } catch (Exception e) {

            e.printStackTrace();
        }

        return key;
    }
    //判??描的每?是不是?于??清??
    public int CheckOk(String piclno,String noid,String before){
        Hashtable ht=null;
        int key=0;
        //清單號查棧板號
        String sql="SELECT PALLET_NO  FROM MES_PACK_PALLET WHERE PICK_OID=(SELECT PICK_OID  FROM MES_PACK_PICK WHERE PICK_NO='"+piclno+"') AND PALLET_NO='" +noid+"' ";

        //清單號查箱號
        String sql2="SELECT CARTON_NO  FROM MES_PACK_CARTON WHERE PICK_OID=(SELECT PICK_OID  FROM MES_PACK_PICK WHERE PICK_NO='"+piclno+"')  AND CARTON_NO='" +noid+"' ";
        String sql3 = "select * from MES_PACK_CARTON t where carton_no = '"+noid+"'";//執行查詢SQL語句
        //  String sql4="SELECT CARTON_NO  FROM MES_PACK_CARTON WHERE PALLET_OID=(SELECT PALLET_OID  FROM MES_PACK_PALLET WHERE PALLET_NO='"+before+"')";
        //   String sql5="STELEC PALLET_OID  FROM MES_PACK_CARTON WHERE CARTON_NO='"+noid+"'";
        String sql4="SELECT PALLET_NO  FROM MES_PACK_PALLET WHERE PALLET_OID=(SELECT PALLET_OID  FROM MES_PACK_CARTON WHERE CARTON_NO='"+noid+"')";

        try {
            //默認執行行棧板查詢，如果為空則執行箱號查詢
            ht= dh.getDataOne(sql, DataSourceType._MultiCompanyCurrentDB);
            if (ht!=null)
            {
                key = 1;
            }else {
                ht=dh.getDataOne(sql2, DataSourceType._MultiCompanyCurrentDB);
                if (ht!=null){
                    key = 2;
                }else {
                    //   System.out.println(before+"000");
                    if (before!=null){
                        ht=dh.getDataOne(sql3, DataSourceType._MultiCompanyCurrentDB);
                        //  System.out.println(ht+"11111");
                        if (ht!=null){
                            ht=dh.getDataOne(sql4, DataSourceType._MultiCompanyCurrentDB);
                            // System.out.println(ht.get("PALLET_NO")+"2222"+before);
                            if (before.equals(ht.get("PALLET_NO"))){
                                key=3;
                                System.out.println("3");
                            }else {
                                key=4;
                                System.out.println("4");
                            }

                        }
                    }
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return key;
    }
    //驗證完畢

    public  boolean LastCheck(String pick,String pallet) {

        String sql = "UPDATE MES_PACK_PICK SET STORAGE_ID = '" + pallet + "', STATUS='1',LAST_SYSTEMDATE=SYSDATE WHERE PICK_NO='"+pick+"'";
        Vector a=null;


        try {
            a= dh.updateData(sql,DataSourceType._MultiCompanyCurrentDB);

            if (a==null){
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

   /* public static void main(String[] args) {
        System.out.println("....");
    }*/
}
