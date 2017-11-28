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
package com.jaspersoft.ireport.jasperserver.ui.nodes.dnd;

import com.jaspersoft.ireport.designer.utils.Misc;
import com.jaspersoft.ireport.jasperserver.ui.nodes.FolderNode;
import com.jaspersoft.ireport.jasperserver.ui.nodes.ResourceNode;
import com.jaspersoft.jasperserver.api.metadata.xml.domain.impl.ResourceDescriptor;
import java.awt.datatransfer.Transferable;
import java.io.IOException;
import javax.swing.JOptionPane;
import org.openide.nodes.Node;
import org.openide.nodes.NodeTransfer;
import org.openide.util.datatransfer.PasteType;

/**
 *
 * @author gtoffoli
 */
public class FolderPasteType extends PasteType {

    private FolderNode destinationNode;
    private Node originNode;
    private int dropAction = 0;

    public static FolderPasteType createFolderPasteType(int dropAction, FolderNode toFolderNode, Node originNode)
    {
        if (toFolderNode == null || originNode == null) return null;

        if (originNode instanceof ResourceNode)
        {
            // 1. It is not possible to copy from two different servers...
            if (toFolderNode.getFolder().getServer() != ((ResourceNode)originNode).getRepositoryObject().getServer())
            {
                return null;
            }

            // 2. Check that we are not coping a parent in a child...
            String origin_uri = ((ResourceNode)originNode).getResourceDescriptor().getUriString();
            String destination_uri = toFolderNode.getResourceDescriptor().getUriString();
            if (!destination_uri.equals("/") && destination_uri.startsWith(origin_uri)) return null;

            if (origin_uri.equals("/")) return null;

            if (((ResourceNode)originNode).getResourceDescriptor().getParentFolder().equals(destination_uri)) return null;

            return new FolderPasteType(dropAction, toFolderNode, originNode);
        }
        return null;
    }


    protected FolderPasteType(int dropAction, FolderNode toFolderNode, Node originNode) {
        this.destinationNode = toFolderNode;
        this.originNode = originNode;
        this.dropAction = dropAction;
    }

    @SuppressWarnings("unchecked")
    public Transferable paste() throws IOException {

        ResourceNode origin = (ResourceNode)getOriginNode();
        ResourceDescriptor origin_rd = origin.getResourceDescriptor();

        Node originParent = getOriginNode().getParentNode();

        String origin_uri = origin.getResourceDescriptor().getUriString();
        String destination_uri = getDestinationNode().getResourceDescriptor().getUriString();

        if ((getDropAction() & NodeTransfer.MOVE) != 0) // Moving parameter...
        {
            try {
                origin.getRepositoryObject().getServer().getWSClient().move(origin_rd, destination_uri);

                // refresh childrens of both the nodes...
                if (originParent != null && originParent instanceof FolderNode)
                {
                    ((FolderNode)originParent).getResourceDescriptor().getChildren().remove(origin_rd);
                    ((FolderNode)originParent).refreshChildrens(false);
                }

                origin_rd.setParentFolder(destination_uri);
                origin_rd.setUriString(destination_uri + "/" + origin_rd.getName());
                getDestinationNode().getResourceDescriptor().getChildren().add(origin_rd);
                getDestinationNode().refreshChildrens(false);

            } catch (Exception ex)
            {
                Misc.showErrorMessage("Unable to move the resource: " + ex.getMessage(), "Move error");
            }

        } else // Duplicating
        {
            try {

                System.out.println("Pasting resource... " + ((ResourceNode)originNode).getResourceDescriptor().getUriString() + " to " + destination_uri);
                System.out.flush();

                String newResName = origin_rd.getName();
                if (destination_uri.endsWith("/")) newResName = destination_uri + newResName;
                else newResName = destination_uri + "/" + newResName;

                ResourceDescriptor newRd = origin.getRepositoryObject().getServer().getWSClient().copy(origin_rd, newResName);

                // refresh childrens of both the nodes...
                getDestinationNode().getResourceDescriptor().getChildren().add(origin_rd);
                getDestinationNode().refreshChildrens(false);

            } catch (Exception ex)
            {
                Misc.showErrorMessage("Unable to copy the resource: " + ex.getMessage(), "Move error");
            }
        }
        return null;
    }

    /**
     * @return the destinationNode
     */
    public FolderNode getDestinationNode() {
        return destinationNode;
    }

    /**
     * @param destinationNode the destinationNode to set
     */
    public void setDestinationNode(FolderNode destinationNode) {
        this.destinationNode = destinationNode;
    }

    /**
     * @return the originNode
     */
    public Node getOriginNode() {
        return originNode;
    }

    /**
     * @param originNode the originNode to set
     */
    public void setOriginNode(Node originNode) {
        this.originNode = originNode;
    }

    /**
     * @return the dropAction
     */
    public int getDropAction() {
        return dropAction;
    }

    /**
     * @param dropAction the dropAction to set
     */
    public void setDropAction(int dropAction) {
        this.dropAction = dropAction;
    }
}
