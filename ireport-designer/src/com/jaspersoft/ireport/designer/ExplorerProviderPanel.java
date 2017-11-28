/*
 * ExplorerProviderPanel.java
 * 
 * Created on Sep 11, 2007, 3:58:51 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer;

import javax.swing.JPanel;
import org.openide.explorer.ExplorerManager;

/**
 *
 * @author gtoffoli
 */
public class ExplorerProviderPanel extends JPanel implements ExplorerManager.Provider {

    ExplorerManager manager = null;
    public ExplorerProviderPanel(ExplorerManager manager)
    {
        super();
        this.manager = manager;
        
    }

    public ExplorerManager getExplorerManager() {
        return manager;
    }

}
