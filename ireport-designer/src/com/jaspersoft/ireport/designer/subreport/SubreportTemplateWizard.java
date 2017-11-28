/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.subreport;

import javax.swing.JComponent;
import net.sf.jasperreports.engine.design.JRDesignSubreport;
import org.openide.loaders.TemplateWizard;

/**
 *
 * @author gtoffoli
 */
public class SubreportTemplateWizard extends TemplateWizard {

    SubreportWizardIterator iterator = null;
    
    public SubreportTemplateWizard()
    {
        super();
        iterator = new SubreportWizardIterator();
        iterator.initialize(this);
        setPanelsAndSettings(iterator, this);
    }
    
    @Override
    protected void updateState() {
        super.updateState();
        
        // Set the setings...
        // compoun steps pane info
        putProperty("WizardPanel_contentSelectedIndex", ((JComponent)iterator.current().getComponent()).getClientProperty("WizardPanel_contentSelectedIndex")   );
        putProperty("WizardPanel_contentData", ((JComponent)iterator.current().getComponent()).getClientProperty("WizardPanel_contentData")  );
    }
    
    public JRDesignSubreport getElement()
    {
        return iterator.getElement();
    }
    
    
    
}
