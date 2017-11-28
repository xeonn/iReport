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
package com.jaspersoft.ireport.designer.outline.nodes;

import com.jaspersoft.ireport.designer.ModelUtils;
import com.jaspersoft.ireport.designer.dnd.DnDUtilities;

import com.jaspersoft.ireport.designer.menu.SortFieldsAction;
import com.jaspersoft.ireport.designer.outline.NewTypesUtils;
import com.jaspersoft.ireport.locale.I18n;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import javax.swing.Action;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignField;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.actions.NewAction;
import org.openide.actions.PasteAction;
import org.openide.actions.ReorderAction;
import org.openide.nodes.Node;
import org.openide.nodes.NodeEvent;
import org.openide.nodes.NodeListener;
import org.openide.nodes.NodeMemberEvent;
import org.openide.nodes.NodeReorderEvent;
import org.openide.nodes.NodeTransfer;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.actions.SystemAction;
import org.openide.util.datatransfer.NewType;
import org.openide.util.datatransfer.PasteType;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;

/**
 * This node is used just as folder for the report fields
 *        
 * @author gtoffoli
 */
public class FieldsNode extends IRIndexedNode implements PropertyChangeListener {

    private JasperDesign jd = null;
    private JRDesignDataset dataset = null;

    private boolean sort = false;
    private boolean sorting = false;

    public FieldsNode(JasperDesign jd, Lookup doLkp) {
        this(jd, (JRDesignDataset)jd.getMainDataset(),doLkp);
    }
    
    public FieldsNode(JasperDesign jd, JRDesignDataset dataset, Lookup doLkp) {
        this(new FieldsChildren(jd, dataset, doLkp), jd, dataset, doLkp);
    }

    public FieldsNode(FieldsChildren pc, JasperDesign jd, JRDesignDataset dataset, Lookup doLkp) {
        super(pc, pc.getIndex(), new ProxyLookup(doLkp, Lookups.fixed(jd, dataset)));
    
        this.jd = jd;
        if (dataset == null) dataset = jd.getMainDesignDataset();
        this.dataset = dataset;
        // Add things to the node lookup...
        dataset.getEventSupport().addPropertyChangeListener(this);
        
        
        setDisplayName(I18n.getString("FieldsNode.Property.Fields"));
        setIconBaseWithExtension("com/jaspersoft/ireport/designer/resources/fields-16.png");
    
    
        addNodeListener(new NodeListener() {

            public void childrenAdded(NodeMemberEvent ev) {
                //System.out.println("childrenAdded");
            }

            public void childrenRemoved(NodeMemberEvent ev) {
                //System.out.println("childrenRemoved");
            }

            @SuppressWarnings("unchecked")
            public void childrenReordered(NodeReorderEvent ev) {

                if (isSorting() || isSort())
                {
                    return;
                }
                
                List list = getDataset().getFieldsList();
                ArrayList newList = new ArrayList();

                Node[] nodes = getChildren().getNodes();
                for (int i = 0; i < nodes.length; ++i) {
                    JRDesignField s = ((FieldNode) nodes[i]).getField();
                    newList.add(s);
                }

                list.clear();
                list.addAll(newList);
                
                getDataset().getEventSupport().firePropertyChange(
                        new PropertyChangeEvent(getDataset(), JRDesignDataset.PROPERTY_FIELDS, null, null ) );

            }

            public void nodeDestroyed(NodeEvent ev) {
                //System.out.println("nodeDestroyed");
            }

            public void propertyChange(PropertyChangeEvent evt) {
                //System.out.println("propertyChange " + evt.getPropertyName());
            }
        });
    }

