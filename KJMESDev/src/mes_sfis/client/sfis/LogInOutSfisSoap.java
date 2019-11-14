package mes_sfis.client.sfis;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.soap.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Chris1_Liao on 2018/5/24.
 */
public class LogInOutSfisSoap extends SoapBase{
    //�w�q�n�򥻫H��
    private static final Logger logger = LogManager.getLogger(LogInOutSfisSoap.class);
    public static final String PROGRAM_ID = "TSP_NLSPAD";
    public static final String PROGRAM_PASSWORD = "N3eaM;";
    public static final String NAME_SPACE = "sfis";
    public static final String TSP = "LINK";


    //�w�q�o��SOAP �@�~���ӶǤJ���H��
    String device;
    String op;
    String password;
    String status;//1�n���A2�n�X

    public LogInOutSfisSoap() throws SOAPException {
    }

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


    public SOAPMessage createSOAPRequest() throws SOAPException {

            //WS�bWSDL������k�W��
            SOAPElement soapBodyElem = soapBody.addChildElement("WTSP_LOGINOUT", NAME_SPACE);

            //�]�wSOAP �H�����e
            soapBodyElem.addChildElement("programId", NAME_SPACE).addTextNode(PROGRAM_ID);

            soapBodyElem.addChildElement("programPassword", NAME_SPACE).addTextNode(PROGRAM_PASSWORD);

            soapBodyElem.addChildElement("op", NAME_SPACE).addTextNode(op);

            soapBodyElem.addChildElement("device", NAME_SPACE).addTextNode(device);
            soapBodyElem.addChildElement("TSP", NAME_SPACE).addTextNode(TSP);

            soapBodyElem.addChildElement("status", NAME_SPACE).addTextNode(status);

            //WS�bWSDL������k�W��
            MimeHeaders headers = soapMessage.getMimeHeaders();
            headers.addHeader("SOAPAction", serverURI + "WTSP_LOGINOUT");

            soapMessage.saveChanges();


        return soapMessage;
    }

    public static void main(String args[]) throws Exception {

        SoapUtil su = new SoapUtil();

        LogInOutSfisSoap loc = new LogInOutSfisSoap();
        loc.setOp("K16000790");
        loc.setDevice("111043");
        loc.setStatus("1");

        List list = loc.createAndSendSOAPRequest(su);

        for(Iterator i = list.iterator();i.hasNext();){
            String temp = (String)i.next();
            logger.debug(temp);
        }

        if("0".equals(list.get(0))){
            logger.debug("�^�ǭ�0");
            String resultStr = (String) list.get(1);
            logger.debug("resultStr:"+resultStr);
            if(!resultStr.startsWith("Login Twice!")){

            }else{
                logger.debug("�٦b�n�J���A");
            }

        }
        su.close();
    }
}
