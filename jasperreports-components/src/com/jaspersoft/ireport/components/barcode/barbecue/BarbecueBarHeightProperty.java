/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.components.barcode.barbecue;

import com.jaspersoft.ireport.designer.sheet.properties.IntegerProperty;
import net.sf.jasperreports.components.barbecue.StandardBarbecueComponent;

/**
 *
 * @author gtoffoli
 */
class BarbecueBarHeightProperty extends IntegerProperty {

    private StandardBarbecueComponent component = null;
    public BarbecueBarHeightProperty(StandardBarbecueComponent component) {
        super(component);
        this.component = component;
        setName(StandardBarbecueComponent.PROPERTY_BAR_HEIGTH);
        setDisplayName("Bar Height");
    }

    @Override
    public Integer getInteger() {
        return (component.getBarHeight() == null) ? new Integer(0) : component.getBarHeight();
    }

    @Override
    public Integer getOwnInteger() {
        return component.getBarHeight();
    }

    @Override
    public Integer getDefaultInteger() {
        return new Integer(0);
    }

    @Override
    public void validateInteger(Integer value) {
        if (value != null && value.intValue() < 0)
        {
            throw new IllegalArgumentException("Bar height must be a positive number");
        }
    }

    @Override
    public void setInteger(Integer value) {
        if (value != null && value.intValue() <= 0) value = new Integer(0);
        component.setBarHeight(value);
    }

    @Override
    public boolean supportsDefaultValue() {
        return true;
    }


}
