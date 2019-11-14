package mes_sfis.client.model.service;

import base.enums.DataSourceType;
import base.vo.UI_InitVO;
import base.vo.URL_VO;
import mes_sfis.client.model.bean.MesPackConfig;
import mes_sfis.client.model.bean.Product;
import mes_sfis.client.util.DataHandler;

import java.math.BigDecimal;
import java.net.URL;
import java.util.*;


/**
 * Created by Chris1_Liao on 2018/4/2.
 */
public class PackageService extends BaseService {
    MesPackConfig mpc = new MesPackConfig();
    UI_InitVO uiVO;
    DataHandler dh;

    public List<Product> productList = new ArrayList<Product>();

    boolean isFirst = false;

    //TODO 建立對應 MES_PACK_CONFIG表的java bean
    //public PackingConfig pc;

    public PackageService(UI_InitVO uiVO) {
        this.uiVO = uiVO;
        dh = new DataHandler(uiVO);
    }

    /**
     * 作為檢查成品碼是否正確的主方法
     *
     * @param ssn
     * @throws Exception
     */
    public String process(String ssn) throws Exception {
        String isn = ssn.substring(0, 20).replace("+", "");
        String eeee = isn.substring(11, 15);
        System.out.println("isn:" + isn);
        System.out.println("eeee:" + eeee);

        if (this.isScrapped(isn)) {
            throw new Exception("已報廢");
        }

        return isn;
    }

    /**
     * 每箱第一個刷入成品碼必須呼叫
     *
     * @param ssn
     * @throws Exception
     */
    public String checkFirstProduct(String ssn) throws Exception {
        String message = "";
        String isn = ssn.substring(0, 20).replace("+", "");
        String eeee = isn.substring(11, 15);
        System.out.println("isn:" + isn);
        System.out.println("eeee:" + eeee);

        /**
         * TODO  INIITAL PackingConfig  打印需要檢查的東西與信息都在裡面
         */
        return message;
    }

    /**
     * 新增掃碼
     *
     * @param ssn
     * @return
     * @throws Exception
     */
    public String addProduct(String ssn) throws Exception {
        String message = "";
        //判斷是否第一筆，第一筆數據必須initial　PackingConfig

        /**
         * TODO  INIITAL PackingConfig  打印需要檢查的東西與信息都在裡面
         */

        Product product = this.initProductByCode(ssn);

        productList.add(product);

        return message;
    }

    private Product initProductByCode(String ssn) throws Exception {
        String isn = ssn.substring(0, 20).replace("+", "");
        Product product = new Product(isn, dh);

        String eeee = isn.substring(11, 15);
        System.out.println("isn:" + isn);
        System.out.println("eeee:" + eeee);

        return product;
    }

    /**
     * TODO 檢查是否完成所有製程 SELECT 1 FROM MO_ROUTE WHERE ISN = ISN AND GRP =   PackingConfig.SFIS_CHECK_GRP
     */
    public boolean checkFinishRoute(String isn) {
        return false;
    }

