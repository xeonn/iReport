package com.jaspersoft.ireport.designer.actions;

import com.jaspersoft.ireport.locale.I18n;
import com.jaspersoft.ireport.designer.outline.nodes.NullCell;
import com.jaspersoft.ireport.designer.outline.nodes.NullCellNode;
import net.sf.jasperreports.crosstabs.design.JRCrosstabOrigin;
import net.sf.jasperreports.crosstabs.design.JRDesignCellContents;
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstab;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.util.HelpCtx;
import org.openide.util.actions.NodeAction;

public final class AddCellAction extends NodeAction {

    private static AddCellAction instance = null;
    
    public static synchronized AddCellAction getInstance()
    {
        if (instance == null)
        {
            instance = new AddCellAction();
        }
        
        return instance;
    }
    
    private AddCellAction()
    {
        super();
    }
    
    
    public String getName() {
        return I18n.getString("AddCellAction.Name.CTL_AddCellAction");
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
        
        for (int i=0; i<activatedNodes.length; ++i)
        {
            if (activatedNodes[i] instanceof NullCellNode)
            {
                NullCellNode nbn = (NullCellNode)activatedNodes[i];
                JasperDesign jd = nbn.getLookup().lookup(JasperDesign.class);
                NullCell cell = nbn.getLookup().lookup(NullCell.class);
                JRDesignCrosstab crosstab = nbn.getLookup().lookup(JRDesignCrosstab.class);
                
                if (jd != null && cell !=null && crosstab !=null)
                {
                    JRDesignCellContents content = new JRDesignCellContents();
                    
                    if (cell.getOrigin().getType() == JRCrosstabOrigin.TYPE_HEADER_CELL)
                    {
                        crosstab.setHeaderCell(content);
                    }
                    
                    //AddBandUndoableEdit edit = new AddBandUndoableEdit(b,jd);
                    //IReportManager.getInstance().addUndoableEdit(edit);
                }
            }
            
        }
    }

    protected boolean enable(org.openide.nodes.Node[] activatedNodes) {
        if (activatedNodes == null || activatedNodes.length == 0) return false;
        for (int i=0; i<activatedNodes.length; ++i)
        {
            if (!(activatedNodes[i] instanceof NullCellNode)) return false;
        }
        return true;
    }
}