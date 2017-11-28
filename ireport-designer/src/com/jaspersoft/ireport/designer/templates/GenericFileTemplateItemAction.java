/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.templates;

import javax.swing.ImageIcon;

/**
 *
 * @author gtoffoli
 */
public class GenericFileTemplateItemAction extends TemplateItemAction {

    private String targetName = null;
    private String templateName = null;


    public GenericFileTemplateItemAction(String displayName, String description, String targetName, String templateName, ImageIcon icon)
    {
        setDisplayName(displayName);
        setDescription(description);
        this.targetName = targetName;
        this.templateName = templateName;
        setIcon(icon);
        putProperty(PROP_SHOW_TEMPLATES, Boolean.FALSE);
        putProperty(PROP_SHOW_FINISH_BUTTON, Boolean.TRUE);
        putProperty(PROP_SHOW_LAUNCH_REPORT_WIZARD_BUTTON, Boolean.FALSE);
        putProperty(PROP_SHOW_OPEN_TEMPLATE_BUTTON, Boolean.FALSE);
    }

    @Override
    public void performAction(TemplatesFrame frame, int buttonId) {

        if (buttonId == BUTTON_FINISH)
        {
            frame.runTemplateWizard(getTargetName(), getTemplateName());
        }


    }

    /**
     * @return the targetName
     */
    public String getTargetName() {
        return targetName;
    }

    /**
     * @param targetName the targetName to set
     */
    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    /**
     * @return the templateName
     */
    public String getTemplateName() {
        return templateName;
    }

    /**
     * @param templateName the templateName to set
     */
    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

}
