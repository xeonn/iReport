/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.data;

import java.util.List;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.design.JRDesignField;

/**
 *
 * @author gtoffoli
 */
public interface WizardFieldsProvider {

    /**
     * Return the name of the language to use
     * Null if a language is not required.
     */
    public String getQueryLanguage();
    
    /**
     * Return the fields read the specified query
     */
    public List<JRDesignField> readFields(String query) throws Exception;
    
    /**
     * Return the fields read the specified query
     */
    public boolean supportsDesign();
    
    /**
     * Return the fields read the specified query
     */
    public String designQuery(String query);
    
}
