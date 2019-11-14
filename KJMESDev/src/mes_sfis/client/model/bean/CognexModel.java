package mes_sfis.client.model.bean;

import base.vo.UI_InitVO;
import base.vo.URL_VO;
import mes_sfis.client.util.DataHandler;
import oracle.sql.BLOB;
import oracle.sql.NCLOB;
import oracle.sql.NUMBER;

import java.net.URL;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

/**
 * Created by Chris1_Liao on 2018/4/3.
 */
public class CognexModel extends BaseBean{
    private  String TIME;
    private  String AMODULE;
    private  String  READING;
    private  String  DECODE_TIME;
    private  String  OVERALL_GRAGE;
    private  String SYMBOL_CONTRAST;
    private Double  RAWUP;
    private String PRINTGROWTH;
    private Double RAWDOWN;
    private String  ERROR_CORRECTION;
    private int  RAWONE;
    private String  MODULATION;
    private int    RAWTWO;
    private String FIXEDPATTERN;
    private int RAWTHREE;
    private String GRID_NONUNIFORMITY;
    private Double RAWFOUR;


    public String getTIME() {
        return TIME;
    }

    public void setTIME(String TIME) {
        this.TIME = TIME;
    }

    public String getAMODULE() {
        return AMODULE;
    }

    public void setAMODULE(String AMODULE) {
        this.AMODULE = AMODULE;
    }

    public String getREADING() {
        return READING;
    }

    public void setREADING(String READING) {
        this.READING = READING;
    }

    public String getDECODE_TIME() {
        return DECODE_TIME;
    }

    public void setDECODE_TIME(String DECODE_TIME) {
        this.DECODE_TIME = DECODE_TIME;
    }

    public String getOVERALL_GRAGE() {
        return OVERALL_GRAGE;
    }

    public void setOVERALL_GRAGE(String OVERALL_GRAGE) {
        this.OVERALL_GRAGE = OVERALL_GRAGE;
    }

    public String getSYMBOL_CONTRAST() {
        return SYMBOL_CONTRAST;
    }

    public void setSYMBOL_CONTRAST(String SYMBOL_CONTRAST) {
        this.SYMBOL_CONTRAST = SYMBOL_CONTRAST;
    }

    public Double getRAWUP() {
        return RAWUP;
    }

    public void setRAWUP(Double RAWUP) {
        this.RAWUP = RAWUP;
    }

    public String getPRINTGROWTH() {
        return PRINTGROWTH;
    }

    public void setPRINTGROWTH(String PRINTGROWTH) {
        this.PRINTGROWTH = PRINTGROWTH;
    }

    public Double getRAWDOWN() {
        return RAWDOWN;
    }

    public void setRAWDOWN(Double RAWDOWN) {
        this.RAWDOWN = RAWDOWN;
    }

    public String getERROR_CORRECTION() {
        return ERROR_CORRECTION;
    }

    public void setERROR_CORRECTION(String ERROR_CORRECTION) {
        this.ERROR_CORRECTION = ERROR_CORRECTION;
    }

    public int getRAWONE() {
        return RAWONE;
    }

    public void setRAWONE(int RAWONE) {
        this.RAWONE = RAWONE;
    }

    public String getMODULATION() {
        return MODULATION;
    }

    public void setMODULATION(String MODULATION) {
        this.MODULATION = MODULATION;
    }

    public int getRAWTWO() {
        return RAWTWO;
    }

    public void setRAWTWO(int RAWTWO) {
        this.RAWTWO = RAWTWO;
    }

    public String getFIXEDPATTERN() {
        return FIXEDPATTERN;
    }

    public void setFIXEDPATTERN(String FIXEDPATTERN) {
        this.FIXEDPATTERN = FIXEDPATTERN;
    }

    public int getRAWTHREE() {
        return RAWTHREE;
    }

    public void setRAWTHREE(int RAWTHREE) {
        this.RAWTHREE = RAWTHREE;
    }

    public String getGRID_NONUNIFORMITY() {
        return GRID_NONUNIFORMITY;
    }

    public void setGRID_NONUNIFORMITY(String GRID_NONUNIFORMITY) {
        this.GRID_NONUNIFORMITY = GRID_NONUNIFORMITY;
    }

    public Double getRAWFOUR() {
        return RAWFOUR;
    }

    public void setRAWFOUR(Double RAWFOUR) {
        this.RAWFOUR = RAWFOUR;
    }

    public CognexModel() {

    }


    @Override
    public String toString() {
        return "CognexModel{" +
                "TIME='" + TIME + '\'' +
                ", AMODULE='" + AMODULE + '\'' +
                ", READING='" + READING + '\'' +
                ", DECODE_TIME='" + DECODE_TIME + '\'' +
                ", OVERALL_GRAGE='" + OVERALL_GRAGE + '\'' +
                ", SYMBOL_CONTRAST='" + SYMBOL_CONTRAST + '\'' +
                ", RAWUP=" + RAWUP +
                ", PRINTGROWTH='" + PRINTGROWTH + '\'' +
                ", RAWDOWN=" + RAWDOWN +
                ", ERROR_CORRECTION='" + ERROR_CORRECTION + '\'' +
                ", RAWONE=" + RAWONE +
                ", MODULATION='" + MODULATION + '\'' +
                ", RAWTWO=" + RAWTWO +
                ", FIXEDPATTERN='" + FIXEDPATTERN + '\'' +
                ", RAWTHREE=" + RAWTHREE +
                ", GRID_NONUNIFORMITY='" + GRID_NONUNIFORMITY + '\'' +
                ", RAWFOUR=" + RAWFOUR +
                '}';
    }

    public static void main(String[] args) throws Exception {


        URL CODEBASE = new URL("http://127.0.0.1:8080/PEGAMES/");
        URL PROXY_SERVER = new URL("http://10.162.244.107:8088/PEGAMES/");
        URL HTTP_SERVER = new URL("http://127.0.0.1:8080/PEGAMES/");
        URL FILE_SERVER = new URL("http://10.162.244.107:8088/PEGAMES/**/");
        URL_VO codeBase = new URL_VO(CODEBASE,PROXY_SERVER,HTTP_SERVER,FILE_SERVER);

        UI_InitVO uiVO = new UI_InitVO ("zh_TW", "3FC0955A8EF33408E05363F4A20AED66", "3FC0955A8EF23408E05363F4A2-31276","鎧嘉電腦配件(蘇州)有限公司", "ENAME",  codeBase, "USER NAME TEST", "SYSTEM","系統管理者 鎧嘉電腦配件(蘇州)有限公司", "3B5B598592A53EB2E05363F4A20A-BU2");

        CognexModel pc=new CognexModel();
        pc.setDh(new DataHandler(uiVO));

        String projectCode = "COP1721";
        /*System.out.println("getCartonSn:"+pc.getCartonSn(projectCode));
        System.out.println("getCartonDateCode:"+pc.getDateCode());*/

    }


}
