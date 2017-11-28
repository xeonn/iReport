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
import net.sf.jasperreports.components.barcode4j.POSTNETComponent;


public final class Barcode4JShortBarHeightProperty  extends DoubleProperty
{
        private POSTNETComponent component;

        @SuppressWarnings("unchecked")
        public Barcode4JShortBarHeightProperty(POSTNETComponent component)
        {
           super(component);
           this.component = component;
           setName(POSTNETComponent.PROPERTY_SHORT_BAR_HEIGHT);
           setDisplayName(I18n.getString("barcode4j.property.shortBarHeight.name"));
           setShortDescription(I18n.getString("barcode4j.property.shortBarHeight.description"));
        }



    @Override
    public Double getDouble() {
        return component.getShortBarHeight();
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
        component.setShortBarHeight(value);
    }
}

