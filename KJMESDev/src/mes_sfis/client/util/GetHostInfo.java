package mes_sfis.client.util;

/**
 * Created by andy_lau on 2019/1/14.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;


public class GetHostInfo {
    /**
     * ?取本机?网IP
     * @return
     */
    public static String getLocalHostIP(){
        return getLocalHost().getHostAddress();
    }

    /**
     * ?取本机?前用?名
     * @return
     */
    public static String getLocalHostName(){
        return getLocalHost().getHostName();
    }

    public static InetAddress getLocalHost(){
        InetAddress netAddress=null;
        try {
            netAddress=InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return netAddress;
    }

    /**
     * ?取外网IP
     * 采用??http://iframe.ip138.com/ic.asp??取外网IP
     * @return
     */
    public static String getOuterNetIP(){
        String checkURL="http://iframe.ip138.com/ic.asp";
        HttpURLConnection conn=null;
        String outerIPStr="";
        try {
            URL url=new URL(checkURL);
            conn=(HttpURLConnection) url.openConnection();
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            BufferedReader reader=new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while((outerIPStr=reader.readLine()) != null){
                if(outerIPStr.indexOf("[") > 0){
                    outerIPStr=outerIPStr.substring(outerIPStr.indexOf("[")+1,outerIPStr.indexOf("]"));
                    break;
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outerIPStr;
    }
    public static List<String> getMACAddress() {
        List<String> list = new ArrayList<>();
        try {
            Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
            while (enumeration.hasMoreElements()) {
                StringBuffer stringBuffer = new StringBuffer();
                NetworkInterface networkInterface = enumeration.nextElement();
                if (networkInterface != null) {
                    byte[] bytes = networkInterface.getHardwareAddress();
                    if (bytes != null) {
                        for (int i = 0; i < bytes.length; i++) {
                            if (i != 0) {
                                stringBuffer.append("-");
                            }
                            int tmp = bytes[i] & 0xff; // ?r???????
                            String str = Integer.toHexString(tmp);
                            if (str.length() == 1) {
                                stringBuffer.append("0" + str);
                            } else {
                                stringBuffer.append(str);
                            }
                        }
                        String mac = stringBuffer.toString().toUpperCase().replaceAll("-", ":");
                        list.add(mac);
                        System.out.println(mac);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}

