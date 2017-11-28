/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.jasperserver.ui.actions;

import com.jaspersoft.ireport.jasperserver.ui.nodes.FolderNode;
import com.jaspersoft.ireport.jasperserver.ui.nodes.ResourceNode;
import com.jaspersoft.jasperserver.api.metadata.xml.domain.impl.ResourceDescriptor;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.Collection;
import javax.swing.Action;
import javax.swing.JComponent;
import org.openide.util.ContextAwareAction;
import org.openide.util.HelpCtx;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;
import org.openide.util.actions.CallableSystemAction;
import org.openide.util.actions.SystemAction;

/**
 *
 * @author gtoffoli
 */
public class PublishReportUnitAction extends CallableSystemAction implements ContextAwareAction, LookupListener {

    public Action createContextAwareInstance(Lookup arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
    private final Lookup lkp;
    private final Lookup.Result <FolderNode> result;
    
    public PublishReportUnitAction() {
        this (Utilities.actionsGlobalContext());
    }
    
    
    private PublishReportUnitAction (Lookup lkp) {
        this.lkp = lkp;
        result = lkp.lookup(new Lookup.Template(FolderNode.class));
        result.addLookupListener(this);
        resultChanged (null);
    }

    public void resultChanged(LookupEvent arg0) {
        this.setEnabled( result.allInstances().size() > 0);
    }

    @Override
    public void performAction() {
        
        Collection<? extends FolderNode>nodes = result.allInstances();
        if (nodes.size() > 0)
        {
            ResourceNode selectedNode = (ResourceNode)nodes.iterator().next();
            SystemAction.get(AddResourceAction.class).addResource(selectedNode, ResourceDescriptor.TYPE_REPORTUNIT);
        }
    }

    public String getName() {
        return NbBundle.getMessage(PublishReportUnitAction.class, "CTL_PublishReportUnitAction");
    }

  
    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    protected boolean asynchronous() {
        return false;
    }

    @Override
    protected String iconResource() {
        return "com/jaspersoft/ireport/jasperserver/res/publish_report.png";
    }

    @Override
    protected void initialize() {
        super.initialize();
        putValue(Action.SHORT_DESCRIPTION, NbBundle.getMessage(PublishReportUnitAction.class, "CTL_PublishReportUnitAction"));
    
    }

 
}
