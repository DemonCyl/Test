package mes_sfis.client.model.service;

import base.enums.DataSourceType;
import base.vo.UI_InitVO;
import base.vo.URL_VO;
import mes_sfis.client.model.bean.CognexModel;
import mes_sfis.client.task.PassDeviceTask;
import mes_sfis.client.util.DataHandler;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.Vector;

/**
 * Created by Pino_Gao on 2018/7/11.
 */
public class CognexUploadService extends BaseService  {
    private static final Logger logger = LogManager.getLogger(PassDeviceTask.class);
    UI_InitVO uiVO;
    DataHandler dh;
    Set<String> set=null;

    public CognexUploadService(UI_InitVO uiVO) {
        this.uiVO = uiVO;
        dh = new DataHandler(uiVO);
    }

    public String insertCognexRawData(List<CognexModel> cognexModels) throws Exception {
        String result;
        //System.out.println(LoadBean.toString());
        if(cognexModels==null){
            //System.out.println("*************************************shibai ");
            result="沒有新數據";
            return result;
        }


        Vector arg = new Vector();
        for (int i=0;i<cognexModels.size();i++) {
         String sql="insert into TP.MES_VERIFY_BARCODE VALUES (sys_guid(),'" + cognexModels.get(i).getTIME() + "','" + cognexModels.get(i).getAMODULE() + "','" + cognexModels.get(i).getREADING() + "','"+ cognexModels.get(i).getDECODE_TIME() + "','" + cognexModels.get(i).getOVERALL_GRAGE() + "','"
         + cognexModels.get(i).getSYMBOL_CONTRAST() + "'," + cognexModels.get(i).getRAWUP() + ",'" + cognexModels.get(i).getPRINTGROWTH() + "',"  + cognexModels.get(i).getRAWDOWN() + ",'" + cognexModels.get(i).getERROR_CORRECTION() + "',"
         + cognexModels.get(i).getRAWONE() + ",'" + cognexModels.get(i).getMODULATION() + "',"+ cognexModels.get(i).getRAWTWO() + ",'"  + cognexModels.get(i).getFIXEDPATTERN() + "',"  + cognexModels.get(i).getRAWTHREE() + ",'"
         + cognexModels.get(i).getGRID_NONUNIFORMITY() + "'," + cognexModels.get(i).getRAWFOUR()+")";

            arg.add(sql);
        }
         /*  String sqa="insert into TP.MES_VERIFY_BARCODE VALUESUNIVO";
            for (int a=0;a<cognexModels.size();a++){
                String sql="";
                sql += " select sys_guid()," ;
                sql += " '" + cognexModels.get(a).getTIME() + "'," ;
                sql += "'" + cognexModels.get(a).getAMODULE() + "',";
                sql += "'" + cognexModels.get(a).getREADING() + "'," ;
                sql += "'" + cognexModels.get(a).getDECODE_TIME() + "'," ;
                sql += "'" + cognexModels.get(a).getOVERALL_GRAGE() + "'," ;
                sql += "'" + cognexModels.get(a).getSYMBOL_CONTRAST() + "',";
                sql += "" + cognexModels.get(a).getRAWUP() + ",";
                sql += "'" + cognexModels.get(a).getPRINTGROWTH() + "',";
                sql += "" + cognexModels.get(a).getRAWDOWN() + ",";
                sql += "'" + cognexModels.get(a).getERROR_CORRECTION() + "',";
                sql += "" + cognexModels.get(a).getRAWONE() + ",";
                sql += "'" + cognexModels.get(a).getMODULATION() + "',";
                sql += "" + cognexModels.get(a).getRAWTWO() + ",";
                sql += "'" + cognexModels.get(a).getFIXEDPATTERN() + "',";
                sql += "" + cognexModels.get(a).getRAWTHREE() + ",";
                sql += "'" + cognexModels.get(a).getGRID_NONUNIFORMITY() + "',";
                if (a==cognexModels.size()-1){
                    sql +=  cognexModels.get(a).getRAWFOUR()+ " from dual";
                    sqa+=sql;
                }else {

                    sql +=  cognexModels.get(a).getRAWFOUR()+ " from dual union";
                    sqa+=sql;
                }
            }*/


        try {
            dh.updateData(arg, DataSourceType._MultiCompanyCurrentDB);
            result= "添加成功";
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("數據添加失敗"+e.getMessage());

        }
        return result;

    }




