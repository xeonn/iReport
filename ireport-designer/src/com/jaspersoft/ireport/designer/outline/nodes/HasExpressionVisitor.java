/*
 * ReportElementNodeFactory.java
 * 
 * Created on Sep 12, 2007, 7:23:23 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.outline.nodes;

import com.jaspersoft.ireport.designer.errorhandler.IRExpressionCollector;
import net.sf.jasperreports.crosstabs.JRCrosstab;
import net.sf.jasperreports.engine.JRBreak;
import net.sf.jasperreports.engine.JRChart;
import net.sf.jasperreports.engine.JRComponentElement;
import net.sf.jasperreports.engine.JRElementGroup;
import net.sf.jasperreports.engine.JREllipse;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRFrame;
import net.sf.jasperreports.engine.JRGenericElement;
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
 * This visitor look for a particular JRExpression. If it is found, the parent
 * element is returned.
 * 
 * @author Giulio Toffoli
 */
public class HasExpressionVisitor implements JRVisitor {

    private JasperDesign jasperDesign = null;
    private JRExpression expression = null;
    private IRExpressionCollector collector = null;
    
    
    /**
     *
     */
    public HasExpressionVisitor(JasperDesign jasperDesign, JRExpression expression)
    {
        this.jasperDesign = jasperDesign;
        this.expression = expression;
        collector = new IRExpressionCollector(jasperDesign);

    }
    
    
    /**
     *
     */
    public boolean hasExpression(JRVisitable visitable)
    {
        visitable.visit(this);
        return collector.getExpressions().contains(getExpression());
    }
    
    
    
    
    /**
     *
     */
    public void visitBreak(JRBreak breakElement)
    {
        collector.collect(breakElement);
    }

    /**
     *
     */
    public void visitChart(JRChart chart)
    {
        collector.collect(chart);
    }

    /**
     *
     */
    public void visitCrosstab(JRCrosstab crosstab)
    {
        collector.collect(crosstab);
    }

    /**
     *
     */
    public void visitElementGroup(JRElementGroup elementGroup)
    {

    }

    /**
     *
     */
    public void visitEllipse(JREllipse ellipse)
    {
        collector.collect(ellipse);
    }

    /**
     *
     */
    public void visitFrame(JRFrame frame)
    {
        collector.collect(frame);
    }

    /**
     *
     */
    public void visitImage(JRImage image)
    {
        collector.collect(image);
    }

    /**
     *
     */
    public void visitLine(JRLine line)
    {
        collector.collect(line);
    }

    /**
     *
     */
    public void visitRectangle(JRRectangle rectangle)
    {
        collector.collect(rectangle);
    }

    /**
     *
     */
    public void visitStaticText(JRStaticText staticText)
    {
        collector.collect(staticText);
    }

    /**
     *
     */
    public void visitSubreport(JRSubreport subreport)
    {
        collector.collect(subreport);
    }

    /**
     *
     */
    public void visitTextField(JRTextField textField)
    {
        collector.collect(textField);
    }

    public JRExpression getExpression() {
        return expression;
    }

    public void setExpression(JRExpression expression) {
        this.expression = expression;
    }

    public void visitComponentElement(JRComponentElement componentElement) {
        collector.collect(componentElement);
    }

    public void visitGenericElement(JRGenericElement genericElement) {
        collector.collect(genericElement);
    }

    
}
