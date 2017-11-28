/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jaspersoft.ireport.designer.jrtx;

import java.io.IOException;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObjectExistsException;
import org.openide.loaders.MultiDataObject;
import org.openide.loaders.UniFileLoader;
import org.openide.util.NbBundle;

public class JRTXDataLoader extends UniFileLoader {

    public static final String REQUIRED_MIME = "text/x-jrtx+xml";
    private static final long serialVersionUID = 1L;

    public JRTXDataLoader() {
        super("com.jaspersoft.ireport.designer.jrtx.JRTXDataObject");
    }

    @Override
    protected String defaultDisplayName() {
        return NbBundle.getMessage(JRTXDataLoader.class, "LBL_JRTX_loader_name");
    }

    @Override
    protected void initialize() {
        super.initialize();
        getExtensions().addMimeType(REQUIRED_MIME);
    }

    protected MultiDataObject createMultiObject(FileObject primaryFile) throws DataObjectExistsException, IOException {
        return new JRTXDataObject(primaryFile, this);
    }

    @Override
    protected String actionsContext() {
        return "Loaders/" + REQUIRED_MIME + "/Actions";
    }
}