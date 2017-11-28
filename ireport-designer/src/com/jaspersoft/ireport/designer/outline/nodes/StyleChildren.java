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
import java.util.List;
import net.sf.jasperreports.engine.design.JRDesignConditionalStyle;
import net.sf.jasperreports.engine.design.JRDesignStyle;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.nodes.Index;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.Mutex;

/**
 *
 * @author gtoffoli
 */
public class StyleChildren extends Index.KeysChildren implements PropertyChangeListener {

    JasperDesign jd = null;
    private JRDesignStyle style = null;
    private Lookup doLkp = null;
    
    @SuppressWarnings("unchecked")
    public StyleChildren(JasperDesign jd, JRDesignStyle style, Lookup doLkp) {
        super(new ArrayList());
        this.jd = jd;
        this.doLkp=doLkp;
        this.style=style;
        this.style.getEventSupport().addPropertyChangeListener(this);
    }

    /**
     * This method assumes that all the keys are JRDesignConditionalStyle.
     * 
     * @param key
     * @return
     */
    protected Node[] createNodes(Object key) {
        
        return new Node[]{new ConditionalStyleNode(jd, (JRDesignConditionalStyle)key, doLkp, getStyle())};
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
        List styles = null;
        l.addAll(getStyle().getConditionalStyleList());
        update();
    }
    
    @SuppressWarnings("unchecked")
    public void reorder() { 
            Mutex.Action action = new Mutex.Action(){ 
                public Object run(){ 
                    Index.Support.showIndexedCustomizer(StyleChildren.this.getIndex()); 
                    return null; 
                } 
            }; 
            MUTEX.writeAccess(action); 
        }

    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName() == null) return;
        
        if (evt.getPropertyName().equals( JRDesignStyle.PROPERTY_CONDITIONAL_STYLES))
        {
            recalculateKeys();
        }
    }

    public JRDesignStyle getStyle() {
        return style;
    }

    public void setStyle(JRDesignStyle style) {
        this.style = style;
    }
}
