/*
 * Created on 2004-10-28
 *
 */
package dyna.framework.client.dfempdm;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;

import dyna.framework.client.DynaMOAD;
import dyna.framework.service.dos.DOSChangeable;

/**
 * @author 李渊
 *
 */
public class FileCellEditor extends AbstractCellEditor implements
        TableCellEditor {

    private JFrame parentFrame = null;
    private JPanel panel = null;
    private JTextField txtValue = null;
    private JButton btnSelect = null;

    private DOSChangeable dosRouting = null;
    
    public FileCellEditor(JFrame parent) {
        parentFrame = parent;
        
        // editor
        txtValue = new JTextField();
        txtValue.setEditable(false);
        
        btnSelect = new JButton("...");
        btnSelect.setPreferredSize(new Dimension(18, 22));
        btnSelect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String ouidRouting = (String)dosRouting.get("ouid");
                if (ouidRouting == null || ouidRouting.equals(""))
                    JOptionPane.showMessageDialog(parentFrame, "此工序尚未保存.\n请先保存工序信息, 然后再添加工序附件.");
                else
                    ShowFileDialog();
                
                stopCellEditing();
            }
        });
        
        panel = new JPanel();
	    panel.setLayout(new BorderLayout());
	    panel.add(txtValue, BorderLayout.CENTER);
	    panel.add(btnSelect, BorderLayout.EAST);
    }
    
    /* (non-Javadoc)
     * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean, int, int)
     */
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        
        dosRouting = null;
        RoutingTableModel routingModel = (RoutingTableModel)table.getModel();
        dosRouting = routingModel.getRawData(row);
        
        txtValue.setText(value != null ? value.toString() : "");
        
        return panel;
    }

    /* (non-Javadoc)
     * @see javax.swing.CellEditor#cancelCellEditing()
     */
    public void cancelCellEditing() {
		super.cancelCellEditing();
    }

    /* (non-Javadoc)
     * @see javax.swing.CellEditor#stopCellEditing()
     */
    public boolean stopCellEditing() {
		super.stopCellEditing();
		return true;
    }

    /* (non-Javadoc)
     * @see javax.swing.CellEditor#getCellEditorValue()
     */
    public Object getCellEditorValue() {
        // 重新获取附件数量
        try {
            String ouidRouting = (String)dosRouting.get("ouid");
            if (ouidRouting != null && !ouidRouting.equals("")) {
                ArrayList files = DynaMOAD.dos.listFile(ouidRouting);
                txtValue.setText(Integer.toString(files.size()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
        return txtValue.getText();
    }

    /* (non-Javadoc)
     * @see javax.swing.CellEditor#shouldSelectCell(java.util.EventObject)
     */
    public boolean shouldSelectCell(EventObject anEvent) {
        return true;
    }

    private void ShowFileDialog() {
        JDialog dialog = new JDialog(parentFrame, "附件", true);
        FileManagePanel filePanel = new FileManagePanel(parentFrame, dosRouting);
        
	    dialog.getContentPane().setLayout(new BorderLayout());
	    dialog.getContentPane().add(filePanel, BorderLayout.CENTER);

        // Center the window
	    dialog.setSize(320, 360);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dlgSize = dialog.getSize();
        if (dlgSize.height > screenSize.height) {
            dlgSize.height = screenSize.height;
        }
        if (dlgSize.width > screenSize.width) {
            dlgSize.width = screenSize.width;
        }
        dialog.setLocation((screenSize.width - dlgSize.width) / 2,
                (screenSize.height - dlgSize.height) / 2);

	    dialog.show();
	    
	    filePanel = null;
	    dialog = null;
    }
//    /**
//     * 
//     */
//    private void buildUI() {
//        try {
////            if (uiGen != null) {
////            Class uiGenClass1 = uiGen.getClass();
////            Field filePanelField1 = uiGenClass1.getDeclaredField("filePanel");
////            filePanelField1.setAccessible(true);
////            JPanel filePanel1 = (JPanel)filePanelField1.get(uiGen);
////            if (filePanel1 != null) {
////                JTabbedPane tab = (JTabbedPane)filePanel1.getParent();
//////                tab.removeAll();
////                tab.add(filePanel1);
////                
////                uiGen.show();
////                return;
////            }
////            }
//            String ouid = (String)dosRouting.get("ouid");
//            uiGen = UIBuilder.displayInstanceWindow (ouid, DynaMOAD.dos, DynaMOAD.dss);
//            uiGen.mak
//            
//            Class uiGenClass = uiGen.getClass();
//            Field filePanelField = uiGenClass.getDeclaredField("filePanel");
//            filePanelField.setAccessible(true);
//            JPanel filePanel = (JPanel)filePanelField.get(uiGen);
//            JTabbedPane tabPane = (JTabbedPane)filePanel.getParent();
//            tabPane.removeAll();
//            tabPane.add(filePanel);
////            Component [] comps = uiGen.getContentPane().getComponents();
////            for (int i = 0; i < comps.length; i++) {
////                System.out.println(comps.getClass());
////            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}