    /**
     * @Author:zhurenwei
     * @Description:獲得字段名
     * @Date: 2018/5/27 0027
     */
    public String getCheckField(String PROJECT_CODE) {
        String sql = "select SFIS_CHECK_FIELD from mes_pack_config where PROJECT_CODE='" + PROJECT_CODE + "'and rownum=1";
        Hashtable ht = null;
        try {
            ht = dh.getDataOne(sql, DataSourceType._MultiCompanyCurrentDB);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (ht != null) {
            return ht.get("SFIS_CHECK_FIELD").toString();
        } else {
            return null;
        }
    }

    public String getBinCheckField(String PROJECT_CODE) {
        String sql = "select CHECK_FIELD from mes_pack_config where PROJECT_CODE='" + PROJECT_CODE + "'and rownum=1";
        Hashtable ht = null;
        try {
            ht = dh.getDataOne(sql, DataSourceType._MultiCompanyCurrentDB);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (ht != null) {
            return ht.get("CHECK_FIELD").toString();
        } else {
            return null;
        }
    }
    public String getCheckEEEEValue(String ISN,String value,String table){
        String sql ="select "+value+" from "+table+" where isn = '"+ISN+"'";
        Hashtable ht = null;
        try {
            ht = dh.getDataOne(sql, DataSourceType._SFIS_KAIJIA_STD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("----------------+++++++++++++++--------------"+ht.get(value));
        if (ht != null&&ht.get(value)!=null) {
            System.out.println("這是空1");
            return ht.get(value).toString();
        }else if(ht.get(value)==null||ht.get(value).equals("")){
            System.out.println("這是空2");
            return "";
        } else{
            System.out.println("這是空3");
            return null;
        }
    }

    public String getCheckBinValue(String ISN,String value,String table){
        String sql ="select * from (select "+value+" from "+table+" where  barcode='"+ISN+"' and ump_type like '%T1%'  order by ump_timestamp desc) where  rownum =1 ";
        Hashtable ht = null;
        try {
            ht = dh.getDataOne(sql, DataSourceType._SFIS_KAIJIA_STD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("----------------+++++++++++++++--------------"+ht.get(value));
        if (ht != null&&ht.get(value)!=null) {
            System.out.println("這是空1");
            return ht.get(value).toString();
        }else if(ht.get(value)==null||ht.get(value).equals("")){
            System.out.println("這是空2");
            return "";
        } else{
            System.out.println("這是空3");
            return null;
        }
    }


    /**
     * 檢查是否報廢
     *
     * @param isn
     * @return
     * @throws Exception
     */
    public boolean isScrapped(String isn) throws Exception {
        boolean isScrapped = true;
        String sql = "select 1 from tp.mo_d t where t.isn = '" + isn + "' and t.status ='4'";
        try {
            Hashtable ht = dh.getDataOne(sql, DataSourceType._SFIS_KAIJIA_STD);
            if (ht != null) {
                isScrapped = true;//已報廢
            } else {
                isScrapped = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("是否報廢查詢錯誤:" + e.getMessage());
        }
        return isScrapped;
    }
	
	public boolean isSpec(String ISNCode){
		try{
			DataHandler dh = new DataHandler(uiVO);
			String ssl = "select isn from mes_pack_config_spisn where isn = '" + ISNCode + "'";
			Hashtable sst = dh.getDataOne(ssl, DataSourceType._MultiCompanyCurrentDB);
			if(sst != null ){
				return true;
			}else{
				return false;
			}
		}catch (Exception e) {
            e.printStackTrace();
        }
		return false;
	}
	
	public int dateCheck(String ISNCode){
		int dateflag = 0;
		try{
			String sql1 = "select * from mo_route where grp = 'TZN' AND intime between TO_DATE('2018/11/21 20:00:00','yyyy/mm/dd hh24:mi:ss') AND TO_DATE('2018/11/27 08:00:00','yyyy/mm/dd hh24:mi:ss') AND ISN = '" + ISNCode + "'";
			String sql2 = "select * from mo_route where grp = 'TZN' AND intime > TO_DATE('2018/11/27 08:00:00','yyyy/mm/dd hh24:mi:ss') AND ISN = '" + ISNCode + "'";
			DataHandler dh = new DataHandler(uiVO);
			Hashtable ht1 = dh.getDataOne(sql1, DataSourceType._SFIS_KAIJIA_STD);
			if(ht1 != null ){
				return 1;
			}else{
				Hashtable ht2 = dh.getDataOne(sql2, DataSourceType._SFIS_KAIJIA_STD);
				if(ht2 != null ){
					return 2;
				}else{
					return 0;
				}
			}
		}catch (Exception e) {
            e.printStackTrace();
        }
		return dateflag;
	}

    public MesPackConfig getConfigByEeeeProjectCode(String PROJECT_CODE, String check_value,String check_bin_value,String standStr,String NEW_OLD_MATERIALS,String Europe,String ISNCode,int dfg) throws Exception {
        try {
            DataHandler dh = new DataHandler(uiVO);
            String sql = "";
			/*if("W".equals(check_value) && "COP1721".equals(PROJECT_CODE) && "PRQ".equals(standStr) && "1".equals(NEW_OLD_MATERIALS) && "N".equals(Europe))
				check_bin_value = "2";*/
            if(check_bin_value==null||check_bin_value.equals("")){
                sql = "SELECT * FROM mes_pack_config where rownum = 1 and SFIS_CHECK_VALUE ='" + check_value + "'and CHECK_FIELD_VALUE is null and PROJECT_CODE='" + PROJECT_CODE + "'";
            }else {
                sql = "SELECT * FROM mes_pack_config where rownum = 1 and SFIS_CHECK_VALUE ='" + check_value + "'and CHECK_FIELD_VALUE ='" + check_bin_value + "'and PROJECT_CODE='" + PROJECT_CODE + "'";
            }
			/*if("W".equals(check_value) && "COP1721".equals(PROJECT_CODE) && "PRQ".equals(standStr) && "1".equals(NEW_OLD_MATERIALS) && "N".equals(Europe))
				sql += "and kj_pn = 'YFM00Y0J9-113-N'";*/
			//添加階段
			sql += " and MOSTAGE = '" + standStr + "'";
			sql += " and NEW_OLD_MATERIALS = '" + NEW_OLD_MATERIALS + "'";
			sql += " and ISEUROPE = '" + Europe + "'";
			String ssl = "select isn from mes_pack_config_spisn where isn = '" + ISNCode + "'";
			Hashtable sst = dh.getDataOne(ssl, DataSourceType._MultiCompanyCurrentDB);
			if(sst != null ){
				sql = sql.replaceAll("mes_pack_config","mes_pack_config_sp");
			}
			if(dfg == 2 && (!sql.contains("mes_pack_config_sp")) && "PRQ".equals(standStr)){
				sql = sql.replaceAll("mes_pack_config","mes_pack_config_date");
			}
            Hashtable ht = dh.getDataOne(sql, DataSourceType._MultiCompanyCurrentDB);
            if (ht != null) {
                 if (null == ht.get("PROJECT_CODE")) {
                    mpc.setProjectCode("");
                } else {
                    mpc.setProjectCode(ht.get("PROJECT_CODE").toString());
                }
                if (null == ht.get("OEMPN")) {
                    mpc.setOemPN("");
                } else {
                    mpc.setOemPN(ht.get("OEMPN").toString());
                }
                if (null == ht.get("APPLEPN")) {
                    mpc.setApplepn("");
                } else {
                    mpc.setApplepn(ht.get("APPLEPN").toString());
                }
                if (null == ht.get("STAGE")) {
                    mpc.setStage("");
                } else {
                    mpc.setStage(ht.get("STAGE").toString());
                }
                if (null == ht.get("LOCATION")) {
                    mpc.setLocation("");
                } else {
                    mpc.setLocation(ht.get("LOCATION").toString());
                }
                if (null == ht.get("REV")) {
                    mpc.setRev("");
                } else {
                    mpc.setRev(ht.get("REV").toString());
                }
                if (null == ht.get("DESCRIPTION")) {
                    mpc.setDescription("");
                } else {
                    mpc.setDescription(ht.get("DESCRIPTION").toString());
                }
                if (null == ht.get("CONFIG")) {
                    mpc.setConfig("");
                } else {
                    mpc.setConfig(ht.get("CONFIG").toString());
                }
                if (null == ht.get("VENDOR")) {
                    mpc.setVendor("");
                } else {
                    mpc.setVendor(ht.get("VENDOR").toString());
                }
                if (null == ht.get("VENDER")) {
                    mpc.setVender("");
                } else {
                    mpc.setVender(ht.get("VENDER").toString());
                }
                if (null == ht.get("OEMDRI")) {
                    mpc.setOemdri("");
                } else {
                    mpc.setOemdri(ht.get("OEMDRI").toString());
                }
                if (null == ht.get("PER")) {
                    mpc.setPer("");
                } else {
                    mpc.setPer(ht.get("PER").toString());
                }
                if (null == ht.get("REQ")) {
                    mpc.setReq("");
                } else {
                    mpc.setReq(ht.get("REQ").toString());
                }
                if (null == ht.get("SHIP_SHORT")) {
                    mpc.setShipShort("");
                } else {
                    mpc.setShipShort(ht.get("SHIP_SHORT").toString());
                }
                if (null == ht.get("ETD")) {
                    mpc.setEtd("");
                } else {
                    mpc.setEtd(ht.get("ETD").toString());
                }
                if (null == ht.get("ETA")) {
                    mpc.setEta("");
                } else {
                    mpc.setEta(ht.get("ETA").toString());
                }
                if (null == ht.get("KJ_PN")) {
                    mpc.setKJPN("");
                } else {
                    mpc.setKJPN(ht.get("KJ_PN").toString());
                }
                if (null == ht.get("BATCHNO")) {
                    mpc.setBatChno("");
                } else {
                    mpc.setBatChno(ht.get("BATCHNO").toString());
                }
                if (null == ht.get("LC")) {
                    mpc.setLc("");
                } else {
                    mpc.setLc(ht.get("LC").toString());
                }
                if (null == ht.get("VERSION")) {
                    mpc.setVersion(null);
                } else {
                    mpc.setVersion((BigDecimal) ht.get("VERSION"));
                }
                if (null == ht.get("SFIS_CHECK_FIELD")) {
                    mpc.setSfisCheckField("");
                } else {
                    mpc.setSfisCheckField(ht.get("SFIS_CHECK_FIELD").toString());
                }
                if (null == ht.get("SFIS_CHECK_VALUE")) {
                    mpc.setSfisCheckValue("");
                } else {
                    mpc.setSfisCheckValue(ht.get("SFIS_CHECK_VALUE").toString());
                }
                if (null == ht.get("SFIS_CHECK_GRP")) {
                    mpc.setSfisCheckGrp("");
                } else {
                    mpc.setSfisCheckGrp(ht.get("SFIS_CHECK_GRP").toString());
                }
                if (null == ht.get("PCS_CARTON")) {
                    mpc.setPcsCarton(null);
                } else {
                    mpc.setPcsCarton((BigDecimal) ht.get("PCS_CARTON"));
                }
                if (null == ht.get("CARTON_PALLET")) {
                    mpc.setCartonPallet(null);
                } else {
                    mpc.setCartonPallet((BigDecimal) ht.get("CARTON_PALLET"));
                }
                if (null == ht.get("LAST_CARTON_DATE")) {
                    mpc.setLastCartonDate("");
                } else {
                    mpc.setLastCartonDate(ht.get("LAST_CARTON_DATE").toString());
                }
                if (null == ht.get("LAST_CARTON_SN")) {
                    mpc.setLastCartonSn(null);
                } else {
                    mpc.setLastCartonSn(ht.get("LAST_CARTON_SN").toString());
                }
                if (null == ht.get("LAST_PALLET_DATE")) {
                    mpc.setLastPalletDate("");
                } else {
                    mpc.setLastPalletDate(ht.get("LAST_PALLET_DATE").toString());
                }
                if (null == ht.get("LAST_PALLET_SN")) {
                    mpc.setLastPalletSn(null);
                } else {
                    mpc.setLastPalletSn(ht.get("LAST_PALLET_SN").toString());
                }
                if (null == ht.get("SITE_CODE")) {
                    mpc.setSiteCode(null);
                } else {
                    mpc.setSiteCode(ht.get("SITE_CODE").toString());
                }
                if (null == ht.get("CHECK_FIELD")) {
                    mpc.setCheckField("");
                } else {
                    mpc.setCheckField(ht.get("CHECK_FIELD").toString());
                }
                if (null == ht.get("CHECK_FIELD_VALUE")) {
                    mpc.setCheckFieldValue("");
                } else {
                    mpc.setCheckFieldValue(ht.get("CHECK_FIELD_VALUE").toString());
                }
            } else {
                mpc = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mpc;
    }

    public boolean isTerminalWorking(String grp, String ISNCode) throws Exception {
        boolean isTerminalWorking = true;

        String sql = "select 1 from MO_ROUTE where GRP = '" + grp + "' and ISN ='" + ISNCode + "'";
        try {
            Hashtable ht = dh.getDataOne(sql, DataSourceType._SFIS_KAIJIA_STD);
            if (ht != null) {
                isTerminalWorking = true;//正常
            } else {
                isTerminalWorking = false;//不正常
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isTerminalWorking;
    }

    public boolean checkISN(String isn, String eeee) throws Exception {
        boolean checkISN = true;
        String colorCode = "";
        //ANDY修改，JVMG KRWG ??洲版玻璃工程?构代?
        if ("JPT3".equals(eeee)||"JVMG".equals(eeee)) {
            colorCode = "B";
        }
        if ("JPT4".equals(eeee)||"KRWG".equals(eeee)) {
            colorCode = "W";
        }
        String sql = "select 1 from ISNINFO where ISN = '" + isn + "' and SNE ='" + colorCode + "'";
        try {
            Hashtable ht = dh.getDataOne(sql, DataSourceType._SFIS_KAIJIA_STD);
            if (ht != null) {
                checkISN = true;//存在
            } else {
                checkISN = false;//已報廢
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return checkISN;
    }

    public boolean insertCarton(HashMap<String, String> insertMap) throws Exception {

        boolean insertOk = true;
        String sql = "";
        sql += " INSERT INTO MES_PACK_CARTON( ";
        sql += " CARTON_OID,                  ";
        sql += " PROJECT_CODE,                ";
        sql += " KJ_PN,                       ";
        sql += " CARTON_NO,                   ";
        sql += " DATE_CODE,                   ";
        sql += " QTY,                         ";
        sql += " LOG_SYSTEMDATE,              ";
        sql += " LOG_EMPLOYEE_OID,             ";
        sql += " SNAPSHOT,             ";
        sql += " JSON,             ";
        sql += " MEMO,             ";
        sql += " IS_CLOSE,             ";
        sql += " IS_BREAK,             ";
        sql += " CLOSE_SYSTEMDATE,             ";
        sql += " BREAK_SYSTEMDATE,             ";
        sql += " STATUS,             ";
        sql += " LAST_SYSTEMDATE,             ";
        sql += " PALLET_OID,             ";
        sql += " PICK_OID,             ";
        sql += " MO_ID,             ";
        sql += " OEMPN             ";
        sql += " )values( ";
        sql += insertMap.get("CARTON_OID") + ", ";
        sql += "'" + insertMap.get("PROJECT_CODE") + "'" + ", ";
        sql += "'" + insertMap.get("KJ_PN") + "'" + ", ";
        sql += "'" + insertMap.get("CARTON_NO") + "'" + ", ";
        sql += "'" + insertMap.get("DATE_CODE") + "'" + ", ";
        sql += "'" + insertMap.get("QTY") + "'" + ", ";
        sql += insertMap.get("LOG_SYSTEMDATE") + ", ";
        sql += "'" + insertMap.get("LOG_EMPLOYEE_OID") + "'" + ", ";
        sql += "'" + insertMap.get("SNAPSHOT") + "'" + ", ";
        sql += "'" + insertMap.get("JSON") + "'" + ", ";
        sql += "'" + insertMap.get("MEMO") + "'" + ", ";
        sql += insertMap.get("IS_CLOSE") + ", ";
        sql += insertMap.get("IS_BREAK") + ", ";
        sql += insertMap.get("CLOSE_SYSTEMDATE") + ", ";
        sql += "'" + insertMap.get("BREAK_SYSTEMDATE") + "'" + ", ";
        sql += "'" + insertMap.get("STATUS") + "'" + ", ";
        sql += insertMap.get("LAST_SYSTEMDATE") + ", ";
        sql += "'" + insertMap.get("PALLET_OID") + "'" + ", ";
        sql += "'" + insertMap.get("PICK_OID") + "'" + ", ";
        sql += "'" + insertMap.get("MO_ID") + "'" + ", ";
        sql += "'" + insertMap.get("OEMPN") + "')";
        try {
            Vector ht = dh.updateData(sql, DataSourceType._MultiCompanyCurrentDB);
            if (ht != null) {
                insertOk = true;//ok
            } else {
                insertOk = false;//升級失敗
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return insertOk;

    }

    public boolean insertCartonISN(List<Product> updateMap, int productNum) throws Exception {
        boolean updateOk = true;

        Vector arg = new Vector();

        for (int i = 0; i < productNum; i++) {
            String sql = "";
            sql += " INSERT INTO MES_PACK_CARTON_ISN( ";
            sql += " CARTON_OID,                  ";
            sql += " PROJECT_CODE,                ";
            sql += " M_SN,                       ";
            sql += " S_SN,                   ";
            sql += " C_SN,                   ";
            sql += " SN1,                         ";
            sql += " SN2,              ";
            sql += " SN3,              ";
            sql += " SN4,              ";
            sql += " SN5,              ";
            sql += " SN6,              ";
            sql += " SN7,              ";
            sql += " SN8,              ";
            sql += " SN9,              ";
            sql += " SN10,              ";
            sql += " STATUS             ";
            sql += " )values(                     ";
            sql += "'" + updateMap.get(i).getCartonOid() + "'" + ", ";
            sql += "'" + updateMap.get(i).getProjectCode() + "'" + ", ";
            sql += "'" + updateMap.get(i).getMSN() + "'" + ", ";
            sql += "'" + updateMap.get(i).getSSN() + "'" + ", ";
            sql += "'" + updateMap.get(i).getCSN() + "'" + ", ";
            sql += "'" + updateMap.get(i).getSN1() + "'" + ", ";
            sql += "'" + updateMap.get(i).getSN2() + "'" + ", ";
            sql += "'" + updateMap.get(i).getSN3() + "'" + ", ";
            sql += "'" + updateMap.get(i).getSN4() + "'" + ", ";
            sql += "'" + updateMap.get(i).getSN5() + "'" + ", ";
            sql += "'" + updateMap.get(i).getSN6() + "'" + ", ";
            sql += "'" + updateMap.get(i).getSN7() + "'" + ", ";
            sql += "'" + updateMap.get(i).getSN8() + "'" + ", ";
            sql += "'" + updateMap.get(i).getSN9() + "'" + ", ";
            sql += "'" + updateMap.get(i).getSN10() + "'" + ", ";
            sql += updateMap.get(i).getStatus() + ")";

            arg.add(sql);
        }

        DataHandler dh = new DataHandler(uiVO);
        try {
            Vector result = dh.updateData(arg, DataSourceType._MultiCompanyCurrentDB);
            if (result != null) {
                updateOk = true;//ok
            } else {
                updateOk = false;//升級失敗
            }
        } catch (Exception e) {
            e.printStackTrace();

            throw new Exception("錯誤信息:" + e.getMessage());
        }
        return updateOk;
    }

    public String findByCartonNo(String cartonNo) throws Exception {
        String cartonOid = "";
        String sql = "select carton_oid from mes_pack_carton where carton_no = '" + cartonNo + "'";
        try {
            Hashtable ht = dh.getDataOne(sql, DataSourceType._MultiCompanyCurrentDB);
            if (ht != null) {
                cartonOid = (String) ht.get("CARTON_OID");//正常
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cartonOid;
    }

    public boolean isRepeatISN(String ISNCode) throws Exception {
        boolean isRepeat = false;
        String sql = "select  c.is_Break from mes_pack_carton c join mes_pack_carton_isn i on c.carton_oid=i.carton_oid where i.M_SN = '"+ISNCode+"'";
        String sql2 = "select SN3 from mes_pack_carton_isn where M_SN  = '"+ISNCode+"'";
        try {
            Hashtable a = null;
            a = dh.getDataOne(sql, DataSourceType._MultiCompanyCurrentDB);
            Hashtable b = null;
            b = dh.getDataOne(sql2, DataSourceType._MultiCompanyCurrentDB);

            if(a!=null&&b!=null){
                if(a.get("IS_BREAK").toString().equals("0")){
                    if(b.get("SN3")==null){
                        System.out.println(1);
                        isRepeat=true;
                    }else if(b.get("SN3").equals("1")){
                        isRepeat = false;
                        System.out.println(2);
                    }else{
                        System.out.println(3);
                        isRepeat=true;
                    }

                }else{
                    System.out.println(4);
                    isRepeat = false;
                }
            }else{
                System.out.println(5);
                isRepeat = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return isRepeat;
    }

    public List isISNExist(String SSN) throws Exception {
        List ISN = new ArrayList();
        String sql = "select ISN,CSSN,SNA,SND from ISNINFO where SSN='" + SSN + "'";
        try {
            Hashtable ht = dh.getDataOne(sql, DataSourceType._SFIS_KAIJIA_STD);
            if (ht != null) {
                ISN.add((String) ht.get("ISN"));
                ISN.add((String) ht.get("CSSN"));
                if ("".equals((String) ht.get("SNA")) || null == ht.get("SNA")) {
                    ISN.add("");
                } else {
                    ISN.add((String) ht.get("SNA"));
                }
				if ("".equals((String) ht.get("SND")) || null == ht.get("SND")) {
                    ISN.add("");
                } else {
                    ISN.add((String) ht.get("SND"));
                }
            } else {
                ISN = null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ISN;
    }
	
    public String isNewMaterial(String ISN){
        String isNew="0";

            //String sql="select (case when intime>to_date('2018-09-29','YYYY-MM-DD') then 1 else 0 end )as \"ISNEW\"  from mo_route  where  GRP like 'TZN' and isn = '"+ISN+"' and rownum=1";
        String sql="select (case when intime>to_date('2018/10/24 18:30:00','yyyy/mm/dd hh24:mi:ss') then 1 else 0 end )as ISNEW   from mo_route mr\n" +
                "join isninfo ii on ii.isn = mr.isn\n" +
                "where  mr.GRP = 'TZU'\n" +
                "and ii.sne = 'W'  \n" +
                "and MR.isn = '"+ISN+"' and rownum = 1 ";
        try {
            Hashtable ht=dh.getDataOne(sql,DataSourceType._SFIS_KAIJIA_STD);
            if(ht!=null){
                    isNew=ht.get("ISNEW").toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isNew ;
    }

    public void sealing(String cartoNo) throws Exception {

        String sql = "update mes_pack_carton set is_close='1',log_systemdate='sysdate',close_systemdate='sysdate',last_systemdate='sysdate' where carton_no='" + cartoNo + "' ";
        try {
            Vector ht = dh.updateData(sql, DataSourceType._MultiCompanyCurrentDB);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public String checkUmpMaterial(String ISN){
//        String sql = "select bin from tp.mes_ump_log where 1=1 and barcode='" + ISN + "' or barcode2='" + ISN + "' or barcode3='" + ISN + "') and UMP_TYPE LIKE 'T1%' order by ump_timestamp desc";
        String sql = "select bin from tp.mes_ump_log where 1=1 and barcode='" + ISN + "' and ump_type like '%T1%'  order by ump_timestamp desc";
        List<Hashtable> result = null;
        try {
            result = dh.getDataList(sql, DataSourceType._SFIS_KAIJIA_STD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String backResult;
        if (result.get(0) != null || result.get(0).get("BIN") != "") {
            backResult = (String) result.get(0).get("BIN");
        } else {
            backResult = "";
        }

        return backResult;
    }

    public static void main(String[] args) throws Exception {

        URL CODEBASE = new URL("http://127.0.0.1:8088/PEGAMES/");
        URL PROXY_SERVER = new URL("http://10.162.244.237:8088/PEGAMES/");
        URL HTTP_SERVER = new URL("http://127.0.0.1:8088/PEGAMES/");
        URL FILE_SERVER = new URL("http://10.162.244.237:8088/PEGAMES/**/");
        URL_VO codeBase = new URL_VO(CODEBASE, PROXY_SERVER, HTTP_SERVER, FILE_SERVER);

        UI_InitVO uiVO2 = new UI_InitVO("zh_TW", "3FC0955A8EF33408E05363F4A20AED66", "3FC0955A8EF23408E05363F4A2-31276", "鎧嘉電腦配件(蘇州)有限公司", "ENAME", codeBase, "USER NAME TEST", "SYSTEM", "系統管理者 鎧嘉電腦配件(蘇州)有限公司", "3B5B598592A53EB2E05363F4A20A-BU2");
        DataHandler dh2 = new DataHandler(uiVO2);



        boolean isRepeat = false;
        String sql = "select  c.is_Break from mes_pack_carton c join mes_pack_carton_isn i on c.carton_oid=i.carton_oid where i.S_SN = 'GKP82132AMCJPT45D+37+12345678901+11NNNNNS1225U1225S1256KA22502'";
        String sql2 = "select SN3 from mes_pack_carton_isn where S_SN  = 'GKP82132AMCJPT45D+37+12345678901+11NNNNNS1225U1225S1256KA22502'";
        try {
            Hashtable a = null;
            a = dh2.getDataOne(sql, DataSourceType._MultiCompanyCurrentDB);
            Hashtable b = null;
            b = dh2.getDataOne(sql2, DataSourceType._MultiCompanyCurrentDB);
            if(a!=null&&b!=null){
                if(a.get("IS_BREAK").toString().equals("0")){
                    if(b.get("SN3")==null){
                        System.out.println("不能裝箱");
                    }else if(b.get("SN3").equals("1")){
                        System.out.println("可以裝箱");
                    }else{
                        System.out.println("不能裝箱");
                    }

                }else{
                    System.out.println("可以裝箱");
                }
            }else{
                System.out.println("可以裝箱");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public int getCountOne(String isnCode) {
        String sql = "SELECT COUNT(*) as COUNTNUM FROM MO_ROUTE WHERE GRP = 'TZK' AND TO_CHAR(INTIME, 'YYYYMMDD') < '20180727' AND ISN = '"+isnCode+"'";
        try {
            Hashtable ht = dh.getDataOne(sql, DataSourceType._SFIS_KAIJIA_STD);
            if (ht == null) {
                return 0;
            }
            BigDecimal a = (BigDecimal) ht.get("COUNTNUM");
            int result = a.intValue();
            System.out.println("------------------------"+result);
            return result;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getCountTwo(String isnCode) {
        String sql = "SELECT COUNT(*) as COUNTNUMTWO  FROM MES_CTRL WHERE GRP = 'AAA' AND ISN = '"+isnCode+"'";
        try {
            Hashtable ht = dh.getDataOne(sql, DataSourceType._SFIS_KAIJIA_STD);
            if (ht == null) {
                return 0;
            }
            BigDecimal a = (BigDecimal) ht.get("COUNTNUMTWO");
            int result = a.intValue();
            System.out.println("------------------------"+result);
            return result;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    public int checkStatus(String isnCode) {
        //String sql = "select count(*) as COUNTNUM from mo_d where status = '0' and grp = 'QMF' and isn = '"+isnCode+"'";
		String sql = "select count(*) as COUNTNUM from mo_d md join route_step rs on rs.step = md.step  where isn = '" + isnCode + "' and md.status = 0 and md.nstep like '0' and rs.ridx between (select ridx from mo_d md"
		+ " join route_step rs on rs.route = md.route where isn = '" + isnCode + "' and rs.grp = 'QMF') and 10000";
        try {
            Hashtable ht = dh.getDataOne(sql, DataSourceType._SFIS_KAIJIA_STD);
            if (ht == null) {
                return 0;
            }
            BigDecimal a = (BigDecimal) ht.get("COUNTNUM");
            int result = a.intValue();
            return result;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String getSNEName(String s) {
        String sql = "select sne as SNE from isninfo where isn = '"+s+"'";
        String SNE = "";
        try {
            Hashtable ht = dh.getDataOne(sql, DataSourceType._SFIS_KAIJIA_STD);
            if (ht != null) {
                SNE = ht.get("SNE").toString();
            }
            return SNE;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SNE;
    }

}
