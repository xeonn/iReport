/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.jrctx;

import com.jaspersoft.ireport.designer.utils.Misc;
import com.jaspersoft.ireport.locale.I18n;
import org.openide.util.HelpCtx;
import org.openide.util.actions.NodeAction;

/**
 *
 * @author gtoffoli
 */
public class ExportAsJarAction extends NodeAction {

    public String getName() {
        return I18n.getString("ExportAsJarAction.name");
    }

    @Override
    protected String iconResource() {
        return "com/jaspersoft/ireport/designer/resources/export_jrctx_action-16.png";
    }

    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    protected boolean asynchronous() {
        return false;
    }

    protected void performAction(org.openide.nodes.Node[] activatedNodes) {

        JRCTXEditorSupport editorSupport =  activatedNodes[0].getLookup().lookup(JRCTXEditorSupport.class);
        ExportToJarDialog d = new ExportToJarDialog(Misc.getMainFrame(), true);
        d.setJRCTXEditorSupport(editorSupport);
        d.setVisible(true);

    }


    protected boolean enable(org.openide.nodes.Node[] activatedNodes) {
        if (activatedNodes == null || activatedNodes.length == 0) return false;

        // Check if all the elements are a JRBoxContainer
        return (activatedNodes.length == 1 && activatedNodes[0].getLookup().lookup(JRCTXEditorSupport.class) != null);
    }
}
