package com.jaspersoft.ireport.designer.menu;

import com.jaspersoft.ireport.designer.ReportDesignerPanel;
import java.util.Iterator;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import org.openide.util.HelpCtx;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;
import org.openide.util.actions.CallableSystemAction;
import org.openide.util.actions.Presenter;

public final class ShowGridAction extends CallableSystemAction
        implements Presenter.Menu, LookupListener {

    private static JCheckBoxMenuItem SHOW_GRID_MENU;
    private final Lookup lkp;
    private final Lookup.Result <? extends ReportDesignerPanel> result;

    public void performAction() {
        
       // reportDesignerPanel
       Iterator<? extends ReportDesignerPanel> i = result.allInstances().iterator();
       if (i.hasNext())
       {
         ReportDesignerPanel rdp = i.next();
         rdp.setGridVisible(SHOW_GRID_MENU.isSelected());
       }
       
       
    }
    
    public ShowGridAction(){
            this (Utilities.actionsGlobalContext());
    }
    
    private ShowGridAction(Lookup lkp) {
        
        
        SHOW_GRID_MENU  = new JCheckBoxMenuItem(getName());
        this.lkp = lkp;
        result = lkp.lookupResult(ReportDesignerPanel.class);
        result.addLookupListener(this);
        result.allItems();
        SHOW_GRID_MENU.addActionListener(this);
        setMenu();
    }
    
    public void resultChanged(LookupEvent e) {
        setMenu();
    }
    
    protected void setMenu(){
        
            Iterator<? extends ReportDesignerPanel> i = result.allInstances().iterator();
            SHOW_GRID_MENU.setEnabled(i.hasNext());
            if (i.hasNext())
            {
                ReportDesignerPanel rdp = i.next();
                SHOW_GRID_MENU.setSelected(rdp.isGridVisible());
            }
        }
    
    public String getName() {
        return NbBundle.getMessage(ShowGridAction.class, "CTL_ShowGridAction");
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
    
    public JMenuItem getMenuPresenter()
    {
        return SHOW_GRID_MENU;
    }
       
}