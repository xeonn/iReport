/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.components.barcode.barcode4j;

import com.jaspersoft.ireport.designer.AbstractReportObjectScene;
import com.jaspersoft.ireport.designer.widgets.JRDesignElementWidget;
import java.beans.PropertyChangeEvent;
import net.sf.jasperreports.components.barcode4j.BarcodeComponent;
import net.sf.jasperreports.components.barcode4j.CodabarComponent;
import net.sf.jasperreports.components.barcode4j.Code39Component;
import net.sf.jasperreports.components.barcode4j.DataMatrixComponent;
import net.sf.jasperreports.components.barcode4j.FourStateBarcodeComponent;
import net.sf.jasperreports.components.barcode4j.POSTNETComponent;
import net.sf.jasperreports.engine.design.JRDesignComponentElement;
import net.sf.jasperreports.engine.design.JRDesignElement;

/**
 *
 * @author gtoffoli
 */
public class Barcode4JComponentWidget extends JRDesignElementWidget {

    public Barcode4JComponentWidget(AbstractReportObjectScene scene, JRDesignElement element) {
        super(scene, element);

        if (((JRDesignComponentElement)element).getComponent() instanceof BarcodeComponent)
        {
            BarcodeComponent c = (BarcodeComponent) ((JRDesignComponentElement)element).getComponent();
            c.getEventSupport().addPropertyChangeListener(this);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        
        if (evt.getPropertyName().equals(BarcodeComponent.PROPERTY_CODE_EXPRESSION) ||
            evt.getPropertyName().equals(BarcodeComponent.PROPERTY_MODULE_WIDTH) ||
            evt.getPropertyName().equals(BarcodeComponent.PROPERTY_ORIENTATION) ||
            evt.getPropertyName().equals(BarcodeComponent.PROPERTY_PATTERN_EXPRESSION) ||
            evt.getPropertyName().equals(BarcodeComponent.PROPERTY_TEXT_POSITION) ||
            evt.getPropertyName().equals(Code39Component.PROPERTY_CHECKSUM_MODE) ||
            evt.getPropertyName().equals(CodabarComponent.PROPERTY_WIDE_FACTOR) ||
            evt.getPropertyName().equals(DataMatrixComponent.PROPERTY_SHAPE) ||
            evt.getPropertyName().equals(FourStateBarcodeComponent.PROPERTY_ASCENDER_HEIGHT) ||
            evt.getPropertyName().equals(FourStateBarcodeComponent.PROPERTY_INTERCHAR_GAP_WIDTH) ||
            evt.getPropertyName().equals(FourStateBarcodeComponent.PROPERTY_TRACK_HEIGHT) ||
            evt.getPropertyName().equals(Code39Component.PROPERTY_EXTENDED_CHARSET_ENABLED) ||
            evt.getPropertyName().equals(Code39Component.PROPERTY_DISPLAY_CHECKSUM) ||
            evt.getPropertyName().equals(Code39Component.PROPERTY_DISPLAY_START_STOP) ||
            evt.getPropertyName().equals(POSTNETComponent.PROPERTY_BASELINE_POSITION) ||
            evt.getPropertyName().equals(POSTNETComponent.PROPERTY_SHORT_BAR_HEIGHT))
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
