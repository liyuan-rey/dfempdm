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
    private String matchWorkshopSequence = "";
    
    public RoutingFilterModel(JTable table, RoutingTableModel model) {
        this.table = table;
        this.model = model;

        model.addTableModelListener(this);
    }

    public void setFilterInfo(String ouidWorkshop, String ouidOpSpec, String workshopSquence) {
        ouidMatchWorkshop = ouidWorkshop;
        ouidMatchOpSpec = ouidOpSpec;
        matchWorkshopSequence = workshopSquence;
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
        return model.getValueAt(convertViewRowToModel(row), col);
    }

    public void setValueAt(Object value, int row, int col) {
        model.setValueAt(value, convertViewRowToModel(row), col);
    }

    public boolean isCellEditable(int row, int column) {
        return model.isCellEditable(convertViewRowToModel(row), column);
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
                && (ouidMatchOpSpec == null || "".equals(ouidMatchOpSpec))
                && (matchWorkshopSequence == null || "".equals(matchWorkshopSequence)));
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

        // 以分厂工艺路线顺序条件过滤
        value = model.getValueAt(row,
                RoutingTableModel.WORKSHOP_SEQUENCE_COLUMN);

        if (!matchWorkshopSequence.equals("") && !matchWorkshopSequence.equals(value))
            return false;

        return true;
    }

    public int convertViewRowToModel(int viewRowIndex) {
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

    public int convertModelRowToView(int modelRowIndex) {
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