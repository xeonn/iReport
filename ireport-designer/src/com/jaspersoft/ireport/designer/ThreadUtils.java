/*
 * ThreadUtils.java
 * 
 * Created on Sep 28, 2007, 3:29:48 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer;

import javax.swing.SwingUtilities;

/**
 *
 * @author gtoffoli
 */
public class ThreadUtils {

    public static final void invokeInAWTThread(Runnable r)
    {
        if (SwingUtilities.isEventDispatchThread())
        {
            r.run();
        }
        else
        {
            SwingUtilities.invokeLater(r);
        }
    }
}
