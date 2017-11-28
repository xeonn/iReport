/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.components.barcode.barbecue;

import com.jaspersoft.ireport.designer.sheet.properties.BooleanProperty;
import com.jaspersoft.ireport.locale.I18n;
import net.sf.jasperreports.components.barbecue.StandardBarbecueComponent;

/**
 * Class to manage the JRBaseStyle.PROPERTY_ITALIC property
 * @author sanda zaharia (shertage@users.sourceforge.net)
 */

public class BarbecueChecksumRequiredProperty extends BooleanProperty{

    private final StandardBarbecueComponent component;

    @SuppressWarnings("unchecked")
    public BarbecueChecksumRequiredProperty(StandardBarbecueComponent component)
    {
        super(component);
        this.component = component;
    }
    @Override
    public String getName()
    {
        return StandardBarbecueComponent.PROPERTY_CHECKSUM_REQUIRED;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("barbecue.property.checksumRequired.name");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("barbecue.property.checksumRequired.description");
    }

    @Override
    public Boolean getBoolean()
    {
        return component.isChecksumRequired();
    }

    @Override
    public Boolean getOwnBoolean()
    {
        return component.isChecksumRequired();
    }

    @Override
    public boolean supportsDefaultValue() {
        return false;
    }



    @Override
    public Boolean getDefaultBoolean()
    {
        return false;
    }

    @Override
    public void setBoolean(Boolean isTrue)
    {
        if (isTrue != null)
        {
            component.setChecksumRequired(isTrue.booleanValue());
        }
    }
    
}
