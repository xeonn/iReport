/*
 * ElementPropertiesFactory.java
 * 
 * Created on 31-ott-2007, 23.03.43
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties;

import com.jaspersoft.ireport.designer.sheet.Tag;
import com.jaspersoft.ireport.designer.sheet.properties.StringListProperty;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.JRStyle;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JRDesignStyle;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.util.WeakListeners;

    
/**
 *  Class to manage the JRDesignElement.PROPERTY_PARENT_STYLE_NAME_REFERENCE property
 */
public abstract class AbstractStyleProperty extends StringListProperty implements PropertyChangeListener
{
    private final JasperDesign jasperDesign;

    @SuppressWarnings("unchecked")
    public AbstractStyleProperty(Object object, JasperDesign jasperDesign)
    {
        super(object);
        this.jasperDesign = jasperDesign;

        jasperDesign.getEventSupport().addPropertyChangeListener(
            WeakListeners.propertyChange(this, jasperDesign.getEventSupport())
            );
    }
    @Override
    public String getName()
    {
        return JRDesignElement.PROPERTY_PARENT_STYLE_NAME_REFERENCE;
    }

    @Override
    public String getDisplayName()
    {
        return "Style";
    }

    @Override
    public String getShortDescription()
    {
        return "The optional style to use for this object. Can be the name of a locally defined style or the name of a style defined in an external style template file.";
    }

    @Override
    public List getTagList() 
    {
        List tags = new ArrayList();
        tags.add(new Tag( null , ""));
        List styles = jasperDesign.getStylesList();
        for (int i=0; i<styles.size(); ++i)
        {
            JRDesignStyle style = (JRDesignStyle)styles.get(i);
            tags.add(new Tag( style , style.getName()));
            style.getEventSupport().addPropertyChangeListener(
                WeakListeners.propertyChange(this, style.getEventSupport())
                );
        }
        return tags;
    }

    @Override
    public Object getPropertyValue()
    {
        if (getStyle() == null)
            return getStyleNameReference();
        return getStyle();
    }

    @Override
    public Object getOwnPropertyValue()
    {
        return getPropertyValue();
    }

    @Override
    public void setPropertyValue(Object value)
    {
        if (value instanceof JRStyle)
        {
            setStyle((JRStyle)value);
            setStyleNameReference(null);
        }
        else
        {
            String styleNameReference = (String)value;
            if (styleNameReference != null && styleNameReference.trim().length() == 0)
            {
                styleNameReference = null;
            }
            setStyleNameReference(styleNameReference);
            setStyle(null);
        }
    }

    @Override
    public String getString()
    {
        //not used
        return null;
    }

    @Override
    public String getOwnString()
    {
        //not used
        return null;
    }

    @Override
    public String getDefaultString()
    {
        return null;
    }

    @Override
    public void setString(String str)
    {
        //not used
    }

    public void propertyChange(PropertyChangeEvent evt) 
    {
        if (editor == null) return;
        if (evt.getPropertyName() == null) return;
        if (evt.getPropertyName().equals( JasperDesign.PROPERTY_STYLES) 
            || evt.getPropertyName().equals( JRDesignStyle.PROPERTY_NAME))
        {
            editor.setTagValues(getTagList());
        }
    }
    
    public abstract String getStyleNameReference();

    public abstract void setStyleNameReference(String s);
    
    public abstract JRStyle getStyle();
    
    public abstract void setStyle(JRStyle s);

}
