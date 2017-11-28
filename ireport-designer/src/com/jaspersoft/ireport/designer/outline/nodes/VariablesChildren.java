/*
 * VariablesChildren.java
 * 
 * Created on Aug 31, 2007, 4:55:47 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.outline.nodes;

import com.jaspersoft.ireport.designer.IReportManager;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;
import net.sf.jasperreports.engine.JRVariable;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignVariable;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.nodes.Index;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.Mutex;

/**
 *
 * @author gtoffoli
 */
public class VariablesChildren extends Index.KeysChildren implements PropertyChangeListener, PreferenceChangeListener {

    JasperDesign jd = null;
    private JRDesignDataset dataset = null;
    private Lookup doLkp = null;
    
    public VariablesChildren(JasperDesign jd, Lookup doLkp) {
        this(jd, jd.getMainDesignDataset(), doLkp);
    }
    @SuppressWarnings("unchecked")
    public VariablesChildren(JasperDesign jd, JRDesignDataset dataset, Lookup doLkp) {
        super(new ArrayList());
        this.jd = jd;
        this.doLkp = doLkp;
        if (dataset == null) dataset = jd.getMainDesignDataset();
        this.dataset = dataset;
        this.dataset.getEventSupport().addPropertyChangeListener(this);
        IReportManager.getPreferences().addPreferenceChangeListener(this);
    }

    /*
    @Override
    protected List<Node> initCollection() {
        return recalculateKeys();
    }
    */
    
    
    protected Node[] createNodes(Object key) {
        
        return new Node[]{new VariableNode(jd, (JRDesignVariable)key, doLkp)};
    }
    
    
    
    @Override
    protected void addNotify() {
        super.addNotify();
        recalculateKeys();
    }
    
    
    @SuppressWarnings("unchecked")
    public void recalculateKeys() {
        
        List l = (List)lock();
        l.clear();

        if (IReportManager.getPreferences().getBoolean("filter_variables",false))
        {
            List varsAll = dataset.getVariablesList();
            for (int i=0; i<varsAll.size(); ++i)
            {
                JRVariable p = (JRVariable)varsAll.get(i);
                if (!p.isSystemDefined())
                {
                    l.add(p);
                }
            }
        }
        else
        {
            l.addAll(dataset.getVariablesList());
        }
        update();
    }
    
    @SuppressWarnings("unchecked")
    public void reorder() { 
            Mutex.Action action = new Mutex.Action(){ 
                public Object run(){ 
                    Index.Support.showIndexedCustomizer(VariablesChildren.this.getIndex()); 
                    return null; 
                } 
            }; 
            MUTEX.writeAccess(action); 
        }

    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName() == null) return;
        if (evt.getPropertyName().equals( JRDesignDataset.PROPERTY_VARIABLES))
        {
            recalculateKeys();
        }
    }

    public void preferenceChange(PreferenceChangeEvent evt) {
        if (evt.getKey().equals("filter_variables"))
        {
            recalculateKeys();
        }
    }
}
