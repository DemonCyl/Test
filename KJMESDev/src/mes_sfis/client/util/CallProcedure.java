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
//戳э皌竚弄
    public void open(){
        try {
            Class.forName(name);
            //莉渺钡
            conn = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //把计摸  sql 纗筁祘块把计块把计
    public ArrayList CallProcedure(String sql, HashMap<Integer,Object> indata, HashMap<Integer,Integer> outdata){
        ArrayList list=new ArrayList();
        try {
            stmt = conn.prepareCall(sql);
            //筂菌块把计
            for (int key :indata.keySet()){
               stmt.setObject(key,indata.get(key));
            }
            //筂菌块把计
            for (int key:outdata.keySet()){
                stmt.registerOutParameter(key,outdata.get(key));
            }
            stmt.execute();
            //块挡狦
            for (int key:outdata.keySet()){
                list.add(key+"="+stmt.getObject(key));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
    //把计摸  sql 纗筁祘块把计
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
    //把计摸  sql 纗筁祘块把计
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
    //礚块块把 秸ノ纗筁祘
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
