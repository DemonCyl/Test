package mes_sfis.client.model.bean;

import mes_sfis.client.util.DataHandler;
import org.apache.axis2.dataretrieval.Data;

import java.util.Date;

/**
 * Created by Mark_Yang on 2018/4/5.
 */
public class PackList extends BaseBean {
    //TODO 把ISNINFO的所有字段讀入
    String pickOid;//清單主鍵
    String pickNo;//清單碼
    String dateCode;//日期編碼YWWD
    String storageId;//ERP入庫單ID
    int cartonQty;//箱數量
    int palletQty;//棧版數量
    Date logSystemDate;//新增系統時間
    String logEmployee;//新增用戶
    int status;//0: 未入庫 1:已入庫
    String memo;//備註
    int palletCartonNumber;//總棧板里箱數

    public int getPalletCartonNumber() {
        return palletCartonNumber;
    }

    public void setPalletCartonNumber(int palletCartonNumber) {
        this.palletCartonNumber = palletCartonNumber;
    }

    public String getPickOid() {
        return pickOid;
    }

    public void setPickOid(String pickOid) {
        this.pickOid = pickOid;
    }

    public String getPickNo() {
        return pickNo;
    }

    public void setPickNo(String pickNo) {
        this.pickNo = pickNo;
    }

    public String getDateCode() {
        return dateCode;
    }

    public void setDateCode(String dateCode) {
        this.dateCode = dateCode;
    }

    public String getStorageId() {
        return storageId;
    }

    public void setStorageId(String storageId) {
        this.storageId = storageId;
    }

    public int getCartonQty() {
        return cartonQty;
    }

    public void setCartonQty(int cartonQty) {
        this.cartonQty = cartonQty;
    }

    public int getPalletQty() {
        return palletQty;
    }

    public void setPalletQty(int palletQty) {
        this.palletQty = palletQty;
    }

    public Date getLogSystemDate() {
        return logSystemDate;
    }

    public void setLogSystemDate(Date logSystemDate) {
        this.logSystemDate = logSystemDate;
    }

    public String getLogEmployee() {
        return logEmployee;
    }

    public void setLogEmployee(String logEmployee) {
        this.logEmployee = logEmployee;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
