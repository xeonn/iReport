/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.jasperserver.ui.actions;

import com.jaspersoft.ireport.designer.AbstractReportObjectScene;
import com.jaspersoft.ireport.designer.ModelUtils;
import com.jaspersoft.ireport.designer.palette.PaletteItemAction;
import com.jaspersoft.ireport.designer.utils.Misc;
import com.jaspersoft.ireport.designer.widgets.JRDesignElementWidget;
import com.jaspersoft.ireport.jasperserver.RepositoryReportUnit;
import com.jaspersoft.jasperserver.api.metadata.xml.domain.impl.ResourceDescriptor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.dnd.DropTargetDropEvent;
import java.util.List;
import net.sf.jasperreports.engine.JRHyperlink;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignHyperlink;
import net.sf.jasperreports.engine.design.JRDesignHyperlinkParameter;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author gtoffoli
 */
public class CreateDrillDownAction extends PaletteItemAction  {

    @Override
    public void drop(DropTargetDropEvent dtde) {
        
        RepositoryReportUnit ru = (RepositoryReportUnit)getPaletteItem().getData();

        LayerWidget elementsLayer = ((AbstractReportObjectScene)this.getScene()).getElementsLayer();
        
        Point p = getScene().convertViewToScene( dtde.getLocation() );
            
        List<Widget> children = elementsLayer.getChildren();
        for (Widget w : children)
        {
            if (w instanceof JRDesignElementWidget)
            {
                System.out.println("check if hit: " + w + " " + p + " " + w.getLocation() + "  " + w.getBounds());
                Rectangle r = w.getBounds();
                r.x = w.getLocation().x;
                r.y = w.getLocation().y;

                if (r.contains(p))
                {
                    JRDesignElement element = ((JRDesignElementWidget)w).getElement();
                    System.out.println("Element hit at: " + element);
                    if (element instanceof JRHyperlink)
                    {
                        System.out.println("Element JRHyperlink hit at: " + element);
                        JRHyperlink hl = (JRHyperlink)element;
                        JRDesignHyperlink newHl = new JRDesignHyperlink();
                        newHl.setLinkType("ReportExecution");
                    
                        List linkParameters = new java.util.ArrayList();
                        JRDesignHyperlinkParameter p1 = new JRDesignHyperlinkParameter();
                        p1.setName("_report");
                        JRDesignExpression p1_exp = Misc.createExpression("java.lang.String", "\"" + ru.getDescriptor().getUriString() + "\"");
                        p1.setValueExpression(p1_exp);
                        newHl.addHyperlinkParameter(p1);
                    
                        List listChildren = ru.getDescriptor().getChildren();
                        
                        if (!ru.isLoaded() || ru.getChildren().size() == 0)
                        {
                            ResourceDescriptor descriptor = new ResourceDescriptor();
                            descriptor.setWsType( descriptor.TYPE_REPORTUNIT);
                            descriptor.setUriString(ru.getDescriptor().getUriString());
                            try {

                                listChildren = ru.getServer().getWSClient().list(descriptor);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                //Exceptions.printStackTrace(ex);
                            }
                        }
                    
                    
                        // Look for all the Input controls availables....
                        for (int i=0; i<listChildren.size(); ++i)
                        {
                            ResourceDescriptor childRd = (ResourceDescriptor)listChildren.get(i);
                            if (childRd.getWsType().equals( childRd.TYPE_INPUT_CONTROL ))
                            {
                                JRDesignHyperlinkParameter ptmp = new JRDesignHyperlinkParameter();
                                ptmp.setName(childRd.getName());
                                JRDesignExpression ptmp_exp = Misc.createExpression("java.lang.Object", null);
                                ptmp.setValueExpression(ptmp_exp);
                                newHl.addHyperlinkParameter(ptmp);
                            }
                        }
                    
                        ModelUtils.copyHyperlink(newHl, hl);
                    }
                }
            }
        }
        
    }

}
