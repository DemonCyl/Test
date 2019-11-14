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



    //�d�߽c�X�����S���n�d�ߪ��c�X
    public boolean addNutsData(Map<String,String> map) {
        boolean isadd; //�]�m��l�Ȭ�true(�N���q�{�����c�X)
        String sql = "insert into MES_THIRTEENNUTS_LINK (THIRTEEN_OID,MCE,SN1,MACHINE_ID,INTIME,TESTDATE) VALUES ((select sys_guid() from dual),'"+map.get("mce")+"','"+map.get("isn")+"','"+map.get("user")+"',(select sysdate from dual),(select sysdate from dual))";//����d��SQL�y�y
        System.out.println(sql);
        try {
            System.out.println("aaa1"+map);
            Vector ht =  dh.updateData(sql, DataSourceType._SFIS_KAIJIA_STD);
            System.out.println("bbbb1"+map);
            isadd=true;
        } catch (Exception e) {

            isadd = false;

            e.printStackTrace();

            //   throw new Exception("�O�_���c�X�d�߿��~:"+e.getMessage());

        }
        return isadd;

    }




}
