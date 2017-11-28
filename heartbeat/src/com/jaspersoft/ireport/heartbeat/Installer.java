/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jaspersoft.ireport.heartbeat;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.utils.Misc;
import com.jaspersoft.ireport.locale.I18n;
import java.net.URLConnection;
import java.util.prefs.Preferences;
import javax.swing.JOptionPane;
import org.openide.modules.ModuleInstall;
import org.openide.windows.WindowManager;

/**
 * Manages a module's lifecycle. Remember that an installer is optional and
 * often not needed at all.
 */
public class Installer extends ModuleInstall implements Runnable {

    public static final String VERSION = "3.5.1";//"3.5.0";//"3.4.0";
    
    @Override
    public void restored() {

        Thread t = new Thread(this);
        t.start();
        
        
    }
    
    public void run()
    {
            Preferences props = IReportManager.getPreferences();
            
            try {
            /*
            if (props.getProperty("update.useProxy", "false").equals("true"))
            {
                System.getProperties().put( "proxySet", "true" );
                
                String urlProxy = props.getProperty("update.proxyUrl", "");
                String port = "8080";
                String server = urlProxy;
                if (urlProxy.indexOf(":") > 0)
                {
                    port = urlProxy.substring(urlProxy.indexOf(":") + 1);
                    server = urlProxy.substring(0, urlProxy.indexOf(":"));
                }
                
                System.getProperties().put( "proxyHost", server );
                System.getProperties().put( "proxyPort", port );

                MainFrame.getMainInstance().logOnConsole("Using proxy: " + urlProxy);
                
            }
            */
            java.net.URL url = new java.net.URL("http://ireport.sf.net/lastversion.php?version=" + VERSION + "&nb=1");

            
            byte[] webBuffer = new byte[400];
            URLConnection uConn = url.openConnection();
            
            
            java.io.InputStream is = uConn.getInputStream();
            int readed = is.read(webBuffer);
            final String version = new String(webBuffer,0,readed);

            if (version.compareTo(VERSION) > 0)
            {
                WindowManager.getDefault().invokeWhenUIReady(new Runnable() {

                    public void run() {

                        JOptionPane.showMessageDialog(Misc.getMainFrame(), I18n.getString("new.version.available", version), I18n.getString("new.version.available.title"), JOptionPane.INFORMATION_MESSAGE);
                    }
                });

            }
            
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

}
