package mes_sfis.client.model.service;

import base.enums.DataSourceType;
import base.vo.UI_InitVO;
import mes_sfis.client.util.DataHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Xiaojian1_Yu on 2018/7/17.
 */
public class ANOBatchPassService extends BaseService {
    private static final Logger logger = LogManager.getLogger(ANOBatchPassService.class);
    DataHandler dh;
    Set<String> set = null;
    String oidHt = null;
    private static UI_InitVO uiVO;

    public ANOBatchPassService(UI_InitVO uiVO) {
        this.uiVO = uiVO;
        dh = new DataHandler(uiVO);
    }

    //獲取OID的方法
    public String getUID() {
        Hashtable oid = null;
        String oidsql = "select rawtohex(sys_guid()) from dual";
        logger.debug("getUID：" + oidsql);
        try {
            oid = dh.getDataOne(oidsql, DataSourceType._SFIS_KAIJIA_STD);
            oidHt = oid.values().toString().replaceAll("\\[", "").replaceAll("\\]", "");
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("getUID：" + e.getMessage());
        }
        return oidHt;
    }

    //檢查ISN是否已經被綁定過掛具的方法
    public boolean Check_ISNRepeat(String ISN) {
        boolean result = true;
        String sql = "SELECT HANGER_ID FROM TP.MES_ANO_HANGER_ISN WHERE M_SN='" + ISN + "'";
        logger.debug("Check_ISNRepeat：" + sql);
        try {
            Hashtable ht = dh.getDataOne(sql, DataSourceType._SFIS_KAIJIA_STD);
            if (ht == null) {
                result = false;
            } else {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Check_ISNRepeat：" + e.getMessage());
        }
        return result;
    }

    //根據一片ISN查詢虛擬母掛號方法
    public String VHC_Code(String VC) {
        String sql = " SELECT H.HANGER_ID FROM TP.MES_ANO_HANGER_ISN I " +
                " INNER JOIN TP.MES_ANO_HANGER H " +
                " ON I.HANGER_PROCESS_OID=H.HANGER_PROCESS_OID " +
                " WHERE I.M_SN='" + VC + "'";
        logger.debug("VHC_Code：" + sql);
        try {
            Hashtable ht = dh.getDataOne(sql, DataSourceType._SFIS_KAIJIA_STD);
            VC = ht.values().toString();
        } catch (Exception e) {
            VC = null;
            logger.debug("VHC_Code ：" + e.getMessage());
        }
        return VC;
    }

    //虛擬母掛號與母掛替換方法
    public void replace_function(String V, String thisHangerID) {
        Vector updateHangerSQl = new Vector();
        Vector updateHangerIsnSQl = new Vector();
        String updateMES_ANO_HANGER = "UPDATE TP.MES_ANO_HANGER SET HANGER_ID='" + thisHangerID + "',STATUS='1' " +
                " WHERE HANGER_PROCESS_OID=(SELECT H.HANGER_PROCESS_OID FROM TP.MES_ANO_HANGER_ISN I " +
                " INNER JOIN TP.MES_ANO_HANGER H " +
                " ON I.HANGER_PROCESS_OID=H.HANGER_PROCESS_OID " +
                " WHERE I.M_SN='" + V + "')";
        logger.debug("replace_function sql updateMES_ANO_HANGER :" + updateMES_ANO_HANGER);
        String updateMES_ANO_HANGER_ISN = "UPDATE TP.MES_ANO_HANGER_ISN SET HANGER_ID = '" + thisHangerID + "' " +
                "WHERE HANGER_PROCESS_OID = (SELECT H.HANGER_PROCESS_OID FROM TP.MES_ANO_HANGER_ISN I " +
                " INNER JOIN TP.MES_ANO_HANGER H " +
                " ON I.HANGER_PROCESS_OID=H.HANGER_PROCESS_OID " +
                " WHERE I.M_SN='" + V + "') ";
        logger.debug("replace_function sql updateMES_ANO_HANGER_ISN" + updateMES_ANO_HANGER_ISN);
        updateHangerSQl.add(updateMES_ANO_HANGER);
        updateHangerIsnSQl.add(updateMES_ANO_HANGER_ISN);
        try {
            dh.updateData(updateHangerSQl, DataSourceType._SFIS_KAIJIA_STD);
            dh.updateData(updateHangerIsnSQl, DataSourceType._SFIS_KAIJIA_STD);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("replace_function :" + e.getMessage());
        }
    }

    //檢查飛靶時間
    public long CheckFly_time(String thisFlyID) throws ParseException {
        Hashtable ht = new Hashtable();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String bb = df.format(new Date());
        String aa = null;
        long time_hm_fly = 0;
        Map map = new HashMap();
        String sql = "select * from(SELECT to_char(LOG_SYSTEMDATE,'yyyy-mm-dd HH24:mi:ss') FROM TP.MES_ANO_FLY WHERE ANO_FLY_ID='"+thisFlyID+"' order by log_systemdate desc) where ROWNUM=1";
        logger.debug("CheckFly_time sql：" + sql);
        try {
            ht = dh.getDataOne(sql, DataSourceType._SFIS_KAIJIA_STD);
            if (ht != null) {
                aa = ht.values().toString().replaceAll("\\[", "").replaceAll("\\]", "");
                Date currentTime = df.parse(bb);   //獲取系統當前時間
                Date firstTime = df.parse(aa);     //查詢數據庫存的時間
                time_hm_fly = getTime(currentTime, firstTime);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("CheckFly_time :" + e.getMessage());
        }
        return time_hm_fly;
    }

    //檢查母掛時間的方法
    public long CheckHanger_time(String thisHangerID) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String bb = df.format(new Date());
        String aa = null;
        long time_hm = 0;
        Map map = new HashMap();
        String sql_time = "select to_char(LOG_SYSTEMDATE,'yyyy-mm-dd HH24:mi:ss') from (select * from tp.MES_ANO_HANGER where hanger_id='" + thisHangerID + "' order by LOG_SYSTEMDATE desc) where rownum=1";
        logger.debug("CheckHanger_time sql:" + sql_time);
        Hashtable ht = new Hashtable();
        try {
            ht = dh.getDataOne(sql_time, DataSourceType._SFIS_KAIJIA_STD);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("CheckHanger_time :" + e.getMessage());
        }
        if (ht != null) {
            aa = ht.values().toString().replaceAll("\\[", "").replaceAll("\\]", "");
            Date currentTime = df.parse(bb);   //獲取系統時間
            Date firstTime = df.parse(aa);     //獲取數據庫時間
            time_hm = getTime(currentTime, firstTime);
        }
        return time_hm;
    }

    //檢查ISN狀態的方法
    public Hashtable CheckISN_State(String ISN) {
        Hashtable ht = null;
        String sql = "SELECT  NGRP FROM MO_D WHERE ISN='" + ISN + "'";
        logger.debug("CheckISN_State sql" + sql);
        try {
            ht = dh.getDataOne(sql, DataSourceType._SFIS_KAIJIA_STD);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("CheckISN_State :" + e.getMessage());
        }
        return ht;
    }

    //檢查顏色的方法
    public Hashtable CheckColor_State(String ISN) {
        Hashtable th = null;
        String sql = "SELECT SNE FROM ISNINFO WHERE ISN='" + ISN + "'";
        logger.debug("CheckColor_State sql:" + sql);
        try {
            th = dh.getDataOne(sql, DataSourceType._SFIS_KAIJIA_STD);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("CheckColor_State :" + e.getMessage());
        }
        return th;
    }

    //檢查母掛綁定的ISN是否已經過站了
    public int IsPassDevice(String thisHangerID) {
        int isPassDevice = 0;//0表示該母掛符合要求
        Hashtable ht = null;
        String sql = " SELECT * FROM(SELECT FLY_PROCESS_OID FROM TP.MES_ANO_HANGER WHERE HANGER_ID='" + thisHangerID + "' ORDER BY LOG_SYSTEMDATE DESC)WHERE ROWNUM=1";
        logger.debug("IsPassDevice sql:" + sql);
        try {
            ht = dh.getDataOne(sql, DataSourceType._SFIS_KAIJIA_STD);
            if (ht.get("FLY_PROCESS_OID") != null) {
                isPassDevice = 1;//1表示該母掛綁定的ISN已經過過站了
            } else {
                isPassDevice = 0;//0表示該母掛符合要求
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("IsPassDevice:" + e.getMessage());
        }
        return isPassDevice;
    }

    //檢查母掛是否有ISN信息，有沒有綁定ISN
    public int CheckHang_ISNisBanding(String thisHangerID) {
        int hangerState = 9;//隨便一個數字
        Hashtable ht = null;
        //查詢該母掛最近一次的使用情況，進行判斷
        String sql = "select * from(select a.M_SN from TP.MES_ANO_HANGER_ISN a inner join TP.MES_ANO_HANGER b on a.hanger_process_oid = b.hanger_process_oid where b.hanger_id = '" + thisHangerID + "' order by b.log_systemdate) where rownum=1";
        logger.debug("CheckHang_ISNisBanding sql:" + sql);
        try {
            ht = dh.getDataOne(sql, DataSourceType._SFIS_KAIJIA_STD);
            if (ht.get("M_SN") != null) {
                hangerState = 0;
            }
        } catch (Exception e) {
            logger.debug("CheckHang_ISNisBanding :" + e.getMessage());
            hangerState = 1;
        }
        return hangerState;
    }

    public String manipulation(HashMap map) {
        String str = "mark";
        set = map.keySet(); //獲取全部的KEY值
        System.out.println(set);
        for (String key : set) {
            str += ",'" + key + "'";
        }
        str = str.replaceFirst("mark,", "");
        return str;
    }

    //ISN與虛擬母掛綁定
    public int ISN_VHCBinding(String UUID, String VHC, HashMap map) {
        int a = 0;
        //?B?zISN
        String manyISN = manipulation(map);
        Vector insertVHCSQl = new Vector();
        String[] isnArray = manyISN.split(",");
        String sqla = "insert into TP.MES_ANO_HANGER(HANGER_PROCESS_OID, PROJECT_CODE, HANGER_ID,QTY,LOG_SYSTEMDATE,LOG_EMPLOYEE_OID,STATUS) values('" + UUID + "','COP1721','" + VHC + "'," + isnArray.length + ",sysdate,'" + uiVO.getLogin_id() + "','0')";
        logger.debug("ISN_VHCBinding sqla:" + sqla);
        insertVHCSQl.add(sqla);
        for (int i = 0; i < isnArray.length; i++) {
            String sql = " insert into TP.MES_ANO_HANGER_ISN(HANGER_PROCESS_OID,PROJECT_CODE,M_SN,HANGER_ID) values('" + UUID + "','COP1721','" + isnArray[i].replaceAll("'", "") + "','" + VHC + "')";
            logger.debug("ISN_VHCBinding sql:" + sql);
            insertVHCSQl.add(sql);
        }
        try {
            dh.updateData(insertVHCSQl, DataSourceType._SFIS_KAIJIA_STD);
            a = 1;
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("ISN_VHCBinding :" + e.getMessage());
        }
        return a;
    }

    //ISN與母掛綁定
    public int ISN_HangerBinding(String UUID, String thisHangerID, HashMap map) {
        int a = 0;
        //?B?zISN
        String manyISN = manipulation(map);
        Vector insertHangerSQl = new Vector();
        String[] isnArray = manyISN.split(",");
        String sqla = "insert into TP.MES_ANO_HANGER(HANGER_PROCESS_OID, PROJECT_CODE, HANGER_ID,QTY,LOG_SYSTEMDATE,LOG_EMPLOYEE_OID,STATUS) values('" + UUID + "','COP1721','" + thisHangerID + "'," + isnArray.length + ",sysdate,'" + uiVO.getLogin_id() + "','1')";
        logger.debug("ISN_HangerBinding sqla:" + sqla);
        insertHangerSQl.add(sqla);
        for (int i = 0; i < isnArray.length; i++) {
            System.out.println("-----------------------" + isnArray[i]);
            String sql = " insert into TP.MES_ANO_HANGER_ISN(HANGER_PROCESS_OID,PROJECT_CODE,M_SN,HANGER_ID) values('" + UUID + "','COP1721','" + isnArray[i].replaceAll("'", "") + "','" + thisHangerID + "')";
            logger.debug("ISN_HangerBinding sql :" + sql);
            insertHangerSQl.add(sql);
        }
        try {
            dh.updateData(insertHangerSQl, DataSourceType._SFIS_KAIJIA_STD);
            a = 1;
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("ISN_HangerBinding " + e.getMessage());
        }
        return a;
    }

    //緊急補資料
    public List JJBZL() {
        List list = new ArrayList<>();
        String sqlISN = " SELECT I.M_SN FROM TP.MES_ANO_HANGER_ISN I \n" +
                "INNER JOIN TP.MES_ANO_HANGER H ON I.HANGER_PROCESS_OID=H.HANGER_PROCESS_OID \n" +
                "INNER JOIN TP.MES_ANO_FLY F ON H.FLY_PROCESS_OID=F.ANO_FLY_BIND_OID \n" +
                "INNER JOIN ISNINFO O ON O.ISN=I.M_SN \n" +
                "INNER JOIN MO_D ON MO_D.ISN=I.M_SN \n" +
                "WHERE F.LOG_SYSTEMDATE between trunc(sysdate-3) AND sysdate \n" +
                "and H.STATUS IS NOT NULL and MO_D.NGRP = 'TZH' order by F.LOG_SYSTEMDATE";
        logger.debug("打印SQL：" + sqlISN);
        try {
            list = dh.getDataList(sqlISN, DataSourceType._SFIS_KAIJIA_STD);
            System.out.println(list.toString());
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("JJBZL :" + e.getMessage());
        }
        return list;
    }

    //將所有ISN檢查出來，用來ISN過站
    public HashMap ISNnumber(HashMap mapISN, HashMap map, String thisFlyID) {
        Hashtable ht;
        List<Hashtable> list = new ArrayList<>();
        List<Hashtable> list_big = new ArrayList<>();
        String manyHanger = manipulation(map);
        String[] hanger_idArray = manyHanger.split(",");
        for (int i = 0; i < hanger_idArray.length; i++) {
            String sqlISN = "SELECT M_SN FROM TP.MES_ANO_HANGER_isn where hanger_process_oid=" +
                    " (SELECT * FROM(SELECT h.hanger_process_oid FROM TP.MES_ANO_HANGER_ISN I " +
                    " inner join TP.MES_ANO_HANGER H on I.HANGER_PROCESS_OID=H.HANGER_PROCESS_OID" +
                    " WHERE i.hanger_id='" + hanger_idArray[i].replaceAll("'", "") + "' ORDER BY H.LOG_SYSTEMDATE DESC)" +
                    " where rownum=1)";
            logger.debug("ISNnumber sqlISN:" + sqlISN);
            try {
                list = dh.getDataList(sqlISN, DataSourceType._SFIS_KAIJIA_STD);
                list_big.addAll(list);
            } catch (Exception e) {
                e.printStackTrace();
                logger.debug("ISNnumber :" + e.getMessage());
            }
        }
        for (int i = 0; i < list_big.size(); i++) {
            mapISN.put(i, list_big.get(i).get("M_SN"));
            String updateSNG = "UPDATE ISNINFO SET SNG = '" + thisFlyID + "' WHERE ISN ='" + list_big.get(i).values().toString().replaceAll("\\[", "").replaceAll("\\]", "") + "'";
            logger.debug("ISNnumber updateSNG:" + updateSNG);
            Vector arg = new Vector();
            arg.add(updateSNG);
            try {
                dh.updateData(arg, DataSourceType._SFIS_KAIJIA_STD);
            } catch (Exception e) {
                e.printStackTrace();
                logger.debug("ISNnumber :" + e.getMessage());
            }
        }
        return mapISN;
    }

    //母掛與飛靶綁定
    public int Hanger_FlyBinding(String flyUID, String thisFlyID, HashMap map) {
        int a = 0;
        String manyHanger = manipulation(map);
        Vector insertHangerSQl = new Vector();
        String[] hanger_idArray = manyHanger.split(",");
        String sqla = "insert into TP.MES_ANO_FLY" +
                " (ANO_FLY_BIND_OID, PROJECT_CODE, " +
                "  ANO_FLY_ID,LOG_SYSTEMDATE," +
                "  LOG_EMPLOYEE_OID,BATCH_NO,LAST_SYSTEMDATE)" +
                "  values('" + flyUID + "','COP1721'," + thisFlyID + ",sysdate,'" + uiVO.getLogin_id() + "'," +
                "  " + batch_no(thisFlyID) + ",sysdate)";
        logger.debug("Hanger_FlyBinding sqla :" + sqla);
        insertHangerSQl.add(sqla);
        Vector insertHangerIsnSQl = new Vector();
        for (int i = 0; i < hanger_idArray.length; i++) {
            String sql = "UPDATE TP.MES_ANO_HANGER SET FLY_PROCESS_OID = '" + flyUID + "' WHERE HANGER_PROCESS_OID=(SELECT * FROM(SELECT HANGER_PROCESS_OID FROM TP.MES_ANO_HANGER WHERE HANGER_ID='" + hanger_idArray[i].replaceAll("'", "") + "' ORDER BY LOG_SYSTEMDATE DESC)WHERE ROWNUM=1)";
            logger.debug("Hanger_FlyBinding sql:" + sql);
            insertHangerIsnSQl.add(sql);
        }
        try {
            dh.updateData(insertHangerSQl, DataSourceType._SFIS_KAIJIA_STD);
            dh.updateData(insertHangerIsnSQl, DataSourceType._SFIS_KAIJIA_STD);
            a = 1;
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Hanger_FlyBinding :" + e.getMessage());
        }
        return a;
    }

    //獲取時間，用來寫當天第幾把的
    public long getTime(Date currentTime, Date firstTime) {
        long diff = currentTime.getTime() - firstTime.getTime();
        return diff;
    }

    //Date類型轉換為Calendar類型
    public Calendar dataToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    //檢查是否是當天的方法
    public boolean check_date(String thisFlyID) {
        boolean TT = true;
        Hashtable ht = new Hashtable();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String now = df.format(new Date());
        String last = null;
        String sql = "SELECT to_char(log_systemdate,'yyyy-mm-dd') FROM TP.MES_ANO_FLY WHERE ANO_FLY_ID='" + thisFlyID + "'";
        System.out.println(sql);
        try {
            ht = dh.getDataOne(sql, DataSourceType._SFIS_KAIJIA_STD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (ht != null) {
            last = ht.values().toString().replaceAll("\\[", "").replaceAll("\\]", "");
            if (now.equals(last)) {
                TT = true;
            } else {
                TT = false;
            }
        } else {
            TT = false;
        }
        return TT;
    }

    //當天把數
    public int batch_no(String thisFlyID) {
        int batch_no = 0;
        Hashtable ht = new Hashtable();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String DATE_zao = df.format(new Date());// 00:00:00
        String DATE_wan = df.format(new Date());// 23:59:59
        String sql = "select * from (SELECT batch_no+1 FROM tp.MES_ANO_FLY  " +
                "where  ano_fly_id='" + thisFlyID + "' and   log_systemdate between to_date('" + DATE_zao + " 00:00:00','yyyy/mm/dd hh24:mi:ss')  and to_date('" + DATE_wan + " 23:59:59','yyyy/mm/dd hh24:mi:ss') " +
                "order  by batch_no  desc )where rownum=1";
        logger.debug("batch_no sql:" + sql);
        try {
            ht = dh.getDataOne(sql, DataSourceType._SFIS_KAIJIA_STD);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("batch_no :" + e.getMessage());
        }
        if (ht == null) {
            batch_no = 1;
        } else {
            batch_no = Integer.parseInt(ht.values().toString().replaceAll("\\[", "").replaceAll("\\]", ""));
        }
        return batch_no;
    }

    //生成虛擬母掛號
    public long virtualHangerCode_function() {
        long VHC = System.currentTimeMillis();
        return VHC;
    }

    //生成txt文件的方法
    public void txtfile(String device, HashMap map, String op) {

        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String hehe = dateFormat.format(now);
        String name = "D:\\" + op + "_" + device + "BatchPass" + hehe + ".txt";

        File file = new File(name);
        String content = map.toString();
        FileWriter fw = null;
        BufferedWriter bw = null;

        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
