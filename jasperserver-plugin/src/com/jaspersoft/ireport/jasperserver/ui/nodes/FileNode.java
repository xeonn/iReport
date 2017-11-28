/*
 * iReport - Visual Designer for JasperReports.
 * Copyright (C) 2002 - 2009 Jaspersoft Corporation. All rights reserved.
 * http://www.jaspersoft.com
 *
 * Unless you have purchased a commercial license agreement from Jaspersoft,
 * the following license terms apply:
 *
 * This program is part of iReport.
 *
 * iReport is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * iReport is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with iReport. If not, see <http://www.gnu.org/licenses/>.
 */
package com.jaspersoft.ireport.jasperserver.ui.nodes;

import com.jaspersoft.ireport.designer.dnd.ReportObjectPaletteTransferable;
import com.jaspersoft.ireport.designer.outline.nodes.IRAbstractNode;
import com.jaspersoft.ireport.jasperserver.RepositoryFile;
import com.jaspersoft.ireport.jasperserver.RepositoryFolder;
import com.jaspersoft.ireport.jasperserver.ui.actions.DeleteAction;
import com.jaspersoft.ireport.jasperserver.ui.actions.ImportDatasourceAction;
import com.jaspersoft.ireport.jasperserver.ui.actions.ImportXMLADatasourceAction;
import com.jaspersoft.ireport.jasperserver.ui.actions.OpenFileAction;
import com.jaspersoft.ireport.jasperserver.ui.actions.PropertiesAction;
import com.jaspersoft.ireport.jasperserver.ui.actions.RefreshAction;
import com.jaspersoft.ireport.jasperserver.ui.actions.ReplaceFileAction;
import com.jaspersoft.ireport.jasperserver.ui.actions.RunReportUnitAction;
import com.jaspersoft.jasperserver.api.metadata.xml.domain.impl.ResourceDescriptor;
import java.awt.Image;
import java.awt.datatransfer.Transferable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Action;
import javax.swing.ImageIcon;
import org.openide.actions.CopyAction;
import org.openide.actions.CutAction;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.actions.SystemAction;
import org.openide.util.datatransfer.ExTransferable;

/**
 *
 * @author gtoffoli
 */
public class FileNode extends IRAbstractNode implements ResourceNode {

    static ImageIcon datasourceJndiIcon;
    static ImageIcon datasourceJdbcIcon;
    static ImageIcon datasourceBeanIcon;
    static ImageIcon datasourceIcon;
    static ImageIcon imageIcon;
    static ImageIcon jrxmlIcon;
    static ImageIcon fontIcon;
    static ImageIcon jarIcon;
    static ImageIcon refIcon;
    static ImageIcon bundleIcon;
    static ImageIcon inputcontrolIcon;
    static ImageIcon datatypeIcon;
    static ImageIcon lovIcon;
    static ImageIcon unknowIcon;
    static ImageIcon queryIcon;
    static ImageIcon waitingIcon;
    static ImageIcon styleTemplateIcon;
    static ImageIcon reportOptionsResourceIcon;
    
