package com.jaspersoft.ireport.jasperserver.ui.actions;

import com.jaspersoft.ireport.designer.utils.Misc;
import com.jaspersoft.ireport.jasperserver.JServer;
import com.jaspersoft.ireport.jasperserver.JasperServerManager;
import com.jaspersoft.ireport.jasperserver.RepositoryFolder;
import com.jaspersoft.ireport.jasperserver.RepositoryReportUnit;
import com.jaspersoft.ireport.jasperserver.ui.nodes.FolderNode;
import com.jaspersoft.ireport.jasperserver.ui.nodes.ReportUnitInputControlsNode;
import com.jaspersoft.ireport.jasperserver.ui.nodes.ReportUnitNode;
import com.jaspersoft.ireport.jasperserver.ui.nodes.ReportUnitResourcesNode;
import com.jaspersoft.ireport.jasperserver.ui.nodes.ResourceNode;
import com.jaspersoft.ireport.jasperserver.ui.resources.DataSourceDialog;
import com.jaspersoft.ireport.jasperserver.ui.resources.DataTypeDialog;
import com.jaspersoft.ireport.jasperserver.ui.resources.InputControlDialog;
import com.jaspersoft.ireport.jasperserver.ui.resources.ListOfValuesDialog;
import com.jaspersoft.ireport.jasperserver.ui.resources.NewResourceDialog;
import com.jaspersoft.ireport.jasperserver.ui.resources.QueryDialog;
import com.jaspersoft.ireport.jasperserver.ui.resources.ResourceReferenceDialog;
import com.jaspersoft.ireport.jasperserver.ui.resources.XMLAConnectionDialog;
import com.jaspersoft.ireport.jasperserver.ui.wizards.ReportUnitWizardDescriptor;
import com.jaspersoft.jasperserver.api.metadata.xml.domain.impl.ResourceDescriptor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.NodeAction;

public class AddResourceAction extends NodeAction {

    JMenu jMenuAdd = null;
    JSeparator jSeparator1 = null;
    JSeparator jSeparator2 = null;
    JSeparator jSeparator3 = null;
    JSeparator jSeparator4 = null;
    
    JMenuItem jMenuItemReportUnit = null;
    JMenuItem jMenuItemFolder = null;
    JMenuItem jMenuItemReference = null;
    JMenuItem jMenuItemImage = null;
    JMenuItem jMenuItemBundle = null;
    JMenuItem jMenuItemJrxml = null;
    JMenuItem jMenuItemJar = null;
    JMenuItem jMenuItemFont = null;
    JMenuItem jMenuItemDatasource = null;
    JMenuItem jMenuItemXMLADatasource = null;
    JMenuItem jMenuItemDataType = null;
    JMenuItem jMenuItemListOfValues = null;
    JMenuItem jMenuItemQuery = null;
    JMenuItem jMenuItemInputControl = null;
    
    public String getName() {
        return NbBundle.getMessage(AddResourceAction.class, "CTL_PropertiesAction");
    }

    @Override
    public JMenuItem getPopupPresenter() {
        
        
        if (jMenuAdd == null)
        {
            initMenu();
        }
        
        jMenuItemReference.setEnabled( getActivatedNodes()[0] instanceof ReportUnitNode ||
                                       getActivatedNodes()[0] instanceof ReportUnitInputControlsNode ||
                                       getActivatedNodes()[0] instanceof ReportUnitResourcesNode);
        jMenuItemReportUnit.setEnabled( getActivatedNodes()[0] instanceof FolderNode);
        jMenuItemFolder.setEnabled( getActivatedNodes()[0] instanceof FolderNode);
        jMenuItemDatasource.setEnabled( getActivatedNodes()[0] instanceof FolderNode);
        jMenuItemXMLADatasource.setEnabled( getActivatedNodes()[0] instanceof FolderNode);
        jMenuItemDataType.setEnabled( getActivatedNodes()[0] instanceof FolderNode);
        jMenuItemListOfValues.setEnabled( getActivatedNodes()[0] instanceof FolderNode);
        
        
        
        return jMenuAdd;
    }
    
