package mes_sfis.client.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;

/**
 * Created by Efil_Ding on 2018/5/28.
 */
public class ReadConfig2 {
   public static   Hashtable iniHash=null;
    public static String iniFilePath= "D://pegabase//PegaBase.ini";
    public static void  main(String arg[]){
    ReadConfig2 a=new ReadConfig2();
       a.hashINI();
       // System.out.println(iniHash.get(""));
    }
    public void hashINI()
    {
        String Section="",Key="",Value="";
        if(iniHash==null) iniHash=new Hashtable();
        if(!isEmpty()) iniHash.clear();
        try
        {
            BufferedReader bufReader = new BufferedReader(new InputStreamReader(new FileInputStream(iniFilePath),"unicode"));
            String strLine="";
            int firstLeftSquareBrackets=0,firstRightSquareBrackets=0;
            while( (strLine=bufReader.readLine())!=null)
            {

                strLine=strLine.trim();
                firstLeftSquareBrackets=strLine.indexOf("[");
                firstRightSquareBrackets=strLine.indexOf("]");
                if(firstLeftSquareBrackets>=0 && firstRightSquareBrackets>=0 && firstLeftSquareBrackets<firstRightSquareBrackets)
                {  Section=strLine.substring(firstLeftSquareBrackets+1,firstRightSquareBrackets);}
                else
                {
                    int index=0;
                    index=strLine.indexOf("=");
                    if (!(index==-1)){
                    Key=strLine.substring(0,index).trim().toString();
                    Value=strLine.substring(index+1).trim();

                    String hashKey="";
                    hashKey=Section+"."+Key;
                    iniHash.put(hashKey.toLowerCase(),Value);
                    }
                }
            }
            bufReader.close();
        }
        catch(Exception e)
        { System.out.println("讀取配置文件發生錯誤。"+e.getMessage());}
    }
    public boolean isEmpty()
    {
        if(iniHash==null) return true;
        try { return iniHash.isEmpty(); }
        catch(NullPointerException e) { return true; }
    }
    public String getHashValue(String Section,String Key)
    {
        if(isEmpty()) hashINI();
        if(isEmpty()) return "";
        String hashKey=Section+"."+Key;
        String Value=(String)iniHash.get(hashKey.toLowerCase());
        return (Value==null)?"":Value;
    }
}
