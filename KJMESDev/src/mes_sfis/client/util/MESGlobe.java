package mes_sfis.client.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.InputStream;

/**
 * Created by Chris1_Liao on 2018/4/9.
 */
public class MESGlobe {
    private static final Logger logger = LogManager.getLogger(MESGlobe.class);

    public static final String PEGA_URL = "http://pegabase.ch.casetekcorp.com:8088/PEGAMES";

    public static final String MES_DATA_PATH = "D:\\mes_data\\";

    private static final String MES_PDF_PATH = "D:\\mes_data\\pdf\\";

    public static void CREATE_MES_DIR() {
        File temp = new File(MES_DATA_PATH);
        if(!temp.exists()){
            temp.mkdir();
        }
    }

    public static void CREATE_MES_PDF_DIR() {
        File temp = new File(MES_PDF_PATH);
        if(!temp.exists()){
            temp.mkdir();
        }
    }

    public static String GET_MES_PDF_DIR() {
        CREATE_MES_PDF_DIR();
        return MES_PDF_PATH;
    }

    public static String GET_MES_DIR() {
        CREATE_MES_DIR();
        return MES_DATA_PATH;
    }

    public static String getQuerySql(String sqlName) throws DocumentException {
        logger.debug("getResourceAsStream /mes_sql.xml");
        InputStream inputStream = MESGlobe.class.getClass().getResourceAsStream("/mes_sql.xml");
        logger.debug("saxReader /mes_sql.xml:"+inputStream +"|||");
        SAXReader saxReader = new SAXReader();
        logger.debug("saxReader.read /mes_sql.xml");
        Document document = saxReader.read(inputStream);
        logger.debug("selectSingleNode /mes_sql.xml");
        Node sqlNode = document.selectSingleNode("//"+sqlName);
        logger.debug("sqlNode /mes_sql.xml");
        return sqlNode.getText();
    }

}