    public void initMenu()
    {
        
        jMenuAdd = new JMenu();
        jMenuAdd.setText( JasperServerManager.getString("menu.add", "Add") );
        
        jMenuItemReportUnit = new javax.swing.JMenuItem();
        jMenuItemReportUnit.setText( JasperServerManager.getString("menu.reportUnit", "Report Unit") );
        jMenuItemReportUnit.setIcon( new ImageIcon(AddResourceAction.class.getResource("/com/jaspersoft/ireport/jasperserver/res/reportunit.png")));
        jMenuItemReportUnit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addResource( ResourceDescriptor.TYPE_REPORTUNIT);
            }
        });
        jMenuAdd.add(jMenuItemReportUnit);
        
        jSeparator1 = new javax.swing.JSeparator();
        jMenuAdd.add(jSeparator1);
        
        jMenuItemFolder = new javax.swing.JMenuItem();
	jMenuItemFolder.setText( JasperServerManager.getString("menu.folder", "Folder") );
        jMenuItemFolder.setIcon( new ImageIcon(AddResourceAction.class.getResource("/com/jaspersoft/ireport/jasperserver/res/folder.png")));
        jMenuItemFolder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addResource( ResourceDescriptor.TYPE_FOLDER);
            }
        });
        jMenuAdd.add(jMenuItemFolder);
        
        jMenuItemReference = new javax.swing.JMenuItem();
        jMenuItemReference.setText( JasperServerManager.getString("menu.reference", "Reference") );
        jMenuItemReference.setIcon( new ImageIcon(AddResourceAction.class.getResource("/com/jaspersoft/ireport/jasperserver/res/link.png")));
        jMenuItemReference.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addResource( ResourceDescriptor.TYPE_REFERENCE);
            }
        });
        jMenuAdd.add(jMenuItemReference);
        
        jSeparator2 = new javax.swing.JSeparator();
        jMenuAdd.add(jSeparator2);
                
        jMenuItemImage = new javax.swing.JMenuItem();
        jMenuItemImage.setText( JasperServerManager.getString("menu.image", "Image") );
        jMenuItemImage.setIcon( new ImageIcon(AddResourceAction.class.getResource("/com/jaspersoft/ireport/jasperserver/res/picture.png")));
        jMenuItemImage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addResource( ResourceDescriptor.TYPE_IMAGE);
            }
        });
        jMenuAdd.add(jMenuItemImage);
        
        jMenuItemBundle = new javax.swing.JMenuItem();
        jMenuItemBundle.setText( JasperServerManager.getString("menu.bundle", "Resource bundle") );
        jMenuItemBundle.setIcon( new ImageIcon(AddResourceAction.class.getResource("/com/jaspersoft/ireport/jasperserver/res/bundle.png")));
        jMenuItemBundle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addResource( ResourceDescriptor.TYPE_RESOURCE_BUNDLE);
            }
        });
        jMenuAdd.add(jMenuItemBundle);
        
        jMenuItemJrxml = new javax.swing.JMenuItem();
        jMenuItemJrxml.setText( JasperServerManager.getString("menu.Jrxml", "JRXML document") );
        jMenuItemJrxml.setIcon( new ImageIcon(AddResourceAction.class.getResource("/com/jaspersoft/ireport/jasperserver/res/jrxml_file.png")));
        jMenuItemJrxml.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addResource( ResourceDescriptor.TYPE_JRXML);
            }
        });
        jMenuAdd.add(jMenuItemJrxml);
        
        jMenuItemJar = new javax.swing.JMenuItem();
        jMenuItemJar.setText( JasperServerManager.getString("menu.jar", "Jar archive") );
        jMenuItemJar.setIcon( new ImageIcon(AddResourceAction.class.getResource("/com/jaspersoft/ireport/jasperserver/res/jar.png")));
        jMenuItemJar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addResource( ResourceDescriptor.TYPE_CLASS_JAR);
            }
        });
        jMenuAdd.add(jMenuItemJar);
        
        jMenuItemFont = new javax.swing.JMenuItem();
        jMenuItemFont.setText( JasperServerManager.getString("menu.font", "Font file") );
        jMenuItemFont.setIcon( new ImageIcon(AddResourceAction.class.getResource("/com/jaspersoft/ireport/jasperserver/res/font.png")));
        jMenuItemFont.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addResource( ResourceDescriptor.TYPE_FONT);
            }
        });
        jMenuAdd.add(jMenuItemFont);
        
        jSeparator3 = new javax.swing.JSeparator();
        jMenuAdd.add(jSeparator3);
        
        jMenuItemDatasource = new javax.swing.JMenuItem();
        jMenuItemDatasource.setText( JasperServerManager.getString("menu.datasource", "Datasource") );
        jMenuItemDatasource.setIcon( new ImageIcon(AddResourceAction.class.getResource("/com/jaspersoft/ireport/jasperserver/res/datasource.png")));
        jMenuItemDatasource.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addResource( ResourceDescriptor.TYPE_DATASOURCE);
            }
        });
        jMenuAdd.add(jMenuItemDatasource);
        
        jMenuItemXMLADatasource = new javax.swing.JMenuItem();
        jMenuItemXMLADatasource.setText( JasperServerManager.getString("menu.xmlaConnection", "XMLA Connection") );
        jMenuItemXMLADatasource.setIcon( new ImageIcon(AddResourceAction.class.getResource("/com/jaspersoft/ireport/jasperserver/res/datasource.png")));
        jMenuItemXMLADatasource.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addResource( ResourceDescriptor.TYPE_OLAP_XMLA_CONNECTION);
            }
        });
        jMenuAdd.add(jMenuItemXMLADatasource);
        
        jSeparator4 = new javax.swing.JSeparator();
        jMenuAdd.add(jSeparator4);
        
        jMenuItemDataType = new javax.swing.JMenuItem();
        jMenuItemDataType.setText( JasperServerManager.getString("menu.dataType", "Datatype") );
        jMenuItemDataType.setIcon( new ImageIcon(AddResourceAction.class.getResource("/com/jaspersoft/ireport/jasperserver/res/datatype.png")));
        jMenuItemDataType.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addResource( ResourceDescriptor.TYPE_DATA_TYPE);
            }
        });
        jMenuAdd.add(jMenuItemDataType);
        
        jMenuItemListOfValues = new javax.swing.JMenuItem();
        jMenuItemListOfValues.setText( JasperServerManager.getString("menu.listOfValues", "List of values") );
        jMenuItemListOfValues.setIcon( new ImageIcon(AddResourceAction.class.getResource("/com/jaspersoft/ireport/jasperserver/res/lov.png")));
        jMenuItemListOfValues.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addResource( ResourceDescriptor.TYPE_LOV);
            }
        });
        jMenuAdd.add(jMenuItemListOfValues);
        
        jMenuItemQuery = new javax.swing.JMenuItem();
        jMenuItemQuery.setText( JasperServerManager.getString("menu.query", "Query") );
        jMenuItemQuery.setIcon( new ImageIcon(AddResourceAction.class.getResource("/com/jaspersoft/ireport/jasperserver/res/query.png")));
        jMenuItemQuery.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addResource( ResourceDescriptor.TYPE_QUERY);
            }
        });
        jMenuAdd.add(jMenuItemQuery);
        
        jMenuItemInputControl = new javax.swing.JMenuItem();
        jMenuItemInputControl.setText( JasperServerManager.getString("menu.inputControl", "Input control") );
        jMenuItemInputControl.setIcon( new ImageIcon(AddResourceAction.class.getResource("/com/jaspersoft/ireport/jasperserver/res/inputcontrol.png")));
        jMenuItemInputControl.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addResource( ResourceDescriptor.TYPE_INPUT_CONTROL);
            }
        });
        jMenuAdd.add(jMenuItemInputControl);
    }
    

