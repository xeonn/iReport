package com.jaspersoft.ireport.designer.palette;

import com.jaspersoft.ireport.locale.I18n;
import java.io.IOException;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObjectExistsException;
import org.openide.loaders.MultiDataObject;
import org.openide.loaders.UniFileLoader;

public class PaletteItemDataLoader extends UniFileLoader {

    public static final String REQUIRED_MIME = "text/x-irpitem";
    private static final long serialVersionUID = 1L;

    public PaletteItemDataLoader() {
        super("com.jaspersoft.ireport.designer.palette.PaletteItemDataObject");
    }

    @Override
    protected String defaultDisplayName() {
        return I18n.getString("LBL_PaletteItem_loader_name");
    }

    @Override
    protected void initialize() {
        super.initialize();
        getExtensions().addMimeType(REQUIRED_MIME);
    }

    protected MultiDataObject createMultiObject(FileObject primaryFile) throws DataObjectExistsException, IOException {
        return new PaletteItemDataObject(primaryFile, this);
    }

    @Override
    protected String actionsContext() {
        return "Loaders/" + REQUIRED_MIME + "/Actions";
    }
}