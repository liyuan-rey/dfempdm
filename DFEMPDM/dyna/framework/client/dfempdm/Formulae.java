/*
 * Created on 2004-11-9
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package dyna.framework.client.dfempdm;

import javax.swing.JFrame;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
/**
 * @author 李渊
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Formulae extends JDialog {

	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JPanel paneData = null;
	private JPanel jPanel3 = null;
	private JButton btnOk = null;
	private JButton btnCancel = null;
	private JLabel lblFormula = null;
	private JLabel lblFormulaDesc = null;
	private JScrollPane jScrollPane = null;
	private JPanel paneDataItem = null;  //  @jve:decl-index=0:visual-constraint="428,148"
	private JLabel lblName = null;
	private JTextField txtValue = null;
	/**
	 * This method initializes 
	 * 
	 */
	public Formulae() {
		super();
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
        this.setContentPane(getJScrollPane());
        this.setTitle("公式计算");
        this.setSize(278, 281);
			
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5,5,5,5));
			jPanel.add(getJPanel1(), java.awt.BorderLayout.NORTH);
			jPanel.add(getPaneData(), java.awt.BorderLayout.CENTER);
			jPanel.add(getJPanel3(), java.awt.BorderLayout.SOUTH);
		}
		return jPanel;
	}
	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			lblFormulaDesc = new JLabel();
			lblFormula = new JLabel();
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BoxLayout(jPanel1, BoxLayout.Y_AXIS));
			lblFormula.setText("a+b*c/d");
			lblFormula.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,5,0));
			lblFormulaDesc.setText("<公式描述>");
			lblFormulaDesc.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,5,0));
			jPanel1.setBackground(new java.awt.Color(223,216,206));
			jPanel1.add(lblFormulaDesc, null);
			jPanel1.add(lblFormula, null);
		}
		return jPanel1;
	}
	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getPaneData() {
		if (paneData == null) {
			GridLayout gridLayout31 = new GridLayout();
			paneData = new JPanel();
			paneData.setLayout(gridLayout31);
			paneData.setBackground(new java.awt.Color(223,216,206));
			gridLayout31.setRows(3);
			gridLayout31.setVgap(5);
			gridLayout31.setColumns(1);
			paneData.add(getPaneDataItem(), null);
		}
		return paneData;
	}
	/**
	 * This method initializes jPanel3	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			FlowLayout flowLayout24 = new FlowLayout();
			jPanel3 = new JPanel();
			jPanel3.setLayout(flowLayout24);
			flowLayout24.setAlignment(java.awt.FlowLayout.RIGHT);
			jPanel3.setBackground(new java.awt.Color(223,216,206));
			jPanel3.add(getBtnOk(), null);
			jPanel3.add(getBtnCancel(), null);
		}
		return jPanel3;
	}
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setText("计算");
		}
		return btnOk;
	}
	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("取消");
		}
		return btnCancel;
	}
	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJPanel());
		}
		return jScrollPane;
	}
	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getPaneDataItem() {
		if (paneDataItem == null) {
			paneDataItem = new JPanel();
			lblName = new JLabel();
			paneDataItem.setLayout(new BorderLayout());
			lblName.setText("<name>");
			lblName.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,5,5));
			paneDataItem.add(lblName, java.awt.BorderLayout.WEST);
			paneDataItem.add(getTxtValue(), java.awt.BorderLayout.CENTER);
		}
		return paneDataItem;
	}
	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getTxtValue() {
		if (txtValue == null) {
			txtValue = new JTextField();
			txtValue.setText("<value>");
		}
		return txtValue;
	}
   }  //  @jve:decl-index=0:visual-constraint="10,10"
