/*
 * Created on 2004-10-22
 *
 */
package dyna.framework.client.dfempdm;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.tree.DefaultMutableTreeNode;

import dyna.framework.client.CheckOut;
import dyna.framework.service.DOS;
import dyna.framework.service.dos.DOSChangeable;
import javax.swing.JTextArea;
import javax.swing.JList;
/**
 * @author 李渊
 *
 */
public class CalculatorPanel extends JPanel {
    private DOS dos = dyna.framework.client.DynaMOAD.dos;
    private JFrame parentFrame = null;
    
	public static final int SEQUENCE_NO_COLUMN = 0; // 编号
	public static final int DESCRIPTION_COLUMN = 1; // 描述
	// 以下是隐藏列
	public static final int REMARK_COLUMN = 2; // 工时定额方案备注
	public static final int MHS_CATEGORY_COLUMN = 3; // 工时定额方案分类
	public static final int RAW_OUID_COLUMN = 4; // 工时定额方案对象的 ouid

	private static final String[][] columnInfo = { //说明: {"表格列名", "对应的 Routing Template DOSChangable 对象的 HashMap key"}
	        {"编号", "md$number"},
	        {"描述", "md$description"},
	        // 以下是隐藏列
	        {"remarks", "remarks"},
	        {"Category", "Category"},
	        {"ouid", "ouid"} };

