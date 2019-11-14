package mes_sfis.client.model.service;

import base.enums.DataSourceType;
import base.vo.UI_InitVO;
import base.vo.URL_VO;
import mes_sfis.client.model.bean.MesPackConfig;
import mes_sfis.client.ui.barcode.MyTableModel;
import mes_sfis.client.util.DataHandler;
import org.apache.axis2.dataretrieval.Data;

import javax.swing.*;
import java.net.URL;
import java.util.*;

/**
 * Created by Xiaojian1_Yu on 2018/5/17.
 */
public class Borrow_LenderService extends BaseService{
    DataHandler dh;
    UI_InitVO uiVO;
    public Borrow_LenderService(UI_InitVO uiVO) {
        this.uiVO = uiVO;
        dh = new DataHandler(uiVO)  ;
    }

    //獲取單個ISN
    public Hashtable CheckISN(String isnText,String status){
        String sql="";
        if(status.equals("1")){
             sql ="  select i.*,n.house,n.batch from Sfis_1721_Detail_Info i join SFIS_1721_NOTE n on n.isn = i.isn where i.ISN ='"+isnText+"'and n.HOUSE !='報廢領用倉' order by n.time asc";
        }
        if(status.equals("0")){
             sql ="  select i.*,n.house,n.batch from Sfis_1721_Detail_Info i join SFIS_1721_NOTE n on n.isn = i.isn where i.ISN ='"+isnText+"'and n.HOUSE ='報廢領用倉' order by n.time desc";
        }
        if(status.equals("5")){
             sql ="  select i.*,n.house,n.batch from Sfis_1721_Detail_Info i join SFIS_1721_NOTE n on n.isn = i.isn where i.ISN ='"+isnText+"'and n.HOUSE !='報廢領用倉' order by n.time asc";
        }
        if(status.equals("6")){
             sql ="  select i.*,n.house,n.batch from Sfis_1721_Detail_Info i join SFIS_1721_NOTE n on n.isn = i.isn where i.ISN ='"+isnText+"'and n.HOUSE ='報廢領用倉' order by n.time desc";
        }

        List<Hashtable> ht =null;
        Hashtable table = new Hashtable();
        try {
            ht=dh.getDataList(sql, DataSourceType._MultiCompanyCurrentDB);
            if(ht==null){
                return null;
            }else{
                table=ht.get(0);
                return table;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //獲取批號內所有ISN
    public List<Hashtable>  CheckBATCH (String BATCH) throws Exception {
        String sql = "select HOUSE,BATCH,ISN,KJ,STATUS from SFIS_1721_NOTE where BATCH='"+BATCH+"'";
        List<Hashtable> list = new ArrayList<>();
        try {
            list= dh.getDataList(sql,DataSourceType._MultiCompanyCurrentDB);
            if(list==null){
                return null;
            }else{
                return list;
            }
        }catch (Exception e){
        }
        return null;
    }

    //檢查員工權限
    public String CheckEmpOid(String empOid){
        String sql ="select POWER from SFIS_1721_USERID where USERID='"+empOid+"'";
        Hashtable ht=null;
        try {
            ht = dh.getDataOne(sql, DataSourceType._MultiCompanyCurrentDB);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (ht==null) {
            return null;
        }else{
            return ht.get("POWER").toString();
        }
    }

    //更新SFIS_1721_WAREHOUSE
    public void ChangeWAREHOUSE(String houseName,int num,String status) throws Exception {
        String sql = "select NSUM from SFIS_1721_WAREHOUSE where HNAME='"+houseName+"'";
        Hashtable aa =null;
        aa=dh.getDataOne(sql,DataSourceType._MultiCompanyCurrentDB);
        String sum = aa.get("NSUM").toString();
        int result=0;
        String time="";
        if(status.equals("1")){
            result=Integer.parseInt(sum)-num;
            time="OUTTIME";
        }
        if(status.equals("0")){
            result=Integer.parseInt(sum)+num;
            time="INTIME";
        }
        if(status.equals("5")){
            result=Integer.parseInt(sum)+num;
            time="INTIME";
        }
        if(status.equals("6")){
            result=Integer.parseInt(sum)-num;
            time="OUTTIME";
        }

        String sql2="update  SFIS_1721_WAREHOUSE set NSUM='"+result+"',"+time+"=to_char(sysdate, 'yyyy-mm-dd hh24:mi:ss') where HNAME='"+houseName+"'";
        dh.updateData(sql2,DataSourceType._MultiCompanyCurrentDB);
    }

    //插入SFIS_1721_NOTE
    public void InsertNote(List<HashMap> list,String status,String batch)throws  Exception{
        String HOUSE="";
        String ISN="";
        String KJ="";
        String sql="";
        String in_out_status="";
        Vector arg = new Vector();
        for (int i=0;i<list.size();i++){
            if(status.equals("1")){
                HOUSE=list.get(i).get("HOUSE").toString();
                in_out_status="1";
            }
            if(status.equals("0")){
                HOUSE=list.get(i).get("HOUSE").toString();
                in_out_status="3";
            }
            if(status.equals("5")){
                HOUSE="報廢領用倉";
                in_out_status="0";
            }
            if(status.equals("6")){
                HOUSE="報廢領用倉";
                in_out_status="1";
            }
            ISN=list.get(i).get("ISN").toString();
            KJ=list.get(i).get("KJ").toString();
            sql ="insert into SFIS_1721_NOTE(HOUSE,BATCH,ISN,KJ,STATUS,TIME)values('"+HOUSE+"','"+batch+"','"+ISN+"','"+KJ+"','"+in_out_status+"',to_char(sysdate, 'yyyy-mm-dd hh24:mi:ss'))";
            arg.add(sql);
        }
        dh.updateData(arg, DataSourceType._MultiCompanyCurrentDB);
    }

    //改變ISN狀態SFIS_1721_DETAIL_INFO
    public void ChangeISNStatus(List<HashMap> list,String status)throws Exception{
        String sql="";
        String isn="";
        Vector arg = new Vector();
        for (int i=0;i<list.size();i++){
            isn = list.get(i).get("ISN").toString();
            sql="update SFIS_1721_DETAIL_INFO set STATUS='"+status+"' where ISN='"+isn+"'";
            arg.add(sql);
        }
        dh.updateData(arg,DataSourceType._MultiCompanyCurrentDB);
    }

    //插入SFIS_1721_IN_OUT_WAREHOUSE
    public void ChangeInOutWAREHOUSE(String batch,int num,String desc,String house,String user,String status)throws Exception{
        if(status.equals("1")){
            String sql = "insert into SFIS_1721_IN_OUT_WAREHOUSE(IN_OUT,BATCH_NO,ISN,TIM,DESCP,HOUSENAME,USERNAME)values('"+status+"','"+batch+"','"+num+"',to_char(sysdate, 'yyyy-mm-dd hh24:mi:ss'),'"+desc+"','"+house+"','"+user+"')";
            dh.updateData(sql,DataSourceType._MultiCompanyCurrentDB);
        }
        if(status.equals("0")){
            String sql = "update SFIS_1721_IN_OUT_WAREHOUSE set IN_OUT='3',TIM=to_char(sysdate, 'yyyy-mm-dd hh24:mi:ss'),HOUSENAME='"+house+"',USERNAME='"+user+"' where BATCH_NO='"+batch+"'";
            dh.updateData(sql,DataSourceType._MultiCompanyCurrentDB);
        }
        if(status.equals("5")){
            String sql = "update SFIS_1721_IN_OUT_WAREHOUSE set IN_OUT='0',TIM=to_char(sysdate, 'yyyy-mm-dd hh24:mi:ss'),HOUSENAME='"+house+"',USERNAME='"+user+"' where BATCH_NO='"+batch+"'";
            dh.updateData(sql,DataSourceType._MultiCompanyCurrentDB);
        }
        if(status.equals("6")){
            String sql = "update SFIS_1721_IN_OUT_WAREHOUSE set IN_OUT='1',TIM=to_char(sysdate, 'yyyy-mm-dd hh24:mi:ss'),HOUSENAME='"+house+"',USERNAME='"+user+"' where BATCH_NO='"+batch+"'";
            dh.updateData(sql,DataSourceType._MultiCompanyCurrentDB);
        }
    }

    public String CheckReturnBatch(String batch)throws Exception{
        String sql="select HOUSE from SFIS_1721_NOTE where batch='"+batch+"' and rownum=1 order by TIME";
        Hashtable data = new Hashtable();
        data = dh.getDataOne(sql,DataSourceType._MultiCompanyCurrentDB);
        if(data==null){
            return null;
        }else{
            return data.get("HOUSE").toString();
        }
    }


}
