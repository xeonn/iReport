package com.jaspersoft.ireport.designer.sheet.properties.style;

import com.jaspersoft.ireport.designer.sheet.JRLineBoxProperty;
import net.sf.jasperreports.engine.base.JRBaseStyle;

/**
 * Class to manage the JRDesignStyle.PROPERTY_ITALIC property
 */
public final class PaddingAndBordersProperty extends JRLineBoxProperty {

    private final JRBaseStyle style;

    @SuppressWarnings(value = "unchecked")
    public PaddingAndBordersProperty(JRBaseStyle style) {
        super(style);
        this.style = style;
    }
}
