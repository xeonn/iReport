/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet;

import com.jaspersoft.ireport.designer.ModelUtils;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignPropertyExpression;

/**
 *
 * @author gtoffoli
 */
public class GenericProperty {

    private String key = null;
    private Object value = null;
    private boolean useExpression = false;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public boolean isUseExpression() {
        return useExpression;
    }

    public void setUseExpression(boolean useExpression) {
        this.useExpression = useExpression;
    }
    
    public GenericProperty(String key, Object value)
    {
        this.key = key;
        this.value = value;
    }
    
    public GenericProperty(String key, JRDesignExpression exp)
    {
        this.key = key;
        this.value = ModelUtils.cloneExpression(exp);
        this.useExpression = true;
    }
    public GenericProperty()
    {
        this.key = "";
    }
    
    public JRDesignExpression getExpression()
    {
        return (value instanceof JRDesignExpression) ? (JRDesignExpression)value : null;
    }
    
    public JRDesignPropertyExpression toPropertyExpression()
    {
        JRDesignPropertyExpression pp = new JRDesignPropertyExpression();
        pp.setName(key);
        pp.setValueExpression(getExpression());
        return pp;
    }
    
    @Override
    public String toString()
    {
        return getKey();
    }
    
}
