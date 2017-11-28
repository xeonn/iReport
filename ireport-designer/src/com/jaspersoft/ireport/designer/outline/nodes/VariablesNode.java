/*
 * ReportNode.java
 * 
 * Created on Aug 31, 2007, 4:55:47 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.outline.nodes;

import com.jaspersoft.ireport.designer.ModelUtils;
import com.jaspersoft.ireport.designer.dnd.DnDUtilities;
import com.jaspersoft.ireport.designer.outline.NewTypesUtils;
import com.jaspersoft.ireport.locale.I18n;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.Action;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignVariable;
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
 * This node is used just as folder for the report variables
 * 
 * @author gtoffoli
 */
public class VariablesNode extends IRIndexedNode implements PropertyChangeListener {

    private JasperDesign jd = null;
    private JRDesignDataset dataset = null;
    
    public VariablesNode(JasperDesign jd, Lookup doLkp) {
        this(jd, (JRDesignDataset)jd.getMainDataset(), doLkp);
    }
    
    public VariablesNode(JasperDesign jd, JRDesignDataset dataset, Lookup doLkp) {
        this(new VariablesChildren(jd, dataset, doLkp), jd, dataset, doLkp);
    }

    public VariablesNode(VariablesChildren pc, JasperDesign jd, JRDesignDataset dataset, Lookup doLkp) {
        super(pc, pc.getIndex(), new ProxyLookup( doLkp, Lookups.fixed(jd, dataset)));
    
        addNodeListener(new NodeListener() {

            public void childrenAdded(NodeMemberEvent ev) {
                //System.out.println("childrenAdded");
            }

            public void childrenRemoved(NodeMemberEvent ev) {
                //System.out.println("childrenRemoved");
            }

            @SuppressWarnings("unchecked")
            public void childrenReordered(NodeReorderEvent ev) {

                List list = getDataset().getVariablesList();
                ArrayList newList = new ArrayList();

                for (int i = 0; i < list.size(); ++i) {
                    JRDesignVariable p = (JRDesignVariable) list.get(i);
                    if (p.isSystemDefined()) {
                        newList.add(p);
                    }
                }

                Node[] nodes = getChildren().getNodes();
                for (int i = 0; i < nodes.length; ++i) {
                    JRDesignVariable p = ((VariableNode) nodes[i]).getVariable();
                    if (p.isSystemDefined()) {
                        continue;
                    }
                    newList.add(p);
                }

                list.clear();
                list.addAll(newList);
                
                getDataset().getEventSupport().firePropertyChange(
                        new PropertyChangeEvent(getDataset(), JRDesignDataset.PROPERTY_VARIABLES , null, null ) );

            }

            public void nodeDestroyed(NodeEvent ev) {
                //System.out.println("nodeDestroyed");
            }

            public void propertyChange(PropertyChangeEvent evt) {
                //System.out.println("propertyChange " + evt.getPropertyName());
            }
        });
        //(dataset != null) ? Lookups.fixed(jd,dataset) : Lookups.singleton(jd)
        
        this.jd = jd;
        if (dataset == null) dataset = jd.getMainDesignDataset();
        this.dataset = dataset;
        // Add things to the node lookup...
        dataset.getEventSupport().addPropertyChangeListener(this);
        
        
        setDisplayName(I18n.getString("VariableNode.Property.Variables"));
        setIconBaseWithExtension("com/jaspersoft/ireport/designer/resources/variables-16.png");
    }

