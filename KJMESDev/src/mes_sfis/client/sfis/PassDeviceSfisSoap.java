package mes_sfis.client.sfis;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Chris1_Liao on 2018/4/6.
 */
public class PassDeviceSfisSoap extends SoapBase {
    private static final Logger logger = LogManager.getLogger(PassDeviceSfisSoap.class);
    public static final String PROGRAM_ID = "TSP_YARVND";
    public static final String PROGRAM_PASSWORD = "Xcf7h_";
    public static final String TYPE = "1";
    public static final String NAME_SPACE = "sfis";


    String data;
    String device;

    public PassDeviceSfisSoap() throws SOAPException {
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    @Override
    public SOAPMessage createSOAPRequest() {

        try {

            //WS在WSDL中的方法名稱
            SOAPElement soapBodyElem = soapBody.addChildElement("WTSP_SSD_INPUTDATA", NAME_SPACE);

            //送出信息，變動的字段由外傳入，固定的字段可以設定為常量
            soapBodyElem.addChildElement("programId", NAME_SPACE).addTextNode(PROGRAM_ID);

            soapBodyElem.addChildElement("programPassword", NAME_SPACE).addTextNode(PROGRAM_PASSWORD);

            soapBodyElem.addChildElement("data", NAME_SPACE).addTextNode(data);

            soapBodyElem.addChildElement("device", NAME_SPACE).addTextNode(device);

            soapBodyElem.addChildElement("type", NAME_SPACE).addTextNode(TYPE);

            //WS在WSDL中的方法名稱
            MimeHeaders headers = soapMessage.getMimeHeaders();
            headers.addHeader("SOAPAction", serverURI  + "WTSP_SSD_INPUTDATA");

            soapMessage.saveChanges();

        } catch (SOAPException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return soapMessage;
    }

    public static void main(String args[]) throws Exception {
        SoapUtil su = new SoapUtil();

        PassDeviceSfisSoap pdc = new PassDeviceSfisSoap();
        pdc.setData("GKP8100001YJKKX4X50");
        pdc.setDevice("111043");
        List list = pdc.createAndSendSOAPRequest(su);


        for(Iterator i = list.iterator(); i.hasNext();){
            String temp = (String)i.next();
            logger.debug(temp);
        }

        if("0".equals(list.get(0))){
            logger.debug("過站失敗:"+list.get(1));
        }

        su.close();

    }
}
