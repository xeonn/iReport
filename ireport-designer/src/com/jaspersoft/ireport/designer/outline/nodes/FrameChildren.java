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
import java.util.List;
import net.sf.jasperreports.engine.design.JRDesignFrame;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.util.Lookup;

/**
 *
 * @author gtoffoli
 */
public class FrameChildren  extends ElementContainerChildren implements PropertyChangeListener {

    private JRDesignFrame frame = null;
    
    @SuppressWarnings("unchecked")
    public FrameChildren(JasperDesign jd, JRDesignFrame frame, Lookup doLkp) {
        super(jd, doLkp);
        //this.jd = jd;
        this.frame = frame;
        this.frame.getEventSupport().addPropertyChangeListener(this);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void recalculateKeys() {
        
        List l = (List)lock();
        l.clear();
        l.addAll(frame.getChildren());
        update();
    }
    

}
