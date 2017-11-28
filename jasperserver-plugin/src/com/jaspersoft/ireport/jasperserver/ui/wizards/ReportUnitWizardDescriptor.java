/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.jasperserver.ui.wizards;

import com.jaspersoft.ireport.designer.utils.Misc;
import com.jaspersoft.ireport.jasperserver.JServer;
import com.jaspersoft.ireport.jasperserver.JasperServerManager;
import com.jaspersoft.ireport.jasperserver.RepositoryJrxmlFile;
import com.jaspersoft.ireport.jasperserver.RepositoryReportUnit;
import com.jaspersoft.ireport.jasperserver.validation.JrxmlValidationDialog;
import com.jaspersoft.jasperserver.api.metadata.xml.domain.impl.ResourceDescriptor;
import java.awt.Component;
import java.awt.Dialog;
import java.io.File;
import java.io.FileOutputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.engine.xml.JRXmlWriter;
import org.openide.DialogDisplayer;
import org.openide.WizardDescriptor;

/**
 *
 * @author gtoffoli
 */
public class ReportUnitWizardDescriptor extends WizardDescriptor {

    private JServer server = null;
    private String parentFolder = null;
    
    private ResourceDescriptor newResourceDescriptor = null;
    
    
    private WizardDescriptor.Panel[] panels;
    
    public ReportUnitWizardDescriptor()
    {
        super();
        setTitleFormat(new MessageFormat("{0}"));
        setTitle("ReportUnit Wizard");
    }
    
    public boolean runWizard()
    {
        setPanelsAndSettings(new WizardDescriptor.ArrayIterator(getPanels()), this);
        Dialog dialog = DialogDisplayer.getDefault().createDialog(this);
        dialog.setVisible(true);
        dialog.toFront();
        boolean cancelled = this.getValue() != WizardDescriptor.FINISH_OPTION;
        if (!cancelled) {
            return createTheReportUnit();
        }
        return false;
    }

    public JServer getServer() {
        return server;
    }

    public void setServer(JServer server) {
        this.server = server;
    }

    public String getParentFolder() {
        return parentFolder;
    }

    public void setParentFolder(String parentFolder) {
        this.parentFolder = parentFolder;
    }
    
    
    private List datasources = new ArrayList();
    /**
     * Accept a list of Strings or ResourceDescriptor
     */
    public void setDatasources(List datasources)
    {
        this.datasources = datasources;
    }

    public ResourceDescriptor getNewResourceDescriptor() {
        return newResourceDescriptor;
    }

    public void setNewResourceDescriptor(ResourceDescriptor newResourceDescriptor) {
        this.newResourceDescriptor = newResourceDescriptor;
    }

    /**
     * Initialize panels representing individual wizard's steps and sets
     * various properties for them influencing wizard appearance.
     */
    private WizardDescriptor.Panel[] getPanels() {
        if (panels == null) {
            panels = new WizardDescriptor.Panel[]{
                new ReportUnitWizardPanel1(this),
                new ReportUnitWizardPanel2(this),
                new ReportUnitWizardPanel3(this)
            };
            String[] steps = new String[panels.length];
            for (int i = 0; i < panels.length; i++) {
                Component c = panels[i].getComponent();
                // Default step name to component name of panel. Mainly useful
                // for getting the name of the target chooser to appear in the
                // list of steps.
                steps[i] = c.getName();
                if (c instanceof JComponent) { // assume Swing components
                    JComponent jc = (JComponent) c;
                    // Sets step number of a component
                    jc.putClientProperty("WizardPanel_contentSelectedIndex", new Integer(i));
                    // Sets steps names for a panel
                    jc.putClientProperty("WizardPanel_contentData", steps);
                    // Turn on subtitle creation on each step
                    jc.putClientProperty("WizardPanel_autoWizardStyle", Boolean.TRUE);
                    // Show steps on the left side with the image on the background
                    jc.putClientProperty("WizardPanel_contentDisplayed", Boolean.TRUE);
                    // Turn on numbering of all steps
                    jc.putClientProperty("WizardPanel_contentNumbered", Boolean.TRUE);
                }
            }
        }
        return panels;
    }

    public List getDatasources() {
        return datasources;
    }
    
