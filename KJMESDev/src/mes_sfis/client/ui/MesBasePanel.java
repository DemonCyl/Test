package mes_sfis.client.ui;

import base.client.ui.BasePanel;
import base.client.util.component.P_Component_Meta;
import base.vo.UI_InitVO;
import mes_sfis.client.util.SoundUtil;

import javax.swing.*;
import java.util.Hashtable;

/**
 * Created by Chris1_Liao on 2018/4/17.
 */
public class MesBasePanel extends BasePanel {

    protected String PROJECT_CODE = "COP1721";

    // logText area
    protected JTextArea logText = new JTextArea("");

    public MesBasePanel(UI_InitVO uiVO) {
        super(uiVO);
    }

    protected void showWarnMessage(String message){
        JOptionPane.showConfirmDialog(null,message,"Äµ§i",JOptionPane.PLAIN_MESSAGE);
        logText.append( message + "\n");
        SoundUtil.PlayNo();
    }

    @Override
    public void create() {

    }

    @Override
    public void save() {

    }

    @Override
    public void cancel() {

    }

    @Override
    public void refresh() {

    }

    @Override
    public void modify() {

    }

    @Override
    public void delete() {

    }

    @Override
    public void singleQuery() {

    }

    @Override
    public void multiQuery() {

    }

    @Override
    public void print() {

    }

    @Override
    public void exit() {

    }

    @Override
    public void help() {

    }

    @Override
    public void email() {

    }

    @Override
    public void export() {

    }

    @Override
    public void importData() {

    }

    @Override
    public Hashtable<String, P_Component_Meta> needValidateComponents() {
        return null;
    }

    @Override
    public void setReportOid(String s) {

    }
}
