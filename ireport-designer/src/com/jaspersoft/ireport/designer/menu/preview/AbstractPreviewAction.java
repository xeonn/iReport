/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.menu.preview;

import com.jaspersoft.ireport.designer.IReportManager;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import org.openide.util.HelpCtx;
import org.openide.util.actions.CallableSystemAction;

/**
 *
 * @author gtoffoli
 */
public abstract class AbstractPreviewAction extends CallableSystemAction implements PreferenceChangeListener, ItemListener {

    public abstract String getPreviewType();

    private JCheckBoxMenuItem item;
    
    public AbstractPreviewAction()
    {
        item = new JCheckBoxMenuItem(getName());
        IReportManager.getPreferences().addPreferenceChangeListener(this);
        preferenceChange(null);
        item.addItemListener(this);
    }
    
    
    @Override
    public JMenuItem getMenuPresenter() {
        
        return item;
    }
     
    
    public void performAction() {
        
        if (item.isSelected())
        {
            if (getPreviewType().length() > 0)
            {
                IReportManager.getPreferences().put("output_format", getPreviewType());
            }
            else
            {
                IReportManager.getPreferences().remove("output_format");
            }
        }
    }

    @Override
    protected void initialize() {
        super.initialize();
        
        // see org.openide.util.actions.SystemAction.iconResource() Javadoc for more details
        putValue("noIconInMenu", Boolean.TRUE);
        
    }

    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    protected boolean asynchronous() {
        return false;
    }
    
    public void preferenceChange(PreferenceChangeEvent evt)
    {
        item.setSelected(  IReportManager.getPreferences().get("output_format", "").equals(getPreviewType()) ); 
    }
    
    
    public void itemStateChanged(ItemEvent e)
    {
        performAction();
    }

}
