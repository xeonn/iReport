package com.jaspersoft.ireport.designer.actions;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.locale.I18n;
import com.jaspersoft.ireport.designer.outline.nodes.ElementNode;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import org.openide.util.HelpCtx;
import org.openide.util.actions.NodeAction;
import com.jaspersoft.ireport.designer.editor.ExpressionEditor;
import com.jaspersoft.ireport.designer.ModelUtils;
import com.jaspersoft.ireport.designer.undo.PropertyUndoableEdit;
import com.jaspersoft.ireport.designer.utils.Misc;
import javax.swing.JOptionPane;
import com.jaspersoft.ireport.designer.sheet.properties.AbstractProperty;
import com.jaspersoft.ireport.designer.tools.FieldPatternDialog;
import net.sf.jasperreports.engine.base.JRBaseStyle;

public final class EditTextfieldPatternAction extends NodeAction {

        
    public String getName() {
        return I18n.getString("EditTextfieldPatternAction.name");
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
        ElementNode node = (ElementNode)activatedNodes[0];

        JRDesignTextField element = (JRDesignTextField)node.getElement();

        FieldPatternDialog fpd = new FieldPatternDialog(Misc.getMainFrame(),true);
        fpd.setPattern( element.getPattern() );

        fpd.setVisible(true);

        if (fpd.getDialogResult() == JOptionPane.OK_OPTION)
        {
            String oldPattern = element.getOwnPattern();
            String newPattern = fpd.getPattern();
            element.setPattern(newPattern);

            Object obj = ModelUtils.findProperty(node.getPropertySets(), JRBaseStyle.PROPERTY_PATTERN);
            if (obj != null && obj instanceof AbstractProperty)
            {
                PropertyUndoableEdit edit = new PropertyUndoableEdit((AbstractProperty)obj, oldPattern, newPattern);
                IReportManager.getInstance().addUndoableEdit(edit);
                IReportManager.getInstance().notifyReportChange();
            }
            else
            {
                System.out.println("Unable to find property " + JRBaseStyle.PROPERTY_PATTERN);
                System.out.flush();
            }
        }


    }

    protected boolean enable(org.openide.nodes.Node[] activatedNodes) {
        if (activatedNodes == null || activatedNodes.length != 1) return false;
        if (!(activatedNodes[0] instanceof ElementNode)) return false;
        ElementNode node = (ElementNode)activatedNodes[0];
        if (node.getElement() instanceof JRDesignTextField)
        {
            return true;
        }
        return false;
    }
}