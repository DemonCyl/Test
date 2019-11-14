package mes_sfis.client.model.bean;

import base.vo.UI_InitVO;
import base.vo.URL_VO;
import mes_sfis.client.util.DataHandler;

import java.net.URL;

/**
 * Created by MARK_YANG on 2018/12/11.
 */
public class NutsDataModel extends BaseBean{
    private  String INTIME;
    private  String SN1;
    private  String  MACHINE_ID;
    private  String  MCE;


    public String getINTIME() {
        return INTIME;
    }

    public void setINTIME(String INTIME) {
        this.INTIME = INTIME;
    }

    public String getSN1() {
        return SN1;
    }

    public void setSN1(String SN1) {
        this.SN1 = SN1;
    }

    public String getMACHINE_ID() {
        return MACHINE_ID;
    }

    public void setMACHINE_ID(String MACHINE_ID) {
        this.MACHINE_ID = MACHINE_ID;
    }

    public String getMCE() {
        return MCE;
    }

    public void setMCE(String MCE) {
        this.MCE = MCE;
    }

    public NutsDataModel() {

    }

    @Override
    public String toString() {
        return "NutsDataModel{" +
                "INTIME='" + INTIME + '\'' +
                ", SN1='" + SN1 + '\'' +
                ", MACHINE_ID='" + MACHINE_ID + '\'' +
                ", MCE='" + MCE + '\'' +
                '}';
    }

    public static void main(String[] args) throws Exception {


        URL CODEBASE = new URL("http://127.0.0.1:8080/PEGAMES/");
        URL PROXY_SERVER = new URL("http://10.162.244.107:8088/PEGAMES/");
        URL HTTP_SERVER = new URL("http://127.0.0.1:8080/PEGAMES/");
        URL FILE_SERVER = new URL("http://10.162.244.107:8088/PEGAMES/**/");
        URL_VO codeBase = new URL_VO(CODEBASE,PROXY_SERVER,HTTP_SERVER,FILE_SERVER);

        UI_InitVO uiVO = new UI_InitVO ("zh_TW", "3FC0955A8EF33408E05363F4A20AED66", "3FC0955A8EF23408E05363F4A2-31276","鎧嘉電腦配件(蘇州)有限公司", "ENAME",  codeBase, "USER NAME TEST", "SYSTEM","系統管理者 鎧嘉電腦配件(蘇州)有限公司", "3B5B598592A53EB2E05363F4A20A-BU2");

        NutsDataModel pc=new NutsDataModel();
        pc.setDh(new DataHandler(uiVO));

        String projectCode = "COP1721";
        /*System.out.println("getCartonSn:"+pc.getCartonSn(projectCode));
        System.out.println("getCartonDateCode:"+pc.getDateCode());*/

    }


}
