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
import net.sf.jasperreports.engine.JRVisitable;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignElementGroup;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.nodes.Index;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.Mutex;

/**
 *
 * @author gtoffoli
 */
public class ElementContainerChildren  extends Index.KeysChildren implements PropertyChangeListener {

    private ElementNodeVisitor visitor = null;
    private JRDesignElementGroup container = null;
    
    private boolean init=false;
    private synchronized void setInit(boolean init)
    {
        this.init = init;
    }
    private synchronized boolean isInit()
    {
        return this.init;
    }
    
    @SuppressWarnings("unchecked")
    protected ElementContainerChildren(JasperDesign jasperDesign, Lookup doLkp) {
        super(new ArrayList());
        this.visitor = new ElementNodeVisitor(jasperDesign, doLkp);
    }
    
    
    @SuppressWarnings("unchecked")
    public ElementContainerChildren(JasperDesign jasperDesign, JRDesignElementGroup container, Lookup doLkp) {
        super(new ArrayList());
        this.visitor = new ElementNodeVisitor(jasperDesign, doLkp);
        this.container = container;
        this.container.getEventSupport().addPropertyChangeListener(this);
    }

    protected Node[] createNodes(Object key) 
    {
        IRIndexedNode node = visitor.getNode((JRVisitable)key);
        
        if (node != null)
            return new Node[]{node};
        
        return new Node[]{};
    }
    
    @Override
    protected void addNotify() {
        super.addNotify();
        recalculateKeys();
    }
    
    
    @SuppressWarnings("unchecked")
    public void recalculateKeys() {
        
        if (container == null) return;
        
        List l = (List)lock();
        l.clear();
        l.addAll(container.getChildren());
        boolean b = isInit();
        setInit(true);
        update();
        setInit(b);
    }
    
    @SuppressWarnings("unchecked")
    public void reorder() { 
            Mutex.Action action = new Mutex.Action(){ 
                public Object run(){ 
                    Index.Support.showIndexedCustomizer(ElementContainerChildren.this.getIndex()); 
                    return null; 
                } 
            }; 
            MUTEX.writeAccess(action); 
        }

    public void propertyChange(PropertyChangeEvent evt) {
        if (isInit()) return;
        
        if (evt.getPropertyName() == null) return;
        if (evt.getPropertyName().equals( JRDesignBand.PROPERTY_CHILDREN))
        {
            recalculateKeys();
        }
    }
}
