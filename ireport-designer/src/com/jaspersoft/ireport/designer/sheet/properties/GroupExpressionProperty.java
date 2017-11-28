/*
 * ReportNode.java
 * 
 * Created on Aug 31, 2007, 4:55:47 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties;

import com.jaspersoft.ireport.designer.sheet.properties.ExpressionProperty;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignGroup;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public final class GroupExpressionProperty extends ExpressionProperty 
{
    private final JRDesignGroup group;

    public GroupExpressionProperty(JRDesignGroup group, JRDesignDataset dataset)
    {
        super(group, dataset);
        this.group = group;
    }

    @Override
    public String getName()
    {
        return JRDesignGroup.PROPERTY_EXPRESSION;
    }

    @Override
    public String getDisplayName()
    {
        return "Group Expression";
    }

    @Override
    public String getShortDescription()
    {
        return "Expression to group records, it can be simply the value of a field or a more complex expression.";
    }

    @Override
    public String getDefaultExpressionClassName()
    {
        return Object.class.getName();
    }

    @Override
    public JRDesignExpression getExpression()
    {
        return (JRDesignExpression)group.getExpression();
    }

    @Override
    public void setExpression(JRDesignExpression expression)
    {
        group.setExpression(expression);
    }

}
