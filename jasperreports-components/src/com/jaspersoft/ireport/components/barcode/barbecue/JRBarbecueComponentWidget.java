/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.components.barcode.barbecue;

import com.jaspersoft.ireport.designer.AbstractReportObjectScene;
import com.jaspersoft.ireport.designer.widgets.JRDesignElementWidget;
import java.beans.PropertyChangeEvent;
import net.sf.jasperreports.components.barbecue.StandardBarbecueComponent;
import net.sf.jasperreports.engine.design.JRDesignComponentElement;
import net.sf.jasperreports.engine.design.JRDesignElement;

/**
 *
 * @author gtoffoli
 */
public class JRBarbecueComponentWidget extends JRDesignElementWidget {

    public JRBarbecueComponentWidget(AbstractReportObjectScene scene, JRDesignElement element) {
        super(scene, element);

        if (((JRDesignComponentElement)element).getComponent() instanceof StandardBarbecueComponent)
        {
            StandardBarbecueComponent c = (StandardBarbecueComponent) ((JRDesignComponentElement)element).getComponent();
            c.getEventSupport().addPropertyChangeListener(this);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        if (evt.getPropertyName().equals(StandardBarbecueComponent.PROPERTY_DRAW_TEXT) ||
            evt.getPropertyName().equals(StandardBarbecueComponent.PROPERTY_BAR_HEIGTH) ||
            evt.getPropertyName().equals(StandardBarbecueComponent.PROPERTY_BAR_WIDTH) ||
            evt.getPropertyName().equals(StandardBarbecueComponent.PROPERTY_CHECKSUM_REQUIRED) ||
            evt.getPropertyName().equals(StandardBarbecueComponent.PROPERTY_CODE_EXPRESSION) ||
            evt.getPropertyName().equals(StandardBarbecueComponent.PROPERTY_APPLICATION_IDENTIFIER_EXPRESSION) ||
            evt.getPropertyName().equals(StandardBarbecueComponent.PROPERTY_TYPE))
        {
            updateBounds();
            this.repaint();
            this.revalidate(true);
            this.getSelectionWidget().updateBounds();
            this.getSelectionWidget().revalidate(true);
            getScene().validate();
        }
        
        super.propertyChange(evt);
    }

}
