/*
 * Created on 2004-10-22
 *
 */
package dyna.framework.client.dfempdm;

import javax.swing.JPanel;

import javax.swing.JTextArea;
/**
 * @author ภ๎ิจ
 *
 */
public class CalculatorPanel extends JPanel {

	private JTextArea jTextArea = null;
	/**
	 * This method initializes 
	 * 
	 */
	public CalculatorPanel() {
		super();
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
        this.add(getJTextArea(), null);
			
	}
	/**
	 * This method initializes jTextArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */    
	private JTextArea getJTextArea() {
		if (jTextArea == null) {
			jTextArea = new JTextArea();
			jTextArea.setPreferredSize(new java.awt.Dimension(120,80));
			jTextArea.setText("Nothing at all;");
		}
		return jTextArea;
	}
 }
