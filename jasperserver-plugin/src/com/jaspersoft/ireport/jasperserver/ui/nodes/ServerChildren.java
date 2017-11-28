/*
 * ReportNode.java
 * 
 * Created on Aug 31, 2007, 4:55:47 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.jasperserver.ui.nodes;

import com.jaspersoft.ireport.jasperserver.JServer;
import com.jaspersoft.ireport.jasperserver.JasperServerManager;
import com.jaspersoft.ireport.jasperserver.RepositoryFolder;
import com.jaspersoft.jasperserver.api.metadata.xml.domain.impl.ResourceDescriptor;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import org.openide.nodes.Index;
import org.openide.nodes.Node;
import org.openide.util.Mutex;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author gtoffoli
 */
public class ServerChildren extends Index.KeysChildren implements PropertyChangeListener {
       
    public ServerChildren() {
        super(new ArrayList());
    }

    /*
    @Override
    protected List<Node> initCollection() {
        return recalculateKeys();
    }
    */
    
    
    protected Node[] createNodes(Object key) {
        
        if (key instanceof JServer)
        {
            JServer server = (JServer)key;
            ResourceDescriptor rd = new ResourceDescriptor();
            rd.setWsType( ResourceDescriptor.TYPE_FOLDER);
            rd.setName("/");
            rd.setLabel(server.getName());
            rd.setUriString("/");
            
            RepositoryFolder folder = new RepositoryFolder(server,rd, true);
            FolderNode node = new FolderNode(folder, Lookups.singleton(server), true);
            node.setName(folder.getDescriptor().getLabel());

            return new Node[]{node};
        }
        
        return null;
    }
    
    
    
    @Override
    protected void addNotify() {
        super.addNotify();
        recalculateKeys();
    }
    
    
    @SuppressWarnings("unchecked")
    public void recalculateKeys() {
        
        List l = (List)lock();
        l.clear();
        l.addAll( JasperServerManager.getMainInstance().getJServers());
        update();
    }
    
    @SuppressWarnings("unchecked")
    public void reorder() { 
            Mutex.Action action = new Mutex.Action(){ 
                public Object run(){ 
                    Index.Support.showIndexedCustomizer(ServerChildren.this.getIndex()); 
                    return null; 
                } 
            }; 
            MUTEX.writeAccess(action); 
        }

    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName() == null) return;
    }
}
