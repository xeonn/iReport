/*
 * ReportNode.java
 * 
 * Created on Aug 31, 2007, 4:55:47 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.outline.nodes;

import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.List;
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstab;
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstabGroup;
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstabRowGroup;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.nodes.Sheet;
import org.openide.util.Lookup;

/**
 *
 * @author gtoffoli
 */
public class CrosstabRowGroupNode extends CrosstabGroupNode implements PropertyChangeListener {

    private JRDesignCrosstabRowGroup group = null;

    public CrosstabRowGroupNode(JasperDesign jd, JRDesignCrosstab crosstab, JRDesignCrosstabRowGroup group, Lookup doLkp)
    {
        super (jd, crosstab, group, doLkp);
        this.group = group;
        setIconBaseWithExtension("com/jaspersoft/ireport/designer/resources/crosstabrows-16.png");
    }

    /**
     *  This is the function to create the sheet...
     * 
     */
    @Override
    protected Sheet createSheet() {
        Sheet sheet = super.createSheet();
        return sheet;
    }
    
    
    @Override
    public List<JRDesignCrosstabGroup> getGroups() {
        List list = Arrays.asList(getCrosstab().getRowGroups());
        return (List<JRDesignCrosstabGroup>)list;
    }

    @Override
    public int getType() {
        return ROW_GROUP;
    }
    
    
 
}
