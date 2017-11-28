/*
 * iReport - Visual Designer for JasperReports.
 * Copyright (C) 2002 - 2009 Jaspersoft Corporation. All rights reserved.
 * http://www.jaspersoft.com
 *
 * Unless you have purchased a commercial license agreement from Jaspersoft,
 * the following license terms apply:
 *
 * This program is part of iReport.
 *
 * iReport is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * iReport is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with iReport. If not, see <http://www.gnu.org/licenses/>.
 */
package com.jaspersoft.ireport.heartbeat;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.utils.Misc;
import com.jaspersoft.ireport.locale.I18n;
import java.net.URLConnection;
import java.util.UUID;
import java.util.prefs.Preferences;
import javax.swing.JOptionPane;
import org.openide.modules.ModuleInstall;
import org.openide.windows.WindowManager;

/**
 * Manages a module's lifecycle. Remember that an installer is optional and
 * often not needed at all.
 */
public class Installer extends ModuleInstall implements Runnable {

    public static final String VERSION = "3.7.5";//"3.7.4";//"3.7.3";//"3.7.2";//"3.7.1";//"3.7.0";//"3.6.2";//"3.6.2-RC1";//"3.6.1";//"3.6.0";//"3.6.0";//"3.5.3";//"3.5.2";//"3.5.1";//"3.5.0";//"3.4.0";
    
    @Override
    public void restored() {

        Thread t = new Thread(this);
        t.start();
        
        
    }
    
    public void run()
    {
            if (IReportManager.getInstance().isNoNetwork()) return;
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
            String uuid = IReportManager.getPreferences().get("UUID",null);
            int newInstallation = 0;
            if (uuid == null || uuid.length() == 0)
            {
                newInstallation = 1;
                uuid = UUID.randomUUID().toString();
                IReportManager.getPreferences().put("UUID",uuid);
            }

            System.out.println("Invoking URL " + "http://ireport.sf.net/lastversion.php?version=" + VERSION + "&nb=1&uuid=" + uuid + "&new="+newInstallation);
            System.out.flush();
            java.net.URL url = new java.net.URL("http://ireport.sf.net/lastversion.php?version=" + VERSION + "&nb=1&uuid=" + uuid + "&new="+newInstallation);
            
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
