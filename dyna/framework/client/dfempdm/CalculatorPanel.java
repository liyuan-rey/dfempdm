/*
 * Created on 2004-10-22
 *
 */
package dyna.framework.client.dfempdm;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.tree.DefaultMutableTreeNode;

import dyna.framework.service.DOS;
import dyna.framework.service.dos.DOSChangeable;
/**
 * @author 李渊
 *
 */
public class CalculatorPanel extends JPanel {
    private DOS dos = dyna.framework.client.DynaMOAD.dos;
    
	public static final int SEQUENCE_NO_COLUMN = 0; // 编号
	public static final int DESCRIPTION_COLUMN = 1; // 描述
	public static final int FILES_COLUMN = 2; // 文件列表
	// 以下是隐藏列
	public static final int MHS_CATEGORY_COLUMN = 3; // 工时定额方案分类
	public static final int RAW_OUID_COLUMN = 4; // 工时定额方案对象的 ouid

	private static final String[][] columnInfo = { //说明: {"表格列名", "对应的 Routing Template DOSChangable 对象的 HashMap key"}
	        {"编号", "md$number"},
	        {"描述", "md$description"},
	        {"文件", ""},
	        // 以下是隐藏列
	        {"Category", "Category"},
	        {"ouid", "ouid"} };

	private JSplitPane jSplitPane = null;
	private JScrollPane treeScrollPane = null;
	private JScrollPane tableScrollPane = null;
	private CodeTree codeTree = null;
	private JTable templTable = null;
	private DefaultTableModel tableModel = null;   //  @jve:decl-index=0:
    private FileListCellEditor flceFiles = null;
    private JFrame parentFrame = null;
	/**
	 * This method initializes 
	 * 
	 */
	public CalculatorPanel(JFrame parent) {
		super();
		
        parentFrame = parent;
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new java.awt.Dimension(120,80));
        this.setSize(384, 213);
        this.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
	}
	/**
	 * This method initializes jSplitPane	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */    
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setLeftComponent(getTreeScrollPane());
			jSplitPane.setRightComponent(getTableScrollPane());
			jSplitPane.setDividerLocation(180);
			jSplitPane.setDividerSize(3);
		}
		return jSplitPane;
	}
	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getTreeScrollPane() {
		if (treeScrollPane == null) {
			treeScrollPane = new JScrollPane();
			treeScrollPane.setViewportView(getCodeTree());
		}
		return treeScrollPane;
	}
	/**
	 * This method initializes jScrollPane1	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getTableScrollPane() {
		if (tableScrollPane == null) {
			tableScrollPane = new JScrollPane();
			tableScrollPane.setViewportView(getTemplTable());
		}
		return tableScrollPane;
	}
	/**
	 * This method initializes codeTree	
	 * 	
	 * @return javax.swing.JTree	
	 */    
	private CodeTree getCodeTree() {
		if (codeTree == null) {
		    codeTree = new CodeTree();
		    codeTree.addTreeSelectionListener(new TreeSelectionListener() {
	            
	            public void valueChanged(TreeSelectionEvent e) {
	                templTable.clearSelection();
	                tableModel.getDataVector().removeAllElements();

	                DefaultMutableTreeNode selnode = (DefaultMutableTreeNode)codeTree.getLastSelectedPathComponent();
	                if (selnode != null) {
		                DOSObjectAdapter dosItemAdapter = (DOSObjectAdapter)selnode.getUserObject();
		                if(dosItemAdapter != null)
		                    refreshTemplTable((String)dosItemAdapter.get("ouid"));
	                }

	                templTable.updateUI();
	            }
	        });
		    
		    try {
			    DOSChangeable dosCode = dos.getCodeWithName("工时");
			    codeTree.setCode(dosCode);
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}
		return codeTree;
	}
	/**
	 * This method initializes templTable	
	 * 	
	 * @return javax.swing.JTable	
	 */    
	public JTable getTemplTable() {
		if (templTable == null) {
			templTable = new JTable();
			
			templTable.setModel(getTableModel());
			templTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
			templTable.getTableHeader().setReorderingAllowed(false);
			templTable.setRowHeight(22);
			
			templTable.setColumnSelectionAllowed(true);
			templTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
			
			setupColumnEditor();
			
	        // Remove column of raw data
			TableColumnModel columnModel = templTable.getColumnModel();
			
			templTable.removeColumn(columnModel.getColumn(RAW_OUID_COLUMN));
			templTable.removeColumn(columnModel.getColumn(MHS_CATEGORY_COLUMN));
		}
		return templTable;
	}
	/**
	 * This method initializes tableModel	
	 * 	
	 * @return javax.swing.table.DefaultTableModel	
	 */    
	private DefaultTableModel getTableModel() {
		if (tableModel == null) {
		    Vector columns = new Vector(columnInfo.length);
		    for (int i = 0; i < columnInfo.length; i++)
		        columns.add(columnInfo[i][0]);
		    
			tableModel = new DefaultTableModel(columns, 0) {
				public boolean isCellEditable(int row, int col) {
				    if (col == FILES_COLUMN)
				        return true;
				    
				    return false;
				}
			};
		}
		return tableModel;
	}
    /**
     * Setup every column cell editor
     */
    private void setupColumnEditor() {
        TableColumnModel columnModel = templTable.getColumnModel();
        TableColumn column = null;

        // Editor of SEQUENCE_NO_COLUMN
        column = columnModel.getColumn(SEQUENCE_NO_COLUMN);
        column.setPreferredWidth(48);

        // Editor of DESCRIPTION_COLUMN
        column = columnModel.getColumn(DESCRIPTION_COLUMN);
        column.setPreferredWidth(412);

        // Editor of FILES_COLUMN
        flceFiles = new FileListCellEditor(parentFrame);

        column = columnModel.getColumn(FILES_COLUMN);
        column.setCellEditor(flceFiles);

        column.setPreferredWidth(200);
    }
    /**
     * @param ouidCategory
     */
    public void refreshIfNeed(String ouidCategory) {
        DOSChangeable dosTemplCodeItem = codeTree.getSelectedCodeItem();
        if (dosTemplCodeItem != null) {
            String ouidCurrent = (String)dosTemplCodeItem.get("ouid");
            if (ouidCurrent.equals(ouidCategory))
            	refreshTemplTable(ouidCategory);
        }
    }
    
    private void refreshTemplTable(String ouidCategory) {
        templTable.clearSelection();
        tableModel.getDataVector().removeAllElements();
        
        if (ouidCategory == null || ouidCategory.equals(""))
            return;
        
        try {
	        // search Man-Hour Scheme by its Category field
            String categoryFieldOuidOfMHS = "86082e70";
            HashMap searchCondition = new HashMap();
            searchCondition.put(categoryFieldOuidOfMHS,
                    ouidCategory);

            String ouidOfMHSClass = "86082e6f";
            ArrayList searchResults = null;
            
            searchResults = dos.list(ouidOfMHSClass, searchCondition);
            if (searchResults != null) {
                ArrayList tempList = null;
                int size = searchResults.size();
                
                for (int i = 0; i < size; i++) {
                    tempList = (ArrayList) searchResults.get(i);
                    String ouidTempl = (String) tempList.get(0);
                    DOSChangeable dosTempl = dos.get(ouidTempl);
                    if (dosTempl == null)
                        continue;
                    
                    addRowToTable(dosTempl);
                    tempList = null;
                }
            }
            
            templTable.updateUI();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
    private void addRowToTable(DOSChangeable dosMHS) {
        Vector rowData = new Vector(columnInfo.length);
        for (int i = 0; i < columnInfo.length; i++) {
            rowData.add(dosMHS.get(columnInfo[i][1]));
        }
        
        // 预先获取一个文件的名称
        try {
            String ouid = (String)rowData.elementAt(RAW_OUID_COLUMN);
            ArrayList files = dos.listFile(ouid);
            HashMap file = null;
            
            Iterator filesKey = files.iterator();
            while (filesKey.hasNext()) {
                file = (HashMap)filesKey.next();
                String clientPath = (String)file.get("md$description");
                rowData.set(FILES_COLUMN, clientPath);
                
                break;// only need once
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        tableModel.addRow(rowData);
    }
    
    public static String getColumnDosName(int col) {
        return columnInfo[col][1];
    }
}
