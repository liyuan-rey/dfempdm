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
 * @author 李渊
 *  
 */
public class RoutingFilterModel extends AbstractTableModel implements
        TableModelListener {

    protected JTable table = null;
    protected RoutingTableModel model = null;
    private String ouidMatchWorkshop = "";
    private String ouidMatchOpSpec = "";

    
    public RoutingFilterModel(JTable table, RoutingTableModel model) {
        this.table = table;
        this.model = model;

        model.addTableModelListener(this);
    }

    public void setFilterInfo(String ouidWorkshop, String ouidOpSpec) {
        ouidMatchWorkshop = ouidWorkshop;
        ouidMatchOpSpec = ouidOpSpec;
    }

    public int getRowCount() {
        int count = model.getRowCount();
        int viewCount = count;

        if (needFilter()) {
	        for (int i = 0; i < count; i++) {
	            if (!isMatch(i))
	                viewCount--;
	        }
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
        return !((ouidMatchWorkshop == null || "".equals(ouidMatchWorkshop))
                && (ouidMatchOpSpec == null || "".equals(ouidMatchOpSpec)));
    }

    public boolean isMatch(int row) {
        Object value = null;

        // 以加工分厂条件过滤
        value = model.getValueAt(row, RoutingTableModel.RAW_WORKSHOP_COLUMN);

        if (!ouidMatchWorkshop.equals("") && !ouidMatchWorkshop.equals(value))
            return false;

        // 以工艺专业类型条件过滤
        value = model.getValueAt(row,
                RoutingTableModel.RAW_OPERATION_SPECIALTY_COLUMN);

        if (!ouidMatchOpSpec.equals("") && !ouidMatchOpSpec.equals(value))
            return false;

        return true;
    }

    public int convertRowIndexToModel(int viewRowIndex) {
        if (!needFilter())
            return viewRowIndex;

        int modelIndex = -1;

        int count = model.getRowCount();
        for (int i = 0; i < count; i++) {
            if (isMatch(i)) {
                viewRowIndex--;
                
                if (viewRowIndex < 0) {
                    modelIndex = i;
                    break;
                }
            }
        }

        return modelIndex;
    }

    public int convertRowIndexToView(int modelRowIndex) {
        if (!needFilter())
            return modelRowIndex;

        int viewIndex = -1;

        for (int i = 0; i <= modelRowIndex; i++) {
            if (isMatch(i))
                viewIndex++;
        }

        return viewIndex;
    }

}