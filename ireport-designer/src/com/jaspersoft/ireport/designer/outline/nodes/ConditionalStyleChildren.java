/*
 * ReportNode.java
 * 
 * Created on Aug 31, 2007, 4:55:47 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.outline.nodes;

import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.design.JRDesignConditionalStyle;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.nodes.Index;
import org.openide.nodes.Node;
import org.openide.util.Lookup;

/**
 *
 * @author gtoffoli
 */
public class ConditionalStyleChildren extends Index.KeysChildren {

    JasperDesign jd = null;
    private JRDesignConditionalStyle style = null;
    private Lookup doLkp = null;
    
    @SuppressWarnings("unchecked")
    public ConditionalStyleChildren(JasperDesign jd, JRDesignConditionalStyle style, Lookup doLkp) {
        super(new ArrayList());
        this.jd = jd;
        this.doLkp=doLkp;
        this.style=style;
    }

    

    /**
     * This method assumes that all the keys are JRDesignConditionalStyle.
     * 
     * @param key
     * @return
     */
    protected Node[] createNodes(Object key) {
        
        return null;
    }
    
    @Override
    protected void addNotify() {
        super.addNotify();
    }
    
    
    @SuppressWarnings("unchecked")
    public void recalculateKeys() {
        List l = (List)lock();
        l.clear();
        update();
    }

  
    public JRDesignConditionalStyle getStyle() {
        return style;
    }

    public void setStyle(JRDesignConditionalStyle style) {
        this.style = style;
    }

}
