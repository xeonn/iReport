/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.templates;

import org.openide.WizardDescriptor;
import org.openide.filesystems.FileObject;

/**
 * Interface to plug new ReportGenerator types. IReport has a default
 * report generator that works with his own templates.
 *  
 * @author gtoffoli
 */
public interface ReportGenerator {

    /**
     * You can use properties coming from the WizardDescriptor.
     * The WizardDescriptor should normally be a TemplateWizard,
     * so you can assume to use getTargetDirectory and getTargetName.
     * You need to return the generated jrxml file.
     * 
     * @param descriptor
     * @return
     */
    public FileObject generateReport(WizardDescriptor descriptor);
}
