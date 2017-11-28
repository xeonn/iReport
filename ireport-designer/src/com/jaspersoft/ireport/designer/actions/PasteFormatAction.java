package com.jaspersoft.ireport.designer.actions;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.locale.I18n;
import com.jaspersoft.ireport.designer.outline.nodes.ElementNode;
import org.openide.util.HelpCtx;
import org.openide.util.actions.NodeAction;
import com.jaspersoft.ireport.designer.ModelUtils;
import com.jaspersoft.ireport.designer.sheet.properties.AbstractProperty;
import com.jaspersoft.ireport.designer.undo.PropertyUndoableEdit;
import org.openide.nodes.Node;
import org.openide.nodes.Node.Property;
import org.openide.nodes.Node.PropertySet;

public final class PasteFormatAction extends NodeAction {


    public String getName() {
        return I18n.getString("PasteFormatAction.name");
    }

    @Override
    protected void initialize() {
        super.initialize();
        // see org.openide.util.actions.SystemAction.iconResource() javadoc for more details
        putValue("noIconInMenu", Boolean.TRUE);
    }

    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    protected boolean asynchronous() {
        return false;
    }

    protected void performAction(org.openide.nodes.Node[] activatedNodes) {

         PropertyUndoableEdit undo = null;
        for (int k=0; k<activatedNodes.length; ++k)
        {
            Node node = activatedNodes[k];
            PropertySet[] sets = node.getPropertySets();
           
            for (int i=0; i<CopyFormatAction.propertyNames.length; ++i)
            {
                if (CopyFormatAction.formattingValues.containsKey(CopyFormatAction.propertyNames[i]))
                {
                    Property p = ModelUtils.findProperty(sets, CopyFormatAction.propertyNames[i]);
                    if (p != null)
                    {
                        Object oldValue;
                        try {
                            oldValue = p.getValue();

                            Object newValue = CopyFormatAction.formattingValues.get(CopyFormatAction.propertyNames[i]);
                            if (newValue != null)
                            {
                                p.setValue(newValue);
                                if (p instanceof AbstractProperty)
                                {
                                     if (undo == null)
                                     {
                                        undo = new PropertyUndoableEdit((AbstractProperty)p,oldValue,newValue);
                                     }
                                     else
                                     {
                                         undo.concatenate(new PropertyUndoableEdit((AbstractProperty)p,oldValue,newValue));
                                     }
                                }
                            }
                        } catch (Exception ex) {
                        }
                    }
                }
            }
        }
        if (undo != null)
        {
            IReportManager.getInstance().addUndoableEdit(undo);
        }
    }


    protected boolean enable(org.openide.nodes.Node[] activatedNodes) {
        if (CopyFormatAction.formattingValues.isEmpty()) return false;
        if (activatedNodes == null || activatedNodes.length < 1) return false;
        for (int i=0; i<activatedNodes.length; ++i)
        {
            if (!(activatedNodes[i] instanceof ElementNode)) return false;
        }
        return true;
    }
}