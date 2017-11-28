/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.styles;

import com.jaspersoft.ireport.designer.AbstractReportObjectScene;
import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.ModelUtils;
import com.jaspersoft.ireport.designer.palette.PaletteItemAction;
import com.jaspersoft.ireport.designer.widgets.JRDesignElementWidget;
import java.awt.Point;
import java.awt.dnd.DropTargetDropEvent;
import java.util.List;
import javax.swing.SwingUtilities;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRStyle;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JRDesignStyle;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Widget;
import org.openide.util.Exceptions;
import org.openide.windows.WindowManager;

/**
 *
 * @author gtoffoli
 */
public class DragStyleAction extends PaletteItemAction {

    private static JRDesignElement findElementAt(AbstractReportObjectScene theScene, Point p) {

         LayerWidget layer = theScene.getElementsLayer();
         List<Widget> widgets = layer.getChildren();

         // Use revers order to find the top most...
         for (int i= widgets.size()-1; i>=0; --i)
         {
             Widget w = widgets.get(i);
             Point p2 = w.convertSceneToLocal(p);
             if (w.isHitAt(p2))
             {
                 if (w instanceof JRDesignElementWidget)
                 {
                     JRDesignElement de =((JRDesignElementWidget)w).getElement();
                     return de;
                 }
             }
         }
         return null;
    }

    @Override
    public void drop(DropTargetDropEvent dtde) {

        // Let's see what to do with this style....

        Point p = getScene().convertViewToScene(dtde.getLocation());

        final JRDesignElement elem = findElementAt((AbstractReportObjectScene)getScene(), p);

        JRDesignStyle s = (JRDesignStyle) getPaletteItem().getData();

        //TODO... Create a copy of s
        s = ModelUtils.cloneStyle(s);

        Object toActivate = s;
        if (elem != null)
        {
            if (getJasperDesign().getStylesMap().containsKey(s.getName()))
            {
                elem.setStyle( (JRStyle)getJasperDesign().getStylesMap().get(s.getName()) );
            }
            else
            {
                try {
                    getJasperDesign().addStyle(s);
                    elem.setStyle(s);
                } catch (JRException ex) {
                    ex.printStackTrace();
                }
            }

            toActivate = elem;
            
            
            //IReportManager.getInstance().getActiveVisualView().getReportDesignerPanel().requestFocusInWindow();
            //IReportManager.getInstance().getActiveVisualView().requestFocus(false);

        }
        else
        {
            if (!getJasperDesign().getStylesMap().containsKey(s.getName()))
            {
                try {
                    getJasperDesign().addStyle(s);
                } catch (JRException ex) {
                    ex.printStackTrace();
                }
            }
            else
            {
                toActivate = null;
            }
        }

        final Object obj = toActivate;

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
