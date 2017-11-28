/*
 * IRIndexedNode.java
 * 
 * Created on 9-nov-2007, 15.05.46
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.outline.nodes;

import javax.swing.JPanel;
import org.openide.nodes.Children;
import org.openide.nodes.Index;
import org.openide.nodes.Node;
import org.openide.util.Lookup;

/**
 *
 * @author gtoffoli
 */
public class IRIndexedNode extends IRAbstractNode {

    /** Index implementation */
    private Index indexImpl;

   
    /** Allows subclasses to provide their own children and
    * index handling.
    * @param children Lookup...
    * @param children the children implementation
    * @param indexImpl the index implementation
    */
    protected IRIndexedNode(Children children, Index indexImpl, Lookup lookup) {
        super(children, lookup);
        this.indexImpl = indexImpl;
    }

    /*
    * @return false to signal that the customizer should not be used.
    *  Subclasses can override this method to enable customize action
    *  and use customizer provided by this class.
    */
    @Override
    public boolean hasCustomizer() {
        return indexImpl != null;
    }

    /* Returns the customizer component.
    * @return the component
    */
    @Override
    @SuppressWarnings("deprecation")
    public java.awt.Component getCustomizer() {
        
        java.awt.Container c = new JPanel();
        if (indexImpl != null)
        {
            org.openide.nodes.IndexedCustomizer customizer = new org.openide.nodes.IndexedCustomizer();
            customizer.setObject(indexImpl);
        }
        return c;
    }

    /** Get a cookie.
    * @param clazz representation class
    * @return the index implementation or children if these match the cookie class,
    * else using the superclass cookie lookup
    */
    @Override
    public <T extends Node.Cookie> T getCookie(Class<T> clazz) {
        if (indexImpl != null && clazz.isInstance(indexImpl)) {
            // ok, Index implementor is enough
            return clazz.cast(indexImpl);
        }

        Children ch = getChildren();

        if (clazz.isInstance(ch)) {
            // ok, children are enough
            return clazz.cast(ch);
        }

        
        System.out.println("Cookie requested: " + clazz);
        System.out.flush();
        return super.getCookie(clazz);
    }
    
}
