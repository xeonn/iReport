package com.jaspersoft.ireport.designer.actions;

import com.jaspersoft.ireport.locale.I18n;
import com.jaspersoft.ireport.designer.outline.nodes.ElementGroupNode;
import com.jaspersoft.ireport.designer.outline.nodes.ElementNode;
import java.util.Arrays;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import net.sf.jasperreports.engine.JRElementGroup;
import net.sf.jasperreports.engine.design.JRDesignElement;
import org.openide.nodes.Children;
import org.openide.nodes.Index;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.Utilities;
import org.openide.util.actions.NodeAction;

public final class SendElementBackwardAction extends NodeAction {

    public String getName() {
        return I18n.getString("SendElementBackwardAction.Name");
    }

    @Override
    protected void initialize() {
        super.initialize();
        // see org.openide.util.actions.SystemAction.iconResource() javadoc for more details
        putValue("noIconInMenu", Boolean.FALSE);
    }

    @Override
    protected String iconResource() {
        return "com/jaspersoft/ireport/designer/resources/formatting/sendbackward.png";
    }

    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    protected boolean asynchronous() {
        return false;
    }

    protected void performAction(org.openide.nodes.Node[] activatedNodes) {

        // 1. order the selected elements by index.
        Node parent = activatedNodes[0].getParentNode();

        Children childrens = parent.getChildren();
        int[] perms = new int[childrens.getNodesCount()];
        for (int i=0; i<perms.length; ++i)
        {
            perms[i] = i;
        }

        int[] indexes = new int[activatedNodes.length];

        List allNodes = Arrays.asList(childrens.getNodes());
        for (int i=0; i<activatedNodes.length; ++i)
        {
            indexes[i] = allNodes.indexOf(activatedNodes[i]);
        }

        Arrays.sort(indexes);
        for (int i=0; i<indexes.length; ++i)
        {
            swap(perms, indexes[i]-1, indexes[i]);
        }


        ((Index.KeysChildren)parent.getChildren()).getIndex().reorder(perms);
/*
        JRElementGroup groupContainer = null;
        List objectsToMove = new ArrayList();

        for (int i=0; i<activatedNodes.length; ++i)
        {
            if (activatedNodes[0] instanceof ElementNode)
            {
                JRDesignElement element = ((ElementNode)activatedNodes[0]).getElement();
                if (groupContainer == null) groupContainer = element.getElementGroup();
                objectsToMove.add(element);
            }
            else if (activatedNodes[0] instanceof ElementGroupNode)
            {
                JRElementGroup element = ((ElementGroupNode)activatedNodes[0]).getElementGroup();
                if (groupContainer == null) groupContainer = element.getElementGroup();
                objectsToMove.add(element);
            }
        }

        List childrens = groupContainer.getChildren();
        while (objectsToMove.size() > 0)
        {
            int minIndex = -1;
            Object elementToMove = null;
            // 1. find the one with the minimum index in the list
            for (int i=0; i<objectsToMove.size(); ++i)
            {
                Object element = objectsToMove.get(i);
                int idx = childrens.indexOf(element);
                if (minIndex == -1 || minIndex > idx)
                {
                    minIndex = idx;
                    elementToMove = element;
                }
            }

            if (elementToMove != null && minIndex>0)  // Should be always not null...
            {
                childrens.remove(elementToMove);
                childrens.add(minIndex-1, elementToMove);
                objectsToMove.remove(elementToMove);
            }
            else
            {
                break; // some problem..?
            }
        }

        System.out.println("New Childrens: " + childrens);
        System.out.flush();
                
        if (groupContainer instanceof JRDesignElementGroup)
        {
            ((JRDesignElementGroup)groupContainer).getEventSupport().firePropertyChange( JRDesignElementGroup.PROPERTY_CHILDREN , null, null);
        }
        else if (groupContainer instanceof JRDesignCellContents)
        {
            ((JRDesignCellContents)groupContainer).getEventSupport().firePropertyChange( JRDesignCellContents.PROPERTY_CHILDREN , null, null);
        }

        // We should add an undo here...
        IReportManager.getInstance().notifyReportChange();
 */
    }

    public void swap(int[] perms, int a, int b)
    {
        int indexOfA = -1;
        int indexOfB = -1;

        // find the original node ad position a...
        for (int i=0; i<perms.length; ++i)
        {
            if (perms[i] == a)
            {
                indexOfA = i;
            }
            if (perms[i] == b)
            {
                indexOfB = b;
            }
            if (indexOfA != -1 && indexOfB != -1)
            {
                perms[indexOfA] = b;
                perms[indexOfB] = a;
                return;
            }
        }
        // unable to find one of the indexes...
    }

    protected boolean enable(org.openide.nodes.Node[] activatedNodes) {
        
        if (activatedNodes == null || activatedNodes.length == 0) return false;
        
        // Check if all the nodes are under the same parent...
        Node parent =  activatedNodes[0].getParentNode();
        
        if (parent == null) return false;


        
        for (int i=0; i<activatedNodes.length; ++i)
        {
            if (activatedNodes[i].getParentNode() != parent) 
            {
                return false;
            }
            
            if (activatedNodes[i] instanceof ElementNode)
            {
                JRDesignElement element = ((ElementNode)activatedNodes[i]).getElement();
                JRElementGroup group = element.getElementGroup();
                if (group.getChildren().indexOf(element) == 0) return false;
            }
            else if (activatedNodes[i] instanceof ElementGroupNode)
            {
                JRElementGroup element = ((ElementGroupNode)activatedNodes[i]).getElementGroup();
                JRElementGroup group = element.getElementGroup();
                if (group.getChildren().indexOf(element) == 0) return false;
            }
        }
        
        return true;
    }

    @Override
    public JMenuItem getPopupPresenter() {
        JMenuItem item = super.getPopupPresenter();
        item.setIcon(getIcon());
        return item;
    }

    
}