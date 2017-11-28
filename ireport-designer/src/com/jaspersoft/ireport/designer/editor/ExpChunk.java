/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.editor;

import org.netbeans.api.lexer.Token;

/**
 *
 * @author gtoffoli
 */
public class ExpChunk {

    Token token = null;
    int startPos = 0;
    int endPos = 0;

    public ExpChunk(Token token, int pos)
    {
        this.token = token;
        this.startPos=pos;
        this.endPos = startPos + token.length();
    }

}
