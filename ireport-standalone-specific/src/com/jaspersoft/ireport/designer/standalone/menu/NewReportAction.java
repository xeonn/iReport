/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jaspersoft.ireport.designer.standalone.menu;

import com.jaspersoft.ireport.designer.standalone.EmptyReportWizardIterator;
import com.jaspersoft.ireport.designer.utils.Misc;
import java.awt.Dialog;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import org.openide.DialogDisplayer;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.filesystems.Repository;
import org.openide.loaders.DataFolder;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.loaders.TemplateWizard;
import org.openide.util.Exceptions;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CallableSystemAction;

public final class NewReportAction extends CallableSystemAction {

    public void performAction() {
      
            TemplateWizard wizardDescriptor = new TemplateWizard();
            File targetFolder = Misc.findStartingDirectory();
            DataFolder df = DataFolder.findFolder(FileUtil.toFileObject(targetFolder));
            wizardDescriptor.setTargetFolder(df);

            try {
            FileObject templateFileObject = Repository.getDefault().getDefaultFileSystem().getRoot().getFileObject("Templates/Report/Report.jrxml");
            wizardDescriptor.setTemplate(DataObject.find(templateFileObject));
            } catch (DataObjectNotFoundException ex) {
            }
            
            wizardDescriptor.setTargetName("report.jrxml");
            wizardDescriptor.putProperty("useCustomChooserPanel", "true");
            
            EmptyReportWizardIterator wIterator = new EmptyReportWizardIterator();
            wIterator.initialize(wizardDescriptor);
            wizardDescriptor.setPanelsAndSettings(wIterator, wizardDescriptor);

            // {0} will be replaced by WizardDesriptor.Panel.getComponent().getName()
            wizardDescriptor.setTitleFormat(new MessageFormat("{0}"));
            wizardDescriptor.setTitle("New report");
            Dialog dialog = DialogDisplayer.getDefault().createDialog(wizardDescriptor);
            dialog.setVisible(true);
            dialog.toFront();
         
    }

    public String getName() {
        return NbBundle.getMessage(NewReportAction.class, "CTL_NewReportAction");
    }

    @Override
    protected void initialize() {
        super.initialize();
        // see org.openide.util.actions.SystemAction.iconResource() Javadoc for more details
        putValue("noIconInMenu", Boolean.TRUE);
    }

    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    protected boolean asynchronous() {
        return false;
    }
}
