/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.outline.nodes;

import com.jaspersoft.ireport.designer.editor.ExpressionContext;
import net.sf.jasperreports.engine.design.JRDesignExpression;

/**
 *
 * @author gtoffoli
 */
public interface ExpressionHolder {

    public boolean hasExpression(JRDesignExpression ex);
    public ExpressionContext getExpressionContext(JRDesignExpression ex);
}
