/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.styles;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.palette.PaletteItemAction;
import com.jaspersoft.ireport.designer.utils.Misc;
import java.awt.dnd.DropTargetDropEvent;
import javax.swing.SwingUtilities;
import net.sf.jasperreports.engine.JRTemplateReference;
import net.sf.jasperreports.engine.design.JRDesignReportTemplate;
import net.sf.jasperreports.engine.design.JasperDesign;

/**
 *
 * @author gtoffoli
 */
public class DragTemplateReferenceAction extends PaletteItemAction {

    @Override
    public void drop(DropTargetDropEvent dtde) {

        JasperDesign jd = getJasperDesign();
        Object toActivate = null;

        JRTemplateReference reference = (JRTemplateReference)getPaletteItem().getData();
        if (reference != null)
        {
                // Copy the reference...
                JRDesignReportTemplate reportTemplate = new JRDesignReportTemplate();
                reportTemplate.setSourceExpression(Misc.createExpression("java.lang.String", "\""+ Misc.string_replace("\\\\","\\",reference.getLocation()) +"\""));
                jd.addTemplate(reportTemplate);
                IReportManager.getInstance().notifyReportChange();
                toActivate = reportTemplate;
        }

        final Object obj = toActivate;

        if (obj != null)
        {
            SwingUtilities.invokeLater(new Runnable()
            {
                public void run()
                {
                    IReportManager.getInstance().setSelectedObject(obj);
                    IReportManager.getInstance().getActiveVisualView().requestActive();
                    IReportManager.getInstance().getActiveVisualView().requestAttention(true);
                }
            });
        }
    }

}
