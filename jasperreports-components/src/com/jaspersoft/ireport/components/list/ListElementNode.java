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
package com.jaspersoft.ireport.components.list;

import com.jaspersoft.ireport.designer.dnd.DnDUtilities;
import com.jaspersoft.ireport.designer.outline.nodes.ElementNode;
import com.jaspersoft.ireport.designer.outline.nodes.ElementPasteType;
import com.jaspersoft.ireport.designer.sheet.Tag;
import com.jaspersoft.ireport.designer.sheet.properties.ByteProperty;
import com.jaspersoft.ireport.locale.I18n;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Action;
import net.sf.jasperreports.components.list.DesignListContents;
import net.sf.jasperreports.components.list.StandardListComponent;
import net.sf.jasperreports.engine.design.JRDesignComponentElement;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.nodes.Children;
import org.openide.nodes.Index;
import org.openide.nodes.Node;
import org.openide.nodes.NodeTransfer;
import org.openide.nodes.Sheet;
import org.openide.util.Lookup;
import org.openide.util.actions.SystemAction;
import org.openide.util.datatransfer.PasteType;

/**
 *
 * @author gtoffoli
 */
public class ListElementNode extends ElementNode {

    public ListElementNode(JasperDesign jd, JRDesignElement element, Children children, Index index, Lookup doLkp)
    {
        super(jd, element, children, index, doLkp);
        setIconBaseWithExtension("com/jaspersoft/ireport/components/list/list-16.png");
    }

    @Override
    public String getDisplayName() {
        return I18n.getString("ListElementNode.name");
    }

    /**
     *  This is used internally to understand if the element can accept other elements...
     */
    @Override
    public boolean canPaste() {
        return true;
    }

    @Override
    public PasteType getDropType(Transferable t, final int action, int index) {

        Node dropNode = NodeTransfer.node(t, DnDConstants.ACTION_COPY_OR_MOVE + NodeTransfer.CLIPBOARD_CUT);
        int dropAction = DnDUtilities.getTransferAction(t);


        if (null != dropNode) {
            JRDesignElement element = dropNode.getLookup().lookup(JRDesignElement.class);


            if (null != element) {

                DesignListContents contents = (DesignListContents) ((StandardListComponent)((JRDesignComponentElement)this.getElement()).getComponent()).getContents();

                return new ElementPasteType( element.getElementGroup(),
                                             contents,
                                             element,dropAction,this);
            }

        }
        return null;
    }


    @SuppressWarnings("unchecked")
    @Override
    protected void createPasteTypes(Transferable t, List s) {
        super.createPasteTypes(t, s);
        PasteType paste = getDropType(t, DnDConstants.ACTION_MOVE, -1);
        if (null != paste) {
            s.add(paste);
        }
    }

    @Override
    public Action[] getActions(boolean popup) {

        List<Action> actions = new ArrayList<Action>();
        Action[] originalActions = super.getActions(popup);

        actions.add(SystemAction.get(EditDatasetRunAction.class));
        for (int i=0; i<originalActions.length; ++i)
        {
            actions.add(originalActions[i]);
        }
        return actions.toArray(new Action[actions.size()]);
    }

    @Override
    protected Sheet createSheet() {
        
        Sheet sheet = super.createSheet();
        
        // adding common properties...
        Sheet.Set set = Sheet.createPropertiesSet();
        set.setName("List");
        set.setDisplayName(I18n.getString("List"));
        DesignListContents contents = (DesignListContents) ((StandardListComponent)((JRDesignComponentElement)this.getElement()).getComponent()).getContents();
        set.put(new ListContentsHeightProperty(contents));
        set.put(new ListContentsWidthProperty(contents));
        set.put(new PrintOrderProperty((StandardListComponent)((JRDesignComponentElement)this.getElement()).getComponent()));

        sheet.put( set);
        
        return sheet;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt != null &&
            JRDesignElement.PROPERTY_HEIGHT.equals(evt.getPropertyName()))
        {
            firePropertyChange("LC" + DesignListContents.PROPERTY_HEIGHT, null, null);
        }

        if (evt != null &&
            JRDesignElement.PROPERTY_WIDTH.equals(evt.getPropertyName()))
        {
            firePropertyChange("LC" + DesignListContents.PROPERTY_WIDTH, null, null);
        }
        super.propertyChange(evt);
    }

}


/**
 *  Class to manage the JasperDesign.PROPERTY_ORIENTATION property
 */
class PrintOrderProperty extends ByteProperty
{
        private final StandardListComponent element;

        @SuppressWarnings("unchecked")
        public PrintOrderProperty(StandardListComponent element)
        {
            super(element);
            this.element = element;
        }

        @Override
        public String getName()
        {
            return StandardListComponent.PROPERTY_PRINT_ORDER;
        }

        @Override
        public String getDisplayName()
        {
            return I18n.getString("StandardListComponent.Property.PrintOrder");
        }

        @Override
        public String getShortDescription()
        {
            return I18n.getString("StandardListComponent.Property.PrintOrder.detail");
        }

        @Override
        public List getTagList()
        {
            List tags = new java.util.ArrayList();
            tags.add(new Tag(new Byte(JasperDesign.PRINT_ORDER_VERTICAL), I18n.getString("ReportNode.Property.Vertical")));
            tags.add(new Tag(new Byte(JasperDesign.PRINT_ORDER_HORIZONTAL), I18n.getString("ReportNode.Property.Horizontal")));

            return tags;
        }

        @Override
        public Byte getByte()
        {
            return element.getPrintOrder();
        }

        @Override
        public Byte getOwnByte()
        {
            return element.getPrintOrder();
        }

        @Override
        public Byte getDefaultByte()
        {
            return null;
        }

        @Override
        public void setByte(Byte orderType)
        {
            element.setPrintOrder(orderType);
        }

}
