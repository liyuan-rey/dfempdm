/*
 * Created on 2005-1-21
 *
 */
package dyna.framework.client.dfempdm;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;

import dyna.framework.client.CheckOut;
import dyna.framework.service.DOS;
import dyna.framework.service.dos.DOSChangeable;

/**
 * @author 李渊
 *
 */
public class FileListCellEditor extends AbstractCellEditor implements
        TableCellEditor {
    private DOS dos = dyna.framework.client.DynaMOAD.dos;

    private JFrame parentFrame = null;
    private JPanel panel = null;
    private JComboBox cbxFiles = null;
    private JButton btnOpen = null;
    
    private String ouidCurrentMHS = "";
//    DOSChangeable dosManHourScheme = null;

    public FileListCellEditor(JFrame parent) {
        parentFrame = parent;

        // editor
        cbxFiles = new JComboBox();
        
        btnOpen = new JButton(new ImageIcon(getClass().getResource(
                "/icons/Open.gif")));
        btnOpen.setPreferredSize(new Dimension(22, 22));
        btnOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Object value = cbxFiles.getSelectedItem();
                    if (!(value instanceof DOSObjectAdapter))
                        return;

                    DOSObjectAdapter dosFile = (DOSObjectAdapter)value;
                    
                    HashMap tmpMap = dosFile.getDosObject().getValueMap();
                    CheckOut checkedOut = new CheckOut(parentFrame, true, tmpMap);
                    File downLoadFile = new File((String) tmpMap.get("md$description"));
                    
                    String fileSeperator = System.getProperty("file.separator") != null ?
                            System.getProperty("file.separator") : "\\";
                            
                    String workingDirectory = System.getProperty("user.dir")
                            + fileSeperator + "tmp" + fileSeperator
                            + downLoadFile.getName();
                    
                    checkedOut.setSession(null);
                    checkedOut.setPreselectedFilePath(workingDirectory);
                    checkedOut.checkOutCheckBox.setSelected(false);
                    checkedOut.downloadCheckBox.setSelected(true);
                    checkedOut.invokeCheckBox.setSelected(true);
                    checkedOut.setReadOnlyModel(true);
                    checkedOut.processButton.doClick();
                    checkedOut.setVisible(false);
                    checkedOut = null;
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(parentFrame, "处理文件时发生错误: " + ex);
                }
            }
        });
        
        panel = new JPanel();
	    panel.setLayout(new BorderLayout());
	    panel.add(btnOpen, BorderLayout.EAST);
	    panel.add(cbxFiles, BorderLayout.CENTER);
    }
    /* (non-Javadoc)
     * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean, int, int)
     */
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        // 重新获取当前选择对象的文件列表
        TableModel tableModel = (TableModel)table.getModel();
        String ouidMHS = (String)tableModel.getValueAt(row, CalculatorPanel.RAW_OUID_COLUMN);
        
        if (ouidCurrentMHS.equals("") || !ouidCurrentMHS.equals(ouidMHS))
            refreshCbxFiles(ouidMHS);
        
        util.syncComboBoxWithValue(cbxFiles, value);
        
        return panel;
    }

    /* (non-Javadoc)
     * @see javax.swing.CellEditor#getCellEditorValue()
     */
    public Object getCellEditorValue() {
        return cbxFiles.getSelectedItem().toString();
    }

    private void refreshCbxFiles(String ouid) {
        cbxFiles.removeAllItems();
        
        try {
            ArrayList files = dos.listFile(ouid);
            HashMap file = null;
            
            Iterator filesKey = files.iterator();
            while (filesKey.hasNext()) {
                file = (HashMap)filesKey.next();
                DOSChangeable dosFile = new DOSChangeable();
                dosFile.setValueMap(file);
                
                cbxFiles.addItem(new DOSObjectAdapter(dosFile, "%md$description%"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
