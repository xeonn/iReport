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
import java.lang.reflect.InvocationTargetException;
import net.sf.jasperreports.components.barcode4j.BarcodeComponent;


public final class Barcode4JModuleWidthProperty  extends DoubleProperty
{
        private BarcodeComponent component;
        private ComboBoxPropertyEditor editor;

        @SuppressWarnings("unchecked")
        public Barcode4JModuleWidthProperty(BarcodeComponent component)
        {
           super(component);
           this.component = component;
           setName(BarcodeComponent.PROPERTY_MODULE_WIDTH);
           setDisplayName(I18n.getString("barcode4j.property.moduleWidth.name"));
           setShortDescription(I18n.getString("barcode4j.property.moduleWidth.description"));
        }



    @Override
    public Double getDouble() {
        return component.getModuleWidth();
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
    }

    @Override
    public void setDouble(Double value) {
        component.setModuleWidth(value);
    }

    @Override
    public boolean isDefaultValue() {
        return getDouble() == null;
    }

    @Override
    public void restoreDefaultValue() throws IllegalAccessException, InvocationTargetException {
        setDouble(null);
    }

    @Override
    public boolean supportsDefaultValue() {
        return true;
    }


}