    @Override
    public PasteType getDropType(Transferable t, final int action, int index) {

        final Node dropNode = NodeTransfer.node(t, DnDConstants.ACTION_COPY_OR_MOVE + NodeTransfer.CLIPBOARD_CUT);
        final int dropAction = DnDUtilities.getTransferAction(t);
        
        final int insertAt = index;
        if (null != dropNode) {
            final JRDesignVariable variable = dropNode.getLookup().lookup(JRDesignVariable.class);
            if (null != variable) {
                return new PasteType() {

                    @SuppressWarnings("unchecked")
                    public Transferable paste() throws IOException {
                        
                        if (variable.isSystemDefined()) {
                            return null;
                        }
                        
                        List list = getDataset().getVariablesList();
                        int currentIndex = -1; //Current position in the list
                        int lastSystemDefinedVariableIndex = -1; //First valid position
                        
                        for (int i = 0; i < list.size(); ++i) {
                            JRDesignVariable p = (JRDesignVariable) list.get(i);
                            if (p == variable) {
                                currentIndex = i;
                            }
                            if (p.isSystemDefined()) {
                                lastSystemDefinedVariableIndex = i;
                            }
                        }
                        // At this point lastSystemDefinedVariableIndex contains the first valid index
                        // to add a variable and currentIndex contains the index of the variable in the list
                        // if present
                        
                        if( (dropAction & NodeTransfer.MOVE) != 0 ) // Moving variable...
                        {
                            int newIndex = -1;
                            if (currentIndex != -1) { // Case 1: Moving in the list...
                                // Put the field in a valid position...
                                // Find the position of the node...
                                Node[] nodes = getChildren().getNodes();
                                for (int i = 0; i < nodes.length; ++i) {
                                    if (((VariableNode) nodes[i]).getVariable() == variable) {
                                        newIndex = i;
                                        break;
                                    }
                                }
                                
                                list.remove(variable);
                                if (newIndex == -1) 
                                {
                                    list.add(variable);
                                }
                                else
                                {
                                    list.add(Math.max(newIndex, lastSystemDefinedVariableIndex+1),variable );
                                }
                            } 
                            else // Adding a copy to the list 
                            {
                                try {
                                    JRDesignVariable newVariable = ModelUtils.cloneVariable(variable);
                                    Map map = getDataset().getVariablesMap();
                                    int k = 1;
                                    while (map.containsKey(newVariable.getName())) {
                                        newVariable.setName(variable.getName() + "_" + k);
                                        k++;
                                    }
                                    (getDataset()).addVariable(newVariable);
                                    // Remove the variable from the old list...
                                    if (dropNode.getParentNode() instanceof VariablesNode) {
                                        VariablesNode pn = (VariablesNode) dropNode.getParentNode();
                                        pn.getDataset().removeVariable(variable);
                                    }
                                } catch (JRException ex) {
                                    Exceptions.printStackTrace(ex);
                                }
                            }
                        }
                        else // Duplicating
                        {
                            try {
                                JRDesignVariable newVariable = ModelUtils.cloneVariable(variable);
                                Map map = getDataset().getVariablesMap();
                                int k = 1;
                                while (map.containsKey(newVariable.getName())) {
                                    newVariable.setName(variable.getName() + "_" + k);
                                    k++;
                                }
                                (getDataset()).addVariable(newVariable);
                            } catch (JRException ex) {
                                Exceptions.printStackTrace(ex);
                            }
                        }
                        return null;
                    };
                };
            }
        }
        return null;
    }

    
//
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
            SystemAction.get(ReorderAction.class)};
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

    public

    JasperDesign getJasperDesign() {
        return jd;
    }

    public JRDesignDataset getDataset() {
        return dataset;
    }

    public void setDataset(JRDesignDataset dataset) {
        this.dataset = dataset;
    }

    
    /**
     *  We can create a new Variable here...
     */
    @Override
    public NewType[] getNewTypes()
    {
        return NewTypesUtils.getNewType(this, NewTypesUtils.VARIABLE);
    }

    public void propertyChange(PropertyChangeEvent evt) {
        
        com.jaspersoft.ireport.designer.IReportManager.getInstance().notifyReportChange();
        if (evt.getPropertyName().equals( JRDesignDataset.PROPERTY_VARIABLES))
        {
            // Refrersh variables list...
            ((VariablesChildren)this.getChildren()).recalculateKeys();
        }
    }
}
