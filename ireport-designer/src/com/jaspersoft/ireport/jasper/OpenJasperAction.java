package com.jaspersoft.ireport.jasper;

import com.jaspersoft.ireport.locale.I18n;
import javax.swing.SwingUtilities;
import org.openide.cookies.OpenCookie;
import org.openide.util.HelpCtx;
import org.openide.util.actions.NodeAction;

public final class OpenJasperAction extends NodeAction {

    public String getName() {
        return I18n.getString("OpenJasperAction.name");
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

        for (int i=0; i<activatedNodes.length; ++i)
        {
            final OpenCookie oc = activatedNodes[i].getCookie(OpenCookie.class);
            if (oc != null)
            {
                SwingUtilities.invokeLater(new Runnable() {

                    public void run() {
                        oc.open();
                    }
                });
            }
        }

    }

    protected boolean enable(org.openide.nodes.Node[] activatedNodes) {
        for (int i=0; i<activatedNodes.length; ++i)
        {
            System.out.println("Node of type: " + activatedNodes[i].getClass().getName());
            System.out.flush();
            if (activatedNodes[i].getCookie(OpenCookie.class) == null ||
                !(activatedNodes[i].getCookie(OpenCookie.class) instanceof JasperOpenCookie)) return false;
        }
        return true;
    }
}