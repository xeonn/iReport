/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.outline.nodes;

import com.jaspersoft.ireport.designer.ModelUtils;
import net.sf.jasperreports.crosstabs.design.JRCrosstabOrigin;

/**
 *
 * @author gtoffoli
 */
public class NullCell {
    
    private JRCrosstabOrigin origin = null;
    private String name = null;
    
    public NullCell(JRCrosstabOrigin origin)
    {
        this.origin = origin;
        this.name = ModelUtils.nameOf(origin);
    }
    
    @Override
    public String toString()
    {
        return ""+name;
    }

    public JRCrosstabOrigin getOrigin() {
        return origin;
    }

    public void setOrigin(JRCrosstabOrigin origin) {
        this.origin = origin;
        this.name = ModelUtils.nameOf(origin);
    }
}
