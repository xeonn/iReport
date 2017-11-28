/*
 * IRFont.java
 * 
 * Created on 7-nov-2007, 12.46.56
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer;

import com.jaspersoft.ireport.locale.I18n;

/**
 *
 * @author gtoffoli
 */
public class IRFont {
    
    private java.awt.Font font;
    
    private java.lang.String file;
    
    /** Creates a new instance of IRFont */
    public IRFont() {
    }
    
     public IRFont(java.awt.Font font, java.lang.String file) {
         this.font = font;
         this.file = file;
    }
    
    /** Getter for property file.
     * @return Value of property file.
     *
     */
    public java.lang.String getFile() {
        return file;
    }
    
    /** Setter for property file.
     * @param file New value of property file.
     *
     */
    public void setFile(java.lang.String file) {
        this.file = file;
    }
    
    /** Getter for property font.
     * @return Value of property font.
     *
     */
    public java.awt.Font getFont() {
        return font;
    }
    
    /** Setter for property font.
     * @param font New value of property font.
     *
     */
    public void setFont(java.awt.Font font) {
        this.font = font;
    }
    
    
    @Override
    public String toString()
    {
        if (font == null || file == null) return I18n.getString("IRFont.Message.NotInitializedFont");
        return font.getFontName()+" ("+file+")";
    }    
}
