/*
 * Misc.java
 * 
 * Created on 20-ott-2007, 0.18.08
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.utils;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.sheet.Tag;
import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Window;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.text.JTextComponent;
import javax.swing.tree.TreePath;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import org.jdesktop.swingx.JXErrorPane;
import org.jdesktop.swingx.error.ErrorInfo;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataFolder;
import org.openide.loaders.DataObject;
import org.openide.util.Lookup;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author gtoffoli
 */
public class Misc {

    public static String getDataFolderPath(DataFolder targetFolder) {
       if (targetFolder == null) return null;
       FileObject file = targetFolder.getPrimaryFile();
       File f = FileUtil.toFile (file);
       String path = f.getPath();
       return path;
    }
    
    /**
     * If c != null, the ancestor window is return, otherwise this method looks for the main window.
     */
    public static Window getParentWindow(Component c)
    {
        if (c == null)
        {
            return getMainWindow();
        }
        else
        {
            return SwingUtilities.getWindowAncestor(c);
        }
    }
    
    /**
     * Return the NetBeans main window.
     */
    public static Window getMainWindow()
    {
        return getMainFrame();
    }
    
    /**
     * Return the NetBeans main window.
     */
    public static Frame getMainFrame()
    {
        WindowManager w = Lookup.getDefault().lookup( WindowManager.class );
        return (w == null) ? null : w.getMainWindow();
    }
    
    public static String formatString(String s, Object[] params)
    {
        return MessageFormat.format(s, params);
    }

    public static java.awt.Image loadImageFromResources(String filename) {
            
        return loadImageFromResources(filename, Misc.class.getClassLoader());
    }
    
    public static java.awt.Image loadImageFromResources(String filename, ClassLoader cl) {
            
            try {
                    if (!filename.startsWith("/")) filename = "/" + filename;
                    return new javax.swing.ImageIcon( cl.getResource(  filename )).getImage();
            } catch (Exception ex) {
                    System.out.println("Exception loading resource: " +filename);
            }
            return null;
    }
    
    public static java.awt.Image loadImageFromFile(String path) {
                java.io.File file = new java.io.File(path);
                if (file.exists()) {
                        java.awt.Toolkit tk = java.awt.Toolkit.getDefaultToolkit();
                        java.awt.Image img = tk.createImage(path);
                        try {
                                java.awt.MediaTracker mt = new java.awt.MediaTracker( new javax.swing.JPanel() );
                                mt.addImage(img,0);
                                mt.waitForID(0);
                        } catch (Exception ex){
                                return null;
                        }
                        return img;
                }
                return null;
        }

    public static void msg(String string) {
        Logger.global.log(Level.INFO, string );
        java.util.logging.Handler[] handlres = Logger.global.getHandlers();
        for (int i=0; i<handlres.length; ++i) handlres[i].flush();
    }
    
    public static void msg(String string, Throwable t) {
        Logger.global.log(Level.SEVERE, string, t);
        java.util.logging.Handler[] handlres = Logger.global.getHandlers();
        for (int i=0; i<handlres.length; ++i) handlres[i].flush();
    }

    
    static public String readPCDATA(Node textNode) {
        return readPCDATA(textNode,true);
    }

    static public String readPCDATA(Node textNode, boolean trim) {
        NodeList list_child = textNode.getChildNodes();
        for (int ck=0; ck< list_child.getLength(); ck++) {
            Node child_child = list_child.item(ck);

            // --- start solution: if there is another node this should be the PCDATA-node
            Node ns = child_child.getNextSibling();
            if (ns != null)
            child_child = ns;
            // --- end solution

            final short nt = child_child.getNodeType();
            if ((nt == Node.CDATA_SECTION_NODE) || (nt == Node.TEXT_NODE)) {
               if (trim) return (child_child.getNodeValue()).trim();
                return child_child.getNodeValue();
            }
        }
        return "";
    }
    
