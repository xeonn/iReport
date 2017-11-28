/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.charts.multiaxis;

import com.jaspersoft.ireport.designer.outline.nodes.ElementNode;
import java.beans.PropertyChangeEvent;
import java.util.List;
import net.sf.jasperreports.charts.design.JRDesignMultiAxisPlot;
import net.sf.jasperreports.engine.design.JRDesignChart;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.nodes.NodeEvent;
import org.openide.nodes.NodeListener;
import org.openide.nodes.NodeMemberEvent;
import org.openide.nodes.NodeReorderEvent;
import org.openide.util.Lookup;

/**
 *
 * @author gtoffoli
 */
public class MultiAxisChartElementNode  extends ElementNode {


    public MultiAxisChartElementNode(JasperDesign jd, JRDesignChart element, Lookup doLkp)
    {
        this(jd, new AxisChartChildren(jd, element, doLkp), element, doLkp);
    }


    public MultiAxisChartElementNode(JasperDesign jd, AxisChartChildren children, JRDesignChart element, Lookup doLkp)
    {
           super(jd, element, children, children.getIndex(), doLkp);

           this.addNodeListener(new NodeListener() {

            public void childrenAdded(NodeMemberEvent ev) {}
            public void childrenRemoved(NodeMemberEvent ev) {}
            public void nodeDestroyed(NodeEvent ev) {}
            public void propertyChange(PropertyChangeEvent evt) {}

            @SuppressWarnings("unchecked")
            public void childrenReordered(NodeReorderEvent ev) {
                // Fire an event now...

                JRDesignMultiAxisPlot plot = (JRDesignMultiAxisPlot) ((JRDesignChart)getElement()).getPlot();

                List elements = plot.getAxes();
                int[] permutations = ev.getPermutation();

                Object[] elementsArray = new Object[elements.size()];
                for (int i=0; i<elementsArray.length; ++i)
                {
                    elementsArray[permutations[i]] = elements.get(i);
                }
                elements.clear();
                for (int i=0; i<elementsArray.length; ++i)
                {
                    elements.add(elementsArray[i]);
                }

                plot.getEventSupport().firePropertyChange( JRDesignMultiAxisPlot.PROPERTY_AXES, null, plot.getAxes());
            }
        });
    }

}
