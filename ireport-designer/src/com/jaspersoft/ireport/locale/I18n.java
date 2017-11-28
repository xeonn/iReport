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

    private static void printMissingResourceMessage(String key, Exception ex)
    {
        System.out.println("Missing resouce key: " + key);
    }

    /**
     *  Finds a localized and/or branded string in the iReport resource bundle.
     *  This call never throws an exception, but a warning message is printed when
     *  a requested resource is not found.
     *
     * @param key name of the resource to look for
     * @return the localizaed string or the key if no resource is found
     */
    //The method given a key return its Value saved in the Resource Boundle.
    public static String getString(String key){        
        return getString(I18n.class, key);
    }

    /**
     *  Finds a localized and/or branded string in the iReport resource bundle
     *  and formats the message by passing requested parameters.
     *  This call never throws an exception, but a warning message is printed when
     *  a requested resource is not found.
     *
     * @param key name of the resource to look for
     * @param params
     * @return
     */
    public static String getString(String key, Object[] params){
        return getString(I18n.class, key, params);
    }

    /**
     * Finds a localized and/or branded string in the iReport resource bundle
     * and formats the message by passing requested parameters.
     *
     *  This call never throws an exception, but a warning message is printed when
     *  a requested resource is not found.
     *
     * @param key name of the resource to look for
     * @param param1 the argument to use when formatting the message
     * @return
     */
    public static String getString(String key, Object param){
        return getString(I18n.class, key, param);
    }

    /**
     * Finds a localized and/or branded string in the iReport resource bundle
     * and formats the message by passing requested parameters.
     *  This call never throws an exception, but a warning message is printed when
     *  a requested resource is not found.
     *
     * @param key name of the resource to look for
     * @param param1 the argument to use when formatting the message
     * @param param2 the second argument to use for formatting
     * @return
     */
    public static String getString(String key, Object param1, Object param2){
        return getString(I18n.class, key, param1, param2);
    }

    /**
     * Finds a localized and/or branded string in the iReport resource bundle
     * and formats the message by passing requested parameters.
     *  This call never throws an exception, but a warning message is printed when
     *  a requested resource is not found.
     *
     * @param key name of the resource to look for
     * @param param1 the argument to use when formatting the message
     * @param param2 the second argument to use for formatting
     * @param param3 the third argument to use for formatting
     * @return
     */
    public static String getString(String key, Object param1, Object param2, Object param3){
        //return java.util.ResourceBundle.getBundle(DEFAULT_PACKAGE).getString(key);
        return getString(I18n.class, key, param1, param2, param3);
    }



    /**
     *  Finds a localized and/or branded string in the iReport resource bundle.
     *  This call never throws an exception, but a warning message is printed when
     *  a requested resource is not found.
     *
     * @param clazz - the class to use to locate the bundle
     * @param key name of the resource to look for
     * @return the localizaed string or the key if no resource is found
     */
    //The method given a key return its Value saved in the Resource Boundle.
    public static String getString(Class clazz, String key){
        //return java.util.ResourceBundle.getBundle(DEFAULT_PACKAGE).getString(key);

        try {
            return NbBundle.getMessage(clazz, key);
        } catch (Exception ex) {
            printMissingResourceMessage(key, ex);
        }
        return key;
    }

    /**
     *  Finds a localized and/or branded string in the iReport resource bundle
     *  and formats the message by passing requested parameters.
     *  This call never throws an exception, but a warning message is printed when
     *  a requested resource is not found.
     *
     * @param clazz - the class to use to locate the bundle
     * @param key name of the resource to look for
     * @param params
     * @return
     */
    public static String getString(Class clazz, String key, Object[] params){
        //return java.util.ResourceBundle.getBundle(DEFAULT_PACKAGE).getString(key);

        try {
            return NbBundle.getMessage(clazz, key, params);
        } catch (Exception ex) {
            printMissingResourceMessage(key, ex);
        }
        return key;
    }

    /**
     * Finds a localized and/or branded string in the iReport resource bundle
     * and formats the message by passing requested parameters.
     *
     *  This call never throws an exception, but a warning message is printed when
     *  a requested resource is not found.
     *
     * @param clazz - the class to use to locate the bundle
     * @param key name of the resource to look for
     * @param param1 the argument to use when formatting the message
     * @return
     */
    public static String getString(Class clazz, String key, Object param){
        //return java.util.ResourceBundle.getBundle(DEFAULT_PACKAGE).getString(key);

        try {
            return NbBundle.getMessage(clazz, key, param);
        } catch (Exception ex) {
            printMissingResourceMessage(key, ex);
        }
        return key;
    }

    /**
     * Finds a localized and/or branded string in the iReport resource bundle
     * and formats the message by passing requested parameters.
     *  This call never throws an exception, but a warning message is printed when
     *  a requested resource is not found.
     *
     * @param clazz - the class to use to locate the bundle
     * @param key name of the resource to look for
     * @param param1 the argument to use when formatting the message
     * @param param2 the second argument to use for formatting
     * @return
     */
    public static String getString(Class clazz, String key, Object param1, Object param2){
        //return java.util.ResourceBundle.getBundle(DEFAULT_PACKAGE).getString(key);

        try {
            return NbBundle.getMessage(clazz, key, param1, param2);
        } catch (Exception ex) {
            printMissingResourceMessage(key, ex);
        }
        return key;
    }

    /**
     * Finds a localized and/or branded string in the iReport resource bundle
     * and formats the message by passing requested parameters.
     *  This call never throws an exception, but a warning message is printed when
     *  a requested resource is not found.
     *
     * @param clazz - the class to use to locate the bundle
     * @param key name of the resource to look for
     * @param param1 the argument to use when formatting the message
     * @param param2 the second argument to use for formatting
     * @param param3 the third argument to use for formatting
     * @return
     */
    public static String getString(Class clazz, String key, Object param1, Object param2, Object param3){
        //return java.util.ResourceBundle.getBundle(DEFAULT_PACKAGE).getString(key);

        try {
            return NbBundle.getMessage(clazz, key, param1, param2, param3);
        } catch (Exception ex) {
            printMissingResourceMessage(key, ex);
        }
        return key;
    }

}
