/*
 * GraphicElementPropertiesFactory.java
 * 
 * Created on 5-nov-2007, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.jrctx.nodes.properties;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.sheet.SeriesColorsProperty;
import com.jaspersoft.ireport.designer.undo.ObjectPropertyUndoableEdit;
import java.awt.Color;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import net.sf.jasperreports.chartthemes.simple.ColorProvider;
import net.sf.jasperreports.chartthemes.simple.PlotSettings;
import net.sf.jasperreports.engine.base.JRBaseChartPlot;
    
    
/**
 *
 */
public final class PlotSeriesColorsProperty extends SeriesColorsProperty 
{
    private final PlotSettings settings;

    @SuppressWarnings("unchecked")
    public PlotSeriesColorsProperty(PlotSettings settings)
    {
        super(JRBaseChartPlot.PROPERTY_SERIES_COLORS, 
              "Series Colors",
              "Series Colors");
        this.settings = settings;
    }

    @Override
    public Object getValue()
    {
        SortedSet set = new TreeSet();
        if (settings.getSeriesColorSequence() != null)
        {
            for(int i = 0; i < settings.getSeriesColorSequence().size(); i++)
            {
                set.add(
                    new JRBaseChartPlot.JRBaseSeriesColor(i, ((ColorProvider)settings.getSeriesColorSequence().get(i)).getColor())
                    
                    );
            }
        }
        return set;
    }

    @Override
    public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        setPropertyValue(val);
    }

    @SuppressWarnings("unchecked")
    private void setPropertyValue(Object val)
    {
        if (val instanceof SortedSet)
        {
            if (val == settings.getSeriesColorSequence()) return;
            SortedSet oldValue = (SortedSet)getValue();
            SortedSet newValue =  (SortedSet)val;
            
            List colors = new ArrayList();
            if (newValue != null)
            {
                for(Iterator it = newValue.iterator(); it.hasNext();)
                {
                    colors.add(new ColorProvider(((JRBaseChartPlot.JRBaseSeriesColor)it.next()).getColor()));
                }
            }
            settings.setSeriesColorSequence(colors);
            
            ObjectPropertyUndoableEdit urob =
                    new ObjectPropertyUndoableEdit(
                        settings,
                        "SeriesColors", 
                        Collection.class,
                        oldValue,newValue);
            // Find the undoRedo manager...
            IReportManager.getInstance().addUndoableEdit(urob);
        }
    }
}
