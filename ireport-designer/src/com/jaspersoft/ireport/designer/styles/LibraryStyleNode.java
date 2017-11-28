/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.styles;

import com.jaspersoft.ireport.designer.dnd.ReportObjectPaletteTransferable;
import com.jaspersoft.ireport.designer.jrtx.StyleNode;
import java.awt.datatransfer.Transferable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Action;
import net.sf.jasperreports.engine.JRSimpleTemplate;
import net.sf.jasperreports.engine.base.JRBaseStyle;
import org.openide.util.Lookup;
import org.openide.util.actions.SystemAction;
import org.openide.util.datatransfer.ExTransferable;

/**
 *
 * @author gtoffoli
 */
public class LibraryStyleNode extends StyleNode {

    public LibraryStyleNode(JRSimpleTemplate template, JRBaseStyle style, Lookup doLkp)
    {
        super (template, style, doLkp);
    }

    @Override
    public Action[] getActions(boolean popup) {

        List<Action> actions = new ArrayList<Action>();
        actions.add(SystemAction.get( AddStyleToReportAction.class ));
        actions.add(null);

        Action[] originals = super.getActions(popup);

        for (int i=0; i<originals.length; ++i)
        {
            actions.add(originals[i]);
        }

        return actions.toArray(new Action[actions.size()]);
    }

    @Override
    public Transferable drag() throws IOException {

        ExTransferable tras = ExTransferable.create(clipboardCut());
        tras.put(new ReportObjectPaletteTransferable(
                    "com.jaspersoft.ireport.designer.styles.DragStyleAction",
                    getStyle()));

        return tras;
    }

}
