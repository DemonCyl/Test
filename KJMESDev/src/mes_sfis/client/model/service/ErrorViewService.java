package mes_sfis.client.model.service;

import base.enums.DataSourceType;
import base.vo.UI_InitVO;
import base.vo.URL_VO;
import mes_sfis.client.sfis.LogInOutSfisSoap;
import mes_sfis.client.sfis.PassDeviceSfisSoap;
import mes_sfis.client.sfis.SoapUtil;
import mes_sfis.client.task.PassDeviceTask;
import mes_sfis.client.util.DataHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.xml.soap.SOAPException;
import java.io.IOException;
import java.net.URL;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.Vector;

/**
 * Created by Srx_Zhu on 2018/06/14.
 */
public class ErrorViewService extends BaseService {
    private static final Logger logger = LogManager.getLogger(PassDeviceTask.class);
    UI_InitVO uiVO;
    DataHandler dh;
    Set<String> set=null;

    public ErrorViewService(UI_InitVO uiVO) {
        this.uiVO = uiVO;
        dh = new DataHandler(uiVO);
    }
    public List passRoute(String device, String isn, String op) throws Exception {

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


    public Vector findDeviceLine(String cnum)  {
        List<Hashtable> list; //設置初始值為true(代表默認有此棧板碼)
        String sql = " SELECT p.LINE,p.DEVICE,p.DEVICENM FROM TP.MES_DEVICE_CONTROL s LEFT JOIN DEVICE p on p.DEVICE=s.DEVICE WHERE s.C_NUM= '"+cnum+"'";

        try {
            Vector vector=dh.getDataVector(sql, DataSourceType._SFIS_KAIJIA_STD);

            if(vector==null||vector.size()==0){
                JOptionPane.showConfirmDialog(null, "數據庫無"+cnum+"的數據", "警告", JOptionPane.PLAIN_MESSAGE);
            }else{
                return vector;//返回數據
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showConfirmDialog(null, "根據"+cnum+"查詢刷ERROR站點信息發生異常", "警告", JOptionPane.PLAIN_MESSAGE);
            //  throw new Exception("是否報廢查詢錯誤:"+e.getMessage());
        }
        return null;
    }



 /*   public static void main(String[] args)throws Exception {
        URL CODEBASE = new URL("http://127.0.0.1:8088/PEGAMES/");
        URL PROXY_SERVER = new URL("http://10.162.244.237:8088/PEGAMES/");
        URL HTTP_SERVER = new URL("http://127.0.0.1:8088/PEGAMES/");
        URL FILE_SERVER = new URL("http://10.162.244.237:8088/PEGAMES*//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//*");
        URL_VO codeBase = new URL_VO(CODEBASE, PROXY_SERVER, HTTP_SERVER, FILE_SERVER);

        UI_InitVO uiVO2 = new UI_InitVO("zh_TW", "3FC0955A8EF33408E05363F4A20AED66", "3FC0955A8EF23408E05363F4A2-31276", "鎧嘉電腦配件(蘇州)有限公司", "ENAME", codeBase, "USER NAME TEST", "SYSTEM", "系統管理者 鎧嘉電腦配件(蘇州)有限公司", "3B5B598592A53EB2E05363F4A20A-BU2");
        DataHandler dh2 = new DataHandler(uiVO2);

       // findDeviceLine();
        String sql = "SELECT p.LINE,p.DEVICE FROM TP.MES_DEVICE_CONTROL s LEFT JOIN DEVICE p on p.DEVICE=s.DEVICE WHERE s.C_NUM= '"+1001+"'";
        Vector a = null;
        try {
            a = dh2.getDataVector(sql, DataSourceType._SFIS_KAIJIA_STD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(a);
    }*/

}
