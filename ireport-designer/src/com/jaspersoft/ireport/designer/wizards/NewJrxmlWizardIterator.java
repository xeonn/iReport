/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jaspersoft.ireport.designer.wizards;

import com.jaspersoft.ireport.designer.templates.DefaultReportGenerator;
import com.jaspersoft.ireport.designer.utils.Misc;
import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.openide.WizardDescriptor;
import org.openide.cookies.OpenCookie;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataFolder;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.loaders.TemplateWizard;

public final class NewJrxmlWizardIterator implements TemplateWizard.Iterator {

    private int index;
    private WizardDescriptor wizard;
    private WizardDescriptor.Panel[] panels;

    /**
     * Initialize panels representing individual wizard's steps and sets
     * various properties for them influencing wizard appearance.
     */
    private WizardDescriptor.Panel[] getPanels() {
        
            if (panels == null) {
            
                // Check what targetChooserPanel to use.
                WizardDescriptor.Panel targetChooserPanel = null;
                boolean useCustomChooserPanel = false;
                
                if (wizard.getProperty("useCustomChooserPanel") != null &&
                    wizard.getProperty("useCustomChooserPanel").equals("true"))
                {
                    targetChooserPanel = new CustomChooserWizardPanel(wizard);
                }
                else
                {
                
                    targetChooserPanel = ((TemplateWizard) wizard).targetChooser();
                    try {
                        // Suggest a good name...
                        DataFolder folder = ((TemplateWizard) wizard).getTargetFolder();
                        String dir = Misc.getDataFolderPath(folder);

                        String fname = "report1.jrxml";
                        for (int i = 1; i < 1000; ++i) {
                            fname = "report" + i + ".jrxml";
                            File f = new File(dir, fname);
                            if (f.exists()) {
                                continue;
                            }
                            break;
                        }
                        ((TemplateWizard) wizard).setTargetName(fname);
                    } catch (IOException ex) {
                        //Exceptions.printStackTrace(ex);
                    }
                }
                
                panels = new WizardDescriptor.Panel[]{
                    targetChooserPanel,
                    new ConnectionSelectionWizardPanel(wizard),
                    new FieldsSelectionWizardPanel(wizard),
                    new GroupingWizardPanel(wizard),
                    new TemplateWizardPanel(wizard),
                    new NewJrxmlWizardPanel6(wizard)
                };
                
                String[] steps = createSteps();
                for (int i = 0; i < panels.length; i++) {
                    Component c = panels[i].getComponent();
                    steps[i] = c.getName();
                    
                    if (c instanceof JComponent) { // assume Swing components
                        JComponent jc = (JComponent) c;
                        // Sets step number of a component
                        jc.putClientProperty("WizardPanel_contentSelectedIndex", new Integer(i));
                        // Sets steps names for a panel
                        jc.putClientProperty("WizardPanel_contentData", steps);
                        // Turn on subtitle creation on each step
                        jc.putClientProperty("WizardPanel_autoWizardStyle", Boolean.TRUE);
                        // Show steps on the left side with the image on the background
                        jc.putClientProperty("WizardPanel_contentDisplayed", Boolean.TRUE);
                        // Turn on numbering of all steps
                        jc.putClientProperty("WizardPanel_contentNumbered", Boolean.TRUE);
                    }
                }
        }
        return panels;
    }

    private DataObject createdDataObject = null;
    
    public void setCreatedDataObject(DataObject obj)
    {
        createdDataObject = obj;
    }
    
