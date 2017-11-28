/*
 * DesignerManager.java
 * 
 * Created on 19-ott-2007, 23.47.22
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer;

import com.jaspersoft.ireport.designer.connection.JREmptyDatasourceConnection;
import com.jaspersoft.ireport.designer.fonts.TTFFontsLoader;
import com.jaspersoft.ireport.designer.data.queryexecuters.QueryExecuterDef;
import com.jaspersoft.ireport.designer.fonts.TTFFontsLoaderMonitor;
import com.jaspersoft.ireport.designer.outline.OutlineTopComponent;
import com.jaspersoft.ireport.designer.outline.nodes.ElementNode;
import com.jaspersoft.ireport.designer.undo.AggregatedUndoableEdit;
import com.jaspersoft.ireport.designer.utils.Misc;
import com.jaspersoft.ireport.designer.widgets.JRDesignElementWidget;
import com.jaspersoft.ireport.locale.I18n;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import javax.swing.event.UndoableEditEvent;
import javax.swing.undo.UndoableEdit;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.component.Component;
import net.sf.jasperreports.engine.design.JRDesignChartDataset;
import net.sf.jasperreports.engine.design.JRDesignComponentElement;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.FileResolver;
import net.sf.jasperreports.engine.util.JRProperties;
import org.apache.xerces.parsers.DOMParser;
import org.netbeans.api.db.explorer.JDBCDriver;
import org.netbeans.api.db.explorer.JDBCDriverManager;
import org.openide.awt.StatusDisplayer;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.Repository;
import org.openide.loaders.DataFolder;
import org.openide.loaders.DataObject;
import org.openide.modules.InstalledFileLocator;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.NbPreferences;
import org.openide.util.RequestProcessor;
import org.openide.util.lookup.Lookups;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author gtoffoli
 */
public class IReportManager {
    
    public static final String PROPERTY_CONNECTIONS = "PROPERTY_CONNECTIONS";
    public static final String PROPERTY_DEFAULT_CONNECTION = "PROPERTY_DEFAULT_CONNECTION";
    public static final String PROPERTY_SHOW_CELL_NAMES = "PROPERTY_SHOW_CELL_NAMES";
    public static final String PROPERTY_SHOW_BAND_NAMES = "PROPERTY_SHOW_BAND_NAMES";
    
    
    public static final String CURRENT_DIRECTORY = "CURRENT_DIRECTORY";
    public static final String IREPORT_CLASSPATH = "IREPORT_CLASSPATH";
    public static final String IREPORT_RELODABLE_CLASSPATH = "IREPORT_RELODABLE_CLASSPATH";
    public static final String IREPORT_FONTPATH = "IREPORT_FONTPATH";
    public static final String DEFAULT_CONNECTION_NAME = "DEFAULT_CONNECTION_NAME";
    
    public static final String USE_AUTO_REGISTER_FIELDS = "UseAutoRegiesterFields";
    public static final String TEMPLATE_PATH = "TEMPLATE_PATH";
    
    
    private static ReportClassLoader reportClassLoader = null;
    private static IReportManager mainInstance = null;

    public static ElementNode getComponentNode(JasperDesign jd, JRDesignComponentElement componentElement, Lookup lkp) {

        ElementNodeFactory factory = getElementNodeFactory(componentElement.getComponent().getClass().getName());
        if (factory != null)
        {
            return factory.createElementNode(jd, componentElement, lkp);
        }
        return null;
    }

    public static JRDesignElementWidget getComponentWidget(AbstractReportObjectScene scene, JRDesignComponentElement componentElement) {

        ElementNodeFactory factory = getElementNodeFactory(componentElement.getComponent().getClass().getName());
        if (factory != null)
        {
            return factory.createElementWidget(scene, componentElement);
        }
        return null;
    }

