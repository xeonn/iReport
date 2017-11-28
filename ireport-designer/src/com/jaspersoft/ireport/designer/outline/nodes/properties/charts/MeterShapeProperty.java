/*
 * GraphicElementPropertiesFactory.java
 * 
 * Created on 5-nov-2007, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.outline.nodes.properties.charts;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.ModelUtils;
import com.jaspersoft.ireport.designer.editor.ExpressionContext;
import com.jaspersoft.ireport.designer.sheet.ExpressionProperty;
import com.jaspersoft.ireport.designer.sheet.JRFontProperty;
import com.jaspersoft.ireport.designer.sheet.MeterIntervalsProperty;
import com.jaspersoft.ireport.designer.sheet.SeriesColorsProperty;
import com.jaspersoft.ireport.designer.sheet.Tag;
import com.jaspersoft.ireport.designer.sheet.editors.ComboBoxPropertyEditor;
import com.jaspersoft.ireport.designer.undo.ObjectPropertyUndoableEdit;
import java.awt.Color;
import java.beans.PropertyEditor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import net.sf.jasperreports.charts.JRDataRange;
import net.sf.jasperreports.charts.JRValueDisplay;
import net.sf.jasperreports.charts.design.JRDesignAreaPlot;
import net.sf.jasperreports.charts.design.JRDesignBar3DPlot;
import net.sf.jasperreports.charts.design.JRDesignBarPlot;
import net.sf.jasperreports.charts.design.JRDesignBubblePlot;
import net.sf.jasperreports.charts.design.JRDesignCandlestickPlot;
import net.sf.jasperreports.charts.design.JRDesignDataRange;
import net.sf.jasperreports.charts.design.JRDesignHighLowPlot;
import net.sf.jasperreports.charts.design.JRDesignLinePlot;
import net.sf.jasperreports.charts.design.JRDesignMeterPlot;
import net.sf.jasperreports.charts.design.JRDesignPie3DPlot;
import net.sf.jasperreports.charts.design.JRDesignPiePlot;
import net.sf.jasperreports.charts.design.JRDesignScatterPlot;
import net.sf.jasperreports.charts.design.JRDesignThermometerPlot;
import net.sf.jasperreports.charts.design.JRDesignTimeSeriesPlot;
import net.sf.jasperreports.charts.design.JRDesignValueDisplay;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRFont;
import net.sf.jasperreports.engine.JRGroup;
import net.sf.jasperreports.engine.base.JRBaseChart;
import net.sf.jasperreports.engine.base.JRBaseChartPlot;
import net.sf.jasperreports.engine.design.JRDesignChart;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignFont;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.ErrorManager;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.util.Exceptions;
    
    
/**
 *  Class to manage the JRDesignChart.PROPERTY_TITLE_POSITION property
 */
public final class MeterShapeProperty extends PropertySupport
{
        private final JRDesignMeterPlot element;
        private ComboBoxPropertyEditor editor;
        
        @SuppressWarnings("unchecked")
        public MeterShapeProperty(JRDesignMeterPlot element)
        {
            // TODO: Replace WhenNoDataType with the right constant
            super(JRDesignMeterPlot.PROPERTY_SHAPE,Byte.class, "Shape", "Shape", true, true);
            this.element = element;
            setValue("suppressCustomEditor", Boolean.TRUE);
        }

        @Override
        @SuppressWarnings("unchecked")
        public PropertyEditor getPropertyEditor() {

            if (editor == null)
            {
                java.util.ArrayList l = new java.util.ArrayList();
                l.add(new Tag(new Byte(JRDesignMeterPlot.SHAPE_PIE), "Pie"));
                l.add(new Tag(new Byte(JRDesignMeterPlot.SHAPE_CIRCLE), "Circle"));
                l.add(new Tag(new Byte(JRDesignMeterPlot.SHAPE_CHORD), "Chord"));
                editor = new ComboBoxPropertyEditor(false, l);
            }
            return editor;
        }
        
        public Object getValue() throws IllegalAccessException, InvocationTargetException {
            return new Byte(element.getShape());
        }

        public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            setPropertyValue(val);
        }

    
        private void setPropertyValue(Object val)
        {
            if (val instanceof Byte)
            {
            try {
                byte oldValue = element.getShape();
                byte newValue = ((Byte) val).byteValue();
                element.setShape(newValue);

                ObjectPropertyUndoableEdit urob = new ObjectPropertyUndoableEdit(element, "Shape", Byte.TYPE, oldValue, newValue);
                // Find the undoRedo manager...
                IReportManager.getInstance().addUndoableEdit(urob);
            } catch (JRException ex) {
                Exceptions.printStackTrace(ex);
            }
            }
        }
        
        @Override
        public boolean isDefaultValue() {
            return element.getShape() == JRDesignMeterPlot.SHAPE_PIE;
        }

        @Override
        public void restoreDefaultValue() throws IllegalAccessException, InvocationTargetException {
            setValue(new Byte( JRDesignMeterPlot.SHAPE_PIE) );
        }

        @Override
        public boolean supportsDefaultValue() {
            return true;
        }
}
