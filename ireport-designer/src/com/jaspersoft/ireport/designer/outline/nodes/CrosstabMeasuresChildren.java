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
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstabMeasure;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.nodes.Index;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.Mutex;

/**
 *
 * @author gtoffoli
 */
public class CrosstabMeasuresChildren extends Index.KeysChildren implements PropertyChangeListener {

    JasperDesign jd = null;
    private JRDesignCrosstab crosstab = null;
    private Lookup doLkp = null;
    

    @SuppressWarnings("unchecked")
    public CrosstabMeasuresChildren(JasperDesign jd, JRDesignCrosstab crosstab, Lookup doLkp) {
        super(new ArrayList());
        this.jd = jd;
        this.doLkp = doLkp;
        this.crosstab = crosstab;
        // This is rendunant since the CrosstabMeasuresNode already is leastining to
        // update the children.
        // this.crosstab.getEventSupport().addPropertyChangeListener(this);
    }

    /*
    @Override
    protected List<Node> initCollection() {
        return recalculateKeys();
    }
    */
    
    
    protected Node[] createNodes(Object key) {
        
        return new Node[]{new CrosstabMeasureNode(jd, crosstab, (JRDesignCrosstabMeasure)key,doLkp)};
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
        List params = null;
        // TODO: use the getMeasuresList when available
        l.addAll( Arrays.asList(crosstab.getMeasures() ));
        update();
    }
    
    @SuppressWarnings("unchecked")
    public void reorder() { 
            Mutex.Action action = new Mutex.Action(){ 
                public Object run(){ 
                    Index.Support.showIndexedCustomizer(CrosstabMeasuresChildren.this.getIndex()); 
                    return null; 
                } 
            }; 
            MUTEX.writeAccess(action); 
        }

    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName() == null) return;
        // The CrosstabMeasuresNode already call recalculateKeys();
//        if (evt.getPropertyName().equals( JRDesignCrosstab.PROPERTY_MEASURES))
//        {
//            recalculateKeys();
//        }
    }
}
