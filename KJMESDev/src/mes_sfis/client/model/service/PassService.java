package mes_sfis.client.model.service;

import base.enums.DataSourceType;
import base.vo.UI_InitVO;
import base.vo.URL_VO;
import mes_sfis.client.sfis.LogInOutSfisSoap;
import mes_sfis.client.sfis.PassDeviceSfisSoap;
import mes_sfis.client.sfis.RepairSfisSoap;
import mes_sfis.client.sfis.SoapUtil;
import mes_sfis.client.util.DataHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.soap.SOAPException;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.*;

/**
 * Created by Srx_Zhu on 2018/08/13.
 */
public class PassService extends BaseService {
    private static final Logger logger = LogManager.getLogger(LogInOutSfisSoap.class);
    UI_InitVO uiVO;
    DataHandler dh;
    public PassService(UI_InitVO uiVO) {
        this.uiVO = uiVO;
        dh = new DataHandler(uiVO);
    }
    public String getErrorName(String errorId){
        String sql ="select * from error where ERRID = '"+errorId+"'";
        Hashtable a = null;
        try {
            a = dh.getDataOne(sql, DataSourceType._SFIS_KAIJIA_STD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(a==null){
            return null;
        }else{
            return a.get("ERRNM").toString();
        }

    }

    public List<String> PassDeviceSfisSoap(String ISN,String Device){
        List<String> resultList = new ArrayList<>();
        SoapUtil su = null;
        try {
            su = new SoapUtil();
        } catch (SOAPException e) {
            e.printStackTrace();
        }
        PassDeviceSfisSoap pdc = null;
        try {
            pdc = new PassDeviceSfisSoap();
        } catch (SOAPException e) {
            e.printStackTrace();
        }
        pdc.setData(ISN);
        pdc.setDevice(Device);
        List list = null;
        try {
            list = pdc.createAndSendSOAPRequest(su);
        } catch (SOAPException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(Iterator i = list.iterator(); i.hasNext();){
            String temp = (String)i.next();
            resultList.add(temp);
            logger.debug(temp);
        }
        if("0".equals(list.get(0))){
            logger.debug("�L������:"+list.get(1));
        }
        su.close();
        return resultList;
    }
    public Hashtable getAllConfig(String code){
        Hashtable a = null;
        String sql = "select  ISN,CSSN,SSN,SNE,SNC  from ISNINFO where isn='"+code+"' or ssn='"+code+"' or cssn='"+code+"'";
        try{
            a=dh.getDataOne(sql,DataSourceType._SFIS_KAIJIA_STD);
        }catch (Exception e){
            e.printStackTrace();
        }
        return a;
    }

    public int checkUmpT3Pass(String isn) {
        String sql = "SELECT COUNT(*) as COUNTNUM FROM MO_D MD WHERE ISN = '"+isn+"' AND SECTION = 'ENG'";
        int flag = 0;
        Hashtable ht = null;
        try{
            ht= dh.getDataOne(sql,DataSourceType._SFIS_KAIJIA_STD);
            if(ht==null){
                return 0;
            }
            BigDecimal a = (BigDecimal) ht.get("COUNTNUM");
            flag = a.intValue();
            return  flag;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;

    }

    public int umpNGCount(String isn) {
        String sql = "select COUNT(*) as UMPNUM from repair where error = 'ERR231' and sn = '"+isn+"' ";
        int flag = 0;
        Hashtable ht = null;
        try{
            ht= dh.getDataOne(sql,DataSourceType._SFIS_KAIJIA_STD);
            if(ht==null){
                return 0;
            }
            BigDecimal a = (BigDecimal) ht.get("UMPNUM");
            flag = a.intValue();
            return  flag;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public int altNGCount(String isn) {
        String sql = "select COUNT(*) as ALTNUM from repair where ERROR IN('ERR225', 'ERR226') and sn = '"+isn+"'";
        int flag = 0;
        Hashtable ht = null;
        try{
            ht= dh.getDataOne(sql,DataSourceType._SFIS_KAIJIA_STD);
            if(ht==null){
                return 0;
            }
            BigDecimal a = (BigDecimal) ht.get("ALTNUM");
            flag = a.intValue();
            return  flag;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public int autoRepair(String isn, String device) {
        String sql = "select count(*) as COUNTNM from mes_auto_repair mar\n" +
                "join snerr se on se.error = mar.errid and mar.grp = se.grp\n" +
                "join device dev on dev.grp = mar.grp\n" +
                "where dev.device = '"+device+"' and se.sn = '"+isn+"'\n" +
                "and se.status = '0'\n";
        Hashtable a = null;
        try {
            a = dh.getDataOne(sql, DataSourceType._SFIS_KAIJIA_STD);
            if(a!=null){
                BigDecimal c = (BigDecimal)a.get("COUNTNM");
                int result = c.intValue();
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    public String findNameByDevice(String device) {
        String sql = "select devicenm as DEVICENM from device where device = '"+device+"'";
        Hashtable a = null;
        String devicenm = null;
        try{
            a = dh.getDataOne(sql, DataSourceType._SFIS_KAIJIA_STD);
            if(a==null){
                return null;
            }
            devicenm = a.get("DEVICENM").toString();

        }catch (Exception e){
            e.printStackTrace();
        }

        return devicenm;
    }
    public Hashtable getDeviceConfig(List<String> mac) {
        Hashtable a = null;
        String sql = "select  MAC,DEVICE,LINK_SNE,LINK_SNF,LINK_MCE,AUTO_REPAIR,SCANNER,RDEVICE,ERRFLAG  from mes_device_config where mac in ( ";
        for(int i =0;i<mac.size();i++){
            sql+="'"+mac.get(i)+"'";
            if(i<mac.size()-1){
                sql+=",";
            }
        }
        sql+=")";
        try{
            a=dh.getDataOne(sql,DataSourceType._SFIS_KAIJIA_STD);
        }catch (Exception e){
            e.printStackTrace();
        }
        return a;
    }

    public boolean insertLogininfo(HashMap<String, String> info) {
        boolean insertOk = false;
        String sql = "insert into tp.log_logininfo (op,device,intime,hostname,message)" +
                "values ('"+
                info.get("OP")+"','"+
                info.get("DEVICE")+"',sysdate,'"+
                info.get("HOSTNAME")+"','"+
                info.get("MESSAGE")+"')";

        try {
            Vector vector = dh.updateData(sql, DataSourceType._SFIS_KAIJIA_STD);
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


    public String getIsn(String SSN) {
        String sql ="select ISN from ISNINFO where  '"+SSN+"' in (sn1,isn,ssn,cssn) ";
        Hashtable a = null;
        try {
            a = dh.getDataOne(sql, DataSourceType._SFIS_KAIJIA_STD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(a==null){
            return SSN;
        }else{
            return a.get("ISN").toString();
        }

    }

    public boolean checkISNOk(String isn){
        String sql = "select 1 from snerr where status = '0' and sn = '"+isn+"' and ERROR in ('ERR225','ERR226')";
        Hashtable ht = null;
        try{
            ht= dh.getDataOne(sql,DataSourceType._SFIS_KAIJIA_STD);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(ht==null){
            return false;
        }else{
            return true;
        }


    }

}
