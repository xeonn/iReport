package com.jaspersoft.ireport.designer.menu;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.ReportDesignerPanel;
import com.jaspersoft.ireport.designer.data.ReportQueryDialog;
import com.jaspersoft.ireport.designer.utils.Misc;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.util.Iterator;
import org.openide.util.HelpCtx;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;
import org.openide.util.actions.CallableSystemAction;

public final class ZoomInAction extends CallableSystemAction implements LookupListener {

    private final Lookup lkp;
    private final Lookup.Result <? extends ReportDesignerPanel> result;

    
    public void performAction() {
       
        IReportManager.getInstance().getActiveVisualView().getReportDesignerPanel().zoomIn();
    }

    public void resultChanged(LookupEvent e) {
        
        updateStatus();
    }
    
    public void updateStatus()
    {
        Iterator<? extends ReportDesignerPanel> i = result.allInstances().iterator();
        if (i.hasNext())
        {
            setEnabled(true);
        }
        else
        {
            setEnabled(false);
        }
    }
    
    public ZoomInAction(){
            this (Utilities.actionsGlobalContext());
    }
    
    private ZoomInAction(Lookup lkp) {
        
        this.lkp = lkp;
        result = lkp.lookupResult(ReportDesignerPanel.class);
        result.addLookupListener(this);
        result.allItems();
        updateStatus();
    }
            
    public String getName() {
        return NbBundle.getMessage(ZoomInAction.class, "CTL_ZoomInAction");
    }

    @Override
    protected String iconResource() {
        return "com/jaspersoft/ireport/designer/resources/zoomin-16.png";
    }

    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    protected boolean asynchronous() {
        return false;
    }
    
    
}