    @Override
    public PasteType getDropType(Transferable t, final int action, int index) {

        final Node dropNode = NodeTransfer.node(t, DnDConstants.ACTION_COPY_OR_MOVE + NodeTransfer.CLIPBOARD_CUT);
        final int dropAction = DnDUtilities.getTransferAction(t);

        final int insertAt = index;
        if (null != dropNode) {
            final JRDesignField field = dropNode.getLookup().lookup(JRDesignField.class);

            if (null != field) {

                if (isSort() && getDataset().getFieldsList().contains(field)) return null;

                return new PasteType() {

                    @SuppressWarnings("unchecked")
                    public Transferable paste() throws IOException {
                        
                        List list = getDataset().getFieldsList();
                        int currentIndex = -1; //Current position in the list
                        
                        for (int i = 0; i < list.size(); ++i) {
                            JRDesignField f = (JRDesignField) list.get(i);
                            if (f == field) {
                                currentIndex = i;
                            }
                        }
                         
                        if( (dropAction & NodeTransfer.MOVE) != 0 ) // Moving field...
                        {
                            int newIndex = -1;
                            if (currentIndex != -1) { // Case 1: Moving in the list...
                                // Put the field in a valid position...
                                // Find the position of the node...
                                Node[] nodes = getChildren().getNodes();

                                for (int i = 0; i < nodes.length; ++i) {
                                        if (((FieldNode) nodes[i]).getField() == field) {
                                            newIndex = i;
                                            break;
                                        }
                                    }

                                
                                list.remove(field);
                                if (newIndex == -1) 
                                {
                                    list.add(field);
                                }
                                else
                                {
                                    list.add(newIndex, field );
                                }
                            } 
                            else // Adding a copy to the list 
                            {
                                try {
                                    JRDesignField newField = ModelUtils.cloneField(field);
                                    Map map = getDataset().getFieldsMap();
                                    int k = 1;
                                    while (map.containsKey(newField.getName())) {
                                        newField.setName(field.getName() + "_" + k);
                                        k++;
                                    }
                                    (getDataset()).addField(newField);
                                    // Remove the field from the old list...
                                    if (dropNode.getParentNode() instanceof FieldsNode) {
                                        FieldsNode pn = (FieldsNode) dropNode.getParentNode();
                                        pn.getDataset().removeField(field);
                                    }
                                    
                                } catch (Exception ex) {
                                    Exceptions.printStackTrace(ex);
                                }
                            }
                        }
                        else // Duplicating
                        {
                            try {
                                JRDesignField newField = ModelUtils.cloneField(field);
                                Map map = getDataset().getFieldsMap();
                                int k = 1;
                                while (map.containsKey(newField.getName())) {
                                    newField.setName(field.getName() + "_" + k);
                                    k++;
                                }
                                (getDataset()).addField(newField);
                            } catch (Exception ex) {
                                Exceptions.printStackTrace(ex);
                            }
                        }
                        return null;
                    }
                };
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
    public Action[] getActions(boolean context) {
        return new Action[]{
            SystemAction.get(NewAction.class),
            SystemAction.get(PasteAction.class),
            SystemAction.get(ReorderAction.class),
            SystemAction.get(SortFieldsAction.class)};
    }

    @Override
    public boolean canDestroy() {
        return false;
    }

    /*
     * @return false to signal that the customizer should not be used.
     *  Subclasses can override this method to enable customize action
     *  and use customizer provided by this class.
     */
    @Override
    public boolean hasCustomizer() {
        return true;
    }

    public JasperDesign getJasperDesign() {
        return jd;
    }

    public JRDesignDataset getDataset() {
        return dataset;
    }

    public void setDataset(JRDesignDataset dataset) {
        this.dataset = dataset;
    }

    
    /**
     *  We can create a new Field here...
     */
    @Override
    public NewType[] getNewTypes()
    {
        return NewTypesUtils.getNewType(this, NewTypesUtils.FIELD);
    }

    public void propertyChange(PropertyChangeEvent evt) {
        
        com.jaspersoft.ireport.designer.IReportManager.getInstance().notifyReportChange();
        if (evt.getPropertyName().equals( JRDesignDataset.PROPERTY_FIELDS))
        {
            // Refrersh fields list...
            ((FieldsChildren)this.getChildren()).recalculateKeys();
        }
    }

    /**
     *
     * @return the sort
     */
    public boolean isSort() {
        return sort;
    }

    /**
     * Activate/Disable alphabetical sort.
     * A recalculation of the children is performed.
     * @param sort the sort to set
     */
    public void setSort(boolean sort) {
        this.sort = sort;
        if (sort)
        {
            setIconBaseWithExtension("com/jaspersoft/ireport/designer/resources/fields-sort-16.png");
        }
        else
        {
            setIconBaseWithExtension("com/jaspersoft/ireport/designer/resources/fields-16.png");
        }
        this.fireIconChange();
        if (this.getChildren() != null && this.getChildren() instanceof FieldsChildren)
        {
            this.setSorting(true);
            ((FieldsChildren)this.getChildren()).recalculateKeys();
            this.setSorting(false);
        }
    }

    /**
     * This value says if a sorting operation is occurring.
     * In that case no changes must be made to the model.
     * This value is not related to the sorting status,
     * see isSort() for that.
     * @return sorting status sorting
     */
    public boolean isSorting() {
        return sorting;
    }

    /**
     * See isSorting() for an explanation of this method.
     * @param sorting the sorting to set
     */
    public void setSorting(boolean sorting) {
        this.sorting = sorting;
    }

    /**
     * This method recalculates the childrens if the sort is set to true,
     * without recreate the list of nodes.
     * It is called i.e. when the name of a child node changes...
     */
    public void updateSorting()
    {
        if (!isSort()) return;

        
        setSorting(true);
        try {

            Node[] nodes = getChildren().getNodes();
            List nodesList = new ArrayList(Arrays.asList(nodes));
            Arrays.sort(nodes, new Comparator<Node>() {

                public int compare(Node o1, Node o2) {
                    return o1.getName().compareToIgnoreCase(o2.getName());
                }
            });
            

            int ints[] = new int[nodes.length];

            for (int i=0; i<ints.length; ++i)
            {
                ints[ nodesList.indexOf(nodes[i])]=i;
            }


            ((FieldsChildren)getChildren()).forceReorder(ints);

        } finally
        {
            setSorting(false);
        }
    }


}