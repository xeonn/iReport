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
import com.jaspersoft.ireport.designer.actions.DeleteGroupAction;
import com.jaspersoft.ireport.designer.actions.MaximizeBackgroundAction;
import com.jaspersoft.ireport.designer.actions.MoveGroupDownAction;
import com.jaspersoft.ireport.designer.actions.MoveGroupUpAction;
import com.jaspersoft.ireport.locale.I18n;
import java.util.ArrayList;
import javax.swing.Action;
import net.sf.jasperreports.engine.JROrigin;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignGroup;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.nodes.Children;
import org.openide.nodes.Sheet;
import org.openide.util.Lookup;
import org.openide.util.actions.SystemAction;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;

/**
 *
 * @author gtoffoli
 */
public class NullBandNode extends IRAbstractNode implements GroupNode {

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

        JRDesignGroup group = getGroup();
        if (group != null)
        {
            Sheet.Set groupPropertiesSet = Sheet.createPropertiesSet();
            groupPropertiesSet.setName("GROUP_PROPERTIES");
            groupPropertiesSet.setDisplayName(I18n.getString("BandNode.Property.Groupproperties"));
            groupPropertiesSet = BandNode.fillGroupPropertySet(groupPropertiesSet, getDataset(), group);

            sheet.put(groupPropertiesSet);
        }
        return sheet;
    }
    
    @Override
    public Action[] getActions(boolean popup) {

        java.util.List<Action> list = new ArrayList<Action>();
        list.add( SystemAction.get(AddBandAction.class));

        if (band.getOrigin().getBandType() == JROrigin.BACKGROUND)
        {
            list.add(SystemAction.get(MaximizeBackgroundAction.class));
        }

        JRDesignGroup group = getGroup();

        if (group != null)
        {
            list.add( null );
            list.add( SystemAction.get(MoveGroupUpAction.class));
            list.add( SystemAction.get(MoveGroupDownAction.class));
            list.add( DeleteGroupAction.getInstance() );
        }

        return list.toArray(new Action[list.size()]);
    }

    public JRDesignDataset getDataset() {
        return jd.getMainDesignDataset();
    }

    public JRDesignGroup getGroup() {
        if (band.getOrigin() != null &&
            band.getOrigin().getGroupName() != null)
        {
            return (JRDesignGroup) getDataset().getGroupsMap().get(band.getOrigin().getGroupName());
        }
        return null;
    }
}
