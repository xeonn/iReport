/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.jasperserver.ui;

import com.jaspersoft.jasperserver.api.metadata.xml.domain.impl.ResourceDescriptor;

/**
 *
 * @author gtoffoli
 */
public interface ResourceSelectedListener {

    public void resourceSelected(ResourceDescriptor res);
    
}
