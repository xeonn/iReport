/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.templates;

import com.jaspersoft.ireport.designer.welcome.TextLabel;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author gtoffoli
 */
public class ClickableTextLabel extends TextLabel {

    public Color normalColor = new Color(109,109,109);
    public Color selectedColor = new Color(22,152,212);
    public Color hoverColor = new Color(210,82,31);

    private boolean selected = false;
    public static final String PROPERTY_SELECTED = "selected";


    public ClickableTextLabel()
    {
        super();

        setForeground(normalColor);

        this.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                if (!isSelected())
                {
                    setForeground(hoverColor);
                }
                
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                if (!isSelected())
                {
                    setForeground(normalColor);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (!selected)
                {
                    setSelected(true);
                    firePropertyChange(PROPERTY_SELECTED, false, true);
                }
            }
        });

    }

    /**
     * @return the selected
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * @param selected the selected to set
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
        this.setForeground((selected) ? selectedColor : normalColor);
    }

}
