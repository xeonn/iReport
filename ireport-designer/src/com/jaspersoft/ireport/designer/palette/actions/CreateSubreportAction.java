/*
 * NewReportElementPaletteItemAction.java
 * 
 * Created on 12-nov-2007, 21.30.43
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.palette.actions;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.subreport.SubreportTemplateWizard;
import com.jaspersoft.ireport.designer.subreport.SubreportWizardIterator;
import java.awt.Dialog;
import java.io.File;
import java.text.MessageFormat;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.DialogDisplayer;
import org.openide.WizardDescriptor;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataFolder;
import org.openide.loaders.TemplateWizard;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class CreateSubreportAction extends CreateReportElementAction 
{

    public JRDesignElement createReportElement(JasperDesign jd)
    {
        JRDesignElement element = null;

        SubreportTemplateWizard wizardDescriptor = new SubreportTemplateWizard();
        
        try {
            FileObject fo = IReportManager.getInstance().getActiveVisualView().getEditorSupport().getDataObject().getPrimaryFile();
            DataFolder df = DataFolder.findFolder(FileUtil.toFileObject(FileUtil.toFile(fo).getParentFile()));
            wizardDescriptor.setTargetFolder(df);
            
            // Try to create a potential subreprt name...
            String fname = fo.getName();
            
            for (int i=1; i<100; ++i)
            {
                File f = new File(df.getPrimaryFile().getPath(), fname + "_subreport" + i + ".jrxml");
                if (f.exists()) continue;

                wizardDescriptor.setTargetName( fname + "_subreport" + i);
                break;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // {0} will be replaced by WizardDescriptor.Panel.getComponent().getName()
        // {1} will be replaced by WizardDescriptor.Iterator.name()
        wizardDescriptor.setTitleFormat(new MessageFormat("{0} ({1})"));
        wizardDescriptor.setTitle("Subreport wizard");
        Dialog dialog = DialogDisplayer.getDefault().createDialog(wizardDescriptor);
        dialog.setVisible(true);
        dialog.toFront();

        
        boolean cancelled = wizardDescriptor.getValue() != WizardDescriptor.FINISH_OPTION;
        if (!cancelled) {

            element = wizardDescriptor.getElement();
            if (element.getWidth() == 0) element.setWidth(200);
            if (element.getHeight() == 0) element.setHeight(100);
        }
        
        return element;
    }
    
}
