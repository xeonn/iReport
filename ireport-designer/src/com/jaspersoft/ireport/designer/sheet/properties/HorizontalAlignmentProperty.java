/*
 * GraphicElementPropertiesFactory.java
 * 
 * Created on 5-nov-2007, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.sheet.Tag;
import com.jaspersoft.ireport.locale.I18n;
import java.util.List;
import net.sf.jasperreports.engine.JRAlignment;
import net.sf.jasperreports.engine.base.JRBaseStyle;
import net.sf.jasperreports.engine.xml.JRXmlConstants;

    
/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public final class HorizontalAlignmentProperty extends ByteProperty
{
    private final JRAlignment element;

    @SuppressWarnings("unchecked")
    public HorizontalAlignmentProperty(JRAlignment element)
    {
        super(element);
        this.element = element;
    }

    @Override
    public String getName()
    {
        return JRBaseStyle.PROPERTY_HORIZONTAL_ALIGNMENT;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Global.Property.HorizontalAlignment");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Global.Property.HorizontalAlignmentdetail");
    }

    @Override
    public List getTagList() 
    {
        List tags = new java.util.ArrayList();
        tags.add(new Tag(new Byte(JRAlignment.HORIZONTAL_ALIGN_LEFT), I18n.getString("Global.Property.Left")));
        tags.add(new Tag(new Byte(JRAlignment.HORIZONTAL_ALIGN_CENTER), I18n.getString("Global.Property.Center")));
        tags.add(new Tag(new Byte(JRAlignment.HORIZONTAL_ALIGN_RIGHT), I18n.getString("Global.Property.Right")));
        tags.add(new Tag(new Byte(JRAlignment.HORIZONTAL_ALIGN_JUSTIFIED), I18n.getString("Global.Property.Justified")));
        return tags;
    }

    @Override
    public Byte getByte()
    {
        return element.getHorizontalAlignment();
    }

    @Override
    public Byte getOwnByte()
    {
        return element.getOwnHorizontalAlignment();
    }

    @Override
    public Byte getDefaultByte()
    {
        return null;
    }

    @Override
    public void setByte(Byte alignment)
    {
        element.setHorizontalAlignment(alignment);

        if (IReportManager.getPreferences().getBoolean("designer_debug_mode", false))
        {
            System.out.println(new java.util.Date() + ": setting HorizontalAlignment to: " + alignment + ". If the value is unattended or null, please report this notification to http://jasperforge.org/plugins/mantis/view.php?id=4139");
            Thread.dumpStack();
        }
    }

}
