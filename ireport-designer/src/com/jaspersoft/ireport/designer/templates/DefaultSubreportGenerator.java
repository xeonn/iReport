/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.templates;

import com.jaspersoft.ireport.designer.utils.Misc;
import java.io.File;
import java.io.FileOutputStream;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlWriter;
import org.openide.WizardDescriptor;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;

/**
 *
 * @author gtoffoli
 */
public class DefaultSubreportGenerator extends DefaultReportGenerator {

    public FileObject generateReport(WizardDescriptor wizard) {
        
        try {
            // 1. Load the selected template...
            JasperDesign jasperDesign = generateDesign(wizard);
            
            int newPageWidth = jasperDesign.getPageWidth() - jasperDesign.getLeftMargin() - jasperDesign.getRightMargin();
            int newPageHeight = jasperDesign.getPageHeight() - jasperDesign.getTopMargin() - jasperDesign.getBottomMargin();
            
            jasperDesign.setTopMargin(0);
            jasperDesign.setBottomMargin(0);
            jasperDesign.setLeftMargin(0);
            jasperDesign.setRightMargin(0);
            
            jasperDesign.setPageWidth(newPageWidth);
            jasperDesign.setPageHeight(newPageHeight);
            
            File f = getFile(wizard);
            FileOutputStream os = new FileOutputStream(f);
            JRXmlWriter.writeReport(jasperDesign, os ,"UTF8");
            
            try {
                os.close();
            } catch (Exception ex) {}
            return FileUtil.toFileObject(f);
        
        } catch (Exception ex) {
            ex.printStackTrace();
            Misc.showErrorMessage("An error has occurred generating the subreport:\n" + ex.getMessage(), "Error", ex);
            return null;
        }
    }
}
