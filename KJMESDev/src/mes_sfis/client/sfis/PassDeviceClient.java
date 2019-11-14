package mes_sfis.client.sfis;

import javax.xml.soap.*;
import java.io.IOException;
import java.util.Date;

/**
 * Created by Chris1_Liao on 2018/4/6.
 */
public class PassDeviceClient extends SoapBaseThread {

    public static final String PROGRAM_ID = "TSP_YARVND";
    public static final String PROGRAM_PASSWORD = "Xcf7h_";
    public static final String TYPE = "1";
    public static final String NAME_SPACE = "sfis";

    String data;
    String device;

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
    protected SOAPMessage createSOAPRequest() {

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

            /* Print the request message */
            System.out.println("Request SOAP Message:");
            soapMessage.writeTo(System.out);
            System.out.println("");
            System.out.println("getXmlFromSOAPMessage============");
            //get request string
            System.out.println(getXmlFromSOAPMessage(soapMessage));
        } catch (SOAPException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return soapMessage;
    }

    public static void main(String args[]) throws Exception {
        //Date start = new Date();
        for(int i=0; i<180;i++){
            PassDeviceClient pdc = new PassDeviceClient();
            pdc.setData("GKP8100001YJKKX4X50");
            pdc.setDevice("111043");
            pdc.init();
            pdc.start();
        }
        //Date end = new Date();
    }
}
