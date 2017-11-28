/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.jasperserver.ui.nodes;

import com.jaspersoft.ireport.designer.outline.nodes.IRIndexedNode;
import com.jaspersoft.ireport.jasperserver.RepositoryFolder;
import com.jaspersoft.ireport.jasperserver.RepositoryReportUnit;
import com.jaspersoft.ireport.jasperserver.ui.actions.RunReportUnitCookieImpl;
import com.jaspersoft.jasperserver.api.metadata.xml.domain.impl.ResourceDescriptor;
import javax.swing.Action;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;

/**
 *
 * @author gtoffoli
 */
public class ReportUnitInputControlsNode extends IRIndexedNode implements ResourceNode {

    private Lookup doLkp;
    private RepositoryReportUnit reportUnit;
            
    
    public ReportUnitInputControlsNode(RepositoryReportUnit reportUnit, Lookup doLkp) {
        this(new InputControlsChildren(reportUnit, doLkp), reportUnit, doLkp);
    }
    
    public ReportUnitInputControlsNode(InputControlsChildren pc, RepositoryReportUnit reportUnit, Lookup doLkp) {
        super(pc, pc.getIndex(), new ProxyLookup(doLkp, Lookups.fixed(new RunReportUnitCookieImpl(), reportUnit, reportUnit.getServer())));
        this.reportUnit = reportUnit;
        setDisplayName("Input controls");
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
    public Action[] getActions(boolean arg0) {
        if (getParentNode() != null)
        {
            return getParentNode().getActions(arg0);
        }
        
        return new Action[]{
        };
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
        fireDisplayNameChange(null,null);
    }
    
    
}
