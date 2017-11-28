package com.jaspersoft.ireport.designer.palette;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObjectExistsException;
import org.openide.loaders.MultiDataObject;
import org.openide.nodes.Node;
import org.openide.util.Lookup;

public class PaletteItemDataObject extends MultiDataObject {

    private PaletteItem paletteItem = null;
    
    public PaletteItemDataObject(FileObject pf, PaletteItemDataLoader loader) throws DataObjectExistsException, IOException {
        super(pf, loader);
        
        InputStream input = pf.getInputStream();
        Properties props = new Properties();
        props.load(input);
        input.close();
        paletteItem = new PaletteItem(props);
        
    }

    protected Node createNodeDelegate() {
        return new PaletteItemDataNode(this, paletteItem);
    }

    @Override
    public Lookup getLookup() {
        return getCookieSet().getLookup();
    }
}