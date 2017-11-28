/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.components.list;

import com.jaspersoft.ireport.designer.AbstractReportObjectScene;
import com.jaspersoft.ireport.designer.ElementNodeFactory;
import com.jaspersoft.ireport.designer.outline.nodes.ElementNode;
import com.jaspersoft.ireport.designer.widgets.JRDesignElementWidget;
import net.sf.jasperreports.engine.design.JRDesignComponentElement;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.util.Lookup;

/**
 *
 * @author gtoffoli
 */
public class ListElementNodeFactory  implements ElementNodeFactory {

    public ElementNode createElementNode(JasperDesign jd, JRDesignComponentElement element, Lookup lkp) {

        ListChildren children = new ListChildren(jd, element, lkp);
        ListElementNode node = new ListElementNode(jd, element, children, children.getIndex(), lkp);
        
        return node;
    }

    public JRDesignElementWidget createElementWidget(AbstractReportObjectScene scene, JRDesignElement element) {

            return new JRListComponentWidget(scene, element);

    }

}

