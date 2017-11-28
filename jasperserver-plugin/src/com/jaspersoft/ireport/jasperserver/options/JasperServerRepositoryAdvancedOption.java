/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jaspersoft.ireport.jasperserver.options;

import org.netbeans.spi.options.AdvancedOption;
import org.netbeans.spi.options.OptionsPanelController;
import org.openide.util.NbBundle;

public final class JasperServerRepositoryAdvancedOption extends AdvancedOption {

    public String getDisplayName() {
        return NbBundle.getMessage(JasperServerRepositoryAdvancedOption.class, "AdvancedOption_DisplayName_JasperServerRepository");
    }

    public String getTooltip() {
        return NbBundle.getMessage(JasperServerRepositoryAdvancedOption.class, "AdvancedOption_Tooltip_JasperServerRepository");
    }

    public OptionsPanelController create() {
        return new JasperServerRepositoryOptionsPanelController();
    }
}
