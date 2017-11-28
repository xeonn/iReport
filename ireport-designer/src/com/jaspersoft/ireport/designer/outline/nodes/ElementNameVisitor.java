/*
 * ReportElementNodeFactory.java
 * 
 * Created on Sep 12, 2007, 7:23:23 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.outline.nodes;

import com.jaspersoft.ireport.designer.utils.Misc;
import net.sf.jasperreports.crosstabs.JRCrosstab;
import net.sf.jasperreports.engine.JRBreak;
import net.sf.jasperreports.engine.JRChart;
import net.sf.jasperreports.engine.JRElementGroup;
import net.sf.jasperreports.engine.JREllipse;
import net.sf.jasperreports.engine.JRFrame;
import net.sf.jasperreports.engine.JRImage;
import net.sf.jasperreports.engine.JRLine;
import net.sf.jasperreports.engine.JRRectangle;
import net.sf.jasperreports.engine.JRStaticText;
import net.sf.jasperreports.engine.JRSubreport;
import net.sf.jasperreports.engine.JRTextField;
import net.sf.jasperreports.engine.JRVisitable;
import net.sf.jasperreports.engine.JRVisitor;
import net.sf.jasperreports.engine.design.JasperDesign;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class ElementNameVisitor implements JRVisitor {

    private JasperDesign jasperDesign = null;
    private String name = null;
    
    
    /**
     *
     */
    public ElementNameVisitor(JasperDesign jasperDesign)
    {
        this.jasperDesign = jasperDesign;
    }
    
    
    /**
     *
     */
    public String getName(JRVisitable visitable)
    {
        visitable.visit(this);
        return name;
    }
    
    
    /**
     *
     */
    public void visitBreak(JRBreak breakElement)
    {
        if (breakElement.getType() == JRBreak.TYPE_PAGE)
            name = "Page Break";
        else
           name = "Column Break";
    }

    /**
     *
     */
    public void visitChart(JRChart chart)
    {
        switch (chart.getChartType())
        {
            case JRChart.CHART_TYPE_AREA: name = "Area"; break;
            case JRChart.CHART_TYPE_BAR: name = "Bar"; break;
            case JRChart.CHART_TYPE_BAR3D: name = "Bar 3D"; break;
            case JRChart.CHART_TYPE_BUBBLE: name = "Bubble"; break;
            case JRChart.CHART_TYPE_CANDLESTICK: name = "Candlestick"; break;
            case JRChart.CHART_TYPE_HIGHLOW: name = "High Low"; break;
            case JRChart.CHART_TYPE_LINE: name = "Line"; break;
            case JRChart.CHART_TYPE_METER: name = "Meter"; break;
            case JRChart.CHART_TYPE_MULTI_AXIS: name = "Multi Axis"; break;
            case JRChart.CHART_TYPE_PIE: name = "Pie"; break;
            case JRChart.CHART_TYPE_PIE3D: name = "Pie 3D"; break;
            case JRChart.CHART_TYPE_SCATTER: name = "Scatter"; break;
            case JRChart.CHART_TYPE_STACKEDBAR: name = "Stackedbar"; break;
            case JRChart.CHART_TYPE_STACKEDBAR3D: name = "Stackedbar 3D"; break;
            case JRChart.CHART_TYPE_THERMOMETER: name = "Thermometer"; break;
            case JRChart.CHART_TYPE_TIMESERIES: name = "Timeseries"; break;
            case JRChart.CHART_TYPE_XYAREA: name = "XY Area"; break;
            case JRChart.CHART_TYPE_XYBAR: name = "XY Bar"; break;
            case JRChart.CHART_TYPE_XYLINE: name = "XY Line"; break;
            case JRChart.CHART_TYPE_STACKEDAREA: name = "Stackedarea"; break; 
            default: name = "Unknown";
        }

        name += " Chart";
    }

    /**
     *
     */
    public void visitCrosstab(JRCrosstab crosstab)
    {
        name = "[" + crosstab.getX() + ", " + crosstab.getY() + ", " + crosstab.getWidth() + ", " + crosstab.getHeight() + "]";
    }

    /**
     *
     */
    public void visitElementGroup(JRElementGroup elementGroup)
    {
        name = null;
    }

    /**
     *
     */
    public void visitEllipse(JREllipse ellipse)
    {
        name = "[" + ellipse.getX() + ", " + ellipse.getY() + ", " + ellipse.getWidth() + ", " + ellipse.getHeight() + "]";
    }

    /**
     *
     */
    public void visitFrame(JRFrame frame)
    {
        name = "[" + frame.getX() + ", " + frame.getY() + ", " + frame.getWidth() + ", " + frame.getHeight() + "]";
    }

    /**
     *
     */
    public void visitImage(JRImage image)
    {
        name = Misc.getExpressionText( image.getExpression() );
    }

    /**
     *
     */
    public void visitLine(JRLine line)
    {
        name = "[" + line.getX() + ", " + line.getY() + ", " + line.getWidth() + ", " + line.getHeight() + "]";
    }

    /**
     *
     */
    public void visitRectangle(JRRectangle rectangle)
    {
        name = "[" + rectangle.getX() + ", " + rectangle.getY() + ", " + rectangle.getWidth() + ", " + rectangle.getHeight() + "]";
    }

    /**
     *
     */
    public void visitStaticText(JRStaticText staticText)
    {
        name = staticText.getText();
    }

    /**
     *
     */
    public void visitSubreport(JRSubreport subreport)
    {
        name = Misc.getExpressionText( subreport.getExpression() );
    }

    /**
     *
     */
    public void visitTextField(JRTextField textField)
    {
        name = Misc.getExpressionText( textField.getExpression() );
    }
}