    static 
    {
        if (datasourceJndiIcon == null) datasourceJndiIcon = new javax.swing.ImageIcon(FileNode.class.getResource("/com/jaspersoft/ireport/jasperserver/res/datasource_jndi.png"));
        if (datasourceIcon == null) datasourceIcon = new javax.swing.ImageIcon(FileNode.class.getResource("/com/jaspersoft/ireport/jasperserver/res/datasource.png"));
        if (datasourceJdbcIcon == null) datasourceJdbcIcon = new javax.swing.ImageIcon(FileNode.class.getResource("/com/jaspersoft/ireport/jasperserver/res/datasource_jdbc.png"));
        if (imageIcon == null) imageIcon = new javax.swing.ImageIcon(FileNode.class.getResource("/com/jaspersoft/ireport/jasperserver/res/picture.png"));
        if (jrxmlIcon == null) jrxmlIcon = new javax.swing.ImageIcon(FileNode.class.getResource("/com/jaspersoft/ireport/jasperserver/res/jrxml_file.png"));
        if (styleTemplateIcon == null) styleTemplateIcon = new javax.swing.ImageIcon(FileNode.class.getResource("/com/jaspersoft/ireport/jasperserver/res/style-16.png"));

        if (refIcon == null) refIcon = new javax.swing.ImageIcon(FileNode.class.getResource("/com/jaspersoft/ireport/jasperserver/res/link.png"));
        if (bundleIcon == null) bundleIcon = new javax.swing.ImageIcon(FileNode.class.getResource("/com/jaspersoft/ireport/jasperserver/res/bundle.png"));
        if (fontIcon == null) fontIcon = new javax.swing.ImageIcon(FileNode.class.getResource("/com/jaspersoft/ireport/jasperserver/res/font.png"));
        if (jarIcon == null) jarIcon = new javax.swing.ImageIcon(FileNode.class.getResource("/com/jaspersoft/ireport/jasperserver/res/jar.png"));
        if (inputcontrolIcon == null) inputcontrolIcon = new javax.swing.ImageIcon(FileNode.class.getResource("/com/jaspersoft/ireport/jasperserver/res/inputcontrol.png"));
        if (datatypeIcon == null) datatypeIcon = new javax.swing.ImageIcon(FileNode.class.getResource("/com/jaspersoft/ireport/jasperserver/res/datatype.png"));
        if (lovIcon == null) lovIcon = new javax.swing.ImageIcon(FileNode.class.getResource("/com/jaspersoft/ireport/jasperserver/res/lov.png"));
        
        if (datasourceBeanIcon == null) datasourceBeanIcon = new javax.swing.ImageIcon(FileNode.class.getResource("/com/jaspersoft/ireport/jasperserver/res/datasource_bean.png"));
        if (unknowIcon == null) unknowIcon = new javax.swing.ImageIcon(FileNode.class.getResource("/com/jaspersoft/ireport/jasperserver/res/unknow.png"));
        if (queryIcon == null) queryIcon = new javax.swing.ImageIcon(FileNode.class.getResource("/com/jaspersoft/ireport/jasperserver/res/query.png"));
        
        if (waitingIcon == null) waitingIcon = new javax.swing.ImageIcon(FileNode.class.getResource("/com/jaspersoft/ireport/jasperserver/res/waiting.png"));

        if (reportOptionsResourceIcon == null) reportOptionsResourceIcon  = new javax.swing.ImageIcon(FileNode.class.getResource("/com/jaspersoft/ireport/jasperserver/res/reportunit_options.png"));
    }
    
    private RepositoryFile file = null;
    
    
    
    public FileNode(RepositoryFile file, Lookup doLkp) {
        super(Children.LEAF, doLkp);
        this.file = file;
    }
    
    @Override
    public String getDisplayName() {
        return getFile().getDescriptor().getLabel();
    }

    @Override
    public Image getIcon(int arg0) {

        return getResourceIcon(getFile().getDescriptor()).getImage();
        
    }
    
    public boolean hasCustomizer() {
        return false;
    }
    
    public static ImageIcon getResourceIcon(ResourceDescriptor resource)
    {
        if (resource == null) return unknowIcon;
        else if (resource.getWsType() == null) return unknowIcon;
        else if (resource.getIsReference()) return refIcon;
        //else if (resource.getWsType().equals(ResourceDescriptor.TYPE_FOLDER)) return folderIcon;
        //else if (resource.getWsType().equals(ResourceDescriptor.TYPE_REPORTUNIT)) return reportUnitIcon;
        else if (resource.getWsType().equals(ResourceDescriptor.TYPE_DATASOURCE_JNDI)) return datasourceJndiIcon;
        else if (resource.getWsType().equals(ResourceDescriptor.TYPE_DATASOURCE_JDBC)) return datasourceJdbcIcon;
        else if (resource.getWsType().equals(ResourceDescriptor.TYPE_DATASOURCE_BEAN)) return datasourceBeanIcon;
        else if (resource.getWsType().equals(ResourceDescriptor.TYPE_OLAP_XMLA_CONNECTION)) return datasourceIcon;
        else if (resource.getWsType().equals(ResourceDescriptor.TYPE_IMAGE)) return imageIcon;
        else if (resource.getWsType().equals(ResourceDescriptor.TYPE_JRXML)) return jrxmlIcon;
        else if (resource.getWsType().equals(ResourceDescriptor.TYPE_FONT)) return fontIcon;
        else if (resource.getWsType().equals(ResourceDescriptor.TYPE_CLASS_JAR)) return jarIcon;
        else if (resource.getWsType().equals(ResourceDescriptor.TYPE_RESOURCE_BUNDLE)) return bundleIcon;
        else if (resource.getWsType().equals(ResourceDescriptor.TYPE_INPUT_CONTROL)) return inputcontrolIcon;
        else if (resource.getWsType().equals(ResourceDescriptor.TYPE_DATA_TYPE)) return datatypeIcon;
        else if (resource.getWsType().equals(ResourceDescriptor.TYPE_LOV)) return lovIcon;
        else if (resource.getWsType().equals(ResourceDescriptor.TYPE_QUERY)) return queryIcon;
        else if (resource.getWsType().equals(ResourceDescriptor.TYPE_STYLE_TEMPLATE)) return styleTemplateIcon;
        else if (resource.getWsType().equals("ReportOptionsResource")) return reportOptionsResourceIcon;
        return unknowIcon;
        
    }
    
