/*
 * Created on 2004-10-22
 *
 */
package dyna.framework.client.dfempdm;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.tree.DefaultMutableTreeNode;

import dyna.framework.service.DOS;
import dyna.framework.service.dos.DOSChangeable;
/**
 * @author ��Ԩ
 *
 */
public class RoutingTemplatePanel extends JPanel {
    private DOS dos = dyna.framework.client.DynaMOAD.dos;
    
	public static final int SEQUENCE_NO_COLUMN = 0; // �����
	public static final int WORKSHOP_COLUMN = 1; // �ӹ��ֳ�
	public static final int WORKCENTER_COLUMN = 2; // ��������
	public static final int OPERATION_SPECIALTY_COLUMN = 3; // ����רҵ����
	public static final int WORKSHOP_SEQUENCE_COLUMN = 4; // �ֳ�����·��˳��
	public static final int DESCRIPTION_COLUMN = 5; // ����
	// ������������
	public static final int ROUTING_CATEGORY_COLUMN = 6; // ģ�����
	public static final int RAW_OUID_COLUMN = 7; // Routing ����� ouid
	public static final int RAW_WORKSHOP_COLUMN = 8; // workshop codeitem �� ouid
	public static final int RAW_WORKCENTER_COLUMN = 9; // workcenter �� ouid
	public static final int RAW_OPERATION_SPECIALTY_COLUMN = 10; // Operation Specialty codeitem �� ouid

	private static final String[][] columnInfo = { //˵��: {"�������", "��Ӧ�� Routing Template DOSChangable ����� HashMap key"}
	        {"��", "Squence"},
	        {"�ӹ��ֳ�", "name@Workshop"},
	        {"��������", "name@Workcenter"},
	        {"����רҵ����", "name@Operation Specialty"},
	        {"�ֳ�����·��˳��", "Workshop Squence"},
	        {"����", "md$description"},
	        // ������������
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
			jSplitPane.setRightComponent(getTableScrollPanel());
			jSplitPane.setDividerLocation(180);
			jSplitPane.setLeftComponent(getTreeScrollPanel());
			jSplitPane.setDividerSize(3);
		}
		return jSplitPane;
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
			    DOSChangeable dosCode = dos.getCodeWithName("����ģ�����");
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
			templTable = new JTable/*DynaTable*/();
			templTable.setModel(getTableModel());
			templTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
			templTable.getTableHeader().setReorderingAllowed(false);
			templTable.setRowHeight(22);
			
	        // Remove column of raw data
			TableColumnModel columnModel = templTable.getColumnModel();
			
			templTable.removeColumn(columnModel.getColumn(RAW_OPERATION_SPECIALTY_COLUMN));
			templTable.removeColumn(columnModel.getColumn(RAW_WORKCENTER_COLUMN));
			templTable.removeColumn(columnModel.getColumn(RAW_WORKSHOP_COLUMN));
			templTable.removeColumn(columnModel.getColumn(RAW_OUID_COLUMN));
			templTable.removeColumn(columnModel.getColumn(ROUTING_CATEGORY_COLUMN));
			
			templTable.addMouseListener(new MouseAdapter() {
                // ����������, ��ѡ�еĶ����Ƶ����ؼ�����
                public void mouseReleased(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON1) { // ���
                        doCopyTemplate();
                    }
                }
			});
		}
		return templTable;
	}
    /**
     * 
     */
    public void doCopyTemplate() {
	    int[] selRows = templTable.getSelectedRows();
	    if (selRows == null || selRows.length < 1
	            || selRows[0] < 0
	            || selRows[0] >= templTable.getRowCount())
	        return;
        
	    try {
	        final String format =
	            "%" + columnInfo[SEQUENCE_NO_COLUMN][1] + "%\t" + 
	            "%" + columnInfo[WORKSHOP_COLUMN][1] + "%\t" +
	            "%" + columnInfo[WORKCENTER_COLUMN][1] + "%\t" +
	            "%" + columnInfo[OPERATION_SPECIALTY_COLUMN][1] + "%\t" +
	            "%" + columnInfo[WORKSHOP_SEQUENCE_COLUMN][1] + "%\t" +
	            "%" + columnInfo[DESCRIPTION_COLUMN][1] + "%\t" +
	            "%" + columnInfo[ROUTING_CATEGORY_COLUMN][1] + "%\n";
	        
		    // Prepare clipboard data
		    DOSArrayList datas = new DOSArrayList(selRows.length);
	
		    for (int i = 0; i < selRows.length; i++) {
		        String ouid = (String)tableModel.getValueAt(selRows[i], RAW_OUID_COLUMN);
		        DOSChangeable dosObj = dos.get(ouid);
		        datas.add(new DOSObjectAdapter(dosObj, format, DOSObjectAdapter.ROUTING_TEMPLATE));
		    }
		    
		    DOSObjectSelection selection = new DOSObjectSelection(datas);
		    
		    // Copy to local clipboard
		    RoutingEditor.clipboard.setContents(selection, null);
		    
//		    // Copy to system clipboard
//		    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
//		    clipboard.setContents(selection, null);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
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
				    return false;
				}
			};
		}
		return tableModel;
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
	        // search WorkCenter by it's workshop field value
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
//                    tableModel.cbxWorkCenter.addItem(new DOSObjectAdapter(dosWorkCenter, "%md$number% %md$description%"));
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
