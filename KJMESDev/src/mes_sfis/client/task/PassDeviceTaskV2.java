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
                statMessage = "過站失敗，登錄錯誤，請洽系統管理員，分機12050";
            }
            while (!canceled && !done && current < isnList.size()) {
                try {
                    Thread.sleep(1000); //延遲一秒
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
                            logger.debug("過站失敗:" + list.get(1));
                            isnErrorList.add(hm);
                        } else {
                            logger.debug("過站成功:" + list.get(1));
                        }
                        isnResultList.add(hm);
                        statMessage = "Completed " + current + " out of " + lengthOfTask + ".";
                    } catch (SOAPException e) {
                        e.printStackTrace();
                        logger.debug("過站時創建PassDeviceSfisSoap對象的SOAPException異常" + e.getMessage());
                        this.printException(e);
                        throw new InterruptedException("SOAPException 遠端過站連線出錯，請洽MIS");
                    } catch (IOException e) {
                        e.printStackTrace();
                        this.printException(e);
                        logger.debug("過站時創建PassDeviceSfisSoap對象的IOException異常" + e.getMessage());
                        throw new InterruptedException("IOException 遠端過站連線出錯，請洽MIS");
                    }
                    if (current >= lengthOfTask) {
                        done = true;
                        current = lengthOfTask;
                    }
                    statMessage = "Completed " + current +
                            " out of " + lengthOfTask + ". \r\n";
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    logger.debug("過站程式異常：" + e.getMessage());
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
                                logger.debug("過站失敗:" + list.get(1));
                                isnErrorList.add(hm);
                            } else {
                                logger.debug("過站成功:" + list.get(1));
                                isPassSuccess = true;
                            }
                            isnResultList.add(hm);
                            statMessage = "Completed " + current + " out of " + lengthOfTask + ".";
                        } catch (SOAPException e1) {
                            e1.printStackTrace();
                            logger.debug(reTryCount + " 過站出現異常，再次循環過站5次時創建PassDeviceSfisSoap對象的SOAPException異常:" + e1.getMessage());
                            this.printException(e1);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                            logger.debug(reTryCount + " 過站出現異常，再次循環過站5次時創建PassDeviceSfisSoap對象的IOException異常:" + e1.getMessage());
                            this.printException(e1);
                        }
                    }
                    logger.debug("REPASS PROCESS ISN:" + thisISN + " isPassSuccess:" + isPassSuccess + "reTryCount:" + reTryCount);
                }
            }
            statMessage += "過站錯誤清單:\n";
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
                        throw new InterruptedException("登入失敗");
                    }
                    if (!resultStr.startsWith("Login First!")) {
                        throw new InterruptedException("登入失敗");
                    }
                    while (!islogin && reLoginCount < 5) {
                        reLoginCount = reLoginCount + 1;
                        su = new SoapUtil();
                        loc = new LogInOutSfisSoap();
                        loc.setOp(op);
                        loc.setDevice(device);
                        loc.setStatus("1");
                        list = loc.createAndSendSOAPRequest(su);
                        System.out.println("登入失敗，重新登入第" + reLoginCount + "次");
                        System.out.println("list.get(0):" + list.get(0) + " ,list.get(1):" + list.get(1));
                        if ("1".equals(list.get(0))) {
                            islogin = true;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.debug("登入異常：" + e.getMessage());
            }
        }

        private void loginOut() {
            try {
                try {
                    su = new SoapUtil();
                } catch (SOAPException e) {
                    e.printStackTrace();
                    logger.debug("登出時創建SoapUtil對象異常：" + e.getMessage());
                    throw new InterruptedException("遠端連線出錯，請洽MIS");
                }
                try {
                    loc = new LogInOutSfisSoap();
                } catch (SOAPException e) {
                    e.printStackTrace();
                    logger.debug("登出時創建LogInOutSfisSoap對象異常：" + e.getMessage());
                    throw new InterruptedException("遠端登出連線出錯，請洽MIS");
                }
                loc.setOp(op);
                loc.setDevice(device);
                loc.setStatus("2");
                try {
                    list = loc.createAndSendSOAPRequest(su);
                } catch (SOAPException e) {
                    e.printStackTrace();
                    logger.debug("登出時?建并?送SOAP?求返回list的SOAPException異常：" + e.getMessage());
                    throw new InterruptedException("SOAPException遠端登入連線出錯，請洽MIS");
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.debug("登出時?建并?送SOAP?求返回list的IOException異常：" + e.getMessage());
                    throw new InterruptedException("IOException遠端登入連線出錯，請洽MIS");
                }
                if ("0".equals(list.get(0))) {
                    //登出失敗，暫時忽略
                    String resultStr = (String) list.get(1);
                    if (!resultStr.startsWith("Login Twice!")) {
                        throw new InterruptedException("登出失敗");
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                logger.debug("登出異常信息：" + e.getMessage());
                statMessage = e.getMessage();
            }
        }
    }
}
