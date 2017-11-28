package com.jaspersoft.ireport.designer.menu;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.utils.Misc;
import com.jaspersoft.ireport.locale.I18n;
import java.io.IOException;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.filesystems.FileUtil;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.actions.NodeAction;

public final class EditWithExternalEditorAction extends NodeAction {

    @Override
    protected void performAction(Node[] nodes) {
        
        JasperDesign jd = IReportManager.getInstance().getActiveReport();
        if (jd != null)
        {

            if (IReportManager.getInstance().getActiveVisualView().getEditorSupport().isModified())
            {
                int res = javax.swing.JOptionPane.showConfirmDialog(Misc.getMainFrame(),
                        I18n.getString("messages.fileModifiedLaunchingEditor"),"",javax.swing.JOptionPane.INFORMATION_MESSAGE);
                if (res == javax.swing.JOptionPane.OK_OPTION) {
                    try {
                        IReportManager.getInstance().getActiveVisualView().getEditorSupport().saveDocument();
                    } catch (IOException ex) {
                        return;
                    }
                }
                else
                    return;
            }
            Runtime rt = Runtime.getRuntime();
            String editor = null;
            String fileName = null;
            try {
                editor = IReportManager.getPreferences().get("ExternalEditor", null);
                if (editor == null)
                {
                    javax.swing.JOptionPane.showMessageDialog(Misc.getMainFrame(),
                    I18n.getString("messages.noExternalEditorDefined"),"",javax.swing.JOptionPane.WARNING_MESSAGE);
                    return;
                }
                fileName = FileUtil.toFile(IReportManager.getInstance().getActiveVisualView().getEditorSupport().getDataObject().getPrimaryFile()).getPath();

            // WRONG ON UNIX: rt.exec(editor+ " \"" +jrf.getReport().getFilename()+"\"");
            // String tokenizer wasn't parsing the command and arguments
            // properly, so pass them in as separate elements.
                rt.exec(new String[] { editor, fileName });

            } catch (Exception ex) {

                javax.swing.JOptionPane.showMessageDialog(Misc.getMainFrame(),
                        I18n.getString("messages.errorExecutingEditor", new Object[]{editor,fileName}) ,"",javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    protected void initialize() {
        super.initialize();
        // see org.openide.util.actions.SystemAction.iconResource() javadoc for more details
        putValue("noIconInMenu", Boolean.TRUE);
    }

    public String getName() {
        return I18n.getString("action.editWithExternalEditor");
    }

    @Override
    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    protected boolean asynchronous() {
        return false;
    }

    @Override
    protected boolean enable(Node[] nodes) {
        return IReportManager.getInstance().getActiveReport() != null;
    }
}