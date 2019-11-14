package mes_sfis.client.util;

/**
 * Created by Chris1_Liao on 2018/4/6.
 */



import java.io.ByteArrayOutputStream;
        import java.io.File;
        import java.io.FileOutputStream;
        import java.io.IOException;

        import javax.xml.soap.*;
//http://stackoverflow.com/questions/15948927/working-soap-client-example
//http://harryjoy.com/2011/10/20/soap-client-in-java/
//http://cafeconleche.org/books/xmljava/chapters/ch03s05.html
//maven http://www.logicsector.com/java/how-to-create-a-wsdl-first-soap-client-in-java-with-cxf-and-maven/
public class SoapClientUtil {


    //  http://ws.cdyne.com/emailverify/Emailvernotestemail.asmx?wsdl

    public static void main(String args[]) throws Exception {
        // Create SOAP Connection
        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection soapConnection = soapConnectionFactory.createConnection();

        // Send SOAP Message to SOAP Server
        String url = "http://sfisws.ch.casetekcorp.com/SFISWebService/SFISTSPWebService.asmx";
        SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(), url);

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
    }

    private static SOAPMessage createSOAPRequest() throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

        String serverURI = "http://www.pegatroncorp.com/SFISWebService/";

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("sfis", serverURI);

        /*example
        Constructed SOAP Request Message:
        <soap-env:envelope xmlns:example="http://ws.cdyne.com/" xmlns:soap-env="http://schemas.xmlsoap.org/soap/envelope/">
            <soap-env:header>
            <soap-env:body>
                <example:verifyemail>
                    <example:email>mutantninja@gmail.com</example:email>
                    <example:licensekey>123</example:licensekey>
                </example:verifyemail>
            </soap-env:body>
        </soap-env:header></soap-env:envelope>
         */

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement("WTSP_GETVERSION", "sfis");
        SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("programId", "sfis");
        soapBodyElem1.addTextNode("TSP_NLSPAD");

        SOAPElement soapBodyElem2 = soapBodyElem.addChildElement("programPassword", "sfis");
        soapBodyElem2.addTextNode("N3eaM;");

        soapBodyElem.addChildElement("ISN", "sfis").addTextNode("GKP8100002BJKKX4X50");

        soapBodyElem.addChildElement("device", "sfis").addTextNode("111041");

        soapBodyElem.addChildElement("type", "sfis").addTextNode("YH_LASER_26AB");

        soapBodyElem.addChildElement("Chk", "sfis").addTextNode("SN");

        soapBodyElem.addChildElement("ChkData2", "sfis").addTextNode("?");





        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", serverURI  + "WTSP_GETVERSION");

        soapMessage.saveChanges();

        /* Print the request message */
        System.out.println("Request SOAP Message:");
        soapMessage.writeTo(System.out);
        System.out.println("");
        System.out.println("getXmlFromSOAPMessage============");
        //get request string
        System.out.println(getXmlFromSOAPMessage(soapMessage));

        return soapMessage;
    }

    static String getXmlFromSOAPMessage(SOAPMessage msg) throws SOAPException, IOException {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        msg.writeTo(byteArrayOS);
        return new String(byteArrayOS.toByteArray());
    }
}