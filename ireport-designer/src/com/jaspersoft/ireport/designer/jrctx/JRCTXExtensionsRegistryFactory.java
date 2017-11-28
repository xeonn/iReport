/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JRCTXPreviewPanel.java
 *
 * Created on Feb 3, 2009, 4:32:04 PM
 */

package com.jaspersoft.ireport.designer.jrctx;

import java.util.Collections;
import java.util.List;
import net.sf.jasperreports.charts.ChartTheme;
import net.sf.jasperreports.charts.ChartThemeBundle;
import net.sf.jasperreports.chartthemes.simple.ChartThemeSettings;
import net.sf.jasperreports.chartthemes.simple.SimpleChartTheme;
import net.sf.jasperreports.engine.JRPropertiesMap;
import net.sf.jasperreports.extensions.ExtensionsRegistry;
import net.sf.jasperreports.extensions.ExtensionsRegistryFactory;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class JRCTXExtensionsRegistryFactory implements ExtensionsRegistryFactory, ChartThemeBundle 
{
    private static final ThreadLocal threadLocal = new ThreadLocal();
    
    public static void setChartThemeSettings(ChartThemeSettings chartThemeSettings)
    {
        threadLocal.set(chartThemeSettings);
    }

    public String[] getChartThemeNames()
    {
        return new String[0];
    }

    public ChartTheme getChartTheme(String themeName)
    {
        ChartThemeSettings settings = (ChartThemeSettings)threadLocal.get();
        if (settings != null)
        {
            return new SimpleChartTheme(settings);
        }
        return null;
    }

    private final ExtensionsRegistry extensionsRegistry = 
        new ExtensionsRegistry()
        {
            public List getExtensions(Class extensionType) 
            {
                if (ChartThemeBundle.class.equals(extensionType))
                {
                    return Collections.singletonList(JRCTXExtensionsRegistryFactory.this);
                }
                return null;
            }
        };

    public ExtensionsRegistry createRegistry(String registryId, JRPropertiesMap properties) 
    {
        return extensionsRegistry;
    }
}
