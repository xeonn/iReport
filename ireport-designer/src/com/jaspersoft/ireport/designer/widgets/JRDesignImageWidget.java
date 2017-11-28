/*
 * JRDesignImageWidget.java
 * 
 * Created on 14-nov-2007, 15.56.26
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.widgets;

import com.jaspersoft.ireport.designer.AbstractReportObjectScene;
import com.jaspersoft.ireport.designer.JrxmlVisualView;
import java.awt.Component;
import java.io.File;
import net.sf.jasperreports.engine.design.JRDesignImage;
import net.sf.jasperreports.engine.util.FileResolver;
import net.sf.jasperreports.engine.util.JRResourcesUtil;
import net.sf.jasperreports.engine.util.SimpleFileResolver;
import org.openide.filesystems.FileUtil;

/**
 *
 * @author gtoffoli
 */
public class JRDesignImageWidget extends JRDesignElementWidget {

    public JRDesignImageWidget(AbstractReportObjectScene scene, JRDesignImage element) {
        super(scene, element);
    }
    
    
    @Override
    protected void paintWidget() {
        
        Component component = ((AbstractReportObjectScene)getScene()).getView();
        JrxmlVisualView visualView = null;
        while (component.getParent() != null) //FIXME: the component could be not installed...
        {
            if (component.getParent() instanceof JrxmlVisualView)
            {
                visualView = (JrxmlVisualView) component.getParent();
                break;
            }
            else
            {
                component = component.getParent();
            }
        }
        
        File reportFolder = null;
        if (visualView != null)
        {
            File file = FileUtil.toFile(visualView.getEditorSupport().getDataObject().getPrimaryFile());
            if (file.getParentFile() != null)
            {
                reportFolder = file.getParentFile();
            }
        }

        FileResolver fileResolver = new SimpleFileResolver(reportFolder);//FIXMETD can we keep the parent folder somewhere? in the draw visitor maybe?
        JRResourcesUtil.setThreadFileResolver(fileResolver);
        
        try
        {
            super.paintWidget();
        }
        finally
        {
            JRResourcesUtil.resetThreadFileResolver();
        }
    }
    
}