    public RepositoryFile getFile() {
        return file;
    }

    public void setFile(RepositoryFile file) {
        this.file = file;
    }




    @Override
    public Action[] getActions(boolean arg0) {
        
        List<Action> actions = new ArrayList<Action>();

        ResourceDescriptor resource = getFile().getDescriptor();

        if (resource.getWsType().equals(ResourceDescriptor.TYPE_IMAGE) ||
            resource.getWsType().equals(ResourceDescriptor.TYPE_JRXML) ||
            resource.getWsType().equals(ResourceDescriptor.TYPE_RESOURCE_BUNDLE) ||
            resource.getWsType().equals(ResourceDescriptor.TYPE_STYLE_TEMPLATE))
        {
            actions.add(SystemAction.get( OpenFileAction.class));
            actions.add(SystemAction.get( ReplaceFileAction.class));
        }


        if (ReportUnitNode.getParentReportUnit(this) == null)
        {
            actions.add(SystemAction.get( CopyAction.class));
            actions.add(SystemAction.get( CutAction.class));
        }

        actions.add(SystemAction.get( DeleteAction.class));
        if (ReportUnitNode.getParentReportUnit(this) != null)
        {
            actions.add(null);
            actions.add(SystemAction.get( RunReportUnitAction.class));
        }

        if (getFile().getDescriptor().getWsType().equals(ResourceDescriptor.TYPE_DATASOURCE_JDBC))
        {
            actions.add(SystemAction.get( ImportDatasourceAction.class));
        }

        if (getFile().getDescriptor().getWsType().equals(ResourceDescriptor.TYPE_OLAP_XMLA_CONNECTION))
        {
            actions.add(SystemAction.get( ImportXMLADatasourceAction.class));
        }


        actions.add(null);
        actions.add(SystemAction.get( RefreshAction.class));
        actions.add(SystemAction.get( PropertiesAction.class));
            
        return actions.toArray(new Action[actions.size()]);
    }

    @Override
    public Action getPreferredAction() {
        if (getFile().getDescriptor().getWsType().equals(ResourceDescriptor.TYPE_JRXML))
        {
            return SystemAction.get( OpenFileAction.class);
        }

        return SystemAction.get( PropertiesAction.class);
    }

    public ResourceDescriptor getResourceDescriptor() {
        return getFile().getDescriptor();
    }

    public RepositoryFolder getRepositoryObject() {
        return getFile();
    }

    public void refreshChildrens(boolean b) {
    }

    public void updateDisplayName() {
        fireDisplayNameChange(null,null);
    }
    
    public void removeChildren(Node[] nodes)
    {
        getChildren().remove(nodes);
        fireNodeDestroyed();
    }
    
    @Override
    public Transferable drag() throws IOException {
        ExTransferable tras = ExTransferable.create(clipboardCut());
        
        if (getFile().getDescriptor().getWsType().equals(ResourceDescriptor.TYPE_IMAGE))
        {
            tras.put(new ReportObjectPaletteTransferable( 
                    "com.jaspersoft.ireport.jasperserver.ui.actions.CreateImageAction",
                    getFile()));
        }
        return tras;
    }
    
    @Override
    public boolean canCopy() {
        return ReportUnitNode.getParentReportUnit(this) == null;
    }

    @Override
    public boolean canCut() {
        return ReportUnitNode.getParentReportUnit(this) == null;
    }
}
