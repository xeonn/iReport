/*
 * ElementPropertiesFactory.java
 * 
 * Created on 31-ott-2007, 23.03.43
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.components.barcode.barcode4j;

import com.jaspersoft.ireport.designer.sheet.properties.DoubleProperty;
import com.jaspersoft.ireport.locale.I18n;
import net.sf.jasperreports.components.barcode4j.BarcodeComponent;
import net.sf.jasperreports.components.barcode4j.Code39Component;
import net.sf.jasperreports.components.barcode4j.FourStateBarcodeComponent;
import net.sf.jasperreports.components.barcode4j.POSTNETComponent;


public final class Barcode4JIntercharGapWidthProperty  extends DoubleProperty
{
        private BarcodeComponent component;

        @SuppressWarnings("unchecked")
        public Barcode4JIntercharGapWidthProperty(BarcodeComponent component)
        {
           super(component);
           this.component = component;
           setName(FourStateBarcodeComponent.PROPERTY_INTERCHAR_GAP_WIDTH);
           setDisplayName(I18n.getString("barcode4j.property.intercharGapWidth.name"));
           setShortDescription(I18n.getString("barcode4j.property.intercharGapWidth.description"));
        }



    @Override
    public Double getDouble() {
        return getComponentValue();
    }

    @Override
    public Double getOwnDouble() {
        return getDouble();
    }

    @Override
    public Double getDefaultDouble() {
        return null;
    }

    @Override
    public void validateDouble(Double value) {
//        if (value != null && value.doubleValue() <= 1.0)
//        {
//            throw annotateException("Wide factor must be > 1.0");
//        }
    }

    @Override
    public void setDouble(Double value) {
        setComponentValue(value);
    }


    public Double getComponentValue()
    {
        if (component instanceof FourStateBarcodeComponent)
        {
            return ((FourStateBarcodeComponent)component).getIntercharGapWidth();
        }
        else if (component instanceof Code39Component)
        {
            return ((Code39Component)component).getIntercharGapWidth();
        }
        else if (component instanceof POSTNETComponent)
        {
            return ((POSTNETComponent)component).getIntercharGapWidth();
        }
        return null;
    }

    public void setComponentValue(Double d)
    {
        if (component instanceof FourStateBarcodeComponent)
        {
            ((FourStateBarcodeComponent)component).setIntercharGapWidth(d);
        }
        else if (component instanceof Code39Component)
        {
            ((Code39Component)component).setIntercharGapWidth(d);
        }
        else if (component instanceof POSTNETComponent)
        {
            ((POSTNETComponent)component).setIntercharGapWidth(d);
        }
    }
}

