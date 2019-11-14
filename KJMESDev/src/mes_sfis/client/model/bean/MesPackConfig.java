package mes_sfis.client.model.bean;

import java.math.BigDecimal;

/**
 * Created by Srx_Zhu on 2018/4/03 0003.
 */
public class MesPackConfig {
    private String projectCode;

    private String oemPN;

    private String applepn;

    private String stage;

    private String location;

    private String rev;

    private String description;

    private String config;

    private String vendor;

    private String vender;

    private String oemdri;

    private String per;

    private String req;

    private String shipShort;

    private String etd;

    private String eta;

    private String oemComments;

    private String batChno;

    private String lc;

    private BigDecimal version;

    private String sfisCheckField;

    private String sfisCheckValue;

    private String sfisCheckGrp;

    private BigDecimal pcsCarton;

    private BigDecimal cartonPallet;

    private String lastCartonDate;

    private String lastCartonSn;

    private String lastPalletDate;

    private String lastPalletSn;

    private String KJPN;

    private String SiteCode;

    private String checkField;

    private String checkFieldValue;

    public MesPackConfig(){

    }
    //TODO 把MES_PACK_CONFIG的所有字段讀入
    public MesPackConfig(String projectCode, String oemPN, String applepn, String stage, String location, String rev, String description, String config, String vendor, String vender, String oemdri, String per, String req, String shipShort, String etd, String eta, String oemComments, String batChno, String lc, BigDecimal version, String sfisCheckField, String sfisCheckValue, String sfisCheckGrp, BigDecimal pcsCarton, BigDecimal cartonPallet, String lastCartonDate, String lastCartonSn, String lastPalletDate, String lastPalletSn,String KJPN,String SiteCode,String checkField,String checkFieldValue) {
        this.projectCode = projectCode;
        this.oemPN = oemPN;
        this.applepn = applepn;
        this.stage = stage;
        this.location = location;
        this.rev = rev;
        this.description = description;
        this.config = config;
        this.vendor = vendor;
        this.vender = vender;
        this.oemdri = oemdri;
        this.per = per;
        this.req = req;
        this.shipShort = shipShort;
        this.etd = etd;
        this.eta = eta;
        this.oemComments = oemComments;
        this.batChno = batChno;
        this.lc = lc;
        this.version = version;
        this.sfisCheckField = sfisCheckField;
        this.sfisCheckValue = sfisCheckValue;
        this.sfisCheckGrp = sfisCheckGrp;
        this.pcsCarton = pcsCarton;
        this.cartonPallet = cartonPallet;
        this.lastCartonDate = lastCartonDate;
        this.lastCartonSn = lastCartonSn;
        this.lastPalletDate = lastPalletDate;
        this.lastPalletSn = lastPalletSn;
        this.KJPN=KJPN;
        this.SiteCode=SiteCode;
        this.checkField=checkField;
        this.checkFieldValue=checkFieldValue;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getOemPN() {
        return oemPN;
    }

    public void setOemPN(String oemPN) {
        this.oemPN = oemPN;
    }

    public String getApplepn() {
        return applepn;
    }

    public void setApplepn(String applepn) {
        this.applepn = applepn;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRev() {
        return rev;
    }

    public void setRev(String rev) {
        this.rev = rev;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getVender() {
        return vender;
    }

    public void setVender(String vender) {
        this.vender = vender;
    }

    public String getOemdri() {
        return oemdri;
    }

    public void setOemdri(String oemdri) {
        this.oemdri = oemdri;
    }

    public String getPer() {
        return per;
    }

    public void setPer(String per) {
        this.per = per;
    }

    public String getReq() {
        return req;
    }

    public void setReq(String req) {
        this.req = req;
    }

    public String getShipShort() {
        return shipShort;
    }

    public void setShipShort(String shipShort) {
        this.shipShort = shipShort;
    }

    public String getEtd() {
        return etd;
    }

    public void setEtd(String etd) {
        this.etd = etd;
    }

    public String getEta() {
        return eta;
    }

    public void setEta(String eta) {
        this.eta = eta;
    }

    public String getOemComments() {
        return oemComments;
    }

    public void setOemComments(String oemComments) {
        this.oemComments = oemComments;
    }

    public String getBatChno() {
        return batChno;
    }

    public void setBatChno(String batChno) {
        this.batChno = batChno;
    }

    public String getLc() {
        return lc;
    }

    public void setLc(String lc) {
        this.lc = lc;
    }

    public BigDecimal getVersion() {
        return version;
    }

    public void setVersion(BigDecimal version) {
        this.version = version;
    }

    public String getSfisCheckField() {
        return sfisCheckField;
    }

    public void setSfisCheckField(String sfisCheckField) {
        this.sfisCheckField = sfisCheckField;
    }

    public String getSfisCheckValue() {
        return sfisCheckValue;
    }

    public void setSfisCheckValue(String sfisCheckValue) {
        this.sfisCheckValue = sfisCheckValue;
    }

    public String getSfisCheckGrp() {
        return sfisCheckGrp;
    }

    public void setSfisCheckGrp(String sfisCheckGrp) {
        this.sfisCheckGrp = sfisCheckGrp;
    }

    public BigDecimal getPcsCarton() {
        return pcsCarton;
    }

    public void setPcsCarton(BigDecimal pcsCarton) {
        this.pcsCarton = pcsCarton;
    }

    public BigDecimal getCartonPallet() {
        return cartonPallet;
    }

    public void setCartonPallet(BigDecimal cartonPallet) {
        this.cartonPallet = cartonPallet;
    }

    public String getLastCartonDate() {
        return lastCartonDate;
    }

    public void setLastCartonDate(String lastCartonDate) {
        this.lastCartonDate = lastCartonDate;
    }

    public String getLastCartonSn() {
        return lastCartonSn;
    }

    public void setLastCartonSn(String lastCartonSn) {
        this.lastCartonSn = lastCartonSn;
    }

    public String getLastPalletDate() {
        return lastPalletDate;
    }

    public void setLastPalletDate(String lastPalletDate) {
        this.lastPalletDate = lastPalletDate;
    }

    public String getLastPalletSn() {
        return lastPalletSn;
    }

    public void setLastPalletSn(String lastPalletSn) {
        this.lastPalletSn = lastPalletSn;
    }

    public String getKJPN() {
        return KJPN;
    }

    public void setKJPN(String KJPN) {
        this.KJPN = KJPN;
    }

    public String getSiteCode() {
        return SiteCode;
    }

    public void setSiteCode(String siteCode) {
        SiteCode = siteCode;
    }

    public String getCheckFieldValue() {
        return checkFieldValue;
    }

    public void setCheckFieldValue(String checkFieldValue) {
        this.checkFieldValue = checkFieldValue;
    }

    public String getCheckField() {
        return checkField;
    }

    public void setCheckField(String checkField) {
        this.checkField = checkField;
    }
}
