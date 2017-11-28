package com.jaspersoft.ireport;

import java.io.IOException;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObjectExistsException;
import org.openide.loaders.MultiDataObject;
import org.openide.loaders.UniFileLoader;
import org.openide.util.NbBundle;

public class JrxmlDataLoader extends UniFileLoader {

    public static final String REQUIRED_MIME = "text/x-jrxml+xml";
    private static final long serialVersionUID = 1L;

    public JrxmlDataLoader() {
        super("com.jaspersoft.ireport.JrxmlDataObject");
    }

    @Override
    protected String defaultDisplayName() {
        return NbBundle.getMessage(JrxmlDataLoader.class, "LBL_Jrxml_loader_name");
    }

    @Override
    protected void initialize() {
        super.initialize();
        getExtensions().addMimeType(REQUIRED_MIME);
    }

    protected MultiDataObject createMultiObject(FileObject primaryFile) throws DataObjectExistsException, IOException {
        return new JrxmlDataObject(primaryFile, this);
    }

    @Override
    protected String actionsContext() {
        return "Loaders/" + REQUIRED_MIME + "/Actions";
    }
}