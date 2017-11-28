/*
 * ReportNode.java
 * 
 * Created on Aug 31, 2007, 4:55:47 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties.charts;

import com.jaspersoft.ireport.designer.sheet.Tag;
import com.jaspersoft.ireport.designer.sheet.properties.StringListProperty;
import java.util.List;
import net.sf.jasperreports.engine.JRChart;
import net.sf.jasperreports.engine.design.JRDesignChart;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public final class RenderTypeProperty extends StringListProperty
{
    private final JRDesignChart chart;

    @SuppressWarnings("unchecked")
    public RenderTypeProperty(JRDesignChart chart)
    {
        super(chart);
        this.chart = chart;
    }

    @Override
    public String getName()
    {
        return JRChart.PROPERTY_CHART_RENDER_TYPE;
    }

    @Override
    public String getDisplayName()
    {
        return "Render Type";
    }

    @Override
    public String getShortDescription()
    {
        return "The render type of the chart.";
    }

    @Override
    public List getTagList() 
    {
        List tags = new java.util.ArrayList();
        tags.add(new Tag(JRChart.RENDER_TYPE_DRAW, JRChart.RENDER_TYPE_DRAW));
        tags.add(new Tag(JRChart.RENDER_TYPE_IMAGE, JRChart.RENDER_TYPE_IMAGE));
        tags.add(new Tag(JRChart.RENDER_TYPE_SVG, JRChart.RENDER_TYPE_SVG));
        return tags;
    }

    @Override
    public String getString()
    {
        return chart.getRenderType();
    }

    @Override
    public String getOwnString()
    {
        return chart.getRenderType();
    }

    @Override
    public String getDefaultString()
    {
        return null;
    }

    @Override
    public void setString(String renderType)
    {
        chart.setRenderType(renderType);
    }

}
