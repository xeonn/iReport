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
import com.jaspersoft.ireport.designer.utils.Misc;
import java.awt.dnd.DropTargetDropEvent;
import javax.swing.JOptionPane;
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstabParameter;
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
public class CreateTextFieldFromCrosstabParameterAction extends CreateTextFieldAction {

    @Override
    public void drop(DropTargetDropEvent dtde) {
    
        if (!(getScene() instanceof CrosstabObjectScene))
        {
            Runnable r = new Runnable() {
                public void run() {
                    JOptionPane.showMessageDialog(Misc.getMainFrame(), "You can only use a crosstab parameter inside a crosstab.","Error", JOptionPane.WARNING_MESSAGE);
                }
            };
            
            Mutex.EVENT.readAccess(r); 
            return;
        }
        
        super.drop(dtde);
    }

    
    
    
    public JRDesignElement createReportElement(JasperDesign jd)
    {
        JRDesignTextField element = (JRDesignTextField)super.createReportElement( jd );
        
        JRDesignCrosstabParameter parameter = (JRDesignCrosstabParameter)getPaletteItem().getData();

        ((JRDesignExpression)element.getExpression()).setText("$P{"+ parameter.getName() + "}");
        setMatchingClassExpression(
                ((JRDesignExpression)element.getExpression()),
                parameter.getValueClassName(), 
                true);
        
        return element;
    }

}
