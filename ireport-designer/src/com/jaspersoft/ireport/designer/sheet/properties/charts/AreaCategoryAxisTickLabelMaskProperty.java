/*
 * GraphicElementPropertiesFactory.java
 * 
 * Created on 5-nov-2007, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties.charts;

import com.jaspersoft.ireport.designer.sheet.properties.StringProperty;
import com.jaspersoft.ireport.locale.I18n;
import net.sf.jasperreports.charts.design.JRDesignAreaPlot;
    
    
/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public final class AreaCategoryAxisTickLabelMaskProperty extends StringProperty
{
    private final JRDesignAreaPlot plot;

    @SuppressWarnings("unchecked")
    public AreaCategoryAxisTickLabelMaskProperty(JRDesignAreaPlot plot)
    {
        super(plot);
        this.plot = plot;
    }
    
    @Override
    public String getName()
    {
        return JRDesignAreaPlot.PROPERTY_CATEGORY_AXIS_TICK_LABEL_MASK;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Global.Property.CategoryAxisTickLabelMask");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Global.Property.CategoryAxisTickLabelMaskdetail");
    }

    @Override
    public String getString()
    {
        return plot.getCategoryAxisTickLabelMask();
    }

    @Override
    public String getOwnString()
    {
        return plot.getCategoryAxisTickLabelMask();
    }

    @Override
    public String getDefaultString()
    {
        return null;
    }

    @Override
    public void setString(String mask)
    {
        plot.setCategoryAxisTickLabelMask(mask);
    }

}
