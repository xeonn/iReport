/*
 * ChartDataAction.java
 * 
 * Created on 29-nov-2007, 16.53.52
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.menu;

import com.jaspersoft.ireport.designer.ModelUtils;
import com.jaspersoft.ireport.designer.editor.ExpressionContext;
import com.jaspersoft.ireport.designer.outline.nodes.ElementNode;
import com.jaspersoft.ireport.designer.tools.HyperlinkPanel;
import com.jaspersoft.ireport.designer.utils.Misc;
import net.sf.jasperreports.engine.JRHyperlink;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.util.HelpCtx;
import org.openide.util.actions.NodeAction;

/**
 *
 * @author gtoffoli
 */
public final class HyperlinkAction extends NodeAction {

    private static HyperlinkAction instance = null;
    
    public static synchronized HyperlinkAction getInstance()
    {
        if (instance == null)
        {
            instance = new HyperlinkAction();
        }
        
        return instance;
    }
    
    private HyperlinkAction()
    {
        super();
    }
    
    
    public String getName() {
        return "Hyperlink";
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
        
        
        JRHyperlink hyperlink = (JRHyperlink) ((ElementNode)activatedNodes[0]).getElement();
        JasperDesign design = ((ElementNode)activatedNodes[0]).getJasperDesign();
                        
        HyperlinkPanel pd = new HyperlinkPanel();
        pd.setExpressionContext(new ExpressionContext( ModelUtils.getElementDataset(((ElementNode)activatedNodes[0]).getElement(), design)) );
        pd.setHyperlink(hyperlink);
        pd.showDialog( Misc.getMainFrame() );
        
    }

    protected boolean enable(org.openide.nodes.Node[] activatedNodes) {
        return (activatedNodes != null &&
                activatedNodes.length == 1 &&
                activatedNodes[0] instanceof ElementNode &&
                ((ElementNode)activatedNodes[0]).getElement() instanceof JRHyperlink);
    }
}
