/*
 * EditorContext.java
 * 
 * Created on Oct 11, 2007, 4:25:53 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.editor;

import java.util.ArrayList;
import java.util.List;
import javax.swing.text.Document;
import org.netbeans.api.languages.CompletionItem;
import org.netbeans.api.languages.Context;
import org.netbeans.api.lexer.Token;
import org.netbeans.api.lexer.TokenSequence;

/**
 *
 * @author gtoffoli
 */
public class JRSyntaxSupport {

    public static List<CompletionItem> completionItems (Context context) {
 
        List<CompletionItem> result = new ArrayList<CompletionItem> ();
        
        ExpressionContext editorContext = ExpressionContext.getGlobalContext();
            
            
        Document doc = context.getDocument();
        if (editorContext.activeEditor != null && context.getDocument() == editorContext.activeEditor.getDocument())
        {
            
            // TODO: Code completion must ne finished...
                int pos = ExpressionContext.activeEditor.getCaretPosition();
                String text = editorContext.activeEditor.getText();
                if (pos > 0 && text.charAt(pos-1) == '$') {
                    CompletionItem ci = CompletionItem.create("Token starting width $...");
                    result.add(ci);
                }
                
            
        }
        /*
            // Look if I'm starting a field...
        TokenSequence ts = context.getTokenSequence();
        
        
        System.out.println("Current token: " + ts.token().toString());
        
        Token t = previousToken(ts);
        System.out.println("Previous token: " + t);
        if (t == null) return result;
        
        String s = t.text().toString();
        System.out.println("String s is " + s);
        
        if (s.equals("."))
        {
            t = previousToken(ts);
            s = t.text().toString();
            // Try to identify the class....
            CompletionItem ci = CompletionItem.create( "Try to identify the token...."+s );
            result.add(ci);
        }
        else if (s.startsWith("$"))
        {
            CompletionItem ci = CompletionItem.create( "Token starting width $..." + s);
            result.add(ci);
        }
*/
        if (editorContext == null) return result;
        
        return result;
    }
    
    private static Token previousToken (TokenSequence ts) {
        do {
            if (!ts.movePrevious ()) return ts.token ();
        } while (
            ts.token ().id ().name ().endsWith ("whitespace") ||
            ts.token ().id ().name ().endsWith ("comment")
        );
        return ts.token ();
    }
    
}