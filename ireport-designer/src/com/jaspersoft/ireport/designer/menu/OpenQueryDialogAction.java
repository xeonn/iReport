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

public final class OpenQueryDialogAction extends CallableSystemAction implements LookupListener {

    private final Lookup lkp;
    private final Lookup.Result <? extends ReportDesignerPanel> result;

    
    public void performAction() {
       
        Window pWin = Misc.getMainWindow();
        ReportQueryDialog rqd = null;
        if (pWin instanceof Dialog) rqd = new ReportQueryDialog((Dialog)pWin, true);
        else if (pWin instanceof Frame) rqd = new ReportQueryDialog((Frame)pWin, true);
        else rqd = new ReportQueryDialog((Dialog)null, true);

           
        rqd.setDataset( IReportManager.getInstance().getActiveReport().getMainDesignDataset() );
        rqd.setVisible(true);
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
    
    public OpenQueryDialogAction(){
            this (Utilities.actionsGlobalContext());
    }
    
    private OpenQueryDialogAction(Lookup lkp) {
        
        this.lkp = lkp;
        result = lkp.lookupResult(ReportDesignerPanel.class);
        result.addLookupListener(this);
        result.allItems();
        updateStatus();
    }
            
    public String getName() {
        return NbBundle.getMessage(OpenQueryDialogAction.class, "CTL_OpenQueryDialogAction");
    }

    @Override
    protected String iconResource() {
        return "com/jaspersoft/ireport/designer/resources/query-16.png";
    }

    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    protected boolean asynchronous() {
        return false;
    }
    
    
}