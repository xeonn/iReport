/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.templates;

import com.jaspersoft.ireport.locale.I18n;

/**
 *
 * @author gtoffoli
 */
public class ReportTemplateItemAction extends TemplateItemAction {

    public ReportTemplateItemAction()
    {
        setDisplayName(I18n.getString("ReportTemplateItemAction.displayName"));
        setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jaspersoft/ireport/designer/resources/template/new_report.png")));
        setDescription(I18n.getString("ReportTemplateItemAction.description"));
        putProperty(PROP_SHOW_TEMPLATES, Boolean.TRUE);
        putProperty(PROP_SHOW_FINISH_BUTTON, Boolean.FALSE);
        putProperty(PROP_SHOW_LAUNCH_REPORT_WIZARD_BUTTON, Boolean.TRUE);
        putProperty(PROP_SHOW_OPEN_TEMPLATE_BUTTON, Boolean.TRUE);
    }


    @Override
    public void performAction(TemplatesFrame frame, int buttonIndex) {

        if (frame.getSelectedTemplateDescriptor() == null) return;
        frame.runTemplateWizard(frame.getSelectedTemplateDescriptor(), buttonIndex == BUTTON_LAUNCH_REPORT_WIZARD);
    }

}
