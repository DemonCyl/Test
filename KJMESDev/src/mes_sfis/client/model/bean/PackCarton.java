package mes_sfis.client.model.bean;

import base.vo.UI_InitVO;
import base.vo.URL_VO;
import mes_sfis.client.util.DataHandler;
import oracle.sql.BLOB;
import oracle.sql.NCLOB;

import java.net.URL;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

/**
 * Created by Chris1_Liao on 2018/4/3.
 */
public class PackCarton extends BaseBean{
    private  String CARTON_OID;
    private  String PROJECT_CODE;
    private  String  KJ_PN;
    private  String  CARTON_NO;
    private  String  DATE_CODE;
    private   Date LOG_SYSTEMDATE;
    private  String   LOG_EMPLOYEE_OID;
    private BLOB SNAPSHOT;
    private NCLOB JSON;
    private  String  MEMO;
    private  int  IS_CLOSE;
    private  int  IS_BREAK;
    private  Date  CLOSE_SYSTEMDATE;
    private  Date BREAK_SYSTEMDATE;
    private  int STATUS;
    private  Date LAST_SYSTEMDATE;
    private  String PALLET_OID;
    private  String MO_ID;
    private  int  QTY;
    private  String PICK_OID;

    public String getCARTON_OID() {
        return CARTON_OID;
    }

    public void setCARTON_OID(String CARTON_OID) {
        this.CARTON_OID = CARTON_OID;
    }

    public String getPROJECT_CODE() {
        return PROJECT_CODE;
    }

    public void setPROJECT_CODE(String PROJECT_CODE) {
        this.PROJECT_CODE = PROJECT_CODE;
    }

    public String getKJ_PN() {
        return KJ_PN;
    }

    public void setKJ_PN(String KJ_PN) {
        this.KJ_PN = KJ_PN;
    }

    public String getCARTON_NO() {
        return CARTON_NO;
    }

    public void setCARTON_NO(String CARTON_NO) {
        this.CARTON_NO = CARTON_NO;
    }

    public String getDATE_CODE() {
        return DATE_CODE;
    }

    public void setDATE_CODE(String DATE_CODE) {
        this.DATE_CODE = DATE_CODE;
    }

    public Date getLOG_SYSTEMDATE() {
        return LOG_SYSTEMDATE;
    }

    public void setLOG_SYSTEMDATE(Date LOG_SYSTEMDATE) {
        this.LOG_SYSTEMDATE = LOG_SYSTEMDATE;
    }

    public String getLOG_EMPLOYEE_OID() {
        return LOG_EMPLOYEE_OID;
    }

    public void setLOG_EMPLOYEE_OID(String LOG_EMPLOYEE_OID) {
        this.LOG_EMPLOYEE_OID = LOG_EMPLOYEE_OID;
    }

    public BLOB getSNAPSHOT() {
        return SNAPSHOT;
    }

    public void setSNAPSHOT(BLOB SNAPSHOT) {
        this.SNAPSHOT = SNAPSHOT;
    }

    public NCLOB getJSON() {
        return JSON;
    }

    public void setJSON(NCLOB JSON) {
        this.JSON = JSON;
    }

    public String getMEMO() {
        return MEMO;
    }

    public void setMEMO(String MEMO) {
        this.MEMO = MEMO;
    }

    public int getIS_CLOSE() {
        return IS_CLOSE;
    }

    public void setIS_CLOSE(int IS_CLOSE) {
        this.IS_CLOSE = IS_CLOSE;
    }

    public int getIS_BREAK() {
        return IS_BREAK;
    }

    public void setIS_BREAK(int IS_BREAK) {
        this.IS_BREAK = IS_BREAK;
    }

    public Date getCLOSE_SYSTEMDATE() {
        return CLOSE_SYSTEMDATE;
    }

    public void setCLOSE_SYSTEMDATE(Date CLOSE_SYSTEMDATE) {
        this.CLOSE_SYSTEMDATE = CLOSE_SYSTEMDATE;
    }

    public Date getBREAK_SYSTEMDATE() {
        return BREAK_SYSTEMDATE;
    }

    public void setBREAK_SYSTEMDATE(Date BREAK_SYSTEMDATE) {
        this.BREAK_SYSTEMDATE = BREAK_SYSTEMDATE;
    }

