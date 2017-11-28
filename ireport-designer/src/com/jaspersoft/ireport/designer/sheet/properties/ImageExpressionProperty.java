/*
 * GraphicElementPropertiesFactory.java
 * 
 * Created on 5-nov-2007, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties;

import com.jaspersoft.ireport.designer.ModelUtils;
import com.jaspersoft.ireport.designer.editor.ExpressionContext;
import com.jaspersoft.ireport.locale.I18n;
import net.sf.jasperreports.crosstabs.design.JRDesignCellContents;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignImage;

    
/**
 *
 */
public final class ImageExpressionProperty extends ExpressionProperty 
{
    private final JRDesignImage image;

    public ImageExpressionProperty(JRDesignImage image, JRDesignDataset dataset)
    {
        super(image, dataset);
        this.image = image;
        // if this textfield is child of a crosstab replace the expression context..
        if (ModelUtils.getTopElementGroup(image) instanceof JRDesignCellContents)
        {
            JRDesignCellContents contents = (JRDesignCellContents) ModelUtils.getTopElementGroup(image);
            this.setValue(ExpressionContext.ATTRIBUTE_EXPRESSION_CONTEXT, new ExpressionContext(contents.getOrigin().getCrosstab()));
        }
    }

    @Override
    public String getName()
    {
        return JRDesignImage.PROPERTY_EXPRESSION;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Global.Property.ImageExpression");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Global.Property.ImageExpressiondetail");
    }

    @Override
    public String getDefaultExpressionClassName()
    {
        return String.class.getName();
    }

    @Override
    public JRDesignExpression getExpression()
    {
        return (JRDesignExpression)image.getExpression();
    }

    @Override
    public void setExpression(JRDesignExpression expression)
    {
        image.setExpression(expression);
    }

}
