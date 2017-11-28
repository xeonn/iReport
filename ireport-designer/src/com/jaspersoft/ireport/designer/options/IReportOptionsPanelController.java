/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jaspersoft.ireport.designer.options;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.swing.JComponent;
import org.netbeans.spi.options.AdvancedOption;
import org.netbeans.spi.options.OptionsPanelController;
import org.openide.util.HelpCtx;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;

final public class IReportOptionsPanelController extends OptionsPanelController {

    private IReportPanel panel;
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private boolean changed;
    private List<OptionsPanelController> delegatedControllers = new ArrayList<OptionsPanelController>();
    private List<AdvancedOption> advancedOptions = new ArrayList<AdvancedOption>();

    public IReportOptionsPanelController()
    {
        Lookup lookup = Lookups.forPath("OptionsDialog/iReport"); // NOI18N
        Collection<? extends AdvancedOption> subTabs = lookup.lookupAll(AdvancedOption.class);
        Iterator<? extends AdvancedOption> it = subTabs.iterator();
        while (it.hasNext ()) {

            AdvancedOption option = it.next();
            advancedOptions.add(option);
            OptionsPanelController opc = option.create();
            delegatedControllers.add(opc);
            JComponent c = opc.getComponent(null);
            getPanel().addTab( option.getDisplayName() , c);
        }
    }

    public void update() {
        getPanel().load();
        for (OptionsPanelController opc : delegatedControllers)
        {
            opc.update();
        }

        changed = false;
    }

    public void applyChanges() {
        getPanel().store();
        for (OptionsPanelController opc : delegatedControllers)
        {
            opc.applyChanges();
        }
        changed = false;
    }

    public void cancel() {
        for (OptionsPanelController opc : delegatedControllers)
        {
            opc.cancel();
        }
    }

    public boolean isValid() {
        for (OptionsPanelController opc : delegatedControllers)
        {
            if (!opc.isValid()) return false;
        }
        return getPanel().valid();
    }

    public boolean isChanged() {
        return changed;
    }

    public HelpCtx getHelpCtx() {
        return null; // new HelpCtx("...ID") if you have a help set
    }

    public JComponent getComponent(Lookup masterLookup) {
        return getPanel();
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        pcs.addPropertyChangeListener(l);
        for (OptionsPanelController opc : delegatedControllers)
        {
            opc.addPropertyChangeListener(l);
        }
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        pcs.removePropertyChangeListener(l);
        for (OptionsPanelController opc : delegatedControllers)
        {
            opc.removePropertyChangeListener(l);
        }
    }

    private IReportPanel getPanel() {
        if (panel == null) {
            panel = new IReportPanel(this);
        }
        return panel;
    }

    public void changed() {
        if (!changed) {
            changed = true;
            pcs.firePropertyChange(OptionsPanelController.PROP_CHANGED, false, true);
        }
        pcs.firePropertyChange(OptionsPanelController.PROP_VALID, null, null);
    }
}
