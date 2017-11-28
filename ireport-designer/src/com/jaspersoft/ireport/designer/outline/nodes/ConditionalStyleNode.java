/*
 * ReportNode.java
 * 
 * Created on Aug 31, 2007, 4:55:47 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.outline.nodes;

import com.jaspersoft.ireport.designer.ModelUtils;
import com.jaspersoft.ireport.designer.actions.EditConditionalStyleExpressionBandAction;
import com.jaspersoft.ireport.designer.editor.ExpressionContext;
import com.jaspersoft.ireport.designer.editor.FullExpressionContext;
import com.jaspersoft.ireport.designer.sheet.properties.ExpressionProperty;
import com.jaspersoft.ireport.designer.utils.Misc;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import javax.swing.Action;
import net.sf.jasperreports.engine.design.JRDesignConditionalStyle;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignStyle;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.nodes.Sheet;
import org.openide.nodes.Sheet.Set;
import org.openide.util.Lookup;
import org.openide.util.actions.SystemAction;

/**
 * ParameterNode detects the events fired by the subtended parameter.
 * Implements the support for the property sheet of a parameter.
 * If a parameter is system defined, it can not be cut.
 * Actions of a parameter node include copy, paste, reorder, rename and delete.
 * 
 * @author gtoffoli
 */
public class ConditionalStyleNode extends AbstractStyleNode implements PropertyChangeListener {

    private JRDesignStyle parentStyle;
    
    public ConditionalStyleNode(JasperDesign jd, JRDesignConditionalStyle style, Lookup doLkp, JRDesignStyle parentStyle)
    {
        super (jd, style, doLkp);
        this.parentStyle = parentStyle;
        style.getEventSupport().addPropertyChangeListener(this);
        this.setName("conditionalStyle");
        
    }
    
    public JRDesignConditionalStyle getConditionalStyle()
    {
        return (JRDesignConditionalStyle)getStyle();
    }

    @Override
    public String getDisplayName() {

        if (getConditionalStyle().getConditionExpression() != null)
        {
            return Misc.getExpressionText(getConditionalStyle().getConditionExpression());
        }
        else
        {
            return "<No condition set>";
        }
    }

    /**
     *  This is the function to create the sheet...
     * 
     */
    @Override
    protected Sheet createSheet() {
        Sheet sheet = super.createSheet();
        
        Set set = sheet.get(Sheet.PROPERTIES);
        Property[] props = set.getProperties();
        
        // Remove all the properties...
        for (int i=0; i<props.length; ++i)
        {
            set.remove(props[i].getName());
        }
        // Add the missing properties...
        set.put(new ConditionExpressionProperty( getConditionalStyle(), jd));
        
        for (int i=0; i<props.length; ++i)
        {
            set.put(props[i]);
        }
        
        return sheet;
    }
    

    @Override
    public boolean canRename() {
        return false;
    }
    
    
    @Override
    public void destroy() throws IOException {
       
          int index = parentStyle.getConditionalStyleList().indexOf(getStyle());
          
          // add destroy....
          parentStyle.removeConditionalStyle(getConditionalStyle());
          // TODO: Add the undo operation
          //DeleteStyleUndoableEdit undo = new DeleteStyleUndoableEdit(getStyle(), jd,index); //newIndex
          //IReportManager.getInstance().addUndoableEdit(undo);
          
          super.destroy();
    }

    public JRDesignStyle getParentStyle() {
        return parentStyle;
    }

    public void setParentStyle(JRDesignStyle parentStyle) {
        this.parentStyle = parentStyle;
    }
        
    public void propertyChange(PropertyChangeEvent evt) {
        
        if (evt.getPropertyName() == null) return;
        if (evt.getPropertyName().equals( JRDesignConditionalStyle.PROPERTY_CONDITION_EXPRESSION ))
        {
            fireDisplayNameChange(null, null);
        }
        
        super.propertyChange(evt);
    }

    @Override
    public Action getPreferredAction() {
        return SystemAction.get(EditConditionalStyleExpressionBandAction.class); 
    }

    
    
    
    /***************  SHEET PROPERTIES DEFINITIONS **********************/
    
    /**
     */
    public static final class ConditionExpressionProperty extends ExpressionProperty
    {
        private final JRDesignConditionalStyle conditionalStyle;
        private final JasperDesign jd;

        public JRDesignConditionalStyle getConditionalStyle() {
            return conditionalStyle;
        }
        
        @SuppressWarnings("unchecked")
        public ConditionExpressionProperty(JRDesignConditionalStyle conditionalStyle, JasperDesign jd)
        {
            super(conditionalStyle, new FullExpressionContext(jd));
            setName( JRDesignConditionalStyle.PROPERTY_CONDITION_EXPRESSION);
            setDisplayName("Condition Expression");
            setShortDescription("The expression used as condition. It should return a boolean object.");
            this.conditionalStyle = conditionalStyle;
            this.jd = jd;
        }
        
        public JasperDesign getJasperDesign()
        {
            return jd;
        }
        
        @Override
        public String getDefaultExpressionClassName() {
            return "java.lang.Boolean";
        }

        @Override
        public JRDesignExpression getExpression() {
            return (JRDesignExpression)conditionalStyle.getConditionExpression();
        }

        @Override
        public void setExpression(JRDesignExpression expression) {
            conditionalStyle.setConditionExpression(expression);
        }
        
    }

}
