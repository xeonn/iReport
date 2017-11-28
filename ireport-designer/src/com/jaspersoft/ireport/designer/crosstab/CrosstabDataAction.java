/*
 * ChartDataAction.java
 * 
 * Created on 29-nov-2007, 16.53.52
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.crosstab;

import com.jaspersoft.ireport.designer.charts.*;
import com.jaspersoft.ireport.designer.outline.nodes.CrosstabNode;
import com.jaspersoft.ireport.designer.outline.nodes.ElementNode;
import com.jaspersoft.ireport.designer.utils.Misc;
import java.awt.Dialog;
import java.awt.Frame;
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstab;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.util.HelpCtx;
import org.openide.util.actions.NodeAction;

/**
 *
 * @author gtoffoli
 */
public final class CrosstabDataAction extends NodeAction {

    private CrosstabDataAction()
    {
        super();
    }
    
    
    public String getName() {
        return "Crosstab Data";
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
        
        JRDesignCrosstab crosstab = (JRDesignCrosstab)((CrosstabNode)activatedNodes[0]).getElement();
        JasperDesign design = ((ElementNode)activatedNodes[0]).getJasperDesign();
                     
        Object pWin = Misc.getMainWindow();
        CrosstabDataDialog pd = null;
        if (pWin instanceof Dialog) pd = new CrosstabDataDialog((Dialog)pWin,true);
        else pd = new CrosstabDataDialog((Frame)pWin,true);
        
        pd.setCrosstabElement(crosstab, design);
        pd.setVisible(true);
        
    }

    protected boolean enable(org.openide.nodes.Node[] activatedNodes) {
        return (activatedNodes != null &&
                activatedNodes.length == 1 &&
                activatedNodes[0] instanceof CrosstabNode);
    }
}
