/*
 * Created on 2004-11-9
 *
 */
package dyna.framework.client.dfempdm;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import dyna.framework.service.DOS;
import dyna.framework.service.dos.DOSChangeable;
/**
 * @author 李渊
 *
 */
public class MaterialCalculator extends JFrame {
    private static DOS dos = dyna.framework.client.DynaMOAD.dos;
    private DOSChangeable contextObj = null;
    private static final String remarkTempl = "其中, 夹头 %j 压头 %y    每 %m 件一个夹头或压头.";
    
	private JPanel jPanel = null;
	private JLabel jLabel = null;
	private JTextField txtMaterialDim = null;
	private JLabel jLabel1 = null;
	private JTextField txtRoughQty = null;
	private JLabel jLabel2 = null;
	private JTextField txtWorkshopRouting = null;
	private JLabel jLabel3 = null;
	private JCheckBox chkShowAllFormulae = null;
	private JLabel jLabel4 = null;
	private JComboBox cbxRoughUom = null;
	private JLabel jLabel5 = null;
	private JComboBox cbxMaterialUom = null;
	private JLabel jLabel6 = null;
	private JTextField txtMakePartNum = null;
	private JPanel jPanel1 = null;
	private JLabel jLabel7 = null;
	private JTextField txtColletNum = null;
	private JLabel jLabel8 = null;
	private JTextField txtBallastNum = null;
	private JLabel jLabel9 = null;
	private JTextField txtEveryRange = null;
	private JLabel jLabel10 = null;
	private JComboBox cbxFormulae = null;
	private JLabel jLabel11 = null;
	private JPanel jPanel2 = null;
	private JButton btnOk = null;
	private JButton btnCancel = null;
	private JTextField txtRation = null;
	private JLabel jLabel12 = null;
	private JScrollPane jScrollPane = null;
	/**
	 * This method initializes 
	 * 
	 */
	public MaterialCalculator() {
		super();
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        this.setContentPane(getJScrollPane());
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icons/SmallSearchButton.gif")));
        this.setTitle("材料定额计算");
        this.setSize(331, 324);
			
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel12 = new JLabel();
			jLabel11 = new JLabel();
			jLabel6 = new JLabel();
			jLabel5 = new JLabel();
			jLabel4 = new JLabel();
			jLabel3 = new JLabel();
			jLabel2 = new JLabel();
			jLabel1 = new JLabel();
			jLabel = new JLabel();
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints17 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints18 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints19 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints22 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints23 = new GridBagConstraints();
			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.gridy = 5;
			gridBagConstraints1.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints1.insets = new java.awt.Insets(0,0,5,0);
			jLabel.setText("毛坯下料尺寸");
			gridBagConstraints2.gridx = 1;
			gridBagConstraints2.gridy = 5;
			gridBagConstraints2.weightx = 1.0;
			gridBagConstraints2.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints2.insets = new java.awt.Insets(0,5,5,0);
			gridBagConstraints3.gridx = 2;
			gridBagConstraints3.gridy = 5;
			gridBagConstraints3.insets = new java.awt.Insets(0,5,5,0);
			jLabel1.setText("毛坯数量");
			gridBagConstraints4.gridx = 3;
			gridBagConstraints4.gridy = 5;
			gridBagConstraints4.weightx = 1.0;
			gridBagConstraints4.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints4.insets = new java.awt.Insets(0,5,5,0);
			jPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5,5,5,5));
			jPanel.setBackground(new java.awt.Color(223,216,206));
			gridBagConstraints5.gridx = 0;
			gridBagConstraints5.gridy = 1;
			gridBagConstraints5.insets = new java.awt.Insets(0,0,5,0);
			gridBagConstraints5.anchor = java.awt.GridBagConstraints.WEST;
			jLabel2.setText("分厂工艺路线");
			gridBagConstraints6.gridx = 1;
			gridBagConstraints6.gridy = 1;
			gridBagConstraints6.weightx = 1.0;
			gridBagConstraints6.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints6.gridwidth = 3;
			gridBagConstraints6.insets = new java.awt.Insets(0,5,5,0);
			gridBagConstraints7.gridx = 0;
			gridBagConstraints7.gridy = 2;
			gridBagConstraints7.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints7.insets = new java.awt.Insets(0,0,5,0);
			jLabel3.setText("计算公式");
			gridBagConstraints9.gridx = 1;
			gridBagConstraints9.gridy = 2;
			gridBagConstraints9.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints9.insets = new java.awt.Insets(0,5,5,0);
			gridBagConstraints10.gridx = 0;
			gridBagConstraints10.gridy = 6;
			gridBagConstraints10.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints10.insets = new java.awt.Insets(0,0,5,0);
			jLabel4.setText("毛坯单位");
			gridBagConstraints11.gridx = 1;
			gridBagConstraints11.gridy = 6;
			gridBagConstraints11.weightx = 1.0;
			gridBagConstraints11.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints11.insets = new java.awt.Insets(0,5,5,0);
			gridBagConstraints12.gridx = 2;
			gridBagConstraints12.gridy = 6;
			gridBagConstraints12.insets = new java.awt.Insets(0,5,5,0);
			jLabel5.setText("材料单位");
			gridBagConstraints13.gridx = 3;
			gridBagConstraints13.gridy = 6;
			gridBagConstraints13.weightx = 1.0;
			gridBagConstraints13.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints13.insets = new java.awt.Insets(0,5,5,0);
			gridBagConstraints14.gridx = 0;
			gridBagConstraints14.gridy = 7;
			gridBagConstraints14.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints14.insets = new java.awt.Insets(0,0,5,0);
			jLabel6.setText("加工零件数量");
			gridBagConstraints15.gridx = 1;
			gridBagConstraints15.gridy = 7;
			gridBagConstraints15.weightx = 1.0;
			gridBagConstraints15.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints15.insets = new java.awt.Insets(0,5,5,0);
			gridBagConstraints16.gridx = 0;
			gridBagConstraints16.gridy = 8;
			gridBagConstraints16.gridwidth = 4;
			gridBagConstraints16.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints17.gridx = 0;
			gridBagConstraints17.gridy = 4;
			gridBagConstraints17.weightx = 1.0;
			gridBagConstraints17.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints17.gridwidth = 4;
			gridBagConstraints17.insets = new java.awt.Insets(0,0,5,0);
			gridBagConstraints18.gridx = 0;
			gridBagConstraints18.gridy = 9;
			gridBagConstraints18.gridwidth = 4;
			gridBagConstraints18.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints18.insets = new java.awt.Insets(0,0,5,0);
			jLabel11.setText("");
			jLabel11.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			gridBagConstraints19.gridx = 2;
			gridBagConstraints19.gridy = 12;
			gridBagConstraints19.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints19.gridwidth = 2;
			jPanel.add(getJPanel2(), gridBagConstraints19);
			gridBagConstraints22.gridx = 1;
			gridBagConstraints22.gridy = 11;
			gridBagConstraints22.weightx = 1.0;
			gridBagConstraints22.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints22.insets = new java.awt.Insets(0,5,5,0);
			gridBagConstraints23.gridx = 0;
			gridBagConstraints23.gridy = 11;
			gridBagConstraints23.insets = new java.awt.Insets(0,0,5,0);
			gridBagConstraints23.anchor = java.awt.GridBagConstraints.WEST;
			jLabel12.setText("定额");
			jPanel.add(jLabel, gridBagConstraints1);
			jPanel.add(getTxtMaterialDim(), gridBagConstraints2);
			jPanel.add(jLabel1, gridBagConstraints3);
			jPanel.add(getTxtRoughQty(), gridBagConstraints4);
			jPanel.add(jLabel2, gridBagConstraints5);
			jPanel.add(getTxtWorkshopRouting(), gridBagConstraints6);
			jPanel.add(jLabel3, gridBagConstraints7);
			jPanel.add(getChkShowAllFormulae(), gridBagConstraints9);
			jPanel.add(jLabel4, gridBagConstraints10);
			jPanel.add(getCbxRoughUom(), gridBagConstraints11);
			jPanel.add(jLabel5, gridBagConstraints12);
			jPanel.add(getCbxMaterialUom(), gridBagConstraints13);
			jPanel.add(jLabel6, gridBagConstraints14);
			jPanel.add(getTxtMakePartNum(), gridBagConstraints15);
			jPanel.add(getJPanel1(), gridBagConstraints16);
			jPanel.add(getCbxFormulae(), gridBagConstraints17);
			jPanel.add(jLabel11, gridBagConstraints18);
			jPanel.add(getTxtRation(), gridBagConstraints22);
			jPanel.add(jLabel12, gridBagConstraints23);
		}
		return jPanel;
	}
	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getTxtMaterialDim() {
		if (txtMaterialDim == null) {
			txtMaterialDim = new JTextField();
		}
		return txtMaterialDim;
	}
	/**
	 * This method initializes jTextField1	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getTxtRoughQty() {
		if (txtRoughQty == null) {
			txtRoughQty = new JTextField();
		}
		return txtRoughQty;
	}
	/**
	 * This method initializes jTextField2	
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
	 * This method initializes jCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */    
	private JCheckBox getChkShowAllFormulae() {
		if (chkShowAllFormulae == null) {
			chkShowAllFormulae = new JCheckBox();
			chkShowAllFormulae.setText("列出所有公式");
			chkShowAllFormulae.setBackground(new java.awt.Color(223,216,206));
		}
		return chkShowAllFormulae;
	}
	/**
	 * This method initializes jComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */    
	private JComboBox getCbxRoughUom() {
		if (cbxRoughUom == null) {
			cbxRoughUom = new JComboBox();
		}
		return cbxRoughUom;
	}
	/**
	 * This method initializes jComboBox1	
	 * 	
	 * @return javax.swing.JComboBox	
	 */    
	private JComboBox getCbxMaterialUom() {
		if (cbxMaterialUom == null) {
			cbxMaterialUom = new JComboBox();
		}
		return cbxMaterialUom;
	}
	/**
	 * This method initializes jTextField3	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getTxtMakePartNum() {
		if (txtMakePartNum == null) {
			txtMakePartNum = new JTextField();
		}
		return txtMakePartNum;
	}
	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabel10 = new JLabel();
			jLabel9 = new JLabel();
			jLabel8 = new JLabel();
			jLabel7 = new JLabel();
			FlowLayout flowLayout20 = new FlowLayout();
			jPanel1 = new JPanel();
			jPanel1.setLayout(flowLayout20);
			jLabel7.setText("其中, 夹头");
			jLabel8.setText("压头");
			jLabel9.setText("    每");
			jLabel10.setText("件一个夹头或压头");
			flowLayout20.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel1.setBackground(new java.awt.Color(223,216,206));
			jPanel1.add(jLabel7, null);
			jPanel1.add(getTxtColletNum(), null);
			jPanel1.add(jLabel8, null);
			jPanel1.add(getTxtBallastNum(), null);
			jPanel1.add(jLabel9, null);
			jPanel1.add(getTxtEveryRange(), null);
			jPanel1.add(jLabel10, null);
		}
		return jPanel1;
	}
	/**
	 * This method initializes jTextField4	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getTxtColletNum() {
		if (txtColletNum == null) {
			txtColletNum = new JTextField();
			txtColletNum.setColumns(2);
		}
		return txtColletNum;
	}
	/**
	 * This method initializes jTextField5	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getTxtBallastNum() {
		if (txtBallastNum == null) {
			txtBallastNum = new JTextField();
			txtBallastNum.setColumns(2);
		}
		return txtBallastNum;
	}
	/**
	 * This method initializes jTextField6	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getTxtEveryRange() {
		if (txtEveryRange == null) {
			txtEveryRange = new JTextField();
			txtEveryRange.setColumns(2);
		}
		return txtEveryRange;
	}
	/**
	 * This method initializes jComboBox2	
	 * 	
	 * @return javax.swing.JComboBox	
	 */    
	private JComboBox getCbxFormulae() {
		if (cbxFormulae == null) {
			cbxFormulae = new JComboBox();
			cbxFormulae.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
    			    Formulae f = new Formulae();
    			    f.show();
                }
			});
		}
		return cbxFormulae;
	}
	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			FlowLayout flowLayout21 = new FlowLayout();
			jPanel2 = new JPanel();
			jPanel2.setLayout(flowLayout21);
			flowLayout21.setAlignment(java.awt.FlowLayout.RIGHT);
			jPanel2.setBackground(new java.awt.Color(223,216,206));
			jPanel2.add(getBtnOk(), null);
			jPanel2.add(getBtnCancel(), null);
		}
		return jPanel2;
	}
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setText("确定");
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
	 * This method initializes jTextField7	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getTxtRation() {
		if (txtRation == null) {
			txtRation = new JTextField();
		}
		return txtRation;
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
     * @param dosObj
     */
    public void setContextObject(DOSChangeable dosObj)
            throws IllegalArgumentException {
        if (dosObj == null)
            throw new IllegalArgumentException("Invalidate argument - dosObj.");

        contextObj = dosObj;
        
        refresh();
    }

    /**
     * 刷新界面数据
     */
    private void refresh() {
        try {
            Object [][] map = {
                {"workshop routing", txtWorkshopRouting}, // 分厂工艺路线
                {"r_material_dim", txtMaterialDim}, // 毛坯下料尺寸
                {"rough qty", txtRoughQty}, // 毛坯数量
                {"makepartnum", txtMakePartNum}, // 加工零件数量
                {"materialration", txtRation} // 定额
            };
            
            Object tmpObject = null;
            
            for (int i = 0; i < map.length; i++) {
                tmpObject = contextObj.get((String)map[i][0]);
                ((JTextField)map[i][1]).setText(
                        tmpObject == null ? "" : tmpObject.toString());
            }
            
            // 定额备注
            tmpObject = contextObj.get("Material Ration Remarks");
            if (tmpObject != null) {
                try {
                    String tmpString = tmpObject.toString();
                    // 夹头
                    int start = 7;
                    int end = tmpString.indexOf(" ", start);
                    String tmpNum = tmpString.substring(start, end);
                    txtColletNum.setText(tmpNum);
                    // 压头
                    start = end + 4;
                    end = tmpString.indexOf(" ", start);
                    tmpNum = tmpString.substring(start, end);
                    txtBallastNum.setText(tmpNum);
                    // 每
                    start = end + 2;
                    end = tmpString.indexOf(" ", start);
                    tmpNum = tmpString.substring(start, end);
                    txtEveryRange.setText(tmpNum);
                } catch (Exception e) {
                    // Maybe we should got error, but no matter
                }
            }
            
            // 毛坯单位
            tmpObject = contextObj.get("rough_uom");
            util.refreshCodeComboBox("单位", cbxRoughUom, (String)tmpObject);
            
            // 材料单位
            tmpObject = contextObj.get("m_uom");
            util.refreshCodeComboBox("单位", cbxMaterialUom, (String)tmpObject);
            
            // 公式列表
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "读取数据失败.", "提示", JOptionPane.WARNING_MESSAGE);
        }
        
    }

}  //  @jve:decl-index=0:visual-constraint="10,10"
