package mes_sfis.client.sfis;

import mes_sfis.client.util.CallProcedure;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Efil_Ding on 2018/10/19.
 */
public class PassForProcedure {
    private static CallProcedure callProcedure=new CallProcedure();
    private  static ArrayList list=null;
    private  static String result=null;

    //�n���n�X
    public String procedureLogin(HashMap map){

        String sqllogin="{call TSP_LOGIN(?,?,?,?,?,?,?,?,?)}";
        HashMap indata=new HashMap();
        indata.put(1,map.get("OP"));
        indata.put(2,map.get("DEVICE"));
        indata.put(3,"");
        indata.put(4,map.get("STATUS"));
        HashMap outdata=new HashMap();
        outdata.put(5,12);
        outdata.put(6,12);
        outdata.put(7,2);
        outdata.put(8,12);
        outdata.put(9,12);
        list=callProcedure.CallProcedure(sqllogin,indata,outdata);
        result=list.get(2).toString()+list.get(3).toString();
        return result;
    }
    //�L��
    public String procedurePass(HashMap map){
        String sqlinputdata="{call TSP_SSD_INPUTDATA(?,?,?,?,?)}";
        //�s�x�L�{  ��J�Ѽ�
        HashMap indata2=new HashMap();
        indata2.put(1,map.get("DEVICE"));
        indata2.put(2,map.get("ISN"));
        indata2.put(5,1);
        //�s�x�L�{  ��X�Ѽ�
        HashMap outdata2=new HashMap();
        outdata2.put(3,2);
        outdata2.put(4,12);
        list=callProcedure.CallProcedure(sqlinputdata,indata2,outdata2);
        result=list.get(0).toString()+list.get(1).toString();
        return result;
    }

    public String inserterrflag(HashMap map){
        String sqlinputdata="{call TP.INS_ERROR_FLAG_N(?,?,?,?,?,?)}";
        //�s�x�L�{  ��J�Ѽ�
        HashMap indata2=new HashMap();
        indata2.put(1,map.get("ISN"));
        indata2.put(2,map.get("ERROR"));
        indata2.put(3,map.get("DEVICE"));
        indata2.put(4,map.get("OP"));
        //�s�x�L�{  ��X�Ѽ�
        HashMap outdata2=new HashMap();
        outdata2.put(5,2);
        outdata2.put(6,12);
        list=callProcedure.CallProcedure(sqlinputdata,indata2,outdata2);
        result=list.get(0).toString()+list.get(1).toString();
        return result;
    }
    public String repair(HashMap map){
        String sqlinputdata="{call TP.tsp_repair(?,?,?,?,?,?,?,?,?,?)}";
        //�s�x�L�{  ��J�Ѽ�
        HashMap indata2=new HashMap();
        indata2.put(1,"");
        indata2.put(2,map.get("ISN"));
        indata2.put(3,map.get("DEVICE"));
        indata2.put(4,"00");
        indata2.put(5,"700");
        indata2.put(6,"");
        indata2.put(7,"");
        indata2.put(10,"");
        //�s�x�L�{  ��X�Ѽ�
        HashMap outdata2=new HashMap();
        outdata2.put(8,2);
        outdata2.put(9,12);
        list=callProcedure.CallProcedure(sqlinputdata,indata2,outdata2);
        result=list.get(0).toString()+list.get(1).toString();
        return result;
    }
    public void open(){
        callProcedure.open();

    }
    public void close(){

        callProcedure.close();
    }
    //���եi��
    public static void main(String[] args) {
        PassForProcedure test=new PassForProcedure();
        //���}�ƾ��챵
        test.open();
        try {//�o�htry catch  �����p�ٲ�
            HashMap map = new HashMap();
            map.put("DEVICE", 999018);
            map.put("OP", "K18000205");
            map.put("ERROR", "ERR001");
            map.put("ISN", "GKP84811EM0JKKXAN0A");
           result =  test.inserterrflag(map);
            System.out.println(result);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //�����ƾ��챵
            test.close();
        }


    }

}
