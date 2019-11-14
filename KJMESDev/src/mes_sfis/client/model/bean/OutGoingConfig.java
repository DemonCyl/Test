package mes_sfis.client.model.bean;

import mes_sfis.client.ui.pack.OutGoingUI;

import java.util.Date;

/**
 * Created by Srx_Zhu on 2018/4/11 0011.
 */
public class OutGoingConfig {
    private String shipOid;

    private String kjPn;

    private String csPn;

    private int planQty;

    private int actualQty;

    private String erpShipOid;

    private String erpOutOid;

    private String projectName;

    private String manufacturers;

    private Date logSystemDate;

    private String logEmployeeOid;

    private int status;

    private String memo;

    public OutGoingConfig(){

    }

    public OutGoingConfig(String shipOid, String kjPn, String csPn, int planQty, int actualQty, String erpShipOid, String erpOutOid, String projectName, String manufacturers, Date logSystemDate, String logEmployeeOid, int status, String memo) {
        this.shipOid = shipOid;
        this.kjPn = kjPn;
        this.csPn = csPn;
        this.planQty = planQty;
        this.actualQty = actualQty;
        this.erpShipOid = erpShipOid;
        this.erpOutOid = erpOutOid;
        this.projectName = projectName;
        this.manufacturers = manufacturers;
        this.logSystemDate = logSystemDate;
        this.logEmployeeOid = logEmployeeOid;
        this.status = status;
        this.memo = memo;
    }

    public String getShipOid() {
        return shipOid;
    }

    public void setShipOid(String shipOid) {
        this.shipOid = shipOid;
    }

    public String getKjPn() {
        return kjPn;
    }

    public void setKjPn(String kjPn) {
        this.kjPn = kjPn;
    }

    public String getCsPn() {
        return csPn;
    }

    public void setCsPn(String csPn) {
        this.csPn = csPn;
    }

    public int getPlanQty() {
        return planQty;
    }

    public void setPlanQty(int planQty) {
        this.planQty = planQty;
    }

    public int getActualQty() {
        return actualQty;
    }

    public void setActualQty(int actualQty) {
        this.actualQty = actualQty;
    }

    public String getErpShipOid() {
        return erpShipOid;
    }

    public void setErpShipOid(String erpShipOid) {
        this.erpShipOid = erpShipOid;
    }

    public String getErpOutOid() {
        return erpOutOid;
    }

    public void setErpOutOid(String erpOutOid) {
        this.erpOutOid = erpOutOid;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getManufacturers() {
        return manufacturers;
    }

    public void setManufacturers(String manufacturers) {
        this.manufacturers = manufacturers;
    }

    public Date getLogSystemDate() {
        return logSystemDate;
    }

    public void setLogSystemDate(Date logSystemDate) {
        this.logSystemDate = logSystemDate;
    }

    public String getLogEmployeeOid() {
        return logEmployeeOid;
    }

    public void setLogEmployeeOid(String logEmployeeOid) {
        this.logEmployeeOid = logEmployeeOid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
