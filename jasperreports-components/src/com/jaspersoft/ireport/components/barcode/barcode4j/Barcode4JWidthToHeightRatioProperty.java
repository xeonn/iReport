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
import net.sf.jasperreports.components.barcode4j.PDF417Component;


public final class Barcode4JWidthToHeightRatioProperty  extends DoubleProperty
{
        private PDF417Component component;

        @SuppressWarnings("unchecked")
        public Barcode4JWidthToHeightRatioProperty(PDF417Component component)
        {
           super(component);
           this.component = component;
           setName(PDF417Component.PROPERTY_WIDTH_TO_HEIGHT_RATIO);
           setDisplayName(I18n.getString("barcode4j.property.widthToHeightRatio.name"));
           setShortDescription(I18n.getString("barcode4j.property.ascenderHeight.description"));
        }



    @Override
    public Double getDouble() {
        return component.getWidthToHeightRatio();
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
        component.setWidthToHeightRatio(value);
    }
}

