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
 * Created by xiaojian1_yu on 2018/11/23.
 */
public class PassDeviceTaskV2 extends LongTask {
    private static final Logger logger = LogManager.getLogger(PassDeviceTaskV2.class);
    private static final String LOGIN = "1";
    private static final String LOGOUT = "2";
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

    public PassDeviceTaskV2(int lengthOfTask) {
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
        SoapUtil su = null;
        LogInOutSfisSoap loc = null;
        List list = null;

        ActualTask() {
            List<Map> isnResultList = new ArrayList();
            List<Map> isnErrorList = new ArrayList();
            String thisISN = "";
            try {
                login();
            } catch (InterruptedException e) {
                e.printStackTrace();
                canceled = true;
                statMessage = "�L�����ѡA�n�����~�A�Ь��t�κ޲z���A����12050";
            }
            while (!canceled && !done && current < isnList.size()) {
                try {
                    Thread.sleep(1000); //����@��
                    logger.debug("current:" + current);
                    thisISN = (String) isnList.get(current);
                    logger.debug("thisISN:" + thisISN);
                    current++;
                    logger.debug("Passing ISN:" + thisISN);
                    try {
                        PassDeviceSfisSoap pdc = new PassDeviceSfisSoap();
                        pdc.setData(thisISN);
                        pdc.setDevice(device);
                        list = pdc.createAndSendSOAPRequest(su);
                        HashMap hm = new HashMap();
                        hm.put("isn", thisISN);
                        hm.put("ret", list.get(0));
                        hm.put("msg", list.get(1));
                        if ("0".equals(list.get(0))) {
                            logger.debug("�L������:" + list.get(1));
                            isnErrorList.add(hm);
                        } else {
                            logger.debug("�L�����\:" + list.get(1));
                        }
                        isnResultList.add(hm);
                        statMessage = "Completed " + current + " out of " + lengthOfTask + ".";
                    } catch (SOAPException e) {
                        e.printStackTrace();
                        logger.debug("�L���ɳЫ�PassDeviceSfisSoap��H��SOAPException���`" + e.getMessage());
                        this.printException(e);
                        throw new InterruptedException("SOAPException ���ݹL���s�u�X���A�Ь�MIS");
                    } catch (IOException e) {
                        e.printStackTrace();
                        this.printException(e);
                        logger.debug("�L���ɳЫ�PassDeviceSfisSoap��H��IOException���`" + e.getMessage());
                        throw new InterruptedException("IOException ���ݹL���s�u�X���A�Ь�MIS");
                    }
                    if (current >= lengthOfTask) {
                        done = true;
                        current = lengthOfTask;
                    }
                    statMessage = "Completed " + current +
                            " out of " + lengthOfTask + ". \r\n";
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    logger.debug("�L���{�����`�G" + e.getMessage());
                    int reTryCount = 0;
                    boolean isPassSuccess = false;
                    while (!isPassSuccess && reTryCount < 5) {
                        reTryCount = reTryCount + 1;
                        try {
                            PassDeviceSfisSoap pd = new PassDeviceSfisSoap();
                            pd.setData(thisISN);
                            pd.setDevice(device);
                            list = pd.createAndSendSOAPRequest(su);

                            HashMap hm = new HashMap();
                            hm.put("isn", thisISN);
                            hm.put("ret", list.get(0));
                            hm.put("msg", list.get(1));
                            if ("0".equals(list.get(0))) {
                                logger.debug("�L������:" + list.get(1));
                                isnErrorList.add(hm);
                            } else {
                                logger.debug("�L�����\:" + list.get(1));
                                isPassSuccess = true;
                            }
                            isnResultList.add(hm);
                            statMessage = "Completed " + current + " out of " + lengthOfTask + ".";
                        } catch (SOAPException e1) {
                            e1.printStackTrace();
                            logger.debug(reTryCount + " �L���X�{���`�A�A���`���L��5���ɳЫ�PassDeviceSfisSoap��H��SOAPException���`:" + e1.getMessage());
                            this.printException(e1);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                            logger.debug(reTryCount + " �L���X�{���`�A�A���`���L��5���ɳЫ�PassDeviceSfisSoap��H��IOException���`:" + e1.getMessage());
                            this.printException(e1);
                        }
                    }
                    logger.debug("REPASS PROCESS ISN:" + thisISN + " isPassSuccess:" + isPassSuccess + "reTryCount:" + reTryCount);
                }
            }
            statMessage += "�L�����~�M��:\n";
            for (Map isnResult : isnErrorList) {
                statMessage += "ISN:" + isnResult.get("isn");
                statMessage += "  " + isnResult.get("ret");
                statMessage += "  " + isnResult.get("msg") + "\n";
            }
            isnErrorList.clear();
            isnErrorList = null;
            loginOut();
        }

        private void printException(Exception e) {
            StackTraceElement[] ste = e.getStackTrace();
            for (StackTraceElement thisSte : ste) {
                logger.debug(thisSte);
            }

        }

        private void login() throws InterruptedException {
            boolean islogin = false;
            int reLoginCount = 0;
            try {
                su = new SoapUtil();
                loc = new LogInOutSfisSoap();
                loc.setOp(op);
                loc.setDevice(device);
                loc.setStatus("1");
                list = loc.createAndSendSOAPRequest(su);
                if ("0".equals(list.get(0))) {
                    String resultStr = (String) list.get(1);
                    if (!resultStr.startsWith("Login Twice!")) {
                        throw new InterruptedException("�n�J����");
                    }
                    if (!resultStr.startsWith("Login First!")) {
                        throw new InterruptedException("�n�J����");
                    }
                    while (!islogin && reLoginCount < 5) {
                        reLoginCount = reLoginCount + 1;
                        su = new SoapUtil();
                        loc = new LogInOutSfisSoap();
                        loc.setOp(op);
                        loc.setDevice(device);
                        loc.setStatus("1");
                        list = loc.createAndSendSOAPRequest(su);
                        System.out.println("�n�J���ѡA���s�n�J��" + reLoginCount + "��");
                        System.out.println("list.get(0):" + list.get(0) + " ,list.get(1):" + list.get(1));
                        if ("1".equals(list.get(0))) {
                            islogin = true;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.debug("�n�J���`�G" + e.getMessage());
            }
        }

        private void loginOut() {
            try {
                try {
                    su = new SoapUtil();
                } catch (SOAPException e) {
                    e.printStackTrace();
                    logger.debug("�n�X�ɳЫ�SoapUtil��H���`�G" + e.getMessage());
                    throw new InterruptedException("���ݳs�u�X���A�Ь�MIS");
                }
                try {
                    loc = new LogInOutSfisSoap();
                } catch (SOAPException e) {
                    e.printStackTrace();
                    logger.debug("�n�X�ɳЫ�LogInOutSfisSoap��H���`�G" + e.getMessage());
                    throw new InterruptedException("���ݵn�X�s�u�X���A�Ь�MIS");
                }
                loc.setOp(op);
                loc.setDevice(device);
                loc.setStatus("2");
                try {
                    list = loc.createAndSendSOAPRequest(su);
                } catch (SOAPException e) {
                    e.printStackTrace();
                    logger.debug("�n�X��?�ئ}?�eSOAP?�D��^list��SOAPException���`�G" + e.getMessage());
                    throw new InterruptedException("SOAPException���ݵn�J�s�u�X���A�Ь�MIS");
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.debug("�n�X��?�ئ}?�eSOAP?�D��^list��IOException���`�G" + e.getMessage());
                    throw new InterruptedException("IOException���ݵn�J�s�u�X���A�Ь�MIS");
                }
                if ("0".equals(list.get(0))) {
                    //�n�X���ѡA�Ȯɩ���
                    String resultStr = (String) list.get(1);
                    if (!resultStr.startsWith("Login Twice!")) {
                        throw new InterruptedException("�n�X����");
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                logger.debug("�n�X���`�H���G" + e.getMessage());
                statMessage = e.getMessage();
            }
        }
    }
}
