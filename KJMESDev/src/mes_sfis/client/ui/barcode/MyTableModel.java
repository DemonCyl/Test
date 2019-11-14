package mes_sfis.client.ui.barcode;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.Vector;

/**
 * Created by Feng1_Lu on 2017/11/4.
 */

public class MyTableModel extends AbstractTableModel {
    private boolean DEBUG = true;
    //public static String[] columnNames  = new String[100];
    //public static Object[][] data = new Object[1000][1000];

    protected List<String> columnNames;
    protected List <Object> contents;

    public MyTableModel(){
        this.columnNames = new Vector<String>();
        this.contents = new Vector <Object>();
    }

    public MyTableModel(String[] columnNames) {
        this();

        if (null == columnNames) {
            return;
        }
        for (String columnName : columnNames) {
            this.columnNames.add(columnName);
        }
    }
    public MyTableModel(Object[][] datas, String[] columnNames) throws Exception {
        this(columnNames);
        refreshContents(datas);
    }

    public void refreshContents(Object[][] datas) throws Exception {
        this.contents.clear();

        if (null == datas) {
            return;
        }

        for (Object[] data : datas) {
            addRow(data);
        }
    }

    public void addRow(Object[] data) throws Exception {
        if (null == data) {
            return;
        }

        if (this.columnNames.size() != data.length) {
			System.out.println("=======this is columnNames.size()==="+this.columnNames.size());
            System.out.println("=======this is data.length=========="+data.length);
            throw new Exception("error length");
        }

        Vector <Object> content = new Vector <Object>(this.columnNames.size());

        for (int ii = 0; ii < this.columnNames.size(); ii++) {
            content.add(data[ii]);
        }
        contents.add(content);
    }
    public void removeRow(int row) {
        contents.remove(row);
    }

    public void removeRows(int row, int count) {
        for (int ii = 0; ii < count; ii++) {
            if (contents.size() > row) {
                contents.remove(row);
            }
        }
    }
    public int getColumnCount() {
        // TODO Auto-generated method stub
        return this.columnNames.size();
    }

    public int getRowCount() {
        // TODO Auto-generated method stub
        return this.contents.size();
    }
    public String getColumnName(int col){
        return columnNames.get(col);
    }
    public Object getValueAt(int row, int col) {
        // TODO Auto-generated method stub
        return ((Vector) contents.get(row)).get(col);
    }
    public Class getColumnClass(int c){
        return getValueAt(0,c).getClass();
    }
    public boolean isCellEditable(int row,int col){
        if(col>=0){
            return true;
        }else{
            return false;
        }
    }
    public void setValueAt(Object value,int row,int col){
        ((Vector) contents.get(row)).set(col, value);
        this.fireTableCellUpdated(row, col);
    }
	public void Clearcontents(){
        this.contents.clear();
    }

}
