package mes_sfis.client.util;

/**
 * Created by Xiaojian1_Yu on 2018/12/20.
 */
public class SoundUtilANO {
    SoundThreadANO playSoundNO = new SoundThreadANO(SoundThreadANO.NO);
    SoundThreadANO playSoundOK = new SoundThreadANO(SoundThreadANO.OK);

    public void PlayNo() {
        playSoundNO = new SoundThreadANO(SoundThreadANO.NO);
        playSoundNO.run();
        System.out.println("PlayNo���u�{�O�_���_:" + playSoundOK.isInterrupted());
    }

    public void PlayOk() {
        playSoundOK.run();
        System.out.println("PlayOk���u�{�O�_���_:" + playSoundOK.isInterrupted());
    }
}
