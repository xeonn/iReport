/*
 * GuideLine.java
 * 
 * Created on Oct 9, 2007, 1:44:27 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.ruler;

/**
 *
 * @author gtoffoli
 */
public class GuideLine {
    private int position = -1;
    private boolean vertical = false;

    public boolean isVertical() {
        return vertical;
    }

    public void setVertical(boolean vertical) {
        this.vertical = vertical;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
    
    public GuideLine(int position)
    {
        this(position,false);
    }
    
    public GuideLine(int position, boolean vertical)
    {
        this.position = position;
        this.vertical = vertical;
    }
}
