/*
 * Created on 2004-12-9
 *
 */
package dyna.framework.client.dfempdm;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.event.TableModelListener;
import javax.swing.event.TableModelEvent;

/**
 * @author ¿Ó‘®
 *  
 */
public class RoutingFilterModel extends AbstractTableModel implements
        TableModelListener {
    public static final String FILTER_ALL = "-À˘”–-";
    
    protected JTable table = null;
    protected RoutingTableModel model = null;
    private String matchOpSpec = "";

    public RoutingFilterModel(JTable table, RoutingTableModel model) {
        this.table = table;
        this.model = model;

        model.addTableModelListener(this);
    }

    public void setMatchOpSpec(String matchOpSpec) {
        this.matchOpSpec = matchOpSpec;
    }

    public int getRowCount() {
        int count = model.getRowCount();
        int viewCount = count;

        if (needFilter()) {
            Object value = null;

            for (int i = 0; i < count; i++) {
                value = model.getValueAt(i,
                        RoutingTableModel.OPERATION_SPECIALTY_COLUMN);
                if (!matchOpSpec.equals(value))
                    viewCount--;
            }

            value = null;
        }

        return viewCount;
    }

    public Object getValueAt(int row, int col) {
        return model.getValueAt(convertRowIndexToModel(row), col);
    }

    public void setValueAt(Object value, int row, int col) {
        model.setValueAt(value, convertRowIndexToModel(row), col);
    }

    public boolean isCellEditable(int row, int column) {
        return model.isCellEditable(convertRowIndexToModel(row), column);
    }

    // By default, implement TableModel by forwarding all messages
    // to the model.

    public int getColumnCount() {
        return model.getColumnCount();
    }

    public String getColumnName(int col) {
        return model.getColumnName(col);
    }

    public Class getColumnClass(int col) {
        return model.getColumnClass(col);
    }

    // Implementation of the TableModelListener interface,
    // By default forward all events to all the listeners.
    public void tableChanged(TableModelEvent e) {
        fireTableChanged(e);
    }

    // tool func
    
    public boolean needFilter() {
        return (matchOpSpec != null && !"".equals(matchOpSpec) && !FILTER_ALL.equals(matchOpSpec));
    }

    public int convertRowIndexToModel(int viewRowIndex) {
        if (!needFilter())
            return viewRowIndex;
        
        // need filter
        int modelIndex = -1;

        int count = model.getRowCount();
        Object value = null;

        for (int i = 0; i < count && modelIndex < viewRowIndex; i++) {
            value = model.getValueAt(i,
                    RoutingTableModel.OPERATION_SPECIALTY_COLUMN);
            if (matchOpSpec.equals(value))
                modelIndex = i;
        }

        value = null;

        return modelIndex;
    }

    public int convertColumnIndexToView(int modelRowIndex) {
        int viewIndex = -1;

        Object value = null;
        for (int i = 0; i <= modelRowIndex; i++) {
            value = model.getValueAt(i,
                    RoutingTableModel.OPERATION_SPECIALTY_COLUMN);
            if (!matchOpSpec.equals(value))
                viewIndex++;
        }

        value = null;

        return viewIndex;
    }

}