/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jaspersoft.ireport.jasper;

import java.io.IOException;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObjectExistsException;
import org.openide.loaders.MultiDataObject;
import org.openide.loaders.UniFileLoader;
import org.openide.util.NbBundle;

public class JasperDataLoader extends UniFileLoader {

    public static final String REQUIRED_MIME = "application/x-jasper";
    private static final long serialVersionUID = 1L;

    public JasperDataLoader() {
        super("com.jaspersoft.ireport.jasper.JasperDataObject");
    }

    @Override
    protected String defaultDisplayName() {
        return NbBundle.getMessage(JasperDataLoader.class, "LBL_Jasper_loader_name");
    }

    @Override
    protected void initialize() {
        super.initialize();
        getExtensions().addMimeType(REQUIRED_MIME);
    }

    protected MultiDataObject createMultiObject(FileObject primaryFile) throws DataObjectExistsException, IOException {
        return new JasperDataObject(primaryFile, this);
    }

    @Override
    protected String actionsContext() {
        return "Loaders/" + REQUIRED_MIME + "/Actions";
    }
}
