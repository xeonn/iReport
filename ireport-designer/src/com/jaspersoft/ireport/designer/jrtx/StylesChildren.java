/*
 * ReportNode.java
 * 
 * Created on Aug 31, 2007, 4:55:47 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.jrtx;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.sf.jasperreports.engine.JRSimpleTemplate;
import net.sf.jasperreports.engine.JRTemplateReference;
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
public class StylesChildren extends Index.KeysChildren implements PropertyChangeListener {

    private JRSimpleTemplate template = null;
    private Lookup doLkp = null;
    
    @SuppressWarnings("unchecked")
    public StylesChildren(JRSimpleTemplate template, Lookup doLkp) {
        super(new ArrayList());
        this.template = template;
        this.doLkp=doLkp;
        //this.template.getEventSupport().addPropertyChangeListener(this);
    }

    /*
    @Override
    protected List<Node> initCollection() {
        return recalculateKeys();
    }
    */
    
    
    protected Node[] createNodes(Object key) {
        
        Node node = null;
        if (key instanceof JRDesignStyle)
        {
            node = new StyleNode(getTemplate(), (JRDesignStyle)key,getDoLkp());
        }
        else if (key instanceof JRTemplateReference)
        {
            node = new TemplateReferenceNode(getTemplate(), (JRTemplateReference)key,getDoLkp());
        }

        return new Node[]{node};
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

        l.addAll( Arrays.asList(getTemplate().getIncludedTemplates()) );
        l.addAll( Arrays.asList(getTemplate().getStyles()) );
        update();
    }
    
    @SuppressWarnings("unchecked")
    public void reorder() { 
            Mutex.Action action = new Mutex.Action(){ 
                public Object run(){ 
                    Index.Support.showIndexedCustomizer(StylesChildren.this.getIndex()); 
                    return null; 
                } 
            }; 
            MUTEX.writeAccess(action); 
        }

    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName() == null) return;
        if (evt.getPropertyName().equals( JasperDesign.PROPERTY_STYLES ))
        {
            recalculateKeys();
        }
    }

    /**
     * @return the template
     */
    public JRSimpleTemplate getTemplate() {
        return template;
    }

    /**
     * @param template the template to set
     */
    public void setTemplate(JRSimpleTemplate template) {
        this.template = template;
    }

    /**
     * @return the doLkp
     */
    public Lookup getDoLkp() {
        return doLkp;
    }

    /**
     * @param doLkp the doLkp to set
     */
    public void setDoLkp(Lookup doLkp) {
        this.doLkp = doLkp;
    }
}
