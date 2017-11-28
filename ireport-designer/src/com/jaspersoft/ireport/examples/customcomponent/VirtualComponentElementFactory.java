/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.examples.customcomponent;

import com.jaspersoft.ireport.designer.AbstractReportObjectScene;
import com.jaspersoft.ireport.designer.ElementNodeFactory;
import com.jaspersoft.ireport.designer.outline.nodes.ElementNode;
import com.jaspersoft.ireport.designer.outline.nodes.ElementNodeVisitor;
import com.jaspersoft.ireport.designer.widgets.JRDesignElementWidget;
import net.sf.jasperreports.engine.design.JRDesignComponentElement;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.util.Lookup;

/**
 *
 * @author gtoffoli
 */
public class VirtualComponentElementFactory implements ElementNodeFactory {

    public ElementNode createElementNode(JasperDesign jd, JRDesignComponentElement element, Lookup lkp) {

        ElementNode node = new ElementNode(jd, element, lkp);
        node.setIconBaseWithExtension( ElementNodeVisitor.ICON_CHART );
        return node;
    }

    public JRDesignElementWidget createElementWidget(AbstractReportObjectScene scene, JRDesignElement element) {

            return new JRDesignJChartWidget(scene, element);

    }

}
