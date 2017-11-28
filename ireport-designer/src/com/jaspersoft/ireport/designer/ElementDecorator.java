/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer;

import org.netbeans.api.visual.widget.Widget;
import org.openide.nodes.Node;
import org.openide.util.actions.SystemAction;

/**
 *
 * @author gtoffoli
 */
public interface ElementDecorator {
    
    /**
     * Allow to paint extra stuff over the element.
     * Used only for elements.
     * @param w
     */
    public void paintWidget(Widget w);
    
    /**
     * Allow to specify extra menu items in the context of
     * the element node...
     * @return
     */
    public SystemAction[] getActions(Node node);

    /**
     * Return true if this decorator can be used with the given
     * design element...
     * @return
     */
    public boolean appliesTo(Object designElement);
}
