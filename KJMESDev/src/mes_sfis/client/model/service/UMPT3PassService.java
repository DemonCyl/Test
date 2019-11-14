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
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Pino_Gao on 2018/10/10.
 */
public class UMPT3PassService extends BaseService {
    private static final Logger logger = LogManager.getLogger(LogInOutSfisSoap.class);
    UI_InitVO uiVO;
    DataHandler dh;
    public UMPT3PassService(UI_InitVO uiVO) {
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

    public List<String> LogInOut(String op, String device, String status){
        List<String> resultList = new ArrayList<>();
        List list = null;
        SoapUtil su = null;
        try {
            su = new SoapUtil();
        } catch (SOAPException e) {
            e.printStackTrace();
        }
        LogInOutSfisSoap loc = null;
        try {
            loc = new LogInOutSfisSoap();
        } catch (SOAPException e) {
            e.printStackTrace();
        }
        loc.setOp(op);
        loc.setDevice(device);
        loc.setStatus(status);
        try {
            list = loc.createAndSendSOAPRequest(su);
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
            logger.debug("回傳值0");
            String resultStr = (String) list.get(1);
            logger.debug("resultStr:"+resultStr);
            if(!resultStr.startsWith("Login Twice!")){

            }else{
                logger.debug("還在登入狀態");
            }
        }
        su.close();
        return resultList;
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
            logger.debug("過站失敗:"+list.get(1));
        }
        su.close();
        return resultList;
    }

    public List<String> RepairSfisSoap(String ISN,String Device){
        List<String> resultList = new ArrayList<>();
        SoapUtil su = null;
        try {
            su = new SoapUtil();
        } catch (SOAPException e) {
            e.printStackTrace();
        }
        RepairSfisSoap rss = null;
        try {
            rss = new RepairSfisSoap();
        } catch (SOAPException e) {
            e.printStackTrace();
        }
        rss.setISN(ISN);
        rss.setDEVICE(Device);
        List list = null;
        try {
            list = rss.createAndSendSOAPRequest(su);
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
        su.close();
        return resultList;
    }

    public int checkISNOk(String isn){

        String sql ="select COUNT(*) as COUNTNUM from snerr  where status = '0' and sn = '"+isn+"' and ERROR='ERR231'";
        Hashtable a = null;
        try {
            a = dh.getDataOne(sql, DataSourceType._SFIS_KAIJIA_STD);
            if(a!=null){
                BigDecimal c = (BigDecimal)a.get("COUNTNUM");
                int result = c.intValue();
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    public static void main(String[] args)throws Exception {
        URL CODEBASE = new URL("http://127.0.0.1:8088/PEGAMES/");
        URL PROXY_SERVER = new URL("http://10.162.244.237:8088/PEGAMES/");
        URL HTTP_SERVER = new URL("http://127.0.0.1:8088/PEGAMES/");
        URL FILE_SERVER = new URL("http://10.162.244.237:8088/PEGAMES/**/");
        URL_VO codeBase = new URL_VO(CODEBASE, PROXY_SERVER, HTTP_SERVER, FILE_SERVER);

        UI_InitVO uiVO2 = new UI_InitVO("zh_TW", "3FC0955A8EF33408E05363F4A20AED66", "3FC0955A8EF23408E05363F4A2-31276", "鎧嘉電腦配件(蘇州)有限公司", "ENAME", codeBase, "USER NAME TEST", "SYSTEM", "系統管理者 鎧嘉電腦配件(蘇州)有限公司", "3B5B598592A53EB2E05363F4A20A-BU2");
        DataHandler dh2 = new DataHandler(uiVO2);
        OQC1PassService oqc1PassService = new OQC1PassService(uiVO2);
        List<String> list = new ArrayList<>();
        list = oqc1PassService.PassDeviceSfisSoap("ERR001","999048");
        for(String str:list){
            System.out.println(str);
        }
    }

    public String getIsn(String ssn) {
        String sql ="select isn as ISN from isninfo where '"+ssn+"' in (isn,ssn,sn1)";
        Hashtable a = null;
        try {
            a = dh.getDataOne(sql, DataSourceType._SFIS_KAIJIA_STD);
            if(a!=null){
                String isn = a.get("ISN").toString();
                return isn;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getRepiarCount(String isn) {
        String sql ="select COUNT(*) as COUNTNUM from repair where error = 'ERR231' AND SN = '"+isn+"'";
        Hashtable a = null;
        try {
            a = dh.getDataOne(sql, DataSourceType._SFIS_KAIJIA_STD);
            if(a!=null){
                BigDecimal c = (BigDecimal)a.get("COUNTNUM");
                int result = c.intValue();
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}