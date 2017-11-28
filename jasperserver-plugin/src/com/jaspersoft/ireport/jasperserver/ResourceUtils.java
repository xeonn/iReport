/*
 * ResourceUtils.java
 * 
 * Created on Jun 1, 2007, 11:02:13 AM
 * 
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.jasperserver;

import com.jaspersoft.jasperserver.api.metadata.xml.domain.impl.ResourceDescriptor;
import com.jaspersoft.jasperserver.api.metadata.xml.domain.impl.ResourceProperty;

/**
 *
 * @author gtoffoli
 */
public class ResourceUtils {

    /**
     * Create a clone of the resource descriptor. The replication is recursive.
     * @param rd The ResourceDescriptor to clone
     * @return the new clone
     */
    public static ResourceDescriptor cloneResourceDescriptor(ResourceDescriptor rd)
    {
        ResourceDescriptor newRd = new ResourceDescriptor();
        newRd.setName( rd.getName());
        newRd.setWsType( rd.getWsType() );
        newRd.setLabel( rd.getLabel());
        newRd.setDescription( rd.getDescription());
        newRd.setUriString( rd.getUriString());
        newRd.setIsNew( rd.getIsNew());
        
        for (int i=0; i< rd.getChildren().size(); ++i)
        {
            ResourceDescriptor tmpRd = (ResourceDescriptor)rd.getChildren().get(i);
            newRd.getChildren().add( cloneResourceDescriptor( tmpRd ) );
        }
        
        for (int i=0; i< rd.getProperties().size(); ++i)
        {
            ResourceProperty tmpRp = (ResourceProperty)rd.getProperties().get(i);
            newRd.getProperties().add( cloneResourceProperty( tmpRp ) );
        }
        
        return newRd;
    }
    
    /**
     * Create a clone of the resource property. The replication is recursive.
     * @param rp The ResourceProperty to clone
     * @return the new clone
     */
    public static ResourceProperty cloneResourceProperty(ResourceProperty rp)
    {
        ResourceProperty newRp = new ResourceProperty(rp.getName(), rp.getValue());
        
        for (int i=0; i< rp.getProperties().size(); ++i)
        {
            ResourceProperty tmpRp = (ResourceProperty)rp.getProperties().get(i);
            newRp.getProperties().add( cloneResourceProperty( tmpRp ) );
        }
        
        return newRp;
    }

}
