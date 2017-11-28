/*
 * ElementPropertiesFactory.java
 * 
 * Created on 31-ott-2007, 23.03.43
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.components.barcode.barcode4j;

import com.jaspersoft.ireport.designer.sheet.properties.ExpressionProperty;
import com.jaspersoft.ireport.locale.I18n;
import net.sf.jasperreports.components.barcode4j.BarcodeComponent;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignExpression;

    
/**
 * Class to manage the JRDesignSubreport.PROPERTY_CONNECTION_EXPRESSION property.
 */
public final class Barcode4JPatternExpressionProperty extends ExpressionProperty
{
    private final BarcodeComponent component;

    public Barcode4JPatternExpressionProperty(BarcodeComponent component, JRDesignDataset dataset)
    {
        super(component, dataset);
        this.component = component;
    }

    @Override
    public String getName()
    {
        return "patternExpression";
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("barcode4j.property.patternExpression.name");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("barcode4j.property.patternExpression.description");
    }

    @Override
    public String getDefaultExpressionClassName()
    {
        return String.class.getName();
    }

    @Override
    public JRDesignExpression getExpression()
    {
        return (JRDesignExpression)component.getPatternExpression();
    }

    @Override
    public void setExpression(JRDesignExpression expression)
    {
        component.setPatternExpression(expression);
    }
    
}
