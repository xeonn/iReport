/*
 * DocumentNavigatorPanel.java
 * 
 * Created on Aug 31, 2007, 11:03:06 AM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.palette;

import java.awt.datatransfer.DataFlavor;
import java.util.Properties;
import javax.swing.Action;
import org.netbeans.spi.palette.PaletteActions;
import org.netbeans.spi.palette.PaletteController;
import org.netbeans.spi.palette.PaletteFactory;
import org.openide.util.Lookup;

/**
 *
 * @author gtoffoli
 */
public class PaletteUtils {

    public static final DataFlavor PALETTE_ITEM_DATA_FLAVOR = new DataFlavor( PaletteItem.class, "IR_PaletteItem");
    
    public static PaletteController controller = null;
    public static PaletteController createPalette() {
        try {
            if (controller == null)
            { 
                controller = PaletteFactory.createPalette("ireport/palette", // NOI18N
            
                                                new PaletteActions() {
                public Action[] getCustomCategoryActions(Lookup lookup) {
                    return new Action[0];
                }
                public Action[] getCustomItemActions(Lookup lookup) {
                    return new Action[0];
                }
                public Action[] getCustomPaletteActions() {
                    return new Action[0];
                }
                public Action[] getImportActions() {
                    return new Action[0];
                }
                public Action getPreferredAction(Lookup lookup) {
                    return null; //TODO
                }
            });
          }
          return controller;

        }
        catch (Throwable ex) {
            
            ex.printStackTrace();
            //ErrorManager.getDefault().notify(ErrorManager.INFORMATIONAL, ex);
            return null;
        }
    }
}
