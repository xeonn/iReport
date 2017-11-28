/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.widgets.visitor;

import java.awt.Graphics2D;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRFrame;
import net.sf.jasperreports.engine.JRPrintFrame;
import net.sf.jasperreports.engine.JRReport;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.convert.ConvertVisitor;
import net.sf.jasperreports.engine.convert.ReportConverter;
import net.sf.jasperreports.engine.export.TextRenderer;
import net.sf.jasperreports.engine.export.draw.DrawVisitor;
import net.sf.jasperreports.engine.export.draw.FrameDrawer;

/**
 *
 * @author gtoffoli
 */
public class ConfigurableDrawVisitor extends DrawVisitor {

    private ConvertVisitor convertVisitor = null;
    private ReportConverter reportConverter = null;
    private FrameDrawer frameDrawer = null;
    private Graphics2D grx = null;

    /**
	 *
	 */
	public ConfigurableDrawVisitor(JRReport report, Graphics2D grx)
	{
		this(new ReportConverter(report, true, true), grx);
	}

	/**
	 *
	 */
	public ConfigurableDrawVisitor(ReportConverter reportConverter, Graphics2D grx)
	{
		super( reportConverter, grx);
        //this.convertVisitor = new ConvertVisitor(reportConverter);
        this.grx = grx;
        this.reportConverter = reportConverter;

	}

    @Override
    public void visitFrame(JRFrame frame) {

       

        try
		{
            if (convertVisitor == null)
            {
                convertVisitor = new ConvertVisitor(reportConverter);
            }

            if (frameDrawer == null)
            {
                frameDrawer = new FrameDrawer(null, new TextRenderer(false));
                frameDrawer.setClip(true);
            }
            JRPrintFrame element = (JRPrintFrame)convertVisitor.getVisitPrintElement(frame);
            element.getElements().clear();
			frameDrawer.draw(
				grx,
				element,
				-frame.getX(),
				-frame.getY()
				);
		}
        catch (JRException e)
		{
			throw new JRRuntimeException(e);
		}


    }


    /**
	 *
	 */
    @Override
	public void setGraphics2D(Graphics2D grx)
	{
		this.grx = grx;
        super.setGraphics2D(grx);
	}



}
