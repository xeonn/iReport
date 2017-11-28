/**
 * Copyright (C) 2005, 2006 CINCOM SYSTEMS, INC.
 * All Rights Reserved
 * www.cincom.com
 * @author MPenningroth
 */
package com.jaspersoft.ireport.designer.data.fieldsproviders;

import net.sf.jasperreports.engine.design.JRDesignField;

public class XmlaFieldNode extends JRDesignField {
    private int axisNumber;
    
    /**
     * Creates a new instance of XmlaFieldNode 
     * assings the values for Node name and the Axis number.
     */
    public XmlaFieldNode(String name, int axisNumber) {
        super();
        setName(name);
        setValueClassName("java.lang.String");
        this.axisNumber = axisNumber;
    }
    /**
     * Returns axisNumber.
     */
    public int getAxisNumber() {
        return axisNumber;
    }
    
}
