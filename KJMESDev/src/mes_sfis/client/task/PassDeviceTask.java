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
                        throw new InterruptedException("���ݳs�u�X���A�Ь�MIS");
                    }

                    try {
                        loc = new LogInOutSfisSoap();
                    } catch (SOAPException e) {
                        e.printStackTrace();
                        throw new InterruptedException("���ݵn�J�s�u�X���A�Ь�MIS");
                    }
                    loc.setOp(op);
                    loc.setDevice(device);
                    loc.setStatus("1");



                    try {
                        list = loc.createAndSendSOAPRequest(su);
                    } catch (SOAPException e) {
                        e.printStackTrace();
                        throw new InterruptedException("SOAPException���ݵn�J�s�u�X���A�Ь�MIS");
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw new InterruptedException("IOException���ݵn�J�s�u�X���A�Ь�MIS");
                    }

                    if("0".equals(list.get(0))){
                        String resultStr = (String) list.get(1);
                        if(!resultStr.startsWith("Login Twice!")){
                            throw new InterruptedException("�n�J����");
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
                                logger.debug("�L������:"+list.get(1));
                                isnErrorList.add(hm);
                            }else{
                                logger.debug("�L�����\:"+list.get(1));
                            }
                            isnResultList.add(hm);
                            statMessage = "Completed " + current +
                                    " out of " + lengthOfTask + ".";
                        } catch (SOAPException e) {
                            e.printStackTrace();
                            throw new InterruptedException("SOAPException ���ݹL���s�u�X���A�Ь�MIS");
                        } catch (IOException e) {
                            e.printStackTrace();
                            throw new InterruptedException("IOException ���ݹL���s�u�X���A�Ь�MIS");
                        }
                    }




                    try {
                        su = new SoapUtil();
                    } catch (SOAPException e) {
                        e.printStackTrace();
                        throw new InterruptedException("���ݳs�u�X���A�Ь�MIS");
                    }

                    try {
                        loc = new LogInOutSfisSoap();
                    } catch (SOAPException e) {
                        e.printStackTrace();
                        throw new InterruptedException("���ݵn�J�s�u�X���A�Ь�MIS");
                    }
                    loc.setOp(op);
                    loc.setDevice(device);
                    loc.setStatus("2");



                    try {
                        list = loc.createAndSendSOAPRequest(su);
                    } catch (SOAPException e) {
                        e.printStackTrace();
                        throw new InterruptedException("SOAPException���ݵn�J�s�u�X���A�Ь�MIS");
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw new InterruptedException("IOException���ݵn�J�s�u�X���A�Ь�MIS");
                    }

                    if("0".equals(list.get(0))){
                        //�n�X���ѡA�Ȯɩ���
                        //String resultStr = (String) list.get(1);
                        //if(!resultStr.startsWith("Login Twice!")){
                        //    throw new InterruptedException("�n�J����");
                        //}
                    }


                    //�i�ण�ݭn
                    if (current >= lengthOfTask) {
                        done = true;
                        current = lengthOfTask;
                    }

                    statMessage = "Completed " + current +
                            " out of " + lengthOfTask + ". \r\n";
                    statMessage += "�L�����~�M��:\n";
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
