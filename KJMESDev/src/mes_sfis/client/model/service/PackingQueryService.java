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



    //�d�߽c�X�����S���n�d�ߪ��c�X
    public String isBox(String carton_no) {
        String isbox; //�]�m��l�Ȭ�true(�N���q�{�����c�X)
       // String isvalid;
        int isClose,isBreak;
        String sql = "select * from MES_PACK_CARTON t where carton_no = '"+carton_no+"'";//����d��SQL�y�y

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
                    isbox="-�y�{���`-���ʽc,�Э��s�˽c";
                }else {
                    isbox="-�y�{���`-�ʽc�Q�}�a";
                }
                //isbox = isValidCarton(carton_no);//�����c�X
            }else{
                isbox = "false";//�L���c�X

            }
        } catch (Exception e) {

            isbox = "-�t�β��`-�d�߽c�X";

            e.printStackTrace();

            //   throw new Exception("�O�_���c�X�d�߿��~:"+e.getMessage());

        }
        return isbox;

    }



    //�d�ߴ̪O�����S���n�d�ߪ��̪O�X
    public String isPallet(String pallet_no) {
        String ispallet; //�]�m��l�Ȭ�true(�N���q�{�����̪O�X)
        int isClose,isBreak;
        String sql = "select * from MES_PACK_PALLET p where pallet_no = '"+pallet_no+"'";
        // System.out.print("�o�̬O�P�_�O�_�O�̪O�X����k");
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
                    ispallet="-�y�{���`-���ʦ��̪O,�Э��s���O";
                }else {
                    ispallet="-�y�{���`-�Ӵ̪O�w�Q�}�a";
                }
             //   ispallet = isValidPallet(pallet_no);//�����̪O�X
            }else{
                ispallet = "false";//�L���̪O�X
            }
        } catch (Exception e) {
            e.printStackTrace();
            ispallet = "-�t�β��`-�d�ߴ̪O�X";
            //  throw new Exception("�O�_���o�d�߿��~:"+e.getMessage());
        }
        return ispallet;
    }

    //�d�ߴ̪O�����h�ֽc��
    public int zhanbxNumber(String pallet_no) {
        int zhanbxNumber=0; //�]�m��l�Ȭ�true(�N���q�{�����̪O�X)
        String sql = "select CARTON_QTY from MES_PACK_PALLET p where pallet_no = '"+pallet_no+"'";
        try {
            Hashtable ht = (Hashtable) dh.getDataVector(sql, DataSourceType._MultiCompanyCurrentDB).get(1);
            BigDecimal bigDecimal= (BigDecimal) ht.get("CARTON_QTY");
            zhanbxNumber=bigDecimal.intValue();

        } catch (Exception e) {
            e.printStackTrace();
            zhanbxNumber=-1;
            //  throw new Exception("�O�_���o�d�߿��~:"+e.getMessage());
        }
        return zhanbxNumber;
    }

    //�d�ߴ̪O�����h�ֲ��~��
    public int zhanbProductNumber(String pallet_no) {
        int zhanbProductNumber=0; //�]�m��l�Ȭ�true(�N���q�{�����̪O�X)
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
            //  throw new Exception("�O�_���o�d�߿��~:"+e.getMessage());
        }
        return zhanbProductNumber;
    }

    //�d�߽c�����h�ֲ��~��
    public static int xProductNumber(String carton_no) {
        int xProductNumber=0; //�]�m��l�Ȭ�true(�N���q�{�����̪O�X)
        String sql = "select QTY from MES_PACK_CARTON t where CARTON_NO = '"+carton_no+"'";
        try {
            Hashtable ht=dh.getDataOne(sql, DataSourceType._MultiCompanyCurrentDB);

            BigDecimal bigDecimal= (BigDecimal) ht.get("QTY");
            xProductNumber=bigDecimal.intValue();

        } catch (Exception e) {
            e.printStackTrace();
            xProductNumber=-1;
            //  throw new Exception("�O�_���o�d�߿��~:"+e.getMessage());
        }
        //   System.out.println(xProductNumber+"�h�ֲ��~");
        return xProductNumber;
    }

    //�d�߱��y�X�O�_�w�J�M��
    public static int isCreatPick(String carton_no,String strbiao,String strcolumn) {
        int status; //�]�m��l�Ȭ�true(�N���q�{�����̪O�X)
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
    //�d�߱��y�X�O�_�w�J�w
    public static int isWhatPick(String carton_no,String strbiao,String strcolumn) {
        int status; //�]�m��l�Ȭ�true(�N���q�{�����̪O�X)
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
            //  throw new Exception("�O�_���o�d�߿��~:"+e.getMessage());
        }

        return status;
    }


    //�߳f�M��[�J�ƾڮw
    public boolean addPackingNews(PackList packList) {

        boolean isSuccess; //�]�m��l�Ȭ�true(�N���q�{�����̪O�X)
        String sql = "insert into MES_PACK_PICK  values ((select sys_guid() from dual),'"+
                packList.getPickNo()+"','"+packList.getDateCode()+"',"+"NULL"+packList.getCartonQty()+","+packList.getPalletQty()+","+" (select to_date(sysdate(),'yyyy-mm-dd,hh24:mi:ss') from dual ),'"+packList.getLogEmployee()+"',"+packList.getStatus()+",'"+packList.getMemo()+"')";

        try {
            Vector ht=dh.updateData(sql, DataSourceType._MultiCompanyCurrentDB);
            isSuccess=true;

        } catch (Exception e) {
            e.printStackTrace();
            isSuccess=false;//�X�{���`��^false
            //  throw new Exception("�O�_���o�d�߿��~:"+e.getMessage());
        }
        return isSuccess;
    }

    //�b�c��M�̪O���K�[�M��ID
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
