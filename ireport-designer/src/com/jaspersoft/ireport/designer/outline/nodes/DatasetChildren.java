/*
 * DatasetChildren.java
 * 
 * Created on Sep 12, 2007, 10:14:02 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.outline.nodes;

import java.util.ArrayList;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author gtoffoli
 */
class DatasetChildren extends Children.Keys {

    JasperDesign jd = null;
    JRDesignDataset dataset = null;
    Lookup doLkp = null;
    
    public DatasetChildren(JasperDesign jd, JRDesignDataset dataset, Lookup doLkp) {
        this.jd = jd;
        this.doLkp = doLkp;
        this.dataset = dataset;
    }
        
    protected Node[] createNodes(Object key) {
        
        if (key.equals("parameters"))
        {
            return new Node[]{new ParametersNode(jd, dataset, doLkp)};
        }
        else if (key.equals("fields"))
        {
            return new Node[]{new FieldsNode(jd, dataset, doLkp)};
        }
        else if (key.equals("variables"))
        {
            return new Node[]{new VariablesNode(jd, dataset, doLkp)};
        }
        //else if (key.equals("groups"))
        //{
        //    return new Node[]{new VariablesNode(jd, dataset)};
        //}
        
        AbstractNode node = new AbstractNode(LEAF, Lookups.singleton(key));
        node.setName(key+"");
        return new Node[]{node};
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected void addNotify() {
        super.addNotify();
        ArrayList children = new ArrayList();
        children.add("parameters");
        children.add("fields");
        children.add("variables");
        //children.add("groups");
        setKeys(children);
    }

}
