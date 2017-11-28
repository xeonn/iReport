/*
 * ReportElementPopupMenuProvider.java
 * 
 * Created on 4-dic-2007, 22.02.28
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.actions;

import com.jaspersoft.ireport.designer.AbstractReportObjectScene;
import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.outline.nodes.ElementNode;
import com.jaspersoft.ireport.designer.widgets.JRDesignElementWidget;
import com.jaspersoft.ireport.designer.widgets.SelectionWidget;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.JPopupMenu;
import net.sf.jasperreports.engine.design.JRDesignElement;
import org.netbeans.api.visual.action.PopupMenuProvider;
import org.netbeans.api.visual.widget.Widget;
import org.openide.nodes.Node;
import org.openide.nodes.NodeOp;
import org.openide.util.Lookup;
import org.openide.util.Utilities;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;

/**
 *
 * @author gtoffoli
 */
public class ReportElementPopupMenuProvider implements PopupMenuProvider {

    public JPopupMenu getPopupMenu(Widget widget, Point localLocation) {
        
        JRDesignElement element = getElement(widget);
        
        if (element == null) {
            return null;
        }
        
        if ( !((AbstractReportObjectScene)widget.getScene()).getSelectedObjects().contains(element) )
        {
            return null;
        }
        
        Node node = null;
        
        
            // We can assume that the node is selected in this explorer window...
            Node[] selectedNodes = IReportManager.getInstance().getActiveVisualView().getExplorerManager().getSelectedNodes();
            if (selectedNodes != null && selectedNodes.length > 0)
            {
                node = selectedNodes[0];
            }
            //node = IReportManager.getInstance().findNodeOf(element, IReportManager.getInstance().getActiveVisualView().getExplorerManager().getRootContext());
        
        if (selectedNodes != null && selectedNodes.length > 0)
        {
            Action[] actions = NodeOp.findActions(selectedNodes);
            if (actions != null && actions.length > 0)
            {
                ActionMap actionsMap = new ActionMap();
                for (int i=0; i<actions.length; ++i)
                {
                    if (actions[i] != null && actions[i].getValue( Action.ACTION_COMMAND_KEY) != null)
                        actionsMap.put( actions[i].getValue( Action.ACTION_COMMAND_KEY), actions[i]);
                }
                
                List<Lookup> allLookups = new ArrayList<Lookup>();
                allLookups.add(node.getLookup());
                allLookups.add(Utilities.actionsGlobalContext());
                allLookups.add(Lookups.singleton(actionsMap));
                Lookup lookup = new ProxyLookup(allLookups.toArray(new Lookup[allLookups.size()]));
                return Utilities.actionsToPopup(actions, lookup);
            }
            
            
        }
        
        
        return null;
    }
    
    private JRDesignElement getElement(Widget widget) {
        
        if (widget == null) return null;
        
        if (widget instanceof JRDesignElementWidget)
        {
            JRDesignElement element = ((JRDesignElementWidget)widget).getElement();
            return element;
        }
        else if (widget instanceof SelectionWidget)
        {
            return getElement( ((SelectionWidget)widget).getRealWidget() );
        }
        return null;
    }

}
