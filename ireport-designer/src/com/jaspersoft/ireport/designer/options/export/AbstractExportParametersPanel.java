/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.options.export;

import javax.swing.JPanel;
import com.jaspersoft.ireport.designer.options.OptionsPanel;
import com.jaspersoft.ireport.designer.options.IReportOptionsPanelController;

/**
 *
 * @author gtoffoli
 */
public abstract class AbstractExportParametersPanel extends JPanel implements OptionsPanel {

    private IReportOptionsPanelController controller = null;

    private boolean init = false;

    public boolean setInit(boolean b)
    {
        boolean old = init;
        init =b;
        return old;
    }

    public boolean isInit()
    {
        return init;
    }

    public AbstractExportParametersPanel()
    {
    }

    /**
     * Notify a change in the UI.
     */
    public void notifyChange()
    {
        if (this.getController() != null && !isInit())
        {
            getController().changed();
        }
    }

    /**
     * @return the controller
     */
    public IReportOptionsPanelController getController() {
        return controller;
    }
    
    /**
     * The contorller is always set by iReport before to use the panel...
     * @return the controller
     */
    public void setController(IReportOptionsPanelController ctrl) {
        this.controller = ctrl;
    }




    /**
     * return the name that should appear in the exporters list
     * @return
     */
    abstract public String getDisplayName();
}
