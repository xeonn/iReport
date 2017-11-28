/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.jasperserver.ui.actions;

import com.jaspersoft.ireport.designer.AbstractReportObjectScene;
import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.ModelUtils;
import com.jaspersoft.ireport.designer.ReportObjectScene;
import com.jaspersoft.ireport.jasperserver.JServer;
import com.jaspersoft.ireport.jasperserver.JasperServerManager;
import com.jaspersoft.ireport.jasperserver.RepoImageCache;
import com.jaspersoft.ireport.jasperserver.RepositoryReportUnit;
import com.jaspersoft.jasperserver.api.metadata.xml.domain.impl.ResourceDescriptor;
import java.io.File;
import java.util.Collection;
import java.util.List;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JRDesignImage;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRExpressionUtil;
import org.openide.loaders.DataObject;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.Mutex;
import org.openide.util.RequestProcessor;

/**
 *
 * @author gtoffoli
 */
public class JrxmLookupListener implements LookupListener {
    
    private final Lookup.Result <JasperDesign> result;
    private RepositoryReportUnit parentReportUnit = null;
    private JServer server = null;
    
    /**
     * This is a pointer to the list that holds this object preventig it is removed
     * from the garbage collector. This class will remove hisself from the list
     * to destroy itself when his job is done.
     */
    List jrxmlListeners = null;
    
    public JrxmLookupListener(DataObject obj, 
                              List jrxmlListeners,
                              RepositoryReportUnit parentReportUnit,
                              JServer server)
    {
        this.server = server;
        this.parentReportUnit = parentReportUnit;
        
        result = obj.getLookup().lookup(new Lookup.Template(JasperDesign.class));
        result.addLookupListener(this);
        result.allItems();
        resultChanged(null);
        this.jrxmlListeners = jrxmlListeners;
                                
    }
    
    public void resultChanged(LookupEvent ev) {
        
        
        Collection<? extends JasperDesign> jds = result.allInstances();
        if (jds.size() > 0)
        {
            // JasperDesign found...
            final JasperDesign jd = jds.iterator().next();

            // Look inside the jasperdesign for all the images having a simple expression
            // starting with repo: ....
            Runnable run = new Runnable() {

                public void run() {
                    List<JRDesignElement> elements = ModelUtils.getAllElements(jd);
                    for (JRDesignElement ele : elements)
                    {
                        if (ele instanceof JRDesignImage)
                        {
                            JRDesignImage img = (JRDesignImage)ele;
                            if (img.getExpression() != null &&
                                img.getExpression().getText() != null &&
                                img.getExpression().getValueClassName().equals("java.lang.String"))
                            {
                                String s = JRExpressionUtil.getSimpleExpressionText(img.getExpression());
                                if (s.startsWith("repo:"))
                                {
                                    String uri = s.substring(5);
                                    
                                    ResourceDescriptor rd = new ResourceDescriptor();
                                    if (!uri.startsWith("/") && getParentReportUnit() != null)
                                    {
                                        uri = getParentReportUnit().getDescriptor().getUriString() + "_files/" + uri;
                                    }
                                    rd.setUriString(uri);
                                    try {
                                        rd = getServer().getWSClient().get(rd, null);
                                        String fname = JasperServerManager.createTmpFileName("img","");
                                        rd = getServer().getWSClient().get(rd, new File(fname));
                                        RepoImageCache.getInstance().put(s, new File(fname));
                                    } catch (Exception ex)
                                    {
                                       ex.printStackTrace(); 
                                    }
                                }
                            }
                        }
                    }
                    
                    Mutex.EVENT.readAccess(new Runnable() {
                        public void run() {
                            try {
                                ReportObjectScene scene = IReportManager.getInstance().getActiveVisualView().getReportDesignerPanel().getScene();
                                scene.refreshBands();
                            } catch (Exception ex) { }
                        }
                    });
                }
            };
            
            RequestProcessor.getDefault().post(run);
            
            result.removeLookupListener(this);
            if (jrxmlListeners != null)
            {
                jrxmlListeners.remove(this);
            }
        }
    }

    public RepositoryReportUnit getParentReportUnit() {
        return parentReportUnit;
    }

    public void setParentReportUnit(RepositoryReportUnit parentReportUnit) {
        this.parentReportUnit = parentReportUnit;
    }

    public JServer getServer() {
        return server;
    }

    public void setServer(JServer server) {
        this.server = server;
    }

}