    public static HashMap<String, ElementNodeFactory> elementNodeFactories = new HashMap<String, ElementNodeFactory>();
    public static ElementNodeFactory getElementNodeFactory(String componentClassName)
    {
        if (elementNodeFactories.containsKey(componentClassName))
        {
            return elementNodeFactories.get(componentClassName);
        }

        FileObject nodesFileObject = Repository.getDefault().getDefaultFileSystem().getRoot().getFileObject("ireport/components/nodes");
        if (nodesFileObject == null) return null;
        DataFolder nodesDataFolder = DataFolder.findFolder(nodesFileObject);
        if (nodesDataFolder == null) return null;

        Enumeration<DataObject> enObj = nodesDataFolder.children();
        while (enObj.hasMoreElements())
        {
            DataObject dataObject = enObj.nextElement();
            FileObject fileObject = dataObject.getPrimaryFile();
            String name = dataObject.getName();
            boolean instance = false;
            System.out.println("Found: " + name + " ." + fileObject.getExt());
            System.out.flush();
            if (fileObject.getExt().equals("instance"))
            {
                instance = true;
            }
            name = name.replace('-', '.');

            try {
                if (name.equals(componentClassName))
                {
                    ElementNodeFactory enf = null;
                    if (instance)
                    {
                        Lookup lookup = Lookups.forPath("ireport/components/nodes"); // NOI18N
                        String elementFactoryClassName = (String)fileObject.getAttribute("instanceClass");
                        Collection<? extends ElementNodeFactory> elementNodeFactories = lookup.lookupAll(ElementNodeFactory.class);
                        Iterator<? extends ElementNodeFactory> it = elementNodeFactories.iterator();
                        while (it.hasNext ()) {

                            ElementNodeFactory tmp_enf = it.next();
                            if (tmp_enf.getClass().getName().equals(elementFactoryClassName))
                            {
                                enf = tmp_enf;
                                break;
                            }
                        }
                    }
                    else
                    {
                        String elementFactoryClassName = (String)fileObject.getAttribute("elementFactory");
                        enf =((ElementNodeFactory)Class.forName(elementFactoryClassName).newInstance());

                    }
                    elementNodeFactories.put(componentClassName, enf);
                    return enf;
                }
            } catch (Throwable ex)
            {
                System.out.println("Unable to find element node Factory for the component class: " + name);
                ex.printStackTrace();
            }
        }
        return null;
    }


    /**
     * Look into the virtual file system for element decorators...
     *
     * @param element
     * @return
     */
    public static List<ElementDecorator> getElementDecorators(JRDesignElement element) {
        
        List<ElementDecorator> decorators = new ArrayList<ElementDecorator>();
        
        FileObject decoratorsFileObject = Repository.getDefault().getDefaultFileSystem().getRoot().getFileObject("ireport/decorators/elements");
        if (decoratorsFileObject == null) return decorators;
        DataFolder decoratorsDataFolder = DataFolder.findFolder(decoratorsFileObject);
        if (decoratorsDataFolder == null) return decorators;

        Enumeration<DataObject> enObj = decoratorsDataFolder.children();
        while (enObj.hasMoreElements())
        {
            DataObject dataObject = enObj.nextElement();
            String name = dataObject.getName();
            name = name.replace('-', '.');
            try {
                ElementDecorator decorator = (ElementDecorator) Class.forName(name).newInstance();
                if (decorator.appliesTo(element))
                {
                    decorators.add(decorator);
                }
            } catch (Throwable ex)
            {
                System.out.println("Unable to find element decorator class: " + name);
            }
        }
        return decorators;
    }
    
    private java.util.ArrayList<IReportConnection> connections = null;
    private java.util.ArrayList<QueryExecuterDef> queryExecuters = null;
    private java.util.List<IRFont> fonts = null;
    private java.util.List<FileResolver> fileResolvers = null;
    private java.util.HashMap parameterValues = new java.util.HashMap();

    public List<IRFont> getFonts() {
        return fonts;
    }

    public void setFonts(List<IRFont> fonts) {
        this.fonts = fonts;
    }
    private java.util.List connectionImplementations = null;
    private IReportConnection defaultConnection = null;
    private PropertyChangeSupport propertyChangeSupport = null;

    public PropertyChangeSupport getPropertyChangeSupport() {
        return propertyChangeSupport;
    }
            
    private IReportManager()
    {
        /*
        System.out.println("Drivers: ");
        JDBCDriver[] drivers = JDBCDriverManager.getDefault().getDrivers();
        for (int j = 0; j < drivers.length; j++) {
            System.out.println(drivers[j].getDisplayName() + " " + drivers[j].getClassName());
        }
        */
        
        propertyChangeSupport = new PropertyChangeSupport(this);
        
        
    }
    
    /**
     * This method initializa the manager. This code is not in the constructor
     * because it need to be executed using the ReportClassLoader, that requires an
     * instance of IReportManager.
     * 
     */
    private void initialize()
    {
        try {
            net.sf.jasperreports.engine.util.JRProperties.setProperty("net.sf.jasperreports.query.executer.factory.xmla-mdx",
                    "net.sf.jasperreports.engine.query.JRXmlaQueryExecuterFactory");
        } catch (Exception ex)
        { 
            System.out.println(I18n.getString("IReportManager.Error.WarningJRXmla") +ex.getMessage());
            System.out.flush();
        }
        
        try {
            net.sf.jasperreports.engine.util.JRProperties.setProperty("net.sf.jasperreports.xpath.executer.factory",
                    "net.sf.jasperreports.engine.util.xml.JaxenXPathExecuterFactory");
        } catch (Exception ex)
        { 
            System.out.println(I18n.getString("IReportManager.Error.ErrorJaxenXP") +ex.getMessage());
            System.out.flush();
        }
        
        
    
        // Loading fonts...
        RequestProcessor.getDefault().post(new Runnable()
        {
            public void run()
            {
                //((ReportClassLoader)getReportClassLoader()).rescanAdditionalClasspath();
                Thread.currentThread().setContextClassLoader( getReportClassLoader() );
                // Stuff to load the fonts...
                setFonts( TTFFontsLoader.loadTTFFonts(new TTFFontsLoaderMonitor() {

                    public void fontsLoadingStarted() {
                    }

                    public void fontsLoadingStatusUpdated(String statusMsg) {
                        StatusDisplayer.getDefault().setStatusText(statusMsg);
                    }

                    public void fontsLoadingFinished() {
                        StatusDisplayer.getDefault().setStatusText("");
                    }
                }) );
                
            }
        });
        
        /*
         System.out.println("Gif writers");
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("gif");
        while (writers.hasNext())
        {
            ImageWriter w=writers.next();
            System.out.println("For gif: " + w);
        }
        System.out.flush();
         */

        createPaletteItem();
    }
    
