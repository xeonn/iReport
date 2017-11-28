/*
 * DnDUtilities.java
 * 
 * Created on Sep 14, 2007, 3:23:15 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.dnd;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

/**
 *
 * @author gtoffoli
 */
public class DnDUtilities {
    
    public static final int getTransferAction(Transferable t)
    {
        String subtype = "x-java-openide-nodednd"; // NOI18N
        String primary = "application"; // NOI18N
        String mask = "mask"; // NOI18N
        DataFlavor[] flavors = t.getTransferDataFlavors();
        for (int i = 0; i <flavors.length; i++) {
            DataFlavor df = flavors[i];

            if (df.getSubType().equals(subtype) && df.getPrimaryType().equals(primary)) {
                try {
                    int m = Integer.valueOf(df.getParameter(mask)).intValue();
                    return m;
                } catch (NumberFormatException nfe) {}
            }
        }
        return -1;
    }
}
