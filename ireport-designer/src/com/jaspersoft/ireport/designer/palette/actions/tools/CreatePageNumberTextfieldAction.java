/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.palette.actions.tools;

import com.jaspersoft.ireport.designer.palette.actions.*;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JasperDesign;

/**
 *
 * @author gtoffoli
 */
public class CreatePageNumberTextfieldAction extends CreateTextFieldAction {

    @Override
    public JRDesignElement createReportElement(JasperDesign jd)
    {
        JRDesignTextField element = (JRDesignTextField)super.createReportElement( jd );

        ((JRDesignExpression)element.getExpression()).setText("$V{PAGE_NUMBER}");
        ((JRDesignExpression)element.getExpression()).setValueClassName("java.lang.Integer");

        setMatchingClassExpression(
            ((JRDesignExpression)element.getExpression()),
            "java.lang.Integer",
            true
            );

        return element;
    }

    /*
    public static PaletteItem createPaletteItem()
    {
        Properties props = new Properties();
        props.setProperty(PaletteItem.ACTION ,CreatePageNumberTextfieldAction.class.getName());
        props.setProperty(PaletteItem.PROP_ID , "PageNumber");
        props.setProperty(PaletteItem.PROP_NAME , "Page #");
        props.setProperty(PaletteItem.PROP_COMMENT , "Creates a textfield to display the page number");
        props.setProperty(PaletteItem.PROP_ICON16,"com/jaspersoft/ireport/designer/resources/textfield-16.png");
        props.setProperty(PaletteItem.PROP_ICON32,"com/jaspersoft/ireport/designer/resources/textfield-32.png");

        try {
            FileObject selectedPaletteFolder = Misc.createFolders("ireport/palette/tools");

            // We create the card if it does not exist:

            FileObject toolFile = selectedPaletteFolder.getFileObject(
                "PageNumber","irpitem");
            if (toolFile==null) {
                toolFile = selectedPaletteFolder.createData(
                     "PageNumber","irpitem");
            }
            FileLock lock = toolFile.lock();
            OutputStream out = toolFile.getOutputStream(lock);
    // Write the icon that the user selected
    // to the card file:
            props.store(out, "Tool PageNumber");
            out.close();
            lock.releaseLock();
        } catch (IOException ex) {
                ex.printStackTrace();
        }

        return null;
    }
     */
}
