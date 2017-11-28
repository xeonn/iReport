/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.outline.nodes;

import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignGroup;

/**
 *
 * @author gtoffoli
 */
public interface GroupNode {
    public JRDesignDataset getDataset();
    public JRDesignGroup getGroup();
}
