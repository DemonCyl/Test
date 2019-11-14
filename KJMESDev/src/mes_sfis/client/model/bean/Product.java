package mes_sfis.client.model.bean;

import base.enums.DataSourceType;
import mes_sfis.client.util.DataHandler;

import java.util.Hashtable;

/**
 * Created by Chris1_Liao on 2018/4/3.
 */
public class Product extends BaseBean{
    private String cartonOid;
    private String projectCode;
    private String MSN;
    private String SSN;
    private String CSN;
    private String SN1;
    private String SN2;
    private String SN3;
    private String SN4;
    private String SN5;
    private String SN6;
    private String SN7;
    private String SN8;
    private String SN9;
    private String SN10;
    private Integer status;

    public Product(){

    }

    public Product(String cartonOid, String projectCode, String MSN, String SSN, String CSN, String SN1, String SN2, String SN3, String SN4, String SN5, String SN6, String SN7, String SN8, String SN9, String SN10, Integer status) {
        this.cartonOid = cartonOid;
        this.projectCode = projectCode;
        this.MSN = MSN;
        this.SSN = SSN;
        this.CSN = CSN;
        this.SN1 = SN1;
        this.SN2 = SN2;
        this.SN3 = SN3;
        this.SN4 = SN4;
        this.SN5 = SN5;
        this.SN6 = SN6;
        this.SN7 = SN7;
        this.SN8 = SN8;
        this.SN9 = SN9;
        this.SN10 = SN10;
        this.status = status;
    }

    public String getCartonOid() {
        return cartonOid;
    }

    public void setCartonOid(String cartonOid) {
        this.cartonOid = cartonOid;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getMSN() {
        return MSN;
    }

    public void setMSN(String MSN) {
        this.MSN = MSN;
    }

    public String getSSN() {
        return SSN;
    }

    public void setSSN(String SSN) {
        this.SSN = SSN;
    }

    public String getCSN() {
        return CSN;
    }

    public void setCSN(String CSN) {
        this.CSN = CSN;
    }

    public String getSN1() {
        return SN1;
    }

    public void setSN1(String SN1) {
        this.SN1 = SN1;
    }

    public String getSN2() {
        return SN2;
    }

    public void setSN2(String SN2) {
        this.SN2 = SN2;
    }

    public String getSN3() {
        return SN3;
    }

    public void setSN3(String SN3) {
        this.SN3 = SN3;
    }

    public String getSN4() {
        return SN4;
    }

    public void setSN4(String SN4) {
        this.SN4 = SN4;
    }

    public String getSN5() {
        return SN5;
    }

    public void setSN5(String SN5) {
        this.SN5 = SN5;
    }

    public String getSN6() {
        return SN6;
    }

    public void setSN6(String SN6) {
        this.SN6 = SN6;
    }

    public String getSN7() {
        return SN7;
    }

    public void setSN7(String SN7) {
        this.SN7 = SN7;
    }

    public String getSN8() {
        return SN8;
    }

    public void setSN8(String SN8) {
        this.SN8 = SN8;
    }

    public String getSN9() {
        return SN9;
    }

    public void setSN9(String SN9) {
        this.SN9 = SN9;
    }

    public String getSN10() {
        return SN10;
    }

    public void setSN10(String SN10) {
        this.SN10 = SN10;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Product(String isn, DataHandler dh) throws Exception {
        this.dh = dh;
        //做相應的產品狀態檢查
        this.isScrapped(isn);
    }

    /**
     * 檢查是否報廢
     * @param isn
     * @return
     * @throws Exception
     */
    public boolean isScrapped(String isn) throws Exception {
        boolean isScrapped = true;
        String sql = "select 1 from tp.mo_d t where t.isn = '"+isn+"' and t.status ='4'";
        try {
            Hashtable ht =  dh.getDataOne(sql, DataSourceType._SFIS_KAIJIA_STD);
            if(ht!=null){
                isScrapped = true;//已報廢
            }else{
                isScrapped = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("是否報廢查詢錯誤:"+e.getMessage());
        }
        return isScrapped;
    }
}
