/*
 * EditorContext.java
 * 
 * Created on Oct 11, 2007, 4:25:53 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.editor;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JEditorPane;
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstab;
import net.sf.jasperreports.engine.design.JRDesignDataset;

/**
 * This class provides a way to define what objects are available in the context of
 * a particular expression.
 * @author gtoffoli
 */
public class ExpressionContext 
{
    public static final String ATTRIBUTE_EXPRESSION_CONTEXT = "EXPRESSION_CONTEXT";
    
    private static ExpressionContext globalContext = null;
    public static ExpressionContext getGlobalContext()
    {
        return globalContext;
    }

    synchronized public static void setGlobalContext(ExpressionContext g)
    {
        globalContext = g;
    }
    
    public static JEditorPane activeEditor = null;
    
    private List<JRDesignDataset> datasets = new ArrayList<JRDesignDataset>();
    private List<JRDesignCrosstab> crosstabs = new ArrayList<JRDesignCrosstab>();
    
    /**
     *  Return the datasources available for this context.
     *  Actually only one datasource should be visible at time, but in the future
     *  you never know.
     * 
     */
    public List<JRDesignDataset> getDatasets() { return datasets; }
    
    /**
     *  Return the crosstabs available for this context.
     */
    public List<JRDesignCrosstab> getCrosstabs() { return crosstabs; }

    public ExpressionContext() {}
    
    /**
     * The most commond kind of Expressioncontext used for elements in
     * a document (not in a crosstab), for fields and variables, for
     * crosstab groups (bucket expressions) and measures.
     * 
     * @param dataset
     */
    public ExpressionContext(JRDesignDataset dataset)
    {
        datasets.add(dataset);
    }
    
    /**
     * This kind of expression context should be set for element expressions
     * (like textfields and images) appearing in a crosstab
     * 
     * @param crosstab
     */
    public ExpressionContext(JRDesignCrosstab crosstab)
    {
        crosstabs.add(crosstab);
    }
}
