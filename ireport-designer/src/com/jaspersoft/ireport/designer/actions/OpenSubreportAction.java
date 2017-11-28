/*
 * ChartDataAction.java
 * 
 * Created on 29-nov-2007, 16.53.52
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.actions;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.JrxmlVisualView;
import com.jaspersoft.ireport.designer.outline.nodes.ElementNode;
import com.jaspersoft.ireport.designer.utils.ExpressionInterpreter;
import com.jaspersoft.ireport.designer.utils.Misc;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignSubreport;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.cookies.OpenCookie;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.util.HelpCtx;
import org.openide.util.actions.NodeAction;

/**
 *
 * @author gtoffoli
 */
public final class OpenSubreportAction extends NodeAction {

    public String getName() {
        return "Open Subreport";
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

    protected void subreportNotFound(String msg)
    {
        // Display a message here...
        JOptionPane.showMessageDialog(Misc.getMainFrame(), "Unable to open the subreport:\n"+msg);
    }

    protected void performAction(org.openide.nodes.Node[] activatedNodes) {
        
        JasperDesign jasperDesign = ((ElementNode)activatedNodes[0]).getJasperDesign();

        JRDesignSubreport subreport = (JRDesignSubreport)((ElementNode)activatedNodes[0]).getElement();

        // Find the jrxml pointed by this subreport expression...

        if (subreport.getExpression() == null ||
                subreport.getExpression().getValueClassName() == null ||
                !subreport.getExpression().getValueClassName().equals("java.lang.String"))
        {
           // Return default image...
           // Unable to resolve the subreoport jrxml file...
            subreportNotFound("The subreport expression is empty or it is not of type String.");
            return;
        }

        JRDesignDataset dataset =  jasperDesign.getMainDesignDataset();
        ClassLoader classLoader = IReportManager.getReportClassLoader();

        try {

            // Try to process the expression...
            ExpressionInterpreter interpreter = new ExpressionInterpreter(dataset, classLoader);

            Object ret = interpreter.interpretExpression( subreport.getExpression().getText() );

            if (ret != null)
            {
                String resourceName = ret + "";
                if (resourceName.toLowerCase().endsWith(".jasper"))
                {
                    resourceName = resourceName.substring(0, resourceName.length() -  ".jasper".length());
                    resourceName += ".jrxml";
                }

                if (!resourceName.toLowerCase().endsWith(".jrxml"))
                {
                    throw new Exception("Unable to resolve the jrxml file for this subreport expression");
                }

                File f = new File(resourceName);
                if (!f.exists())
                {
                    File reportFolder = null;
                    JrxmlVisualView visualView = IReportManager.getInstance().getActiveVisualView();
                    if (visualView != null)
                    {
                        File file = FileUtil.toFile(visualView.getEditorSupport().getDataObject().getPrimaryFile());
                        if (file.getParentFile() != null)
                        {
                            reportFolder = file.getParentFile();
                        }
                    }

                    URL[] urls = new URL[]{};
                    if (reportFolder != null)
                    {
                        urls = new URL[]{ reportFolder.toURI().toURL()};
                    }
                    URLClassLoader urlClassLoader = new URLClassLoader(urls, classLoader);
                    URL url = urlClassLoader.findResource(resourceName);
                    if (url == null)
                    {
                        throw new Exception(resourceName + " not found.");
                    }
                        
                    f = new File(url.getPath());
                    if (f.exists())
                    {
                        openFile(f);
                    }
                    else
                    {
                        throw new Exception(f + " not found.");
                    }
                }
                else
                {
                    openFile(f);
                }

             }
            else
            {
                subreportNotFound("The subreport expression returned null. I'm unable to locate the subreport jrxml :-(");
            }
        } catch (Throwable ex) {

            subreportNotFound(ex.getMessage());
            ex.printStackTrace();
        }

        
    }

    
     
     
    protected boolean enable(org.openide.nodes.Node[] activatedNodes) {
        if (activatedNodes == null || activatedNodes.length != 1) return false;
        
        // Check we have selected a subreport
        if (activatedNodes[0] instanceof ElementNode &&
            ((ElementNode)activatedNodes[0]).getElement() instanceof JRDesignSubreport)
        {
            return true;
        }
        return false;
    }

    private void openFile(File f) throws Exception {

        DataObject obj;

        f = FileUtil.normalizeFile(f);
        FileObject fl = FileUtil.toFileObject(f);
        if (fl == null) throw new Exception("Unable to open the file " + f);
        obj = DataObject.find(fl);

        OpenCookie ocookie = obj.getCookie(OpenCookie.class);

        if (ocookie != null)
        {
            ocookie.open();
        }

    }
}
