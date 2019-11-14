
package mes_sfis.client.sfis;


public class SFISTSP {
    public static void sfisTsp(String isn, String device) {
        try {
            SFISTSPWebServiceSoapProxy ab = new SFISTSPWebServiceSoapProxy();
            ab.WTSP_LOGINOUT("TSP_NLSPAD", "N3eaM;", "TSP_NLSPAD", "N3eaM;", device, "LINK", 1);
            ab.WTSP_CHKROUTE("TSP_NLSPAD", "N3eaM;", isn, device, "1", "1", 1);
            ab.WTSP_RESULT_MASSDATA("TSP_NLSPAD", "N3eaM;", isn, null, device, "LINK", "1", 1, "1", "0", "1", "1", "1", "1", "1", "1", "1");
            ab.WTSP_LOGINOUT("TSP_NLSPAD", "N3eaM;", "TSP_NLSPAD", "N3eaM;", device, "LINK", 2);
            System.out.println("ISN為" + isn + "正在站點為" + device + "過站....");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("過站失敗!");
        }
    }
}