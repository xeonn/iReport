/*
 * GraphicElementPropertiesFactory.java
 * 
 * Created on 5-nov-2007, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.components.barcode.barcode4j;

import com.jaspersoft.ireport.designer.sheet.Tag;
import com.jaspersoft.ireport.designer.sheet.properties.ByteProperty;
import com.jaspersoft.ireport.locale.I18n;
import java.util.List;
import net.sf.jasperreports.components.barcode4j.BarcodeComponent;
import net.sf.jasperreports.engine.design.JRDesignTextElement;


/**
 *  Class to manage the JRDesignChart.PROPERTY_TITLE_POSITION property
 */
public final class Barcode4JOrientationProperty extends ByteProperty
{

    private final BarcodeComponent component;

    
    public Barcode4JOrientationProperty(BarcodeComponent component)
    {
        super(component);
        this.component = component;
        setName(BarcodeComponent.PROPERTY_ORIENTATION);
        setDisplayName(I18n.getString("barcode4j.property.orientation.name"));
        setShortDescription(I18n.getString("barcode4j.property.orientation.description"));
    }


    @Override
    public List getTagList()
    {
        List tags = new java.util.ArrayList();
        tags.add(new Tag(new Byte(JRDesignTextElement.ROTATION_NONE), I18n.getString("Global.Property.None")));
        tags.add(new Tag(new Byte(JRDesignTextElement.ROTATION_LEFT), I18n.getString("Global.Property.Left")));
        tags.add(new Tag(new Byte(JRDesignTextElement.ROTATION_RIGHT), I18n.getString("Global.Property.Right")));
        tags.add(new Tag(new Byte(JRDesignTextElement.ROTATION_UPSIDE_DOWN), I18n.getString("Global.Property.UpsideDown")));
        return tags;
    }

    @Override
    public Byte getByte()
    {
        return getOwnByte();
    }

    @Override
    public Byte getOwnByte()
    {
        if (component.getOrientation() == 0) return JRDesignTextElement.ROTATION_NONE;
        if (component.getOrientation() == 90) return JRDesignTextElement.ROTATION_LEFT;
        if (component.getOrientation() == 180) return JRDesignTextElement.ROTATION_UPSIDE_DOWN;
        if (component.getOrientation() == 270) return JRDesignTextElement.ROTATION_RIGHT;

        return 0;
    }

    @Override
    public Byte getDefaultByte()
    {
        return 0;
    }

    @Override
    public void setByte(Byte position)
    {
        if (position == null)
        {
            component.setOrientation(0);
        }
        else if (position.equals(JRDesignTextElement.ROTATION_NONE))
        {
            component.setOrientation(0);
        }
        else if (position.equals(JRDesignTextElement.ROTATION_LEFT))
        {
            component.setOrientation(90);
        }
        else if (position.equals(JRDesignTextElement.ROTATION_UPSIDE_DOWN))
        {
            component.setOrientation(180);
        }
        else if (position.equals(JRDesignTextElement.ROTATION_RIGHT))
        {
            component.setOrientation(270);
        }
    }

}
