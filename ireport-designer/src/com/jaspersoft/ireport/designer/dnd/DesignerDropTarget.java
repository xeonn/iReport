/*
 * DesignerDropTarget.java
 * 
 * Created on 12-nov-2007, 19.04.37
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.dnd;

import com.jaspersoft.ireport.designer.ReportObjectScene;
import java.awt.dnd.DropTarget;

/**
 *
 * @author gtoffoli
 */
public class DesignerDropTarget extends DropTarget {

    public DesignerDropTarget(ReportObjectScene scene)
    {
        super(scene.getJComponent(), new DesignerDropTargetListener(scene) );
    }
}
