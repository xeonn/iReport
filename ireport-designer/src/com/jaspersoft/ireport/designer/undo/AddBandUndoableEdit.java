/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.undo;

import com.jaspersoft.ireport.designer.ModelUtils;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import net.sf.jasperreports.engine.JROrigin;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignGroup;
import net.sf.jasperreports.engine.design.JRDesignSection;
import net.sf.jasperreports.engine.design.JasperDesign;

/**
 *
 * @author gtoffoli
 */
public class AddBandUndoableEdit extends AggregatedUndoableEdit {

    private JRDesignBand band = null;
    private JasperDesign jasperDesign = null;
    
    public AddBandUndoableEdit(JRDesignBand band, JasperDesign jasperDesign)
    {
        this.band = band;
        this.jasperDesign = jasperDesign;
    }
    
    @Override
    public void undo() throws CannotUndoException {
        
        super.undo();
        JROrigin origin = getBand().getOrigin();
        if (origin != null)
        {
            switch (origin.getBandType())
            {
                case JROrigin.BACKGROUND: 
                    jasperDesign.setBackground(null); break;
                case JROrigin.TITLE: 
                    jasperDesign.setTitle(null); break;
                case JROrigin.PAGE_HEADER: 
                    jasperDesign.setPageHeader(null); break;
                case JROrigin.COLUMN_HEADER: 
                    jasperDesign.setColumnHeader(null); break;
                case JROrigin.DETAIL:
                {
                    JRDesignSection section = (JRDesignSection)jasperDesign.getDetailSection();
                    section.removeBand(band);
                    break;
                    //jasperDesign.setDetail(null); break;
                }
                case JROrigin.COLUMN_FOOTER:
                    jasperDesign.setColumnFooter(null); break;
                case JROrigin.PAGE_FOOTER: 
                    jasperDesign.setPageFooter(null); break;
                case JROrigin.LAST_PAGE_FOOTER: 
                    jasperDesign.setLastPageFooter(null); break;
                case JROrigin.SUMMARY: 
                    jasperDesign.setSummary(null); break;
                case JROrigin.NO_DATA: 
                    jasperDesign.setNoData(null); break;
                case JROrigin.GROUP_HEADER:
                {
                    JRDesignGroup group = ((JRDesignGroup)jasperDesign.getGroupsMap().get(origin.getGroupName()));
                    JRDesignSection section = (JRDesignSection)group.getGroupHeaderSection();
                    section.removeBand(band);
                    break;
                    //setGroupHeader(null); break;
                }
                case JROrigin.GROUP_FOOTER:
                {
                    JRDesignGroup group = ((JRDesignGroup)jasperDesign.getGroupsMap().get(origin.getGroupName()));
                    JRDesignSection section = (JRDesignSection)group.getGroupFooterSection();
                    section.removeBand(band);
                    break;
                    //((JRDesignGroup)jasperDesign.getGroupsMap().get(origin.getGroupName())).setGroupFooter(null); break;
                }
                    
            }
        }
        
    }

    @Override
    public void redo() throws CannotRedoException {
        
        super.redo();
        
        JROrigin origin = getBand().getOrigin();

        if (origin != null)
        {
            switch (origin.getBandType())
            {
                case JROrigin.BACKGROUND: 
                    jasperDesign.setBackground(band); break;
                case JROrigin.TITLE: 
                    jasperDesign.setTitle(band); break;
                case JROrigin.PAGE_HEADER: 
                    jasperDesign.setPageHeader(band); break;
                case JROrigin.COLUMN_HEADER: 
                    jasperDesign.setColumnHeader(band); break;
                case JROrigin.DETAIL:
                {
                    //jasperDesign.setDetail(band);
                    ((JRDesignSection)jasperDesign.getDetailSection()).addBand(band);
                    break;
                }
                case JROrigin.COLUMN_FOOTER:
                    jasperDesign.setColumnFooter(band); break;
                case JROrigin.PAGE_FOOTER: 
                    jasperDesign.setPageFooter(band); break;
                case JROrigin.LAST_PAGE_FOOTER: 
                    jasperDesign.setLastPageFooter(band); break;
                case JROrigin.SUMMARY: 
                    jasperDesign.setSummary(band); break;
                case JROrigin.NO_DATA: 
                    jasperDesign.setNoData(band); break;
                case JROrigin.GROUP_HEADER:
                {
                    JRDesignGroup group = (JRDesignGroup)jasperDesign.getGroupsMap().get(origin.getGroupName());
                    ((JRDesignSection)group.getGroupHeaderSection()).addBand(band);
                    break;
                }
                case JROrigin.GROUP_FOOTER:
                {
                    JRDesignGroup group = (JRDesignGroup)jasperDesign.getGroupsMap().get(origin.getGroupName());
                    ((JRDesignSection)group.getGroupFooterSection()).addBand(band);
                    break;
                }
            }
        }
    }
    
    @Override
    public String getPresentationName() {
        return "Add Band " + ModelUtils.nameOf(getBand().getOrigin());
    }

    public JRDesignBand getBand() {
        return band;
    }

    public void setBand(JRDesignBand band) {
        this.band = band;
    }

    public JasperDesign getJasperDesign() {
        return jasperDesign;
    }

    public void setJasperDesign(JasperDesign jasperDesign) {
        this.jasperDesign = jasperDesign;
    }
}