//    @Override
//    public JMenuItem getMenuPresenter() {
//        return menu;
//    }

    
    
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
        
            System.out.println("Eccci!! " + activatedNodes.toString());
            System.out.flush();
    }

    protected boolean enable(org.openide.nodes.Node[] activatedNodes) {
        if (activatedNodes == null || activatedNodes.length != 1) return false;
        if ( activatedNodes[0] instanceof ResourceNode)
        {
            return true;
        }
        return false;
    }

    protected void addChild(ResourceNode parentNode, JServer server, ResourceDescriptor newResourceDescriptor) {
        RepositoryFolder obj = RepositoryFolder.createRepositoryObject(server, newResourceDescriptor);
        if (parentNode.getRepositoryObject().isLoaded())
        {
            parentNode.getResourceDescriptor().getChildren().add( newResourceDescriptor );
            parentNode.getRepositoryObject().getChildren().add(obj);
            parentNode.refreshChildrens(false);
        }
    }
    
    protected void addResource(String resourceType)
    {
        Node[] activatedNodes = getActivatedNodes();
        if (activatedNodes == null ||
            activatedNodes.length != 1 ||
            !(activatedNodes[0] instanceof ResourceNode)) return;
        
        ResourceNode selectedNode = (ResourceNode)activatedNodes[0];
        addResource(selectedNode, resourceType);
    }
    
    public void addResource(ResourceNode selectedNode, String resourceType)
    {
        
        JServer server = null;
        
        String currentUri = "/";
        String reportUnitUri = null;
        
        boolean parentLoaded = false;
        
        RepositoryFolder rf = ((ResourceNode)selectedNode).getRepositoryObject();
        server = rf.getServer();
        currentUri = rf.getDescriptor().getUriString();
        if (rf instanceof RepositoryReportUnit)
        {
            reportUnitUri = currentUri;
        }
        parentLoaded = rf.isLoaded();
        
        
        if  (resourceType.equals(ResourceDescriptor.TYPE_REPORTUNIT))
        {
            List datasources = null;

            if (JasperServerManager.getMainInstance().getBrandingProperties().getProperty("ireport.manage.datasources.enabled", "true").equals("true"))
            {
                try {
                    datasources = server.getWSClient().listDatasources();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(Misc.getMainFrame(),
                            JasperServerManager.getFormattedString("repositoryExplorer.message.errorListingDatasources", "Error getting the list of available datasources:\n{0}", new Object[] {ex.getMessage()}));
                    ex.printStackTrace();
                    return;
                }

                if (datasources == null || datasources.size() == 0) {
                    JOptionPane.showMessageDialog(Misc.getMainFrame(),
                            JasperServerManager.getString("repositoryExplorer.message.noDatasourceFound",
                            "No datasources was found on the server.\nPlease create a new datasource on the server before create a report unit.")) ;
                    return;
                }
            } else {
                datasources = new java.util.ArrayList();
            }

            ReportUnitWizardDescriptor wizardDescriptor = new ReportUnitWizardDescriptor();
        
            wizardDescriptor.setParentFolder(currentUri);
            wizardDescriptor.setDatasources(datasources);
            wizardDescriptor.setServer(server);
            if (wizardDescriptor.runWizard())
            {
                ResourceDescriptor rd = wizardDescriptor.getNewResourceDescriptor();
                if (rd != null) {
                    addChild(selectedNode, server, rd);
                }
            }
        
        }
        else if  (resourceType.equals(ResourceDescriptor.TYPE_DATASOURCE))
        {
            DataSourceDialog nrd = new DataSourceDialog(Misc.getMainFrame(), true);

            nrd.setParentFolder(currentUri);
            nrd.setServer(server);
            //nrd.setReportUnitUri(reportUnitUri);
            //nrd.setResourceType(resourceType);

            nrd.setVisible(true);

            if (nrd.getDialogResult() == JOptionPane.OK_OPTION)
            {
                addChild(selectedNode, server, nrd.getNewResourceDescriptor());
            }
        }
        else if  (resourceType.equals(ResourceDescriptor.TYPE_OLAP_XMLA_CONNECTION))
        {
            XMLAConnectionDialog nrd = new XMLAConnectionDialog(Misc.getMainFrame(), true);

            nrd.setParentFolder(currentUri);
            nrd.setServer(server);
            //nrd.setReportUnitUri(reportUnitUri);
            //nrd.setResourceType(resourceType);

            nrd.setVisible(true);

            if (nrd.getDialogResult() == JOptionPane.OK_OPTION)
            {
                addChild(selectedNode, server, nrd.getNewResourceDescriptor());
            }
        }
        else if (resourceType.equals(ResourceDescriptor.TYPE_REFERENCE))
        {
            ResourceReferenceDialog rrd = new ResourceReferenceDialog(Misc.getMainFrame(), true);
            rrd.setServer(server);
            rrd.setParentFolder(currentUri);
            //ResourceChooser rc = new ResourceChooser();
            //rc.setServer( server );
            //rc.showDialog(this, null);
            rrd.setVisible(true);
            
            if (rrd.getDialogResult() == JOptionPane.OK_OPTION)
            {
                addChild(selectedNode, server, rrd.getNewResourceDescriptor());
            }
        }
        else if  (resourceType.equals(ResourceDescriptor.TYPE_DATA_TYPE))
        {
            DataTypeDialog nrd = new DataTypeDialog(Misc.getMainFrame(), true);

            nrd.setParentFolder(currentUri);
            nrd.setServer(server);

            nrd.setVisible(true);

            if (nrd.getDialogResult() == JOptionPane.OK_OPTION)
            {
                addChild(selectedNode, server, nrd.getNewResourceDescriptor());
            }
        }
        else if  (resourceType.equals(ResourceDescriptor.TYPE_LOV))
        {
            ListOfValuesDialog nrd = new ListOfValuesDialog(Misc.getMainFrame(), true);

            nrd.setParentFolder(currentUri);
            nrd.setServer(server);

            nrd.setVisible(true);

            if (nrd.getDialogResult() == JOptionPane.OK_OPTION)
            {
                addChild(selectedNode, server, nrd.getNewResourceDescriptor());
            }
        }
        else if  (resourceType.equals(ResourceDescriptor.TYPE_QUERY))
        {
            QueryDialog nrd = new QueryDialog(Misc.getMainFrame(), true);

            nrd.setParentFolder(currentUri);
            nrd.setServer(server);

            if (JasperServerManager.getMainInstance().getBrandingProperties().getProperty("ireport.manage.datasources.enabled", "true").equals("true"))
            {
                List datasources = null;
                try {
                       datasources = server.getWSClient().listDatasources(); 
                       nrd.setDatasources(datasources);
                } catch (Exception ex)
                {

                   JOptionPane.showMessageDialog(Misc.getMainFrame(),
                           JasperServerManager.getFormattedString("repositoryExplorer.message.errorListingDatasources", "Error getting the list of available datasources:\n{0}", new Object[] {ex.getMessage()}));
                   ex.printStackTrace();
                }
            }
            
            nrd.setVisible(true);
            
            if (nrd.getDialogResult() == JOptionPane.OK_OPTION)
            {
                addChild(selectedNode, server, nrd.getNewResourceDescriptor());
            }
        }
        else if  (resourceType.equals(ResourceDescriptor.TYPE_INPUT_CONTROL))
        {
            InputControlDialog nrd = new InputControlDialog(Misc.getMainFrame(), true);

            nrd.setParentFolder(currentUri);
            nrd.setServer(server);
            nrd.setReportUnitUri(reportUnitUri);

            nrd.setVisible(true);

            if (nrd.getDialogResult() == JOptionPane.OK_OPTION)
            {
//                if (parentLoaded)
//                {
//                    if (reportUnitUri != null)
//                    {
//                        if ( selectedNode.getUserObject() instanceof ControlsSet)
//                        {
//                            addChild(selectedNode, server, nrd.getNewResourceDescriptor());
//                        }
//                        else if (selectedNode.getUserObject() instanceof RepositoryReportUnit)
//                        {
//                            for (int i=0; i< selectedNode.getChildCount(); ++i)
//                            {
//                                DefaultMutableTreeNode dmtn = selectedNode.getChildAt(i);
//                                if (dmtn.getUserObject() instanceof ControlsSet)
//                                {
//                                    addChild(dmtn, server, nrd.getNewResourceDescriptor());
//                                    break;
//                                }
//                            }
//                        }
//                    }
               addChild(selectedNode, server, nrd.getNewResourceDescriptor());
            }
        }
        else
        {
            NewResourceDialog nrd = new NewResourceDialog(Misc.getMainFrame(), true);

            nrd.setParentUri(currentUri);
            nrd.setServer(server);
            nrd.setReportUnitUri(reportUnitUri);
            nrd.setResourceType(resourceType);

            nrd.setVisible(true);

            if (nrd.getDialogResult() == JOptionPane.OK_OPTION)
            {
                addChild(selectedNode, server, nrd.getNewResourceDescriptor());
            }
        
        }
    }
}