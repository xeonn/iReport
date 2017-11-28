/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.options.jasperreports;

import java.util.List;
import net.sf.jasperreports.engine.util.JRProperties;
import net.sf.jasperreports.engine.util.JRProperties.PropertySuffix;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.nodes.Node.PropertySet;
import org.openide.nodes.Sheet;

/**
 *
 * @author gtoffoli
 */
public class JRPropertiesNode extends AbstractNode {

    public JRPropertiesNode()
    {
        super(Children.LEAF);
    }

     @Override
    protected Sheet createSheet() {
        Sheet sheet = super.createSheet();

        Sheet.Set set = createSet("JasperReports properties",null);

        List props = JRProperties.getProperties("");
        
        for (int i=0; i<props.size(); ++i)
        {
            PropertySuffix prop = (PropertySuffix)props.get(i);
            set.put(new StringProperty(prop.getKey()));
        }

        sheet.put(set);
        return sheet;
    }


     public Sheet.Set createSet(String name, String displayName)
     {
         Sheet.Set set = Sheet.createPropertiesSet();
         set.setName(name);
         if (displayName == null)
         {
             set.setDisplayName(name);
         }
         else
         {
             set.setDisplayName(displayName);
         }

         return set;
     }

}
