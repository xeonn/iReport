package com.jaspersoft.ireport.designer.palette;

import java.awt.Image;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.beans.BeanInfo;
import java.io.IOException;
import org.openide.loaders.DataNode;
import org.openide.nodes.Children;
import org.openide.util.datatransfer.ExTransferable;

public class PaletteItemDataNode extends DataNode {

    
    private PaletteItem paletteItem;
    
    public PaletteItemDataNode(PaletteItemDataObject obj, PaletteItem paletteItem) {
        super(obj, Children.LEAF);
        this.paletteItem = paletteItem;
        setName( paletteItem.getId() );
        setShortDescription( paletteItem.getComment());
    }
    
    public Image getIcon(int i) {
        if( i == BeanInfo.ICON_COLOR_16x16 ||
            i == BeanInfo.ICON_MONO_16x16 ) {
                return paletteItem.getSmallImage();
        }
        return paletteItem.getBigImage();
    }
    
    public String getDisplayName() {
        return paletteItem.getDisplayName();
    }

    @Override
    public Transferable drag() throws IOException {
        
        ExTransferable tras = ExTransferable.create(super.drag());
        tras.put(new ExTransferable.Single( PaletteUtils.PALETTE_ITEM_DATA_FLAVOR) {

            protected Object getData() throws IOException, UnsupportedFlavorException {
                return paletteItem;
            }
                
        }  );
        
        return tras;
    }
    
    
}