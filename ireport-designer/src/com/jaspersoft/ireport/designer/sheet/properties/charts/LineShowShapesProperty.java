/*
 * GraphicElementPropertiesFactory.java
 * 
 * Created on 5-nov-2007, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties.charts;

import com.jaspersoft.ireport.designer.sheet.properties.BooleanProperty;
import net.sf.jasperreports.charts.design.JRDesignLinePlot;
    
    
/**
 *  Class to manage the JRDesignLinePlot.PROPERTY_SHOW_SHAPES property
 */
public final class LineShowShapesProperty extends BooleanProperty {

    private final JRDesignLinePlot plot;

    @SuppressWarnings("unchecked")
    public LineShowShapesProperty(JRDesignLinePlot plot)
    {
        super(plot);
        this.plot = plot;
    }
    @Override
    public String getName()
    {
        return JRDesignLinePlot.PROPERTY_SHOW_SHAPES;
    }

    @Override
    public String getDisplayName()
    {
        return "Show Shapes";
    }

    @Override
    public String getShortDescription()
    {
        return "Show Shapes.";
    }

    @Override
    public Boolean getBoolean()
    {
        return plot.isShowShapes();
    }

    @Override
    public Boolean getOwnBoolean()
    {
        return plot.isShowShapes();
    }

    @Override
    public Boolean getDefaultBoolean()
    {
        return Boolean.TRUE;
    }

    @Override
    public void setBoolean(Boolean isShow)
    {
    	plot.setShowShapes(isShow);
    }

}
