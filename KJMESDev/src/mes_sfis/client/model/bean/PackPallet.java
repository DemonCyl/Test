package mes_sfis.client.model.bean;

import base.enums.DataSourceType;
import base.vo.UI_InitVO;
import base.vo.URL_VO;
import mes_sfis.client.task.PassDeviceTask;
import mes_sfis.client.util.DataHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jfree.util.Log;

import java.net.URL;
import java.util.Hashtable;
import java.util.Vector;

/**
 * Created by Chris1_Liao on 2018/4/3.
 */
public class PackPallet extends BaseBean{
    private static final Logger logger = LogManager.getLogger(PassDeviceTask.class);
    private String dateCode;

    public String getDateCode() {
        return dateCode;
    }

    public void setDateCode(String dateCode) {
        this.dateCode = dateCode;
    }

    public String getPalletSn(String projectCode) throws Exception {

        Vector arg = new Vector();
        arg.add(projectCode);
        Hashtable row = dh.selectOneHashTable("MES_PACK_SYSTEM","getPalletSn",arg);
        this.setDateCode((String)row.get("DATE_CODE"));
        return getDateCode()+(String)row.get("THIS_SN");
    }




    public static void main(String[] args) throws Exception {
        /**
         codeBase : http://127.0.0.1:8080/PEGAMES/
         PROXY_SERVER : http://10.162.244.107:8088/PEGAMES/
         HTTP_SERVER : http://127.0.0.1:8080/PEGAMES/
         FILE_SERVER : http://10.162.244.107:8088/PEGAMES/**/

        URL CODEBASE = new URL("http://127.0.0.1:8088/PEGAMES/");
        URL PROXY_SERVER = new URL("http://10.162.244.107:8088/PEGAMES/");
        URL HTTP_SERVER = new URL("http://127.0.0.1:8088/PEGAMES/");
        URL FILE_SERVER = new URL("http://10.162.244.107:8088/PEGAMES/**/");
        URL_VO codeBase = new URL_VO(CODEBASE,PROXY_SERVER,HTTP_SERVER,FILE_SERVER);

        UI_InitVO uiVO = new UI_InitVO ("zh_TW", "3FC0955A8EF33408E05363F4A20AED66", "3FC0955A8EF23408E05363F4A2-31276","鎧嘉電腦配件(蘇州)有限公司", "ENAME",  codeBase, "USER NAME TEST", "SYSTEM","系統管理者 鎧嘉電腦配件(蘇州)有限公司", "3B5B598592A53EB2E05363F4A20A-BU2");

        PackPallet pc = new PackPallet();
        pc.setDh(new DataHandler(uiVO));

        String projectCode = "COP1721";
        System.out.println("getPalletSn:"+pc.getPalletSn(projectCode));
        System.out.println("getPalletDateCode:"+pc.getDateCode());

    }
}
