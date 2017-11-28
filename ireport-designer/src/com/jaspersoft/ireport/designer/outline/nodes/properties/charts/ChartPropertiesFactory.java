/*
 * GraphicElementPropertiesFactory.java
 * 
 * Created on 5-nov-2007, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.outline.nodes.properties.charts;

import com.jaspersoft.ireport.designer.sheet.properties.charts.Pie3DDepthFactorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.MeterTickIntervalProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.PlotBackgroundAlphaProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.PlotForegroundAlphaProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.Bar3DXOffsetProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.Bar3DYOffsetProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.PlotLabelRotationProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.PieCircularProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.LegendPositionProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.MeterMeterAngleProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.ThermometerValueLocationProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.MeterShapeProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.Pie3DCircularProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.BubbleScaleTypeProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.ImageEvaluationTimeProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.LegendFontProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.BarShowLabelsProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.TimeSeriesShowShapesProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.LineShowShapesProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.CandlestickShowVolumeProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.LineShowLinesProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.ShowLegendProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.ScatterShowShapesProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.ScatterShowLinesProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.HighLowShowCloseTicksProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.BarShowTickLabelsProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.TimeSeriesShowLinesProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.ThermometerShowValueLinesProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.HighLowShowOpenTicksProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.BarShowTickMarksProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.Bar3DShowLabelsProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.ThermometerValueMaskProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.LineValueAxisTickLabelMaskProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.MeterValueMaskProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.BubbleXAxisTickLabelMaskProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.Bar3DValueAxisTickLabelMaskProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.ScatterYAxisTickLabelMaskProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.ScatterXAxisTickLabelMaskProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.BarCategoryAxisTickLabelMaskProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.TimeSeriesTimeAxisTickLabelMaskProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.Bar3DCategoryAxisTickLabelMaskProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.BubbleYAxisTickLabelMaskProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.AreaValueAxisTickLabelMaskProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.HighLowValueAxisTickLabelMaskProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.CandlestickTimeAxisTickLabelMaskProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.BarValueAxisTickLabelMaskProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.CandlestickValueAxisTickLabelMaskProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.TimeSeriesValueAxisTickLabelMaskProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.HighLowTimeAxisTickLabelMaskProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.LineCategoryAxisTickLabelMaskProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.AreaCategoryAxisTickLabelMaskProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.LineValueAxisTickLabelFontProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.Bar3DCategoryAxisLabelFontProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.BarCategoryAxisTickLabelFontProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.HighLowTimeAxisLabelFontProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.BarValueAxisTickLabelFontProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.HighLowValueAxisTickLabelFontProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.BubbleXAxisTickLabelFontProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.TimeSeriesValueAxisLabelFontProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.BubbleYAxisTickLabelFontProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.TimeSeriesTimeAxisLabelFontProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.CandlestickValueAxisLabelFontProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.BubbleYAxisLabelFontProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.LineCategoryAxisLabelFontProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.TimeSeriesValueAxisTickLabelFontProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.AreaValueAxisTickLabelFontProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.Bar3DCategoryAxisTickLabelFontProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.HighLowTimeAxisTickLabelFontProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.ScatterYAxisLabelFontProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.ScatterXAxisTickLabelFontProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.ScatterYAxisTickLabelFontProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.Bar3DValueAxisTickLabelFontProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.TimeSeriesTimeAxisTickLabelFontProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.ThermometerValueFontProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.AreaValueAxisLabelFontProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.SubtitleFontProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.Bar3DValueAxisLabelFontProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.HighLowValueAxisLabelFontProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.CandlestickTimeAxisTickLabelFontProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.CandlestickTimeAxisLabelFontProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.CandlestickValueAxisTickLabelFontProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.LineValueAxisLabelFontProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.BarCategoryAxisLabelFontProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.AreaCategoryAxisTickLabelFontProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.TitleFontProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.BarValueAxisLabelFontProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.BubbleXAxisLabelFontProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.ScatterXAxisLabelFontProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.LineCategoryAxisTickLabelFontProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.MeterValueFontProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.Bar3DCategoryAxisTickLabelColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.AreaCategoryAxisLabelFontProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.TitlePositionProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.LegendBackgroundColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.ScatterXAxisTickLabelColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.CandlestickTimeAxisTickLabelColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.LineValueAxisTickLabelColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.BarValueAxisLineColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.LegendColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.HighLowTimeAxisLineColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.BubbleYAxisLineColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.TimeSeriesValueAxisLineColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.BarValueAxisTickLabelColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.ScatterYAxisTickLabelColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.LineCategoryAxisLineColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.ScatterXAxisLabelColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.TimeSeriesTimeAxisLineColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.MeterMeterBackgroundColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.HighLowValueAxisLabelColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.BubbleXAxisLabelColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.ScatterYAxisLineColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.HighLowTimeAxisLabelColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.ScatterYAxisLabelColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.BarCategoryAxisTickLabelColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.BarCategoryAxisLabelColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.MeterValueColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.Bar3DValueAxisLineColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.CandlestickTimeAxisLineColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.BubbleYAxisLabelColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.LineCategoryAxisTickLabelColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.MeterTickColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.LineCategoryAxisLabelColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.TimeSeriesTimeAxisLabelColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.Bar3DValueAxisLabelColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.HighLowValueAxisLineColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.Bar3DValueAxisTickLabelColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.CandlestickValueAxisTickLabelColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.LineValueAxisLabelColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.ThermometerMercuryColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.MeterNeedleColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.CandlestickValueAxisLabelColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.TimeSeriesValueAxisLabelColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.CandlestickValueAxisLineColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.BubbleXAxisLineColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.HighLowValueAxisTickLabelColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.CandlestickTimeAxisLabelColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.SubtitleColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.BubbleYAxisTickLabelColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.BarValueAxisLabelColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.ThermometerValueColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.BarCategoryAxisLineColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.LineValueAxisLineColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.TitleColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.ScatterXAxisLineColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.TimeSeriesValueAxisTickLabelColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.HighLowTimeAxisTickLabelColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.TimeSeriesTimeAxisTickLabelColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.BubbleXAxisTickLabelColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.Bar3DCategoryAxisLineColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.Bar3DCategoryAxisLabelColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.AreaValueAxisTickLabelColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.AreaValueAxisLineColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.AreaValueAxisLabelColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.AreaCategoryAxisTickLabelColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.AreaCategoryAxisLineColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.AreaCategoryAxisLabelColorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.ThermometerMediumDataRangeLowExpressionProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.ThermometerLowDataRangeLowExpressionProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.ThermometerHighDataRangeHighExpressionProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.ThermometerHighDataRangeLowExpressionProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.ThermometerDataRangeLowExpressionProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.ThermometerDataRangeHighExpressionProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.ThermometerLowDataRangeHighExpressionProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.ThermometerMediumDataRangeHighExpressionProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.MeterDataRangeLowExpressionProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.MeterDataRangeHighExpressionProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.TitleExpressionProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.SubtitleExpressionProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.TimeSeriesTimeAxisLabelExpressionProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.HighLowTimeAxisLabelExpressionProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.CandlestickTimeAxisLabelExpressionProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.CandlestickValueAxisLabelExpressionProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.HighLowValueAxisLabelExpressionProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.TimeSeriesValueAxisLabelExpressionProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.BubbleYAxisLabelExpressionProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.ScatterYAxisLabelExpressionProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.BubbleXAxisLabelExpressionProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.ScatterXAxisLabelExpressionProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.BarValueAxisLabelExpressionProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.Bar3DValueAxisLabelExpressionProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.LineValueAxisLabelExpressionProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.BarCategoryAxisLabelExpressionProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.LineCategoryAxisLabelExpressionProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.Bar3DCategoryAxisLabelExpressionProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.AreaValueAxisLabelExpressionProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.AreaCategoryAxisLabelExpressionProperty;
import com.jaspersoft.ireport.designer.sheet.properties.charts.RenderTypeProperty;
import com.jaspersoft.ireport.designer.ModelUtils;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.charts.design.JRDesignAreaPlot;
import net.sf.jasperreports.charts.design.JRDesignBar3DPlot;
import net.sf.jasperreports.charts.design.JRDesignBarPlot;
import net.sf.jasperreports.charts.design.JRDesignBubblePlot;
import net.sf.jasperreports.charts.design.JRDesignCandlestickPlot;
import net.sf.jasperreports.charts.design.JRDesignHighLowPlot;
import net.sf.jasperreports.charts.design.JRDesignLinePlot;
import net.sf.jasperreports.charts.design.JRDesignMeterPlot;
import net.sf.jasperreports.charts.design.JRDesignPie3DPlot;
import net.sf.jasperreports.charts.design.JRDesignPiePlot;
import net.sf.jasperreports.charts.design.JRDesignScatterPlot;
import net.sf.jasperreports.charts.design.JRDesignThermometerPlot;
import net.sf.jasperreports.charts.design.JRDesignTimeSeriesPlot;
import net.sf.jasperreports.engine.base.JRBaseChartPlot;
import net.sf.jasperreports.engine.design.JRDesignChart;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.nodes.Sheet;

/**
 *
 * @author gtoffoli
 */
