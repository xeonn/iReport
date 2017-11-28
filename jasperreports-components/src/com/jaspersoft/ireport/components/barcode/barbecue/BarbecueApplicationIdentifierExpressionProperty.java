/*
 * ElementPropertiesFactory.java
 * 
 * Created on 31-ott-2007, 23.03.43
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.components.barcode.barbecue;

import com.jaspersoft.ireport.designer.sheet.properties.ExpressionProperty;
import com.jaspersoft.ireport.locale.I18n;
import net.sf.jasperreports.components.barbecue.StandardBarbecueComponent;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignExpression;

    
/**
 * Class to manage the JRDesignSubreport.PROPERTY_CONNECTION_EXPRESSION property.
 */
public final class BarbecueApplicationIdentifierExpressionProperty extends ExpressionProperty
{
    private final StandardBarbecueComponent component;

    public BarbecueApplicationIdentifierExpressionProperty(StandardBarbecueComponent component, JRDesignDataset dataset)
    {
        super(component, dataset);
        this.component = component;
    }

    @Override
    public String getName()
    {
        return StandardBarbecueComponent.PROPERTY_APPLICATION_IDENTIFIER_EXPRESSION;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("barbecue.property.applicationIdentifierExpression.name");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("barbecue.property.applicationIdentifierExpression.description");
    }

    @Override
    public String getDefaultExpressionClassName()
    {
        return String.class.getName();
    }

    @Override
    public JRDesignExpression getExpression()
    {
        return (JRDesignExpression)component.getApplicationIdentifierExpression();
    }

    @Override
    public void setExpression(JRDesignExpression expression)
    {
        component.setApplicationIdentifierExpression(expression);
    }

    /**
     * @return the component
     */
    public StandardBarbecueComponent getComponent() {
        return component;
    }
}
