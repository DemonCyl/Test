package mes_sfis.client.sfis;

public class SFISTSPWebServiceSoapProxy implements SFISTSPWebServiceSoap {
  private String _endpoint = null;
  private SFISTSPWebServiceSoap sFISTSPWebServiceSoap = null;
  
  public SFISTSPWebServiceSoapProxy() {
    _initSFISTSPWebServiceSoapProxy();
  }
  
  public SFISTSPWebServiceSoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initSFISTSPWebServiceSoapProxy();
  }
  
  private void _initSFISTSPWebServiceSoapProxy() {
    try {
      sFISTSPWebServiceSoap = (new SFISTSPWebServiceLocator()).getSFISTSPWebServiceSoap();
      if (sFISTSPWebServiceSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)sFISTSPWebServiceSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)sFISTSPWebServiceSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (sFISTSPWebServiceSoap != null)
      ((javax.xml.rpc.Stub)sFISTSPWebServiceSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public SFISTSPWebServiceSoap getSFISTSPWebServiceSoap() {
    if (sFISTSPWebServiceSoap == null)
      _initSFISTSPWebServiceSoapProxy();
    return sFISTSPWebServiceSoap;
  }
  
  public String getDatabaseInformation() throws java.rmi.RemoteException{
    if (sFISTSPWebServiceSoap == null)
      _initSFISTSPWebServiceSoapProxy();
    return sFISTSPWebServiceSoap.getDatabaseInformation();
  }
  
  public String WTSP_LOGINOUT(String programId, String programPassword, String op, String password, String device, String TSP, int status) throws java.rmi.RemoteException{
    if (sFISTSPWebServiceSoap == null)
      _initSFISTSPWebServiceSoapProxy();
    return sFISTSPWebServiceSoap.WTSP_LOGINOUT(programId, programPassword, op, password, device, TSP, status);
  }
  
  public String WTSP_LOGINOUT_PROGID(String programId, String programPassword, String op, String password, String device, String TSP, int status, String PROGID, String SRC, String VER) throws java.rmi.RemoteException{
    if (sFISTSPWebServiceSoap == null)
      _initSFISTSPWebServiceSoapProxy();
    return sFISTSPWebServiceSoap.WTSP_LOGINOUT_PROGID(programId, programPassword, op, password, device, TSP, status, PROGID, SRC, VER);
  }
  
  public String WTSP_CHKROUTE(String programId, String programPassword, String ISN, String device, String checkFlag, String checkData, int type) throws java.rmi.RemoteException{
    if (sFISTSPWebServiceSoap == null)
      _initSFISTSPWebServiceSoapProxy();
    return sFISTSPWebServiceSoap.WTSP_CHKROUTE(programId, programPassword, ISN, device, checkFlag, checkData, type);
  }
  
  public String WTSP_CHKROUTE_AOILOC(String programId, String programPassword, String ISN, String device, String checkFlag, String checkData, int type, String aoiloc) throws java.rmi.RemoteException{
    if (sFISTSPWebServiceSoap == null)
      _initSFISTSPWebServiceSoapProxy();
    return sFISTSPWebServiceSoap.WTSP_CHKROUTE_AOILOC(programId, programPassword, ISN, device, checkFlag, checkData, type, aoiloc);
  }
  
  public String WTSP_GETVERSION(String programId, String programPassword, String ISN, String device, String type, String chkData, String chkData2) throws java.rmi.RemoteException{
    if (sFISTSPWebServiceSoap == null)
      _initSFISTSPWebServiceSoapProxy();
    return sFISTSPWebServiceSoap.WTSP_GETVERSION(programId, programPassword, ISN, device, type, chkData, chkData2);
  }
  
  public String WTSP_RESULT(String programId, String programPassword, String ISN, String error, String device, String TSP, String data, int status, String CPKFlag) throws java.rmi.RemoteException{
    if (sFISTSPWebServiceSoap == null)
      _initSFISTSPWebServiceSoapProxy();
    return sFISTSPWebServiceSoap.WTSP_RESULT(programId, programPassword, ISN, error, device, TSP, data, status, CPKFlag);
  }
  
  public String WTSP_RESULT_MASSDATA(String programId, String programPassword, String ISN,String error, String device, String TSP, String data, int status, String CPKFlag, String aoiloc, String data2, String data3, String data4, String data5, String data6, String data7, String data8) throws java.rmi.RemoteException{
    if (sFISTSPWebServiceSoap == null)
      _initSFISTSPWebServiceSoapProxy();
    return sFISTSPWebServiceSoap.WTSP_RESULT_MASSDATA(programId, programPassword, ISN, error, device, TSP, data, status, CPKFlag, aoiloc, data2, data3, data4, data5, data6, data7, data8);
  }
  
  public String WTSP_GETIMAC(String programId, String programPassword, String device, String ISN, int status, int imacnum) throws java.rmi.RemoteException{
    if (sFISTSPWebServiceSoap == null)
      _initSFISTSPWebServiceSoapProxy();
    return sFISTSPWebServiceSoap.WTSP_GETIMAC(programId, programPassword, device, ISN, status, imacnum);
  }
  
  public String WTSP_GETIMAC_T(String programId, String programPassword, String device, String ISN, int status, int imacnum, int type) throws java.rmi.RemoteException{
    if (sFISTSPWebServiceSoap == null)
      _initSFISTSPWebServiceSoapProxy();
    return sFISTSPWebServiceSoap.WTSP_GETIMAC_T(programId, programPassword, device, ISN, status, imacnum, type);
  }
  
  public String WTSP_REPAIR(String programId, String programPassword, String TYPE, String ISN, String DEV, String REASON, String DUTY, String NGRP, String TSP) throws java.rmi.RemoteException{
    if (sFISTSPWebServiceSoap == null)
      _initSFISTSPWebServiceSoapProxy();
    return sFISTSPWebServiceSoap.WTSP_REPAIR(programId, programPassword, TYPE, ISN, DEV, REASON, DUTY, NGRP, TSP);
  }
  
  public String WTSP_DEVIF_MO(String programId, String programPassword, String device, String MO) throws java.rmi.RemoteException{
    if (sFISTSPWebServiceSoap == null)
      _initSFISTSPWebServiceSoapProxy();
    return sFISTSPWebServiceSoap.WTSP_DEVIF_MO(programId, programPassword, device, MO);
  }
  
  public String WTSP_GETI1394(String programId, String programPassword, String ISN, String device, int status, int i1394NUM) throws java.rmi.RemoteException{
    if (sFISTSPWebServiceSoap == null)
      _initSFISTSPWebServiceSoapProxy();
    return sFISTSPWebServiceSoap.WTSP_GETI1394(programId, programPassword, ISN, device, status, i1394NUM);
  }
  
  public String WTSP_GETLABEL(String programId, String programPassword, String type, String ISN, String DEV, String status) throws java.rmi.RemoteException{
    if (sFISTSPWebServiceSoap == null)
      _initSFISTSPWebServiceSoapProxy();
    return sFISTSPWebServiceSoap.WTSP_GETLABEL(programId, programPassword, type, ISN, DEV, status);
  }
  
  public String WTSP_LOADKEY(String programId, String programPassword, String SNTYPE, String DATA, String OP) throws java.rmi.RemoteException{
    if (sFISTSPWebServiceSoap == null)
      _initSFISTSPWebServiceSoapProxy();
    return sFISTSPWebServiceSoap.WTSP_LOADKEY(programId, programPassword, SNTYPE, DATA, OP);
  }
  
  public String WTSP_SSD_INPUTDATA(String programId, String programPassword, String device, String data, String type) throws java.rmi.RemoteException{
    if (sFISTSPWebServiceSoap == null)
      _initSFISTSPWebServiceSoapProxy();
    return sFISTSPWebServiceSoap.WTSP_SSD_INPUTDATA(programId, programPassword, device, data, type);
  }
  
  public String WTSP_INPUTGSDATA(String programId, String programPassword, String ISN, String data) throws java.rmi.RemoteException{
    if (sFISTSPWebServiceSoap == null)
      _initSFISTSPWebServiceSoapProxy();
    return sFISTSPWebServiceSoap.WTSP_INPUTGSDATA(programId, programPassword, ISN, data);
  }
  
  public String WTSP_ONLINE_REWORK_N(String programId, String programPassword, String type, String data1, String data2, String data3, String flag) throws java.rmi.RemoteException{
    if (sFISTSPWebServiceSoap == null)
      _initSFISTSPWebServiceSoapProxy();
    return sFISTSPWebServiceSoap.WTSP_ONLINE_REWORK_N(programId, programPassword, type, data1, data2, data3, flag);
  }
  
  public String WTSP_SEND_MAIL(String programId, String programPassword, String subject, String sendto, String sendtext) throws java.rmi.RemoteException{
    if (sFISTSPWebServiceSoap == null)
      _initSFISTSPWebServiceSoapProxy();
    return sFISTSPWebServiceSoap.WTSP_SEND_MAIL(programId, programPassword, subject, sendto, sendtext);
  }
  
  public String WTSP_ASSIGN_DEVICE(String programId, String programPassword, String type, String DEVType, String DEVID, String ACTType, String ISN, String flag, String data1, String data2) throws java.rmi.RemoteException{
    if (sFISTSPWebServiceSoap == null)
      _initSFISTSPWebServiceSoapProxy();
    return sFISTSPWebServiceSoap.WTSP_ASSIGN_DEVICE(programId, programPassword, type, DEVType, DEVID, ACTType, ISN, flag, data1, data2);
  }
  
  
}