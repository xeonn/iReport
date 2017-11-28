/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.jasperserver.ui.nodes;

import com.jaspersoft.ireport.designer.outline.nodes.IRIndexedNode;
import com.jaspersoft.ireport.jasperserver.RepositoryFolder;
import com.jaspersoft.ireport.jasperserver.ui.actions.AddResourceAction;
import com.jaspersoft.ireport.jasperserver.ui.actions.DeleteAction;
import com.jaspersoft.ireport.jasperserver.ui.actions.DeleteServerAction;
import com.jaspersoft.ireport.jasperserver.ui.actions.ModifyServerAction;
import com.jaspersoft.ireport.jasperserver.ui.actions.NewServerAction;
import com.jaspersoft.ireport.jasperserver.ui.actions.PropertiesAction;
import com.jaspersoft.ireport.jasperserver.ui.actions.RefreshAction;
import com.jaspersoft.jasperserver.api.metadata.xml.domain.impl.ResourceDescriptor;
import java.awt.Image;
import javax.swing.Action;
import javax.swing.ImageIcon;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.actions.SystemAction;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;

/**
 *
 * @author gtoffoli
 */
public class FolderNode extends IRIndexedNode implements ResourceNode {

    private boolean root = false;
    private RepositoryFolder folder = null;
    private boolean loading = false;
    
    protected static final ImageIcon loadingIcon = new ImageIcon(FolderNode.class.getResource("/com/jaspersoft/ireport/jasperserver/res/waiting.png"));
    protected static final ImageIcon serverIcon =  new ImageIcon(FolderNode.class.getResource("/com/jaspersoft/ireport/jasperserver/res/server.png"));
    protected static final ImageIcon folderIcon =  new ImageIcon(FolderNode.class.getResource("/com/jaspersoft/ireport/jasperserver/res/folder.png"));
    
    public boolean hasCustomizer() {
        return false;
    }
    
    public FolderNode(RepositoryFolder folder, Lookup doLkp) {
        this(new FolderChildren(folder, doLkp), folder, doLkp, false);
    }
    
    public FolderNode(RepositoryFolder folder, Lookup doLkp, boolean root) {
        this(new FolderChildren(folder, doLkp), folder, doLkp, root);
    }

    public FolderNode(FolderChildren pc, RepositoryFolder folder, Lookup doLkp, boolean root) {
        super(pc, pc.getIndex(), new ProxyLookup(doLkp, Lookups.fixed(folder, folder.getServer())));
        this.root = root;
        this.folder = folder;
    }

    public boolean isRoot() {
        return root;
    }

    public void setRoot(boolean root) {
        this.root = root;
    }

    public RepositoryFolder getFolder() {
        return folder;
    }

    public void setFolder(RepositoryFolder folder) {
        this.folder = folder;
    }

    @Override
    public String getDisplayName() {
        return getFolder().getDescriptor().getLabel() + ( (isLoading()) ? " (Loading....)" : "");
    }
    
    @Override
    public Image getIcon(int arg0) {
    
        if (isLoading())
        {
            return loadingIcon.getImage();
        }
        
        if (isRoot())
        {
            return serverIcon.getImage();
        }
        else
        {
            return folderIcon.getImage();
        }
    }
    
    @Override
    public Image getOpenedIcon(int arg0) {
    
        if (isLoading())
        {
            return loadingIcon.getImage();
        }
        else if (isRoot())
        {
            return serverIcon.getImage();
        }
        return folderIcon.getImage();
    
    }
    
    

    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
        this.fireIconChange();
    }

    @Override
    public Action[] getActions(boolean arg0) {
        return new Action[]{
            SystemAction.get( AddResourceAction.class), null,
            SystemAction.get( DeleteAction.class), null,
            SystemAction.get( NewServerAction.class),
            SystemAction.get( ModifyServerAction.class),
            SystemAction.get( DeleteServerAction.class), null,
            SystemAction.get( RefreshAction.class), 
            SystemAction.get( PropertiesAction.class)
        };
    }

    
    public ResourceDescriptor getResourceDescriptor() {
        return getFolder().getDescriptor();
    }

    public RepositoryFolder getRepositoryObject() {
        return getFolder();
    }

    public void refreshChildrens(boolean reload) {
        Object children = getChildren();
        if (children instanceof FolderChildren)
        {
            ((FolderChildren)children).recalculateKeys(reload);
        }
        else if (children instanceof ServerChildren)
        {
            ((ServerChildren)children).recalculateKeys();
        }
    }
    
    public void updateDisplayName() {
        fireDisplayNameChange(null,null);
    }
    
}
