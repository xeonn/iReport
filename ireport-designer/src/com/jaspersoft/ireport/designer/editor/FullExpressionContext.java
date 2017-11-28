/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.editor;

import com.jaspersoft.ireport.designer.ModelUtils;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstab;
import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.JRElementGroup;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JasperDesign;

/**
 * A full expression context is an expression context exposing all 
 * the datasets and crosstabs of a document.
 * The context keep just a weak pointer to the Jasperdesign to avoid
 * memory leak.
 * 
 * @author gtoffoli
 */
public class FullExpressionContext extends ExpressionContext {

    WeakReference<JasperDesign> documentReference = null;
    public FullExpressionContext(JasperDesign jasperDesign)
    {
        this.documentReference = new WeakReference<JasperDesign>(jasperDesign);
    }

    @Override
    public List<JRDesignCrosstab> getCrosstabs() {
        if (documentReference != null &&
            documentReference.get() != null)
        {
            // collect all the crosstabs...
            List<JRDesignCrosstab> list = new ArrayList<JRDesignCrosstab>();
            // collect all the crosstabs...
            List<JRBand> bands = ModelUtils.getBands(documentReference.get());
            
            for (JRBand band : bands)
            {
                if (band != null)
                {
                    findCrosstabs( band, list);
                }
            }
            return list;
        }
        return Collections.EMPTY_LIST;
    }
    
    public List<JRDesignCrosstab> findCrosstabs( JRElementGroup group, List<JRDesignCrosstab> list)
    {
        List objects = group.getChildren();
        for (int i=0; i<objects.size(); ++i)
        {
            Object obj = objects.get(i);
            if (obj instanceof JRDesignCrosstab)
            {
                list.add((JRDesignCrosstab)obj);
            }
            else if (obj instanceof JRElementGroup)
            {
                findCrosstabs( (JRElementGroup)obj, list);
            }
        }
        
        return list;
    }

    @Override
    public List<JRDesignDataset> getDatasets() {
        
        if (documentReference != null &&
            documentReference.get() != null)
        {
            List<JRDesignDataset> list = new ArrayList<JRDesignDataset>();
            // collect all the crosstabs...
            list.add(documentReference.get().getMainDesignDataset());
            List datasets = documentReference.get().getDatasetsList();
            for (int i=0; i<datasets.size(); ++i)
            {
                list.add((JRDesignDataset)datasets.get(i));
            }
            
            return list;
        }
        
        return super.getDatasets();
    }
    
    
    
}
