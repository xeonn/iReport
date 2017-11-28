/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.jasperserver.ui.actions;

import com.jaspersoft.ireport.designer.palette.actions.CreateReportElementAction;
import com.jaspersoft.ireport.jasperserver.RepoImageCache;
import com.jaspersoft.ireport.jasperserver.RepositoryFile;
import java.io.File;
import javax.swing.ImageIcon;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignImage;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRExpressionUtil;

/**
 *
 * @author gtoffoli
 */
public class CreateImageAction  extends CreateReportElementAction 
{

    public JRDesignElement createReportElement(JasperDesign jd)
    {
            RepositoryFile file = (RepositoryFile) getPaletteItem().getData();
            
            
            JRDesignElement element = new JRDesignImage(jd);
            element.setWidth(100);
            element.setHeight(50);
            
            JRDesignExpression exp = new JRDesignExpression();
            exp.setValueClassName("java.lang.String");
            exp.setText("\"repo:" + file.getDescriptor().getUriString()+"\"");
            
            // Try to load the image...
            try {
                String fname = file.getFile();
                
                ImageIcon img = new ImageIcon(fname);
                element.setWidth(img.getIconWidth());
                element.setHeight(img.getIconHeight());
                
                RepoImageCache.getInstance().put( JRExpressionUtil.getSimpleExpressionText(exp) , new File(fname));
                
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            
            ((JRDesignImage)element).setExpression(exp);
            
            return element;
    }
}
