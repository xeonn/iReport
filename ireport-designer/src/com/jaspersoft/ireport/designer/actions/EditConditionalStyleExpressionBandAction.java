package com.jaspersoft.ireport.designer.actions;

import com.jaspersoft.ireport.designer.editor.ExpressionEditor;
import com.jaspersoft.ireport.designer.editor.FullExpressionContext;
import com.jaspersoft.ireport.designer.outline.nodes.ConditionalStyleNode;
import com.jaspersoft.ireport.designer.utils.Misc;

import java.lang.reflect.InvocationTargetException;
import javax.swing.JOptionPane;

import net.sf.jasperreports.engine.design.JRDesignConditionalStyle;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.nodes.Node.Property;
import org.openide.nodes.Node.PropertySet;
import org.openide.nodes.Sheet;
import org.openide.util.Exceptions;
import org.openide.util.HelpCtx;
import org.openide.util.actions.NodeAction;

public final class EditConditionalStyleExpressionBandAction extends NodeAction {

    private static EditConditionalStyleExpressionBandAction instance = null;
    
    public static synchronized EditConditionalStyleExpressionBandAction getInstance()
    {
        if (instance == null)
        {
            instance = new EditConditionalStyleExpressionBandAction();
        }
        
        return instance;
    }
    
    private EditConditionalStyleExpressionBandAction()
    {
        super();
    }
    
    
    public String getName() {
        return "Edit Condition";
    }

    @Override
    protected void initialize() {
        super.initialize();
        // see org.openide.util.actions.SystemAction.iconResource() javadoc for more details
        putValue("noIconInMenu", Boolean.TRUE);
    }

    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    protected boolean asynchronous() {
        return false;
    }

    protected void performAction(org.openide.nodes.Node[] activatedNodes) {
        
        ConditionalStyleNode node = (ConditionalStyleNode)activatedNodes[0];

        String exp = Misc.getExpressionText(node.getConditionalStyle().getConditionExpression());
        
        FullExpressionContext context = new FullExpressionContext( node.getLookup().lookup(JasperDesign.class));

        ExpressionEditor editor = new ExpressionEditor();
        editor.setExpression(exp);
        editor.setExpressionContext(context);
        
        int res = editor.showDialog(Misc.getMainFrame());
        if (res == JOptionPane.OK_OPTION)
        {
            PropertySet[] sets = node.getPropertySets();
            for (int i=0; i<sets.length; ++i)
            {
                if (sets[i].getName().equals( Sheet.PROPERTIES))
                {
                    Property[] props = sets[i].getProperties();
                    for (int k=0; k<props.length; ++k)
                    {
                        if (props[k].getName().equals( JRDesignConditionalStyle.PROPERTY_CONDITION_EXPRESSION  ))
                        {
                            try {
                                props[k].setValue(editor.getExpression());
                                return;
                            } catch (IllegalAccessException ex) {
                                Exceptions.printStackTrace(ex);
                            } catch (IllegalArgumentException ex) {
                                Exceptions.printStackTrace(ex);
                            } catch (InvocationTargetException ex) {
                                Exceptions.printStackTrace(ex);
                            }
                        }
                    }
                }
            }
        }
        
        
    }

    protected boolean enable(org.openide.nodes.Node[] activatedNodes) {
        if (activatedNodes != null &&
            activatedNodes.length == 1 &&
            activatedNodes[0] instanceof ConditionalStyleNode)
        {
            return true;
        }
        
        return false;
    }
}