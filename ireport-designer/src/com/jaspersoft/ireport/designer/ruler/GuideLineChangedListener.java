/*
 * GuideLine.java
 * 
 * Created on Oct 9, 2007, 1:44:27 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.ruler;

import java.util.EventListener;

/**
 *
 * @author gtoffoli
 */
public interface GuideLineChangedListener extends EventListener {

    public void guideLineAdded(GuideLine guideLine);
    public void guideLineRemoved(GuideLine guideLine);
    public void guideLineMoved(GuideLine guideLine);
}
