package mes_sfis.client.model.bean;

import mes_sfis.client.util.DataHandler;

/**
 * Created by Chris1_Liao on 2018/4/3.
 */
public class BaseBean {
    public DataHandler getDh() {
        return dh;
    }

    public void setDh(DataHandler dh) {
        this.dh = dh;
    }

    protected DataHandler dh;
}
