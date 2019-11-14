package mes_sfis.client.util;

/**
 * Created by Srx_Zhu on 2018/6/01 0001.
 */

import java.awt.*;
import javax.swing.*;

public class ThreadDiag extends Thread {
    private Thread currentThread = null;//???��?�N�OTestThread��??�z?�{
    private String messages = "";//���ܮت����ܫH��
    private JFrame parentFrame = null;//���ܮت������^
    private JDialog clueDiag = null;// ��?�{���b?�桨���ܮ�
    private Dimension dimensions = Toolkit.getDefaultToolkit().getScreenSize();
    private int width = dimensions.width / 4, height = 60;
    private int left = 0, top = 0;

    public ThreadDiag(Thread currentThread, String messages) {

        this.currentThread = currentThread;
        this.messages = messages;
        initDiag();//��l�ƴ��ܮ�
    }

    protected void initDiag() {
        clueDiag = new JDialog(parentFrame, "���b����A�е���", true);
        clueDiag.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        JPanel testPanel = new JPanel();
        JLabel testLabel = new JLabel(messages);
        clueDiag.getContentPane().add(testPanel);
        testPanel.add(testLabel);
        (new DisposeDiag()).start();//????���ܮ�?�{
    }

    public void run() {//?�ܴ��ܮ�
        int left = (dimensions.width - width) / 2;
        int top = (dimensions.height - height) / 2;
        clueDiag.setSize(new Dimension(width, height));
        clueDiag.setLocation(left, top);
        clueDiag.show();
    }

    class DisposeDiag extends Thread {
        public void run() {
            try {
                currentThread.join();//���ݨ�??�z?�{?��
            } catch (InterruptedException e) {
                System.out.println("Exception:" + e);
            }
            clueDiag.dispose();//??���ܮ�
        }
    }

}