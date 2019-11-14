package mes_sfis.client.model.service;

import base.enums.DataSourceType;
import base.vo.UI_InitVO;
import mes_sfis.client.sfis.LoginOutClient;
import mes_sfis.client.task.PassDeviceTask;
import mes_sfis.client.task.base.LongTask;
import mes_sfis.client.util.DataHandler;

import javax.xml.soap.SOAPException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Efil_Ding on 2018/5/3.
 */
public class BatchPassService extends BaseService {

    DataHandler dh;
    Set<String> set = null;
    private static UI_InitVO uiVO;

    public BatchPassService(UI_InitVO uiVO) {
        this.uiVO = uiVO;
        dh = new DataHandler(uiVO);
    }

    public Hashtable CheckISN_State(String ISN) {
        Hashtable ht = null;
        String sql = "SELECT  NGRP FROM MO_D WHERE ISN='" + ISN + "'";
        System.out.println(sql);
        try {
            ht = dh.getDataOne(sql, DataSourceType._SFIS_KAIJIA_STD);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("checkgg");
        }
        return ht;
    }

    //?����k
    public void StatePass(String device, HashMap map, String op) {

        LongTask task = null;
        List list = new ArrayList<>();
        set = map.keySet(); //���X�Ҧ���key��
        for (String key : set) {
            list.add(key);
        }
        task = new PassDeviceTask(list.size());

        task.setIsnList(list);
        task.setDevice(device);
        task.setOp(op);
        task.getMessage();
        task.go();

        //�O�sISN�ƾڨ���
        txtfile(device, map, op);
    }

    public void LoginOut(String device, String op, String startus) {
        LoginOutClient loc = new LoginOutClient();
        loc.setOp(op);
        loc.setDevice(device);
        loc.setStatus(startus);
        try {
            loc.init();
        } catch (SOAPException e) {
            if (startus.equals("1")) {
                System.out.println("login gg");
            } else {
                System.out.println("logout  gg");
            }
        }
        loc.start();
    }

    //���C�� �Ҩ�(��q�L��)�A�C��SNE�r�q�]�w
    public int ISNColorSet(String color, HashMap map, String mce, String op, String device) {
        int a = 0;
        //�B�zISN
        String manyISN = manipulationISN(map);
        String sql = "UPDATE  ISNINFO  SET SNE='" + color + "'  WHERE  ISN  IN (" + manyISN + ")";
        Vector vector = new Vector();
        vector.add(sql);
        set = map.keySet(); //���X�Ҧ���key��
        for (String key : set) {
            sql = "INSERT INTO  MCEQUEUE(SEQ,DEVICE,MCE,ISN,INTIME,STATUS,MSG,OP) values  (MCEQUEUE_SEQ.NEXTVAL," + device + ",'" + mce + "','" + key + "',sysdate,'1','BatchPass','" + op + "')";
            vector.add(sql);
        }
        try {
            dh.updateData(vector, DataSourceType._SFIS_KAIJIA_STD);
            a=1;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("colorgg");
            a = 0;
        }
        return a;
    }

    //���,CELL(��q�L��)�A�����ӽs�X SNF�r�q�]�w
    public int ISNCodeSet(String code, HashMap map) {
        int a = 0;
        //�B�zISN
        String manyISN = manipulationISN(map);

        String sql = "UPDATE  ISNINFO  SET SNF='" + code + "'  WHERE  ISN  IN (" + manyISN + ")";
        System.out.println(sql);
        try {
            dh.updateData(sql, DataSourceType._SFIS_KAIJIA_STD);
            a=1;
        } catch (Exception e) {
            a = 0;
            System.out.println("codegg");
        }
        return a;
    }

    public String manipulationISN(HashMap map) {

        String str = "efil";
        set = map.keySet(); //���X�Ҧ���key��
        System.out.println(set);
        for (String key : set) {
            str += ",'" + key + "'";
        }
        str = str.replaceFirst("efil,", "");
        return str;
    }

    public void txtfile(String device, HashMap map, String op) {

        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");//�i�H��K�a�ק����榡
        String hehe = dateFormat.format(now);
        String name = "D:\\" + op + "_" + device + "BatchPass" + hehe + ".txt";

        File file = new File(name);
        String content = map.toString();
        FileWriter fw = null;
        BufferedWriter bw = null;

        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fw = new FileWriter(file.getAbsoluteFile(), true);  //true��ܥi�H�l�[�s?�e
            //fw=new FileWriter(f.getAbsoluteFile()); //��ܤ��l�[
            bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
