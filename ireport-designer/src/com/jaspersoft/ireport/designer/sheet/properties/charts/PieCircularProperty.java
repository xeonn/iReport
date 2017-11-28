/*
 * GraphicElementPropertiesFactory.java
 * 
 * Created on 5-nov-2007, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties.charts;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.sheet.properties.BooleanProperty;
import com.jaspersoft.ireport.designer.undo.ObjectPropertyUndoableEdit;
import java.lang.reflect.InvocationTargetException;
import net.sf.jasperreports.charts.design.JRDesignPiePlot;
    
    
/**
 *  Class to manage the JRDesignPiePlot.PROPERTY_CIRCULAR property
 */
public final class PieCircularProperty extends BooleanProperty {

    private final JRDesignPiePlot plot;

    @SuppressWarnings("unchecked")
    public PieCircularProperty(JRDesignPiePlot plot)
    {
        super(plot);
        this.plot = plot;
    }
    @Override
    public String getName()
    {
        return JRDesignPiePlot.PROPERTY_CIRCULAR;
    }

    @Override
    public String getDisplayName()
    {
        return "Circular";
    }

    @Override
    public String getShortDescription()
    {
        return "Circular.";
    }

    @Override
    public Boolean getBoolean()
    {
        return plot.isCircular();
    }

    @Override
    public Boolean getOwnBoolean()
    {
        return plot.isCircular();
    }

    @Override
    public Boolean getDefaultBoolean()
    {
        return Boolean.TRUE;
    }

    @Override
    public void setBoolean(Boolean isShow)
    {
    	plot.setCircular(isShow);
    }

}
