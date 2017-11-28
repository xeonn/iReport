/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.charts.multiaxis;

import com.jaspersoft.ireport.designer.NotRealElementNode;
import com.jaspersoft.ireport.designer.outline.nodes.ElementNode;
import com.jaspersoft.ireport.designer.outline.nodes.ElementNodeVisitor;
import com.jaspersoft.ireport.designer.outline.nodes.properties.charts.AxisPositionTypeProperty;
import com.jaspersoft.ireport.locale.I18n;
import java.io.IOException;
import net.sf.jasperreports.charts.design.JRDesignChartAxis;
import net.sf.jasperreports.charts.design.JRDesignMultiAxisPlot;
import net.sf.jasperreports.engine.design.JRDesignChart;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.nodes.Node;
import org.openide.nodes.Sheet;
import org.openide.nodes.Sheet.Set;
import org.openide.util.Lookup;

/**
 *
 * @author gtoffoli
 */
public class AxisChartNode extends ElementNode implements NotRealElementNode {

    JRDesignChartAxis chartAxis = null;

    public AxisChartNode(JasperDesign jd, JRDesignChartAxis chartAxis, Lookup doLkp)
    {
        super(jd,(JRDesignChart)chartAxis.getChart(),doLkp);
        this.chartAxis = chartAxis;
        setIconBaseWithExtension(ElementNodeVisitor.ICON_BASE + "chartaxis-16.png");
    }

    private JRDesignChart getChart()
    {
        return (JRDesignChart)chartAxis.getChart();
    }

    @Override
    protected Sheet createSheet() {
        Sheet sheet = super.createSheet();
        
        PropertySet[] sets = sheet.toArray();
        for (int i=0; i<sets.length; ++i)
        {
            sheet.remove(sets[i].getName());
        }


        Sheet.Set propertySet = Sheet.createPropertiesSet();
        propertySet.setName("AXIS_PROPERTIES");
        propertySet.setDisplayName(I18n.getString("Global.AxisProperties"));
        
        propertySet.put(new AxisPositionTypeProperty( chartAxis ));

        sheet.put(propertySet);

        for (int i=0; i<sets.length; ++i)
        {
            sheet.put((Set)sets[i]);
        }

        return sheet;
    }



    @Override
    public void destroy() throws IOException {
        super.destroy();

        // Get the parent node...
        Node node = getParentNode();
        if (node instanceof MultiAxisChartElementNode)
        {
            JRDesignChart element = (JRDesignChart) ((MultiAxisChartElementNode)node).getElement();
            JRDesignMultiAxisPlot plot = (JRDesignMultiAxisPlot)element.getPlot();
            if (plot.getAxes().size() > 1)
            {
                plot.getAxes().remove(chartAxis);
                JRDesignChartAxis axis0 = (JRDesignChartAxis)plot.getAxes().get(0);
                ((JRDesignChart)plot.getChart()).setDataset( axis0.getChart().getDataset());
                plot.getEventSupport().firePropertyChange( JRDesignMultiAxisPlot.PROPERTY_AXES, null, plot.getAxes());
            }
        }

    }

    @Override
    public boolean canRename() {
        return false;
    }


}
