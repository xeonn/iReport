/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.utils;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.ModelUtils;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignImage;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.FileResolver;

/**
 *
 * @author gtoffoli
 */
public class ImageExpressionFileResolver implements FileResolver {

    private JRDesignImage imageElement = null;
    private JRDesignExpression imageExpression = null;
    private JasperDesign jasperDesign = null;
    private String reportFolder = null;
    private File file = null;
    private boolean resolveFile = true;

    public ImageExpressionFileResolver(JRDesignImage imageElement,
                                       String reportFolder,
                                       JasperDesign jd)
    {
        this.imageElement = imageElement;
        this.imageExpression = (JRDesignExpression) imageElement.getExpression();
        this.reportFolder = reportFolder;
        this.jasperDesign = jd;
    }

    public File resolveFile(String arg0) {
        if (resolveFile)
        {
            // We need to resolve this file...
            if (imageElement.getExpression() == null ||
                imageElement.getExpression().getValueClassName() == null ||
                !imageElement.getExpression().getValueClassName().equals("java.lang.String"))
            {
               // Return default image...
                return null;
            }

            JRDesignDataset dataset = ModelUtils.getElementDataset(imageElement, jasperDesign);
            ClassLoader classLoader = IReportManager.getReportClassLoader();

            try {
                
                // Try to process the expression...
                ExpressionInterpreter interpreter = new ExpressionInterpreter(dataset, classLoader);

                Object ret = interpreter.interpretExpression( imageElement.getExpression().getText() );

                System.out.println("Resolved: " + ret);
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
     * @return the imageElement
     */
    public JRDesignImage getImageElement() {
        return imageElement;
    }

    /**
     * @param imageElement the imageElement to set
     */
    public void setImageElement(JRDesignImage imageElement) {
        if (this.imageElement != imageElement ||
            this.imageExpression != imageElement.getExpression())
        {
            this.imageElement = imageElement;
            this.imageExpression = (JRDesignExpression) imageElement.getExpression();
            this.file = null;
            resolveFile = true;
        }
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
