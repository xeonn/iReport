/*
 * ReportNode.java
 * 
 * Created on Aug 31, 2007, 4:55:47 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.outline.nodes;

import com.jaspersoft.ireport.designer.ModelUtils;
import com.jaspersoft.ireport.designer.actions.AddCellAction;
import javax.swing.Action;
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstab;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.nodes.Children;
import org.openide.nodes.Sheet;
import org.openide.util.Lookup;
import org.openide.util.actions.SystemAction;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;

/**
 *
 * @author gtoffoli
 */
public class NullCellNode extends IRAbstractNode {

    JasperDesign jd = null;
    private NullCell cell = null;
    private JRDesignCrosstab crosstab = null;
    public NullCellNode(JasperDesign jd, NullCell cell, JRDesignCrosstab crosstab, Lookup doLkp)
    {
        super (Children.LEAF, new ProxyLookup( doLkp, Lookups.fixed(jd, cell, crosstab)));
        this.jd = jd;
        this.cell = cell;
        this.crosstab = crosstab;
        
        setDisplayName ( ModelUtils.nameOf(cell.getOrigin()));
        
        setIconBaseWithExtension("com/jaspersoft/ireport/designer/resources/cell-16.png");
    }
    
    @Override
    public String getHtmlDisplayName()
    {
        return "<font color=#999999>" + getDisplayName();
    }

    @Override
    protected Sheet createSheet() {
        Sheet sheet = super.createSheet();
        return sheet;
    }
    
    @Override
    public Action[] getActions(boolean popup) {
        return new Action[] { SystemAction.get(AddCellAction.class)};
    }

    public JRDesignCrosstab getCrosstab() {
        return crosstab;
    }

    public NullCell getCell() {
        return cell;
    }
}
