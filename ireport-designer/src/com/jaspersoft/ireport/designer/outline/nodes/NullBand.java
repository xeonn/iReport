/*
 * NullBand.java
 * 
 * Created on Sep 24, 2007, 4:26:07 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.outline.nodes;

import com.jaspersoft.ireport.designer.ModelUtils;
import net.sf.jasperreports.engine.JROrigin;

/**
 *
 * @author gtoffoli
 */
public class NullBand {
    
    private JROrigin origin = null;
    private String name = null;
    
    public NullBand(JROrigin origin)
    {
        this.origin = origin;
        this.name = ModelUtils.nameOf(origin);
    }
    
    @Override
    public String toString()
    {
        return ""+name;
    }

    public JROrigin getOrigin() {
        return origin;
    }

    public void setOrigin(JROrigin origin) {
        this.origin = origin;
        this.name = ModelUtils.nameOf(origin);
    }
}
