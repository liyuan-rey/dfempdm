/*
 * Created on 2004-11-10
 *
 */
package dyna.framework.client.dfempdm;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JComboBox;

import dyna.framework.service.dos.DOSChangeable;

/**
 * @author ภ๎ิจ
 *
 */
public class util {

    public static void CenterWindow(Component alternateOwner, Component centerComp) {
        Dimension ownerSize = (alternateOwner == null ?
                Toolkit.getDefaultToolkit().getScreenSize() :
                    alternateOwner.getSize());
        Dimension centerSize = centerComp.getSize();
        
        if (centerSize.height > ownerSize.height) {
            centerSize.height = ownerSize.height;
        }
        
        if (centerSize.width > ownerSize.width) {
            centerSize.width = ownerSize.width;
        }
        
        centerComp.setLocation((ownerSize.width - centerSize.width) / 2,
                (ownerSize.height - centerSize.height) / 2);
    }

    public static void refreshCodeComboBox(String codeName, JComboBox combo,
            String selItemId) throws Exception {
        combo.removeAllItems();
    
        DOSChangeable dosSqType = dyna.framework.client.DynaMOAD.dos.getCodeWithName(codeName);
        if (dosSqType != null) {
            String ouid = (String)dosSqType.get("ouid");
            ArrayList codeList = dyna.framework.client.DynaMOAD.dos.listCodeItem(ouid);
            
            int size = codeList.size();
            for (int i = 0; i < size; i++) {
                DOSChangeable codeItem = (DOSChangeable)codeList.get(i);
                if (codeItem == null)
                    continue;
                
                combo.addItem(new DOSObjectAdapter(codeItem, "%name% [%codeitemid%]"));
                if (selItemId != null && !selItemId.equals("")) {
                    if (selItemId.equals(codeItem.get("ouid")))
                        combo.setSelectedIndex(combo.getItemCount()-1);
                }
            }
        }
    }

}
