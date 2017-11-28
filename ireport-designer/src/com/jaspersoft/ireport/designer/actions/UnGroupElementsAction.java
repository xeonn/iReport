package com.jaspersoft.ireport.designer.actions;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.locale.I18n;
import com.jaspersoft.ireport.designer.outline.nodes.ElementNode;
import com.jaspersoft.ireport.designer.outline.nodes.ElementGroupNode;
import com.jaspersoft.ireport.designer.undo.AddElementGroupUndoableEdit;
import com.jaspersoft.ireport.designer.undo.AddElementUndoableEdit;
import com.jaspersoft.ireport.designer.undo.AggregatedUndoableEdit;
import com.jaspersoft.ireport.designer.undo.DeleteElementGroupUndoableEdit;
import com.jaspersoft.ireport.designer.undo.DeleteElementUndoableEdit;
import com.jaspersoft.ireport.designer.undo.RemoveElementGroupUndoableEdit;
import org.openide.util.HelpCtx;
import org.openide.util.actions.NodeAction;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.crosstabs.design.JRDesignCellContents;
import net.sf.jasperreports.engine.JRElementGroup;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JRDesignElementGroup;
import net.sf.jasperreports.engine.design.JRDesignFrame;


public final class UnGroupElementsAction extends NodeAction {

        
    public String getName() {
        return I18n.getString("UnGroupElementsAction.name");
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

        AggregatedUndoableEdit undoEdit = new AggregatedUndoableEdit();
        undoEdit.setPresentationName(getName());

        List<JRDesignElement> elements = new ArrayList<JRDesignElement>();
        List<JRDesignElementGroup> groups = new ArrayList<JRDesignElementGroup>();

        for (int i=0; i<activatedNodes.length; ++i)
        {
            JRDesignElementGroup parentElementGroup = ((ElementGroupNode)activatedNodes[i].getParentNode()).getElementGroup();

            if (activatedNodes[i] instanceof ElementNode)
            {
                JRDesignElement element = ((ElementNode)activatedNodes[i]).getElement();
                int index = parentElementGroup.getChildren().indexOf(element);
                parentElementGroup.removeElement(element);
                undoEdit.concatenate(new DeleteElementUndoableEdit(element, parentElementGroup, index));

                addElement(element, parentElementGroup.getElementGroup());
                undoEdit.concatenate(new AddElementUndoableEdit(element, parentElementGroup.getElementGroup()));
                elements.add(element);
            }
            else if (activatedNodes[i] instanceof ElementGroupNode)
            {
                JRDesignElementGroup element = ((ElementGroupNode)activatedNodes[i]).getElementGroup();
                int index = parentElementGroup.getChildren().indexOf(element);
                parentElementGroup.removeElementGroup(element);
                undoEdit.concatenate(new DeleteElementGroupUndoableEdit(element, parentElementGroup, index));

                addElementGroup(element, parentElementGroup.getElementGroup());
                undoEdit.concatenate(new AddElementGroupUndoableEdit(element, parentElementGroup.getElementGroup()));
                groups.add(element);
            }

            if (parentElementGroup.getChildren().size() == 0)
            {
                // remove the group from the parent...
                undoEdit.concatenate(new RemoveElementGroupUndoableEdit(parentElementGroup, parentElementGroup.getElementGroup()));
                removeElementGroup(parentElementGroup, parentElementGroup.getElementGroup());
                
            }
        }

        // Select all the group childrens...
        for (JRDesignElementGroup group : groups)
        {
            IReportManager.getInstance().addSelectedObject(group);
        }

        // Select all the group childrens...
        for (JRDesignElement element : elements)
        {
            IReportManager.getInstance().addSelectedObject(element);
        }

        IReportManager.getInstance().addUndoableEdit(undoEdit);
    }


    protected boolean enable(org.openide.nodes.Node[] activatedNodes) {

        if (activatedNodes.length == 0) return false;

        // All the nodes must have the same parent and must be all "close" to each other...
        for (int i=0; i<activatedNodes.length; ++i)
        {
            if (!((activatedNodes[i] instanceof ElementNode ||
                  activatedNodes[i] instanceof ElementGroupNode) &&
                  activatedNodes[i].getParentNode() instanceof ElementGroupNode)
                )
            {
                return false;
            }
        }
        return true;
    }

    private void addElement(JRDesignElement element, JRElementGroup elementGroup) {

        if (elementGroup instanceof JRDesignElementGroup)
        {
            ((JRDesignElementGroup)elementGroup).addElement(element);
        }
        else if (elementGroup instanceof JRDesignBand)
        {
            ((JRDesignBand)elementGroup).addElement(element);
        }
        else if (elementGroup instanceof JRDesignCellContents)
        {
            ((JRDesignCellContents)elementGroup).addElement(element);
        }
        else if (elementGroup instanceof JRDesignFrame)
        {
            ((JRDesignFrame)elementGroup).addElement(element);
        }
    }

    private void addElementGroup(JRDesignElementGroup element, JRElementGroup elementGroup) {

        if (elementGroup instanceof JRDesignElementGroup)
        {
            ((JRDesignElementGroup)elementGroup).addElementGroup(element);
        }
        else if (elementGroup instanceof JRDesignBand)
        {
            ((JRDesignBand)elementGroup).addElementGroup(element);
        }
        else if (elementGroup instanceof JRDesignCellContents)
        {
            ((JRDesignCellContents)elementGroup).addElementGroup(element);
        }
        else if (elementGroup instanceof JRDesignFrame)
        {
            ((JRDesignFrame)elementGroup).addElementGroup(element);
        }
    }

    private void removeElementGroup(JRDesignElementGroup element, JRElementGroup elementGroup) {

        if (elementGroup instanceof JRDesignElementGroup)
        {
            ((JRDesignElementGroup)elementGroup).removeElementGroup(element);
        }
        else if (elementGroup instanceof JRDesignBand)
        {
            ((JRDesignBand)elementGroup).removeElementGroup(element);
        }
        else if (elementGroup instanceof JRDesignCellContents)
        {
            ((JRDesignCellContents)elementGroup).removeElementGroup(element);
        }
        else if (elementGroup instanceof JRDesignFrame)
        {
            ((JRDesignFrame)elementGroup).removeElementGroup(element);
        }
    }
}