	private JSplitPane jSplitPaneRoot = null;
	private JSplitPane jSplitPaneLeft = null;
	private JSplitPane jSplitPaneRight = null;
	private JScrollPane treeScrollPane = null;
	private JScrollPane tableScrollPane = null;
	private JScrollPane textScrollPane = null;
	private JScrollPane listScrollPane = null;
	private CodeTree codeTree = null;
	private JTable templTable = null;
	private JList lstFiles = null;
	private JTextArea txtRemark = null;
	private DefaultTableModel tableModel = null;   //  @jve:decl-index=0:
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
        this.setSize(711, 213);
        this.add(getJSplitPaneRoot(), java.awt.BorderLayout.CENTER);
	}
	/**
	 * This method initializes jSplitPane1	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */    
	private JSplitPane getJSplitPaneRoot() {
		if (jSplitPaneRoot == null) {
			jSplitPaneRoot = new JSplitPane();
			jSplitPaneRoot.setLeftComponent(getJSplitPaneLeft());
			jSplitPaneRoot.setRightComponent(getJSplitPaneRight());
			jSplitPaneRoot.setDividerLocation(380);
			jSplitPaneRoot.setDividerSize(3);
		}
		return jSplitPaneRoot;
	}
	/**
	 * This method initializes jSplitPane	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */    
	private JSplitPane getJSplitPaneLeft() {
		if (jSplitPaneLeft == null) {
			jSplitPaneLeft = new JSplitPane();
			jSplitPaneLeft.setLeftComponent(getTreeScrollPane());
			jSplitPaneLeft.setRightComponent(getTableScrollPane());
			jSplitPaneLeft.setDividerLocation(180);
			jSplitPaneLeft.setDividerSize(3);
		}
		return jSplitPaneLeft;
	}
	/**
	 * This method initializes jSplitPane2	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */    
	private JSplitPane getJSplitPaneRight() {
		if (jSplitPaneRight == null) {
			jSplitPaneRight = new JSplitPane();
			jSplitPaneRight.setDividerLocation(240);
			jSplitPaneRight.setDividerSize(3);
			jSplitPaneRight.setLeftComponent(getTextScrollPane());
			jSplitPaneRight.setRightComponent(getListScrollPane());
		}
		return jSplitPaneRight;
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
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getTextScrollPane() {
		if (textScrollPane == null) {
			textScrollPane = new JScrollPane();
			textScrollPane.setViewportView(getTxtRemark());
		}
		return textScrollPane;
	}
	/**
	 * This method initializes jScrollPane1	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getListScrollPane() {
		if (listScrollPane == null) {
			listScrollPane = new JScrollPane();
			listScrollPane.setViewportView(getLstFiles());
		}
		return listScrollPane;
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
			
			templTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
			
			setupColumnEditor();
			
	        // Remove column of raw data
			TableColumnModel columnModel = templTable.getColumnModel();
			
			templTable.removeColumn(columnModel.getColumn(RAW_OUID_COLUMN));
			templTable.removeColumn(columnModel.getColumn(MHS_CATEGORY_COLUMN));
			templTable.removeColumn(columnModel.getColumn(REMARK_COLUMN));
			
			// Row selection listener
			templTable.getSelectionModel().addListSelectionListener(
			        new ListSelectionListener() {
		                public void valueChanged(ListSelectionEvent e) {
		                    if (e.getValueIsAdjusting()) {
		                        return;
		                    }

		                    int row = templTable.getSelectedRow();

		                    if (row >= 0 && row < templTable.getRowCount()) {
		                        String strRemark = (String)tableModel.getValueAt(row, REMARK_COLUMN);
		                        txtRemark.setText(strRemark);
		                        
		                        String ouidMHS = (String)tableModel.getValueAt(row, RAW_OUID_COLUMN);
		                        refreshLstFiles(ouidMHS);
		                    } else {
		                        txtRemark.setText("");
		                        
		                        lstFiles.clearSelection();
		                        lstFiles.setListData(new Object[0]);
		                    }
		                }
			        });
		}
		
		return templTable;
	}
	/**
	 * This method initializes jTextArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */    
	private JTextArea getTxtRemark() {
		if (txtRemark == null) {
			txtRemark = new JTextArea();
			txtRemark.setEditable(false);
		}
		return txtRemark;
	}
	/**
	 * This method initializes jList	
	 * 	
	 * @return javax.swing.JList	
	 */    
	private JList getLstFiles() {
		if (lstFiles == null) {
			lstFiles = new JList();
			lstFiles.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
			
			lstFiles.addMouseListener(new MouseAdapter() {
			     public void mouseClicked(MouseEvent e) {
			         if (e.getClickCount() == 2) {
		                try {
                            Object value = lstFiles.getSelectedValue();
                            if (!(value instanceof DOSObjectAdapter))
                                return;

                            DOSObjectAdapter dosFile = (DOSObjectAdapter) value;

                            HashMap tmpMap = dosFile.getDosObject().getValueMap();
                            CheckOut checkedOut = new CheckOut(parentFrame, true, tmpMap);
                            File downLoadFile = new File((String) tmpMap.get("md$description"));

                            String fileSeperator = System
                                    .getProperty("file.separator") != null ? System
                                    .getProperty("file.separator")
                                    : "\\";

                            String workingDirectory = System
                                    .getProperty("user.dir")
                                    + fileSeperator
                                    + "tmp"
                                    + fileSeperator
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
                            JOptionPane.showMessageDialog(parentFrame,
                                    "处理文件时发生错误: " + ex);
                        }
			         }
			     }
			 });
		}
		return lstFiles;
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
        column.setPreferredWidth(144);
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
	
    private void refreshLstFiles(String ouidMHS) {
        Vector data = new Vector();
        
        try {
            ArrayList files = dos.listFile(ouidMHS);
            HashMap file = null;
            
            Iterator filesKey = files.iterator();
            while (filesKey.hasNext()) {
                file = (HashMap)filesKey.next();
                DOSChangeable dosFile = new DOSChangeable();
                dosFile.setValueMap(file);
                
                String fname = (String)dosFile.get("md$description");
                File f = new File((String)dosFile.get("md$description"));
                data.add(new DOSObjectAdapter(dosFile, f.getName()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        lstFiles.clearSelection();
        lstFiles.setListData(data);
    }

    private void addRowToTable(DOSChangeable dosMHS) {
        Vector rowData = new Vector(columnInfo.length);
        for (int i = 0; i < columnInfo.length; i++) {
            rowData.add(dosMHS.get(columnInfo[i][1]));
        }

        tableModel.addRow(rowData);
    }
    
    public static String getColumnDosName(int col) {
        return columnInfo[col][1];
    }
}  //  @jve:decl-index=0:visual-constraint="10,10"
