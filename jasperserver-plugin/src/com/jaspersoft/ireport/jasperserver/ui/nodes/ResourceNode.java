/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.jasperserver.ui.nodes;

import com.jaspersoft.ireport.jasperserver.RepositoryFolder;
import com.jaspersoft.jasperserver.api.metadata.xml.domain.impl.ResourceDescriptor;

/**
 *
 * @author gtoffoli
 */
public interface ResourceNode {
    
    public ResourceDescriptor getResourceDescriptor();
    public RepositoryFolder getRepositoryObject();
    public void refreshChildrens(boolean reload);
    public void updateDisplayName();

}
