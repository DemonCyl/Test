package mes_sfis.client.util;

/**
 * Created by Xiaojian1_Yu on 2018/12/20.
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

//播放?音的?
public class SoundThreadANO extends Thread {
    private static final Logger logger = LogManager.getLogger(SoundThreadANO.class);
    public static final String OK = "okay.wav";
    public static final String NO = "no-1.wav";
    public static final String SOUND_SAVE_PATH = "D:\\mes_data\\sound\\";
    String fileName = "";

    public SoundThreadANO(String soundKind) {
        super(soundKind);
        this.init(soundKind);
    }

    public void run() {
        File soundFile = new File(SOUND_SAVE_PATH+this.getFileName());
        AudioInputStream audioInputStream = null;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(soundFile);
        } catch (Exception e1) {
            e1.printStackTrace();
            return;
        }
        AudioFormat format = audioInputStream.getFormat();
        SourceDataLine auline = null;
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
        try {
            auline = (SourceDataLine) AudioSystem.getLine(info);
            auline.open(format);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        auline.start();
        int nBytesRead = 0;
        //?是??
        byte[] abData = new byte[512];
        try {
            while (nBytesRead != -1) {
                nBytesRead = audioInputStream.read(abData, 0, abData.length);
                if (nBytesRead >= 0)
                    auline.write(abData, 0, nBytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } finally {
            auline.drain();
            auline.close();
        }
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