public class ChartPropertiesFactory {

    /**
     * Get the ChartElement properties...
     */
    public static List<Sheet.Set> getPropertySets(JRDesignChart element, JasperDesign jd)
    {
        JRDesignDataset dataset = ModelUtils.getElementDataset(element, jd);
        
        List<Sheet.Set> list = new ArrayList<Sheet.Set>();
        Sheet.Set propertySet = Sheet.createPropertiesSet();
        propertySet.setName("CHART_ELEMENT_PROPERTIES");
        propertySet.setDisplayName("Common chart properties");
        propertySet.put(new ImageEvaluationTimeProperty( element,dataset));
        propertySet.put(new EvaluationGroupProperty( element,dataset));
        propertySet.put(new TitleExpressionProperty(element,dataset));
        propertySet.put(new TitleFontProperty(element, jd));
        propertySet.put(new TitleColorProperty(element));
        propertySet.put(new TitlePositionProperty(element));
        propertySet.put(new SubtitleExpressionProperty(element,dataset));
        propertySet.put(new SubtitleFontProperty(element, jd));
        propertySet.put(new SubtitleColorProperty(element));
        propertySet.put(new ShowLegendProperty(element));
        propertySet.put(new LegendFontProperty(element, jd));
        propertySet.put(new LegendColorProperty(element));
        propertySet.put(new LegendBackgroundColorProperty(element));
        propertySet.put(new LegendPositionProperty(element));
        propertySet.put(new CustomizerClassProperty(element));
        propertySet.put(new RenderTypeProperty(element));
        
        
        // Common plot properties
        JRBaseChartPlot plot = (JRBaseChartPlot)element.getPlot();
        
        propertySet.put(new PlotBackgroundAlphaProperty(plot));
        propertySet.put(new PlotForegroundAlphaProperty(plot));
        propertySet.put(new PlotLabelRotationProperty(plot));
        propertySet.put(new PlotSeriesColorsProperty(plot));
        
        list.add(propertySet);
        
        list.add(getPlotProperties(plot,element,jd,dataset));
        
        
        
        return list;
    }
    