    public String findNewTime(){
        String time=null;
        String sql = "select * from(select ADDTIME FROM TP.MES_VERIFY_BARCODE  order by ADDTIME DESC) where rownum=1";
        try {
            Hashtable ht = dh.getDataOne(sql,DataSourceType._MultiCompanyCurrentDB);

            if (ht!=null) {

                time = (String) ht.get("ADDTIME");
            }

            return time;

        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
           // throw new Exception("查詢數據庫最新數據的時間異常"+e.getMessage());
        }

    }

      /*  public static void main(String[] args)throws Exception {
        URL CODEBASE = new URL("http://127.0.0.1:8088/PEGAMES/");
        URL PROXY_SERVER = new URL("http://10.162.244.237:8088/PEGAMES/");
        URL HTTP_SERVER = new URL("http://127.0.0.1:8088/PEGAMES/");
        URL FILE_SERVER = new URL("http://10.162.244.237:8088/PEGAMES*//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//*");
        URL_VO codeBase = new URL_VO(CODEBASE, PROXY_SERVER, HTTP_SERVER, FILE_SERVER);

        UI_InitVO uiVO2 = new UI_InitVO("zh_TW", "3FC0955A8EF33408E05363F4A20AED66", "3FC0955A8EF23408E05363F4A2-31276", "鎧嘉電腦配件(蘇州)有限公司", "ENAME", codeBase, "USER NAME TEST", "SYSTEM", "系統管理者 鎧嘉電腦配件(蘇州)有限公司", "3B5B598592A53EB2E05363F4A20A-BU2");
        DataHandler dh2 = new DataHandler(uiVO2);
            Vector arg = new Vector();
            for (int i=0;i<4;i++) {
                String sqla= "insert into TP.MES_VERIFY_BARCODE VALUES ('2018-04-19 14:38:16','GKP814100E7JPT34J+58+G0K8104A3J8+11S1164U1164S1165HA15102','4.971s','68ms','A','A',0.844,'A',-0.154,'A',1,'A',4,'A',4,'A',0.0738)";
                String sql="insert into TP.MES_VERIFY_BARCODE VALUES ('2018-04-19 14:38:16', 'GKP814100E7JPT34J+58+G0K8104A3J8+11S1164U1164S1165HA15102','4.971s','68ms','A','A',0.844,'A',-0.154,'A',1,'A',4,'A',4,'A',0.0738)";

                arg.add(sqla);
            }
            String sqla = "insert into TP.MES_VERIFY_BARCODE VALUESUNIVO select sys_guid(), '2018-04-19 14:38:16','GKP814100E7JPT34J+58+G0K8104A3J8+11S1164U1164S1165HA15102','4.971s','68ms','A','A',0.844,'A',-0.154,'A',1,'A',4,'A',4,'A',0.0738 from dual union select sys_guid(), '2018-04-19 14:39:58','GKP81440JKTJPT34W+58+G0K81040GGY+11S1164U1164S1165HA16202','0.485s','67ms','A','A',0.854,'A',-0.154,'A',1,'A',4,'A',4,'A',0.0607 from dual union select sys_guid(), '2018-04-19 14:40:49','GKP812608Q4JPT34J+58+G0K81066J7T+11S1164U1164S1165HA16203','0.646s','72ms','A','A',0.84,'A',-0.152,'A',1,'A',4,'A',4,'A',0.0441 from dual union select sys_guid(), '2018-04-19 14:41:21','GKP81440J99JPT34A+58+G0K81040J75+11S1164U1164S1165HA16203','0.488s','69ms','A','A',0.828,'A',-0.158,'A',1,'A',4,'A',3,'A',0.0509 from dual union select sys_guid(), '2018-04-19 14:42:12','GKP812609LHJPT34G+58+G0K81040JCA+11S1164U1164S1165HA16203','0.675s','72ms','A','A',0.839,'A',-0.154,'A',1,'A',4,'A',3,'A',0.0621 from dual union select sys_guid(), '2018-04-19 14:42:45','GKP81260CMVJPT34Y+58+G0K81040G5S+11S1164U1164S1165HA15102','0.492s','69ms','B','A',0.736,'A',-0.0391,'A',1,'B',3,'B',2,'A',0.033 from dual union select sys_guid(), '2018-04-19 14:43:10','GKP81260ACCJPT34A+58+G0K8104A3HN+11S1164U1164S1164HA16203','0.659s','74ms','A','A',0.842,'A',-0.16,'A',1,'A',4,'A',3,'A',0.0559 from dual union select sys_guid(), '2018-04-19 15:59:26','GKP8126075JJPT34U+58+G0K81040GGQ+11S1164U1164S1165HA15103','17.681s','68ms','A','A',0.781,'A',-0.164,'A',1,'A',4,'A',4,'A',0.0611 from dual union select sys_guid(), '2018-04-19 23:03:28','GKP812608MEJPT34H+58+G0K81046MZE+11S1165U1165S1165HA16203','0.492s','70ms','A','A',0.807,'A',-0.148,'A',1,'A',4,'A',4,'A',0.0543 from dual union select sys_guid(), '2018-04-19 23:05:39','GKP812609ZQJPT344+58+G0K81056JA2+11S1165U1164S1165HA16203','0.639s','139ms','B','A',0.808,'A',-0.152,'A',1,'A',4,'B',3,'A',0.0473 from dual union select sys_guid(), '2018-04-19 23:07:02','GKP812608Q7JPT34F+58+G0K81066J30+11S1165U1164S1165HA15102','0.637s','71ms','A','A',0.819,'A',-0.156,'A',1,'A',4,'A',4,'A',0.0492 from dual union select sys_guid(), '2018-04-19 23:07:35','GKP8141013GJPT347+58+G0K81046LUR+11S1165U1164S1165HA16203','0.634s','135ms','A','A',0.792,'A',-0.141,'A',1,'A',4,'A',4,'A',0.0426 from dual union select sys_guid(), '2018-04-19 23:08:15','GKP8126082JJPT342+58+G0K81040GG3+11S1165U1165S1165HA15103','0.482s','66ms','B','A',0.758,'A',-0.195,'A',1,'B',3,'B',3,'A',0.0794 from dual union select sys_guid(), '2018-04-19 23:08:55','GKP81420B7FJPT34K+58+G0K81046LUJ+11S1165U1165S1165HA16203','0.633s','135ms','A','A',0.794,'A',-0.174,'A',1,'A',4,'A',4,'A',0.0574 from dual union select sys_guid(), '2018-04-19 23:09:31','GKP81260D4HJPT34S+58+G0K81043UH6+11S1165U1165S1165HA15102','0.64s','71ms','A','A',0.814,'A',-0.162,'A',1,'A',4,'A',4,'A',0.047 from dual union select sys_guid(), '2018-04-19 23:10:03','GKP81260AXZJPT340+58+G0K81040GNN+11S1165U1165S1165HA15102','0.651s','72ms','A','A',0.821,'A',-0.168,'A',1,'A',4,'A',4,'A',0.0703 from dual union select sys_guid(), '2018-04-19 23:10:34','GKP812606T5JPT34A+58+G0K81040GNJ+11S1165U1165S1165HA15103','0.638s','69ms','A','A',0.816,'A',-0.16,'A',1,'A',4,'A',4,'A',0.0436 from dual union select sys_guid(), '2018-04-19 23:11:05','GKP81260AG8JPT342+58+G0K81046LUE+11S1165U1164S1165HA15103','0.647s','71ms','A','A',0.836,'A',-0.152,'A',1,'A',4,'A',4,'A',0.0524 from dual union select sys_guid(), '2018-04-19 23:11:27','GKP812603U0JPT34F+58+G0K81056JAN+11S1165U1164S1165HA15401','0.636s','70ms','A','A',0.836,'A',-0.148,'A',1,'A',4,'A',4,'A',0.0428 from dual union select sys_guid(), '2018-04-19 23:12:35','GKP81260ASNJPT34S+58+G0K81066K7J+11S1165U1164S1165HA16203','0.659s','133ms','A','A',0.806,'A',-0.172,'A',1,'A',4,'A',4,'A',0.0672 from dual union select sys_guid(), '2018-04-19 23:13:19','GKP814101P9JPT34N+58+G0K81060D7F+11S1165U1164S1165HA16203','0.632s','134ms','B','A',0.747,'A',-0.191,'A',1,'B',3,'A',4,'A',0.0725 from dual union select sys_guid(), '2018-04-19 23:15:01','GKP81260B89JPT34Q+58+G0K81060EE0+11S1165U1164S1165HA15102','0.488s','68ms','A','A',0.796,'A',-0.174,'A',1,'A',4,'A',4,'A',0.0515 from dual union select sys_guid(), '2018-04-19 23:15:17','GKP81440GRMJPT34L+58+G0K81056JAG+11S1165U1164S1165HA15102','0.571s','73ms','A','A',0.79,'A',-0.178,'A',1,'A',4,'A',4,'A',0.0621 from dual union select sys_guid(), '2018-04-19 23:15:36','GKP81440HGCJPT34M+58+G0K81066J2V+11S1165U1164S1165HA16203','0.486s','69ms','A','A',0.798,'A',-0.17,'A',1,'A',4,'A',4,'A',0.0542 from dual union select sys_guid(), '2018-04-19 23:15:48','GKP81260A8YJPT342+58+G0K81066JEG+11S1165U1164S1165HA15103','0.633s','137ms','A','A',0.817,'A',-0.15,'A',1,'A',4,'A',4,'A',0.0444 from dual union select sys_guid(), '2018-04-19 23:16:04','GKP81260AZQJPT343+58+G0K81040HKV+11S1165U1164S1165HA15103','0.631s','68ms','A','A',0.795,'A',-0.15,'A',1,'A',4,'A',3,'A',0.0519 from dual union select sys_guid(), '2018-04-19 23:16:40','GKP812606VCJPT34X+58+G0K81040GRR+11S1165U1164S1165HA15602','0.556s','69ms','A','A',0.788,'A',-0.184,'A',1,'A',4,'A',4,'A',0.0566 from dual union select sys_guid(), '2018-04-19 23:16:54','GKP81260CZPJPT342+58+G0K81046LUU+11S1165U1165S1165HA16203','0.557s','70ms','A','A',0.824,'A',-0.162,'A',1,'A',4,'A',4,'A',0.0566 from dual union select sys_guid(), '2018-04-19 23:17:05','GKP8141010HJPT34F+58+G0K81056JBB+11S1165U1164S1165HA1510P','0.507s','66ms','A','A',0.739,'A',-0.166,'A',1,'A',4,'A',4,'A',0.0602 from dual\n";
            Vector b = null;

       // findDeviceLine();
        String sql = "insert into TP.MES_VERIFY_BARCODE VALUES ('2018-04-19 14:38:16', 'GKP814100E7JPT34J+58+G0K8104A3J8+11S1164U1164S1165HA15102','4.971s','68ms','A','A',0.844,'A',-0.154,'A',1,'A',4,'A',4,'A',0.0738)";
        Vector a = null;
        try {
           // a = dh2.getDataVector(sqla, DataSourceType._SFIS_KAIJIA_STD);
            a = dh2.updateData(sqla, DataSourceType._MultiCompanyCurrentDB);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(a);
    }*/
}
