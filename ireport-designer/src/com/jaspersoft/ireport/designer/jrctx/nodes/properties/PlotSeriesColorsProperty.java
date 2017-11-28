/*
 * iReport - Visual Designer for JasperReports.
 * Copyright (C) 2002 - 2009 Jaspersoft Corporation. All rights reserved.
 * http://www.jaspersoft.com
 *
 * Unless you have purchased a commercial license agreement from Jaspersoft,
 * the following license terms apply:
 *
 * This program is part of iReport.
 *
 * iReport is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * iReport is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with iReport. If not, see <http://www.gnu.org/licenses/>.
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
