/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.templates;

import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.design.JRDesignStaticText;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import org.openide.WizardDescriptor;
import org.openide.filesystems.FileObject;

/**
 *
 * @author gtoffoli
 */
public abstract class AbstractReportGenerator implements ReportGenerator {

    abstract public FileObject generateReport(WizardDescriptor descriptor);
    
    /**
     * Find in band a JRDesignStaticText element having exp as text.
     * @param band
     * @param exp
     * @return the first matching element or null.
     */
    public static  JRDesignStaticText findStaticTextElement(JRBand band, String exp)
    {
        JRElement[] elements = band.getElements();
        for (int i=0; i<elements.length; ++i)
        {
            JRElement ele = elements[i];
            if (ele instanceof JRDesignStaticText)
            {
                JRDesignStaticText st = (JRDesignStaticText)ele;
                if (st.getText() != null &&
                    st.getText().equals(exp))
                {
                    return st;
                }
            }
        }
        return null;
    }
    
    /**
     * Find in band a JRDesignTextField element having exp as expression value.
     * @param band
     * @param exp
     * @return the first matching element or null.
     */
    public static JRDesignTextField findTextFieldElement(JRBand band, String exp)
    {
        JRElement[] elements = band.getElements();
        for (int i=0; i<elements.length; ++i)
        {
            JRElement ele = elements[i];
            if (ele instanceof JRDesignTextField)
            {
                JRDesignTextField st = (JRDesignTextField)ele;
                if (st.getExpression() != null &&
                    st.getExpression().getText() != null &&
                    st.getExpression().getText().equals(exp))
                {
                    return st;
                }
            }
        }
        return null;
    }
    

}
