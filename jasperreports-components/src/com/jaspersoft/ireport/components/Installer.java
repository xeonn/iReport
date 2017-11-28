/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jaspersoft.ireport.components;

import com.jaspersoft.ireport.locale.I18n;
import java.util.ResourceBundle;
import org.openide.modules.ModuleInstall;

/**
 * Manages a module's lifecycle. Remember that an installer is optional and
 * often not needed at all.
 */
public class Installer extends ModuleInstall {

    @Override
    public void restored() {
        // By default, do nothing.
        // Put your startup code here.

        I18n.addBundleLocation(ResourceBundle.getBundle("/com/jaspersoft/ireport/components/list/Bundle"));
        I18n.addBundleLocation(ResourceBundle.getBundle("/com/jaspersoft/ireport/components/barcode/Bundle"));
        I18n.addBundleLocation(ResourceBundle.getBundle("/com/jaspersoft/ireport/components/barcode/barbecue/Bundle"));
        I18n.addBundleLocation(ResourceBundle.getBundle("/com/jaspersoft/ireport/components/barcode/barcode4j/Bundle"));
    }
}
