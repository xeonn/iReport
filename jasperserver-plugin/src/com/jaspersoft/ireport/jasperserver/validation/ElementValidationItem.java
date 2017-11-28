/*
 * ElementValidationItem.java
 * 
 * Created on Sep 7, 2007, 4:03:01 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.jasperserver.validation;

import java.io.File;
import net.sf.jasperreports.engine.design.JRDesignElement;

/**
 *
 * @author gtoffoli
 */
public class ElementValidationItem {

    private File originalFileName = null;
            
    private String proposedExpression = null;
    
    private String resourceName = null;
    
    private String parentFolder = null;
    
    private JRDesignElement reportElement = null;
    
    public JRDesignElement getReportElement() {
        return reportElement;
    }

    public void setReportElement(JRDesignElement reportElement) {
        this.reportElement = reportElement;
    }
    
    public File getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(File originalFileName) {
        this.originalFileName = originalFileName;
    }
    
    public String toString()
    {
        return this.getReportElement().toString();
    }

    public String getProposedExpression() {
        return proposedExpression;
    }

    public void setProposedExpression(String proposedExpression) {
        this.proposedExpression = proposedExpression;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getParentFolder() {
        return parentFolder;
    }

    public void setParentFolder(String parentFolder) {
        this.parentFolder = parentFolder;
    }
    
}
