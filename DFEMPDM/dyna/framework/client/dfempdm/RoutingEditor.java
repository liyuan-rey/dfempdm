/*
 * Created on 2004-10-19
 *
 */
package dyna.framework.client.dfempdm;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

import javax.swing.Box;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.text.JTextComponent;

import com.jgoodies.swing.ExtToolBar;
import com.jgoodies.swing.util.ToolBarButton;

import dyna.framework.service.DOS;
import dyna.framework.service.dos.DOSChangeable;
import dyna.uic.MInternalFrame;

/**
 * @author ��Ԩ
 *  
 */
public class RoutingEditor extends JFrame {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(RoutingEditor.class
            .getName());

	private static DOS dos = dyna.framework.client.DynaMOAD.dos;
    private DOSChangeable contextObject = null;
    private int searchToRow = 0; // used for increasedly search
    private int searchToCol = 0;
    private String searchText = "";
    
    public static Clipboard clipboard = new Clipboard("local");

    private JComboBox cbxWorkshop = null;
    private JComboBox cbxWorkCenter = null;
    private JComboBox cbxOperationSpecialty = null;
    private JTextField cbxWorkshopSquence = null;
    private JComboBox cbxSquenceType = null;
    private JComboBox cbxXSquence = null; // ��ʼ/��������
    private JTextField txtXTime = null;
    private JTextField txtOperatorNum = null;
    private FileCellEditor fceAttachment = null;

    private JPanel jPanelRoot = null;
    private ExtToolBar jToolBar = null;
    private JScrollPane jScrollPane = null;
    private JLabel jLabel = null;
    private JTextField txtNumber = null;
    private JLabel jLabel1 = null;
    private JTextField txtCategory = null;
    private JTextField txtMaterial = null;
    private JLabel jLabel5 = null;
    private JTextField txtDrawingNo = null;
    private JLabel jLabel2 = null;
    private JLabel jLabel4 = null;
    private JLabel jLabel3 = null;
    private JTextField txtDesc = null;
    private RoutingTableModel tableModel = null;
    private JTextField txtWorkshopRouting = null;
    private JTable routingTable = null;
    private ExtToolBar mainToolbar = null;
    private ToolBarButton btnSave = null;
    private ToolBarButton btnAdd = null;
    private ToolBarButton btnRemove = null;
    private ToolBarButton btnUp = null;
    private ToolBarButton btnDown = null;
    private MInternalFrame innerFrameRouting = null;
    private MInternalFrame innerFrameInfo = null;
	private JSplitPane jSplitPane = null;
	private JTabbedPane jTabbedPane = null;
	private RoutingTemplatePanel templPanel = null;
	private CalculatorPanel calcPanel = null;
	private MInternalFrame innerFrameTools = null;
	private JPanel jPanelContent = null;
	private ToolBarButton btnDetail = null;
	private JTextField txtSearch = null;
	private ToolBarButton btnSearch = null;
	private ToolBarButton btnFittingInfo = null;
	private TextAreaPanel textAreaPanel = null;

	private ToolBarButton btnSaveAs = null;
    private ListSelectionListener colSelListener = null;
    
