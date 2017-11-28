/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.welcome;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This class uses code from Piet Blok released under Apache License Version 2.0.
 *
 * @author gtoffoli
 */
public class TextLabel extends JLabel {

    private int maxWidth = 0;

    JLabel textLabel = null;
    JPanel offScreenPanel = null;

    @Override
    public final Dimension getPreferredSize() {
        Dimension preferred = super.getPreferredSize();
        if (getMaxWidth() > 0 && getMaxWidth() < preferred.width) {
            //preferred =  new Dimension(getMaxWidth(), preferred.height * (int)Math.ceil(preferred.width / (double)getMaxWidth()));
            preferred =  recalculatePreferredSize(maxWidth);
        }
        return preferred;
    }

    /**
     * @return the maxWidth
     */
    public int getMaxWidth() {
        return maxWidth;
    }

    /**
     * @param maxWidth the maxWidth to set
     */
    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
    }



    private Dimension recalculatePreferredSize(int widthLimit) {
        if (textLabel == null)
        {
            textLabel = new JLabel(this.getText());
        }
        textLabel.setBorder(this.getBorder());
        textLabel.setIcon(this.getIcon());
        textLabel.setLocale(this.getLocale());
        textLabel.setDisabledIcon(this.getDisabledIcon());
        textLabel.setFont(this.getFont());
        textLabel.setHorizontalAlignment(this.getHorizontalAlignment());
        textLabel.setHorizontalTextPosition(this.getHorizontalTextPosition());
        textLabel.setVerticalAlignment(this.getVerticalAlignment());
        textLabel.setVerticalTextPosition(this.getVerticalTextPosition());
        textLabel.setIconTextGap(this.getIconTextGap());

        if (offScreenPanel == null)
        {
            offScreenPanel = new JPanel();
            offScreenPanel.setLayout(new BorderLayout());
            offScreenPanel.add(textLabel);
        }

        Dimension initialPreferred = offScreenPanel.getPreferredSize();
        offScreenPanel.setSize(widthLimit, 2 * initialPreferred.height
			+ initialPreferred.height * initialPreferred.width/ widthLimit);

        offScreenPanel.getLayout().layoutContainer(offScreenPanel);
        //offScreenPanel.paint(previewGraphics);

    	return offScreenPanel.getSize();

    }

}
