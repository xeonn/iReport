/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.jasperserver.ui.actions;

import org.openide.nodes.Node;
import org.openide.util.actions.SystemAction;

/**
 *
 * @author gtoffoli
 */
public class RunReportUnitCookieImpl implements RunReportUnitCookie {

    private Node node = null;
    public RunReportUnitCookieImpl()
    {}
    
    public RunReportUnitCookieImpl(Node node)
    {
        this.node = node;
    }
    
    public void runReportUnit() {
                
       if (getNode() != null)
       {
           RunReportUnitAction action = SystemAction.get(RunReportUnitAction.class);
           action.runReportUnit(getNode());
       }
    }

    public

    Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }
        

}
