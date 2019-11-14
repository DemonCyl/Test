package mes_sfis.client.model.service;

import base.enums.DataSourceType;
import base.vo.UI_InitVO;
import mes_sfis.client.sfis.LogInOutSfisSoap;
import mes_sfis.client.util.DataHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Vector;

/**
 * Created by Pino_Gao on 2018/9/11.
 */
public class StationToService extends BaseService  {
    private static final Logger logger = LogManager.getLogger(LogInOutSfisSoap.class);
    UI_InitVO uiVO;
    DataHandler dh;

    public StationToService(UI_InitVO uiVO) {
        this.uiVO = uiVO;
        dh = new DataHandler(uiVO);
    }

    public boolean updateSn1BySn1(String sp) {
        String sql = "update isninfo set sn1 = '' where sn1 = '"+sp+"' ";
        try {
            Vector ht = dh.updateData(sql, DataSourceType._SFIS_KAIJIA_STD);
            return   true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateSn1BySpIsn(String sp, String isn) {
        String sql = "update isninfo set sn1 = '"+sp+"' where isn = '"+isn+"'";
        try {
            Vector ht = dh.updateData(sql, DataSourceType._SFIS_KAIJIA_STD);
            return   true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateSndByBgIsn(String bgno, String isn) {
        String sql = "update isninfo set snd = '"+bgno+"' where isn = '"+isn+"'";
        try {
            Vector ht = dh.updateData(sql, DataSourceType._SFIS_KAIJIA_STD);
            return   true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
