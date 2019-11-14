package mes_sfis.test;

import mes_sfis.client.util.SoundUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Chris1_Liao on 2018/4/6.
 */
public class SoundTest {
    private static final Logger logger = LogManager.getLogger(SoundTest.class);
    public static void main(String[] args) throws Exception {
        SoundUtil.PlayNo();
        SoundUtil.PlayNice(1000);
        logger.debug("123123123");
        /**
         *

        for (int i = 0; i < 180; i++) {
            Thread.sleep(1000);
            SoundUtil.PlayNice();
            SoundUtil.PlayOk();
            SoundUtil.PlayNo();
        }*/

    }
}
