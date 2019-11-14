package mes_sfis.client.util;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class PlaySound {
    private String soundPath; //音?文件路徑
    private volatile boolean run = true;  //??音?是否播放
    private Thread mainThread;   //播放音?的任??程

    private AudioInputStream audioStream;//音??入流
    private AudioFormat audioFormat;//音?格式
    private SourceDataLine sourceDataLine;//源?据行

    public PlaySound(String soundPath) {
        this.soundPath = soundPath;
        prefetch();
    }

    //?据准?
    private void prefetch() {
        try {
            //?取音??入流
            audioStream = AudioSystem.getAudioInputStream(new File(soundPath));
            //?取音?的???象
            audioFormat = audioStream.getFormat();
            //包?音?信息
            DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat, AudioSystem.NOT_SPECIFIED);
            //使用包?音?信息后的Info??建源?据行，充?混?器的源
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

    //析构函?:??音??取流和?据行
    protected void finalize() throws Throwable {
        super.finalize();
        sourceDataLine.drain();
        sourceDataLine.close();
        audioStream.close();
    }

    //播放音?:通?loop???置是否循?播放
    private void playMusic(boolean loop) throws InterruptedException {
        try {
            if (loop) {
                while (true) {
                    playSound();
                }
            } else {
                playSound();
                //清空?据行并??
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
            //通??据行?取音??据流，?送到混音器;
            //?据流???程：AudioInputStream -> SourceDataLine;
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


    //?停播放音?
    private void stopMusic() {
        synchronized (this) {
            run = false;
            notifyAll();
        }
    }

    //??播放聲音
    private void continueMusic() {
        synchronized (this) {
            run = true;
            notifyAll();
        }
    }


    //外部?用控制方法:生成音?主?程；
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

    //外部?用控制方法：?停音??程
    public void stop() {
        new Thread(new Runnable() {
            public void run() {
                stopMusic();

            }
        }).start();
    }

    //外部?用控制方法：??音??程
    public void continues() {
        new Thread(new Runnable() {
            public void run() {
                continueMusic();
            }
        }).start();
    }

//Test

    public static void main(String[] args) throws InterruptedException {

        PlaySound player = new PlaySound("D:\\mes_data\\sound\\nice-work.wav");//?建聲音播放器

        player.start(false);//以?始以循?的形式播放，player(false)?不循?播放

        TimeUnit.SECONDS.sleep(5);

        player.stop();//?停播放音?

        TimeUnit.SECONDS.sleep(4);

        player.continues();//???始播放音?

    }
}