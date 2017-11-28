/*
 * iReport - Visual Designer for JasperReports.
 * Copyright (C) 2002 - 2009 Jaspersoft Corporation. All rights reserved.
 * http://www.jaspersoft.com
 * 
 * Unless you have purchased a commercial license agreement from Jaspersoft,
 * the following license terms apply:
 * 
 * This program is part of iReport.
 * 
 * iReport is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * iReport is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with iReport. If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * LayersListPanel.java
 *
 * Created on 25-feb-2010, 12.26.41
 */

package com.jaspersoft.ireport.addons.layers;

import com.jaspersoft.ireport.designer.IReportManager;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;

/**
 *
 * @author gtoffoli
 */
public class LayersListPanel extends javax.swing.JPanel implements LayersChangedListener  {

    public static final String PROPERTY_SELECTED_ITEMS = "PROPERTY_SELECTED_ITEMS";

    private LayerItemPanel selectedItem = null; // primary selection...
    

    /** Creates new form LayersListPanel */
    public LayersListPanel() {
        initComponents();
        LayersSupport.getInstance().addLayersChangedListener(this);
        LayersChangedEvent event = new LayersChangedEvent( LayersChangedEvent.LAYERS_LIST_CHANGED, IReportManager.getInstance().getActiveReport());
        layersChanged(event);
    }

    @Override
    public Dimension getMinimumSize() {

        int h = 0;
        for (int i=0; i<getComponentCount(); ++i)
        {
            Dimension d = getComponent(i).getPreferredSize();
            if (d != null)
            {
                h += d.height;
            }
        }

        Dimension gg = super.getMinimumSize();
        gg.height = h;

        return gg;
    }



    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    
    public List<Layer> getSelectedLayers()
    {
        List<Layer> layers = new ArrayList<Layer>();
        Component[] components = this.getComponents();

            for (int i=0; i<components.length; ++i)
            {
                if (components[i] instanceof LayerItemPanel)
                {
                    if (((LayerItemPanel)components[i]).isSelected())
                    {
                        layers.add( ((LayerItemPanel)components[i]).getLayer());
                    }
                }
            }
        return layers;
    }



    public void itemMousePressed(MouseEvent evt)
    {
        LayerItemPanel currentlySelectedItem = (LayerItemPanel)evt.getSource();

        //currentlySelectedItem.setSelected(true);

        if (evt.isControlDown())
        {
            if (currentlySelectedItem.isSelected())
            {
                currentlySelectedItem.setSelected(false);
                if (currentlySelectedItem == selectedItem)
                {
                    List<Layer> selectedLayers = getSelectedLayers();
                    if (selectedLayers.size() > 0)
                    {
                        selectedItem = getLayerPanelOf(selectedLayers.get(0));
                    }
                    else
                    {
                        selectedItem = null;
                    }
                }
            }
            else
            {
                currentlySelectedItem.setSelected(true);
                selectedItem = currentlySelectedItem;
            }
        }
        else if (evt.isShiftDown() && selectedItem != null)
        {
            // Select all the items between selectedItem and currentlySelectedItem
            Component[] components = this.getComponents();
            boolean select = false;
            for (int i=0; i<components.length; ++i)
            {
                if (components[i] instanceof LayerItemPanel)
                {
                    if (components[i] == selectedItem ||
                        components[i] == currentlySelectedItem)
                    {
                        ((LayerItemPanel)components[i]).setSelected(true);
                        select = !select;
                    }
                    else
                    {
                        ((LayerItemPanel)components[i]).setSelected(select);
                    }
                }
            }
            selectedItem = currentlySelectedItem;
        }
        else
        {
            // Unselect all the others...
            currentlySelectedItem.setSelected(true);
            selectedItem = currentlySelectedItem;
            for (int i=0; i<getComponentCount(); ++i)
            {
                Component c = getComponent(i);
                if (c instanceof LayerItemPanel && c != currentlySelectedItem)
                {
                    ((LayerItemPanel)c).setSelected(false);
                }
            }
        }

        

        firePropertyChange(PROPERTY_SELECTED_ITEMS, null, selectedItem);


    }


    private LayerItemPanel getLayerPanelOf(Layer l) {

        Component[] components = this.getComponents();
        for (int i=0; i<components.length; ++i)
        {
            if (components[i] instanceof LayerItemPanel)
            {
                if (((LayerItemPanel)components[i]).getLayer() == l)
                {
                    return (LayerItemPanel)components[i];
                }
            }
        }

        return null;
    }



    public void layersChanged(LayersChangedEvent event) {
        if (event.getType() == LayersChangedEvent.LAYERS_ADDED)
        {
            for (Layer layer : event.getChangedLayers())
            {
                LayerItemPanel l = new LayerItemPanel();
                l.setLayer(layer);
                add(l,0);
            }
            updateUI();
        }
        else if (event.getType() == LayersChangedEvent.LAYERS_REMOVED)
        {
            boolean fireSelectionChanged = false;
            Object oldSelectedItem = selectedItem;
            for (Layer layer : event.getChangedLayers())
            {
                LayerItemPanel l = getLayerPanelOf(layer);
                if (l.isSelected())
                {
                    fireSelectionChanged = true;
                    if (l == selectedItem)
                    {
                        selectedItem = null;
                    }
                }
                remove(l);
            }
            
            if (fireSelectionChanged)
            {
                if (selectedItem == null)
                {
                    // Find the first selected item...
                    Component[] components = this.getComponents();
                    for (int i=0; i<components.length; ++i)
                    {
                        if (components[i] instanceof LayerItemPanel)
                        {
                            if (((LayerItemPanel)components[i]).isSelected())
                            {
                               selectedItem = (LayerItemPanel)components[i];
                               break;
                            }
                        }
                    }
                }
                else
                {
                    oldSelectedItem = null;
                }
                firePropertyChange(PROPERTY_SELECTED_ITEMS, oldSelectedItem, selectedItem);
                
            }


            updateUI();
        }
        else if (event.getType() == LayersChangedEvent.LAYERS_LIST_CHANGED)
        {
            removeAll();
            List<Layer> list = event.getLayers();

            for (Layer layer : list)
            {
                LayerItemPanel l = new LayerItemPanel();
                l.setLayer(layer);
                add(l,0);
            }
            add(Box.createVerticalGlue());
            updateUI();
            LayerItemPanel oldSelection = selectedItem;
            selectedItem = null;
            firePropertyChange(PROPERTY_SELECTED_ITEMS, oldSelection, selectedItem);
        }
    }

    


}
