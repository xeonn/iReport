package com.jaspersoft.ireport;

import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataNode;
import org.openide.nodes.Children;
import org.openide.util.Lookup;

public class JrxmlDataNode extends DataNode {

    private static final String IMAGE_ICON_BASE = "com/jaspersoft/ireport/designer/resources/report-16.png";

    private final ChangeCallback callback = null;
    
    public static interface ChangeCallback {
        public void modelChanged(JasperDesign model)  throws IllegalArgumentException;
    }
    
    public JrxmlDataNode(JrxmlDataObject obj) {
        super(obj, Children.LEAF);
        setIconBaseWithExtension(IMAGE_ICON_BASE);
        setShortDescription(  FileUtil.toFile( obj.getPrimaryFile() ) +"" );
    }

    JrxmlDataNode(JrxmlDataObject obj, Lookup lookup) {
        super(obj, Children.LEAF, lookup);
        setIconBaseWithExtension(IMAGE_ICON_BASE);
        setShortDescription(  FileUtil.toFile( obj.getPrimaryFile() ) +"" );
    }

       
}