/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.styles;

import com.jaspersoft.ireport.designer.dnd.ReportObjectPaletteTransferable;
import com.jaspersoft.ireport.designer.jrtx.TemplateReferenceNode;
import java.awt.datatransfer.Transferable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Action;
import net.sf.jasperreports.engine.JRSimpleTemplate;
import net.sf.jasperreports.engine.JRTemplateReference;
import org.openide.actions.CopyAction;
import org.openide.actions.CutAction;
import org.openide.actions.DeleteAction;
import org.openide.actions.RenameAction;
import org.openide.util.Lookup;
import org.openide.util.actions.SystemAction;
import org.openide.util.datatransfer.ExTransferable;

/**
 *
 * @author gtoffoli
 */
public class LibraryTemplateReferenceNode extends TemplateReferenceNode {

    public LibraryTemplateReferenceNode(JRSimpleTemplate template, JRTemplateReference reference, Lookup doLkp)
    {
        super (template, reference, doLkp);
    }

    @Override
    public boolean canCut() {
        return true;
    }

    @Override
    public boolean canCopy() {
        return true;
    }

    @Override
    public Action[] getActions(boolean popup) {

        List<Action> actions = new ArrayList<Action>();
        actions.add(SystemAction.get( AddTemplateReferenceToReportAction.class ));
        //actions.add(null);
        //actions.add(SystemAction.get( CopyAction.class ));
        //actions.add(SystemAction.get( CutAction.class ));
        //actions.add(SystemAction.get( RenameAction.class ));
        actions.add(null);
        actions.add(SystemAction.get( DeleteAction.class ));

        return actions.toArray(new Action[actions.size()]);
    }

    @Override
    public Transferable drag() throws IOException {

        System.out.println("dragging....");
        System.out.flush();

        ExTransferable tras = (ExTransferable)super.drag();
        tras.put(new ReportObjectPaletteTransferable(
                    "com.jaspersoft.ireport.designer.styles.DragTemplateReferenceAction",
                    getReference()));

        return tras;
    }
}
