/*
 * ValidationUtils.java
 * 
 * Created on Sep 5, 2007, 10:43:36 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.jasperserver.ui;

import com.jaspersoft.ireport.jasperserver.JasperServerManager;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author gtoffoli
 */
public class ValidationUtils {

    	public static final int MAX_LENGTH_NAME = 100;
	public static final int MAX_LENGTH_LABEL = 100;
	public static final int MAX_LENGTH_DESC = 100;
        private static final Pattern PATTERN_NAME = Pattern.compile("(\\p{L}|\\p{N}|(\\_)|(\\.)|(\\-)|[;@])+");
        
        
        public static void validateName(String name) throws Exception
        {
            if (name==null || name.length() == 0) 
                throw new Exception(JasperServerManager.getString("resource.name.isEmpty","The name can not be empty"));
            Matcher mat = PATTERN_NAME.matcher(name.trim());
            if (!mat.matches()) 
                    throw new Exception(JasperServerManager.getString("resource.name.invalidCharacters","The name contains invalid characters"));
            if (name.trim().length() > MAX_LENGTH_NAME) throw new Exception(JasperServerManager.getFormattedString("resource.name.tooLong","The name can not be longer than {0,integer} characters",new Object[]{new Integer(MAX_LENGTH_NAME)}));
        }
        
        public static void validateLabel(String name) throws Exception
        {
            if (name==null || name.length() == 0) throw new Exception(JasperServerManager.getString("resource.label.isEmpty","The label can not be empty"));
            if (name.trim().length() > MAX_LENGTH_LABEL) throw new Exception(JasperServerManager.getFormattedString("resource.label.tooLong","The label can not be longer than {0,integer} characters",new Object[]{new Integer(MAX_LENGTH_LABEL)}));
        }
        
        public static void validateDesc(String name) throws Exception
        {
            if (name != null && name.trim().length() > MAX_LENGTH_DESC) throw new Exception(JasperServerManager.getFormattedString("resource.desc.tooLong","The description can not be longer than characters",new Object[]{new Integer(MAX_LENGTH_DESC)}));
        }
        
}
