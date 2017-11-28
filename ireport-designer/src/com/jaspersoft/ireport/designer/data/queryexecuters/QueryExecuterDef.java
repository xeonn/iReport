/*
 * Copyright (C) 2005 - 2007 JasperSoft Corporation.  All rights reserved. 
 * http://www.jaspersoft.com.
 *
 * Unless you have purchased a commercial license agreement from JasperSoft,
 * the following license terms apply:
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as published by
 * the Free Software Foundation.
 *
 * This program is distributed WITHOUT ANY WARRANTY; and without the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see http://www.gnu.org/licenses/gpl.txt
 * or write to:
 *
 * Free Software Foundation, Inc.,
 * 59 Temple Place - Suite 330,
 * Boston, MA  USA  02111-1307
 *
 *
 *
 *
 * QueryExecuterDef.java
 * 
 * Created on 10 marzo 2004, 18.08
 *
 */

package com.jaspersoft.ireport.designer.data.queryexecuters;

/**
 *
 * @author  Administrator
 */
public class QueryExecuterDef {
    
    private String language="";
    private String className="";
    private String fieldsProvider="";

    private boolean builtin = false;

    public QueryExecuterDef(String language, String className, String fieldsProvider) {

            this(language, className, fieldsProvider, false);
    }
    /** Creates a new instance of JRProperty */
    public QueryExecuterDef(String language, String className, String fieldsProvider, boolean isBuiltin) {
        this.language = language;
        this.className = className;
        this.fieldsProvider = fieldsProvider;
        this.builtin = isBuiltin;
    }
    
    /** Creates a new instance of JRProperty */
    public QueryExecuterDef(String language, String className) {
        this(language , className, "");
    }
    
    /** Creates a new instance of JRProperty */
    public QueryExecuterDef(){
    }
   
    
    @Override
    public String toString()
    {
        return (getLanguage() == null) ? "" : getLanguage();
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getFieldsProvider() {
        return fieldsProvider;
    }

    public void setFieldsProvider(String fieldsProvider) {
        this.fieldsProvider = fieldsProvider;
    }

    /**
     * @return the builtin
     */
    public boolean isBuiltin() {
        return builtin;
    }

    /**
     * @param builtin the builtin to set
     */
    public void setBuiltin(boolean builtin) {
        this.builtin = builtin;
    }

    public QueryExecuterDef cloneMe()
    {
        QueryExecuterDef copy = new QueryExecuterDef();
        copy.setLanguage(this.language);
        copy.setBuiltin(builtin);
        copy.setClassName(className);
        copy.setFieldsProvider(fieldsProvider);

        return copy;
    }
}
