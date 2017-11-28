/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.outline.nodes;

import com.jaspersoft.ireport.designer.pdf508.Pdf508TagDecorator;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.Lookup;
import org.openide.util.actions.SystemAction;


/**
 *
 * @author gtoffoli
 */
public class IRAbstractNode extends AbstractNode  {

    /**
     * This lookup should contain only cookies like SaveCookie, PrintCookie, etc... 
     * 
     */
    private Lookup specialDataObjectLookup = null;

    /**
     * 
     * @param children
     * @param lkp  Lookup to be used with the node
     */
    public IRAbstractNode(Children children, Lookup lkp)
    {
        super(children, lkp);
        this.specialDataObjectLookup = lkp;
    }
       
    public Lookup getSpecialDataObjectLookup() {
        return specialDataObjectLookup;
    }

    public void setSpecialDataObjectLookup(Lookup specialDataObjectLookup) {
        this.specialDataObjectLookup = specialDataObjectLookup;
    }

}
