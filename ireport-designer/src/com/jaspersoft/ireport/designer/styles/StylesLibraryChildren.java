/*
 * ReportNode.java
 * 
 * Created on Aug 31, 2007, 4:55:47 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.styles;

import com.jaspersoft.ireport.designer.jrtx.*;
import net.sf.jasperreports.engine.JRSimpleTemplate;
import net.sf.jasperreports.engine.JRTemplateReference;
import net.sf.jasperreports.engine.design.JRDesignStyle;
import org.openide.nodes.Node;
import org.openide.util.Lookup;

/**
 *
 * @author gtoffoli
 */
public class StylesLibraryChildren extends StylesChildren {

    public StylesLibraryChildren(JRSimpleTemplate template, Lookup doLkp) {
        super(template,doLkp);
    }

    /*
    @Override
    protected List<Node> initCollection() {
        return recalculateKeys();
    }
    */
    
    
    @Override
    protected Node[] createNodes(Object key) {
        
        Node node = null;
        if (key instanceof JRDesignStyle)
        {
            node = new LibraryStyleNode(getTemplate(), (JRDesignStyle)key, getDoLkp());
        }
        else if (key instanceof JRTemplateReference)
        {
            node = new LibraryTemplateReferenceNode(getTemplate(), (JRTemplateReference)key, getDoLkp());
        }

        return new Node[]{node};
    }
}
