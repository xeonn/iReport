/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jaspersoft.ireport.designer.wizards;

import java.awt.Component;
import javax.swing.event.ChangeListener;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;

public class FieldsSelectionWizardPanel implements WizardDescriptor.Panel {

    private WizardDescriptor wizard;
    
    public FieldsSelectionWizardPanel(WizardDescriptor wizard)
    {
        this.wizard = wizard;
    }
    
    /**
     * The visual component that displays this panel. If you need to access the
     * component from this class, just use getComponent().
     */
    private FieldsSelectionVisualPanel component;

    // Get the visual component for the panel. In this template, the component
    // is kept separate. This can be more efficient: if the wizard is created
    // but never displayed, or not all panels are displayed, it is better to
    // create only those which really need to be visible.
    public Component getComponent() {
        if (component == null) {
            component = new FieldsSelectionVisualPanel(this);
        }
        return component;
    }

    public HelpCtx getHelp() {
        // Show no Help button for this panel:
        return HelpCtx.DEFAULT_HELP;
    // If you have context help:
    // return new HelpCtx(SampleWizardPanel1.class);
    }

    public boolean isValid() {
        // If it is always OK to press Next or Finish, then:
        return true;
    // If it depends on some condition (form filled out...), then:
    // return someCondition();
    // and when this condition changes (last form field filled in...) then:
    // fireChangeEvent();
    // and uncomment the complicated stuff below.
    }

    public final void addChangeListener(ChangeListener l) {
    }

    public final void removeChangeListener(ChangeListener l) {
    }
    /*
    private final Set<ChangeListener> listeners = new HashSet<ChangeListener>(1); // or can use ChangeSupport in NB 6.0
    public final void addChangeListener(ChangeListener l) {
    synchronized (listeners) {
    listeners.add(l);
    }
    }
    public final void removeChangeListener(ChangeListener l) {
    synchronized (listeners) {
    listeners.remove(l);
    }
    }
    protected final void fireChangeEvent() {
    Iterator<ChangeListener> it;
    synchronized (listeners) {
    it = new HashSet<ChangeListener>(listeners).iterator();
    }
    ChangeEvent ev = new ChangeEvent(this);
    while (it.hasNext()) {
    it.next().stateChanged(ev);
    }
    }
     */

    // You can use a settings object to keep track of state. Normally the
    // settings object will be the WizardDescriptor, so you can use
    // WizardDescriptor.getProperty & putProperty to store information entered
    // by the user.
    public void readSettings(Object settings) {
        
        if (component == null) getComponent(); 
        
        if (getWizard().getProperty("discoveredFieldsNeedRefresh") != null &&
            getWizard().getProperty("discoveredFieldsNeedRefresh").equals("true"))
        {
           component.updateLists();
           getWizard().putProperty("discoveredFieldsNeedRefresh", null);
        }
        component.setSelectionChanged(false);
    }

    public void storeSettings(Object settings) {
        
        // Check if the list is changed...
        if (component.isSelectionChanged())
        {
            getWizard().putProperty("selectedFields", component.getSelectedFields());
            getWizard().putProperty("selectedFieldsNeedRefresh", "true");
        }
        
        
        
    }

    public WizardDescriptor getWizard() {
        return wizard;
    }

    public void setWizard(WizardDescriptor wizard) {
        this.wizard = wizard;
    }
    
}

