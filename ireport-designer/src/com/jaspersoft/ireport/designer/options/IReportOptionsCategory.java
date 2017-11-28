/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jaspersoft.ireport.designer.options;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import org.netbeans.spi.options.OptionsCategory;
import org.netbeans.spi.options.OptionsPanelController;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;

public final class IReportOptionsCategory extends OptionsCategory {

    public Icon getIcon() {
        return new ImageIcon(Utilities.loadImage("com/jaspersoft/ireport/designer/options/ireport_icon.png"));
    }

    public String getCategoryName() {
        return NbBundle.getMessage(IReportOptionsCategory.class, "OptionsCategory_Name_IReport");
    }

    public String getTitle() {
        return NbBundle.getMessage(IReportOptionsCategory.class, "OptionsCategory_Title_IReport");
    }

    public OptionsPanelController create() {
        return new IReportOptionsPanelController();
    }
}
