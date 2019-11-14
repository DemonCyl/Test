package mes_sfis.client.util;

import base.client.util.BaseServletAgent;
import base.enums.CommandName;
import base.vo.BaseVO;
import base.vo.ResultVO;
import base.vo.UI_InitVO;

import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

/**
 * Created by Chris1_Liao on 2018/4/2.
 */
public class DataHandler {

    UI_InitVO uiVO;
    String message = "";

    public DataHandler(UI_InitVO uiVO){
        this.uiVO = uiVO;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Vector selectVector(String packageName, String functionName, Vector arg) throws Exception {

        BaseServletAgent bsa = new BaseServletAgent(uiVO);
        BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(), CommandName.CallPLSQLCmd2,arg);
        bvo.setPackageName(packageName);
        bvo.setFunctionName(functionName);
        ResultVO rvo = bsa.doFunc(bvo);
        Vector result = rvo.getData();
        message = rvo.getErrorMessage();
        if(result==null || result.size() < 2){
            return null;
        }
        return  result;
    }

    public Hashtable selectOneHashTable(String packageName,String functionName,Vector arg) throws Exception {

        Vector result = this.selectVector(packageName, functionName, arg);
        if(result==null || result.size() < 2){
            return null;
        }
        return  (Hashtable)result.get(1);
    }


    /**
     *回傳多筆數據 Vector 中的第0元素為meta data即字段列表，型態也是vector，第１元素以後為數據，型態為Hashtable
     * @param sql
     * @param ds
     * @return
     * @throws Exception
     */
    public Vector getDataVector(String sql,String ds)throws Exception{
        Vector arg = new Vector();
        arg.add(sql);
        BaseServletAgent bsa1 = new BaseServletAgent(uiVO);
        BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallSQLCmd2,arg);
        bvo.setDataSourceType(ds);
        ResultVO rvo = bsa1.doFunc(bvo);
        Vector result = rvo.getData();

        message = rvo.getErrorMessage();
        if(result==null || result.size() < 2){
            return null;
        }
        return result;
    }

    /**
     * 去除回傳的字段vector，僅保留數據，以list操作
     * @param sql
     * @param ds
     * @return
     * @throws Exception
     */
    public List<Hashtable> getDataList(String sql, String ds)throws Exception{
        Vector result = this.getDataVector(sql,ds);
        if(result==null || result.size()<2){
            return null;
        }
        result.removeElementAt(0);
        return (List)result;
    }

    /**
     *
     * @param sql
     * @param ds
     * @return Hashtable 需用大寫取得數據 如 ht.get("ISN")
     * @throws Exception
     */
    public Hashtable getDataOne(String sql,String ds)throws Exception{

        Vector result = this.getDataVector(sql,ds);
        if(result==null || result.size() < 2){
            return null;
        }
        if(result==null || result.size() > 2){
            throw new Exception("Mutiple data error should be 1");
        }
        return  (Hashtable)result.get(1);
    }

    /**
     *更新數據
     * @param sql
     * @param ds
     * @return
     * @throws Exception
     */
    public Vector updateData(String sql,String ds)throws Exception{
        Vector arg = new Vector();
        arg.add(sql);

        return this.updateData(arg,ds);
    }

    /**
     * 可更新多筆數據
     * @param sqlVector　存放String 型態的SQL,可多筆
     * @param ds
     * @return
     * @throws Exception
     */
    public Vector updateData(Vector sqlVector,String ds)throws Exception{
        BaseServletAgent bsa1 = new BaseServletAgent(uiVO);
        BaseVO bvo = new BaseVO(uiVO,this.getClass().toString(),CommandName.CallSQLUpdate,sqlVector);
        bvo.setDataSourceType(ds);
        ResultVO rvo = bsa1.doFunc(bvo);
        Vector result = rvo.getData();
        message = rvo.getErrorMessage();
        if(result==null || result.size() < 2){
            return null;
        }
        return result;
    }



}
