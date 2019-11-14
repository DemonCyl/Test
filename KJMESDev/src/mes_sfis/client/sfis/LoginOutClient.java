package mes_sfis.client.sfis;

import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import java.io.IOException;

/**
 * Created by Chris1_Liao on 2018/4/6.
 */
public class LoginOutClient extends SoapBaseThread {

    public static final String PROGRAM_ID = "TSP_NLSPAD";
    public static final String PROGRAM_PASSWORD = "N3eaM;";
    public static final String NAME_SPACE = "sfis";
    public static final String TSP = "LINK";


    String device;
    String op;
    String password;
    String status;//1登錄，2登出

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
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
            SOAPElement soapBodyElem = soapBody.addChildElement("WTSP_LOGINOUT", NAME_SPACE);

            //送出信息，變動的字段由外傳入，固定的字段可以設定為常量
            soapBodyElem.addChildElement("programId", NAME_SPACE).addTextNode(PROGRAM_ID);

            soapBodyElem.addChildElement("programPassword", NAME_SPACE).addTextNode(PROGRAM_PASSWORD);

            soapBodyElem.addChildElement("op", NAME_SPACE).addTextNode(op);

            soapBodyElem.addChildElement("device", NAME_SPACE).addTextNode(device);
            soapBodyElem.addChildElement("TSP", NAME_SPACE).addTextNode(TSP);

            soapBodyElem.addChildElement("status", NAME_SPACE).addTextNode(status);

            //WS在WSDL中的方法名稱
            MimeHeaders headers = soapMessage.getMimeHeaders();
            headers.addHeader("SOAPAction", serverURI + "WTSP_LOGINOUT");

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
//        for(int i=0; 1<180;i++){
//            PassDeviceClient pdc = new PassDeviceClient();
//            pdc.setData("GKP8100001YJKKX4X50");
//            pdc.setDevice("111043");
//            pdc.init();
//            pdc.start();
//        }
        LoginOutClient loc = new LoginOutClient();
        loc.setOp("SYSTEM");
        loc.setDevice("111016");
        loc.setStatus("1");
        loc.init();
        loc.start();
        System.out.print("aa");

        //Date end = new Date();
    }
}
