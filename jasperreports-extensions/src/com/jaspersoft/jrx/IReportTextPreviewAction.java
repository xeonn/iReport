/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jaspersoft.jrx;

import com.jaspersoft.ireport.designer.menu.preview.AbstractPreviewAction;
import com.jaspersoft.ireport.locale.I18n;


public final class IReportTextPreviewAction extends AbstractPreviewAction {


    public String getName() {
        return I18n.getString("CTL_IReportTextPreviewAction");
    }

    @Override
    public String getPreviewType() {
        return "irtxt";
    }

}
