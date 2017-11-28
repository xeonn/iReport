/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jaspersoft.ireport.designer.jrctx;

import com.jaspersoft.ireport.designer.utils.FileEncodingQueryImpl;
import java.io.IOException;
import javax.swing.JOptionPane;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObjectExistsException;
import org.openide.loaders.MultiDataObject;
import org.openide.loaders.SaveAsCapable;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

public class JRCTXDataObject extends MultiDataObject {

    final InstanceContent ic;

    public InstanceContent getIc() {
        return ic;
    }
   private AbstractLookup lookup;

   private JRCTXEditorSupport dataEditorSupport = null;

    public JRCTXDataObject(FileObject pf, JRCTXDataLoader loader) throws DataObjectExistsException, IOException {
        super(pf, loader);
        ic = new InstanceContent();
        lookup = new AbstractLookup(ic);
        dataEditorSupport = JRCTXEditorSupport.create(this);
        ic.add(dataEditorSupport);
        ic.add(this);
        ic.add( new FileEncodingQueryImpl());
        ic.add( getPrimaryFile() );
    }

    @Override
    protected Node createNodeDelegate() {
        return new JRCTXDataNode(this, getLookup());
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

    /**
     * @return the dataEditorSupport
     */
    public JRCTXEditorSupport getDataEditorSupport() {
        return dataEditorSupport;
    }

    /**
     * @param dataEditorSupport the dataEditorSupport to set
     */
    public void setDataEditorSupport(JRCTXEditorSupport dataEditorSupport) {
        this.dataEditorSupport = dataEditorSupport;
    }
}