/*
 * ElementPropertiesFactory.java
 * 
 * Created on 31-ott-2007, 23.03.43
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.components.barcode.barcode4j;

import com.jaspersoft.ireport.designer.sheet.editors.ComboBoxPropertyEditor;
import com.jaspersoft.ireport.designer.sheet.properties.DoubleProperty;
import com.jaspersoft.ireport.locale.I18n;
import net.sf.jasperreports.components.barcode4j.BarcodeComponent;
import net.sf.jasperreports.components.barcode4j.CodabarComponent;
import net.sf.jasperreports.components.barcode4j.Code39Component;
import net.sf.jasperreports.components.barcode4j.Interleaved2Of5Component;


public final class Barcode4JWideFactorProperty  extends DoubleProperty
{
        private BarcodeComponent component;
        private ComboBoxPropertyEditor editor;

        @SuppressWarnings("unchecked")
        public Barcode4JWideFactorProperty(BarcodeComponent component)
        {
           super(component);
           this.component = component;
           setName(CodabarComponent.PROPERTY_WIDE_FACTOR);
           setDisplayName(I18n.getString("barcode4j.property.wideFactor.name"));
           setShortDescription(I18n.getString("barcode4j.property.wideFactor.description"));
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
        if (value != null && value.doubleValue() <= 1.0)
        {
            throw annotateException("Wide factor must be > 1.0");
        }
    }

    @Override
    public void setDouble(Double value) {
        setComponentValue(value);
    }

    public Double getComponentValue()
    {
        if (component instanceof CodabarComponent)
        {
            return ((CodabarComponent)component).getWideFactor();
        }
        else if (component instanceof Code39Component)
        {
            return ((Code39Component)component).getWideFactor();
        }
        else if (component instanceof Interleaved2Of5Component)
        {
            return ((Interleaved2Of5Component)component).getWideFactor();
        }
        return null;
    }

    public void setComponentValue(Double d)
    {
        if (component instanceof CodabarComponent)
        {
            ((CodabarComponent)component).setWideFactor(d);
        }
        else if (component instanceof Code39Component)
        {
            ((Code39Component)component).setWideFactor(d);
        }
        else if (component instanceof Interleaved2Of5Component)
        {
            ((Interleaved2Of5Component)component).setWideFactor(d);
        }
    }
}

