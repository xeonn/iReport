/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.components.list;

import com.jaspersoft.ireport.designer.sheet.properties.IntegerProperty;
import net.sf.jasperreports.components.list.DesignListContents;

/**
 *
 * @author gtoffoli
 */
class ListContentsHeightProperty extends IntegerProperty {

    private DesignListContents contents = null;
    public ListContentsHeightProperty(DesignListContents contents) {
        super(contents);
        this.contents = contents;
        setName("LC" + DesignListContents.PROPERTY_HEIGHT);
        setDisplayName("Item height");
    }

    @Override
    public Integer getInteger() {
        return new Integer(contents.getHeight());
    }

    @Override
    public Integer getOwnInteger() {
        return getInteger();
    }

    @Override
    public Integer getDefaultInteger() {
        return getInteger();
    }

    @Override
    public void validateInteger(Integer value) {
    }

    @Override
    public void setInteger(Integer value) {
        if (value != null)
        contents.setHeight(value.intValue());
    }

    @Override
    public boolean supportsDefaultValue() {
        return false;
    }


}
