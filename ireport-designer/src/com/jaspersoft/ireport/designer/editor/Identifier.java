/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.editor;

/**
 *
 * @author gtoffoli
 */
public class Identifier {
    
    public static final int ATTRIBUTE = 1;
    public static final int METHOD = 2;
    public static final int CLASS = 3;
    public static final int PACKAGE_OR_CLASS = 4;
    public static final int UNDEFINED = 5;
    public static final int REPORT_OBJECT = 6;
    private String    name = "";
    private int type = UNDEFINED;

    public Identifier(String name, int type)
    {
        this.name = name;
        this.type = type;
    }

    public Identifier(String name)
    {
        this.name = name;
        this.type = UNDEFINED;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
    }

}
