/*
 * Created on 2004-11-2
 *
 */
package dyna.framework.client.dfempdm;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.AbstractCellEditor;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellEditor;

/**
 * @author ภ๎ิจ
 *
 */
public class TextAreaPanel extends JPanel {
    private myCellEditor cellEditor = null;

	private JTextArea txtContent = null;
	private JScrollPane jScrollPane = null;
    
    /**
	 * This method initializes 
	 * 
	 */
	public TextAreaPanel() {
		super();
		initialize();
		
		cellEditor = new myCellEditor();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
        this.setLayout(new BorderLayout());
        this.setSize(366, 257);
        this.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
	}
	/**
	 * This method initializes txtContent	
	 * 	
	 * @return javax.swing.JTextArea	
	 */    
	private JTextArea getTxtContent() {
		if (txtContent == null) {
			txtContent = new JTextArea();
	        txtContent.addKeyListener(new KeyAdapter() {
	            
	            public void keyTyped(KeyEvent e) {
	                cellEditor.editor.setText(txtContent.getText());
	            }
	        });
		}
		return txtContent;
	}
	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTxtContent());
		}
		return jScrollPane;
	}

    public void setText(String text) {
        txtContent.setText(text == null ? "" : text);
    }
	
	public void setEditable(boolean flag) {
	    txtContent.setEditable(flag);
	}

    public String getText() {
        return txtContent.getText();
    }
	
    public myCellEditor getCellEditor() {
        return cellEditor;
    }

    class myCellEditor extends AbstractCellEditor implements
            TableCellEditor {

	    JTextArea editor = null;
	    
        /**
         * @param panel
         */
        public myCellEditor() {
            editor = new JTextArea();
            //editor.setEditable(false);
//            editor.getDocument().addDocumentListener(new DocumentListener() {
//                public void changedUpdate(DocumentEvent e) {
//                    Document doc = e.getDocument();
//                    setText(editor.getText()/*doc.getText(0, doc.getLength())*/);
//                }
//
//                public void insertUpdate(DocumentEvent e) {}
//                public void removeUpdate(DocumentEvent e) {}
//            });
            editor.addKeyListener(new KeyAdapter() {
                public void keyTyped(KeyEvent e) {
                    TextAreaPanel.this.setText(editor.getText());
                }
            });
        }

        /*
         * (non-Javadoc)
         * 
         * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent(javax.swing.JTable,
         *      java.lang.Object, boolean, int, int)
         */
        public Component getTableCellEditorComponent(JTable table,
                Object value, boolean isSelected, int row, int column) {
            setText((String)value);
            editor.setText((String)value);
            return editor;
        }

        /*
         * (non-Javadoc)
         * 
         * @see javax.swing.CellEditor#getCellEditorValue()
         */
        public Object getCellEditorValue() {
            return getText();
        }
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
