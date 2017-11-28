/*
 * GraphicElementPropertiesFactory.java
 * 
 * Created on 5-nov-2007, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties;

import com.jaspersoft.ireport.designer.sheet.Tag;
import com.jaspersoft.ireport.locale.I18n;
import java.util.List;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.design.JRDesignImage;

    
/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public final class EvaluationTimeProperty extends ByteProperty
{
    private final JRDesignImage image;

    @SuppressWarnings("unchecked")
    public EvaluationTimeProperty(JRDesignImage image)
    {
        super(image);
        this.image = image;
    }

    @Override
    public String getName()
    {
        return JRDesignImage.PROPERTY_EVALUATION_TIME;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Global.Property.EvaluationTime");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Global.Property.ETdetail");
    }

    @Override
    public List getTagList() 
    {
        List tags = new java.util.ArrayList();
        tags.add(new Tag(new Byte(JRExpression.EVALUATION_TIME_NOW), I18n.getString("Global.Property.Now")));
        tags.add(new Tag(new Byte(JRExpression.EVALUATION_TIME_REPORT), I18n.getString("Global.Property.Report")));
        tags.add(new Tag(new Byte(JRExpression.EVALUATION_TIME_PAGE), I18n.getString("Global.Property.Page")));
        tags.add(new Tag(new Byte(JRExpression.EVALUATION_TIME_COLUMN), I18n.getString("Global.Property.Column")));
        tags.add(new Tag(new Byte(JRExpression.EVALUATION_TIME_GROUP), I18n.getString("Global.Property.Group")));
        tags.add(new Tag(new Byte(JRExpression.EVALUATION_TIME_BAND), I18n.getString("Global.Property.Band")));
        tags.add(new Tag(new Byte(JRExpression.EVALUATION_TIME_AUTO), I18n.getString("Global.Property.Auto")));
        return tags;
    }

    @Override
    public Byte getByte()
    {
        return image.getEvaluationTime();
    }

    @Override
    public Byte getOwnByte()
    {
        return image.getEvaluationTime();
    }

    @Override
    public Byte getDefaultByte()
    {
        return JRExpression.EVALUATION_TIME_NOW;
    }

    @Override
    public void setByte(Byte evaluationTime)
    {
        image.setEvaluationTime(evaluationTime);
    }

}

//FIXMETD i don't think we should affect the evaluation group automatically or make any validation
//private void setPropertyValue(Byte val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException 
//{
//        Byte oldValue = element.getEvaluationTime();
//        Byte newValue = val;
//
//        ObjectPropertyUndoableEdit urob =
//                new ObjectPropertyUndoableEdit(
//                    element,
//                    "EvaluationTime", 
//                    Byte.TYPE,
//                    oldValue,newValue);
//
//        JRGroup oldGroupValue = element.getEvaluationGroup();
//        JRGroup newGroupValue = null;
//        if ( (val).byteValue() == JRExpression.EVALUATION_TIME_GROUP )
//        {
//            if (dataset.getGroupsList().size() == 0)
//            {
//                IllegalArgumentException iae = annotateException("No groups are defined to be used with this element"); 
//                throw iae; 
//            }
//
//            newGroupValue = (JRGroup)dataset.getGroupsList().get(0);
//        }
//
//        element.setEvaluationTime(newValue);
//
//        if (oldGroupValue != newGroupValue)
//        {
//            ObjectPropertyUndoableEdit urobGroup =
//                    new ObjectPropertyUndoableEdit(
//                        element,
//                        "EvaluationGroup", 
//                        JRGroup.class,
//                        oldGroupValue,newGroupValue);
//            element.setEvaluationGroup(newGroupValue);
//            urob.concatenate(urobGroup);
//        }
//
//        // Find the undoRedo manager...
//        IReportManager.getInstance().addUndoableEdit(urob);
//}
