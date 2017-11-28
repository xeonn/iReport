/*
 * ReportElementPopupMenuProvider.java
 * 
 * Created on 4-dic-2007, 22.02.28
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.actions;

import com.jaspersoft.ireport.designer.outline.OutlineTopComponent;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.JPopupMenu;
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
public class ReportPopupMenuProvider implements PopupMenuProvider {

    public JPopupMenu getPopupMenu(Widget widget, Point localLocation) {
        
        Node[] nodes = null;
        
        try {
            nodes = OutlineTopComponent.getDefault().getExplorerManager().getSelectedNodes();
        } catch (Exception ex) { }
       
        if (nodes != null && nodes.length > 0)
        {
            //return NodeOp.findContextMenu(nodes);
            Action[] actions = NodeOp.findActions(nodes);
            if (actions != null && actions.length > 0)
            {
                ActionMap actionsMap = new ActionMap();
                for (int i=0; i<actions.length; ++i)
                {
                    if (actions[i] != null && actions[i].getValue( Action.ACTION_COMMAND_KEY) != null)
                        actionsMap.put( actions[i].getValue( Action.ACTION_COMMAND_KEY), actions[i]);
                }
                
                List<Lookup> allLookups = new ArrayList<Lookup>();
                for (int i=0; i<nodes.length; ++i)
                {
                    allLookups.add(nodes[i].getLookup());
                }
                allLookups.add(Utilities.actionsGlobalContext());
                allLookups.add(Lookups.singleton(actionsMap));
                Lookup lookup = new ProxyLookup(allLookups.toArray(new Lookup[allLookups.size()]));
                return Utilities.actionsToPopup(actions, lookup);
            }
        
        }
        
        return null;
    }
}
