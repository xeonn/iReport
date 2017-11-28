package com.jaspersoft.ireport.designer.menu;

import com.jaspersoft.ireport.designer.IReportManager;
import java.io.File;
import java.io.IOException;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.filesystems.Repository;
import org.openide.loaders.DataFolder;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.loaders.DataShadow;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.NodeAction;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

public final class OpenReportDirectoryInFavoritesAction extends NodeAction {

    private static DataFolder getFavoritesFolder() {
        try {
            FileObject fo = FileUtil.createFolder (
                Repository.getDefault().getDefaultFileSystem().getRoot(),
                "Favorites" // NOI18N
            );
            DataFolder folder = DataFolder.findFolder(fo);
            return folder;
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
            return DataFolder.findFolder (
                Repository.getDefault().getDefaultFileSystem().getRoot()
            );
        }

    }

    private boolean isInFavorites (FileObject fo) {
            DataFolder f = getFavoritesFolder();

            DataObject [] arr = f.getChildren();
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] instanceof DataShadow) {
                    if (fo.equals(((DataShadow) arr[i]).getOriginal().getPrimaryFile())) {
                        return true;
                    }
                }
            }
            return false;
        }

    @Override
    protected void performAction(Node[] nodes) {
        try {

            File directory = FileUtil.toFile(IReportManager.getInstance().getActiveVisualView().getEditorSupport().getDataObject().getPrimaryFile());
            directory = directory.getParentFile();
            FileObject dirFO = FileUtil.toFileObject(directory);
            DataObject dirDO = DataObject.find(dirFO);
            // Add the directory to the favorites folder...
            if (!isInFavorites(dirFO))
            {
                DataShadow createdDO = dirDO.createShadow(getFavoritesFolder());
            }

            TopComponent win = WindowManager.getDefault().findTopComponent("favorites");
            if (win != null) {
                win.open();
                win.requestActive();
            }
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }

    }

    public String getName() {
        return NbBundle.getMessage(OpenReportDirectoryInFavoritesAction.class, "CTL_OpenReportDirectoryInFavoritesAction");
    }

    @Override
    protected void initialize() {
        super.initialize();
        // see org.openide.util.actions.SystemAction.iconResource() javadoc for more details
        putValue("noIconInMenu", Boolean.TRUE);
    }


    @Override
    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    protected boolean asynchronous() {
        return false;
    }

    @Override
    protected boolean enable(Node[] nodes) {
        return IReportManager.getInstance().getActiveVisualView() != null;
    }
}