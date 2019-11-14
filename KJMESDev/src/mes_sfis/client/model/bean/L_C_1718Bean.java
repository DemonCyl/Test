package mes_sfis.client.model.bean;

import base.enums.DataSourceType;
import base.vo.UI_InitVO;
import mes_sfis.client.task.PassDeviceTask;
import mes_sfis.client.util.DataHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Hashtable;
import java.util.Vector;

/**
 * Created by Srx_Zhu on 2018/07/19.
 */
public class L_C_1718Bean  extends BaseBean{
    private static final Logger logger = LogManager.getLogger(PassDeviceTask.class);
    private String dateCode;

    public String getDateCode() {
        return dateCode;
    }

    public void setDateCode(String dateCode) {
        this.dateCode = dateCode;
    }


    public String getL_CEnd(String projectCode) throws Exception{
        String L_C="N/A";
        Vector arg = new Vector();
        arg.add(projectCode);
        Hashtable row = dh.selectOneHashTable("MES_PACK_SYSTEM","getCartonNo",arg);
        this.setDateCode((String)row.get("DATE_CODE"));
        L_C=dateCode+(String)row.get("THIS_SN");
        return L_C;
    }


    public void insertRecord(String l_c_no,String employee,UI_InitVO uivo){
        String sql="insert into sfis_lable_1718_record(L_C_NO,systemtime,employee)values('"+l_c_no+"',sysdate,'"+employee+"')";
        DataHandler dh  =  new DataHandler(uivo);
        try {
            dh.updateData(sql, DataSourceType._MultiCompanyCurrentDB);
        } catch (Exception e) {
            logger.debug("´¡¤J°O¿ý¥¢±Ñ"+e.getMessage());
            e.printStackTrace();
        }
    }

    public Hashtable getL_CStart(String oem_pn,UI_InitVO uivo){
        String sql ="select VENDOR_CODE,SITE_CODE,PART_NO,PART_NAME,REV,CONFIG from  SFIS_1718_TRACE_CODE where OEM_PN='"+oem_pn+"'";
        Hashtable ht  = new Hashtable();
        DataHandler dh  =  new DataHandler(uivo);
        try {
            ht=dh.getDataOne(sql, DataSourceType._MultiCompanyCurrentDB);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ht;
    }
	
	public Hashtable getL_CStart1901(String oem_pn,UI_InitVO uivo){
        String sql ="select VENDOR_CODE,SITE_CODE,PART_NO,PART_NAME,REV,CONFIG from  SFIS_1901_TRACE_CODE where OEM_PN='"+oem_pn+"'";
        Hashtable ht  = new Hashtable();
        DataHandler dh  =  new DataHandler(uivo);
        try {
            ht=dh.getDataOne(sql, DataSourceType._MultiCompanyCurrentDB);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ht;
    }

    public Hashtable getL_CStartD5X(String oem_pn,UI_InitVO uivo){
        String sql ="select VENDOR_CODE,SITE_CODE,PART_NO,PART_NAME,REV,CONFIG from  SFIS_D5X_TRACE_CODE where OEM_PN='"+oem_pn+"'";
        Hashtable ht  = new Hashtable();
        DataHandler dh  =  new DataHandler(uivo);
        try {
            ht=dh.getDataOne(sql, DataSourceType._MultiCompanyCurrentDB);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ht;
    }
}
