package com.jaspersoft.ireport.jasperserver.ui.actions;

import com.jaspersoft.ireport.jasperserver.ui.nodes.ResourceNode;
import com.jaspersoft.jasperserver.api.metadata.xml.domain.impl.ResourceDescriptor;
import javax.swing.JMenuItem;
import org.openide.awt.Actions;
import org.openide.util.NbBundle;

public final class AddInputControlAction extends AddResourceAction {

    public String getName() {
        return NbBundle.getMessage(AddInputControlAction.class, "CTL_AddInputControlAction");
    }

    @Override
    public JMenuItem getPopupPresenter() {
        
        return new Actions.MenuItem(this, true);

    }
    
   
    protected void performAction(org.openide.nodes.Node[] activatedNodes) {
            addResource( ResourceDescriptor.TYPE_INPUT_CONTROL);
    }

    protected boolean enable(org.openide.nodes.Node[] activatedNodes) {
        if (activatedNodes == null || activatedNodes.length != 1) return false;
        if ( activatedNodes[0] instanceof ResourceNode)
        {
            return true;
        }
        return false;
    }

   
}