    public static IReportManager getInstance()
    {
        if (mainInstance == null)
        {
            mainInstance = new IReportManager();
            Thread.currentThread().setContextClassLoader(getReportClassLoader());
            mainInstance.initialize();
        }
        return mainInstance;
    }
    
    /**
     * Return the available connections. 
     */
    public java.util.List<IReportConnection> getConnections()
    {
        if (connections == null)
        {
            connections = new java.util.ArrayList<IReportConnection>();
            // Load connections from preferences...
            for (int i=0; ; ++i)
            {
                String s = getPreferences().get("connection." + i, null);
                if (s == null) break;
                IReportConnection con = loadConnection(s);
                if (con != null) connections.add(con);
            }
            
            if (connections.size() == 0)
            {
                JREmptyDatasourceConnection c = new JREmptyDatasourceConnection();
                c.setName(I18n.getString("IReportManager.Type.EmptyDatasource"));
                connections.add(c);
                saveiReportConfiguration();
            }
        }
        return connections;
    }
    
    /**
     * Please use this method to add a connection.
     * It will fire an PROPERTY_CONNECTIONS cahnged event.
     * A call to saveiReportConfiguration() is suggested to make the changes permament
     */
    public void addConnection(IReportConnection con)
    {
        getConnections().add(con);
        saveiReportConfiguration();
        propertyChangeSupport.firePropertyChange(PROPERTY_CONNECTIONS, null, getConnections());
    }
    
    /**
     * Please use this method to remove a connection.
     * It will fire an PROPERTY_CONNECTIONS changed event.
     * A call to saveiReportConfiguration() is suggested to make the changes permament
     */
    public void removeConnection(IReportConnection con)
    {
        getConnections().remove(con);
        propertyChangeSupport.firePropertyChange(PROPERTY_CONNECTIONS, null, getConnections());
    }
    
    /***
     *  Load a connection from an XML to type iReportConnection
     * 
     */
    @SuppressWarnings("unchecked")
    public IReportConnection loadConnection(String xml)
    {
        try {
            
             ClassLoader oldClassLoader = Thread.currentThread().getContextClassLoader();
             Thread.currentThread().setContextClassLoader(DOMParser.class.getClassLoader());
             DOMParser parser = new DOMParser();
             org.xml.sax.InputSource input_sss  = new org.xml.sax.InputSource(new java.io.StringReader(xml));
             //input_sss.setSystemId(filename);
             parser.parse( input_sss );

             Thread.currentThread().setContextClassLoader(oldClassLoader);
             
             Document document = parser.getDocument();
             Node node = document.getDocumentElement();


            Node connectionNode = node;
             if (connectionNode.getNodeName() != null && connectionNode.getNodeName().equals("iReportConnection"))
             {
                // Take the CDATA...
                    String connectionName = "";
                    String connectionClass = "";
                    HashMap hm = new HashMap();
                    NamedNodeMap nnm = connectionNode.getAttributes();
                    if ( nnm.getNamedItem("name") != null) connectionName = nnm.getNamedItem("name").getNodeValue();
                    if ( nnm.getNamedItem("connectionClass") != null) connectionClass = nnm.getNamedItem("connectionClass").getNodeValue();

                    // Get all connections parameters...
                    NodeList list_child2 = connectionNode.getChildNodes();
                    for (int ck2=0; ck2< list_child2.getLength(); ck2++) {
                        String parameterName = "";
                        Node child_child = list_child2.item(ck2);
                        if (child_child.getNodeType() == Node.ELEMENT_NODE && child_child.getNodeName().equals("connectionParameter")) {

                            NamedNodeMap nnm2 = child_child.getAttributes();
                            if ( nnm2.getNamedItem("name") != null)
                                parameterName = nnm2.getNamedItem("name").getNodeValue();
                            hm.put( parameterName,Misc.readPCDATA(child_child));
                        }
                    }

                    try {
                        IReportConnection con = (IReportConnection) Class.forName(connectionClass, true, getReportClassLoader()).newInstance();
                        con.loadProperties(hm);
                        con.setName(connectionName);
                        return con;
                    } catch (Exception ex) {

                        /*
                        JOptionPane.showMessageDialog(this,
                            Misc.formatString("Error loading  {0}", new Object[]{connectionName}), //"messages.connectionsDialog.errorLoadingConnection"
                            "Error", JOptionPane.ERROR_MESSAGE);
                         */
                        ex.printStackTrace();
                    }
                }
         } catch (Exception ex)
         {
             /*
             JOptionPane.showMessageDialog(this,
                                Misc.formatString("Error loading connections.\n{0}", new Object[]{ex.getMessage()}), //"messages.connectionsDialog.errorLoadingConnections"
                                "Error", JOptionPane.ERROR_MESSAGE);
             */
            ex.printStackTrace();
         }

         return null;
    }


