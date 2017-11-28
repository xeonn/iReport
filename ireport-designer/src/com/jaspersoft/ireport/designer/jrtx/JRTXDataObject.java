/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jaspersoft.ireport.designer.jrtx;

import java.io.IOException;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObjectExistsException;
import org.openide.loaders.MultiDataObject;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

public class JRTXDataObject extends MultiDataObject implements Lookup.Provider {

    final InstanceContent ic;

    public InstanceContent getIc() {
        return ic;
    }
   private AbstractLookup lookup;

    public JRTXDataObject(FileObject pf, JRTXDataLoader loader) throws DataObjectExistsException, IOException {
        super(pf, loader);
        ic = new InstanceContent();
        lookup = new AbstractLookup(ic);
        JRTXEditorSupport support = JRTXEditorSupport.create(this);
        ic.add(support);
        ic.add(this);
        ic.add( getPrimaryFile() );
    }

    @Override
    protected Node createNodeDelegate() {
        return new JRTXDataNode(this, getLookup());
    }



     @Override
    public Lookup getLookup() {
        return lookup;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Node.Cookie> T getCookie(Class<T> type) {

        Object o = lookup.lookup(type);
        if (o == null && Node.Cookie.class.isAssignableFrom(type)) // try to look in the super cookie...
        {
           o = super.getCookie(type);
        }

        return o instanceof Node.Cookie ? (T)o : null;
    }
}
