/*
 * ReportNode.java
 * 
 * Created on Aug 31, 2007, 4:55:47 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.outline.nodes;

import java.util.ArrayList;
import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.JRGroup;
import net.sf.jasperreports.engine.JROrigin;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;

/**
 *
 * @author gtoffoli
 */
public class ReportChildren extends Children.Keys {

    JasperDesign jd = null;
    Lookup doLkp = null;
    
    public ReportChildren(JasperDesign jd, Lookup doLkp) {
        this.jd = jd;
        this.doLkp = doLkp;
    }
    
    protected Node[] createNodes(Object key) {
        
        if (key.equals("styles"))
        {
            return new Node[]{new StylesNode(jd,doLkp)};
        }
        else if (key.equals("parameters"))
        {
            return new Node[]{new ParametersNode(jd,doLkp)};
        }
        else if (key.equals("fields"))
        {
            return new Node[]{new FieldsNode(jd, jd.getMainDesignDataset(),doLkp)};
        }
        else if (key.equals("variables"))
        {
            return new Node[]{new VariablesNode(jd, jd.getMainDesignDataset(),doLkp)};
        }
        else if (key instanceof JRDesignDataset)
        {
            return new Node[]{new DatasetNode(jd, (JRDesignDataset)key,doLkp)};
        }
        else if (key instanceof JRDesignBand)
        {
            return new Node[]{new BandNode(jd, (JRDesignBand)key,doLkp)};
        }
        else if (key instanceof NullBand)
        {
            return new Node[]{new NullBandNode(jd, (NullBand)key,doLkp)};
        }
        
        AbstractNode node = new IRAbstractNode(LEAF, new ProxyLookup(doLkp, Lookups.singleton(key)));
        node.setName(key+"");
        return new Node[]{node};
    }
    
    @Override
    protected void addNotify() {
        super.addNotify();
        updateChildren();
    }
    
    @SuppressWarnings("unchecked")
    public void updateChildren()
    {
        ArrayList children = new ArrayList();
        children.add("styles");
        children.add("parameters");
        children.add("fields");
        children.add("variables");
        children.addAll( jd.getDatasetsList() );
        //children.addAll( ModelUtils.getBands(jd) );
        children.add( ( jd.getBackground() != null) ? jd.getBackground() : new NullBand(new JROrigin(jd.getName(), JROrigin.BACKGROUND )) );
        children.add( ( jd.getTitle() != null) ? jd.getTitle() : new NullBand(new JROrigin(jd.getName(), JROrigin.TITLE )) );
        children.add( ( jd.getPageHeader() != null) ? jd.getPageHeader() : new NullBand(new JROrigin(jd.getName(), JROrigin.PAGE_HEADER )) );
        children.add( ( jd.getColumnHeader() != null) ? jd.getColumnHeader() : new NullBand(new JROrigin(jd.getName(), JROrigin.COLUMN_HEADER )) );
        // Group headers...
        JRGroup[] groups = jd.getGroups();
        for (int i=0 ;i<groups.length; ++i)
        {
            children.add( (groups[i].getGroupHeader() != null) ? groups[i].getGroupHeader() : 
                new NullBand(new JROrigin(jd.getName(),groups[i].getName(), JROrigin.GROUP_HEADER )) );
        
        }
        children.add( ( jd.getDetail() != null) ? jd.getDetail() : new NullBand(new JROrigin(jd.getName(), JROrigin.DETAIL )) );
        // Group footers...
        for (int i=groups.length-1; i>=0; --i)
        {
            children.add( (groups[i].getGroupFooter() != null) ? groups[i].getGroupFooter() : 
                new NullBand(new JROrigin(jd.getName(),groups[i].getName(), JROrigin.GROUP_FOOTER )) );
        
        }
        children.add( ( jd.getColumnFooter() != null) ? jd.getColumnFooter() : new NullBand(new JROrigin(jd.getName(), JROrigin.COLUMN_FOOTER )) );
        children.add( ( jd.getPageFooter() != null) ? jd.getPageFooter() : new NullBand(new JROrigin(jd.getName(), JROrigin.PAGE_FOOTER )) );
        children.add( ( jd.getLastPageFooter() != null) ? jd.getLastPageFooter() : new NullBand(new JROrigin(jd.getName(), JROrigin.LAST_PAGE_FOOTER )) );
        children.add( ( jd.getSummary() != null) ? jd.getSummary() : new NullBand(new JROrigin(jd.getName(), JROrigin.SUMMARY )) );
        children.add( ( jd.getNoData() != null) ? jd.getNoData() : new NullBand(new JROrigin(jd.getName(), JROrigin.NO_DATA )) );
        
        setKeys(children);
    }
}
