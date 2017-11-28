/*
 * GraphicElementPropertiesFactory.java
 * 
 * Created on 5-nov-2007, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties.charts;

import com.jaspersoft.ireport.designer.sheet.Tag;
import com.jaspersoft.ireport.designer.sheet.properties.ByteProperty;
import com.jaspersoft.ireport.locale.I18n;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.base.JRBaseChartPlot;
import org.jfree.chart.plot.PlotOrientation;


/**
 *  Class to manage the JRDesignChart.PROPERTY_TITLE_POSITION property
 */
public final class OrientationProperty extends ByteProperty
{

    public static final Byte ORIENTATION_HORIZONTAL = new Byte((byte)0);
    public static final Byte ORIENTATION_VERTICAL = new Byte((byte)1);

    private final JRBaseChartPlot plot;

    
    public OrientationProperty(JRBaseChartPlot plot)
    {
        super(plot);
        this.plot = plot;
    }

    @Override
    public String getName()
    {
        return JRBaseChartPlot.PROPERTY_ORIENTATION;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Chart_orientation");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Chart_orientation.desc");
    }

    @Override
    public List getTagList() 
    {
        List tags = new ArrayList();
        tags.add(new Tag(ORIENTATION_VERTICAL, I18n.getString("Chart_orientation.Vertical")));
        tags.add(new Tag(ORIENTATION_HORIZONTAL, I18n.getString("Chart_orientation.Horizontal")));
        return tags;
    }

    @Override
    public Byte getByte()
    {
        if (plot.getOrientation() == null) return ORIENTATION_VERTICAL;
        if (plot.getOrientation().equals(PlotOrientation.HORIZONTAL)) return ORIENTATION_HORIZONTAL;
        return ORIENTATION_VERTICAL;
    }

    @Override
    public Byte getOwnByte()
    {
        return getByte();
    }

    @Override
    public Byte getDefaultByte()
    {
        return ORIENTATION_VERTICAL;
    }

    @Override
    public void setByte(Byte position)
    {
        if (position.equals(ORIENTATION_VERTICAL))
        {
            plot.setOrientation(PlotOrientation.VERTICAL);
        }
        else
        {
            plot.setOrientation(PlotOrientation.HORIZONTAL);
        }
    }

}
