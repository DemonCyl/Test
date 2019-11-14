package mes_sfis.test;

import mes_sfis.client.util.MESGlobe;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

/**
 * Created by Chris1_Liao on 2018/6/6.
 */
public class TestReadProperties {
    private static final Logger logger = LogManager.getLogger(SoundTest.class);
    public static void main(String[] args) throws Exception {

        String testKey = MESGlobe.getQuerySql("ISN_STATUS_ALL");
        logger.debug(testKey);
    }
}