    public static Sheet.Set getPlotProperties(JRBaseChartPlot plot, 
                                              JRDesignChart element,
                                              JasperDesign jd,
                                              JRDesignDataset dataset)
    {
        Sheet.Set propertySet = Sheet.createPropertiesSet();
        propertySet.setName("CHART_PLOT_PROPERTIES");
        
        if (plot instanceof JRDesignPiePlot)
        {
            propertySet.setDisplayName("PiePlot properties");
            propertySet.put(new PieCircularProperty((JRDesignPiePlot)plot));
            
            
        }
        else if (plot instanceof JRDesignPie3DPlot)
        {
            propertySet.setDisplayName("Pie3DPlot properties");
            propertySet.put(new Pie3DCircularProperty((JRDesignPie3DPlot)plot));
            propertySet.put(new Pie3DDepthFactorProperty((JRDesignPie3DPlot)plot));
        }
        else if (plot instanceof JRDesignBarPlot)
        {
            propertySet.setDisplayName("BarPlot properties");
            propertySet.put(new BarShowLabelsProperty((JRDesignBarPlot)plot));
            propertySet.put(new BarShowTickMarksProperty((JRDesignBarPlot)plot));
            propertySet.put(new BarShowTickLabelsProperty((JRDesignBarPlot)plot));
            propertySet.put(new BarCategoryAxisLabelExpressionProperty((JRDesignBarPlot)plot, dataset));
            propertySet.put(new BarCategoryAxisLabelFontProperty((JRDesignBarPlot)plot, jd));
            propertySet.put(new BarCategoryAxisLabelColorProperty((JRDesignBarPlot)plot));
            propertySet.put(new BarCategoryAxisTickLabelMaskProperty((JRDesignBarPlot)plot));
            propertySet.put(new BarCategoryAxisTickLabelFontProperty((JRDesignBarPlot)plot, jd));
            propertySet.put(new BarCategoryAxisTickLabelColorProperty((JRDesignBarPlot)plot));
            propertySet.put(new BarCategoryAxisLineColorProperty((JRDesignBarPlot)plot));
            propertySet.put(new BarValueAxisLabelExpressionProperty((JRDesignBarPlot)plot, dataset));
            propertySet.put(new BarValueAxisLabelFontProperty((JRDesignBarPlot)plot, jd));
            propertySet.put(new BarValueAxisLabelColorProperty((JRDesignBarPlot)plot));
            propertySet.put(new BarValueAxisTickLabelMaskProperty((JRDesignBarPlot)plot));
            propertySet.put(new BarValueAxisTickLabelFontProperty((JRDesignBarPlot)plot, jd));
            propertySet.put(new BarValueAxisTickLabelColorProperty((JRDesignBarPlot)plot));
            propertySet.put(new BarValueAxisLineColorProperty((JRDesignBarPlot)plot));
            
        }
        else if (plot instanceof JRDesignBar3DPlot)
        {
            propertySet.setDisplayName("Bar3DPlot properties");
            propertySet.put(new Bar3DShowLabelsProperty((JRDesignBar3DPlot)plot));
            propertySet.put(new Bar3DXOffsetProperty((JRDesignBar3DPlot)plot));
            propertySet.put(new Bar3DYOffsetProperty((JRDesignBar3DPlot)plot));
            propertySet.put(new Bar3DCategoryAxisLabelExpressionProperty((JRDesignBar3DPlot)plot, dataset));
            propertySet.put(new Bar3DCategoryAxisLabelFontProperty((JRDesignBar3DPlot)plot, jd));
            propertySet.put(new Bar3DCategoryAxisLabelColorProperty((JRDesignBar3DPlot)plot));
            propertySet.put(new Bar3DCategoryAxisTickLabelMaskProperty((JRDesignBar3DPlot)plot));
            propertySet.put(new Bar3DCategoryAxisTickLabelFontProperty((JRDesignBar3DPlot)plot, jd));
            propertySet.put(new Bar3DCategoryAxisTickLabelColorProperty((JRDesignBar3DPlot)plot));
            propertySet.put(new Bar3DCategoryAxisLineColorProperty((JRDesignBar3DPlot)plot));
            propertySet.put(new Bar3DValueAxisLabelExpressionProperty((JRDesignBar3DPlot)plot, dataset));
            propertySet.put(new Bar3DValueAxisLabelFontProperty((JRDesignBar3DPlot)plot, jd));
            propertySet.put(new Bar3DValueAxisLabelColorProperty((JRDesignBar3DPlot)plot));
            propertySet.put(new Bar3DValueAxisTickLabelMaskProperty((JRDesignBar3DPlot)plot));
            propertySet.put(new Bar3DValueAxisTickLabelFontProperty((JRDesignBar3DPlot)plot, jd));
            propertySet.put(new Bar3DValueAxisTickLabelColorProperty((JRDesignBar3DPlot)plot));
            propertySet.put(new Bar3DValueAxisLineColorProperty((JRDesignBar3DPlot)plot));
            
        }
        else if (plot instanceof JRDesignLinePlot)
        {
            propertySet.setDisplayName("LinePlot properties");
            propertySet.put(new LineShowLinesProperty((JRDesignLinePlot)plot));
            propertySet.put(new LineShowShapesProperty((JRDesignLinePlot)plot));
            propertySet.put(new LineCategoryAxisLabelExpressionProperty((JRDesignLinePlot)plot, dataset));
            propertySet.put(new LineCategoryAxisLabelFontProperty((JRDesignLinePlot)plot, jd));
            propertySet.put(new LineCategoryAxisLabelColorProperty((JRDesignLinePlot)plot));
            propertySet.put(new LineCategoryAxisTickLabelMaskProperty((JRDesignLinePlot)plot));
            propertySet.put(new LineCategoryAxisTickLabelFontProperty((JRDesignLinePlot)plot, jd));
            propertySet.put(new LineCategoryAxisTickLabelColorProperty((JRDesignLinePlot)plot));
            propertySet.put(new LineCategoryAxisLineColorProperty((JRDesignLinePlot)plot));
            propertySet.put(new LineValueAxisLabelExpressionProperty((JRDesignLinePlot)plot, dataset));
            propertySet.put(new LineValueAxisLabelFontProperty((JRDesignLinePlot)plot, jd));
            propertySet.put(new LineValueAxisLabelColorProperty((JRDesignLinePlot)plot));
            propertySet.put(new LineValueAxisTickLabelMaskProperty((JRDesignLinePlot)plot));
            propertySet.put(new LineValueAxisTickLabelFontProperty((JRDesignLinePlot)plot, jd));
            propertySet.put(new LineValueAxisTickLabelColorProperty((JRDesignLinePlot)plot));
            propertySet.put(new LineValueAxisLineColorProperty((JRDesignLinePlot)plot));
        }
        else if (plot instanceof JRDesignAreaPlot)
        {
            propertySet.setDisplayName("AreaPlot properties");
            propertySet.put(new AreaCategoryAxisLabelExpressionProperty((JRDesignAreaPlot)plot, dataset));
            propertySet.put(new AreaCategoryAxisLabelFontProperty((JRDesignAreaPlot)plot, jd));
            propertySet.put(new AreaCategoryAxisLabelColorProperty((JRDesignAreaPlot)plot));
            propertySet.put(new AreaCategoryAxisTickLabelMaskProperty((JRDesignAreaPlot)plot));
            propertySet.put(new AreaCategoryAxisTickLabelFontProperty((JRDesignAreaPlot)plot, jd));
            propertySet.put(new AreaCategoryAxisTickLabelColorProperty((JRDesignAreaPlot)plot));
            propertySet.put(new AreaCategoryAxisLineColorProperty((JRDesignAreaPlot)plot));
            propertySet.put(new AreaValueAxisLabelExpressionProperty((JRDesignAreaPlot)plot, dataset));
            propertySet.put(new AreaValueAxisLabelFontProperty((JRDesignAreaPlot)plot, jd));
            propertySet.put(new AreaValueAxisLabelColorProperty((JRDesignAreaPlot)plot));
            propertySet.put(new AreaValueAxisTickLabelMaskProperty((JRDesignAreaPlot)plot));
            propertySet.put(new AreaValueAxisTickLabelFontProperty((JRDesignAreaPlot)plot, jd));
            propertySet.put(new AreaValueAxisTickLabelColorProperty((JRDesignAreaPlot)plot));
            propertySet.put(new AreaValueAxisLineColorProperty((JRDesignAreaPlot)plot));
            
        }
        else if (plot instanceof JRDesignScatterPlot)
        {
            propertySet.setDisplayName("ScatterPlot properties");
            propertySet.put(new ScatterShowLinesProperty((JRDesignScatterPlot)plot));
            propertySet.put(new ScatterShowShapesProperty((JRDesignScatterPlot)plot));
            propertySet.put(new ScatterXAxisLabelExpressionProperty((JRDesignScatterPlot)plot, dataset));
            propertySet.put(new ScatterXAxisLabelFontProperty((JRDesignScatterPlot)plot, jd));
            propertySet.put(new ScatterXAxisLabelColorProperty((JRDesignScatterPlot)plot));
            propertySet.put(new ScatterXAxisTickLabelMaskProperty((JRDesignScatterPlot)plot));
            propertySet.put(new ScatterXAxisTickLabelFontProperty((JRDesignScatterPlot)plot, jd));
            propertySet.put(new ScatterXAxisTickLabelColorProperty((JRDesignScatterPlot)plot));
            propertySet.put(new ScatterXAxisLineColorProperty((JRDesignScatterPlot)plot));
            propertySet.put(new ScatterYAxisLabelExpressionProperty((JRDesignScatterPlot)plot, dataset));
            propertySet.put(new ScatterYAxisLabelFontProperty((JRDesignScatterPlot)plot, jd));
            propertySet.put(new ScatterYAxisLabelColorProperty((JRDesignScatterPlot)plot));
            propertySet.put(new ScatterYAxisTickLabelMaskProperty((JRDesignScatterPlot)plot));
            propertySet.put(new ScatterYAxisTickLabelFontProperty((JRDesignScatterPlot)plot, jd));
            propertySet.put(new ScatterYAxisTickLabelColorProperty((JRDesignScatterPlot)plot));
            propertySet.put(new ScatterYAxisLineColorProperty((JRDesignScatterPlot)plot));
            
        }
        else if (plot instanceof JRDesignBubblePlot)
        {
            propertySet.setDisplayName("BubblePlot properties");
            propertySet.put(new BubbleScaleTypeProperty((JRDesignBubblePlot)plot));
            propertySet.put(new BubbleXAxisLabelExpressionProperty((JRDesignBubblePlot)plot, dataset));
            propertySet.put(new BubbleXAxisLabelFontProperty((JRDesignBubblePlot)plot, jd));
            propertySet.put(new BubbleXAxisLabelColorProperty((JRDesignBubblePlot)plot));
            propertySet.put(new BubbleXAxisTickLabelMaskProperty((JRDesignBubblePlot)plot));
            propertySet.put(new BubbleXAxisTickLabelFontProperty((JRDesignBubblePlot)plot, jd));
            propertySet.put(new BubbleXAxisTickLabelColorProperty((JRDesignBubblePlot)plot));
            propertySet.put(new BubbleXAxisLineColorProperty((JRDesignBubblePlot)plot));
            propertySet.put(new BubbleYAxisLabelExpressionProperty((JRDesignBubblePlot)plot, dataset));
            propertySet.put(new BubbleYAxisLabelFontProperty((JRDesignBubblePlot)plot, jd));
            propertySet.put(new BubbleYAxisLabelColorProperty((JRDesignBubblePlot)plot));
            propertySet.put(new BubbleYAxisTickLabelMaskProperty((JRDesignBubblePlot)plot));
            propertySet.put(new BubbleYAxisTickLabelFontProperty((JRDesignBubblePlot)plot, jd));
            propertySet.put(new BubbleYAxisTickLabelColorProperty((JRDesignBubblePlot)plot));
            propertySet.put(new BubbleYAxisLineColorProperty((JRDesignBubblePlot)plot));
            
        }
        else if (plot instanceof JRDesignTimeSeriesPlot)
        {
            propertySet.setDisplayName("TimeSeriesPlot properties");
            propertySet.put(new TimeSeriesShowLinesProperty((JRDesignTimeSeriesPlot)plot));
            propertySet.put(new TimeSeriesShowShapesProperty((JRDesignTimeSeriesPlot)plot));
            propertySet.put(new TimeSeriesTimeAxisLabelExpressionProperty((JRDesignTimeSeriesPlot)plot, dataset));
            propertySet.put(new TimeSeriesTimeAxisLabelFontProperty((JRDesignTimeSeriesPlot)plot, jd));
            propertySet.put(new TimeSeriesTimeAxisLabelColorProperty((JRDesignTimeSeriesPlot)plot));
            propertySet.put(new TimeSeriesTimeAxisTickLabelMaskProperty((JRDesignTimeSeriesPlot)plot));
            propertySet.put(new TimeSeriesTimeAxisTickLabelFontProperty((JRDesignTimeSeriesPlot)plot, jd));
            propertySet.put(new TimeSeriesTimeAxisTickLabelColorProperty((JRDesignTimeSeriesPlot)plot));
            propertySet.put(new TimeSeriesTimeAxisLineColorProperty((JRDesignTimeSeriesPlot)plot));
            propertySet.put(new TimeSeriesValueAxisLabelExpressionProperty((JRDesignTimeSeriesPlot)plot, dataset));
            propertySet.put(new TimeSeriesValueAxisLabelFontProperty((JRDesignTimeSeriesPlot)plot, jd));
            propertySet.put(new TimeSeriesValueAxisLabelColorProperty((JRDesignTimeSeriesPlot)plot));
            propertySet.put(new TimeSeriesValueAxisTickLabelMaskProperty((JRDesignTimeSeriesPlot)plot));
            propertySet.put(new TimeSeriesValueAxisTickLabelFontProperty((JRDesignTimeSeriesPlot)plot, jd));
            propertySet.put(new TimeSeriesValueAxisTickLabelColorProperty((JRDesignTimeSeriesPlot)plot));
            propertySet.put(new TimeSeriesValueAxisLineColorProperty((JRDesignTimeSeriesPlot)plot));
        }
        else if (plot instanceof JRDesignHighLowPlot)
        {
            propertySet.setDisplayName("HighLowPlot properties");
            propertySet.put(new HighLowShowCloseTicksProperty((JRDesignHighLowPlot)plot));
            propertySet.put(new HighLowShowOpenTicksProperty((JRDesignHighLowPlot)plot));
            propertySet.put(new HighLowTimeAxisLabelExpressionProperty((JRDesignHighLowPlot)plot, dataset));
            propertySet.put(new HighLowTimeAxisLabelFontProperty((JRDesignHighLowPlot)plot, jd));
            propertySet.put(new HighLowTimeAxisLabelColorProperty((JRDesignHighLowPlot)plot));
            propertySet.put(new HighLowTimeAxisTickLabelMaskProperty((JRDesignHighLowPlot)plot));
            propertySet.put(new HighLowTimeAxisTickLabelFontProperty((JRDesignHighLowPlot)plot, jd));
            propertySet.put(new HighLowTimeAxisTickLabelColorProperty((JRDesignHighLowPlot)plot));
            propertySet.put(new HighLowTimeAxisLineColorProperty((JRDesignHighLowPlot)plot));
            propertySet.put(new HighLowValueAxisLabelExpressionProperty((JRDesignHighLowPlot)plot, dataset));
            propertySet.put(new HighLowValueAxisLabelFontProperty((JRDesignHighLowPlot)plot, jd));
            propertySet.put(new HighLowValueAxisLabelColorProperty((JRDesignHighLowPlot)plot));
            propertySet.put(new HighLowValueAxisTickLabelMaskProperty((JRDesignHighLowPlot)plot));
            propertySet.put(new HighLowValueAxisTickLabelFontProperty((JRDesignHighLowPlot)plot, jd));
            propertySet.put(new HighLowValueAxisTickLabelColorProperty((JRDesignHighLowPlot)plot));
            propertySet.put(new HighLowValueAxisLineColorProperty((JRDesignHighLowPlot)plot));
            
        }
        else if (plot instanceof JRDesignCandlestickPlot)
        {
            propertySet.setDisplayName("CandlestickPlot properties");
            propertySet.put(new CandlestickShowVolumeProperty((JRDesignCandlestickPlot)plot));
            propertySet.put(new CandlestickTimeAxisLabelExpressionProperty((JRDesignCandlestickPlot)plot, dataset));
            propertySet.put(new CandlestickTimeAxisLabelFontProperty((JRDesignCandlestickPlot)plot, jd));
            propertySet.put(new CandlestickTimeAxisLabelColorProperty((JRDesignCandlestickPlot)plot));
            propertySet.put(new CandlestickTimeAxisTickLabelMaskProperty((JRDesignCandlestickPlot)plot));
            propertySet.put(new CandlestickTimeAxisTickLabelFontProperty((JRDesignCandlestickPlot)plot, jd));
            propertySet.put(new CandlestickTimeAxisTickLabelColorProperty((JRDesignCandlestickPlot)plot));
            propertySet.put(new CandlestickTimeAxisLineColorProperty((JRDesignCandlestickPlot)plot));
            propertySet.put(new CandlestickValueAxisLabelExpressionProperty((JRDesignCandlestickPlot)plot, dataset));
            propertySet.put(new CandlestickValueAxisLabelFontProperty((JRDesignCandlestickPlot)plot, jd));
            propertySet.put(new CandlestickValueAxisLabelColorProperty((JRDesignCandlestickPlot)plot));
            propertySet.put(new CandlestickValueAxisTickLabelMaskProperty((JRDesignCandlestickPlot)plot));
            propertySet.put(new CandlestickValueAxisTickLabelFontProperty((JRDesignCandlestickPlot)plot, jd));
            propertySet.put(new CandlestickValueAxisTickLabelColorProperty((JRDesignCandlestickPlot)plot));
            propertySet.put(new CandlestickValueAxisLineColorProperty((JRDesignCandlestickPlot)plot));
            
        }
        else if (plot instanceof JRDesignMeterPlot)
        {
            propertySet.setDisplayName("MeterPlot properties");
            propertySet.put(new MeterShapeProperty((JRDesignMeterPlot)plot));
            propertySet.put(new MeterMeterAngleProperty((JRDesignMeterPlot)plot));
            propertySet.put(new MeterUnitsProperty((JRDesignMeterPlot)plot));
            propertySet.put(new MeterTickIntervalProperty((JRDesignMeterPlot)plot));
            propertySet.put(new MeterMeterBackgroundColorProperty((JRDesignMeterPlot)plot));
            propertySet.put(new MeterNeedleColorProperty((JRDesignMeterPlot)plot));
            propertySet.put(new MeterTickColorProperty((JRDesignMeterPlot)plot));
            propertySet.put(new MeterValueColorProperty((JRDesignMeterPlot)plot));
            propertySet.put(new MeterValueMaskProperty((JRDesignMeterPlot)plot));
            propertySet.put(new MeterValueFontProperty((JRDesignMeterPlot)plot, jd));
            propertySet.put(new MeterDataRangeLowExpressionProperty((JRDesignMeterPlot)plot, dataset));
            propertySet.put(new MeterDataRangeHighExpressionProperty((JRDesignMeterPlot)plot, dataset));
            propertySet.put(new MeterPlotIntervalsProperty((JRDesignMeterPlot)plot, dataset));
        }
        else if (plot instanceof JRDesignThermometerPlot)
        {
            propertySet.setDisplayName("ThermometerPlot properties");
            propertySet.put(new ThermometerValueLocationProperty((JRDesignThermometerPlot)plot));
            propertySet.put(new ThermometerShowValueLinesProperty((JRDesignThermometerPlot)plot));
            propertySet.put(new ThermometerMercuryColorProperty((JRDesignThermometerPlot)plot));
            propertySet.put(new ThermometerValueColorProperty((JRDesignThermometerPlot)plot));
            propertySet.put(new ThermometerValueMaskProperty((JRDesignThermometerPlot)plot));
            propertySet.put(new ThermometerValueFontProperty((JRDesignThermometerPlot)plot, jd));
            propertySet.put(new ThermometerDataRangeLowExpressionProperty((JRDesignThermometerPlot)plot, dataset));
            propertySet.put(new ThermometerDataRangeHighExpressionProperty((JRDesignThermometerPlot)plot, dataset));
            propertySet.put(new ThermometerLowDataRangeLowExpressionProperty((JRDesignThermometerPlot)plot, dataset));
            propertySet.put(new ThermometerLowDataRangeHighExpressionProperty((JRDesignThermometerPlot)plot, dataset));
            propertySet.put(new ThermometerMediumDataRangeLowExpressionProperty((JRDesignThermometerPlot)plot, dataset));
            propertySet.put(new ThermometerMediumDataRangeHighExpressionProperty((JRDesignThermometerPlot)plot, dataset));
            propertySet.put(new ThermometerHighDataRangeLowExpressionProperty((JRDesignThermometerPlot)plot, dataset));
            propertySet.put(new ThermometerHighDataRangeHighExpressionProperty((JRDesignThermometerPlot)plot, dataset));
        }
            
        return propertySet;
    }
    
}
