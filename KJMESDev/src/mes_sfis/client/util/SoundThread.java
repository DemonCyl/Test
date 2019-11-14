package mes_sfis.client.util;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Line;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/**
 * Created by Chris1_Liao on 2018/4/9.
 */
public class SoundThread extends Thread {
    public static final String PColor = "pcolor.wav";
    public static final String Bin1 = "bin1.wav";
    public static final String Bin2 = "bin2.wav";
    public static final String Bin3 = "bin3.wav";
    public static final String Black = "black.wav";
    public static final String AClass = "aclass.wav";
    public static final String BClass = "bclass.wav";
    public static final String CClass = "cclass.wav";
    public static final String NG = "ng.wav";
    public static final String OK = "okay.wav";
    public static final String NO = "no-1.wav";
    public static final String NICE = "nice-work.wav";
    public static final String SOUND_SAVE_PATH = "D:\\mes_data\\sound\\";

    String fileName = "";

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public SoundThread(String soundKind, long timeDelay) throws InterruptedException {
        super(soundKind);
        Thread.sleep(timeDelay);
        this.init(soundKind);
    }

    public SoundThread(String soundKind) {
        super(soundKind);
        this.init(soundKind);
    }

    public void init(String soundKind) {
        this.setFileName(soundKind);
        File temp = new File(SOUND_SAVE_PATH);
        if (!temp.exists()) {
            System.out.println("MAKE DIR:" + SOUND_SAVE_PATH);
            temp.mkdirs();
        }
        temp = new File(SOUND_SAVE_PATH + this.getFileName());
        if (!temp.exists()) {
            System.out.println("DownLoad");
            downloadFile();
        }
    }

    public void run() {
        System.out.println("Thread: " + getName() + " running");
        Line.Info linfo = new Line.Info(Clip.class);
        Line line;
        AudioInputStream ais = null;
        try {
            line = AudioSystem.getLine(linfo);
            Clip clip = (Clip) line;
            System.out.println(this.getFileName());
            ais = AudioSystem.getAudioInputStream(new File(SOUND_SAVE_PATH + this.getFileName()));
            clip.open(ais);
            Thread.sleep(50);
            clip.start();
            Thread.sleep(50);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                ais.close();
                ais = null;
            } catch (Exception x) {
                System.out.println("Thread AudioInputStream close error: " + getName() + " " + x.getMessage());
            }
        }
    }

    private void downloadFile() {
        URL website = null;
        try {
            website = new URL(MESGlobe.PEGA_URL + "/sounds/" + this.getFileName());
            System.out.println(website);
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = new FileOutputStream(SOUND_SAVE_PATH + this.getFileName());
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
