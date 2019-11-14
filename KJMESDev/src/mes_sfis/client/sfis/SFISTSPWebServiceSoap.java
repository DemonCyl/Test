/**
 * SFISTSPWebServiceSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package mes_sfis.client.sfis;

public interface SFISTSPWebServiceSoap extends java.rmi.Remote {
    public String getDatabaseInformation() throws java.rmi.RemoteException;
    public String WTSP_LOGINOUT(String programId, String programPassword, String op, String password, String device, String TSP, int status) throws java.rmi.RemoteException;
    public String WTSP_LOGINOUT_PROGID(String programId, String programPassword, String op, String password, String device, String TSP, int status, String PROGID, String SRC, String VER) throws java.rmi.RemoteException;
    public String WTSP_CHKROUTE(String programId, String programPassword, String ISN, String device, String checkFlag, String checkData, int type) throws java.rmi.RemoteException;
    public String WTSP_CHKROUTE_AOILOC(String programId, String programPassword, String ISN, String device, String checkFlag, String checkData, int type, String aoiloc) throws java.rmi.RemoteException;
    public String WTSP_GETVERSION(String programId, String programPassword, String ISN, String device, String type, String chkData, String chkData2) throws java.rmi.RemoteException;
    public String WTSP_RESULT(String programId, String programPassword, String ISN, String error, String device, String TSP, String data, int status, String CPKFlag) throws java.rmi.RemoteException;
    public String WTSP_RESULT_MASSDATA(String programId, String programPassword, String ISN, String error, String device, String TSP, String data, int status, String CPKFlag, String aoiloc, String data2, String data3, String data4, String data5, String data6, String data7, String data8) throws java.rmi.RemoteException;
    public String WTSP_GETIMAC(String programId, String programPassword, String device, String ISN, int status, int imacnum) throws java.rmi.RemoteException;
    public String WTSP_GETIMAC_T(String programId, String programPassword, String device, String ISN, int status, int imacnum, int type) throws java.rmi.RemoteException;
    public String WTSP_REPAIR(String programId, String programPassword, String TYPE, String ISN, String DEV, String REASON, String DUTY, String NGRP, String TSP) throws java.rmi.RemoteException;
    public String WTSP_DEVIF_MO(String programId, String programPassword, String device, String MO) throws java.rmi.RemoteException;
    public String WTSP_GETI1394(String programId, String programPassword, String ISN, String device, int status, int i1394NUM) throws java.rmi.RemoteException;
    public String WTSP_GETLABEL(String programId, String programPassword, String type, String ISN, String DEV, String status) throws java.rmi.RemoteException;
    public String WTSP_LOADKEY(String programId, String programPassword, String SNTYPE, String DATA, String OP) throws java.rmi.RemoteException;
    public String WTSP_SSD_INPUTDATA(String programId, String programPassword, String device, String data, String type) throws java.rmi.RemoteException;
    public String WTSP_INPUTGSDATA(String programId, String programPassword, String ISN, String data) throws java.rmi.RemoteException;
    public String WTSP_ONLINE_REWORK_N(String programId, String programPassword, String type, String data1, String data2, String data3, String flag) throws java.rmi.RemoteException;
    public String WTSP_SEND_MAIL(String programId, String programPassword, String subject, String sendto, String sendtext) throws java.rmi.RemoteException;
    public String WTSP_ASSIGN_DEVICE(String programId, String programPassword, String type, String DEVType, String DEVID, String ACTType, String ISN, String flag, String data1, String data2) throws java.rmi.RemoteException;
}
