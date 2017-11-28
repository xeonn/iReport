/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.util.FileResolver;

/**
 *
 * @author gtoffoli
 */
public class ProxyFileResolver implements FileResolver {

    private List<FileResolver> resolvers = null;
    
    /**
     * Add a resolver on top of the list....
     * 
     * @param resolver
     */
    public void addResolver(FileResolver resolver)
    {
        if (!resolvers.contains(resolver))
        {
            resolvers.add(0, resolver);
        }
    }
    
    public void removeResolver(FileResolver resolver)
    {
        resolvers.remove(resolver);
    }
    
    public ProxyFileResolver()
    {
        resolvers = new ArrayList<FileResolver>();
    }
    
    public ProxyFileResolver(List<FileResolver> resolverList)
    {
        this();
        resolvers.addAll(resolverList);
    }
    
    public File resolveFile(String arg0) {
        
        for (FileResolver res : resolvers)
        {
            try {
                File f = res.resolveFile(arg0);
                if (f!= null) return f;
            } catch (Exception ex)
            {
                
            }
        }
        
        return null;
    }

}
