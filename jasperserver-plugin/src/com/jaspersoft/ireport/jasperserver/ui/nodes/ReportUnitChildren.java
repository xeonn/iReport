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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;
import org.openide.nodes.Index;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.Mutex;

/**
 *
 * @author gtoffoli
 */
public class ReportUnitChildren extends Index.KeysChildren implements PropertyChangeListener {

    private RepositoryReportUnit reportUnit = null;
    private Lookup doLkp = null;
    private ReportUnitInputControlsNode controlsNode = null;
    private ReportUnitResourcesNode resourcesNode = null;
            
       
    public ReportUnitChildren(RepositoryReportUnit reportUnit, Lookup doLkp) {
        super(new ArrayList());
        this.reportUnit = reportUnit;
        this.doLkp = doLkp;
        controlsNode = new ReportUnitInputControlsNode(getReportUnit(), doLkp);
        resourcesNode = new ReportUnitResourcesNode(getReportUnit(), doLkp);
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
        else if (key instanceof Node)
        {
            return new Node[]{(Node)key};
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
    
    public void recalculateKeys(final boolean reload) {
        final List l = (List)lock();
        l.clear();
        
        // We dived here connection, main jrxml, input controls and resources 
        
        Runnable run = new Runnable() {

            public void run() {
                
                SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                       ((ReportUnitNode)getNode()).setLoading(true);
                    }
                });
                
                List children = getReportUnit().getChildren(reload);
                
                boolean mainReportFound = false;
                
                for (int i=0; i<children.size(); ++i)
                {
                    
                    RepositoryFolder item = (RepositoryFolder)children.get(i);
                    
                    if (item.isDataSource()) continue;
                    if (item.getDescriptor().isMainReport())
                    {
                        if (!mainReportFound) // This check is a fix for a WS bug that sends this descriptor twice
                        {
                            l.add(item);
                        }
                        mainReportFound = true;
                    }
                }
                l.add(controlsNode);
                l.add(resourcesNode);
                    
                SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                       update();
                       ((InputControlsChildren)controlsNode.getChildren()).recalculateKeys();
                       ((ResourcesChildren)resourcesNode.getChildren()).recalculateKeys();
                       ((ReportUnitNode)getNode()).setLoading(false);
                    }
                });
            }
        };
        
        Thread t = new Thread(run);
        t.start();
    }
    
    @SuppressWarnings("unchecked")
    public void reorder() { 
            Mutex.Action action = new Mutex.Action(){ 
                public Object run(){ 
                    Index.Support.showIndexedCustomizer(ReportUnitChildren.this.getIndex()); 
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
