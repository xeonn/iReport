/*
 * FrameNode.java
 * 
 * Created on 12-nov-2007, 11.58.58
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.outline.nodes;

import com.jaspersoft.ireport.designer.dnd.DnDUtilities;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.beans.PropertyChangeEvent;
import java.util.List;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JRDesignElementGroup;
import net.sf.jasperreports.engine.design.JRDesignFrame;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.nodes.Node;
import org.openide.nodes.NodeEvent;
import org.openide.nodes.NodeListener;
import org.openide.nodes.NodeMemberEvent;
import org.openide.nodes.NodeReorderEvent;
import org.openide.nodes.NodeTransfer;
import org.openide.util.Lookup;
import org.openide.util.datatransfer.PasteType;

/**
 *
 * @author gtoffoli
 */
public class FrameNode extends ElementNode {

    private JRDesignFrame frame = null;
    
    public JRDesignFrame getFrame() {
        return frame;
    }
    
    
    public FrameNode(JasperDesign jd, JRDesignFrame frame, Lookup doLkp)
    {
        super(jd, frame, new FrameChildren(jd, frame, doLkp), doLkp);
        this.frame = frame;
        
        this.addNodeListener(new NodeListener() {

            public void childrenAdded(NodeMemberEvent ev) {}
            public void childrenRemoved(NodeMemberEvent ev) {}
            public void nodeDestroyed(NodeEvent ev) {}
            public void propertyChange(PropertyChangeEvent evt) {}

            @SuppressWarnings("unchecked")
            public void childrenReordered(NodeReorderEvent ev) {
                // Fire an event now...
                
                // TODO: Undo reorder...
                      
                List elements = getFrame().getChildren();
                int[] permutations = ev.getPermutation();
                
                boolean permFound = false;
                
                Object[] elementsArray = new Object[elements.size()];
                for (int i=0; i<elementsArray.length; ++i)
                {
                    //System.out.println("["+i+"]=" + permutations[i]);
                    elementsArray[permutations[i]] = elements.get(i);
                    if (permutations[i] != i) 
                    {
                        permFound = true;
                    }
                }

                if (!permFound) return;
                
                elements.clear();
                for (int i=0; i<elementsArray.length; ++i)
                {
                    elements.add(elementsArray[i]);
                }
                
                getFrame().getEventSupport().firePropertyChange( JRDesignBand.PROPERTY_CHILDREN, null, getFrame().getChildren());
            }
        });
        
    }
    
    /**
     *  This is used internally to understand if the element can accept other elements...
     */
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
                
                return new ElementPasteType( element.getElementGroup(),
                                             getFrame(),
                                             element,dropAction,this);
            }
            
            // Check if we are pasting a group not an element
            if (dropNode instanceof ElementGroupNode)
            {
                JRDesignElementGroup g = ((ElementGroupNode)dropNode).getElementGroup();
                return new ElementPasteType( g.getElementGroup(),
                                             getFrame(),
                                             g,dropAction,this);
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
}
