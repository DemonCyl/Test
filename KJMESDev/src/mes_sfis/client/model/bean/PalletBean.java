package mes_sfis.client.model.bean;

import java.sql.Blob;
import java.util.Date;

/**
 * Created by Haifeng_Zhou on 2018/4/3.
 */
public class PalletBean {
    String	PALLET_OID;
    String	PALLET_NO;
    Date LOG_SYSTEMDATE;
    String	LOG_EMPLOYEE_OID;
    Blob SNAPSHOT;
    String	DATA_JSON;
    String	MEMO;
    int	IS_CLOSE;
    int	IS_BREAK;
    Date	CLOSE_SYSTEMDATE;
    Date	BREAK_SYSTEMDATE;
    int	STATUS;
    int	LAST_SYSTEMDATE;
    String	STORAGE_OID;

    public String getPALLET_OID() {
        return PALLET_OID;
    }

    public void setPALLET_OID(String PALLET_OID) {
        this.PALLET_OID = PALLET_OID;
    }

    public String getPALLET_NO() {
        return PALLET_NO;
    }

    public void setPALLET_NO(String PALLET_NO) {
        this.PALLET_NO = PALLET_NO;
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

    public Blob getSNAPSHOT() {
        return SNAPSHOT;
    }

    public void setSNAPSHOT(Blob SNAPSHOT) {
        this.SNAPSHOT = SNAPSHOT;
    }

    public String getDATA_JSON() {
        return DATA_JSON;
    }

    public void setDATA_JSON(String DATA_JSON) {
        this.DATA_JSON = DATA_JSON;
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

    public int getLAST_SYSTEMDATE() {
        return LAST_SYSTEMDATE;
    }

    public void setLAST_SYSTEMDATE(int LAST_SYSTEMDATE) {
        this.LAST_SYSTEMDATE = LAST_SYSTEMDATE;
    }

    public String getSTORAGE_OID() {
        return STORAGE_OID;
    }

    public void setSTORAGE_OID(String STORAGE_OID) {
        this.STORAGE_OID = STORAGE_OID;
    }

    @Override
    public String toString() {
        return "PalletBean{" +
                "PALLET_OID='" + PALLET_OID + '\'' +
                ", PALLET_NO='" + PALLET_NO + '\'' +
                ", LOG_SYSTEMDATE=" + LOG_SYSTEMDATE +
                ", LOG_EMPLOYEE_OID='" + LOG_EMPLOYEE_OID + '\'' +
                ", SNAPSHOT=" + SNAPSHOT +
                ", DATA_JSON='" + DATA_JSON + '\'' +
                ", MEMO='" + MEMO + '\'' +
                ", IS_CLOSE=" + IS_CLOSE +
                ", IS_BREAK=" + IS_BREAK +
                ", CLOSE_SYSTEMDATE=" + CLOSE_SYSTEMDATE +
                ", BREAK_SYSTEMDATE=" + BREAK_SYSTEMDATE +
                ", STATUS=" + STATUS +
                ", LAST_SYSTEMDATE=" + LAST_SYSTEMDATE +
                ", STORAGE_OID='" + STORAGE_OID + '\'' +
                '}';
    }
}
