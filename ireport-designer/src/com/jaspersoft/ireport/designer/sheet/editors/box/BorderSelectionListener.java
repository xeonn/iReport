/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.editors.box;

import com.jaspersoft.ireport.designer.sheet.editors.box.BoxBorderSelectionPanel.Side;
import java.util.List;

/**
 *
 * @author gtoffoli
 */
public interface BorderSelectionListener {

    public void selectionChanged(List<Side> selectedBorders);
    
}
