package mes_sfis.client.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Efil_Ding on 2018/10/19.
 */
public class ProgressBar {
    Timer timer;
    JProgressBar jpbFileLoading;
    private  int loadingValue;

    public ProgressBar() {
        JFrame jf = new JFrame("?��???");
        /**
         * ?�ؤ@?�`?�Ҧ���?��?,���q??������V,�̤p��?0,�̤j��?100,��l��?0
         */
        jpbFileLoading = new JProgressBar();
        jpbFileLoading.setStringPainted(true);  //?�m?��?�e??�צr�Ŧ�,�q??false
        jpbFileLoading.setBorderPainted(false); //��?��?��,�q??true
        jpbFileLoading.setPreferredSize(new Dimension(100, 40)); //?�m��?�j�p
        timer = new Timer(50, new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                schedule();
            }
        });
        timer.start();
        /**
         * ?�ؤ@?���̩w�Ҧ���?��?
         */
        JProgressBar jpbFileLoadingIndeterminate = new JProgressBar();
        jpbFileLoadingIndeterminate.setIndeterminate(true); //?�m?��??���̩w�Ҧ�,�q??�̩w�Ҧ�
        jpbFileLoadingIndeterminate.setStringPainted(true);
        jpbFileLoadingIndeterminate.setString("�[����......");
        /**
         * ??��?��?���D���O��
         */
        jf.add(jpbFileLoading, BorderLayout.NORTH);
        jf.add(new JLabel(getChickenSoup(), SwingConstants.CENTER), BorderLayout.CENTER);
        jf.add(jpbFileLoadingIndeterminate, BorderLayout.SOUTH);
        jf.setSize(300, 150);
        jf.setLocationRelativeTo(null); //�~��?��
        jf.setUndecorated(true);        //�T�Φ����^��??
        jf.getRootPane().setWindowDecorationStyle(JRootPane.NONE); //���Ϋ��w�����^???��
        jf.setVisible(true);
        /**
         * ���̩w�Ҧ���?��??�z
         */
        ctrlRule();
        jpbFileLoadingIndeterminate.setIndeterminate(false); //?�m?��??�̩w�Ҧ�,�Y�`?�Ҧ�,�_?��????��?���h
        jpbFileLoadingIndeterminate.setString("�[������..");
        try {
            Thread.sleep(800);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /**
         * ??���^
         */
        jf.setVisible(false); //?�õ��^
        jf.dispose();         //?��?��,??���^
        jf = null;            //�Y���A�ϥΤF�N??
    }
    //�i�{�i�汱��
    public void schedule() {
        loadingValue = jpbFileLoading.getValue();
        if (loadingValue < 100){
            jpbFileLoading.setValue(++loadingValue);
        }else {
            timer.stop();
        }

    }
    //�פ�W�h
    public void ctrlRule(){
        while(loadingValue<100) {
//            System.out.println(loadingValue);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public String getChickenSoup(){
        ArrayList list=new ArrayList();
        list.add("�j�������\�a�o�J���O�p�n���o���V�O�A�]���O���~�ƪ����J�A�ӬO���N�w�n���X���M�ѽ�C<br>");
        list.add("���ʪ��ɭԡA�\�h�~���H�H����ӥ@�ɳ��߱�F�ۤv�A�O�̤F�A�@�ɮڥ��N�S�ݭn�L�p�C<br>");
        list.add("�p����ͬ�����A�������h�F�����M�ʤO�A�o�ر��p���쩳�A�N�O�p����i�C<br>");
        list.add("�`ť��O�H���G�ڧƱ�L/�o������ܷ����A���n�b�O�ᤤ�ˤH�C<br>���ǤH�H�H���u�A�ӹ�ڤW�A�ڪ�����ҩ��A�����O�H�a�ܡA�O�H�|�D�`����A�����C<br>�ҥH���˧O�H���w�n�b�I��C<br>");
        list.add("�C����o�L��αI��F�A�h��B�ͳ��_�Y���M�}�A�u���L�O���F�����ۤv�P�H�橹���ݨD�A�⤣�W�O����C<br>");
        list.add("���ǤH�P�n�G�ۤv���Ƥ��p�F�A�٨S�������_�ӡC<br>���p�̤w�g�����_�ӤF�A�p�̦����_�ӴN�o�ˡC<br>");
        list.add("���Ǥj��˱o���p������H�A�ä��O�h��I���A�֪��������O�j����Ӫ��A�ӬO�Lı�o�p�̪����t������n�A�]�����n�~�˱o�C<br>");
        list.add("�\�h�Ƥ��n�`�Ȱ����A�Y�Ͽ��F�A�]�����Ҵo�A�H�ʹN�O�������A�^�Y�ݨӡA����w�g�L�ҿפF�C<br>");
        list.add("����T�Q���~���D�A�M���P���H�����P���ܡA��{�X�����˪��A�סA�O���ثD�`�i�Q����O�A�Ӥ��O�갰�C<br>");
        list.add("�O��a�Bź�ơB�����P���_�A�伵�p�줵�ѡC<br>�p�������O�̿�o�ǭt����q�A�ӫD�ѥͪ����}�C<br>");
        list.add("�^�����ơA�ڵo�{�ۤv���h�F�ܦh�_�Q���F��C<br>���ڨä����L�A�]���ڪ��D�A�H��|���h����h�C<br>");
        list.add("�ҿת��k�~�l�A�u���L�O�]�����o��Ӥw�A���Z���Ƿݭ̮�誺�}�G�h�Q�A���Q�٬��k���j�H�C<br>");
        list.add("�ܦh�ɭԩp�p�G���G�ۤv����A�p�N�����D�p�٦���Ʊ��d�{�����ơC<br>");
        list.add("�O�H����m�u�O�]�������ѤѪ��w�ơA�u���L�Ѥѫo��p���F�t�~�w�ơC<br>");
        list.add("���ǨƤ��O�V�O�N�i�H���ܪ��A���Q�����H�����]�p���A�n�ݡA�]�S�����ʶ����ۤH���w�C<br>");
        list.add("�b�u�h���C�K?�A�ܦh�_�Q���F��q�ڨ���Ȩ��C<br>���ڪ��D�A�H���ٷ|���h��h�A�ҥH�ڨä����L�C<br>");
        list.add("�ڵo�{�ܦh�V�o���n���H�ݱo���ܶ}�C<br>�]�����D�L�̬O�]���ݱo�z���Ӥ��h��@�U�����\�A�٬O�]�������\�Ӥ��o���ݱo�}�C<br>");
        list.add("���ɭԧڭ̻ݭn�����O���J�����A�ӬO���Ӥڴx�I���ǤH�X�{�b�p���ͩR?�A�N�O���F�i�D�p�G�p�u�n�F�C<br>");
        list.add("�ܸr�}�p�̯�M�p�̳��w���H�b���_�A�����ڡA�P�򳣬O���w�ڪ��H�C<br>");
        list.add("�R�����k�͹B�𤣷|�Ӯt�A����ܡA�p�G���Ӥk���B��������n�A�ڤ����D�o��򯺱o�X��");


        int a = (int) (Math.random() * 20);
        return  "<html><body>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+list.get(a).toString()+"<body></html>";
    }


    public static void main(String[] args) {

        new ProgressBar();
    }
}

