/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.utils;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.JrxmlVisualView;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.FileResolver;
import org.openide.filesystems.FileUtil;

/**
 *
 * @author gtoffoli
 */
public class ExpressionFileResolver implements FileResolver {

    private JRDesignExpression expression = null;
    private JasperDesign jasperDesign = null;
    private String reportFolder = null;
    private File file = null;
    private boolean resolveFile = true;
    private JRDesignDataset dataset = null;

    public ExpressionFileResolver(JRDesignExpression expression,
                                       JRDesignDataset dataset,
                                       JasperDesign jd)
    {
        this.expression = expression;
        this.jasperDesign = jd;
        this.dataset = dataset;
        if (dataset == null)
        {
            this.dataset = jd.getMainDesignDataset();
        }


        JrxmlVisualView visualView = IReportManager.getInstance().getActiveVisualView();
        if (visualView != null)
        {
            File reportFolderFile = FileUtil.toFile(visualView.getEditorSupport().getDataObject().getPrimaryFile());
            if (reportFolderFile.getParentFile() != null)
            {
                reportFolder = reportFolderFile.getParent();
            }
        }
    }

    public File resolveFile(String arg0) {
        if (resolveFile)
        {
            ClassLoader classLoader = IReportManager.getReportClassLoader();

            try {

                // Try to process the expression...
                ExpressionInterpreter interpreter = new ExpressionInterpreter(dataset, classLoader);

                Object ret = interpreter.interpretExpression( Misc.getExpressionText(expression) );

                if (ret != null)
                {
                    String resourceName = ret + "";
                    File f = new File(resourceName);
                    if (!f.exists())
                    {
                        URL[] urls = new URL[]{};
                        if (reportFolder != null)
                        {
                            urls = new URL[]{ (new File(reportFolder)).toURI().toURL()};
                        }
                        URLClassLoader urlClassLoader = new URLClassLoader(urls, classLoader);
                        URL url = urlClassLoader.findResource(resourceName);
                        if (url != null)
                        {
                            f = new File(url.getPath());
                            if (f.exists())
                            {
                                file = f;
                            }
                        }
                    }
                    else
                    {
                        file = f;
                    }

                    resolveFile = false;
                 }
            } catch (Exception ex) {
                resolveFile = false;
                ex.printStackTrace();
            }
        }
        return getFile();
    }

    /**
     * @return the file
     */
    public File getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(File file) {
        this.file = file;
    }

    /**
     * @return the jasperDesign
     */
    public JasperDesign getJasperDesign() {
        return jasperDesign;
    }

    /**
     * @param jasperDesign the jasperDesign to set
     */
    public void setJasperDesign(JasperDesign jasperDesign) {
        if (this.jasperDesign != jasperDesign)
        {
            this.jasperDesign = jasperDesign;
            this.file = null;
            resolveFile = true;
        }
    }

    /**
     * @return the reportFolder
     */
    public String getReportFolder() {
        return reportFolder;
    }

    /**
     * @param reportFolder the reportFolder to set
     */
    public void setReportFolder(String reportFolder) {

        if (reportFolder == null && this.reportFolder == null) return;

        if ( (reportFolder != null && this.reportFolder == null) ||
             (reportFolder == null && this.reportFolder != null) ||
             !(reportFolder.equals(this.reportFolder)))
        {
            this.reportFolder = reportFolder;
            this.file = null;
            resolveFile = true;
        }
    }

}
