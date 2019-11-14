package mes_sfis.client.util;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by Efil_Ding on 2018/10/19.
 */
public class CallProcedure {

    public static final String url = "jdbc:oracle:thin:@sfisdb01.ch.casetekcorp.com:1521:szihdb0";
    public static final String name = "oracle.jdbc.driver.OracleDriver";
    public static final String user = "OL_ASP";
    public static final String password = "AOLSUS";

    public Connection conn = null;
    public PreparedStatement pst = null;
    public CallableStatement stmt = null;
//����i�קאּ�t�mŪ���C
    public void open(){
        try {
            Class.forName(name);
            //����챵
            conn = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //�Ѽ�����  sql �s�x�L�{�A��J�ѼơA��X�Ѽ�
    public ArrayList CallProcedure(String sql, HashMap<Integer,Object> indata, HashMap<Integer,Integer> outdata){
        ArrayList list=new ArrayList();
        try {
            stmt = conn.prepareCall(sql);
            //�M����J�Ѽ�
            for (int key :indata.keySet()){
               stmt.setObject(key,indata.get(key));
            }
            //�M����X�Ѽ�
            for (int key:outdata.keySet()){
                stmt.registerOutParameter(key,outdata.get(key));
            }
            stmt.execute();
            //��X��^���G
            for (int key:outdata.keySet()){
                list.add(key+"="+stmt.getObject(key));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
    //�Ѽ�����  sql �s�x�L�{�A��J�Ѽ�
    public Boolean CallProcedureIn(String sql, HashMap<Integer,Object> indata){
        boolean result=false;
        try {
            stmt = conn.prepareCall(sql);
            for (int key :indata.keySet()){
                stmt.setObject(key,indata.get(key));
            }
            stmt.execute();
            result=true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    //�Ѽ�����  sql �s�x�L�{�A��X�Ѽ�
    public ArrayList CallProcedureOut(String sql ,HashMap<Integer,Integer> outdata){
        ArrayList list=new ArrayList();
        try {
            stmt = conn.prepareCall(sql);
            for (int key:outdata.keySet()){
                stmt.registerOutParameter(key,outdata.get(key));
            }
            stmt.execute();
            for (int key:outdata.keySet()){
                list.add(key+"="+stmt.getObject(key));
//                list.add(key,stmt.getObject(key));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    //�L��J��X�� �եΦs�x�L�{
    public Boolean CallProcedure(String sql){
        boolean result=false;
        try {
            stmt = conn.prepareCall(sql);
            stmt.execute();
            result=true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    public void close() {
        try {
            this.stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            this.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
