package mes_sfis.client.util;

/**
 * Created by Srx_Zhu on 2018/6/01 0001.
 */

import java.awt.*;
import javax.swing.*;

public class ThreadDiag extends Thread {
    private Thread currentThread = null;//???用?就是TestThread事??理?程
    private String messages = "";//提示框的提示信息
    private JFrame parentFrame = null;//提示框的父窗体
    private JDialog clueDiag = null;// “?程正在?行”提示框
    private Dimension dimensions = Toolkit.getDefaultToolkit().getScreenSize();
    private int width = dimensions.width / 4, height = 60;
    private int left = 0, top = 0;

    public ThreadDiag(Thread currentThread, String messages) {

        this.currentThread = currentThread;
        this.messages = messages;
        initDiag();//初始化提示框
    }

    protected void initDiag() {
        clueDiag = new JDialog(parentFrame, "正在執行，請等待", true);
        clueDiag.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        JPanel testPanel = new JPanel();
        JLabel testLabel = new JLabel(messages);
        clueDiag.getContentPane().add(testPanel);
        testPanel.add(testLabel);
        (new DisposeDiag()).start();//????提示框?程
    }

    public void run() {//?示提示框
        int left = (dimensions.width - width) / 2;
        int top = (dimensions.height - height) / 2;
        clueDiag.setSize(new Dimension(width, height));
        clueDiag.setLocation(left, top);
        clueDiag.show();
    }

    class DisposeDiag extends Thread {
        public void run() {
            try {
                currentThread.join();//等待事??理?程?束
            } catch (InterruptedException e) {
                System.out.println("Exception:" + e);
            }
            clueDiag.dispose();//??提示框
        }
    }

}