package mes_sfis.client.sfis;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.soap.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Chris1_Liao on 2018/5/31.
 */
public class SoapUtil {
    private static final Logger logger = LogManager.getLogger(SoapUtil.class);
    protected final static String SFIS_WEB_SERVICE_URL = "http://sfisws.ch.casetekcorp.com/SFISWebService/SFISTSPWebService.asmx";

    protected SOAPConnectionFactory soapConnectionFactory;

    protected SOAPConnection soapConnection;


    public SoapUtil() throws SOAPException {
        soapConnectionFactory = SOAPConnectionFactory.newInstance();
        soapConnection = soapConnectionFactory.createConnection();
    }


    public SOAPMessage sendSoap(SOAPMessage sendMessage) throws SOAPException {

        SOAPMessage soapResponse = soapConnection.call(sendMessage, SFIS_WEB_SERVICE_URL);

        return soapResponse;

    }

    public void close() {
        try {
            soapConnection.close();
        } catch (SOAPException e) {
        }
    }

    public String getXmlFromSOAPMessage(SOAPMessage msg) throws SOAPException, IOException {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        msg.writeTo(byteArrayOS);
        return new String(byteArrayOS.toByteArray());
    }

    public List getFirstChildSoapMessageList(SOAPMessage soapMessage) throws SOAPException, IOException {

        logger.debug(getXmlFromSOAPMessage(soapMessage));

        SOAPBody body = soapMessage.getSOAPBody();
        String result = body.getFirstChild().getTextContent();
        logger.debug("result:" + result);
        String[] resultArr = result.split("\u007F");

        List returnList = Arrays.asList(resultArr);

        return returnList;
    }
}
