/*
 * EditorContext.java
 * 
 * Created on Oct 11, 2007, 4:25:53 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.editor;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import org.openide.util.Utilities;

/**
 *
 * @author gtoffoli
 */
public class NamedIconItem {
    
    public static final String ICON_FOLDER_FIELDS = "com/jaspersoft/ireport/designer/resources/fields-16.png";
    public static final String ICON_FOLDER_PARAMETERS = "com/jaspersoft/ireport/designer/resources/parameters-16.png";
    public static final String ICON_FOLDER_VARIABLES = "com/jaspersoft/ireport/designer/resources/variables-16.png";
    public static final String ICON_FOLDER_WIZARDS = "com/jaspersoft/ireport/designer/resources/fields-16.png";
    public static final String ICON_FOLDER_RECENT_EXPRESSIONS = "com/jaspersoft/ireport/designer/resources/fields-16.png";
    public static final String ICON_FOLDER_FORMULAS = "com/jaspersoft/ireport/designer/resources/fields-16.png";
    public static final String ICON_CROSSTAB = "com/jaspersoft/ireport/designer/resources/crosstab-16.png";
    
    private Object item = null;
    private String displayName = null;
    private Icon icon = null;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public Object getItem() {
        return item;
    }

    public void setItem(Object item) {
        this.item = item;
    }
    
    public NamedIconItem(Object item)
    {
        this(item, null);
    }
    
    public NamedIconItem(Object item, String displayName)
    {
        this(item, displayName, (Icon)null);
    }
    
    public NamedIconItem(Object item, String displayName, Icon icon)
    {
        this.item = item;
        this.displayName = displayName;
        this.icon = icon;
    }
    
    public NamedIconItem(Object item, String displayName, String iconName)
    {
        this(item, displayName);
        try {
            this.icon = new ImageIcon( Utilities.loadImage(iconName) );
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    
    @Override
    public String toString()
    {
        return (displayName == null) ? item+"" : displayName;
    }
}
