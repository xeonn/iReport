/*
 * PaletteItemAction.java
 * 
 * Created on 12-nov-2007, 21.23.45
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.palette;

import net.sf.jasperreports.engine.design.JasperDesign;
import org.netbeans.api.visual.widget.Scene;

/**
 *
 * @author gtoffoli
 */
public abstract class PaletteItemAction {

    private PaletteItem paletteItem = null;
    private Scene scene = null;
    private JasperDesign jasperDesign = null;
    
    public PaletteItem getPaletteItem() {
        return paletteItem;
    }

    public void setPaletteItem(PaletteItem paletteItem) {
        this.paletteItem = paletteItem;
    }
    
    /**
     * It can be a ReportObjectScene or a scene relative to a Crosstab...
     */
    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public JasperDesign getJasperDesign() {
        return jasperDesign;
    }

    public void setJasperDesign(JasperDesign jasperDesign) {
        this.jasperDesign = jasperDesign;
    }
    
    public abstract void drop(java.awt.dnd.DropTargetDropEvent dtde);
    
    
    
}
