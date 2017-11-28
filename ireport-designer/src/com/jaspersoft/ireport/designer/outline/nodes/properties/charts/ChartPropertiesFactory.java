/*
 * GraphicElementPropertiesFactory.java
 * 
 * Created on 5-nov-2007, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.outline.nodes.properties.charts;

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
        propertySet.put(new EvaluationTimeProperty( element,dataset));
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
