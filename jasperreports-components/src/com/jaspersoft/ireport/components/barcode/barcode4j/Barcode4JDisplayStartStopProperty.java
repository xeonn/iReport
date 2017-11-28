/*
 * ElementPropertiesFactory.java
 * 
 * Created on 31-ott-2007, 23.03.43
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.components.barcode.barcode4j;

import com.jaspersoft.ireport.designer.sheet.properties.BooleanProperty;
import com.jaspersoft.ireport.locale.I18n;
import net.sf.jasperreports.components.barcode4j.BarcodeComponent;
import net.sf.jasperreports.components.barcode4j.Code39Component;


public final class Barcode4JDisplayStartStopProperty  extends BooleanProperty
{
    private BarcodeComponent component;

    @SuppressWarnings("unchecked")
    public Barcode4JDisplayStartStopProperty(BarcodeComponent component)
    {
       super(component);
       this.component = component;
       setName(Code39Component.PROPERTY_DISPLAY_START_STOP);
       setDisplayName(I18n.getString("barcode4j.property.displayStartStop.name"));
       setShortDescription(I18n.getString("barcode4j.property.displayStartStop.description"));
    }

    @Override
    public Boolean getBoolean() {
        return getComponentValue();
    }

    @Override
    public Boolean getOwnBoolean() {
        return getBoolean();
    }

    @Override
    public Boolean getDefaultBoolean() {
        return null;
    }

    @Override
    public void setBoolean(Boolean value) {
        setComponentValue(value);
    }

    private Boolean getComponentValue() {
        if (component instanceof Code39Component)
        {
            return ((Code39Component)component).isDisplayStartStop();
        }
        return null;
    }

    private void setComponentValue(Boolean value) {
        if (component instanceof Code39Component)
        {
            ((Code39Component)component).setDisplayStartStop(value);
        }
    }
}

