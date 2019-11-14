/**
 * SFISTSPWebService.java
 * <p/>
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package mes_sfis.client.sfis;

public interface SFISTSPWebService extends javax.xml.rpc.Service {

    /**
     * SFIS Web Services for TSP[v1.55 (SOA)]
     */
    public String getSFISTSPWebServiceSoapAddress();

    public SFISTSPWebServiceSoap getSFISTSPWebServiceSoap() throws javax.xml.rpc.ServiceException;

    public SFISTSPWebServiceSoap getSFISTSPWebServiceSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
