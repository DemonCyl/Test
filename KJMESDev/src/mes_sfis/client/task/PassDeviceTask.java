package mes_sfis.client.task;

import mes_sfis.client.sfis.LogInOutSfisSoap;
import mes_sfis.client.sfis.PassDeviceSfisSoap;
import mes_sfis.client.sfis.SoapUtil;
import mes_sfis.client.task.base.KjSwingWorker;
import mes_sfis.client.task.base.LongTask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.soap.SOAPException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Chris1_Liao on 2018/6/2.
 */
public class PassDeviceTask extends LongTask {
    private static final Logger logger = LogManager.getLogger(PassDeviceTask.class);
    private String op;
    private String device;
    private List<String> isnList;

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

    public List getIsnList() {
        return isnList;
    }

    public void setIsnList(List isnList) {
        this.isnList = isnList;
    }






    public PassDeviceTask(int lengthOfTask) {
        super(lengthOfTask);
    }



    public void go() {
        final KjSwingWorker worker = new KjSwingWorker() {
            public Object construct() {
                current = 0;
                done = false;
                canceled = false;
                statMessage = null;
                return new ActualTask();
            }
        };
        worker.start();
    }

    class ActualTask {
        ActualTask() {
            //Fake a long task,
            //making a random amount of progress every second.
            while (!canceled && !done) {
                try {
                    Thread.sleep(1000); //sleep for a second

                    SoapUtil su = null;
                    LogInOutSfisSoap loc = null;
                    List list = null;

                    try {
                        su = new SoapUtil();
                    } catch (SOAPException e) {
                        e.printStackTrace();
                        throw new InterruptedException("遠端連線出錯，請洽MIS");
                    }

                    try {
                        loc = new LogInOutSfisSoap();
                    } catch (SOAPException e) {
                        e.printStackTrace();
                        throw new InterruptedException("遠端登入連線出錯，請洽MIS");
                    }
                    loc.setOp(op);
                    loc.setDevice(device);
                    loc.setStatus("1");



                    try {
                        list = loc.createAndSendSOAPRequest(su);
                    } catch (SOAPException e) {
                        e.printStackTrace();
                        throw new InterruptedException("SOAPException遠端登入連線出錯，請洽MIS");
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw new InterruptedException("IOException遠端登入連線出錯，請洽MIS");
                    }

                    if("0".equals(list.get(0))){
                        String resultStr = (String) list.get(1);
                        if(!resultStr.startsWith("Login Twice!")){
                            throw new InterruptedException("登入失敗");
                        }
                    }



                    List<Map> isnResultList = new ArrayList();
                    List<Map> isnErrorList = new ArrayList();

                    for (String isn:isnList) {
                        current ++;

                        logger.debug("Passing ISN:"+isn);
                        try {
                            PassDeviceSfisSoap pdc = new PassDeviceSfisSoap();
                            pdc.setData(isn);
                            pdc.setDevice(device);
                            list = pdc.createAndSendSOAPRequest(su);
                            HashMap hm = new HashMap();
                            hm.put("isn",isn);
                            hm.put("ret",list.get(0));
                            hm.put("msg",list.get(1));

                            if("0".equals(list.get(0))){
                                logger.debug("過站失敗:"+list.get(1));
                                isnErrorList.add(hm);
                            }else{
                                logger.debug("過站成功:"+list.get(1));
                            }
                            isnResultList.add(hm);
                            statMessage = "Completed " + current +
                                    " out of " + lengthOfTask + ".";
                        } catch (SOAPException e) {
                            e.printStackTrace();
                            throw new InterruptedException("SOAPException 遠端過站連線出錯，請洽MIS");
                        } catch (IOException e) {
                            e.printStackTrace();
                            throw new InterruptedException("IOException 遠端過站連線出錯，請洽MIS");
                        }
                    }




                    try {
                        su = new SoapUtil();
                    } catch (SOAPException e) {
                        e.printStackTrace();
                        throw new InterruptedException("遠端連線出錯，請洽MIS");
                    }

                    try {
                        loc = new LogInOutSfisSoap();
                    } catch (SOAPException e) {
                        e.printStackTrace();
                        throw new InterruptedException("遠端登入連線出錯，請洽MIS");
                    }
                    loc.setOp(op);
                    loc.setDevice(device);
                    loc.setStatus("2");



                    try {
                        list = loc.createAndSendSOAPRequest(su);
                    } catch (SOAPException e) {
                        e.printStackTrace();
                        throw new InterruptedException("SOAPException遠端登入連線出錯，請洽MIS");
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw new InterruptedException("IOException遠端登入連線出錯，請洽MIS");
                    }

                    if("0".equals(list.get(0))){
                        //登出失敗，暫時忽略
                        //String resultStr = (String) list.get(1);
                        //if(!resultStr.startsWith("Login Twice!")){
                        //    throw new InterruptedException("登入失敗");
                        //}
                    }


                    //可能不需要
                    if (current >= lengthOfTask) {
                        done = true;
                        current = lengthOfTask;
                    }

                    statMessage = "Completed " + current +
                            " out of " + lengthOfTask + ". \r\n";
                    statMessage += "過站錯誤清單:\n";
                    for (Map isnResult:isnErrorList) {
                        statMessage += "ISN:"+ isnResult.get("isn");
                        statMessage += "  "+ isnResult.get("ret");
                        statMessage += "  "+ isnResult.get("msg") + "\n";
                    }
                    isnErrorList.clear();
                    isnErrorList = null;

                } catch (InterruptedException e) {
                    System.out.println("ActualTask interrupted");
                }
            }
        }
    }
}
