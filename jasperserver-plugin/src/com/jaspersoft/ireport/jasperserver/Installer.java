/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jaspersoft.ireport.jasperserver;

import com.jaspersoft.ireport.designer.IReportManager;
import net.sf.jasperreports.engine.util.JRProperties;
import org.openide.modules.ModuleInstall;

/**
 * Manages a module's lifecycle. Remember that an installer is optional and
 * often not needed at all.
 */
public class Installer extends ModuleInstall {

    @Override
    public void restored() {

        IReportManager.getInstance().getFileResolvers().add(RepoImageCache.getInstance());
        IReportManager.getInstance().addCustomLinkType("ReportExecution", "ReportExecution");

        // Set a fake query executer for sl languages...
        if (JRProperties.getProperty("net.sf.jasperreports.query.executer.factory.sl") == null)
        {
            IReportManager.getInstance().setJRProperty("net.sf.jasperreports.query.executer.factory.sl", "net.sf.jasperreports.engine.query.JRJdbcQueryExecuterFactory");
        }
    }
}
