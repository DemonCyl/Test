package mes_sfis.client.model.bean;

import mes_sfis.client.util.DataHandler;
import org.apache.axis2.dataretrieval.Data;

import java.util.Date;

/**
 * Created by Mark_Yang on 2018/4/5.
 */
public class PackList extends BaseBean {
    //TODO ��ISNINFO���Ҧ��r�qŪ�J
    String pickOid;//�M��D��
    String pickNo;//�M��X
    String dateCode;//����s�XYWWD
    String storageId;//ERP�J�w��ID
    int cartonQty;//�c�ƶq
    int palletQty;//�̪��ƶq
    Date logSystemDate;//�s�W�t�ήɶ�
    String logEmployee;//�s�W�Τ�
    int status;//0: ���J�w 1:�w�J�w
    String memo;//�Ƶ�
    int palletCartonNumber;//�`�̪O���c��

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
