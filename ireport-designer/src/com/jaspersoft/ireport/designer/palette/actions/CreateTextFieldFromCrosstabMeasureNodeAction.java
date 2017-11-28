/*
 * NewReportElementPaletteItemAction.java
 * 
 * Created on 12-nov-2007, 21.30.43
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.palette.actions;

import com.jaspersoft.ireport.designer.crosstab.CrosstabObjectScene;
import com.jaspersoft.ireport.designer.outline.nodes.CrosstabMeasureNode;
import com.jaspersoft.ireport.designer.utils.Misc;
import java.awt.dnd.DropTargetDropEvent;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.util.Mutex;

/**
 *
 * @author gtoffoli
 */
@SuppressWarnings("unchecked")
public class CreateTextFieldFromCrosstabMeasureNodeAction extends CreateTextFieldAction {

    @Override
    public void drop(DropTargetDropEvent dtde) {
    
        if ( !(getPaletteItem().getData() instanceof CrosstabMeasureNode))
        {
            System.out.println("Not a CrosstabMeasureNode");
            System.out.flush();
            return;
        }
        
        CrosstabMeasureNode measureNode = (CrosstabMeasureNode)getPaletteItem().getData();
        
        if (!(getScene() instanceof CrosstabObjectScene) ||
            measureNode.getCrosstab() != ((CrosstabObjectScene)getScene()).getDesignCrosstab() )
        {
            Runnable r = new Runnable() {
                public void run() {
                    JOptionPane.showMessageDialog(Misc.getMainFrame(), "You can only use a measure inside the crosstab\nin which the measure is defined.","Error", JOptionPane.WARNING_MESSAGE);
                }
            };
            
            Mutex.EVENT.readAccess(r); 
            return;
        }
        
        super.drop(dtde);
    }

    @Override
    public JRDesignElement createReportElement(JasperDesign jd)
    {
        JRDesignTextField element = (JRDesignTextField)super.createReportElement( jd );
        
        CrosstabMeasureNode measureNode = (CrosstabMeasureNode)getPaletteItem().getData();
        
        ((JRDesignExpression)element.getExpression()).setText("$V{"+ measureNode.getMeasure().getName() + "}");
        setMatchingClassExpression(
                ((JRDesignExpression)element.getExpression()),
                measureNode.getMeasure().getValueClassName(), 
                true);
        
        return element;
    }

}
