/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jaspersoft.ireport.jasperserver.ui.actions;

import javax.swing.Action;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CookieAction;

public final class RunReportUnitButton extends CookieAction {

    protected void performAction(Node[] activatedNodes) {
        RunReportUnitCookie runReportUnitCookie = activatedNodes[0].getLookup().lookup(RunReportUnitCookie.class);
    // TODO use runReportUnitCookie
    }


    protected int mode() {
        return CookieAction.MODE_EXACTLY_ONE;
    }

    public String getName() {
        return NbBundle.getMessage(RunReportUnitAction.class, "CTL_RunReportUnitAction");
    }

    protected Class[] cookieClasses() {
        return new Class[]{RunReportUnitCookie.class};
    }

    @Override
    protected String iconResource() {
        return "com/jaspersoft/ireport/jasperserver/res/run_report.png";
    }

    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    protected boolean asynchronous() {
        return false;
    }
    
    @Override
    protected void initialize() {
        super.initialize();
        putValue(Action.SHORT_DESCRIPTION, NbBundle.getMessage(RunReportUnitAction.class, "CTL_RunReportUnitAction"));
    
    }
}

