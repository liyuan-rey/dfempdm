/*
 * Created on 2004-11-4
 *
 */
package dyna.framework.client.dfempdm;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import dyna.framework.client.UIGeneration;
import dyna.framework.service.dos.DOSChangeable;

/**
 * @author ภ๎ิจ
 *
 */
public class HowTo {

    public static void useRoutingEditor(Object uiNormalPart) {
		try {
		    if (false == Class.forName("dyna.framework.client.UIGeneration").isInstance(uiNormalPart))
				JOptionPane.showMessageDialog(null, "inputObject's type mismatch when calling routing editor.\n");
		    
		    DOSChangeable dosObj = ((UIGeneration)uiNormalPart).getInstanceData();
			RoutingEditor editor = new RoutingEditor();
			
			ArrayList OpTypes = new ArrayList();
			String [] testOpTypes = {"8605728e", /*"8605ddf8", */"11111111"}; // OpSpec code item ouid for test
			for (int i = 0; i < testOpTypes.length; i++) {
				OpTypes.add(testOpTypes[i]);
			}
			
			editor.setContextObject(dosObj, 3, OpTypes);
			editor.show();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "@_@! Something goes wrong!\n"
					+ e.toString());
		}
    }
    
}
