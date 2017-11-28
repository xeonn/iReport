/*
 * ElementPropertiesFactory.java
 * 
 * Created on 31-ott-2007, 23.03.43
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties;

import com.jaspersoft.ireport.designer.ModelUtils;
import com.jaspersoft.ireport.designer.editor.ExpressionContext;
import com.jaspersoft.ireport.designer.sheet.properties.ExpressionProperty;
import com.jaspersoft.ireport.locale.I18n;
import net.sf.jasperreports.crosstabs.JRCellContents;
import net.sf.jasperreports.crosstabs.design.JRDesignCellContents;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignTextField;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public final class TextFieldExpressionProperty extends ExpressionProperty 
{
    private final JRDesignTextField textField;

    public TextFieldExpressionProperty(JRDesignTextField textField, JRDesignDataset dataset)
    {
        super(textField, dataset);
        this.textField = textField;
        
        // if this textfield is child of a crosstab replace the expression context..
        if (ModelUtils.getTopElementGroup(textField) instanceof JRDesignCellContents)
        {
            JRDesignCellContents contents = (JRDesignCellContents) ModelUtils.getTopElementGroup(textField);
            this.setValue(ExpressionContext.ATTRIBUTE_EXPRESSION_CONTEXT, new ExpressionContext(contents.getOrigin().getCrosstab()));
        }
    }


    @Override
    public String getName()
    {
        return JRDesignTextField.PROPERTY_EXPRESSION;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Global.Property.TextFieldExpression");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Global.Property.TextFieldExpressiondetail");
    }

    @Override
    public String getDefaultExpressionClassName()
    {
        return String.class.getName();
    }

    @Override
    public JRDesignExpression getExpression()
    {
        return (JRDesignExpression)textField.getExpression();
    }

    @Override
    public void setExpression(JRDesignExpression expression)
    {
        textField.setExpression(expression);
    }

}
