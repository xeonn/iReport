/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer;

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
public interface ElementNodeFactory {

    /**
     * Creates a special node implementation that extends ElementNode
     * to provide custom properties, actions, icon, and so on.
     * If return null, the default ElementNode is used instead.
     * @param jd
     * @param element
     * @param lkp
     * @return
     */
    public ElementNode createElementNode(JasperDesign jd, JRDesignComponentElement element, Lookup lkp);

    /**
     * Creates a custom element Widget to control the element rendering.
     * If null, the default JRDesignElementWidget is used.
     * @param scene
     * @param element
     * @return
     */
    public JRDesignElementWidget createElementWidget(AbstractReportObjectScene scene, JRDesignElement element);
}
