/**
 * SFISTSPWebServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package mes_sfis.client.sfis;

public class SFISTSPWebServiceLocator extends org.apache.axis.client.Service implements SFISTSPWebService {

/**
 * SFIS Web Services for TSP[v1.55 (SOA)]
 */

    public SFISTSPWebServiceLocator() {
    }


    public SFISTSPWebServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public SFISTSPWebServiceLocator(String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for SFISTSPWebServiceSoap
    private String SFISTSPWebServiceSoap_address = "http://10.162.244.237/SFISWebService/SFISTSPWebService.asmx";

    public String getSFISTSPWebServiceSoapAddress() {
        return SFISTSPWebServiceSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private String SFISTSPWebServiceSoapWSDDServiceName = "SFISTSPWebServiceSoap";

    public String getSFISTSPWebServiceSoapWSDDServiceName() {
        return SFISTSPWebServiceSoapWSDDServiceName;
    }

    public void setSFISTSPWebServiceSoapWSDDServiceName(String name) {
        SFISTSPWebServiceSoapWSDDServiceName = name;
    }

    public SFISTSPWebServiceSoap getSFISTSPWebServiceSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(SFISTSPWebServiceSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSFISTSPWebServiceSoap(endpoint);
    }

    public SFISTSPWebServiceSoap getSFISTSPWebServiceSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            SFISTSPWebServiceSoapStub _stub = new SFISTSPWebServiceSoapStub(portAddress, this);
            _stub.setPortName(getSFISTSPWebServiceSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setSFISTSPWebServiceSoapEndpointAddress(String address) {
        SFISTSPWebServiceSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (SFISTSPWebServiceSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                SFISTSPWebServiceSoapStub _stub = new SFISTSPWebServiceSoapStub(new java.net.URL(SFISTSPWebServiceSoap_address), this);
                _stub.setPortName(getSFISTSPWebServiceSoapWSDDServiceName());
                return _stub;
            }
        }
        catch (Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        String inputPortName = portName.getLocalPart();
        if ("SFISTSPWebServiceSoap".equals(inputPortName)) {
            return getSFISTSPWebServiceSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.pegatroncorp.com/SFISWebService/", "SFISTSPWebService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.pegatroncorp.com/SFISWebService/", "SFISTSPWebServiceSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(String portName, String address) throws javax.xml.rpc.ServiceException {
        
if ("SFISTSPWebServiceSoap".equals(portName)) {
            setSFISTSPWebServiceSoapEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