    static public String nvl(Object obj, String def)
    {
        if (obj == null) return def;
        else return ""+obj;
    }
    
    
    
    /**
         *  Take a string like _it_IT or it_IT or it
         *  and return the right locale
         *  Default return value is Locale.getDefault()
         */
        static public java.util.Locale getLocaleFromString( String localeName )
        {
            return getLocaleFromString(localeName, Locale.getDefault() );
        }

        public static String toHTML(String s) {
                s = Misc.string_replace("&gt;",">",s);
                s = Misc.string_replace("&lt;","<",s);
                s = Misc.string_replace("&nbsp;"," ",s);
                s = Misc.string_replace("&nbsp;&nbsp;&nbsp;&nbsp;","\t",s);
                s = Misc.string_replace("<br>", "\n", s);
                return s;
        }
        
        /**
         *  Take a string like _it_IT or it_IT or it
         *  and return the right locale
         *  
         */
        static public java.util.Locale getLocaleFromString( String localeName, Locale defaultLocale )
	{
		String language = "";
		String country = "";
		String variant = "";
		Locale locale = defaultLocale;

		if (localeName == null || localeName.length() == 0) return locale;
		if (localeName.startsWith("_")) localeName = localeName.substring(1);
		if (localeName.indexOf("_") > 0)
		{
			language = localeName.substring(0,localeName.indexOf("_"));
			localeName = localeName.substring(localeName.indexOf("_")+1);

			if (localeName.indexOf("_") > 0)
			{
				country = localeName.substring(0,localeName.indexOf("_"));
				localeName = localeName.substring(localeName.indexOf("_")+1);

				if (localeName.indexOf("_") > 0)
				{
				    variant = localeName.substring(0,localeName.indexOf("_"));
				    localeName = localeName.substring(localeName.indexOf("_")+1);
				}
				else
				{
				    variant = localeName;
				}
			}
			else
			{
				country = localeName;
			}
		}
		else
		{
			language = localeName;
		}

		locale = new Locale(language,country,variant);

		return locale;
	}
        
        
        /**
         *    Replace s2 with s1 in s3
         **/
        public static String string_replace(String s1, String s2, String s3) {
                String string="";
                string = "";

                if (s2 == null || s3 == null || s2.length() == 0) return s3;

                int pos_i = 0; // posizione corrente.
                int pos_f = 0; // posizione corrente finale

                int len = s2.length();
                while ( (pos_f = s3.indexOf(s2, pos_i)) >= 0) {
                        string += s3.substring(pos_i,pos_f)+s1;
                        //+string.substring(pos+ s2.length());
                        pos_f = pos_i = pos_f + len;

                }

                string += s3.substring(pos_i);
                return string;
        }
        
        
        /**
   * This method select the whole text inside a textarea and set there the focus.
   * It should be used to select a component that contains a wrong expression.
   * In the future other properties of the componenct can be modified
   */
  public static void selectTextAndFocusArea(final JComponent expArea)
  {
      if (expArea == null) return;
      
      if (expArea instanceof JTextComponent)
      {
        ((JTextComponent)expArea).setSelectionStart(0);
        ((JTextComponent)expArea).setSelectionEnd(  ((JTextComponent)expArea).getText().length() );
        ((JTextComponent)expArea).setBorder(new LineBorder(Color.RED.darker(),2));
      }
      else if (expArea instanceof JEditorPane)
      {
        ((JEditorPane)expArea).setSelectionStart(0);
        ((JEditorPane)expArea).setSelectionEnd(  ((JTextComponent)expArea).getText().length() );
        ((JEditorPane)expArea).setBorder(new LineBorder(Color.RED.darker(),2));
      }
      
      expArea.requestFocusInWindow();

  }
  
  
  /**
     * Return the correct field type...
     *
     */
    public static String getJRFieldType(String type)
    {
        if (type == null) return "java.lang.Object";
        if (type.equals("java.lang.Boolean") || type.equals("boolean")) return "java.lang.Boolean";
        if (type.equals("java.lang.Byte") || type.equals("byte")) return "java.lang.Byte";
        if (type.equals("java.lang.Integer") || type.equals("int")) return "java.lang.Integer";
        if (type.equals("java.lang.Long") || type.equals("long")) return "java.lang.Long";
        if (type.equals("java.lang.Double") || type.equals("double")) return "java.lang.Double";
        if (type.equals("java.lang.Float") || type.equals("float")) return "java.lang.Float";
        if (type.equals("java.lang.Short") || type.equals("short")) return "java.lang.Short";
        if (type.startsWith("[")) return "java.lang.Object";
        return type;
    }
    
    
        /**
     * Save the query asking for a file.
     * see saveSQLQuery(String query, Component c)
     */
    public static boolean saveSQLQuery(String query)
    {
         return saveSQLQuery(query, null);
    }
    /**
     * Save the query asking for a file.
     * The optional component is used as parent for the file selection dialog
     * Default is the MainFrame
     */
    public static boolean saveSQLQuery(String query, Component c)
    {
            if (c == null) c = getMainWindow();
            JFileChooser jfc = new JFileChooser();
            jfc.setFileFilter( new javax.swing.filechooser.FileFilter() {
		    public boolean accept(java.io.File file) {
			    String filename = file.getName().toLowerCase();
			    return (filename.endsWith(".sql") || filename.endsWith(".txt") ||file.isDirectory()) ;
		    }
		    public String getDescription() {
			    return "SQL query (*.sql, *.txt)";
		    }
	    });

	    if (jfc.showSaveDialog( Misc.getMainWindow() ) == JFileChooser.APPROVE_OPTION) {

                try {

                    String fileName = jfc.getSelectedFile().getName();
                    if (fileName.indexOf(".") < 0)
                    {
                        fileName += ".sql";
                    }

                    File f = new File( jfc.getSelectedFile().getParent(), fileName);

                    FileWriter fw = new FileWriter(f);
                    fw.write( query );
                    fw.close();

                    return true;
                } catch (Exception ex)
                {
                    JOptionPane.showMessageDialog(getMainWindow(),"Error saving the query: " + ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
	    }

            return false;
    }

    
    /**
     * Load the query asking for a file.
     * The optional component is used as parent for the file selection dialog
     * Default is the MainFrame
     */
    public static String loadSQLQuery()
    {
        return loadSQLQuery(null);
    }
    /**
     * Load the query asking for a file.
     * The optional component is used as parent for the file selection dialog
     * Default is the MainFrame
     */
    public static String loadSQLQuery(Component c)
    {
            if (c == null) c = getMainWindow(); 
            JFileChooser jfc = new JFileChooser();
            jfc.setMultiSelectionEnabled(false);
            jfc.setFileFilter( new javax.swing.filechooser.FileFilter() {
		    public boolean accept(java.io.File file) {
			    String filename = file.getName().toLowerCase();
			    return (filename.endsWith(".sql") || filename.endsWith(".txt") ||file.isDirectory()) ;
		    }
		    public String getDescription() {
			    return "SQL query (*.sql, *.txt)";
		    }
	    });

	    if (jfc.showOpenDialog( getMainWindow()) == JFileChooser.APPROVE_OPTION) {

                try {

                    FileReader fr = new FileReader(jfc.getSelectedFile());
                    StringBuffer sb = new StringBuffer();
                    char[] cbuf = new char[1024];
                    int i = fr.read(cbuf);
                    while (i > 0)
                    {
                        sb.append( cbuf, 0, i);
                        i = fr.read(cbuf);
                    }
                    fr.close();

                    return sb.toString();
                } catch (Exception ex)
                {
                    JOptionPane.showMessageDialog(getMainWindow(),"Error loading the query: " + ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
	    }

            return null;
    }
    
    
    /**
         * Thanx to Jackie Manning j.m@programmer.net for this method!!
         */
        public static String getJdbcTypeClass(java.sql.ResultSetMetaData rsmd, int t ) {
                String cls = "java.lang.Object";

                try {
                    cls = rsmd.getColumnClassName(t);
                    cls =  getJRFieldType(cls);

                } catch (Exception ex)
                {
                    // if getColumnClassName is not supported...
                    try {
                        int type = rsmd.getColumnType(t);
                        switch( type ) {
                                case java.sql.Types.TINYINT:
                                case java.sql.Types.BIT:
                                        cls = "java.lang.Byte";
                                        break;
                                case java.sql.Types.SMALLINT:
                                        cls = "java.lang.Short";
                                        break;
                                case java.sql.Types.INTEGER:
                                        cls = "java.lang.Integer";
                                        break;
                                case java.sql.Types.FLOAT:
                                case java.sql.Types.REAL:
                                case java.sql.Types.DOUBLE:
                                case java.sql.Types.NUMERIC:
                                case java.sql.Types.DECIMAL:
                                        cls = "java.lang.Double";
                                        break;
                                case java.sql.Types.CHAR:
                                case java.sql.Types.VARCHAR:
                                        cls = "java.lang.String";
                                        break;

                                case java.sql.Types.BIGINT:
                                        cls = "java.lang.Long";
                                        break;
                                case java.sql.Types.DATE:
                                        cls = "java.util.Date";
                                        break;
                                case java.sql.Types.TIME:
                                        cls = "java.sql.Time";
                                        break;
                                case java.sql.Types.TIMESTAMP:
                                        cls = "java.sql.Timestamp";
                                        break;
                        }
                    } catch (Exception ex2){
                        ex2.printStackTrace();
                    }
                }
                return cls;
        }
        
        
  /**
   * If treePath is not in the current jTree selection, set it as selected.
   *
   */
  public static void ensurePathIsSelected(TreePath treePath, JTree jTree)
  {
        if (jTree == null || treePath == null) return;
        
        TreePath[] selectedPaths = jTree.getSelectionPaths();
        for (int i=0; selectedPaths != null && i<selectedPaths.length; ++i)
        {
            if (selectedPaths[i].equals( treePath )) return;
        }
        jTree.setSelectionPath(treePath);
  }
   
    public static void updateComboBox(javax.swing.JComboBox comboBox, List newItems) {
            updateComboBox(comboBox,newItems, false);
    }
    
    @SuppressWarnings("unchecked")
    public static void updateComboBox(javax.swing.JComboBox comboBox, List newItems, boolean addNullEntry) {
            Object itemSelected = null;
            if (comboBox.getSelectedIndex() >=0 ) {
                    itemSelected = comboBox.getSelectedItem();
            }

            //comboBox.removeAllItems();

            java.util.Vector items = new java.util.Vector(newItems.size(),1);
            boolean selected = false;
            boolean foundNullItem = false;
            Iterator e = newItems.iterator();
            int selectedIndex = -1;
            int currentelement = 0;
            while (e.hasNext()) {
                    Object item = e.next();
                    items.add(item);
                    if (item == itemSelected) {
                            selectedIndex = currentelement;
                    }
                    if (item.equals("")) {
                            foundNullItem = true;
                    }

                    currentelement++;
            }

            if (addNullEntry) {
                    if (!foundNullItem) items.add(0,""); 
                    if (selectedIndex < 0) selectedIndex = 0;
            }

            comboBox.setModel( new DefaultComboBoxModel(items)  );
            comboBox.setSelectedIndex(selectedIndex);
    }
    
    public static void setComboboxSelectedTagValue(javax.swing.JComboBox comboBox, Object itemValue) {
            for (int i=0; i<comboBox.getItemCount(); ++i)
            {
                Object val = comboBox.getItemAt(i);
                if ( val instanceof Tag)
                {
                    if ( (val == null && itemValue==null) ||
                         ((Tag)val).getValue() == itemValue ||
                         ((Tag)val).getValue().equals(itemValue))
                    {
                        comboBox.setSelectedIndex( i );
                        break;
                    }
                }
            }
        }
    
    /**
         *  Take a filename, strip out the extension and append the new extension
         *  newExtension =   ".xyz"  or "xyz"
         *  If filename is null, ".xyz" is returned
         */
        public static String changeFileExtension(String filename, String newExtension ) {
                if (!newExtension.startsWith(".")) newExtension = "."+newExtension;
                if (filename == null || filename.length()==0 ) {
                        return newExtension;
                }

                int index = filename.lastIndexOf(".");
                if (index >= 0) {
                        filename = filename.substring(0,index);
                }
                return filename += newExtension;
        }
        
        public static String getClassPath() {
                String cp = System.getProperty("java.class.path");
                if (IReportManager.getInstance() != null)
                {
                    List<String> cp_v = IReportManager.getInstance().getClasspath();
                    for (String s : cp_v)
                    {
                        cp += File.pathSeparator + s;
                    }
                }
                return cp;
        }
        
        
    public static String getExpressionText(JRExpression exp)
    {
        if (exp == null) return "";
        if (exp.getText() == null) return "";
        return exp.getText();
    }
    
    public static JRDesignExpression createExpression(String className, String text)
    {
        if (text == null || text.trim().length() == 0) return null;
        JRDesignExpression exp = new JRDesignExpression();
        exp.setValueClassName(className != null ? className : "java.lang.Object");
        exp.setText(text);
        return exp;
    }
    
    public static  void  showErrorMessage(String errorMsg, String title, Throwable theException)
    {
        
        final JXErrorPane pane = new JXErrorPane();
        //pane.setLocale(I18n.getCurrentLocale());
       
        String[] lines = errorMsg.split("\r\n|\n|\r");

        String shortMessage = errorMsg;
        if (lines.length > 4)
        {
            shortMessage = "";
            for (int i=0; i<4; ++i)
            {
                shortMessage += lines[i]+"\n";
            }
            shortMessage = shortMessage.trim() + "\n...";
        }
      
        final ErrorInfo ei = new ErrorInfo(title,
                 shortMessage,
                 null, //"<html><pre>" + errorMsg + "</pre>"
                 null,
                 theException,
                 null,
                 null);
         
        
        /*
        
        
        final String fErrorMsg = errorMsg;
        */
        Runnable r = new Runnable() {
                public void run() {
                   // JOptionPane.showMessageDialog(MainFrame.getMainInstance(),fErrorMsg,title,JOptionPane.ERROR_MESSAGE);
                
                    pane.setErrorInfo(ei);
                   JXErrorPane.showDialog(Misc.getMainWindow(), pane);
                }
            };

        if (!SwingUtilities.isEventDispatchThread())
        {
            try {
                SwingUtilities.invokeAndWait( r );
            } catch (InvocationTargetException ex) {
                ex.printStackTrace();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        else
        {
                r.run();
        }
    }
    
    
    /**
	 * Try to find a directory to open the chooser open.
	 * If there is a file among selected nodes (e.g. open editor windows),
	 * use that directory; else just stick to the user's home directory.
	 */
	public static File findStartingDirectory() {
		org.openide.nodes.Node[] nodes = TopComponent.getRegistry().getActivatedNodes();
		for (int i = 0; i < nodes.length; i++) {
			DataObject d = (DataObject) nodes[i].getCookie(DataObject.class);
			if (d != null) {
				File f = FileUtil.toFile(d.getPrimaryFile());
				if (f != null) {
					if (f.isFile()) {
						f = f.getParentFile();
					}
					return f;
				}
			}
		}
                
                String dir = IReportManager.getPreferences().get( IReportManager.CURRENT_DIRECTORY, null);
                if (dir != null) return new File(dir);
		// Backup:
		return new File(System.getProperty("user.home"));
	}
    
}
