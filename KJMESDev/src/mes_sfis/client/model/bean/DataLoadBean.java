package mes_sfis.client.model.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Pino_Gao on 2018/7/11.
 */
public class DataLoadBean {
    private String barcode;
    private String timeStamp;
    private String slot;
    private Double temperature;
    private Integer status;
    private Integer rownum;
    private String mac;
    //private Date time_stamp;


    @Override
    public String toString() {
        return "DataLoadBean{" +
                "barcode='" + barcode + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                ", slot='" + slot + '\'' +
                ", temperature=" + temperature +
                ", status=" + status +
                ", rownum=" + rownum +
                ", mac='" + mac + '\'' +
                '}';
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public Integer getRownum() {
        return rownum;
    }

    public void setRownum(Integer rownum) {
        this.rownum = rownum;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {

        this.timeStamp = timeStamp;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
