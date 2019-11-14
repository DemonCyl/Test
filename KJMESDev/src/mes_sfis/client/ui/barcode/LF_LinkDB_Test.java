package mes_sfis.client.ui.barcode;

import base.client.ui.BasePanel;
import base.client.util.BaseServletAgent;
import base.client.util.CloneArray_ChangeStr;
import base.client.util.component.P_Component_Meta;
import base.enums.CommandName;
import base.enums.DataSourceType;
import base.vo.BaseVO;
import base.vo.ResultVO;
import base.vo.UI_InitVO;

import java.util.Hashtable;
import java.util.Vector;

/**
 * Created by Feng1_Lu on 2018/1/8.
 */
public class LF_LinkDB_Test extends BasePanel {
    public static final String VERSION = "$Name:  $, $Id: LF_LinkDB_Test.java,v 1.6 2018/01/08 06:45:33 Lucy6_Lu Exp $";
    public static final String COPYRIGHT = "Copyright (c) 2008 PEGATRON Inc. All Rights Reserved.";

    private UI_InitVO xx;
    private String LoginNo;
    private String LoginName;
    String sql = "insert into tp.mo_route_owner  (isn,seq,owner_seq,owner_sec,owner_grp,c_op,c_date,flag,status) values ('KJ180150002EEEE0XE0','1','1','TEST','TZ8','XXX',to_date ('2018/01/08 11:34:19','YYYY-MM-DD HH24:MI:SS'),'D','0')";
    public LF_LinkDB_Test(UI_InitVO uiVO) {
        super(uiVO);
        this.xx = uiVO;
        LoginNo =  xx.getLogin_id();
        //LoginName = xx.getUSERNAME();
        System.out.println("================LoginNo:"+LoginNo);
        try {
            SelectLoginName();
            getDBDATA(sql,uiVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
}

    public void SelectLoginName() throws Exception{
        Vector arg = new Vector();
        arg.add(LoginNo);
        BaseServletAgent bsa = new BaseServletAgent(uiVO);
        BaseVO bvo = new BaseVO(uiVO, this.getClass().toString(), CommandName.CallPLSQLCmd2, arg);
        bvo.setPackageName("AAA_1721_GET_HOUSE");
        bvo.setFunctionName("USERNAME");
        ResultVO rvo = bsa.doFunc(bvo);
        System.out.println("*************************************************-");
        Vector result = rvo.getData();
        Hashtable ht = (Hashtable) result.elementAt(1);
        LoginName = CloneArray_ChangeStr.NulltoSpace(ht.get("CNAME"));
        System.out.println("-------------------ladallada---------------------"+LoginName);
    }
    public void getDBDATA(String sql,UI_InitVO uiVO)throws Exception{
        Vector arg = new Vector();
        arg.add(sql);
        BaseServletAgent bsa1 = new BaseServletAgent(uiVO);
        BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallSQLCmd2,arg);
        bvo.setDataSourceType(DataSourceType._SFIS_KAIJIA_STD);
        ResultVO rvo = bsa1.doFunc(bvo);
        Vector result = rvo.getData();
        System.out.println("======say hello=====");
    }
    public void ChangeStepStatus(String ds,String isn,int s,String owner_seq,String owner_sec,String owner_grp,String c_op,String c_date,String flag,String status,UI_InitVO uiVO)throws Exception{

       String reString = "insert into tp.mo_route_owner  (isn,seq,owner_seq,owner_sec,owner_grp,c_op,c_date,flag,status) values ('"+isn+"','"+s+"','"+owner_seq+"','"+owner_sec+"','"+owner_grp+"','"+c_op+"',to_date ('"+c_date+"','YYYY-MM-DD HH24:MI:SS'),'"+flag+"','"+status+"')";

        Vector arg = new Vector();
        arg.add(reString);
        BaseServletAgent bsa1 = new BaseServletAgent(uiVO);
        BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallSQLCmd2,arg);
        bvo.setDataSourceType(ds);
        ResultVO rvo = bsa1.doFunc(bvo);
        System.out.println("===============================hello------hello-----hello=================================");
        Vector result = rvo.getData();
    }


    @Override
    public void create() {

    }

    @Override
    public void save() {

    }

    @Override
    public void cancel() {

    }

    @Override
    public void refresh() {

    }

    @Override
    public void modify() {

    }

    @Override
    public void delete() {

    }

    @Override
    public void singleQuery() {

    }

    @Override
    public void multiQuery() {

    }

    @Override
    public void print() {

    }

    @Override
    public void exit() {

    }

    @Override
    public void help() {

    }

    @Override
    public void email() {

    }

    @Override
    public void export() {

    }

    @Override
    public void importData() {

    }

    @Override
    public Hashtable<String, P_Component_Meta> needValidateComponents() {
        return null;
    }

    @Override
    public void setReportOid(String s) {

    }
}
