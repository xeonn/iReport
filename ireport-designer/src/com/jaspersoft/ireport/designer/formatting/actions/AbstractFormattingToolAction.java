/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.formatting.actions;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.outline.nodes.ElementNode;
import com.jaspersoft.ireport.designer.undo.ElementTransformationUndoableEdit;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import net.sf.jasperreports.engine.design.JRDesignElement;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.actions.NodeAction;

/**
 *
 * @author gtoffoli
 */
public abstract class AbstractFormattingToolAction extends NodeAction {

    
    @Override
    protected boolean enable(Node[] nodes) {

        int minElements = requiresMultipleObjects() ? 2 : 1;
        return getSelectedElements(nodes).size() >= minElements;
    }
    
    /**
     * Returns the elements currently selected.
     * 
     * @return
     */
    public List<JRDesignElement> getSelectedElements()
    {
            return getSelectedElements(getActivatedNodes());
    }
    
    /**
     * Returns the elements from an array of Nodes.
     * 
     * @return
     */
    public List<JRDesignElement> getSelectedElements(Node[] nodes)
    {
        List<JRDesignElement> elements = new ArrayList<JRDesignElement>();
        for (int i=0; i<nodes.length; ++i)
        {
            if (nodes[i] instanceof ElementNode)
            {
                elements.add( ((ElementNode)nodes[i]).getElement());
            }
        }
        
        return elements;
    }

    @Override
    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

    /**
     * Tells if the tool requires more that a single element selected.
     * 
     * @return
     */
    public boolean requiresMultipleObjects()
    {
        return false;
    }
    
    
    /**
     * Convenient funtion to create the undo operation...
     * 
     * @param element
     * @return
     */
    public Rectangle getElementBounds(JRDesignElement element) 
    {
        return new Rectangle( element.getX(), element.getY(), element.getWidth(), element.getHeight());
    }
    
    /**
     * Add the changes of the element in terms of bounds to the list of undo ops.
     * Returns true if the op has been added, the value of aggregate otherwise.
     * The undo op is not added if oldBounds is the same as the new ones.
     * 
     * @param element
     * @param oldBounds
     * @param aggregate
     * @return
     */
    public boolean addTransformationUndo(JRDesignElement element, Rectangle oldBounds, boolean aggregate)
    {
         if (element.getX() != oldBounds.x ||
             element.getY() != oldBounds.y ||
             element.getWidth() != oldBounds.width ||
             element.getHeight() != oldBounds.height)
         {
                 ElementTransformationUndoableEdit edit = new ElementTransformationUndoableEdit( element, oldBounds);
                 IReportManager.getInstance().getInstance().addUndoableEdit(edit, aggregate);
                 return true;
         }
         return aggregate;
    }
    
    /**
     * Return a new list of elements ordered by position X and Y
     * @param elements
     * @return
     */
    public List<JRDesignElement> sortXY(List<JRDesignElement> elements)
    {
        return sortXY(elements, false);
    }
    
    /**
     * Return a new list of elements ordered by position X and Y
     * @param elements
     * @return
     */
    public List<JRDesignElement> sortXY(List<JRDesignElement> elements, final boolean reverse)
    {
        JRDesignElement[] elements_array = new JRDesignElement[elements.size()];
        elements_array = elements.toArray(elements_array);
        
        Arrays.sort(elements_array, new Comparator<JRDesignElement>() {

            public int compare(JRDesignElement e1, JRDesignElement e2) {
                
                int x1 = (reverse) ? e1.getX()+e1.getWidth() : e1.getX();
                int x2 = (reverse) ? e2.getX()+e2.getWidth() : e2.getX();
                int y1 = (reverse) ? e1.getY()+e1.getHeight() : e1.getY();
                int y2 = (reverse) ? e2.getY()+e2.getHeight() : e2.getY();
                
                int mul = (reverse) ? -1 : 1;
                
                if (x1 > x2) return 1*mul;
                else if (x1 < x2) return -1*mul;
                else if (y1 > y2) return 1*mul;
                else if (y1 < y2) return -1*mul;
                return 0;
            }
        });
        
        return Arrays.asList(elements_array);
    }
    
    /**
     * Return a new list of elements ordered by position Y and X
     * @param elements
     * @return
     */
    public List<JRDesignElement> sortYX(List<JRDesignElement> elements, final boolean reverse)
    {
        JRDesignElement[] elements_array = new JRDesignElement[elements.size()];
        elements_array = elements.toArray(elements_array);
        
        Arrays.sort(elements_array, new Comparator<JRDesignElement>() {

            public int compare(JRDesignElement e1, JRDesignElement e2) {
                
                int x1 = (reverse) ? e1.getX()+e1.getWidth() : e1.getX();
                int x2 = (reverse) ? e2.getX()+e2.getWidth() : e2.getX();
                int y1 = (reverse) ? e1.getY()+e1.getHeight() : e1.getY();
                int y2 = (reverse) ? e2.getY()+e2.getHeight() : e2.getY();
                
                int mul = (reverse) ? -1 : 1;
                
                if (y1 > y2) return 1*mul;
                else if (y1 < y2) return -1*mul;
                else if (x1 > x2) return 1*mul;
                else if (x1 < x2) return -1*mul;
                
                return 0;
            }
        });
        
        return Arrays.asList(elements_array);
    }
    
    /**
     * Return a new list of elements ordered by position Y and X
     * @param elements
     * @return
     */
    public List<JRDesignElement> sortYX(List<JRDesignElement> elements)
    {
        return sortYX(elements, false);
    }
}
 