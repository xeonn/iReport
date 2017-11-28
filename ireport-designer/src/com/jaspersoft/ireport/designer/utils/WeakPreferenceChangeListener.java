/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.utils;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;

/**
 *
 * @author gtoffoli
 */
public class WeakPreferenceChangeListener implements PreferenceChangeListener{
    WeakReference listenerRef;
    Object src;

    public WeakPreferenceChangeListener(PreferenceChangeListener listener, Object src){
        listenerRef = new WeakReference(listener);
        this.src = src;
    }

    private void removeListener(){
        try{
            Method method = src.getClass().getMethod("removePreferenceChangeListener"
                    , new Class[] {PreferenceChangeListener.class});
            method.invoke(src, new Object[]{ this });
        } catch(Exception e){
            e.printStackTrace(); 
        }
    }

    public void preferenceChange(PreferenceChangeEvent evt) {
        PreferenceChangeListener listener = (PreferenceChangeListener)listenerRef.get();
        if(listener==null){
            removeListener();
        }else
            listener.preferenceChange(evt);
    }
}
