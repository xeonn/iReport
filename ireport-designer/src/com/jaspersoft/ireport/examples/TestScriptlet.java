/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.examples;

import net.sf.jasperreports.engine.JRAbstractScriptlet;
import net.sf.jasperreports.engine.JRScriptletException;

/**
 *
 * @author gtoffoli
 */
public class TestScriptlet extends JRAbstractScriptlet {

    @Override
    public void beforeReportInit() throws JRScriptletException {
       this.setVariableValue("testVariable", "beforeReportInit");
    }

    @Override
    public void afterReportInit() throws JRScriptletException {
        this.setVariableValue("testVariable", "afterReportInit");
    }

    @Override
    public void beforePageInit() throws JRScriptletException {
        this.setVariableValue("testVariable", "beforePageInit");
    }

    @Override
    public void afterPageInit() throws JRScriptletException {
        this.setVariableValue("testVariable", "afterPageInit");
    }

    @Override
    public void beforeColumnInit() throws JRScriptletException {
        this.setVariableValue("testVariable", "beforeColumnInit");
    }

    @Override
    public void afterColumnInit() throws JRScriptletException {
        this.setVariableValue("testVariable", "afterColumnInit");
    }

    @Override
    public void beforeGroupInit(String arg0) throws JRScriptletException {
        this.setVariableValue("testVariable", "beforeGroupInit");
    }

    @Override
    public void afterGroupInit(String arg0) throws JRScriptletException {
        this.setVariableValue("testVariable", "afterGroupInit");
    }

    @Override
    public void beforeDetailEval() throws JRScriptletException {
        this.setVariableValue("testVariable", "beforeDetailEval");
    }

    @Override
    public void afterDetailEval() throws JRScriptletException {
        this.setVariableValue("testVariable", "afterDetailEval");
    }

}
