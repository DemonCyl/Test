package mes_sfis.client.sfis;

import javax.xml.soap.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Chris1_Liao on 2018/5/24.
 */
public abstract class SoapBaseThread extends Thread  {

    protected final static String SFIS_WEB_SERVICE_URL = "http://sfisws.ch.casetekcorp.com/SFISWebService/SFISTSPWebService.asmx";

    protected MessageFactory messageFactory;
    protected SOAPMessage soapMessage  ;
    protected SOAPPart soapPart ;

    protected String serverURI = "http://www.pegatroncorp.com/SFISWebService/";

    // SOAP Envelope
    protected SOAPEnvelope envelope ;
    protected SOAPBody soapBody ;
    public void init() throws SOAPException {
        messageFactory = MessageFactory.newInstance();
        soapMessage = messageFactory.createMessage();
        soapPart = soapMessage.getSOAPPart();

        // SOAP Envelope
        envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("sfis", serverURI);
        soapBody = envelope.getBody();
    }

    @Override
    public void run()  {
        try {
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            // Send SOAP Message to SOAP Server
            SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(), SFIS_WEB_SERVICE_URL);

            // print SOAP Response
            System.out.println("Response SOAP Message:");

            //save response string
            File file = new File("D:\\soapClient.txt");
            FileOutputStream fop = new FileOutputStream(file);
            if (!file.exists()) {
                file.createNewFile();
            }

            soapResponse.writeTo(fop);

            soapResponse.writeTo(System.out);
            soapConnection.close();
        } catch (SOAPException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    protected abstract SOAPMessage createSOAPRequest();

    protected String getXmlFromSOAPMessage(SOAPMessage msg) throws SOAPException, IOException {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        msg.writeTo(byteArrayOS);
        return new String(byteArrayOS.toByteArray());
    }
}
