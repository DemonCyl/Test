package mes_sfis.client.model.service;

import base.enums.DataSourceType;
import base.vo.UI_InitVO;
import base.vo.URL_VO;
import mes_sfis.client.sfis.LogInOutSfisSoap;
import mes_sfis.client.sfis.PassDeviceSfisSoap;
import mes_sfis.client.sfis.SoapUtil;
import mes_sfis.client.task.PassDeviceTask;
import mes_sfis.client.task.base.LongTask;
import mes_sfis.client.util.DataHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.soap.SOAPException;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Created by Srx_Zhu on 2018/06/14.
 */
public class OQC2PassService extends BaseService{
    private static final Logger logger = LogManager.getLogger(PassDeviceTask.class);
    UI_InitVO uiVO;
    DataHandler dh;
    Set<String> set=null;

    public OQC2PassService(UI_InitVO uiVO) {
        this.uiVO = uiVO;
        dh = new DataHandler(uiVO);
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
    //用於顯示某個過站的時間點
    /*public  String getStaionTime(String isn){
        String stationTime="SP TO BAND 過站時間：";
        Hashtable sqlResult=null;
        // 需求如下：OQC2過站時顯示  SP TO BAND 的過站時間，目的區分新舊料   以2018.9.28 為時間分界線
        //  TZN 為SP TO BAND  站點
        String  GRP="TZN";
            String sql="select INTIME  from mo_route  where  GRP ='"+GRP+"' and isn ='"+isn+ "' and rownum=1";
        try {
            sqlResult= dh.getDataOne(sql,DataSourceType._SFIS_KAIJIA_STD);
            stationTime+=sqlResult.get("INTIME").toString()+".";
        } catch (Exception e) {
            stationTime+="無.";
            e.printStackTrace();
        }
        return stationTime;
    }

   /* public void StatePass(String device, HashMap map, String op){

        LongTask task=null;
        List list=new ArrayList<>();
        set=map.keySet(); //取出所有的key值
        for (String key:set) {
            list.add(key);
        }
        task = new PassDeviceTask(list.size());
        task.setIsnList(list);
        task.setDevice(device);
        task.setOp(op);
        task.getMessage();
        task.go();
    }


   /* public List passRoute(String device, String isn, String op) throws Exception {

        SoapUtil su = null;
        LogInOutSfisSoap loc = null;
        List list = null;

        try {
            su = new SoapUtil();
        } catch (SOAPException e) {
            e.printStackTrace();
            throw new Exception("遠端連線出錯，請洽MIS");
        }

        try {
            loc = new LogInOutSfisSoap();
        } catch (SOAPException e) {
            e.printStackTrace();
            throw new Exception("遠端登入連線出錯，請洽MIS");
        }
        loc.setOp(op);
        loc.setDevice(device);
        loc.setStatus("1");

        try {
            list = loc.createAndSendSOAPRequest(su);
        } catch (SOAPException e) {
            e.printStackTrace();
            throw new Exception("SOAPException遠端登入連線出錯，請洽MIS");
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("IOException遠端登入連線出錯，請洽MIS");
        }

        if("0".equals(list.get(0))){
            String resultStr = (String) list.get(1);
            if(!resultStr.startsWith("Login Twice!")){
                throw new Exception("登入失敗");
            }
        }

        logger.debug("Passing ISN:"+isn);
        try {
            PassDeviceSfisSoap pdc = new PassDeviceSfisSoap();
            pdc.setData(isn);
            pdc.setDevice(device);
            list = pdc.createAndSendSOAPRequest(su);

            return list;
        } catch (SOAPException e) {
            e.printStackTrace();
            throw new Exception("SOAPException 遠端過站連線出錯，請洽MIS");
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("IOException 遠端過站連線出錯，請洽MIS");
        }

    }

/*    public static void main(String[] args)throws Exception {
        URL CODEBASE = new URL("http://127.0.0.1:8088/PEGAMES/");
        URL PROXY_SERVER = new URL("http://10.162.244.237:8088/PEGAMES/");
        URL HTTP_SERVER = new URL("http://127.0.0.1:8088/PEGAMES/");
        URL FILE_SERVER = new URL("http://10.162.244.237:8088/PEGAMES*//**//*");
        URL_VO codeBase = new URL_VO(CODEBASE, PROXY_SERVER, HTTP_SERVER, FILE_SERVER);

        UI_InitVO uiVO2 = new UI_InitVO("zh_TW", "3FC0955A8EF33408E05363F4A20AED66", "3FC0955A8EF23408E05363F4A2-31276", "鎧嘉電腦配件(蘇州)有限公司", "ENAME", codeBase, "USER NAME TEST", "SYSTEM", "系統管理者 鎧嘉電腦配件(蘇州)有限公司", "3B5B598592A53EB2E05363F4A20A-BU2");
        DataHandler dh2 = new DataHandler(uiVO2);


        String sql = "select  ISN,CSSN,SSN,SNE,SNC  from ISNINFO where isn='ZPC5264000023' or ssn='ZPC5264000023' or cssn='ZPC5264000023'";
        Hashtable a = null;
        try {
            a = dh2.getDataOne(sql, DataSourceType._SFIS_KAIJIA_STD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(a);
    }*/
}
