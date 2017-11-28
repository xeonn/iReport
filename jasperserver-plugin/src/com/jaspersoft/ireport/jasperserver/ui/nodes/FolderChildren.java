/*
 * ReportNode.java
 * 
 * Created on Aug 31, 2007, 4:55:47 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.jasperserver.ui.nodes;

import com.jaspersoft.ireport.jasperserver.RepositoryFile;
import com.jaspersoft.ireport.jasperserver.RepositoryFolder;
import com.jaspersoft.ireport.jasperserver.RepositoryReportUnit;
import com.jaspersoft.jasperserver.api.metadata.xml.domain.impl.ResourceDescriptor;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Index;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.Mutex;
import sun.misc.Perf.GetPerfAction;

/**
 *
 * @author gtoffoli
 */
public class FolderChildren extends Index.KeysChildren implements PropertyChangeListener {

    private RepositoryFolder folder = null;
    private Lookup doLkp = null;
    private AbstractNode parentNode = null;
       
    public FolderChildren(RepositoryFolder folder, Lookup doLkp) {
        super(new ArrayList());
        this.folder = folder;
        this.doLkp = doLkp;
    }

    /*
    @Override
    protected List<Node> initCollection() {
        return recalculateKeys();
    }
    */
    
    
    protected Node[] createNodes(Object key) {
        
        if (key instanceof RepositoryFile)
        {
            return new Node[]{new FileNode((RepositoryFile)key, doLkp)};
        }
        if (key instanceof RepositoryReportUnit)
        {
            return new Node[]{new ReportUnitNode((RepositoryReportUnit)key, doLkp)};
        }
        else if (key instanceof RepositoryFolder)
        {
            return new Node[]{new FolderNode((RepositoryFolder)key, doLkp)};
        }
        return new Node[]{};
    }
    
    
    
    @Override
    protected void addNotify() {
        super.addNotify();
        recalculateKeys();
    }
    
    
    @SuppressWarnings("unchecked")
    public void recalculateKeys() {
        recalculateKeys(false);
    }
    
    @SuppressWarnings("unchecked")
    public void recalculateKeys(final boolean refresh) {
        
        final List l = (List)lock();
        l.clear();
        List params = null;
        
        Runnable run = new Runnable() {

            public void run() {
                
                SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                       ((FolderNode)getNode()).setLoading(true);
                    }
                });
                
                List children = folder.getChildren(true);
                if (children != null)
                {
                    l.addAll( children );
                    SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                           update();
                           ((FolderNode)getNode()).setLoading(false);
                        }
                    });
                }
                else
                {
                    folder.setLoaded(false);
                    ((FolderNode)getNode()).setLoading(false);
                }
            }
        };
        
        Thread t = new Thread(run);
        t.start();
    }
    
    @SuppressWarnings("unchecked")
    public void reorder() { 
            Mutex.Action action = new Mutex.Action(){ 
                public Object run(){ 
                    Index.Support.showIndexedCustomizer(FolderChildren.this.getIndex()); 
                    return null; 
                } 
            }; 
            MUTEX.writeAccess(action); 
        }

    public void propertyChange(PropertyChangeEvent evt) {
        
    }

    public RepositoryFolder getFolder() {
        return folder;
    }

    public void setFolder(RepositoryFolder folder) {
        this.folder = folder;
    }
}
