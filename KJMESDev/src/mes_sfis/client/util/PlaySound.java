package mes_sfis.client.util;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class PlaySound {
    private String soundPath; //��?�����|
    private volatile boolean run = true;  //??��?�O�_����
    private Thread mainThread;   //����?����??�{

    private AudioInputStream audioStream;//��??�J�y
    private AudioFormat audioFormat;//��?�榡
    private SourceDataLine sourceDataLine;//��?�u��

    public PlaySound(String soundPath) {
        this.soundPath = soundPath;
        prefetch();
    }

    //?�u��?
    private void prefetch() {
        try {
            //?����??�J�y
            audioStream = AudioSystem.getAudioInputStream(new File(soundPath));
            //?����?��???�H
            audioFormat = audioStream.getFormat();
            //�]?��?�H��
            DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat, AudioSystem.NOT_SPECIFIED);
            //�ϥΥ]?��?�H���Z��Info??�ط�?�u��A�R?�V?������
            sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
            sourceDataLine.open(audioFormat);
            sourceDataLine.start();
        } catch (UnsupportedAudioFileException ex) {
            ex.printStackTrace();
        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    //�R�ۨ�?:??��??���y�M?�u��
    protected void finalize() throws Throwable {
        super.finalize();
        sourceDataLine.drain();
        sourceDataLine.close();
        audioStream.close();
    }

    //����?:�q?loop???�m�O�_�`?����
    private void playMusic(boolean loop) throws InterruptedException {
        try {
            if (loop) {
                while (true) {
                    playSound();
                }
            } else {
                playSound();
                //�M��?�u��}??
                sourceDataLine.drain();
                sourceDataLine.close();
                audioStream.close();
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }

    private void playSound() {
        try {
            synchronized (this) {
                run = true;
            }
            //�q??�u��?����??�u�y�A?�e��V����;
            //?�u�y???�{�GAudioInputStream -> SourceDataLine;
            audioStream = AudioSystem.getAudioInputStream(new File(soundPath));
            int count;
            byte tempBuff[] = new byte[1024];

            while ((count = audioStream.read(tempBuff, 0, tempBuff.length)) != -1) {
                synchronized (this) {
                    while (!run)
                        wait();
                }
                sourceDataLine.write(tempBuff, 0, count);

            }

        } catch (UnsupportedAudioFileException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

    }


    //?������?
    private void stopMusic() {
        synchronized (this) {
            run = false;
            notifyAll();
        }
    }

    //??�����n��
    private void continueMusic() {
        synchronized (this) {
            run = true;
            notifyAll();
        }
    }


    //�~��?�α����k:�ͦ���?�D?�{�F
    public void start(final boolean loop) {
        mainThread = new Thread(new Runnable() {
            public void run() {
                try {
                    playMusic(loop);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        mainThread.start();
    }

    //�~��?�α����k�G?����??�{
    public void stop() {
        new Thread(new Runnable() {
            public void run() {
                stopMusic();

            }
        }).start();
    }

    //�~��?�α����k�G??��??�{
    public void continues() {
        new Thread(new Runnable() {
            public void run() {
                continueMusic();
            }
        }).start();
    }

//Test

    public static void main(String[] args) throws InterruptedException {

        PlaySound player = new PlaySound("D:\\mes_data\\sound\\nice-work.wav");//?���n������

        player.start(false);//�H?�l�H�`?���Φ�����Aplayer(false)?���`?����

        TimeUnit.SECONDS.sleep(5);

        player.stop();//?������?

        TimeUnit.SECONDS.sleep(4);

        player.continues();//???�l����?

    }
}