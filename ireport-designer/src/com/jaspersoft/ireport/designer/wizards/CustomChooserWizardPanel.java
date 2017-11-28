/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jaspersoft.ireport.designer.wizards;

import com.jaspersoft.ireport.designer.utils.Misc;
import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.openide.WizardDescriptor;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataFolder;
import org.openide.loaders.TemplateWizard;
import org.openide.util.Exceptions;
import org.openide.util.HelpCtx;

public class CustomChooserWizardPanel implements WizardDescriptor.Panel {

    private WizardDescriptor wizard;
    
    public CustomChooserWizardPanel(WizardDescriptor wizard)
    {
        this.wizard = wizard;
    }
    /**
     * The visual component that displays this panel. If you need to access the
     * component from this class, just use getComponent().
     */
    private CustomChooserVisualPanel component;

    // Get the visual component for the panel. In this template, the component
    // is kept separate. This can be more efficient: if the wizard is created
    // but never displayed, or not all panels are displayed, it is better to
    // create only those which really need to be visible.
    public Component getComponent() {
        if (component == null) {
            component = new CustomChooserVisualPanel(this);
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
        if (component == null) return false;
        
        try {
            component.validateForm();
            getWizard().putProperty("WizardPanel_errorMessage", null);
            return true;
        } catch (Exception ex)
        {
            getWizard().putProperty("WizardPanel_errorMessage", ex.getMessage());
        }
        return false;
    }

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
     

    // You can use a settings object to keep track of state. Normally the
    // settings object will be the WizardDescriptor, so you can use
    // WizardDescriptor.getProperty & putProperty to store information entered
    // by the user.
    public void readSettings(Object settings) {
    
        if (settings instanceof TemplateWizard)
        {
            try {
                if (((TemplateWizard) settings).getTargetFolder() != null)
                {
                    ((CustomChooserVisualPanel)getComponent()).setTargetDirectory( Misc.getDataFolderPath( ((TemplateWizard) settings).getTargetFolder()) );

                    String name = //((TemplateWizard) settings).getTargetName();
                            ((TemplateWizard) settings).getTemplate().getPrimaryFile().getNameExt();

                    if (name != null && name.toLowerCase().endsWith(".properties"))
                    {
                        for (int i=0; i<100; ++i)
                        {
                            String tmpName = "Bundle" + ((i>0) ? i+"" : "") ;
                            File f = new File( Misc.getDataFolderPath(  ((TemplateWizard) settings).getTargetFolder()), tmpName + ".properties");
                            if (f.exists()) continue;

                            component.setReportName(tmpName);
                            component.setExtension(".properties");
                            break;
                        }
                    }
                    else
                    {
                        // Look for the first available reportX.jrxml
                        for (int i=1; i<100; ++i)
                        {
                            // get the file extension...\
                            File f = new File( Misc.getDataFolderPath(  ((TemplateWizard) settings).getTargetFolder()), "report" + i + ".jrxml");
                            if (f.exists()) continue;

                            component.setReportName("report" + i);
                            component.setExtension(".jrxml");
                            break;
                        }
                    }
                }
                
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }

    public void storeSettings(Object settings) {
        
        ((WizardDescriptor)settings).putProperty("filename", component.getFileName() );
        ((WizardDescriptor)settings).putProperty("reportname", component.getReportName() );
        if (settings instanceof TemplateWizard)
        {
            File f = new File(component.getFileName());
            try {
            ((TemplateWizard)settings).setTargetFolder(DataFolder.findFolder( FileUtil.toFileObject(f.getParentFile())));
            ((TemplateWizard)settings).setTargetName(component.getReportName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public WizardDescriptor getWizard() {
        return wizard;
    }

    public void setWizard(WizardDescriptor wizard) {
        this.wizard = wizard;
    }
    
}

