/*
 * ReportNode.java
 * 
 * Created on Aug 31, 2007, 4:55:47 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.components.list;

import com.jaspersoft.ireport.designer.outline.nodes.ElementContainerChildren;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import net.sf.jasperreports.components.list.DesignListContents;
import net.sf.jasperreports.components.list.ListComponent;
import net.sf.jasperreports.engine.design.JRDesignComponentElement;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.util.Lookup;

/**
 *
 * @author gtoffoli
 */
public class ListChildren extends ElementContainerChildren implements PropertyChangeListener {

    private JRDesignComponentElement component = null;
    
    @SuppressWarnings("unchecked")
    public ListChildren(JasperDesign jd, JRDesignComponentElement component, Lookup doLkp) {
        super(jd, doLkp);
        //this.jd = jd;
        this.component = component;
        if (component.getComponent() instanceof ListComponent)
        {
            ListComponent c = (ListComponent) component.getComponent();
            DesignListContents contents = (DesignListContents) c.getContents();
            contents.getEventSupport().addPropertyChangeListener(this);
        }
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void recalculateKeys() {
        
        List l = (List)lock();
        l.clear();
        if (component.getComponent() instanceof ListComponent)
        {
            ListComponent c = (ListComponent) component.getComponent();
            DesignListContents contents = (DesignListContents) c.getContents();
            l.addAll(contents.getChildren());
        }
        update();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        System.out.println("Event:  " + evt.getPropertyName());
        super.propertyChange(evt);

    }



}