    public int getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(int STATUS) {
        this.STATUS = STATUS;
    }

    public Date getLAST_SYSTEMDATE() {
        return LAST_SYSTEMDATE;
    }

    public void setLAST_SYSTEMDATE(Date LAST_SYSTEMDATE) {
        this.LAST_SYSTEMDATE = LAST_SYSTEMDATE;
    }

    public String getPALLET_OID() {
        return PALLET_OID;
    }

    public void setPALLET_OID(String PALLET_OID) {
        this.PALLET_OID = PALLET_OID;
    }

    public String getMO_ID() {
        return MO_ID;
    }

    public void setMO_ID(String MO_ID) {
        this.MO_ID = MO_ID;
    }

    public int getQTY() {
        return QTY;
    }

    public void setQTY(int QTY) {
        this.QTY = QTY;
    }

    public String getPICK_OID() {
        return PICK_OID;
    }

    public void setPICK_OID(String PICK_OID) {
        this.PICK_OID = PICK_OID;
    }

    private String dateCode;

    public String getDateCode() {
        return dateCode;
    }

    public void setDateCode(String dateCode) {
        this.dateCode = dateCode;
    }

    @Override
    public String toString() {
        return "PackCarton{" +
                "CARTON_OID='" + CARTON_OID + '\'' +
                ", PROJECT_CODE='" + PROJECT_CODE + '\'' +
                ", KJ_PN='" + KJ_PN + '\'' +
                ", CARTON_NO='" + CARTON_NO + '\'' +
                ", DATE_CODE='" + DATE_CODE + '\'' +
                ", LOG_SYSTEMDATE=" + LOG_SYSTEMDATE +
                ", LOG_EMPLOYEE_OID='" + LOG_EMPLOYEE_OID + '\'' +
                ", SNAPSHOT=" + SNAPSHOT +
                ", JSON=" + JSON +
                ", MEMO='" + MEMO + '\'' +
                ", IS_CLOSE=" + IS_CLOSE +
                ", IS_BREAK=" + IS_BREAK +
                ", CLOSE_SYSTEMDATE=" + CLOSE_SYSTEMDATE +
                ", BREAK_SYSTEMDATE=" + BREAK_SYSTEMDATE +
                ", STATUS=" + STATUS +
                ", LAST_SYSTEMDATE=" + LAST_SYSTEMDATE +
                ", PALLET_OID='" + PALLET_OID + '\'' +
                ", MO_ID='" + MO_ID + '\'' +
                ", QTY=" + QTY +
                ", PICK_OID='" + PICK_OID + '\'' +
                ", dateCode='" + dateCode + '\'' +
                '}';
    }

    public String getCartonSn(String projectCode) throws Exception {

        Vector arg = new Vector();
        arg.add(projectCode);
        Hashtable row = dh.selectOneHashTable("MES_PACK_SYSTEM","getCartonSn",arg);
        this.setDateCode((String)row.get("DATE_CODE"));
        return getDateCode()+(String)row.get("THIS_SN");
    }


    public static void main(String[] args) throws Exception {


        URL CODEBASE = new URL("http://127.0.0.1:8080/PEGAMES/");
        URL PROXY_SERVER = new URL("http://10.162.244.107:8088/PEGAMES/");
        URL HTTP_SERVER = new URL("http://127.0.0.1:8080/PEGAMES/");
        URL FILE_SERVER = new URL("http://10.162.244.107:8088/PEGAMES/**/");
        URL_VO codeBase = new URL_VO(CODEBASE,PROXY_SERVER,HTTP_SERVER,FILE_SERVER);

        UI_InitVO uiVO = new UI_InitVO ("zh_TW", "3FC0955A8EF33408E05363F4A20AED66", "3FC0955A8EF23408E05363F4A2-31276","鎧嘉電腦配件(蘇州)有限公司", "ENAME",  codeBase, "USER NAME TEST", "SYSTEM","系統管理者 鎧嘉電腦配件(蘇州)有限公司", "3B5B598592A53EB2E05363F4A20A-BU2");

        PackCarton pc = new PackCarton();
        pc.setDh(new DataHandler(uiVO));

        String projectCode = "COP1721";
        System.out.println("getCartonSn:"+pc.getCartonSn(projectCode));
        System.out.println("getCartonDateCode:"+pc.getDateCode());

    }


}
