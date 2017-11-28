/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.wizards;

import com.jaspersoft.ireport.designer.wizards.CustomChooserWizardPanel;
import org.openide.WizardDescriptor;
import org.openide.WizardDescriptor.Panel;
import org.openide.loaders.TemplateWizard;

/**
 *
 * @author gtoffoli
 */
public class CustomTemplateWizard extends TemplateWizard {

    @Override
    protected Panel<WizardDescriptor> createTargetChooser() {
        return new CustomChooserWizardPanel(this);
    }

}
