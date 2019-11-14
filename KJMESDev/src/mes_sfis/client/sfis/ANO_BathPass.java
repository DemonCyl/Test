package mes_sfis.client.sfis;

import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import java.util.Iterator;
import java.util.List;

public class ANO_BathPass extends SoapBase {

    public static final String programId="TSP_NLSPAD";
    public static final String programPassword="N3eaM";
    public static final String type="YH_LASER_SN";
    public static final String ChkData="1";//1表示成功過站，0表示未成功過站
    public static final String ChkData2="";
    String ISN;
    String Device;

    public String getDevice() {return Device;}

    public void setDevice(String device) {Device = device;}

    public String getISN() {return ISN;}

    public void setISN(String ISN) {this.ISN = ISN;}

    public ANO_BathPass() throws SOAPException {
    }

    @Override
    public SOAPMessage createSOAPRequest() throws SOAPException {
        try{
            //WTSP_GETVERSION在WSDL中的方法名稱
            SOAPElement soapBodyElem = soapBody.addChildElement("WTSP_GETVERSION");

            //送出信息，變動的字段由外傳入，固定的字段可以設定為常量
            soapBodyElem.addChildElement("programId").addTextNode(programId);

            soapBodyElem.addChildElement("programPassword").addTextNode(programPassword);

            soapBodyElem.addChildElement("ISN").addTextNode(ISN);

            soapBodyElem.addChildElement("device").addTextNode(Device);

            soapBodyElem.addChildElement("type").addTextNode(type);

            soapBodyElem.addChildElement("ChkData").addTextNode(ChkData);

            soapBodyElem.addChildElement("ChkData2").addTextNode(ChkData2);

            //WS在WSDL中的方法名稱
            MimeHeaders headers = soapMessage.getMimeHeaders();
            headers.addHeader("SOAPAction",serverURI+"WTSP_GETVERSION");
            soapMessage.saveChanges();
            System.out.println("111111111111111111111111111111");
        }catch (SOAPException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return soapMessage;
    }

    public static void main(String args[]) throws Exception {
        SoapUtil su = new SoapUtil();
        ANO_BathPass RSS = new ANO_BathPass();
        RSS.setISN("GKP82132B3TJKKX5H59");
        RSS.setDevice("999028");

        su.close();
    }
}
