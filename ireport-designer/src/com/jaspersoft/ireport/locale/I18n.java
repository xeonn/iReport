/*
 * This class is a I18n custom-made.  
 * 
 */

package com.jaspersoft.ireport.locale;

import org.openide.util.NbBundle;

/**
 *I18n is used to internationalize all the keys in the project.
 * 
 * @author Daniele Miatello
 * @mail daniele.miatello@tiscali.it
 */
public class I18n {  
    
    //This is the default Resource Boundle for all the keys of the project.
    static final String DEFAULT_PACKAGE = "com/jaspersoft/ireport/locale";
    
    //The method given a key return its Value saved in the Resource Boundle.
    public static String getString(String key){        
        //return java.util.ResourceBundle.getBundle(DEFAULT_PACKAGE).getString(key);
    
        try {
            return NbBundle.getMessage(I18n.class, key);
        } catch (Exception ex) {
            System.out.println("Missing resouce key: " + key);
        }
        return key;
    }

}