	/**
     * This method initializes
     *  
     */
    public RoutingEditor() {
        super();

        logger.addHandler(new StreamHandler(System.out, new SimpleFormatter()));
        logger.setLevel(Level.ALL);

        initialize();
        setupColumnEditor();
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
        this.setBounds(0, 0, 600, 440);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(
                getClass().getResource("/icons/ChartView.gif")));
        this.setTitle("����·�߱༭��");
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
        	    int option = JOptionPane.showConfirmDialog(RoutingEditor.this, "ȷ��Ҫ�˳�����·�߱༭����?\n��ע��, ����δ�������Ϣ���ᶪʧ.", "��ʾ", 
        	            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        	    if (option != JOptionPane.YES_OPTION) {
        	        return;
        	    }
        	    
        	    RoutingEditor.this.dispose();
            }
        });
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        this.setContentPane(getJPanelRoot());
    }

    /**
     * This method initializes getJPanelRoot
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJPanelRoot() {
        if (jPanelRoot == null) {
            jPanelRoot = new JPanel();
            jPanelRoot.setLayout(new BorderLayout());
            jPanelRoot.setBackground(new java.awt.Color(223, 216, 206));
            jPanelRoot.add(getMainToolbar(), java.awt.BorderLayout.NORTH);
            jPanelRoot.add(getJPanelContent(), java.awt.BorderLayout.CENTER);
        }
        return jPanelRoot;
    }

    /**
     * This method initializes jToolBar
     * 
     * @return com.jgoodies.swing.ExtToolBar
     */
    private ExtToolBar getJToolBar() {
        if (jToolBar == null) {
            jToolBar = new ExtToolBar("");
            jToolBar.add(getBtnAdd());
            jToolBar.add(getBtnRemove());
            jToolBar.add(Box.createHorizontalStrut(5));
            jToolBar.add(getBtnUp());
            jToolBar.add(getBtnDown());
            jToolBar.add(Box.createHorizontalStrut(5));
            jToolBar.add(getBtnDetail());
            jToolBar.add(Box.createHorizontalStrut(5));
            jToolBar.add(getBtnFittingInfo());
            jToolBar.add(Box.createHorizontalStrut(10));
            jToolBar.add(getTxtSearch());
            jToolBar.add(Box.createHorizontalStrut(2));
            jToolBar.add(getBtnSearch());
        }
        return jToolBar;
    }

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanelContent() {
		if (jPanelContent == null) {
			jPanelContent = new JPanel();
			jPanelContent.setLayout(new BorderLayout());
			jPanelContent.add(getInnerFrameInfo(), java.awt.BorderLayout.NORTH);
			jPanelContent.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanelContent;
	}

    /**
     * This method initializes jScrollPane
     * 
     * @return javax.swing.JScrollPane
     */
    private JScrollPane getJScrollPane() {
        if (jScrollPane == null) {
            jScrollPane = new JScrollPane();
            jScrollPane.setViewportView(getRoutingTable());
        }
        return jScrollPane;
    }

    /**
     * This method initializes txtNumber
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getTxtNumber() {
        if (txtNumber == null) {
            txtNumber = new JTextField();
            txtNumber.setEditable(false);
        }
        return txtNumber;
    }

    /**
     * This method initializes txtCategory
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getTxtCategory() {
        if (txtCategory == null) {
            txtCategory = new JTextField();
            txtCategory.setEditable(false);
        }
        return txtCategory;
    }

    /**
     * This method initializes txtMaterial
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getTxtMaterial() {
        if (txtMaterial == null) {
            txtMaterial = new JTextField();
            txtMaterial.setEditable(false);
        }
        return txtMaterial;
    }

    /**
     * This method initializes txtDrawingNo
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getTxtDrawingNo() {
        if (txtDrawingNo == null) {
            txtDrawingNo = new JTextField();
            txtDrawingNo.setEditable(false);
        }
        return txtDrawingNo;
    }

    /**
     * This method initializes txtDesc
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getTxtDesc() {
        if (txtDesc == null) {
            txtDesc = new JTextField();
            txtDesc.setEditable(false);
        }
        return txtDesc;
    }

    /**
     * This method initializes tableModel
     * 
     * @return dyna.framework.client.dfempdm.RoutingTableModel
     */
    private RoutingTableModel getRoutingTableModel() {
        if (tableModel == null) {
            tableModel = new RoutingTableModel();
        }
        return tableModel;
    }

    /**
     * This method initializes txtWorkshopRouting
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getTxtWorkshopRouting() {
        if (txtWorkshopRouting == null) {
            txtWorkshopRouting = new JTextField();
            txtWorkshopRouting.setEditable(false);
        }
        return txtWorkshopRouting;
    }

    /**
     * This method initializes routingTable
     * 
     * @return dyna.uic.DynaTable
     */
    private JTable getRoutingTable() {
        if (routingTable == null) {
            routingTable = new JTable/*DynaTable*/() {
                //Implement table header tool tips. 
                protected JTableHeader createDefaultTableHeader() {
                    return new JTableHeader(columnModel) {
                        public String getToolTipText(MouseEvent e) {
                            java.awt.Point p = e.getPoint();
                            int index = columnModel.getColumnIndexAtX(p.x);
                            int realIndex = columnModel.getColumn(index).getModelIndex();
                            return tableModel.getColumnName(realIndex);
                        }
                    };
                }
            };
            
            routingTable.setModel(getRoutingTableModel());
            routingTable.setFocusable(true);
            
            routingTable.getSelectionModel().addListSelectionListener(getColSelListener());
            routingTable.getColumnModel().getSelectionModel()
                    .addListSelectionListener(getColSelListener());
            
            routingTable.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyChar() == KeyEvent.CHAR_UNDEFINED)
                        return;

                    int row = routingTable.getSelectedRow();
                    int col = routingTable.getSelectedColumn();
                    
                    if (routingTable.isCellEditable(row, col) == false)
                        return;
                    
                    TableCellEditor editor = routingTable.getCellEditor(
                            row, col);

                    Component component = null;
                    if (editor instanceof DefaultCellEditor) {
                        component = ((DefaultCellEditor) editor).getComponent();
                        if (!(component instanceof JTextField))
                            component = null;
                    } else if (editor instanceof TextAreaPanel.myCellEditor) {
                        component = (((TextAreaPanel.myCellEditor) editor)).editor;
                    }
                    
                    if (component != null) {
                        // �� TextField �ĵ�Ԫ����Ϊ�ɱ༭״̬
                        routingTable.editCellAt(row, col);
                        component.requestFocus();
                        
                        // �� delete ��ֱ��ɾ������
                        //if (e.getKeyCode() == KeyEvent.VK_DELETE)
                            ((JTextComponent)component).setText("");
                    }
                }
            });
            
            routingTable.addMouseListener(new MouseAdapter() {
                // ����������, �Ҽ�ճ������, ���Խ�����͹���ģ�����ճ��Ϊ�¹������
                public void mouseReleased(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON1) { // ���
                        doCopyRouting();
                    } else if (e.getButton() == MouseEvent.BUTTON3) { // �Ҽ�
                        // ����"��"���ϸ�������, ����ֻ���Ƶ��еĵ�Ԫ��
                        Point pt = e.getPoint();
                        int row = routingTable.rowAtPoint(pt);
                        int col = routingTable.columnAtPoint(pt);
                        
                        if (row != -1 && col != -1
                                && col != RoutingTableModel.SEQUENCE_NO_COLUMN) {
                            
                            if (tableModel.isCellEditable(row, col))
                                doPasteToCell(row, col);
                        } else {
                    	    if (hasPermission("add"))
                    	        doPasteToRouting();
                        }
                    }
                }
            });
            
            routingTable.setRowHeight(22);
            routingTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            routingTable.getTableHeader().setReorderingAllowed(false); // ��ͷ�����϶�
        }
        return routingTable;
    }

    private ListSelectionListener getColSelListener() {
        if (colSelListener == null) {
            colSelListener = new ListSelectionListener() {
                // �н����ƶ�ʱ, ���»�ý���ĵ�Ԫ���Ϊ�ɱ༭״̬��������ش���
                public void valueChanged(ListSelectionEvent e) {
                    if (e.getValueIsAdjusting()) {
                        return;
                    }
                    
                    int row = routingTable.getSelectedRow();
                    int col = routingTable.getSelectedColumn();

                    if (row >= 0 && row < routingTable.getRowCount()
                            && col >= 0 && col < routingTable.getColumnCount()) {
                        // ���ƶ����ı��༭��״̬
                        if (col == RoutingTableModel.DESCRIPTION_COLUMN) {

                            textAreaPanel.setText((String) routingTable
                                    .getValueAt(row, col));

                            if (tableModel.isCellEditable(row, col)) {
                                textAreaPanel.setEditable(true);
                            } else {
                                textAreaPanel.setEditable(false);
                            }
                            
                            jTabbedPane.setSelectedComponent(textAreaPanel);
                        } else {
                            textAreaPanel.setEditable(false);
                            textAreaPanel.setText("");
                        }

                        // ���ƹ�ʱ����༭��״̬
                        if (col == RoutingTableModel.PREPARATION_TIME_COLUMN
                                || col == RoutingTableModel.OPERATING_TIME_COLUMN) {
                            jTabbedPane.setSelectedComponent(calcPanel);
                        } else if (col != RoutingTableModel.DESCRIPTION_COLUMN) {
                            // ���ƹ���ģ�����״̬
                            jTabbedPane.setSelectedComponent(templPanel);
                        }
                    }
                }
            };
        }
        return colSelListener;
    }

    private void doCopyRouting() {
	    int[] selRows = routingTable.getSelectedRows();
	    if (selRows == null || selRows.length < 1
	            || selRows[0] < 0
	            || selRows[0] >= routingTable.getRowCount()) {
	        return;
	    }
	    
	    try {
	        final String format =
	            "%" + RoutingTableModel.getColumnDosName(RoutingTableModel.SEQUENCE_NO_COLUMN) + "%\t" + 
	            "%" + RoutingTableModel.getColumnDosName(RoutingTableModel.WORKSHOP_COLUMN) + "%\t" +
	            "%" + RoutingTableModel.getColumnDosName(RoutingTableModel.WORKCENTER_COLUMN) + "%\t" +
                "%" + RoutingTableModel.getColumnDosName(RoutingTableModel.WORKSHOP_SEQUENCE_COLUMN) + "%\t" +
	            "%" + RoutingTableModel.getColumnDosName(RoutingTableModel.OPERATION_SPECIALTY_COLUMN) + "%\t" +
	            "%" + RoutingTableModel.getColumnDosName(RoutingTableModel.DESCRIPTION_COLUMN) + "%\n";
	        
		    // Prepare clipboard data
		    DOSArrayList datas = new DOSArrayList(selRows.length);
	
		    for (int i = 0; i < selRows.length; i++) {
		        DOSChangeable dosObj = tableModel.getRawData(selRows[i]);
		        datas.add(new DOSObjectAdapter(dosObj, format, DOSObjectAdapter.ROUTING));
		    }
		    
		    DOSObjectSelection selection = new DOSObjectSelection(datas);
		    
		    // Copy to local clipboard
		    RoutingEditor.clipboard.setContents(selection, null);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
    }

    private void doPasteToCell(int row, int col) {
        Transferable contents = clipboard.getContents(null);
        if (contents == null) {
            return;
        }
        
        try {
            DataFlavor flavor = new DataFlavor("application/x-java-jvm-local-objectref;class=" + DOSArrayList.class.getName());
            
            if (contents.isDataFlavorSupported(flavor) == false) {
                return;
            }
            
            DOSArrayList dosObjects = (DOSArrayList)contents.getTransferData(flavor);
            if (dosObjects.size() < 1)
                return;
            
    	    DOSObjectAdapter dosAdapter = (DOSObjectAdapter)dosObjects.get(0);
    	    DOSChangeable dosTemp = dosAdapter.getDosObject();
    	    
    	    switch (col) {
    	    case RoutingTableModel.WORKSHOP_COLUMN: // �ӹ��ֳ��͹����������, 
    	    case RoutingTableModel.WORKCENTER_COLUMN: { // ��Ҫͬʱ����ֵ
	    	    int [][] updateIndex = {
    	    	        {RoutingTableModel.WORKSHOP_COLUMN, RoutingTemplatePanel.WORKSHOP_COLUMN},
	    	            {RoutingTableModel.RAW_WORKSHOP_COLUMN, RoutingTemplatePanel.RAW_WORKSHOP_COLUMN},
	    	            {RoutingTableModel.WORKCENTER_COLUMN, RoutingTemplatePanel.WORKCENTER_COLUMN},
	    	            {RoutingTableModel.RAW_WORKCENTER_COLUMN, RoutingTemplatePanel.RAW_WORKCENTER_COLUMN} };

    	        DOSChangeable dosObject = tableModel.getRawData(row);
    	    	String tmpValue = null;

    	    	int dosObjectType = dosAdapter.getDosType();
    	    	if (dosObjectType == DOSObjectAdapter.ROUTING) {
    	    	    for (int i = 0; i < updateIndex.length; i++) {
        	    	    tmpValue = (String)dosTemp.get(RoutingTableModel.getColumnDosName(updateIndex[i][0]));
        	    	    dosObject.put(RoutingTableModel.getColumnDosName(updateIndex[i][0]), tmpValue);
    	    	    }
    	    	} else if (dosObjectType == DOSObjectAdapter.ROUTING_TEMPLATE) {
    	    	    for (int i = 0; i < updateIndex.length; i++) {
        	    	    tmpValue = (String)dosTemp.get(RoutingTemplatePanel.getColumnDosName(updateIndex[i][1]));
        	    	    dosObject.put(RoutingTemplatePanel.getColumnDosName(updateIndex[i][1]), tmpValue);
    	    	    }
    	    	}
    	    	
    	        tableModel.fireTableRowsUpdated(row, row);
    	        break;
    	    }
    	    case RoutingTableModel.OPERATION_SPECIALTY_COLUMN:
    		    // �����Ҫд "����רҵ����" ��, ����Ҫд���ֵ�Ƿ��봫��� authorType ��ͻ,
    	        // ���Ҳ��ܵ��� setValueAt ����, ��Ҫͨ��model.getRawData �Լ�д��
    	        // OPERATION_SPECIALTY_COLUMN �� RAW_OPERATION_SPECIALTY_COLUMN.
    	        // Ŀǰ������ճ��
//				String ouidOpSpec = (String)dosTemp.get(
//					RoutingTableModel.getColumnDosName(
//		        		RoutingTableModel.RAW_OPERATION_SPECIALTY_COLUMN));
//		        if (tableModel.authorTypes.contains(ouidOpSpec) == false)
//		            return;
    	        break;
    	    
    	    case RoutingTableModel.SEQUENCE_TYPE_COLUMN: {
    	        // ��Ӧ��ʹ�� setValueAt ����, ��Ϊ�Ӵ���� value ֵ��� CodeItem ��
                // �󲢲�����, ���� CodeItem �� Name �а����ո�ʱ, ���޷���ȷ�ָ��ַ���
    	        DOSChangeable dosObject = tableModel.getRawData(row);
    	    	String tmpValue = null;
    	    	int dosObjectType = dosAdapter.getDosType();
    	    	if (dosObjectType == DOSObjectAdapter.ROUTING) {
			    	tmpValue = (String)dosTemp.get(RoutingTableModel.getColumnDosName(RoutingTableModel.SEQUENCE_TYPE_COLUMN));
		    	    dosObject.put(RoutingTableModel.getColumnDosName(RoutingTableModel.SEQUENCE_TYPE_COLUMN), tmpValue);
	
			    	tmpValue = (String)dosTemp.get(RoutingTableModel.getColumnDosName(RoutingTableModel.RAW_SEQUENCE_TYPE_COLUMN));
		    	    dosObject.put(RoutingTableModel.getColumnDosName(RoutingTableModel.RAW_SEQUENCE_TYPE_COLUMN), tmpValue);
    	    	} else if (dosObjectType == DOSObjectAdapter.ROUTING_TEMPLATE) {
    	    	    // Do NOTHING, since routing template has no SEQUENCE_TYPE_COLUMN
    	    	}
    	    	
    	    	tableModel.fireTableRowsUpdated(row, row);
	    	    break;
    	    }
    	    default: { // ����û��Լ�����������°취
        	    Object newValue = null;
	    	    int dosObjectType = dosAdapter.getDosType();
	            if (dosObjectType == DOSObjectAdapter.ROUTING) {
	                newValue = dosTemp.get(RoutingTableModel.getColumnDosName(col));
	            	routingTable.setValueAt(newValue, row, col);
	            } else if (dosObjectType == DOSObjectAdapter.ROUTING_TEMPLATE) {
	                // �����ֶ�ӳ���, ����ܹ�ӳ�������ճ��
	                int mapIndex = Integer.MIN_VALUE;
	            	for (int i = 0; i < RoutingTableModel.mapping.length; i++) {
	            	    if (col == RoutingTableModel.mapping[i][1]) {
	            	        mapIndex = RoutingTableModel.mapping[i][0];
	            	        break;
	            	    }
	            	}
	            	
	            	if (mapIndex == Integer.MIN_VALUE)
	            	    return;
	            	
		            newValue = dosTemp.get(RoutingTemplatePanel.getColumnDosName(mapIndex));
			        routingTable.setValueAt(newValue, row, col);
	            }
	
	            newValue = null;
	            dosTemp = null;
	            dosAdapter = null;
    	    }
    	    }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ������͹���ģ�����ճ��Ϊ�¹���
     */
    private void doPasteToRouting() {
        Transferable contents = clipboard.getContents(null);
        if (contents == null)
            return;
        
        try {
            DataFlavor flavor = new DataFlavor("application/x-java-jvm-local-objectref;class=" + DOSArrayList.class.getName());
            
            if (contents.isDataFlavorSupported(flavor) == false)
                return;
            
//            // stringFlavor is for debug
//            if (contents.isDataFlavorSupported(DataFlavor.stringFlavor))
//                System.out.println(contents.getTransferData(DataFlavor.stringFlavor));

            // ȷ�������, �߼�Ϊ��ǰѡ�еĵ�һ�е�λ��, ������������; ��û��ѡ�е���, ����ĩβ
            int insertTo = routingTable.getRowCount();
            
    	    int[] selRows = routingTable.getSelectedRows();
    	    if (selRows != null && selRows.length > 0
    	            && selRows[0] >= 0
    	            && selRows[0] < routingTable.getRowCount())
    	        insertTo = selRows[0];

    	    // �����½ڵ�
            DOSArrayList dosObjects = (DOSArrayList)contents.getTransferData(flavor);
    	    DOSObjectAdapter dosAdapter = null;
    	    DOSChangeable dosTemp = null;
    	    DOSChangeable dosNew = null;
    	    
            int size = dosObjects.size();
            for (int i = 0; i < size; i++) {
                dosAdapter = (DOSObjectAdapter)dosObjects.get(i);
                dosTemp = dosAdapter.getDosObject();
                
                switch (dosAdapter.getDosType()) {
                case DOSObjectAdapter.ROUTING:
                    dosNew = RoutingTableModel.cloneRouting(dosTemp);
                    break;
                
                case DOSObjectAdapter.ROUTING_TEMPLATE:
                    dosNew = RoutingTableModel.routingFromTemplate(dosTemp);
                    break;
                }

                tableModel.addRow(insertTo++, dosNew, true);

                dosNew = null;
                dosTemp = null;
                dosAdapter = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    /**
     * This method initializes mainToolbar
     * 
     * @return com.jgoodies.swing.ExtToolBar
     */
    private ExtToolBar getMainToolbar() {
        if (mainToolbar == null) {
            mainToolbar = new ExtToolBar("");
            mainToolbar.add(getBtnSave());
            mainToolbar.add(getBtnSaveAs());
        }
        return mainToolbar;
    }

    /**
     * This method initializes btnSave
     * 
     * @return com.jgoodies.swing.util.ToolBarButton
     */
    private ToolBarButton getBtnSave() {
        if (btnSave == null) {
            btnSave = new ToolBarButton(new ImageIcon(getClass().getResource("/icons/Save.gif")));
            btnSave.setPreferredSize(new java.awt.Dimension(24,24));
            btnSave.setMaximumSize(new java.awt.Dimension(24,24));
            btnSave.addActionListener(new java.awt.event.ActionListener() { 
            	public void actionPerformed(java.awt.event.ActionEvent e) {
            	    if (!hasPermission("save")) {
            	        JOptionPane.showMessageDialog(RoutingEditor.this, "û��Ȩ�޽��д������.");
            	        return;
            	    }
            	    
            	    TableCellEditor editor = routingTable.getCellEditor();
            	    if (editor != null)
            	        editor.stopCellEditing();

            	    save();
            	}
            });
        }
        return btnSave;
    }

	/**
	 * This method initializes btnSaveAs	
	 * 	
	 * @return com.jgoodies.swing.util.ToolBarButton	
	 */    
	private ToolBarButton getBtnSaveAs() {
		if (btnSaveAs == null) {
			btnSaveAs = new ToolBarButton(new ImageIcon(getClass().getResource("/icons/SaveAs16.gif")));
			btnSaveAs.setPreferredSize(new java.awt.Dimension(24,24));
			btnSaveAs.setMaximumSize(new java.awt.Dimension(24,24));
			btnSaveAs.addActionListener(new java.awt.event.ActionListener() { 
            	public void actionPerformed(java.awt.event.ActionEvent e) {
            	    if (!hasPermission("save template")) {
            	        JOptionPane.showMessageDialog(RoutingEditor.this, "û��Ȩ�޽��д������.");
            	        return;
            	    }
            	    
            	    TableCellEditor editor = routingTable.getCellEditor();
            	    if (editor != null)
            	        editor.stopCellEditing();

            	    int[] selRows = routingTable.getSelectedRows();
            	    if (selRows == null || selRows.length < 1
            	            || selRows[0] < 0
            	            || selRows[0] >= routingTable.getRowCount()) {
            	        JOptionPane.showMessageDialog(RoutingEditor.this, "����ѡ����Ҫ����Ϊģ��Ĺ���.");
            	        return;
            	    }
            	    
                    SaveRoutingToTemplate(selRows);
            	}
            });
		}
		return btnSaveAs;
	}

    private void SaveRoutingToTemplate(int [] rows) {
	    try {
	        CodeTree codeTree = new CodeTree();
            codeTree.setCode((DOSChangeable)dos.getCodeWithName("����ģ�����"));
	    
            CodeSelectDialog dlg = new CodeSelectDialog(this, "ѡ��", true, codeTree);

            util.centerWindow(null, this);
            dlg.show();

            if (dlg.getChoice() != JOptionPane.OK_OPTION)
                return;

            DOSChangeable dosTemplCodeItem = codeTree.getSelectedCodeItem();
            if (dosTemplCodeItem == null)
                return;
            
            String ouidCategory = (String)dosTemplCodeItem.get("ouid");
            DOSChangeable dosTempl = null;
            DOSChangeable dosRouting = null;
            
            for (int i = 0; i < rows.length; i++) {
                dosRouting = tableModel.getRawData(rows[i]);
                dosTempl = RoutingTableModel.routingToTemplate(dosRouting);
                dosTempl.put(
                        RoutingTemplatePanel.getColumnDosName(
                                RoutingTemplatePanel.ROUTING_CATEGORY_COLUMN),
                        ouidCategory);

                dos.add(dosTempl);
                
                dosTempl = null;
                dosRouting = null;
            }
            
            templPanel.refreshIfNeed(ouidCategory);
            
            JOptionPane.showMessageDialog(this, "ģ�屣��ɹ�.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method initializes btnAdd
     * 
     * @return com.jgoodies.swing.util.ToolBarButton
     */
    private ToolBarButton getBtnAdd() {
        if (btnAdd == null) {
            btnAdd = new ToolBarButton(new ImageIcon(getClass().getResource("/icons/add_att.gif")));
            btnAdd.setToolTipText("����");
            btnAdd.setMaximumSize(new java.awt.Dimension(24,24));
            btnAdd.setPreferredSize(new java.awt.Dimension(24,24));
            btnAdd.addActionListener(new java.awt.event.ActionListener() { 
            	public void actionPerformed(java.awt.event.ActionEvent e) {    
            	    if (!hasPermission("add")) {
            	        JOptionPane.showMessageDialog(RoutingEditor.this, "û��Ȩ�޽��д������.");
            	        return;
            	    }

            	    TableCellEditor editor = routingTable.getCellEditor();
            	    if (editor != null)
            	        editor.stopCellEditing();

            	    // ����е�ǰѡ���еĻ�, �Ե�ǰ��Ϊģ���¼��ڵ�ǰ����, ����ӵ�ĩβ
                    int insertTo = routingTable.getRowCount();
                    DOSChangeable dosModel = null;
                    
            	    int[] selRows = routingTable.getSelectedRows();
            	    if (selRows != null && selRows.length > 0
            	            && selRows[0] >= 0
            	            && selRows[0] < routingTable.getRowCount()) {
            	        dosModel = tableModel.getRawData(selRows[0]);
            	        insertTo = selRows[0] + 1;
            	    }
            	    
            	    if (dosModel != null) {
            	        DOSChangeable dosNew = RoutingTableModel.cloneRouting(dosModel);
            	        tableModel.addRow(insertTo, dosNew, false);
            	    } else {
            	        tableModel.addNew();
            	        insertTo = routingTable.getRowCount() - 1;
            	    }

            	    routingTable.changeSelection(insertTo, RoutingTableModel.WORKSHOP_COLUMN, false, false);
            	}
            });
        }
        return btnAdd;
    }

    /**
     * This method initializes btnRemove
     * 
     * @return com.jgoodies.swing.util.ToolBarButton
     */
    private ToolBarButton getBtnRemove() {
        if (btnRemove == null) {
            btnRemove = new ToolBarButton(new ImageIcon(getClass().getResource("/icons/remove_att.gif")));
            btnRemove.setToolTipText("ɾ��");
            btnRemove.setPreferredSize(new java.awt.Dimension(24,24));
            btnRemove.setMaximumSize(new java.awt.Dimension(24,24));
            btnRemove.addActionListener(new java.awt.event.ActionListener() { 
            	public void actionPerformed(java.awt.event.ActionEvent e) {    
            	    if (!hasPermission("remove")) {
            	        JOptionPane.showMessageDialog(RoutingEditor.this, "û��Ȩ�޽��д������.");
            	        return;
            	    }

            	    int[] selRows = routingTable.getSelectedRows();
            	    if (selRows == null || selRows.length < 1
            	            || selRows[0] < 0
            	            || selRows[0] >= routingTable.getRowCount())
            	        return;
            	    
            	    int option = JOptionPane.showConfirmDialog(RoutingEditor.this, "ȷ��Ҫɾ����ѡ��Ĺ�����?", "��ʾ", 
            	            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            	    if (option != JOptionPane.YES_OPTION)
            	        return;
            	    
            	    
            	    TableCellEditor editor = routingTable.getCellEditor();
            	    if (editor != null)
            	        editor.stopCellEditing();
            	    
            	    tableModel.removeRows(selRows);
            	    
            	    int selIndex = selRows[0];
            	    if (selIndex >= tableModel.getRowCount())
            	        selIndex = tableModel.getRowCount() - 1;
            	    
            	    if (selIndex >= 0) {
            	        routingTable.changeSelection(selIndex, RoutingTableModel.WORKSHOP_COLUMN, false, false);
            	    } else {
            	        routingTable.clearSelection();
            	    }
            	}
            });
        }
        return btnRemove;
    }

    /**
     * This method initializes btnUp
     * 
     * @return com.jgoodies.swing.util.ToolBarButton
     */
    private ToolBarButton getBtnUp() {
        if (btnUp == null) {
            btnUp = new ToolBarButton(new ImageIcon(getClass().getResource("/icons/Upward.gif")));
            btnUp.setToolTipText("����");
            btnUp.setMaximumSize(new java.awt.Dimension(24,24));
            btnUp.setPreferredSize(new java.awt.Dimension(24,24));
            btnUp.addActionListener(new java.awt.event.ActionListener() { 
            	public void actionPerformed(java.awt.event.ActionEvent e) {
            	    if (!hasPermission("move up")) {
            	        JOptionPane.showMessageDialog(RoutingEditor.this, "û��Ȩ�޽��д������.");
            	        return;
            	    }

            	    // Ŀǰ���ṩͬʱ�ƶ����еĹ���, ��Ϊ�и��ж�ѡʱ�����ƶ����߼���ȷ�� 
            	    int selRow = routingTable.getSelectedRow();
             	    if (selRow < 1 || selRow >= routingTable.getRowCount()) // < 1: �� 0 ��Ҳ����������
            	        return;

            	    int selCol = routingTable.getSelectedColumn();
            	    
            	    TableCellEditor editor = routingTable.getCellEditor();
            	    if (editor != null)
            	        editor.stopCellEditing();

            	    tableModel.moveRow(selRow, selRow, selRow - 1);

            	    routingTable.changeSelection(selRow - 1, selCol, false, false);
            	}
            });
        }
        return btnUp;
    }

    /**
     * This method initializes btnDown
     * 
     * @return com.jgoodies.swing.util.ToolBarButton
     */
    private ToolBarButton getBtnDown() {
        if (btnDown == null) {
            btnDown = new ToolBarButton(new ImageIcon(getClass().getResource("/icons/Downward.gif")));
            btnDown.setToolTipText("����");
            btnDown.setPreferredSize(new java.awt.Dimension(24,24));
            btnDown.setMaximumSize(new java.awt.Dimension(24,24));
            btnDown.addActionListener(new java.awt.event.ActionListener() { 
            	public void actionPerformed(java.awt.event.ActionEvent e) {    
            	    if (!hasPermission("move down")) {
            	        JOptionPane.showMessageDialog(RoutingEditor.this, "û��Ȩ�޽��д������.");
            	        return;
            	    }

            	    // Ŀǰ���ṩͬʱ�ƶ����еĹ���, ��Ϊ�и��ж�ѡʱ�����ƶ����߼���ȷ�� 
            	    int selRow = routingTable.getSelectedRow();
            	    if (selRow < 0 || selRow >= routingTable.getRowCount() - 1) // >= routingTable.getRowCount() - 1: ���һ�в��������ƶ�
            	        return;

            	    int selCol = routingTable.getSelectedColumn();
            	    
            	    TableCellEditor editor = routingTable.getCellEditor();
            	    if (editor != null)
            	        editor.stopCellEditing();

            	    tableModel.moveRow(selRow, selRow, selRow + 1);

            	    routingTable.changeSelection(selRow + 1, selCol, false, false);
            	}
            });
        }
        return btnDown;
    }

    /**
     * This method initializes innerFrameRoot
     * 
     * @return dyna.uic.MInternalFrame
     */
    private MInternalFrame getInnerFrameRouting() {
        if (innerFrameRouting == null) {
            innerFrameRouting = new MInternalFrame();
            innerFrameRouting.add(getJToolBar(), java.awt.BorderLayout.NORTH);
            innerFrameRouting.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
        }
        return innerFrameRouting;
    }

    /**
     * This method initializes innerFrameTop
     * 
     * @return dyna.uic.MInternalFrame
     */
    private MInternalFrame getInnerFrameInfo() {
        if (innerFrameInfo == null) {
            innerFrameInfo = new MInternalFrame();
            innerFrameInfo.setLayout(new GridBagLayout());
            innerFrameInfo.setPreferredSize(new java.awt.Dimension(0, 110));

            java.awt.GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
            jLabel3 = new JLabel();
            jLabel4 = new JLabel();
            jLabel2 = new JLabel();
            jLabel5 = new JLabel();
            jLabel1 = new JLabel();
            jLabel = new JLabel();
            java.awt.GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
            java.awt.GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
            java.awt.GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
            java.awt.GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
            java.awt.GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
            java.awt.GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
            java.awt.GridBagConstraints gridBagConstraints17 = new GridBagConstraints();
            java.awt.GridBagConstraints gridBagConstraints18 = new GridBagConstraints();
            java.awt.GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
            java.awt.GridBagConstraints gridBagConstraints22 = new GridBagConstraints();
            java.awt.GridBagConstraints gridBagConstraints23 = new GridBagConstraints();
            java.awt.GridBagConstraints gridBagConstraints25 = new GridBagConstraints();
            gridBagConstraints5.gridx = 0;
            gridBagConstraints5.gridy = 0;
            gridBagConstraints5.anchor = java.awt.GridBagConstraints.EAST;
            gridBagConstraints5.insets = new java.awt.Insets(0, 0, 0, 0);
            jLabel.setText("���:");
            gridBagConstraints6.gridx = 1;
            gridBagConstraints6.gridy = 0;
            gridBagConstraints6.weightx = 1.0;
            gridBagConstraints6.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints6.insets = new java.awt.Insets(0, 5, 3, 0);
            gridBagConstraints8.gridx = 2;
            gridBagConstraints8.gridy = 0;
            gridBagConstraints8.insets = new java.awt.Insets(0,5,0,0);
            jLabel1.setText("����:");
            gridBagConstraints9.gridx = 3;
            gridBagConstraints9.gridy = 0;
            gridBagConstraints9.weightx = 1.0;
            gridBagConstraints9.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints9.insets = new java.awt.Insets(0, 5, 3, 0);
            gridBagConstraints16.gridx = 3;
            gridBagConstraints16.gridy = 2;
            gridBagConstraints16.weightx = 1.0;
            gridBagConstraints16.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints16.insets = new java.awt.Insets(0, 5, 3, 0);
            gridBagConstraints17.gridx = 0;
            gridBagConstraints17.gridy = 2;
            gridBagConstraints17.anchor = java.awt.GridBagConstraints.EAST;
            gridBagConstraints17.insets = new java.awt.Insets(0,0,0,0);
            jLabel5.setText("ʹ��ͼ��:");
            gridBagConstraints18.gridx = 1;
            gridBagConstraints18.gridy = 2;
            gridBagConstraints18.weightx = 1.0;
            gridBagConstraints18.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints18.insets = new java.awt.Insets(0, 5, 3, 0);
            gridBagConstraints8.anchor = java.awt.GridBagConstraints.EAST;
            gridBagConstraints21.gridx = 0;
            gridBagConstraints21.gridy = 1;
            gridBagConstraints21.anchor = java.awt.GridBagConstraints.EAST;
            jLabel2.setText("����:");
            gridBagConstraints22.gridx = 2;
            gridBagConstraints22.gridy = 2;
            gridBagConstraints22.anchor = java.awt.GridBagConstraints.EAST;
            jLabel4.setText("����:");
            gridBagConstraints23.gridx = 0;
            gridBagConstraints23.gridy = 3;
            gridBagConstraints23.insets = new java.awt.Insets(0, 0, 0, 0);
            jLabel3.setText("�ֳ�����·��:");
            gridBagConstraints25.gridx = 1;
            gridBagConstraints25.gridy = 1;
            gridBagConstraints25.weightx = 1.0;
            gridBagConstraints25.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints25.gridwidth = 3;
            gridBagConstraints25.insets = new java.awt.Insets(0, 5, 3, 0);
            innerFrameInfo.add(jLabel, gridBagConstraints5);
            innerFrameInfo.add(jLabel2, gridBagConstraints21);
            innerFrameInfo.add(getTxtDesc(), gridBagConstraints25);
            innerFrameInfo.setBorder(javax.swing.BorderFactory
                    .createEmptyBorder(5, 0, 5, 0));
            gridBagConstraints11.gridx = 1;
            gridBagConstraints11.gridy = 1;
            gridBagConstraints11.weightx = 1.0;
            gridBagConstraints11.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints11.gridwidth = 3;
            gridBagConstraints11.insets = new java.awt.Insets(0, 5, 3, 0);
            gridBagConstraints1.gridx = 1;
            gridBagConstraints1.gridy = 3;
            gridBagConstraints1.weightx = 1.0;
            gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints1.insets = new java.awt.Insets(0, 5, 3, 0);
            innerFrameInfo.add(jLabel1, gridBagConstraints8);
            innerFrameInfo.add(getTxtMaterial(), gridBagConstraints16);
            innerFrameInfo.add(jLabel5, gridBagConstraints17);
            innerFrameInfo.add(getTxtDrawingNo(), gridBagConstraints18);
            innerFrameInfo.add(jLabel4, gridBagConstraints22);
            innerFrameInfo.add(jLabel3, gridBagConstraints23);
            innerFrameInfo.add(getTxtNumber(), gridBagConstraints6);
            innerFrameInfo.add(getTxtCategory(), gridBagConstraints9);
            innerFrameInfo.add(getTxtWorkshopRouting(), gridBagConstraints1);
        }
        return innerFrameInfo;
    }

    /**
     * @param dosObj
     * @param editMode	0 - ֻ�����������κβ���
     * 					1 - �ɱ༭���򣬿����ɾ�����򣬿�������λ�����Բ��������͹���ģ��
     * 					2 - ������1�Ĺ����������Ա༭׼��ʱ�䡢����ʱ�䡢����������
     * 					3 - �������б༭���� 1&2
     * @param authorTypes
     * @throws IllegalArgumentException
     */
    public void setContextObject(DOSChangeable dosObj, int editMode, ArrayList authorTypes)
            throws IllegalArgumentException {
        if (dosObj == null)
            throw new IllegalArgumentException("Invalidate argument - dosObj.");

        if (editMode < 0 || editMode > 3)
            throw new IllegalArgumentException("Invalidate argument - editMode.");
        
        contextObject = dosObj;
        tableModel.setAuthorInfo(editMode, authorTypes);

        refresh();
    }

    /**
     * Setup every column cell editor
     */
    private void setupColumnEditor() {
        TableColumnModel columnModel = routingTable.getColumnModel();
        TableColumn column = null;

        // Editor of SEQUENCE_NO_COLUMN
        column = columnModel
                .getColumn(RoutingTableModel.SEQUENCE_NO_COLUMN);
        column.setCellEditor(new DefaultCellEditor(new JTextField()));

        column.setPreferredWidth(24);

        // Editor of WORKSHOP_COLUMN
        cbxWorkshop = new JComboBox();

        column = columnModel.getColumn(RoutingTableModel.WORKSHOP_COLUMN);
        column.setCellEditor(new DefaultCellEditor(cbxWorkshop) {

            public Component getTableCellEditorComponent(JTable table,
                    Object value, boolean isSelected, int row, int column) {
                util.syncComboBoxWithValue(cbxWorkshop, value);
                return super.getTableCellEditorComponent(table, value, isSelected, row, column);
            }
            
        	public boolean stopCellEditing() {
        	    int selRow = routingTable.getSelectedRow();

        	    Object workshop = cbxWorkshop.getSelectedItem();
                Object oldWorkshop = routingTable.getValueAt(
                        routingTable.getSelectedRow(), RoutingTableModel.WORKSHOP_COLUMN);
                
            	if (workshop == null || oldWorkshop == null ||
            	        !workshop.toString().equals(oldWorkshop.toString()))
            	    if (selRow >= 0) { // �����ǰѡ��Ĺ�������
            	        tableModel.setValueAt(null, 
            	                selRow, RoutingTableModel.WORKCENTER_COLUMN);
            	    }

        		return super.stopCellEditing();
        	}
        });

        column.setPreferredWidth(48);

        // Editor of WORKCENTER_COLUMN
        cbxWorkCenter = new JComboBox();

        column = columnModel
                .getColumn(RoutingTableModel.WORKCENTER_COLUMN);
        column.setCellEditor(new DefaultCellEditor(cbxWorkCenter) {
            
            public Component getTableCellEditorComponent(JTable table,
                    Object value, boolean isSelected, int row, int column) {

                String workshopOuid = (String)tableModel.getValueAt(
                        row, RoutingTableModel.RAW_WORKSHOP_COLUMN);

                refreshCbxWorkCenter(workshopOuid);
                
                // select current value in combobox
                util.syncComboBoxWithValue(cbxWorkCenter, value);
                
                return super.getTableCellEditorComponent(table, value,
                        isSelected, row, column);
            }
        });

        column.setPreferredWidth(54);

        // Editor of OPERATION_SPECIALTY_COLUMN
        cbxOperationSpecialty = new JComboBox();
        
        column = columnModel.getColumn(RoutingTableModel.OPERATION_SPECIALTY_COLUMN);
        column.setCellEditor(new DefaultCellEditor(cbxOperationSpecialty) {

            public Component getTableCellEditorComponent(JTable table,
                    Object value, boolean isSelected, int row, int column) {
                util.syncComboBoxWithValue(cbxOperationSpecialty, value);
                return super.getTableCellEditorComponent(table, value, isSelected, row, column);
            }
        });

        column.setPreferredWidth(45);

        // Editor of WORKSHOP_SEQUENCE_COLUMN
        cbxWorkshopSquence = new JTextField();
        
        column = columnModel.getColumn(RoutingTableModel.WORKSHOP_SEQUENCE_COLUMN);
        column.setCellEditor(new DefaultCellEditor(cbxWorkshopSquence));

        column.setPreferredWidth(28);

        // Editor of SEQUENCE_TYPE_COLUMN
        cbxSquenceType = new JComboBox();

        column = columnModel
                .getColumn(RoutingTableModel.SEQUENCE_TYPE_COLUMN);
        column.setCellEditor(new DefaultCellEditor(cbxSquenceType) {

            public Component getTableCellEditorComponent(JTable table,
                    Object value, boolean isSelected, int row, int column) {
                util.syncComboBoxWithValue(cbxSquenceType, value);
                return super.getTableCellEditorComponent(table, value, isSelected, row, column);
            }

        	public boolean stopCellEditing() {
                Object squenceType = cbxSquenceType.getSelectedItem();
            	if (squenceType == null || squenceType.toString().equals("����˳�� [0]")) {
            	    // �������ͽ���������������
            	    int selRow = routingTable.getSelectedRow();
            	    if (selRow >= 0) {
            	        tableModel.setValueAt(null, selRow, RoutingTableModel.ENTER_SEQUENCE_COLUMN);
            	    	tableModel.setValueAt(null, selRow, RoutingTableModel.END_SEQUENCE_COLUMN);
            	    }
            	}

        		return super.stopCellEditing();
        	}
        });

        column.setPreferredWidth(36);

        // Editor of ENTER_SEQUENCE_COLUMN
        cbxXSquence = new JComboBox();

        column = columnModel
                .getColumn(RoutingTableModel.ENTER_SEQUENCE_COLUMN);
        column.setCellEditor(new DefaultCellEditor(cbxXSquence) {
            
            public Component getTableCellEditorComponent(JTable table,
                    Object value, boolean isSelected, int row, int column) {

                refreshCbxXSquence();
                util.syncComboBoxWithValue(cbxXSquence, value);

                return super.getTableCellEditorComponent(table, value,
                        isSelected, row, column);
            }
        });

        column.setPreferredWidth(36);

        // Editor of END_SEQUENCE_COLUMN, same as ENTER_SEQUENCE_COLUMN
        TableCellEditor sameEditorAsEnterSequenceColumn = column.getCellEditor();

        column = columnModel
                .getColumn(RoutingTableModel.END_SEQUENCE_COLUMN);
        column.setCellEditor(sameEditorAsEnterSequenceColumn);

        column.setPreferredWidth(36);

        // Editor of DESCRIPTION_COLUMN
        column = columnModel
                .getColumn(RoutingTableModel.DESCRIPTION_COLUMN);
        column.setCellEditor(textAreaPanel.getCellEditor());

        column.setPreferredWidth(512);

        // Editor of PREPARATION_TIME_COLUMN
        txtXTime = new JTextField();

        column = columnModel
                .getColumn(RoutingTableModel.PREPARATION_TIME_COLUMN);
        column.setCellEditor(new DefaultCellEditor(txtXTime) {
            
        	public Object getCellEditorValue() {
        	    // if the input text is the format like '1:50', change it to float '1.83'
        	    String value = txtXTime.getText().trim();
        	    if (value.matches("^\\d+:[0-5]\\d$")) {
        	        String [] splits = value.split(":");
        	        Float floatValue = new Float(Integer.parseInt(splits[0])
        	                + (Float.parseFloat(splits[1]) / 60));
        	        
        	        NumberFormat formator = NumberFormat.getInstance();
        	        formator.setMaximumFractionDigits(2);
        	        txtXTime.setText(formator.format(floatValue));
        	    }
        	    
        		return super.getCellEditorValue();
        	}
        });

        column.setPreferredWidth(36);

        // Editor of OPERATING_TIME_COLUMN, same as PREPARATION_TIME_COLUMN
        TableCellEditor sameEditorAsPreparationTimeColumn = column.getCellEditor();

        column = columnModel
                .getColumn(RoutingTableModel.OPERATING_TIME_COLUMN);
        column.setCellEditor(sameEditorAsPreparationTimeColumn);

        column.setPreferredWidth(36);

        // Editor of PROCESS_NUM_COLUMN
        JTextField txtProcessNum = new JTextField();

        column = columnModel
                .getColumn(RoutingTableModel.PROCESS_NUM_COLUMN);
        column.setCellEditor(new DefaultCellEditor(txtProcessNum));

        column.setPreferredWidth(36);

        // Editor of OPERATOR_NUM_COLUMN
        txtOperatorNum = new JTextField();

        column = columnModel
                .getColumn(RoutingTableModel.OPERATOR_NUM_COLUMN);
        column.setCellEditor(new DefaultCellEditor(txtOperatorNum));

        column.setPreferredWidth(36);

        // Editor of ATTACHMENT_COLUMN
        fceAttachment = new FileCellEditor(RoutingEditor.this);

        column = columnModel
                .getColumn(RoutingTableModel.ATTACHMENT_COLUMN);
        column.setCellEditor(fceAttachment);

        column.setPreferredWidth(45);
        
        // Remove column of raw data
        routingTable.removeColumn(columnModel.getColumn(RoutingTableModel.RAW_SEQUENCE_TYPE_COLUMN));
        routingTable.removeColumn(columnModel.getColumn(RoutingTableModel.RAW_OPERATION_SPECIALTY_COLUMN));
        routingTable.removeColumn(columnModel.getColumn(RoutingTableModel.RAW_WORKCENTER_COLUMN));
        routingTable.removeColumn(columnModel.getColumn(RoutingTableModel.RAW_WORKSHOP_COLUMN));
        routingTable.removeColumn(columnModel.getColumn(RoutingTableModel.RAW_OUID_COLUMN));
    }

    /**
     * Reload data of routing object
     */
    private void refresh() {
        // Getting data about contextObject
        String tmpString = (String) contextObject.get("md$number"); // ���
        txtNumber.setText(tmpString);
        tmpString = (String) contextObject.get("md$description"); // ����
        txtDesc.setText(tmpString);
        tmpString = (String) contextObject.get("old_no"); // ʹ��ͼ��
        txtDrawingNo.setText(tmpString);
        String strWorkshopRouting = (String) contextObject.get("workshop routing"); // �ֳ�����·��
        txtWorkshopRouting.setText(strWorkshopRouting);
        tmpString = (String) contextObject.get("name@part_category"); // ����
        txtCategory.setText(tmpString);
        tmpString = (String)contextObject.get("name@rawmaterial"); // ����
        txtMaterial.setText(tmpString);

        // Get routing object which associate with contextObject
        tableModel.removeAllRows();
        
		HashMap filter = new HashMap();
        filter.put("list.mode", "association");

        try {
            ArrayList searchResults = dos.listLinkFrom((String)contextObject.get("ouid"),
                    filter);

            if (searchResults != null) {
                int size = searchResults.size();
                for (int i = 0; i < size; i++) {
                    ArrayList tempList = (ArrayList) searchResults.get(i);
                    // the first element of a row is the info of linked instance.
                    String routingOuid = (String) tempList.get(0);
                    
                    DOSChangeable dosRouting = dos.get(routingOuid);
                    if (dosRouting != null)
                        tableModel.addRow(dosRouting, false);

                    tempList = null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Geting data of options
        try {
            // �ӹ��ֳ� ComboBox, �������Էֳ�����·��
            cbxWorkshop.removeAllItems();
            
            DOSChangeable dosCode = dos.getCodeWithName("�ֳ�");
            if (dosCode != null && strWorkshopRouting != null) {
	            String strCodeOuid = (String)dosCode.get("ouid");
	            String[] arrItems = strWorkshopRouting.split("-");

	            DOSChangeable dosCodeItem = null;
	            for (int i = 0; i < arrItems.length; i++) {
	                dosCodeItem = dos.getCodeItemWithId(strCodeOuid, arrItems[i]);
	                if (dosCodeItem == null)
	                    continue;
	
	                cbxWorkshop.addItem(new DOSObjectAdapter(dosCodeItem, "%name% [%codeitemid%]"));
	                
	                dosCodeItem = null;
	            }
            }
            
            // �������� ComboBox, �ڼ����༭ʱ��ʼ��
            
            // ����רҵ���� ComboBox, ���ݴ���� authorTypes ��ʼ��
            cbxOperationSpecialty.removeAllItems();

            int size = tableModel.authorTypes.size();
            for (int i = 0; i < size; i++) {
                String opSpecItemOuid = (String) tableModel.authorTypes.get(i);
                DOSChangeable dosOpSpecItem = dos.getCodeItem(opSpecItemOuid);
                if (dosOpSpecItem == null)
                    continue;
                
                cbxOperationSpecialty.addItem(new DOSObjectAdapter(dosOpSpecItem, "%name% [%codeitemid%]"));
            }
            
            // ˳������ ComboBox
            util.refreshCodeComboBox("����˳������", cbxSquenceType, null);
            
            // ����/�������� ComboBox, �ڼ����༭ʱ��ʼ��
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
	/**
     * ˢ�½���/�������� ComboBox, �Ե�ǰ�����еĹ�������
     */
    private void refreshCbxXSquence() {
        cbxXSquence.removeAllItems();
        
        int rows = routingTable.getRowCount();
        for (int i = 0; i < rows; i++) {
            Object squenceNum = tableModel.getValueAt(i,
                    RoutingTableModel.SEQUENCE_NO_COLUMN);
            
            cbxXSquence.addItem(squenceNum);
        }
    }

    /**
     * ˢ�¹������� ComboBox ��ѡ��, ֻ��ʾ�ӹ��ֳ���Ӧ�Ĺ�������
     */
    private void refreshCbxWorkCenter(String ouidOfWorkshopCodeItem) {
        cbxWorkCenter.removeAllItems();

        if (ouidOfWorkshopCodeItem == null || ouidOfWorkshopCodeItem.equals(""))
            return;

        try {
	        // search WorkCenter by it's workshop field value
            String strWorkshopFieldOuidOfWorkCenter = "860572a3";
            HashMap searchCondition = new HashMap();
            searchCondition.put(strWorkshopFieldOuidOfWorkCenter,
                    ouidOfWorkshopCodeItem);

            String strWorkCenterClassOuid = "86057288";
            ArrayList searchResults = null;
            
            searchResults = dos.list(strWorkCenterClassOuid, searchCondition);
            if (searchResults != null) {
                ArrayList tempList = null;
                
                int size = searchResults.size();
                for (int i = 0; i < size; i++) {
                    tempList = (ArrayList) searchResults.get(i);
                    String workCenterOuid = (String) tempList.get(0);
                    DOSChangeable dosWorkCenter = dos.get(workCenterOuid);
                    if (dosWorkCenter == null)
                        continue;
                    
                    cbxWorkCenter.addItem(new DOSObjectAdapter(dosWorkCenter, "%md$number% %md$description%"));
                    tempList = null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method initializes jSplitPane
     * 
     * @return javax.swing.JSplitPane
     */    
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setTopComponent(getInnerFrameRouting());
			jSplitPane.setDividerSize(6);
			jSplitPane.setDividerLocation(150);
			jSplitPane.setBottomComponent(getInnerFrameTools());
			jSplitPane.setOneTouchExpandable(true);
		}
		return jSplitPane;
	}
	/**
	 * This method initializes jTabbedPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */    
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setTabPlacement(javax.swing.JTabbedPane.LEFT);
			jTabbedPane.setMaximumSize(new java.awt.Dimension(32767,180));
			jTabbedPane.addTab("����ģ��", null, getRoutingTemplatePanel(), null);
			jTabbedPane.addTab("�����༭��", null, getTextAreaPanel(), null);
			jTabbedPane.addTab("��ʽ������", null, getFormulaCalculator(), null);
		}
		return jTabbedPane;
	}
	/**
	 * This method initializes templPanel	
	 * 	
	 * @return dyna.framework.client.dfempdm.RoutingTemplatePanel	
	 */    
	private RoutingTemplatePanel getRoutingTemplatePanel() {
		if (templPanel == null) {
			templPanel = new RoutingTemplatePanel();
			templPanel.setPreferredSize(new java.awt.Dimension(120,40));
		}
		return templPanel;
	}
	/**
	 * This method initializes calcPanel	
	 * 	
	 * @return dyna.framework.client.dfempdm.CalculatorPanel	
	 */    
	private CalculatorPanel getFormulaCalculator() {
		if (calcPanel == null) {
			calcPanel = new CalculatorPanel();
		}
		return calcPanel;
	}
	/**
	 * This method initializes jInternalFrame	
	 * 	
	 * @return javax.swing.JInternalFrame	
	 */    
	private MInternalFrame getInnerFrameTools() {
		if (innerFrameTools == null) {
			innerFrameTools = new MInternalFrame();
			innerFrameTools.add(getJTabbedPane(), java.awt.BorderLayout.CENTER);
		}
		return innerFrameTools;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return com.jgoodies.swing.util.ToolBarButton
	 */    
	private ToolBarButton getBtnDetail() {
		if (btnDetail == null) {
			btnDetail = new ToolBarButton(new ImageIcon(getClass().getResource("/icons/linkDetail.gif")));
			btnDetail.setToolTipText("�����Ϣ");
			btnDetail.setPreferredSize(new java.awt.Dimension(24,24));
			btnDetail.setMaximumSize(new java.awt.Dimension(24,24));
			btnDetail.addActionListener(new java.awt.event.ActionListener() { 
            	public void actionPerformed(java.awt.event.ActionEvent e) {    
            	    // ��ʾѡ�й���Ĺ������Ķ�����Ϣ
            	    int selRow = routingTable.getSelectedRow();
            	    if (selRow == -1)
            	        return;

            	    String ouidOfWorkCenter = (String)tableModel.getValueAt(
            	            selRow, RoutingTableModel.RAW_WORKCENTER_COLUMN);
            	    
                    try {
                        dyna.framework.client.UIBuilder.displayInstanceWindow(
                                ouidOfWorkCenter, dos, 
                                dyna.framework.client.DynaMOAD.dss);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
            	}
			});
		}
		return btnDetail;
	}
	/**
	 * This method initializes btnFittingInfo	
	 * 	
	 * @return com.jgoodies.swing.util.ToolBarButton	
	 */    
	private ToolBarButton getBtnFittingInfo() {
		if (btnFittingInfo == null) {
			btnFittingInfo = new ToolBarButton(new ImageIcon(getClass().getResource("/icons/Properties16.gif")));
			btnFittingInfo.setToolTipText("�鿴��װ��Ϣ");
			btnFittingInfo.setPreferredSize(new java.awt.Dimension(24,24));
			btnFittingInfo.setMaximumSize(new java.awt.Dimension(24,24));
			btnFittingInfo.addActionListener(new java.awt.event.ActionListener() { 
            	public void actionPerformed(java.awt.event.ActionEvent e) {
            	    //XXX ��ʾ����װ����Ϣ
            	}
            });
		}
		return btnFittingInfo;
	}
	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getTxtSearch() {
		if (txtSearch == null) {
			txtSearch = new JTextField();
			txtSearch.setPreferredSize(new java.awt.Dimension(180,22));
			txtSearch.setMaximumSize(new java.awt.Dimension(180,22));
		}
		return txtSearch;
	}
	/**
	 * This method initializes btnSearch	
	 * 	
	 * @return com.jgoodies.swing.util.ToolBarButton
	 */    
	private ToolBarButton getBtnSearch() {
		if (btnSearch == null) {
			btnSearch = new ToolBarButton(new ImageIcon(getClass().getResource("/icons/Search.gif")));
			btnSearch.setToolTipText("�ı���λ");
			btnSearch.setPreferredSize(new java.awt.Dimension(24,24));
			btnSearch.setMaximumSize(new java.awt.Dimension(24,24));
			btnSearch.addActionListener(new java.awt.event.ActionListener() { 
            	public void actionPerformed(java.awt.event.ActionEvent e) {
            	    String strSearch = txtSearch.getText().trim();
            	    if (strSearch.equals("")) {
            	        JOptionPane.showMessageDialog(RoutingEditor.this, "������Ҫ��ѯ���ı�.");
            	        return;
            	    }
            	    
            	    int rows = routingTable.getRowCount();
            	    int cols = routingTable.getColumnCount();
            	    boolean found = false;
            	    int i = 0, j = 0;
            	    
            	    if (strSearch.equals(searchText)) { // ��������
            	        i = searchToRow;
            	        j = searchToCol + 1; // search begin at the next cell since last column 
            	    }
            	    
            	    searchText = strSearch;
            	    txtSearch.setText(strSearch); // write back because we are trimmed
            	    
            	    CycleOfSearch:
            	    for (; i < rows; i++, j = 0)
            	        for (; j < cols; j++) {
            	            Object obj = routingTable.getValueAt(i, j);
            	            if (obj == null) {
            	                continue;
            	            }
            	            
            	            // ����һ������, ��Ϊ������� Integer ����, �޷�ǿ��ת��
            	            String value = obj.toString();
            	            if (value.indexOf(strSearch) != -1) {
            	                found = true;
            	                break CycleOfSearch;
            	            }
            	        }
            	    
            	    if (found) {
            	        // ���ҵ��ĵ�Ԫ����Ϊ�༭״̬
            	        routingTable.changeSelection(i, j, false, false);
            	        routingTable.editCellAt(i, j);
            	        
            	        searchToRow = i;
            	        searchToCol = j;
            	        
            	    } else {
            			JOptionPane.showMessageDialog(RoutingEditor.this, "δ�ҵ�������������Ŀ, �´ν���������.");
            	        searchToRow = 0;
            	        searchToCol = 0;
            	    }
            	}
			});
		}
		return btnSearch;
	}
	
    private void save() {
        logger.fine("--- start save ---");

        // ��������ǰ���м��
        if (!checkData())
            return;
        
        int row = -1;
        try {
	        // ��ȡԭ�������б�, �Ա����Ƚ�ɾ��
            ArrayList oldRoutings = new ArrayList();
            
			HashMap filter = new HashMap();
	        filter.put("list.mode", "association");

            ArrayList searchResults = dos.listLinkFrom((String)contextObject.get("ouid"),
                    filter);

            if (searchResults != null) {
                ArrayList tempList = null;
                int size = searchResults.size();
                for (int i = 0; i < size; i++) {
                    tempList = (ArrayList) searchResults.get(i);
                    oldRoutings.add((String)tempList.get(0)); // tempList.get(0) is ouid of routing
                    tempList = null;
                }
            }
            
            // ������������, ������º���������
            DOSChangeable dosRouting = null;
            String ouid = null;
            
            int size = tableModel.getRowCount();
            for (row = 0; row < size; row++) {
                dosRouting = tableModel.getRawData(row);
                ouid = (String)dosRouting.get("ouid");
                
                if (ouid == null || ouid.equals("")) { // ��������
                    String tmpString = dos.add(dosRouting);
                    // ���»�ȡ��Ӻ�� DosObject, 
                    dosRouting = dos.get(tmpString);
                    tableModel.setRawData(row, dosRouting);
                    // ͬ�㲿������������ϵ
                    dos.link((String)contextObject.get("ouid"), (String)dosRouting.get("ouid"));
                    logger.fine("Row " + row + ":Object added" + tmpString);
                } else { // ���¶���
                    if (dosRouting.isChanged()) {
                        dos.set(dosRouting);
                        logger.fine("Row " + row + ":Object updated" + ouid);
                    }
                }
                
                // ���˳���ɾ���Ķ����б�
                if (oldRoutings.contains(ouid))
                    oldRoutings.remove(ouid);
                
                dosRouting = null;
                ouid = null;
            }
            
            // ������Ҫɾ��������
            size = oldRoutings.size();
            for (int i = 0; i < size; i++ ) {
                String ouidRouting = (String)oldRoutings.get(i);
                try {
                    dos.remove(ouidRouting);
                    logger.fine("Object deleted" + ouidRouting);
                } catch (Exception e) {
                    logger.warning("Object deleted - ERROR!!\nexception: " + e);
                }
            }
            
            // �����㲿��������±�ʶ
            Object changeMark = contextObject.get("Operation Mark");
            if (changeMark == null || !changeMark.toString().equals("OPR")) {
                contextObject.put("Operation Mark", "OPR");
                dos.set(contextObject);
            }
            
            JOptionPane.showMessageDialog(this, "����ɹ�.", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            logger.warning("exception: " + e);
            JOptionPane.showMessageDialog(this, "����ʧ��, ������ " + (row+1) + " ���������Ƿ�����.", "��ʾ", JOptionPane.WARNING_MESSAGE);
        }

        logger.fine("--- end save ---");
    }

    /**
     * @return
     */
    private boolean checkData() {
        String msg = "";
        
        // �߼�Ϊ: "����"�ֶμ���ǰ��������ֶζ�Ϊ����, ���˵�˳������Ϊ"����"ʱ
        // ��ʼ/��ֹ�ֶα���Ϊ��, ���׼��ʱ��/����ʱ��/����/�����ֶ�Ϊ������
        String tmpString = null;
        int rows = routingTable.getRowCount();
        int cols = routingTable.getColumnCount();
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Object value = tableModel.getValueAt(i, j);
                tmpString = value == null ? "" : value.toString().trim();
                
                String rowTitle = (String)routingTable.getValueAt(i, RoutingTableModel.SEQUENCE_NO_COLUMN);
                
                switch (j) {
                case RoutingTableModel.SEQUENCE_NO_COLUMN:
                case RoutingTableModel.WORKSHOP_COLUMN:
                case RoutingTableModel.WORKCENTER_COLUMN:
                case RoutingTableModel.OPERATION_SPECIALTY_COLUMN:
                case RoutingTableModel.SEQUENCE_TYPE_COLUMN:
                case RoutingTableModel.DESCRIPTION_COLUMN:
                    if (tmpString.equals(""))
                        msg += "�� " + rowTitle + ", " + routingTable.getColumnName(j) + "������д;\n";
                    break;
                case RoutingTableModel.ENTER_SEQUENCE_COLUMN:
                case RoutingTableModel.END_SEQUENCE_COLUMN:
                    Object tmpObject = routingTable.getValueAt(i, RoutingTableModel.SEQUENCE_TYPE_COLUMN);
                	Integer intValue = null;
                	
                    if (tmpObject != null && tmpObject.toString().equals("����˳�� [1]")) {
                        if (value == null || tmpString.equals(""))
                            msg += "�� " + rowTitle + ", ���й��������" + routingTable.getColumnName(j) + ";\n";
                        else {
                            try {
                                intValue = new Integer(tmpString);
                                if (intValue.intValue() > (routingTable.getRowCount())*10) {
                                    msg += "�� " + rowTitle + ", " + routingTable.getColumnName(j) + "ֵ��Χ����;\n";
                                    break;
                                }
                            } catch (NumberFormatException nfe) {
                                msg += "�� " + rowTitle + ", " + routingTable.getColumnName(j) + "����������Ч����;\n";
                            }
                        }
                    
                        if (j == RoutingTableModel.END_SEQUENCE_COLUMN
                                && value != null && tmpString.equals("") == false) {
                            tmpObject = routingTable.getValueAt(i, RoutingTableModel.ENTER_SEQUENCE_COLUMN);
                            if (tmpObject != null && Integer.parseInt(tmpObject.toString()) >= Integer.parseInt(tmpString))
                                msg += "�� " + rowTitle + ", ����������������ʼ����;\n";
                        }
                    }
                    
                    tableModel.getRawData(i).put(RoutingTableModel.getColumnDosName(j), intValue);
                    break;
                case RoutingTableModel.PREPARATION_TIME_COLUMN:
                case RoutingTableModel.OPERATING_TIME_COLUMN:
                    try {
                        if (value != null && !tmpString.equals(""))
                            tableModel.getRawData(i).put(RoutingTableModel.getColumnDosName(j), new Float(tmpString));
                    } catch (NumberFormatException nfe) {
                        msg += "�� " + rowTitle + ", " + routingTable.getColumnName(j) + "����������Ч�����ȸ�����;\n";
                    }
                    break;
                case RoutingTableModel.PROCESS_NUM_COLUMN:
                    try {
                        if (value != null && !tmpString.equals(""))
                        	tableModel.getRawData(i).put(RoutingTableModel.getColumnDosName(j), new Double(tmpString));
                    } catch (NumberFormatException nfe) {
                        msg += "�� " + rowTitle + ", " + routingTable.getColumnName(j) + "����������Ч˫���ȸ�����;\n";
                    }
                    break;
                case RoutingTableModel.WORKSHOP_SEQUENCE_COLUMN:
                case RoutingTableModel.OPERATOR_NUM_COLUMN:
                    try {
                        if (value != null && !tmpString.equals(""))
                        	tableModel.getRawData(i).put(RoutingTableModel.getColumnDosName(j), new Integer(tmpString));
                    } catch (NumberFormatException nfe) {
                        msg += "�� " + rowTitle + ", " + routingTable.getColumnName(j) + "����������Ч����;\n";
                    }
                    break;
                }
            }
        }
        
        if (msg.length() > 0) {
            JDialog msgDlg = util.createInformationDialog(this, "��ʾ", "����������д����ȷ:\n" + msg + "�����������±���.");
            util.centerWindow(null, msgDlg);
            msgDlg.show();
            msgDlg = null;
            
            return false;
        }
        
        return true;
    }

    /**
	 * This method initializes textAreaPanel	
	 * 	
	 * @return dyna.framework.client.dfempdm.TextAreaPanel	
	 */
	private TextAreaPanel getTextAreaPanel() {
		if (textAreaPanel == null) {
			textAreaPanel = new TextAreaPanel();
		}
		return textAreaPanel;
	}
	
	/** �Բ���(�����, ɾ��, ����, ��/����λ�Ƚ�����֤)
	 * @param action ������ʶ, ������"save", "save tempate", "add", "remove", "move up", "move down"
	 * @return true - ����Ȩ��
	 *         false - û��Ȩ��
	 */
	public boolean hasPermission(String action) {
	    // ���ȱ༭ģʽ��֤ editMode
	    if (tableModel.editMode == 0) {
	        return false;
	    } else if (tableModel.editMode == 2) {
	        if (action.equals("save") == false) // ������ save ����, ��Ķ�������
	            return false;
	    }

//	    // ��ν��б༭�߹�����֤ authorTypes
//	    int row = routingTable.getSelectedRow();
//        String ouidOpSpec = (String)routingTable.getValueAt(row, RoutingTableModel.RAW_OPERATION_SPECIALTY_COLUMN);
//        if (tableModel.authorTypes.contains(ouidOpSpec) == false) {
//            if (action.equals(""))
//                return false;
//        }
        
	    return true;
	}
    
} //  @jve:decl-index=0:visual-constraint="10,10"