    public DataObject getCreatedDataObject()
    {
        return createdDataObject;
    }
    
    
    public Set instantiate(TemplateWizard wizard) throws IOException {

        setCreatedDataObject(null);
        final Thread t = Thread.currentThread();
                
        Runnable r = new Runnable() {

            public void run() {

                try { 
                    
                    if (getCreatedDataObject() == null)
                    {
                       StackTraceElement[] ees = t.getStackTrace();
                       for (int i=0; i<ees.length; ++i)
                       {
                           Misc.msg(""+ees[i]);
                       }
                    }
                    
                } catch (Exception ex) {}
                
            }
        };
        
        Thread t2 = new Thread(r);
        t2.start();

        
        Logger.global.log(Level.INFO, "Instancing the report generator");
        DefaultReportGenerator reportGenerator = new DefaultReportGenerator();
        Logger.global.log(Level.INFO, "Report generator instanced");

        FileObject createdFile = reportGenerator.generateReport(wizard);

        Logger.global.log(Level.INFO, "Report generated");

        if (createdFile == null) {
            throw new IOException("Unable to create the report.");
        }

        try {
            setCreatedDataObject(DataObject.find(createdFile));
        } catch (DataObjectNotFoundException ex) {
            //Exceptions.printStackTrace(ex);
        }
            //DataFolder favoritesDataFolder = DataFolder.findFolder(favoritesFileObject);
            //createdDataObject.createShadow(favoritesDataFolder);
        
        if (getCreatedDataObject() != null) {
            return Collections.singleton(getCreatedDataObject());
        }
        else 
        {
            throw new IOException("Unable to create the report.");
        }
    }

    public void initialize(TemplateWizard wizard) {
        this.wizard = wizard;

        index = 0;
        setCreatedDataObject(null);
        getPanels();
        //fireChangeEvent();
    }

    public void uninitialize(TemplateWizard wizard) {
        
        if (getCreatedDataObject() != null)
        {
            Runnable r = new Runnable() {

                public void run() {
                    Logger.global.log(Level.INFO, "Opening report");
                    if (getCreatedDataObject() != null)
                    {
                        OpenCookie cookie = getCreatedDataObject().getCookie(OpenCookie.class);
                        if (cookie != null)
                        {
                            cookie.open();
                        }
                    }
                    Logger.global.log(Level.INFO, "Report opened...");
                }
            };
            
            SwingUtilities.invokeLater(r);
        }
        
        
        panels = null;
    }

    public WizardDescriptor.Panel current() {
        return getPanels()[index];
    }

    public String name() {
        return getPanels()[index].getComponent().getName();
    }

    public boolean hasNext() {
        return index < getPanels().length - 1;
    }

    public boolean hasPrevious() {
        return index > 0;
    }

    public void nextPanel() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        index++;
        updateStepsList();
     }

    public void previousPanel() {
        if (!hasPrevious()) {
            throw new NoSuchElementException();
        }
        index--;
        updateStepsList();
    }

    void updateStepsList() {
        final JComponent component = (JComponent) current().getComponent();
        if (component == null)
            return;
        String[] list = createSteps();
        component.putClientProperty("WizardPanel_contentData", list); // NOI18N
        component.putClientProperty("WizardPanel_contentSelectedIndex", new Integer(index)); // NOI18N
        component.firePropertyChange("WizardPanel_contentSelectedIndex", new Integer(index-1), new Integer(index));
    }
    

    // If nothing unusual changes in the middle of the wizard, simply:
    public void addChangeListener(ChangeListener l) {
    }

    public void removeChangeListener(ChangeListener l) {
    }

    // If something changes dynamically (besides moving between panels), e.g.
    // the number of panels changes in response to user input, then uncomment
    // the following and call when needed: fireChangeEvent();
    
//    private Set<ChangeListener> listeners = new HashSet<ChangeListener>(1); // or can use ChangeSupport in NB 6.0
//    public final void addChangeListener(ChangeListener l) {
//        synchronized (listeners) {
//        listeners.add(l);
//        }
//        System.out.println("Added change listener...");
//        System.out.flush();
//    }
//    public final void removeChangeListener(ChangeListener l) {
//        synchronized (listeners) {
//            listeners.remove(l);
//        }
//    }
//    protected final void fireChangeEvent() {
//    Iterator<ChangeListener> it;
//    synchronized (listeners) {
//        it = new HashSet<ChangeListener>(listeners).iterator();
//    }
//    ChangeEvent ev = new ChangeEvent(this);
//        while (it.hasNext()) {
//            it.next().stateChanged(ev);
//        }
//    }
    
    
    public String[] createSteps()
    {
        WizardDescriptor.Panel[] pans = getPanels();
        String[] steps = new String[pans.length]; 
        for (int i = 0; i < pans.length; i++) {
            Component c = pans[i].getComponent();
            steps[i] = c.getName();
        }
        return steps;
    }

    
    
    
 }
