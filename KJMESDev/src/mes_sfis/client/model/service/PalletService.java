package mes_sfis.client.model.service;

import base.enums.DataSourceType;
import base.vo.UI_InitVO;
import base.vo.URL_VO;
import mes_sfis.client.model.bean.MesPackConfig;
import mes_sfis.client.model.bean.PackCarton;
import mes_sfis.client.pdf.PDFPallet;
import mes_sfis.client.util.DataHandler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * Created by Chris1_Liao on 2018/4/3.
 */
public class PalletService extends BaseService {
    UI_InitVO uiVO;
    DataHandler dh;
    public PalletService(UI_InitVO uiVO) {
        this.uiVO = uiVO;
        dh = new DataHandler(uiVO);
    }
    String pallet_no="P";
    //pdf 生成

    public  void PDF(String KJ_PN, String mun,String FIELD_VALUE) {
        MesPackConfig mes = new MesPackConfig();
        HashMap pDFMap = new HashMap();  //pdf數據
        try {
            PackageService testpack = new PackageService(uiVO);
            mes = testpack.getConfigByEeeeProjectCode(KJ_PN,"COP1721",FIELD_VALUE,null,null,null,null,0);
            //獲取DATE_CODE 日期編碼YWWD
            DataHandler dh = new DataHandler(uiVO);
            PackCarton packCarton =new PackCarton();
            packCarton.setDh(dh);
            String cartonSn= packCarton.getCartonSn("COP1721");
            String dateCode= packCarton.getDateCode();
            pDFMap.put("vendor",mes.getVendor());
            pDFMap.put("oemPN",mes.getOemPN());
            pDFMap.put("dateStr",dateCode);//DATE_CODE
            pDFMap.put("venderCode",mes.getVender()); //VENDER
            pDFMap.put("venderSite",mes.getSiteCode());
            pDFMap.put("applePN",mes.getApplepn());
            pDFMap.put("rev",mes.getRev());
            pDFMap.put("config",mes.getConfig());
            pDFMap.put("desc",mes.getDescription());//DESCRIPTION
            pDFMap.put("lc",mes.getLc());
            pDFMap.put("qty",mun);//數量
            pDFMap.put("batch",mes.getBatChno());
            pDFMap.put("cartonNo",pallet_no+=mes.getVender()+mes.getSiteCode()+cartonSn);
            pDFMap.put("stage",mes.getStage());
            pDFMap.put("oemComments",mes.getKJPN()); //kj_pn
            PDFPallet pdfCreater =new PDFPallet("pallet.pdf");
            pdfCreater.addPage(pDFMap);
            pdfCreater.close();
            pdfCreater.printPdf();
            //cmd 打印pdf
        //    Runtime.getRuntime().exec("cmd.exe /C start acrord32 /P /h " + "d://pallet.pdf");
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }
    //查詢數據
    public   List<Hashtable> key(String CARTON_NO) {
        MesPackConfig mes = new MesPackConfig();
         DataHandler dh = new DataHandler(uiVO);
        List<Hashtable> ht = null;
        String sql = "select cf.KJ_PN as KJ_PN,OEMPN ,sfis_check_value as VALUEBM ,(CASE WHEN cf.sfis_check_value='B' THEN '黑' WHEN cf.sfis_check_value='W' THEN '白' END) as valuezw  from mes_pack_config cf where cf.KJ_PN=(select cn.KJ_PN from MES_PACK_CARTON cn where cn.carton_no='"+CARTON_NO+"' and cn.IS_CLOSE=1)";
        try {
            ht = dh.getDataVector(sql, DataSourceType._MultiCompanyCurrentDB);
        } catch (Exception e) {
            e.printStackTrace();
            ht = null;
        }
        return ht;
    }
    //更新打板封板
    public  void upCARTON(ArrayList list,String PALLET_OID){
        DataHandler dh = new DataHandler(uiVO);
        try {
            String sqlPALLET="update MES_PACK_PALLET set IS_CLOSE=1,CLOSE_SYSTEMDATE=sysdate where PALLET_OID='"+PALLET_OID+"'";
            dh.updateData(sqlPALLET, DataSourceType._MultiCompanyCurrentDB);
            for (int i = 0; i < list.size(); i++) {
                //打箱表中添加棧板號
                String upsql = "update MES_PACK_CARTON set PALLET_OID='" + PALLET_OID + "' where CARTON_NO='" + list.get(i) + "'";
                dh.updateData(upsql, DataSourceType._MultiCompanyCurrentDB);
            }
        }catch (Exception e) {
                e.printStackTrace();
            }
    }
    //產生pdf 數據保存到MES_PACK_PALLET 狀態為0未封板
    public  String increase(String[] arr,String FIELD_VALUE) {
        Hashtable uuid=null;
        DataHandler dh = new DataHandler(uiVO);
        PackageService testpack = new PackageService(uiVO);
        MesPackConfig mes = new MesPackConfig();
        PackCarton packCarton =new PackCarton();
        packCarton.setDh(dh);
        try {
            mes = testpack.getConfigByEeeeProjectCode(arr[1],"COP1721",FIELD_VALUE,null,null,null,null,0);
            String cartonSn= packCarton.getCartonSn("COP1721");
            String dateCode= packCarton.getDateCode();
            //獲取新的棧板號
             uuid = dh.getDataOne("select rawtohex(sys_guid()) as UUID from dual", DataSourceType._MultiCompanyCurrentDB);
            //插入棧板數據
           String inssql = "insert into MES_PACK_PALLET (PALLET_OID,PALLET_NO,DATE_CODE,CARTON_QTY,LOG_SYSTEMDATE,LOG_EMPLOYEE_OID,IS_CLOSE) values ('" + uuid.get("UUID") + "','"+pallet_no+"','" + dateCode + "'," + arr[0] + ",sysdate,'" + uiVO.getUSERNAME()+"',0)";
            dh.updateData(inssql, DataSourceType._MultiCompanyCurrentDB);
            pallet_no="P";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (String) uuid.get("UUID");
    }
    //驗證是否已打版
    public  String PalletVerify(String CARTON_NO){
        DataHandler dh = new DataHandler(uiVO);
        Hashtable ht = null;
        String value = null;
        String sql = "select pallet_oid  from mes_pack_carton where  carton_no='"+CARTON_NO+"'";
        try {
            ht = dh.getDataOne(sql, DataSourceType._MultiCompanyCurrentDB);
            value= (String) ht.get("PALLET_OID");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public static void main(String[] args) {
        System.out.println(aaa("I19170001815600012"));
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
    //查詢數據
    public static List<Hashtable> aaa(String CARTON_NO) {
        MesPackConfig mes = new MesPackConfig();
        DataHandler dh = new DataHandler(jdbc());
        List<Hashtable>  ht = null;
        String sql = "select cf.KJ_PN as KJ_PN,OEMPN ,sfis_check_value as VALUEBM ,(CASE WHEN cf.sfis_check_value='B' THEN '黑' WHEN cf.sfis_check_value='W' THEN '白' END) as valuezw  from mes_pack_config cf where cf.KJ_PN=(select cn.KJ_PN from MES_PACK_CARTON cn where cn.carton_no='"+CARTON_NO+"' and cn.IS_CLOSE=1)";
        try {
             ht = dh.getDataVector(sql, DataSourceType._MultiCompanyCurrentDB);
        } catch (Exception e) {
            System.out.println("xxxx");
            e.printStackTrace();
            ht = null;
        }
        System.out.println("aaaaa"+ht.get(1).get("VALUEBM"));
        return ht;
    }
}