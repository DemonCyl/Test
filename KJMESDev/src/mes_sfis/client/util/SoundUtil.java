package mes_sfis.client.util;


/**
 * Created by Chris1_Liao on 2018/4/6.
 */
public class SoundUtil {
    public static void PColor() {
        new SoundThread(SoundThread.PColor).start();
    }

    public static void PColor(long deayTime)  {
        try {
            new SoundThread(SoundThread.PColor, deayTime).start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void Black() {
        new SoundThread(SoundThread.Black).start();
    }

    public static void Black(long deayTime)  {
        try {
            new SoundThread(SoundThread.Black, deayTime).start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    public static void AClass() {
        new SoundThread(SoundThread.AClass).start();
    }

    public static void AClass(long deayTime)  {
        try {
            new SoundThread(SoundThread.AClass, deayTime).start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void BClass() {
        new SoundThread(SoundThread.BClass).start();
    }

    public static void BClass(long deayTime)  {
        try {
            new SoundThread(SoundThread.BClass, deayTime).start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void CClass() {
        new SoundThread(SoundThread.CClass).start();
    }

    public static void CClass(long deayTime)  {
        try {
            new SoundThread(SoundThread.CClass, deayTime).start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void NG() {
        new SoundThread(SoundThread.NG).start();
    }
    public static void NG(long deayTime)  {
        try {
            new SoundThread(SoundThread.NG, deayTime).start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void PlayNo() {
        new SoundThread(SoundThread.NO).start();
    }

    public static void PlayNo(long deayTime)  {
        try {
            new SoundThread(SoundThread.NO, deayTime).start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

	public static void PlayBin(String bin) {
		if(("1").equals(bin)){
			bin = "bin1.wav";
		}else if(("2").equals(bin)){
			bin = "bin2.wav";
		}else{
			bin = "bin3.wav";
		}
        new SoundThread(bin).start();
    }

    public static void PlayOk() {
        new SoundThread(SoundThread.OK).start();
        System.out.println("--------------------------------------------------------");
        SoundThread.interrupted();
    }

    public static void PlayOk(long deayTime)  {
        try {
            new SoundThread(SoundThread.OK,deayTime).start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void PlayNice() {
        new SoundThread(SoundThread.NICE).start();
    }

    public static void PlayNice(long deayTime) {
        try {
            new SoundThread(SoundThread.NICE,deayTime).start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
