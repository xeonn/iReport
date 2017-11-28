/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.components.list;

import com.jaspersoft.ireport.designer.palette.actions.*;
import net.sf.jasperreports.components.list.DesignListContents;
import net.sf.jasperreports.components.list.StandardListComponent;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.component.ComponentKey;
import net.sf.jasperreports.engine.design.JRDesignComponentElement;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignDatasetRun;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.util.Exceptions;

/**
 *
 * @author gtoffoli
 */
public class CreateListAction extends CreateReportElementAction {

    @Override
    public JRDesignElement createReportElement(JasperDesign jd) {




        JRDesignComponentElement component = new JRDesignComponentElement();
        StandardListComponent componentImpl = new StandardListComponent();
        DesignListContents contents = new DesignListContents();
        contents.setHeight(50);
        componentImpl.setContents(contents);

        JRDesignDataset newDataset = new JRDesignDataset(false);
        String name = "dataset";
        for (int i = 1;; i++) {
            if (!jd.getDatasetMap().containsKey(name + i)) {
                newDataset.setName(name + i);
                break;
            }
        }
        try {
            jd.addDataset(newDataset);
        } catch (JRException ex) {
            //Exceptions.printStackTrace(ex);
        }
        JRDesignDatasetRun datasetRun = new JRDesignDatasetRun();

        datasetRun.setDatasetName(newDataset.getName());

        JRDesignExpression exp = new JRDesignExpression();
        exp.setValueClassName("net.sf.jasperreports.engine.JRDataSource");//NOI18N
        exp.setText("new net.sf.jasperreports.engine.JREmptyDataSource(1)");//NOI18N

        datasetRun.setDataSourceExpression(exp);
        
        
        componentImpl.setDatasetRun(datasetRun);
        component.setComponent(componentImpl);
        component.setComponentKey(new ComponentKey(
                                    "http://jasperreports.sourceforge.net/jasperreports/components",
                                    "jr", "list"));

        component.setWidth(400);
        component.setHeight(50);

        return component;
    }



    
}
