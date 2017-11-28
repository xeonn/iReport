/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.errorhandler;

import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.crosstabs.JRCrosstab;
import net.sf.jasperreports.engine.JRBreak;
import net.sf.jasperreports.engine.JRChart;
import net.sf.jasperreports.engine.JRDataset;
import net.sf.jasperreports.engine.JRDatasetRun;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JREllipse;
import net.sf.jasperreports.engine.JRExpressionCollector;
import net.sf.jasperreports.engine.JRFrame;
import net.sf.jasperreports.engine.JRLine;
import net.sf.jasperreports.engine.JRRectangle;
import net.sf.jasperreports.engine.JRStaticText;
import net.sf.jasperreports.engine.JRTextField;
import net.sf.jasperreports.engine.design.JRDesignChart;
import net.sf.jasperreports.engine.design.JasperDesign;

/**
 *
 * @author gtoffoli
 */
public class IRExpressionCollector extends JRExpressionCollector {

    JasperDesign jasperDesign = null;
    
    public List extraExpressions = new ArrayList();
    
    public IRExpressionCollector(JasperDesign jd)
    {
        super(null, jd);
        jasperDesign = jd;
    }

    @Override
    public List getExpressions() {
        List expressions = super.getExpressions();
        expressions.addAll(extraExpressions);
        return expressions;
    }
    
    
    
    
    @Override
    public void collect(JRChart element)
    {
            JRDesignChart chart = (JRDesignChart)element;
            super.collect((JRChart)element);
            chart.getDataset().collectExpressions(this);
            chart.getPlot().collectExpressions(this);
            JRDatasetRun datasetRun = chart.getDataset().getDatasetRun();
            
            
            if (datasetRun != null &&
                datasetRun.getDatasetName() != null)
            {
                    JRExpressionCollector collector = getCollector( (JRDataset)jasperDesign.getDatasetMap().get(datasetRun.getDatasetName()));
                    extraExpressions.addAll(collector.getExpressions());
            }
            System.out.flush();
            
    }
    
}
