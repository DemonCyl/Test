package mes_sfis.client.ui.barcode;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TextAreaHeaderRenderer extends JTextArea implements
        TableCellRenderer {
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    private final DefaultTableCellRenderer adaptee = new DefaultTableCellRenderer();
    /** map from table to map of rows to map of column heights */
    private final Map cellSizes = new HashMap();

    public TextAreaHeaderRenderer() {
        setLineWrap(true);
        setWrapStyleWord(true);
        setHighlighter(null);
    }

    public Component getTableCellRendererComponent(
            //
            JTable table, Object obj, boolean isSelected, boolean hasFocus,
            int row, int column) {
        // ���ñ�ͷ��Ⱦʱ��һЩ���Բ���
        JTableHeader header = table.getTableHeader();
        setForeground(header.getForeground());
        setBackground(header.getBackground());
        setFont(header.getFont());
        setText(obj.toString());
        setBorder(UIManager.getBorder("TableHeader.cellBorder"));
        // This line was very important to get it working with JDK1.4
        TableColumnModel columnModel = table.getColumnModel();
        int width = columnModel.getColumn(column).getWidth();
        setSize(width, 100000);
        int height_wanted = (int) getPreferredSize().getHeight();
        addSize(table, column, height_wanted);
        height_wanted = findTotalMaximumRowSize(table, column);
        if (height_wanted != header.getHeight()) {
            // ����JTextArea�Ĵ�С
            this.setSize(new Dimension(width, height_wanted));
        }
        return this;
    }

    private void addSize(JTable table, int column, int height) {
        Map cols = (Map) cellSizes.get(table);
        if (cols == null) {
            cellSizes.put(table, cols = new HashMap());
        }
        Map rowheights = (Map) cols.get(new Integer(column));
        if (rowheights == null) {
            cols.put(new Integer(column), rowheights = new HashMap());
        }
        rowheights.put(new Integer(column), new Integer(height));
    }

    /**
     * Look through all columns and get the renderer. If it is also a
     * TextAreaRenderer, we look at the maximum height in its hash table for
     * this row.
     */
    private int findTotalMaximumRowSize(JTable table, int column) {
        int maximum_height = 0;
        Enumeration columns = table.getColumnModel().getColumns();
        while (columns.hasMoreElements()) {
            TableColumn tc = (TableColumn) columns.nextElement();
            TableCellRenderer cellRenderer = tc.getHeaderRenderer();
            if (cellRenderer instanceof TextAreaHeaderRenderer) {
                TextAreaHeaderRenderer tar = (TextAreaHeaderRenderer) cellRenderer;
                maximum_height = Math.max(maximum_height, tar
                        .findMaximumRowSize(table, column));
            }
            // System.out.println("aaa");
        }
        return maximum_height;
    }

    private int findMaximumRowSize(JTable table, int column) {
        Map rows = (Map) cellSizes.get(table);
        if (rows == null)
            return 0;
        Map rowheights = (Map) rows.get(new Integer(column));
        if (rowheights == null)
            return 0;
        int maximum_height = 0;
        for (Iterator it = rowheights.entrySet().iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            int cellHeight = ((Integer) entry.getValue()).intValue();
            maximum_height = Math.max(maximum_height, cellHeight);
        }
        return maximum_height;
    }
}
