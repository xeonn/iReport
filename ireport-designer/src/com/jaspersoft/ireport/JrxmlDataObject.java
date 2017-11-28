package com.jaspersoft.ireport;

import com.jaspersoft.ireport.designer.JrxmlEditorSupport;
import java.io.IOException;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObjectExistsException;
import org.openide.loaders.MultiDataObject;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

public class JrxmlDataObject extends MultiDataObject 
        implements Lookup.Provider {
    
    
    final InstanceContent ic;

    public InstanceContent getIc() {
        return ic;
    }
   private AbstractLookup lookup;
    
    public JrxmlDataObject(FileObject pf, JrxmlDataLoader loader) throws DataObjectExistsException, IOException {
        super(pf, loader);
        ic = new InstanceContent();
        lookup = new AbstractLookup(ic);
        JrxmlEditorSupport support = JrxmlEditorSupport.create(this);
        ic.add(support);
        ic.add(this);
        ic.add( getPrimaryFile() );
        //ic.add(new JrxmlProviderImpl(getPrimaryFile()));
        //CookieSet cookies = getCookieSet();
        
        //cookies.add((Node.Cookie) DataEditorSupport.create(this, getPrimaryEntry(), cookies));
        
    }
   
    @Override
    protected Node createNodeDelegate() {
        return new JrxmlDataNode(this,getLookup()); //, 
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