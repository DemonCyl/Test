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
    public static final String ChkData="1";//1��ܦ��\�L���A0��ܥ����\�L��
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
            //WTSP_GETVERSION�bWSDL������k�W��
            SOAPElement soapBodyElem = soapBody.addChildElement("WTSP_GETVERSION");

            //�e�X�H���A�ܰʪ��r�q�ѥ~�ǤJ�A�T�w���r�q�i�H�]�w���`�q
            soapBodyElem.addChildElement("programId").addTextNode(programId);

            soapBodyElem.addChildElement("programPassword").addTextNode(programPassword);

            soapBodyElem.addChildElement("ISN").addTextNode(ISN);

            soapBodyElem.addChildElement("device").addTextNode(Device);

            soapBodyElem.addChildElement("type").addTextNode(type);

            soapBodyElem.addChildElement("ChkData").addTextNode(ChkData);

            soapBodyElem.addChildElement("ChkData2").addTextNode(ChkData2);

            //WS�bWSDL������k�W��
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
