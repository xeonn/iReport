/*
 * ActionTransferable.java
 * 
 * Created on 14-nov-2007, 0.24.14
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.dnd;

import com.jaspersoft.ireport.designer.palette.PaletteItem;
import com.jaspersoft.ireport.designer.palette.PaletteItemAction;
import com.jaspersoft.ireport.designer.palette.PaletteUtils;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Properties;
import org.openide.util.datatransfer.ExTransferable;

/**
 *
 * @author gtoffoli
 */
public class ReportObjectPaletteTransferable extends ExTransferable.Single {

    private PaletteItem paletteItem = null;
    
    /**
     * Action is a PaletteItemAction class name
     * paletteData is accessor data that can be passed to this palette item...
     */
    public ReportObjectPaletteTransferable(String action, Object paletteData)
    {
        super( PaletteUtils.PALETTE_ITEM_DATA_FLAVOR);
        
        Properties properties = new Properties();
        properties.setProperty( PaletteItem.ACTION, action);
        paletteItem = new PaletteItem(properties);
        paletteItem.setData(paletteData);
        
    }
    
    protected Object getData() throws IOException, UnsupportedFlavorException {
        return paletteItem;
    }

    
}
