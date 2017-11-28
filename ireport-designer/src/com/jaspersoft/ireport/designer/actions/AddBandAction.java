package com.jaspersoft.ireport.designer.actions;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.outline.nodes.NullBand;
import com.jaspersoft.ireport.designer.outline.nodes.NullBandNode;
import com.jaspersoft.ireport.designer.undo.AddBandUndoableEdit;
import groovy.util.Node;
import net.sf.jasperreports.engine.JRGroup;
import net.sf.jasperreports.engine.JROrigin;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignGroup;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.util.HelpCtx;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.actions.CallableSystemAction;
import org.openide.util.actions.NodeAction;

public final class AddBandAction extends NodeAction {

    private static AddBandAction instance = null;
    
    public static synchronized AddBandAction getInstance()
    {
        if (instance == null)
        {
            instance = new AddBandAction();
        }
        
        return instance;
    }
    
    private AddBandAction()
    {
        super();
    }
    
    
    public String getName() {
        return NbBundle.getMessage(AddBandAction.class, "CTL_AddBandAction");
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
            if (activatedNodes[i] instanceof NullBandNode)
            {
                NullBandNode nbn = (NullBandNode)activatedNodes[i];
                JasperDesign jd = nbn.getLookup().lookup(JasperDesign.class);
                NullBand band = nbn.getLookup().lookup(NullBand.class);
                
                if (jd != null && band !=null)
                {
                    JRDesignBand b = new JRDesignBand();
                    b.setHeight(50);
                    if (band.getOrigin().getBandType() == JROrigin.BACKGROUND) jd.setBackground(b);
                    else if (band.getOrigin().getBandType() == JROrigin.TITLE) jd.setTitle(b);
                    else if (band.getOrigin().getBandType() == JROrigin.PAGE_HEADER) jd.setPageHeader(b);
                    else if (band.getOrigin().getBandType() == JROrigin.COLUMN_HEADER) jd.setColumnHeader(b);
                    else if (band.getOrigin().getBandType() == JROrigin.DETAIL) jd.setDetail(b);
                    else if (band.getOrigin().getBandType() == JROrigin.COLUMN_FOOTER) jd.setColumnFooter(b);
                    else if (band.getOrigin().getBandType() == JROrigin.PAGE_FOOTER) jd.setPageFooter(b);
                    else if (band.getOrigin().getBandType() == JROrigin.LAST_PAGE_FOOTER) jd.setLastPageFooter(b);
                    else if (band.getOrigin().getBandType() == JROrigin.SUMMARY) jd.setSummary(b);
                    else if (band.getOrigin().getBandType() == JROrigin.NO_DATA) jd.setNoData(b);
                    else if (band.getOrigin().getBandType() == JROrigin.GROUP_HEADER)
                    {
                        JRDesignGroup g = (JRDesignGroup)jd.getGroupsMap().get( band.getOrigin().getGroupName());
                        g.setGroupHeader(b);
                    }
                    else if (band.getOrigin().getBandType() == JROrigin.GROUP_FOOTER)
                    {
                        JRDesignGroup g = (JRDesignGroup)jd.getGroupsMap().get( band.getOrigin().getGroupName());
                        g.setGroupFooter(b);
                    }
                    
                    AddBandUndoableEdit edit = new AddBandUndoableEdit(b,jd);
                    IReportManager.getInstance().addUndoableEdit(edit);
                }
            }
            
        }
    }

    protected boolean enable(org.openide.nodes.Node[] activatedNodes) {
        if (activatedNodes == null || activatedNodes.length == 0) return false;
        for (int i=0; i<activatedNodes.length; ++i)
        {
            if (!(activatedNodes[i] instanceof NullBandNode)) return false;
        }
        return true;
    }
}