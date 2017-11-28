/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.components.barcode.barcode4j;

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
public class Barcode4JElementNodeFactory  implements ElementNodeFactory {

    public ElementNode createElementNode(JasperDesign jd, JRDesignComponentElement element, Lookup lkp) {

        
        Barcode4JElementNode node = new Barcode4JElementNode(jd, element, lkp);
        
        return node;
    }

    public JRDesignElementWidget createElementWidget(AbstractReportObjectScene scene, JRDesignElement element) {

            return new Barcode4JComponentWidget(scene, element);

    }

}

