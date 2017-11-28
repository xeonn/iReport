/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jaspersoft.jrx;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.data.queryexecuters.QueryExecuterDef;
import com.jaspersoft.ireport.locale.I18n;
import java.io.File;
import java.io.FilenameFilter;
import java.util.List;
import java.util.ResourceBundle;
import org.openide.modules.InstalledFileLocator;
import org.openide.modules.ModuleInstall;

/**
 * Manages a module's lifecycle. Remember that an installer is optional and
 * often not needed at all.
 */
public class Installer extends ModuleInstall {

    @Override
    public void restored() {

        I18n.addBundleLocation(ResourceBundle.getBundle("/com/jaspersoft/jrx/Bundle"));

        // Add to the iReport classpath the jar with the
        // JRXPathQueryExecuter....

        List<String> classpath = IReportManager.getInstance().getClasspath();
        File libDir = InstalledFileLocator.getDefault().locate("modules/ext", null, false);

        // find a jar called jasperreports-extensions-*.jar
        if (libDir != null && libDir.isDirectory())
        {
            File[] jars = libDir.listFiles(new FilenameFilter() {

                public boolean accept(File dir, String name) {
                    if (name.toLowerCase().startsWith("jasperreports-extensions-") &&
                        name.toLowerCase().endsWith(".jar"))
                    {
                        return true;
                    }
                    return false;
                }
            });

            for (int i=0; i<jars.length; ++i)
            {
                if (classpath.contains(jars[i].getPath())) continue;
                classpath.add(jars[i].getPath());
            }
            IReportManager.getInstance().setClasspath(classpath);
        }

        // Plugging the new datasource implementation.
        IReportManager.getInstance().addConnectionImplementationFactory(new JRXMLDataSourceConnectionFactory());

        // adding the query executer for xpath2...
        QueryExecuterDef qed = new QueryExecuterDef("xpath2",
                "com.jaspersoft.jrx.query.JRXPathQueryExecuterFactory",
                "com.jaspersoft.ireport.designer.data.fieldsproviders.XMLFieldsProvider");
        
        IReportManager.getInstance().addQueryExecuterDef(qed, false);

        QueryExecuterDef qedPlSQL = new QueryExecuterDef("plsql",
                "com.jaspersoft.jrx.query.PlSqlQueryExecuterFactory",
                "com.jaspersoft.ireport.designer.data.fieldsproviders.SQLFieldsProvider");

        IReportManager.getInstance().addQueryExecuterDef(qedPlSQL, false);


        // Pluggin the new exporter...
        IReportManager.getInstance().getExporterFactories().add(new JRTxtExporterFactory());

    }
}
