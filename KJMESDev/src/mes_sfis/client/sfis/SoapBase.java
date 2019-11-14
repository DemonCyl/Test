package mes_sfis.client.sfis;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.soap.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Chris1_Liao on 2018/4/6.
 */
public abstract class SoapBase {
    private static final Logger logger = LogManager.getLogger(SoapBase.class);
    protected MessageFactory messageFactory;
    protected SOAPMessage soapMessage  ;
    protected SOAPPart soapPart ;
    protected SOAPEnvelope envelope ;
    protected SOAPBody soapBody ;

    protected String serverURI = "http://www.pegatroncorp.com/SFISWebService/";

    public SoapBase() throws SOAPException {
        messageFactory = MessageFactory.newInstance();
        soapMessage = messageFactory.createMessage();
        soapPart = soapMessage.getSOAPPart();

        // SOAP Envelope
        envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("sfis", this.serverURI);
        soapBody = envelope.getBody();
    }

    public abstract SOAPMessage createSOAPRequest() throws SOAPException;

    public List createAndSendSOAPRequest(SoapUtil su) throws SOAPException, IOException {
        SOAPMessage sendMessage = this.createSOAPRequest();

        SOAPMessage resMessage = su.sendSoap(sendMessage);

        return su.getFirstChildSoapMessageList(resMessage);
    }


}