    public boolean createTheReportUnit()
    {
            File resourceFile = null;
            ResourceDescriptor rd = new ResourceDescriptor();
            
            //Stored settings:
            java.util.Iterator namesProps = getProperties().keySet().iterator();
            while (namesProps.hasNext())
            {
                String name = ""+namesProps.next();
                System.out.println(name + " " + getProperty(name));
                System.out.flush();
            }
        
            try {
                    rd.setWsType( ResourceDescriptor.TYPE_REPORTUNIT );
                    rd.setDescription(  ((String)getProperty("description")).trim() ); //getResource().getDescriptor().getDescription()
                    rd.setName( (String)getProperty("name")  );
                    String uri = getParentFolder();
                    if (!uri.endsWith("/")) uri = uri + "/";
                    uri += getProperty("name");
                    rd.setUriString( uri );
                    rd.setLabel(((String)getProperty("label")).trim() ); //getResource().getDescriptor().getLabel()  );
                    rd.setParentFolder( getParentFolder() );
                    rd.setIsNew( true );

                    // Add the datasource resource...
                    if (JasperServerManager.getMainInstance().getBrandingProperties().getProperty("ireport.manage.datasources.enabled", "true").equals("true"))
                    {
                        ResourceDescriptor tmpDataSourceDescriptor;
                        if (((String)getProperty("datasource_is_local")).equals("false")) 
                        {
                            tmpDataSourceDescriptor = new ResourceDescriptor();
                            tmpDataSourceDescriptor.setWsType( ResourceDescriptor.TYPE_DATASOURCE );
                            tmpDataSourceDescriptor.setReferenceUri( (String)getProperty("datasource_uri"));
                            tmpDataSourceDescriptor.setIsReference(true);
                        }
                        else
                        {
                            tmpDataSourceDescriptor = (ResourceDescriptor)getProperty("datasource_descriptor");
                            tmpDataSourceDescriptor.setIsReference(false);
                        }

                        rd.getChildren().add( tmpDataSourceDescriptor );

                    }


                    // Add the jrxml resource...
                    ResourceDescriptor jrxmlDescriptor = new ResourceDescriptor();
                    jrxmlDescriptor.setWsType( ResourceDescriptor.TYPE_JRXML );

                    if (((String)getProperty("jrxml_is_local")).equals("false")) 
                     {
                        jrxmlDescriptor.setIsNew(true);
                        jrxmlDescriptor.setMainReport(true);
                        jrxmlDescriptor.setIsReference(true);
                        jrxmlDescriptor.setReferenceUri( (String)getProperty("jrxml_file") );
                    }
                    else
                    {
                            jrxmlDescriptor.setName( getProperty("name") + "_jrxml");
                            jrxmlDescriptor.setLabel("Main jrxml"); //getResource().getDescriptor().getLabel()  );
                            jrxmlDescriptor.setDescription("Main jrxml"); //getResource().getDescriptor().getDescription()
                            jrxmlDescriptor.setIsNew(true);
                            jrxmlDescriptor.setHasData(true);
                            jrxmlDescriptor.setMainReport(true);
                            resourceFile = new File( (String)getProperty("jrxml_file"));
                    }
                    rd.getChildren().add( jrxmlDescriptor );
                        
                    System.out.println("Resource descriptor uri: " + rd.getUriString());
                    System.out.flush();
                    
                    // This call should be not lock... we should put it in a new thread...
                    // and maybe add a window to show the progress...
                    newResourceDescriptor = getServer().getWSClient().addOrModifyResource(rd, resourceFile);
                    
                    System.out.println("resourceFile = " + resourceFile);
                    System.out.flush();

                    if (resourceFile != null)
                    {
                        addRequiredResources(resourceFile, newResourceDescriptor);
                    }
            } catch (java.lang.Exception ex) {
                JOptionPane.showMessageDialog(Misc.getMainFrame(),JasperServerManager.getFormattedString("messages.error.3", "Error:\n {0}", new Object[] {ex.getMessage()}));
                ex.printStackTrace();
                return false;
            }
            
            return true;
    }
    
    private void addRequiredResources(File resourceFile, ResourceDescriptor rd) throws java.lang.Exception {
        
        addRequiredResources(getServer(), resourceFile, rd);
    }


    public static void addRequiredResources(JServer server, File resourceFile, ResourceDescriptor rd) throws java.lang.Exception {

        JasperDesign report = JRXmlLoader.load(resourceFile);
        List children = RepositoryJrxmlFile.identifyElementValidationItems(report, rd, resourceFile.getParent());


        if (children.size() > 0)
        {
            // We will create a temporary file somewhere else...
            String tmpFileName = JasperServerManager.createTmpFileName("newfile",".jrxml");
            JRXmlWriter.writeReport(report, new java.io.FileOutputStream(tmpFileName), "UTF-8");
            resourceFile = new File(tmpFileName);
            long modified = resourceFile.lastModified();

            System.out.println("Temporary file: " + resourceFile + " " + resourceFile.lastModified() + " " + resourceFile.exists());
            System.out.flush();

            JrxmlValidationDialog jvd = new JrxmlValidationDialog(Misc.getMainFrame(),true);
            jvd.setElementVelidationItems( children );
            jvd.setServer( server );
            jvd.setFileName(tmpFileName);
            jvd.setReportUnit( new RepositoryReportUnit(server, rd) );
            jvd.setReport( report );
            jvd.setVisible(true);
            if (jvd.getDialogResult() != JOptionPane.CANCEL_OPTION)
            {
                // Save the report in a new temporary file and store it....
                // Look for the main jrxml...
                if (modified != resourceFile.lastModified())
                {
                    System.out.println("Jrxml modified....");
                    System.out.flush();
                    for (int i=0; i<rd.getChildren().size(); ++i)
                    {
                        ResourceDescriptor rdMainJrxml = (ResourceDescriptor)rd.getChildren().get(i);
                        if (rdMainJrxml.getWsType().equals(rdMainJrxml.TYPE_JRXML) && rdMainJrxml.isMainReport())
                        {

                            rdMainJrxml.setIsNew(false);
                            rdMainJrxml.setHasData(true);
                            rdMainJrxml = server.getWSClient().modifyReportUnitResource(rd.getUriString(), rdMainJrxml, new File(tmpFileName) );
                            // Refresh reportUnitResourceDescriptor....
                            rd.getChildren().set(i, rdMainJrxml);
                            break;
                        }
                    }

                    // At this point, if the file is open we should reload it...
                    
                }
                else
                {
                    System.out.println("Not modified...." + modified + " != " + resourceFile.lastModified());
                    System.out.flush();
                }
            }
        }
    }
    
}
