/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jaspersoft.ireport.designer.standalone;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.openide.modules.ModuleInstall;
import org.openide.windows.TopComponent.Registry;
import org.openide.windows.WindowManager;

/**
 * Manages a module's lifecycle. Remember that an installer is optional and
 * often not needed at all.
 */
public class Installer extends ModuleInstall {

    @Override
    public void restored() {
        // By default, do nothing.
        // Put your startup code here.
        WindowManager.getDefault().getRegistry().addPropertyChangeListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals( Registry.PROP_TC_CLOSED))
                {
                    System.gc();
                }
            }
        });
    }
}
