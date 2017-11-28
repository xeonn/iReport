/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.jasperserver.ui.nodes;

import com.jaspersoft.ireport.designer.outline.nodes.IRIndexedNode;
import com.jaspersoft.ireport.jasperserver.RepositoryFolder;
import com.jaspersoft.ireport.jasperserver.RepositoryReportUnit;
import com.jaspersoft.ireport.jasperserver.ui.actions.AddResourceAction;
import com.jaspersoft.ireport.jasperserver.ui.actions.RunReportUnitAction;
import com.jaspersoft.ireport.jasperserver.ui.actions.RunReportUnitCookieImpl;
import com.jaspersoft.jasperserver.api.metadata.xml.domain.impl.ResourceDescriptor;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Action;
import org.openide.util.Lookup;
import org.openide.util.actions.SystemAction;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;

/**
 *
 * @author gtoffoli
 */
public class ReportUnitResourcesNode extends IRIndexedNode implements ResourceNode {

    private Lookup doLkp;
    private RepositoryReportUnit reportUnit;
            
    
    public ReportUnitResourcesNode(RepositoryReportUnit reportUnit, Lookup doLkp) {
        this(new ResourcesChildren(reportUnit, doLkp), reportUnit, doLkp);
    }
    
    public ReportUnitResourcesNode(ResourcesChildren pc, RepositoryReportUnit reportUnit, Lookup doLkp) {
        super(pc, pc.getIndex(), new ProxyLookup(doLkp, Lookups.fixed(new RunReportUnitCookieImpl(), reportUnit, reportUnit.getServer())));
        this.reportUnit = reportUnit;
        setDisplayName("Resources");
        setIconBaseWithExtension("/com/jaspersoft/ireport/jasperserver/res/folder.png");
        getLookup().lookup(RunReportUnitCookieImpl.class).setNode(this);
    }

    public RepositoryReportUnit getReportUnit() {
        return reportUnit;
    }

    public void setReportUnit(RepositoryReportUnit reportUnit) {
        this.reportUnit = reportUnit;
    }
    
       @Override
    public Action[] getActions(boolean b) {

        List<Action> actions = new ArrayList<Action>();

        actions.add( SystemAction.get(AddResourceAction.class));
        actions.add(null);
        actions.add( SystemAction.get(RunReportUnitAction.class));

//        if (getParentNode() != null)
//        {
//            Action[] parentActions = getParentNode().getActions(b);
//            for (int i=0; i<parentActions.length; ++i)
//            {
//                actions.add(  parentActions[i] );
//            }
//        }

        return actions.toArray(new Action[actions.size()]);
    }
    
    public ResourceDescriptor getResourceDescriptor() {
        return getReportUnit().getDescriptor();
    }

    public RepositoryFolder getRepositoryObject() {
        return getReportUnit();
    }

    public void refreshChildrens(boolean reload) {
        if (getParentNode() != null &&
            getParentNode() instanceof ReportUnitNode)
        {
            ((ReportUnitNode)getParentNode()).refreshChildrens(reload);
        }
    }
    
    public void updateDisplayName() {
        //fireDisplayNameChange(null,null);
        if (getParentNode() != null &&
            getParentNode() instanceof ReportUnitNode)
        {
            ((ReportUnitNode)getParentNode()).updateDisplayName();
        }
        
    }
}
