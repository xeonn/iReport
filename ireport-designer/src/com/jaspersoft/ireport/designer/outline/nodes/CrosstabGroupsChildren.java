/*
 * ReportNode.java
 * 
 * Created on Aug 31, 2007, 4:55:47 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.outline.nodes;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstab;
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstabColumnGroup;
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstabRowGroup;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.nodes.Index;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.Mutex;

/**
 *
 * @author gtoffoli
 */
public class CrosstabGroupsChildren extends Index.KeysChildren implements PropertyChangeListener {

    JasperDesign jd = null;
    private JRDesignCrosstab crosstab = null;
    private Lookup doLkp = null;
    private int type = CrosstabGroupNode.ROW_GROUP;
    

    @SuppressWarnings("unchecked")
    public CrosstabGroupsChildren(JasperDesign jd, JRDesignCrosstab crosstab, Lookup doLkp, int type) {
        super(new ArrayList());
        this.jd = jd;
        this.doLkp = doLkp;
        this.crosstab = crosstab;
        this.type = type;
        this.crosstab.getEventSupport().addPropertyChangeListener(this);
    }

    /*
    @Override
    protected List<Node> initCollection() {
        return recalculateKeys();
    }
    */
    
    
    protected Node[] createNodes(Object key) {
        
        if (getType() == CrosstabGroupNode.ROW_GROUP)
        {
            return new Node[]{new CrosstabRowGroupNode(jd, crosstab, (JRDesignCrosstabRowGroup)key,doLkp)};
        }
        else
        {
            return new Node[]{new CrosstabColumnGroupNode(jd, crosstab, (JRDesignCrosstabColumnGroup)key,doLkp)};
        }
    }
    
    
    
    @Override
    protected void addNotify() {
        super.addNotify();
        recalculateKeys();
    }
    
    
    @SuppressWarnings("unchecked")
    public void recalculateKeys() {
        
        List l = (List)lock();
        l.clear();
        if (getType() == CrosstabGroupNode.ROW_GROUP)
        {
            l.addAll( Arrays.asList(crosstab.getRowGroups()));
        }
        else
        {
            l.addAll( Arrays.asList(crosstab.getColumnGroups()));
        }
        
        update();
    }
    
    @SuppressWarnings("unchecked")
    public void reorder() { 
            Mutex.Action action = new Mutex.Action(){ 
                public Object run(){ 
                    Index.Support.showIndexedCustomizer(CrosstabGroupsChildren.this.getIndex()); 
                    return null; 
                } 
            }; 
            MUTEX.writeAccess(action); 
        }

    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName() == null) return;
        if (evt.getPropertyName().equals( JRDesignCrosstab.PROPERTY_ROW_GROUPS) && getType()==CrosstabGroupNode.ROW_GROUP)
        {
            recalculateKeys();
        }
        if (evt.getPropertyName().equals( JRDesignCrosstab.PROPERTY_COLUMN_GROUPS) && getType()==CrosstabGroupNode.COLUMN_GROUP)
        {
            recalculateKeys();
        }
    }

    public int getType() {
        return type;
    }
}
