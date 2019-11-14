package mes_sfis.client.sfis;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Xiaojian1_Yu on 2018/10/12.
 */
public class Check_ISN extends SoapBase {
    private static final Logger logger = LogManager.getLogger(Check_ISN.class);
    public static final String PROGRAM_ID = "TSP_NLSPAD";
    public static final String PROGRAM_PASSWORD = "N3eaM;";
    public static final String type = "YH_ROUTE_NSTEP";
    public static final String ChkData = "SN";
    public static final String ChkData2 = "?";

    String ISN;
    String device;

    public String getISN() {
        return ISN;
    }

    public void setISN(String ISN) {
        this.ISN = ISN;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public Check_ISN() throws SOAPException {
    }

    @Override
    public SOAPMessage createSOAPRequest() throws SOAPException {
        try {
            try {
                //WS�bWSDL������k�W��
                SOAPElement soapBodyElem = soapBody.addChildElement("WTSP_GETVERSION", "sfis");

                //�e�X�H���A�ܰʪ��r�q�ѥ~�ǤJ�A�T�w���r�q�i�H�]�w���`�q
                soapBodyElem.addChildElement("programId", "sfis").addTextNode(PROGRAM_ID);

                soapBodyElem.addChildElement("programPassword", "sfis").addTextNode(PROGRAM_PASSWORD);

                soapBodyElem.addChildElement("ISN", "sfis").addTextNode(ISN);

                soapBodyElem.addChildElement("device", "sfis").addTextNode(device);

                soapBodyElem.addChildElement("type", "sfis").addTextNode(type);

                soapBodyElem.addChildElement("ChkData", "sfis").addTextNode(ChkData);

                soapBodyElem.addChildElement("ChkData2", "sfis").addTextNode(ChkData2);

                logger.debug("ISN:" + ISN + " device:" + device);
            } catch (Exception e) {
                e.getStackTrace();
                logger.debug("soapBodyElem addChildElement���`�G" + e.getMessage());
            }
            //WS�bWSDL������k�W��
            MimeHeaders headers = soapMessage.getMimeHeaders();
            headers.addHeader("SOAPAction", serverURI + "WTSP_GETVERSION");
            soapMessage.saveChanges();
        } catch (SOAPException e) {
//            System.out.println(e.getMessage());
            logger.debug("�ˬdISN���`�G" + e.getMessage());
            e.printStackTrace();
        }
        return soapMessage;
    }

    public static void main(String args[]) throws Exception {
        SoapUtil su = new SoapUtil();
        Check_ISN check_isn = new Check_ISN();
        check_isn.setISN("GKP840421GRJKKX5Q65");
        check_isn.setDevice("1721010051");
        List list = check_isn.createAndSendSOAPRequest(su);
        if ("0".equals(list.get(0))) {
            System.out.println("����L��:" + list.get(1));
        }
        if ("1".equals(list.get(0))) {
            System.out.println("�i�H�L��:" + list.get(1));
        }
        su.close();
    }
}
