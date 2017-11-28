/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.welcome;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import javax.swing.Action;
import javax.swing.Icon;
import org.openide.awt.StatusDisplayer;

/**
 *
 * @author gtoffoli
 */
public class ActionButton extends LinkButton {

    private Action action;
    private String urlString;
    private boolean visited = false;

    public ActionButton( Action a, boolean showBullet, String urlString ) {
        super( a.getValue( Action.NAME ).toString(), showBullet );
        this.action = a;
        this.urlString = urlString;
        Object icon = a.getValue( Action.SMALL_ICON );
        if( null != icon && icon instanceof Icon )
            setIcon( (Icon)icon );
        Object tooltip = a.getValue( Action.SHORT_DESCRIPTION );
        if( null != tooltip )
            setToolTipText( tooltip.toString() );
    }

    public void actionPerformed(ActionEvent e) {
        if( null != action ) {
            action.actionPerformed( e );
        }
        if( null != urlString )
            visited = true;
    }

    protected void onMouseExited(MouseEvent e) {
        if( null != urlString ) {
            StatusDisplayer.getDefault().setStatusText( "" ); //NOI18N
        }
    }

    protected void onMouseEntered(MouseEvent e) {
        if( null != urlString ) {
            StatusDisplayer.getDefault().setStatusText( urlString );
        }
    }

    protected boolean isVisited() {
        return visited;
    }

    private static final long serialVersionUID = 1L;
}

