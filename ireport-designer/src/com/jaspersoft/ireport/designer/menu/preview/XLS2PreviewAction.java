/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jaspersoft.ireport.designer.menu.preview;

import com.jaspersoft.ireport.locale.I18n;

public final class XLS2PreviewAction extends AbstractPreviewAction {

    public String getName() {
        return I18n.getString("global.menu.XLS2Preview");
    }

    @Override
    public String getPreviewType() {
        return "xls2";
    }

    
}
