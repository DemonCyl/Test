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
 * Created by Srx_Zhu on 2018/08/11.
 */
public class RepairSfisSoap extends SoapBase{
    private static final Logger logger = LogManager.getLogger(PassDeviceSfisSoap.class);
    public static final String PROGRAMID = "TSP_NLSPAD";
    public static final String PROGRAM_PASSWORD = "N3eaM;";
    public static final String TYPE = "1";
    public String DEVICE = "111016";
    public static final String REASON = "00";
    public static final String DUTY = "700";
    public static final String NAME_SPACE = "sfis";
    public static final String NGRP = "";
    public static final String TSP = "";

    String ISN;
    String device;

    public RepairSfisSoap() throws SOAPException {
    }

    public String getISN() {
        return ISN;
    }

    public void setISN(String ISN) {
        this.ISN = ISN;
    }

    public String getDEVICE() {
        return DEVICE;
    }

    public void setDEVICE(String DEVICE) {
        this.DEVICE = DEVICE;
    }

    @Override
    public SOAPMessage createSOAPRequest() {

        try {

            //WS在WSDL中的方法名稱
            SOAPElement soapBodyElem = soapBody.addChildElement("WTSP_REPAIR", NAME_SPACE);

            //送出信息，變動的字段由外傳入，固定的字段可以設定為常量
            soapBodyElem.addChildElement("programId", NAME_SPACE).addTextNode(PROGRAMID);

            soapBodyElem.addChildElement("programPassword", NAME_SPACE).addTextNode(PROGRAM_PASSWORD);

            soapBodyElem.addChildElement("TYPE", NAME_SPACE).addTextNode(TYPE);

            soapBodyElem.addChildElement("DEV", NAME_SPACE).addTextNode(DEVICE);

            soapBodyElem.addChildElement("ISN", NAME_SPACE).addTextNode(ISN);

            soapBodyElem.addChildElement("REASON", NAME_SPACE).addTextNode(REASON);

            soapBodyElem.addChildElement("DUTY", NAME_SPACE).addTextNode(DUTY);

            soapBodyElem.addChildElement("NGRP", NAME_SPACE).addTextNode(NGRP);

            soapBodyElem.addChildElement("TSP", NAME_SPACE).addTextNode(TSP);

            //WS在WSDL中的方法名稱
            MimeHeaders headers = soapMessage.getMimeHeaders();
            headers.addHeader("SOAPAction", serverURI  + "WTSP_REPAIR");

            soapMessage.saveChanges();

        } catch (SOAPException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return soapMessage;
    }

    public static void main(String args[]) throws Exception {
        SoapUtil su = new SoapUtil();

        RepairSfisSoap rss = new RepairSfisSoap();
        rss.setISN("GKP8185046MJKKX4858");
        rss.setDEVICE("111016");
        List list = rss.createAndSendSOAPRequest(su);
        for(Iterator i = list.iterator(); i.hasNext();){
            String temp = (String)i.next();
            logger.debug(temp);
        }
        su.close();

    }
}
