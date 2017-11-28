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
import com.jaspersoft.ireport.designer.actions.AddBandAction;
import javax.swing.Action;
import net.sf.jasperreports.engine.JROrigin;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.nodes.Children;
import org.openide.nodes.Sheet;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;

/**
 *
 * @author gtoffoli
 */
public class NullBandNode extends IRAbstractNode {

    JasperDesign jd = null;
    NullBand band = null;
    public NullBandNode(JasperDesign jd, NullBand band,Lookup doLkp)
    {
        super (Children.LEAF, new ProxyLookup( doLkp, Lookups.fixed(jd, band)));
        this.jd = jd;
        this.band = band;
        setDisplayName ( ModelUtils.nameOf(band.getOrigin()));
        
        if (band.getOrigin().getBandType() == JROrigin.GROUP_FOOTER)
        {
            setIconBaseWithExtension("com/jaspersoft/ireport/designer/resources/groupfooter-16.png");
        }
        else if (band.getOrigin().getBandType() == JROrigin.GROUP_HEADER)
        {
            setIconBaseWithExtension("com/jaspersoft/ireport/designer/resources/groupheader-16.png");
        }
        else
        {
            setIconBaseWithExtension("com/jaspersoft/ireport/designer/resources/band-16.png");
        }
    }
    
    @Override
    public String getHtmlDisplayName()
    {
        return "<font color=#999999>" + getDisplayName();
    }

    @Override
    protected Sheet createSheet() {
        Sheet sheet = super.createSheet();
        return sheet;
    }
    
    @Override
    public Action[] getActions(boolean popup) {
        return new Action[] { AddBandAction.getInstance()  };
    }
}
