/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.components.list;

import com.jaspersoft.ireport.designer.dnd.DnDUtilities;
import com.jaspersoft.ireport.designer.outline.nodes.ElementNode;
import com.jaspersoft.ireport.designer.outline.nodes.ElementPasteType;
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
        super.propertyChange(evt);
    }





}
