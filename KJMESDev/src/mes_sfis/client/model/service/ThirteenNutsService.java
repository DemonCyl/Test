package mes_sfis.client.model.service;

import base.enums.DataSourceType;
import base.vo.UI_InitVO;
import mes_sfis.client.model.bean.PackList;
import mes_sfis.client.util.DataHandler;

import java.math.BigDecimal;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;


/**
 * Created by Mark_Yang on 2018/4/4.
 */
public class ThirteenNutsService extends BaseService {
    UI_InitVO uiVO;
    static  DataHandler dh;

    private String carton_no;
    public ThirteenNutsService(UI_InitVO uiVO){
        this.uiVO = uiVO;
        dh = new DataHandler(uiVO);
    }



    //查詢箱碼表里有沒有要查詢的箱碼
    public boolean addNutsData(Map<String,String> map) {
        boolean isadd; //設置初始值為true(代表默認有此箱碼)
        String sql = "insert into MES_THIRTEENNUTS_LINK (THIRTEEN_OID,MCE,SN1,MACHINE_ID,INTIME,TESTDATE) VALUES ((select sys_guid() from dual),'"+map.get("mce")+"','"+map.get("isn")+"','"+map.get("user")+"',(select sysdate from dual),(select sysdate from dual))";//執行查詢SQL語句
        System.out.println(sql);
        try {
            System.out.println("aaa1"+map);
            Vector ht =  dh.updateData(sql, DataSourceType._SFIS_KAIJIA_STD);
            System.out.println("bbbb1"+map);
            isadd=true;
        } catch (Exception e) {

            isadd = false;

            e.printStackTrace();

            //   throw new Exception("是否有箱碼查詢錯誤:"+e.getMessage());

        }
        return isadd;

    }




}
