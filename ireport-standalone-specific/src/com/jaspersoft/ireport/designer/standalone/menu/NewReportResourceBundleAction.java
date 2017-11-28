/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jaspersoft.ireport.designer.standalone.menu;

import com.jaspersoft.ireport.designer.standalone.wizards.CustomTemplateWizard;
import com.jaspersoft.ireport.designer.utils.Misc;
import java.awt.Dialog;
import java.io.File;
import org.openide.DialogDisplayer;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.filesystems.Repository;
import org.openide.loaders.DataFolder;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CallableSystemAction;

public final class NewReportResourceBundleAction extends CallableSystemAction {

    public void performAction() {
      
            CustomTemplateWizard wizardDescriptor = new CustomTemplateWizard();

            File targetFolder = Misc.findStartingDirectory();
            DataFolder df = DataFolder.findFolder(FileUtil.toFileObject(targetFolder));
            wizardDescriptor.setTargetFolder(df);
            wizardDescriptor.setTargetName("Bundle");
            

            try {
                FileObject templateFileObject = Repository.getDefault().getDefaultFileSystem().getRoot().getFileObject("Templates/Resource_Bundle/Bundle.properties");
                wizardDescriptor.setTemplate(DataObject.find(templateFileObject));
            } catch (DataObjectNotFoundException ex) {
            }
            
            wizardDescriptor.setTitle("New Report Resource Bundle");
            Dialog dialog = DialogDisplayer.getDefault().createDialog(wizardDescriptor);
            dialog.setVisible(true);
            dialog.toFront();
         
    }

    public String getName() {
        return NbBundle.getMessage(NewReportResourceBundleAction.class, "CTL_NewReportBundle");
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
