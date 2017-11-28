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
import org.openide.nodes.Index;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.Mutex;

/**
 *
 * @author gtoffoli
 */
public class InputControlsChildren extends Index.KeysChildren implements PropertyChangeListener {

    private RepositoryReportUnit reportUnit = null;
    private Lookup doLkp = null;
       
    public InputControlsChildren(RepositoryReportUnit reportUnit, Lookup doLkp) {
        super(new ArrayList());
        this.reportUnit = reportUnit;
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
        return new Node[]{};
    }
    
    
    
    @Override
    protected void addNotify() {
        super.addNotify();
        recalculateKeys();
    }
    
    
    @SuppressWarnings("unchecked")
    public void recalculateKeys() {
        
        final List l = (List)lock();
        l.clear();
        
        List children = getReportUnit().getChildren();
        for (int i=0; i<children.size(); ++i)
        {
            RepositoryFolder item = (RepositoryFolder)children.get(i);
            if (item.getDescriptor().getWsType().equals(ResourceDescriptor.TYPE_INPUT_CONTROL))
            {
                l.add(item);
            }
        }
        update();
    }
    
    
    @SuppressWarnings("unchecked")
    public void reorder() { 
            Mutex.Action action = new Mutex.Action(){ 
                public Object run(){ 
                    Index.Support.showIndexedCustomizer(InputControlsChildren.this.getIndex()); 
                    return null; 
                } 
            }; 
            MUTEX.writeAccess(action); 
        }

    public void propertyChange(PropertyChangeEvent evt) {
        
    }

    public RepositoryReportUnit getReportUnit() {
        return reportUnit;
    }

    public void setReportUnit(RepositoryReportUnit reportUnit) {
        this.reportUnit = reportUnit;
    }
}