    /**
     *  Return the current default connection reading the connection  name from the preferences
     *  if required.
     */
    public IReportConnection getDefaultConnection()
    {
        if (defaultConnection == null && connections.size() > 0)
        {
            String defaultConnectionName = getPreferences().get(DEFAULT_CONNECTION_NAME, null);
            if (defaultConnectionName != null)
            {
                for (IReportConnection con : getConnections())
                {
                    if (con.getName() != null && con.getName().equals(defaultConnectionName))
                    {
                        defaultConnection = con;
                        break;
                    }
                }
            }
            if (defaultConnection == null)
            {
                defaultConnection = connections.get(0);
            }
        }
        return defaultConnection;
    }
    
    public void setDefaultConnection(IReportConnection connection)
    {
        IReportConnection con = getDefaultConnection();
        defaultConnection = connection;
        if (defaultConnection == null)
        {
            getPreferences().remove(DEFAULT_CONNECTION_NAME);
        }
        else
        {
            getPreferences().put(DEFAULT_CONNECTION_NAME, defaultConnection.getName());
        }
        propertyChangeSupport.firePropertyChange(PROPERTY_DEFAULT_CONNECTION, con, defaultConnection);
    }
    
    public void saveiReportConfiguration()
    {
        try {
            int i=0;
            
            for (IReportConnection con : getConnections())
            {
                StringWriter sw = new StringWriter();
                con.save( new PrintWriter( sw ) );
                getPreferences().put("connection."+i,sw.toString());
                i++;
            }
            
            // Remove all the remaining connections...
            while (getPreferences().get("connection."+i, null) != null)
            {
                getPreferences().remove("connection."+i);
                i++;
            }
            
            getPreferences().flush();
        } catch (BackingStoreException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
    
    /**
     *  Lazy initialization of the available implementations
     */
    public List getConnectionImplementations()
    {
        if (connectionImplementations == null)
        {
            connectionImplementations = new java.util.ArrayList();
            addDefaultConnectionImplementations();
        }
        return connectionImplementations;
    }
    
    /** 
     * This method provides the preferred way to register a new connection type
     */
    @SuppressWarnings("unchecked")
    public boolean addConnectionImplementation(String className)
    {
        if (getConnectionImplementations().contains(className)) return true;
        
        try {
            
            Class.forName(className, true, getReportClassLoader());
            getConnectionImplementations().add(className);
            return true;
        } catch (Throwable t)
        {
            t.printStackTrace();
        }
        return false;
    }

    public static ClassLoader getReportClassLoader()
    {
        return getReportClassLoader(false);
    }

    public static ClassLoader getReportClassLoader(boolean recreate)
    {
        return IReportManager.getInstance().getReportClassLoaderImpl(recreate);
    }

    private ClassLoader getReportClassLoaderImpl(boolean recreate)
    {
        if (recreate || reportClassLoader == null)
        {
            ClassLoader syscl = Lookup.getDefault().lookup(ClassLoader.class);
            reportClassLoader = new ReportClassLoader(syscl);
            //reportClassLoader.rescanAdditionalClasspath();
        }
        
        /*
        org.netbeans.editor.Registry.getMostActiveComponent();

        DataObject.Registry registries = DataObject.getRegistry();
        registries.
        Lookup lookup = Lookup.getDefault();
        lookup.lookup(arg0);
        
        ProxyClassLoader 
        */
        // Add all the dabatase classpath entries...
        JDBCDriverManager manager = JDBCDriverManager.getDefault();
        
        JDBCDriver[] drivers = manager.getDrivers();
        InstalledFileLocator locator = InstalledFileLocator.getDefault();

        
        for (int i=0; i<drivers.length; ++i)
        {
            URL[] urls = drivers[i].getURLs();
            for (int j=0; j<urls.length; ++j)
            {
                String path = urls[j].getPath();
                if (path.startsWith("/"))
                {
                    path = path.substring(1);
                }
                if (path.length() == 0) continue;
                File f = new File(path);
                if (!f.exists())
                {
                    f = locator.locate(path, null, false);
                }

                if (f != null && f.exists())
                {
                    try {
                        reportClassLoader.addNoRelodablePath( f.getCanonicalPath() );
                        
                    } catch (IOException ex) {}
                }
            }
        }
        reportClassLoader.rescanAdditionalClasspath();

        // set the not cached classpath...
        //

        String separator = System.getProperty("path.separator");
        List<String> rcp = getRelodableClasspath();
        /* THIS DOES NOT WORK... let's use a brand new classloader...
        String fullRcp = "";
        for (String path : rcp)
        {
            fullRcp += path + separator;
        }
        reportClassLoader.setRelodablePaths(fullRcp);
        */
        List<URL> urls = new ArrayList<URL>();
        int i=0;
        for (String path : rcp)
        {
            File f = new File(path);
            try {
                urls.add(f.toURL());
            } catch (MalformedURLException ex) {
                //Exceptions.printStackTrace(ex);
            }
        }
        
        URLClassLoader urlCl = new URLClassLoader( urls.toArray(new URL[urls.size()]) , reportClassLoader);

        return urlCl;
    }

    /**
     *  Set the give object selected in the outline view.
     *  The lookup of the object is done looking first at the node that has this object in his lookup...
     */
    public void setSelectedObject(Object obj) {
        
        
        org.openide.nodes.Node root = OutlineTopComponent.getDefault().getExplorerManager().getRootContext();
        org.openide.nodes.Node node = null;
        if (obj == null)
        {
            node = root;
        }
        else
        {
            node = findNodeOf(obj, root);
        }
        
        if (node != null)
        {
            try {
                OutlineTopComponent.getDefault().getExplorerManager().setSelectedNodes(new org.openide.nodes.Node[]{node});
            } catch (PropertyVetoException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        
    }

    /**
     *  Find the node that that has this object in his lookup, or the object is a well know object
     *  and can be found using a GenericLookup instance...
     */
    public String str="";
    public org.openide.nodes.Node findNodeOf(Object obj, org.openide.nodes.Node root) {

        str +=" ";
        org.openide.nodes.Node candidate = null;
        //System.out.println(str + "Looking for " + obj + " in "+root);
        //System.out.flush();
        if (obj == null || root == null) return null;
        
        // Look in the lookup
        if (root.getLookup().lookup(obj.getClass()) == obj)
        {
            //System.out.println(str + "Probably found " + obj + " in "+root);
            //System.out.flush();
            candidate = root;
        }
        
        org.openide.nodes.Node[] children = root.getChildren().getNodes(true);
        for (int i=0; i<children.length; ++i)
        {
            org.openide.nodes.Node res = findNodeOf(obj, children[i]);
            if (res != null)
            {
                //System.out.println(str + "Found in " + res);
                //System.out.flush();
                return res;
            }
            str = str.substring(0, str.length()-1);
        }
        return candidate;
    }
    
    private void addDefaultConnectionImplementations()
    {
        addConnectionImplementation("com.jaspersoft.ireport.designer.connection.JDBCConnection");
        addConnectionImplementation("com.jaspersoft.ireport.designer.connection.JDBCNBConnection");
        addConnectionImplementation("com.jaspersoft.ireport.designer.connection.JRXMLDataSourceConnection");
        addConnectionImplementation("com.jaspersoft.ireport.designer.connection.JavaBeanDataSourceConnection");
        addConnectionImplementation("com.jaspersoft.ireport.designer.connection.JRCSVDataSourceConnection");
        addConnectionImplementation("com.jaspersoft.ireport.designer.connection.JRDataSourceProviderConnection");
        addConnectionImplementation("com.jaspersoft.ireport.designer.connection.JRCustomDataSourceConnection");
        addConnectionImplementation("com.jaspersoft.ireport.designer.connection.JREmptyDatasourceConnection");
        addConnectionImplementation("com.jaspersoft.ireport.designer.connection.JRHibernateConnection");
        addConnectionImplementation("com.jaspersoft.ireport.designer.connection.JRSpringLoadedHibernateConnection");
        addConnectionImplementation("com.jaspersoft.ireport.designer.connection.EJBQLConnection");
        addConnectionImplementation("com.jaspersoft.ireport.designer.connection.JRXMLADataSourceConnection");
        addConnectionImplementation("com.jaspersoft.ireport.designer.connection.MondrianConnection");
        addConnectionImplementation("com.jaspersoft.ireport.designer.connection.QueryExecuterConnection");
    }
    
    /**
     *  Shortcut for getPreferences().get(key, def);
     */
    public String getProperty(String key, String def)
    {
        return getPreferences().get(key, def);
    }
    
    /**
     *  Shortcut for getPreferences().get(key, null);
     */
    public String getProperty(String key)
    {
        return getPreferences().get(key, null);
    }

    /**
     *  Return the user defined set of additional paths for the classpath.
     */
    public String getCurrentDirectory() {
        return getPreferences().get(CURRENT_DIRECTORY, System.getProperty("user.dir"));
    }
    
    /**
     *  Set the path to use as current directory when opem a file/directory dialog box.
     */
    public void setCurrentDirectory(String currentDirectory) {
        setCurrentDirectory(currentDirectory, true);
    }
    
    /**
     *  Set the path to use as current directory when opem a file/directory dialog box.
     */
    public void setCurrentDirectory(String currentDirectory, boolean save) {
        setCurrentDirectory( new File(currentDirectory), save);
    }
    
    /**
     *  Set the current directory.
     *  If save = true, the config file is updated...
     */
    public void setCurrentDirectory( File f, boolean save) {
        String currentDirectory = "";
        if( f == null || !f.exists() ) return;

        try {
            if( f.isDirectory() ) {
                currentDirectory = f.getAbsolutePath();
            } else {
                currentDirectory = f.getParentFile().getAbsolutePath();
            }

            if (save) {
                getPreferences().put(CURRENT_DIRECTORY, currentDirectory);
            }
        } catch (Exception ex) {
        }
    }
    
    /**
     * Returns the Preferences object associated with iReport
     */
    public static Preferences getPreferences()
    {
        return NbPreferences.forModule(IReportManager.class);
    }
    
    /**
     *  Return the user defined set of additional paths for the classpath.
     */
    public List<String> getClasspath() {
        ArrayList<String> cp = new ArrayList<String>();
        String[] paths = getPreferences().get(IREPORT_CLASSPATH, "").split(";");
        for (int idx = 0; idx < paths.length; idx++) {
            cp.add(paths[idx]);
        }
        return cp;
    }

    /**
     *  Return the user defined set of additional paths for the classpath.
     */
    public List<String> getRelodableClasspath() {
        ArrayList<String> cp = new ArrayList<String>();
        String[] paths = getPreferences().get(IREPORT_RELODABLE_CLASSPATH, "").split(";");
        for (int idx = 0; idx < paths.length; idx++) {
            cp.add(paths[idx]);
        }
        return cp;
    }
    
    /**
     *  Save the new classpath
     */
    public void setClasspath(List<String> cp) {
        String classpathString = "";
        for (String path : cp)
        {
            classpathString += path + ";";
        }
        getPreferences().put(IREPORT_CLASSPATH, classpathString);

        if (reportClassLoader != null)
        {
            reportClassLoader.rescanAdditionalClasspath();
        }
    }

    public void setRelodableClasspath(List<String> cp) {
        String classpathString = "";
        for (String path : cp)
        {
            classpathString += path + ";";
        }
        getPreferences().put(IREPORT_RELODABLE_CLASSPATH, classpathString);
    }

    public void updateConnection(int i, IReportConnection con) {
        getConnections().set(i, con);
        propertyChangeSupport.firePropertyChange(PROPERTY_CONNECTIONS, null, getConnections());
    }
    
    
    /** 
     * Return the list of QueryExecuterDef in memory.
     * When the list is initialized, it is fileld with the following predefined QueryExecuterDef languages:
     * sql, SQL, xPath, XPath, hql, HQL, mdx, MDX, ejbql, EJBQL, xmla-mdx
     */
    public ArrayList<QueryExecuterDef> getQueryExecuters() {
        
        if (queryExecuters == null)
        {
            queryExecuters = new java.util.ArrayList<QueryExecuterDef>();
            // Adding defaults....
            addQueryExecuterDef(new QueryExecuterDef("sql", 
                        JRProperties.getProperty("net.sf.jasperreports.query.executer.factory.sql"),
                        "com.jaspersoft.ireport.designer.data.fieldsproviders.SQLFieldsProvider"), true);

            addQueryExecuterDef(new QueryExecuterDef("SQL", 
                        JRProperties.getProperty("net.sf.jasperreports.query.executer.factory.SQL"),
                        "com.jaspersoft.ireport.designer.data.fieldsproviders.SQLFieldsProvider"), true);
            
            addQueryExecuterDef(new QueryExecuterDef("xPath", 
                        JRProperties.getProperty("net.sf.jasperreports.query.executer.factory.xPath"),
                        "com.jaspersoft.ireport.designer.data.fieldsproviders.XMLFieldsProvider"), true);
            
            addQueryExecuterDef(new QueryExecuterDef("XPath", 
                        JRProperties.getProperty("net.sf.jasperreports.query.executer.factory.XPath"),
                        "com.jaspersoft.ireport.designer.data.fieldsproviders.XMLFieldsProvider"), true);

            addQueryExecuterDef(new QueryExecuterDef("hql", 
                        JRProperties.getProperty("net.sf.jasperreports.query.executer.factory.hql"),
                        "com.jaspersoft.ireport.designer.data.fieldsproviders.HQLFieldsProvider"), true);
            
            addQueryExecuterDef(new QueryExecuterDef("mdx", 
                        JRProperties.getProperty("net.sf.jasperreports.query.executer.factory.mdx"),
                        "com.jaspersoft.ireport.designer.data.fieldsproviders.MDXFieldsProvider"), true);
            
            addQueryExecuterDef(new QueryExecuterDef("MDX", 
                        JRProperties.getProperty("net.sf.jasperreports.query.executer.factory.MDX"),
                        "com.jaspersoft.ireport.designer.data.fieldsproviders.MDXFieldsProvider"), true);
            
            addQueryExecuterDef(new QueryExecuterDef("ejbql", 
                        JRProperties.getProperty("net.sf.jasperreports.query.executer.factory.ejbql"),
                        "com.jaspersoft.ireport.designer.data.fieldsproviders.EJBQLFieldsProvider"), true);
            
            addQueryExecuterDef(new QueryExecuterDef("EJBQL", 
                        JRProperties.getProperty("net.sf.jasperreports.query.executer.factory.EJBQL"),
                        "com.jaspersoft.ireport.designer.data.fieldsproviders.EJBQLFieldsProvider"), true);
            
            addQueryExecuterDef(new QueryExecuterDef("xmla-mdx", 
                        JRProperties.getProperty("net.sf.jasperreports.query.executer.factory.xmla-mdx"),
                        "com.jaspersoft.ireport.designer.data.fieldsproviders.CincomMDXFieldsProvider"), true);
            
            
            
        }
        return queryExecuters;
    }
    
    /** 
     * This method provides the preferred way to register a new connection type
     * Return false if a custom QueryExecuter is already defined for the specified 
     * language and overrideSameLanguage is false.
     *
     * if qed is null, the method returns false.
     */
    public boolean addQueryExecuterDef(QueryExecuterDef qed, boolean overrideSameLanguage)
    {
        if (qed == null) return false;
        // Look for another qed with the same language...
        boolean found = false; // Override the QE at that position...
        
        for (int i=0; i<getQueryExecuters().size(); ++i)
        {
            QueryExecuterDef tqe = getQueryExecuters().get(i);
            if (tqe.getLanguage().equals( qed.getLanguage()) )
            {
                if (overrideSameLanguage)
                {
                   getQueryExecuters().set(i, qed);
                   found = true;
                   break;
                }
                else
                {
                    return false;
                }
            }
        }
        
        if (!found)
        {
            getQueryExecuters().add( qed );
        }
        
        // register the QE in the JasperServer properties...
        net.sf.jasperreports.engine.util.JRProperties.setProperty("net.sf.jasperreports.query.executer.factory." + qed.getLanguage(), qed.getClassName());
            
        
        return true;
    }
    
    public JasperDesign getActiveReport()
    {
        try {
            return OutlineTopComponent.getDefault().getCurrentJrxmlVisualView().getReportDesignerPanel().getJasperDesign();
        } catch (Exception ex)
        {
            return null;
        }
    }
    
    
    /**
     * Return the list of available fonts (AWT).
     **/
    public List<IRFont> getIRFonts()
    {
        if (fonts == null)
        {
            fonts = TTFFontsLoader.loadTTFFonts();
        }
        
        return fonts;
    }
    
    /**
     *  Return the user defined set of additional paths for the classpath.
     */
    public List<String> getFontpath() {
        ArrayList<String> cp = new ArrayList<String>();
        String[] paths = getPreferences().get(IREPORT_FONTPATH, "").split(";");
        for (int idx = 0; idx < paths.length; idx++) {
            cp.add(paths[idx]);
        }
        return cp;
    }
    
    /**
     *  Save the new classpath
     */
    public void setFontpath(List<String> cp) {
        String fontpathString = "";
        for (String path : cp)
        {
            fontpathString += path + ";";
        }
        getPreferences().put(IREPORT_FONTPATH, fontpathString);
        
        fonts = null; // Force a rescan when needed.
    }
    
    /**
     *  Shortcut for OutlineTopComponent.getDefault().getCurrentJrxmlVisualView();
     *  Easy way to get the current visual view, assuming it is visible and
     *  correctly registered in the OutlineTopComponent.
     **/
    public JrxmlVisualView getActiveVisualView()
    {
        return OutlineTopComponent.getDefault().getCurrentJrxmlVisualView();
    }
    
    /**
     *  Shortcut for getActiveVisualView().getEditorSupport().notifyModified();
     *  Easy way to say the current report was modified.
     **/
    public boolean notifyReportChange()
    {
        if (getActiveVisualView() != null)
        {
            return getActiveVisualView().getEditorSupport().notifyModified();
        }
        return false;
    }
    
    
    private JRDesignChartDataset chartDatasetClipBoard = null;
    private java.util.List chartSeriesClipBoard = null;
    
    /**
     * Copy of a dataset
     */
    public JRDesignChartDataset getChartDatasetClipBoard() {
        return chartDatasetClipBoard;
    }
    
    /**
     * Set a JRDesignChartDataset to be used with another chart.
     */
    public void setChartDatasetClipBoard(JRDesignChartDataset chartDatasetClipBoard) {
        this.chartDatasetClipBoard = chartDatasetClipBoard;
    }
    
    /**
     * Place to store a list of series to be used with a dataset.
     */
    public java.util.List getChartSeriesClipBoard() {
        return chartSeriesClipBoard;
    }
    
    /**
     * Place to store a list of series to be used with a dataset.
     */
    public void setChartSeriesClipBoard(java.util.List list) {
        chartSeriesClipBoard = list;
    }
    
    private UndoableEdit lastUndoableEdit = null;
    
    // This is a trick to aggregate undo operations done on a set of nodes...
    // We try to add all to the last undo op if it was created in the last
    // 100 milliseconds...
    private long lastUndoableEditTime = 0;
    
    public void addUndoableEdit(UndoableEdit edit)
    {
        addUndoableEdit(edit, false);
    }
    
    private boolean forceAggregateUndo = false;
    public void setForceAggregateUndo(boolean b)
    {
        forceAggregateUndo = b;
    }
    
    public void addUndoableEdit(UndoableEdit edit, boolean aggregate)
    {
        notifyReportChange();
        if (aggregate || forceAggregateUndo)
        {
            if (lastUndoableEdit != null &&
                 lastUndoableEdit instanceof AggregatedUndoableEdit &&
                 System.currentTimeMillis() - lastUndoableEditTime < 100)
             {
                 ((AggregatedUndoableEdit)lastUndoableEdit).concatenate(edit);
                 lastUndoableEditTime = System.currentTimeMillis();
                 return;
             }
        }
        lastUndoableEditTime = System.currentTimeMillis();
        lastUndoableEdit = edit;
        getActiveVisualView().getUndoRedoManager().undoableEditHappened(new UndoableEditEvent(this, edit));
    }
    
    public UndoableEdit getLastUndoableEdit()
    {
        return lastUndoableEdit;
    } 
    
    /**
     * Return the last value specified by the user for this parameter when prompted...
     * 
     * @param p
     * @return
     */
    public Object getLastParameterValue(JRParameter p)
    {
          Object o = parameterValues.get(p.getName() + " " + p.getValueClassName());
          
          if (p.getValueClass().isInstance(o))
          {
              return o;
          }
          return null;
    }
    
    /**
     * set the last value specified by the user for this parameter when prompted...
     * 
     * @param p
     * @return
     */
    public void setLastParameterValue(JRParameter p, Object value)
    {
          if (p == null || value == null || p.getValueClass() == null) return;
          parameterValues.put(p.getName() + " " + p.getValueClassName(), value);
    }

    public java.util.List<FileResolver> getFileResolvers() {
        
        if (fileResolvers == null)
        {
            fileResolvers = new ArrayList<FileResolver>();
        }
        return fileResolvers;
    }

    public void setFileResolvers(java.util.List<FileResolver> fileResolvers) {
        this.fileResolvers = fileResolvers;
    }
    
    
    
    private final Set<JrxmlVisualViewActivatedListener> listeners = new HashSet<JrxmlVisualViewActivatedListener>(1); // or can use ChangeSupport in NB 6.0
    public final void addJrxmlVisualViewActivatedListener(JrxmlVisualViewActivatedListener l) {
        synchronized (listeners) {
            listeners.add(l);
        }
    }
    public final void removeJrxmlVisualViewActivatedListener(JrxmlVisualViewActivatedListener l) {
        synchronized (listeners) {
            listeners.remove(l);
        }
    }
    public final void fireJrxmlVisualViewActivatedListenerEvent(JrxmlVisualView view) {
        Iterator<JrxmlVisualViewActivatedListener> it;
        synchronized (listeners) {
            it = new HashSet<JrxmlVisualViewActivatedListener>(listeners).iterator();
        }
    
        while (it.hasNext()) {
            it.next().jrxmlVisualViewActivated(view);
        }
    }


    public void createPaletteItem()
    {

        //PaletteItem item = CreatePageNumberTextfieldAction.createPaletteItem();
    }
}
