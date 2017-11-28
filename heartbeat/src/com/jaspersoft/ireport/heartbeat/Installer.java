/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jaspersoft.ireport.heartbeat;

import com.jaspersoft.ireport.designer.IReportManager;
import java.net.URLConnection;
import java.util.prefs.Preferences;
import org.openide.modules.ModuleInstall;

/**
 * Manages a module's lifecycle. Remember that an installer is optional and
 * often not needed at all.
 */
public class Installer extends ModuleInstall implements Runnable {

    public static final String VERSION = "3.1.1";
    
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
            java.net.URL url = new java.net.URL("http://ireport.sf.net/lastversion.php?version=" + VERSION);
            
            byte[] webBuffer = new byte[100];
            URLConnection uConn = url.openConnection();
            /*
            if (props.getProperty("update.useProxy", "false").equals("true") &&
                props.getProperty("update.useAuth", "false").equals("true"))
            {
                String password = props.getProperty("update.username", "") + ":" + props.getProperty("update.password", "");
                
                org.w3c.tools.codec.Base64Encoder b = new org.w3c.tools.codec.Base64Encoder(password);
		String encodedPassword = b.processString();
                uConn.setRequestProperty( "Proxy-Authorization", encodedPassword );
            }
            */
            
            //uConn.setReadTimeout(1000);
            java.io.InputStream is = uConn.getInputStream();
            int readed = is.read(webBuffer);
            String version = new String(webBuffer,0,readed);
//            if (version.toLowerCase().startsWith("ireport")) {
//                if (version.compareTo(it.businesslogic.ireport.gui.MainFrame.constTitle) > 0) {
//                    
//                    final String fversion = version;
//                    javax.swing.SwingUtilities.invokeLater(new Runnable() {
//                    public void run() {
//                        javax.swing.JOptionPane.showMessageDialog(it.businesslogic.ireport.gui.MainFrame.getMainInstance(),
//                                I18n.getFormattedString("messages.upgradeSearch.newVersion",
//                                "{0} is available on http://ireport.sf.net!", new Object[]{fversion}) );
//                    }});
//                }
//            }
            
            
        } catch (Throwable ex) {
            //ex.printStackTrace();
        }
    }

}
