/*
 * Created on 2004-10-22
 *
 */
package dyna.framework.client.dfempdm;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.tree.DefaultMutableTreeNode;

import dyna.framework.service.DOS;
import dyna.framework.service.dos.DOSChangeable;
/**
 * @author 李渊
 *
 */
public class RoutingTemplatePanel extends JPanel {
    private DOS dos = dyna.framework.client.DynaMOAD.dos;
    
	public static final int SEQUENCE_NO_COLUMN = 0; // 工序号
	public static final int WORKSHOP_COLUMN = 1; // 加工分厂
	public static final int WORKCENTER_COLUMN = 2; // 工作中心
	public static final int OPERATION_SPECIALTY_COLUMN = 3; // 工艺专业类型
//	public static final int WORKSHOP_SEQUENCE_COLUMN = 4; // 分厂工艺路线顺序
	public static final int DESCRIPTION_COLUMN = 4; // 描述
	// 以下是隐藏列
	public static final int ROUTING_CATEGORY_COLUMN = 5; // 模板类别
	public static final int RAW_OUID_COLUMN = 6; // Routing 对象的 ouid
	public static final int RAW_WORKSHOP_COLUMN = 7; // workshop codeitem 的 ouid
	public static final int RAW_WORKCENTER_COLUMN = 8; // workcenter 的 ouid
	public static final int RAW_OPERATION_SPECIALTY_COLUMN = 9; // Operation Specialty codeitem 的 ouid

	private static final String[][] columnInfo = { //说明: {"表格列名", "对应的 Routing Template DOSChangable 对象的 HashMap key"}
	        {"序", "Squence"},
	        {"加工分厂", "name@Workshop"},
	        {"工作中心", "name@Workcenter"},
	        {"工艺专业类型", "name@Operation Specialty"},
//	        {"分厂工艺路线顺序", "Workshop Squence"},
	        {"描述", "md$description"},
	        // 以下是隐藏列
	        {"Routing Category", "Routing Category"},
	        {"ouid", "ouid"},
	        {"Workshop", "Workshop"},
	        {"Workcenter", "Workcenter"},
	        {"Operation Specialty", "Operation Specialty"} };

	private JSplitPane jSplitPane = null;  //  @jve:decl-index=0:visual-constraint="6,6"
	private CodeTree codeTree = null;
	private JTable templTable = null;
	private JScrollPane tableScrollPanel = null;
	private JScrollPane treeScrollPanel = null;
	private DefaultTableModel tableModel = null;   //  @jve:decl-index=0:
	/**
	 * This method initializes 
	 * 
	 */
	public RoutingTemplatePanel() {
		super();
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
			jSplitPane.setLeftComponent(getTreeScrollPanel());
			jSplitPane.setRightComponent(getTableScrollPanel());
			jSplitPane.setDividerLocation(180);
			jSplitPane.setDividerSize(3);
		}
		return jSplitPane;
	}
	/**
	 * This method initializes jScrollPane2	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getTreeScrollPanel() {
		if (treeScrollPanel == null) {
			treeScrollPanel = new JScrollPane();
			treeScrollPanel.setViewportView(getJTree());
		}
		return treeScrollPanel;
	}
	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getTableScrollPanel() {
		if (tableScrollPanel == null) {
			tableScrollPanel = new JScrollPane();
			tableScrollPanel.setViewportView(getTemplTable());
		}
		return tableScrollPanel;
	}
	/**
	 * This method initializes codeTree	
	 * 	
	 * @return javax.swing.JTree	
	 */    
	private CodeTree getJTree() {
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
			    DOSChangeable dosCode = dos.getCodeWithName("工艺模板分类");
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
			
	        // Remove column of raw data
			TableColumnModel columnModel = templTable.getColumnModel();
			
			templTable.removeColumn(columnModel.getColumn(RAW_OPERATION_SPECIALTY_COLUMN));
			templTable.removeColumn(columnModel.getColumn(RAW_WORKCENTER_COLUMN));
			templTable.removeColumn(columnModel.getColumn(RAW_WORKSHOP_COLUMN));
			templTable.removeColumn(columnModel.getColumn(RAW_OUID_COLUMN));
			templTable.removeColumn(columnModel.getColumn(ROUTING_CATEGORY_COLUMN));
		}
		return templTable;
	}
	/**
	 * This method initializes tableModel	
	 * 	
	 * @return javax.swing.table.DefaultTableModel	
	 */    
	public DefaultTableModel getTableModel() {
		if (tableModel == null) {
		    Vector columns = new Vector(columnInfo.length);
		    for (int i = 0; i < columnInfo.length; i++)
		        columns.add(columnInfo[i][0]);
		    
			tableModel = new DefaultTableModel(columns, 0) {
				public boolean isCellEditable(int row, int col) {
				    return false;
				}
			};
		}
		return tableModel;
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
	        // search
            String categoryFieldOuidOfWorkTemplate = "86057290";
            HashMap searchCondition = new HashMap();
            searchCondition.put(categoryFieldOuidOfWorkTemplate,
                    ouidCategory);

            String ouidOfTemplateClass = "86057287";
            ArrayList searchResults = null;
            
            searchResults = dos.list(ouidOfTemplateClass, searchCondition);
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
	
    private void addRowToTable(DOSChangeable dosTempl) {
        Vector rowData = new Vector(columnInfo.length);
        for (int i = 0; i < columnInfo.length; i++) {
            rowData.add(dosTempl.get(columnInfo[i][1]));
        }
        
        tableModel.addRow(rowData);
    }
    
    public static String getColumnDosName(int col) {
        return columnInfo[col][1];
    }

}  //  @jve:decl-index=0:visual-constraint="10,10"
