/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.welcome;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JLabel;
import javax.swing.UIManager;

/**
 *
 * @author gtoffoli
 */
public class TitleLabel extends JLabel {

    public TitleLabel()
    {
        super();
        setFont(new Font( null, Font.BOLD, getDefaultFontSize()+12 ));
        setForeground(new Color(154,175,201));
    }
    
    public TitleLabel(String title)
    {
        this();
        setText(title);
        
    }

    /**
     * This function comes from the open IDE (welcome module)
     * @return
     */
    public static int getDefaultFontSize() {

        Integer theCustomFontSize = (Integer)UIManager.get("customFontSize"); // NOI18N

        if (theCustomFontSize != null) {
            return theCustomFontSize.intValue();
        } else {
            Font systemDefaultFont = UIManager.getFont("TextField.font"); // NOI18N
            return (systemDefaultFont != null) ? systemDefaultFont.getSize() : 12;
        }
    }

    @Override
    public void paint(Graphics g) {
        ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        super.paint(g);
    }



}
