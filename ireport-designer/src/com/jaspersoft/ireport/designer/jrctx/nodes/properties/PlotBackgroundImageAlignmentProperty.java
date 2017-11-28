/*
 * GraphicElementPropertiesFactory.java
 * 
 * Created on 5-nov-2007, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.jrctx.nodes.properties;

import com.jaspersoft.ireport.designer.sheet.properties.*;
import com.jaspersoft.ireport.designer.sheet.Tag;
import com.jaspersoft.ireport.designer.sheet.editors.ComboBoxPropertyEditor;
import com.jaspersoft.ireport.locale.I18n;
import java.beans.PropertyEditor;
import java.util.List;
import net.sf.jasperreports.chartthemes.simple.PlotSettings;
import org.jfree.ui.Align;

    
/**
 *  Class to manage the JRDesignElement.PROPERTY_POSITION_TYPE property
 */
public final class PlotBackgroundImageAlignmentProperty extends IntegerProperty
{
    private final PlotSettings settings;
    private ComboBoxPropertyEditor editor;

    @SuppressWarnings("unchecked")
    public PlotBackgroundImageAlignmentProperty(PlotSettings settings)
    {
        super(settings);
        this.settings = settings;
    }

    @Override
    public String getName()
    {
        return PlotSettings.PROPERTY_backgroundImageAlignment;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Global.Property." + getName());
    }

    @Override
    public String getShortDescription()
    {
        return getDisplayName();
    }

    @SuppressWarnings("unchecked")
    @Override
    public PropertyEditor getPropertyEditor()
    {
        if (editor == null)
        {
            editor = new ComboBoxPropertyEditor(false, getTagList());
        }
        return editor;
    }

    public List getTagList() 
    {
        List tags = new java.util.ArrayList();
        tags.add(new Tag(new Integer(Align.BOTTOM), I18n.getString("Global.Property.Bottom")));
        tags.add(new Tag(new Integer(Align.BOTTOM_LEFT), I18n.getString("Global.Property.BottomLeft")));
        tags.add(new Tag(new Integer(Align.BOTTOM_RIGHT), I18n.getString("Global.Property.BottomRight")));
        tags.add(new Tag(new Integer(Align.CENTER), I18n.getString("Global.Property.Center")));
        tags.add(new Tag(new Integer(Align.FIT), I18n.getString("Global.Property.Fit")));
        tags.add(new Tag(new Integer(Align.FIT_HORIZONTAL), I18n.getString("Global.Property.FitHorizontal")));
        tags.add(new Tag(new Integer(Align.FIT_VERTICAL), I18n.getString("Global.Property.FitVertical")));
        tags.add(new Tag(new Integer(Align.LEFT), I18n.getString("Global.Property.Left")));
        tags.add(new Tag(new Integer(Align.RIGHT), I18n.getString("Global.Property.Right")));
        tags.add(new Tag(new Integer(Align.TOP), I18n.getString("Global.Property.Top")));
        tags.add(new Tag(new Integer(Align.TOP_LEFT), I18n.getString("Global.Property.TopLeft")));
        tags.add(new Tag(new Integer(Align.TOP_RIGHT), I18n.getString("Global.Property.TopRight")));
        return tags;
    }

    @Override
    public Integer getInteger()
    {
        return settings.getBackgroundImageAlignment();
    }

    @Override
    public Integer getOwnInteger()
    {
        return settings.getBackgroundImageAlignment();
    }

    @Override
    public Integer getDefaultInteger()
    {
        return null;
    }

    @Override
    public void setInteger(Integer value)
    {
        settings.setBackgroundImageAlignment(value);
    }


    @Override
    public void validateInteger(Integer value)
    {
    }

}
